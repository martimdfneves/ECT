# makefile para o TDA Fraction
CC = gcc
CFLAGS = -Wall

OBJM = fraction.o
HEADERS = fraction.h

OBJECTS = $(OBJM) simfraction.o
EXES = sfrac

all:	$(EXES)

sfrac:	simfraction.o $(OBJM)
	$(CC) $(CFLAGS) simfraction.o $(OBJM) -o sfrac

$(OBJM):	$(HEADERS)

clean:
	rm -f $(OBJECTS) *~

cleanall:	clean
	rm -f $(EXES)
