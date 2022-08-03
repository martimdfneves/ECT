library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.NUMERIC_STD.ALL;

entity counter4_updown is
    Port ( clk      : in STD_LOGIC;     
           reset    : in STD_LOGIC;
           enable   : in STD_LOGIC;
           updown   : in STD_LOGIC;
           count    : out STD_LOGIC_VECTOR(3 downto 0) 
          );
end counter4_updown;

architecture Behavioral of counter4_updown is
    signal s_cnt : unsigned(count'range); -- natural range 0 to 15;
    
begin

    process(clk)
    begin
        if rising_edge(clk) then
            if reset = '1' then
                s_cnt <= (others => '0');
            elsif enable = '1' then
                -- incrementar quando o contador está up
                if updown = '1' then
                    s_cnt <= s_cnt + 1;
                -- decrementar quando o contador está down
                else
                    s_cnt <= s_cnt - 1;
                end if;  
            end if;   
        end if;
    end process;

count <= std_logic_vector(s_cnt);

end Behavioral;