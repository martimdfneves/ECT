library IEEE;

use IEEE.STD_LOGIC_1164.all;


ENTITY CRC_ENCODER IS
	PORT( dIN_8,dIN_9,dIN_10,dIN_11 : IN STD_LOGIC;
			dIN_12,dIN_13,dIN_14,dIN_15 : IN STD_LOGIC;
			dIN_16,dIN_17,dIN_18,dIN_19 : IN STD_LOGIC;
			dIN_20,dIN_21,dIN_22,dIN_23 : IN STD_LOGIC;
			CRC_0,CRC_1,CRC_2,CRC_3 : OUT STD_LOGIC;
			CRC_4,CRC_5,CRC_6,CRC_7 : OUT STD_LOGIC
	);
END CRC_ENCODER;

ARCHITECTURE Structural OF CRC_ENCODER IS
SIGNAL d16_17,d8_12,d11_13,d10_22					: STD_LOGIC;
SIGNAL d18_19,d20_23,d9_21,d14_15					: STD_LOGIC;
SIGNAL d15_8,d14_17,d10_20,d17_18					: STD_LOGIC;
SIGNAL composite00, composite01,composite02		: STD_LOGIC;
SIGNAL sig0_0,sig0_1,sig1_0,sig1_1,sig2_0			: STD_LOGIC;
SIGNAL sig2_1,sig3_0,sig3_1,sig4_0,sig4_1			: STD_LOGIC;
SIGNAL sig5_0,sig5_1,sig6_0,sig7_0,sig7_1			: STD_LOGIC;

component exor
    port(x0,x1 : in STD_LOGIC;
         y: out STD_LOGIC);
end component;

BEGIN
	
	---------XORS---------
	d_16_17: exor port map(dIN_16,dIN_17,d16_17); --- USED
	d_8_12 : exor port map(dIN_8,dIN_12,d8_12);   --- USED
	d_11_13: exor port map(dIN_11,dIN_13,d11_13); --- USED
	d_10_22: exor port map(dIN_10,dIN_22,d10_22); --- USED
	d_18_19: exor port map(dIN_18,dIN_19,d18_19); --- USED
	d_20_23: exor port map(dIN_20,dIN_23,d20_23); --- USED
	d_9_21 : exor port map(dIN_9,dIN_21,d9_21);   --- USED
	d_14_15: exor port map(dIN_14,dIN_15,d14_15); --- USED
	d_15_8 : exor port map(dIN_15,dIN_8,d15_8);   --- USED
	d_14_17: exor port map(dIN_14,dIN_17,d14_17); --- USED
	d_10_20: exor port map(dIN_10,dIN_20,d10_20); --- USED
	d_17_18: exor port map(dIN_17,dIN_18,d17_18); --- USED
	
	--------Composite---------
	compos1617_1415	: exor port map(d16_17,d14_15,composite00); --- USED: 3
	compos1819_2023	: exor port map(d18_19,d20_23,composite01); --- USED: 2
	compos1113_921		: exor port map(d11_13,d9_21, composite02); --- USED: 2
	
	---------x0------------
	x_00	: exor port map(composite01,d11_13,sig0_0);
	x_01	: exor port map(d16_17,d15_8,sig0_1);
	x0		: exor port map(sig0_0,sig0_1,CRC_0);
	
	---------x1-------------
	x_10	: exor port map(composite02,dIN_23,sig1_0);
	x_11	: exor port map(d8_12,d14_15,sig1_1);
	x1		: exor port map(sig1_0,sig1_1,CRC_1);
	
	---------x2--------------
	x_20	: exor port map(composite01,d14_17,sig2_0);
	x_21	: exor port map(d8_12,d10_22,sig2_1);
	x2		: exor port map(sig2_0,sig2_1,CRC_2);
	
	--------x3---------------
	x_30 	: exor port map(d16_17,d8_12,sig3_0);
	x_31	: exor port map(d9_21,dIN_10,sig3_1);
	x3		: exor port map(sig3_0,sig3_1,CRC_3);
	
	--------x4---------------
	x_40	: exor port map(d11_13,d10_22, sig4_0);
	x_41	: exor port map(d17_18, dIN_9,sig4_1);
	x4		: exor port map (sig4_0,sig4_1,CRC_4);
	
	-----------x5-------------
	x_50	: exor port map(composite00,d10_20,sig5_0);
	x_51	: exor port map(d8_12,dIN_13,sig5_1);
	x5		: exor port map(sig5_0,sig5_1,CRC_5);
	
	-----------x6--------------
	x_60	: exor port map(composite00,composite01,sig6_0);
	x6		: exor port map(sig6_0,dIN_18,CRC_6);
	
	-----------x7--------------
	x_70	: exor port map(composite00,d18_19,sig7_0);
	x_71	: exor port map(d10_22,dIN_12,sig7_1);
	x7		: exor port map(sig7_0,sig7_1);
	
	
END Structural;