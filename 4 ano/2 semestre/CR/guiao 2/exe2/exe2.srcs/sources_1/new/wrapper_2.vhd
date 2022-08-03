library IEEE;
use IEEE.STD_LOGIC_1164.ALL;

entity wrapper_2 is
    Port ( clk  : in STD_LOGIC;
           btnC : in STD_LOGIC; 
           led  : out STD_LOGIC_VECTOR (3 downto 0);
           an   : out STD_LOGIC_VECTOR (7 downto 0);
           seg  : out STD_LOGIC_VECTOR (6 downto 0));
end wrapper_2;

architecture Behavioral of wrapper_2 is
    signal s_reset : std_logic; 
	signal s_en : std_logic; 
	signal s_count : std_logic_vector(3 downto 0); 

begin

process(clk)
begin
    if (rising_edge(clk)) then
        s_reset <= btnC;
    end if;
end process;

led <= s_count;

counter     : entity work.counter4_up(Behavioral)
                    port map(clk    => clk,
                             reset  => s_reset,
                             enable => s_en,
                             count  => s_count);

pulse_1Hz   : entity work.divisor1Hz(Behavioral)
	               port map(clk    => clk,
                            reset  => s_reset,
                            pulse  => s_en);
                        
displays    : entity work.Hex2Seg(Behavioral)
	           port map(hex     => s_count,
                        en_L    => x"FE",
                        an_L    => an,
                        seg_L   => seg);

end Behavioral;