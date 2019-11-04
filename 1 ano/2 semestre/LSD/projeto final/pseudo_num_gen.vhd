library IEEE;
use     IEEE.STD_LOGIC_1164.ALL;
use     IEEE.NUMERIC_STD.ALL;

entity pseudo_num_gen is 
	generic 
	(
	num_bits : integer range 1 to 24:=4;
	semente  : std_logic_vector(47 downto 0):=x"000000000000");
	port( my_clock   : in  std_logic;
			write_data : in  std_logic_vector(3 downto 0);
			read_data  : out std_logic_vector(3 downto 0);
			user       : in  std_logic;
			user1      : in  std_logic;
			insert     : in  std_logic;
			my_enable  : in  std_logic := '1';                   
			rng  	     : out std_logic_vector(num_bits-1 downto 0);
			out1, out2, out3, out4, out5, out6, out7, out8 : out std_logic_vector(3 downto 0));
end pseudo_num_gen;

architecture heavy of pseudo_num_gen is 
	constant NUM_WORDS : integer := 16;
	subtype TDataWord is std_logic_vector(3 downto 0);
	type TMemory is array (0 to NUM_WORDS-1) of TDataWord;
	type State is (s0,s1,s2,s3,s4,s5,sf);
	signal PS,NS : State;
	signal s_memory : Tmemory;
	signal s_address : integer :=0;
	signal s_rng : std_logic_vector(num_bits-1 downto 0);
begin
	abc : entity work.pseudo_random_generator(heavy) 
	generic map(N_BITS => num_bits,
					SEED => semente)
	port map(clock => my_clock,
				enable => my_enable,
				rnd => s_rng);
				
process(my_clock)
begin
case PS is
when s0=>
	if(rising_edge(my_clock)) then
		if (user = '0' and insert='1') then 
			s_memory(s_address) <= write_data;
			s_address <= s_address + 1;
		elsif user='1' then 
			s_memory(s_address) <=s_rng;
			s_address <= s_address + 1;
		end if;
		if (s_memory(7)='0' or s_memory(7)='1' or s_memory(7)='2' or s_memory(7)='3' or s_memory(7)='4' or s_memory(7)='5' or s_memory(7)='6' or s_memory(7)='7' or s_memory(7)='8' or s_memory(7)='9' or s_memory(7)='A' or s_memory(7)='B' or s_memory(7)='C' or s_memory(7)='D' or s_memory(7)='E' or s_memory(7)='F') then
			NS<=s1;
		end if;
	end if;
when s1=>
	if user1='0' then
		s_memory(8) <=  s_memory(0) when (s_memory(0) <s_memory(1)) else s_memory(1);
		s_memory(9) <=  s_memory(1) when (s_memory(0) <s_memory(1)) else s_memory(0);
		s_memory(10) <= s_memory(2) when (s_memory(2) <s_memory(3)) else s_memory(3);
		s_memory(11) <= s_memory(3) when (s_memory(2) <s_memory(3)) else s_memory(2);
		s_memory(12) <= s_memory(4) when (s_memory(4) <s_memory(5)) else s_memory(5);
		s_memory(13) <= s_memory(5) when (s_memory(4) <s_memory(5)) else s_memory(4);
		s_memory(14) <= s_memory(6) when (s_memory(6) <s_memory(7)) else s_memory(7);
		s_memory(15) <= s_memory(7) when (s_memory(6) <s_memory(7)) else s_memory(6);
		if (s_memory(8)<s_memory(9) and  s_memory(9)<s_memory(10) and s_memory(10)<s_memory(11) and s_memory(11)<s_memory(12) and s_memory(12)<s_memory(13) and s_memory(13)<s_memory(14) and s_memory(14)<s_memory(15)) then
			NS<=sf;
		else
			NS<=s2;
		end if;
	else 
		s_memory(8) <=  s_memory(0) when (s_memory(0) >s_memory(1)) else s_memory(1);
		s_memory(9) <=  s_memory(1) when (s_memory(0) >s_memory(1)) else s_memory(0);
		s_memory(10) <= s_memory(2) when (s_memory(2) >s_memory(3)) else s_memory(3);
		s_memory(11) <= s_memory(3) when (s_memory(2) >s_memory(3)) else s_memory(2);
		s_memory(12) <= s_memory(4) when (s_memory(4) >s_memory(5)) else s_memory(5);
		s_memory(13) <= s_memory(5) when (s_memory(4) >s_memory(5)) else s_memory(4);
		s_memory(14) <= s_memory(6) when (s_memory(6) >s_memory(7)) else s_memory(7);
		s_memory(15) <= s_memory(7) when (s_memory(6) >s_memory(7)) else s_memory(6);
		if (s_memory(8)>s_memory(9) and  s_memory(9)>s_memory(10) and s_memory(10)>s_memory(11) and s_memory(11)>s_memory(12) and s_memory(12)>s_memory(13) and s_memory(13)>s_memory(14) and s_memory(14)>s_memory(15)) then
			NS<=sf;
		else
			NS<=s2;
		end if;
	end if;
