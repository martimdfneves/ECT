#include <stdio.h>
#include "xil_printf.h"
#include "platform.h"
#include "xparameters.h"
#include "xgpio_l.h"
#include "xtmrctr_l.h"
#include "stdbool.h"

/******************************** Data types *********************************/

// State machine data type
typedef enum {Stopped, Started, SetLSSec, SetMSSec, SetLSMin, SetMSMin} TFSMState;

// Buttons GPIO masks => btnC=10 (0), btnR=08 (1), btnD=04 (2), btnL=02 (3), btnU=01 (4)
#define BUTTON_UP_MASK		0x01
#define BUTTON_DOWN_MASK	0x04
#define BUTTON_RIGHT_MASK	0x08
#define BUTTON_CENTER_MASK	0x10

// Data structure to store buttons status
typedef struct SButtonStatus
{
	bool upPressed;
	bool downPressed;
	bool setPressed;
	bool startPressed;
	bool setPrevious;
	bool startPrevious;
} TButtonStatus;

// Data structure to store countdown timer value
typedef struct STimerValue
{
	unsigned int minMSValue;
	unsigned int minLSValue;
	unsigned int secMSValue;
	unsigned int secLSValue;
} TTimerValue;

/***************************** Helper functions ******************************/

// 7 segment decoder => converts a 4-bit number [0..15] to 7-segments; dp is the decimal point
unsigned char Hex2Seg(unsigned int value, bool dp)
{
	static const char Hex2SegLUT[] = {0x40, 0x79, 0x24, 0x30, 0x19, 0x12, 0x02, 0x78, 0x00, 0x10, 0x08, 0x03, 0x46, 0x21, 0x06, 0x0E};
	return dp ? Hex2SegLUT[value] : (0x80 | Hex2SegLUT[value]);
}

// Rising edge detection and clear
bool DetectAndClearRisingEdge(bool* pOldValue, bool newValue)
{
	bool retValue;
	retValue = (!(*pOldValue)) && newValue;		//&& - AND logico as we work with boolean values
	*pOldValue = newValue;
	return retValue;
}

// Modular increment
bool ModularInc(unsigned int* pValue, unsigned int modulo)
{
	if (*pValue < modulo - 1)
	{
		(*pValue)++;
		return false;
	}
	else
	{
		*pValue = 0;
		return true;
	}
}

// Modular decrement
bool ModularDec(unsigned int* pValue, unsigned int modulo)
{
	if (*pValue > 0)
	{
		(*pValue)--;
		return false;
	}
	else
	{
		*pValue = modulo - 1;
		return true;
	}
}

// Tests if all timer digits are zero
bool IsTimerValueZero(const TTimerValue* pTimerValue)
{
	return ((pTimerValue->secLSValue == 0) && (pTimerValue->secMSValue == 0) && (pTimerValue->minLSValue == 0) && (pTimerValue->minMSValue == 0));
}

// Conversion of the countdown timer values stored in a structure to an array of digits
void TimerValue2DigitValues(const TTimerValue* pTimerValue, unsigned int digitValues[8])
{
	digitValues[0] = 0;		// digito mais a direita
	digitValues[1] = 0;
	digitValues[2] = pTimerValue->secLSValue;
	digitValues[3] = pTimerValue->secMSValue;
	digitValues[4] = pTimerValue->minLSValue;
	digitValues[5] = pTimerValue->minMSValue;
	digitValues[6] = 0;
	digitValues[7] = 0;		// mais a esquerda
}

/******************* Countdown timer operations functions ********************/
//all enables come in positive logic, this function has to be invoked at correct frequency (e.g. 800Hz)
void RefreshDisplays(unsigned char digitEnables, const unsigned int digitValues[8], unsigned char decPtEnables)
{
	static unsigned int digitRefreshIdx = 0; // static variable - is preserved across calls

	///*** STEP 1
	unsigned int an = 0x01;
	an = an << digitRefreshIdx; 	// select the right display to refresh (rotatively)
	an = an & digitEnables;			// check if the selected display is enabled
	bool dp = an & decPtEnables;	// check if the selected dot is enabled

	XGpio_WriteReg(XPAR_AXI_GPIO_DISPLAYS_BASEADDR, XGPIO_DATA_OFFSET, ~an); //an
	XGpio_WriteReg(XPAR_AXI_GPIO_DISPLAYS_BASEADDR, XGPIO_DATA2_OFFSET, Hex2Seg(digitValues[digitRefreshIdx], dp)); //seg

	digitRefreshIdx++;
	digitRefreshIdx &= 0x07; // AND bitwise
}

