library IEEE;
use IEEE.STD_LOGIC_1164.all;

entity SeqDet1001 is
	port(LEDG : out std_logic_vector(8 downto 0);
		  s_reset : in std_logic;
		  SW   : in std_logic_vector(0 downto 0);
		  LEDR : out std_logic_vector(0 downto 0));
end SeqDet1001;

architecture teste of SeqDet1001 is
signal s_clk :std_logic;
begin
uut: entity work.SeqDetFSM(MealyArch) 
	port map(
				x_in => SW(0),
			   y_out => LEDR(0),
				clock_50 => s_clk,
				reset => s_reset);
LEDG(8) <= s_clk;
end teste;
 