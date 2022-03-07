/*
 * Martim Neves 888904
 * Daniel Vala Correia 90480
 * 
 * FREERTOS demo for ChipKit MAX32 board
 * - Creates two periodic tasks
 * - One toggles Led LD4, other is a long (interfering)task that 
 *      activates LD5 when executing 
 * - When the interfering task has higher priority interference becomes visible
 *      - LD4 does not blink at the right rate
 *
 * Environment:
 * - MPLAB X IDE v5.45
 * - XC32 V2.50
 * - FreeRTOS V202107.00
 *
 *
 */

/* Standard includes. */
#include <stdio.h>
#include <string.h>
#include <xc.h>

/* Kernel includes. */
#include "FreeRTOS.h"
#include "task.h"
#include "queue.h"

/* App includes */
#include "../UART/uart.h"

/* Set the tasks' period (in system ticks) */
#define Task_PERIOD_MS 	( 100 / portTICK_RATE_MS ) // 

/* Priorities of the demo application tasks (high numb. -> high prio.) */
#define TASK_Acq_PRIORITY	( tskIDLE_PRIORITY + 2 )
#define TASK_Proc_PRIORITY	( tskIDLE_PRIORITY + 1 )
#define TASK_Out_PRIORITY	( tskIDLE_PRIORITY + 1 )

// IPC
QueueHandle_t xQueue1;
QueueHandle_t xQueue2;

/*
 * Prototypes and tasks
 */
void pvAcq(void *pvParam){
    TickType_t xLastWakeTime;
    uint8_t mesg[80];
    float task1_out;     
    
    // Initialize the xLastWakeTime variable with the current time.
    xLastWakeTime = xTaskGetTickCount();
    for(;;) {
        // Blink LD4
        PORTAbits.RA3 = !PORTAbits.RA3;
        
        // Get one sample
        IFS1bits.AD1IF = 0; // Reset interrupt flag
        AD1CON1bits.ASAM = 1; // Start conversion
        while (IFS1bits.AD1IF == 0); // Wait fo EOC
        
        // Convert
        task1_out = (ADC1BUF0 * 3.3) / 1023;
        
        // Print value
        sprintf(mesg, "Valor lido: %f\n\r", task1_out);
        PrintStr(mesg);    

        if(xQueueSendToBack(xQueue1, ( void * ) &task1_out, portMAX_DELAY) != pdTRUE){
            printf("Failed1\n\r");
        }
        
        vTaskDelayUntil(&xLastWakeTime, Task_PERIOD_MS);
    }
}

void pvProc(void *pvParam){
    float valores[5] = {0,0,0,0,0};
    float soma = 0;
    float tmp;
    int i = 0;
    int k;
    int j;
    int task2_out;    
    
    for(;;) {
        if(xQueue1 != NULL){
            if(xQueueReceive(xQueue1, &(tmp), portMAX_DELAY)){
                printf("tmp=task1_out\n\r");
            }
        }
        if (i<=3){
            valores[i] = tmp;
            printf("Valores insuficientes para cálculo da média\n\r");
        }
        else{
            valores[4] = tmp;
            for (j = 0; j<5; j++){
                soma = soma + valores[j];
            }
            
            printf("Valor da soma: %f\n\r",soma);

            task2_out = soma/5;
            
            soma = 0;
            
            for(k = 0; k < 4; k++){
                valores[k] = valores[k + 1];
            }
            printf("Array alterado \n\r");

            if(xQueueSendToBack(xQueue2, ( void * ) &task2_out, portMAX_DELAY) != pdTRUE){
                printf("Failed2\n\r");
            }
        }
        
        i = i + 1;
    }
}

void pvOut(void *pvParam){
    
    int tmp;
            
    for(;;) {

        if(xQueue2 != NULL){

            if(xQueueReceive(xQueue2, &(tmp), portMAX_DELAY)){
                printf("tmp=task2_out\n\r");
            }
        }
        
        printf("Média das últimas 5 amostras: %d\n\r",tmp);
    }
}

/*
 * Create the demo tasks then start the scheduler.
 */
int mainA4( void ){
    
    // Set RA3 (LD4) and RC1 (LD5) as outputs
    TRISAbits.TRISA3 = 0;
    TRISCbits.TRISC1 = 0;
    PORTAbits.RA3 = 0;
    PORTCbits.RC1 = 0;

	// Init UART and redirect stdin/stdot/stderr to UART
    if(UartInit(configPERIPHERAL_CLOCK_HZ, 115200) != UART_SUCCESS) {
        PORTAbits.RA3 = 1; // If Led active error initializing UART
        while(1);
    }

     __XC_UART = 1; /* Redirect stdin/stdout/stderr to UART1*/
     
     // Initialize ADC module
    // Polling mode, AN0 as input
    AD1CON1bits.SSRC = 7; // Internal counter ends sampling and starts conversion
    AD1CON1bits.CLRASAM = 1; //Stop conversion when 1st A/D converter interrupt is generated and clears ASAM bit automatically
    AD1CON1bits.FORM = 0; // Integer 16 bit output format
    AD1CON2bits.VCFG = 0; // VR+=AVdd; VR-=AVss
    AD1CON2bits.SMPI = 0; // Number (+1) of consecutive conversions, stored in ADC1BUF0...ADCBUF{SMPI}
    AD1CON3bits.ADRC = 1; // ADC uses internal RC clock
    AD1CON3bits.SAMC = 16; // Sample time is 16TAD ( TAD = 100ns)
    // Set AN0 as input
    AD1CHSbits.CH0SA = 0; // Select AN0 as input for A/D converter
    TRISBbits.TRISB0 = 1; // Set AN0 to input mode
    AD1PCFGbits.PCFG0 = 0; // Set AN0 to analog mode
    // Enable module
    AD1CON1bits.ON = 1; // Enable A/D module
    
    /* Welcome message*/
    printf("\n\n ********************************\n\r");
    printf("Starting SOTR FreeRTOS Demo - A4\n\r");
    printf("*************************************\n\r");
    
    // Initialize Queues
    xQueue1 = xQueueCreate(1, sizeof(float));
    xQueue2 = xQueueCreate(1, sizeof(int));

    if(xQueue1 == NULL){
        printf("Queue 1 was not created and must not be used.\n\r");
    }
    if(xQueue2 == NULL){
        printf("Queue 2 was not created and must not be used.\n\r");
    }
    
    /* Create the tasks defined within this file. */
	xTaskCreate( pvAcq, ( const signed char * const ) "Acq", configMINIMAL_STACK_SIZE, NULL, TASK_Acq_PRIORITY, NULL );
    xTaskCreate( pvProc, ( const signed char * const ) "Proc", configMINIMAL_STACK_SIZE, NULL, TASK_Proc_PRIORITY, NULL );
    xTaskCreate( pvOut, ( const signed char * const ) "Out", configMINIMAL_STACK_SIZE, NULL, TASK_Out_PRIORITY, NULL );
    
    /* Finally start the scheduler. */
	vTaskStartScheduler();

	/* Will only reach here if there is insufficient heap available to start
	the scheduler. */
	return 0;
}
