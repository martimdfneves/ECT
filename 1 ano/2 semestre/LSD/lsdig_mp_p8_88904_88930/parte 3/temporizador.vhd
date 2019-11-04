library IEEE;
use IEEE.STD_LOGIC_1164.all;
use IEEE.NUMERIC_STD.all;
use ieee.std_logic_unsigned.all;

entity temporizador is
	port( relogio 			: in std_logic;
			reset				: in std_logic;
			min_inicial		: in std_logic_vector(5 downto 0);
			sec_inicial		: in std_logic_vector(5 downto 0);
			sec				: out std_logic_vector(5 downto 0);
			min				: out std_logic_vector(5 downto 0);
			relogio_out 	: out std_logic);

end temporizador;

architecture hardware of temporizador is
signal mim_min    	 : integer:=59;
signal sec_sec   		 : integer:=59;
signal un_sec_ini     : unsigned(5 downto 0);
signal un_min_ini     : unsigned(5 downto 0);

begin	
	process(reset,relogio,min_inicial,sec_inicial)
	begin	
	un_sec_ini <= unsigned(sec_inicial);
	un_min_ini <= unsigned(min_inicial);
	
	if (rising_edge(relogio)) then		
		if(sec_sec = 0) then						
			if (mim_min>0) then
				mim_min <= mim_min - 1;
				sec_sec <= 59;
			end if;
		else
			sec_sec <= sec_sec-1;
		end if;	
		if (reset='0') then
			if (to_integer(un_sec_ini)>59 and to_integer(un_min_ini)<59) then
				sec_sec <= 59;
				mim_min <= to_integer(un_min_ini);
			elsif (to_integer(un_sec_ini)<59 and to_integer(un_min_ini)>59) then
				mim_min <= 59;
				sec_sec <= to_integer(un_sec_ini);
			elsif (to_integer(un_sec_ini)>59 and to_integer(un_min_ini)>59) then
				mim_min <= 59;
				sec_sec <= 59;
			else
			mim_min <= to_integer(un_min_ini);
			sec_sec <= to_integer(un_sec_ini);
			end if;
		end if;
	end if;
	
	if(mim_min=0 and sec_sec=0)then
		relogio_out<=relogio;
	else
		relogio_out<='0';
	end if;
	
	sec <= std_logic_vector(to_unsigned(sec_sec,6));
	min <= std_logic_vector(to_unsigned(mim_min,6));	
	end process;

end hardware;