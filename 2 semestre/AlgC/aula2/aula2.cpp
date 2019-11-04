#include <stdio.h>
#include <stdlib.h>
#include <math.h>

#include "elapsed_time.h"


/* defini��o do tipo de dados inteiro sem sinal de 64 bits */
/* definition of the 64-bit unsigned integer type */
typedef unsigned long long u64;

/* alus�o das fun��es que implementam os algoritmos pretendidos */
/* allusion to the functions that implement the required algorithms */
u64 fib (int);

/* vari�vel global para contar as opera��es aritm�ticas executadas pelo algoritmo */
/* global variable for counting the arithmetic operations executed by the algorithm */
u64 Count;

int main (void)
{
	u64 Result; int NLines, N, Test;

	/* leitura do valor limite da tabela */
	/* reading the limit value for the table */
	do
	{
		printf ("N de elementos da tabela? "); Test = scanf ("%d", &NLines);
		scanf ("%*[^\n]"); scanf ("%*c");
	} while (Test == 0);

    /* impress�o da tabela de execu��o do algoritmo */
    /* impression of the table values for the algorithm's execution */
	for (N = 1; N <= NLines; N++)
	{
        /* inicializa��o da vari�vel global contadora das opera��es aritm�ticas */
        /* initialization of the global variable for counting the arithmetic operations */
		(void)elapsed_time();

		/* invoca��o do algoritmo pretendido */
		/* invocation of the pretended algorithm */
		Result = fib (N);
		
		double dt;
		dt = elapsed_time();

		/* apresenta��o do resultado e do n�mero de opera��es aritm�ticas executadas pelo algoritmo */
		/* presenting the result and the number of arithmetic operations executed by the algorithm */
		fprintf (stdout, "Fibonacci de %2d = %22llu e demorou %f segundos\n", N, Result, dt);
	}

	exit (EXIT_SUCCESS);
}

/* implementa��o dos algoritmos pretendidos */
/* acrescentar a contagem das opera��es aritm�ticas executadas pelos algoritmos usando a vari�vel global */

/* implementation of the pretended algorithms */
/* do not forget to count the arithmetic operations using the global variable */

u64 fib (int n) {
	
	double phi=(1+sqrt(5))/2;
	double c1=0.44721357549995793928;
	double c2=0.48121182505960344750;
	double one=n*c2;
	double two=exp(one);
	
	return round(c1*two);
	}

