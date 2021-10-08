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

-- DATE "12/15/2020 17:07:58"

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

ENTITY 	CRC_ENCODER_STD IS
    PORT (
	dIN : IN std_logic_vector(15 DOWNTO 0);
	CRC : BUFFER std_logic_vector(7 DOWNTO 0)
	);
END CRC_ENCODER_STD;

-- Design Ports Information
-- CRC[0]	=>  Location: PIN_L5,	 I/O Standard: 2.5 V,	 Current Strength: Default
-- CRC[1]	=>  Location: PIN_K12,	 I/O Standard: 2.5 V,	 Current Strength: Default
-- CRC[2]	=>  Location: PIN_N9,	 I/O Standard: 2.5 V,	 Current Strength: Default
-- CRC[3]	=>  Location: PIN_L7,	 I/O Standard: 2.5 V,	 Current Strength: Default
-- CRC[4]	=>  Location: PIN_M9,	 I/O Standard: 2.5 V,	 Current Strength: Default
-- CRC[5]	=>  Location: PIN_K11,	 I/O Standard: 2.5 V,	 Current Strength: Default
-- CRC[6]	=>  Location: PIN_N8,	 I/O Standard: 2.5 V,	 Current Strength: Default
-- CRC[7]	=>  Location: PIN_L9,	 I/O Standard: 2.5 V,	 Current Strength: Default
-- dIN[10]	=>  Location: PIN_N10,	 I/O Standard: 2.5 V,	 Current Strength: Default
-- dIN[11]	=>  Location: PIN_G13,	 I/O Standard: 2.5 V,	 Current Strength: Default
-- dIN[12]	=>  Location: PIN_H13,	 I/O Standard: 2.5 V,	 Current Strength: Default
-- dIN[15]	=>  Location: PIN_K9,	 I/O Standard: 2.5 V,	 Current Strength: Default
-- dIN[3]	=>  Location: PIN_M7,	 I/O Standard: 2.5 V,	 Current Strength: Default
-- dIN[5]	=>  Location: PIN_N7,	 I/O Standard: 2.5 V,	 Current Strength: Default
-- dIN[8]	=>  Location: PIN_N13,	 I/O Standard: 2.5 V,	 Current Strength: Default
-- dIN[9]	=>  Location: PIN_K8,	 I/O Standard: 2.5 V,	 Current Strength: Default
-- dIN[0]	=>  Location: PIN_E13,	 I/O Standard: 2.5 V,	 Current Strength: Default
-- dIN[7]	=>  Location: PIN_L11,	 I/O Standard: 2.5 V,	 Current Strength: Default
-- dIN[4]	=>  Location: PIN_M13,	 I/O Standard: 2.5 V,	 Current Strength: Default
-- dIN[1]	=>  Location: PIN_N11,	 I/O Standard: 2.5 V,	 Current Strength: Default
-- dIN[13]	=>  Location: PIN_M11,	 I/O Standard: 2.5 V,	 Current Strength: Default
-- dIN[6]	=>  Location: PIN_L13,	 I/O Standard: 2.5 V,	 Current Strength: Default
-- dIN[2]	=>  Location: PIN_N12,	 I/O Standard: 2.5 V,	 Current Strength: Default
-- dIN[14]	=>  Location: PIN_K10,	 I/O Standard: 2.5 V,	 Current Strength: Default


