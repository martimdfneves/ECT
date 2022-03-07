# Standard Makefile to buiild Xenomai 3 applications, 
#   with alchemy skin (former native skin)
# Use xeno-config to get the correct compile and link flags
# Don't forget to specify the skin
XENO_CONFIG := /usr/xenomai/bin/xeno-config
CFLAGS := $(shell $(XENO_CONFIG) --skin=alchemy --cflags)
LDFLAGS := $(shell $(XENO_CONFIG) --skin=alchemy --ldflags)
# Add -lm if math functions are necessary 
LDFLAGS += -lm  
CC := $(shell $(XENO_CONFIG) --cc)

EXECUTABLE_A2 := periodicTaskA2
EXECUTABLE_A3 := periodicTaskA3

all: $(EXECUTABLE_A2) $(EXECUTABLE_A3)

%: %.c
	$(CC) -o $@ $< $(CFLAGS) $(LDFLAGS) 
	
clean:	
	rm $(EXECUTABLE_A2)
	rm $(EXECUTABLE_A3)	
