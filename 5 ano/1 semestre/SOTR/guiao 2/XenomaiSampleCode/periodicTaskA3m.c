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
#include <stdbool.h>

#include <sys/mman.h> // For mlockall

// Xenomai API (former Native API)
#include <alchemy/task.h>
#include <alchemy/timer.h>
#include <alchemy/cond.h>
#include <alchemy/mutex.h>

#define MS_2_NS(ms)(ms*1000*1000) /* Convert ms to ns */

/* *****************************************************
 * Define task structure for setting input arguments
 * *****************************************************/
 struct taskArgsStruct {
	 RTIME taskPeriod_ns;
	 int some_other_arg;
 };

/* *********************************************** *
 * Define Task, mutex's and condition variables attributes *
 * *********************************************** */ 
#define TASK_MODE 0  	// No flags
#define TASK_STKSZ 0 	// Default stack size
#define TASK_A_PRIO 25 	// RT priority [0..99]
#define TASK_B_PRIO 50 	// RT priority [0..99]
#define TASK_C_PRIO 99 	// RT priority [0..99]
#define TASK_A_PERIOD_NS MS_2_NS(1000)

/** Task decriptors **/ 
RT_TASK task_a_desc;
RT_TASK task_b_desc;
RT_TASK task_c_desc;

/** Condition variables decriptors **/ 
RT_COND cond_b_desc;
RT_COND cond_c_desc;

/** Mutex decriptors **/ 
RT_MUTEX mutex_b_desc;
RT_MUTEX mutex_c_desc;

/** Variable to confirm sequence execute **/
int exec_order = 0;

/* *********************
* Function prototypes
* **********************/
void catch_signal(int sig); 	/* Catches CTRL + C to allow a controlled termination of the application */
void wait_for_ctrl_c(void);
void Heavy_Work(void);      	/* Load task */
void task_a_code(void *args); 	/* Task A body */
void task_b_code(void *args); 	/* Task B body */
void task_c_code(void *args); 	/* Task C body */

/* ***********************************************
* Global variables
* ***********************************************/
RTIME min_iat, max_iat; // Hold the minium/maximum observed inter arrival time
int update;
cpu_set_t cpuset;
int seq_num;
/** Task condition variable necessary if broadcast used **/ 
bool condition_b, condition_c;

