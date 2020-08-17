	.data
string: .asciiz "I serodatupmoC ed arutetiuqrA"
str0:	.asciiz "String too long: "
str1:	.asciiz "Reversed string: "
str2:	.asciiz "\n"
toCopy:	.space 31
	.eqv STR_MAX_SIZE, 30
	.eqv print_string, 4
	.eqv print_int10, 1
	.eqv func, $v0
	.eqv arg0, $a0
	.eqv arg1, $a1
	.eqv c1, $t0
	.eqv c2, $t2
	.eqv exit_val, $t3
	.eqv str, $s0
	.eqv p1, $s1
	.eqv p2, $s2 
	.eqv char, $s3
	.eqv len, $s4
	.eqv strp, $s5
	.text
	.globl main
main:
	la $t8, ($ra)
	la arg0, string
	jal strlen
	# if
	bgt len, STR_MAX_SIZE, else
	la arg0, toCopy
	la arg1, string
	jal strcopy
	li func, print_string
	la arg0, toCopy
	syscall
	la arg0, str2
	syscall
	la arg0, toCopy
	jal strrev
	move arg0, func
	li func, print_string
	syscall
	li exit_val, 0
	j if

else:
	la arg0, str0
	jal strlen
	move arg0, func
	li func, print_int10
	syscall
	li exit_val, -1
		
if:
	la $ra, ($t8)
	move exit_val, func
	jr $ra
	
	
#####################################
# string copy
strcopy:
	li len, 0
	move p1, arg0
	move p2, arg1
do:	addu $s6, p1, len
	addu $s7, p2, len
	lb char, ($s7)
	sb char, ($s6)
	addi len, len, 1
	bne char, '\0', do
	
	move func, p1
	jr $ra
	
#####################################
# string reverse
strrev:
	subu $sp, $sp, 16
	sw   $ra, ($sp)
	sw   str, 4($sp)
	sw   p1, 8($sp)
	sw   p2, 12($sp)
	move str, arg0
	move p1, arg0
	move p2, arg0
	
while1:	# counts length
	lb char, (p2)
	beq char, '\0', endWhile1
	addi p2, p2, 1
	j while1
	
endWhile1:
	subi p2, p2, 1

while2:	# troca as letras
	bge p1, p2, endWhile2
	move arg0, p1
	move arg1, p2
	jal exchange
	addi p1, p1, 1
	subi p2, p2, 1
	j while2
	
endWhile2:
	move func, str
	lw   $ra, ($sp)
	lw   str, 4($sp)
	lw   p1, 8($sp)
	lw   p2, 12($sp)
	addu $sp, $sp, 16
	jr $ra
	
exchange:
	lb c1,0(arg0)
	lb c2,0(arg1)
	sb c1,0(arg1)
	sb c2,0(arg0)
	jr $ra

########################################
# string length
strlen:
	li len, 0
	move strp, arg0
while:	lb char, (strp)
	addi len, len, 1
	beq char, '\0', endWhile
	addi strp, strp, 1
	j while

endWhile:
	move func, len
	jr $ra