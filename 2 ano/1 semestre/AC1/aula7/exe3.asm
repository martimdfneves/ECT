	.data
str:	.asciiz "Dividendo: "
str2:	.asciiz "Divisor: "
str3:	.asciiz "Quociente: "
str4:	.asciiz "\n"
	.eqv print_int, 1
	.eqv print_string, 4
	.eqv read_int, 5
	.text
	.globl main
main:
	subiu $sp, $sp 12
	sw $ra, ($sp)
	sw $a0, 4($sp)
	sw $a1, 8($sp)
	
	la $a0, str
	li $v0, print_string
	syscall
	li $v0, read_int
	syscall
	move $s0, $v0			# s0 -> dividendo
	
	la $a0, str2
	li $v0, print_string
	syscall
	li $v0, read_int
	syscall
	move $s1, $v0			# s1 -> dividendo
	
	move $a0, $s0
	move $a1, $s1
	jal diviua

	move $s0, $v0
	la   $a0, str3 
	li   $v0, print_string
	syscall
	move $a0, $s0
	li   $v0, print_int
	syscall
	la   $a0, str4
	li   $v0, print_string
	syscall
	
	lw $ra, ($sp)
	lw $a0, 4($sp)
	lw $a1, 8($sp)
	addiu $sp, $sp, 12
	
	jr $ra
	
#######################################################################################3###########
# diviua
# $a0 -> dividendo
# $a1 -> divisor
# $t0 -> i
# $t1 -> bit
# $t2 -> quociente
# $t3 -> resto
diviua:
	sll  $a1, $a1, 16
	andi $a0, $a0, 0xFFFF
	sll  $a0, $a0, 1
	
	li $t0, 0
for:	bge $t0, 16, endFor
	li  $t1, 0
	blt $a0, $a1, notThis
	subu $a0, $a0, $a1
	li  $t1, 1
	
notThis:
	sll $a0, $a0, 1
	or $a0, $a0, $t1
	addi $t0, $t0,1
	j for
endFor:
	andi $t2, $a0, 0xFFFF
	move $v0, $t2
	jr $ra