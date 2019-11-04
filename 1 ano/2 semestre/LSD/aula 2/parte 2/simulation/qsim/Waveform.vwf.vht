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
-- Generated on "02/23/2018 14:02:46"
                                                             
-- Vhdl Test Bench(with test vectors) for design  :          Mux2_1
-- 
-- Simulation tool : 3rd Party
-- 

LIBRARY ieee;                                               
USE ieee.std_logic_1164.all;                                

ENTITY Mux2_1_vhd_vec_tst IS
END Mux2_1_vhd_vec_tst;
ARCHITECTURE Mux2_1_arch OF Mux2_1_vhd_vec_tst IS
-- constants                                                 
-- signals                                                   
SIGNAL input0 : STD_LOGIC;
SIGNAL input1 : STD_LOGIC;
SIGNAL muxOut : STD_LOGIC;
SIGNAL sel : STD_LOGIC;
COMPONENT Mux2_1
	PORT (
	input0 : IN STD_LOGIC;
	input1 : IN STD_LOGIC;
	muxOut : OUT STD_LOGIC;
	sel : IN STD_LOGIC
	);
END COMPONENT;
BEGIN
	i1 : Mux2_1
	PORT MAP (
-- list connections between master ports and signals
	input0 => input0,
	input1 => input1,
	muxOut => muxOut,
	sel => sel
	);

-- input0
t_prcs_input0: PROCESS
BEGIN
LOOP
	input0 <= '0';
	WAIT FOR 5000 ps;
	input0 <= '1';
	WAIT FOR 5000 ps;
	IF (NOW >= 1000000 ps) THEN WAIT; END IF;
END LOOP;
END PROCESS t_prcs_input0;

-- input1
t_prcs_input1: PROCESS
BEGIN
LOOP
	input1 <= '0';
	WAIT FOR 10000 ps;
	input1 <= '1';
	WAIT FOR 10000 ps;
	IF (NOW >= 1000000 ps) THEN WAIT; END IF;
END LOOP;
END PROCESS t_prcs_input1;

-- sel
t_prcs_sel: PROCESS
BEGIN
LOOP
	sel <= '0';
	WAIT FOR 50000 ps;
	sel <= '1';
	WAIT FOR 50000 ps;
	IF (NOW >= 1000000 ps) THEN WAIT; END IF;
END LOOP;
END PROCESS t_prcs_sel;
END Mux2_1_arch;
