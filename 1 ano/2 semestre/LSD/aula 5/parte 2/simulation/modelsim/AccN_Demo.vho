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

-- DATE "03/21/2018 11:34:50"

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


LIBRARY ALTERA;
LIBRARY CYCLONEIVE;
LIBRARY IEEE;
USE ALTERA.ALTERA_PRIMITIVES_COMPONENTS.ALL;
USE CYCLONEIVE.CYCLONEIVE_COMPONENTS.ALL;
USE IEEE.STD_LOGIC_1164.ALL;

ENTITY 	AccN IS
    PORT (
	clk : IN std_logic;
	reset : IN std_logic;
	enable : IN std_logic;
	dataIn : IN std_logic_vector(3 DOWNTO 0);
	dataOut : BUFFER std_logic_vector(3 DOWNTO 0)
	);
END AccN;

-- Design Ports Information
-- dataOut[0]	=>  Location: PIN_R3,	 I/O Standard: 2.5 V,	 Current Strength: Default
-- dataOut[1]	=>  Location: PIN_R4,	 I/O Standard: 2.5 V,	 Current Strength: Default
-- dataOut[2]	=>  Location: PIN_T4,	 I/O Standard: 2.5 V,	 Current Strength: Default
-- dataOut[3]	=>  Location: PIN_R5,	 I/O Standard: 2.5 V,	 Current Strength: Default
-- clk	=>  Location: PIN_J1,	 I/O Standard: 2.5 V,	 Current Strength: Default
-- dataIn[0]	=>  Location: PIN_R6,	 I/O Standard: 2.5 V,	 Current Strength: Default
-- reset	=>  Location: PIN_U4,	 I/O Standard: 2.5 V,	 Current Strength: Default
-- enable	=>  Location: PIN_R7,	 I/O Standard: 2.5 V,	 Current Strength: Default
-- dataIn[1]	=>  Location: PIN_U3,	 I/O Standard: 2.5 V,	 Current Strength: Default
-- dataIn[2]	=>  Location: PIN_R1,	 I/O Standard: 2.5 V,	 Current Strength: Default
-- dataIn[3]	=>  Location: PIN_R2,	 I/O Standard: 2.5 V,	 Current Strength: Default


ARCHITECTURE structure OF AccN IS
SIGNAL gnd : std_logic := '0';
SIGNAL vcc : std_logic := '1';
SIGNAL unknown : std_logic := 'X';
SIGNAL devoe : std_logic := '1';
SIGNAL devclrn : std_logic := '1';
SIGNAL devpor : std_logic := '1';
SIGNAL ww_devoe : std_logic;
SIGNAL ww_devclrn : std_logic;
SIGNAL ww_devpor : std_logic;
SIGNAL ww_clk : std_logic;
SIGNAL ww_reset : std_logic;
SIGNAL ww_enable : std_logic;
SIGNAL ww_dataIn : std_logic_vector(3 DOWNTO 0);
SIGNAL ww_dataOut : std_logic_vector(3 DOWNTO 0);
SIGNAL \clk~inputclkctrl_INCLK_bus\ : std_logic_vector(3 DOWNTO 0);
SIGNAL \dataOut[0]~output_o\ : std_logic;
SIGNAL \dataOut[1]~output_o\ : std_logic;
SIGNAL \dataOut[2]~output_o\ : std_logic;
SIGNAL \dataOut[3]~output_o\ : std_logic;
SIGNAL \clk~input_o\ : std_logic;
SIGNAL \clk~inputclkctrl_outclk\ : std_logic;
SIGNAL \dataIn[0]~input_o\ : std_logic;
SIGNAL \regN|dataOut[0]~4_combout\ : std_logic;
SIGNAL \reset~input_o\ : std_logic;
SIGNAL \enable~input_o\ : std_logic;
SIGNAL \regN|dataOut[0]~6_combout\ : std_logic;
SIGNAL \dataIn[1]~input_o\ : std_logic;
SIGNAL \regN|dataOut[0]~5\ : std_logic;
SIGNAL \regN|dataOut[1]~7_combout\ : std_logic;
SIGNAL \dataIn[2]~input_o\ : std_logic;
SIGNAL \regN|dataOut[1]~8\ : std_logic;
SIGNAL \regN|dataOut[2]~9_combout\ : std_logic;
SIGNAL \dataIn[3]~input_o\ : std_logic;
SIGNAL \regN|dataOut[2]~10\ : std_logic;
SIGNAL \regN|dataOut[3]~11_combout\ : std_logic;
SIGNAL \regN|dataOut\ : std_logic_vector(3 DOWNTO 0);

