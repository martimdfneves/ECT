library IEEE;
use IEEE.STD_LOGIC_1164.all;
use IEEE.NUMERIC_STD.all;

entity divisor_relogio is
	generic(i 	: natural);
	port(relogio_in	: in  std_logic;
		  relogio_out	: out std_logic);
end divisor_relogio;

architecture hardware of divisor_relogio is

	signal divisor_contador : natural;

begin
	assert(i >= 2);
	
	process(relogio_in)
	begin
		if (rising_edge(relogio_in)) then
			if (divisor_contador = i - 1) then
				relogio_out		 <= '0';
				divisor_contador <= 0;
			else
				if (divisor_contador = (i / 2 - 1)) then
					relogio_out	 <= '1';
				end if;
				divisor_contador <= divisor_contador + 1;
			end if;
		end if;
	end process;
end hardware;
