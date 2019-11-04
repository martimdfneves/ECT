library IEEE;
use IEEE.STD_LOGIC_1164.all;

entity PEnc4_2 is
port(datain  : in STD_LOGIC_VECTOR(3 downto 0);
	  dataout : out STD_LOGIC_VECTOR(1 downto 0));
end PEnc4_2;

architecture Behavioral of PEnc4_2 is
begin 
process(datain)
begin
if (datain(3)='1') then 
	dataout <= "11";
elsif (datain(2)='1') then 
	dataout <= "10";
elsif (datain(1)='1') then 
	dataout <= "01";
elsif (datain(0)='1') then 
	dataout <= "00";
end if;
end process; 
end Behavioral;