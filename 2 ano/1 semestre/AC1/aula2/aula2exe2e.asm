	.data
	.text
	.globl main
	
main:	li $t0,4
	ori $t1,$t0,0
	srl $t8,$t1,4
	xor $t1,$t8,$t1
	srl $t8,$t1,2
	xor $t1,$t8,$t1
	srl $t8,$t1,1
	xor $t2,$t8,$t1
	jr $ra