void ReadButtons(TButtonStatus* pButtonStatus)
{
	unsigned int buttonsPattern;

	///*** STEP 2
	buttonsPattern = XGpio_ReadReg(XPAR_AXI_GPIO_BUTTONS_BASEADDR, XGPIO_DATA_OFFSET);

	pButtonStatus->upPressed    = buttonsPattern & BUTTON_UP_MASK;
	pButtonStatus->downPressed  = buttonsPattern & BUTTON_DOWN_MASK;
	pButtonStatus->setPressed   = buttonsPattern & BUTTON_RIGHT_MASK;
	pButtonStatus->startPressed = buttonsPattern & BUTTON_CENTER_MASK;
}

void UpdateStateMachine(TFSMState* pFSMState, TButtonStatus* pButtonStatus, bool zeroFlag, unsigned char* pSetFlags)
{
	///*** STEP 4
	switch(*pFSMState){
		case Stopped:
			xil_printf("State Stopped\n");
			*pSetFlags = 0x0;
			if(DetectAndClearRisingEdge(&pButtonStatus->startPrevious,pButtonStatus->startPressed) && zeroFlag == 0){
				*pFSMState = Started;
			} else if(DetectAndClearRisingEdge(&pButtonStatus->setPrevious,pButtonStatus->setPressed)){
				*pFSMState = SetLSSec;
			} else {
				*pFSMState = Stopped;
			}
			break;
		case Started:
			xil_printf("State Started\n");
			*pSetFlags = 0x0;
			if(DetectAndClearRisingEdge(&pButtonStatus->startPrevious,pButtonStatus->startPressed) || zeroFlag == 1){
				*pFSMState = Stopped;
			} else if(DetectAndClearRisingEdge(&pButtonStatus->setPrevious,pButtonStatus->setPressed)){
				*pFSMState = SetLSSec;
			} else {
				*pFSMState = Started;
			}
			break;
		case SetLSSec:
			xil_printf("State SetLSSec\n");
			*pSetFlags = 0x1;
			if(DetectAndClearRisingEdge(&pButtonStatus->setPrevious,pButtonStatus->setPressed)){
				*pFSMState = SetMSSec;
			} else {
				*pFSMState = SetLSSec;
			}
			break;
		case SetMSSec:
			xil_printf("State SetMSSec\n");
			*pSetFlags = 0x2;
			if(DetectAndClearRisingEdge(&pButtonStatus->setPrevious,pButtonStatus->setPressed)){
				*pFSMState = SetLSMin;
			} else {
				*pFSMState = SetMSSec;
			}
			break;
		case SetLSMin:
			xil_printf("State SetLSMin\n");
			*pSetFlags = 0x4;
			if(DetectAndClearRisingEdge(&pButtonStatus->setPrevious,pButtonStatus->setPressed)){
				*pFSMState = SetMSMin;
			} else {
				*pFSMState = SetLSMin;
			}
			break;
		case SetMSMin:
			xil_printf("State SetMSMin\n");
			*pSetFlags = 0x8;
			if(DetectAndClearRisingEdge(&pButtonStatus->setPrevious,pButtonStatus->setPressed)){
				*pFSMState = Stopped;
			} else {
				*pFSMState = SetMSMin;
			}
			break;
		default:
			*pSetFlags = 0x0;
			*pFSMState = Stopped;
			break;
	}
}

