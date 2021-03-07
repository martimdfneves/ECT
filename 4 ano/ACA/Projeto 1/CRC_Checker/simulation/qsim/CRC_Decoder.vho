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

-- DATE "12/15/2020 17:15:50"

-- 
-- Device: Altera EP4CGX15BF14C6 Package FBGA169
-- 

-- 
-- This VHDL file should be used for ModelSim-Altera (VHDL) only
-- 

LIBRARY CYCLONEIV;
LIBRARY IEEE;
USE CYCLONEIV.CYCLONEIV_COMPONENTS.ALL;
USE IEEE.STD_LOGIC_1164.ALL;

ENTITY 	hard_block IS
    PORT (
	devoe : IN std_logic;
	devclrn : IN std_logic;
	devpor : IN std_logic
	);
END hard_block;

-- Design Ports Information
-- ~ALTERA_NCEO~	=>  Location: PIN_N5,	 I/O Standard: 2.5 V,	 Current Strength: 16mA
-- ~ALTERA_DATA0~	=>  Location: PIN_A5,	 I/O Standard: 2.5 V,	 Current Strength: Default
-- ~ALTERA_ASDO~	=>  Location: PIN_B5,	 I/O Standard: 2.5 V,	 Current Strength: Default
-- ~ALTERA_NCSO~	=>  Location: PIN_C5,	 I/O Standard: 2.5 V,	 Current Strength: Default
-- ~ALTERA_DCLK~	=>  Location: PIN_A4,	 I/O Standard: 2.5 V,	 Current Strength: Default


ARCHITECTURE structure OF hard_block IS
SIGNAL gnd : std_logic := '0';
SIGNAL vcc : std_logic := '1';
SIGNAL unknown : std_logic := 'X';
SIGNAL ww_devoe : std_logic;
SIGNAL ww_devclrn : std_logic;
SIGNAL ww_devpor : std_logic;
SIGNAL \~ALTERA_DATA0~~padout\ : std_logic;
SIGNAL \~ALTERA_ASDO~~padout\ : std_logic;
SIGNAL \~ALTERA_NCSO~~padout\ : std_logic;
SIGNAL \~ALTERA_DATA0~~ibuf_o\ : std_logic;
SIGNAL \~ALTERA_ASDO~~ibuf_o\ : std_logic;
SIGNAL \~ALTERA_NCSO~~ibuf_o\ : std_logic;

BEGIN

ww_devoe <= devoe;
ww_devclrn <= devclrn;
ww_devpor <= devpor;
END structure;


LIBRARY CYCLONEIV;
LIBRARY IEEE;
USE CYCLONEIV.CYCLONEIV_COMPONENTS.ALL;
USE IEEE.STD_LOGIC_1164.ALL;

ENTITY 	CRC_Checker_STD IS
    PORT (
	dIN : IN std_logic_vector(23 DOWNTO 0);
	CRC_err : OUT std_logic_vector(7 DOWNTO 0);
	Check : OUT std_logic
	);
END CRC_Checker_STD;

-- Design Ports Information
-- CRC_err[0]	=>  Location: PIN_B10,	 I/O Standard: 2.5 V,	 Current Strength: Default
-- CRC_err[1]	=>  Location: PIN_D11,	 I/O Standard: 2.5 V,	 Current Strength: Default
-- CRC_err[2]	=>  Location: PIN_N13,	 I/O Standard: 2.5 V,	 Current Strength: Default
-- CRC_err[3]	=>  Location: PIN_B11,	 I/O Standard: 2.5 V,	 Current Strength: Default
-- CRC_err[4]	=>  Location: PIN_H12,	 I/O Standard: 2.5 V,	 Current Strength: Default
-- CRC_err[5]	=>  Location: PIN_F10,	 I/O Standard: 2.5 V,	 Current Strength: Default
-- CRC_err[6]	=>  Location: PIN_K11,	 I/O Standard: 2.5 V,	 Current Strength: Default
-- CRC_err[7]	=>  Location: PIN_B8,	 I/O Standard: 2.5 V,	 Current Strength: Default
-- Check	=>  Location: PIN_G9,	 I/O Standard: 2.5 V,	 Current Strength: Default
-- dIN[7]	=>  Location: PIN_M7,	 I/O Standard: 2.5 V,	 Current Strength: Default
-- dIN[18]	=>  Location: PIN_N7,	 I/O Standard: 2.5 V,	 Current Strength: Default
-- dIN[19]	=>  Location: PIN_E13,	 I/O Standard: 2.5 V,	 Current Strength: Default
-- dIN[20]	=>  Location: PIN_D12,	 I/O Standard: 2.5 V,	 Current Strength: Default
-- dIN[23]	=>  Location: PIN_C12,	 I/O Standard: 2.5 V,	 Current Strength: Default
-- dIN[11]	=>  Location: PIN_L13,	 I/O Standard: 2.5 V,	 Current Strength: Default
-- dIN[13]	=>  Location: PIN_K12,	 I/O Standard: 2.5 V,	 Current Strength: Default
-- dIN[16]	=>  Location: PIN_H10,	 I/O Standard: 2.5 V,	 Current Strength: Default
-- dIN[17]	=>  Location: PIN_G10,	 I/O Standard: 2.5 V,	 Current Strength: Default
-- dIN[0]	=>  Location: PIN_J13,	 I/O Standard: 2.5 V,	 Current Strength: Default
-- dIN[8]	=>  Location: PIN_L12,	 I/O Standard: 2.5 V,	 Current Strength: Default
-- dIN[15]	=>  Location: PIN_F11,	 I/O Standard: 2.5 V,	 Current Strength: Default
-- dIN[12]	=>  Location: PIN_K13,	 I/O Standard: 2.5 V,	 Current Strength: Default
-- dIN[9]	=>  Location: PIN_D10,	 I/O Standard: 2.5 V,	 Current Strength: Default
-- dIN[21]	=>  Location: PIN_F13,	 I/O Standard: 2.5 V,	 Current Strength: Default
-- dIN[14]	=>  Location: PIN_F12,	 I/O Standard: 2.5 V,	 Current Strength: Default
-- dIN[1]	=>  Location: PIN_C11,	 I/O Standard: 2.5 V,	 Current Strength: Default
-- dIN[2]	=>  Location: PIN_B13,	 I/O Standard: 2.5 V,	 Current Strength: Default
-- dIN[10]	=>  Location: PIN_D13,	 I/O Standard: 2.5 V,	 Current Strength: Default
-- dIN[22]	=>  Location: PIN_F9,	 I/O Standard: 2.5 V,	 Current Strength: Default
-- dIN[3]	=>  Location: PIN_N12,	 I/O Standard: 2.5 V,	 Current Strength: Default
-- dIN[4]	=>  Location: PIN_C13,	 I/O Standard: 2.5 V,	 Current Strength: Default
-- dIN[5]	=>  Location: PIN_E10,	 I/O Standard: 2.5 V,	 Current Strength: Default
-- dIN[6]	=>  Location: PIN_A13,	 I/O Standard: 2.5 V,	 Current Strength: Default


