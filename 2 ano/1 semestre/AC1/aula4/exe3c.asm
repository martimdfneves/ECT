 	.data
array:	.word 7692,23,5,234
 	.eqv print_int10,1
 	.eqv SIZE,4
 	.text
 	.globl main
 	
 main:	li $t0,0  #soma=0
 	li $t1,0  #i=0
 	la $t2,array  #$t2=array[0]
 	li $t5,SIZE  #terminador

while:	bgt $t1,$t5,endwhile  #if $t1<=$t5
	
	sll $t3,$t1,2
	addu $t3,$t2,$t3
	lw $t4,0($t3)  #$t4=array[i]
	
	add $t0,$t0,$t4	#soma+=array[i]
	addi $t1,$t1,1   #i+=4
	j while
	
endwhile:move $a0,$t0 # print_int10(soma);
 	li $v0,print_int10
	syscall
 	jr $ra # termina o programa 
