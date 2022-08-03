library IEEE;
use IEEE.STD_LOGIC_1164.ALL;

entity wrapper_2 is
    Port ( clk : in STD_LOGIC;
           sw  : in STD_LOGIC_VECTOR(15 downto 0);
           an : out STD_LOGIC_VECTOR (7 downto 0);
           seg: out STD_LOGIC_VECTOR (6 downto 0);
           dp : out STD_LOGIC);
end wrapper_2;

architecture Behavioral of wrapper_2 is
    signal s_en : std_logic;
    signal s_sel_s : std_logic_vector(7 downto 0); 
    signal s_sel_p : std_logic_vector(7 downto 0); 
    
begin

pulseGen: entity work.pulseGenerator(Behavioral)
	generic map(MAX => 125_000)
	port map(clk => clk,      
           reset => '0',
           pulse => s_en);
           
s_sel_s <=  sw(7 downto 0); 
s_sel_p(7 downto 0) <=  sw(15 downto 8);

nexysDriver: entity work.Nexys4DispDriver(Behavioral)
	port map(clk => clk,
         en => s_en,
         sel_s => s_sel_s,
           sel_p => s_sel_p,
           dig0 => "0010",
           dig1 => "0010",
           dig2 => "0000",
           dig3 => "0010",
           dig4 => "0011",
           dig5 => "0000",
           dig6 => "0111",
           dig7 => "0010",   
           an => an,
           seg => seg,
           dp => dp);

end Behavioral;
