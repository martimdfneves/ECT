#include<detpic32.h>

void main(void){

    T1CONbits.TCKPS=3;
    PR1=39062;
    TMR1=0;
    T1CONbits.ON=1;
    IPC1bits.T1IP = 2; // Interrupt priority (must be in range [1..6])
    IEC0bits.T1IE = 1; // Enable timer T2 interrupts
    IFS0bits.T1IF = 0; // Reset timer T2 interrupt flag 

    T3CONbits.TCKPS=5;
    PR3=62499;
    TMR3=0;
    T3CONbits.ON=1;
    IPC3bits.T3IP = 2; // Interrupt priority (must be in range [1..6])
    IEC0bits.T3IE = 1; // Enable timer T2 interrupts
    IFS0bits.T3IF = 0; // Reset timer T2 interrupt flag 
    EnableInterrupts();
    while(1){};
}

void _int_(4) isr_T1(void){
    printStr("1");
    IFS0bits.T1IF=0;
} 

void _int_(12) isr_T3(void){
    printStr("3");
    IFS0bits.T3IF=0;
} 