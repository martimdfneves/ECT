----------------------------------------------------------------------------------------------------------------------------------------------------------------
-- LSD.TOS, April 2018 (DO NOT REMOVE THIS LINE), VHDL 2008
--
-- Pseudo random number generator and VGA (histogram) example.
-- * When key(0) is pressed, display a new random number, from 1 to 6, in the rightmost seven segment display.
-- * Draw (VGA output) an histogram of the values obtained from a 9-bit pseudo-random number generator.
-- * Designed for the video mode VGA_MODE_800_600_72, but is also works with other video modes.
-- * The color of each histogram bar depends on its height.
-- * The mean is also displayed (cyan horizontal line).
-- * When key(1) is pressed, the histogram is not updated.
-- * On a side window, display an histogram of the histogram!
--
-- The histogram demonstration (under GNU/Linux, create and compile it using the command "make histogram") uses the following files:
-- * demonstrations/histogram_tl.vhd
-- * vhdl_code/vga_config.vhd
-- * vhdl_code/vga.vhd
-- * vhdl_code/clock_generator.vhd
-- * vhdl_code/pseudo_random_generator.vhd
-- * vhdl_code/debouncer.vhd
-- * vhdl_code/seven_segment_decoder.vhd
-- * vhdl_code/screen_capture.vhd
-- * vhdl_code/rs232_controller.vhd
--

library IEEE;
use     IEEE.STD_LOGIC_1164.ALL;
use     IEEE.NUMERIC_STD.ALL;
use     WORK.VGA_CONFIG.ALL;

entity histogram_tl is
  generic
  (
    SCREEN_CAPTURE_SUPPORT : boolean := false -- if true, include code to capture a VGA screen
  );
  port
  (
    clock_50 : in std_logic;

    uart_rxd : in  std_logic;  -- screen capture interface
    uart_txd : out std_logic;  -- screen capture interface

    key  : in std_logic_vector(1 downto 0);

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
end histogram_tl;

architecture gets_the_job_done of histogram_tl is
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
  -- The seven segments display stuff
  --
  signal rnd_3b         : std_logic_vector(2 downto 0);            -- pseudo random number with 3 bits encoded as a std_logic_vector
  signal clean_key      : std_logic;                               -- debounced key(0)
  constant KEY_LIMIT    : integer := integer(0.5*CLOCK_FREQUENCY); -- for the auto-repeat feature
  signal key_counter    : integer range 0 to KEY_LIMIT;            -- for the auto-repeat feature
  signal key0_press     : std_logic;                               -- key(0) press pulse
  signal rnd_3b_request : std_logic := '0';                        -- if active, request a new pseudo random number in the range 1 to 6
  signal display_data   : std_logic_vector(7*3+2 downto 0) := b"000_000_000_000_000_000_000_000";
  --
  -- The histogram stuff
  --
  signal stop           : std_logic;                                      -- if active, do not update the histogram
  signal rnd_9b         : std_logic_vector(8 downto 0);                   -- the output of the pseudo random number generator
  constant N_UPDATES    : integer := 512;                                 -- number of histogram updates per VGA frame
  signal update_counter : integer range 0 to 2*N_UPDATES := 2*N_UPDATES;  -- progress of the update process
  signal update_x       : std_logic;                                      -- control the update of rnd_9b (asynchronous signal)
  constant N_BITS       : integer := 12;                                  -- number of bits of each histogram counter
  subtype word_t is unsigned(N_BITS-1 downto 0);
  type histogram_t is array(0 to 511) of word_t;
  signal histogram      : histogram_t := (others => (others => '0'));     -- the array of counters
  signal height_1       : unsigned(N_BITS-1 downto 0);                    -- raw height of a histogram bar
  signal average        : unsigned(N_BITS+8 downto 0) := (others => '0'); -- average level
  --
  -- The VGA stuff
  --
  constant XM_COORD : integer := 12;             -- x coordinate of the leftmost pixel of the main window
  constant XS_COORD : integer := XM_COORD+512+8; -- x coordinate of the leftmost pixel of the side window
  constant Y_COORD  : integer := 76;             -- y coordinate of the bottom pixel of the two windows
  signal vga_data_0 : vga_data_t;
  signal vga_data_1 : vga_data_t;
  signal vga_data_2 : vga_data_t;
  signal vga_rgb_2  : vga_rgb_t;
  signal border_1   : std_logic;                 -- '1' when border
  signal m_inside_1 : std_logic;                 -- '1' when inside main window
  signal s_inside_1 : std_logic;                 -- '1' when inside side window
  signal outside_1  : std_logic;                 -- '1' when outside 800x600 area
  signal y_1        : unsigned(8 downto 0);      -- y coordinate relative to the bottom of the main window
  signal h_count    : integer range 0 to 511;    -- number of histogram bars with a given height
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
        vga_data => vga_data_2,
        vga_rgb  => vga_rgb_2,
        uart_rxd => uart_rxd,
        uart_txd => uart_txd,
        freeze   => screen_capture_freeze
      );
  else generate
    screen_capture_freeze <= '0';
  end generate;
  --
  -- The seven segments display stuff
  --
