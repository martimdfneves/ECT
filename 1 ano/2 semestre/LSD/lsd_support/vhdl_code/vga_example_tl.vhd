----------------------------------------------------------------------------------------------------------------------------------------------------------------
-- LSD.TOS, April 2018 (DO NOT REMOVE THIS LINE), VHDL 2008
--
-- Example of utilization of the VGA entities. This example does the following:
-- * instantiates the VGA entities; note that the master clock has to be the VGA clock
-- * generates an image with black/dark green chessboard pattern with a 16-pixel wide white border
--
-- More advanced examples can be found in the demonstrations directory.
--
-- The VGA example (under GNU/Linux, create and compile it using the command "make vga_example") uses the following files:
-- * vhdl_code/vga_example_tl.vhd
-- * vhdl_code/vga_config.vhd
-- * vhdl_code/vga.vhd
-- * vhdl_code/clock_generator.vhd
--

library IEEE;
use     IEEE.STD_LOGIC_1164.ALL;
use     IEEE.NUMERIC_STD.ALL;
use     WORK.VGA_CONFIG.ALL;

entity vga_example_tl is
  port
  (
    clock_50    : in    std_logic;

    vga_clk     : out   std_logic;
    vga_hs      : out   std_logic;
    vga_vs      : out   std_logic;
    vga_sync_n  : out   std_logic;
    vga_blank_n : out   std_logic;
    vga_r       : out   std_logic_vector(7 downto 0);
    vga_g       : out   std_logic_vector(7 downto 0);
    vga_b       : out   std_logic_vector(7 downto 0)
  );
end vga_example_tl;

architecture example of vga_example_tl is
  --
  -- The master clock is the VGA clock for all VGA projects
  --
  constant CLOCK_FREQUENCY : real := VGA_FREQUENCY;
  signal clock : std_logic;
  --
  -- VGA signals
  --
  signal data_0 : vga_data_t;
  signal data_1 : vga_data_t;
  signal rgb_1  : vga_rgb_t;
begin
  --
  -- The master clock
  --
  MASTER_CLOCK_GENERATOR : entity work.vga_clock_generator(optimal)
    port map
    (
      clock_50  => clock_50,
      vga_clock => clock
    );
  --
  -- VGA entities
  --
  VGA_C : entity work.vga_controller(basic)
    port map
    (
      clock      => clock,
      reset      => '0',
      vga_data_0 => data_0
    );
  VGA_O : entity work.vga_output(safe)
    port map
    (
      clock       => clock,
      vga_data    => data_1,
      vga_rgb     => rgb_1,
      vga_clk     => vga_clk,
      vga_hs      => vga_hs,
      vga_vs      => vga_vs,
      vga_sync_n  => vga_sync_n,
      vga_blank_n => vga_blank_n,
      vga_r       => vga_r,
      vga_g       => vga_g,
      vga_b       => vga_b
    );
  --
  -- VGA image generation pipeline stage 0 -> 1
  --
  -- It takes one clock cycle to compute rgb_1, so data_0 has to be delayed one clock cycle
  --
  process(clock) is
  begin
    if rising_edge(clock) then
      data_1 <= data_0;
      if data_0.x < 16 or data_0.y < 16 or data_0.x >= VGA_WIDTH-16 or data_0.y >= VGA_HEIGHT-16 then
        --
        -- white border (16 pixels wide)
        --
        rgb_1.r <= X"FF";
        rgb_1.g <= X"FF";
        rgb_1.b <= X"FF";
      elsif (data_0.x/16 mod 2) = (data_0.y/16 mod 2) then
        --
        -- 16x16 black squares (part of a chessboard pattern)
        --
        rgb_1.r <= X"00";
        rgb_1.g <= X"00";
        rgb_1.b <= X"00";
      else
        --
        -- 16x16 dark green squares (part of a chessboard pattern)
        --
        rgb_1.r <= X"00";
        rgb_1.g <= X"60";
        rgb_1.b <= X"00";
      end if;
    end if;
  end process;
end example;
