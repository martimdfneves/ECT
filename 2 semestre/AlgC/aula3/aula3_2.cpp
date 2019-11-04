#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

/* alus�o da fun��o que implementa o algoritmo */
/* allusion to the function that implements the algorithm */
void ScrambleArray (int [], int);

/* vari�vel global para contar as invoca��es da fun��o rand executadas pelo algoritmo */
/* global variable for counting the rand operations executed by the algorithm */
int Rand = 0;

/* vari�vel global para contar as trocas executadas pelo algoritmo */
/* global variable for counting the swaps executed by the algorithm */
int Swap = 0;

int main (void)
{
    /* declara��o do array de teste - declaration of the test array */
    int Array[] = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 11, 12, 13, 14, 15, 16, 17, 18, 19 };
    int NElem = sizeof (Array) / sizeof (int), i;

    /* invoca��o do algoritmo - algorithm invocation */
	ScrambleArray (Array, NElem);

	/* apresenta��o do resultado e do n�mero de opera��es executadas pelo algoritmo */
	/* presenting the result and the number of operations executed by the algorithm */
	fprintf (stdout, "Array -> ");
	for (i = 0; i < NElem; i++) fprintf (stdout, "%d ", Array[i]);
	fprintf (stdout, "\t Rands = %2d - Swaps = %2d \n", Rand, Swap);

    exit (EXIT_SUCCESS);
}

/*
   Para gerar uma sequ�ncia pseudo-aleat�ria de n�meros inteiros primeiro � preciso
   invocar a fun��o srand, para criar a semente geradora de n�meros aleat�rios.
   A fun��o deve ser invocada com um argumento sempre diferente de forma a obtermos
   sequ�ncias pseudo-aleat�rias distintas. Tal pode ser feito, por exemplo, usando a
   fun��o getpid(). Depois para gerar um n�mero pseudo-aleat�rio inteiro no intervalo
   [0..MAX] � preciso invocar a fun��o rand na seguinte forma:
                    randint = (int) (rand () / (RAND_MAX+1.0) * (MAX+1));

   To generate a pseudo-random sequence of integers we first need to invoke the srand
   function, to create the random number generator seed. The function must be invoked 
   with an argument always different in order to obtain distinct pseudo-random sequences.
   This can be done, for example, using the getpid () function. Then to generate a
   pseudo-random integer number in the interval [0..MAX] it is necessary to invoke
   the rand function in the following form:
					randint = (int) (rand () / (RAND_MAX+1.0) * (MAX+1));
*/

/* implementa��o do algoritmo com a contagem das opera��es rand e as trocas */
/* implementation of the algorithm with the counting of rand and swap operations */

void ScrambleArray (int array[], int n)
{
	/* criar a semente geradora de n�meros aleat�rios - create the random seed */
	srand (getpid ());
	
	int random=0;
	int aux=0;
	
	for (int i=n-1; i>=0; i--) {
		
		random=(int) (rand () / (RAND_MAX+1.0) * (n));
		n--;
		
		if(n==0) {
			
			break;
		}
		
		Rand++;
		
		if(array[i]!=array[random]) {
			
			aux=array[i];
			array[i]=array[random];
			array[random]=aux;
			Swap++;
		}
	}
}
