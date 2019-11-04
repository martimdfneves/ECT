#include <stdio.h>
#include <stdlib.h>

/* alusão das funções que implementam os algoritmos pretendidos */
/* allusion to the functions that implement the required algorithms */
int f3 (int);

/* variável global para contar as operações aritméticas executadas pelo algoritmo */
/* global variable for counting the arithmetic operations executed by the algorithm */
int Count;

int main (void)
{
	int Result, Line, NLines, Test;

	/* leitura do valor limite da tabela */
	/* reading the limit value for the table */
	do
	{
		printf ("N de elementos da tabela? "); Test = scanf ("%d", &NLines);
		scanf ("%*[^\n]"); scanf ("%*c");
	} while (Test == 0);

    /* impressão da tabela de execução do algoritmo */
    /* impression of the table values for the algorithm's execution */
	for (Line = 1; Line <= NLines; Line++)
	{
        /* inicialização da variável global contadora das operações aritméticas */
        /* initialization of the global variable for counting the arithmetic operations */
		Count = 0;

		/* invocação do algoritmo pretendido */
		/* invocation of the pretended algorithm */
		Result = f3 (Line);

		/* apresentação do resultado e do número de operações aritméticas executadas pelo algoritmo */
		/* presenting the result and the number of arithmetic operations executed by the algorithm */
		fprintf (stdout, "Fi(%2d) = %10d e executou %10d somas\n", Line, Result, Count);
	}

	exit (EXIT_SUCCESS);
}

/* implementação dos algoritmos pretendidos */
/* acrescentar a contagem das operações aritméticas executadas pelos algoritmos usando a variável global */

/* implementation of the pretended algorithms */
/* do not forget to count the arithmetic operations using the global variable */

int f1 (int n) {
	
	int i, j, r = 0;
	for (i = 1; i <= n; i++){
		for (j = 1; j <= n; j++){
			r += 1;
			Count++;
		}
	}
	return r;
}

int f2 (int n) {
	
	int i, j, r = 0;
	for (i = 1; i <= n; i++){
		for (j = 1; j <= i; j++){
			r += 1;
			Count++;
		}
	}
	return r;
}

int f3 (int n) {
	
	int i, j, r = 0;
	for (i = 1; i <= n; i++){
		for (j = i; j <= n; j++){
			r += j;
			Count++;
		}
	}
	return r;
}