ARCHITECTURE structure OF CRC_ENCODER_STD IS
SIGNAL gnd : std_logic := '0';
SIGNAL vcc : std_logic := '1';
SIGNAL unknown : std_logic := 'X';
SIGNAL devoe : std_logic := '1';
SIGNAL devclrn : std_logic := '1';
SIGNAL devpor : std_logic := '1';
SIGNAL ww_devoe : std_logic;
SIGNAL ww_devclrn : std_logic;
SIGNAL ww_devpor : std_logic;
SIGNAL ww_dIN : std_logic_vector(15 DOWNTO 0);
SIGNAL ww_CRC : std_logic_vector(7 DOWNTO 0);
SIGNAL \CRC[0]~output_o\ : std_logic;
SIGNAL \CRC[1]~output_o\ : std_logic;
SIGNAL \CRC[2]~output_o\ : std_logic;
SIGNAL \CRC[3]~output_o\ : std_logic;
SIGNAL \CRC[4]~output_o\ : std_logic;
SIGNAL \CRC[5]~output_o\ : std_logic;
SIGNAL \CRC[6]~output_o\ : std_logic;
SIGNAL \CRC[7]~output_o\ : std_logic;
SIGNAL \dIN[7]~input_o\ : std_logic;
SIGNAL \dIN[15]~input_o\ : std_logic;
SIGNAL \dIN[12]~input_o\ : std_logic;
SIGNAL \dIN[11]~input_o\ : std_logic;
SIGNAL \dIN[10]~input_o\ : std_logic;
SIGNAL \crc_16to8|compos1819_2023|y~combout\ : std_logic;
SIGNAL \dIN[5]~input_o\ : std_logic;
SIGNAL \dIN[3]~input_o\ : std_logic;
SIGNAL \dIN[8]~input_o\ : std_logic;
SIGNAL \dIN[9]~input_o\ : std_logic;
SIGNAL \crc_16to8|x0|y~0_combout\ : std_logic;
SIGNAL \dIN[0]~input_o\ : std_logic;
SIGNAL \crc_16to8|x0|y~1_combout\ : std_logic;
SIGNAL \dIN[13]~input_o\ : std_logic;
SIGNAL \dIN[6]~input_o\ : std_logic;
SIGNAL \dIN[1]~input_o\ : std_logic;
SIGNAL \crc_16to8|x1|y~0_combout\ : std_logic;
SIGNAL \dIN[4]~input_o\ : std_logic;
SIGNAL \crc_16to8|d_8_12|y~combout\ : std_logic;
SIGNAL \crc_16to8|d_11_13|y~combout\ : std_logic;
SIGNAL \crc_16to8|x1|y~1_combout\ : std_logic;
SIGNAL \dIN[2]~input_o\ : std_logic;
SIGNAL \dIN[14]~input_o\ : std_logic;
SIGNAL \crc_16to8|x4|y~0_combout\ : std_logic;
SIGNAL \crc_16to8|x2|y~0_combout\ : std_logic;
SIGNAL \crc_16to8|x3|y~2_combout\ : std_logic;
SIGNAL \crc_16to8|x3|y~combout\ : std_logic;
SIGNAL \crc_16to8|x4|y~combout\ : std_logic;
SIGNAL \crc_16to8|x5|y~0_combout\ : std_logic;
SIGNAL \crc_16to8|x6|y~0_combout\ : std_logic;
SIGNAL \crc_16to8|x5|y~1_combout\ : std_logic;
SIGNAL \crc_16to8|x6|y~1_combout\ : std_logic;
SIGNAL \crc_16to8|x7|y~0_combout\ : std_logic;
SIGNAL \crc_16to8|x7|y~1_combout\ : std_logic;

COMPONENT hard_block
    PORT (
	devoe : IN std_logic;
	devclrn : IN std_logic;
	devpor : IN std_logic);
END COMPONENT;

BEGIN

ww_dIN <= dIN;
CRC <= ww_CRC;
ww_devoe <= devoe;
ww_devclrn <= devclrn;
ww_devpor <= devpor;
auto_generated_inst : hard_block
PORT MAP (
	devoe => ww_devoe,
	devclrn => ww_devclrn,
	devpor => ww_devpor);

-- Location: IOOBUF_X14_Y0_N9
\CRC[0]~output\ : cycloneiv_io_obuf
-- pragma translate_off
GENERIC MAP (
	bus_hold => "false",
	open_drain_output => "false")
-- pragma translate_on
PORT MAP (
	i => \crc_16to8|x0|y~1_combout\,
	devoe => ww_devoe,
	o => \CRC[0]~output_o\);

-- Location: IOOBUF_X33_Y11_N9
\CRC[1]~output\ : cycloneiv_io_obuf
-- pragma translate_off
GENERIC MAP (
	bus_hold => "false",
	open_drain_output => "false")
-- pragma translate_on
PORT MAP (
	i => \crc_16to8|x1|y~1_combout\,
	devoe => ww_devoe,
	o => \CRC[1]~output_o\);

-- Location: IOOBUF_X20_Y0_N2
\CRC[2]~output\ : cycloneiv_io_obuf
-- pragma translate_off
GENERIC MAP (
	bus_hold => "false",
	open_drain_output => "false")
-- pragma translate_on
PORT MAP (
	i => \crc_16to8|x2|y~0_combout\,
	devoe => ww_devoe,
	o => \CRC[2]~output_o\);

