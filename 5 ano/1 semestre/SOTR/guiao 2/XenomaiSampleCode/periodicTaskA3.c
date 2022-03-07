/* ************************************************************
* Xenomai - creates a periodic task
*	
* Paulo Pedreiras
* 	Out/2020: Upgraded from Xenomai V2.5 to V3.1    
* 
************************************************************** */

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <signal.h>
#include <math.h>

#include <sys/mman.h> // For mlockall

// Xenomai API (former Native API)
#include <alchemy/task.h>
#include <alchemy/sem.h>
#include <alchemy/timer.h>

#define MS_2_NS(ms)(ms*1000*1000) /* Convert ms to ns */

/* *****************************************************
 * Define task structure for setting input arguments
 * *****************************************************/
 struct taskArgsStruct {
	 RTIME taskPeriod_ns;
	 int some_other_arg;
 };

/* *******************
 * Task and semaphore attributes 
 * *******************/ 
#define TASK_MODE 0  	// No flags
#define TASK_STKSZ 0 	// Default stack size
#define SEM_MODE 0		// No flags

#define TASK_A_PRIO 99 	// RT priority [0..99]
#define TASK_B_PRIO 50 	// RT priority [0..99]
#define TASK_C_PRIO 0 	// RT priority [0..99]
#define TASK_A_PERIOD_NS MS_2_NS(1000)

RT_TASK task_a_desc,task_b_desc,task_c_desc; // Task decriptors
RT_SEM sem1,sem2;   // Semaphore descriptor


/* *********************
* Function prototypes
* **********************/
void catch_signal(int sig); 	/* Catches CTRL + C to allow a controlled termination of the application */
void wait_for_ctrl_c(void);
void Heavy_Work(void);      	/* Load task */
void task_code(void *args); 	/* Task body */

/* ***********************************************
* Global variables
* ***********************************************/
int seq_num=0;
unsigned long icount = 0; //semphore creation variable

/* ******************
* Main function
* *******************/ 
int main(int argc, char *argv[]) {
	int err1,err2,err3,ret1,ret2,ret3; 
	struct taskArgsStruct taskAArgs,taskBArgs,taskCArgs;
	cpu_set_t cpuset;
	
	/* Lock memory to prevent paging */
	mlockall(MCL_CURRENT|MCL_FUTURE); 

	CPU_ZERO(&cpuset);
	CPU_SET(0, &cpuset);

	/* Create RT task */
	/* Args: descriptor, name, stack size, priority [0..99] and mode (flags for CPU, FPU, joinable ...) */
	err1=rt_task_create(&task_a_desc, "1", TASK_STKSZ, TASK_A_PRIO, TASK_MODE);
	if(err1) {
		printf("Error creating task 1 (error code = %d)\n",err1);
		return err1;
	} else 
		printf("Task 1 created successfully\n");

	err2=rt_task_create(&task_b_desc, "2", TASK_STKSZ, TASK_B_PRIO, TASK_MODE);
	if(err2) {
		printf("Error creating task 2 (error code = %d)\n",err2);
		return err2;
	} else 
		printf("Task 2 created successfully\n");

	err3=rt_task_create(&task_c_desc, "3", TASK_STKSZ, TASK_C_PRIO, TASK_MODE);
	if(err3) {
		printf("Error creating task 3 (error code = %d)\n",err3);
		return err3;
	} else 
		printf("Task 3 created successfully\n");

	

	//Create RT semaphores
	//Args: semaphore descriptor, name, icount and mode(PRIO, FIFO, etc)
	rt_sem_create(&sem1, "sem1", icount, SEM_MODE);
	rt_sem_create(&sem2, "sem2", icount, SEM_MODE);
			
	/* Start RT task */
	/* Args: task decriptor, address of function/implementation and argument*/
	taskAArgs.taskPeriod_ns = TASK_A_PERIOD_NS; 
    rt_task_start(&task_a_desc, &task_code, (void *)&taskAArgs);

	taskBArgs.taskPeriod_ns = TASK_A_PERIOD_NS; 
    rt_task_start(&task_b_desc, &task_code, (void *)&taskBArgs);

	taskCArgs.taskPeriod_ns = TASK_A_PERIOD_NS; 	
    rt_task_start(&task_c_desc, &task_code, (void *)&taskCArgs);

	/* Forcing tasks to execute on CPU0 */
	/* Args: descriptor, cpuset */
	ret1 = rt_task_set_affinity(&task_a_desc, &cpuset);
	if (ret1) {
		printf("Error setting affinity for task 1 (error code = %d)\n",ret1);
		return ret1;
	}

	ret2 = rt_task_set_affinity(&task_b_desc, &cpuset);
	if (ret2) {
		printf("Error setting affinity for task 2 (error code = %d)\n",ret2);
		return ret2;
	}

	ret3 = rt_task_set_affinity(&task_c_desc, &cpuset);
	if (ret3) {
		printf("Error setting affinity for task 3 (error code = %d)\n",ret3);
		return ret3;
	}
    
	/* wait for termination signal */	
	wait_for_ctrl_c();

	return 0;
		
}

