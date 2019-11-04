----------------------------------------------------------------------------------------------------------------------------------------------------------------
-- LSD.TOS, April 2018 (DO NOT REMOVE THIS LINE), VHDL 2008
--
-- PS/2 device and VGA text frame buffer demonstration.
--
-- If a PS/2 keyboard is detected by the ps2_controller entity, then
-- * turn ledg(0) on,
-- * the switches sw(2 downto 0) control the three keyboard leds,
-- * the last two bytes received from the keyboard are shown in hexadecimal in the four right-hand side seven segment displays.
-- If a PS/2 mouse is detected by the ps2_controller entity, then
-- * turn ledg(1) on,
-- * the leds ledg(7 downto 5) present the current status of the mouse keys,
-- * the four segment displays on the left-hand side present cumulative x and y coordinates of the mouse movement.
-- In both cases, ledg(8) is turned on during one tenth of a second whenever new information is received from the PS/2 device.
-- The PS/2 and VGA controllers are reset when sw(3) is on.
-- Part of a text buffer, with a border filled initially with random characters, is displayed on the VGA screen using a 16x16 font.
-- All bytes received from the keyboard are written in the text buffer.
-- The visible area of the text buffer can be scrolled up and down using sw(4) and sw(5), or using a PS/2 mouse.
--
-- The text buffer demonstration (under GNU/Linux, create and compile it using the command "make text_buffer") uses the following files:
-- * demonstrations/text_buffer_tl.vhd
-- * vhdl_code/vga_config.vhd
-- * vhdl_code/vga.vhd
-- * vhdl_code/clock_generator.vhd
-- * vhdl_code/font_16x16_bold.vhd
-- * vhdl_code/pulse_generator.vhd
-- * vhdl_code/debouncer.vhd
-- * vhdl_code/ps2_controller.vhd
-- * vhdl_code/seven_segment_decoder.vhd
-- * vhdl_code/pseudo_random_generator.vhd
-- * vhdl_code/screen_capture.vhd
-- * vhdl_code/rs232_controller.vhd
--

library IEEE;
use     IEEE.STD_LOGIC_1164.ALL;
use     IEEE.NUMERIC_STD.ALL;
use     IEEE.MATH_REAL.ALL; -- for the ceil() and log2() functions, used only to compute constants at "analysis and elaboration" time
use     WORK.VGA_CONFIG.ALL;

entity text_buffer_tl is
  generic
  (
    SCREEN_CAPTURE_SUPPORT : boolean := true -- if true, include code to capture a VGA screen
  );
  port
  (
    clock_50 : in std_logic;

    uart_rxd : in  std_logic;  -- screen capture interface
    uart_txd : out std_logic;  -- screen capture interface

    ps2_clk : inout std_logic;
    ps2_dat : inout std_logic;

    sw   : in  std_logic_vector( 5 downto 0);
    ledr : out std_logic_vector(17 downto 0);

    ledg : out std_logic_vector(8 downto 0);

    hex0 : out std_logic_vector(6 downto 0);
    hex1 : out std_logic_vector(6 downto 0);
    hex2 : out std_logic_vector(6 downto 0);
    hex3 : out std_logic_vector(6 downto 0);
    hex4 : out std_logic_vector(6 downto 0);
    hex5 : out std_logic_vector(6 downto 0);
    hex6 : out std_logic_vector(6 downto 0);
    hex7 : out std_logic_vector(6 downto 0);

    vga_clk     : out std_logic;
    vga_hs      : out std_logic;
    vga_vs      : out std_logic;
    vga_sync_n  : out std_logic;
    vga_blank_n : out std_logic;
    vga_r       : out std_logic_vector(7 downto 0);
    vga_g       : out std_logic_vector(7 downto 0);
    vga_b       : out std_logic_vector(7 downto 0)
  );
end text_buffer_tl;

