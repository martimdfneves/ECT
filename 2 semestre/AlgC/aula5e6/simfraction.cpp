/*******************************************************************************

 Programa gráfico de simulação da funcionalidade do TDA Fraction


 Autor : António Manuel Adrego da Rocha    Data : Março de 2019

*******************************************************************************/

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>

#include "fraction.h"  /* Ficheiro de interface do tipo de dados */

#define MAX_FRACTIONS 12
#define MAX_OPTIONS 9

void Menu (void);
void ReadOption (int *);
void ReadFractionIndex (int *, char *);
int ActiveFraction (PtFraction *, int);
int NotActiveFraction (PtFraction *, int);
void WriteErrorMessage (char *);
void ReadFraction (PtFraction *);
void WriteFraction (PtFraction);
void WriteResult (int, int, int);

int main (void)
{
  PtFraction FractionArray[MAX_FRACTIONS];
  int Option, Index, Fraction1, Fraction2, Fraction3, Equals; char *Str;

  for (Index = 0; Index < MAX_FRACTIONS; Index++) FractionArray[Index] = NULL;
  
  do
  {
    /* limpar o ecran e apresentar menu */
    Menu ();

    /* inicializar o erro */
    FractionClearError ();

    /* apresentar as fracções activas */
    for (Index = 0; Index < MAX_FRACTIONS; Index++)
      if (FractionArray[Index] != NULL)
      {
        printf ("\e[1m\e[%d;51f", 5+Index);
		/* WriteFraction (FractionArray[Index]); */
        Str = FractionToString (FractionArray[Index]);
        printf ("%s", Str); printf ("\e[0m");
        free (Str);
      }

    /* ler opcao do utilizador */
    ReadOption (&Option);

    switch (Option)
    {
        case 1 :  ReadFractionIndex (&Fraction1, "fracao");
                  if (ActiveFraction (FractionArray, Fraction1)) break;
                  ReadFraction (&FractionArray[Fraction1]);
                  if (FractionError ()) WriteErrorMessage ("A criacao");
                  break;

        case 2 :  ReadFractionIndex (&Fraction1, "primeira fracao");
                  ReadFractionIndex (&Fraction2, "segunda fracao");
                  do
                  {
                    ReadFractionIndex (&Fraction3, "fracao soma");
                  } while (Fraction3 == Fraction1 || Fraction3 == Fraction2);
                  if (ActiveFraction (FractionArray, Fraction3)) break;
                  FractionArray[Fraction3] = FractionAddition (FractionArray[Fraction1], FractionArray[Fraction2]);
                  if (FractionError ()) WriteErrorMessage ("A soma");
                  break;

        case 3 :  ReadFractionIndex (&Fraction1, "primeira fracao");
                  ReadFractionIndex (&Fraction2, "segunda fracao");
                  do
                  {
                    ReadFractionIndex (&Fraction3, "fracao diferenca");
                  } while (Fraction3 == Fraction1 || Fraction3 == Fraction2);
                   if (ActiveFraction (FractionArray, Fraction3)) break;
                   FractionArray[Fraction3] = FractionSubtraction (FractionArray[Fraction1], FractionArray[Fraction2]);
                   if (FractionError ()) WriteErrorMessage ("A diferenca");
                   break;

        case 4 :  ReadFractionIndex (&Fraction1, "primeira fracao");
                  ReadFractionIndex (&Fraction2, "segunda fracao");
                  do
                  {
                    ReadFractionIndex (&Fraction3, "fracao produto");
                  } while (Fraction3 == Fraction1 || Fraction3 == Fraction2);
                  if (ActiveFraction (FractionArray, Fraction3)) break;
                  FractionArray[Fraction3] = FractionMultiplication (FractionArray[Fraction1], FractionArray[Fraction2]);
                  if (FractionError ()) WriteErrorMessage ("A multiplicacao");
                  break;

        case 5 :  ReadFractionIndex (&Fraction1, "primeira fracao");
                  ReadFractionIndex (&Fraction2, "segunda fracao");
                  do
                  {
                    ReadFractionIndex (&Fraction3, "fracao quociente");
                  } while (Fraction3 == Fraction1 || Fraction3 == Fraction2);
                  if (ActiveFraction (FractionArray, Fraction3)) break;
                  FractionArray[Fraction3] = FractionDivision (FractionArray[Fraction1], FractionArray[Fraction2]);
                  if (FractionError ()) WriteErrorMessage ("A divisao");
                  break;

        case 6 :  ReadFractionIndex (&Fraction1, "fracao origem");
                  if (NotActiveFraction (FractionArray, Fraction1)) break;
                  do
                  {
                    ReadFractionIndex (&Fraction2, "fracao destino");
                  } while (Fraction2 == Fraction1);
                  if (ActiveFraction (FractionArray, Fraction2)) break;
                  FractionArray[Fraction2] = FractionCopy (FractionArray[Fraction1]);
                  if (FractionError ()) WriteErrorMessage ("A copia");
                  break;
        
        case 7 :  ReadFractionIndex (&Fraction1, "fracao origem");
                  if (NotActiveFraction (FractionArray, Fraction1)) break;
                  do
                  {
                    ReadFractionIndex (&Fraction2, "fracao destino");
                  } while (Fraction2 == Fraction1);
                  if (ActiveFraction (FractionArray, Fraction2)) break;
                  FractionArray[Fraction2] = FractionSymmetrical (FractionArray[Fraction1]);
                  if (FractionError ()) WriteErrorMessage ("O simetrico");
                  break;
        
        case 8 :  ReadFractionIndex (&Fraction1, "primeira fracao");
                  if (NotActiveFraction (FractionArray, Fraction1)) break;
                  do
                  {
                    ReadFractionIndex (&Fraction2, "segunda fracao");
                  } while (Fraction2 == Fraction1);
                  if (NotActiveFraction (FractionArray, Fraction2)) break;
                  Equals = FractionCompareTo (FractionArray[Fraction1], FractionArray[Fraction2]);
                  if (FractionError ()) WriteErrorMessage ("A comparacao");
                  else WriteResult (Fraction1, Fraction2, Equals);
                  break;

        case 9 :  ReadFractionIndex (&Fraction1, "fracao");
                  if (NotActiveFraction (FractionArray, Fraction1)) break;
                  FractionDestroy (&FractionArray[Fraction1]);
                  if (FractionError ()) WriteErrorMessage ("A eliminacao");
                  break;
    }
  } while (Option != 0);

  for (Index = 0; Index < MAX_FRACTIONS; Index++) 
    if (FractionArray[Index] != NULL) FractionDestroy (&FractionArray[Index]);

  printf ("\e[28;1f");

  return EXIT_SUCCESS;
}

