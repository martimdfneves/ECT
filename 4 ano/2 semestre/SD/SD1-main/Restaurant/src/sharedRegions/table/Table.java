package sharedRegions.table;
import entities.*;
import main.Logger;
import utils.SimPar;

import java.util.concurrent.ThreadLocalRandom;

import FIFO.*;

/**
 * Where the waiter gets the pad, delivers the portions and presents the bill.
 * Where the students read the menu, inform the companion, prepare the order, add the choices, call and signal the waiter, describe the order, talk, eat and pay the bill.
 * @author Martim Neves
 * @author Tiago Dias
 */
public class Table implements TableStudent, TableWaiter{
	 
	/**
   	 * Checks if the order has been described
   	 */
    private boolean orderDescribed = false;
    
    /**
   	 * Checks if everybody just finished eating
   	 */
    public boolean everybodyFinished = false;
    
    /**
   	 * Checks if the first student joined the talk
   	 */
    private boolean firstStudentJoinedTalk = false;
    
    /**
   	 * Checks if the bill is ready
   	 */
    private boolean billIsReady = false;
    
    /**
   	 * Checks if the bill is paid
   	 */
    private boolean billIsPaid = false;
    
    /**
   	 * The number of students that already choose their course
   	 */
    private int studentSelectedCourses;
    
    /**
   	 * The number of students already served
   	 */
    private int studentServed = 0;
    
    /**
   	 * The number of students that finished eating
   	 */
    private int studentFinishedEating;
    
    /**
   	 * The number of students waiting to be served
   	 */
    private int studentWaiting = 0;
    
    /**
   	 * An array with all the the students 
   	 */
    private final Student[] students;
    
    /**
   	 * An queue that stores the students that are waiting to be served
   	 */
    private MemFIFO<Integer> waitingQueue;
    
    /**
   	 * An reference to the general repository
   	 */
    private final Logger logger;     

    /**
     * Constructor
     * 
     * @param repos reference to the general repository
     */
    public Table(Logger logger){
        students = new Student[SimPar.students_number];
        
        // Initialize the students array with null objects
        for(int i=0; i<SimPar.students_number;i++){
            students[i] = null;
        }

        try {
        	waitingQueue = new MemFIFO<>(new Integer[SimPar.students_number]);
        } catch (MemException e) {
            e.printStackTrace();
        }
        this.logger = logger;
    } 

    /**
     * Waiter gets the pad and waits for the student to describe the order.
     * Waiter notifies the student that he has the pad.
     * Updates internal state of the waiter to TAKING_THE_ORDER.
     */
    @Override
    public synchronized void getThePad(){
    	Waiter waiter = ((Waiter)Thread.currentThread());
	
    	if(waiter.getWaiterState() != WaiterStates.TAKING_THE_ORDER) {
    		waiter.setWaiterState(WaiterStates.TAKING_THE_ORDER);
    		logger.updateWaiterState(WaiterStates.TAKING_THE_ORDER);
    	}

    	// Waits for the student to describe him the order
        while(!orderDescribed){
            try {
                wait();
            } catch(InterruptedException e){
	            System.out.println("getThePad - One thread of Waiter was interrupted.");
	            System.exit(1);
	        }
        }
    }

