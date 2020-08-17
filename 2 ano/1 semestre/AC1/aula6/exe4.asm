	.data
str1:	.asciiz "Arquitetura de "
str2:	.asciiz "Computadores I"
str3:	.asciiz "\n"
string:	.space 50
	.eqv print_string, 4
	.eqv func, $v0
	.eqv arg0, $a0
	.eqv arg1, $a1
	.eqv p1, $t0
	.eqv p2, $t1
	.eqv len, $t2
	.eqv char, $t3
	.eqv nCont, $s0
	.eqv total, $s1
	.eqv callSave, $s7
 	.text
 	.globl main
 	
 main:
	la callSave, ($ra)
	la arg0, string
	la arg1, str1
	jal strcopy
	la arg0, (func)
	li func, print_string
	syscall
	la arg0, str3
	syscall
	la arg0, string
	la arg1, str2
	jal strcat
	la arg0, (func)
	li func, print_string
	syscall
	la $ra, (callSave)
	jr $ra
	
################################################################
# string concatenate
strcat:	
	la $t9, ($ra)
	jal strlen
	la $ra, ($t9)
	move len, func
	move p1, arg0
	move p2, arg1
	addu p1, p1, len
	subi p1, p1, 1
whiles:
	lb char, (p2)
	beq char, '\0', endWhiles
	sb char, (p1)
	addi p1, p1, 1
	addi p2, p2, 1
	j whiles
	
endWhiles:
	move func, arg0
	jr $ra

################################################################
# string copy
strcopy:
	li len, 0
	move p1, arg0
	move p2, arg1
do:	lb char, (p2)
	sb char, (p1)
	addiu p1, p1, 1
	addiu p2, p2, 1
	bne char, '\0', do
	
	move func, arg0
	jr $ra	

########################################
# string length
strlen:
	li len, 0
	move p1, arg0
while:	lb char, (p1)
	addi len, len, 1
	beq char, '\0', endWhile
	addi p1, p1, 1
	j while

endWhile:
	move func, len
	jr $ra