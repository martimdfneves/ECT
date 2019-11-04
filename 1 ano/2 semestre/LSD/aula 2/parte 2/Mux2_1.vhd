library IEEE;
use IEEE.STD_LOGIC_1164.all;

entity Mux2_1 is
	port(sel    : in std_logic;
		  input0 : in std_logic;
		  input1 : in std_logic;
		  muxOut : out std_logic);
end Mux2_1;

architecture manelinho of Mux2_1 is
	signal s_and0out, s_and1out : std_logic;
begin
	s_and0out <= not sel and input0;
	s_and1out <= sel and input1;		
	muxOut    <= s_and0out or s_and1out;
end manelinho;