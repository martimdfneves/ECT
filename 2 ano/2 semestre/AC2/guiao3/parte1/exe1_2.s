        .equ SFR_BASE_HI, 0XBF88  
        .equ TRISE, 0X6100        # TRIS-E address is 0XBF886100 -> '1' is input, '0' is output (E)  
        .equ LATE,  0X6120        # LAT-E address is 0XBF886120  -> value of output port in E
        .equ TRISB, 0x6040        # TRIS-B address is 0XBF886040 -> '1' is input, '0' is output (B)
        .equ PORTB, 0X6050        # PORT-B address is 0XBF886050 -> value of input port in B
        .data
        .text
        .globl main

main:   lui $t1, SFR_BASE_HI    #
        lw $t2, TRISE($t1)      # READ (Mem_addr = 0xBF880000 + 0x6100)
        andi $t2, $t2, 0xFFFE   # MODIFY 
        sw $t2, TRISE($t1)      # WRITE (Write TRISE register)
        lw $t3, TRISB($t1)      # READ (Mem_addr = 0xBF880000 + 0x6100)
        ori $t3, $t3, 0x0001    # MODIFY 
        sw $t3, TRISB($t1)      # WRITE (Write TRISE register)

while:  lw $t3, PORTB($t1)
        andi $t3, $t3, 0x0001
        not $t3, $t3
        lw $t2, LATE($t1)
        andi $t2, $t2, 0xFFFE
        or $t2, $t2, $t3
        sw $t2, LATE($t1)
        j while

end:    jr $ra
