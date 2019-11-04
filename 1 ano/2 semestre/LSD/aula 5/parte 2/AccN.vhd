library IEEE;
use IEEE.STD_LOGIC_1164.all;
use IEEE.NUMERIC_STD.all;

entity AccN is
	generic(N		:		positive := 4);
		port(clk		: in 	std_logic;
   	     reset	: in 	std_logic;
		     enable	: in 	std_logic;
		     dataIn	: in 	std_logic_vector(N-1 downto 0);
		     dataOut: out std_logic_vector(N-1 downto 0));
end AccN;

architecture Structural of AccN is

	signal s_adderOut: std_logic_vector(N-1 downto 0);
	signal s_regOut  : std_logic_vector(N-1 downto 0);
	
begin

	regN:	entity work.RegN(Behavioral)
		generic map(K	=> N)
		port map(clk	 => clk,
					reset  => reset,
					enable => enable,
					dataIn => s_adderOut,
					dataOut=> s_regOut);

	adderN: entity work.AdderN(Behavioral)
		generic map(K	=> N)
		port map(operand0		=> dataIn,
					operand1		=> s_regOut,
					result		=> s_adderOut);
					
	dataOut <= std_logic_vector(s_regOut);
			
end Structural;
					