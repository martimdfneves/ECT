#int main(void)
# {
# int value;
# while(1)
# {
# printStr("\nIntroduza um numero (sinal e módulo): ");
# value = readInt10();
# printStr("\nValor lido, em base 2: ");
# printInt(value, 2);
# printStr("\nValor lido, em base 16: ");
# printInt(value, 16);
# printStr("\nValor lido, em base 10 (unsigned): ");
# printInt(value, 10);
# printStr("\nValor lido, em base 10 (signed): ");
# printInt10(value);
# }
# return 0;
# } 

        .equ readint10, 5
        .equ printstr, 8
        .equ printint, 6
        .equ printint10, 7  
        .data
msg1:   .asciiz "\nIntroduza um numero (sinal e módulo): "
msg2:   .asciiz "\nValor lido, em base 2: "
msg3:   .asciiz "\nValor lido, em base 16: "
msg4:   .asciiz "\nValor lido, em base 10 (unsigned): "
msg5:   .asciiz "\nValor lido, em base 10 (signed): "
        .text
        .globl main

main:   
while:  la $a0, msg1
        li $v0, printstr
        syscall
        li $v0, readint10
        syscall
        move $t1, $v0
        la $a0, msg2
        li $v0, printstr
        syscall
        ori $a0, $t1, 0
        li $a1, 2
        li $v0, printint
        syscall
        la $a0, msg3
        li $v0, printstr
        syscall
        ori $a0, $t1, 0
        li $a1, 16
        li $v0, printint
        syscall
        la $a0, msg4
        li $v0, printstr
        syscall
        ori $a0, $t1, 0
        li $a1, 10
        li $v0, printint
        syscall
        la $a0, msg5
        li $v0, printstr
        syscall
        ori $a0, $t1, 0
        li $v0, printint10
        syscall
        j while
end:    li $v0, 0
        jr $ra