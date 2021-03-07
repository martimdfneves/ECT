#void delay(unsigned int ms)
#{
# for(; ms > 0; ms--)
# {
# resetCoreTimer();
# while(readCoreTimer() < K);
# }
#} 

        .equ readCoreTimer, 11
        .equ resetCoreTimer, 12
        .data
        .text
        .globl main

main:   
for:    ble $a0, 0, end
        li $v0, resetCoreTimer
        syscall 
while:  li $v0, readCoreTimer
        syscall 
        blt $v0, 20000, while
        addi $a0, $a0, -1
        j for 
end:    jr $ra 