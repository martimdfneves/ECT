----------------------------------------------------------------------------------------------------------------------------------------------------------------
-- LSD.TOS, April 2018 (DO NOT REMOVE THIS LINE), VHDL 2008
--
-- RS232 controller (to exxchange information with a computer or another DE2-115 kit using a serial cable).
--
-- Warning: received data with a frame error is silently ignored.
--
-- To make the controller considerably simpler, a parity bit is not directly supported and the number of stop bits is always 1.
-- As a work around, the parity bit and a second stop bit can be incorporated into the data bits and tested/generated outside this entity.
-- It is assumed that the baud rate and the number of data bits are the same for the transmitter and for the receiver.
--
-- On the DE2-115 board, if the transmitter side of this entity is used then the baud rate should not be larger than 250000.0.
--
-- A simple example of the use of the ps2_controller entity can be found in the file ps2_controller_example_tl.vhd
--

library IEEE;
use     IEEE.STD_LOGIC_1164.ALL;
use     IEEE.NUMERIC_STD.ALL;

entity rs232_controller is
  generic
  (
    CLOCK_FREQUENCY : real range 10.0e6 to 250.0e6;            -- (in Hz) frequency of the clock signal
    BAUD_RATE       : real range 300.0 to 500.0e3 := 115200.0; -- (in number of bits per second) inverse of the duration of a bit
    DATA_BITS       : integer range 5 to 10 := 8               -- number of data bits
  );
  port
  (
    clock : in std_logic; -- main clock
    reset : in std_logic; -- reset

    uart_rxd : in  std_logic;        -- the received signal
    uart_txd : out std_logic := '1'; -- the signal to be transmitted

    rxd_data  : out std_logic_vector(DATA_BITS-1 downto 0) := (others => '0'); -- decoded received data
    rxd_valid : out std_logic := '0';                                          -- valid received data pulse (active only during one clock cycle)

    txd_data     : in  std_logic_vector(DATA_BITS-1 downto 0); -- data to send
    txd_request  : in  std_logic;                              -- send request (must be asserted until txd_accepted is asserted)
    txd_accepted : out std_logic := '0'                       -- send request accepted pulse (active only during one clock cycle)
  );
end rs232_controller;

architecture basic of rs232_controller is
  constant BIT_DURATION      : integer := integer(CLOCK_FREQUENCY/BAUD_RATE);
  constant HALF_BIT_DURATION : integer := integer(0.5*CLOCK_FREQUENCY/BAUD_RATE);
  constant TOTAL_BITS        : integer := 1+DATA_BITS+1; -- one start bit, DATA_BITS data bits, one stop bit
  --
  -- Receiver data
  --
  signal rxd         : std_logic;                               -- debounced uart_rxd
  signal rxd_start   : std_logic;                               -- asserted when rxd goes from '1' to '0'
  signal rxd_active  : std_logic := '0';                        -- asserted when the receiver is active (receiving data)
  signal rxd_n_bits  : integer range 0 to TOTAL_BITS;           -- number of bits received so far
  signal rxd_bits    : std_logic_vector(TOTAL_BITS-1 downto 0); -- the received bits
  signal rxd_counter : integer range 0 to BIT_DURATION-1;       -- internal counter
  --
  -- Transmitter data
  --
  signal txd_active  : std_logic := '0';                        -- asserted when the transmitter is active (sending data)
  signal txd_n_bits  : integer range 0 to TOTAL_BITS;           -- number of bits transmitted so far
  signal txd_bits    : std_logic_vector(TOTAL_BITS-1 downto 0); -- the data to transmit
  signal txd_counter : integer range 0 to BIT_DURATION-1;       -- internal counter
begin
  --
  -- Receiver (no synchronization at bit boundaries)
  --
  RX_DEBOUNCER : entity work.debouncer(basic) -- debounce the received signal using a time window with a duration of 5% of the duration of a bit
    generic map
    (
      CLOCK_FREQUENCY => CLOCK_FREQUENCY,
      WINDOW_DURATION => 0.05/BAUD_RATE,
      INITIAL_LEVEL => '1'
    )
    port map
    (
      clock             => clock,
      reset             => reset,
      dirty             => uart_rxd,
      clean             => rxd,
      zero_to_one_pulse => open,
      one_to_zero_pulse => rxd_start
    );
  process(clock) is
  begin
    if rising_edge(clock) then
      rxd_valid <= '0';
      if reset = '1' then
        rxd_active <= '0';
      elsif rxd_active = '0' and rxd_start = '1' then
        -- start bit detected
        rxd_active <= '1';
        rxd_n_bits <= 0;
        rxd_counter <= 0;
      elsif rxd_active = '1' then
        -- sample the rxd line in the middle of a bit
        if rxd_counter = HALF_BIT_DURATION then
          rxd_bits <= rxd & rxd_bits(TOTAL_BITS-1 downto 1);
        end if;
        if rxd_counter /= BIT_DURATION-1 then
          -- in the middle of a bit, advance rxd_counter
          if rxd_n_bits = TOTAL_BITS-1 and rxd_counter = HALF_BIT_DURATION then
            -- stop bit early termination (necessary when the receiver clock is slower than the transmitter clock)
            rxd_counter <= BIT_DURATION-1;
          else
            rxd_counter <= rxd_counter+1;
          end if;
        else
          -- at the end of a bit
          rxd_counter <= 0;
          rxd_n_bits <= rxd_n_bits+1;
          if rxd_n_bits = TOTAL_BITS-1 then
            -- all bits received
            rxd_active <= '0';
            rxd_data <= rxd_bits(DATA_BITS downto 1);
            if rxd_bits(0) = '0' and rxd_bits(TOTAL_BITS-1) = '1' then
              rxd_valid <= '1'; -- all is well (start bit is '0' and stop bit is '1')
            end if;
          end if;
        end if;
      end if;
    end if;
  end process;
  --
  -- Transmitter
  --
  process(clock) is
  begin
    if rising_edge(clock) then
      txd_accepted <= '0'; -- default
      if reset = '1' then
        txd_active <= '0';
      elsif txd_active = '0' and txd_request = '1' then
        -- start transmitting a byte
        txd_accepted <= '1';
        txd_active <= '1';
        txd_n_bits <= 0;
        txd_bits <= '1' & txd_data & '0'; -- for the right to the left: start bit, data bits, stop bit
        txd_counter <= 0;
      elsif txd_active = '1' then
        -- update uart_txd at the start of a new bit
        if txd_counter = 0 then
          uart_txd <= txd_bits(0);
          txd_bits <= '1' & txd_bits(TOTAL_BITS-1 downto 1); -- shift the data to be sent to the right (the shifted-in value is irrelevant)
        end if;
        if txd_counter /= BIT_DURATION-1 then
          -- in the middle of a bit, advance txd_counter
          txd_counter <= txd_counter+1;
        else
          -- at the end of a bit
          txd_counter <= 0;
          txd_n_bits <= txd_n_bits+1;
          if txd_n_bits = TOTAL_BITS-1 then
            -- all bits transmitted (because the stop bit is '1', there is no need to set uart_txd to '1' here)
            txd_active <= '0';
          end if;
        end if;
      end if;
    end if;
  end process;
end basic;