ARCHITECTURE structure OF CRC_Checker_STD IS
SIGNAL gnd : std_logic := '0';
SIGNAL vcc : std_logic := '1';
SIGNAL unknown : std_logic := 'X';
SIGNAL devoe : std_logic := '1';
SIGNAL devclrn : std_logic := '1';
SIGNAL devpor : std_logic := '1';
SIGNAL ww_devoe : std_logic;
SIGNAL ww_devclrn : std_logic;
SIGNAL ww_devpor : std_logic;
SIGNAL ww_dIN : std_logic_vector(23 DOWNTO 0);
SIGNAL ww_CRC_err : std_logic_vector(7 DOWNTO 0);
SIGNAL ww_Check : std_logic;
SIGNAL \dIN[7]~input_o\ : std_logic;
SIGNAL \CRC_err[0]~output_o\ : std_logic;
SIGNAL \CRC_err[1]~output_o\ : std_logic;
SIGNAL \CRC_err[2]~output_o\ : std_logic;
SIGNAL \CRC_err[3]~output_o\ : std_logic;
SIGNAL \CRC_err[4]~output_o\ : std_logic;
SIGNAL \CRC_err[5]~output_o\ : std_logic;
SIGNAL \CRC_err[6]~output_o\ : std_logic;
SIGNAL \CRC_err[7]~output_o\ : std_logic;
SIGNAL \Check~output_o\ : std_logic;
SIGNAL \dIN[8]~input_o\ : std_logic;
SIGNAL \dIN[0]~input_o\ : std_logic;
SIGNAL \dIN[15]~input_o\ : std_logic;
SIGNAL \crc_e|crc_error_0|y~0_combout\ : std_logic;
SIGNAL \dIN[13]~input_o\ : std_logic;
SIGNAL \dIN[11]~input_o\ : std_logic;
SIGNAL \crc_e|crc_16to8|d_11_13|y~combout\ : std_logic;
SIGNAL \dIN[17]~input_o\ : std_logic;
SIGNAL \dIN[16]~input_o\ : std_logic;
SIGNAL \crc_e|crc_16to8|d_16_17|y~0_combout\ : std_logic;
SIGNAL \dIN[19]~input_o\ : std_logic;
SIGNAL \dIN[23]~input_o\ : std_logic;
SIGNAL \dIN[18]~input_o\ : std_logic;
SIGNAL \dIN[20]~input_o\ : std_logic;
SIGNAL \crc_e|crc_16to8|compos1819_2023|y~combout\ : std_logic;
SIGNAL \crc_e|crc_error_0|y~1_combout\ : std_logic;
SIGNAL \dIN[1]~input_o\ : std_logic;
SIGNAL \dIN[12]~input_o\ : std_logic;
SIGNAL \crc_e|crc_error_1|y~0_combout\ : std_logic;
SIGNAL \dIN[21]~input_o\ : std_logic;
SIGNAL \dIN[14]~input_o\ : std_logic;
SIGNAL \dIN[9]~input_o\ : std_logic;
SIGNAL \crc_e|crc_error_1|y~1_combout\ : std_logic;
SIGNAL \crc_e|crc_error_1|y~2_combout\ : std_logic;
SIGNAL \crc_e|crc_16to8|d_8_12|y~combout\ : std_logic;
SIGNAL \dIN[10]~input_o\ : std_logic;
SIGNAL \dIN[2]~input_o\ : std_logic;
SIGNAL \dIN[22]~input_o\ : std_logic;
SIGNAL \crc_e|crc_error_2|y~0_combout\ : std_logic;
SIGNAL \crc_e|crc_error_2|y~1_combout\ : std_logic;
SIGNAL \crc_e|crc_error_5|y~0_combout\ : std_logic;
SIGNAL \dIN[3]~input_o\ : std_logic;
SIGNAL \crc_e|crc_error_3|y~0_combout\ : std_logic;
SIGNAL \crc_e|crc_error_3|y~combout\ : std_logic;
SIGNAL \dIN[4]~input_o\ : std_logic;
SIGNAL \crc_e|crc_error_4|y~0_combout\ : std_logic;
SIGNAL \crc_e|crc_error_4|y~combout\ : std_logic;
SIGNAL \dIN[5]~input_o\ : std_logic;
SIGNAL \crc_e|crc_error_5|y~1_combout\ : std_logic;
SIGNAL \crc_e|crc_error_5|y~2_combout\ : std_logic;
SIGNAL \dIN[6]~input_o\ : std_logic;
SIGNAL \crc_e|crc_error_6|y~0_combout\ : std_logic;
SIGNAL \crc_e|crc_error_6|y~1_combout\ : std_logic;
SIGNAL \crc_e|CHECK~0_combout\ : std_logic;
SIGNAL \crc_e|CHECK~combout\ : std_logic;

