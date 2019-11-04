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

-- VENDOR "Altera"
-- PROGRAM "Quartus Prime"
-- VERSION "Version 17.1.0 Build 590 10/25/2017 SJ Lite Edition"

-- DATE "02/24/2018 11:42:48"

-- 
-- Device: Altera EP4CE115F29C7 Package FBGA780
-- 

-- 
-- This VHDL file should be used for ModelSim-Altera (VHDL) only
-- 

LIBRARY CYCLONEIVE;
LIBRARY IEEE;
USE CYCLONEIVE.CYCLONEIVE_COMPONENTS.ALL;
USE IEEE.STD_LOGIC_1164.ALL;

ENTITY 	hard_block IS
    PORT (
	devoe : IN std_logic;
	devclrn : IN std_logic;
	devpor : IN std_logic
	);
END hard_block;

-- Design Ports Information
-- ~ALTERA_ASDO_DATA1~	=>  Location: PIN_F4,	 I/O Standard: 2.5 V,	 Current Strength: Default
-- ~ALTERA_FLASH_nCE_nCSO~	=>  Location: PIN_E2,	 I/O Standard: 2.5 V,	 Current Strength: Default
-- ~ALTERA_DCLK~	=>  Location: PIN_P3,	 I/O Standard: 2.5 V,	 Current Strength: Default
-- ~ALTERA_DATA0~	=>  Location: PIN_N7,	 I/O Standard: 2.5 V,	 Current Strength: Default
-- ~ALTERA_nCEO~	=>  Location: PIN_P28,	 I/O Standard: 2.5 V,	 Current Strength: 8mA


ARCHITECTURE structure OF hard_block IS
SIGNAL gnd : std_logic := '0';
SIGNAL vcc : std_logic := '1';
SIGNAL unknown : std_logic := 'X';
SIGNAL ww_devoe : std_logic;
SIGNAL ww_devclrn : std_logic;
SIGNAL ww_devpor : std_logic;
SIGNAL \~ALTERA_ASDO_DATA1~~padout\ : std_logic;
SIGNAL \~ALTERA_FLASH_nCE_nCSO~~padout\ : std_logic;
SIGNAL \~ALTERA_DATA0~~padout\ : std_logic;
SIGNAL \~ALTERA_ASDO_DATA1~~ibuf_o\ : std_logic;
SIGNAL \~ALTERA_FLASH_nCE_nCSO~~ibuf_o\ : std_logic;
SIGNAL \~ALTERA_DATA0~~ibuf_o\ : std_logic;

BEGIN

ww_devoe <= devoe;
ww_devclrn <= devclrn;
ww_devpor <= devpor;
END structure;


LIBRARY CYCLONEIVE;
LIBRARY IEEE;
USE CYCLONEIVE.CYCLONEIVE_COMPONENTS.ALL;
USE IEEE.STD_LOGIC_1164.ALL;

ENTITY 	PEnc4_2 IS
    PORT (
	datain : IN std_logic_vector(3 DOWNTO 0);
	dataout : BUFFER std_logic_vector(1 DOWNTO 0)
	);
END PEnc4_2;

-- Design Ports Information
-- dataout[0]	=>  Location: PIN_D11,	 I/O Standard: 2.5 V,	 Current Strength: Default
-- dataout[1]	=>  Location: PIN_A6,	 I/O Standard: 2.5 V,	 Current Strength: Default
-- datain[2]	=>  Location: PIN_G11,	 I/O Standard: 2.5 V,	 Current Strength: Default
-- datain[0]	=>  Location: PIN_H12,	 I/O Standard: 2.5 V,	 Current Strength: Default
-- datain[1]	=>  Location: PIN_B6,	 I/O Standard: 2.5 V,	 Current Strength: Default
-- datain[3]	=>  Location: PIN_G12,	 I/O Standard: 2.5 V,	 Current Strength: Default