architecture gets_the_job_done of text_buffer_tl is
  --
  -- The master clock is the VGA clock for all VGA projects
  --
  constant CLOCK_FREQUENCY : real := VGA_FREQUENCY;
  signal clock : std_logic;
  --
  -- Optional screen capture signals (this signal will be discarded if SCREEN_CAPTURE_SUPPORT is false)
  --
  signal screen_capture_freeze : std_logic;
  --
  -- Debounced switches and reset signal
  --
  signal clean_sw : std_logic_vector(sw'range); -- debounced sw
  signal reset    : std_logic;
  --
  -- PS/2 device interface, with seven segment displays output
  --
  signal keyboard_detected  : std_logic;
  signal key_code           : std_logic_vector(7 downto 0);  -- most recent key code
  signal new_key_code       : std_logic;                     -- new key code pulse
  signal last_two_key_codes : std_logic_vector(15 downto 0); -- last two bytes received from the PS/2 device
  signal mouse_detected     : std_logic;
  signal delta_x            : std_logic_vector(8 downto 0);  -- most recent mouse delta x movement
  signal delta_y            : std_logic_vector(8 downto 0);  -- most recent mouse delta y movement
  signal mouse_buttons      : std_logic_vector(2 downto 0);  -- most recent mouse buttons state
  signal mouse_movement     : std_logic;                     -- mouse movement pulse
  signal mouse_x            : std_logic_vector(7 downto 0) := X"80"; -- cumulative mouse x position
  signal mouse_y            : std_logic_vector(7 downto 0) := X"80"; -- cumulative mouse y position
  signal alarm              : std_logic := '0';              -- ledg(8) status
  signal alarm_pulse        : std_logic;                     -- 10 Hz alarm off pulse (duration of 0.1 seconds gives a frequency of 10 Hz)
  signal tmp_x              : unsigned(9 downto 0);          -- x+delta_x (combinational logic)
  signal tmp_y              : unsigned(9 downto 0);          -- y+delta_y (combinational logic)
  --
  -- Text buffer
  --
  constant N_ROWS_LOG_2 : integer := 7;
  constant N_COLS_LOG_2 : integer := integer(ceil(log2(real(VGA_WIDTH/16))-0.00001));
  constant N_ROWS       : integer := 2**N_ROWS_LOG_2;                          -- number of rows and columns of the
  constant N_COLUMNS    : integer range 1 to 2**N_COLS_LOG_2 := VGA_WIDTH/16;  --   visible part of the text buffer
  constant BORDER       : integer range 1 to 5 := 3;                           -- number of rows/columns surrounding the top left display area
  signal init_buffer    : std_logic := '1';              -- asserted during the initialization of the text buffer memory
  signal write_row      : integer range 0 to 2**N_ROWS_LOG_2-1 := 0;   -- row number for text buffer writes
  signal write_column   : integer range 0 to 2**N_COLS_LOG_2-1 := 0;   -- column number for text buffer writes
  signal write_data     : std_logic_vector(6 downto 0);  -- write data (asynchronous)
  signal rand_char      : std_logic_vector(6 downto 0);  -- random character
  signal pending_data   : std_logic_vector(15 downto 0) := (others => '0');         -- pending write data (bit 15 indicates if there is pending data to write)
  signal space_delay    : integer range 0 to 1+integer(0.05*VGA_REFRESH_RATE) := 0; -- about 50ms delay before writing a space separator
  --
  -- VGA
  --
  signal y_offset       : integer range 0 to 16*N_ROWS-VGA_HEIGHT := 0;
  signal text_x_1       : integer range 0 to 128*16-1;  -- text buffer coordinates of the
  signal text_y_1       : integer range 0 to 128*16-1;  --   pixel that is going to be displayed
  signal text_row_1x    : integer range 0 to 127;       -- row number
  signal text_column_1x : integer range 0 to 127;       -- column number
  signal font_char_2    : std_logic_vector(6 downto 0); -- character number that is
  signal font_char_3    : std_logic_vector(6 downto 0); --   going to be displayed
  signal font_row_2     : integer range 0 to 15;        -- row and column
  signal font_row_3     : integer range 0 to 15;        --   numbers of the
  signal font_column_2  : integer range 0 to 15;        --   character that is
  signal font_column_3  : integer range 0 to 15;        --   going to be displayed
  signal cursor_2       : std_logic;                    -- if asserted, the character
  signal cursor_3       : std_logic;                    --   being dispolayed is at
  signal cursor_4       : std_logic;                    --   the cursor position
  signal border_2       : std_logic;                    -- if asserted, the character
  signal border_3       : std_logic;                    --   being displayed is at
  signal border_4       : std_logic;                    --   the border
  signal color_4        : std_logic;                    -- character pixel color
  signal vga_data_0     : vga_data_t;
  signal vga_data_1     : vga_data_t;
  signal vga_data_2     : vga_data_t;
  signal vga_data_3     : vga_data_t;
  signal vga_data_4     : vga_data_t;
  signal vga_data_5     : vga_data_t;
  signal vga_rgb_5      : vga_rgb_t;
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
        vga_data => vga_data_5,
        vga_rgb  => vga_rgb_5,
        uart_rxd => uart_rxd,
        uart_txd => uart_txd,
        freeze   => screen_capture_freeze
      );
  else generate
    screen_capture_freeze <= '0';
  end generate;
  --
  -- Debounced switches and reset signal
  --