COMPONENT hard_block
    PORT (
	devoe : IN std_logic;
	devclrn : IN std_logic;
	devpor : IN std_logic);
END COMPONENT;

BEGIN

ww_dIN <= dIN;
CRC_err <= ww_CRC_err;
Check <= ww_Check;
ww_devoe <= devoe;
ww_devclrn <= devclrn;
ww_devpor <= devpor;
auto_generated_inst : hard_block
PORT MAP (
	devoe => ww_devoe,
	devclrn => ww_devclrn,
	devpor => ww_devpor);

-- Location: IOOBUF_X24_Y31_N9
\CRC_err[0]~output\ : cycloneiv_io_obuf
-- pragma translate_off
GENERIC MAP (
	bus_hold => "false",
	open_drain_output => "false")
-- pragma translate_on
PORT MAP (
	i => \crc_e|crc_error_0|y~1_combout\,
	devoe => ww_devoe,
	o => \CRC_err[0]~output_o\);

-- Location: IOOBUF_X33_Y28_N2
\CRC_err[1]~output\ : cycloneiv_io_obuf
-- pragma translate_off
GENERIC MAP (
	bus_hold => "false",
	open_drain_output => "false")
-- pragma translate_on
PORT MAP (
	i => \crc_e|crc_error_1|y~2_combout\,
	devoe => ww_devoe,
	o => \CRC_err[1]~output_o\);

-- Location: IOOBUF_X33_Y10_N9
\CRC_err[2]~output\ : cycloneiv_io_obuf
-- pragma translate_off
GENERIC MAP (
	bus_hold => "false",
	open_drain_output => "false")
-- pragma translate_on
PORT MAP (
	i => \crc_e|crc_error_2|y~1_combout\,
	devoe => ww_devoe,
	o => \CRC_err[2]~output_o\);

-- Location: IOOBUF_X24_Y31_N2
\CRC_err[3]~output\ : cycloneiv_io_obuf
-- pragma translate_off
GENERIC MAP (
	bus_hold => "false",
	open_drain_output => "false")
-- pragma translate_on
PORT MAP (
	i => \crc_e|crc_error_3|y~combout\,
	devoe => ww_devoe,
	o => \CRC_err[3]~output_o\);

-- Location: IOOBUF_X33_Y14_N9
\CRC_err[4]~output\ : cycloneiv_io_obuf
-- pragma translate_off
GENERIC MAP (
	bus_hold => "false",
	open_drain_output => "false")
-- pragma translate_on
PORT MAP (
	i => \crc_e|crc_error_4|y~combout\,
	devoe => ww_devoe,
	o => \CRC_err[4]~output_o\);

-- Location: IOOBUF_X33_Y24_N2
\CRC_err[5]~output\ : cycloneiv_io_obuf
-- pragma translate_off
GENERIC MAP (
	bus_hold => "false",
	open_drain_output => "false")
-- pragma translate_on
PORT MAP (
	i => \crc_e|crc_error_5|y~2_combout\,
	devoe => ww_devoe,
	o => \CRC_err[5]~output_o\);

-- Location: IOOBUF_X33_Y11_N2
\CRC_err[6]~output\ : cycloneiv_io_obuf
-- pragma translate_off
GENERIC MAP (
	bus_hold => "false",
	open_drain_output => "false")
-- pragma translate_on
PORT MAP (
	i => \crc_e|crc_error_6|y~1_combout\,
	devoe => ww_devoe,
	o => \CRC_err[6]~output_o\);

-- Location: IOOBUF_X22_Y31_N2
\CRC_err[7]~output\ : cycloneiv_io_obuf
-- pragma translate_off
GENERIC MAP (
	bus_hold => "false",
	open_drain_output => "false")
-- pragma translate_on
PORT MAP (
	i => GND,
	devoe => ww_devoe,
	o => \CRC_err[7]~output_o\);

-- Location: IOOBUF_X33_Y22_N2
\Check~output\ : cycloneiv_io_obuf
-- pragma translate_off
GENERIC MAP (
	bus_hold => "false",
	open_drain_output => "false")
-- pragma translate_on
PORT MAP (
	i => \crc_e|CHECK~combout\,
	devoe => ww_devoe,
	o => \Check~output_o\);

