----------------------------------------------------------------------------------------------------------------------------------------------------------------
-- LSD.TOS, April 2018 (DO NOT REMOVE THIS LINE), VHDL 2008
--
-- Generic pulse generator.
--
-- Pulses last one clock cycle.
-- After a reset, the first pulse occurs at the end of a pulse period.
--
-- A simple example of the use of the pulse_generator entity can be found in the file clock_generator_example_tl.vhd
--

library IEEE;
use     IEEE.STD_LOGIC_1164.ALL;
use     IEEE.NUMERIC_STD.ALL;

entity pulse_generator is
  generic
  (
    CLOCK_FREQUENCY : real range 1.0e6 to 250.0e6; -- (in Hz) frequency of the clock signal
    PULSE_FREQUENCY : real range 0.01  to 250.0e6  -- (in Hz) frequency of the pulse signal
  );
  port
  (
    clock : in  std_logic;        -- main clock
    reset : in  std_logic := '0'; -- reset
    pulse : out std_logic         -- pulse signal (will be set to '1' at the pulse frequency)
  );
end pulse_generator;

architecture periodic of pulse_generator is
  --
  -- Number of clock cycles in one period of the pulse signal (rounded to the nearest integer)
  --
  constant PERIOD : integer := integer(CLOCK_FREQUENCY/PULSE_FREQUENCY);
  --
  -- Pulse generator state
  --
  signal phase : integer range 0 to PERIOD-1 := 0;
begin
  --
  -- make sure the generic parameters make sense
  --
  assert (PERIOD > 0) report "Bad pulse frequency" severity failure;
  assert false report "Pulse generator period = " & integer'image(PERIOD) & " clock cycles" severity note;
  --
  -- Generate the pulse signal
  --
  process(clock) is
  begin
    if rising_edge(clock) then
      if reset = '1' then
        phase <= 0;
        pulse <= '0';
      elsif phase = PERIOD-1 then
        phase <= 0;
        pulse <= '1';
      else
        phase <= phase+1;
        pulse <= '0';
      end if;
    end if;
  end process;
end periodic;
