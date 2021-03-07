        .equ printint, 6
        .equ readCoreTimer, 11
        .equ resetCoreTimer, 12
        .data
        .text
        .globl main

main:   
while:  lui $t1,0xBF88"
        lw $t2,0x6050($t1)
        andi $t2, $t2, 0x000F
        move $a0, $t2
        li $a1, 2
        li $v0, printint
        syscall
        li $a0, 500
        jal delay
        j while
end:    jr $ra

#####################################delay########################################
delay:   
for:    ble $a0, 0, end
        li $v0, resetCoreTimer
        syscall 
while:  li $v0, readCoreTimer
        syscall 
        blt $v0, 20000, while
        addi $a0, $a0, -1
        j for 
end:    jr $ra 