F:for i in sw'range generate
DB: entity work.debouncer(fancy)
      generic map
      (
        CLOCK_FREQUENCY => CLOCK_FREQUENCY,
        WINDOW_DURATION => 20.0e-6,  -- 20μs
        DELAY_DURATION  => 20.0e-3,  -- 20ms
        INITIAL_LEVEL   => '0'       -- assume the switch is initially in the off position
      )
      port map
      (
        clock             => clock,
        reset             => reset,
        dirty             => sw(i),
        clean             => clean_sw(i),
        zero_to_one_pulse => open,
        one_to_zero_pulse => open
      );
  end generate;
  reset <= clean_sw(3);
  ledr(17) <= screen_capture_freeze;
  ledr(16 downto sw'high+1) <= (others => '0');
  ledr(sw'range) <= clean_sw;
  --
  -- PS/2 device interface, with seven segment displays output
  --
P:entity work.ps2_controller(smart)
    generic map
    (
      CLOCK_FREQUENCY => CLOCK_FREQUENCY
    )
    port map
    (
      clock => clock,
      reset => reset,
      ps2_clk => ps2_clk,
      ps2_dat => ps2_dat,
      keyboard_detected => keyboard_detected,
      keyboard_leds     => clean_sw(1) & clean_sw(2) & clean_sw(0), -- num, caps, scroll lock
      key_code          => key_code,
      valid_key_code    => new_key_code,
      mouse_detected   => mouse_detected,
      mouse_delta_x    => delta_x,
      mouse_delta_y    => delta_y,
      mouse_buttons    => mouse_buttons,
      valid_mouse_data => mouse_movement
    );
D:entity work.pulse_generator(periodic)
    generic map
    (
      CLOCK_FREQUENCY => CLOCK_FREQUENCY,
      PULSE_FREQUENCY => 10.0 -- 10 Hz
    )
    port map
    (
      clock => clock,
      reset => new_key_code or mouse_movement,
      pulse => alarm_pulse
    );
  tmp_x <= ("00" & unsigned(mouse_x))+(delta_x(8) & unsigned(delta_x));
  tmp_y <= ("00" & unsigned(mouse_y))+(delta_y(8) & unsigned(delta_y));
  process(clock) is
  begin
    if rising_edge(clock) then
      if reset = '1' then
        alarm <= '0';
        last_two_key_codes <= (others => '0');
        mouse_x <= X"80";
        mouse_y <= X"80";
      end if;
      if new_key_code = '1' or mouse_movement = '1' then
        alarm <= '1'; -- turn led on
      elsif alarm_pulse = '1' then
        alarm <= '0'; -- turn led off
      end if;
      if new_key_code = '1' then
        last_two_key_codes <= last_two_key_codes(7 downto 0) & key_code;
      end if;
      if mouse_movement = '1' then
        if tmp_x(9) = '1' then
          mouse_x <= X"00";
        elsif tmp_x(8) = '1' then
          mouse_x <= X"FF";
        else
          mouse_x <= std_logic_vector(tmp_x(7 downto 0));
        end if;
        if tmp_y(9) = '1' then
          mouse_y <= X"00";
        elsif tmp_y(8) = '1' then
          mouse_y <= X"FF";
        else
          mouse_y <= std_logic_vector(tmp_y(7 downto 0));
        end if;
      end if;
    end if;
  end process;
  ledg(8) <= alarm;
  ledg(7 downto 5) <= mouse_buttons;
  ledg(4 downto 2) <= (others => '0');
  ledg(1) <= mouse_detected;
  ledg(0) <= keyboard_detected;

K3:entity work.seven_segment_decoder port map(code => '0' & last_two_key_codes(3*4+3 downto 3*4+0),enable => keyboard_detected,seg => hex3);
K2:entity work.seven_segment_decoder port map(code => '0' & last_two_key_codes(2*4+3 downto 2*4+0),enable => keyboard_detected,seg => hex2);
K1:entity work.seven_segment_decoder port map(code => '0' & last_two_key_codes(1*4+3 downto 1*4+0),enable => keyboard_detected,seg => hex1);
K0:entity work.seven_segment_decoder port map(code => '0' & last_two_key_codes(0*4+3 downto 0*4+0),enable => keyboard_detected,seg => hex0);

M3:entity work.seven_segment_decoder port map(code => '0' & mouse_x(1*4+3 downto 1*4+0),enable => mouse_detected,seg => hex7);
M2:entity work.seven_segment_decoder port map(code => '0' & mouse_x(0*4+3 downto 0*4+0),enable => mouse_detected,seg => hex6);
M1:entity work.seven_segment_decoder port map(code => '0' & mouse_y(1*4+3 downto 1*4+0),enable => mouse_detected,seg => hex5);
M0:entity work.seven_segment_decoder port map(code => '0' & mouse_y(0*4+3 downto 0*4+0),enable => mouse_detected,seg => hex4);

  --
  -- The write side of the text buffer
  --
T:entity work.text_buffer(synchronous_read)
    generic map
    (
      N_ROWS_LOG_2 => N_ROWS_LOG_2,
      N_COLS_LOG_2 => N_COLS_LOG_2,
      N_BITS       => 7
    )
    port map
    (
      clock => clock,
      write_row    => std_logic_vector(to_unsigned(write_row,N_ROWS_LOG_2)),
      write_column => std_logic_vector(to_unsigned(write_column,N_COLS_LOG_2)),
      write_data   => write_data,
      write_enable => '1', -- we are always writing
      read_row_0 => std_logic_vector(to_unsigned(text_row_1x,N_ROWS_LOG_2)),
      read_column_0 => std_logic_vector(to_unsigned(text_column_1x,N_COLS_LOG_2)),
      read_data_1 => font_char_2
    );
R:entity work.pseudo_random_generator(light)
    generic map
    (
      N_BITS => 7,
      SEED   => X"FE_01_DC_23_BA_45"
    )
    port map
    (
      clock  => clock,
      enable => '1',
      rnd    => rand_char
    );
  process(init_buffer,write_row,write_column,rand_char,pending_data) is -- combinational part of the write side of the text buffer
  begin
    if init_buffer = '1' then
      if write_row >= BORDER and write_row < N_ROWS-BORDER and write_column >= BORDER and write_column < N_COLUMNS-BORDER then
        write_data <= "0100000"; -- space
      else
        write_data <= rand_char;
      end if;
    elsif pending_data(15) = '1' then
      write_data <= pending_data(14 downto 8);
    else
      write_data <= "0001111"; -- write a special character (thin X inside a box) at the cursor position
    end if;
  end process;
  process(clock) is -- sequential part of the write side of the text buffer
  begin
    if rising_edge(clock) then
      if vga_data_0.end_of_frame = '1' and SPACE_DELAY /= 0 then
        space_delay <= space_delay-1;
      end if;
      if screen_capture_freeze = '0' then
        -- update the pending write data buffer
        if new_key_code = '1' then
          -- most significant hexadecimal digit of the key code
          pending_data(15) <= '1';
          if unsigned(key_code(7 downto 4)) < 10 then
            pending_data(14 downto 12) <= "011";
            pending_data(11 downto 8) <= key_code(7 downto 4);
          else
            pending_data(14 downto 12) <= "100";
            pending_data(11 downto 8) <= std_logic_vector(unsigned(key_code(7 downto 4))-9);
          end if;
          -- least significant hexadecimal digit of the key code
          pending_data(7) <= '1';
          if unsigned(key_code(3 downto 0)) < 10 then
            pending_data(6 downto 4) <= "011";
            pending_data(3 downto 0) <= key_code(3 downto 0);
          else
            pending_data(6 downto 4) <= "100";
            pending_data(3 downto 0) <= std_logic_vector(unsigned(key_code(3 downto 0))-9);
          end if;
          space_delay <= space_delay'high;
        elsif pending_data(15) = '1' then
          pending_data <= pending_data(7 downto 0) & "00000000"; -- get rid of one character
        elsif space_delay = 1 then
          pending_data(15 downto 8) <= '1' & "0100000"; -- space separator
          space_delay <= 0;
        end if;
        -- update write coordinates
        if init_buffer = '1' then
          if write_column /= N_COLUMNS-1 then
            write_column <= write_column+1;
          else
            write_column <= 0;
            if write_row /= N_ROWS-1 then
              write_row <= write_row+1;
            else
              write_row <= BORDER+1;
              write_column <= BORDER+1;
              init_buffer <= '0';
            end if;
          end if;
        elsif pending_data(15) = '1' then
          if write_column < N_COLUMNS-BORDER-2 then
            write_column <= write_column+1;
          else
            write_column <= BORDER+1;
            if write_row < N_ROWS-BORDER-2 then
              write_row <= write_row+1;
            else
              write_row <= BORDER+1;
            end if;
          end if;
        end if;
      end if;
      -- a reset signal overrides everything
      if reset = '1' then
        write_row <= 0;
        write_column <= 0;
        init_buffer <= '1';
        pending_data <= (others => '0');
      end if;
    end if;
  end process;
  --
  -- The VGA stuff (part of the action happens in the instantiated entities)
  --
  VGA_C : entity work.vga_controller(basic)
    port map
    (
      clock => clock,
      reset => reset,
      vga_data_0 => vga_data_0
    );
  VGA_O : entity work.vga_output(safe)
    port map
    (
      clock       => clock,
      vga_data    => vga_data_5,
      vga_rgb     => vga_rgb_5,
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
  -- VGA image generation pipeline stage 0 -> 1 (vertical scroll)
  --
  process(clock) is
  begin
    if rising_edge(clock) then
      vga_data_1 <= vga_data_0;
      if vga_data_0.end_of_frame = '1' and screen_capture_freeze = '0' then
        if clean_sw(4) = '0' and clean_sw(5) = '1' and y_offset > 0 then
          y_offset <= y_offset-1;
        end if;
        if clean_sw(4) = '1' and clean_sw(5) = '0' and y_offset < 16*N_ROWS-VGA_HEIGHT-1 then
          y_offset <= y_offset+1;
        end if;
      end if;
      if mouse_movement = '1' then
        if y_offset+to_integer(signed(delta_y)) < 0 then
          y_offset <= 0;
        elsif y_offset+to_integer(signed(delta_y)) >= 16*N_ROWS-VGA_HEIGHT then
          y_offset <= 16*N_ROWS-VGA_HEIGHT;
        else
          y_offset <= y_offset+to_integer(signed(delta_y));
        end if;
      end if;
      if reset = '1' then
        y_offset <= 0;
      end if;
      text_x_1 <= vga_data_0.x;
      text_y_1 <= vga_data_0.y+y_offset;
    end if;
  end process;
  --
  -- VGA image generation pipeline stage 1 -> 2 (access text buffer memory, and compute font x and y coordinates)
  --
  text_row_1x <= text_y_1/16;
  text_column_1x <= text_x_1/16;
  process(clock) is
  begin
    if rising_edge(clock) then
      vga_data_2 <= vga_data_1;
      font_row_2 <= text_y_1 mod 16;
      font_column_2 <= text_x_1 mod 16;
      if text_row_1x = write_row and text_column_1x = write_column then
        cursor_2 <= '1';
      else
        cursor_2 <= '0';
      end if;
      if text_row_1x < BORDER or text_row_1x >= N_ROWS-BORDER or text_column_1x < BORDER or text_column_1x >= N_COLUMNS-BORDER then
        border_2 <= '1';
      else
        border_2 <= '0';
      end if;
    end if;
  end process;
  --
  -- VGA image generation pipeline stage 2 -> 3 (delay of one clock cycle to improve Fmax)
  --
  process(clock) is
  begin
    if rising_edge(clock) then
      vga_data_3 <= vga_data_2;
      cursor_3 <= cursor_2;
      border_3 <= border_2;
      font_char_3 <= font_char_2;
      font_row_3 <= font_row_2;
      font_column_3 <= font_column_2;
    end if;
  end process;
  --
  -- VGA image generation pipeline stage 3 -> 4 (access font memory)
  --
  FONT_16 : entity work.font_16x16_bold(font_data)
    port map
    (
      clock => clock,
      char_0 => font_char_3,
      row_0 => std_logic_vector(to_unsigned(font_row_3,4)),
      column_0 => std_logic_vector(to_unsigned(font_column_3,4)),
      data_1 => color_4
    );
  process(clock) is
  begin
    if rising_edge(clock) then
      vga_data_4 <= vga_data_3;
      cursor_4 <= cursor_3;
      border_4 <= border_3;
    end if;
  end process;
  --
  -- VGA image generation pipeline stage 4 -> 5 (choose color)
  --
  process(clock) is
  begin
    if rising_edge(clock) then
      vga_data_5 <= vga_data_4;
      if color_4 = '0' then
        vga_rgb_5.r <= '0' & border_4 & "100000";
        vga_rgb_5.g <= '0' & cursor_4 & "100000";
        vga_rgb_5.b <= "00100000";
      else
        vga_rgb_5.r <= "11111111";
        vga_rgb_5.g <= "11111111";
        vga_rgb_5.b <= "11111111";
      end if;
    end if;
  end process;
end gets_the_job_done;


----------------------------------------------------------------------------------------------------------------------------------------------------------------
-- LSD.TOS, April 2018 (DO NOT REMOVE THIS LINE), VHDL 2008
--
-- Text buffer (RAM with one synchronous read port and one write port)
--
-- There are 2**N_ROWS_LOG_2 rows and 2**N_COLS_LOG_2 columns, each character has N_BITS bits
--

library IEEE;
use     IEEE.STD_LOGIC_1164.ALL;
use     IEEE.NUMERIC_STD.ALL;

entity text_buffer is
  generic
  (
    N_ROWS_LOG_2 : integer range 1 to 12 := 10;
    N_COLS_LOG_2 : integer range 1 to 12 :=  5;
    N_BITS       : integer range 1 to 16 :=  7
  );
  port
  (
    clock : in  std_logic; -- main clock

    write_row     : in std_logic_vector(N_ROWS_LOG_2-1 downto 0); -- row number for writes
    write_column  : in std_logic_vector(N_COLS_LOG_2-1 downto 0); -- column number for writes
    write_data    : in std_logic_vector(N_BITS-1       downto 0); -- data to write
    write_enable  : in std_logic;                                 -- write enable signal

    read_row_0    : in  std_logic_vector(N_ROWS_LOG_2-1 downto 0); -- row number for reads
    read_column_0 : in  std_logic_vector(N_COLS_LOG_2-1 downto 0); -- column number for reads
    read_data_1   : out std_logic_vector(N_BITS-1       downto 0)  -- data read (available on the next clock cycle)
  );
end text_buffer;

architecture synchronous_read of text_buffer is
  --
  -- dual port RAM memory
  --
  type ram_t is array(0 to 2**(N_ROWS_LOG_2+N_COLS_LOG_2)-1) of std_logic_vector(N_BITS-1 downto 0);
  signal ram : ram_t;
  --
  -- Addresses
  --
  signal write_addr   : std_logic_vector(N_ROWS_LOG_2+N_COLS_LOG_2-1 downto 0);
  signal read_addr_0x : std_logic_vector(N_ROWS_LOG_2+N_COLS_LOG_2-1 downto 0);
begin
  write_addr   <= write_row  & write_column;
  read_addr_0x <= read_row_0 & read_column_0;
  --
  -- Dual port RAM
  --
  process(clock) is
  begin
    if rising_edge(clock) then
      if write_enable = '1' then
        ram(to_integer(unsigned(write_addr))) <= write_data;
      end if;
      read_data_1 <= ram(to_integer(unsigned(read_addr_0x)));
    end if;
  end process;
end synchronous_read;
