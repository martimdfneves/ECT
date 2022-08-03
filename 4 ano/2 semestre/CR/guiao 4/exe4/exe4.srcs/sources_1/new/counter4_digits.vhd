library IEEE;
use IEEE.STD_LOGIC_1164.ALL;

entity counter4_digits is
    Port ( clk : in STD_LOGIC;
           rst: in STD_LOGIC;
           en: in STD_LOGIC; 
           set : in std_logic;
           updown : in std_logic;
           dg : in std_logic_vector(3 downto 0);
           c_out0 : out STD_LOGIC_VECTOR(3 downto 0);
           c_out1 : out STD_LOGIC_VECTOR(3 downto 0);
           c_out2: out STD_LOGIC_VECTOR(3 downto 0);
           c_out3 : out STD_LOGIC_VECTOR(3 downto 0);
           c_end: out STD_LOGIC
           );
end counter4_digits;

architecture Behavioral of counter4_digits is
    
    signal s_c_end0, s_c_end1, s_c_end2, s_c_end3 : std_logic;
    signal en0, en1, en2, en3: std_logic;
    
begin

    s0 : Entity work.counter4_updown(Behavioral)
         Port map(clk => clk,
           rst => rst,
           updown => updown,
           en => en0,
           c_out => c_out0,
           c_end => s_c_end0);
        
    s1 : Entity work.counter4_updown(Behavioral)
         Generic map(MAX => 6)
         Port map(clk => clk,
           rst => rst,
           updown => updown,
           en => en1,
           c_out => c_out1,
           c_end => s_c_end1);
        
    m0 : Entity work.counter4_updown(Behavioral)
         Port map(clk => clk,
           rst => rst,
           updown => updown,
           en => en2,
           c_out => c_out2,
           c_end => s_c_end2);
        
    m1 : Entity work.counter4_updown(Behavioral)
         Generic map(MAX => 6)
         Port map(clk => clk,
           rst => rst,
           updown => updown,
           en => en3,
           c_out => c_out3,
           c_end => s_c_end3);
                    
    c_end <=  s_c_end0 and s_c_end1 and s_c_end2 and s_c_end3;
    en0 <= en when set = '0' else (dg(0) and en);
    en1 <= (s_c_end0 and en) when set = '0' else (dg(1) and en);
    en2 <= (s_c_end1 and s_c_end0 and en) when set = '0' else (dg(2) and en);
    en3 <= (s_c_end2 and s_c_end1 and s_c_end0 and en) when set = '0' else (dg(3) and en);

end Behavioral;