COMPONENT hard_block
    PORT (
	devoe : IN std_logic;
	devclrn : IN std_logic;
	devpor : IN std_logic);
END COMPONENT;

BEGIN

ww_clk <= clk;
ww_reset <= reset;
ww_enable <= enable;
ww_dataIn <= dataIn;
dataOut <= ww_dataOut;
ww_devoe <= devoe;
ww_devclrn <= devclrn;
ww_devpor <= devpor;

\clk~inputclkctrl_INCLK_bus\ <= (vcc & vcc & vcc & \clk~input_o\);
auto_generated_inst : hard_block
PORT MAP (
	devoe => ww_devoe,
	devclrn => ww_devclrn,
	devpor => ww_devpor);

-- Location: IOOBUF_X0_Y34_N23
\dataOut[0]~output\ : cycloneive_io_obuf
-- pragma translate_off
GENERIC MAP (
	bus_hold => "false",
	open_drain_output => "false")
-- pragma translate_on
PORT MAP (
	i => \regN|dataOut\(0),
	devoe => ww_devoe,
	o => \dataOut[0]~output_o\);

-- Location: IOOBUF_X0_Y33_N16
\dataOut[1]~output\ : cycloneive_io_obuf
-- pragma translate_off
GENERIC MAP (
	bus_hold => "false",
	open_drain_output => "false")
-- pragma translate_on
PORT MAP (
	i => \regN|dataOut\(1),
	devoe => ww_devoe,
	o => \dataOut[1]~output_o\);

-- Location: IOOBUF_X0_Y33_N23
\dataOut[2]~output\ : cycloneive_io_obuf
-- pragma translate_off
GENERIC MAP (
	bus_hold => "false",
	open_drain_output => "false")
-- pragma translate_on
PORT MAP (
	i => \regN|dataOut\(2),
	devoe => ww_devoe,
	o => \dataOut[2]~output_o\);

-- Location: IOOBUF_X0_Y32_N23
\dataOut[3]~output\ : cycloneive_io_obuf
-- pragma translate_off
GENERIC MAP (
	bus_hold => "false",
	open_drain_output => "false")
-- pragma translate_on
PORT MAP (
	i => \regN|dataOut\(3),
	devoe => ww_devoe,
	o => \dataOut[3]~output_o\);

-- Location: IOIBUF_X0_Y36_N8
\clk~input\ : cycloneive_io_ibuf
-- pragma translate_off
GENERIC MAP (
	bus_hold => "false",
	simulate_z_as => "z")
-- pragma translate_on
PORT MAP (
	i => ww_clk,
	o => \clk~input_o\);

-- Location: CLKCTRL_G2
\clk~inputclkctrl\ : cycloneive_clkctrl
-- pragma translate_off
GENERIC MAP (
	clock_type => "global clock",
	ena_register_mode => "none")
-- pragma translate_on
PORT MAP (
	inclk => \clk~inputclkctrl_INCLK_bus\,
	devclrn => ww_devclrn,
	devpor => ww_devpor,
	outclk => \clk~inputclkctrl_outclk\);

-- Location: IOIBUF_X0_Y34_N1
\dataIn[0]~input\ : cycloneive_io_ibuf
-- pragma translate_off
GENERIC MAP (
	bus_hold => "false",
	simulate_z_as => "z")
-- pragma translate_on
PORT MAP (
	i => ww_dataIn(0),
	o => \dataIn[0]~input_o\);

-- Location: LCCOMB_X1_Y34_N6
\regN|dataOut[0]~4\ : cycloneive_lcell_comb
-- Equation(s):
-- \regN|dataOut[0]~4_combout\ = (\regN|dataOut\(0) & (\dataIn[0]~input_o\ $ (VCC))) # (!\regN|dataOut\(0) & (\dataIn[0]~input_o\ & VCC))
-- \regN|dataOut[0]~5\ = CARRY((\regN|dataOut\(0) & \dataIn[0]~input_o\))

