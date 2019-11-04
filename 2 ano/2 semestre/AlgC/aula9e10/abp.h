
/*******************************************************************************

 Ficheiro de interface do Tipo de Dados Abstrato Arvore Binaria de Pesquisa (ABP)
 que armazena frações. A implementação tem capacidade de múltipla instanciação, 
 sendo providenciado um construtor para criar uma arvore vazia. É da responsabilidade
 da aplicação, invocar o destructor, para libertar a memória atribuída ao objecto.
 O módulo providencia um controlo centralizado de erro, disponibilizando uma função
 para obter o último erro ocorrido e uma função para obter uma mensagem de erro
 elucidativa. O tipo de dados providencia operações para armazenar e recuperar uma
 árvore de um ficheiro de texto.

 Autor : António Manuel Adrego da Rocha    Data : Maio de 2018

 Interface file of the abstract data type Binary Search Tree ABP (abp.h). The 
 implementation provides a constructor for creating an empty tree. The application
 has the responsibility of calling the destructor to release the dynamic memory 
 allocated to the tree. The data-type has a central control error mechanism, 
 providing operations for obtaining the last error occurred and for obtaining the 
 correspondent message. The data-type has also operations to store and retrieve
 trees from text files.

*******************************************************************************/

#ifndef _ABP_FRACTION
#define _ABP_FRACTION

#include "fraction.h"
#include "queue.h"
#include "stack.h"

/****************** Definição do Tipo Ponteiro para uma ABP *******************/

typedef struct abp *PtABP;

/************************ Definição de Códigos de Erro ************************/

#define	OK			0	/* operação realizada com sucesso - operation with success */
#define	NO_ABP		1	/* a ABP não existe - tree does not exist */
#define	NO_MEM		2	/* memória esgotada - out of memory */
#define	NULL_PTR	3	/* ponteiro nulo - null pointer */
#define	ABP_EMPTY	4	/* ABP vazia - empty tree */
#define	REP_ELEM	5	/* já existe o elemento - element already exists */
#define	NO_ELEM		6	/* o elemento não existe - element does not exist */
#define	NO_FILE		7	/* o ficheiro não existe - file does not exist */

/*************************** Protótipos das Funções ***************************/

int ABPErrorCode (void);
/*******************************************************************************
 Função que devolve o código do último erro ocorrido.Returns the error code.
*******************************************************************************/

char *ABPErrorMessage (void);
/*******************************************************************************
 Função que devolve uma mensagem esclarecedora da causa do último erro ocorrido.
 Returns an error message.
*******************************************************************************/

PtABP ABPCreate (void);
/*******************************************************************************
 Cria uma arvore vazia. Devolve a referência da nova árvore ou NULL, caso não
 consiga criar a árvore por falta de memória. 

 Creates an empty tree. Returns the reference of the new tree or NULL for lack of memory. 
*******************************************************************************/

void ABPDestroy (PtABP *ptree);
/*******************************************************************************
 Destrói a arvore e coloca a referência a NULL. Valores de erro: OK ou NO_ABP.
 
 Destroys the tree and releases the memory. Error codes: OK or NO_ABP.
*******************************************************************************/

int ABPEmpty (PtABP ptree);
/*******************************************************************************
 Verifica se a arvore esta ou nao vazia. Devolve 1 em caso afirmativo e 0 em caso 
 contrário. Valores de erro: OK ou NO_ABP.

 Verifies if the tree pointed by ptree is empty or not. Returns 1 in affirmative
 case and 0 otherwise. Error code: OK or NO_ABP.
*******************************************************************************/

unsigned int  ABPSize (PtABP ptree);
/*******************************************************************************
 Devolve o número de frações armazenadas na abp. Valores de erro: OK ou NO_ABP.

 Returns the number of fractions stored in the bst. Error codes: OK or NO_ABP.
*******************************************************************************/

void ABPInsert (PtABP ptree, PtFraction pelem);
/*******************************************************************************
 Coloca pelem na arvore. Não insere elementos repetidos nem elementos inválidos.
 Valores de erro: OK, NO_ABP, NULL_PTR, NO_MEM ou REP_ELE.

 Inserts pelem in the tree. Does not insert repeated or invalid elements.
 Error codes: OK, NO_ABP, NULL_PTR, NO_MEM or REP_ELE.
*******************************************************************************/

void ABPInsertRec (PtABP ptree, PtFraction pelem); /* inserção recursiva */
/*******************************************************************************
 Coloca pelem na arvore. Não insere elementos repetidos nem elementos inválidos.
 Valores de erro: OK, NO_ABP, NULL_PTR, NO_MEM ou REP_ELE.

 Inserts pelem in the tree. Does not insert repeated or invalid elements.
 Error codes: OK, NO_ABP, NULL_PTR, NO_MEM or REP_ELE.
*******************************************************************************/

void ABPDelete (PtABP ptree, PtFraction pelem);
/*******************************************************************************
 Retira pelem da arvore. Valores de erro: OK, NO_ABP, ABP_EMPTY, NULL_PTR ou NO_ELEM.

 Deletes pelem from the tree. Error codes: OK, NO_ABP, ABP_EMPTY, NULL_PTR or NO_ELEM.
*******************************************************************************/

