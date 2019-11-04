/*******************************************************************************

 Programa gráfico de simulação da funcionalidade do TDA Egyptian Fraction


 Autor : António Manuel Adrego da Rocha    Data : Março de 2019

*******************************************************************************/

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>

#include "fraction.h"  /* Ficheiro de interface do tipo fração */
#include "egyptianfraction.h"  /* Ficheiro de interface do tipo fração egípcia - versão estática */

#define MAX_FRACTIONS 9
#define MAX_OPTIONS 6

void Menu (void);
void ReadOption (int *);
void ReadFractionIndex (int *, char *);
int ActiveFraction (PtEgyptianFraction *, int);
int NotActiveFraction (PtEgyptianFraction *, int);
void WriteFractionErrorMessage (char *);
void WriteEgyptianErrorMessage (char *);
void ReadFraction (char *, PtFraction *);
void WriteEgyptianFraction (PtEgyptianFraction);
void WriteResult (int, int, int);
void WriteFraction (PtFraction);
void WriteResultExist (PtFraction, int, int);

int main (void)
{
  PtEgyptianFraction FractionArray[MAX_FRACTIONS]; PtFraction InputFraction, OutputFraction;
  int Option, Index, Fraction1, Fraction2, Equals, Exist;

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
        printf ("\e[1m\e[%d;41f", 5+Index);
		WriteEgyptianFraction (FractionArray[Index]);
        printf ("\e[0m");
      }

    /* ler opcao do utilizador */
    ReadOption (&Option);

    switch (Option)
    {
        case 1 :  ReadFractionIndex (&Fraction1, "fracao egipcia");
                  if (ActiveFraction (FractionArray, Fraction1)) break;
                  ReadFraction ("conversao para egipcia", &InputFraction);
                  if (FractionError ()) { WriteFractionErrorMessage ("A leitura da fracao"); break; }
                  FractionArray[Fraction1] = EgyptianFractionCreate (InputFraction);
                  if (EgyptianFractionError ()) WriteEgyptianErrorMessage ("A criacao/conversao");
                  FractionDestroy (&InputFraction);
                  break;

        case 2 :  ReadFractionIndex (&Fraction1, "fracao egipcia origem");
                  if (NotActiveFraction (FractionArray, Fraction1)) break;
                  do
                  {
                    ReadFractionIndex (&Fraction2, "fracao egipcia destino");
                  } while (Fraction2 == Fraction1);
                  if (ActiveFraction (FractionArray, Fraction2)) break;
                  FractionArray[Fraction2] = EgyptianFractionCopy (FractionArray[Fraction1]);
                  if (EgyptianFractionError ()) WriteEgyptianErrorMessage ("A copia");
                  break;

        case 3 :  ReadFractionIndex (&Fraction1, "fracao egipcia");
                  if (NotActiveFraction (FractionArray, Fraction1)) break;
                  OutputFraction = EgyptianFractionToFraction (FractionArray[Fraction1]);
                  if (EgyptianFractionError ()) WriteEgyptianErrorMessage ("A desconversao");
                  else
				  {
				  	WriteFraction (OutputFraction);
				  	FractionDestroy (&OutputFraction);
				  }
                  break;

        case 4 :  ReadFractionIndex (&Fraction1, "primeira fracao egipcia");
                  if (NotActiveFraction (FractionArray, Fraction1)) break;
                  do
                  {
                    ReadFractionIndex (&Fraction2, "segunda fracao egipcia");
                  } while (Fraction2 == Fraction1);
                  if (NotActiveFraction (FractionArray, Fraction2)) break;
                  Equals = EgyptianFractionEquals (FractionArray[Fraction1], FractionArray[Fraction2]);
                  if (EgyptianFractionError ()) WriteEgyptianErrorMessage ("A comparacao");
                  else WriteResult (Fraction1, Fraction2, Equals);
                  break;

        case 5 :  ReadFractionIndex (&Fraction1, "fracao egipcia");
                  if (NotActiveFraction (FractionArray, Fraction1)) break;
                  ReadFraction ("verificacao de existencia", &InputFraction);
                  if (FractionError ()) { WriteFractionErrorMessage ("A leitura da fracao"); break; }
                  Exist = EgyptianFractionBelongs (FractionArray[Fraction1], InputFraction);
                  if (EgyptianFractionError ()) WriteEgyptianErrorMessage ("A existencia");
                  WriteResultExist (InputFraction, Fraction1, Exist);
                  FractionDestroy (&InputFraction);
                  break;

        case 6 :  ReadFractionIndex (&Fraction1, "fracao");
                  if (NotActiveFraction (FractionArray, Fraction1)) break;
                  EgyptianFractionDestroy (&FractionArray[Fraction1]);
                  if (EgyptianFractionError ()) WriteEgyptianErrorMessage ("A eliminacao");
                  break;
    }
  } while (Option != 0);

  for (Index = 0; Index < MAX_FRACTIONS; Index++) 
    if (FractionArray[Index] != NULL) EgyptianFractionDestroy (&FractionArray[Index]);

  printf ("\e[26;1f");

  return EXIT_SUCCESS;
}

