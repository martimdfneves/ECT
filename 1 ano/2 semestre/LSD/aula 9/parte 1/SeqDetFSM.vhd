library IEEE;
use IEEE.STD_LOGIC_1164.all;

entity SeqDetFSM is
	port(reset : in std_logic;
		  clock_50 : in std_logic;
		  x_in  : in std_logic;
		  y_out : out std_logic);
end SeqDetFSM;

architecture MealyArch of SeqDetFSM is
type state is (A, B, C, D, E);
signal PS, NS : state;
begin
	sync_proc: process(clock_50)
	begin
		if (rising_edge(clock_50)) then
			if (reset = '1') then
				PS <= A;
			else
				PS <= NS;
			end if;
		end if;
	end process;
	
	comb_proc : process(PS, x_in)
	begin
	y_out <= '0'; 
	case PS is
	when A =>
	if (x_in = '1') then NS <= B;
	else NS <= A;
	end if;
	when B =>
	if (x_in = '0') then NS <= C;
	else NS <= B;
	end if;
	when C =>
	if (x_in = '0') then NS <= D;
	else NS <= B;
	end if;
	when D =>
	if (x_in = '1') then NS <= E;
	y_out <= '1'; 
	else NS <= A;
	end if;
	when E =>
	if (x_in = '1') then NS <= B;
	else NS <= C;
	end if;
	when others => 
	NS <= A;
	end case;
	end process;
	
end MealyArch;