/* ***********************************
* Task body implementation
* *************************************/
void task_code(void *args) {
	RT_TASK *curtask;
	RT_TASK_INFO curtaskinfo;
	struct taskArgsStruct *taskArgs;

	RTIME min_iat, max_iat; // Hold the minium/maximum observed inter arrival time
	int update=0;
	RTIME ta=0;
	RTIME ta_ant=0;
	unsigned long overruns;
	int err,errp,errv;
	
	/* Get task information */
	curtask=rt_task_self();
	rt_task_inquire(curtask,&curtaskinfo);
	taskArgs=(struct taskArgsStruct *)args;
	printf("Task %s init, period:%llu\n", curtaskinfo.name, taskArgs->taskPeriod_ns);
		
	/* Set task as periodic */
	if(strcmp(curtaskinfo.name,"1")==0){
		err=rt_task_set_periodic(NULL, TM_NOW, taskArgs->taskPeriod_ns);
	}
	for(;;) {

		if(strcmp(curtaskinfo.name,"2")==0){
			errp = rt_sem_p(&sem1,TM_INFINITE);
        	if(errp) {
            	printf("Error acquiring semaphore %d\n",errp);
            	return;
        	}
		}
		if(strcmp(curtaskinfo.name,"3")==0){
			errp = rt_sem_p(&sem2,TM_INFINITE);
        	if(errp) {
            	printf("Error acquiring semaphore %d\n",errp);
            	return;
        	}
		}
		if(strcmp(curtaskinfo.name,"1")==0){
			err=rt_task_wait_period(&overruns);
			if(err) {
				printf("task %s overrun!!!\n", curtaskinfo.name);
				break;
			}
		}
		ta=rt_timer_read();
		printf("Task %s activation at time %llu\n", curtaskinfo.name,ta);
		
		if (ta_ant == 0){
			min_iat = ta - ta_ant;
			ta_ant = ta;
			max_iat = ta - ta_ant;
		}
		else{
			if (ta - ta_ant < min_iat){
				min_iat = ta - ta_ant;
				update = 1;
			}
			if (ta - ta_ant > max_iat){
				max_iat = ta - ta_ant;
				update = 1;
			}
		}

		ta_ant = ta;

		if(update) {
			printf("Task %s inter-arrival time: min: %llu / max: %llu\n\r",curtaskinfo.name, min_iat, max_iat);
			update = 0;
		}
		
		/* Task "load" */
		Heavy_Work();

		printf("Task %s seq: %d\n\r", curtaskinfo.name, seq_num);
        seq_num += 1;

		if(strcmp(curtaskinfo.name,"1")==0){
			errv = rt_sem_v(&sem1);
        	if(errv) {
            	printf("Error releasing semaphore %d\n",errv);
            	return;
        	}
		}
		if(strcmp(curtaskinfo.name,"2")==0){
			errv = rt_sem_v(&sem2);
        	if(errv) {
            	printf("Error releasing semaphore %d\n",errv);
            	return;
        	}
		}
		
	}
	return;
}


/* **************************************************************************
 *  Catch control+c to allow a controlled termination
 * **************************************************************************/
 void catch_signal(int sig)
 {
	 return;
 }

void wait_for_ctrl_c(void) {
	signal(SIGTERM, catch_signal); //catch_signal is called if SIGTERM received
	signal(SIGINT, catch_signal);  //catch_signal is called if SIGINT received

	// Wait for CTRL+C or sigterm
	pause();
	
	// Will terminate
	printf("Terminating ...\n");
}


/* **************************************************************************
 *  Task load implementation. In the case integrates numerically a function
 * **************************************************************************/
#define f(x) 1/(1+pow(x,2)) /* Define function to integrate*/
void Heavy_Work(void)
{
	float lower, upper, integration=0.0, stepSize, k;
	int i, subInterval;
	
	RTIME ts, // Function start time
		  tf; // Function finish time
			
	static int first = 0; // Flag to signal first execution		
	
	/* Get start time */
	ts=rt_timer_read();
	
	/* Integration parameters */
	/*These values can be tunned to cause a desired load*/
	lower=0;
	upper=100;
	subInterval=1000000;

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
		tf=rt_timer_read();
		tf-=ts;  // Compute time difference form start to finish
 	
		printf("Integration value is: %.3f. It took %9llu ns to compute.\n", integration, tf);
		
		first = 1;
	}

}


