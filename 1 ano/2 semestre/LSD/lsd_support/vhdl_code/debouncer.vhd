----------------------------------------------------------------------------------------------------------------------------------------------------------------
-- LSD.TOS, April 2018 (DO NOT REMOVE THIS LINE), VHDL 2008
--
-- Debouncer with extra pulsed level change detectors (when instantiating this entity the output signals that are not needed can be left open).
--
-- This entity has two architectures:
--   fancy ... responds quickly to a change of the input signal, and then ignores changes of this signal for a given time interval
--             use a small value for WINDOW_DURATION (20.0e-6 seconds is a good value)
--             use a large value for DELAY_DURATION (20.0e-3 seconds is a good value)
--   basic ... responds only when a change is stable for a given time interval (it responds with a delay of WINDOW_DURATION seconds)
--             use a large value for WINDOW_DURATION (20.0e-3 seconds is a good value)
--             the value of DELAY_DURATION is ignored
-- The default values for the generics WINDOW_DURATION and DELAY_DURATION are for the fancy architecture, which is the one we recommended
--
-- Simple examples of the use of the debouncer entity can be found in the file debouncer_example_tl.vhd
--
-- To consider: add a autorepeat feature to the pulse output signals.
--

library IEEE;
use     IEEE.STD_LOGIC_1164.ALL;
use     IEEE.NUMERIC_STD.ALL;

entity debouncer is
  generic
  (
    CLOCK_FREQUENCY : real range 1.0e6 to 250.0e6;           -- (in Hz) frequency of the clock signal
    WINDOW_DURATION : real range 0.0 to 100.0e-3 := 20.0e-6; -- (in seconds) the input signal has to be stable during this time for the output signal to change
    DELAY_DURATION  : real range 0.0 to 100.0e-3 := 20.0e-3; -- (in seconds) the input signal is ignored for this time after an output signal change
    INITIAL_LEVEL   : std_logic                  := '0'      -- the initial state of the debouncer (use '0' for switches and '1' for keys)
  );
  port
  (
    clock : in std_logic;        -- main clock
    reset : in std_logic;        -- reset signal

    dirty             : in  std_logic;        -- signal to be debounced
    clean             : out std_logic;        -- debounced signal (changes will be detected with a delay of WINDOW_DURATION seconds)
    zero_to_one_pulse : out std_logic := '0'; -- '1' only when there exists a '0' to '1' transition in the clean signal
    one_to_zero_pulse : out std_logic := '0'  -- '1' only when there exists a '1' to '0' transition in the clean signal
  );
end debouncer;

architecture fancy of debouncer is
  --
  -- The maximum() VHDL 2008 function is not recognized by quartus; we provide here a simple replacement
  --
  pure function MAX(x,y : integer) return integer is -- used only for "compile time" evaluation of constants
  begin
    if x > y then
      return x;
    else
      return y;
    end if;
  end max;
  --
  -- Sampled input signal (must be done if the input signal comes directly from a FPGA pin)
  --
  signal sampled_dirty : std_logic := INITIAL_LEVEL;
  --
  -- Debouncer state
  --
  type state_t is
  (
    IDLE,          -- waiting for a transition of the dirty input signal
    INSIDE_WINDOW, -- inside the acceptance window (the signal has to remain stable inside this window for the change to be accepted)
    INSIDE_DELAY   -- inside the delay window (input signal ignored)
  );
  attribute syn_encoding : string;                    -- make sure the state machine recovers
  attribute syn_encoding of state_t : type is "safe"; --   from an illegal state
  signal state : state_t := IDLE;                     -- the state of the debouncer
  signal level : std_logic := INITIAL_LEVEL;          -- always equal to the clean output signal
  --
  -- Timer (to control the amount of time spent in each state of the state machine)
  --
  constant WINDOW_LIMIT : integer := integer(CLOCK_FREQUENCY*WINDOW_DURATION);
  constant DELAY_LIMIT  : integer := integer(CLOCK_FREQUENCY*DELAY_DURATION);
  constant TIMER_LIMIT  : integer := MAX(WINDOW_LIMIT,DELAY_LIMIT); -- should be maximum() instead of MAX(), but quartus does not recognize it!
  signal timer_counter  : integer range 0 to TIMER_LIMIT := 0;