-- Location: IOOBUF_X14_Y0_N2
\CRC[3]~output\ : cycloneiv_io_obuf
-- pragma translate_off
GENERIC MAP (
	bus_hold => "false",
	open_drain_output => "false")
-- pragma translate_on
PORT MAP (
	i => \crc_16to8|x3|y~combout\,
	devoe => ww_devoe,
	o => \CRC[3]~output_o\);

-- Location: IOOBUF_X24_Y0_N2
\CRC[4]~output\ : cycloneiv_io_obuf
-- pragma translate_off
GENERIC MAP (
	bus_hold => "false",
	open_drain_output => "false")
-- pragma translate_on
PORT MAP (
	i => \crc_16to8|x4|y~combout\,
	devoe => ww_devoe,
	o => \CRC[4]~output_o\);

-- Location: IOOBUF_X33_Y11_N2
\CRC[5]~output\ : cycloneiv_io_obuf
-- pragma translate_off
GENERIC MAP (
	bus_hold => "false",
	open_drain_output => "false")
-- pragma translate_on
PORT MAP (
	i => \crc_16to8|x5|y~1_combout\,
	devoe => ww_devoe,
	o => \CRC[5]~output_o\);

-- Location: IOOBUF_X20_Y0_N9
\CRC[6]~output\ : cycloneiv_io_obuf
-- pragma translate_off
GENERIC MAP (
	bus_hold => "false",
	open_drain_output => "false")
-- pragma translate_on
PORT MAP (
	i => \crc_16to8|x6|y~1_combout\,
	devoe => ww_devoe,
	o => \CRC[6]~output_o\);

-- Location: IOOBUF_X24_Y0_N9
\CRC[7]~output\ : cycloneiv_io_obuf
-- pragma translate_off
GENERIC MAP (
	bus_hold => "false",
	open_drain_output => "false")
-- pragma translate_on
PORT MAP (
	i => \crc_16to8|x7|y~1_combout\,
	devoe => ww_devoe,
	o => \CRC[7]~output_o\);

-- Location: IOIBUF_X31_Y0_N1
\dIN[7]~input\ : cycloneiv_io_ibuf
-- pragma translate_off
GENERIC MAP (
	bus_hold => "false",
	simulate_z_as => "z")
-- pragma translate_on
PORT MAP (
	i => ww_dIN(7),
	o => \dIN[7]~input_o\);

-- Location: IOIBUF_X22_Y0_N1
\dIN[15]~input\ : cycloneiv_io_ibuf
-- pragma translate_off
GENERIC MAP (
	bus_hold => "false",
	simulate_z_as => "z")
-- pragma translate_on
PORT MAP (
	i => ww_dIN(15),
	o => \dIN[15]~input_o\);

-- Location: IOIBUF_X33_Y16_N15
\dIN[12]~input\ : cycloneiv_io_ibuf
-- pragma translate_off
GENERIC MAP (
	bus_hold => "false",
	simulate_z_as => "z")
-- pragma translate_on
PORT MAP (
	i => ww_dIN(12),
	o => \dIN[12]~input_o\);

-- Location: IOIBUF_X33_Y16_N22
\dIN[11]~input\ : cycloneiv_io_ibuf
-- pragma translate_off
GENERIC MAP (
	bus_hold => "false",
	simulate_z_as => "z")
-- pragma translate_on
PORT MAP (
	i => ww_dIN(11),
	o => \dIN[11]~input_o\);

-- Location: IOIBUF_X26_Y0_N8
\dIN[10]~input\ : cycloneiv_io_ibuf
-- pragma translate_off
GENERIC MAP (
	bus_hold => "false",
	simulate_z_as => "z")
-- pragma translate_on
PORT MAP (
	i => ww_dIN(10),
	o => \dIN[10]~input_o\);

-- Location: LCCOMB_X26_Y4_N16
\crc_16to8|compos1819_2023|y\ : cycloneiv_lcell_comb
-- Equation(s):
-- \crc_16to8|compos1819_2023|y~combout\ = \dIN[15]~input_o\ $ (\dIN[12]~input_o\ $ (\dIN[11]~input_o\ $ (\dIN[10]~input_o\)))

-- pragma translate_off
GENERIC MAP (
	lut_mask => "0110100110010110",
	sum_lutc_input => "datac")
