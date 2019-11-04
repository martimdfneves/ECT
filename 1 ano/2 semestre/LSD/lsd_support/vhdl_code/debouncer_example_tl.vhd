----------------------------------------------------------------------------------------------------------------------------------------------------------------
-- LSD.TOS, April 2018 (DO NOT REMOVE THIS LINE), VHDL 2008
--
-- Example of utilization of the debouncer. This example does the following:
-- * pressing key(0) will reset the value of an 8-bit counter
-- * sliding sw(0) to the on position will increment the counter
-- * sliding sw(1) to the on position will decrement the counter
-- * whenever there is a key or switch event ledg(8) will be turned on during 0.1s
-- * the current counter value is displayed in ledg(7 downto 0)
-- There exists a global reset signal that is turned on for a brief period of time after the FPGA is programmed (or powered on)
--
-- More advanced examples can be found in the demonstrations directory.
--
-- The debouncer example (under GNU/Linux, create and compile it using the command "make debouncer_example") uses the following files:
-- * vhdl_code/debouncer_example_tl.vhd
-- * vhdl_code/debouncer.vhd
--

library IEEE;
use     IEEE.STD_LOGIC_1164.ALL;
use     IEEE.NUMERIC_STD.ALL;

entity debouncer_example_tl is
  generic
  (
    CLOCK_50_FREQUENCY : real := 50.0e6 -- frequency of the clock_50 input signal (must be 50MHz)
  );
  port
  (
    clock_50 : in std_logic;

    key : in std_logic_vector(0 downto 0);  -- for the counter reset signal
    sw  : in std_logic_vector(1 downto 0);  -- for the count up and count down events

    ledg : out std_logic_vector(8 downto 0) -- to display the counter value, in ledg(7 downto 0), and to indicate activity, in ledg(8)
  );
end debouncer_example_tl;

architecture example of debouncer_example_tl is
  --
  -- The master clock (in this case, this is just clock_50)
  --
  constant CLOCK_FREQUENCY : real := CLOCK_50_FREQUENCY;
  signal clock : std_logic;
  --
  -- Global reset (on a ASIC the global reset requires an outside signal, but in a FPGA we can do it in this way as signals have a well defined initial value)
  --
  signal reset_counter : integer range 0 to 15 := 15; -- number of clock cycles the global reset will be active after power on or programming of the FPGA
  signal reset : std_logic;                           -- the (almost) synchronous reset signal
  --
  -- The up-down counter
  --
  signal reset_pulse,up_pulse,down_pulse : std_logic; -- debounced control signals
  signal counter : unsigned(7 downto 0);              -- the counter
  --
  -- The activity timer
  --
  constant TIMER_LIMIT : integer := integer(0.1*CLOCK_FREQUENCY);
  signal timer_counter : integer range 0 to TIMER_LIMIT := 0;
begin
  --
  -- The master clock (in this case there is no need to change the clock frequency, so our master clock is just clock_50)
  --
  clock <= clock_50;
  --
  -- The global reset signal
  --
  reset <= '1' when reset_counter /= 0 else '0';
  process(clock) is
  begin
    if rising_edge(clock) then
      if reset_counter /= 0 then
        reset_counter <= reset_counter-1;
      end if;
    end if;
  end process;
  --
  -- Debouncers (unused output signals are left open, so that quartus can optimize the design by removing logic that is not being used)
  --
  UP_EVENT_DEBOUNCER : entity work.debouncer(fancy)
    generic map
    (
      CLOCK_FREQUENCY => CLOCK_FREQUENCY,
      WINDOW_DURATION => 20.0e-6,  -- 20μs
      DELAY_DURATION  => 20.0e-3,  -- 20ms
      INITIAL_LEVEL   => '0'       -- assume the switch is initially in the off position (not really relevant, because of the global reset signal)
    )
    port map
    (
      clock             => clock,
      reset             => reset,
      dirty             => sw(0),  -- sw(0) generates the count up event
      clean             => open,   -- not used, so leave it open
      zero_to_one_pulse => up_pulse,
      one_to_zero_pulse => open    -- not used, so leave it open
    );
  DOWN_EVENT_DEBOUNCER : entity work.debouncer(fancy)
    generic map
    (
      CLOCK_FREQUENCY => CLOCK_FREQUENCY,
      WINDOW_DURATION => 20.0e-6,  -- 20μs
      DELAY_DURATION  => 20.0e-3,  -- 20ms
      INITIAL_LEVEL   => '0'       -- assume the switch is initially in the off position (not really relevant, because of the global reset signal)
    )
    port map
    (
      clock             => clock,
      reset             => reset,
      dirty             => sw(1),  -- sw(1) generates the count down event
      clean             => open,   -- not used, so leave it open
      zero_to_one_pulse => down_pulse,
      one_to_zero_pulse => open    -- not used, so leave it open
    );
  RESET_EVENT_DEBOUNCER : entity work.debouncer(basic) -- we use the basic architecture, just to try it out
    generic map
    (
      CLOCK_FREQUENCY => CLOCK_FREQUENCY,
      WINDOW_DURATION => 20.0e-6,  -- 20μs
      INITIAL_LEVEL   => '1'       -- assume the key is initially not pressed (not really relevant, because of the global reset signal)
    )
    port map
    (
      clock             => clock,
      reset             => reset,
      dirty             => key(0), -- key(0) generates the counter reset event
      clean             => open,   -- not used, so leave it open
      zero_to_one_pulse => open,   -- not used, so leave it open
      one_to_zero_pulse => reset_pulse
    );
  --
  -- Counter stuff
  --
  process(clock) is
  begin
    if rising_edge(clock) then
      if reset_pulse = '1' then
       counter <= (others => '0');
      elsif up_pulse = '1' and down_pulse = '0' then
        counter <= counter+1;
      elsif down_pulse = '1' and up_pulse = '0' then
        counter <= counter-1;
      end if;
    end if;
  end process;
  --
  -- The green leds stuff
  --
  ledg(7 downto 0) <= std_logic_vector(counter);
  ledg(8) <= '1' when timer_counter /= 0 else '0';
  process(clock) is
  begin
    if rising_edge(clock) then
      if reset_pulse = '1' or up_pulse = '1' or down_pulse = '1' then
        timer_counter <= TIMER_LIMIT;       -- reset the timer
      elsif timer_counter /= 0 then
        timer_counter <= timer_counter-1;   -- count down
      end if;
    end if;
  end process;
end example;
