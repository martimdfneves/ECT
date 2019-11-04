/*******************************************************************************

 Ficheiro de interface do Tipo de Dados Abstracto EGYPTIAN FRACTION.
 A fração egípcia é composta por uma sequência de frações unitárias apresentadas
 por ordem decrescente. A sequência de frações pode ser armazenada num array ou
 numa lista ligada.

 Autor : António Manuel Adrego da Rocha    Data : Março de 2019

 Interface file of the abstract data type EGYPTIAN FRACTION.
 An egyptian fraction is composed of a sequence of unit fractions presented
 in descending order. The fraction sequence can be stored in an array or
 in a linked list.

*******************************************************************************/

#ifndef _EGYPTIAN_FRACTION
#define _EGYPTIAN_FRACTION

#include "fraction.h"

/* número máximo de frações unitárias - maximum number of unit fractions */
#define MAX_SIZE 10  /* para a implementação estática - for the static implementation */

/************ Definição do Tipo Ponteiro para um Número Fracionário ***********/

typedef struct egyptianfraction *PtEgyptianFraction;

/************************ Definição de Códigos de Erro ************************/

#define	OK          0	/* operação realizada com sucesso - operation with success */
#define	NO_FRACTION 1	/* a(s) fração (frações) não existe(m) - fraction(s) do not exist */
#define	NO_MEM      2	/* memória esgotada - out of memory */
#define	NOT_PROPER  3	/* fração não própria - fraction not proper */
#define NULL_DEN    4	/* denominador nulo - null denominator */
#define BAD_INDEX   5   /* índice errado - bad index */
#define NULL_PTR    6   /* ponteiro null - null pointer */

/************************* Protótipos dos Subprogramas ************************/

void EgyptianFractionClearError (void);
/*******************************************************************************
 Inicialização do erro. Error initialization.
*******************************************************************************/

int EgyptianFractionError (void);
/*******************************************************************************
 Devolve o código do último erro ocorrido. Returns the error code.
*******************************************************************************/

char *EgyptianFractionErrorMessage (void);
/*******************************************************************************
 Devolve uma mensagem esclarecedora da causa do último erro ocorrido.
 Returns an error message.
*******************************************************************************/

PtEgyptianFraction EgyptianFractionCreate (PtFraction pfraction);
/*******************************************************************************
 Cria uma fraçao egípcia a partir de uma fraçao, que deve ser uma fração própria.
 Devolve a referência da nova fração egípcia ou NULL, caso não consiga criar a 
 fração egípcia, por falta de memória, por inexistência da fração ou por ela não 
 ser uma fração própria. Valores de erro: OK, NO_FRACTION, NOT_PROPER ou NO_MEM.

 Creates an egyptian fraction from a fraction, which must be a proper fraction.
 Returns the reference of the new egyptian fraction or NULL for lack of memory, 
 for lack of the fraction or because it is not a proper fraction.
 Error codes: OK, NO_FRACTION, NOT_PROPER or NO_MEM.
*******************************************************************************/

void EgyptianFractionDestroy (PtEgyptianFraction *pegyptian);
/*******************************************************************************
 Destruição da fração. Valores de erro: OK ou NO_FRACTION.

 Destroys the fraction and releases the memory. Error codes: OK or NO_FRACTION.
*******************************************************************************/

int  EgyptianFractionGetSize (PtEgyptianFraction pegyp);
/*******************************************************************************
 Devolve o número de termos da fração. Valores de erro: OK ou NO_FRACTION.

 Returns the number of terms of the fraction. Error codes: OK or NO_FRACTION.
*******************************************************************************/

int  EgyptianFractionIsComplete (PtEgyptianFraction pegyp);
/*******************************************************************************
 Verifica se a fração egípcia está completa. Devolve 1 em caso afirmativo e 0 
 em caso contrário. Valores de erro: OK ou NO_FRACTION.

 Verifies if the egyptian fraction is complete. Returns 1 in affirmative case 
 and 0 otherwise. Error codes: OK or NO_FRACTION.
*******************************************************************************/

PtEgyptianFraction EgyptianFractionCopy (PtEgyptianFraction pegyp);
/*******************************************************************************
 Copia a fração. Devolve a referência da nova fração ou NULL, caso não consiga
 fazer a cópia por falta de memória. Valores de erro: OK, NO_FRACTION ou NO_MEM.

 Copies the fraction. Returns the new fraction or NULL for lack of memory.
 Error codes: OK, NO_FRACTION or NO_MEM.
*******************************************************************************/

PtFraction EgyptianFractionToFraction (PtEgyptianFraction pegyp);
/*******************************************************************************
 Cria uma fração a partir da fração egípcia. Devolve a referência da nova fração 
 ou NULL por falta de memória. Valores de erro: OK, NO_FRACTION ou NO_MEM.

 Creates a fraction from an egyptian fraction. Returns the new fraction or NULL
 for lack of memory. Error codes: OK, NO_FRACTION or NO_MEM.
*******************************************************************************/

int EgyptianFractionEquals (PtEgyptianFraction pegyp1, PtEgyptianFraction pegyp2);
/*******************************************************************************
 Verifica se duas frações egípcias são iguais. Devolve 1 em caso afirmativo e 0
 em caso contrário. Valores de erro: OK ou NO_FRACTION.

 Verifies if two egyptian fractions are equal. Returns 1 in affirmative case and 
 0 otherwise. Error codes: OK or NO_FRACTION.
*******************************************************************************/

int EgyptianFractionBelongs (PtEgyptianFraction pegyptian, PtFraction pfraction);
/*******************************************************************************
 Verifica se a fração pfraction (que deve ser uma fração unitária) pertence à
 fração egípcia pegyptian. Devolve em caso afirmativo e 0 em caso contrário.
 Valores de erro: OK, NO_FRACTION ou NOT_PROPER.

 Verifies if fraction pfraction (which should be a unit fraction) belongs to
 the egiptian fraction pegyptian. Returns 1 in affirmative case and 0 otherwise.
 Error codes: OK, NO_FRACTION or NOT_PROPER.
*******************************************************************************/

PtFraction EgyptianFractionGetPos (PtEgyptianFraction pegyp, int pindex);
/*******************************************************************************
 Devolve a fração armazenada na posição pindex da fração egípcia. A posição é
 positiva e 0<=pindex<size. Valores de erro: OK, NO_FRACTION ou BAD_INDEX.

 Returns the fraction stored in the pindex position of the egyptian fraction. The
 position is positive and 0<=pindex<size. Error codes: OK, NO_FRACTION or BAD_INDEX.
*******************************************************************************/

#endif
