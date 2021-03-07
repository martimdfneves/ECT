        .equ SFR_BASE_HI, 0xBF88 # 16 MSbits of SFR area
        .equ TRISE, 0x6100 # TRISE address is 0xBF886100
        .equ PORTE, 0x6110 # PORTE address is 0xBF886110
        .equ LATE, 0x6120 # LATE address is 0xBF886120
        .equ readCoreTimer, 11
        .equ resetCoreTimer, 12
        .data
        .text
        .globl main

main:   li $t0, 0
        lui $t1, SFR_BASE_HI    #
        lw $t2, TRISE($t1)      # READ (Mem_addr = 0xBF880000 + 0x6100)
        andi $t2, $t2, 0xFFFE   # MODIFY 
        sw $t2, TRISE($t1)      # WRITE (Write TRISE register)

while:  lw $t2, LATE($t1)
        andi $t2, $t2, 0xFFFE
        or $t2, $t2, $t0
        li $a0, 500
        jal delay
        xori $t0, $t0, 1
        j while

end:    jr $ra

##################################delay####################################
delay:   
for:    ble $a0, 0, end
        li $v0, resetCoreTimer
        syscall 
whiled:  li $v0, readCoreTimer
        syscall 
        blt $v0, 20000, whiled
        addi $a0, $a0, -1
        j for 
end:    jr $ra 