#include<detpic32.h>

void delay(int);

int main(void){

    int x=0;
    LATE=LATE & 0xFFF0;
    TRISE=TRISE & 0xFFF0;
    while(1){
        LATE=(LATE & 0xFFF0)|x;
        delay(250);
        x=(x+1)%16;
    }
    return 0;
}

void delay(int ms) {

    for(; ms > 0; ms--) {
        resetCoreTimer();
        while(readCoreTimer() < 20000);
    }
} 