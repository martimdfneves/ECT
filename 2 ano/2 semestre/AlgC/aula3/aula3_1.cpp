#include <stdio.h>
#include <stdlib.h>

/* alus�o da fun��o que implementa o algoritmo */
/* allusion to the function that implements the algorithm */
int CountElements (int [], int);

/* vari�vel global para contar as opera��es aritm�ticas executadas pelo algoritmo */
/* global variable for counting the arithmetic operations executed by the algorithm */
int Count = 0;

int main (void)
{
    /* declara��o dos arrays de teste - usar o pretendido para cada execu��o */
    /* declaration of the test arrays - use each one for each execution */

    /* int Array[] = { 10, 3, 15, 7, 9, 20, 11, 25, 27, 29 }; */
    /* int Array[] = { 10, 3, 15, 7, 35, 33, 20, 55, 27, 29 }; */
    /* int Array[] = { 10, 3, 15, 7, 33, 68, 20, 156, 99, 27 }; */
    /* int Array[] = { 1, 6, 3, 10, 33, 20, 73, 146, 99, 27 }; */
    /* int Array[] = { 1, 6, 3, 10, 33, 20, 73, 146, -96, 196 }; */
    /* int Array[] = { 2, 1, 3, 6, 12, 20, 44, -14, 74, 16 }; */
    /* int Array[] = { 2, 1, 3, 6, 12, 24, 48, -20, -18, 58 }; */
    /* int Array[] = { 2, 1, 3, 6, 12, 24, 48, 96, -98, 94 }; */
    /* int Array[] = { 2, 2, 4, 8, 16, 31, 63, 126, 252, 504 }; */
    int Array[] = { 2, 2, 4, 8, 16, 32, 64, 128, 256, 512 }; 
 
    int NElem = sizeof (Array) / sizeof (int); int Result;

    /* invoca��o do algoritmo - algorithm invocation */
	Result = CountElements (Array, NElem);

	/* apresenta��o do resultado e do n�mero de opera��es executadas pelo algoritmo */
	/* presenting the result and the number of operations executed by the algorithm */
	fprintf (stdout, "Elements = %2d - Operations = %2d\n", Result, Count);

    exit (EXIT_SUCCESS);
}

/* implementa��o do algoritmo com a contagem das opera��es aritm�ticas */
/* implementation of the algorithm with the counting of arithmetic operations */

int CountElements (int array[], int n) {
	
	int sum=0;
	int x=0;
	
    for (int i=1; i<n; i++) {
	
		sum+=array[i-1];
		Count++;
		
		if(array[i]==sum) {
			
			x++;
		}
	}
	
	return x;
}