void SetCountDownTimer(TFSMState fsmState, const TButtonStatus* pButtonStatus, TTimerValue* pTimerValue)
{
	///*** STEP 5
	switch(fsmState) {
		// "segundo" menos significativo
	   case SetLSSec:
		   if (pButtonStatus -> upPressed) {
			   print("upPressed (SetLSSec)\n");
			   ModularInc(&pTimerValue -> secLSValue, 10);
		   }
		   else if (pButtonStatus -> downPressed) {
			   print("downPressed (SetLSSec)\n");
			   ModularDec(&pTimerValue -> secLSValue, 10);
		   }
		   break;
	   // "segundo" mais significativo
	   case SetMSSec:
		   if (pButtonStatus -> upPressed) {
			   xil_printf("upPressed (SetMSSec)\n");
			   ModularInc(&pTimerValue -> secMSValue, 6);
		   }
		   else if (pButtonStatus -> downPressed) {
			   xil_printf("downPressed (SetMSSec)\n");
			   ModularDec(&pTimerValue -> secMSValue, 6);
		   }
		   break;
	   // "minuto" menos significativo
	   case SetLSMin:
		   if (pButtonStatus -> upPressed) {
			   xil_printf("upPressed (SetLSMin)\n");
			   ModularInc(&pTimerValue -> minLSValue, 10);
		   }
		   else if (pButtonStatus -> downPressed) {
			   xil_printf("downPressed (SetLSMin)\n");
			   ModularDec(&pTimerValue -> minLSValue, 10);
		   }
		   break;
	   // "minuto" mais significativo
	   case SetMSMin:
		   if (pButtonStatus -> upPressed) {
			   xil_printf("upPressed (SetMSMin)\n");
			   ModularInc(&pTimerValue -> minMSValue, 6);
		   }
		   else if (pButtonStatus -> downPressed) {
			   xil_printf("downPressed (SetMSMin)\n");
			   ModularDec(&pTimerValue -> minMSValue, 6);
		   }
	   default:
		   break;
	}
}

void DecCountDownTimer(TFSMState fsmState, TTimerValue* pTimerValue)
{
	///*** STEP 3 (apenas enquanto esta no estado 'Started')
	if (fsmState == Started) {
		// "segundo" menos significativo
		bool counting = ModularDec(&pTimerValue -> secLSValue, 10);
		if (counting) {
			// "segundo" mais significativo
			counting = ModularDec(&pTimerValue -> secMSValue,6);
			if (counting) {
				// "minuto" menos significativo
				counting = ModularDec(&pTimerValue -> minLSValue, 10);
				if (counting) {
					// "minuto" mais significativo
					ModularDec(&pTimerValue -> minMSValue, 6);
				}
				else return;
			}
			else return;
		}
		else return;
	}
}

/******************************* Main function *******************************/

