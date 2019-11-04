----------------------------------------------------------------------------------------------------------------------------------------------------------------
-- LSD.TOS, April 2018 (DO NOT REMOVE THIS LINE), VHDL 2008
--
-- Example of utilization of the RS232 controller. This example does the following:
-- * when sw(0) is '0', work in loop-back mode: transmit what is received
-- * when sw(0) is '1', work in counter mode: whenever key(0) is pressed, send a byte (first X"20" (space), then X"21", then X"22", and so on)
-- * the last received byte is displayed in hexadecimal in the two rightmost 7-segment displays
-- Use the lsd_term program (or another terminal emulator) to communicate with the DE2-115 kit using a USB-serial cable
--
-- More advanced examples can be found in the demonstrations directory.
--
-- The rs232 controller example (under GNU/Linux, create and compile it using the command "make rs232_controller_example") uses the following files:
-- * vhdl_code/rs232_controller_example_tl.vhd
-- * vhdl_code/rs232_controller.vhd
-- * vhdl_code/debouncer.vhd
-- * vhdl_code/seven_segment_decoder.vhd
--

library IEEE;
use     IEEE.STD_LOGIC_1164.ALL;
use     IEEE.NUMERIC_STD.ALL;

entity rs232_controller_example_tl is
  generic
  (
    CLOCK_50_FREQUENCY : real := 50.0e6 -- frequency of the clock_50 input signal (must be 50MHz)
  );
  port
  (
    clock_50 : in std_logic;

    key : in std_logic_vector(0 downto 0);
    sw  : in std_logic_vector(0 downto 0);

    hex0 : out std_logic_vector(6 downto 0);
    hex1 : out std_logic_vector(6 downto 0);

    uart_rxd : in  std_logic;
    uart_txd : out std_logic
  );
end rs232_controller_example_tl;

architecture example of rs232_controller_example_tl is
  --
  -- The master clock (in this case, this is just clock_50)
  --
  constant CLOCK_FREQUENCY : real := CLOCK_50_FREQUENCY;
  signal clock : std_logic;
  --
  -- State
  --
  signal state             : std_logic;                          -- debounced sw(0), loop-back mode when '0', "counter" mode when '1'
  signal count             : unsigned(7 downto 0) := "00100000"; -- byte to send
  signal key_pressed_pulse : std_logic;                          -- key(0) pressed pulse, asserted only when key(0) goes from '1' to '0'
  --
  -- RS232 data
  --
  signal rxd_data     : std_logic_vector(7 downto 0);
  signal rxd_valid    : std_logic;
  signal txd_data     : std_logic_vector(7 downto 0);
  signal txd_request  : std_logic;
  signal txd_accepted : std_logic := '0';
begin
  --
  -- The master clock (in this case there is no need to change the clock frequency, so our master clock is just clock_50)
  --
  clock <= clock_50;
  --
  -- Debounce keys and switches
  --
  KEY_DEBOUNCER : entity work.debouncer(basic)
    generic map
    (
      CLOCK_FREQUENCY => CLOCK_FREQUENCY,
      WINDOW_DURATION => 20.0e-6,  -- 20μs
      INITIAL_LEVEL   => '1'       -- assume the key is initially not pressed
    )
    port map
    (
      clock => clock,
      reset => '0',
      dirty => key(0),
      clean => open,
      zero_to_one_pulse => open,
      one_to_zero_pulse => key_pressed_pulse
   );
  SW_DEBOUNCER : entity work.debouncer(fancy)
    generic map
    (
      CLOCK_FREQUENCY => CLOCK_FREQUENCY,
      WINDOW_DURATION => 20.0e-6,  -- 20μs
      DELAY_DURATION  => 20.0e-3,  -- 20ms
      INITIAL_LEVEL   => '0'       -- assume the switch is initially in the off position (not really relevant, because of the global reset signal)
    )
    port map
    (
      clock => clock,
      reset => '0',
      dirty => sw(0),
      clean => state,
      zero_to_one_pulse => open,
      one_to_zero_pulse => open
    );
  --
  -- The RS232 controller
  --
  RS232 : entity work.rs232_controller(basic)
    generic map
    (
      CLOCK_FREQUENCY => CLOCK_FREQUENCY,
      BAUD_RATE => 115200.0,
      DATA_BITS => 8
    )
    port map
    (
      clock => clock,
      reset => '0',
      uart_rxd => uart_rxd,
      uart_txd => uart_txd,
      rxd_data     => rxd_data,
      rxd_valid    => rxd_valid,
      txd_data     => txd_data,
      txd_request  => txd_request,
      txd_accepted => txd_accepted
    );
  --
  -- The state machine
  --
  process(clock) is
  begin
    if rising_edge(clock) then
      --
      -- Deal with accepted transmission requests
      --
      if txd_accepted = '1' then
        txd_request <= '0';
      end if;
      --
      -- State actions
      --
      if state = '0' then
        --
        -- Loop-back mode (if our transmitter is slower that the remote transmitter then some bytes may be lost)
        --
        if rxd_valid = '1' then
          txd_request <= '1';
          txd_data <= rxd_data; -- will overwrite old value if txd_request was already '1'
        end if;
      else
        --
        -- Counter mode (if the key presses are fast enough, some count values will not be sent)
        --
        if key_pressed_pulse = '1' then
          txd_request <= '1';
          txd_data <= std_logic_vector(count); -- will overwrite old value if txd_request was already '1'
          count <= count+1;
        end if;
      end if;
    end if;
  end process;
  --
  -- Display (in hexadecimal) the last character received (note that rxd_data keeps the last received value)
  --
  DISPLAY_HI : entity work.seven_segment_decoder(hex_digits_and_extra_symbols)
    port map
    (
      code   => '0' & rxd_data(7 downto 4),
      enable => '1',
      seg    => hex1
    );
  DISPLAY_LO : entity work.seven_segment_decoder(hex_digits_and_extra_symbols)
    port map
    (
      code   => '0' & rxd_data(3 downto 0),
      enable => '1',
      seg    => hex0
    );
end example;
