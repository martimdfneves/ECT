----------------------------------------------------------------------------------------------------------------------------------------------------------------
-- LSD.TOS, April 2018 (DO NOT REMOVE THIS LINE), VHDL 2008
--
-- Display all 128 font shapes (advanced example).
--
-- The font shapes advanced example (under GNU/Linux, create and compile it using the command "make font_shapes") uses the following files:
-- * vhdl_code/font_shapes_tl.vhd
-- * vhdl_code/vga_config.vhd
-- * vhdl_code/vga.vhd
-- * vhdl_code/clock_generator.vhd
-- * vhdl_code/font_8x8_bold.vhd
-- * vhdl_code/font_16x16_bold.vhd
-- * vhdl_code/screen_capture.vhd
-- * vhdl_code/rs232_controller.vhd
-- * vhdl_code/debouncer.vhd
--

library IEEE;
use     IEEE.STD_LOGIC_1164.ALL;
use     IEEE.NUMERIC_STD.ALL;
use     WORK.VGA_CONFIG.ALL;

entity font_shapes_tl is
  generic
  (
    SCREEN_CAPTURE_SUPPORT : boolean := false -- if true, include code to capture a VGA screen
  );
  port
  (
    clock_50    : in    std_logic;

    uart_rxd : in  std_logic;  -- screen capture interface
    uart_txd : out std_logic;  -- screen capture interface

    vga_clk     : out   std_logic;
    vga_hs      : out   std_logic;
    vga_vs      : out   std_logic;
    vga_sync_n  : out   std_logic;
    vga_blank_n : out   std_logic;
    vga_r       : out   std_logic_vector(7 downto 0);
    vga_g       : out   std_logic_vector(7 downto 0);
    vga_b       : out   std_logic_vector(7 downto 0)
  );
end font_shapes_tl;

architecture advanced_example of font_shapes_tl is
  --
  -- The master clock is the VGA clock for all VGA projects
  --
  constant CLOCK_FREQUENCY : real := VGA_FREQUENCY;
  signal clock : std_logic;
  --
  -- VGA signals
  --
  signal vga_data_0 : vga_data_t;
  --
  -- Signals output by the 0 -> 1 pipeline stage
  --
  signal vga_data_1      : vga_data_t;
  signal tile_row_char_1 : integer range 0 to 127; -- character number displayed by the first tile of a row
  signal tile_char_1     : integer range 0 to 127; -- tile character number
  signal tile_x_1        : integer range 0 to 49;  -- x coordinate inside a tile
  signal tile_y_1        : integer range 0 to 74;  -- y coordinate inside a tile
  --
  -- Signals output by the 1 -> 2 pipeline stage
  --
  signal vga_data_2                  : vga_data_t;
  signal tile_char_2                 : integer range 0 to 127; -- tile character number
  signal tile_x_2                    : integer range 0 to 49;  -- x coordinate inside a tile
  signal tile_y_2                    : integer range 0 to 74;  -- y coordinate inside a tile
  signal big_font_visible_row_2      : std_logic;              -- row is visible indicator
  signal big_font_row_2              : integer range 0 to 15;  -- font_16x16_bold row address
  signal big_font_visible_column_2   : std_logic;              -- column is visible indicator
  signal big_font_column_2           : integer range 0 to 15;  -- font_16x16_bold column address
  signal small_font_visible_row_2    : std_logic;              -- row is visible indicator
  signal small_font_row_2            : integer range 0 to  7;  -- font_8x8_bold row address
  signal small_font_visible_column_2 : std_logic;              -- column is visible indicator
  signal small_font_column_2         : integer range 0 to  7;  -- font_8x8_bold column address
  signal label_char_2                : integer range 0 to 127; -- hexadecimal character number
  signal label_row_2                 : integer range 0 to 7;   -- row address
  signal label_column_2              : integer range 0 to 7;   -- column address
  --
  -- Signals output by the 2 -> 3 pipeline stage
  --
  signal vga_data_3           : vga_data_t;
  signal tile_x_3             : integer range 0 to 49;  -- x coordinate inside a tile
  signal tile_y_3             : integer range 0 to 74;  -- y coordinate inside a tile
  signal big_font_visible_3   : std_logic;              -- is visible indicator
  signal small_font_visible_3 : std_logic;              -- is visible indicator
  signal big_font_color_3     : std_logic;              -- font_16x16_bold pixel color
  signal small_font_color_3   : std_logic;              -- font_8x8_bold pixel color
  signal label_color_3        : std_logic;              -- label color
  --
  -- Signals output by the 3 -> 4 pipeline stage
  --
  signal vga_data_4 : vga_data_t;
  signal vga_rgb_4  : vga_rgb_t;
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
  -- The optional screen capture stuff
  --
