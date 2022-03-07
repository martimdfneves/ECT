/******************************************************************
 * Paulo Pedreiras, Oct/2021
 * DETI/UA/IT - Real-Time Operating Systems course
 *
 * Base code for periodic thread execution using clock_nanosleep
 * Assumes that periods and execution times are below 1 second
 *    
 *
 *****************************************************************/
#define _GNU_SOURCE /* Required by sched_setaffinity */
#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <sys/time.h>
#include <string.h>
#include <sched.h> //sched_setscheduler
#include <pthread.h>
#include <errno.h>
#include <signal.h> // Timers
#include <stdint.h>
#include <unistd.h>
#include <math.h>


/* ***********************************************
* App specific defines
* ***********************************************/
#define NS_IN_SEC 1000000000L



#define PERIOD_NS (100*1000*1000) 	// Period (ns component)
#define PERIOD_S (0)				// Period (seconds component)

#define BOOT_ITER 10				// Number of activations for warm-up
                                    // There is an initial transient in which first activations
                                    // often have an irregular behaviour (cache issues, ..)


/* ***********************************************
* Prototypes
* ***********************************************/
void Heavy_Work(void);
struct  timespec TsAdd(struct  timespec  ts1, struct  timespec  ts2);
struct  timespec TsSub(struct  timespec  ts1, struct  timespec  ts2);


/* ***********************************************
* Global variables
* ***********************************************/
uint64_t min_iat, max_iat; // Hold the minium/maximum observed inter arrival time

/* *************************
* Thread_1 code 
* **************************/

void * Thread_1_code(void *arg)
{
    	/* Timespec variables to manage time */
	struct timespec ts, // thread next activation time (absolute)
			ta, 		// activation time of current thread activation (absolute)
			tit, 		// thread inter-arrival time,
			ta_ant, 	// activation time of last instance (absolute),
			tp; 		// Thread period
		
	/* Other variables */
	int niter = 0; 	// Activation counter
	int update; 	// Flag to signal that min/max should be updated
	
	/* Set absolute activation time of first instance */
	tp.tv_nsec = PERIOD_NS;
	tp.tv_sec = PERIOD_S;	
	clock_gettime(CLOCK_MONOTONIC, &ts);
	ts = TsAdd(ts,tp);	
	
	/* Periodic jobs ...*/ 
	while(1) {

		/* Wait until next cycle */
		clock_nanosleep(CLOCK_MONOTONIC, TIMER_ABSTIME,&ts,NULL);
		clock_gettime(CLOCK_MONOTONIC, &ta);		
		ts = TsAdd(ts,tp);		
		
		niter++; // Coount number of activations
		
		/* Compute latency and jitter */
		if( niter == 1) 
		    ta_ant = ta; // Init ta_ant at first activation
	    	
	    	tit=TsSub(ta,ta_ant);  // Compute time since last activation
		
		if( niter == BOOT_ITER) {	// Boot time finsihed. Init max/min variables	    
		      min_iat = tit.tv_nsec;
		      max_iat = tit.tv_nsec;
		      update = 1;
		}else
		if( niter > BOOT_ITER){ 	// Update max/min, if boot time elapsed 	    
		    if(tit.tv_nsec < min_iat) {
		      min_iat = tit.tv_nsec;
		      update = 1;
		    }
		    if(tit.tv_nsec > max_iat) {
		      max_iat = tit.tv_nsec;
		      update = 1;
		    }
		}
		ta_ant = ta; // Update ta_ant
	
		  
  		/* Print maximum/minimum inter-arrival time */
		if(update) {
		  printf("Task %s inter-arrival time: min: %lu / max: %lu\n\r",(char *) arg, min_iat, max_iat);
		  update = 0;
		}
		
		/* Do the actual processing */
		Heavy_Work();		
	}  
  
    return NULL;
}

/* *************************
* main()
* **************************/