/* ******************
* Main function
* *******************/ 
int main(int argc, char *argv[]) {
	int err,err1,err2,err3,ret1,ret2,ret3,sem1;
	struct taskArgsStruct taskAArgs;
	struct taskArgsStruct taskBArgs;
	struct taskArgsStruct taskCArgs;
	
	/* Lock memory to prevent paging */
	mlockall(MCL_CURRENT|MCL_FUTURE); 

	CPU_ZERO(&cpuset);
	CPU_SET(0, &cpuset);
	
	/** Initialize condition variables **/ 
	condition_b, condition_c = false;

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

	/** Create mutex's and condition variables **/
	err = rt_mutex_create(&mutex_b_desc, NULL);
	if(err)
		{ printf("Error creating mutex_b (error code = %d)\n", err); return err; }

	err = rt_mutex_create(&mutex_c_desc, NULL);
	if(err)
		{ printf("Error creating mutex_c (error code = %d)\n", err); return err; }
	
	err = rt_cond_create(&cond_b_desc, NULL);
	if(err)
		{ printf("Error creating cond_b (error code = %d)\n", err); return err; }

	err = rt_cond_create(&cond_c_desc, NULL);
	if(err)
		{ printf("Error creating cond_c (error code = %d)\n", err); return err; }
			
	/* Start RT task */
	/* Args: task decriptor, address of function/implementation and argument*/
	taskAArgs.taskPeriod_ns = TASK_A_PERIOD_NS; 	
    rt_task_start(&task_a_desc, &task_a_code, (void *)&taskAArgs);

	taskBArgs.taskPeriod_ns = TASK_A_PERIOD_NS; 	
    rt_task_start(&task_b_desc, &task_b_code, (void *)&taskBArgs);

	taskCArgs.taskPeriod_ns = TASK_A_PERIOD_NS; 	
    rt_task_start(&task_c_desc, &task_c_code, (void *)&taskCArgs);


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
void task_a_code(void *args) {
	RT_TASK *curtask;
	RT_TASK_INFO curtaskinfo;
	struct taskArgsStruct *taskArgs;

	RTIME ta=0;
	RTIME ta_ant=0;
	unsigned long overruns;
	int err;
	
	/* Get task information */
	curtask=rt_task_self();
	rt_task_inquire(curtask,&curtaskinfo);
	taskArgs=(struct taskArgsStruct *)args;
	printf("Task %s init, period:%llu\n", curtaskinfo.name, taskArgs->taskPeriod_ns);
		
	/* Set task as periodic */
	err=rt_task_set_periodic(NULL, TM_NOW, taskArgs->taskPeriod_ns);
	for(;;) {
		err=rt_task_wait_period(&overruns);
		ta=rt_timer_read();
		if(err) {
			printf("task %s overrun!!!\n", curtaskinfo.name);
			break;
		}
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

		printf("Task %s: %d\n", curtaskinfo.name, exec_order);
		exec_order += 1;

		err = rt_mutex_acquire_timed(&mutex_b_desc, NULL);
		if(err)
			{ printf("Error locking mutex_b (error code = %d)\n", err); return; }
		condition_b = true;
		err = rt_cond_signal(&cond_b_desc);
		if(err)
			{ printf("Error signaling cond_b (error code = %d)\n", err); return; }
		err = rt_mutex_release(&mutex_b_desc);
		if(err)
			{ printf("Error unlocking mutex_b (error code = %d)\n", err); return; }
	}

	return;
}

void task_b_code(void *args) {
	RT_TASK *curtask;
	RT_TASK_INFO curtaskinfo;
	struct taskArgsStruct *taskArgs;

	RTIME ta=0;
	RTIME ta_ant=0;
	unsigned long overruns;
	int err;

	/* Get task information */
	curtask=rt_task_self();
	rt_task_inquire(curtask,&curtaskinfo);
	taskArgs=(struct taskArgsStruct *)args;
	printf("Task %s init, period:%llu\n", curtaskinfo.name, taskArgs->taskPeriod_ns);
		
	/* Set task as periodic */
	err=rt_task_set_periodic(NULL, TM_NOW, taskArgs->taskPeriod_ns);
	for(;;) {

		/** Wait until signal from task A **/
		err = rt_mutex_acquire_timed(&mutex_b_desc, NULL);
		if(err)
			{ printf("Error locking mutex_b (error code = %d)\n", err); return; }
		while(!condition_b) {
			err = rt_cond_wait_timed(&cond_b_desc, &mutex_b_desc, NULL);
			if(err)
				{ printf("Error receving signal to cond_b (error code = %d)\n", err); return; }
		}
		condition_b = false;
		err = rt_mutex_release(&mutex_b_desc);
		if(err)
			{ printf("Error unlocking mutex_b (error code = %d)\n", err); return; }
		

		err=rt_task_wait_period(&overruns);
		ta=rt_timer_read();
		if(err) {
			printf("task %s overrun!!!\n", curtaskinfo.name);
			break;
		}
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

		printf("Task %s: %d\n", curtaskinfo.name, exec_order);
		exec_order += 1;

		err = rt_mutex_acquire_timed(&mutex_c_desc, NULL);
		if(err)
			{ printf("Error locking mutex_c (error code = %d)\n", err); return; }
		condition_c = true;
		err = rt_cond_signal(&cond_c_desc);
		if(err)
			{ printf("Error signaling cond_c (error code = %d)\n", err); return; }
		err = rt_mutex_release(&mutex_c_desc);
		if(err)
			{ printf("Error unlocking mutex_c (error code = %d)\n", err); return; }

	}

	return;
}

void task_c_code(void *args) {
	RT_TASK *curtask;
	RT_TASK_INFO curtaskinfo;
	struct taskArgsStruct *taskArgs;

	RTIME ta=0;
	RTIME ta_ant=0;
	unsigned long overruns;
	int err;

	/* Get task information */
	curtask=rt_task_self();
	rt_task_inquire(curtask,&curtaskinfo);
	taskArgs=(struct taskArgsStruct *)args;
	printf("Task %s init, period:%llu\n", curtaskinfo.name, taskArgs->taskPeriod_ns);
		
	/* Set task as periodic */
	err=rt_task_set_periodic(NULL, TM_NOW, taskArgs->taskPeriod_ns);
	for(;;) {

		/** Wait until signal from task B **/
		err = rt_mutex_acquire_timed(&mutex_c_desc, NULL);
		if(err)
			{ printf("Error signaling cond_c (error code = %d)\n", err); return; }
		while(!condition_c) {
			err = rt_cond_wait_timed(&cond_c_desc, &mutex_c_desc, NULL);
			if(err)
				{ printf("Error signaling cond_c (error code = %d)\n", err); return; }
		}
		condition_c = false;
		err = rt_mutex_release(&mutex_c_desc);
		if(err)
			{ printf("Error unlocking mutex_c (error code = %d)\n", err); return; }

		err=rt_task_wait_period(&overruns);
		ta=rt_timer_read();
		if(err) {
			printf("task %s overrun!!!\n", curtaskinfo.name);
			break;
		}
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

		printf("Task %s: %d\n", curtaskinfo.name, exec_order);
		exec_order += 1;
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


