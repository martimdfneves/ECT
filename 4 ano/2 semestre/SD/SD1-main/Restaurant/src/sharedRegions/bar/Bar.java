package sharedRegions.bar;
import entities.*;
import main.Logger;
import utils.Order;
import utils.RequestType;
import utils.SimPar;
import java.util.LinkedList;
import java.util.Queue;
import FIFO.*;

/**
 * Where the students enter and wait to be saluted. 
 * Where the waiter salutes the students when they enter, waits for a request, prepares the bill and says goodbye to the students when they leave.
 * @author Martim Neves
 * @author Tiago Dias
 */
public class Bar implements BarWaiter, BarStudent, BarChef{
	/**
	 * The id of the first student inside the restaurant
	 */
    private int firstStudentID;
    
    /**
	 * The id of the last student inside the restaurant
	 */
    private int lastStudentID = -1;
    
    /**
   	 * The number students seated at the table
   	 */
    private int studentsSeat = 0;
    
    /**
   	 * The number of students that already entered the restaurant
   	 */
    private int studentsEntered;
    
    /**
   	 * The number of students that left the restaurant
   	 */
    private int studentsDone;
    
    /**
   	 * The number of times the waiter is requested 
   	 */
    private int waiterIsRequested = 0;
    
    /**
   	 * The number of students at the restaurant door 
   	 */
    private int studentAtDoor = 0;
    
    /**
   	 * The number of portions ready
   	 */
    private int portionReady = 0;
    
    /**
   	 * The current course number
   	 */
    private int courses = 0;
    
    /**
   	 * Checks if it's the first student
   	 */
    private boolean firstStudent = true;
    
    /**
   	 * Checks if the order is done
   	 */
    private boolean orderDone = false;
    
    /**
   	 * Checks if the bill was payed
   	 */
    private boolean payBill = false;
    
    /**
   	 * Checks if all the students left the restaurant
   	 */
    private boolean allStudentsLeft = false;
    
    /**
   	 * An array with all the the students 
   	 */
    private final Student[] students;
    
    /**
   	 * An queue that stores the students that have been saluted by the waiter 
   	 */
    private MemFIFO<Integer> salutedQueue;
    
    /**
   	 * An reference to the general repository
   	 */
    private final Logger logger;   

    /**
     * Queue of requests to be done by the waiter
     */
	private Queue<Order> orderQueue = new LinkedList<>();
	
	/**
     * Constructor
     * 
     * @param repos reference to the general repository
     */
    public Bar(Logger logger){
        students = new Student[SimPar.students_number];
        
        // Initialize the students array with null objects
        for(int i = 0; i < SimPar.students_number; i++){
            students[i] = null;
        }

        // Creates the students saluted queue with the size equals to the number of students
        try {
        	salutedQueue = new MemFIFO<>(new Integer[SimPar.students_number]);
        } catch (MemException e) {
            e.printStackTrace();
        }
        this.logger = logger;
    }

    /**
     * Salutes the client and waits for the student to read the menu.
     * Notifies the student that he has been saluted.
     * Updates waiter state to PRESENTING_THE_MENU.
     */
    @Override
    public synchronized void saluteTheClient(){
        int student_saluted = 0;

        // Salute the client
        try {
            student_saluted = salutedQueue.read();
        } catch (MemException e) {
            e.printStackTrace();
        }

        Waiter waiter = ((Waiter)Thread.currentThread());
        if(waiter.getWaiterState() != WaiterStates.PRESENTING_THE_MENU){
			waiter.setWaiterState(WaiterStates.PRESENTING_THE_MENU);
			logger.updateWaiterState(WaiterStates.PRESENTING_THE_MENU);
		}

        students[student_saluted].setSalutedByWaiter();
        // Notifies the student that he has been saluted
        notifyAll();

        // Set the seat of the student upon being saluted
        students[student_saluted].setTableSeat(this.studentsSeat);
        studentsSeat++;


        // Waits for the student to read the menu
        while(!students[student_saluted].getReadTheMenu()){
            try {
                wait();
            } catch (InterruptedException e) {
            	System.out.println("saluteTheClient - One thread of Waiter was interrupted.");
	            System.exit(1);
            }
        }
    }

    /**
     * Notifies a new order in the requests queue.
     * Update the chef state to DELVERING_THE_PORTION
     */
    @Override
    public synchronized void alertTheWaiter(){
    	Chef chef = ((Chef)Thread.currentThread());
    	
    	Order order = new Order(7, RequestType.COLLECT_PORTIONS);
		orderQueue.add(order);
    	
    	if(chef.getChefState() != ChefStates.DELIVERING_THE_PORTIONS){
			chef.setChefState(ChefStates.DELIVERING_THE_PORTIONS);
			logger.updateChefState(ChefStates.DELIVERING_THE_PORTIONS);
		}

        portionReady++;
        waiterIsRequested++; 
        // Notifies an new order in the queue
        notifyAll();
    }

