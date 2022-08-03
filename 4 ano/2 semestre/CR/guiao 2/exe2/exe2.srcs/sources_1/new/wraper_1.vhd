library IEEE;
use IEEE.STD_LOGIC_1164.ALL;

entity wrapper_1 is
    Port ( clk  : in STD_LOGIC;
           btnC : in STD_LOGIC; -- reset = btnC
           led  : out STD_LOGIC_VECTOR (3 downto 0));
end wrapper_1;

architecture Behavioral of wrapper_1 is
    signal s_reset : std_logic; 
	signal s_en : std_logic; 

begin

process(clk)
begin
    if (rising_edge(clk)) then
        s_reset <= btnC;
    end if;
end process;

counter     : entity work.counter4_up(Behavioral)
                    port map(clk    => clk,
                             reset  => s_reset,
                             enable => s_en,
                             count  => led);
             
pulse_1Hz   : entity work.divisor1Hz(Behavioral)
	               port map(clk    => clk,
                            reset  => s_reset,
                            pulse  => s_en);


end Behavioral;
