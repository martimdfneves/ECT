----------------------------------------------------------------------------------------------------------------------------------------------------------------
-- LSD.TOS, April 2018 (DO NOT REMOVE THIS LINE), VHDL 2008
--
-- Example of utilization of the pseudo random generator. This example does the following:
-- * uses two pseudo random generators to generate two continuous streams of pseudo-random bit vectors, one with 18 bits, the other with 8 bits
-- * uses the pulse_generator entity, fed by the 50MHz clock, to generate a pulse with frequency 10Hz (i.e., with period 0.1s)
-- * the contents of the pseudo-random bit vectors are transfered to the red and green leds ten times per second
--
-- More advanced examples can be found in the demonstrations directory.
--
-- The pseudo random number generator example (under GNU/Linux, create and compile it using the command "make pseudo_random_generator_example") uses the
-- following files:
-- * vhdl_code/pseudo_random_generator_example_tl.vhd
-- * vhdl_code/pseudo_random_generator.vhd
-- * vhdl_code/pulse_generator.vhd
--

library IEEE;
use     IEEE.STD_LOGIC_1164.ALL;
use     IEEE.NUMERIC_STD.ALL;

entity pseudo_random_generator_example_tl is
  generic
  (
    CLOCK_50_FREQUENCY : real := 50.0e6 -- frequency of the clock_50 input signal (must be 50MHz)
  );
  port
  (
    clock_50 : in std_logic;

    ledr : out std_logic_vector(17 downto 0);
    ledg : out std_logic_vector( 7 downto 0)
  );
end pseudo_random_generator_example_tl;

architecture example of pseudo_random_generator_example_tl is
  --
  -- The master clock (in this case, this is just clock_50)
  --
  constant CLOCK_FREQUENCY : real := CLOCK_50_FREQUENCY;
  signal clock : std_logic;
  --
  -- The pulse generator
  --
  constant PULSE_PERIOD : real := 0.1; -- 0.1s (i.e., 10Hz)
  signal pulse : std_logic;
  --
  -- The pseudo random numbers
  --
  signal rnd_18 : std_logic_vector(17 downto 0);
  signal rnd_08 : std_logic_vector( 7 downto 0);
begin
  --
  -- The master clock (in this case there is no need to change the clock frequency, so our master clock is just clock_50)
  --
  clock <= clock_50;
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
  -- The pseudo-random generators (we leave then running all the time, and update the leds only when a pulse arrives)
  --
  -- An alternative that would consume less energy, would be to use the pulse signal as the enable signal to the two generators and to do
  --   ledr <= rnd_18;
  --   ledg <= rnd_08;
  -- instead of sampling rnd_18 and rnd_08 when a pulse arrives, as done below
  --
  GEN_RND_18 : entity work.pseudo_random_generator(heavy)
    generic map
    (
      N_BITS => 18,
      SEED   => X"1234_5678_9ABC"
    )
    port map
    (
      clock  => clock,
      enable => '1',
      rnd    => rnd_18
    );
  GEN_RND_08 : entity work.pseudo_random_generator(light)
    generic map
    (
      N_BITS => 8,
      SEED   => X"2345_6789_ABCD"
    )
    port map
    (
      clock  => clock,
      enable => '1',
      rnd    => rnd_08
    );
  --
  -- The leds
  --
  process(clock,pulse) is
  begin
    if rising_edge(clock) and pulse = '1' then
      ledr <= rnd_18;
      ledg <= rnd_08;
    end if;
  end process;
end example;