    /**
     * Waiter returns to the bar.
     * Updates waiter state to APPRAISING_SITUATION.
     */
    @Override
    public synchronized void returningToTheBar(){
    	Waiter waiter = ((Waiter)Thread.currentThread());
		
    	if(waiter.getWaiterState() != WaiterStates.APPRAISING_SITUATION){
			waiter.setWaiterState(WaiterStates.APPRAISING_SITUATION);
			logger.updateWaiterState(WaiterStates.APPRAISING_SITUATION);
		}
    }

    /**
     * Waiter prepares the bill.
     * Updates waiter state to PROCESSING_THE_BILL.
     */
    @Override
    public synchronized void prepareTheBill(){
    	Waiter waiter = ((Waiter)Thread.currentThread());
		
    	if(waiter.getWaiterState() != WaiterStates.PROCESSING_THE_BILL) {
			waiter.setWaiterState(WaiterStates.PROCESSING_THE_BILL);
			logger.updateWaiterState(WaiterStates.PROCESSING_THE_BILL);
		}
    }

    /**
     * Waiter is waiting for a request.
     * Updates waiter state to APPRAISING_SITUATION.
     */
    @Override
    public synchronized Order lookAround(){
    	Waiter waiter = ((Waiter)Thread.currentThread());
    	logger.updateWaiterState(waiter.getWaiterState());

        // Waits for one of the following conditions:
		// - the chef's signal
		// - the entrance of a student
		// - the exit of a student
		// - a call from the first student who sat at the table
		// - a signal from the last student who finished a course
		// - a call by the last student who sat at the table to pay the bill
        while(this.waiterIsRequested == 0){
			try{
				wait();    
	        }catch(InterruptedException e){
	            System.out.println("lookAround - One thread of Waiter was interrupted.");
	            System.exit(1);
	        }
        }
        this.waiterIsRequested--; 

        // The waiter should go salute the students
        if(studentAtDoor > 0){
            studentAtDoor--; 
            return orderQueue.poll();
        }
        // The waiter is needed to get the pad
        else if(orderDone){
            orderDone = false;
            return orderQueue.poll();
        }
        // The waiter goes to collect the portions ready by the chef
        else if(portionReady > 0){
            portionReady--; 
            return orderQueue.poll();
        }
        // The Waiter needs to prepare the bill
        else if(payBill == true){
            payBill = false;
            return orderQueue.poll();
        }
        // The waiter should say goodbye to the students
        else if(this.allStudentsLeft){
        	waiter.setCanGoHome();
            return orderQueue.poll();
        }
        // ERROR case
        else {
        	return new Order(-1, RequestType.NULL);
        }
    }

    /**
     * Waiter says goodbye to students.
     * Updates waiter state to APPRAISING_SITUATION.
     */
    @Override
    public synchronized void sayGoodbye(){
    	Waiter waiter = ((Waiter)Thread.currentThread());
    	
		if(waiter.getWaiterState() != WaiterStates.APPRAISING_SITUATION) {
			waiter.setWaiterState(WaiterStates.APPRAISING_SITUATION);
			logger.updateWaiterState(WaiterStates.APPRAISING_SITUATION);
		}
		
		// If the waiter already said goodbye to all the students he can go home
        waiter.setCanGoHome();
    }

    /**
     * Student signals the waiter to bring the next course.
     * Notifies the waiter that all the people already ate.
     * Updates internal state of the student to CHATTING_WITH_COMPANION.
     */
    @Override
    public synchronized void signalTheWaiter(){
        Student student = ((Student)Thread.currentThread());
        
        if(student.getStudentState() != StudentStates.CHATTING_WITH_COMPANIONS) {
			student.setStudentState(StudentStates.CHATTING_WITH_COMPANIONS);
			logger.updateStudentState(student.getID(), StudentStates.CHATTING_WITH_COMPANIONS);
		}
        
        // If it's the last student to finish eating he will notify the waiter
        if(student.getLastStudent()){
        	student.setLastStudent(false);
            
        	System.out.println("Student"+student.getID()+": was last to eat, will alert the waiter");
            
            // Notifies the waiter
            courses++;
            waiterIsRequested++; 
            notifyAll();
        }
    }

    /**
     * Student calls the waiter when everyone has chosen.
     * Notifies a new order in the requests queue.
     * Updates internal state of the student to ORGANIZING_THE_ORDER.
     */
    @Override
    public synchronized void callTheWaiter(){
    	Student student = ((Student)Thread.currentThread());
    	
    	if(student.getStudentState() != StudentStates.ORGANIZING_THE_ORDER) {
			student.setStudentState(StudentStates.ORGANIZING_THE_ORDER);
			logger.updateStudentState(student.getID(), StudentStates.ORGANIZING_THE_ORDER);
		}
        
        Order order2 = new Order(student.getID(), RequestType.GET_THE_PAD);	
		orderQueue.add(order2);	
        
        orderDone = true;
        waiterIsRequested++;
        // Notifies an new order in the queue
        notifyAll();
    }

