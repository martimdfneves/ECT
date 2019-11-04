library IEEE;
use IEEE.STD_LOGIC_1164.all;
use IEEE.NUMERIC_STD.all;

entity Bin2BCD is
	port(binInput: in  std_logic_vector(3 downto 0);
		  output0 : out std_logic_vector(3 downto 0);
		  output1 : out std_logic_vector(3 downto 0));
end Bin2BCD;

architecture Behavioral of Bin2BCD is
	
	signal s_input, s_output0, s_output1: unsigned(3 downto 0);
	
begin

	s_input <= unsigned(binInput);
	
	s_output0 <= s_input when (binInput < "1010") else
					 (s_input - "1010") when (binInput >= "1010");
	s_output1 <= "0000" when (binInput < "1010") else
					 "0001" when (binInput >= "1010");
		
	output0 <= std_logic_vector(unsigned(s_output0));
	output1 <= std_logic_vector(unsigned(s_output1));
	
end Behavioral;
	
	