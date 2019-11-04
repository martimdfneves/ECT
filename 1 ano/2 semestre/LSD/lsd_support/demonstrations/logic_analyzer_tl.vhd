----------------------------------------------------------------------------------------------------------------------------------------------------------------
-- LSD.TOS, April 2018 (DO NOT REMOVE THIS LINE), VHDL 2008
--
-- Logical analyzer demonstration.
--
-- Draw (VGA output) the state of irda_rxd, sw(17 downto 0), and of a 1kHz clock, sampled at 100 kHz.
-- * The display is updated whenever one of these signals changes or when key(2) is pressed.
-- * The 1kHz clock is reset when the logical analyzer starts recording.
-- * The red vertical cursor can be moved using
--     key(0) ... move cursor to the right,
--     key(1) ... move cursor to the left.
-- * The image is scrolled to try to keep the cursor away from the borders of the screen.
-- * The cursor position is displayed, in decimal, in the top area of the display.
-- * key(3), when pressed, freezes all screen updates (useful to capture a screen image).
--
-- The logic analyzer demonstration (under GNU/Linux, create and compile it using the command "make logic_analyzer") uses the following files:
-- * demonstrations/logic_analyzer_tl.vhd
-- * vhdl_code/vga_config.vhd
-- * vhdl_code/vga.vhd
-- * vhdl_code/clock_generator.vhd
-- * vhdl_code/font_8x8_bold.vhd
-- * vhdl_code/font_16x16_bold.vhd
-- * vhdl_code/pulse_generator.vhd
-- * vhdl_code/screen_capture.vhd
-- * vhdl_code/rs232_controller.vhd
-- * vhdl_code/debouncer.vhd
--
-- To be done in the future: use a P2/2 mouse to control the cursor position.
--

library IEEE;
use     IEEE.STD_LOGIC_1164.ALL;
use     IEEE.NUMERIC_STD.ALL;
use     WORK.VGA_CONFIG.ALL;

entity logic_analyzer_tl is
  generic
  (
    SCREEN_CAPTURE_SUPPORT : boolean                    := false;   -- if true, include code to capture a VGA screen
    N_SIGNALS              : integer range 1 to 32      := 20;      -- in this demonstration, irda_rdx plus the 18 switches, plus a 10kHz clock
    N_SAMPLES              : integer range 2048 to 8192 := 8192;    -- number of samples to record
    SAMPLING_FREQUENCY     : real range 1.0 to 10.0e6   := 100.0e3; -- sampling frequency (in Hz)
    DEAD_TIME              : real range 0.01 to 1.0     := 0.2      -- minimum delay between recordings (in seconds)
  );
  port
  (
    clock_50 : in std_logic;

    uart_rxd : in  std_logic;  -- screen capture interface
    uart_txd : out std_logic;  -- screen capture interface

    key  : in  std_logic_vector( 3 downto 0);
    ledr : out std_logic_vector(17 downto 0);
    ledg : out std_logic_vector( 8 downto 0);

    sw       : in std_logic_vector(17 downto 0);
    irda_rxd : in std_logic;

    vga_clk     : out std_logic;
    vga_hs      : out std_logic;
    vga_vs      : out std_logic;
    vga_sync_n  : out std_logic;
    vga_blank_n : out std_logic;
    vga_r       : out std_logic_vector(7 downto 0);
    vga_g       : out std_logic_vector(7 downto 0);
    vga_b       : out std_logic_vector(7 downto 0)
  );
end logic_analyzer_tl;

