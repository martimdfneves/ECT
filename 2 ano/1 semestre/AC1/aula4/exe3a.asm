 	.data
array:	.word 7692,23,5,234
 	.eqv print_int10,1
 	.eqv SIZE,4
 	.text
 	.globl main
 	
main: 	li $t3,0 # soma = 0;
 	li $t4,SIZE #
 	sub $t4,$t4,1 # $t4 = 3
 	sll $t4,$t4,2 # ou "mul $t4,$t4,4"
 	la $t0,array # p = array;
 	addu $t1,$t0,$t4 # pultimo = array + SIZE - 1;
 	
while: 	# while(p <= pultimo)
 	bgtu $t0,$t1,endw # {
 	lw $t2,0($t0) # $t2 = *p;
 	add $t3,$t3,$t2 # soma = soma + (*p);
 	addiu $t0,$t0,4 # p++;
 	j while # }
 	
endw: 	move $a0,$t3 # print_int10(soma);
 	li $v0,print_int10
	syscall
 	jr $ra # termina o programa 