-- pragma translate_on
PORT MAP (
	dataa => \dIN[15]~input_o\,
	datab => \dIN[12]~input_o\,
	datac => \dIN[11]~input_o\,
	datad => \dIN[10]~input_o\,
	combout => \crc_16to8|compos1819_2023|y~combout\);

-- Location: IOIBUF_X16_Y0_N1
\dIN[5]~input\ : cycloneiv_io_ibuf
-- pragma translate_off
GENERIC MAP (
	bus_hold => "false",
	simulate_z_as => "z")
-- pragma translate_on
PORT MAP (
	i => ww_dIN(5),
	o => \dIN[5]~input_o\);

-- Location: IOIBUF_X16_Y0_N8
\dIN[3]~input\ : cycloneiv_io_ibuf
-- pragma translate_off
GENERIC MAP (
	bus_hold => "false",
	simulate_z_as => "z")
-- pragma translate_on
PORT MAP (
	i => ww_dIN(3),
	o => \dIN[3]~input_o\);

-- Location: IOIBUF_X33_Y10_N8
\dIN[8]~input\ : cycloneiv_io_ibuf
-- pragma translate_off
GENERIC MAP (
	bus_hold => "false",
	simulate_z_as => "z")
-- pragma translate_on
PORT MAP (
	i => ww_dIN(8),
	o => \dIN[8]~input_o\);

-- Location: IOIBUF_X22_Y0_N8
\dIN[9]~input\ : cycloneiv_io_ibuf
-- pragma translate_off
GENERIC MAP (
	bus_hold => "false",
	simulate_z_as => "z")
-- pragma translate_on
PORT MAP (
	i => ww_dIN(9),
	o => \dIN[9]~input_o\);

-- Location: LCCOMB_X26_Y4_N26
\crc_16to8|x0|y~0\ : cycloneiv_lcell_comb
-- Equation(s):
-- \crc_16to8|x0|y~0_combout\ = \dIN[5]~input_o\ $ (\dIN[3]~input_o\ $ (\dIN[8]~input_o\ $ (\dIN[9]~input_o\)))

-- pragma translate_off
GENERIC MAP (
	lut_mask => "0110100110010110",
	sum_lutc_input => "datac")
-- pragma translate_on
PORT MAP (
	dataa => \dIN[5]~input_o\,
	datab => \dIN[3]~input_o\,
	datac => \dIN[8]~input_o\,
	datad => \dIN[9]~input_o\,
	combout => \crc_16to8|x0|y~0_combout\);

-- Location: IOIBUF_X33_Y25_N8
\dIN[0]~input\ : cycloneiv_io_ibuf
-- pragma translate_off
GENERIC MAP (
	bus_hold => "false",
	simulate_z_as => "z")
-- pragma translate_on
PORT MAP (
	i => ww_dIN(0),
	o => \dIN[0]~input_o\);

-- Location: LCCOMB_X26_Y4_N28
\crc_16to8|x0|y~1\ : cycloneiv_lcell_comb
-- Equation(s):
-- \crc_16to8|x0|y~1_combout\ = \dIN[7]~input_o\ $ (\crc_16to8|compos1819_2023|y~combout\ $ (\crc_16to8|x0|y~0_combout\ $ (\dIN[0]~input_o\)))

-- pragma translate_off
GENERIC MAP (
	lut_mask => "0110100110010110",
	sum_lutc_input => "datac")
-- pragma translate_on
PORT MAP (
	dataa => \dIN[7]~input_o\,
	datab => \crc_16to8|compos1819_2023|y~combout\,
	datac => \crc_16to8|x0|y~0_combout\,
	datad => \dIN[0]~input_o\,
	combout => \crc_16to8|x0|y~1_combout\);

-- Location: IOIBUF_X29_Y0_N8
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
\dIN[6]~input\ : cycloneiv_io_ibuf
-- pragma translate_off
GENERIC MAP (
	bus_hold => "false",
	simulate_z_as => "z")
-- pragma translate_on
PORT MAP (
	i => ww_dIN(6),
	o => \dIN[6]~input_o\);

-- Location: IOIBUF_X26_Y0_N1
\dIN[1]~input\ : cycloneiv_io_ibuf
-- pragma translate_off
GENERIC MAP (
	bus_hold => "false",
	simulate_z_as => "z")
-- pragma translate_on
PORT MAP (
	i => ww_dIN(1),
	o => \dIN[1]~input_o\);

