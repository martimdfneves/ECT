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

-- DATE "03/21/2018 12:08:04"

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

ENTITY 	TimerN_Demo IS
    PORT (
	LEDR : OUT std_logic_vector(0 DOWNTO 0);
	CLOCK_50 : IN std_logic;
	SW : IN std_logic_vector(2 DOWNTO 0)
	);
END TimerN_Demo;

-- Design Ports Information
-- LEDR[0]	=>  Location: PIN_G19,	 I/O Standard: 2.5 V,	 Current Strength: Default
-- SW[0]	=>  Location: PIN_AB28,	 I/O Standard: 2.5 V,	 Current Strength: Default
-- SW[1]	=>  Location: PIN_AC28,	 I/O Standard: 2.5 V,	 Current Strength: Default
-- SW[2]	=>  Location: PIN_AC27,	 I/O Standard: 2.5 V,	 Current Strength: Default
-- CLOCK_50	=>  Location: PIN_Y2,	 I/O Standard: 2.5 V,	 Current Strength: Default


ARCHITECTURE structure OF TimerN_Demo IS
SIGNAL gnd : std_logic := '0';
SIGNAL vcc : std_logic := '1';
SIGNAL unknown : std_logic := 'X';
SIGNAL devoe : std_logic := '1';
SIGNAL devclrn : std_logic := '1';
SIGNAL devpor : std_logic := '1';
SIGNAL ww_devoe : std_logic;
SIGNAL ww_devclrn : std_logic;
SIGNAL ww_devpor : std_logic;
SIGNAL ww_LEDR : std_logic_vector(0 DOWNTO 0);
SIGNAL ww_CLOCK_50 : std_logic;
SIGNAL ww_SW : std_logic_vector(2 DOWNTO 0);
SIGNAL \CLOCK_50~inputclkctrl_INCLK_bus\ : std_logic_vector(3 DOWNTO 0);
SIGNAL \LEDR[0]~output_o\ : std_logic;
SIGNAL \CLOCK_50~input_o\ : std_logic;
SIGNAL \CLOCK_50~inputclkctrl_outclk\ : std_logic;
SIGNAL \SW[0]~input_o\ : std_logic;
SIGNAL \SW[2]~input_o\ : std_logic;
SIGNAL \inst|s_counter[0]~35_combout\ : std_logic;
SIGNAL \SW[1]~input_o\ : std_logic;
SIGNAL \inst|s_counter[0]~43_combout\ : std_logic;
SIGNAL \inst|s_counter[5]~48\ : std_logic;
SIGNAL \inst|s_counter[6]~49_combout\ : std_logic;
SIGNAL \inst|s_counter[6]~50\ : std_logic;
SIGNAL \inst|s_counter[7]~51_combout\ : std_logic;
SIGNAL \inst|s_counter[7]~52\ : std_logic;
SIGNAL \inst|s_counter[8]~53_combout\ : std_logic;
SIGNAL \inst|s_counter[8]~54\ : std_logic;
SIGNAL \inst|s_counter[9]~55_combout\ : std_logic;
SIGNAL \inst|s_counter[9]~56\ : std_logic;
SIGNAL \inst|s_counter[10]~57_combout\ : std_logic;
SIGNAL \inst|s_counter[10]~58\ : std_logic;
SIGNAL \inst|s_counter[11]~59_combout\ : std_logic;
SIGNAL \inst|s_counter[11]~60\ : std_logic;
SIGNAL \inst|s_counter[12]~61_combout\ : std_logic;
SIGNAL \inst|s_counter[12]~62\ : std_logic;
SIGNAL \inst|s_counter[13]~63_combout\ : std_logic;
SIGNAL \inst|s_counter[13]~64\ : std_logic;
SIGNAL \inst|s_counter[14]~65_combout\ : std_logic;
SIGNAL \inst|s_counter[14]~66\ : std_logic;
SIGNAL \inst|s_counter[15]~67_combout\ : std_logic;
SIGNAL \inst|s_counter[15]~68\ : std_logic;
SIGNAL \inst|s_counter[16]~69_combout\ : std_logic;
SIGNAL \inst|s_counter[16]~70\ : std_logic;
SIGNAL \inst|s_counter[17]~71_combout\ : std_logic;
SIGNAL \inst|s_counter[17]~72\ : std_logic;
SIGNAL \inst|s_counter[18]~73_combout\ : std_logic;
SIGNAL \inst|s_counter[18]~74\ : std_logic;
SIGNAL \inst|s_counter[19]~75_combout\ : std_logic;
SIGNAL \inst|s_counter[19]~76\ : std_logic;
SIGNAL \inst|s_counter[20]~77_combout\ : std_logic;
SIGNAL \inst|s_counter[20]~78\ : std_logic;
SIGNAL \inst|s_counter[21]~79_combout\ : std_logic;
SIGNAL \inst|s_counter[21]~80\ : std_logic;
SIGNAL \inst|s_counter[22]~81_combout\ : std_logic;
SIGNAL \inst|s_counter[22]~82\ : std_logic;
SIGNAL \inst|s_counter[23]~83_combout\ : std_logic;
SIGNAL \inst|s_counter[23]~84\ : std_logic;
SIGNAL \inst|s_counter[24]~85_combout\ : std_logic;
SIGNAL \inst|s_counter[24]~86\ : std_logic;
SIGNAL \inst|s_counter[25]~87_combout\ : std_logic;
SIGNAL \inst|s_counter[25]~88\ : std_logic;
SIGNAL \inst|s_counter[26]~89_combout\ : std_logic;
SIGNAL \inst|process_0~5_combout\ : std_logic;
SIGNAL \inst|s_counter[26]~90\ : std_logic;
SIGNAL \inst|s_counter[27]~91_combout\ : std_logic;
SIGNAL \inst|s_counter[27]~92\ : std_logic;
SIGNAL \inst|s_counter[28]~93_combout\ : std_logic;
SIGNAL \inst|s_counter[28]~94\ : std_logic;
SIGNAL \inst|s_counter[29]~95_combout\ : std_logic;
SIGNAL \inst|s_counter[29]~96\ : std_logic;
SIGNAL \inst|s_counter[30]~97_combout\ : std_logic;
SIGNAL \inst|process_0~6_combout\ : std_logic;
SIGNAL \inst|Equal1~0_combout\ : std_logic;
SIGNAL \inst|Equal1~1_combout\ : std_logic;
SIGNAL \inst|process_0~0_combout\ : std_logic;
SIGNAL \inst|process_0~2_combout\ : std_logic;
SIGNAL \inst|process_0~3_combout\ : std_logic;
SIGNAL \inst|process_0~1_combout\ : std_logic;
SIGNAL \inst|process_0~4_combout\ : std_logic;
SIGNAL \inst|Equal1~2_combout\ : std_logic;
SIGNAL \inst|s_counter[0]~44_combout\ : std_logic;
SIGNAL \inst|s_counter[0]~36\ : std_logic;
SIGNAL \inst|s_counter[1]~37_combout\ : std_logic;
SIGNAL \inst|s_counter[1]~38\ : std_logic;
SIGNAL \inst|s_counter[2]~39_combout\ : std_logic;
SIGNAL \inst|s_counter[2]~40\ : std_logic;
SIGNAL \inst|s_counter[3]~41_combout\ : std_logic;
SIGNAL \inst|s_counter[3]~42\ : std_logic;
SIGNAL \inst|s_counter[4]~45_combout\ : std_logic;
SIGNAL \inst|s_counter[4]~46\ : std_logic;
SIGNAL \inst|s_counter[5]~47_combout\ : std_logic;
SIGNAL \inst|s_counter[0]~32_combout\ : std_logic;
SIGNAL \inst|s_counter[0]~33_combout\ : std_logic;
SIGNAL \inst|s_counter[0]~31_combout\ : std_logic;
SIGNAL \inst|s_counter[0]~34_combout\ : std_logic;
SIGNAL \inst|timerOut~0_combout\ : std_logic;
SIGNAL \inst|timerOut~1_combout\ : std_logic;
SIGNAL \inst|timerOut~q\ : std_logic;
SIGNAL \inst|s_counter\ : std_logic_vector(30 DOWNTO 0);

COMPONENT hard_block
    PORT (
	devoe : IN std_logic;
	devclrn : IN std_logic;
	devpor : IN std_logic);
END COMPONENT;

BEGIN

LEDR <= ww_LEDR;
ww_CLOCK_50 <= CLOCK_50;
ww_SW <= SW;
ww_devoe <= devoe;
ww_devclrn <= devclrn;
ww_devpor <= devpor;

\CLOCK_50~inputclkctrl_INCLK_bus\ <= (vcc & vcc & vcc & \CLOCK_50~input_o\);
auto_generated_inst : hard_block
PORT MAP (
	devoe => ww_devoe,
	devclrn => ww_devclrn,
	devpor => ww_devpor);