-- pragma translate_off
GENERIC MAP (
	lut_mask => "0110011010001000",
	sum_lutc_input => "datac")
-- pragma translate_on
PORT MAP (
	dataa => \regN|dataOut\(0),
	datab => \dataIn[0]~input_o\,
	datad => VCC,
	combout => \regN|dataOut[0]~4_combout\,
	cout => \regN|dataOut[0]~5\);

-- Location: IOIBUF_X0_Y34_N15
\reset~input\ : cycloneive_io_ibuf
-- pragma translate_off
GENERIC MAP (
	bus_hold => "false",
	simulate_z_as => "z")
-- pragma translate_on
PORT MAP (
	i => ww_reset,
	o => \reset~input_o\);

-- Location: IOIBUF_X0_Y35_N15
\enable~input\ : cycloneive_io_ibuf
-- pragma translate_off
GENERIC MAP (
	bus_hold => "false",
	simulate_z_as => "z")
-- pragma translate_on
PORT MAP (
	i => ww_enable,
	o => \enable~input_o\);

-- Location: LCCOMB_X1_Y34_N16
\regN|dataOut[0]~6\ : cycloneive_lcell_comb
-- Equation(s):
-- \regN|dataOut[0]~6_combout\ = (\reset~input_o\) # (\enable~input_o\)

-- pragma translate_off
GENERIC MAP (
	lut_mask => "1111111111110000",
	sum_lutc_input => "datac")
-- pragma translate_on
PORT MAP (
	datac => \reset~input_o\,
	datad => \enable~input_o\,
	combout => \regN|dataOut[0]~6_combout\);

-- Location: FF_X1_Y34_N7
\regN|dataOut[0]\ : dffeas
-- pragma translate_off
GENERIC MAP (
	is_wysiwyg => "true",
	power_up => "low")
-- pragma translate_on
PORT MAP (
	clk => \clk~inputclkctrl_outclk\,
	d => \regN|dataOut[0]~4_combout\,
	sclr => \reset~input_o\,
	ena => \regN|dataOut[0]~6_combout\,
	devclrn => ww_devclrn,
	devpor => ww_devpor,
	q => \regN|dataOut\(0));

-- Location: IOIBUF_X0_Y34_N8
\dataIn[1]~input\ : cycloneive_io_ibuf
-- pragma translate_off
GENERIC MAP (
	bus_hold => "false",
	simulate_z_as => "z")
-- pragma translate_on
PORT MAP (
	i => ww_dataIn(1),
	o => \dataIn[1]~input_o\);

