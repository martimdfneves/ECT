----------------------------------------------------------------------------------------------------------------------------------------------------------------
-- LSD.TOS, April 2018 (DO NOT REMOVE THIS LINE), VHDL 2008
--
-- Example of utilization of the PS/2 controller to receive events from a keyboard. This example does the following:
-- * turns ledg(8) on if a PS/2 keyboard is connected to the DE2-115 kit
-- * the state of the qwert keyboard keys (pressed or not pressed) is shown in the first 5 red leds
--
-- More advanced examples can be found in the demonstrations directory.
--
-- The PS/2 controller example (under GNU/Linux, create and compile it using the command "make ps2_controller_example") uses the following files:
-- * vhdl_code/ps2_controller_example_tl.vhd
-- * vhdl_code/ps2_controller.vhd
--

library IEEE;
use     IEEE.STD_LOGIC_1164.ALL;
use     IEEE.NUMERIC_STD.ALL;

entity ps2_controller_example_tl is
  generic
  (
    CLOCK_50_FREQUENCY : real := 50.0e6 -- frequency of the clock_50 input signal (must be 50MHz)
  );
  port
  (
    clock_50 : in std_logic;

    ledr : out std_logic_vector(4 downto 0); -- these leds are controlled by some keyboard keys
    ledg : out std_logic_vector(8 downto 8); -- this leds indicates if a PS/2 keyboard is connected to tghe DE2-115 kit

    ps2_clk : inout std_logic;
    ps2_dat : inout std_logic
  );
end ps2_controller_example_tl;

architecture example of ps2_controller_example_tl is
  --
  -- The master clock (in this case, this is just clock_50)
  --
  constant CLOCK_FREQUENCY : real := CLOCK_50_FREQUENCY;
  signal clock : std_logic;
  --
  -- PS/2
  --
  signal key_code,last_key_code : std_logic_vector(7 downto 0);      -- last two received key codes
  signal new_key_code_pulse : std_logic;                             -- new key code pulse
  signal q_state,w_state,e_state,r_state,t_state : std_logic := '0'; -- state of the qwert keyboard keys
begin
  --
  -- The master clock (in this case there is no need to change the clock frequency, so our master clock is just clock_50)
  --
  clock <= clock_50;
  --
  -- PS/2 controller
  --
  PS2_KEYBOARD_CONTROLLER : entity work.ps2_controller(smart)
    generic map
    (
      CLOCK_FREQUENCY => CLOCK_FREQUENCY
    )
    port map
    (
      clock => clock,
      reset => '0',  -- no reset
      ps2_clk => ps2_clk,
      ps2_dat => ps2_dat,
      keyboard_detected => ledg(8),
      keyboard_leds     => "010", -- just for fun, turn on the keyboard num lock led
      key_code          => key_code,
      valid_key_code    => new_key_code_pulse,
      mouse_detected   => open, -- in this simple example
      mouse_delta_x    => open, --   we are not
      mouse_delta_y    => open, --   interested in
      mouse_buttons    => open, --   PS/2 mouse
      valid_mouse_data => open  --   events
    );
  --
  -- Deal with keyboard key press and key release events
  --
  process(clock) is
  begin
    if rising_edge(clock) then
      if new_key_code_pulse = '1' then
        last_key_code <= key_code;                           -- a key press --    -- a key release --
        if key_code = X"15" then if last_key_code /= X"F0" then q_state <= '1'; else q_state <= '0'; end if; end if; -- q key
        if key_code = X"1D" then if last_key_code /= X"F0" then w_state <= '1'; else w_state <= '0'; end if; end if; -- w key
        if key_code = X"24" then if last_key_code /= X"F0" then e_state <= '1'; else e_state <= '0'; end if; end if; -- e key
        if key_code = X"2D" then if last_key_code /= X"F0" then r_state <= '1'; else r_state <= '0'; end if; end if; -- r key
        if key_code = X"2C" then if last_key_code /= X"F0" then t_state <= '1'; else t_state <= '0'; end if; end if; -- t key
      end if;
    end if;
  end process;
  --
  -- The red leds show the keyboard key states
  --
  ledr <= q_state & w_state & e_state & r_state & t_state;
end example;
