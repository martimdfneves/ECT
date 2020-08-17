	.data
	.text
	.globl main
	
main:	li $t0,11
	srl $t0,$t0,1
	xori $t1,$t0,11
	jr $ra
