library ieee;
use ieee.std_logic_1164.all;
use ieee.numeric_std.all;
library work;
use work.DisplayUnit_pkg.all; 

entity instructionMemory is
	generic(ADDR_BUS_SIZE : positive := 6);
	port( address : in std_logic_vector(ADDR_BUS_SIZE-1 downto 0);
			readData : out std_logic_vector(31 downto 0));
end instructionMemory;

architecture Behavioral of instructionMemory is
	constant NUM_WORDS : positive := (2 ** ADDR_BUS_SIZE );
	subtype TData is std_logic_vector(31 downto 0);
	type TMemory is array(0 to NUM_WORDS - 1) of TData;
	constant s_memory : TMemory := (x"2002001a",		-- addi $2, $0, 0x1a
											  x"2003fff9",		--	addi $3, $0, 7
											  x"00432020",		--	add  $4, $2, $3
											  x"00432822",		-- sub  $5, $2, $3
											  x"00433024",		-- and  $6, $2, $3
											  x"00433825",		-- or   $7, $2, $3
											  x"00434027",		-- nor  $8, $2, $3
											  x"00434826",		-- xor  $9, $2, $3
											  x"0043502a",		-- slt  $10, $2, $3
											  x"28ebfff9",		-- slti $11, $7, -7
											  x"292cffe7",		-- slti $12, $9, -25
											  others => x"00000000");
begin
	readData <= s_memory(to_integer(unsigned(address)));
	DU_IMaddr	 <= address;
	DU_IMdata    <= s_memory(to_integer(unsigned(DU_IMaddr)));
end Behavioral;