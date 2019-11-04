library ieee;
use ieee.std_logic_1164.all;

entity inicio is
	port(relogio : in  std_logic;
		  estado  : in  std_logic;
		  saida_t : out std_logic;
		  saida_l : out std_logic);
end inicio;

architecture hardware of inicio is
signal sinal       : std_logic;
signal relogio_1hz : std_logic;
begin

divisor_relogio_1hz : entity work.divisor_relogio(hardware)
							generic map(i			=> 50000000)
							port map(relogio_in			=> relogio,
										relogio_out		=> relogio_1hz);
	process(estado)
	begin
		if (rising_edge(estado)) then
			if (sinal = '0') then
				sinal <= '1';
			else
				sinal <= '0';
			end if;
		end if;
	end process;
	
	saida_t <= relogio_1hz when(sinal='1') else '0';
	saida_l <= relogio_1hz when(sinal='0') else '0';
end hardware;