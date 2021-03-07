#include<detpic32.h>

void send2displays(unsigned char);

int main(void){

    PORTB=PORTB & 0xFFF0;
    TRISB=(TRISB & 0xFFF0)|0x000F;
    LATB=LATB & 0x80FF;
    TRISB=TRISB & 0x80FF;
    LATDbits.LATD5=1;
    LATDbits.LATD6=0;
    TRISDbits.TRISD5=0;
    TRISDbits.TRISD6=0;
    while(1){
        send2displays(PORTB & 0x00FF);
    }
    return 0;
}

void send2displays(unsigned char value){

    static const char display7Scodes[]= {0x3F, 0x06, 0x5B, 0x4F, 0x66, 0x6D, 0x7D,
                            0x07, 0x7F, 0x6F, 0x77, 0x7C, 0x39, 0x5E, 0x79, 0x71};
    unsigned char dl=value & 0x0F;
    unsigned char dh=(value & 0xF0)>>4;
    char hexcode;
    LATDbits.LATD5=1;
    LATDbits.LATD6=0;
    hexcode=display7Scodes[dl];
    LATB=(LATB & 0x0000)|((int)hexcode<<8);
    LATDbits.LATD5=0;
    LATDbits.LATD6=1;
    hexcode=display7Scodes[dh];
    LATB=(LATB & 0x0000)|((int)hexcode<<8);
}