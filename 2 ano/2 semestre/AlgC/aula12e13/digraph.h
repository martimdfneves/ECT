/*******************************************************************************

 Ficheiro de interface do Tipo de Dados Abstrato Dígrafo/Grafo (digraph.h). A 
 implementação tem capacidade de múltipla instanciação, sendo providenciado um 
 construtor para criar um grafo (0) /dígrafo (1) vazio. É da responsabilidade da
 aplicação, invocar o destructor, para libertar a memória atribuída ao objecto. 
 O módulo providencia um controlo de erro no retorno das operações. 

 Autor : António Manuel Adrego da Rocha    Data : Maio de 2015

 Interface file of the abstract data type Digraph/Graph (digraph.h). The 
 implementation provides a constructor for creating an empty graph (0) /digraph (1).
 The application has the responsibility of calling the destructor to release the
 dynamic memory allocated to the digraph. The data-type has a control error 
 mechanism, basead on the return value of the functions. 

*******************************************************************************/
#include "queue.h"  /* interface da fila */

#ifndef _DIGRAPH_DYNAMIC
#define _DIGRAPH_DYNAMIC

/*********** Definição do Tipo Ponteiro para um Dígrafo/Grafo **********/

typedef struct digraph *PtDigraph;

/********************* Definição de Códigos de Erro ********************/

#define	OK				0	/* operação realizada com sucesso - operation with success */
#define	NO_DIGRAPH		1	/* dígrafo/grafo inexistente - digraph/graph does not exist*/
#define	NO_MEM			2	/* memória esgotada - out of memory */
#define	NULL_PTR		3	/* ponteiro nulo - null pointer */
#define	DIGRAPH_EMPTY	4	/* dígrafo/grafo vazio - empty digraph/graph */
#define	NO_VERTEX		5	/* vértice inexistente - vertex does not exist */
#define	REP_VERTEX		6	/* vértice repetido - vertex already exists */
#define	NO_EDGE			7	/* aresta inexistente - edge does not exist */
#define	REP_EDGE		8	/* aresta repetida - edge already exists */
#define	NO_FILE			9	/* ficheiro inexistente - file does not exist */
#define	NO_DAG			10	/* dígrafo cíclico - acyclic digraph */
#define	NEG_CYCLE		11	/* dígrafo/grafo com ciclo negativo - digraph/graph with negative loop */
#define	NO_CONNECTED	12	/* grafo não conexo - not connected graph */
#define	NO_PATH			13	/* não existe caminho/circuito - path/cycle does not exist */
#define	SINK			14	/* vértice sumidouro - sink vertex */
#define	SOURCE			15	/* vértice fonte - source vertex */
#define	DISC			16	/* vértice desconexo - disconnected vertex */

/********************* Protótipos dos Subprogramas *********************/

PtDigraph Create (unsigned int ptype);
/*******************************************************************************
 Cria um dígrafo ou um grafo, caso ptype seja igual a, respetivamente, 1 ou 0.
 Devolve a referência do dígrafo/grafo criado ou NULL, no caso de inexistência de
 memória.

 Creates the empty digraph/graph, case ptype is respectively 1 or 0. Returns the 
 reference to the new digraph/graph or NULL if there isn't enough memory.
*******************************************************************************/

int Destroy (PtDigraph *pdig);
/*******************************************************************************
 Destrói o dígrafo/grafo pdig e coloca a referência a NULL. Valores de retorno:
 OK ou NO_DIGRAPH.

 Destroys the digraph/graph pdig and releases the memory. Returning error codes:
 OK or NO_DIGRAPH.
*******************************************************************************/

PtDigraph Copy (PtDigraph pdig);
/*******************************************************************************
 Copia o dígrafo/grafo pdig. Devolve a referência da cópia ou NULL, caso não 
 consiga fazer a cópia por inexistência de memória ou do dígrafo/grafo pdig.

 Copies the digraph/graph pdig. Returns the reference to the new digraph/graph or
 NULL if there isn't enough memory or if pdig does not exist.
*******************************************************************************/

