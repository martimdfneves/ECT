----------------------------------------------------------------------------------------------------------------------------------------------------------------
-- LSD.TOS, April 2018 (DO NOT REMOVE THIS LINE), VHDL 2008
--
-- Example of utilization of the clock generator. This example does the following:
-- * creates a 250MHz clock (warning: most projects do not work at such high frequencies)
--     to see the maximum frequency that can be used in a project open the "TimeQuest Timing Analyzer/Slow 1200mv 85C Model/Fmax summary"
--     section of the compilation report
-- * uses the pulse generator entity, fed by the 250MHz clock, to generate a pulse with frequency 10Hz (i.e., with period 0.1s)
-- * the pulse is used to increment a two-digit decimal counter
-- * the counter output is displayed in two seven-segment displays (using the seven_segment_decoder entity)
-- * the state of sw(4 downto 0) is displayed in another seven-segment display (this can be used to test all patterns of the seven_segment_decoder entity)
--
-- More advanced examples can be found in the demonstrations directory.
--
-- The clock generator example (under GNU/Linux, create and compile it using the command "make clock_generator_example") uses the following files:
-- * vhdl_code/clock_generator_example_tl.vhd
-- * vhdl_code/clock_generator.vhd
-- * vhdl_code/pulse_generator.vhd
-- * vhdl_code/seven_segment_decoder.vhd
--

library IEEE;
use     IEEE.STD_LOGIC_1164.ALL;
use     IEEE.NUMERIC_STD.ALL;

entity clock_generator_example_tl is
  port
  (
    clock_50 : in std_logic;

    sw  : in std_logic_vector(4 downto 0);

    hex4 : out std_logic_vector(6 downto 0); -- to display sw(4 downto 0)
    hex1 : out std_logic_vector(6 downto 0); -- to display the most significant digit of the decimal counter
    hex0 : out std_logic_vector(6 downto 0)  -- to display the least significant digit of the decimal counter
  );
end clock_generator_example_tl;

architecture example of clock_generator_example_tl is
  --
  -- The master clock
  --
  constant CLOCK_FREQUENCY : real := 250.0e6;
  signal clock : std_logic;
  --
  -- The pulse generator
  --
  constant PULSE_PERIOD : real := 0.1; -- 0.1s (i.e., 10Hz)
  signal pulse : std_logic;
  --
  -- The decimal counter
  --
  signal bcd_counter : unsigned(7 downto 0) := X"00"; -- the two digits (encoded in hexadecimal, so that they can be easily displayed)
begin
  --
  -- The master clock
  --
  NEW_CLOCK : entity work.clock_generator(cyclone4e)
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
  -- The pulse generator
  --
  NEW_PULSE : entity work.pulse_generator(periodic)
    generic map
    (
      CLOCK_FREQUENCY => CLOCK_FREQUENCY,
      PULSE_FREQUENCY => 1.0/PULSE_PERIOD
    )
    port map
    (
      clock => clock,
      reset => '0', -- no need for a reset
      pulse => pulse
    );
  --
  -- The decimal counter
  --
  process(clock,pulse) is
  begin
    if rising_edge(clock) and pulse = '1' then
      if bcd_counter = X"99" then
        bcd_counter <= X"00"; -- 99 becomes 00
      elsif bcd_counter(3 downto 0) = X"9" then
        bcd_counter <= bcd_counter+7; -- if the least significant digit is 9 then add 7 (7=16-9, for example X"09" becomes X"10")
      else
        bcd_counter <= bcd_counter+1; -- otherwise, add 1
      end if;
    end if;
  end process;
  --
  -- The seven-segment drivers
  --
  LSB_DIGIT : entity work.seven_segment_decoder(hex_digits_and_extra_symbols)
    port map
    (
      code   => '0' & std_logic_vector(bcd_counter(3 downto 0)),
      enable => '1', -- always on
      seg    => HEX0
    );
  MSB_DIGIT : entity work.seven_segment_decoder(hex_digits_and_extra_symbols)
    port map
    (
      code   => '0' & std_logic_vector(bcd_counter(7 downto 4)),
      enable => '1', -- always on
      seg    => HEX1
    );
  TEST : entity work.seven_segment_decoder(hex_digits_and_extra_symbols)
    port map
    (
      code   => sw(4 downto 0),
      enable => '1', -- always on
      seg    => HEX4
    );
end example;
