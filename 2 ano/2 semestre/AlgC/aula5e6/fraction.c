/*******************************************************************************

 Ficheiro de implementação do Tipo de Dados Abstracto FRACTION (fraction.c).
 A estrutura de dados de suporte da fração é uma estrutura, constituída pelos
 campos de tipo inteiro Num e Den para armazenar, respectivamente, o numerador 
 e o denominador da fração.

 Autor : António Manuel Adrego da Rocha    Data : Março de 2019
 
 Implementation file of the abstract data type FRACTION (fraction.c). The data
 structure to store the fraction is composed with the integer fields Num and Den
 for storing the fraction's numerator and denominator.

*******************************************************************************/

#include <stdlib.h>
#include <stdio.h>
#include "fraction.h" /* Ficheiro de interface do TDA - ADT Interface file */

/************** Definição da Estrutura de Dados Interna do Vector *************/
/**************** Fraction's Internal Data Structure Definition ***************/

struct fraction
{
    int Num; /* numerador da fração - fraction's numerator */
    int Den; /* denominador da fração - fraction's denominator */
};

/*********************** Controlo Centralizado de Erro ************************/
/************************* Centralized Error Control **************************/

static unsigned int Error = OK; /* inicialização do erro - error initialization */

static char *ErrorMessages[] = {"sem erro - without error",

                               "fracao(fracoes) inexistente(s) - fraction(s) do not exist",

                               "memoria esgotada - out of memory",

                               "divisor nulo - division by zero",

                               "denominador nulo - null denominator"

};

static char *AbnormalErrorMessage = "erro desconhecido - unknown error";

/************** Número de mensagens de erro previstas no módulo ***************/
/************** Number of predicted error messages in the module **************/

#define N (sizeof(ErrorMessages) / sizeof(char *))

/************************ Alusão às Funções Auxiliares ************************/
/*********************** Allusion to Auxiliary Functions **********************/

static PtFraction CreateFraction(void); /* construtor primário - primary constructor */
static void ReduceFraction(PtFraction); /* simplificador da fração - fraction simplifier */

/*************************** Definição das Funções ****************************/
/*************************** Function Definitions *****************************/

void FractionClearError(void)
{
    Error = OK;
}

int FractionError(void)
{
    return Error;
}

char *FractionErrorMessage(void)
{
    if (Error < N)
        return ErrorMessages[Error];
    else
        return AbnormalErrorMessage;   /* não há mensagem de erro - - no error message */
}

PtFraction FractionCreate(int pnum, int pden) /* construtor inicializador - initializer constructor */
{
    PtFraction Frac;

    if (pden == 0) { Error = NO_FRACTION; return NULL; }
    if (pden < 0) { pnum = -pnum; pden = -pden; }
    if ((Frac = CreateFraction()) == NULL) { Error = NO_MEM; return NULL; }
    Frac->Num = pnum;
    Frac->Den = pden;
    if (Frac->Num != 0) ReduceFraction(Frac);
    Error = OK;
    return Frac;
}

int FractionGetNumerator(PtFraction pfrac)
{
    if (pfrac == NULL) { Error = NO_FRACTION; return 0; }
    else { Error = OK; return pfrac->Num; }
}

int FractionGetDenominator(PtFraction pfrac)
{
    if (pfrac == NULL)
    {
        Error = NO_FRACTION;
        return 0;
    }
    else
    {
        Error = OK;
        return pfrac->Den;
    }
}

void FractionDestroy(PtFraction *pfrac) /* destrutor - destructor */
{
    PtFraction Frac = *pfrac;

    if (Frac == NULL) { Error = NO_FRACTION; return; }
    free (Frac);
    *pfrac = NULL;               // Programação Defensiva
    Error = OK;
}

PtFraction FractionCopy(PtFraction pfrac) /* construtor cópia - copy constructor */
{
    if (pfrac == NULL) { Error = NO_FRACTION; return NULL; }
    else return FractionCreate (pfrac->Num, pfrac->Den);
}

