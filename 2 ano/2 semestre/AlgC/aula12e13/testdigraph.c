#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>
#include "digraph.h"

void WriteErrorMessage (unsigned int, char *);
void ReadVertexNumber (unsigned int *);
void DisplayDigraph (PtDigraph);
void PrintQueue (PtQueue);
void PrintQueuePair (PtQueue);

int main (int argc, char *argv[])
{
	PtDigraph Digraph = NULL, DigraphCopy = NULL; PtQueue Queue = NULL;
	int Error; unsigned int Vertex, NVertexes, NEdges; double Class;

	if (argc < 2)
	{
		printf ("Usage %s filename\n", argv[0]);
		exit (1);
	}

	Digraph = CreateFile (argv[1]);
	if (Digraph == NULL) return 1;

	printf ("------------- Apresentar Digrafo - Presenting Digraph -------------\n");
	printf ("Digrafo lido do ficheiro (digraph read from file) %s\n", argv[1]);
	if (Digraph == NULL) WriteErrorMessage (NO_MEM, "Leitura - Reading");
	VertexNumber (Digraph, &NVertexes);
	EdgeNumber (Digraph, &NEdges);
	printf ("Vertexes = %d / Edges = %d\n", NVertexes, NEdges);
	DisplayDigraph (Digraph);

	printf ("-------------------------------------------------------------------\n");
	for (Vertex = 1; Vertex <= NVertexes; Vertex++)
	{
		Error = VertexType (Digraph, Vertex);
		switch (Error)
		{
			case OK     : printf ("Vertice (Vertex) %d Normal\n", Vertex);  break;
			case SINK   : printf ("Vertice (Vertex) %d Sumidouro (Sink)\n", Vertex);  break;
			case SOURCE : printf ("Vertice (Vertex) %d Fonte (Source)\n", Vertex);  break;
			case DISC   : printf ("Vertice (Vertex) %d Desconexo (Disconnected)\n", Vertex);  break;
			default     : WriteErrorMessage (Error, "Teste do Vertice - Vertex Test");
		}
	}
	printf ("-------------------------------------------------------------------\n");

	for (Vertex = 1; Vertex <= NVertexes; Vertex++)
	{
		Error = VertexClassification (Digraph, Vertex, &Class);
		if (Error) WriteErrorMessage (Error, "Classificacao do Vertice - Vertex Classification");
		else printf ("Classificacao do vertice (Vertex Classification) %d = %f\n", Vertex, Class);
	}
	printf ("-------------------------------------------------------------------\n");

	Error = AllSuccDist (Digraph, 2, 1, &Queue);
	printf ("Sucessores do vertice 2 a distancia inferior a 1\n");
	printf ("Successors of vertex 2 at a distance less than 1\n");
	if (Error) WriteErrorMessage (Error, "Vertices Sucessores - Successors Vertexes");
	else if (QueueIsEmpty (Queue)) printf ("Nao existem vertices sucessores - There are no successor vertexes\n");
		 else PrintQueue (Queue);
	QueueDestroy (&Queue);
	printf ("-------------------------------------------------------------------\n");

	Error = AllSuccDist (Digraph, 1, 5, &Queue);
	printf ("Sucessores do vertice 1 a distancia inferior a 5\n");
	printf ("Successors of vertex 1 at a distance less than 5\n");
	if (Error) WriteErrorMessage (Error, "Vertices Sucessores - Successors Vertexes");
	else if (QueueIsEmpty (Queue)) printf ("Nao existem vertices sucessores - There are no successor vertexes\n");
		 else PrintQueue (Queue);
	QueueDestroy (&Queue);
	printf ("-------------------------------------------------------------------\n");

	Error = AllInEdgesVertex (Digraph, 3, &Queue);
	printf ("Arestas incidentes no vertice 3 - Edges incident on vertex 3\n");
	if (Error) WriteErrorMessage (Error, "Arestas Incidentes - edges");
	else if (QueueIsEmpty (Queue)) printf ("Nao existem arestas incidentes - There are no incident edges\n");
		 else PrintQueuePair (Queue);
	QueueDestroy (&Queue);
	printf ("-------------------------------------------------------------------\n");

	DigraphCopy = Copy (Digraph);
	printf ("Subdividir aresta 1->3 - Split edge 1->3\n");
	printf ("---- Apresentar Digrafo Original - Presenting Original Digraph ----\n");
	VertexNumber (Digraph, &NVertexes);
	EdgeNumber (Digraph, &NEdges);
	printf ("Vertexes = %d / Edges = %d\n", NVertexes, NEdges);
	DisplayDigraph (Digraph);
	Error = DigraphEdgeSplit (Digraph, 1, 3);
	printf ("---- Apresentar Digrafo Alterado - Presenting Changed Digraph -----\n");
	if (Error) WriteErrorMessage (NO_MEM, "Split Edge");
	VertexNumber (Digraph, &NVertexes);
	EdgeNumber (Digraph, &NEdges);
	printf ("Vertexes = %d / Edges = %d\n", NVertexes, NEdges);
	DisplayDigraph (Digraph);
	printf ("-------------------------------------------------------------------\n");

	printf ("Cindir vertice 3 - Split vertex 3\n");
	printf ("---- Apresentar Digrafo Original - Presenting Original Digraph ----\n");
	VertexNumber (DigraphCopy, &NVertexes);
	EdgeNumber (DigraphCopy, &NEdges);
	printf ("Vertexes = %d / Edges = %d\n", NVertexes, NEdges);
	DisplayDigraph (DigraphCopy);			
	Error = DigraphVertexSplit (DigraphCopy, 3);
	printf ("---- Apresentar Digrafo Alterado - Presenting Changed Digraph -----\n");
	if (Error) WriteErrorMessage (NO_MEM, "Split Vertex");
	VertexNumber (DigraphCopy, &NVertexes);
	EdgeNumber (DigraphCopy, &NEdges);
	printf ("Vertexes = %d / Edges = %d\n", NVertexes, NEdges);
	DisplayDigraph (DigraphCopy);
	printf ("-------------------------------------------------------------------\n");

/*
	printf ("\nPrima uma tecla para continuar - Hit key to continue");
	scanf ("%*[^\n]"); scanf ("%*c");
*/
	return 0;
}

