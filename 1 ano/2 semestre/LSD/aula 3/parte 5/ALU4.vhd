library IEEE;
use IEEE.STD_LOGIC_1164.all;
use IEEE.NUMERIC_STD.all;

entity ALU4 is
	port(a, b: in	std_logic_vector(3 downto 0);
		  op	: in	std_logic_vector(2 downto 0);
		  r, m: out std_logic_vector(3 downto 0));
end ALU4;

architecture Behavioral of ALU4 is

	signal s_a_s, s_b_s, s_r_s: signed(3 downto 0);
	signal s_m		     		  : unsigned(7 downto 0);
	signal s_a_u, s_b_u       : unsigned(3 downto 0);

begin
	
	s_a_s <= signed(a);
	s_b_s <= signed(b);
	s_a_u <= unsigned(a);
	s_b_u <= unsigned(b);
	
	s_m <= s_a_u * s_b_u;
	
	with op select
		s_r_s <= s_a_s + s_b_s         when "000",
				 s_a_s - s_b_s           when "001",
				 signed(s_m(3 downto 0)) when "010",
				 signed(s_a_u/s_b_u)     when "011",
				 signed(s_a_u rem s_b_u) when "100",
				 s_a_s and s_b_s         when "101",
				 s_a_s or  s_b_s         when "110",
				 s_a_s xor s_b_s         when "111";
	
	r <= std_logic_vector(s_r_s);
	m <= std_logic_vector(s_m(7 downto 4)) when (op = "010") else (others => '0');

end Behavioral;
	