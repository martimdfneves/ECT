	.data
str1:	.asciiz "AC1 - 2019 grande nóia"
	.text
	.globl main
	
main:	addiu $sp,$sp,-4	# reserva 4 bytes na stack
	sw $ra, 0($sp)	# 
	
	la $a0,str1	
	jal strlen		# $ra

	move $a0,$v0
	li $v0,1
	syscall
	#
	#li $v0,10
	#syscall

	lw  $ra, 0($sp)
	addiu $sp,$sp,4
	jr $ra	
#----------------------------<--------main

#int strlen(char *s) 				
strlen:	li $v0,0 		# len = 0;

while: 	lb $t0,0($a0) 		# while(*s++ != '\0')
 	addiu $a0,$a0,1		# s++
 	beq $t0,'\0',endw 	# {
 	addi $v0,$v0,1 		# len++;
 	j while 			# }
 	
endw: 	#move $v0,$t1 		# return len;
 	jr $ra 			# 