int InVertex (PtDigraph pdig, unsigned int pv);
/*******************************************************************************
 Insere o vértice pv, no dígrafo/grafo pdig. Valores de retorno: OK, NO_DIGRAPH
 ou REP_VERTEX.
 
 Inserts vertex pv in digraph/graph pdig. Returning error codes: OK, NO_DIGRAPH
 or REP_VERTEX.
*******************************************************************************/

int OutVertex (PtDigraph pdig, unsigned int pv);
/*******************************************************************************
 Retira o vértice pv, no dígrafo/grafo pdig. Retira também todas as suas arestas
 incidentes e emergentes. Valores de retorno: OK, NO_DIGRAPH, DIGRAPH_EMPTY ou 
 NO_VERTEX.
 
 Deletes vertex pv from digraph/graph pdig and all its in and out edges.
 Returning error codes: OK, NO_DIGRAPH, DIGRAPH_EMPTY or NO_VERTEX.
*******************************************************************************/

int InEdge (PtDigraph pdig, unsigned int pv1, unsigned int pv2, int pcost);
/*******************************************************************************
 Insere a aresta pv1-pv2, com custo pcost, no dígrafo/grafo pdig. Valores de 
 retorno: OK, NO_DIGRAPH, NO_VERTEX ou REP_EDGE.
 
 Inserts edge pv1-pv2 with cost pcost in digraph/graph pdig. Returning error codes:
 OK, NO_DIGRAPH, NO_VERTEX or REP_EDGE.
*******************************************************************************/

int OutEdge (PtDigraph pdig, unsigned int pv1, unsigned int pv2);
/*******************************************************************************
 Retira a aresta pv1-pv2 do dígrafo/grafo pdig. Valores de retorno: OK, NO_DIGRAPH,
 NO_VERTEX ou NO_EDGE.
 
 Deletes edge pv1-pv2 from digraph/graph pdig. Returning error codes: OK, NO_DIGRAPH,
 NO_VERTEX or NO_EDGE.
*******************************************************************************/

int Type (PtDigraph pdig, unsigned int *pty);
/*******************************************************************************
 Determina e coloca em pty o tipo do dígrafo/grafo pdig (dígrafo = 1/ grafo = 0).
 Valores de retorno: OK, NO_DIGRAPH ou NULL_PTR.
 
 Decide and stores in pty the type of the digraph/graph pdig (dídigraph = 1/ 
 graph = 0). Returning error codes: OK, NO_DIGRAPH or NULL_PTR. 
*******************************************************************************/

int VertexNumber (PtDigraph pdig, unsigned int *pnv);
/*******************************************************************************
 Determina e coloca em pnv o número de vértices do dígrafo/grafo pdig. Valores 
 de retorno: OK, NO_DIGRAPH ou NULL_PTR.
 
 Decide and stores in pnv the number of vertexes of digraph/graph pdig.
 Returning error codes: OK, NO_DIGRAPH or NULL_PTR.
*******************************************************************************/

int EdgeNumber (PtDigraph pdig, unsigned int *pne);
/*******************************************************************************
 Determina e coloca em pne o número de arestas do dígrafo/grafo pdig. Valores de
 retorno: OK, NO_DIGRAPH ou NULL_PTR.
 
 Decide and stores in pne the number of edges of digraph/graph pdig. Returning 
 error codes: OK, NO_DIGRAPH or NULL_PTR.
*******************************************************************************/

