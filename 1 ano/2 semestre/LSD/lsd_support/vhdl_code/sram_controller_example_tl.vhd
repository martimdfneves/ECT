----------------------------------------------------------------------------------------------------------------------------------------------------------------
-- LSD.TOS, April 2018 (DO NOT REMOVE THIS LINE), VHDL 2008
--
-- Example of utilization of the static ram controller and of the audio entities (ADC and DAC parts). This example does the following:
-- * sends the line-in sound to line-out with a delay of 4 seconds (the sound data is stored in the static RAM memory located outside of the FPGA)
-- This examples uses a master clock of 75 MHz just to check if all entities work well at a frequency different from 50 MHz.
--
-- More advanced examples can be found in the demonstrations directory.
--
-- The sram memory controller example (under GNU/Linux, create and compile it using the command "make sram_controller_example") uses the following files:
-- * vhdl_code/sram_controller_example_tl.vhd
-- * vhdl_code/sram_controller.vhd
-- * vhdl_code/audio.vhd
-- * vhdl_code/clock_generator.vhd
--

library IEEE;
use     IEEE.STD_LOGIC_1164.ALL;
use     IEEE.NUMERIC_STD.ALL;

entity sram_controller_example_tl is
  port
  (
    clock_50    : in    std_logic;

    sram_addr : out   std_logic_vector(19 downto 0);
    sram_dq   : inout std_logic_vector(15 downto 0);
    sram_ce_n : out   std_logic;
    sram_oe_n : out   std_logic;
    sram_we_n : out   std_logic;
    sram_ub_n : out   std_logic;
    sram_lb_n : out   std_logic;

    i2c_sclk    : inout std_logic;
    i2c_sdat    : inout std_logic;
    aud_xck     : out   std_logic;
    aud_bclk    : in    std_logic;
    aud_adclrck : in    std_logic;
    aud_adcdat  : in    std_logic;
    aud_dacdat  : out   std_logic
  );
end sram_controller_example_tl;

architecture example of sram_controller_example_tl is
  --
  -- The master clock (in this case 75MHz, to test if the sram_controller entity works well at an anbitrary frequency)
  --
  constant CLOCK_FREQUENCY : real := 75.0e6; -- we try here a different frequency (just to check if the audio entities work well)
  signal clock : std_logic;
  --
  -- Audio loop
  --
  signal from_left   : std_logic_vector(15 downto 0);
  signal from_right  : std_logic_vector(15 downto 0);
  signal audio_pulse : std_logic;
  signal to_left     : std_logic_vector(15 downto 0);
  signal to_right    : std_logic_vector(15 downto 0);
  type state_t is
  (
    IDLE,
    STORE_LEFT,  -- store the left channel data
    STORE_RIGHT, -- store the right channel data
    LOAD_LEFT,   -- load the left channel data (delayed 4 seconds)
    LOAD_RIGHT   -- load the right channel data (delayed 4 seconds)
  );
  attribute syn_encoding : string;                    -- make sure the state machine recovers
  attribute syn_encoding of state_t : type is "safe"; --   from an illegal state
  signal state : state_t := IDLE;
  signal write_address  : integer range 0 to 2**20-1 := 4*2*48000; -- 4 seconds ahead
  signal write_data     : std_logic_vector(15 downto 0);
  signal write_request  : std_logic;
  signal write_accepted : std_logic;
  signal read_address   : integer range 0 to 2**20-1 := 0;
  signal read_data      : std_logic_vector(15 downto 0);
  signal read_request   : std_logic;
  signal read_valid     : std_logic;
begin
  MASTER_CLOCK_GENERATOR : entity work.clock_generator(cyclone4e)
    generic map
    (
      NEW_CLOCK_FREQUENCY => CLOCK_FREQUENCY
    )
    port map
    (
      clock_50  => clock_50,
      new_clock => clock
    );
  --
  -- Audio entities (with fixed volume and input and output gains)
  --
  AUDIO_C : entity work.audio_controller(smart)
    generic map
    (
      CLOCK_FREQUENCY => CLOCK_FREQUENCY
    )
    port map
    (
      clock => clock,
      reset => '0',
      i2c_sclk => i2c_sclk,
      i2c_sdat => i2c_sdat,
      use_line_in => '1',
      line_in_gain => "11100",
      use_mic => '0',
      mic_boost => '0',
      line_bypass => '0',
      mic_bypass => '0',
      volume => "1101000" -- a relatively low volume
    );
  AUDIO_IO : entity work.audio_io(audio_data_transfer)
    generic map
    (
      CLOCK_FREQUENCY => CLOCK_FREQUENCY
    )
    port map
    (
      clock    => clock,
      clock_50 => clock_50,
      aud_xck     => aud_xck,
      aud_bclk    => aud_bclk,
      aud_adclrck => aud_adclrck,
      aud_adcdat  => aud_adcdat,
      aud_dacdat  => aud_dacdat,
      from_left  => from_left,
      from_right => from_right,
      valid      => audio_pulse,
      to_left    => to_left,
      to_right   => to_right
    );
  --
  -- Audio loop (line-out = line-in delayed 4 seconds)
  --
  SRAM : entity work.sram_controller(slow_but_safe)
    generic map
    (
      CLOCK_FREQUENCY => CLOCK_FREQUENCY
    )
    port map
    (
      clock => clock,
      sram_addr => sram_addr,
      sram_dq   => sram_dq,
      sram_ce_n => sram_ce_n,
      sram_oe_n => sram_oe_n,
      sram_we_n => sram_we_n,
      sram_ub_n => sram_ub_n,
      sram_lb_n => sram_lb_n,
      write_addr     => std_logic_vector(to_unsigned(write_address,20)),
      write_data     => write_data,
      write_request  => write_request,
      write_accepted => write_accepted,
      read_addr      => std_logic_vector(to_unsigned(read_address,20)),
      read_data      => read_data,
      read_request   => read_request,
      read_valid     => read_valid
    );
  process(clock) is
  begin
    if rising_edge(clock) then
      if audio_pulse = '1' then
        state <= STORE_LEFT;
      end if;
      write_request <= '0';
      read_request <= '0';
      case state is
        when IDLE =>
          null;  -- do nothing
        when STORE_LEFT =>
          write_data <= from_left;
          write_request <= '1';
          if write_accepted = '1' then
            state <= STORE_RIGHT;
            write_address <= write_address+1;
          end if;
        when STORE_RIGHT =>
          write_data <= from_right;
          write_request <= '1';
          if write_accepted = '1' then
            state <= LOAD_LEFT;
            write_address <= write_address+1;
          end if;
        when LOAD_LEFT =>
          read_request <= '1';
          if read_valid = '1' then
            state <= LOAD_RIGHT;
            to_left <= read_data;
            read_address <= read_address+1;
          end if;
        when LOAD_RIGHT =>
          read_request <= '1';
          if read_valid = '1' then
            state <= IDLE;
            to_right <= read_data;
            read_address <= read_address+1;
          end if;
      end case;
    end if;
  end process;
end example;
