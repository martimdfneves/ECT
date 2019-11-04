#include <stdio.h>
#include <stdlib.h>

/* alus�o da fun��o que implementa o algoritmo pretendido */
/* allusion to the function that implements the algorithm */
int VerifyDifferences (int [], int);

/* vari�vel global para contar as opera��es aritm�ticas executadas pelo algoritmo */
/* global variable for counting the arithmetic operations executed by the algorithm */
int Count = 0;

int main (void)
{
    /* declara��o dos arrays de teste - usar o pretendido para cada execu��o */
    /* declaration of the test arrays - use each one for each execution */

    /* int Array[] = { 1, 3, 5, 7, 9, 11, 20, 25, 27, 29 }; */
    /* int Array[] = { 1, 3, 6, 9, 11, 13, 20, 25, 27, 29 }; */
    /* int Array[] = { 1, 3, 6, 10, 11, 13, 20, 25, 27, 29 }; */
    /* int Array[] = { 1, 3, 6, 10, 15, 17, 20, 25, 27, 29 }; */
    /* int Array[] = { 1, 3, 6, 10, 15, 21, 22, 25, 27, 29 }; */
    /* int Array[] = { 1, 3, 6, 10, 15, 21, 28, 30, 37, 39 }; */
    /* int Array[] = { 1, 3, 6, 10, 15, 21, 28, 36, 39, 49 }; */
    /* int Array[] = { 1, 3, 6, 10, 15, 21, 28, 36, 45, 49 }; */
     int Array[] = { 1, 3, 6, 10, 15, 21, 28, 36, 45, 55 }; 
  
    int NElem = sizeof (Array) / sizeof (int); int Result;

    /* invoca��o do algoritmo - algorithm invocation */    
	Result = VerifyDifferences (Array, NElem);

	/* apresenta��o do resultado e do n�mero de opera��es executadas pelo algoritmo */
	/* presenting the result and the number of arithmetic executed by the algorithm */
	if (Result) fprintf (stdout, "Checks ");
	else fprintf (stdout, "Does not check ");

	fprintf (stdout, " - Arithmetic operations = %3d \n", Count);

    exit (EXIT_SUCCESS);
}

/* implementa��o do algoritmo com a contagem das opera��es aritm�ticas */
/* implementation of the algorithm with the counting of arithmetic operations */

int VerifyDifferences (int array[], int n)
{
	int dif=array[1]-array[0];
	Count++;
    for (int i=0; i<n-2; i++) {
    	
    	dif++;
    	Count++;
    	if(dif==array[i+2]-array[i+1]) {
    		
    		Count++;
		}
		else {
			
			Count++;
			return 0;
		}
	}
	return 1;
}
