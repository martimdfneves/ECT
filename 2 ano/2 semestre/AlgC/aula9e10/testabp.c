#include <stdio.h>
#include <stdlib.h>
#include <assert.h>

#include "abp.h"

int main (void)
{
	PtABP abp1, abp2, abp3; PtFraction f1, f2, f3, min, max;

	abp1 = ABPCreateFile ("abp1.txt");

	if (ABPEmpty (abp1)) printf ("\nArvore vazia! Empty tree!\n");
	printf ("Arvore 1 (Tree 1)\n");
	printf ("Numero de nos da arvore (node number) = %d\n", ABPSize (abp1));
	printf ("Altura da arvore (tree height) = %d\n", ABPHeight (abp1));
	printf ("Arvore listada em ordem - Tree listed in-order\n");
	ABPPrint (abp1); printf ("\n");
	printf ("Arvore listada hierarquicamente - Tree listed hierarchically\n");
	ABPDisplay (abp1); printf ("\n");
	printf ("Press a key"); scanf ("%*c");

	printf ("Copiar a arvore e comparar - Copy and compare the tree\n");
	printf ("Copy (abp3 = abp1) and compare using Equals (abp3 == abp1 ? true)\n");
	abp3 = ABPCopy (abp1);
	printf ("Arvore 3 (Tree 3)\n");
	printf ("Numero de nos da arvore (node number) = %d\n", ABPSize (abp3));
	printf ("Altura da arvore (tree height)= %d\n", ABPHeight (abp3));
	ABPPrint (abp3); printf ("\n");
	printf ("Arvore listada hierarquicamente - Tree listed hierarchically\n");
	ABPDisplay (abp3); printf ("\n");
	assert (ABPEquals (abp3, abp1) == 1);
	printf ("Press a key"); scanf ("%*c");

	printf ("Retirar 3/4 da arvore abp1 e pesquisar 3/4 na abp3 - Remove 3/4 from abp1 and search 3/4 in abp3\n");
	f1 = FractionCreate (3, 4);
	ABPDelete (abp1, f1);
	assert (ABPSearch (abp3, f1) == 1);
	printf ("Inserir 3/4 na arvore abp1 - Insert 3/4 in abp1\n");
	ABPInsert (abp1, f1);
	printf ("Inserir outra vez 3/4 na arvore abp1 - Insert again 3/4 in abp1\n");
	ABPInsert (abp1, f1);
	assert (ABPErrorCode () == REP_ELEM);
	
	if (ABPEmpty (abp1)) printf ("\nArvore vazia! Empty tree!\n");
	printf ("Numero de nos da arvore (node number) = %d\n", ABPSize (abp1));
	printf ("Altura da arvore (tree height) = %d\n", ABPHeight (abp1));
	printf ("Arvore listada hierarquicamente - Tree listed hierarchically\n");
	ABPDisplay (abp1); printf ("\n");
	
	min = ABPMin (abp1);
	printf ("Min == -6/7 ? true\n");
	f2 = FractionCreate (-6, 7);
	assert (FractionEquals (min, f2) == 1);
	max = ABPMax (abp1);
	printf ("Max == 4/5 ? true\n");
	f3 = FractionCreate (4, 5);
	assert (FractionEquals (max, f3) == 1);
	printf ("Press a key"); scanf ("%*c");

	printf ("Arvore 2 (Tree 2)\n");
	abp2 = ABPCreateFile ("abp2.txt");
	printf ("Arvore listada em ordem - Tree listed in-order\n");
	ABPPrint (abp2); printf ("\n");
	printf ("Arvore listada hierarquicamente - Tree listed hierarchically\n");
	ABPDisplay (abp2); printf ("\n");

	printf ("Reuniao da abp1 e abp2 - Reunion of abp1 and abp2\n");
	ABPReunion (abp1, abp2);
	printf ("Arvore listada em ordem - Tree listed in-order\n");
	ABPPrint (abp1); printf ("\n");
	printf ("Arvore listada hierarquicamente - Tree listed hierarchically\n");
	ABPDisplay (abp1); printf ("\n");
	printf ("Press a key"); scanf ("%*c");

	printf ("Diferenca da abp1 e abp2 - Difference of abp1 and abp2\n");
	ABPDifference (abp1, abp2);
	printf ("Arvore listada em ordem - Tree listed in-order\n");
	ABPPrint (abp1); printf ("\n");
	printf ("Arvore listada hierarquicamente - Tree listed hierarchically\n");
	ABPDisplay (abp1); printf ("\n");
	printf ("Press a key"); scanf ("%*c");

	printf ("Recuperar a abp1 original - Recover the original abp1\n");
	printf ("Interseccao da abp1 e abp2 - Intersection of abp1 and abp2\n");
	ABPDestroy (&abp1);
	abp1 = ABPCopy (abp3);	
	ABPIntersection (abp1, abp2);
	printf ("Arvore listada em ordem - Tree listed in-order\n");
	ABPPrint (abp1); printf ("\n");
	printf ("Arvore listada hierarquicamente - Tree listed hierarchically\n");
	ABPDisplay (abp1); printf ("\n");

	printf ("Destruir as arvores - Releasing the trees\n\n");
	ABPDestroy (&abp1);
	ABPDestroy (&abp2);
	ABPDestroy (&abp3);

	return 0;
}