begin
  assert (WINDOW_LIMIT > 0) report "Bad WINDOW_DURATION generic value" severity failure;
  --
  -- Sample the dirty input signal (pass it through a flip-flop, so that our logic will work with a synchronous signal)
  --
  process(clock) is
  begin
    if rising_edge(clock) then
      sampled_dirty <= dirty;
    end if;
  end process;
  --
  -- the clean output signal is the current level of the debouncer
  --
  clean <= level;
  --
  -- State machine
  --
  process(clock) is
  begin
    if rising_edge(clock) then
      zero_to_one_pulse <= '0'; -- default value
      one_to_zero_pulse <= '0'; -- default value
      if reset = '1' then
        state <= IDLE;
        level <= sampled_dirty;
      else
        case state is
          when IDLE =>
            if sampled_dirty /= level then
              state <= INSIDE_WINDOW;           -- change detected
              timer_counter <= WINDOW_LIMIT;    -- let's see if it is stable for WINDOW_DURATION seconds
            end if;
          when INSIDE_WINDOW =>
            if sampled_dirty = level then
              state <= IDLE;                    -- the change was not stable for long enough
            elsif timer_counter /= 0 then
              timer_counter <= timer_counter-1; -- still inside the acceptance period
            else
              level <= sampled_dirty;           -- accept the new level
              if sampled_dirty = '1' then
                zero_to_one_pulse <= '1';       -- produce the '0' to '1' pulse
              else
                one_to_zero_pulse <= '1';       -- produce the '1' to '0' pulse
              end if;
              state <= INSIDE_DELAY;
              timer_counter <= DELAY_LIMIT;
            end if;
          when INSIDE_DELAY =>
            if timer_counter /= 0 then
              timer_counter <= timer_counter-1; -- still inside the delay period
            else
              state <= IDLE;                    -- return to the idle state
            end if;
        end case;
      end if;
    end if;
  end process;
end fancy;

architecture basic of debouncer is
  --
  -- Sampled input signal (must be done if the input signal comes directly from a FPGA pin)
  --
  signal sampled_dirty : std_logic := INITIAL_LEVEL;
  --
  -- Debouncer state
  --
  signal level : std_logic := INITIAL_LEVEL; -- always equal to the clean output signal
  --
  -- Timer (to control the amount of time spent in the acceptance window)
  --
  constant TIMER_LIMIT : integer := integer(CLOCK_FREQUENCY*WINDOW_DURATION);
  signal timer_counter : integer range 0 to TIMER_LIMIT := 0;
begin
  assert (TIMER_LIMIT > 0) report "Bad WINDOW_DURATION generic value" severity failure;
  --
  -- Sample the dirty input signal (pass it through a flip-flop, so that our logic will work with a synchronous signal)
  --
  process(clock) is
  begin
    if rising_edge(clock) then
      sampled_dirty <= dirty;
    end if;
  end process;
  --
  -- the clean output signal is the current level of the debouncer
  --
  clean <= level;
  --
  -- Debouncer
  --
  process(clock) is
  begin
    if rising_edge(clock) then
      zero_to_one_pulse <= '0'; -- default value
      one_to_zero_pulse <= '0'; -- default value
      if reset = '1' then
        level <= sampled_dirty;
        timer_counter <= TIMER_LIMIT;     -- initialize the timer counter
      elsif sampled_dirty = level then
        timer_counter <= TIMER_LIMIT;     -- reinitialize the timer counter when there is no change (for long enough) in the input signal level
      elsif timer_counter /= 0 then
        timer_counter <= timer_counter-1; -- inside the transition window (decrease the timer counter)
      else
        level <= sampled_dirty;           -- accept the new level
        if sampled_dirty = '1' then
          zero_to_one_pulse <= '1';       -- produce the '0' to '1' pulse
        else
          one_to_zero_pulse <= '1';       -- produce the '1' to '0' pulse
        end if;
        timer_counter <= TIMER_LIMIT;     -- initialize the timer counter
      end if;
    end if;
  end process;
end basic;