-- Location: LCCOMB_X26_Y4_N8
\crc_16to8|x1|y~0\ : cycloneiv_lcell_comb
-- Equation(s):
-- \crc_16to8|x1|y~0_combout\ = \dIN[7]~input_o\ $ (\dIN[13]~input_o\ $ (\dIN[6]~input_o\ $ (\dIN[1]~input_o\)))

-- pragma translate_off
GENERIC MAP (
	lut_mask => "0110100110010110",
	sum_lutc_input => "datac")
-- pragma translate_on
PORT MAP (
	dataa => \dIN[7]~input_o\,
	datab => \dIN[13]~input_o\,
	datac => \dIN[6]~input_o\,
	datad => \dIN[1]~input_o\,
	combout => \crc_16to8|x1|y~0_combout\);

-- Location: IOIBUF_X33_Y10_N1
\dIN[4]~input\ : cycloneiv_io_ibuf
-- pragma translate_off
GENERIC MAP (
	bus_hold => "false",
	simulate_z_as => "z")
-- pragma translate_on
PORT MAP (
	i => ww_dIN(4),
	o => \dIN[4]~input_o\);

-- Location: LCCOMB_X26_Y4_N22
\crc_16to8|d_8_12|y\ : cycloneiv_lcell_comb
-- Equation(s):
-- \crc_16to8|d_8_12|y~combout\ = \dIN[4]~input_o\ $ (\dIN[0]~input_o\)

-- pragma translate_off
GENERIC MAP (
	lut_mask => "0000111111110000",
	sum_lutc_input => "datac")
-- pragma translate_on
PORT MAP (
	datac => \dIN[4]~input_o\,
	datad => \dIN[0]~input_o\,
	combout => \crc_16to8|d_8_12|y~combout\);

-- Location: LCCOMB_X26_Y2_N0
\crc_16to8|d_11_13|y\ : cycloneiv_lcell_comb
-- Equation(s):
-- \crc_16to8|d_11_13|y~combout\ = \dIN[5]~input_o\ $ (\dIN[3]~input_o\)

-- pragma translate_off
GENERIC MAP (
	lut_mask => "0101101001011010",
	sum_lutc_input => "datac")
-- pragma translate_on
PORT MAP (
	dataa => \dIN[5]~input_o\,
	datac => \dIN[3]~input_o\,
	combout => \crc_16to8|d_11_13|y~combout\);

-- Location: LCCOMB_X26_Y4_N10
\crc_16to8|x1|y~1\ : cycloneiv_lcell_comb
-- Equation(s):
-- \crc_16to8|x1|y~1_combout\ = \dIN[15]~input_o\ $ (\crc_16to8|x1|y~0_combout\ $ (\crc_16to8|d_8_12|y~combout\ $ (\crc_16to8|d_11_13|y~combout\)))

-- pragma translate_off
GENERIC MAP (
	lut_mask => "0110100110010110",
	sum_lutc_input => "datac")
-- pragma translate_on
PORT MAP (
	dataa => \dIN[15]~input_o\,
	datab => \crc_16to8|x1|y~0_combout\,
	datac => \crc_16to8|d_8_12|y~combout\,
	datad => \crc_16to8|d_11_13|y~combout\,
	combout => \crc_16to8|x1|y~1_combout\);

-- Location: IOIBUF_X29_Y0_N1
\dIN[2]~input\ : cycloneiv_io_ibuf
-- pragma translate_off
GENERIC MAP (
	bus_hold => "false",
	simulate_z_as => "z")
-- pragma translate_on
PORT MAP (
	i => ww_dIN(2),
	o => \dIN[2]~input_o\);

-- Location: IOIBUF_X31_Y0_N8
\dIN[14]~input\ : cycloneiv_io_ibuf
-- pragma translate_off
GENERIC MAP (
	bus_hold => "false",
	simulate_z_as => "z")
-- pragma translate_on
PORT MAP (
	i => ww_dIN(14),
	o => \dIN[14]~input_o\);

-- Location: LCCOMB_X26_Y4_N4
\crc_16to8|x4|y~0\ : cycloneiv_lcell_comb
-- Equation(s):
-- \crc_16to8|x4|y~0_combout\ = \dIN[9]~input_o\ $ (\dIN[2]~input_o\ $ (\dIN[14]~input_o\))

-- pragma translate_off
GENERIC MAP (
	lut_mask => "1010010101011010",
	sum_lutc_input => "datac")
