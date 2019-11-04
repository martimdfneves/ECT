----------------------------------------------------------------------------------------------------------------------------------------------------------------
-- LSD.TOS, April 2018 (DO NOT REMOVE THIS LINE), VHDL 2008
--
-- Screen capture.
--
-- To use it, copy vhdl_code/screen_capture.vhd, vhdl_code/rs232_controller.vhd , and vhdl_code/debouncer.vhd to your project, put
--    uart_rxd : in  std_logic;  -- screen capture interface
--    uart_txd : out std_logic;  -- screen capture interface
-- in the top level ports, put
--   signal screen_capture_freeze : std_logic;
-- in the top level signal area, and put
--   C:entity work.screen_capture(gets_the_job_done)
--       port map
--       (
--         clock    => clock,
--         vga_data => vga_data_1,
--         vga_rgb  => vga_rgb_1,
--         uart_rxd => uart_rxd,
--         uart_txd => uart_txd,
--         freeze   => screen_capture_freeze
--       );
-- in the top level architecture. Replace vga_data_1 and vga_rgb_1 with whatever is appropriate in your case.
--
-- To do: do not rely on the x and y coordinates (to allow image transformations).
--

library IEEE;
use     IEEE.STD_LOGIC_1164.ALL;
use     IEEE.NUMERIC_STD.ALL;
use     WORK.VGA_CONFIG.ALL;

entity screen_capture is
  port
  (
    clock    : in  std_logic;
    vga_data : in  vga_data_t;
    vga_rgb  : in  vga_rgb_t;

    uart_rxd : in  std_logic;
    uart_txd : out std_logic;

    freeze          : out std_logic
  );
end screen_capture;

architecture gets_the_job_done of screen_capture is
  signal state                   : std_logic_vector(31 downto 0);
  signal write_data              : std_logic_vector(31 downto 0);
  signal write_en                : std_logic;
  signal recording               : std_logic := '0';
  signal empty                   : std_logic;
  signal top                     : vga_x_t := VGA_HEIGHT;
  signal bottom                  : vga_y_t := VGA_HEIGHT;
  signal record_request          : std_logic;
  signal record_request_accepted : std_logic;
begin
B:entity work.special_circular_buffer(gets_the_job_done)
    port map
    (
      clock      => clock,
      write_data => write_data,
      write_en   => write_en,
      empty => empty,
      record_request          => record_request,
      record_request_accepted => record_request_accepted,
      uart_rxd => uart_rxd,
      uart_txd => uart_txd
    );
  freeze <= '1' when top /= VGA_HEIGHT else '0';
  process(clock) is
  begin
    if rising_edge(clock) then
      write_en <= '0';
      --
      -- Recording control
      --
      record_request_accepted <= '0';
      if vga_data.x = 0 and vga_data.y = VGA_HEIGHT+2 then
        if record_request = '1' and empty = '1' and top = VGA_HEIGHT then
          record_request_accepted <= '1';
          recording <= '1';
          top <= 0;
          bottom <= 50;
          write_data <= std_logic_vector(to_unsigned(VGA_WIDTH,16)) & std_logic_vector(to_unsigned(VGA_HEIGHT,16));
          write_en <= '1';
        elsif empty = '1' and top /= 0 and top /= VGA_HEIGHT then
          recording <= '1';
        end if;
      end if;
      --
      -- Run-length encoding
      --
      if vga_data.blank_n = '1' then
        if vga_data.x = 0 and vga_data.y = 0 then
          state <= "00000000" & vga_rgb.r & vga_rgb.g & vga_rgb.b;
        elsif state(31 downto 24) = "11111111" or state(23 downto 16) /= vga_rgb.r or state(15 downto 8) /= vga_rgb.g or state(7 downto 0) /= vga_rgb.b then
          if recording = '1' and vga_data.y >= top and vga_data.y < bottom then
            write_data <= state;
            write_en <= '1';
          end if;
          state <= "00000000" & vga_rgb.r & vga_rgb.g & vga_rgb.b;
        else
          state(31 downto 24) <= std_logic_vector(unsigned(state(31 downto 24))+1);
        end if;
      elsif vga_data.x = VGA_WIDTH and vga_data.y = VGA_HEIGHT-1 then
        if recording = '1' and vga_data.y >= top and vga_data.y < bottom then
          write_data <= state;
          write_en <= '1';
        end if;
        if recording = '1' then
          top <= bottom;
          if bottom <= VGA_HEIGHT-50 then
            bottom <= bottom+50;
          else
            bottom <= VGA_HEIGHT;
          end if;
        end if;
        recording <= '0';
      end if;
    end if;
  end process;
end gets_the_job_done;


----------------------------------------------------------------------------------------------------------------------------------------------------------------
-- LSD.TOS, April 2018 (DO NOT REMOVE THIS LINE), VHDL 2008
--
-- Special circular buffer.
--

