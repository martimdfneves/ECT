	.data
str1: 	.asciiz "Introduza um numero: "
str2: 	.asciiz "\nO valor em binário é: "
 	.eqv print_string,4
 	.eqv read_int,5
 	.eqv print_char,11
 	.text
 	.globl main
 	
 main:	la $a0,str1
 	li $v0,print_string # (instrução virtual)
 	syscall # print_string(str1);
 	
 	li $v0,read_int # value=read_int();
 	syscall
 	move $t0,$v0
 	
 	la $a0,str2 # print_string("...");
 	li $v0,print_string
 	syscall
 	
 	li $t2,0 # i = 0
 	li $t5,0
 	
for: 	bge $t2,32,endfor # while(i < 32) {
	srl $t1,$t0,31
	
ifFlag:	bne $t5,$0,if1
	beq $t1,$0,endif

if1:	li $t5,1
	rem $t4,$t2,4
	bne $t4,0,if
	li $a0,' ' # print_char(' ');
 	li $v0,print_char
 	syscall

if: 	ori $t4,$t1,0x30
 	move $a0,$t4 
 	li $v0,print_char
 	syscall
 	j endif
 	 	
endif: 	sll $t0,$t0,1  # value = value << 1;
 	addi $t2,$t2,1  # i++;
 	j for # }
 	
endfor: 	#
 	jr $ra # fim do programa 
