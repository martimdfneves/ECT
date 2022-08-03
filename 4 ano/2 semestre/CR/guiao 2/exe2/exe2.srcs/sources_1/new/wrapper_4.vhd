library IEEE;
use IEEE.STD_LOGIC_1164.ALL;

entity wrapper_4 is
    Port ( clk  : in STD_LOGIC;
           btnC : in STD_LOGIC; -- reset = btnC
           btnU : in STD_LOGIC; -- up = btnU
           btnD : in STD_LOGIC; -- down = btnD
           btnR : in STD_LOGIC; -- change state = btnR
           led  : out STD_LOGIC_VECTOR (3 downto 0);
           an   : out STD_LOGIC_VECTOR (7 downto 0);
           seg  : out STD_LOGIC_VECTOR (6 downto 0));
end wrapper_4;

architecture Behavioral of wrapper_4 is
    -- button signals
    signal s_reset, s_up, s_down, s_changeState : std_logic; 
    -- multiplexed enable signal dependent on state
	signal s_en : std_logic; 
	-- multiplexed counter increment direction
	signal s_ud : std_logic; 
	-- mode of operation
	signal s_state : std_logic; 
	-- types of enable for each state, en1 for counter mode and en2 for set mode (click detection)
	signal s_en1, s_en2: std_logic; 
	-- increment direction for set mode
	signal s_ud2: std_logic; 
	-- multiplexed enable for segments (counter mode | set mode)
	signal s_segEn: std_logic_vector(7 downto 0); 
	-- pulse to control blinking for set mode (toggle blink at 2Hz frequency)
	signal s_pulseBlink: std_logic; 
	-- multiplexed constant value for set mode, dependent on s_blink, in order to get blinking effect
	signal s_segValue: std_logic_vector(7 downto 0); 
	-- toggling signal stored in a toggle flipflop
	signal s_blink: std_logic; 
	-- Current counter value
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
        if s_changeState = '1' then
            s_state <= not s_state;
        end if;
    end if;
end process;

process(clk)
begin
    if (rising_edge(clk)) then
        if s_pulseBlink = '1' then
            s_blink <= not s_blink;
        end if;
    end if;
end process;

s_segValue <= x"FE" when s_blink = '1' else x"FE";

s_segEn <= x"FE" when s_state = '1' else s_segValue;

led <= s_count;

s_en2 <= s_up or s_down;

s_ud2 <= '1' when s_up = '1' else '0';

s_ud <= '1' when s_state = '1' else s_ud2;

s_en <= s_en1 when s_state = '1' else s_en2; 

counter     : entity work.counter4_updown(Behavioral)
                    port map(clk    => clk,
                             reset  => s_reset,
                             enable => s_en,
                             updown => s_ud,
                             count  => s_count);
             
pulse_2Hz   : entity work.divisor1Hz(Behavioral)
	           generic map(MAX => 50_000_000)
	               port map(clk    => clk,
                            reset  => s_reset,
                            pulse  => s_pulseBlink);

pulse_1Hz   : entity work.divisor1Hz(Behavioral)
	               port map(clk    => clk,
                            reset  => s_reset,
                            pulse  => s_en1);
                        
displays    : entity work.Hex2Seg(Behavioral)
	           port map(hex     => s_count,
                        en_L    => s_segEn,
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
        
debouncerR  : entity work.DebounceUnit(Behavioral)
	           port map(refClk     => clk,
                        dirtyIn    => btnR,
                        pulsedOut  => s_changeState);

end Behavioral;