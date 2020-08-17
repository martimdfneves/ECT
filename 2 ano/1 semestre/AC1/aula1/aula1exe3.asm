	.data
str1:.asciiz "\n"
	.text
	.globl main
main: 	li $v0,5 
	syscall # chamada ao syscall "read_int()"
	or $t0,$0,$v0 # $t0 = $v0 = valor lido do teclado
		      # (valor de x pretendido)
	li $t2,8 # $t2 = 8
	add $t1,$t0,$t0 # $t1 = $t0 + $t0 = x + x = 2 * x
	sub $t1,$t1,$t2 # $t1 = $t1 + $t2 = y = 2 * x - 8
 		        # ($t1 tem o valor calculado de y)
	or $a0,$0,$t1 # $a0 = y
	li $v0,1 #
	syscall # chamada ao syscall "print_int10()"
	la $a0,str1
	li $v0,4
	syscall
	
	or $a0,$0,$t1 # $a0 = y
	li $v0,34 #
	syscall # chamada ao syscall "print_int16()"
	la $a0,str1
	li $v0,4
	syscall
	
	or $a0,$0,$t1 # $a0 = y
	li $v0,36 #
	syscall # chamada ao syscall "print_intu10()"
	la $a0,str1
	li $v0,4
	syscall
	
	jr $ra # fim do programa