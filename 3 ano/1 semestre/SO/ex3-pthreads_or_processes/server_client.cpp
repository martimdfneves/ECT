#include <stdio.h>
#include <unistd.h>
#include <stdbool.h>
#include <errno.h>
#include <pthread.h>
#include <stdint.h>
#include <string>

#include "fifo.h"
#include "delays.h"
#include "thread.h"
#include "fifo.cpp"

typedef struct ServiceRequest{ 
    
    std::string req;
} ServiceRequest;

typedef struct ServiceResponse{ 
    
    int chars;
    int letters;
    int nums;
} ServiceResponse;

void main() {


}

void callService(ServiceRequest & req, ServiceResponse & res){


}

void processService(){


}

void insert(FIFO * fifo, uint32_t id){


}

uint32_t retrieve(FIFO * fifo){


}

void signalResponseIsAvailable(uint32_t id){


}

void waitForResponse(uint32_t id){


}