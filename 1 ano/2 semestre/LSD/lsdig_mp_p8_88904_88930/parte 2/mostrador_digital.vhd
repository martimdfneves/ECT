library IEEE;
use IEEE.STD_LOGIC_1164.all;

entity mostrador_digital is
	port(binario_in	: in  std_logic_vector(3 downto 0);
		 decimal_out	: out std_logic_vector(6 downto 0));
end mostrador_digital;

architecture hardware of mostrador_digital is

	signal sinal_out : std_logic_vector(6 downto 0);

begin
	with binario_in select
		sinal_out <= "1111001" when "0001",   
						  "0100100" when "0010",   
						  "0110000" when "0011",   
						  "0011001" when "0100",   
						  "0010010" when "0101",   
						  "0000010" when "0110",   
						  "1111000" when "0111",   
						  "0000000" when "1000",   
						  "0010000" when "1001",   
						  "1000000" when others;   

	decimal_out <= sinal_out;
	
end hardware;
