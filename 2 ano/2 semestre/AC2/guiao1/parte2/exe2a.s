#int main(void)
# {
# char c;
# do
# {
# c = getChar();
# if( c != '\n' )
# putChar( c );
# } while( c != '\n' );
# return 0;
# }

        .equ getchar, 2
        .equ putchar, 3
        .data
        .text
        .globl main

main:   
while:  li $v0, getchar
        syscall
        move $t0, $v0
if:     beq $t0, '\n', end
        move $a0, $t0
        li $v0, putchar
        syscall
        j while
end:    li $v0, 0
        jr $ra