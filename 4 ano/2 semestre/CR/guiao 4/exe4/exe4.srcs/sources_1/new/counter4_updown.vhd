library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.NUMERIC_STD.ALL;

entity counter4_updown is
    Generic(MAX: natural := 10);
    Port ( clk : in STD_LOGIC;
           rst: in STD_LOGIC;
           updown : in STD_LOGIC;
           en : in STD_LOGIC;
           c_out : out STD_LOGIC_VECTOR(3 downto 0);
           c_end : out STD_LOGIC 
          );
end counter4_updown;

architecture Behavioral of counter4_updown is

    signal s_cnt : unsigned(3 downto 0) := TO_UNSIGNED(MAX-1, 4);
    signal s_end : std_logic;
    
begin
    c_end <= s_end;

    process(clk)
    begin
    if rising_edge(clk) then
        if rst = '1' then
            s_cnt <= TO_UNSIGNED(MAX-1, 4);
        elsif en = '1' then
            s_end <= '0';
            if updown = '1' then
                if s_cnt = MAX-1 then
                    s_end <= '1';
                    s_cnt <= TO_UNSIGNED(0, 4);
                else
                    s_cnt <= s_cnt + 1;
                end if;
            else
                if s_cnt = 1 then
                    s_end <= '1';
                end if;
                if s_cnt = 0 then
                    s_cnt <= TO_UNSIGNED(MAX-1, 4);
                else
                    s_cnt <= s_cnt - 1;
                end if;
            end if;  
        end if;   
    end if;
end process;

c_out <= std_logic_vector(s_cnt);

end Behavioral;