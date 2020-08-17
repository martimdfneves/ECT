	.data
 	.eqv FALSE,0
 	.eqv TRUE,1
 	.eqv size,10
 	.eqv print_string,4
 	.eqv print_int10,1
str:	.asciiz "\nArray:\n" 
lista:	.word 8,-4,3,5,124,-15,87,9,27,15
 	.text
 	.globl main
 	
main: 	la $t6,lista #
 	
do: 	# do {
 	li $t4,FALSE # houve_troca = FALSE;
 	li $t5,0 # i = 0;
 	
while:	bgt $t5,size,endif # while(i < SIZE-1){

if: 	sll $t7,$t5,2 # $t7 = i * 4
 	addu $t7,$t7,$t6 # $t7 = &lista[i]
 	lw $t8,0($t7) # $t8 = lista[i]
 	lw $t9,4($t7) # $t9 = lista[i+1]
	ble $t8,$t9,endif # if(lista[i] > lista[i+1]){
 	sw $t8,4($t7) # lista[i+1] = $t8
 	sw $t9,0($t7) # lista[i] = $t9
 	li $t4,TRUE #
 	addi $t5,$t5,4 # i++;
	j while # }
	
endif: 	bne $t4,TRUE,end # } while(houve_troca == TRUE);
 	la $a0,str
 	li $v0,print_string # codigo de impressao do conteudo do array
 	syscall
 	li $t5,0
 	la $t6,lista
 	bge $t5,size,end
 	move $a0,$t7
 	li $v0,print_int10
 	syscall
 	addiu $t5,$t5,1
 	addiu $t6,$t6,4
 	
end: 	jr $ra # termina o programa