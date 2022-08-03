library IEEE;
use IEEE.STD_LOGIC_1164.ALL;

entity controlUnit is
    Port ( clk : in STD_LOGIC;
           reset : in STD_LOGIC;
           play : in STD_LOGIC;
           counter_end : in STD_LOGIC;
           rst : out STD_LOGIC;
           en : out std_logic;
           set  : out STD_LOGIC;
           dg : out std_logic_vector(3 downto 0)
    );
end controlUnit;

architecture Behavioral of controlUnit is

    type TState is (D0, D1, D2, D3, CNT, PS);
    signal pState, nState: TState := PS;
    
begin

sync_proc : process(clk)
begin
    if (rising_edge(clk)) then
        pState <= nState;
     end if;
end process;

comb_proc : process(pState, play)
begin
    nState <= pState;
    
    case pState is
        when D0 => -- State D0
            en <= '0';
            set <= '1';
            dg <= "1000";
            
            if reset = '1' then
                nState <= D1;
            end if;
        when D1 => -- State D1
            en <= '0';
            set <= '1';
            dg <= "0100";
            
            if reset = '1' then
                nState <= D2;
            end if;
        when D2 => -- State D2
            en <= '0';
            set <= '1';
            dg <= "0010";
            
            if reset = '1' then
                nState <= D3;
            end if;
        when D3 => -- State D3
            en <= '0';
            set <= '1';
            dg <= "0001";
            
            if reset = '1' then
                nState <= CNT;
            end if;
        when CNT =>
            en <= '1';
            set <= '0';
            dg <= "1111";
            
            if reset = '1' then
                nState <= D0;
            elsif play = '1' then
                nState <= PS;
            elsif counter_end = '1' then
                nState <= PS;
            end if;
        when PS =>
            en <= '0';
            set <= '0';
            dg <= "0000";
            
            if play = '1' then
                nState <= CNT;
            elsif reset = '1' then
                nState <= D0;
            end if;
            
        when others =>
            nState <= PS;
     end case;
 end process;
                
end Behavioral;