int main(int argc, char *argv[])
{
	int err;
	pthread_t threadid;
	pthread_attr_t attr;
	char procname[40]; 
	struct sched_param parm;
	cpu_set_t cpuset;

	/* Process input args */
	if(argc != 3) {
	  printf("Usage: %s PROCNAME PRIORITY, where PROCNAME is a string and PRIORITY is an integer between 0 and 99\n\r", argv[0]);
	  return -1; 
	}

	CPU_ZERO(&cpuset);
	CPU_SET(0,&cpuset);
	if(sched_setaffinity(0, sizeof(cpuset), &cpuset)) {
		printf("\n Lock of process to CPU0 failed!!!");
	}
	
	strcpy(procname, argv[1]);
	int prio = atoi(argv[2]);

	pthread_attr_init(&attr);
	pthread_attr_setinheritsched(&attr,PTHREAD_EXPLICIT_SCHED);
	pthread_attr_setschedpolicy(&attr,SCHED_RR);
	parm.sched_priority = prio;
	pthread_attr_setschedparam(&attr, &parm);
	
	/* Create periodic thread/task */
	err=pthread_create(&threadid, &attr, Thread_1_code, &procname);
 	if(err != 0) {
		printf("\n\r Error creating Thread [%s]", strerror(err));
		return -1;
	}
	else 
		printf("%d",parm.sched_priority);
		while(1); // Ok. Thread shall run
	
	/* Last thing that main() should do */
    	pthread_exit(NULL);
		
	return 0;
}




/* ***********************************************
* Auxiliary functions 
* ************************************************/

// Task load. In the case integrates numerically a function
#define f(x) 1/(1+pow(x,2)) /* Define function to integrate*/
void Heavy_Work(void)
{
	float lower, upper, integration=0.0, stepSize, k;
	int i, subInterval;
	
	struct timespec ts, // Function start time
			tf; 		// Function finish time
	static int first = 0;	// Flag to signal first execution
	
	/* Get start time */
	clock_gettime(CLOCK_MONOTONIC, &ts);
	
	/* Integration parameters */
	/* These values can be tunned to cause a desired load*/
	lower=0;
	upper=100;
	subInterval=470000;

	 /* Calculation */
	 /* Finding step size */
	 stepSize = (upper - lower)/subInterval;

	 /* Finding Integration Value */
	 integration = f(lower) + f(upper);
	 for(i=1; i<= subInterval-1; i++)
	 {
		k = lower + i*stepSize;
		integration = integration + 2 * f(k);
 	}
	integration = integration * stepSize/2;
 	
 	/* Get finish time and show results */
 	if (!first) {
		clock_gettime(CLOCK_MONOTONIC, &tf);
		tf=TsSub(tf,ts);  // Compute time difference form start to finish
 	
		printf("Integration value is: %.3f. It took %9lu ns to compute.\n", integration, tf.tv_nsec);
		
		first = 1;
	}

}


// Adds two timespect variables
struct  timespec  TsAdd(struct  timespec  ts1, struct  timespec  ts2){
	
    struct  timespec  tr;
	
	// Add the two timespec variables
    	tr.tv_sec = ts1.tv_sec + ts2.tv_sec ;
    	tr.tv_nsec = ts1.tv_nsec + ts2.tv_nsec ;
	// Check for nsec overflow	
	if (tr.tv_nsec >= NS_IN_SEC) {
        	tr.tv_sec++ ;
		tr.tv_nsec = tr.tv_nsec - NS_IN_SEC ;
    	}

    return (tr) ;
}

// Subtracts two timespect variables
struct  timespec  TsSub (struct  timespec  ts1, struct  timespec  ts2) {
  struct  timespec  tr;

  // Subtract second arg from first one 
  if ((ts1.tv_sec < ts2.tv_sec) || ((ts1.tv_sec == ts2.tv_sec) && (ts1.tv_nsec <= ts2.tv_nsec))) {
    // Result would be negative. Return 0
    tr.tv_sec = tr.tv_nsec = 0 ;  
  } else {						
	// If T1 > T2, proceed 
        tr.tv_sec = ts1.tv_sec - ts2.tv_sec ;
        if (ts1.tv_nsec < ts2.tv_nsec) {
            tr.tv_nsec = ts1.tv_nsec + NS_IN_SEC - ts2.tv_nsec ;
            tr.tv_sec-- ;				
        } else {
            tr.tv_nsec = ts1.tv_nsec - ts2.tv_nsec ;
        }
    }

    return (tr) ;

}