ARCHITECTURE structure OF PEnc4_2 IS
SIGNAL gnd : std_logic := '0';
SIGNAL vcc : std_logic := '1';
SIGNAL unknown : std_logic := 'X';
SIGNAL devoe : std_logic := '1';
SIGNAL devclrn : std_logic := '1';
SIGNAL devpor : std_logic := '1';
SIGNAL ww_devoe : std_logic;
SIGNAL ww_devclrn : std_logic;
SIGNAL ww_devpor : std_logic;
SIGNAL ww_datain : std_logic_vector(3 DOWNTO 0);
SIGNAL ww_dataout : std_logic_vector(1 DOWNTO 0);
SIGNAL \dataout[0]~output_o\ : std_logic;
SIGNAL \dataout[1]~output_o\ : std_logic;
SIGNAL \datain[3]~input_o\ : std_logic;
SIGNAL \datain[0]~input_o\ : std_logic;
SIGNAL \datain[2]~input_o\ : std_logic;
SIGNAL \datain[1]~input_o\ : std_logic;
SIGNAL \comb~0_combout\ : std_logic;
SIGNAL \comb~1_combout\ : std_logic;
SIGNAL \dataout[0]$latch~combout\ : std_logic;
SIGNAL \dataout[1]~0_combout\ : std_logic;
SIGNAL \comb~2_combout\ : std_logic;
SIGNAL \dataout[1]$latch~combout\ : std_logic;

COMPONENT hard_block
    PORT (
	devoe : IN std_logic;
	devclrn : IN std_logic;
	devpor : IN std_logic);
END COMPONENT;

BEGIN

ww_datain <= datain;
dataout <= ww_dataout;
ww_devoe <= devoe;
ww_devclrn <= devclrn;
ww_devpor <= devpor;
auto_generated_inst : hard_block
PORT MAP (
	devoe => ww_devoe,
	devclrn => ww_devclrn,
	devpor => ww_devpor);

-- Location: IOOBUF_X23_Y73_N9
\dataout[0]~output\ : cycloneive_io_obuf
-- pragma translate_off
GENERIC MAP (
	bus_hold => "false",
	open_drain_output => "false")
-- pragma translate_on
PORT MAP (
	i => \dataout[0]$latch~combout\,
	devoe => ww_devoe,
	o => \dataout[0]~output_o\);

-- Location: IOOBUF_X27_Y73_N16
\dataout[1]~output\ : cycloneive_io_obuf
-- pragma translate_off
GENERIC MAP (
	bus_hold => "false",
	open_drain_output => "false")
-- pragma translate_on
PORT MAP (
	i => \dataout[1]$latch~combout\,
	devoe => ww_devoe,
	o => \dataout[1]~output_o\);

-- Location: IOIBUF_X27_Y73_N8
\datain[3]~input\ : cycloneive_io_ibuf
-- pragma translate_off
GENERIC MAP (
	bus_hold => "false",
	simulate_z_as => "z")
-- pragma translate_on
PORT MAP (
	i => ww_datain(3),
	o => \datain[3]~input_o\);

-- Location: IOIBUF_X25_Y73_N22
\datain[0]~input\ : cycloneive_io_ibuf
-- pragma translate_off
GENERIC MAP (
	bus_hold => "false",
	simulate_z_as => "z")
-- pragma translate_on
PORT MAP (
	i => ww_datain(0),
	o => \datain[0]~input_o\);

-- Location: IOIBUF_X25_Y73_N15
\datain[2]~input\ : cycloneive_io_ibuf
-- pragma translate_off
GENERIC MAP (
	bus_hold => "false",
	simulate_z_as => "z")
-- pragma translate_on
PORT MAP (
	i => ww_datain(2),
	o => \datain[2]~input_o\);

-- Location: IOIBUF_X27_Y73_N22
\datain[1]~input\ : cycloneive_io_ibuf
-- pragma translate_off
GENERIC MAP (
	bus_hold => "false",
	simulate_z_as => "z")
-- pragma translate_on
PORT MAP (
	i => ww_datain(1),
	o => \datain[1]~input_o\);