    /**
     * Student enters the restaurant, updates internal state of student to TAKING_A_SEAT_AT_THE_TABLE.
     * Notifies the waiter that a student has entered.
     * Adds a request of type SALUTE_THE_CLIENT to the queue.
     */
    @Override
    public synchronized void enter(){
    	Student student = ((Student)Thread.currentThread());
        students[student.getID()] = student;

        Order order1 = new Order(student.getID(), RequestType.SALUTE_THE_CLIENT);
		orderQueue.add(order1);
        
		try {
			salutedQueue.write(student.getID());
            studentsEntered++;
        } catch (MemException e) {
            e.printStackTrace();
        }

        studentAtDoor++; 
        waiterIsRequested++; 
        // Notifies an new order in the queue
        notifyAll();

        // If he's the first student to enter the restaurant
        if(firstStudent){
            firstStudent = false;
            firstStudentID = student.getID();
            System.out.println("Student"+firstStudentID+": was the first to enter the restaurant");
        }
        
        // If he's not the first student means this is the last student
        if(studentsEntered == SimPar.students_number){
            lastStudentID = student.getID();
            System.out.println("Student"+lastStudentID+": was the last to enter the restaurant");
        }
        
        if(student.getStudentState() != StudentStates.TAKING_A_SEAT_AT_THE_TABLE) {
			student.setStudentState(StudentStates.TAKING_A_SEAT_AT_THE_TABLE);
			logger.updateStudentState(student.getID(), StudentStates.TAKING_A_SEAT_AT_THE_TABLE);
		}
        
        // Waits to be saluted by the waiter
        while(!students[student.getID()].getSalutedByWaiter()){
            try {
                wait();
            }catch(InterruptedException e){
	            System.out.println("enter - One thread of Student was interrupted.");
	            System.exit(1);
            }
        }
    }

    /**
     * Checks if the student was the first student to enter the restaurant 
     *
     * @param sID The ID of the student.
     */
    public synchronized boolean FirstStudent(int sID){
        if(firstStudentID == sID){
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Student leaves the restaurant, updates internal state of student to GOING_HOME.
     * Notifies the waiter that a student has left.
     * Adds a request of type SAY_GOODBYE to the queue.
     */
    @Override
    public synchronized void exit(){
    	Student student = ((Student)Thread.currentThread());
    	
    	if(student.getStudentState() != StudentStates.GOING_HOME) {
			student.setStudentState(StudentStates.GOING_HOME);
			logger.updateStudentState(student.getID(), StudentStates.GOING_HOME);
		}
        studentsDone++;

		Order order5 = new Order(student.getID(), RequestType.SAY_GOODBYE);
		orderQueue.add(order5);
        
        if(studentsDone == SimPar.students_number){
            allStudentsLeft = true;
            waiterIsRequested++; 
        	// Notifies an new order in the queue
            notifyAll();
        }
    }

    /**
     * Gets the id of the student who arrived last.
     * Notifies a new order in the requests queue.
     * Updates internal state of the student to PAYING_THE_BILL.
     * 
     * @return boolean if the student was the last to enter the restaurant
     */
    @Override
    public synchronized boolean shouldHaveArrivedEarlier(int sID){
        if(lastStudentID == sID){
        	Student student = ((Student)Thread.currentThread());
        	
        	if(student.getStudentState() != StudentStates.PAYING_THE_BILL) {
    			student.setStudentState(StudentStates.PAYING_THE_BILL);
    			logger.updateStudentState(student.getID(), StudentStates.PAYING_THE_BILL);
    		}
            
            Order order4 = new Order(student.getID(), RequestType.PREPARE_THE_BILL);
    		orderQueue.add(order4);
            
            waiterIsRequested++;
            payBill = true;
            
            // Notifies an new order in the queue
    		notifyAll();
            
            return true; 
        }
        else {
            return false;
        }
    }

    /**
     * Student reads the menu.
     * Notifies the waiter that he has read the menu.
     * Updates internal state of the student to SELECTING_THE_COURSES.
     */
    @Override
    public synchronized void readTheMenu(){
    	Student student = ((Student)Thread.currentThread());
		
		if(student.getStudentState() != StudentStates.SELECTING_THE_COURSES) {
			student.setStudentState(StudentStates.SELECTING_THE_COURSES);
			logger.updateStudentState(student.getID(), StudentStates.SELECTING_THE_COURSES);
		}
	
        students[student.getID()].setReadTheMenu();
        
        // Notifies the waiter that the menu was read
        notifyAll();
    }
}