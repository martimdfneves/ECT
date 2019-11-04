transcript on
if {[file exists rtl_work]} {
	vdel -lib rtl_work -all
}
vlib rtl_work
vmap work rtl_work

vcom -93 -work work {C:/Users/Martim/Desktop/Uni/1 ano 2 semestre/LSD/aula 7/parte 1/Dec2_4En.vhd}