D:entity work.debouncer(fancy)
    generic map
    (
      CLOCK_FREQUENCY => CLOCK_FREQUENCY,
      WINDOW_DURATION => 20.0e-6,  -- 20μs
      DELAY_DURATION  => 20.0e-3,  -- 20ms
      INITIAL_LEVEL   => '1'       -- assume the switch is initially in the off position (not really relevant, because of the global reset signal)
    )
    port map
    (
      clock             => clock,
      reset             => '0',
      dirty             => key(0),
      clean             => clean_key,
      zero_to_one_pulse => open,
      one_to_zero_pulse => key0_press
    );
R:entity work.pseudo_random_generator(light)
    generic map
    (
      N_BITS => 3,
      seed   => x"01_23_45_67_89_AB"
    )
    port map
    (
      clock  => clock,
      enable => rnd_3b_request, -- using '1' here is better!
      rnd    => rnd_3b
    );
D0: entity work.seven_segment_decoder(hex_digits_and_extra_symbols) port map(code => "00" & display_data(0*3+2 downto 0*3+0),enable => '1',seg => hex0);
D1: entity work.seven_segment_decoder(hex_digits_and_extra_symbols) port map(code => "00" & display_data(1*3+2 downto 1*3+0),enable => '1',seg => hex1);
D2: entity work.seven_segment_decoder(hex_digits_and_extra_symbols) port map(code => "00" & display_data(2*3+2 downto 2*3+0),enable => '1',seg => hex2);
D3: entity work.seven_segment_decoder(hex_digits_and_extra_symbols) port map(code => "00" & display_data(3*3+2 downto 3*3+0),enable => '1',seg => hex3);
D4: entity work.seven_segment_decoder(hex_digits_and_extra_symbols) port map(code => "00" & display_data(4*3+2 downto 4*3+0),enable => '1',seg => hex4);
D5: entity work.seven_segment_decoder(hex_digits_and_extra_symbols) port map(code => "00" & display_data(5*3+2 downto 5*3+0),enable => '1',seg => hex5);
D6: entity work.seven_segment_decoder(hex_digits_and_extra_symbols) port map(code => "00" & display_data(6*3+2 downto 6*3+0),enable => '1',seg => hex6);
D7: entity work.seven_segment_decoder(hex_digits_and_extra_symbols) port map(code => "00" & display_data(7*3+2 downto 7*3+0),enable => '1',seg => hex7);
  process(clock) is
  begin
    if rising_edge(clock) then
      -- key(0) stuff
      if key0_press = '1' then
        -- key(0) pressed -> request a new pseudo random number
        rnd_3b_request <= '1';
        key_counter <= KEY_LIMIT;
      elsif clean_key = '0' then
        -- key(0) still pressed -> every half second request a new pseudo random number
        if key_counter /= 0 then
          key_counter <= key_counter-1;
        else
          rnd_3b_request <= '1';
          key_counter <= KEY_LIMIT;
        end if;
      end if;
      -- deal with a request for a new pseudo-random number (do not accept neither a 0 nor a 7)
      if rnd_3b_request = '1' and rnd_3b /= "000" and rnd_3b /= "111" then
        rnd_3b_request <= '0';
        display_data <= display_data(6*3+2 downto 0) & rnd_3b; -- shift in the new pseudo random number
      end if;
    end if;
  end process;
  --
  -- The histogram stuff (includes fetching the histogram bar height for the VGA image)
  --
