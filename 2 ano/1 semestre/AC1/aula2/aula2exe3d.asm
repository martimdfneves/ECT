	.data
str1: 	.asciiz "Introduza 2 numeros\n"
str2: 	.asciiz "A soma dos dois numeros é: "
 	.eqv print_string,4
 	.eqv read_int,5
 	.eqv print_int10,1
 	.text
 	.globl main
 	
main: 	la $a0,str1
 	li $v0,print_string
 	syscall # print_string(str1);
 	li $v0,read_int
 	syscall # valor lido é retornado em $v0
 	move $t0,$v0 # $t0=read_int()
 	li $v0,read_int
 	syscall
 	move $t1,$v0 #$t1=read_int()
 	add $t1,$t1,$t0
 	la $a0,str2
 	li $v0,print_string
 	syscall # print_string(str2);
 	move $a0,$t1
 	li $v0,print_int10
 	syscall # print_int10;
 	jr $ra # fim do programa