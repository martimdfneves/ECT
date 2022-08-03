package serverSide.sharedRegions;

import serverSide.entities.*;
import commInfra.*;
import serverSide.main.SimPar;
import serverSide.stubs.*;;

/**
 * Where the students enter and wait to be saluted. 
 * Where the waiter salutes the students when they enter, waits for a request, prepares the bill and says goodbye to the students when they leave.
 * @author Martim Neves
 * @author Tiago Dias
 */
public class Bar extends Thread{
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
    private MemFIFO<Integer> queue;
    
    /**
   	 * A reference to the stub of the general repository
   	 */
    private final GeneralReposStub repos;   

    /**
     * Constructor
     * @param repos reference to the stub of the logger
     */
    public Bar(GeneralReposStub repos){
        students = new Student[SimPar.STUDENTS];
        
        for(int i=0; i<SimPar.STUDENTS;i++){
            students[i] = null;
        }

        try {
            queue = new MemFIFO<>(new Integer[SimPar.STUDENTS]);
        } 
        catch (MemException e) {
            e.printStackTrace();
        }
        this.repos = repos;
    }

    /**
     * Salutes the client and waits for the student to read the menu.
     * Notifies the student that he has been saluted.
     * Updates waiter state to PRESENTING_THE_MENU.
     */
    public synchronized void saluteTheClient(){
        int student_saluted=0;

        try {
            student_saluted = queue.read();
        } 
        catch (MemException e) {
            e.printStackTrace();
        }

        ((Waiter) Thread.currentThread()).setWaiterState(WaiterState.PRESENTING_THE_MENU);
        repos.setWaiterState(((Waiter) Thread.currentThread()).getWaiterState());

        students[student_saluted].setSalutedByWaiter();
        notifyAll();

        students[student_saluted].setTableSeat(this.studentsSeat);
        this.studentsSeat++;

        while(!students[student_saluted].getReadTheMenu()){
            try {
                wait();
            } 
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Notifies a new order in the requests queue.
     * Update the chef state to DELVERING_THE_PORTION
     */
    public synchronized void alertTheWaiter(){
        ((Chef) Thread.currentThread()).setChefState(ChefState.DELIVERING_THE_PORTIONS);
        repos.setChefState(((Chef) Thread.currentThread()).getChefState());
        this.portionReady++;
        this.waiterIsRequested++; 
        notifyAll();
    }

    /**
     * Waiter returns to the bar.
     * Updates waiter state to APPRAISING_SITUATION.
     */
    public synchronized void returningToTheBar(){
        ((Waiter) Thread.currentThread()).setWaiterState(WaiterState.APPRAISING_SITUATION);
        repos.setWaiterState(((Waiter) Thread.currentThread()).getWaiterState());
    }

    /**
     * Waiter prepares the bill.
     * Updates waiter state to PROCESSING_THE_BILL.
     */
    public synchronized void prepareTheBill(){
        ((Waiter) Thread.currentThread()).setWaiterState(WaiterState.PROCESSING_THE_BILL);
        repos.setWaiterState(((Waiter) Thread.currentThread()).getWaiterState());
    }

    /**
     * Waiter is waiting for a request.
     * Updates waiter state to APPRAISING_SITUATION.
     * @return the oldest order
     */
    public synchronized int lookAround(){
        ((Waiter) Thread.currentThread()).setWaiterState(WaiterState.APPRAISING_SITUATION);
        repos.setWaiterState(((Waiter) Thread.currentThread()).getWaiterState());

        while(this.waiterIsRequested == 0){
            try {
                wait();
            } 
            catch (InterruptedException e) {}
        }
        this.waiterIsRequested--; 
        
        if(this.studentAtDoor > 0){
            this.studentAtDoor--; 
            return 0;
        }
        else if(this.orderDone){
            this.orderDone = false;
            return 1;
        }
        else if(this.portionReady > 0){
            this.portionReady--; 
            return 2;
        }
        else if(this.payBill == true){
            this.payBill = false;
            return 3;
        }
        else if(this.allStudentsLeft){
            ((Waiter) Thread.currentThread()).setCanGoHome();
            return 4;
        }
        else{
            return -1;
        }
    }

    /**
     * Waiter says goodbye to students.
     * Updates waiter state to APPRAISING_SITUATION.
     */
    public synchronized void sayGoodbye(){
        ((Waiter) Thread.currentThread()).setWaiterState(WaiterState.APPRAISING_SITUATION);
        repos.setWaiterState(((Waiter) Thread.currentThread()).getWaiterState());
        ((Waiter) Thread.currentThread()).setCanGoHome();
    }

    /**
     * Student signals the waiter to bring the next course.
     * Notifies the waiter that all the people already ate.
     * Updates internal state of the student to CHATTING_WITH_COMPANION.
     * @param sID student id
     */
    public synchronized void signalTheWaiter(int sID){
        ((Student) Thread.currentThread()).setStudentState(StudentState.CHATTING_WITH_COMPANIONS);
        int studentId = ((Student) Thread.currentThread ()).getID();
        repos.setStudentState(studentId, ((Student) Thread.currentThread()).getStudentState());
        boolean last = ((Student) Thread.currentThread()).isLastStudent();
        
        if(last){
            ((Student) Thread.currentThread()).setLastStudent(false);
            System.out.printf("Student[%d] was the last to eat, will alert the waiter\n", sID);
            
            this.courses ++;
            this.waiterIsRequested++; 
            notifyAll();
        }
    }

    /**
     * Student calls the waiter when everyone has chosen.
     * Notifies a new order in the requests queue.
     * Updates internal state of the student to ORGANIZING_THE_ORDER.
     */
    public synchronized void callTheWaiter(){
        ((Student) Thread.currentThread()).setStudentState(StudentState.ORGANIZING_THE_ORDER);
        int studentId = ((Student) Thread.currentThread ()).getID();
        repos.setStudentState(studentId, ((Student) Thread.currentThread()).getStudentState());
        this.orderDone = true;
        
        this.waiterIsRequested++;
        notifyAll();
    }

    /**
     * Student enters the restaurant, updates internal state of student to TAKING_A_SEAT_AT_THE_TABLE.
     * Notifies the waiter that a student has entered.
     * Adds a request of type SALUTE_THE_CLIENT to the queue.
     */
    public synchronized void enter(){
        int sID;
        sID = ((Student) Thread.currentThread()).getID();
        students[sID] =  ((Student) Thread.currentThread());

        try {
            queue.write(sID);
            this.studentsEntered++;
        } 
        catch (MemException e1) {
            e1.printStackTrace();
        }

        this.studentAtDoor++; 
        this.waiterIsRequested++; 
        notifyAll();

        if(firstStudent){
            this.firstStudent = false;
            this.firstStudentID = sID;
            System.out.printf("First Student: Student[%d] \n", this.firstStudentID);
        }
        
        if(this.studentsEntered==SimPar.STUDENTS){
            this.lastStudentID = sID;
            System.out.printf("Last Student: Student[%d] \n", this.lastStudentID);
        }

        while(!students[sID].getSalutedByWaiter()){
            try {
                wait();
            } 
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        students[sID].setStudentState(StudentState.TAKING_A_SEAT_AT_THE_TABLE);
        repos.setStudentState(sID, ((Student) Thread.currentThread()).getStudentState());
    }

    /**
     * Checks if the student was the first student to enter the restaurant 
     *
     * @param sID The ID of the student.
     * @return true if it was the first student to enter, false otherwise
     */
    public synchronized boolean FirstStudent(int sID){
        if(this.firstStudentID == sID){
            return true;
        }
        else
            return false;
    }

    /**
     * Student leaves the restaurant, updates internal state of student to GOING_HOME.
     * Notifies the waiter that a student has left.
     * Adds a request of type SAY_GOODBYE to the queue.
     */
    public synchronized void exit(){
        ((Student) Thread.currentThread()).setStudentState(StudentState.GOING_HOME);
        int studentId = ((Student) Thread.currentThread ()).getID();
        repos.setStudentState(studentId, ((Student) Thread.currentThread()).getStudentState());
        this.studentsDone++;

        if(this.studentsDone==SimPar.STUDENTS){
            this.allStudentsLeft = true;
            this.waiterIsRequested++; 
            notifyAll();
        }
    }

    /**
     * Gets the id of the student who arrived last.
     * Notifies a new order in the requests queue.
     * Updates internal state of the student to PAYING_THE_BILL.
     * @param sID student id
     * @return boolean if the student was the last to enter the restaurant
     */
    public synchronized boolean shouldHaveArrivedEarlier(int sID){
        if(this.lastStudentID == sID){
            ((Student) Thread.currentThread()).setStudentState(StudentState.PAYING_THE_BILL);
            int studentId = ((Student) Thread.currentThread ()).getID();
            repos.setStudentState(studentId, ((Student) Thread.currentThread()).getStudentState());
            
            this.waiterIsRequested++;
            this.payBill = true;
            notifyAll();
            return true; 
        }
        else
            return false;
    }

    /**
     * Student reads the menu.
     * Notifies the waiter that he has read the menu.
     * Updates internal state of the student to SELECTING_THE_COURSES.
     */
    public synchronized void readTheMenu(){
        int sID;
        sID = ((Student) Thread.currentThread()).getID();
        students[sID].setReadTheMenu();

        notifyAll();

        ((Student) Thread.currentThread()).setStudentState(StudentState.SELECTING_THE_COURSES);
        repos.setStudentState(sID, ((Student) Thread.currentThread()).getStudentState());
    }
}
