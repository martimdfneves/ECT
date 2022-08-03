library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.NUMERIC_STD.ALL;

entity Nexys4DispDriver is
    Port ( clk : in STD_LOGIC;
           en  : in STD_LOGIC;
           sel_s : in STD_LOGIC_VECTOR(7 downto 0);
           sel_p : in STD_LOGIC_VECTOR(7 downto 0);
           dig0, dig1, dig2, dig3, dig4, dig5, dig6, dig7 : in STD_LOGIC_VECTOR(3 downto 0);    
           an : out STD_LOGIC_VECTOR(7 downto 0);
           seg : out STD_LOGIC_VECTOR(6 downto 0);
           dp : out STD_LOGIC);
           
end Nexys4DispDriver;

architecture Behavioral of Nexys4DispDriver is
    signal s_count : unsigned(2 downto 0);
    signal s_state : std_logic_vector(2 downto 0);
    signal s_seg: std_logic_vector(3 downto 0);
    signal s_cycle_an: std_logic;
begin

counter3bit: process(clk)
             begin
                    if rising_edge(clk) then
                        if en = '1' then
                            s_count <= s_count+1;
                        end if;
                    end if;
             end process;
s_state <= std_logic_vector(s_count);

an <= x"FE" when s_count = 0 else
      x"FD" when s_count = 1 else
      x"FB" when s_count = 2 else
      x"F7" when s_count = 3 else
      x"EF" when s_count = 4 else
      x"DF" when s_count = 5 else
      x"BF" when s_count = 6 else
      x"7F";
              
s_seg <= dig0 when s_count = 0 else
        dig1 when s_count = 1 else
        dig2 when s_count = 2 else
        dig3 when s_count = 3 else
        dig4 when s_count = 4 else
        dig5 when s_count = 5 else
        dig6 when s_count = 6 else
        dig7;    
        
seg <=  "1111111" when (s_cycle_an = '0') else
        "1111001" when (s_seg = "0001") else  --1                 
        "0100100" when (s_seg = "0010") else  --2         
        "0110000" when (s_seg = "0011") else  --3                 
        "0011001" when (s_seg = "0100") else  --4                 
        "0010010" when (s_seg = "0101") else  --5                 
        "0000010" when (s_seg = "0110") else  --6                 
        "1111000" when (s_seg = "0111") else  --7                 
        "0000000" when (s_seg = "1000") else  --8                 
        "0010000" when (s_seg = "1001") else  --9                 
        "0001000" when (s_seg = "1010") else  --A                 
        "0000011" when (s_seg = "1011") else  --b                 
        "1000110" when (s_seg = "1100") else  --C                 
        "0100001" when (s_seg = "1101") else  --D                 
        "0000110" when (s_seg = "1110") else  --E                 
        "0001110" when (s_seg = "1111") else  --F                 
        "1000000";                            --0                        
         
dp <= not sel_p(0) when s_count = 0 else
      not  sel_p(1) when s_count = 1 else
      not  sel_p(2) when s_count = 2 else
      not  sel_p(3) when s_count = 3 else
      not  sel_p(4) when s_count = 4 else
      not  sel_p(5) when s_count = 5 else
      not  sel_p(6) when s_count = 6 else
      not  sel_p(7);
      
               
s_cycle_an <=  sel_s(0) when s_count = 0 else
        sel_s(1) when s_count = 1 else
        sel_s(2) when s_count = 2 else
        sel_s(3) when s_count = 3 else
        sel_s(4) when s_count = 4 else
        sel_s(5) when s_count = 5 else
        sel_s(6) when s_count = 6 else
        sel_s(7);
      
end Behavioral;