CC = gcc
CFLAGS = -Wall

LK = gcc
LDFLAGS = -pthread -lm

TARGETS = bounded_buffer_unsafe bounded_buffer_bwsafe bounded_buffer_safe

objs = fifo_unsafe.o bounded_buffer.o delays.o

all:	$(TARGETS)

bounded_buffer_unsafe:	fifo_unsafe.o bounded_buffer.o delays.o thread.o
		$(LK) -o $@ $^ $(LDFLAGS)

bounded_buffer_bwsafe:	fifo_bwsafe.o bounded_buffer.o delays.o thread.o
		$(LK) -o $@ $^ $(LDFLAGS)

bounded_buffer_safe:	fifo_safe.o bounded_buffer.o delays.o thread.o
		$(LK) -o $@ $^ $(LDFLAGS)

$(objs):	%.o:	%.cpp fifo.h
		$(CC) $(CFLAGS) -c -o $@ $<

clean:
		rm -f *.o *~

cleanall:	clean
		rm -f $(TARGETS)
