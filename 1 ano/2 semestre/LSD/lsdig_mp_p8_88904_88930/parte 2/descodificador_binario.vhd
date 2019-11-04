library ieee ;
use ieee.std_logic_1164.all ;
use ieee.std_logic_unsigned.all ;

entity descodificador_binario is
    port ( binario_in   : in  std_logic_vector (5 downto 0) ;
           dezenas  		: out std_logic_vector (3 downto 0) ;
           unidades 		: out std_logic_vector (3 downto 0) ) ;
end descodificador_binario ;

architecture hardware of descodificador_binario is
begin
   process ( binario_in )
   variable fonte     : std_logic_vector (4 downto 0) ;
   variable resultado_bcd     : std_logic_vector (7 downto 0) ;
	begin
	resultado_bcd         := (others => '0') ;
	resultado_bcd(0) 		 := binario_in(5) ;
	fonte           		 := binario_in(4 downto 0) ;

	for i in fonte'range loop
		 
	  if resultado_bcd(3 downto 0) > "0100" then
			resultado_bcd(3 downto 0) := resultado_bcd(3 downto 0) + "0011" ;
	  end if ;
	  
	  if resultado_bcd(7 downto 4) > "0100" then
			resultado_bcd(7 downto 4) := resultado_bcd(7 downto 4) + "0011" ;
	  end if ;

	  resultado_bcd   := resultado_bcd(6 downto 0) & fonte(fonte'left) ; 
	  fonte := fonte(fonte'left - 1 downto fonte'right) & '0' ; 
			  
	 end loop ;
	 
    dezenas  <= resultado_bcd(7  downto 4) ;
    unidades <= resultado_bcd(3  downto 0) ;
	 
	end process ;
end hardware ;