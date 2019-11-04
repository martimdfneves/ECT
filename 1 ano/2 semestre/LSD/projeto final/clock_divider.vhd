library IEEE;
use IEEE.STD_LOGIC_1164.all;
use IEEE.NUMERIC_STD.all;

entity clock_divider is
	generic(k 	: natural);
	port(clock_in	: in  std_logic;
		  clock_out	: out std_logic);
end clock_divider;

architecture hardware of clock_divider is

	signal divider_count : natural;

begin
	assert(i >= 2);
	
	process(clock_in)
	begin
		if (rising_edge(clock_in)) then
			if (divider_count = k - 1) then
				clock_out		 <= '0';
				divider_count <= 0;
			else
				if (divider_count = (k / 2 - 1)) then
					clock_out	 <= '1';
				end if;
				divider_count <= divider_count + 1;
			end if;
		end if;
	end process;
end hardware;