    /**
     * Waits while there's no one to be served.
     * When a student is waiting to be served the waiter serves and notifies him.
     */
    @Override
    public synchronized void deliverPortion(){
        int student_served = 0;

        // Wait while there's no one to be served
        while(studentWaiting == 0){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        studentWaiting--;
        
        // Serves the student 
        try {
            student_served = waitingQueue.read();
        } catch (MemException e) {
        	System.out.println("deliverPortion - No one to be served.");
            System.exit(1);
        }

        students[student_served].setServedByWaiter(true);
        // Notifies the student that he has been served
        notifyAll();
    }

    /**
     * Checks if all the clients have been served.
     * Updates internal state of the waiter to WAITING_FOR_PORTION.
     * @return if all the clients have been served.
     */
    @Override
    public synchronized boolean haveAllClientsBeenServed(){
    	Waiter waiter = ((Waiter)Thread.currentThread());
    	
		if(waiter.getWaiterState() != WaiterStates.WAITING_FOR_PORTION) {
			waiter.setWaiterState(WaiterStates.WAITING_FOR_PORTION);
			logger.updateWaiterState(WaiterStates.WAITING_FOR_PORTION);
		}
        
        if(studentServed == SimPar.students_number){
            studentServed=0;
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Waiter presents the bill and waits for the student to pay.
     * Waiter notifies the student that the bill is ready.
     * Updates internal state of the waiter to RECEIVING_PAYMENT.
     */
    @Override
    public synchronized void presentTheBill(){
    	 Waiter waiter = ((Waiter)Thread.currentThread());
 		
         if(waiter.getWaiterState() != WaiterStates.RECEIVING_PAYMENT) {
 			waiter.setWaiterState(WaiterStates.RECEIVING_PAYMENT);
 			logger.updateWaiterState(WaiterStates.RECEIVING_PAYMENT);
 		}
         
    	billIsReady = true;
        // Notifies the student that the bill is ready
        notifyAll();

        // waits for the student to pay the bill
        while(!billIsPaid){
            try {
                wait();
            } catch(InterruptedException e){
	            System.out.println("presentTheBill - One thread of Waiter was interrupted.");
	            System.exit(1);
            }
        }
    }

    /**
     * Student informs companion of his course choice.
     * Notifies the companion that he has chosen.
     * Updates internal state of the student to CHATTING_WITH_COMPANIONS.
     */
    @Override
    public synchronized void informCompanion(){
    	Student student = ((Student)Thread.currentThread());
    	
		if(student.getStudentState() != StudentStates.CHATTING_WITH_COMPANIONS) {
			students[student.getID()] = student;
			students[student.getID()].setStudentState(StudentStates.CHATTING_WITH_COMPANIONS);
			logger.updateStudentState(student.getID(), StudentStates.CHATTING_WITH_COMPANIONS);
		}
        
        studentSelectedCourses++;
        // Notifies the first student about his order
        notifyAll();
        
        // Wait until the course is served 
        while(!firstStudentJoinedTalk){
            try {
                wait();
            }catch(InterruptedException e){
	            System.out.println("informCompanion - One thread of Student was interrupted.");
	            System.exit(1);
            }
        }
    }

    /**
     * Increases the number of requests received by the first student.
     * Updates internal state of the student to ORGANIZING_THE_ORDER.
     */
    @Override
    public synchronized void prepareTheOrder(){
    	Student student = ((Student)Thread.currentThread());
		
    	if(student.getStudentState() != StudentStates.ORGANIZING_THE_ORDER) {
			student.setStudentState(StudentStates.ORGANIZING_THE_ORDER);
			logger.updateStudentState(student.getID(), StudentStates.ORGANIZING_THE_ORDER);
		}
    	
        studentSelectedCourses++;
    }
    
    /**
     * Student chats with his companions.
     * Updates internal state of the student to CHATTING_WITH_COMPANIONS.
     */
    @Override
    public synchronized void joinTheTalk(){
		Student student = ((Student)Thread.currentThread());
		
		if(student.getStudentState() != StudentStates.CHATTING_WITH_COMPANIONS) {
			students[student.getID()] =  student;
			students[student.getID()].setStudentState(StudentStates.CHATTING_WITH_COMPANIONS);
			logger.updateStudentState(student.getID(), StudentStates.CHATTING_WITH_COMPANIONS);
		}
    	        
        firstStudentJoinedTalk = true;
        // Notifies that he's joining the talk
        notifyAll();
    }

    /**
     * Updates internal state of the student to CHATTING_WITH_COMPANIONS.
     * Waits for everybody to finished eating.
     */
    @Override
    public synchronized void hasEverbodyFinished(){
    	Student student = ((Student)Thread.currentThread());
    	
    	if(student.getStudentState() != StudentStates.CHATTING_WITH_COMPANIONS) {
			student.setStudentState(StudentStates.CHATTING_WITH_COMPANIONS);
		}
        
    	// Waits for everybody to finished eating
    	while(!this.everybodyFinished) {
            try {
                wait();
            }catch(InterruptedException e){
	            System.out.println("hasEverbodyFinished - One thread of Student was interrupted.");
	            System.exit(1);
            }
        }
    }

    /**
     * Students wait until everyone is served and then start eating.
     * Updates internal state of the student to ENJOYING_THE_MEAL.
     */
    @Override
    public synchronized void startEating(){
    	Student student = ((Student)Thread.currentThread());
		
		if(student.getStudentState() != StudentStates.ENJOYING_THE_MEAL) {
			student.setStudentState(StudentStates.ENJOYING_THE_MEAL);
			logger.updateStudentState(student.getID(), StudentStates.ENJOYING_THE_MEAL);
		}
	     
		try {
			Thread.sleep(ThreadLocalRandom.current().nextInt(1, 20));
        } catch (InterruptedException e) {
            System.out.println("startEating - One thread of Student was interrupted.");
            System.exit(1);
        }
    }

    /**
     * Updates internal state of the student to CHATTING_WITH_COMPANIONS.
     */
    @Override
    public synchronized void endEating(){
        Student student = ((Student)Thread.currentThread());
        
        if(student.getStudentState() != StudentStates.CHATTING_WITH_COMPANIONS) {
			student.setStudentState(StudentStates.CHATTING_WITH_COMPANIONS);
			logger.updateStudentState(student.getID(), StudentStates.CHATTING_WITH_COMPANIONS);
		}
        
        studentFinishedEating++;
        students[student.getID()].setServedByWaiter(false);

        if(studentFinishedEating == SimPar.students_number){
        	student.setLastStudent(true);
            everybodyFinished = true;
            studentFinishedEating = 0;
            // Notifies that everybody just finished eating
            notifyAll();
        }
    }

    /**
     * Student waits until the bill is ready and then pays it.
     * Notifies the waiter that the bill is payed.
     */
    @Override
    public synchronized void honourTheBill(){
    	// Waits for the bill
        while(!billIsReady){
            try {
                wait();
            } catch (InterruptedException e) {
            	System.out.println("honourTheBill - One thread of Student was interrupted.");
 	            System.exit(1);
            }
        }

        billIsPaid = true;
        // Notifies the waiter that the bill has been payed
        notifyAll();
    }

    /**
     * Student waits to be informed by his companions and increases the number of requests received by the first student.
     * Updates internal state of the student to ORGANIZING_THE_ORDER.
     */
    @Override
    public synchronized void addUpOnesChoice(){
    	Student student = ((Student)Thread.currentThread());
		
    	if(student.getStudentState() != StudentStates.ORGANIZING_THE_ORDER) {
			student.setStudentState(StudentStates.ORGANIZING_THE_ORDER);
			logger.updateStudentState(student.getID(), StudentStates.ORGANIZING_THE_ORDER);
		}
		        
    	// waits for another student to inform him
        while(studentSelectedCourses < SimPar.students_number){
            try {
                wait();
            } catch (InterruptedException e) {
            	System.out.println("addUpOnesChoice - One thread of student was interrupted.");
	            System.exit(1);
            }
        }
    }

    /**
     * Checks if everybody has chosen.
     * @return if everybody has chosen.
     */
    @Override
    public synchronized boolean hasEverybodyChosen(){
        if(studentSelectedCourses == SimPar.students_number){
            return true;
        }
        return false;
    }

    /**
     * Student calls the waiter when everyone has chosen and waits for the waiter to get the pad.
     * Notifies the waiter that the order has been described.
     * Updates internal state of the student to ORGANIZING_THE_ORDER.
     */
    @Override
    public synchronized void describeTheOrder(){
    	Student student = ((Student)Thread.currentThread());
    	
		if(student.getStudentState() != StudentStates.ORGANIZING_THE_ORDER) {
			student.setStudentState(StudentStates.ORGANIZING_THE_ORDER);
			logger.updateStudentState(student.getID(), StudentStates.ORGANIZING_THE_ORDER);
		}
		
		orderDescribed = true;
		// Notifies the waiter that the order has been described
		notifyAll();
    }

    /**
     * The student waits to be served.
     * @param sID the student ID
     */
    public synchronized void waitingToBeServed(int sID){
        // Add the student to the waiting to be served queue 
        try {
        	waitingQueue.write(sID);
        } catch (MemException e) {
            e.printStackTrace();
        }
        
        studentWaiting++;
        // Notifies the waiter that he's waiting to be served 
        notifyAll();
        
        // Waits while he's not served
        while(!students[sID].servedByWaiter()){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        everybodyFinished = false;
        studentServed++;
        logger.setNPortion(1);
    }
}
