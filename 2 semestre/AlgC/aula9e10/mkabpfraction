# makefile para a arvore ABP Fraction
CC = gcc
CFLAGS = -Wall

OBJM = queue.o stack.o abp.o fraction.o
HEADERS = queue.h stack.h abp.h fraction.h

OBJECTS = $(OBJM) testabp.o
EXES = tabp

all:	$(EXES)

tabp:	testabp.o $(OBJM)
	$(CC) $(CFLAGS) testabp.o $(OBJM) -o tabp

$(OBJM):	$(HEADERS)

clean:
	rm -f $(OBJECTS) *~

cleanall:	clean
	rm -f $(EXES)