int main()
{
	init_platform();
	xil_printf("\n\nCount down timer - polling based version.\nConfiguring..."); //\r is carriage return, and \n is line feed

	//	GPIO tri-state configuration
	//	Inputs
	XGpio_WriteReg(XPAR_AXI_GPIO_SWITCHES_BASEADDR, XGPIO_TRI_OFFSET,  0xFFFFFFFF);
	XGpio_WriteReg(XPAR_AXI_GPIO_BUTTONS_BASEADDR,  XGPIO_TRI_OFFSET,  0xFFFFFFFF);

	//	Outputs
	XGpio_WriteReg(XPAR_AXI_GPIO_LEDS_BASEADDR,     XGPIO_TRI_OFFSET,  0xFFFF0000);
	XGpio_WriteReg(XPAR_AXI_GPIO_DISPLAYS_BASEADDR,  XGPIO_TRI_OFFSET,  0xFFFFFF00);
	XGpio_WriteReg(XPAR_AXI_GPIO_DISPLAYS_BASEADDR,  XGPIO_TRI2_OFFSET, 0xFFFFFF00);

	xil_printf("\nI/Os configured.");

	// Disable hardware timer
	XTmrCtr_SetControlStatusReg(XPAR_AXI_TIMER_0_BASEADDR, 0, 0x00000000);
	// Set hardware timer load value
	XTmrCtr_SetLoadReg(XPAR_AXI_TIMER_0_BASEADDR, 0, 125000); // Counter will wrap around every 1.25 ms
	XTmrCtr_SetControlStatusReg(XPAR_AXI_TIMER_0_BASEADDR, 0, XTC_CSR_LOAD_MASK);
	// Enable hardware timer, down counting with auto reload
	XTmrCtr_SetControlStatusReg(XPAR_AXI_TIMER_0_BASEADDR, 0, XTC_CSR_ENABLE_TMR_MASK  | XTC_CSR_AUTO_RELOAD_MASK | XTC_CSR_DOWN_COUNT_MASK);

	xil_printf("\n\rHardware timer configured.");

	xil_printf("\n\rSystem running.\n\r");

	// Timer event software counter
	unsigned hwTmrEventCount = 0;

	TFSMState     fsmState       = Stopped;
	unsigned char setFlags       = 0x0;
	TButtonStatus buttonStatus   = {false, false, false, false, false, false};
	TTimerValue   timerValue     = {5, 9, 5, 9};
	bool          zeroFlag       = false;

	unsigned char digitEnables   = 0x3C;
	unsigned int  digitValues[8] = {0, 0, 9, 5, 9, 5, 0, 0};
	unsigned char decPtEnables   = 0x00;

	bool          blink1HzStat   = false;
	bool          blink2HzStat   = false;

 	while (1)
 	{
 		unsigned int tmrCtrlStatReg = XTmrCtr_GetControlStatusReg(XPAR_AXI_TIMER_0_BASEADDR, 0);

 		if (tmrCtrlStatReg & XTC_CSR_INT_OCCURED_MASK)
		{
 			// Clear hardware timer event (interrupt request flag)
			XTmrCtr_SetControlStatusReg(XPAR_AXI_TIMER_0_BASEADDR, 0, tmrCtrlStatReg | XTC_CSR_INT_OCCURED_MASK);
			hwTmrEventCount++;

			// Put here operations that must be performed at 800Hz rate
			// Refresh displays
			RefreshDisplays(digitEnables, digitValues, decPtEnables);


			if (hwTmrEventCount % 100 == 0) // 8Hz
			{
				// Put here operations that must be performed at 8Hz rate
				// Read push buttons
				ReadButtons(&buttonStatus);
				// Update state machine
				//fsmState - current FSM state
				//buttonStatus - structure holding status of four buttons
				//zeroFlag - is the current countdown timer value zero?
				//setFlags - what digit is being set?
				UpdateStateMachine(&fsmState, &buttonStatus, zeroFlag, &setFlags);
				if ((setFlags == 0x0) || (blink2HzStat))
				{
					digitEnables = 0x3C; // All digits active
				}
				else
				{
					digitEnables = (~(setFlags << 2)) & 0x3C; // Setting digit inactive
				}


				if (hwTmrEventCount % 200 == 0) // 4Hz
				{
					// Put here operations that must be performed at 4Hz rate
					// Switch digit set 2Hz blink status
					blink2HzStat = !blink2HzStat;


					if (hwTmrEventCount % 400 == 0) // 2Hz
					{
						// Put here operations that must be performed at 2Hz rate
						// Switch point 1Hz blink status
						blink1HzStat = !blink1HzStat;
						if (fsmState == Started)
							decPtEnables = (blink1HzStat ? 0x00 : 0x10);

						// Digit set increment/decrement
						//timerValue - structure holding the current countdown timer value
						SetCountDownTimer(fsmState, &buttonStatus, &timerValue);

						if (hwTmrEventCount == 800) // 1Hz
						{
							xil_printf("\r%d%d:%d%d", timerValue.minMSValue, timerValue.minLSValue, timerValue.secMSValue, timerValue.secLSValue);
							// Put here operations that must be performed at 1Hz rate
							// Count down timer normal operation
							DecCountDownTimer(fsmState, &timerValue);

							// Reset hwTmrEventCount every second
							hwTmrEventCount = 0;
						}
					}
				}
			}
		}

		zeroFlag = IsTimerValueZero(&timerValue);
		//digitValues - array holding display digits
		TimerValue2DigitValues(&timerValue, digitValues);

		XGpio_WriteReg(XPAR_AXI_GPIO_LEDS_BASEADDR, XGPIO_DATA_OFFSET, zeroFlag);


		///*** STEP6 (quando chega a zero, acender o led)
		//if (zeroFlag){
		//	XGpio_WriteReg(XPAR_AXI_GPIO_LEDS_BASEADDR, XGPIO_DATA_OFFSET, 0x0001);
		//}

	}

	cleanup_platform();
	return 0;
}
