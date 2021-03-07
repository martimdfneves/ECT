#include<detpic32.h>

int main(void){

    LATB=LATB & 0x00FF;
    LATDbits.LATD5=1;
    LATDbits.LATD6=0;
    TRISB=TRISB & 0x00FF;
    TRISD=TRISD & 0xFF9F;
    char c;
    while(1){
        c=getChar();
        switch (c){
            case 'a':
            case 'A':
                LATBbits.LATB8=1;
                break;
            case 'b':
            case 'B':
                LATBbits.LATB9=1;
                break;
            case 'c':
            case 'C':
                LATBbits.LATB10=1;
                break;
            case 'd':
            case 'D':
                LATBbits.LATB11=1;
                break;
            case 'e':
            case 'E':
                LATBbits.LATB12=1;
                break;
            case 'f':
            case 'F':
                LATBbits.LATB13=1;
                break;
            case 'g':
            case 'G':
                LATBbits.LATB14=1;
                break;
        }
    }
    return 0;
}