void Menu (void)
{
  system ("clear");

  printf("\e[2;1f~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
  printf("\e[3;1f|            Programa Grafico Para Simular Operacoes Sobre Fracoes             |\n");
  printf("\e[4;1f~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
  printf("\e[5;1f| 1 - Criar uma fracao         | Fracao  0 =                                   |\n");
  printf("\e[6;1f| 2 - Somar fracoes            | Fracao  1 =                                   |\n");
  printf("\e[7;1f| 3 - Subtrair fracoes         | Fracao  2 =                                   |\n");
  printf("\e[8;1f| 4 - Multiplicar fracoes      | Fracao  3 =                                   |\n");
  printf("\e[9;1f| 5 - Dividir fracoes          | Fracao  4 =                                   |\n");
  printf("\e[10;1f| 6 - Copiar uma fracao        | Fracao  5 =                                   |\n");
  printf("\e[11;1f| 7 - Simetrico de uma fracao  | Fracao  6 =                                   |\n");  
  printf("\e[12;1f| 8 - Comparar fracoes         | Fracao  7 =                                   |\n");
  printf("\e[13;1f| 9 - Apagar uma fracao        | Fracao  8 =                                   |\n");
  printf("\e[14;1f| 0 - Terminar o programa      | Fracao  9 =                                   |\n");
  printf("\e[15;1f|~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~| Fracao 10 =                                   |\n");
  printf("\e[16;1f| Opcao ->                     | Fracao 11 =                                   |\n");
  printf("\e[17;1f~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~-~\n");
  printf("\e[18;1f|                         Janela de Aquisicao de Dados                         |\n");
  printf("\e[19;1f~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
  printf("\e[20;1f|                                                                              |\n");
  printf("\e[21;1f~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
  printf("\e[22;1f|                  Janela de Mensagens de Erro e de Resultados                 |\n");
  printf("\e[23;1f~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
  printf("\e[24;1f|                                                                              |\n");
  printf("\e[25;1f|                                                                              |\n");
  printf("\e[26;1f|                                                                              |\n");
  printf("\e[27;1f~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
}

void ReadOption (int *poption)
{
  do
  {  
    printf("\e[16;1f| Opcao ->                     |");
    printf("\e[16;12f"); scanf ("%d", poption);
    scanf ("%*[^\n]"); scanf ("%*c");
  } while (*poption < 0 || *poption > MAX_OPTIONS);
}

void ReadFractionIndex (int *pnf, char *pmsg)
{
  int msglen = strlen (pmsg);

  do
  {
    *pnf = -1;
    printf("\e[20;1f| Indice do %s ->                          ", pmsg);
    printf("\e[20;%df", 17+msglen); scanf ("%d", pnf);
    scanf ("%*[^\n]"); scanf ("%*c");
  } while (*pnf < 0 || *pnf >= MAX_FRACTIONS);
}

int ActiveFraction (PtFraction pnfarray[], int pnf)
{
  char car;

  if (pnfarray[pnf] != NULL)
  {
    do
    {
      printf("\e[1m\e[24;1f| A fracao %d ja existe!                     ", pnf);
      printf("\e[0m\e[25;1f| Deseja apaga-lo (s/n)? ");
      scanf ("%c", &car); car = tolower (car);
      scanf ("%*[^\n]"); scanf ("%*c");
    } while (car != 'n' && car != 's');

    if (car == 's') { FractionDestroy (&pnfarray[pnf]); return 0; }
    else return 1;
  }
  else return 0;
}

int NotActiveFraction (PtFraction pnfarray[], int pnf)
{
  if (pnfarray[pnf] == NULL)
  {
    printf("\e[1m\e[24;1f| A fracao %d nao existe!                     ", pnf);
    printf("\e[0m\e[25;1f| Prima uma tecla para continuar ");
    scanf ("%*[^\n]"); scanf ("%*c");
    return 1;
  }
  else return 0;
}

void WriteErrorMessage (char *pmsg)
{
  printf("\e[24;1f| %s de fracoes nao foi efectuada devido a", pmsg);
  printf("\e[25;1f| \e[1m%s", FractionErrorMessage ());
  printf("\e[0m\e[26;1f| Prima uma tecla para continuar ");
  scanf ("%*[^\n]"); scanf ("%*c");
}

void ReadFraction (PtFraction *pf)
{
  int VNum, VDen; int Status;

  do
  {
    printf("\e[20;1f| Numerador da fracao ->                      ");
    printf("\e[20;26f"); Status = scanf ("%d", &VNum);
    scanf ("%*[^\n]"); scanf ("%*c");
  } while (!Status);

  do
  {
    printf("\e[20;1f| Denominador da fracao ->                ");
    printf("\e[20;28f"); Status = scanf ("%d", &VDen);
    scanf ("%*[^\n]"); scanf ("%*c");
  } while (!Status || VDen == 0);

  *pf = FractionCreate (VNum, VDen);
}

void WriteFraction (PtFraction pf)
{
  if (pf != NULL) printf ("%d / %d", FractionGetNumerator (pf),  FractionGetDenominator (pf));
}

void WriteResult (int pfrac1, int pfrac2, int pres)
{
/*
  if (pres) printf("\e[24;1f| \e[1mfracoes iguais \e[0m");
  else printf("\e[24;1f| \e[1mfracoes diferentes \e[0m");
*/
  if (pres < 0) printf("\e[24;1f| \e[1mfracao %d menor do que fracao %d \e[0m", pfrac1, pfrac2);
  else if (pres > 0) printf("\e[24;1f| \e[1mfracao %d maior do que fracao %d \e[0m", pfrac1, pfrac2);
       else printf("\e[24;1f| \e[1mfracoes %d e %d iguais \e[0m", pfrac1, pfrac2);
  printf("\e[0m\e[25;1f| Prima uma tecla para continuar ");
  scanf ("%*[^\n]"); scanf ("%*c");
}