-- Location: IOOBUF_X69_Y73_N16
\LEDR[0]~output\ : cycloneive_io_obuf
-- pragma translate_off
GENERIC MAP (
	bus_hold => "false",
	open_drain_output => "false")
-- pragma translate_on
PORT MAP (
	i => \inst|timerOut~q\,
	devoe => ww_devoe,
	o => \LEDR[0]~output_o\);

-- Location: IOIBUF_X0_Y36_N15
\CLOCK_50~input\ : cycloneive_io_ibuf
-- pragma translate_off
GENERIC MAP (
	bus_hold => "false",
	simulate_z_as => "z")
-- pragma translate_on
PORT MAP (
	i => ww_CLOCK_50,
	o => \CLOCK_50~input_o\);

-- Location: CLKCTRL_G4
\CLOCK_50~inputclkctrl\ : cycloneive_clkctrl
-- pragma translate_off
GENERIC MAP (
	clock_type => "global clock",
	ena_register_mode => "none")
-- pragma translate_on
PORT MAP (
	inclk => \CLOCK_50~inputclkctrl_INCLK_bus\,
	devclrn => ww_devclrn,
	devpor => ww_devpor,
	outclk => \CLOCK_50~inputclkctrl_outclk\);

-- Location: IOIBUF_X115_Y17_N1
\SW[0]~input\ : cycloneive_io_ibuf
-- pragma translate_off
GENERIC MAP (
	bus_hold => "false",
	simulate_z_as => "z")
-- pragma translate_on
PORT MAP (
	i => ww_SW(0),
	o => \SW[0]~input_o\);

-- Location: IOIBUF_X115_Y15_N8
\SW[2]~input\ : cycloneive_io_ibuf
-- pragma translate_off
GENERIC MAP (
	bus_hold => "false",
	simulate_z_as => "z")
-- pragma translate_on
PORT MAP (
	i => ww_SW(2),
	o => \SW[2]~input_o\);

-- Location: LCCOMB_X108_Y16_N2
\inst|s_counter[0]~35\ : cycloneive_lcell_comb
-- Equation(s):
-- \inst|s_counter[0]~35_combout\ = \inst|s_counter\(0) $ (VCC)
-- \inst|s_counter[0]~36\ = CARRY(\inst|s_counter\(0))

-- pragma translate_off
GENERIC MAP (
	lut_mask => "0011001111001100",
	sum_lutc_input => "datac")
-- pragma translate_on
PORT MAP (
	datab => \inst|s_counter\(0),
	datad => VCC,
	combout => \inst|s_counter[0]~35_combout\,
	cout => \inst|s_counter[0]~36\);

-- Location: IOIBUF_X115_Y14_N1
\SW[1]~input\ : cycloneive_io_ibuf
-- pragma translate_off
GENERIC MAP (
	bus_hold => "false",
	simulate_z_as => "z")
-- pragma translate_on
PORT MAP (
	i => ww_SW(1),
	o => \SW[1]~input_o\);

-- Location: LCCOMB_X109_Y15_N0
\inst|s_counter[0]~43\ : cycloneive_lcell_comb
-- Equation(s):
-- \inst|s_counter[0]~43_combout\ = (\SW[1]~input_o\) # (\inst|s_counter[0]~34_combout\)

-- pragma translate_off
GENERIC MAP (
	lut_mask => "1111111111001100",
	sum_lutc_input => "datac")
-- pragma translate_on
PORT MAP (
	datab => \SW[1]~input_o\,
	datad => \inst|s_counter[0]~34_combout\,
	combout => \inst|s_counter[0]~43_combout\);

