#
# LSD.TOS, April 2018 (DO NOT REMOVE THIS LINE)
#
# Makefile for the c_code directory.
#

SHELL := /bin/bash

FILES  =
FILES += lsd_term.c
FILES += screen_capture.c
FILES += pseudo_random_generator.c


clean:
	@rm -f lsd_term screen_capture pseudo_random_generator heavy.vhd light.vhd *.ppm


files:
	@for f in makefile $(FILES); do readlink -f $$f; done


bin:				../bin/lsd_term ../bin/screen_capture


lsd_term:			lsd_term.c
	@cc -Wall -O2 -march=native lsd_term.c -o lsd_term

../bin/lsd_term:			lsd_term.c
	@cc -Wall -O2 -march=native lsd_term.c -o ../bin/lsd_term
	@chmod 500 ../bin/lsd_term


screen_capture:			screen_capture.c
	@cc -Wall -O2 -march=native screen_capture.c -o screen_capture

../bin/screen_capture:			screen_capture.c
	@cc -Wall -O2 -march=native screen_capture.c -o ../bin/screen_capture
	@chmod 500 ../bin/screen_capture


pseudo_random_generator:	pseudo_random_generator.c
	@cc -Wall -O2 -march=native pseudo_random_generator.c -o pseudo_random_generator

heavy.vhd:			pseudo_random_generator
	@rm -f heavy.vhd
	@./pseudo_random_generator -heavy 48 1 >heavy.vhd

light.vhd:			pseudo_random_generator
	@rm -f light.vhd
	@./pseudo_random_generator -light 48 1 >light.vhd
