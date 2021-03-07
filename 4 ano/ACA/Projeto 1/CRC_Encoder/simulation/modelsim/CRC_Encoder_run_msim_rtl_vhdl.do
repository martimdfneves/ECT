transcript on
if {[file exists rtl_work]} {
	vdel -lib rtl_work -all
}
vlib rtl_work
vmap work rtl_work

vcom -93 -work work {C:/Users/Martim/Desktop/Uni/4_ano_1_semestre/ACA/Projeto 1/CRC_Encoder/CRC_Encoder.vhd}
vcom -93 -work work {C:/Users/Martim/Desktop/Uni/4_ano_1_semestre/ACA/Projeto 1/CRC_Encoder/CRC_ENCODER_STD.vhd}
vcom -93 -work work {C:/Users/Martim/Desktop/Uni/4_ano_1_semestre/ACA/Projeto 1/CRC_Encoder/exor1.vhd}