-- Location: IOIBUF_X33_Y12_N1
\dIN[8]~input\ : cycloneiv_io_ibuf
-- pragma translate_off
GENERIC MAP (
	bus_hold => "false",
	simulate_z_as => "z")
-- pragma translate_on
PORT MAP (
	i => ww_dIN(8),
	o => \dIN[8]~input_o\);

-- Location: IOIBUF_X33_Y15_N8
\dIN[0]~input\ : cycloneiv_io_ibuf
-- pragma translate_off
GENERIC MAP (
	bus_hold => "false",
	simulate_z_as => "z")
-- pragma translate_on
PORT MAP (
	i => ww_dIN(0),
	o => \dIN[0]~input_o\);

-- Location: IOIBUF_X33_Y24_N8
\dIN[15]~input\ : cycloneiv_io_ibuf
-- pragma translate_off
GENERIC MAP (
	bus_hold => "false",
	simulate_z_as => "z")
-- pragma translate_on
PORT MAP (
	i => ww_dIN(15),
	o => \dIN[15]~input_o\);

-- Location: LCCOMB_X29_Y19_N4
\crc_e|crc_error_0|y~0\ : cycloneiv_lcell_comb
-- Equation(s):
-- \crc_e|crc_error_0|y~0_combout\ = \dIN[8]~input_o\ $ (\dIN[0]~input_o\ $ (\dIN[15]~input_o\))

-- pragma translate_off
GENERIC MAP (
	lut_mask => "1001011010010110",
	sum_lutc_input => "datac")
-- pragma translate_on
PORT MAP (
	dataa => \dIN[8]~input_o\,
	datab => \dIN[0]~input_o\,
	datac => \dIN[15]~input_o\,
	combout => \crc_e|crc_error_0|y~0_combout\);

-- Location: IOIBUF_X33_Y11_N8
\dIN[13]~input\ : cycloneiv_io_ibuf
-- pragma translate_off
GENERIC MAP (
	bus_hold => "false",
	simulate_z_as => "z")
-- pragma translate_on
PORT MAP (
	i => ww_dIN(13),
	o => \dIN[13]~input_o\);

-- Location: IOIBUF_X33_Y12_N8
\dIN[11]~input\ : cycloneiv_io_ibuf
-- pragma translate_off
GENERIC MAP (
	bus_hold => "false",
	simulate_z_as => "z")
-- pragma translate_on
PORT MAP (
	i => ww_dIN(11),
	o => \dIN[11]~input_o\);

-- Location: LCCOMB_X29_Y19_N24
\crc_e|crc_16to8|d_11_13|y\ : cycloneiv_lcell_comb
-- Equation(s):
-- \crc_e|crc_16to8|d_11_13|y~combout\ = \dIN[13]~input_o\ $ (\dIN[11]~input_o\)

-- pragma translate_off
GENERIC MAP (
	lut_mask => "0101101001011010",
	sum_lutc_input => "datac")
-- pragma translate_on
PORT MAP (
	dataa => \dIN[13]~input_o\,
	datac => \dIN[11]~input_o\,
	combout => \crc_e|crc_16to8|d_11_13|y~combout\);

-- Location: IOIBUF_X33_Y22_N8
\dIN[17]~input\ : cycloneiv_io_ibuf
-- pragma translate_off
GENERIC MAP (
	bus_hold => "false",
	simulate_z_as => "z")
-- pragma translate_on
PORT MAP (
	i => ww_dIN(17),
	o => \dIN[17]~input_o\);

-- Location: IOIBUF_X33_Y14_N1
\dIN[16]~input\ : cycloneiv_io_ibuf
-- pragma translate_off
GENERIC MAP (
	bus_hold => "false",
	simulate_z_as => "z")
-- pragma translate_on
PORT MAP (
	i => ww_dIN(16),
	o => \dIN[16]~input_o\);

-- Location: LCCOMB_X29_Y19_N2
\crc_e|crc_16to8|d_16_17|y~0\ : cycloneiv_lcell_comb
-- Equation(s):
-- \crc_e|crc_16to8|d_16_17|y~0_combout\ = \dIN[17]~input_o\ $ (\dIN[16]~input_o\)

-- pragma translate_off
GENERIC MAP (
	lut_mask => "0011110000111100",
	sum_lutc_input => "datac")
-- pragma translate_on
PORT MAP (
	datab => \dIN[17]~input_o\,
	datac => \dIN[16]~input_o\,
	combout => \crc_e|crc_16to8|d_16_17|y~0_combout\);

-- Location: IOIBUF_X33_Y25_N8
\dIN[19]~input\ : cycloneiv_io_ibuf
-- pragma translate_off
GENERIC MAP (
	bus_hold => "false",
	simulate_z_as => "z")
-- pragma translate_on
PORT MAP (
	i => ww_dIN(19),
	o => \dIN[19]~input_o\);

-- Location: IOIBUF_X31_Y31_N8
\dIN[23]~input\ : cycloneiv_io_ibuf
-- pragma translate_off
GENERIC MAP (
	bus_hold => "false",
	simulate_z_as => "z")
-- pragma translate_on
PORT MAP (
	i => ww_dIN(23),
	o => \dIN[23]~input_o\);

-- Location: IOIBUF_X16_Y0_N1
\dIN[18]~input\ : cycloneiv_io_ibuf
-- pragma translate_off
GENERIC MAP (
	bus_hold => "false",
	simulate_z_as => "z")