-- Location: LCCOMB_X1_Y34_N8
\regN|dataOut[1]~7\ : cycloneive_lcell_comb
-- Equation(s):
-- \regN|dataOut[1]~7_combout\ = (\dataIn[1]~input_o\ & ((\regN|dataOut\(1) & (\regN|dataOut[0]~5\ & VCC)) # (!\regN|dataOut\(1) & (!\regN|dataOut[0]~5\)))) # (!\dataIn[1]~input_o\ & ((\regN|dataOut\(1) & (!\regN|dataOut[0]~5\)) # (!\regN|dataOut\(1) & 
-- ((\regN|dataOut[0]~5\) # (GND)))))
-- \regN|dataOut[1]~8\ = CARRY((\dataIn[1]~input_o\ & (!\regN|dataOut\(1) & !\regN|dataOut[0]~5\)) # (!\dataIn[1]~input_o\ & ((!\regN|dataOut[0]~5\) # (!\regN|dataOut\(1)))))

-- pragma translate_off
GENERIC MAP (
	lut_mask => "1001011000010111",
	sum_lutc_input => "cin")
-- pragma translate_on
PORT MAP (
	dataa => \dataIn[1]~input_o\,
	datab => \regN|dataOut\(1),
	datad => VCC,
	cin => \regN|dataOut[0]~5\,
	combout => \regN|dataOut[1]~7_combout\,
	cout => \regN|dataOut[1]~8\);

-- Location: FF_X1_Y34_N9
\regN|dataOut[1]\ : dffeas
-- pragma translate_off
GENERIC MAP (
	is_wysiwyg => "true",
	power_up => "low")
-- pragma translate_on
PORT MAP (
	clk => \clk~inputclkctrl_outclk\,
	d => \regN|dataOut[1]~7_combout\,
	sclr => \reset~input_o\,
	ena => \regN|dataOut[0]~6_combout\,
	devclrn => ww_devclrn,
	devpor => ww_devpor,
	q => \regN|dataOut\(1));

-- Location: IOIBUF_X0_Y35_N8
\dataIn[2]~input\ : cycloneive_io_ibuf
-- pragma translate_off
GENERIC MAP (
	bus_hold => "false",
	simulate_z_as => "z")
-- pragma translate_on
PORT MAP (
	i => ww_dataIn(2),
	o => \dataIn[2]~input_o\);

-- Location: LCCOMB_X1_Y34_N10
\regN|dataOut[2]~9\ : cycloneive_lcell_comb
-- Equation(s):
-- \regN|dataOut[2]~9_combout\ = ((\regN|dataOut\(2) $ (\dataIn[2]~input_o\ $ (!\regN|dataOut[1]~8\)))) # (GND)
-- \regN|dataOut[2]~10\ = CARRY((\regN|dataOut\(2) & ((\dataIn[2]~input_o\) # (!\regN|dataOut[1]~8\))) # (!\regN|dataOut\(2) & (\dataIn[2]~input_o\ & !\regN|dataOut[1]~8\)))

-- pragma translate_off
GENERIC MAP (
	lut_mask => "0110100110001110",
	sum_lutc_input => "cin")
-- pragma translate_on
PORT MAP (
	dataa => \regN|dataOut\(2),
	datab => \dataIn[2]~input_o\,
	datad => VCC,
	cin => \regN|dataOut[1]~8\,
	combout => \regN|dataOut[2]~9_combout\,
	cout => \regN|dataOut[2]~10\);

-- Location: FF_X1_Y34_N11
\regN|dataOut[2]\ : dffeas
-- pragma translate_off
GENERIC MAP (
	is_wysiwyg => "true",
	power_up => "low")
-- pragma translate_on
PORT MAP (
	clk => \clk~inputclkctrl_outclk\,
	d => \regN|dataOut[2]~9_combout\,
	sclr => \reset~input_o\,
	ena => \regN|dataOut[0]~6_combout\,
	devclrn => ww_devclrn,
	devpor => ww_devpor,
	q => \regN|dataOut\(2));

-- Location: IOIBUF_X0_Y35_N1
\dataIn[3]~input\ : cycloneive_io_ibuf
-- pragma translate_off
GENERIC MAP (
	bus_hold => "false",
	simulate_z_as => "z")
-- pragma translate_on
PORT MAP (
	i => ww_dataIn(3),
	o => \dataIn[3]~input_o\);

-- Location: LCCOMB_X1_Y34_N12
\regN|dataOut[3]~11\ : cycloneive_lcell_comb
-- Equation(s):
-- \regN|dataOut[3]~11_combout\ = \dataIn[3]~input_o\ $ (\regN|dataOut[2]~10\ $ (\regN|dataOut\(3)))

-- pragma translate_off
GENERIC MAP (
	lut_mask => "1010010101011010",
	sum_lutc_input => "cin")
-- pragma translate_on
PORT MAP (
	dataa => \dataIn[3]~input_o\,
	datad => \regN|dataOut\(3),
	cin => \regN|dataOut[2]~10\,
	combout => \regN|dataOut[3]~11_combout\);

-- Location: FF_X1_Y34_N13
\regN|dataOut[3]\ : dffeas
-- pragma translate_off
GENERIC MAP (
	is_wysiwyg => "true",
	power_up => "low")
-- pragma translate_on
PORT MAP (
	clk => \clk~inputclkctrl_outclk\,
	d => \regN|dataOut[3]~11_combout\,
	sclr => \reset~input_o\,
	ena => \regN|dataOut[0]~6_combout\,
	devclrn => ww_devclrn,
	devpor => ww_devpor,
	q => \regN|dataOut\(3));

ww_dataOut(0) <= \dataOut[0]~output_o\;

ww_dataOut(1) <= \dataOut[1]~output_o\;

ww_dataOut(2) <= \dataOut[2]~output_o\;

ww_dataOut(3) <= \dataOut[3]~output_o\;
END structure;


