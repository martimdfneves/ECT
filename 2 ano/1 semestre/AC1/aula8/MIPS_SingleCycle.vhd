---------------------------------------------------------------------------
-- University of Aveiro - DETI
-- "Computer Architecture I" course (Practical classes)
-- 
-- MIPS single-cycle datapath
---------------------------------------------------------------------------
library ieee;
use ieee.std_logic_1164.all;

library work;
use work.DisplayUnit_pkg.all;
use work.MIPS_pkg.all;

entity MIPS_SingleCycle is
	port(	CLOCK_50 : in std_logic;
			KEY  		: in std_logic_vector(3 downto 0);
			SW   		: in std_logic_vector(17 downto 0);
			LEDR  	: out std_logic_vector(17 downto 0);
			LEDG  	: out std_logic_vector(8 downto 0);
			HEX0  	: out std_logic_vector(6 downto 0);
			HEX1  	: out std_logic_vector(6 downto 0);
			HEX2  	: out std_logic_vector(6 downto 0);
			HEX3  	: out std_logic_vector(6 downto 0);
			HEX4  	: out std_logic_vector(6 downto 0);
			HEX5  	: out std_logic_vector(6 downto 0);
			HEX6  	: out std_logic_vector(6 downto 0);
			HEX7  	: out std_logic_vector(6 downto 0));
end MIPS_SingleCycle;

architecture Shell of MIPS_SingleCycle is
-- Data signals
	signal sd_readData1, s_writeData : std_logic_vector(31 downto 0);
	
-- Control signals (generated by the control unit)
	signal sc_RegDst : std_logic;

-- Signals related to the instruction code
	signal si_instr : std_logic_vector(31 downto 0);

-- Other signals
	signal s_clk : std_logic;
	signal s_pc : std_logic_vector(31 downto 0);
	signal s_jaddr : std_logic_vector(25 downto 0);
	signal s_instruct_addr, s_offset, s_rs_data, s_rt_data : std_logic_vector(31 downto 0); 
	signal s_extendImm, s_ALU_in2, s_ALUout, s_memOut : std_logic_vector(31 downto 0);	
	signal s_regWrite ,s_ALUscr, s_zero, s_memToReg : std_logic;
	signal s_dstReg, s_rs_readAddr, s_rt_readAddr : std_logic_vector(4 downto 0);
	signal s_ALUop	: std_logic_vector(1 downto 0);
	signal s_funct : std_logic_vector(5 downto 0);
	signal s_ALUaction : std_logic_vector(2 downto 0);
	
begin

-- PC Update
pcupd:	entity work.PCupdate(Behavioral)	
			port map(clk		=> s_clk,
						reset		=> not KEY(1),
						branch	=> '0',
						jump		=> '0',
						zero		=> '0',
						offset32	=> s_offset,
						jAddr26	=> s_jaddr,
						pc			=> s_pc);

-- Instruction Memory
instmem:	entity work.InstructionMemory(Behavioral)
			generic map(ADDR_BUS_SIZE => ROM_ADDR_SIZE)
			port map(address		=> s_pc(7 downto 2),
						readData		=> si_instr);

-- Splitter
spliter:	entity work.InstrSplitter(Behavioral)
			port map(instruction		=> si_instr,
						opcode	=> si_instr(31 downto 26),
						rs			=> si_instr(25 downto 21),
						rt			=> si_instr(20 downto 16),
						rd			=> si_instr(15 downto 11),
						funct		=> si_instr(5 downto 0),
						imm		=> s_offset(15 downto 0),
						jAddr		=> s_jaddr);
	
-- Sign Extender
signext:	entity work.SignExtend(Behavioral)
			port map(dataIn	=> s_offset(15 downto 0), 
						dataOut	=> s_offset);
	
--	DU_RFdata <= s_instr;
--	DU_DMdata <= (others => '0');
	
------------------------------------------------------------------------------
-- Support Modules						
------------------------------------------------------------------------------

-- Display Unit
display:	entity work.DisplayUnit(Behavioral)
			generic map(dataPathType => SINGLE_CYCLE_DP,
							IM_ADDR_SIZE => ROM_ADDR_SIZE,
							DM_ADDR_SIZE => RAM_ADDR_SIZE)
			port map(RefClk	=> CLOCK_50,
						InputSel	=> SW(1 downto 0),	
						DispMode	=> SW(17),
						NextAddr	=> KEY(3),
						Dir		=> KEY(2),
						disp0		=> HEX0,
						disp1		=> HEX1,
						disp2		=> HEX2,
						disp3		=> HEX3,
						disp4		=> HEX4,
						disp5		=> HEX5,
						disp6		=> HEX6,
						disp7		=> HEX7);		

-- Debouncer
debncer:	entity work.DebounceUnit(Behavioral)
			generic map(inPolarity	=> '0',
							outPolarity => '1')
			port map(refClk	=> CLOCK_50, 
						dirtyIn	=> KEY(0), 
						pulsedOut=> s_clk);	
	
-- RegFile
	regFile: entity work.RegFile(Structural)
	port map (clk			 => s_clk,
				 writeEnable => s_regWrite,
				 writeReg    => s_dstReg,
				 writeData   => s_writeData,
				 readReg1	 => s_rs_readAddr,
				 readReg2	 => s_rt_readAddr,
				 readData1	 => s_rs_data,
				 readData2	 => s_rt_data);
				 
	
-- ALU Control Unit
ALUCtrl: entity work.ALUControlUnit(Behavioral)			 
port map (ALUop      => s_ALUop,
			 funct      => s_funct,
			 ALUcontrol => s_ALUaction);
			 
			 
-- Mux2N
muxALU: entity work.Mux2N(BehavAssign)
generic map(N => 32)
port map( sel    => s_ALUscr,
			 input0 => s_rt_data,
			 input1 => s_extendImm,
			 muxOut => s_ALU_in2);
			 
			 
-- ALU
alu: entity work.ALU32(DataFlow)
port map(a	  => s_rs_data,
			b	  => s_ALU_in2,
			op   => s_ALUaction,
			r    => s_ALUout,
			zero => s_zero);

			
-- Mux2N
muxMemOut: entity work.Mux2N(BehavAssign)
generic map(N => 32)
port map( sel    => s_memToReg,
			 input0 => s_ALUout,
			 input1 => s_memOut,
			 muxOut => s_writeData);
				
	
end Shell;