-- Location: LCCOMB_X108_Y16_N12
\inst|s_counter[5]~47\ : cycloneive_lcell_comb
-- Equation(s):
-- \inst|s_counter[5]~47_combout\ = (\inst|s_counter\(5) & (!\inst|s_counter[4]~46\)) # (!\inst|s_counter\(5) & ((\inst|s_counter[4]~46\) # (GND)))
-- \inst|s_counter[5]~48\ = CARRY((!\inst|s_counter[4]~46\) # (!\inst|s_counter\(5)))

-- pragma translate_off
GENERIC MAP (
	lut_mask => "0101101001011111",
	sum_lutc_input => "cin")
-- pragma translate_on
PORT MAP (
	dataa => \inst|s_counter\(5),
	datad => VCC,
	cin => \inst|s_counter[4]~46\,
	combout => \inst|s_counter[5]~47_combout\,
	cout => \inst|s_counter[5]~48\);

-- Location: LCCOMB_X108_Y16_N14
\inst|s_counter[6]~49\ : cycloneive_lcell_comb
-- Equation(s):
-- \inst|s_counter[6]~49_combout\ = (\inst|s_counter\(6) & (\inst|s_counter[5]~48\ $ (GND))) # (!\inst|s_counter\(6) & (!\inst|s_counter[5]~48\ & VCC))
-- \inst|s_counter[6]~50\ = CARRY((\inst|s_counter\(6) & !\inst|s_counter[5]~48\))

-- pragma translate_off
GENERIC MAP (
	lut_mask => "1100001100001100",
	sum_lutc_input => "cin")
-- pragma translate_on
PORT MAP (
	datab => \inst|s_counter\(6),
	datad => VCC,
	cin => \inst|s_counter[5]~48\,
	combout => \inst|s_counter[6]~49_combout\,
	cout => \inst|s_counter[6]~50\);

-- Location: FF_X108_Y16_N15
\inst|s_counter[6]\ : dffeas
-- pragma translate_off
GENERIC MAP (
	is_wysiwyg => "true",
	power_up => "low")
-- pragma translate_on
PORT MAP (
	clk => \CLOCK_50~inputclkctrl_outclk\,
	d => \inst|s_counter[6]~49_combout\,
	sclr => \inst|s_counter[0]~43_combout\,
	ena => \inst|s_counter[0]~44_combout\,
	devclrn => ww_devclrn,
	devpor => ww_devpor,
	q => \inst|s_counter\(6));

-- Location: LCCOMB_X108_Y16_N16
\inst|s_counter[7]~51\ : cycloneive_lcell_comb
-- Equation(s):
-- \inst|s_counter[7]~51_combout\ = (\inst|s_counter\(7) & (!\inst|s_counter[6]~50\)) # (!\inst|s_counter\(7) & ((\inst|s_counter[6]~50\) # (GND)))
-- \inst|s_counter[7]~52\ = CARRY((!\inst|s_counter[6]~50\) # (!\inst|s_counter\(7)))

-- pragma translate_off
GENERIC MAP (
	lut_mask => "0011110000111111",
	sum_lutc_input => "cin")
-- pragma translate_on
PORT MAP (
	datab => \inst|s_counter\(7),
	datad => VCC,
	cin => \inst|s_counter[6]~50\,
	combout => \inst|s_counter[7]~51_combout\,
	cout => \inst|s_counter[7]~52\);

-- Location: FF_X108_Y16_N17
\inst|s_counter[7]\ : dffeas
-- pragma translate_off
GENERIC MAP (
	is_wysiwyg => "true",
	power_up => "low")
-- pragma translate_on
PORT MAP (
	clk => \CLOCK_50~inputclkctrl_outclk\,
	d => \inst|s_counter[7]~51_combout\,
	sclr => \inst|s_counter[0]~43_combout\,
	ena => \inst|s_counter[0]~44_combout\,
	devclrn => ww_devclrn,
	devpor => ww_devpor,
	q => \inst|s_counter\(7));

-- Location: LCCOMB_X108_Y16_N18
\inst|s_counter[8]~53\ : cycloneive_lcell_comb
-- Equation(s):
-- \inst|s_counter[8]~53_combout\ = (\inst|s_counter\(8) & (\inst|s_counter[7]~52\ $ (GND))) # (!\inst|s_counter\(8) & (!\inst|s_counter[7]~52\ & VCC))
-- \inst|s_counter[8]~54\ = CARRY((\inst|s_counter\(8) & !\inst|s_counter[7]~52\))

-- pragma translate_off
GENERIC MAP (
	lut_mask => "1100001100001100",
	sum_lutc_input => "cin")
-- pragma translate_on
PORT MAP (
	datab => \inst|s_counter\(8),
	datad => VCC,
	cin => \inst|s_counter[7]~52\,
	combout => \inst|s_counter[8]~53_combout\,
	cout => \inst|s_counter[8]~54\);

-- Location: FF_X108_Y16_N19
\inst|s_counter[8]\ : dffeas
-- pragma translate_off
GENERIC MAP (
	is_wysiwyg => "true",
	power_up => "low")
-- pragma translate_on
PORT MAP (
	clk => \CLOCK_50~inputclkctrl_outclk\,
	d => \inst|s_counter[8]~53_combout\,
	sclr => \inst|s_counter[0]~43_combout\,
	ena => \inst|s_counter[0]~44_combout\,
	devclrn => ww_devclrn,
	devpor => ww_devpor,
	q => \inst|s_counter\(8));

-- Location: LCCOMB_X108_Y16_N20
\inst|s_counter[9]~55\ : cycloneive_lcell_comb
-- Equation(s):
-- \inst|s_counter[9]~55_combout\ = (\inst|s_counter\(9) & (!\inst|s_counter[8]~54\)) # (!\inst|s_counter\(9) & ((\inst|s_counter[8]~54\) # (GND)))
-- \inst|s_counter[9]~56\ = CARRY((!\inst|s_counter[8]~54\) # (!\inst|s_counter\(9)))

-- pragma translate_off
GENERIC MAP (
	lut_mask => "0011110000111111",
	sum_lutc_input => "cin")
-- pragma translate_on
PORT MAP (
	datab => \inst|s_counter\(9),
	datad => VCC,
	cin => \inst|s_counter[8]~54\,
	combout => \inst|s_counter[9]~55_combout\,
	cout => \inst|s_counter[9]~56\);

-- Location: FF_X108_Y16_N21
\inst|s_counter[9]\ : dffeas
-- pragma translate_off
GENERIC MAP (
	is_wysiwyg => "true",
	power_up => "low")
-- pragma translate_on
PORT MAP (
	clk => \CLOCK_50~inputclkctrl_outclk\,
	d => \inst|s_counter[9]~55_combout\,
	sclr => \inst|s_counter[0]~43_combout\,
	ena => \inst|s_counter[0]~44_combout\,
	devclrn => ww_devclrn,
	devpor => ww_devpor,
	q => \inst|s_counter\(9));

-- Location: LCCOMB_X108_Y16_N22
\inst|s_counter[10]~57\ : cycloneive_lcell_comb
-- Equation(s):
-- \inst|s_counter[10]~57_combout\ = (\inst|s_counter\(10) & (\inst|s_counter[9]~56\ $ (GND))) # (!\inst|s_counter\(10) & (!\inst|s_counter[9]~56\ & VCC))
-- \inst|s_counter[10]~58\ = CARRY((\inst|s_counter\(10) & !\inst|s_counter[9]~56\))

-- pragma translate_off
GENERIC MAP (
	lut_mask => "1100001100001100",
	sum_lutc_input => "cin")
-- pragma translate_on
PORT MAP (
	datab => \inst|s_counter\(10),
	datad => VCC,
	cin => \inst|s_counter[9]~56\,
	combout => \inst|s_counter[10]~57_combout\,
	cout => \inst|s_counter[10]~58\);

-- Location: FF_X108_Y16_N23
\inst|s_counter[10]\ : dffeas
-- pragma translate_off
GENERIC MAP (
	is_wysiwyg => "true",
	power_up => "low")
-- pragma translate_on
PORT MAP (
	clk => \CLOCK_50~inputclkctrl_outclk\,
	d => \inst|s_counter[10]~57_combout\,
	sclr => \inst|s_counter[0]~43_combout\,
	ena => \inst|s_counter[0]~44_combout\,
	devclrn => ww_devclrn,
	devpor => ww_devpor,
	q => \inst|s_counter\(10));

-- Location: LCCOMB_X108_Y16_N24
\inst|s_counter[11]~59\ : cycloneive_lcell_comb
-- Equation(s):
-- \inst|s_counter[11]~59_combout\ = (\inst|s_counter\(11) & (!\inst|s_counter[10]~58\)) # (!\inst|s_counter\(11) & ((\inst|s_counter[10]~58\) # (GND)))
-- \inst|s_counter[11]~60\ = CARRY((!\inst|s_counter[10]~58\) # (!\inst|s_counter\(11)))

-- pragma translate_off
GENERIC MAP (
	lut_mask => "0101101001011111",
	sum_lutc_input => "cin")
-- pragma translate_on
PORT MAP (
	dataa => \inst|s_counter\(11),
	datad => VCC,
	cin => \inst|s_counter[10]~58\,
	combout => \inst|s_counter[11]~59_combout\,
	cout => \inst|s_counter[11]~60\);

-- Location: FF_X109_Y15_N1
\inst|s_counter[11]\ : dffeas
-- pragma translate_off
GENERIC MAP (
	is_wysiwyg => "true",
	power_up => "low")
-- pragma translate_on
PORT MAP (
	clk => \CLOCK_50~inputclkctrl_outclk\,
	asdata => \inst|s_counter[11]~59_combout\,
	sclr => \inst|s_counter[0]~43_combout\,
	sload => VCC,
	ena => \inst|s_counter[0]~44_combout\,
	devclrn => ww_devclrn,
	devpor => ww_devpor,
	q => \inst|s_counter\(11));

-- Location: LCCOMB_X108_Y16_N26
\inst|s_counter[12]~61\ : cycloneive_lcell_comb
-- Equation(s):
-- \inst|s_counter[12]~61_combout\ = (\inst|s_counter\(12) & (\inst|s_counter[11]~60\ $ (GND))) # (!\inst|s_counter\(12) & (!\inst|s_counter[11]~60\ & VCC))
-- \inst|s_counter[12]~62\ = CARRY((\inst|s_counter\(12) & !\inst|s_counter[11]~60\))

-- pragma translate_off
GENERIC MAP (
	lut_mask => "1100001100001100",
	sum_lutc_input => "cin")
-- pragma translate_on
PORT MAP (
	datab => \inst|s_counter\(12),
	datad => VCC,
	cin => \inst|s_counter[11]~60\,
	combout => \inst|s_counter[12]~61_combout\,
	cout => \inst|s_counter[12]~62\);

-- Location: FF_X109_Y15_N19
\inst|s_counter[12]\ : dffeas
-- pragma translate_off
GENERIC MAP (
	is_wysiwyg => "true",
	power_up => "low")
-- pragma translate_on
PORT MAP (
	clk => \CLOCK_50~inputclkctrl_outclk\,
	asdata => \inst|s_counter[12]~61_combout\,
	sclr => \inst|s_counter[0]~43_combout\,
	sload => VCC,
	ena => \inst|s_counter[0]~44_combout\,
	devclrn => ww_devclrn,
	devpor => ww_devpor,
	q => \inst|s_counter\(12));

-- Location: LCCOMB_X108_Y16_N28
\inst|s_counter[13]~63\ : cycloneive_lcell_comb
-- Equation(s):
-- \inst|s_counter[13]~63_combout\ = (\inst|s_counter\(13) & (!\inst|s_counter[12]~62\)) # (!\inst|s_counter\(13) & ((\inst|s_counter[12]~62\) # (GND)))
-- \inst|s_counter[13]~64\ = CARRY((!\inst|s_counter[12]~62\) # (!\inst|s_counter\(13)))

-- pragma translate_off
GENERIC MAP (
	lut_mask => "0011110000111111",
	sum_lutc_input => "cin")
-- pragma translate_on
PORT MAP (
	datab => \inst|s_counter\(13),
	datad => VCC,
	cin => \inst|s_counter[12]~62\,
	combout => \inst|s_counter[13]~63_combout\,
	cout => \inst|s_counter[13]~64\);

-- Location: FF_X109_Y15_N29
\inst|s_counter[13]\ : dffeas
-- pragma translate_off
GENERIC MAP (
	is_wysiwyg => "true",
	power_up => "low")
-- pragma translate_on
PORT MAP (
	clk => \CLOCK_50~inputclkctrl_outclk\,
	asdata => \inst|s_counter[13]~63_combout\,
	sclr => \inst|s_counter[0]~43_combout\,
	sload => VCC,
	ena => \inst|s_counter[0]~44_combout\,
	devclrn => ww_devclrn,
	devpor => ww_devpor,
	q => \inst|s_counter\(13));

-- Location: LCCOMB_X108_Y16_N30
\inst|s_counter[14]~65\ : cycloneive_lcell_comb
-- Equation(s):
-- \inst|s_counter[14]~65_combout\ = (\inst|s_counter\(14) & (\inst|s_counter[13]~64\ $ (GND))) # (!\inst|s_counter\(14) & (!\inst|s_counter[13]~64\ & VCC))
-- \inst|s_counter[14]~66\ = CARRY((\inst|s_counter\(14) & !\inst|s_counter[13]~64\))

-- pragma translate_off
GENERIC MAP (
	lut_mask => "1010010100001010",
	sum_lutc_input => "cin")
-- pragma translate_on
PORT MAP (
	dataa => \inst|s_counter\(14),
	datad => VCC,
	cin => \inst|s_counter[13]~64\,
	combout => \inst|s_counter[14]~65_combout\,
	cout => \inst|s_counter[14]~66\);

-- Location: FF_X108_Y16_N31
\inst|s_counter[14]\ : dffeas
-- pragma translate_off
GENERIC MAP (
	is_wysiwyg => "true",
	power_up => "low")
-- pragma translate_on
PORT MAP (
	clk => \CLOCK_50~inputclkctrl_outclk\,
	d => \inst|s_counter[14]~65_combout\,
	sclr => \inst|s_counter[0]~43_combout\,
	ena => \inst|s_counter[0]~44_combout\,
	devclrn => ww_devclrn,
	devpor => ww_devpor,
	q => \inst|s_counter\(14));

-- Location: LCCOMB_X108_Y15_N0
\inst|s_counter[15]~67\ : cycloneive_lcell_comb
-- Equation(s):
-- \inst|s_counter[15]~67_combout\ = (\inst|s_counter\(15) & (!\inst|s_counter[14]~66\)) # (!\inst|s_counter\(15) & ((\inst|s_counter[14]~66\) # (GND)))
-- \inst|s_counter[15]~68\ = CARRY((!\inst|s_counter[14]~66\) # (!\inst|s_counter\(15)))

-- pragma translate_off
GENERIC MAP (
	lut_mask => "0011110000111111",
	sum_lutc_input => "cin")
-- pragma translate_on
PORT MAP (
	datab => \inst|s_counter\(15),
	datad => VCC,
	cin => \inst|s_counter[14]~66\,
	combout => \inst|s_counter[15]~67_combout\,
	cout => \inst|s_counter[15]~68\);

-- Location: FF_X108_Y15_N1
\inst|s_counter[15]\ : dffeas
-- pragma translate_off
GENERIC MAP (
	is_wysiwyg => "true",
	power_up => "low")
-- pragma translate_on
PORT MAP (
	clk => \CLOCK_50~inputclkctrl_outclk\,
	d => \inst|s_counter[15]~67_combout\,
	sclr => \inst|s_counter[0]~43_combout\,
	ena => \inst|s_counter[0]~44_combout\,
	devclrn => ww_devclrn,
	devpor => ww_devpor,
	q => \inst|s_counter\(15));

-- Location: LCCOMB_X108_Y15_N2
\inst|s_counter[16]~69\ : cycloneive_lcell_comb
-- Equation(s):
-- \inst|s_counter[16]~69_combout\ = (\inst|s_counter\(16) & (\inst|s_counter[15]~68\ $ (GND))) # (!\inst|s_counter\(16) & (!\inst|s_counter[15]~68\ & VCC))
-- \inst|s_counter[16]~70\ = CARRY((\inst|s_counter\(16) & !\inst|s_counter[15]~68\))

-- pragma translate_off
GENERIC MAP (
	lut_mask => "1100001100001100",
	sum_lutc_input => "cin")
-- pragma translate_on
PORT MAP (
	datab => \inst|s_counter\(16),
	datad => VCC,
	cin => \inst|s_counter[15]~68\,
	combout => \inst|s_counter[16]~69_combout\,
	cout => \inst|s_counter[16]~70\);

-- Location: FF_X108_Y15_N3
\inst|s_counter[16]\ : dffeas
-- pragma translate_off
GENERIC MAP (
	is_wysiwyg => "true",
	power_up => "low")
-- pragma translate_on
PORT MAP (
	clk => \CLOCK_50~inputclkctrl_outclk\,
	d => \inst|s_counter[16]~69_combout\,
	sclr => \inst|s_counter[0]~43_combout\,
	ena => \inst|s_counter[0]~44_combout\,
	devclrn => ww_devclrn,
	devpor => ww_devpor,
	q => \inst|s_counter\(16));

-- Location: LCCOMB_X108_Y15_N4
\inst|s_counter[17]~71\ : cycloneive_lcell_comb
-- Equation(s):
-- \inst|s_counter[17]~71_combout\ = (\inst|s_counter\(17) & (!\inst|s_counter[16]~70\)) # (!\inst|s_counter\(17) & ((\inst|s_counter[16]~70\) # (GND)))
-- \inst|s_counter[17]~72\ = CARRY((!\inst|s_counter[16]~70\) # (!\inst|s_counter\(17)))

-- pragma translate_off
GENERIC MAP (
	lut_mask => "0011110000111111",
	sum_lutc_input => "cin")
-- pragma translate_on
PORT MAP (
	datab => \inst|s_counter\(17),
	datad => VCC,
	cin => \inst|s_counter[16]~70\,
	combout => \inst|s_counter[17]~71_combout\,
	cout => \inst|s_counter[17]~72\);

-- Location: FF_X108_Y15_N5
\inst|s_counter[17]\ : dffeas
-- pragma translate_off
GENERIC MAP (
	is_wysiwyg => "true",
	power_up => "low")
-- pragma translate_on
PORT MAP (
	clk => \CLOCK_50~inputclkctrl_outclk\,
	d => \inst|s_counter[17]~71_combout\,
	sclr => \inst|s_counter[0]~43_combout\,
	ena => \inst|s_counter[0]~44_combout\,
	devclrn => ww_devclrn,
	devpor => ww_devpor,
	q => \inst|s_counter\(17));

-- Location: LCCOMB_X108_Y15_N6
\inst|s_counter[18]~73\ : cycloneive_lcell_comb
-- Equation(s):
-- \inst|s_counter[18]~73_combout\ = (\inst|s_counter\(18) & (\inst|s_counter[17]~72\ $ (GND))) # (!\inst|s_counter\(18) & (!\inst|s_counter[17]~72\ & VCC))
-- \inst|s_counter[18]~74\ = CARRY((\inst|s_counter\(18) & !\inst|s_counter[17]~72\))

-- pragma translate_off
GENERIC MAP (
	lut_mask => "1010010100001010",
	sum_lutc_input => "cin")
-- pragma translate_on
PORT MAP (
	dataa => \inst|s_counter\(18),
	datad => VCC,
	cin => \inst|s_counter[17]~72\,
	combout => \inst|s_counter[18]~73_combout\,
	cout => \inst|s_counter[18]~74\);

-- Location: FF_X109_Y15_N3
\inst|s_counter[18]\ : dffeas
-- pragma translate_off
GENERIC MAP (
	is_wysiwyg => "true",
	power_up => "low")
-- pragma translate_on
PORT MAP (
	clk => \CLOCK_50~inputclkctrl_outclk\,
	asdata => \inst|s_counter[18]~73_combout\,
	sclr => \inst|s_counter[0]~43_combout\,
	sload => VCC,
	ena => \inst|s_counter[0]~44_combout\,
	devclrn => ww_devclrn,
	devpor => ww_devpor,
	q => \inst|s_counter\(18));

-- Location: LCCOMB_X108_Y15_N8
\inst|s_counter[19]~75\ : cycloneive_lcell_comb
-- Equation(s):
-- \inst|s_counter[19]~75_combout\ = (\inst|s_counter\(19) & (!\inst|s_counter[18]~74\)) # (!\inst|s_counter\(19) & ((\inst|s_counter[18]~74\) # (GND)))
-- \inst|s_counter[19]~76\ = CARRY((!\inst|s_counter[18]~74\) # (!\inst|s_counter\(19)))

-- pragma translate_off
GENERIC MAP (
	lut_mask => "0011110000111111",
	sum_lutc_input => "cin")
-- pragma translate_on
PORT MAP (
	datab => \inst|s_counter\(19),
	datad => VCC,
	cin => \inst|s_counter[18]~74\,
	combout => \inst|s_counter[19]~75_combout\,
	cout => \inst|s_counter[19]~76\);

-- Location: FF_X108_Y15_N9
\inst|s_counter[19]\ : dffeas
-- pragma translate_off
GENERIC MAP (
	is_wysiwyg => "true",
	power_up => "low")
-- pragma translate_on
PORT MAP (
	clk => \CLOCK_50~inputclkctrl_outclk\,
	d => \inst|s_counter[19]~75_combout\,
	sclr => \inst|s_counter[0]~43_combout\,
	ena => \inst|s_counter[0]~44_combout\,
	devclrn => ww_devclrn,
	devpor => ww_devpor,
	q => \inst|s_counter\(19));

-- Location: LCCOMB_X108_Y15_N10
\inst|s_counter[20]~77\ : cycloneive_lcell_comb
-- Equation(s):
-- \inst|s_counter[20]~77_combout\ = (\inst|s_counter\(20) & (\inst|s_counter[19]~76\ $ (GND))) # (!\inst|s_counter\(20) & (!\inst|s_counter[19]~76\ & VCC))
-- \inst|s_counter[20]~78\ = CARRY((\inst|s_counter\(20) & !\inst|s_counter[19]~76\))

-- pragma translate_off
GENERIC MAP (
	lut_mask => "1010010100001010",
	sum_lutc_input => "cin")
-- pragma translate_on
PORT MAP (
	dataa => \inst|s_counter\(20),
	datad => VCC,
	cin => \inst|s_counter[19]~76\,
	combout => \inst|s_counter[20]~77_combout\,
	cout => \inst|s_counter[20]~78\);

-- Location: FF_X108_Y15_N11
\inst|s_counter[20]\ : dffeas
-- pragma translate_off
GENERIC MAP (
	is_wysiwyg => "true",
	power_up => "low")
-- pragma translate_on
PORT MAP (
	clk => \CLOCK_50~inputclkctrl_outclk\,
	d => \inst|s_counter[20]~77_combout\,
	sclr => \inst|s_counter[0]~43_combout\,
	ena => \inst|s_counter[0]~44_combout\,
	devclrn => ww_devclrn,
	devpor => ww_devpor,
	q => \inst|s_counter\(20));

-- Location: LCCOMB_X108_Y15_N12
\inst|s_counter[21]~79\ : cycloneive_lcell_comb
-- Equation(s):
-- \inst|s_counter[21]~79_combout\ = (\inst|s_counter\(21) & (!\inst|s_counter[20]~78\)) # (!\inst|s_counter\(21) & ((\inst|s_counter[20]~78\) # (GND)))
-- \inst|s_counter[21]~80\ = CARRY((!\inst|s_counter[20]~78\) # (!\inst|s_counter\(21)))

-- pragma translate_off
GENERIC MAP (
	lut_mask => "0101101001011111",
	sum_lutc_input => "cin")
-- pragma translate_on
PORT MAP (
	dataa => \inst|s_counter\(21),
	datad => VCC,
	cin => \inst|s_counter[20]~78\,
	combout => \inst|s_counter[21]~79_combout\,
	cout => \inst|s_counter[21]~80\);

-- Location: FF_X108_Y15_N13
\inst|s_counter[21]\ : dffeas
-- pragma translate_off
GENERIC MAP (
	is_wysiwyg => "true",
	power_up => "low")
-- pragma translate_on
PORT MAP (
	clk => \CLOCK_50~inputclkctrl_outclk\,
	d => \inst|s_counter[21]~79_combout\,
	sclr => \inst|s_counter[0]~43_combout\,
	ena => \inst|s_counter[0]~44_combout\,
	devclrn => ww_devclrn,
	devpor => ww_devpor,
	q => \inst|s_counter\(21));

-- Location: LCCOMB_X108_Y15_N14
\inst|s_counter[22]~81\ : cycloneive_lcell_comb
-- Equation(s):
-- \inst|s_counter[22]~81_combout\ = (\inst|s_counter\(22) & (\inst|s_counter[21]~80\ $ (GND))) # (!\inst|s_counter\(22) & (!\inst|s_counter[21]~80\ & VCC))
-- \inst|s_counter[22]~82\ = CARRY((\inst|s_counter\(22) & !\inst|s_counter[21]~80\))

-- pragma translate_off
GENERIC MAP (
	lut_mask => "1100001100001100",
	sum_lutc_input => "cin")
-- pragma translate_on
PORT MAP (
	datab => \inst|s_counter\(22),
	datad => VCC,
	cin => \inst|s_counter[21]~80\,
	combout => \inst|s_counter[22]~81_combout\,
	cout => \inst|s_counter[22]~82\);

-- Location: FF_X108_Y15_N15
\inst|s_counter[22]\ : dffeas
-- pragma translate_off
GENERIC MAP (
	is_wysiwyg => "true",
	power_up => "low")
-- pragma translate_on
PORT MAP (
	clk => \CLOCK_50~inputclkctrl_outclk\,
	d => \inst|s_counter[22]~81_combout\,
	sclr => \inst|s_counter[0]~43_combout\,
	ena => \inst|s_counter[0]~44_combout\,
	devclrn => ww_devclrn,
	devpor => ww_devpor,
	q => \inst|s_counter\(22));

-- Location: LCCOMB_X108_Y15_N16
\inst|s_counter[23]~83\ : cycloneive_lcell_comb
-- Equation(s):
-- \inst|s_counter[23]~83_combout\ = (\inst|s_counter\(23) & (!\inst|s_counter[22]~82\)) # (!\inst|s_counter\(23) & ((\inst|s_counter[22]~82\) # (GND)))
-- \inst|s_counter[23]~84\ = CARRY((!\inst|s_counter[22]~82\) # (!\inst|s_counter\(23)))

-- pragma translate_off
GENERIC MAP (
	lut_mask => "0011110000111111",
	sum_lutc_input => "cin")
-- pragma translate_on
PORT MAP (
	datab => \inst|s_counter\(23),
	datad => VCC,
	cin => \inst|s_counter[22]~82\,
	combout => \inst|s_counter[23]~83_combout\,
	cout => \inst|s_counter[23]~84\);

-- Location: FF_X108_Y15_N17
\inst|s_counter[23]\ : dffeas
-- pragma translate_off
GENERIC MAP (
	is_wysiwyg => "true",
	power_up => "low")
-- pragma translate_on
PORT MAP (
	clk => \CLOCK_50~inputclkctrl_outclk\,
	d => \inst|s_counter[23]~83_combout\,
	sclr => \inst|s_counter[0]~43_combout\,
	ena => \inst|s_counter[0]~44_combout\,
	devclrn => ww_devclrn,
	devpor => ww_devpor,
	q => \inst|s_counter\(23));

-- Location: LCCOMB_X108_Y15_N18
\inst|s_counter[24]~85\ : cycloneive_lcell_comb
-- Equation(s):
-- \inst|s_counter[24]~85_combout\ = (\inst|s_counter\(24) & (\inst|s_counter[23]~84\ $ (GND))) # (!\inst|s_counter\(24) & (!\inst|s_counter[23]~84\ & VCC))
-- \inst|s_counter[24]~86\ = CARRY((\inst|s_counter\(24) & !\inst|s_counter[23]~84\))

-- pragma translate_off
GENERIC MAP (
	lut_mask => "1100001100001100",
	sum_lutc_input => "cin")
-- pragma translate_on
PORT MAP (
	datab => \inst|s_counter\(24),
	datad => VCC,
	cin => \inst|s_counter[23]~84\,
	combout => \inst|s_counter[24]~85_combout\,
	cout => \inst|s_counter[24]~86\);

-- Location: FF_X108_Y15_N19
\inst|s_counter[24]\ : dffeas
-- pragma translate_off
GENERIC MAP (
	is_wysiwyg => "true",
	power_up => "low")
-- pragma translate_on
PORT MAP (
	clk => \CLOCK_50~inputclkctrl_outclk\,
	d => \inst|s_counter[24]~85_combout\,
	sclr => \inst|s_counter[0]~43_combout\,
	ena => \inst|s_counter[0]~44_combout\,
	devclrn => ww_devclrn,
	devpor => ww_devpor,
	q => \inst|s_counter\(24));

-- Location: LCCOMB_X108_Y15_N20
\inst|s_counter[25]~87\ : cycloneive_lcell_comb
-- Equation(s):
-- \inst|s_counter[25]~87_combout\ = (\inst|s_counter\(25) & (!\inst|s_counter[24]~86\)) # (!\inst|s_counter\(25) & ((\inst|s_counter[24]~86\) # (GND)))
-- \inst|s_counter[25]~88\ = CARRY((!\inst|s_counter[24]~86\) # (!\inst|s_counter\(25)))

-- pragma translate_off
GENERIC MAP (
	lut_mask => "0011110000111111",
	sum_lutc_input => "cin")
-- pragma translate_on
PORT MAP (
	datab => \inst|s_counter\(25),
	datad => VCC,
	cin => \inst|s_counter[24]~86\,
	combout => \inst|s_counter[25]~87_combout\,
	cout => \inst|s_counter[25]~88\);

-- Location: FF_X108_Y15_N21
\inst|s_counter[25]\ : dffeas
-- pragma translate_off
GENERIC MAP (
	is_wysiwyg => "true",
	power_up => "low")
-- pragma translate_on
PORT MAP (
	clk => \CLOCK_50~inputclkctrl_outclk\,
	d => \inst|s_counter[25]~87_combout\,
	sclr => \inst|s_counter[0]~43_combout\,
	ena => \inst|s_counter[0]~44_combout\,
	devclrn => ww_devclrn,
	devpor => ww_devpor,
	q => \inst|s_counter\(25));

-- Location: LCCOMB_X108_Y15_N22
\inst|s_counter[26]~89\ : cycloneive_lcell_comb
-- Equation(s):
-- \inst|s_counter[26]~89_combout\ = (\inst|s_counter\(26) & (\inst|s_counter[25]~88\ $ (GND))) # (!\inst|s_counter\(26) & (!\inst|s_counter[25]~88\ & VCC))
-- \inst|s_counter[26]~90\ = CARRY((\inst|s_counter\(26) & !\inst|s_counter[25]~88\))

-- pragma translate_off
GENERIC MAP (
	lut_mask => "1010010100001010",
	sum_lutc_input => "cin")
-- pragma translate_on
PORT MAP (
	dataa => \inst|s_counter\(26),
	datad => VCC,
	cin => \inst|s_counter[25]~88\,
	combout => \inst|s_counter[26]~89_combout\,
	cout => \inst|s_counter[26]~90\);

-- Location: FF_X108_Y15_N23
\inst|s_counter[26]\ : dffeas
-- pragma translate_off
GENERIC MAP (
	is_wysiwyg => "true",
	power_up => "low")
-- pragma translate_on
PORT MAP (
	clk => \CLOCK_50~inputclkctrl_outclk\,
	d => \inst|s_counter[26]~89_combout\,
	sclr => \inst|s_counter[0]~43_combout\,
	ena => \inst|s_counter[0]~44_combout\,
	devclrn => ww_devclrn,
	devpor => ww_devpor,
	q => \inst|s_counter\(26));

-- Location: LCCOMB_X109_Y15_N6
\inst|process_0~5\ : cycloneive_lcell_comb
-- Equation(s):
-- \inst|process_0~5_combout\ = (!\inst|s_counter\(23) & (!\inst|s_counter\(26) & (!\inst|s_counter\(25) & !\inst|s_counter\(24))))

-- pragma translate_off
GENERIC MAP (
	lut_mask => "0000000000000001",
	sum_lutc_input => "datac")
-- pragma translate_on
PORT MAP (
	dataa => \inst|s_counter\(23),
	datab => \inst|s_counter\(26),
	datac => \inst|s_counter\(25),
	datad => \inst|s_counter\(24),
	combout => \inst|process_0~5_combout\);

-- Location: LCCOMB_X108_Y15_N24
\inst|s_counter[27]~91\ : cycloneive_lcell_comb
-- Equation(s):
-- \inst|s_counter[27]~91_combout\ = (\inst|s_counter\(27) & (!\inst|s_counter[26]~90\)) # (!\inst|s_counter\(27) & ((\inst|s_counter[26]~90\) # (GND)))
-- \inst|s_counter[27]~92\ = CARRY((!\inst|s_counter[26]~90\) # (!\inst|s_counter\(27)))

-- pragma translate_off
GENERIC MAP (
	lut_mask => "0011110000111111",
	sum_lutc_input => "cin")
-- pragma translate_on
PORT MAP (
	datab => \inst|s_counter\(27),
	datad => VCC,
	cin => \inst|s_counter[26]~90\,
	combout => \inst|s_counter[27]~91_combout\,
	cout => \inst|s_counter[27]~92\);

-- Location: FF_X108_Y15_N25
\inst|s_counter[27]\ : dffeas
-- pragma translate_off
GENERIC MAP (
	is_wysiwyg => "true",
	power_up => "low")
-- pragma translate_on
PORT MAP (
	clk => \CLOCK_50~inputclkctrl_outclk\,
	d => \inst|s_counter[27]~91_combout\,
	sclr => \inst|s_counter[0]~43_combout\,
	ena => \inst|s_counter[0]~44_combout\,
	devclrn => ww_devclrn,
	devpor => ww_devpor,
	q => \inst|s_counter\(27));

-- Location: LCCOMB_X108_Y15_N26
\inst|s_counter[28]~93\ : cycloneive_lcell_comb
-- Equation(s):
-- \inst|s_counter[28]~93_combout\ = (\inst|s_counter\(28) & (\inst|s_counter[27]~92\ $ (GND))) # (!\inst|s_counter\(28) & (!\inst|s_counter[27]~92\ & VCC))
-- \inst|s_counter[28]~94\ = CARRY((\inst|s_counter\(28) & !\inst|s_counter[27]~92\))

-- pragma translate_off
GENERIC MAP (
	lut_mask => "1010010100001010",
	sum_lutc_input => "cin")
-- pragma translate_on
PORT MAP (
	dataa => \inst|s_counter\(28),
	datad => VCC,
	cin => \inst|s_counter[27]~92\,
	combout => \inst|s_counter[28]~93_combout\,
	cout => \inst|s_counter[28]~94\);

-- Location: FF_X108_Y15_N27
\inst|s_counter[28]\ : dffeas
-- pragma translate_off
GENERIC MAP (
	is_wysiwyg => "true",
	power_up => "low")
-- pragma translate_on
PORT MAP (
	clk => \CLOCK_50~inputclkctrl_outclk\,
	d => \inst|s_counter[28]~93_combout\,
	sclr => \inst|s_counter[0]~43_combout\,
	ena => \inst|s_counter[0]~44_combout\,
	devclrn => ww_devclrn,
	devpor => ww_devpor,
	q => \inst|s_counter\(28));

-- Location: LCCOMB_X108_Y15_N28
\inst|s_counter[29]~95\ : cycloneive_lcell_comb
-- Equation(s):
-- \inst|s_counter[29]~95_combout\ = (\inst|s_counter\(29) & (!\inst|s_counter[28]~94\)) # (!\inst|s_counter\(29) & ((\inst|s_counter[28]~94\) # (GND)))
-- \inst|s_counter[29]~96\ = CARRY((!\inst|s_counter[28]~94\) # (!\inst|s_counter\(29)))

-- pragma translate_off
GENERIC MAP (
	lut_mask => "0011110000111111",
	sum_lutc_input => "cin")
-- pragma translate_on
PORT MAP (
	datab => \inst|s_counter\(29),
	datad => VCC,
	cin => \inst|s_counter[28]~94\,
	combout => \inst|s_counter[29]~95_combout\,
	cout => \inst|s_counter[29]~96\);

-- Location: FF_X108_Y15_N29
\inst|s_counter[29]\ : dffeas
-- pragma translate_off
GENERIC MAP (
	is_wysiwyg => "true",
	power_up => "low")
-- pragma translate_on
PORT MAP (
	clk => \CLOCK_50~inputclkctrl_outclk\,
	d => \inst|s_counter[29]~95_combout\,
	sclr => \inst|s_counter[0]~43_combout\,
	ena => \inst|s_counter[0]~44_combout\,
	devclrn => ww_devclrn,
	devpor => ww_devpor,
	q => \inst|s_counter\(29));

-- Location: LCCOMB_X108_Y15_N30
\inst|s_counter[30]~97\ : cycloneive_lcell_comb
-- Equation(s):
-- \inst|s_counter[30]~97_combout\ = \inst|s_counter\(30) $ (!\inst|s_counter[29]~96\)

-- pragma translate_off
GENERIC MAP (
	lut_mask => "1010010110100101",
	sum_lutc_input => "cin")
-- pragma translate_on
PORT MAP (
	dataa => \inst|s_counter\(30),
	cin => \inst|s_counter[29]~96\,
	combout => \inst|s_counter[30]~97_combout\);

-- Location: FF_X108_Y15_N31
\inst|s_counter[30]\ : dffeas
-- pragma translate_off
GENERIC MAP (
	is_wysiwyg => "true",
	power_up => "low")
-- pragma translate_on
PORT MAP (
	clk => \CLOCK_50~inputclkctrl_outclk\,
	d => \inst|s_counter[30]~97_combout\,
	sclr => \inst|s_counter[0]~43_combout\,
	ena => \inst|s_counter[0]~44_combout\,
	devclrn => ww_devclrn,
	devpor => ww_devpor,
	q => \inst|s_counter\(30));

-- Location: LCCOMB_X109_Y15_N4
\inst|process_0~6\ : cycloneive_lcell_comb
-- Equation(s):
-- \inst|process_0~6_combout\ = (!\inst|s_counter\(28) & (!\inst|s_counter\(29) & (!\inst|s_counter\(30) & !\inst|s_counter\(27))))

-- pragma translate_off
GENERIC MAP (
	lut_mask => "0000000000000001",
	sum_lutc_input => "datac")
-- pragma translate_on
PORT MAP (
	dataa => \inst|s_counter\(28),
	datab => \inst|s_counter\(29),
	datac => \inst|s_counter\(30),
	datad => \inst|s_counter\(27),
	combout => \inst|process_0~6_combout\);

-- Location: LCCOMB_X109_Y15_N30
\inst|Equal1~0\ : cycloneive_lcell_comb
-- Equation(s):
-- \inst|Equal1~0_combout\ = (!\inst|s_counter\(1) & (!\inst|s_counter\(3) & (!\inst|s_counter\(4) & !\inst|s_counter\(0))))

-- pragma translate_off
GENERIC MAP (
	lut_mask => "0000000000000001",
	sum_lutc_input => "datac")
-- pragma translate_on
PORT MAP (
	dataa => \inst|s_counter\(1),
	datab => \inst|s_counter\(3),
	datac => \inst|s_counter\(4),
	datad => \inst|s_counter\(0),
	combout => \inst|Equal1~0_combout\);

-- Location: LCCOMB_X109_Y15_N8
\inst|Equal1~1\ : cycloneive_lcell_comb
-- Equation(s):
-- \inst|Equal1~1_combout\ = (((!\inst|s_counter\(5)) # (!\inst|Equal1~0_combout\)) # (!\inst|s_counter\(2))) # (!\inst|s_counter\(6))

-- pragma translate_off
GENERIC MAP (
	lut_mask => "0111111111111111",
	sum_lutc_input => "datac")
-- pragma translate_on
PORT MAP (
	dataa => \inst|s_counter\(6),
	datab => \inst|s_counter\(2),
	datac => \inst|Equal1~0_combout\,
	datad => \inst|s_counter\(5),
	combout => \inst|Equal1~1_combout\);

-- Location: LCCOMB_X108_Y16_N0
\inst|process_0~0\ : cycloneive_lcell_comb
-- Equation(s):
-- \inst|process_0~0_combout\ = (!\inst|s_counter\(10) & (!\inst|s_counter\(7) & (!\inst|s_counter\(8) & !\inst|s_counter\(9))))

-- pragma translate_off
GENERIC MAP (
	lut_mask => "0000000000000001",
	sum_lutc_input => "datac")
-- pragma translate_on
PORT MAP (
	dataa => \inst|s_counter\(10),
	datab => \inst|s_counter\(7),
	datac => \inst|s_counter\(8),
	datad => \inst|s_counter\(9),
	combout => \inst|process_0~0_combout\);

-- Location: LCCOMB_X109_Y15_N2
\inst|process_0~2\ : cycloneive_lcell_comb
-- Equation(s):
-- \inst|process_0~2_combout\ = (!\inst|s_counter\(15) & (!\inst|s_counter\(17) & (!\inst|s_counter\(18) & !\inst|s_counter\(16))))

-- pragma translate_off
GENERIC MAP (
	lut_mask => "0000000000000001",
	sum_lutc_input => "datac")
-- pragma translate_on
PORT MAP (
	dataa => \inst|s_counter\(15),
	datab => \inst|s_counter\(17),
	datac => \inst|s_counter\(18),
	datad => \inst|s_counter\(16),
	combout => \inst|process_0~2_combout\);

-- Location: LCCOMB_X109_Y15_N14
\inst|process_0~3\ : cycloneive_lcell_comb
-- Equation(s):
-- \inst|process_0~3_combout\ = (!\inst|s_counter\(22) & (!\inst|s_counter\(20) & (!\inst|s_counter\(19) & !\inst|s_counter\(21))))

-- pragma translate_off
GENERIC MAP (
	lut_mask => "0000000000000001",
	sum_lutc_input => "datac")
-- pragma translate_on
PORT MAP (
	dataa => \inst|s_counter\(22),
	datab => \inst|s_counter\(20),
	datac => \inst|s_counter\(19),
	datad => \inst|s_counter\(21),
	combout => \inst|process_0~3_combout\);

-- Location: LCCOMB_X109_Y15_N18
\inst|process_0~1\ : cycloneive_lcell_comb
-- Equation(s):
-- \inst|process_0~1_combout\ = (!\inst|s_counter\(11) & (!\inst|s_counter\(13) & (!\inst|s_counter\(12) & !\inst|s_counter\(14))))

-- pragma translate_off
GENERIC MAP (
	lut_mask => "0000000000000001",
	sum_lutc_input => "datac")
-- pragma translate_on
PORT MAP (
	dataa => \inst|s_counter\(11),
	datab => \inst|s_counter\(13),
	datac => \inst|s_counter\(12),
	datad => \inst|s_counter\(14),
	combout => \inst|process_0~1_combout\);

-- Location: LCCOMB_X109_Y15_N16
\inst|process_0~4\ : cycloneive_lcell_comb
-- Equation(s):
-- \inst|process_0~4_combout\ = (\inst|process_0~0_combout\ & (\inst|process_0~2_combout\ & (\inst|process_0~3_combout\ & \inst|process_0~1_combout\)))

-- pragma translate_off
GENERIC MAP (
	lut_mask => "1000000000000000",
	sum_lutc_input => "datac")
-- pragma translate_on
PORT MAP (
	dataa => \inst|process_0~0_combout\,
	datab => \inst|process_0~2_combout\,
	datac => \inst|process_0~3_combout\,
	datad => \inst|process_0~1_combout\,
	combout => \inst|process_0~4_combout\);

-- Location: LCCOMB_X109_Y15_N22
\inst|Equal1~2\ : cycloneive_lcell_comb
-- Equation(s):
-- \inst|Equal1~2_combout\ = (((\inst|Equal1~1_combout\) # (!\inst|process_0~4_combout\)) # (!\inst|process_0~6_combout\)) # (!\inst|process_0~5_combout\)

-- pragma translate_off
GENERIC MAP (
	lut_mask => "1111011111111111",
	sum_lutc_input => "datac")
-- pragma translate_on
PORT MAP (
	dataa => \inst|process_0~5_combout\,
	datab => \inst|process_0~6_combout\,
	datac => \inst|Equal1~1_combout\,
	datad => \inst|process_0~4_combout\,
	combout => \inst|Equal1~2_combout\);

-- Location: LCCOMB_X109_Y15_N10
\inst|s_counter[0]~44\ : cycloneive_lcell_comb
-- Equation(s):
-- \inst|s_counter[0]~44_combout\ = (\SW[1]~input_o\) # ((\SW[0]~input_o\ & ((!\inst|s_counter[0]~34_combout\) # (!\inst|Equal1~2_combout\))))

-- pragma translate_off
GENERIC MAP (
	lut_mask => "1100111011101110",
	sum_lutc_input => "datac")
-- pragma translate_on
PORT MAP (
	dataa => \SW[0]~input_o\,
	datab => \SW[1]~input_o\,
	datac => \inst|Equal1~2_combout\,
	datad => \inst|s_counter[0]~34_combout\,
	combout => \inst|s_counter[0]~44_combout\);

-- Location: FF_X108_Y16_N3
\inst|s_counter[0]\ : dffeas
-- pragma translate_off
GENERIC MAP (
	is_wysiwyg => "true",
	power_up => "low")
-- pragma translate_on
PORT MAP (
	clk => \CLOCK_50~inputclkctrl_outclk\,
	d => \inst|s_counter[0]~35_combout\,
	sclr => \inst|s_counter[0]~43_combout\,
	ena => \inst|s_counter[0]~44_combout\,
	devclrn => ww_devclrn,
	devpor => ww_devpor,
	q => \inst|s_counter\(0));

-- Location: LCCOMB_X108_Y16_N4
\inst|s_counter[1]~37\ : cycloneive_lcell_comb
-- Equation(s):
-- \inst|s_counter[1]~37_combout\ = (\inst|s_counter\(1) & (!\inst|s_counter[0]~36\)) # (!\inst|s_counter\(1) & ((\inst|s_counter[0]~36\) # (GND)))
-- \inst|s_counter[1]~38\ = CARRY((!\inst|s_counter[0]~36\) # (!\inst|s_counter\(1)))

-- pragma translate_off
GENERIC MAP (
	lut_mask => "0011110000111111",
	sum_lutc_input => "cin")
-- pragma translate_on
PORT MAP (
	datab => \inst|s_counter\(1),
	datad => VCC,
	cin => \inst|s_counter[0]~36\,
	combout => \inst|s_counter[1]~37_combout\,
	cout => \inst|s_counter[1]~38\);

-- Location: FF_X108_Y16_N5
\inst|s_counter[1]\ : dffeas
-- pragma translate_off
GENERIC MAP (
	is_wysiwyg => "true",
	power_up => "low")
-- pragma translate_on
PORT MAP (
	clk => \CLOCK_50~inputclkctrl_outclk\,
	d => \inst|s_counter[1]~37_combout\,
	sclr => \inst|s_counter[0]~43_combout\,
	ena => \inst|s_counter[0]~44_combout\,
	devclrn => ww_devclrn,
	devpor => ww_devpor,
	q => \inst|s_counter\(1));

-- Location: LCCOMB_X108_Y16_N6
\inst|s_counter[2]~39\ : cycloneive_lcell_comb
-- Equation(s):
-- \inst|s_counter[2]~39_combout\ = (\inst|s_counter\(2) & (\inst|s_counter[1]~38\ $ (GND))) # (!\inst|s_counter\(2) & (!\inst|s_counter[1]~38\ & VCC))
-- \inst|s_counter[2]~40\ = CARRY((\inst|s_counter\(2) & !\inst|s_counter[1]~38\))

-- pragma translate_off
GENERIC MAP (
	lut_mask => "1010010100001010",
	sum_lutc_input => "cin")
-- pragma translate_on
PORT MAP (
	dataa => \inst|s_counter\(2),
	datad => VCC,
	cin => \inst|s_counter[1]~38\,
	combout => \inst|s_counter[2]~39_combout\,
	cout => \inst|s_counter[2]~40\);

-- Location: FF_X108_Y16_N7
\inst|s_counter[2]\ : dffeas
-- pragma translate_off
GENERIC MAP (
	is_wysiwyg => "true",
	power_up => "low")
-- pragma translate_on
PORT MAP (
	clk => \CLOCK_50~inputclkctrl_outclk\,
	d => \inst|s_counter[2]~39_combout\,
	sclr => \inst|s_counter[0]~43_combout\,
	ena => \inst|s_counter[0]~44_combout\,
	devclrn => ww_devclrn,
	devpor => ww_devpor,
	q => \inst|s_counter\(2));

-- Location: LCCOMB_X108_Y16_N8
\inst|s_counter[3]~41\ : cycloneive_lcell_comb
-- Equation(s):
-- \inst|s_counter[3]~41_combout\ = (\inst|s_counter\(3) & (!\inst|s_counter[2]~40\)) # (!\inst|s_counter\(3) & ((\inst|s_counter[2]~40\) # (GND)))
-- \inst|s_counter[3]~42\ = CARRY((!\inst|s_counter[2]~40\) # (!\inst|s_counter\(3)))

-- pragma translate_off
GENERIC MAP (
	lut_mask => "0011110000111111",
	sum_lutc_input => "cin")
-- pragma translate_on
PORT MAP (
	datab => \inst|s_counter\(3),
	datad => VCC,
	cin => \inst|s_counter[2]~40\,
	combout => \inst|s_counter[3]~41_combout\,
	cout => \inst|s_counter[3]~42\);

-- Location: FF_X108_Y16_N9
\inst|s_counter[3]\ : dffeas
-- pragma translate_off
GENERIC MAP (
	is_wysiwyg => "true",
	power_up => "low")
-- pragma translate_on
PORT MAP (
	clk => \CLOCK_50~inputclkctrl_outclk\,
	d => \inst|s_counter[3]~41_combout\,
	sclr => \inst|s_counter[0]~43_combout\,
	ena => \inst|s_counter[0]~44_combout\,
	devclrn => ww_devclrn,
	devpor => ww_devpor,
	q => \inst|s_counter\(3));

-- Location: LCCOMB_X108_Y16_N10
\inst|s_counter[4]~45\ : cycloneive_lcell_comb
-- Equation(s):
-- \inst|s_counter[4]~45_combout\ = (\inst|s_counter\(4) & (\inst|s_counter[3]~42\ $ (GND))) # (!\inst|s_counter\(4) & (!\inst|s_counter[3]~42\ & VCC))
-- \inst|s_counter[4]~46\ = CARRY((\inst|s_counter\(4) & !\inst|s_counter[3]~42\))

-- pragma translate_off
GENERIC MAP (
	lut_mask => "1010010100001010",
	sum_lutc_input => "cin")
-- pragma translate_on
PORT MAP (
	dataa => \inst|s_counter\(4),
	datad => VCC,
	cin => \inst|s_counter[3]~42\,
	combout => \inst|s_counter[4]~45_combout\,
	cout => \inst|s_counter[4]~46\);

-- Location: FF_X108_Y16_N11
\inst|s_counter[4]\ : dffeas
-- pragma translate_off
GENERIC MAP (
	is_wysiwyg => "true",
	power_up => "low")
-- pragma translate_on
PORT MAP (
	clk => \CLOCK_50~inputclkctrl_outclk\,
	d => \inst|s_counter[4]~45_combout\,
	sclr => \inst|s_counter[0]~43_combout\,
	ena => \inst|s_counter[0]~44_combout\,
	devclrn => ww_devclrn,
	devpor => ww_devpor,
	q => \inst|s_counter\(4));

-- Location: FF_X108_Y16_N13
\inst|s_counter[5]\ : dffeas
-- pragma translate_off
GENERIC MAP (
	is_wysiwyg => "true",
	power_up => "low")
-- pragma translate_on
PORT MAP (
	clk => \CLOCK_50~inputclkctrl_outclk\,
	d => \inst|s_counter[5]~47_combout\,
	sclr => \inst|s_counter[0]~43_combout\,
	ena => \inst|s_counter[0]~44_combout\,
	devclrn => ww_devclrn,
	devpor => ww_devpor,
	q => \inst|s_counter\(5));

-- Location: LCCOMB_X109_Y15_N20
\inst|s_counter[0]~32\ : cycloneive_lcell_comb
-- Equation(s):
-- \inst|s_counter[0]~32_combout\ = (\inst|s_counter\(2)) # ((\inst|s_counter\(6) & ((\inst|s_counter\(3)) # (\inst|s_counter\(4)))))

-- pragma translate_off
GENERIC MAP (
	lut_mask => "1111111110101000",
	sum_lutc_input => "datac")
-- pragma translate_on
PORT MAP (
	dataa => \inst|s_counter\(6),
	datab => \inst|s_counter\(3),
	datac => \inst|s_counter\(4),
	datad => \inst|s_counter\(2),
	combout => \inst|s_counter[0]~32_combout\);

-- Location: LCCOMB_X109_Y15_N26
\inst|s_counter[0]~33\ : cycloneive_lcell_comb
-- Equation(s):
-- \inst|s_counter[0]~33_combout\ = (\inst|s_counter\(6) & (\inst|s_counter\(5) & ((\inst|s_counter[0]~32_combout\)))) # (!\inst|s_counter\(6) & (!\inst|s_counter\(5) & (\inst|Equal1~0_combout\ & !\inst|s_counter[0]~32_combout\)))

-- pragma translate_off
GENERIC MAP (
	lut_mask => "1000100000010000",
	sum_lutc_input => "datac")
-- pragma translate_on
PORT MAP (
	dataa => \inst|s_counter\(6),
	datab => \inst|s_counter\(5),
	datac => \inst|Equal1~0_combout\,
	datad => \inst|s_counter[0]~32_combout\,
	combout => \inst|s_counter[0]~33_combout\);

-- Location: LCCOMB_X109_Y15_N28
\inst|s_counter[0]~31\ : cycloneive_lcell_comb
-- Equation(s):
-- \inst|s_counter[0]~31_combout\ = (\inst|process_0~5_combout\ & (\inst|process_0~6_combout\ & \inst|process_0~4_combout\))

-- pragma translate_off
GENERIC MAP (
	lut_mask => "1000100000000000",
	sum_lutc_input => "datac")
-- pragma translate_on
PORT MAP (
	dataa => \inst|process_0~5_combout\,
	datab => \inst|process_0~6_combout\,
	datad => \inst|process_0~4_combout\,
	combout => \inst|s_counter[0]~31_combout\);

-- Location: LCCOMB_X109_Y15_N24
\inst|s_counter[0]~34\ : cycloneive_lcell_comb
-- Equation(s):
-- \inst|s_counter[0]~34_combout\ = ((\inst|s_counter[0]~33_combout\ & ((\inst|s_counter\(5)) # (!\SW[2]~input_o\)))) # (!\inst|s_counter[0]~31_combout\)

-- pragma translate_off
GENERIC MAP (
	lut_mask => "1101000011111111",
	sum_lutc_input => "datac")
-- pragma translate_on
PORT MAP (
	dataa => \SW[2]~input_o\,
	datab => \inst|s_counter\(5),
	datac => \inst|s_counter[0]~33_combout\,
	datad => \inst|s_counter[0]~31_combout\,
	combout => \inst|s_counter[0]~34_combout\);

-- Location: LCCOMB_X110_Y15_N24
\inst|timerOut~0\ : cycloneive_lcell_comb
-- Equation(s):
-- \inst|timerOut~0_combout\ = (!\SW[1]~input_o\ & (\inst|timerOut~q\ & ((\inst|Equal1~2_combout\) # (!\SW[0]~input_o\))))

-- pragma translate_off
GENERIC MAP (
	lut_mask => "0011000000010000",
	sum_lutc_input => "datac")
-- pragma translate_on
PORT MAP (
	dataa => \SW[0]~input_o\,
	datab => \SW[1]~input_o\,
	datac => \inst|timerOut~q\,
	datad => \inst|Equal1~2_combout\,
	combout => \inst|timerOut~0_combout\);

-- Location: LCCOMB_X109_Y15_N12
\inst|timerOut~1\ : cycloneive_lcell_comb
-- Equation(s):
-- \inst|timerOut~1_combout\ = (\inst|timerOut~0_combout\) # ((\SW[0]~input_o\ & (!\inst|s_counter[0]~34_combout\ & !\SW[1]~input_o\)))

-- pragma translate_off
GENERIC MAP (
	lut_mask => "1111111100000010",
	sum_lutc_input => "datac")
-- pragma translate_on
PORT MAP (
	dataa => \SW[0]~input_o\,
	datab => \inst|s_counter[0]~34_combout\,
	datac => \SW[1]~input_o\,
	datad => \inst|timerOut~0_combout\,
	combout => \inst|timerOut~1_combout\);

-- Location: FF_X109_Y15_N13
\inst|timerOut\ : dffeas
-- pragma translate_off
GENERIC MAP (
	is_wysiwyg => "true",
	power_up => "low")
-- pragma translate_on
PORT MAP (
	clk => \CLOCK_50~inputclkctrl_outclk\,
	d => \inst|timerOut~1_combout\,
	devclrn => ww_devclrn,
	devpor => ww_devpor,
	q => \inst|timerOut~q\);

ww_LEDR(0) <= \LEDR[0]~output_o\;
END structure;