int GetVertexList (PtDigraph pdig, unsigned int ppos, char *pvlist);
/*******************************************************************************
 Cria uma sequência de carateres com a informação do ppos vértice (1 <= ppos <= V),
 incluindo a sua lista de adjacências, do dígrafo/grafo pdig. Esta operação é 
 necessária para que uma aplicação gráfica possa fazer a escrita do dígrafo/grafo
 no monitor de forma controlada. Valores de retorno: OK, NO_DIGRAPH ou NULL_PTR.
 
 Creates a string with the information of vertex ppos (1 <= ppos <= V),
 including its edges list, of digraph/graph pdig. This operation is necessary 
 in order that a graphical application pcan print the digraph/graph on the screen
 in a controlled way. Returning error codes: OK, NO_DIGRAPH or NULL_PTR.
*******************************************************************************/

PtDigraph CreateFile (char *pfilename);
/*******************************************************************************
 Cria um dígrafo/grafo com o conteudo do ficheiro pfilename. Devolve a referência
 do dígrafo/grafo criado ou NULL, no caso de inexistência de memória ou do ficheiro.

 Creates the digraph/graph from the file pfilename. Returns the reference to the 
 new digraph/graph or NULL if there isn't enough memory or if the file does not exist.
*******************************************************************************/

int StoreFile (PtDigraph pdig, char *pfilename);
/*******************************************************************************
 Armazena o dígrafo/grafo pdig no ficheiro pfilename. Valores de retorno: OK, 
 NO_DIGRAPH, DIGRAPH_EMPTY ou NO_FILE.
 
 Stores the digraph/graph pdig in file pfilename. Returning error codes: OK, 
 NO_DIGRAPH, DIGRAPH_EMPTY or NO_FILE.
*******************************************************************************/

int VertexType (PtDigraph pdig, unsigned int pv);
/*******************************************************************************
 Determina de que tipo é o vertice pv. Valores de retorno: OK (vertice normal), 
 NO_DIGRAPH, DIGRAPH_EMPTY, NO_VERTEX, SINK (vertice sumidouro), SOURCE (vertice 
 fonte) ou DISC (vertice desconexo).

 Decide the type of vertex pv of digraph/graph pdig. Returning error codes:
 OK (normal vertex, NO_DIGRAPH, DIGRAPH_EMPTY, NO_VERTEX, SINK (sink vertex), SOURCE
 (source vertex) ou DISC (disconnected vertex).
*******************************************************************************/

int VertexClassification (PtDigraph pdig, unsigned int pv, double *pclass);
/*******************************************************************************
 Determina a classificação (entre 0.0 e 1.0) de um dado vértice de acordo com o seu
 grau (soma do número de antecessores e de sucessores diretos).
 Valores de retorno: OK, NO_DIGRAPH, DIGRAPH_EMPTY, NULL_PTR ou NO_VERTEX.

 Determines the rank (between 0.0 and 1.0) of a given vertex according to its
 degree (sum of the number of predecessors and direct successors).
 Returning error codes: OK, NO_DIGRAPH, DIGRAPH_EMPTY, NULL_PTR or NO_VERTEX.
*******************************************************************************/

PtDigraph DigraphComplement (PtDigraph pdig);
/*******************************************************************************
 Cria o dígrafo complementar do dígrafo pdig. Devolve a referência do dígrafo 
 criado ou NULL, no caso de inexistência de memória ou do dígrafo pdig.
 Considere as arestas com custo unitário.

 Creates the complement of digraph pdig. Returns the reference to the new
 digraph or NULL if there isn't enough memory or if the pdig does not exist.
 Considere the unity cost for the edges.
*******************************************************************************/

PtDigraph DigraphTranspose (PtDigraph pdig);
/*******************************************************************************
 Cria o dígrafo transposto do dígrafo pdig. Devolve a referência do dígrafo 
 criado ou NULL, no caso de inexistência de memória ou do dígrafo pdig.

 Creates the transpose of digraph pdig. Returns the reference to the new
 digraph or NULL if there isn't enough memory or if the pdig does not exist.
*******************************************************************************/