void Menu (void)
{
  system ("clear");

  printf("\e[2;1f~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
  printf("\e[3;1f|            Programa Grafico Para Simular Operacoes Sobre Fracoes Egicpias             |\n");
  printf("\e[4;1f~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
  printf("\e[5;1f| 1 - Criar uma fracao egipcia  | F 0 =                                                 |\n");
  printf("\e[6;1f| 2 - Copiar a fracao egipcia   | F 1 =                                                 |\n");
  printf("\e[7;1f| 3 - Converter para fracao     | F 2 =                                                 |\n");
  printf("\e[8;1f| 4 - Comparar fracoes egipcias | F 3 =                                                 |\n");
  printf("\e[9;1f| 5 - Existe na fracao egipcia  | F 4 =                                                 |\n");
  printf("\e[10;1f| 6 - Apagar uma fracao egipcia | F 5 =                                                 |\n");
  printf("\e[11;1f| 0 - Terminar o programa       | F 6 =                                                 |\n");
  printf("\e[12;1f|~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~| F 7 =                                                 |\n");
  printf("\e[13;1f| Opcao ->                      | F 8 =                                                 |\n");
  printf("\e[14;1f~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
  printf("\e[15;1f|                             Janela de Aquisicao de Dados                              |\n");
  printf("\e[16;1f~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
  printf("\e[17;1f|                                                                                       |\n");
  printf("\e[18;1f~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
  printf("\e[19;1f|                      Janela de Mensagens de Erro e de Resultados                      |\n");
  printf("\e[20;1f~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
  printf("\e[21;1f|                                                                                       |\n");
  printf("\e[22;1f|                                                                                       |\n");
  printf("\e[23;1f|                                                                                       |\n");
  printf("\e[24;1f~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
}

void ReadOption (int *poption)
{
  do
  {  
    printf("\e[13;1f| Opcao ->                      |");
    printf("\e[13;12f"); scanf ("%d", poption);
    scanf ("%*[^\n]"); scanf ("%*c");
  } while (*poption < 0 || *poption > MAX_OPTIONS);
}

void ReadFractionIndex (int *pnf, char *pmsg)
{
  int msglen = strlen (pmsg);

  do
  {
    *pnf = -1;
    printf("\e[17;1f| Indice da %s ->                          ", pmsg);
    printf("\e[17;%df", 17+msglen); scanf ("%d", pnf);
    scanf ("%*[^\n]"); scanf ("%*c");
  } while (*pnf < 0 || *pnf >= MAX_FRACTIONS);
}

int ActiveFraction (PtEgyptianFraction pnfarray[], int pnf)
{
  char car;

  if (pnfarray[pnf] != NULL)
  {
    do
    {
      printf("\e[1m\e[21;1f| A fracao %d ja existe!                     ", pnf);
      printf("\e[0m\e[22;1f| Deseja apaga-la (s/n)? ");
      scanf ("%c", &car); car = tolower (car);
      scanf ("%*[^\n]"); scanf ("%*c");
    } while (car != 'n' && car != 's');

    if (car == 's') { EgyptianFractionDestroy (&pnfarray[pnf]); return 0; }
    else return 1;
  }
  else return 0;
}

int NotActiveFraction (PtEgyptianFraction pnfarray[], int pnf)
{
  if (pnfarray[pnf] == NULL)
  {
    printf("\e[1m\e[21;1f| A fracao %d nao existe!                     ", pnf);
    printf("\e[0m\e[22;1f| Prima uma tecla para continuar ");
    scanf ("%*[^\n]"); scanf ("%*c");
    return 1;
  }
  else return 0;
}

void WriteFractionErrorMessage (char *pmsg)
{
  printf("\e[21;1f| %s de fracoes nao foi efectuada devido a", pmsg);
  printf("\e[22;1f| \e[1m%s", FractionErrorMessage ());
  printf("\e[0m\e[23;1f| Prima uma tecla para continuar ");
  scanf ("%*[^\n]"); scanf ("%*c");
}

void WriteEgyptianErrorMessage (char *pmsg)
{
  printf("\e[21;1f| %s de fracoes nao foi efectuada devido a", pmsg);
  printf("\e[22;1f| \e[1m%s", EgyptianFractionErrorMessage ());
  printf("\e[0m\e[23;1f| Prima uma tecla para continuar ");
  scanf ("%*[^\n]"); scanf ("%*c");
}

void ReadFraction (char *msg, PtFraction *pf)
{
  int VNum, VDen; int Status;

  printf("\e[1m\e[21;1f| Leitura da fracao para %s \e[0m", msg);

  do
  {
    printf("\e[17;1f| Numerador da fracao ->                      ");
    printf("\e[17;26f"); Status = scanf ("%d", &VNum);
    scanf ("%*[^\n]"); scanf ("%*c");
  } while (!Status);

  do
  {
    printf("\e[17;1f| Denominador da fracao ->                ");
    printf("\e[17;28f"); Status = scanf ("%d", &VDen);
    scanf ("%*[^\n]"); scanf ("%*c");
  } while (!Status || VDen == 0);

  *pf = FractionCreate (VNum, VDen);
}

void WriteEgyptianFraction (PtEgyptianFraction pegyptian)
{
  PtFraction fraction; char *Str;
  int n = EgyptianFractionGetSize (pegyptian);
	
  for (int i = 0; i < n-1; i++)
    {
      fraction = EgyptianFractionGetPos (pegyptian, i);
      Str = FractionToString (fraction);
      printf ("%s + ", Str);
      free (Str);
	}
  fraction = EgyptianFractionGetPos (pegyptian, n-1);
  Str = FractionToString (fraction);
  printf ("%s", Str);
  free (Str);
  if (!EgyptianFractionIsComplete (pegyptian)) printf (" + ...");
}

void WriteResult (int pfrac1, int pfrac2, int pres)
{
  if (pres) printf("\e[21;1f| \e[1mfracoes egipcias %d e %d iguais ", pfrac1, pfrac2);
  else printf("\e[21;1f| \e[1mfracoes egipcias %d e %d diferentes ", pfrac1, pfrac2);
  printf("\e[0m\e[22;1f| Prima uma tecla para continuar ");
  scanf ("%*[^\n]"); scanf ("%*c");
}

void WriteFraction (PtFraction pfraction)
{
  char * Str = FractionToString (pfraction);
  printf("\e[21;1f| \e[1mfracao resultado = %s", Str);
  printf("\e[0m\e[22;1f| Prima uma tecla para continuar ");
  scanf ("%*[^\n]"); scanf ("%*c");
  free (Str);
}

void WriteResultExist (PtFraction pfraction, int pfrac1, int pexist)
{
  char * Str = FractionToString (pfraction);
  if (pexist) printf("\e[21;1f| \e[1ma fracao %s existe na fracao egipcia %d              ", Str, pfrac1);
  else printf("\e[21;1f| \e[1ma fracao %s nao existe na fracao egipcia %d              ", Str, pfrac1);
  printf("\e[0m\e[22;1f| Prima uma tecla para continuar ");
  scanf ("%*[^\n]"); scanf ("%*c");
  free (Str);
}