-- pragma translate_on
PORT MAP (
	dataa => \dIN[9]~input_o\,
	datac => \dIN[2]~input_o\,
	datad => \dIN[14]~input_o\,
	combout => \crc_16to8|x4|y~0_combout\);

-- Location: LCCOMB_X26_Y4_N6
\crc_16to8|x2|y~0\ : cycloneiv_lcell_comb
-- Equation(s):
-- \crc_16to8|x2|y~0_combout\ = \crc_16to8|d_8_12|y~combout\ $ (\crc_16to8|compos1819_2023|y~combout\ $ (\dIN[6]~input_o\ $ (\crc_16to8|x4|y~0_combout\)))

-- pragma translate_off
GENERIC MAP (
	lut_mask => "0110100110010110",
	sum_lutc_input => "datac")
-- pragma translate_on
PORT MAP (
	dataa => \crc_16to8|d_8_12|y~combout\,
	datab => \crc_16to8|compos1819_2023|y~combout\,
	datac => \dIN[6]~input_o\,
	datad => \crc_16to8|x4|y~0_combout\,
	combout => \crc_16to8|x2|y~0_combout\);

-- Location: LCCOMB_X26_Y4_N2
\crc_16to8|x3|y~2\ : cycloneiv_lcell_comb
-- Equation(s):
-- \crc_16to8|x3|y~2_combout\ = \dIN[9]~input_o\ $ (\dIN[1]~input_o\ $ (\dIN[8]~input_o\ $ (\dIN[13]~input_o\)))

-- pragma translate_off
GENERIC MAP (
	lut_mask => "0110100110010110",
	sum_lutc_input => "datac")
-- pragma translate_on
PORT MAP (
	dataa => \dIN[9]~input_o\,
	datab => \dIN[1]~input_o\,
	datac => \dIN[8]~input_o\,
	datad => \dIN[13]~input_o\,
	combout => \crc_16to8|x3|y~2_combout\);

-- Location: LCCOMB_X26_Y4_N30
\crc_16to8|x3|y\ : cycloneiv_lcell_comb
-- Equation(s):
-- \crc_16to8|x3|y~combout\ = \dIN[2]~input_o\ $ (\crc_16to8|x3|y~2_combout\ $ (\dIN[4]~input_o\ $ (\dIN[0]~input_o\)))

-- pragma translate_off
GENERIC MAP (
	lut_mask => "0110100110010110",
	sum_lutc_input => "datac")
-- pragma translate_on
PORT MAP (
	dataa => \dIN[2]~input_o\,
	datab => \crc_16to8|x3|y~2_combout\,
	datac => \dIN[4]~input_o\,
	datad => \dIN[0]~input_o\,
	combout => \crc_16to8|x3|y~combout\);

-- Location: LCCOMB_X26_Y2_N26
\crc_16to8|x4|y\ : cycloneiv_lcell_comb
-- Equation(s):
-- \crc_16to8|x4|y~combout\ = \dIN[10]~input_o\ $ (\crc_16to8|d_11_13|y~combout\ $ (\dIN[1]~input_o\ $ (\crc_16to8|x4|y~0_combout\)))

-- pragma translate_off
GENERIC MAP (
	lut_mask => "0110100110010110",
	sum_lutc_input => "datac")
-- pragma translate_on
PORT MAP (
	dataa => \dIN[10]~input_o\,
	datab => \crc_16to8|d_11_13|y~combout\,
	datac => \dIN[1]~input_o\,
	datad => \crc_16to8|x4|y~0_combout\,
	combout => \crc_16to8|x4|y~combout\);

-- Location: LCCOMB_X26_Y4_N0
\crc_16to8|x5|y~0\ : cycloneiv_lcell_comb
-- Equation(s):
-- \crc_16to8|x5|y~0_combout\ = \dIN[2]~input_o\ $ (\dIN[4]~input_o\ $ (\dIN[0]~input_o\))

-- pragma translate_off
GENERIC MAP (
	lut_mask => "1010010101011010",
	sum_lutc_input => "datac")
-- pragma translate_on
PORT MAP (
	dataa => \dIN[2]~input_o\,
	datac => \dIN[4]~input_o\,
	datad => \dIN[0]~input_o\,
	combout => \crc_16to8|x5|y~0_combout\);

