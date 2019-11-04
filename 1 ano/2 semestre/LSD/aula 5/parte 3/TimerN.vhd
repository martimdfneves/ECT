library IEEE;
use IEEE.STD_LOGIC_1164.all;
use IEEE.NUMERIC_STD.all;

entity TimerN is
	generic(N	 :		positive := 4);
	port(clk		 : in	std_logic;
		  enable  : in	std_logic;
		  reset	 : in	std_logic;
		  start	 : in	std_logic;
		  timerOut: out std_logic);
end TimerN;

architecture Behavioral of TimerN is

	signal s_counter : natural;
	
begin
	
	process(clk)
	begin
		if(rising_edge(clk)) then
			if(reset = '1') then
				s_counter <= 0;
				timerOut <= '0';
			elsif(enable = '1') then
				if(s_counter = 0 and start = '1') then
					s_counter <= s_counter + 1;
					timerOut <= '1';
				elsif(s_counter < N and s_counter > 0) then
					s_counter <= s_counter + 1;
					timerOut <= '1';
				elsif(s_counter = N) then
					s_counter <= 0;
					timerOut <= '0';
				end if;
			end if;
		end if;
	end process;
	
end Behavioral;
		
			