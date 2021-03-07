#include<detpic32.h>

void delay(int);
void send2displays(unsigned char);

int main(void){

    TRISBbits.TRISB4 = 1;       // RBx digital output disconnected
    AD1PCFGbits.PCFG4= 0;       // RBx configured as analog input (AN4)
    AD1CON1bits.SSRC = 7;       // Conversion trigger selection bits: in this
                                // mode an internal counter ends sampling and
                                // starts conversion
    AD1CON1bits.CLRASAM = 1;    // Stop conversions when the 1st A/D converter
                                // interrupt is generated. At the same time,
                                // hardware clears the ASAM bit
    AD1CON3bits.SAMC = 16;      // Sample time is 16 TAD (TAD = 100 ns)
    AD1CON2bits.SMPI = 4-1;     // Interrupt is generated after XX samples
                                // (replace XX by the desired number of
                                // consecutive samples)
    AD1CHSbits.CH0SA = 4;       // replace x by the desired input
                                // analog channel (0 to 15)
    AD1CON1bits.ON = 1;         // Enable A/D converter
                                // This must the last command of the A/D
                                // configuration sequence 

    TRISB=(TRISB & 0xFF00)|0x00FF;
    LATB=LATB & 0x80FF;
    LATDbits.LATD5=0;
    LATDbits.LATD6=0;
    TRISDbits.TRISD5=0;
    TRISDbits.TRISD6=0;

    int i;
    int j=0;
    double sum=0;
    double voltage=0;
    int *p;
    while(1){
        if(++j%25==0){
            AD1CON1bits.ASAM = 1;   // Start conversion 
            while( IFS1bits.AD1IF == 0 ); // Wait while conversion not done 
            p = (int *)(&ADC1BUF0);
            for( i = 0; i < 16; i++ ){
                sum+=p[i*4];
            }
            sum/=4;
            voltage=(sum*33)/1023;
            voltage/=10;
            IFS1bits.AD1IF == 0;    //reset AD1IF
        }
        send2displays(voltage);
        delay(10);
    }
    return 0;
}

void delay(int ms) {

    for(; ms > 0; ms--) {
        resetCoreTimer();
        while(readCoreTimer() < 20000);
    }
} 

void send2displays(unsigned char value){

    static const char display7Scodes[]= {0x3F, 0x06, 0x5B, 0x4F, 0x66, 0x6D, 0x7D,
                            0x07, 0x7F, 0x6F, 0x77, 0x7C, 0x39, 0x5E, 0x79, 0x71};
    unsigned char dl=value & 0x0F;
    unsigned char dh=(value & 0xF0)>>4;
    char hexcode;
    static char dispFlag=0;
    if (dispFlag==0){
        LATDbits.LATD5=1;
        LATDbits.LATD6=0;
        hexcode=display7Scodes[dl];
        LATB=(LATB & 0x0000)|((int)hexcode<<8);
    }
    else{
        LATDbits.LATD5=0;
        LATDbits.LATD6=1;
        hexcode=display7Scodes[dh];
        LATB=(LATB & 0x0000)|((int)hexcode<<8);
    }
    dispFlag=!dispFlag;
}