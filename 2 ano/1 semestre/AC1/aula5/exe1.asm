 	.data
 	.eqv SIZE,5
str1: 	.asciiz "\nIntroduza um numero: "
 	.align 2
lista:	.space 20 # SIZE * 4
 	.eqv read_int,5
 	.eqv print_string,4
 	.text
 	.globl main
 	
main: 	li $t0,0 # i = 0;
while: 	bge $t0,SIZE,endw # while(i < SIZE) {
 	la $a0,str1 # print_string(...);
 	li $v0,print_string
 	syscall
 	li $v0,read_int
 	syscall # $v0 = read_int();
 	la $t1,lista # $t1 = lista (ou &lista[0])
 	sll $t2,$t0,2 #
 	addu $t2,$t1,$t3 # $t2 = &lista[i]
 	sw $v0,0($t2) # lista[i] = read_int();
 	addi $t0,$t0,1 # i++
 	sll $t3,$t0,2
 	j while
 	
endw: 	jr $ra # termina programa 