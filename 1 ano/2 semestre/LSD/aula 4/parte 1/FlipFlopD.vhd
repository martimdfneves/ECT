library IEEE;
use IEEE.STD_LOGIC_1164.all;

entity FlipFlopD is
	port(clk : in std_logic;
		  d   : in std_logic;
		  set : in std_logic;
		  rst : in std_logic;
		  q   : out std_logic);
end FlipFlopD;

architecture Behavioral of FlipFlopD is
begin
	process(clk,rst)
	begin
		if (rst = '1') then
		q <= '0';
		elsif(rising_edge(clk)) then
			if set = '1' then 
			q <= d;
			end if;
		end if;
	end process;
end Behavioral;