architecture gets_the_job_done of logic_analyzer_tl is
  --
  -- The master clock is the VGA clock for all VGA projects
  --
  constant CLOCK_FREQUENCY : real := VGA_FREQUENCY;
  signal clock : std_logic;
  --
  -- Optional screen capture signals (this signal will be discarded if SCREEN_CAPTURE_SUPPORT is false
  --
  signal screen_capture_freeze : std_logic;
  --
  -- User interface (key inputs)
  --
  signal freeze : std_logic; -- if '1' the screen contents do not change (useful when capturing a screen image using the screen_captire entity)
  signal key_0 : std_logic; -- inverted key(0), sampled at the end of every VGA frame
  signal key_1 : std_logic; -- inverted key(1), sampled at the end of every VGA frame
  signal key_2 : std_logic; -- inverted key(2), sampled at the end of every VGA frame
  signal key_3 : std_logic; -- inverted key(3), sampled at the end of every VGA frame
  constant AUTO_DELTA : integer := integer(0.1*VGA_REFRESH_RATE); -- auto-repeat delay decrement (0.1 seconds)
  constant AUTO_FIRST : integer := 5*AUTO_DELTA;                  -- first auto-repeat delay (0.5 seconds)
  signal key_0_count       : integer range 0 to AUTO_FIRST;  -- key(0) counter
  signal key_0_count_limit : integer range 0 to AUTO_FIRST;  -- key(0) counter limit
  signal key_0_delta       : vga_x_t;                        -- delta cursor movement
  signal key_1_count       : integer range 0 to AUTO_FIRST;  -- key(1) counter
  signal key_1_count_limit : integer range 0 to AUTO_FIRST;  -- key(1) counter limit
  signal key_1_delta       : vga_x_t;                        -- delta cursor movement
  --
  -- 1kHz clock (active only when recording = '1')
  --
  constant CLOCK_1K_COUNT_LIMIT : integer := integer(CLOCK_FREQUENCY/2.0e3)-1;
  signal clock_1k_count         : integer range 0 to CLOCK_1K_COUNT_LIMIT;
  signal clock_1k               : std_logic;
  --
  -- Waveform recorder
  --
  signal sampling_pulse : std_logic;                                                     -- sampling pulse
  signal recording      : std_logic := '0';                                              -- active when recording a waveform
  signal dead_counter   : integer range 0 to integer(DEAD_TIME*SAMPLING_FREQUENCY) := 0; -- used to force a delay between recordings
  signal write_data_0   : std_logic_vector(N_SIGNALS-1 downto 0); -- data to record
  signal write_data_1   : std_logic_vector(N_SIGNALS-1 downto 0); --   delayed one sample (if different from write_data_0 start a new recording)
  signal write_data_2   : std_logic_vector(N_SIGNALS-1 downto 0); --   delayed two samples
  signal write_data_3   : std_logic_vector(N_SIGNALS-1 downto 0); --   delayed three samples
  signal write_data_4   : std_logic_vector(N_SIGNALS-1 downto 0); --   delayed four samples (this is the one recorded)
  signal write_end_0    : std_logic;                              -- end of write buffer indicator
  signal write_end_1    : std_logic;                              --   and its delayed version, to detect a '0' to '1' transition
  --
  -- Text on the top of the display and on the signal labels
  --
  subtype char_t is std_logic_vector(7 downto 0);
  type ram64_t is array(0 to 63) of char_t;
  signal top_line : ram64_t := (X"4C",X"53",X"44",X"2E",X"54",X"4F",X"53",X"20",X"41",X"70",X"72",X"69",X"6C",X"20",X"32",X"30",  -- "LSD.TOS April 20"
                                X"31",X"38",X"20",X"20",X"4C",X"6F",X"67",X"69",X"63",X"20",X"41",X"6E",X"61",X"6C",X"79",X"7A",  -- "18  Logic Analyz"
                                X"65",X"72",X"20",X"20",X"43",X"75",X"72",X"73",X"6F",X"72",X"20",X"61",X"74",X"20",X"30",X"30",  -- "er  Cursor at 00"
                                X"30",X"30",others => X"20");                                                                     -- "00"
  signal number_to_convert : integer range 0 to N_SAMPLES-1 := 0; -- number to convert to base 10
  signal number_sign       : std_logic;                           -- to deal with a possible small negative number
  signal number_delta      : integer range 0 to N_SAMPLES-1 := 0; -- power of 10
  signal number_digit      : integer range 0 to 15;               -- one base-10 digit
  signal number_addr       : integer range 0 to 63 := 63;         -- addres the digit will be written
  signal number_force_zero : std_logic;                           -- used to not write leading zeros
  type ram256_t is array(0 to 255) of char_t;
  signal labels : ram256_t := (X"20",X"20",X"20",X"20",X"20",X"20",X"20",X"20", -- "        "
                               X"69",X"72",X"64",X"61",X"5F",X"72",X"78",X"64", -- "irda_rxd"
                               X"20",X"20",X"73",X"77",X"28",X"20",X"30",X"29", -- "  sw( 0)"
                               X"20",X"20",X"73",X"77",X"28",X"20",X"31",X"29", -- "  sw( 1)"
                               X"20",X"20",X"73",X"77",X"28",X"20",X"32",X"29", -- "  sw( 2)"
                               X"20",X"20",X"73",X"77",X"28",X"20",X"33",X"29", -- "  sw( 3)"
                               X"20",X"20",X"73",X"77",X"28",X"20",X"34",X"29", -- "  sw( 4)"
                               X"20",X"20",X"73",X"77",X"28",X"20",X"35",X"29", -- "  sw( 5)"
                               X"20",X"20",X"73",X"77",X"28",X"20",X"36",X"29", -- "  sw( 6)"
                               X"20",X"20",X"73",X"77",X"28",X"20",X"37",X"29", -- "  sw( 7)"
                               X"20",X"20",X"73",X"77",X"28",X"20",X"38",X"29", -- "  sw( 8)"
                               X"20",X"20",X"73",X"77",X"28",X"20",X"39",X"29", -- "  sw( 9)"
                               X"20",X"20",X"73",X"77",X"28",X"31",X"30",X"29", -- "  sw(10)"
                               X"20",X"20",X"73",X"77",X"28",X"31",X"31",X"29", -- "  sw(11)"
                               X"20",X"20",X"73",X"77",X"28",X"31",X"32",X"29", -- "  sw(12)"
                               X"20",X"20",X"73",X"77",X"28",X"31",X"33",X"29", -- "  sw(13)"
                               X"20",X"20",X"73",X"77",X"28",X"31",X"34",X"29", -- "  sw(14)"
                               X"20",X"20",X"73",X"77",X"28",X"31",X"35",X"29", -- "  sw(15)"
                               X"20",X"20",X"73",X"77",X"28",X"31",X"36",X"29", -- "  sw(16)"
                               X"20",X"20",X"73",X"77",X"28",X"31",X"37",X"29", -- "  sw(17)"
                               X"31",X"6B",X"48",X"7A",X"20",X"63",X"6C",X"6B", -- "1kHz clk"
                               others => X"20");

  --
  -- VGA signals
  --
  signal cursor_pos : integer range 0 to N_SAMPLES-1    := 0;  -- horizontal position of the cursor (red vertical line)
  signal x_offset   : integer range -64 to N_SAMPLES-1 := -64; -- quantity to add to vga_data.x to get to the true cursor position
  signal vga_data_0 : vga_data_t;
  --
  -- Signals output by the 0 -> 1 pipeline stage
  --
  signal vga_data_1           : vga_data_t;
  signal waveform_read_addr_1 : integer range 0 to N_SAMPLES-1;
  signal top_line_char_1      : char_t;
  signal top_line_row_1       : integer range 0 to 15;
  signal top_line_column_1    : integer range 0 to 15;
  signal label_char_1         : char_t;
  signal label_row_1          : integer range 0 to 7;
  signal label_column_1       : integer range 0 to 7;
  --
  -- Signals output by the 1 -> 2 pipeline stage
  --
  signal vga_data_2             : vga_data_t;
  signal waveform_number_2      : integer range 0 to N_SIGNALS-1;
  signal waveform_read_data_2   : std_logic_vector(N_SIGNALS-1 downto 0);
  signal label_color_2          : std_logic;
  signal top_line_color_2       : std_logic;
  signal inside_top_line_2      : std_logic;
  signal inside_waveform_area_2 : std_logic;
  signal inside_label_2         : std_logic;
  signal inside_wave_top_2      : std_logic;
  signal inside_wave_middle_2   : std_logic;
  signal inside_wave_bottom_2   : std_logic;
  signal inside_cursor_2        : std_logic;
  --
  -- Signals used and output by the 2 -> 3 pipeline stage
  --
  signal waveform_bit_2x : std_logic;
  signal waveform_bit_3  : std_logic;
  signal vga_data_3 : vga_data_t;
  signal vga_rgb_3  : vga_rgb_t;
begin
  --
  -- The master clock
  --
  MASTER_CLOCK_GENERATOR : entity work.vga_clock_generator(optimal)
    port map
    (
      clock_50  => clock_50,
      vga_clock => clock
    );
  --
  -- The optional screen capture stuff
  --
I:if SCREEN_CAPTURE_SUPPORT = true generate
SC: entity work.screen_capture(gets_the_job_done)
      port map
      (
        clock    => clock,
        vga_data => vga_data_3,
        vga_rgb  => vga_rgb_3,
        uart_rxd => uart_rxd,
        uart_txd => uart_txd,
        freeze   => screen_capture_freeze
      );
  else generate
    screen_capture_freeze <= '0';
  end generate;
  --
  -- Freeze control
  --
  freeze <= key_3 or screen_capture_freeze;
  --
  -- User interface (keys and leds)
  --
  process(clock) is
  begin
    if rising_edge(clock) then
      ledr <= sw;
    end if;
  end process;
  ledg(0) <= key_0;
  ledg(1) <= '0';
  ledg(2) <= key_1;
  ledg(3) <= '0';
  ledg(4) <= key_2;
  ledg(5) <= '0';
  ledg(6) <= key_3;
  ledg(7) <= '0';
  ledg(8) <= recording;
  process(clock) is
  begin
    if rising_edge(clock) then
      --
      -- key(2) could have been debounced, but in this project that is not needed
      --
      key_2 <= not key(2);
      --
      -- key(3) could have been debounced, but in this project that is not needed
      --
      key_3 <= not key(3);
      --
      -- key(0), move cursor to the right (there exists a delay of one VGA frame between the key press and the first cursor movement)
      -- key(1), move cursor to the left (there exists a delay of one VGA frame between the key press and the first cursor movement)
      --
      if vga_data_0.end_of_frame = '1' and freeze = '0' then -- the low sample rate automatically debounces key(0) and key(1)
        --
        -- key(0)
        --
        key_0 <= not key(0);
        if key_0 = '0' then
          -- not pressed
          key_0_count <= 0;
          key_0_count_limit <= AUTO_FIRST;
          key_0_delta <= 1;
        elsif key_0_count /= 0 then
          key_0_count <= key_0_count-1;
        else
          key_0_count <= key_0_count_limit;
          if key_0_count_limit /= AUTO_DELTA then
            key_0_count_limit <= key_0_count_limit-AUTO_DELTA;
          end if;
          if cursor_pos < (N_SAMPLES-1)-key_0_delta then
            cursor_pos <= cursor_pos+key_0_delta;
          else
            cursor_pos <= (N_SAMPLES-1);
          end if;
          if key_0_count_limit = AUTO_DELTA and key_0_delta < 64 then
            key_0_delta <= key_0_delta+1;
          end if;
        end if;
        --
        -- key(1)
        --
        key_1 <= not key(1);
        if key_1 = '0' then
          -- not pressed
          key_1_count <= 0;
          key_1_count_limit <= AUTO_FIRST;
          key_1_delta <= 1;
        elsif key_1_count /= 0 then
          key_1_count <= key_1_count-1;
        else
          key_1_count <= key_1_count_limit;
          if key_1_count_limit /= AUTO_DELTA then
            key_1_count_limit <= key_1_count_limit-AUTO_DELTA;
          end if;
          if cursor_pos > key_1_delta then
            cursor_pos <= cursor_pos-key_1_delta;
          else
            cursor_pos <= 0;
          end if;
          if key_1_count_limit = AUTO_DELTA and key_1_delta < 64 then
            key_1_delta <= key_1_delta+1;
          end if;
        end if;
      end if;
    end if;
  end process;
  --
  -- Convert the cursor position to base 10
  --
  process(clock) is
  begin
    if rising_edge(clock) then
      --
      -- start a new conversion after the end of every frame (the comparison with VGA_HEIGHT+1 is to allow cursor_pos to be properly updated)
      --
      if vga_data_0.x = 0 and vga_data_0.y = VGA_HEIGHT+1 then
        if cursor_pos >= 4 then
          number_to_convert <= cursor_pos-4;
          number_sign <= '0';
        else
          number_to_convert <= 4-cursor_pos;
          number_sign <= '1';
        end if;
        number_delta <= 1000;
        number_digit <= 0;
        number_addr <= 46;
        number_force_zero <= '0';
      elsif number_delta /= 0 then
        if number_to_convert >= number_delta then
          number_to_convert <= number_to_convert-number_delta;
          number_digit <= number_digit+1;
        else
          if number_digit = 0 and number_force_zero = '0' then
            if number_sign = '0' or number_addr /= 48 then -- horrible hack (but, just like the architecture name, it gets the job done...)
              top_line(number_addr) <= X"20";
            else
              top_line(number_addr) <= X"2D";
            end if;
          else
            top_line(number_addr) <= X"3" & std_logic_vector(to_unsigned(number_digit,4));
            number_force_zero <= '1';
          end if;
          number_addr <= number_addr+1;
          number_digit <= 0;
          if number_delta = 1000 then
            number_delta <= 100;
          elsif number_delta = 100 then
            number_delta <= 10;
          elsif number_delta = 10 then
            number_delta <= 1;
            number_force_zero <= '1';
          else
            number_delta <= 0;
          end if;
        end if;
      end if;
    end if;
  end process;
  --
  -- 10kHz clock (active only when recording = '1')
  --
  process(clock) is
  begin
    if rising_edge(clock) then
      if recording = '0' then
        clock_1k <= '0';
        clock_1k_count <= CLOCK_1K_COUNT_LIMIT;
      elsif clock_1k_count /= 0 then
        clock_1k_count <= clock_1k_count-1;
      else
         clock_1k_count <= CLOCK_1K_COUNT_LIMIT;
         clock_1k <= not clock_1k;
      end if;
    end if;
  end process;
  --
  -- Waveform recorder, includes fetching the waveform data for the VGA image
  --
  SAMPLING_PULSE_GENERATOR : entity work.pulse_generator(periodic)
    generic map
    (
      CLOCK_FREQUENCY => CLOCK_FREQUENCY,
      PULSE_FREQUENCY => SAMPLING_FREQUENCY
    )
    port map
    (
      clock => clock,
      reset => '0',
      pulse => sampling_pulse
    );
  WAVEFORM_DATA : entity work.waveform_recorder(gets_the_job_done)
    generic map
    (
      BUFFER_SIZE => N_SAMPLES,
      WORD_SIZE   => N_SIGNALS
    )
    port map
    (
      clock => clock,
      reset => '0',
      write_enable_0 => not freeze and recording and sampling_pulse,
      write_data_0   => write_data_4,
      write_end_1    => write_end_0,
      read_addr_0    => waveform_read_addr_1, -- see VGA part of the code
      read_data_1    => waveform_read_data_2  -- see VGA part of the code
    );
  process(clock) is
  begin
    if rising_edge(clock) then
      if sampling_pulse = '1' then
        write_data_0 <= clock_1k & sw(17 downto 0) & irda_rxd; -- data the logic analyzer will display
        write_data_1 <= write_data_0;
        write_data_2 <= write_data_1;
        write_data_3 <= write_data_2;
        write_data_4 <= write_data_3;
        if dead_counter = 0 and freeze = '0' and recording = '0' and (write_data_0 /= write_data_1 or key_2 = '1') then -- data recording start condition
          recording <= '1';
          dead_counter <= dead_counter'high; -- use largest possible dead_counter value
        elsif recording = '0' and dead_counter /= 0 then
          dead_counter <= dead_counter-1;
        end if;
      end if;
      write_end_1 <= write_end_0;
      if write_end_0 = '1' and write_end_1 = '0' then
        recording <= '0'; -- stop recording as soon as write_end goes from '0' to '1'
      end if;
    end if;
  end process;
  --
  -- VGA entities
  --
  VGA_C : entity work.vga_controller(basic)
    port map
    (
      clock      => clock,
      reset      => '0',
      vga_data_0 => vga_data_0
    );
  VGA_O : entity work.vga_output(safe)
    port map
    (
      clock       => clock,
      vga_data    => vga_data_3,
      vga_rgb     => vga_rgb_3,
      vga_clk     => vga_clk,
      vga_hs      => vga_hs,
      vga_vs      => vga_vs,
      vga_sync_n  => vga_sync_n,
      vga_blank_n => vga_blank_n,
      vga_r       => vga_r,
      vga_g       => vga_g,
      vga_b       => vga_b
    );
  --
  -- Compute x offset (to scroll the waveform data)
  --
  -- This will take several clock cycles, but because cursor_pos in changed immediately after the end of a frame, this "long" computation time is irrelevant
  --
  process(clock) is
  begin
    if rising_edge(clock) then
      if x_offset > -64 and x_offset+(64+VGA_WIDTH/8) > cursor_pos then
        x_offset <= x_offset-1;
      elsif x_offset < N_SAMPLES-VGA_WIDTH and x_offset+(64+7*VGA_WIDTH/8) < cursor_pos then
        x_offset <= x_offset+1;
      end if;
    end if;
  end process;
  --
  -- VGA image generation pipeline stage 0 -> 1
  --
  -- * waveform_read_addr_1 ... waveform read address
  -- * top_line_char_1 ........ character number to display in the top line area
  -- * top_line_row_1 ......... row to display
  -- * top_line_column_1 ...... column to display
  -- * label_char_1 ........... character number to display in the label area
  -- * label_row_1 ............ row to display
  -- * label_column_1 ......... column to display
  --
  process(clock) is
  begin
    if rising_edge(clock) then
      vga_data_1 <= vga_data_0;
      if vga_data_0.y >= 24 then
        vga_data_1.y <= vga_data_0.y+8; -- for the remaining pipeline stages line 24 becomes line 32, and so on (to move part of the image up)
      end if;
      waveform_read_addr_1 <= vga_data_0.x+x_offset;
      top_line_char_1 <= top_line(vga_data_0.x/16);
      top_line_row_1 <= vga_data_0.y mod 16;
      top_line_column_1 <= vga_data_0.x mod 16;
      label_char_1 <= labels(8*(vga_data_0.y/32)+(vga_data_0.x/8));
      label_row_1 <= (vga_data_0.y+4) mod 8;
      label_column_1 <= vga_data_0.x mod 8;
    end if;
  end process;
  --
  -- VGA image generation pipeline stage 1 -> 2
  --
  -- * waveform_read_data_2 ..... the waveform data (computed by the waveform_recorder entity)
  -- * waveform_number_2 ........ waveform number
  -- * label_color_2 ............ the color of the label text (computed by the font_8x8 entity)
  -- * top_line_color_2 ......... the color of the top line text (computed by the font_16x16 entity)
  -- * inside_top_line_2 ........ selection
  -- * inside_waveform_area_2 ...  signals
  -- * inside_label_2 ...........   for
  -- * inside_wave_top_2 ........    the
  -- * inside_wave_middle_2 .....     various
  -- * inside_wave_bottom_2 .....      display
  -- * inside_cursor_2 ..........       regions
  --
  process(clock) is
  begin
    if rising_edge(clock) then
      vga_data_2 <= vga_data_1;
      waveform_number_2 <= vga_data_1.y/32-1;
      --
      -- defaults for the inside_*_2 signals
      --
      inside_top_line_2 <= '0';
      inside_waveform_area_2 <= '0';
      inside_label_2 <= '0';
      inside_wave_top_2 <= '0';
      inside_wave_middle_2 <= '0';
      inside_wave_bottom_2 <= '0';
      inside_cursor_2 <= '0';
      --
      -- overrides for the inside_*_2 signals
      --
      if vga_data_1.y < 16 and vga_data_1.x < 64*16 then
        inside_top_line_2 <= '1';
      end if;
      if vga_data_1.y >= 32 and vga_data_1.y < 32*(1+N_SIGNALS) and (vga_data_1.y mod 32) > 1 and (vga_data_1.y mod 32) < 30 then
        inside_waveform_area_2 <= '1';
        if vga_data_1.x >= 0 and vga_data_1.x < 64 and (vga_data_1.y mod 32) >= 12 and (vga_data_1.y mod 32) <= 19 then
          inside_label_2 <= '1';
        end if;
        if (vga_data_1.y mod 32) = 6 then
          inside_wave_top_2 <= '1';
        end if;
        if (vga_data_1.y mod 32) > 6 and (vga_data_1.y mod 32) < 25 then
          inside_wave_middle_2 <= '1';
        end if;
        if (vga_data_1.y mod 32) = 25 then
          inside_wave_bottom_2 <= '1';
        end if;
      end if;
      if vga_data_1.x+x_offset = cursor_pos then
        inside_cursor_2 <= '1';
      end if;
    end if;
  end process;
  --
  -- VGA image generation pipeline stage 2 -> 3
  --
  -- * vga_rgb_3 ... pixel color
  --
  waveform_bit_2x <= waveform_read_data_2(waveform_number_2); -- N.B. asynchronous signal
  process(clock) is
  begin
    if rising_edge(clock) then
      vga_data_3 <= vga_data_2;
      if vga_data_2.y >= 24 then
        vga_data_3.y <= vga_data_2.y-8; -- undo the y coordinate change (the screen capture entity stops when the y coordinate is VGA_HEIGHT...)
      end if;
      waveform_bit_3 <= waveform_bit_2x; -- delay (value of waveform_bit_2x for the previous pixel)
      --
      -- default background color: almost black
      --
      vga_rgb_3.r <= X"10";
      vga_rgb_3.g <= X"10";
      vga_rgb_3.b <= X"10";
      --
      -- waveform areas in dark blue
      --
      if inside_waveform_area_2 = '1' then
        vga_rgb_3.b <= X"50";
      end if;
      --
      -- red cursor in each waveform area
      --
      if inside_waveform_area_2 = '1' and inside_cursor_2 = '1' then
        vga_rgb_3.r <= X"FF";
      end if;
      --
      -- waveform logic level guide lines in dark green
      --
      if inside_wave_top_2 = '1' or inside_wave_bottom_2 = '1' then
        vga_rgb_3.g <= X"50";
      end if;
      --
      -- waveform in green and waveform transition in darker green
      --
      if vga_data_2.x >= 64 then
        if (inside_wave_top_2 = '1' and waveform_bit_2x = '1') or (inside_wave_bottom_2 = '1' and waveform_bit_2x = '0') then
          vga_rgb_3.g <= X"FF";
        elsif vga_data_2.x /= 64 and inside_wave_middle_2 = '1' and waveform_bit_2x /= waveform_bit_3 then
          vga_rgb_3.g <= X"80";
        end if;
      end if;
      --
      -- label text in dark red
      --
      if inside_label_2 = '1' and label_color_2 = '1' then
        vga_rgb_3.r <= X"A0";
        vga_rgb_3.b <= X"00";
      end if;
      --
      -- top line text in white
      --
      if inside_top_line_2 = '1' and top_line_color_2 = '1' then
        vga_rgb_3.r <= X"FF";
        vga_rgb_3.g <= X"FF";
        vga_rgb_3.b <= X"FF";
      end if;
    end if;
  end process;
  --
  -- Font entities
  --
  FONT_16 : entity work.font_16x16_bold(font_data)
    port map
    (
      clock    => clock,
      char_0   => top_line_char_1(6 downto 0),
      row_0    => std_logic_vector(to_unsigned(top_line_row_1,4)),
      column_0 => std_logic_vector(to_unsigned(top_line_column_1,4)),
      data_1   => top_line_color_2
    );
  FONT_8 : entity work.font_8x8_bold(font_data)
    port map
    (
      clock    => clock,
      char_0   => label_char_1(6 downto 0),
      row_0    => std_logic_vector(to_unsigned(label_row_1,3)),
      column_0 => std_logic_vector(to_unsigned(label_column_1,3)),
      data_1   => label_color_2
    );
end gets_the_job_done;


----------------------------------------------------------------------------------------------------------------------------------------------------------------
-- LSD.TOS, April 2018 (DO NOT REMOVE THIS LINE), VHDL 2008
--
-- waveform recorder (circular buffer)
--

library IEEE;
use     IEEE.STD_LOGIC_1164.ALL;
use     IEEE.NUMERIC_STD.ALL;
use     IEEE.MATH_REAL.ALL;

entity waveform_recorder is
  generic
  (
    BUFFER_SIZE : integer range 512 to 8192;  -- number of data points to record
    WORD_SIZE   : integer range 1 to 32       -- number of bits to record per sample
  );
  port
  (
    clock          : in  std_logic; -- clock
    reset          : in  std_logic; -- if active, reset the waveform recorder (the memory contents will be reset as soon as possible)

    write_enable_0 : in  std_logic;                              -- write enable signal (active during only one clock cycle)
    write_data_0   : in  std_logic_vector(WORD_SIZE-1 downto 0); -- data to write
    write_end_1    : out std_logic;                              -- set to '1' when the buffer is full

    read_addr_0    : in  integer range 0 to BUFFER_SIZE-1;       -- read address (number of bits determined by the range of the integer)
    read_data_1    : out std_logic_vector(WORD_SIZE-1 downto 0)  -- data read
  );
end waveform_recorder;

architecture gets_the_job_done of waveform_recorder is
  --
  -- ROM and RAM memories must have an address range that is a power of 2
  --
  constant ARRAY_SIZE : integer := 2**integer(ceil(log2(real(BUFFER_SIZE))-1.0e-5)); -- dimension of the array used to store samples
  --
  -- Dual port ram memory data
  --
  subtype ram_word_type is std_logic_vector(WORD_SIZE-1 downto 0); -- memory word type
  type ram_type is array(0 to ARRAY_SIZE-1) of ram_word_type;      -- memory array type
  signal ram : ram_type;                                           -- memory array
  --
  -- Dual port memory write address
  --
  signal write_addr    : integer range 0 to ARRAY_SIZE-1 := 0; -- write address
  signal end_of_buffer : std_logic := '0';                     -- end of buffer condition
  --
  -- Reset data
  --
  signal doing_reset : std_logic := '0';                     -- active when reseting memory
  signal reset_addr  : integer range 0 to ARRAY_SIZE-1 := 0; -- reset address
begin
  --
  -- Make sure the generic parameters make sense
  --
  assert (BUFFER_SIZE <= ARRAY_SIZE) and (2*BUFFER_SIZE > ARRAY_SIZE) report "Bad array size" severity failure;
  assert false report "Circular buffer size = " & integer'image(ARRAY_SIZE) severity note;
  --
  -- Dual port ram
  --
  write_end_1 <= end_of_buffer;
  process(clock) is
  begin
    if rising_edge(clock) then
      --
      -- write port
      --
      if reset = '1' then
        write_addr <= 0;
        end_of_buffer <= '0';
        doing_reset <= '1';
        reset_addr <= 0;
      elsif write_enable_0 = '1' then
        ram(write_addr) <= write_data_0;
        if write_addr = BUFFER_SIZE-1 then
          write_addr <= 0; -- warp around (circular buffer)
          end_of_buffer <= '1';
        else
          write_addr <= write_addr+1;
          end_of_buffer <= '0';
        end if;
        if doing_reset = '1' and reset_addr = write_addr then
          if write_addr = BUFFER_SIZE-1 then
            doing_reset <= '0';
          else
            reset_addr <= reset_addr+1;
          end if;
        end if;
      elsif doing_reset = '1' then
        ram(reset_addr) <= (others => '0');
        if reset_addr = BUFFER_SIZE-1 then
          doing_reset <= '0';
        else
          reset_addr <= reset_addr+1;
        end if;
      end if;
      --
      -- read port
      --
      read_data_1 <= ram(read_addr_0); -- read old value
    end if;
  end process;
end gets_the_job_done;
