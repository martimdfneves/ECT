#include<detpic32.h>

#define DisableUart1RxInterrupt() IEC0bits.U1RXIE = 0
#define EnableUart1RxInterrupt() IEC0bits.U1RXIE = 1
#define DisableUart1TxInterrupt() IEC0bits.U1TXIE = 0
#define EnableUart1TxInterrupt() IEC0bits.U1TXIE = 1
#define BUF_SIZE 8
#define INDEX_MASK (BUF_SIZE – 1) 

typedef struct{
    unsigned char data[BUF_SIZE];
    unsigned int head;
    unsigned int tail;
    unsigned int count;
} circularBuffer; 

volatile circularBuffer txb; // Transmission buffer
volatile circularBuffer rxb; // Reception buffer 

void comDrv_flushRx(void){

    rxb.count=0;
    rxb.head=0;
    rxb.tail=0;
    for(int i=0; i<BUF_SIZE; i++)]{
        rxb.data[i]=0;
    }
}

void comDrv_flushTx(void){

    txb.count=0;
    txb.head=0;
    txb.tail=0;
    for(int i=0; i<BUF_SIZE; i++){
        txb.data[i]=0;
    }
}

void comDrv_putc(char ch){

    while(txb.count == BUF_SIZE){}; // Wait while buffer is full
    txb.data[txb.tail] = ch; // Copy character to the transmission buffer at position "tail"
    txb.tail = (txb.tail + 1) & INDEX_MASK; // Increment "tail" index (mod. BUF_SIZE)
    DisableUart1TxInterrupt(); // Begin of critical section
    txb.count++;
    EnableUart1TxInterrupt(); // End of critical section
} 

void comDrv_puts(char *s){

    while(*s)!='\0'{
        comDrv_putc(*s);
        (str++);
    }
}

void _int_(24) isr_UART1(){

    if (IFC0bits.U1TXIF) {
        if (txb.count>0) {
            U1TXREG=txb.data[txb.head];
            txb.head=(txb.head + 1) & INDEX_MASK;
            txb.count--;
        }
        else if (txb.count==0) {
            DisableUart1TxInterrupt();
        }
        IFC0bits.U1TXIF =0;
    } 
    if (IFC0bits.U1RXIF) {
        rxb.data[rxb.tail] = U1RXREG; 
        rxb.tail = (rxb.tail + 1) & INDEX_MASK;
        if (rxb.count<BUF_SIZE){
            rxb.count++;
        }
        else{
            rxb.head=(rxb.head + 1) & INDEX_MASK;
        }
        IFC0bits.U1RXIF =0;
    } 
}

char comDrv_getc(char *pchar){
    if (rxb.count==0){
        return FALSE;
    }
    DisableUart1RxInterrupt(); // Begin of critical section
    *pchar=rxb.data[rxb.head];
    rxb.count--;
    txb.head=(txb.head + 1) & INDEX_MASK;
    EnableUart1RxInterrupt(); // End of critical section
    return TRUE;
} 