-- Location: LCCOMB_X26_Y4_N20
\crc_16to8|x6|y~0\ : cycloneiv_lcell_comb
-- Equation(s):
-- \crc_16to8|x6|y~0_combout\ = \dIN[7]~input_o\ $ (\dIN[6]~input_o\ $ (\dIN[8]~input_o\ $ (\dIN[9]~input_o\)))

-- pragma translate_off
GENERIC MAP (
	lut_mask => "0110100110010110",
	sum_lutc_input => "datac")
-- pragma translate_on
PORT MAP (
	dataa => \dIN[7]~input_o\,
	datab => \dIN[6]~input_o\,
	datac => \dIN[8]~input_o\,
	datad => \dIN[9]~input_o\,
	combout => \crc_16to8|x6|y~0_combout\);

-- Location: LCCOMB_X26_Y4_N14
\crc_16to8|x5|y~1\ : cycloneiv_lcell_comb
-- Equation(s):
-- \crc_16to8|x5|y~1_combout\ = \dIN[5]~input_o\ $ (\crc_16to8|x5|y~0_combout\ $ (\dIN[12]~input_o\ $ (\crc_16to8|x6|y~0_combout\)))

-- pragma translate_off
GENERIC MAP (
	lut_mask => "0110100110010110",
	sum_lutc_input => "datac")
-- pragma translate_on
PORT MAP (
	dataa => \dIN[5]~input_o\,
	datab => \crc_16to8|x5|y~0_combout\,
	datac => \dIN[12]~input_o\,
	datad => \crc_16to8|x6|y~0_combout\,
	combout => \crc_16to8|x5|y~1_combout\);

-- Location: LCCOMB_X26_Y4_N24
\crc_16to8|x6|y~1\ : cycloneiv_lcell_comb
-- Equation(s):
-- \crc_16to8|x6|y~1_combout\ = \dIN[15]~input_o\ $ (\dIN[12]~input_o\ $ (\dIN[11]~input_o\ $ (\crc_16to8|x6|y~0_combout\)))

-- pragma translate_off
GENERIC MAP (
	lut_mask => "0110100110010110",
	sum_lutc_input => "datac")
-- pragma translate_on
PORT MAP (
	dataa => \dIN[15]~input_o\,
	datab => \dIN[12]~input_o\,
	datac => \dIN[11]~input_o\,
	datad => \crc_16to8|x6|y~0_combout\,
	combout => \crc_16to8|x6|y~1_combout\);

-- Location: LCCOMB_X26_Y4_N18
\crc_16to8|x7|y~0\ : cycloneiv_lcell_comb
-- Equation(s):
-- \crc_16to8|x7|y~0_combout\ = \dIN[11]~input_o\ $ (\dIN[10]~input_o\ $ (\dIN[2]~input_o\ $ (\dIN[14]~input_o\)))

-- pragma translate_off
GENERIC MAP (
	lut_mask => "0110100110010110",
	sum_lutc_input => "datac")
-- pragma translate_on
PORT MAP (
	dataa => \dIN[11]~input_o\,
	datab => \dIN[10]~input_o\,
	datac => \dIN[2]~input_o\,
	datad => \dIN[14]~input_o\,
	combout => \crc_16to8|x7|y~0_combout\);

-- Location: LCCOMB_X26_Y4_N12
\crc_16to8|x7|y~1\ : cycloneiv_lcell_comb
-- Equation(s):
-- \crc_16to8|x7|y~1_combout\ = \crc_16to8|x7|y~0_combout\ $ (\dIN[4]~input_o\ $ (\crc_16to8|x6|y~0_combout\))

-- pragma translate_off
GENERIC MAP (
	lut_mask => "1100001100111100",
	sum_lutc_input => "datac")
-- pragma translate_on
PORT MAP (
	datab => \crc_16to8|x7|y~0_combout\,
	datac => \dIN[4]~input_o\,
	datad => \crc_16to8|x6|y~0_combout\,
	combout => \crc_16to8|x7|y~1_combout\);

ww_CRC(0) <= \CRC[0]~output_o\;

ww_CRC(1) <= \CRC[1]~output_o\;

ww_CRC(2) <= \CRC[2]~output_o\;

ww_CRC(3) <= \CRC[3]~output_o\;

ww_CRC(4) <= \CRC[4]~output_o\;

ww_CRC(5) <= \CRC[5]~output_o\;

ww_CRC(6) <= \CRC[6]~output_o\;

ww_CRC(7) <= \CRC[7]~output_o\;
END structure;