when s2=>
	if user1='0' then
		s_memory(0) <=  s_memory(8) when (s_memory(8) <s_memory(10)) else s_memory(10);
		s_memory(1) <=  s_memory(10) when (s_memory(8) <s_memory(10)) else s_memory(8);
		s_memory(2) <=  s_memory(9) when (s_memory(9) <s_memory(11)) else s_memory(11);
		s_memory(3) <=  s_memory(11) when (s_memory(9) <s_memory(11)) else s_memory(9);	
		s_memory(4) <=  s_memory(12) when (s_memory(12) <s_memory(14)) else s_memory(12);
		s_memory(5) <=  s_memory(14) when (s_memory(12) <s_memory(14)) else s_memory(14);		
		s_memory(6) <=  s_memory(13) when (s_memory(13) <s_memory(15)) else s_memory(13);
		s_memory(7) <=  s_memory(15) when (s_memory(13) <s_memory(15)) else s_memory(15);
		if (s_memory(0)<s_memory(1) and  s_memory(1)<s_memory(2) and s_memory(2)<s_memory(3) and s_memory(3)<s_memory(4) and s_memory(4)<s_memory(5) and s_memory(5)<s_memory(6) and s_memory(6)<s_memory(7)) then
			NS<=sf;
		else 
			NS<=s3;
		end if;
	else
		s_memory(0) <=  s_memory(8) when (s_memory(8) >s_memory(10)) else s_memory(10);
		s_memory(1) <=  s_memory(10) when (s_memory(8)>s_memory(10)) else s_memory(8);
		s_memory(2) <=  s_memory(9) when (s_memory(9) >s_memory(11)) else s_memory(11);
		s_memory(3) <=  s_memory(11) when (s_memory(9) >s_memory(11)) else s_memory(9);	
		s_memory(4) <=  s_memory(12) when (s_memory(12) >s_memory(14)) else s_memory(12);
		s_memory(5) <=  s_memory(14) when (s_memory(12) >s_memory(14)) else s_memory(14);		
		s_memory(6) <=  s_memory(13) when (s_memory(13) >s_memory(15)) else s_memory(13);
		s_memory(7) <=  s_memory(15) when (s_memory(13) >s_memory(15)) else s_memory(15);
		if (s_memory(0)>s_memory(1) and  s_memory(1)>s_memory(2) and s_memory(2)>s_memory(3) and s_memory(3)>s_memory(4) and s_memory(4)>s_memory(5) and s_memory(5)>s_memory(6) and s_memory(6)>s_memory(7)) then
			NS<=sf;
		else 
			NS<=s3;
		end if;
when s3=>
	if user1='0'then 
		s_memory(8) <= s_memory(0);
		s_memory(9) <= s_memory(1) when (s_memory(1) <s_memory(2)) else s_memory(2);
		s_memory(10) <= s_memory(2) when (s_memory(1) <s_memory(2)) else s_memory(1);
		s_memory(11) <= s_memory(3);
		s_memory(12) <= s_memory(4);
		s_memory(13) <= s_memory(5) when (s_memory(5) <s_memory(6)) else s_memory(6);
		s_memory(14) <= s_memory(6) when (s_memory(5) <s_memory(6)) else s_memory(5);
		s_memory(15) <= s_memory(7);
		if (s_memory(8)<s_memory(9) and  s_memory(9)<s_memory(10) and s_memory(10)<s_memory(11) and s_memory(11)<s_memory(12) and s_memory(12)<s_memory(13) and s_memory(13)<s_memory(14) and s_memory(14)<s_memory(15)) then
			NS<=sf;
		else
			NS<=s4;
		end if;
	else
		s_memory(8) <= s_memory(0);
		s_memory(9) <= s_memory(1) when (s_memory(1) >s_memory(2)) else s_memory(2);
		s_memory(10) <= s_memory(2) when (s_memory(1) >s_memory(2)) else s_memory(1);
		s_memory(11) <= s_memory(3);
		s_memory(12) <= s_memory(4);
		s_memory(13) <= s_memory(5) when (s_memory(5) >s_memory(6)) else s_memory(6);
		s_memory(14) <= s_memory(6) when (s_memory(5) >s_memory(6)) else s_memory(5);
		s_memory(15) <= s_memory(7);
		if (s_memory(8)>s_memory(9) and  s_memory(9)>s_memory(10) and s_memory(10)>s_memory(11) and s_memory(11)>s_memory(12) and s_memory(12)>s_memory(13) and s_memory(13)>s_memory(14) and s_memory(14)>s_memory(15)) then
			NS<=sf;
		else
			NS<=s4;
		end if;
	end if;
