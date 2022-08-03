library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.NUMERIC_STD.ALL;

library IEEE;
use IEEE.STD_LOGIC_1164.ALL;

entity wrapper_3 is
    Port ( clk  : in STD_LOGIC;
           btnC : in STD_LOGIC; -- reset = btnC
           btnU : in STD_LOGIC; -- up = btnU
           btnD : in STD_LOGIC; -- down = btnD
           led  : out STD_LOGIC_VECTOR (3 downto 0);
           an   : out STD_LOGIC_VECTOR (7 downto 0);
           seg  : out STD_LOGIC_VECTOR (6 downto 0));
end wrapper_3;

architecture Behavioral of wrapper_3 is
    signal s_reset, s_up, s_down : std_logic; 
	signal s_en : std_logic; 
	-- multiplexed counter increment direction
	signal s_updown : std_logic; 
	signal s_count : std_logic_vector(3 downto 0); 

begin

process(clk)
begin
    if (rising_edge(clk)) then
        s_reset <= btnC;
    end if;
end process;

process(clk)
begin
    if (rising_edge(clk)) then
        if (btnU = '1') then
            s_up <= btnU;
        elsif (btnD = '1') then
            s_down <= btnD;
        end if;
    end if;
end process;

led <= s_count;

s_updown <= s_up or s_down;

counter     : entity work.counter4_updown(Behavioral)
                    port map(clk    => clk,
                             reset  => s_reset,
                             enable => s_en,
                             updown => s_updown,
                             count  => s_count);
             
displays    : entity work.Hex2Seg(Behavioral)
	           port map(hex     => s_count,
                        en_L    => x"FE",
                        an_L    => an,
                        seg_L   => seg);
          
debouncerU  : entity work.DebounceUnit(Behavioral)
	           port map(refClk     => clk,
                        dirtyIn    => btnU,
                        pulsedOut  => s_up);
        
debouncerD  : entity work.DebounceUnit(Behavioral)
	           port map(refClk     => clk,
                        dirtyIn    => btnD,
                        pulsedOut  => s_down);
        
end Behavioral;