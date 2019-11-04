-- Copyright (C) 2017  Intel Corporation. All rights reserved.
-- Your use of Intel Corporation's design tools, logic functions 
-- and other software and tools, and its AMPP partner logic 
-- functions, and any output files from any of the foregoing 
-- (including device programming or simulation files), and any 
-- associated documentation or information are expressly subject 
-- to the terms and conditions of the Intel Program License 
-- Subscription Agreement, the Intel Quartus Prime License Agreement,
-- the Intel FPGA IP License Agreement, or other applicable license
-- agreement, including, without limitation, that your use is for
-- the sole purpose of programming logic devices manufactured by
-- Intel and sold by Intel or its authorized distributors.  Please
-- refer to the applicable agreement for further details.

-- *****************************************************************************
-- This file contains a Vhdl test bench with test vectors .The test vectors     
-- are exported from a vector file in the Quartus Waveform Editor and apply to  
-- the top level entity of the current Quartus project .The user can use this   
-- testbench to simulate his design using a third-party simulation tool .       
-- *****************************************************************************
-- Generated on "02/28/2018 12:32:52"
                                                             
-- Vhdl Test Bench(with test vectors) for design  :          AdderDemo
-- 
-- Simulation tool : 3rd Party
-- 

LIBRARY ieee;                                               
USE ieee.std_logic_1164.all;                                

ENTITY AdderDemo_vhd_vec_tst IS
END AdderDemo_vhd_vec_tst;
ARCHITECTURE AdderDemo_arch OF AdderDemo_vhd_vec_tst IS
-- constants                                                 
-- signals                                                   
SIGNAL KEY : STD_LOGIC_VECTOR(0 DOWNTO 0);
SIGNAL LEDR : STD_LOGIC_VECTOR(14 DOWNTO 0);
SIGNAL SW : STD_LOGIC_VECTOR(17 DOWNTO 0);
COMPONENT AdderDemo
	PORT (
	KEY : IN STD_LOGIC_VECTOR(0 DOWNTO 0);
	LEDR : OUT STD_LOGIC_VECTOR(14 DOWNTO 0);
	SW : IN STD_LOGIC_VECTOR(17 DOWNTO 0)
	);
END COMPONENT;
BEGIN
	i1 : AdderDemo
	PORT MAP (
-- list connections between master ports and signals
	KEY => KEY,
	LEDR => LEDR,
	SW => SW
	);

-- KEY
t_prcs_KEY: PROCESS
BEGIN
	KEY <= '0';
WAIT;
END PROCESS t_prcs_KEY;

-- KEY[0]
t_prcs_KEY_0: PROCESS
BEGIN
	KEY(0) <= '1';
WAIT;
END PROCESS t_prcs_KEY_0;

-- SW[7]
t_prcs_SW_7: PROCESS
BEGIN
	SW(7) <= '0';
WAIT;
END PROCESS t_prcs_SW_7;

-- SW[6]
t_prcs_SW_6: PROCESS
BEGIN
	SW(6) <= '0';
WAIT;
END PROCESS t_prcs_SW_6;

-- SW[5]
t_prcs_SW_5: PROCESS
BEGIN
	SW(5) <= '0';
WAIT;
END PROCESS t_prcs_SW_5;

-- SW[4]
t_prcs_SW_4: PROCESS
BEGIN
	SW(4) <= '0';
WAIT;
END PROCESS t_prcs_SW_4;

-- SW[3]
t_prcs_SW_3: PROCESS
BEGIN
	SW(3) <= '0';
WAIT;
END PROCESS t_prcs_SW_3;

-- SW[2]
t_prcs_SW_2: PROCESS
BEGIN
	SW(2) <= '0';
WAIT;
END PROCESS t_prcs_SW_2;

-- SW[1]
t_prcs_SW_1: PROCESS
BEGIN
	SW(1) <= '0';
WAIT;
END PROCESS t_prcs_SW_1;

-- SW[0]
t_prcs_SW_0: PROCESS
BEGIN
	SW(0) <= '0';
WAIT;
END PROCESS t_prcs_SW_0;

-- SW[10]
t_prcs_SW_10: PROCESS
BEGIN
	SW(10) <= '1';
WAIT;
END PROCESS t_prcs_SW_10;

-- SW[11]
t_prcs_SW_11: PROCESS
BEGIN
	SW(11) <= '1';
WAIT;
END PROCESS t_prcs_SW_11;

-- SW[12]
t_prcs_SW_12: PROCESS
BEGIN
	SW(12) <= '1';
WAIT;
END PROCESS t_prcs_SW_12;

-- SW[13]
t_prcs_SW_13: PROCESS
BEGIN
	SW(13) <= '1';
WAIT;
END PROCESS t_prcs_SW_13;

-- SW[14]
t_prcs_SW_14: PROCESS
BEGIN
	SW(14) <= '1';
WAIT;
END PROCESS t_prcs_SW_14;

-- SW[15]
t_prcs_SW_15: PROCESS
BEGIN
	SW(15) <= '1';
WAIT;
END PROCESS t_prcs_SW_15;

-- SW[16]
t_prcs_SW_16: PROCESS
BEGIN
	SW(16) <= '1';
WAIT;
END PROCESS t_prcs_SW_16;

-- SW[17]
t_prcs_SW_17: PROCESS
BEGIN
	SW(17) <= '1';
WAIT;
END PROCESS t_prcs_SW_17;
END AdderDemo_arch;
