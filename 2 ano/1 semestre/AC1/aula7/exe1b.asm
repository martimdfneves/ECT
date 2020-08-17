	.data
	.eqv print_int10, 1
string:	.asciiz "2016 e 2020 sao anos bissextos"
	.text
	.globl main
	
main:
	subiu $sp, $sp, 4
	sw $ra, ($sp)
	la $a0, string
	jal atoi
	move $a0, $v0
	li $v0, print_int10
	syscall
	lw $ra, ($sp)
	addiu $sp, $sp, 4
	jr $ra
	
atoi:
	li $v0,0 # res = 0;
	
while: 	lb $t0,0($a0) # while(*s >= ...)
 	blt $t0, '0', endw #
 	bgt $t0, '9', endw # {
 	sub $t1, $t0, '0' # digit = *s � '0'
 	addiu $a0, $a0, 1 # s++;
 	mul $v0,$v0,10 # res = 10 * res;
 	add $v0, $v0, $t1 # res = 10 * res + digit;
 	j while # }
 	
endw: 	jr $ra # termina sub-rotina 