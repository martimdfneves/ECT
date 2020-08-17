	.data
string: .asciiz "ITED - orievA ed edadisrevinU"
str0:	.asciiz "String: "
str1:	.asciiz "Reversed string: "
str2:	.asciiz "\n"
	.eqv print_string, 4
	.text
	.globl main
main:	addiu	$sp,$sp,-4
	sw $ra, 0($sp)
	#
	la $a0, str0
	li $v0, print_string
	syscall
	la $a0, string
	syscall
	la $a0, str2
	syscall
	la $a0, str1
	syscall
	la $a0, string
	
	jal strrev

	move $a0, $v0
	li $v0, print_string
	syscall
	la $a0, str2
	syscall
	
	#
	lw 	$ra, 0($sp)
	addiu	$sp,$sp,4	
	jr 	$ra
# ----------------------------------	
strrev:
	subu $sp, $sp, 16
	sw   $ra, ($sp)
	sw   $s0, 4($sp)
	sw   $s1, 8($sp)
	sw   $s2, 12($sp)
	move $s0, $a0
	move $s1, $a0
	move $s2, $a0
	
while1:	# counts length
	lb $s3, ($s2)
	beq $s3, '\0', endWhile1
	addi $s2, $s2, 1
	j while1
	
endWhile1:
	subi $s2, $s2, 1

while2:	# troca as letras
	bge $s1, $s2, endWhile2
	move $a0, $s1
	move $a1, $s2
	jal exchange
	addi $s1, $s1, 1
	subi $s2, $s2, 1
	j while2
	
endWhile2:
	move $v0, $s0
	lw   $ra, ($sp)
	lw   $s0, 4($sp)
	lw   $s1, 8($sp)
	lw   $s2, 12($sp)
	addu $sp, $sp, 16
	jr $ra
	
exchange:
	lb $t0,0($a0)
	lb $t2,0($a1)
	sb $t0,0($a1)
	sb $t2,0($a0)
	jr $ra
