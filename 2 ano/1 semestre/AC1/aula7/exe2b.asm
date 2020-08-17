	.data
	.eqv STRING_MAX_SIZE
string:	.space 33
str2:	.asciiz "\n"
	.eqv read_int, 5
	.eqv print_string, 4
	.eqv func, $v0
	.eqv retVal, $v0
	.eqv arg0, $a0
	.eqv arg1, $a1
	.eqv arg2, $a2
	.eqv arg3, $a3
	.eqv val, $s0
	.eqv str, $s1
	.text
	.globl main

main:
	subiu $sp, $sp, 4
	sw $ra, ($sp)
	la str, string
mdo:
	li func, read_int
	syscall

	# base 2
	move val, func
	move arg0, val
	li   arg1, 2
	la   arg2, (str)
	jal  itoa
	move arg0, retVal
	li   func, print_string
	syscall 
	la arg0, str2
	syscall

	# base 8
	move arg0, val
	li   arg1, 8
	la   arg2, (str)
	jal  itoa
	move arg0, retVal
	li   func, print_string
	syscall 
	la arg0, str2
	syscall 
	
	# base 16
	move arg0, val
	li   arg1, 16
	la   arg2, (str)
	jal  itoa
	move arg0, retVal
	li   func, print_string
	syscall 
	la arg0, str2
	syscall 	

	bne val, 0, mdo
			
	lw $ra, ($sp)
	addiu $sp, $sp, 4
	jr $ra


###################################################################################################
# integer to ascii
# $t0 -> *p = str
# $t1 -> digit
itoa:
	subiu $sp, $sp, 16
	sw    $ra, ($sp)
	sw    arg0, 4($sp)
	sw    arg1, 8($sp)
	sw    arg2, 12($sp)
	
	move  $t0, arg2			# $t0 -> *p = str
	li    $t1, 0			# $t1 -> digit = 0
itoado:
	rem   $t1, arg0, arg1		# digit = val % base
	div   arg0, arg0, arg1		# val = val / base
	subiu $sp, $sp, 4		# get stack space and store previous arg
	sw    arg0, ($sp)
	move  arg0, $t1
	jal toascii			# get digit ascii value
	sb    retVal, ($t0)		# store value in string
	addiu $t0, $t0, 1		# *p++
	lw    arg0, ($sp)		# return arg0 to previous value before call
	addiu $sp, $sp, 4		# clear stack
	
	bgt arg0, 0, itoado		# while ( val > 0)
	
	li $t2, '\0'			#	
	sb $t2, ($t0)			# *p = '\0'
	subiu $sp, $sp, 4
	sw arg0, ($sp)
	move arg0, arg2
	jal strrev
	lw  arg0, ($sp) 
	addiu $sp, $sp, 4
	
	lw    $ra, ($sp)
	lw    arg0, 4($sp)
	lw    arg1, 8($sp)
	lw    arg2, 12($sp)
	addiu $sp, $sp, 16
	move  retVal, arg2
	jr $ra
	
###################################################################################################
# toascii
toascii:
	addiu arg0, arg0, '0'
	ble arg0, '9', donot
	addiu arg0, arg0, 7

donot:
	move retVal, arg0
	jr $ra
	
###################################################################################################
# string reverse
# $t0 -> *p1
# $t1 -> *p2
strrev:
	subiu $sp, $sp, 8
	sw $ra,  ($sp) 
	sw arg0, 4($sp)

	move $t0, arg0
	move $t1, arg0
	
lenwhile:
	lb $t2, ($t1)
	beq $t2, '\0', endlen
	addiu $t1, $t1, 1
	j lenwhile

endlen:
	subiu $t1, $t1, 1
	
exwhile:
	bge $t0, $t1, endexchange
	subiu $sp, $sp, 8
	sw arg0, ($sp)
	sw arg1, ($sp)
	move arg0, $t0
	move arg1, $t1
	jal exchange
	move $t0, arg0
	move $t1, arg1
	lw arg0, ($sp)
	lw arg1, ($sp)
	addiu $sp, $sp, 8
	addiu $t0, $t0, 1
	subiu $t1, $t1, 1
	j exwhile
	
endexchange:
	move retVal, $t0
	lw $ra,  ($sp) 
	lw arg0, 4($sp)
	addiu $sp, $sp, 8
	jr $ra
	
###################################################################################################
# exchange
exchange:
	lb $t0, (arg0)
	lb $t1, (arg1)
	sb $t0, (arg1)
	sb $t1, (arg0)
	jr $ra