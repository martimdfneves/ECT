#include<detpic32.h>

void putc(char byte2send);
void puts(char *str);

void main(void){

    U1BRG = ((20000000 + 8 * 115200) / (16 * 115200)) – 1;
    U1MODEbits.BRGH=0;
    U1MODEbits.PDSEL=0;
    U1MODEbits.STSEL = 0;
    U1STAbits.UTXEN=1;
    U1STAbits.URXEN=1;
    U1MODEbits.ON=1;
    while(1){
        puts("String de teste\n");
    }
}

void putc(char byte2send){

    while(U1STAbits.UTXBF==1);
    U1TXREG=byte2send;
}

void puts(char *str){

    int i=0;
    while(str[i]!='\0'){
        putc(str[i]);
    }
}