APPS=cyclicCircConv

all: ${APPS}

%: %.cu
	nvcc -O2 -lm -Wno-deprecated-gpu-targets -o $@ $<
clean:
	rm -f ${APPS}