PtFraction FractionSymmetrical(PtFraction pfrac) /* construtor simétrico - symmetrical constructor */
{
    PtFraction Frac;

    if (pfrac == NULL)
    {
        Error = NO_FRACTION;
        return NULL;
    }
    if ((Frac = CreateFraction()) == NULL)
    {
        Error = NO_MEM;
        return NULL;
    }
    
    Frac->Num = -(pfrac->Num);
    Frac->Den = pfrac->Den;

    if (Frac->Num != 0)
        ReduceFraction(Frac);

    Error = OK;
    return Frac;
}

PtFraction FractionAddition(PtFraction pfrac1, PtFraction pfrac2)
{
    PtFraction Frac;

    if (pfrac1 == NULL || pfrac2 == NULL)
    {
        Error = NO_FRACTION;
        return NULL;
    }
    if ((Frac = CreateFraction()) == NULL)
    {
        Error = NO_MEM;
        return NULL;
    }

    Frac->Num = pfrac1->Num * pfrac2->Den + pfrac2->Num * pfrac1->Den;
    Frac->Den = pfrac1->Den * pfrac2->Den;

    if (Frac->Num != 0)
        ReduceFraction(Frac);

    Error = OK;
    return Frac;
}

PtFraction FractionSubtraction(PtFraction pfrac1, PtFraction pfrac2)
{
    PtFraction Frac;

    if (pfrac1 == NULL || pfrac2 == NULL)
    {
        Error = NO_FRACTION;
        return NULL;
    }
    if ((Frac = CreateFraction()) == NULL)
    {
        Error = NO_MEM;
        return NULL;
    }


    Frac->Num = pfrac1->Num*pfrac2->Den - pfrac2->Num*pfrac1->Den;
    Frac->Den = pfrac1->Den*pfrac2->Den;

    if (Frac->Num != 0)
        ReduceFraction(Frac);

    Error = OK;
    return Frac;
}

PtFraction FractionMultiplication(PtFraction pfrac1, PtFraction pfrac2)
{
    PtFraction Frac;

    if (pfrac1 == NULL || pfrac2 == NULL) { Error = NO_FRACTION; return NULL; }
    if ((Frac = CreateFraction()) == NULL) { Error = NO_MEM; return NULL; }

    Frac -> Num = pfrac1->Num * pfrac2->Num;
    Frac-> Den = pfrac1->Den * pfrac2->Den;
    if (Frac->Num != 0) ReduceFraction(Frac);

    Error = OK;
    return Frac;
}

PtFraction FractionDivision(PtFraction pfrac1, PtFraction pfrac2)
{
    PtFraction Frac;

    if (pfrac1 == NULL || pfrac2 == NULL)
    {
        Error = NO_FRACTION;
        return NULL;
    }
    if ((Frac = CreateFraction()) == NULL)
    {
        Error = NO_MEM;
        return NULL;
    }

    if(FractionIsNull(pfrac2)){
        Error = ZERO_DIV;
        return NULL;
    }
    
    Frac->Num = pfrac1->Den * pfrac2->Num;
    Frac->Den = pfrac1->Num * pfrac2->Den;

    if(Frac->Den < 0){
        Frac->Num = -(Frac->Num);
        Frac->Den = -(Frac->Den);
    }

    Error = OK;
    return Frac;
}

int FractionIsNull(PtFraction pfrac)
{
    if (pfrac == NULL)
    {
        Error = NO_FRACTION;
        return NULL;
    }

    Error = OK;

    if(pfrac->Num == 0 || pfrac->Den == 0){
        return 1;
    }
    return 0;
}

int FractionEquals(PtFraction pfrac1, PtFraction pfrac2)
{
    if (pfrac1 == NULL || pfrac2 == NULL)
    {
        Error = NO_FRACTION;
        return NULL;
    }
    Error = OK;
    if (pfrac1->Num == pfrac2->Num && pfrac1->Den == pfrac2->Den)
        return 1;

    return 0;  

}

