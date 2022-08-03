library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.NUMERIC_STD.ALL;

entity counter4_up is
    Port ( clk      : in STD_LOGIC;     
           reset    : in STD_LOGIC;
           enable   : in STD_LOGIC;
           count    : out STD_LOGIC_VECTOR(3 downto 0) 
          );
end counter4_up;

architecture Behavioral of counter4_up is
    signal s_cnt : unsigned(count'range); -- natural range 0 to 15;
    
begin

    process(clk)
    begin
        if rising_edge(clk) then
            if reset = '1' then
                s_cnt <= (others => '0');
            elsif enable = '1' then
                s_cnt <= s_cnt + 1;
            end if;   
        end if;
    end process;

count <= std_logic_vector(s_cnt);

end Behavioral;
