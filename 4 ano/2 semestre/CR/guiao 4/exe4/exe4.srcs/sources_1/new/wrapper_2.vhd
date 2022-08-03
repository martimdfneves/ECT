library IEEE;
use IEEE.STD_LOGIC_1164.ALL;

entity wrapper_2 is
    Port ( clk : in STD_LOGIC;
           btnC : in STD_LOGIC;
           btnR : in STD_LOGIC;
           btnU : in STD_LOGIC;
           btnD : in STD_LOGIC;
           dp   : out STD_LOGIC;
           an   : out STD_LOGIC_VECTOR(7 downto 0);
           seg  : out STD_LOGIC_VECTOR(6 downto 0);
           led  : out STD_LOGIC_VECTOR(15 downto 0)
    );
end wrapper_2;

architecture Behavioral of wrapper_2 is

    signal s_rst, s_play : std_logic ; -- user controls for set and pause and unpause
    signal s_out0, s_out1, s_out2, s_out3 : std_logic_vector(3 downto 0); -- independent digits
    signal s_1hz, s_2hz, s_4hz, s_800hz : std_logic; -- signals with various frequencies pulses
    signal s_counter_en, s_set, s_ud, s_counter_end : std_logic; -- helper signals for counter
    signal s_dg : std_logic_vector(3 downto 0); -- set mode bit mask
    
    signal s_btnC, s_btnR : std_logic; -- debounce signals
    signal s_pt_en, s_dg_en : std_logic; -- blinking signals for point and digits
    signal s_sel_p, s_sel_s : std_logic_vector(7 downto 0); -- enable signals for point and digits toggled by the above
begin
   
    led(0) <= s_counter_end;
    s_sel_p <= "000" & s_pt_en & "0000" when s_play = '1' else
    "00010000";
    
    s_sel_s <= "00" & (not s_dg(3) or s_dg_en) & (not s_dg(2) or s_dg_en) & (not s_dg(1) or s_dg_en) & (not s_dg(0) or s_dg_en) & "00" when s_set = '1' else
    "00111100";

    debouncerR : Entity work.debounceUnit(Behavioral)
         Port map(refClk => clk,
        dirtyIn => btnR,
        pulsedOut => s_btnR);
         
    debouncerC : Entity work.debounceUnit(Behavioral)
         Port map(refClk => clk,
        dirtyIn => btnC,
        pulsedOut => s_btnC);

    controlunit : Entity work.controlUnit(Behavioral)
         Port map(clk => clk,
           reset => '0',
           play => s_btnC,
           counter_end => s_counter_end, 
           rst => s_rst,
           en => s_play,
           set => s_set,
           dg => s_dg);
 
    counter : Entity work.counter4_digits(Behavioral)
         Port map(clk => clk,
           rst => s_btnR,
           en => s_counter_en,
           set => s_set,
           updown => s_ud,
           dg => s_dg,
           c_out0 => s_out0,
           c_out1 => s_out1,
           c_out2 => s_out2,
           c_out3 => s_out3,
           c_end => s_counter_end);
           
     s_counter_en <= (s_1hz and s_play) when s_set = '0' else (s_2hz and (btnU or btnD));
     s_ud <= btnU;
     
     pt_blink : process(clk)
     begin
        if rising_edge(clk) then
            if s_2hz = '1' then
                s_pt_en <= not s_pt_en;
            end if;
            
            if s_4hz = '1' then
                s_dg_en <= not s_dg_en;
            end if;
        end if;
     end process;
      
     segcontroler : Entity work.Nexys4DispDriver(Behavioral)
         Port map(clk => clk,
           en  => s_800hz, 
           sel_s => s_sel_s,
           sel_p => s_sel_p,
           dig0 => x"F",
           dig1 => x"F",
           dig2 => s_out0,
           dig3 => s_out1,
           dig4 => s_out2,
           dig5 => s_out3,
           dig6 => x"F",
           dig7 => x"F",    
           an   => an,
           seg => seg,
           dp => dp);
           
     pulse1Hz : Entity work.pulseGenerator(Behavioral)
         generic map(MAX => 100_000_000)
         Port map(clk => clk,     
           reset => s_btnR,
           pulse => s_1hz);
           
     pulse800Hz : Entity work.pulseGenerator(Behavioral)
         generic map(MAX => 125_000)
         Port map(clk => clk,     
           reset => s_btnR,
           pulse => s_800hz);
     
     pulse2Hz : Entity work.pulseGenerator(Behavioral)
         generic map(MAX => 50_000_000)
         Port map(clk => clk,     
           reset => s_btnR,
           pulse => s_2hz);
           
     pulse4Hz : Entity work.pulseGenerator(Behavioral)
         generic map(MAX => 25_000_000)
         Port map(clk => clk,     
           reset => s_btnR,
           pulse => s_4hz);
		   
end Behavioral;