int ABPSearch (PtABP ptree, PtFraction pelem);
/*******************************************************************************
 Pesquisa se pelem existe na arvore. Devolve 1 em caso afirmativo e 0 em caso 
 contrário. Valores de erro: OK, NO_ABP, ABP_EMPTY, NULL_PTR ou NO_ELEM.

 Searchs pelem in the tree. Returns 1 if it exists and 0 otherwise.
 Error codes: OK, NO_ABP, ABP_EMPTY, NULL_PTR or NO_ELEM.
*******************************************************************************/

PtFraction ABPMin (PtABP ptree);
/*******************************************************************************
 Devolve o menor elemento da arvore. Valores de erro: OK, NO_ABP ou ABP_EMPTY.

 Returns the minimum element of the tree. Error codes: OK, NO_ABP or ABP_EMPTY.
*******************************************************************************/

PtFraction ABPMax (PtABP ptree);
/*******************************************************************************
 Devolve o maior elemento da arvore. Valores de erro: OK, NO_ABP ou ABP_EMPTY.

 Returns the maximum element of the tree. Error codes: OK, NO_ABP or ABP_EMPTY.
*******************************************************************************/

unsigned int ABPHeight (PtABP ptree);
/*******************************************************************************
 Calcula a altura da arvore. Valores de erro: OK ou NO_ABP.

 Calculates the height of the tree pointed by ptree. Error code: OK or NO_ABP.
*******************************************************************************/

PtABP ABPCreateFile (char *pnomef);
/*******************************************************************************
 Cria uma arvore a partir do ficheiro pnomef. Devolve a referência da arvore criada
 ou NULL, caso não consiga criar a arvore por falta de memória. Valores de erro: 
 OK, NO_FILE ou NO_MEM.

 Creates a tree from a text file. Returns a reference to the new tree or NULL if
 there isn't enough memory. Error codes: OK, NO_FILE or NO_MEM.
*******************************************************************************/

void ABPStoreFile (PtABP ptree, char *nomef);
/*******************************************************************************
 Armazena a arvore, caso ela exista, no ficheiro pnomef. O ficheiro tem na
 primeira linha o número de elementos da arvore, seguido dos elementos, um por
 linha. Valores de erro: OK, NO_ABP, ABP_EMPTY ou NO_FILE.

 Stores the tree pointed by ptree in text file pfname. The first line of the file
 constains the number of elements of the tree, followed by the emenets one per line.
 Error codes: OK, NO_ABP, ABP_EMPTY or NO_FILE.
*******************************************************************************/

PtABP ABPBalance (PtABP ptree);
/*******************************************************************************
 Cria uma uma nova arvore balanceada. Devolve a referência da ABP criada ou NULL
 em caso de inexistência de memória. Valores de erro: OK, NO_ABP, ABP_EMPTY ou NO_MEM.

 Creates a new balanced tree from the tree. Returns a reference to the new tree
 or NULL if there isn't enough memory. Error codes: OK, NO_ABP, ABP_EMPTY or NO_MEM.
*******************************************************************************/

void ABPPrint (PtABP ptree);
/*******************************************************************************
 Imprime no monitor os elementos da arvore por ordem crescente.
 Valores de erro: OK, NO_ABP ou ABP_EMPTY.

 Prints the tree in order. Error codes: OK, NO_ABP or ABP_EMPTY.
*******************************************************************************/

void ABPDisplay (PtABP ptree);
/*******************************************************************************
 Imprime no monitor os elementos da arvore de forma hierárquica.
 Valores de erro: OK ou ABP_EMPTY.

 Display the tree hierarchically. Error codes: OK or ABP_EMPTY.Display the tree hierarchically. Error codes: OK or ABP_EMPTY.
*******************************************************************************/

int ABPEquals (PtABP ptree1, PtABP ptree2);
/*******************************************************************************
 Compara duas arvores. Devolve 1 em caso afirmativo e 0 em caso contrário.
 Valores de erro: OK ou NO_ABP.

 Verifies if two fractions are equal. Returns 1 in affirmative case and 0 otherwise.
 Error codes: OK or NO_ABP.
*******************************************************************************/

PtABP ABPCopy (PtABP ptree);
/*******************************************************************************
 Copia a arvore. Devolve a referência da ABP criada ou NULL em caso de
 inexistência de memória. Valores de erro: OK, NO_ABP ou NO_MEM.

 Creates a copy tree of the tree pointed by proot. Returns a reference to the new
 tree or NULL if there isn't enough memory. Error codes: OK, NO_ABP or NO_MEM.
*******************************************************************************/

void ABPReunion (PtABP ptree1, PtABP ptree2);
/*******************************************************************************
 Reunião de duas árvores. Valores de erro: OK, NO_ABP ou NO_MEM.
 
 Reunion of two tree. Error codes: OK, NO_ABP or NO_MEM.
*******************************************************************************/

void ABPDifference (PtABP ptree1, PtABP ptree2);
/*******************************************************************************
 Diferença de duas árvores. Valores de erro: OK, NO_ABP ou NO_MEM.
 
 Difference of two tree. Error codes: OK, NO_ABP or NO_MEM.
*******************************************************************************/

void ABPIntersection (PtABP ptree1, PtABP ptree2);
/*******************************************************************************
 Intersecção de duas árvores. Valores de erro: OK, NO_ABP ou NO_MEM.

 Intersection of two tree. Error codes: OK, NO_ABP or NO_MEM.
*******************************************************************************/

#endif
