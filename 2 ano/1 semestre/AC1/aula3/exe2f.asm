	  .data
str1: 	  .asciiz "Please enter a number: "
str2: 	  .asciiz "The number in binary is: "
	  .eqv print_string, 4
	  .eqv read_int, 5
	  .eqv print_string, 4
	  .eqv print_char, 11
	  .text
	  .globl main

main: 	  li $t0,0 			# int value = 0;
	  li $t1,0			# int bit = 0;
	  li $t2,0			# int i = 0;
	  li $t4,0			# int flag = 0;
	  
	  li $v0,print_string	# print_string("Introduza um numero: "); or using li virtual instruction
	  la  $a0, str1
	  syscall
	  
	  li $v0,read_int		# value = read_int();
	  syscall
	  or  $t0, $0, $v0	
	  
	  li $v0,print_string	# print_string("\nO valor em binário e': ");
	  la  $a0, str2
	  syscall			
	  
do:	  				# do {
	  srl  $t1, $t0, 31		#   bit = value >> 31;
	  				
	  				#   // if flag != 0, must be = 1, since flag can only be 0 or 1, according to the way the program is designed
ifFlag:	  bne $t4, $0, startFlag 	#   if (flag == 1  || bit != 0) { 
	  beq $t1, $0, endIfFlag				
	  
startFlag:li $t4,1		#      flag = 1;
	  rem $t3, $t2, 4		#      if((i % 4) == 0) // resto da divisão inteira
	  bne $t3, $0, endifChar	#        print_char(' ');		
	  li $v0,print_char
	  li $a0,0X20
	  syscall		
	  					
endifChar:li $v0,print_char	#      print_char(0x30 + bit);
	  addi $a0, $t1, 0x30		#      0x30 + bit
	  syscall 		 	#   } 

endIfFlag:sll $t0, $t0, 1		#   value = value << 1; 
	  addi $t2, $t2, 1		#   i++
	  blt $t2, 32, do		# } while (i < 32);    		

endLoop:  jr $ra			# ends program