int FractionCompareTo(PtFraction pfrac1, PtFraction pfrac2)
{
	
	PtFraction Frac1, Frac2;
	
    if (pfrac1 == NULL || pfrac2 == NULL)
    {
        Error = NO_FRACTION;
        return NULL;
    }
    Error = OK;
	
	Frac1->Num = pfrac1->Num*pfrac2->Den;
    Frac1->Den = pfrac1->Den*pfrac2->Den;
    
    Frac1->Num = pfrac2->Num*pfrac1->Den;
    Frac1->Den = pfrac1->Den*pfrac2->Den;

    if (Frac1->Num != 0)
        ReduceFraction(Frac1);
        
    if (Frac2->Num != 0)
        ReduceFraction(Frac2);
	
	int sub=Frac1->Num - Frac2->Num;

    return sub;
}

int FractionIsProper(PtFraction pfrac){
    if (pfrac == NULL)
    {
        Error = NO_FRACTION;
        return NULL;
    }
    Error = OK;
    if(pfrac->Num < pfrac->Den)
        return 1;

    return 0;
}

void FractionSet(PtFraction pfrac, int pnum, int pden){

    if (pfrac == NULL)
    {
        Error = NO_FRACTION;
        return;
    }

    if (pden == 0)
    {
        Error = NULL_DEN;
        return;
    }

    if (pden < 0)
    {
        pnum = -pnum;
        pden = -pden;
    }

    pfrac->Num = pnum;
    pfrac->Den = pden;

    if (pfrac->Num != 0)
        ReduceFraction(pfrac);

    Error = OK;
}

char *FractionToString(PtFraction pfrac)

{

    if (pfrac == NULL)
    {
        Error = NO_FRACTION;
        return 0;
    }

    char *Str;

    /* cria a sequência de caracteres - allocating the string */

    if ((Str = calloc(64, sizeof(char))) == NULL)

    {
        Error = NO_MEM;
        return NULL;
    }

    sprintf(Str, "%d/%d", pfrac->Num, pfrac->Den);

    Error = OK;

    return Str;
}

/*********************** Definição das Funções Internas ***********************/
/*********************** Definition of Internal Functions *********************/

/*******************************************************************************
 Função que aloca memória dinâmica para a fração sem qualquer inicialização.
 Este construtor primário serve para ser usado pelas restantes operações que criam
 uma nova fração.
 
 Function that allocates dynamic memory for the fraction without any initialization.
 This primary constructor is to be used by the remaining operations that create a 
 new fraction.
*******************************************************************************/
static PtFraction CreateFraction(void)
{
    return (PtFraction)malloc(sizeof(struct fraction));
}

/*******************************************************************************
 Função que calcula o máximo divisor comum através do método de Euclides.
 
 Function that calculates the maximum common divisor through the Euclid method.
*******************************************************************************/
static int mdc(int px, int py)
{
    int r, m, n;

    if (px >= py)
    {
        m = px;
        n = py;
    }
    else
    {
        m = py;
        n = px;
    }

    while (n != 0)
    {
        r = m % n;
        m = n;
        n = r;
    }
    return m;
}

/*******************************************************************************
 Função que reduz uma fração. Esta função deve ser usada pelas operações 
 aritméticas e pelo construtor inicializador.
 
 Function that reduces a fraction. This function must be used by the arithmetic 
 operations and by the initializer constructor.
*******************************************************************************/
static void ReduceFraction(PtFraction pfrac)
{
    int MDC;

    if (pfrac->Num < 0)
        MDC = mdc(-pfrac->Num, pfrac->Den);
    else
        MDC = mdc(pfrac->Num, pfrac->Den);

    if (MDC == 1)
        return;
    else
    {
        pfrac->Num /= MDC;
        pfrac->Den /= MDC;
    }
}
