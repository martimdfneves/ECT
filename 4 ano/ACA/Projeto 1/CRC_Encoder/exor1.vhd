library IEEE;
use IEEE.STD_LOGIC_1164.all;
Entity exor IS
	PORT(	x0,x1 : IN STD_LOGIC;
			y: OUT STD_LOGIC
	);

END exor;

ARCHITECTURE logicFUNCTION of exor IS
BEGIN
	y<= x0 xor x1 AFTER 2ns;
END logicFUNCTION;