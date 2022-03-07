CC =  gcc # Path to compiler
L_FLAGS = -lrt -lpthread -lm
#C_FLAGS = -g

all: pt
.PHONY: all

# Project compilation
pt: periodicTask.c
	$(CC) $< -o $@ $(C_FLAGS) $(L_FLAGS)

	
.PHONY: clean 

clean:
	rm -f *.c~ 
	rm -f *.o
	rm pt

# Some notes
# $@ represents the left side of the ":"
# $^ represents the right side of the ":"
# $< represents the first item in the dependency list   