-- pragma translate_on
PORT MAP (
	i => ww_dIN(18),
	o => \dIN[18]~input_o\);

-- Location: IOIBUF_X33_Y28_N8
\dIN[20]~input\ : cycloneiv_io_ibuf
-- pragma translate_off
GENERIC MAP (
	bus_hold => "false",
	simulate_z_as => "z")
-- pragma translate_on
PORT MAP (
	i => ww_dIN(20),
	o => \dIN[20]~input_o\);

-- Location: LCCOMB_X30_Y27_N16
\crc_e|crc_16to8|compos1819_2023|y\ : cycloneiv_lcell_comb
-- Equation(s):
-- \crc_e|crc_16to8|compos1819_2023|y~combout\ = \dIN[19]~input_o\ $ (\dIN[23]~input_o\ $ (\dIN[18]~input_o\ $ (\dIN[20]~input_o\)))

-- pragma translate_off
GENERIC MAP (
	lut_mask => "0110100110010110",
	sum_lutc_input => "datac")
-- pragma translate_on
PORT MAP (
	dataa => \dIN[19]~input_o\,
	datab => \dIN[23]~input_o\,
	datac => \dIN[18]~input_o\,
	datad => \dIN[20]~input_o\,
	combout => \crc_e|crc_16to8|compos1819_2023|y~combout\);

-- Location: LCCOMB_X30_Y27_N2
\crc_e|crc_error_0|y~1\ : cycloneiv_lcell_comb
-- Equation(s):
-- \crc_e|crc_error_0|y~1_combout\ = \crc_e|crc_error_0|y~0_combout\ $ (\crc_e|crc_16to8|d_11_13|y~combout\ $ (\crc_e|crc_16to8|d_16_17|y~0_combout\ $ (\crc_e|crc_16to8|compos1819_2023|y~combout\)))

-- pragma translate_off
GENERIC MAP (
	lut_mask => "0110100110010110",
	sum_lutc_input => "datac")
-- pragma translate_on
PORT MAP (
	dataa => \crc_e|crc_error_0|y~0_combout\,
	datab => \crc_e|crc_16to8|d_11_13|y~combout\,
	datac => \crc_e|crc_16to8|d_16_17|y~0_combout\,
	datad => \crc_e|crc_16to8|compos1819_2023|y~combout\,
	combout => \crc_e|crc_error_0|y~1_combout\);

-- Location: IOIBUF_X31_Y31_N1
\dIN[1]~input\ : cycloneiv_io_ibuf
-- pragma translate_off
GENERIC MAP (
	bus_hold => "false",
	simulate_z_as => "z")
-- pragma translate_on
PORT MAP (
	i => ww_dIN(1),
	o => \dIN[1]~input_o\);

-- Location: IOIBUF_X33_Y15_N1
\dIN[12]~input\ : cycloneiv_io_ibuf
-- pragma translate_off
GENERIC MAP (
	bus_hold => "false",
	simulate_z_as => "z")
-- pragma translate_on
PORT MAP (
	i => ww_dIN(12),
	o => \dIN[12]~input_o\);

-- Location: LCCOMB_X29_Y19_N22
\crc_e|crc_error_1|y~0\ : cycloneiv_lcell_comb
-- Equation(s):
-- \crc_e|crc_error_1|y~0_combout\ = \dIN[13]~input_o\ $ (\dIN[12]~input_o\ $ (\dIN[11]~input_o\ $ (\dIN[8]~input_o\)))

-- pragma translate_off
GENERIC MAP (
	lut_mask => "0110100110010110",
	sum_lutc_input => "datac")
-- pragma translate_on
PORT MAP (
	dataa => \dIN[13]~input_o\,
	datab => \dIN[12]~input_o\,
	datac => \dIN[11]~input_o\,
	datad => \dIN[8]~input_o\,
	combout => \crc_e|crc_error_1|y~0_combout\);

-- Location: IOIBUF_X33_Y16_N8
\dIN[21]~input\ : cycloneiv_io_ibuf
-- pragma translate_off
GENERIC MAP (
	bus_hold => "false",
	simulate_z_as => "z")
-- pragma translate_on
PORT MAP (
	i => ww_dIN(21),
	o => \dIN[21]~input_o\);

-- Location: IOIBUF_X33_Y16_N1
\dIN[14]~input\ : cycloneiv_io_ibuf
-- pragma translate_off
GENERIC MAP (
	bus_hold => "false",
	simulate_z_as => "z")
-- pragma translate_on
PORT MAP (
	i => ww_dIN(14),
	o => \dIN[14]~input_o\);

-- Location: IOIBUF_X33_Y27_N8
\dIN[9]~input\ : cycloneiv_io_ibuf
-- pragma translate_off
GENERIC MAP (
	bus_hold => "false",
	simulate_z_as => "z")
-- pragma translate_on
PORT MAP (
	i => ww_dIN(9),
	o => \dIN[9]~input_o\);

-- Location: LCCOMB_X30_Y27_N4
\crc_e|crc_error_1|y~1\ : cycloneiv_lcell_comb
-- Equation(s):
-- \crc_e|crc_error_1|y~1_combout\ = \dIN[21]~input_o\ $ (\dIN[14]~input_o\ $ (\dIN[15]~input_o\ $ (\dIN[9]~input_o\)))

-- pragma translate_off
GENERIC MAP (
	lut_mask => "0110100110010110",
	sum_lutc_input => "datac")