I:if SCREEN_CAPTURE_SUPPORT = true generate
SC: entity work.screen_capture(gets_the_job_done)
      port map
      (
        clock    => clock,
        vga_data => vga_data_4,
        vga_rgb  => vga_rgb_4,
        uart_rxd => uart_rxd,
        uart_txd => uart_txd,
        freeze   => open
      );
  end generate;
  --
  -- VGA entities
  --
  VGA_C : entity work.vga_controller(basic)
    port map
    (
      clock      => clock,
      reset      => '0',
      vga_data_0 => vga_data_0
    );
  VGA_O : entity work.vga_output(safe)
    port map
    (
      clock       => clock,
      vga_data    => vga_data_4,
      vga_rgb     => vga_rgb_4,
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
  -- The VGA image is subdivided into 50x75 tiles, each with the layout
  --
  --              1111111111222222222233333333334444444444
  --    01234567890123456789012345678901234567890123456789
  --
  --  0 BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBS    S = black
  --  1 BuuBuuBuuBuuBuuBuuBuuBuuBuuBuuBuuBuuBuuBuuBuuBuuBS    B = dark gray
  --  2 BuuBuuBuuBuuBuuBuuBuuBuuBuuBuuBuuBuuBuuBuuBuuBuuBS    u = 16x16 font data ('0' -> dark red, '1' -> white)
  --  3 BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBS    v = 8x8 font data ('0' -> dark green, '1' -> white)
  --    ...                                                   X = character number in hexadecimal (in cyan)
  -- 45 BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBS
  -- 46 BuuBuuBuuBuuBuuBuuBuuBuuBuuBuuBuuBuuBuuBuuBuuBuuBS
  -- 47 BuuBuuBuuBuuBuuBuuBuuBuuBuuBuuBuuBuuBuuBuuBuuBuuBS
  -- 48 BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBS
  -- 49                         BBBBBBBBBBBBBBBBBBBBBBBBBS
  -- 50                         BvvBvvBvvBvvBvvBvvBvvBvvBS
  -- 51       XXXXXXXXXXXXXXXX  BvvBvvBvvBvvBvvBvvBvvBvvBS
  -- 52       XXXXXXXXXXXXXXXX  BBBBBBBBBBBBBBBBBBBBBBBBBS
  -- 53       XXXXXXXXXXXXXXXX  BvvBvvBvvBvvBvvBvvBvvBvvBS
  -- 54       XXXXXXXXXXXXXXXX  BvvBvvBvvBvvBvvBvvBvvBvvBS
  -- 55       XXXXXXXXXXXXXXXX  BBBBBBBBBBBBBBBBBBBBBBBBBS
  -- 56       XXXXXXXXXXXXXXXX  BvvBvvBvvBvvBvvBvvBvvBvvBS
  -- 57       XXXXXXXXXXXXXXXX  BvvBvvBvvBvvBvvBvvBvvBvvBS
  -- 58       XXXXXXXXXXXXXXXX  BBBBBBBBBBBBBBBBBBBBBBBBBS
  -- 59                         BvvBvvBvvBvvBvvBvvBvvBvvBS
  -- 60                         BvvBvvBvvBvvBvvBvvBvvBvvBS
  -- 61                         BBBBBBBBBBBBBBBBBBBBBBBBBS
  -- 62                         BvvBvvBvvBvvBvvBvvBvvBvvBS
  -- 63                         BvvBvvBvvBvvBvvBvvBvvBvvBS
  -- 64                         BBBBBBBBBBBBBBBBBBBBBBBBBS
  -- 65                         BvvBvvBvvBvvBvvBvvBvvBvvBS
  -- 66                         BvvBvvBvvBvvBvvBvvBvvBvvBS
  -- 67                         BBBBBBBBBBBBBBBBBBBBBBBBBS
  -- 68                         BvvBvvBvvBvvBvvBvvBvvBvvBS
  -- 69                         BvvBvvBvvBvvBvvBvvBvvBvvBS
  -- 70                         BBBBBBBBBBBBBBBBBBBBBBBBBS
  -- 71                         BvvBvvBvvBvvBvvBvvBvvBvvBS
  -- 72                         BvvBvvBvvBvvBvvBvvBvvBvvBS
  -- 73                         BBBBBBBBBBBBBBBBBBBBBBBBBS
  -- 74 SSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS
  --
  --              1111111111222222222233333333334444444444
  --    01234567890123456789012345678901234567890123456789
  --
  -- VGA image generation pipeline stage 0 -> 1 (compute tile data)
  --
  process(clock) is
  begin
    if rising_edge(clock) then
      vga_data_1 <= vga_data_0;
      if vga_data_0.x = 0 and vga_data_0.y = 0 then -- if true, new image frame
        tile_row_char_1 <= 0;
        tile_char_1 <= 0;
        tile_x_1 <= 0;
        tile_y_1 <= 0;
      elsif vga_data_0.x = 0 then                   -- if true, new image line
        if tile_y_1 = 74 then                       -- if true, new row of tiles
          tile_row_char_1 <= tile_row_char_1+16;
          tile_char_1 <= tile_row_char_1+16;
          tile_y_1 <= 0;
        else                                        -- otherwise, same row of tiles
          tile_char_1 <= tile_row_char_1;
          tile_y_1 <= tile_y_1+1;
        end if;
        tile_x_1 <= 0;
      elsif tile_x_1 = 49 then                     -- if true, new tile column
        tile_char_1 <= tile_char_1+1;
        tile_x_1 <= 0;
      else                                         -- otherwise, same tile
        tile_x_1 <= tile_x_1+1;
      end if;
    end if;
  end process;
  --
  -- VGA image generation pipeline stage 1 -> 2
  --
  process(clock) is
  begin
    if rising_edge(clock) then
      --
      -- delay signals
      --
      vga_data_2      <= vga_data_1;
      tile_char_2     <= tile_char_1;
      tile_x_2        <= tile_x_1;
      tile_y_2        <= tile_y_1;
      --
      -- compute big font row number
      --
      case tile_y_1 is
        when  1 |  2 =>  big_font_visible_row_2 <= '1'; big_font_row_2 <=  0;
        when  4 |  5 =>  big_font_visible_row_2 <= '1'; big_font_row_2 <=  1;
        when  7 |  8 =>  big_font_visible_row_2 <= '1'; big_font_row_2 <=  2;
        when 10 | 11 =>  big_font_visible_row_2 <= '1'; big_font_row_2 <=  3;
        when 13 | 14 =>  big_font_visible_row_2 <= '1'; big_font_row_2 <=  4;
        when 16 | 17 =>  big_font_visible_row_2 <= '1'; big_font_row_2 <=  5;
        when 19 | 20 =>  big_font_visible_row_2 <= '1'; big_font_row_2 <=  6;
        when 22 | 23 =>  big_font_visible_row_2 <= '1'; big_font_row_2 <=  7;
        when 25 | 26 =>  big_font_visible_row_2 <= '1'; big_font_row_2 <=  8;
        when 28 | 29 =>  big_font_visible_row_2 <= '1'; big_font_row_2 <=  9;
        when 31 | 32 =>  big_font_visible_row_2 <= '1'; big_font_row_2 <= 10;
        when 34 | 35 =>  big_font_visible_row_2 <= '1'; big_font_row_2 <= 11;
        when 37 | 38 =>  big_font_visible_row_2 <= '1'; big_font_row_2 <= 12;
        when 40 | 41 =>  big_font_visible_row_2 <= '1'; big_font_row_2 <= 13;
        when 43 | 44 =>  big_font_visible_row_2 <= '1'; big_font_row_2 <= 14;
        when 46 | 47 =>  big_font_visible_row_2 <= '1'; big_font_row_2 <= 15;
        when others  =>  big_font_visible_row_2 <= '0';
      end case;
      --
      -- compute big font column number
      --
      case tile_x_1 is
        when  1 |  2 =>  big_font_visible_column_2 <= '1'; big_font_column_2 <=  0;
        when  4 |  5 =>  big_font_visible_column_2 <= '1'; big_font_column_2 <=  1;
        when  7 |  8 =>  big_font_visible_column_2 <= '1'; big_font_column_2 <=  2;
        when 10 | 11 =>  big_font_visible_column_2 <= '1'; big_font_column_2 <=  3;
        when 13 | 14 =>  big_font_visible_column_2 <= '1'; big_font_column_2 <=  4;
        when 16 | 17 =>  big_font_visible_column_2 <= '1'; big_font_column_2 <=  5;
        when 19 | 20 =>  big_font_visible_column_2 <= '1'; big_font_column_2 <=  6;
        when 22 | 23 =>  big_font_visible_column_2 <= '1'; big_font_column_2 <=  7;
        when 25 | 26 =>  big_font_visible_column_2 <= '1'; big_font_column_2 <=  8;
        when 28 | 29 =>  big_font_visible_column_2 <= '1'; big_font_column_2 <=  9;
        when 31 | 32 =>  big_font_visible_column_2 <= '1'; big_font_column_2 <= 10;
        when 34 | 35 =>  big_font_visible_column_2 <= '1'; big_font_column_2 <= 11;
        when 37 | 38 =>  big_font_visible_column_2 <= '1'; big_font_column_2 <= 12;
        when 40 | 41 =>  big_font_visible_column_2 <= '1'; big_font_column_2 <= 13;
        when 43 | 44 =>  big_font_visible_column_2 <= '1'; big_font_column_2 <= 14;
        when 46 | 47 =>  big_font_visible_column_2 <= '1'; big_font_column_2 <= 15;
        when others  =>  big_font_visible_column_2 <= '0';
      end case;
      --
      -- compute snmall font row number
      --
      case tile_y_1 is
        when 50 | 51 =>  small_font_visible_row_2 <= '1'; small_font_row_2 <=  0;
        when 53 | 54 =>  small_font_visible_row_2 <= '1'; small_font_row_2 <=  1;
        when 56 | 57 =>  small_font_visible_row_2 <= '1'; small_font_row_2 <=  2;
        when 59 | 60 =>  small_font_visible_row_2 <= '1'; small_font_row_2 <=  3;
        when 62 | 63 =>  small_font_visible_row_2 <= '1'; small_font_row_2 <=  4;
        when 65 | 66 =>  small_font_visible_row_2 <= '1'; small_font_row_2 <=  5;
        when 68 | 69 =>  small_font_visible_row_2 <= '1'; small_font_row_2 <=  6;
        when 71 | 72 =>  small_font_visible_row_2 <= '1'; small_font_row_2 <=  7;
        when others  =>  small_font_visible_row_2 <= '0';
      end case;
      --
      -- compute small font column number
      --
      case tile_x_1 is
        when 25 | 26 =>  small_font_visible_column_2 <= '1'; small_font_column_2 <=  0;
        when 28 | 29 =>  small_font_visible_column_2 <= '1'; small_font_column_2 <=  1;
        when 31 | 32 =>  small_font_visible_column_2 <= '1'; small_font_column_2 <=  2;
        when 34 | 35 =>  small_font_visible_column_2 <= '1'; small_font_column_2 <=  3;
        when 37 | 38 =>  small_font_visible_column_2 <= '1'; small_font_column_2 <=  4;
        when 40 | 41 =>  small_font_visible_column_2 <= '1'; small_font_column_2 <=  5;
        when 43 | 44 =>  small_font_visible_column_2 <= '1'; small_font_column_2 <=  6;
        when 46 | 47 =>  small_font_visible_column_2 <= '1'; small_font_column_2 <=  7;
        when others  =>  small_font_visible_column_2 <= '0';
      end case;
      --
      -- label text
      --
      if tile_x_1 >= 6 and tile_x_1 <= 21 and tile_y_1 >= 51 and tile_y_1 <= 58 then
        if tile_x_1 <= 13 then
          if (tile_char_1/16) < 10 then
            label_char_2 <= 48+(tile_char_1/16);
          else
            label_char_2 <= 55+(tile_char_1/16);
          end if;
        else
          if (tile_char_1 mod 16) < 10 then
            label_char_2 <= 48+(tile_char_1 mod 16);
          else
            label_char_2 <= 55+(tile_char_1 mod 16);
          end if;
        end if;
        label_row_2 <= (tile_y_1-51) mod 8;
        label_column_2 <= (tile_x_1-6) mod 8;
      end if;
    end if;
  end process;
  --
  -- VGA image generation pipeline stage 2 -> 3
  --
  FONT_16 : entity work.font_16x16_bold(font_data)
    port map
    (
      clock    => clock,
      char_0   => std_logic_vector(to_unsigned(tile_char_2,7)),
      row_0    => std_logic_vector(to_unsigned(big_font_row_2,4)),
      column_0 => std_logic_vector(to_unsigned(big_font_column_2,4)),
      data_1   => big_font_color_3
    );
  FONT_8 : entity work.font_8x8_bold(font_data)
    port map
    (
      clock    => clock,
      char_0   => std_logic_vector(to_unsigned(tile_char_2,7)),
      row_0    => std_logic_vector(to_unsigned(small_font_row_2,3)),
      column_0 => std_logic_vector(to_unsigned(small_font_column_2,3)),
      data_1   => small_font_color_3
    );
  FONT_8t : entity work.font_8x8_bold(font_data)
    port map
    (
      clock    => clock,
      char_0   => std_logic_vector(to_unsigned(label_char_2,7)),
      row_0    => std_logic_vector(to_unsigned(label_row_2,3)),
      column_0 => std_logic_vector(to_unsigned(label_column_2,3)),
      data_1   => label_color_3
    );
  process(clock) is
  begin
    if rising_edge(clock) then
      vga_data_3           <= vga_data_2;
      tile_x_3             <= tile_x_2;
      tile_y_3             <= tile_y_2;
      big_font_visible_3   <= big_font_visible_row_2 and big_font_visible_column_2;
      small_font_visible_3 <= small_font_visible_row_2 and small_font_visible_column_2;
      end if;
  end process;
  --
  -- VGA image generation pipeline stage 3 -> 4
  --
  process(clock) is
  begin
    if rising_edge(clock) then
      vga_data_4 <= vga_data_3;
      --
      -- the default color is black
      --
      vga_rgb_4.r <= X"00";
      vga_rgb_4.g <= X"00";
      vga_rgb_4.b <= X"00";
      if tile_x_3 <= 48 and tile_y_3 <= 48 then
        --
        -- inside the font_16x16_bold zoom area
        --
        if big_font_visible_3 = '0' then
          --
          -- dark red background
          --
          vga_rgb_4.r <= X"40";
        else
          --
          -- font pixels in black/white
          --
          vga_rgb_4.r <= (others => big_font_color_3);
          vga_rgb_4.g <= (others => big_font_color_3);
          vga_rgb_4.b <= (others => big_font_color_3);
        end if;
      elsif tile_x_3 >= 24 and tile_x_3 <= 48 and tile_y_3 >= 49 and tile_y_3 <= 73 then
        --
        -- inside the font_8x8_bold zoom area
        --
        if small_font_visible_3 = '0' then
          --
          -- dark green background
          --
          vga_rgb_4.g <= X"40";
        else
          --
          -- font pixels in black/white
          --
          vga_rgb_4.r <= (others => small_font_color_3);
          vga_rgb_4.g <= (others => small_font_color_3);
          vga_rgb_4.b <= (others => small_font_color_3);
        end if;
      elsif tile_x_3 >= 6 and tile_x_3 <= 21 and tile_y_3 >= 51 and tile_y_3 <= 58 then
        --
        -- inside the label area
        --
        if label_color_3 = '1' then
          --
          -- label text in cyan
          --
          vga_rgb_4.g <= X"FF";
          vga_rgb_4.b <= X"FF";
        end if;
      end if;
    end if;
  end process;
end advanced_example;
