#include <stdio.h>
#include <stdlib.h>

/* alusão da função que implementa o algoritmo pretendido */
/* allusion to the function that implements the algorithm */
int MaxRepetition (int [], int);

/* variável global para contar as operações de comparação executadas pelo algoritmo */
/* global variable for counting the comparations executed by the algorithm */
int Count = 0;

int main (void)
{
    /* declaração dos arrays de teste - usar o pretendido para cada execução */
    /* declaration of the test arrays - use each one for each execution */

    // int Array[] = { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 }; 
    // int Array[] = { 1, 1, 1, 1, 1, 3, 1, 1, 1, 1 }; 
    // int Array[] = { 1, 1, 1, 4, 1, 1, 4, 1, 1, 1 }; 
    // int Array[] = { 1, 1, 1, 1, 1, 1, 2, 5, 1, 9 }; 
    // int Array[] = { 1, 1, 1, 1, 9, 1, 2, 5, 1, 9 }; 
    // int Array[] = { 1, 1, 1, 9, 1, 2, 8, 3, 7, 1 }; 
    // int Array[] = { 1, 1, 1, 9, 5, 2, 8, 1, 9, 9 }; 
    // int Array[] = { 1, 1, 3, 9, 5, 2, 8, 7, 9, 9 }; 
    // int Array[] = { 1, 1, 6, 0, 5, 2, 8, 7, 9, 9 }; 
	//int Array[] = { 1, 4, 6, 0, 5, 2, 8, 7, 9, 9 }; 
   int Array[] = { 1, 3, 6, 0, 5, 2, 8, 7, 11, 9 }; 
  
    int NElem = sizeof (Array) / sizeof (int); int Result;

    /* invocação do algoritmo - algorithm invocation */    
	Result = MaxRepetition (Array, NElem);

	/* apresentação do resultado e do número de comparações executadas pelo algoritmo */
	/* presenting the result and the number of comparasions executed by the algorithm */
	fprintf (stdout, "Maximum repeatability of elements = %2d - Comparisons = %3d\n", Result, Count);

    exit (EXIT_SUCCESS);
}

/* implementação do algoritmo com a contagem das comparações */
/* implementation of the algorithm with the counting of comparasions */

int MaxRepetition (int array[], int n)
{
	int maxreps=0;
    for(int i=0; i<n; i++){
    	int reps=1;
    	for(int j=i+1; j<n; j++){
    		Count++;
    		if(array[i]==array[j]){
    			reps++;
			}
		}
		Count++;
		if(reps>maxreps){
			maxreps=reps;
		}
		if (n-i<n-maxreps){
			if(reps!=maxreps){	
				break;
			}
		}
	}
	return maxreps;
}