-- pragma translate_on
PORT MAP (
	dataa => \dIN[21]~input_o\,
	datab => \dIN[14]~input_o\,
	datac => \dIN[15]~input_o\,
	datad => \dIN[9]~input_o\,
	combout => \crc_e|crc_error_1|y~1_combout\);

-- Location: LCCOMB_X30_Y27_N22
\crc_e|crc_error_1|y~2\ : cycloneiv_lcell_comb
-- Equation(s):
-- \crc_e|crc_error_1|y~2_combout\ = \dIN[1]~input_o\ $ (\crc_e|crc_error_1|y~0_combout\ $ (\crc_e|crc_error_1|y~1_combout\ $ (\dIN[23]~input_o\)))

-- pragma translate_off
GENERIC MAP (
	lut_mask => "0110100110010110",
	sum_lutc_input => "datac")
-- pragma translate_on
PORT MAP (
	dataa => \dIN[1]~input_o\,
	datab => \crc_e|crc_error_1|y~0_combout\,
	datac => \crc_e|crc_error_1|y~1_combout\,
	datad => \dIN[23]~input_o\,
	combout => \crc_e|crc_error_1|y~2_combout\);

-- Location: LCCOMB_X29_Y19_N16
\crc_e|crc_16to8|d_8_12|y\ : cycloneiv_lcell_comb
-- Equation(s):
-- \crc_e|crc_16to8|d_8_12|y~combout\ = \dIN[8]~input_o\ $ (\dIN[12]~input_o\)

-- pragma translate_off
GENERIC MAP (
	lut_mask => "0101010110101010",
	sum_lutc_input => "datac")
-- pragma translate_on
PORT MAP (
	dataa => \dIN[8]~input_o\,
	datad => \dIN[12]~input_o\,
	combout => \crc_e|crc_16to8|d_8_12|y~combout\);

-- Location: IOIBUF_X29_Y31_N8
\dIN[10]~input\ : cycloneiv_io_ibuf
-- pragma translate_off
GENERIC MAP (
	bus_hold => "false",
	simulate_z_as => "z")
-- pragma translate_on
PORT MAP (
	i => ww_dIN(10),
	o => \dIN[10]~input_o\);

-- Location: IOIBUF_X26_Y31_N8
\dIN[2]~input\ : cycloneiv_io_ibuf
-- pragma translate_off
GENERIC MAP (
	bus_hold => "false",
	simulate_z_as => "z")
-- pragma translate_on
PORT MAP (
	i => ww_dIN(2),
	o => \dIN[2]~input_o\);

-- Location: IOIBUF_X33_Y25_N1
\dIN[22]~input\ : cycloneiv_io_ibuf
-- pragma translate_off
GENERIC MAP (
	bus_hold => "false",
	simulate_z_as => "z")
-- pragma translate_on
PORT MAP (
	i => ww_dIN(22),
	o => \dIN[22]~input_o\);

-- Location: LCCOMB_X30_Y27_N8
\crc_e|crc_error_2|y~0\ : cycloneiv_lcell_comb
-- Equation(s):
-- \crc_e|crc_error_2|y~0_combout\ = \dIN[17]~input_o\ $ (\dIN[10]~input_o\ $ (\dIN[2]~input_o\ $ (\dIN[22]~input_o\)))

-- pragma translate_off
GENERIC MAP (
	lut_mask => "0110100110010110",
	sum_lutc_input => "datac")
-- pragma translate_on
PORT MAP (
	dataa => \dIN[17]~input_o\,
	datab => \dIN[10]~input_o\,
	datac => \dIN[2]~input_o\,
	datad => \dIN[22]~input_o\,
	combout => \crc_e|crc_error_2|y~0_combout\);

-- Location: LCCOMB_X30_Y27_N10
\crc_e|crc_error_2|y~1\ : cycloneiv_lcell_comb
-- Equation(s):
-- \crc_e|crc_error_2|y~1_combout\ = \crc_e|crc_16to8|d_8_12|y~combout\ $ (\dIN[14]~input_o\ $ (\crc_e|crc_error_2|y~0_combout\ $ (\crc_e|crc_16to8|compos1819_2023|y~combout\)))

-- pragma translate_off
GENERIC MAP (
	lut_mask => "0110100110010110",
	sum_lutc_input => "datac")
-- pragma translate_on
PORT MAP (
	dataa => \crc_e|crc_16to8|d_8_12|y~combout\,
	datab => \dIN[14]~input_o\,
	datac => \crc_e|crc_error_2|y~0_combout\,
	datad => \crc_e|crc_16to8|compos1819_2023|y~combout\,
	combout => \crc_e|crc_error_2|y~1_combout\);

-- Location: LCCOMB_X29_Y19_N10
\crc_e|crc_error_5|y~0\ : cycloneiv_lcell_comb
-- Equation(s):
-- \crc_e|crc_error_5|y~0_combout\ = \dIN[8]~input_o\ $ (\dIN[16]~input_o\ $ (\dIN[17]~input_o\ $ (\dIN[12]~input_o\)))

-- pragma translate_off
GENERIC MAP (
	lut_mask => "0110100110010110",
	sum_lutc_input => "datac")
-- pragma translate_on
PORT MAP (
	dataa => \dIN[8]~input_o\,
	datab => \dIN[16]~input_o\,
	datac => \dIN[17]~input_o\,
	datad => \dIN[12]~input_o\,
	combout => \crc_e|crc_error_5|y~0_combout\);