S:entity work.pseudo_random_generator(heavy)
    generic map
    (
      N_BITS => 9,
      seed => x"45_67_89_AB_CD_EF"
    )
    port map
    (
      clock => clock,
      enable => update_x,
      rnd => rnd_9b
    );
  update_x <= '1' when update_counter mod 2 = 1 else '0'; -- asynchronous so that its effect is felt on the next clock cycle
  process(clock) is
  begin
    if rising_edge(clock) then
      stop <= not key(1) or screen_capture_freeze; -- no need to debounce key(1)
      if update_counter < 2*N_UPDATES and update_counter mod 2 = 0 then
        height_1 <= histogram(to_integer(unsigned(rnd_9b))); -- read the histogram counter
      else
        height_1 <= histogram(vga_data_0.x-16);              -- read histogram data for the VGA display
      end if;
      if vga_data_0.end_of_frame = '1' and stop = '0' then
        update_counter <= 0; -- start the histogram updates
      elsif update_counter < 2*N_UPDATES then
        update_counter <= update_counter+1;
        if update_counter mod 2 = 1 then
          histogram(to_integer(unsigned(rnd_9b))) <= height_1+1; -- update the histogram counter
          average <= average+1;
        end if;
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
      vga_data    => vga_data_2,
      vga_rgb     => vga_rgb_2,
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
  -- VGA image generation pipeline stage 0 -> 1
  --
  process(clock) is
  begin
    if rising_edge(clock) then
      vga_data_1 <= vga_data_0;
      if (vga_data_0.x = 0 and vga_data_0.y < 600) or (vga_data_0.x = 799 and vga_data_0.y < 600) or
         (vga_data_0.x < 800 and vga_data_0.y = 0) or (vga_data_0.x < 800 and vga_data_0.y = 599) or
         ((vga_data_0.x = XM_COORD-1 or vga_data_0.x = XM_COORD+512) and vga_data_0.y >= Y_COORD-1 and vga_data_0.y <= Y_COORD+512) or
         (vga_data_0.x >= XM_COORD-1 and vga_data_0.x <= XM_COORD+512 and (vga_data_0.y = Y_COORD-1 or vga_data_0.y = Y_COORD+512)) or
         ((vga_data_0.x = XS_COORD-1 or vga_data_0.x = XS_COORD+256) and vga_data_0.y >= Y_COORD-1 and vga_data_0.y <= Y_COORD+512) or
         (vga_data_0.x >= XS_COORD-1 and vga_data_0.x <= XS_COORD+256 and (vga_data_0.y = Y_COORD-1 or vga_data_0.y = Y_COORD+512)) then
        border_1 <= '1';
      else
        border_1 <= '0';
      end if;
      if vga_data_0.x >= XM_COORD and vga_data_0.x < XM_COORD+512 and vga_data_0.y >= Y_COORD and vga_data_0.y < Y_COORD+512 then
        m_inside_1 <= '1';
      else
        m_inside_1 <= '0';
      end if;
      if vga_data_0.x >= XS_COORD and vga_data_0.x < XS_COORD+256 and vga_data_0.y >= Y_COORD and vga_data_0.y < Y_COORD+512 then
        s_inside_1 <= '1';
      else
        s_inside_1 <= '0';
      end if;
      if vga_data_0.x >= 800 or vga_data_0.y >= 600 then
        outside_1 <= '1';
      else
        outside_1 <= '0';
      end if;
      y_1 <= to_unsigned(Y_COORD+511-vga_data_0.y,9);
    end if;
  end process;
  --
  -- VGA image generation pipeline stage 1 -> 2
  --
  process(clock) is
  begin
    if rising_edge(clock) then
      vga_data_2 <= vga_data_1;
      if border_1 = '1' then
        -- border in white
        vga_rgb_2.r <= "11111111";
        vga_rgb_2.g <= "11111111";
        vga_rgb_2.b <= "11111111";
      elsif m_inside_1 = '1' then
        if (vga_data_1.x/4) mod 2 = (vga_data_1.y/4) mod 2 then
          -- chess board pattern on in dark green
          vga_rgb_2.r <= "00000000";
          vga_rgb_2.g <= "01000000";
          vga_rgb_2.b <= "00000000";
        else
          -- chess board pattern off in black
          vga_rgb_2.r <= "00000000";
          vga_rgb_2.g <= "00000000";
          vga_rgb_2.b <= "00000000";
        end if;
        if height_1(N_BITS-1 downto N_BITS-9) >= y_1 then
          -- vertical histogram bar in red (just for fun, the red intensity depends on the bar height)
          vga_rgb_2.r <= '1' & std_logic_vector(height_1(N_BITS-1 downto N_BITS-7));
          vga_rgb_2.g <= "00000000";
          vga_rgb_2.b <= "00000000";
        elsif average(N_BITS+8 downto N_BITS) = y_1 then
          -- average bar in cyan
          vga_rgb_2.r <= "00000000";
          vga_rgb_2.g <= "11111111";
          vga_rgb_2.b <= "11111111";
        end if;
      elsif s_inside_1 = '1' then
        if (vga_data_1.x/4) mod 2 = (vga_data_1.y/4) mod 2 then
          -- chess board pattern on in dark green
          vga_rgb_2.r <= "00000000";
          vga_rgb_2.g <= "01000000";
          vga_rgb_2.b <= "00000000";
        else
          -- chess board pattern off in black
          vga_rgb_2.r <= "00000000";
          vga_rgb_2.g <= "00000000";
          vga_rgb_2.b <= "00000000";
        end if;
        if vga_data_1.x-XS_COORD <= 8*h_count then
          -- horizontal histogram bar in orange
          vga_rgb_2.r <= "11111111";
          vga_rgb_2.g <= "10000000";
          vga_rgb_2.b <= "00000000";
        elsif average(N_BITS+8 downto N_BITS) = y_1 then
          -- average bar in cyan
          vga_rgb_2.r <= "00000000";
          vga_rgb_2.g <= "11111111";
          vga_rgb_2.b <= "11111111";
        end if;
      else
        -- background in either black or gray
        vga_rgb_2.r <= "000" & outside_1 & "0000";
        vga_rgb_2.g <= "000" & outside_1 & "0000";
        vga_rgb_2.b <= "000" & outside_1 & "0000";
      end if;
      -- update h_count
      if vga_data_0.x = 0 then
        h_count <= 0;
      elsif m_inside_1 = '1' and height_1(N_BITS-1 downto N_BITS-9) = y_1 then
        h_count <= h_count+1;
      end if;
    end if;
  end process;
end gets_the_job_done;
