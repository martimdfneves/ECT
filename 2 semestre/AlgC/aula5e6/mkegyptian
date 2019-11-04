# makefile para o TDA Egyptian Fraction
CC = gcc
CFLAGS = -Wall

OBJM = fraction.o egyptianfraction.o
HEADERS = fraction.h egyptianfraction.h

OBJECTS = $(OBJM) simegyptian.o
EXES = sim

all:	$(EXES)

sim:	simegyptian.o $(OBJM)
	$(CC) $(CFLAGS) simegyptian.o $(OBJM) -o sim

$(OBJM):	$(HEADERS)

clean:
	rm -f $(OBJECTS) *~

cleanall:	clean
	rm -f $(EXES)