-- Location: IOIBUF_X29_Y0_N1
\dIN[3]~input\ : cycloneiv_io_ibuf
-- pragma translate_off
GENERIC MAP (
	bus_hold => "false",
	simulate_z_as => "z")
-- pragma translate_on
PORT MAP (
	i => ww_dIN(3),
	o => \dIN[3]~input_o\);

-- Location: LCCOMB_X30_Y27_N28
\crc_e|crc_error_3|y~0\ : cycloneiv_lcell_comb
-- Equation(s):
-- \crc_e|crc_error_3|y~0_combout\ = \dIN[21]~input_o\ $ (\dIN[3]~input_o\ $ (\dIN[9]~input_o\))

-- pragma translate_off
GENERIC MAP (
	lut_mask => "1010010101011010",
	sum_lutc_input => "datac")
-- pragma translate_on
PORT MAP (
	dataa => \dIN[21]~input_o\,
	datac => \dIN[3]~input_o\,
	datad => \dIN[9]~input_o\,
	combout => \crc_e|crc_error_3|y~0_combout\);

-- Location: LCCOMB_X30_Y27_N6
\crc_e|crc_error_3|y\ : cycloneiv_lcell_comb
-- Equation(s):
-- \crc_e|crc_error_3|y~combout\ = \dIN[10]~input_o\ $ (\crc_e|crc_error_5|y~0_combout\ $ (\crc_e|crc_error_3|y~0_combout\))

-- pragma translate_off
GENERIC MAP (
	lut_mask => "1100001100111100",
	sum_lutc_input => "datac")
-- pragma translate_on
PORT MAP (
	datab => \dIN[10]~input_o\,
	datac => \crc_e|crc_error_5|y~0_combout\,
	datad => \crc_e|crc_error_3|y~0_combout\,
	combout => \crc_e|crc_error_3|y~combout\);

-- Location: IOIBUF_X29_Y31_N1
\dIN[4]~input\ : cycloneiv_io_ibuf
-- pragma translate_off
GENERIC MAP (
	bus_hold => "false",
	simulate_z_as => "z")
-- pragma translate_on
PORT MAP (
	i => ww_dIN(4),
	o => \dIN[4]~input_o\);

-- Location: LCCOMB_X30_Y27_N24
\crc_e|crc_error_4|y~0\ : cycloneiv_lcell_comb
-- Equation(s):
-- \crc_e|crc_error_4|y~0_combout\ = \dIN[4]~input_o\ $ (\dIN[10]~input_o\ $ (\dIN[17]~input_o\ $ (\dIN[22]~input_o\)))

-- pragma translate_off
GENERIC MAP (
	lut_mask => "0110100110010110",
	sum_lutc_input => "datac")
-- pragma translate_on
PORT MAP (
	dataa => \dIN[4]~input_o\,
	datab => \dIN[10]~input_o\,
	datac => \dIN[17]~input_o\,
	datad => \dIN[22]~input_o\,
	combout => \crc_e|crc_error_4|y~0_combout\);

-- Location: LCCOMB_X30_Y27_N18
\crc_e|crc_error_4|y\ : cycloneiv_lcell_comb
-- Equation(s):
-- \crc_e|crc_error_4|y~combout\ = \dIN[9]~input_o\ $ (\crc_e|crc_16to8|d_11_13|y~combout\ $ (\dIN[18]~input_o\ $ (\crc_e|crc_error_4|y~0_combout\)))

-- pragma translate_off
GENERIC MAP (
	lut_mask => "0110100110010110",
	sum_lutc_input => "datac")
-- pragma translate_on
PORT MAP (
	dataa => \dIN[9]~input_o\,
	datab => \crc_e|crc_16to8|d_11_13|y~combout\,
	datac => \dIN[18]~input_o\,
	datad => \crc_e|crc_error_4|y~0_combout\,
	combout => \crc_e|crc_error_4|y~combout\);

-- Location: IOIBUF_X33_Y27_N1
\dIN[5]~input\ : cycloneiv_io_ibuf
-- pragma translate_off
GENERIC MAP (
	bus_hold => "false",
	simulate_z_as => "z")
-- pragma translate_on
PORT MAP (
	i => ww_dIN(5),
	o => \dIN[5]~input_o\);

-- Location: LCCOMB_X30_Y27_N12
\crc_e|crc_error_5|y~1\ : cycloneiv_lcell_comb
-- Equation(s):
-- \crc_e|crc_error_5|y~1_combout\ = \dIN[5]~input_o\ $ (\dIN[14]~input_o\ $ (\dIN[15]~input_o\ $ (\dIN[13]~input_o\)))

-- pragma translate_off
GENERIC MAP (
	lut_mask => "0110100110010110",
	sum_lutc_input => "datac")
-- pragma translate_on
PORT MAP (
	dataa => \dIN[5]~input_o\,
	datab => \dIN[14]~input_o\,
	datac => \dIN[15]~input_o\,
	datad => \dIN[13]~input_o\,
	combout => \crc_e|crc_error_5|y~1_combout\);

-- Location: LCCOMB_X30_Y27_N14
\crc_e|crc_error_5|y~2\ : cycloneiv_lcell_comb
-- Equation(s):
-- \crc_e|crc_error_5|y~2_combout\ = \crc_e|crc_error_5|y~1_combout\ $ (\dIN[10]~input_o\ $ (\crc_e|crc_error_5|y~0_combout\ $ (\dIN[20]~input_o\)))