when s4=>
	if
		s_memory(0) <=  s_memory(8) when (s_memory(8) <s_memory(12)) else s_memory(12);
		s_memory(1) <=  s_memory(12) when (s_memory(8) <s_memory(12)) else s_memory(8);
		s_memory(2) <=  s_memory(9) when (s_memory(9) <s_memory(13)) else s_memory(13);
		s_memory(3) <=  s_memory(13) when (s_memory(9) <s_memory(13)) else s_memory(9);	
		s_memory(4) <=  s_memory(10) when (s_memory(10) <s_memory(14)) else s_memory(14);
		s_memory(5) <=  s_memory(14) when (s_memory(10) <s_memory(14)) else s_memory(10);		
		s_memory(6) <=  s_memory(11) when (s_memory(11) <s_memory(15)) else s_memory(15);
		s_memory(7) <=  s_memory(15) when (s_memory(11) <s_memory(15)) else s_memory(11);
		if (s_memory(0)<s_memory(1) and  s_memory(1)<s_memory(2) and s_memory(2)<s_memory(3) and s_memory(3)<s_memory(4) and s_memory(4)<s_memory(5) and s_memory(5)<s_memory(6) and s_memory(6)<s_memory(7)) then
			NS<=sf;
		else 
			NS<=s5;
		end if;
	else
		s_memory(0) <=  s_memory(8) when (s_memory(8) >s_memory(12)) else s_memory(12);
		s_memory(1) <=  s_memory(12) when (s_memory(8)>s_memory(12)) else s_memory(8);
		s_memory(2) <=  s_memory(9) when (s_memory(9) >s_memory(13)) else s_memory(13);
		s_memory(3) <=  s_memory(13) when (s_memory(9) >s_memory(13)) else s_memory(9);	
		s_memory(4) <=  s_memory(10) when (s_memory(10) >s_memory(14)) else s_memory(14);
		s_memory(5) <=  s_memory(14) when (s_memory(10) >s_memory(14)) else s_memory(10);		
		s_memory(6) <=  s_memory(11) when (s_memory(11) >s_memory(15)) else s_memory(15);
		s_memory(7) <=  s_memory(15) when (s_memory(11) >s_memory(15)) else s_memory(11);
		if (s_memory(0)>s_memory(1) and  s_memory(1)>s_memory(2) and s_memory(2)>s_memory(3) and s_memory(3)>s_memory(4) and s_memory(4)>s_memory(5) and s_memory(5)>s_memory(6) and s_memory(6)>s_memory(7)) then
			NS<=sf;
		else 
			NS<=s5;
		end if;
	end if;
when s5=>
	if
		s_memory(8) <= s_memory(0);
		s_memory(9) <= s_memory(1) when (s_memory(1)<s_memory(2)) else s_memory(2);
		s_memory(10) <= s_memory(2) when (s_memory(1)<s_memory(2)) else s_memory(1);
		s_memory(11) <= s_memory(3) when (s_memory(3)<s_memory(4)) else s_memory(4);
		s_memory(12) <= s_memory(4) when (s_memory(3)<s_memory(4)) else s_memory(3);
		s_memory(13) <= s_memory(5) when (s_memory(5)<s_memory(6)) else s_memory(6);
		s_memory(14) <= s_memory(6) when (s_memory(5)<s_memory(6)) else s_memory(5);
		s_memory(15) <= s_memory(7);
		NS<=sf;
	else
		s_memory(8) <= s_memory(0);
		s_memory(9) <= s_memory(1) when (s_memory(1)>s_memory(2)) else s_memory(2);
		s_memory(10) <= s_memory(2) when (s_memory(1)>s_memory(2)) else s_memory(1);
		s_memory(11) <= s_memory(3) when (s_memory(3)>s_memory(4)) else s_memory(4);
		s_memory(12) <= s_memory(4) when (s_memory(3)>s_memory(4)) else s_memory(3);
		s_memory(13) <= s_memory(5) when (s_memory(5)>s_memory(6)) else s_memory(6);
		s_memory(14) <= s_memory(6) when (s_memory(5)>s_memory(6)) else s_memory(5);
		s_memory(15) <= s_memory(7);
		NS<=sf;
	end if;
when sf=>
	out8<=s_memory(8);
	out7<=s_memory(9);
	out6<=s_memory(10);
	out5<=s_memory(11);
	out4<=s_memory(12);
	out3<=s_memory(13);
	out2<=s_memory(14);
	out1<=s_memory(15);
end case;
end process;
end heavy;