-- Location: LCCOMB_X26_Y72_N24
\comb~0\ : cycloneive_lcell_comb
-- Equation(s):
-- \comb~0_combout\ = (!\datain[3]~input_o\ & ((\datain[2]~input_o\) # ((\datain[0]~input_o\ & !\datain[1]~input_o\))))

-- pragma translate_off
GENERIC MAP (
	lut_mask => "0101000001010100",
	sum_lutc_input => "datac")
-- pragma translate_on
PORT MAP (
	dataa => \datain[3]~input_o\,
	datab => \datain[0]~input_o\,
	datac => \datain[2]~input_o\,
	datad => \datain[1]~input_o\,
	combout => \comb~0_combout\);

-- Location: LCCOMB_X26_Y72_N18
\comb~1\ : cycloneive_lcell_comb
-- Equation(s):
-- \comb~1_combout\ = (!\datain[3]~input_o\ & ((\datain[2]~input_o\) # (!\datain[1]~input_o\)))

-- pragma translate_off
GENERIC MAP (
	lut_mask => "0101000001010101",
	sum_lutc_input => "datac")
-- pragma translate_on
PORT MAP (
	dataa => \datain[3]~input_o\,
	datac => \datain[2]~input_o\,
	datad => \datain[1]~input_o\,
	combout => \comb~1_combout\);

-- Location: LCCOMB_X25_Y72_N0
\dataout[0]$latch\ : cycloneive_lcell_comb
-- Equation(s):
-- \dataout[0]$latch~combout\ = (!\comb~0_combout\ & ((\dataout[0]$latch~combout\) # (!\comb~1_combout\)))

-- pragma translate_off
GENERIC MAP (
	lut_mask => "0011001100000011",
	sum_lutc_input => "datac")
-- pragma translate_on
PORT MAP (
	datab => \comb~0_combout\,
	datac => \comb~1_combout\,
	datad => \dataout[0]$latch~combout\,
	combout => \dataout[0]$latch~combout\);

-- Location: LCCOMB_X26_Y72_N6
\dataout[1]~0\ : cycloneive_lcell_comb
-- Equation(s):
-- \dataout[1]~0_combout\ = (!\datain[3]~input_o\ & !\datain[2]~input_o\)

-- pragma translate_off
GENERIC MAP (
	lut_mask => "0000010100000101",
	sum_lutc_input => "datac")
-- pragma translate_on
PORT MAP (
	dataa => \datain[3]~input_o\,
	datac => \datain[2]~input_o\,
	combout => \dataout[1]~0_combout\);

-- Location: LCCOMB_X26_Y72_N12
\comb~2\ : cycloneive_lcell_comb
-- Equation(s):
-- \comb~2_combout\ = (!\datain[3]~input_o\ & (!\datain[2]~input_o\ & ((\datain[0]~input_o\) # (\datain[1]~input_o\))))

-- pragma translate_off
GENERIC MAP (
	lut_mask => "0000010100000100",
	sum_lutc_input => "datac")
-- pragma translate_on
PORT MAP (
	dataa => \datain[3]~input_o\,
	datab => \datain[0]~input_o\,
	datac => \datain[2]~input_o\,
	datad => \datain[1]~input_o\,
	combout => \comb~2_combout\);

-- Location: LCCOMB_X26_Y72_N0
\dataout[1]$latch\ : cycloneive_lcell_comb
-- Equation(s):
-- \dataout[1]$latch~combout\ = (!\comb~2_combout\ & ((\dataout[1]$latch~combout\) # (!\dataout[1]~0_combout\)))

-- pragma translate_off
GENERIC MAP (
	lut_mask => "0000111100000101",
	sum_lutc_input => "datac")
-- pragma translate_on
PORT MAP (
	dataa => \dataout[1]~0_combout\,
	datac => \comb~2_combout\,
	datad => \dataout[1]$latch~combout\,
	combout => \dataout[1]$latch~combout\);

ww_dataout(0) <= \dataout[0]~output_o\;

ww_dataout(1) <= \dataout[1]~output_o\;
END structure;