int DigraphEdgeSplit (PtDigraph pdig, unsigned int pve, unsigned int pvi);
/*******************************************************************************
 Subdivide uma dada aresta substituindo-a por um vértice e duas arestas adicionais.
 O identificador do vértice será o próximo índice que se possa utilizar. O custo
 de cada aresta será metade do custo da aresta original. A primeira aresta deve ter
 custo igual a floor (custo/2) e a segunda aresta deve ter custo igual a ceil (custo/2).
 Valores de retorno: OK, NO_DIGRAPH, DIGRAPH_EMPTY, NO_MEM ou NO_EDGE.
 
 Split a given edge by replacing it with a vertex and two additional edges.
 The vertex identifier is the next index that can be used. The cost of each edge
 will be half the cost of the original edge. The first edge must have cost equal
 to floor (cost/2) and the second edge must have cost equal to ceil (cost/2).
 Returning error codes: OK, NO_DIGRAPH, DIGRAPH_EMPTY, NO_MEM or NO_EDGE.
*******************************************************************************/

int DigraphVertexSplit (PtDigraph pdig, unsigned int pv);
/*******************************************************************************
 Cinde um dado vértice substituindo-o por dois vértices ligados por uma aresta de
 custo zero. Os identificadores dos novos vértices serão os próximos índices que
 se possa utilizar. Os predecessores do vértice original são agora os predecessores
 do primeiro vértice acrescentado, e os sucessores são sucessores do segundo vértice
 acrescentado. Valores de retorno: OK, NO_DIGRAPH, DIGRAPH_EMPTY, NO_MEM or NO_VERTEX.

 Split a given vertex by replacing it with two vertices connected by an edge of
 Zero cost. The identifiers of the new vertices will be the next indices that
 can be used. The predecessors of the original vertex are now the predecessors
 of the first vertex added, and the successors are successors of the second vertex
 added. Returning error codes: OK, NO_DIGRAPH, DIGRAPH_EMPTY, NO_MEM or NO_VERTEX.
*******************************************************************************/

int AllSuccDist (PtDigraph pdig, unsigned int pv, double pdst, PtQueue *pqueue);
/*******************************************************************************
 Cria uma fila contendo os vértices que são sucessores diretos de um dado vértice
 e cuja distância a esse vértice é inferior a pdst. A função cria uma fila de
 inteiros contendo a identificação dos vértices e devolve a referência da fila em
 pqueue ou NULL caso não haja memória para criar e preencher a fila.
 Valores de retorno: OK, NO_DIGRAPH, DIGRAPH_EMPTY, NULL_PTR, NO_VERTEX ou NO_MEM.

 Creates a queue containing the vertices that are direct successors of a given vertex
 and whose distance to this vertex is less than pdst. The function creates a queue of
 integers containing the identification of the vertices and returns the reference of
 the queue in pqueue or NULL if there is no memory to create and fill the queue.
 Returning error codes: OK, NO_DIGRAPH, DIGRAPH_EMPTY, NULL_PTR, NO_VERTEX or NO_MEM.
*******************************************************************************/

int AllInEdgesVertex (PtDigraph pdig, unsigned int pv, PtQueue *pqueue);
/*******************************************************************************
 Cria uma fila com os pares de vértices (vértice emergente seguido do vértice 
 incidente) que definem as arestas incidentes num dado vértice. A função cria uma
 fila de pares de inteiros contendo a identificação das arestas e devolve a referência
 da fila em pqueue ou NULL caso não haja memória para criar e preencher a fila.
 Valores de retorno: OK, NO_DIGRAPH, DIGRAPH_EMPTY, NO_VERTEX, NULL_PTR ou NO_MEM.

 Creates a queue containing the edges (emergent vertex followed by vertex incident)
 that define the edges incident on a given vertex. The function creates a queue of
 pairs of integers containing the identification of edges and returns the reference of
 the queue in pqueue or NULL if there is no memory to create and fill the queue.
 Returning error codes: OK, NO_DIGRAPH, DIGRAPH_EMPTY, NULL_PTR, NO_VERTEX or NO_MEM.
*******************************************************************************/

#endif
