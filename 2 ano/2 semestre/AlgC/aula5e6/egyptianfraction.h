/*******************************************************************************

 Ficheiro de interface do Tipo de Dados Abstracto EGYPTIAN FRACTION.
 A fra��o eg�pcia � composta por uma sequ�ncia de fra��es unit�rias apresentadas
 por ordem decrescente. A sequ�ncia de fra��es pode ser armazenada num array ou
 numa lista ligada.

 Autor : Ant�nio Manuel Adrego da Rocha    Data : Mar�o de 2019

 Interface file of the abstract data type EGYPTIAN FRACTION.
 An egyptian fraction is composed of a sequence of unit fractions presented
 in descending order. The fraction sequence can be stored in an array or
 in a linked list.

*******************************************************************************/

#ifndef _EGYPTIAN_FRACTION
#define _EGYPTIAN_FRACTION

#include "fraction.h"

/* n�mero m�ximo de fra��es unit�rias - maximum number of unit fractions */
#define MAX_SIZE 10  /* para a implementa��o est�tica - for the static implementation */

/************ Defini��o do Tipo Ponteiro para um N�mero Fracion�rio ***********/

typedef struct egyptianfraction *PtEgyptianFraction;

/************************ Defini��o de C�digos de Erro ************************/

#define	OK          0	/* opera��o realizada com sucesso - operation with success */
#define	NO_FRACTION 1	/* a(s) fra��o (fra��es) n�o existe(m) - fraction(s) do not exist */
#define	NO_MEM      2	/* mem�ria esgotada - out of memory */
#define	NOT_PROPER  3	/* fra��o n�o pr�pria - fraction not proper */
#define NULL_DEN    4	/* denominador nulo - null denominator */
#define BAD_INDEX   5   /* �ndice errado - bad index */
#define NULL_PTR    6   /* ponteiro null - null pointer */

/************************* Prot�tipos dos Subprogramas ************************/

void EgyptianFractionClearError (void);
/*******************************************************************************
 Inicializa��o do erro. Error initialization.
*******************************************************************************/

int EgyptianFractionError (void);
/*******************************************************************************
 Devolve o c�digo do �ltimo erro ocorrido. Returns the error code.
*******************************************************************************/

char *EgyptianFractionErrorMessage (void);
/*******************************************************************************
 Devolve uma mensagem esclarecedora da causa do �ltimo erro ocorrido.
 Returns an error message.
*******************************************************************************/

PtEgyptianFraction EgyptianFractionCreate (PtFraction pfraction);
/*******************************************************************************
 Cria uma fra�ao eg�pcia a partir de uma fra�ao, que deve ser uma fra��o pr�pria.
 Devolve a refer�ncia da nova fra��o eg�pcia ou NULL, caso n�o consiga criar a 
 fra��o eg�pcia, por falta de mem�ria, por inexist�ncia da fra��o ou por ela n�o 
 ser uma fra��o pr�pria. Valores de erro: OK, NO_FRACTION, NOT_PROPER ou NO_MEM.

 Creates an egyptian fraction from a fraction, which must be a proper fraction.
 Returns the reference of the new egyptian fraction or NULL for lack of memory, 
 for lack of the fraction or because it is not a proper fraction.
 Error codes: OK, NO_FRACTION, NOT_PROPER or NO_MEM.
*******************************************************************************/

void EgyptianFractionDestroy (PtEgyptianFraction *pegyptian);
/*******************************************************************************
 Destrui��o da fra��o. Valores de erro: OK ou NO_FRACTION.

 Destroys the fraction and releases the memory. Error codes: OK or NO_FRACTION.
*******************************************************************************/

int  EgyptianFractionGetSize (PtEgyptianFraction pegyp);
/*******************************************************************************
 Devolve o n�mero de termos da fra��o. Valores de erro: OK ou NO_FRACTION.

 Returns the number of terms of the fraction. Error codes: OK or NO_FRACTION.
*******************************************************************************/

int  EgyptianFractionIsComplete (PtEgyptianFraction pegyp);
/*******************************************************************************
 Verifica se a fra��o eg�pcia est� completa. Devolve 1 em caso afirmativo e 0 
 em caso contr�rio. Valores de erro: OK ou NO_FRACTION.

 Verifies if the egyptian fraction is complete. Returns 1 in affirmative case 
 and 0 otherwise. Error codes: OK or NO_FRACTION.
*******************************************************************************/

PtEgyptianFraction EgyptianFractionCopy (PtEgyptianFraction pegyp);
/*******************************************************************************
 Copia a fra��o. Devolve a refer�ncia da nova fra��o ou NULL, caso n�o consiga
 fazer a c�pia por falta de mem�ria. Valores de erro: OK, NO_FRACTION ou NO_MEM.

 Copies the fraction. Returns the new fraction or NULL for lack of memory.
 Error codes: OK, NO_FRACTION or NO_MEM.
*******************************************************************************/

PtFraction EgyptianFractionToFraction (PtEgyptianFraction pegyp);
/*******************************************************************************
 Cria uma fra��o a partir da fra��o eg�pcia. Devolve a refer�ncia da nova fra��o 
 ou NULL por falta de mem�ria. Valores de erro: OK, NO_FRACTION ou NO_MEM.

 Creates a fraction from an egyptian fraction. Returns the new fraction or NULL
 for lack of memory. Error codes: OK, NO_FRACTION or NO_MEM.
*******************************************************************************/

int EgyptianFractionEquals (PtEgyptianFraction pegyp1, PtEgyptianFraction pegyp2);
/*******************************************************************************
 Verifica se duas fra��es eg�pcias s�o iguais. Devolve 1 em caso afirmativo e 0
 em caso contr�rio. Valores de erro: OK ou NO_FRACTION.

 Verifies if two egyptian fractions are equal. Returns 1 in affirmative case and 
 0 otherwise. Error codes: OK or NO_FRACTION.
*******************************************************************************/

int EgyptianFractionBelongs (PtEgyptianFraction pegyptian, PtFraction pfraction);
/*******************************************************************************
 Verifica se a fra��o pfraction (que deve ser uma fra��o unit�ria) pertence �
 fra��o eg�pcia pegyptian. Devolve em caso afirmativo e 0 em caso contr�rio.
 Valores de erro: OK, NO_FRACTION ou NOT_PROPER.

 Verifies if fraction pfraction (which should be a unit fraction) belongs to
 the egiptian fraction pegyptian. Returns 1 in affirmative case and 0 otherwise.
 Error codes: OK, NO_FRACTION or NOT_PROPER.
*******************************************************************************/

PtFraction EgyptianFractionGetPos (PtEgyptianFraction pegyp, int pindex);
/*******************************************************************************
 Devolve a fra��o armazenada na posi��o pindex da fra��o eg�pcia. A posi��o �
 positiva e 0<=pindex<size. Valores de erro: OK, NO_FRACTION ou BAD_INDEX.

 Returns the fraction stored in the pindex position of the egyptian fraction. The
 position is positive and 0<=pindex<size. Error codes: OK, NO_FRACTION or BAD_INDEX.
*******************************************************************************/

#endif
