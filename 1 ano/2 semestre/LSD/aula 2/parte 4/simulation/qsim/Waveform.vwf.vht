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
-- Generated on "02/24/2018 11:37:53"
                                                             
-- Vhdl Test Bench(with test vectors) for design  :          PEnc4_2
-- 
-- Simulation tool : 3rd Party
-- 

LIBRARY ieee;                                               
USE ieee.std_logic_1164.all;                                

ENTITY PEnc4_2_vhd_vec_tst IS
END PEnc4_2_vhd_vec_tst;
ARCHITECTURE PEnc4_2_arch OF PEnc4_2_vhd_vec_tst IS
-- constants                                                 
-- signals                                                   
SIGNAL datain : STD_LOGIC_VECTOR(3 DOWNTO 0);
SIGNAL dataout : STD_LOGIC_VECTOR(1 DOWNTO 0);
COMPONENT PEnc4_2
	PORT (
	datain : IN STD_LOGIC_VECTOR(3 DOWNTO 0);
	dataout : BUFFER STD_LOGIC_VECTOR(1 DOWNTO 0)
	);
END COMPONENT;
BEGIN
	i1 : PEnc4_2
	PORT MAP (
-- list connections between master ports and signals
	datain => datain,
	dataout => dataout
	);

-- datain[0]
t_prcs_datain_0: PROCESS
BEGIN
LOOP
	datain(0) <= '0';
	WAIT FOR 5000 ps;
	datain(0) <= '1';
	WAIT FOR 5000 ps;
	IF (NOW >= 1000000 ps) THEN WAIT; END IF;
END LOOP;
END PROCESS t_prcs_datain_0;

-- datain[1]
t_prcs_datain_1: PROCESS
BEGIN
LOOP
	datain(1) <= '0';
	WAIT FOR 10000 ps;
	datain(1) <= '1';
	WAIT FOR 10000 ps;
	IF (NOW >= 1000000 ps) THEN WAIT; END IF;
END LOOP;
END PROCESS t_prcs_datain_1;

-- datain[2]
t_prcs_datain_2: PROCESS
BEGIN
	FOR i IN 1 TO 33
	LOOP
		datain(2) <= '0';
		WAIT FOR 15000 ps;
		datain(2) <= '1';
		WAIT FOR 15000 ps;
	END LOOP;
	datain(2) <= '0';
WAIT;
END PROCESS t_prcs_datain_2;

-- datain[3]
t_prcs_datain_3: PROCESS
BEGIN
LOOP
	datain(3) <= '0';
	WAIT FOR 20000 ps;
	datain(3) <= '1';
	WAIT FOR 20000 ps;
	IF (NOW >= 1000000 ps) THEN WAIT; END IF;
END LOOP;
END PROCESS t_prcs_datain_3;
END PEnc4_2_arch;