library IEEE;
use     IEEE.STD_LOGIC_1164.ALL;
use     IEEE.NUMERIC_STD.ALL;
use     WORK.VGA_CONFIG.ALL;

entity special_circular_buffer is
  port
  (
    clock : in std_logic;

    write_data : in  std_logic_vector(31 downto 0);
    write_en   : in  std_logic;
    empty      : out std_logic;

    record_request          : out std_logic := '0';
    record_request_accepted : in  std_logic;

    uart_rxd : in  std_logic; -- not used
    uart_txd : out std_logic
  );
end special_circular_buffer;

architecture gets_the_job_done of special_circular_buffer is
  constant ADDR_BITS  : integer := 16;
  signal write_addr   : integer range 0 to 2**ADDR_BITS-1 := 0;
  signal read_addr    : integer range 0 to 2**ADDR_BITS-1 := 0;
  signal send_state   : integer range 0 to 4 := 0;
  signal read_data    : std_logic_vector(31 downto 0);
  signal rxd_data     : std_logic_vector(7 downto 0);
  signal rxd_valid    : std_logic;
  signal txd_data     : std_logic_vector(7 downto 0);
  signal txd_request  : std_logic;
  signal txd_accepted : std_logic;
begin
M:entity work.dual_port_memory(gets_the_job_done)
    generic map
    (
      ADDR_BITS => ADDR_BITS,
      DATA_BITS => 32
    )
   port map
   (
     clock => clock,
     write_addr_0 => std_logic_vector(to_unsigned(write_addr,ADDR_BITS)),
     write_data_0 => write_data,
     write_en_0 => write_en,
     read_addr_0 => std_logic_vector(to_unsigned(read_addr,ADDR_BITS)),
     read_data_1 => read_data
   );
R:entity work.rs232_controller(basic)
    generic map
    (
      CLOCK_FREQUENCY => VGA_FREQUENCY,
      BAUD_RATE       => 230400.0,
      DATA_BITS       => 8
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
  -- record request
  --
  process(clock) is
  begin
    if rising_edge(clock) then
      if rxd_valid = '1' and rxd_data = X"20" then
        record_request <= '1';
      elsif record_request_accepted = '1' then
        record_request <= '0';
      end if;
    end if;
  end process;
  --
  -- asynchronous stuff
  --
  txd_request <= '1' when send_state /= 0 else '0';
  with send_state select txd_data <= "00000000"              when 0,
                                     read_data(31 downto 24) when 1,
                                     read_data(23 downto 16) when 2,
                                     read_data(15 downto  8) when 3,
                                     read_data( 7 downto  0) when 4;
  --
  -- synchronous stuff
  --
  process(clock) is
  begin
    if rising_edge(clock) then
      if read_addr = write_addr and send_state = 0 then
        empty <= '1';
      else
        empty <= '0';
      end if;
      if write_en = '1' then
        write_addr <= write_addr+1;
      end if;
      case send_state is
        when 0 =>
          if read_addr /= write_addr then
            send_state <= 1; -- one cycle delay to make sure read_data is up to date
          end if;
        when 1 | 2 | 3 =>
          if txd_accepted = '1' then
            send_state <= send_state+1;
          end if;
        when 4 =>
          if txd_accepted = '1' then
            send_state <= 0;
            read_addr <= read_addr+1;
          end if;
      end case;
    end if;
  end process;
end gets_the_job_done;


----------------------------------------------------------------------------------------------------------------------------------------------------------------
-- LSD.TOS, April 2018 (DO NOT REMOVE THIS LINE), VHDL 2008
--
-- Dual port synchronous memory (one write port and one read port)
--

library IEEE;
use     IEEE.STD_LOGIC_1164.ALL;
use     IEEE.NUMERIC_STD.ALL;

entity dual_port_memory is
  generic
  (
    ADDR_BITS : integer range 4 to 16;
    DATA_BITS : integer range 1 to 32
  );
  port
  (
    clock        : in std_logic;

    write_addr_0 : in  std_logic_vector(ADDR_BITS-1 downto 0);
    write_data_0 : in  std_logic_vector(DATA_BITS-1 downto 0);
    write_en_0   : in  std_logic;

    read_addr_0  : in  std_logic_vector(ADDR_BITS-1 downto 0);
    read_data_1  : out std_logic_vector(DATA_BITS-1 downto 0)
  );
end dual_port_memory;

architecture gets_the_job_done of dual_port_memory is
  type ram_t is array(0 to 2**ADDR_BITS-1) of std_logic_vector(DATA_BITS-1 downto 0);
  signal ram : ram_t;
begin
  process(clock) is
  begin
    if rising_edge(clock) then
      if write_en_0 = '1' then
        ram(to_integer(unsigned(write_addr_0))) <= write_data_0;
      end if;
      read_data_1 <= ram(to_integer(unsigned(read_addr_0)));
    end if;
  end process;
end gets_the_job_done;
