----------------------------------------------------------------------------------------------------------------------------------------------------------------
-- LSD.TOS, April 2018 (DO NOT REMOVE THIS LINE), VHDL 2008
--
-- Example of utilization of the infrared decoder to receive events from a remove control unit. This example does the following:
-- * the state of the first three green leds is controlled (toggled) by the keys A, B, and C of the terasIC remote control unit.
-- * the last command received from a IR remote control unit is displayed in the 8 seven-segment displays; it will blink at 1Hz if the command is malformed.
-- * the block effect is produced by a pulse generator.
--
-- More advanced examples can be found in the demonstrations directory.
--
-- The infrared decoder example (under GNU/Linux, create and compile it using the command "make ir_decoder_example") uses the following files:
-- * ir_decoder_example_tl.vhd
-- * ir_decoder.vhd
-- * pulse_generator.vhd
-- * seven_segment_decoder.vhd
--

library IEEE;
use     IEEE.STD_LOGIC_1164.ALL;
use     IEEE.NUMERIC_STD.ALL;

entity ir_decoder_example_tl is
  generic
  (
    CLOCK_50_FREQUENCY : real := 50.0e6 -- frequency of the clock_50 input signal (must be 50MHz)
  );
  port
  (
    clock_50 : in std_logic;

    ledg : out std_logic_vector(2 downto 0); -- these leds are controlled by some remote control keys
    hex0 : out std_logic_vector(6 downto 0);
    hex1 : out std_logic_vector(6 downto 0);
    hex2 : out std_logic_vector(6 downto 0);
    hex3 : out std_logic_vector(6 downto 0);
    hex4 : out std_logic_vector(6 downto 0);
    hex5 : out std_logic_vector(6 downto 0);
    hex6 : out std_logic_vector(6 downto 0);
    hex7 : out std_logic_vector(6 downto 0);

    irda_rxd : in std_logic
  );
end ir_decoder_example_tl;

architecture example of ir_decoder_example_tl is
  --
  -- The master clock (in this case, this is just clock_50)
  --
  constant CLOCK_FREQUENCY : real := CLOCK_50_FREQUENCY;
  signal clock : std_logic;
  --
  -- IR data
  --
  signal ir_data        : std_logic_vector(31 downto 0); -- received command
  signal ir_valid_pulse : std_logic;                     -- received command valid pulse
  signal A_state,B_state,C_state : std_logic := '0';     -- the three bits controlled by some remote control keys
  --
  -- Blink control (to blink the seven-segment displays when the received data is wrong)
  --
  signal blink       : std_logic := '0';
  signal blink_pulse : std_logic;
begin
  --
  -- The master clock (in this case there is no need to change the clock frequency, so our master clock is just clock_50)
  --
  clock <= clock_50;
  --
  -- Infrared decoder
  --
  IR_DECODER : entity work.ir_decoder(nec)
    generic map
    (
      CLOCK_FREQUENCY => CLOCK_FREQUENCY
    )
    port map
    (
      clock => clock,
      reset => '0',  -- no reset
      irda_rxd => irda_rxd,
      data  => ir_data,
      valid => ir_valid_pulse
    );
  --
  -- Deal with events received from the infrared remote control unit
  --
  process(clock,ir_valid_pulse) is
  begin
    if rising_edge(clock) and ir_valid_pulse = '1' then
      if ir_data = X"F0_0F_6B_86" then A_state <= not A_state; end if;
      if ir_data = X"EC_13_6B_86" then B_state <= not B_state; end if;
      if ir_data = X"EF_10_6B_86" then C_state <= not C_state; end if;
    end if;
  end process;
  --
  -- The green leds show the current states
  --
  ledg <= A_state & B_state & C_state;
  --
  -- Display the last command in the seven-segment displays
  --
  H7 : entity work.seven_segment_decoder(hex_digits_and_extra_symbols) port map(code => '0' & ir_data(31 downto 28),enable => blink,seg => HEX7);
  H6 : entity work.seven_segment_decoder(hex_digits_and_extra_symbols) port map(code => '0' & ir_data(27 downto 24),enable => blink,seg => HEX6);
  H5 : entity work.seven_segment_decoder(hex_digits_and_extra_symbols) port map(code => '0' & ir_data(23 downto 20),enable => blink,seg => HEX5);
  H4 : entity work.seven_segment_decoder(hex_digits_and_extra_symbols) port map(code => '0' & ir_data(19 downto 16),enable => blink,seg => HEX4);
  H3 : entity work.seven_segment_decoder(hex_digits_and_extra_symbols) port map(code => '0' & ir_data(15 downto 12),enable => blink,seg => HEX3);
  H2 : entity work.seven_segment_decoder(hex_digits_and_extra_symbols) port map(code => '0' & ir_data(11 downto  8),enable => blink,seg => HEX2);
  H1 : entity work.seven_segment_decoder(hex_digits_and_extra_symbols) port map(code => '0' & ir_data( 7 downto  4),enable => blink,seg => HEX1);
  H0 : entity work.seven_segment_decoder(hex_digits_and_extra_symbols) port map(code => '0' & ir_data( 3 downto  0),enable => blink,seg => HEX0);
  --
  -- Blink control
  --
  PULSE : entity work.pulse_generator(periodic)
    generic map
    (
      CLOCK_FREQUENCY => CLOCK_FREQUENCY,
      PULSE_FREQUENCY => 2.0  -- 2 Hz (the blink signal will be toggled two times per second, i.e., it will have a frequency of 1 Hz)
    )
    port map
    (
      clock => clock,
      reset => '0',
      pulse => blink_pulse
    );
  process(clock) is
  begin
    if rising_edge(clock) then
      if ir_data(31 downto 24) = not ir_data(23 downto 16) then
        blink <= '1';
      elsif blink_pulse = '1' then
        blink <= not blink;
      end if;
    end if;
  end process;
end example;