-- pragma translate_off
GENERIC MAP (
	lut_mask => "0110100110010110",
	sum_lutc_input => "datac")
-- pragma translate_on
PORT MAP (
	dataa => \crc_e|crc_error_5|y~1_combout\,
	datab => \dIN[10]~input_o\,
	datac => \crc_e|crc_error_5|y~0_combout\,
	datad => \dIN[20]~input_o\,
	combout => \crc_e|crc_error_5|y~2_combout\);

-- Location: IOIBUF_X26_Y31_N1
\dIN[6]~input\ : cycloneiv_io_ibuf
-- pragma translate_off
GENERIC MAP (
	bus_hold => "false",
	simulate_z_as => "z")
-- pragma translate_on
PORT MAP (
	i => ww_dIN(6),
	o => \dIN[6]~input_o\);

-- Location: LCCOMB_X30_Y27_N0
\crc_e|crc_error_6|y~0\ : cycloneiv_lcell_comb
-- Equation(s):
-- \crc_e|crc_error_6|y~0_combout\ = \dIN[19]~input_o\ $ (\dIN[23]~input_o\ $ (\dIN[6]~input_o\ $ (\dIN[20]~input_o\)))

-- pragma translate_off
GENERIC MAP (
	lut_mask => "0110100110010110",
	sum_lutc_input => "datac")
-- pragma translate_on
PORT MAP (
	dataa => \dIN[19]~input_o\,
	datab => \dIN[23]~input_o\,
	datac => \dIN[6]~input_o\,
	datad => \dIN[20]~input_o\,
	combout => \crc_e|crc_error_6|y~0_combout\);

-- Location: LCCOMB_X30_Y27_N26
\crc_e|crc_error_6|y~1\ : cycloneiv_lcell_comb
-- Equation(s):
-- \crc_e|crc_error_6|y~1_combout\ = \crc_e|crc_16to8|d_16_17|y~0_combout\ $ (\dIN[14]~input_o\ $ (\dIN[15]~input_o\ $ (\crc_e|crc_error_6|y~0_combout\)))

-- pragma translate_off
GENERIC MAP (
	lut_mask => "0110100110010110",
	sum_lutc_input => "datac")
-- pragma translate_on
PORT MAP (
	dataa => \crc_e|crc_16to8|d_16_17|y~0_combout\,
	datab => \dIN[14]~input_o\,
	datac => \dIN[15]~input_o\,
	datad => \crc_e|crc_error_6|y~0_combout\,
	combout => \crc_e|crc_error_6|y~1_combout\);

-- Location: LCCOMB_X30_Y27_N20
\crc_e|CHECK~0\ : cycloneiv_lcell_comb
-- Equation(s):
-- \crc_e|CHECK~0_combout\ = (\crc_e|crc_error_1|y~2_combout\) # ((\crc_e|crc_error_0|y~1_combout\) # ((\crc_e|crc_error_5|y~2_combout\) # (\crc_e|crc_error_2|y~1_combout\)))

-- pragma translate_off
GENERIC MAP (
	lut_mask => "1111111111111110",
	sum_lutc_input => "datac")
-- pragma translate_on
PORT MAP (
	dataa => \crc_e|crc_error_1|y~2_combout\,
	datab => \crc_e|crc_error_0|y~1_combout\,
	datac => \crc_e|crc_error_5|y~2_combout\,
	datad => \crc_e|crc_error_2|y~1_combout\,
	combout => \crc_e|CHECK~0_combout\);

-- Location: LCCOMB_X30_Y27_N30
\crc_e|CHECK\ : cycloneiv_lcell_comb
-- Equation(s):
-- \crc_e|CHECK~combout\ = (\crc_e|crc_error_4|y~combout\) # ((\crc_e|CHECK~0_combout\) # ((\crc_e|crc_error_6|y~1_combout\) # (\crc_e|crc_error_3|y~combout\)))

-- pragma translate_off
GENERIC MAP (
	lut_mask => "1111111111111110",
	sum_lutc_input => "datac")
-- pragma translate_on
PORT MAP (
	dataa => \crc_e|crc_error_4|y~combout\,
	datab => \crc_e|CHECK~0_combout\,
	datac => \crc_e|crc_error_6|y~1_combout\,
	datad => \crc_e|crc_error_3|y~combout\,
	combout => \crc_e|CHECK~combout\);

-- Location: IOIBUF_X16_Y0_N8
\dIN[7]~input\ : cycloneiv_io_ibuf
-- pragma translate_off
GENERIC MAP (
	bus_hold => "false",
	simulate_z_as => "z")
-- pragma translate_on
PORT MAP (
	i => ww_dIN(7),
	o => \dIN[7]~input_o\);

ww_CRC_err(0) <= \CRC_err[0]~output_o\;

ww_CRC_err(1) <= \CRC_err[1]~output_o\;

ww_CRC_err(2) <= \CRC_err[2]~output_o\;

ww_CRC_err(3) <= \CRC_err[3]~output_o\;

ww_CRC_err(4) <= \CRC_err[4]~output_o\;

ww_CRC_err(5) <= \CRC_err[5]~output_o\;

ww_CRC_err(6) <= \CRC_err[6]~output_o\;

ww_CRC_err(7) <= \CRC_err[7]~output_o\;

ww_Check <= \Check~output_o\;
END structure;