void WriteErrorMessage (unsigned int perro, char *pmsg)
{
	printf ("%s -> ", pmsg);
	switch (perro)
	{
		case NO_DIGRAPH    : printf ("%s", "digrafo inexistente - nonexistent digraph\n"); break;
		case NO_MEM        : printf ("%s", "memoria esgotada - out of memory\n"); break;
		case NULL_PTR      : printf ("%s", "ponteiro nulo - null pointer\n"); break;
		case DIGRAPH_EMPTY : printf ("%s", "digrafo vazio - empty digraph\n"); break;
		case NO_VERTEX     : printf ("%s", "vertice inexistente - nonexistent vertex\n"); break;
		case REP_VERTEX    : printf ("%s", "vertice repetido - repeated vertex\n"); break;
		case NO_EDGE       : printf ("%s", "aresta inexistente - nonexistent edge\n"); break;
		case REP_EDGE      : printf ("%s", "aresta repetida - repeated edge\n"); break;
		case NO_FILE       : printf ("%s", "ficheiro inexistente - nonexistent file\n"); break;
		case NO_DAG        : printf ("%s", "digrafo aciclico - aciclic digraph\n"); break;
		case NEG_CYCLE     : printf ("%s", "digrafo com ciclo negativo - digraph with negative cycle\n"); break;
		default            : printf ("erro desconhecido - unknown error\n");
	}
}

void ReadVertexNumber (unsigned int *pvertice)
{
  do
  {
    printf ("Vertice No -> "); scanf ("%d", pvertice);
    scanf ("%*[^\n]"); scanf ("%*c");
  } while (*pvertice < 0);
}

void DisplayDigraph (PtDigraph pdigraph)
{
  unsigned int NVertexes, Linha; char ListaVert[256];

  VertexNumber (pdigraph, &NVertexes);

  for (Linha = 1; Linha <= NVertexes; Linha++)
  {
    GetVertexList (pdigraph, Linha, ListaVert);
    printf ("%s\n", ListaVert);
  }
}

void PrintQueue (PtQueue pqueue)
{
	if (pqueue == NULL) { printf ("Erro - Error\n"); return ; }
	
	int Number; unsigned int Size;

	QueueSize (pqueue, &Size);
	printf ("%d Vertex(es) -> ", Size);
	while (!QueueIsEmpty (pqueue))
	{
		QueueDequeue (pqueue, &Number);
		printf ("%d ", Number);
	}
	printf ("\n");
}

void PrintQueuePair (PtQueue pqueue)
{
	if (pqueue == NULL) { printf ("Erro - Error\n"); return ; }
	
	int Number; unsigned int Size;

	QueueSize (pqueue, &Size);
	printf ("%d Edge(s) -> ", Size/2);
	while (!QueueIsEmpty (pqueue))
	{
		QueueDequeue (pqueue, &Number); printf ("(%d ", Number);
		QueueDequeue (pqueue, &Number); printf ("- %d) ", Number);
	}
	printf ("\n");
}

