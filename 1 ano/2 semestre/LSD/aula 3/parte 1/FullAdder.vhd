library IEEE;
use IEEE.STD_LOGIC_1164.all;
entity FullAdder is
port(a, b, cin : in std_logic;
s, cout : out std_logic);
end FullAdder;
architecture Behavioral of FullAdder is
signal s_signal, c1_signal, c2_signal : std_logic;
begin
s_signal  <= a xor b;
c1_signal <= s_signal and cin;
c2_signal <= a and b;
s <= s_signal xor cin;
cout <= c1_signal or c2_signal;
end Behavioral;