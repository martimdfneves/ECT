package serverSide.sharedRegions;

import serverSide.entities.*;
import serverSide.main.SimPar;
import serverSide.stubs.*;
import commInfra.*;

/**
 * Where the waiter gets the pad, delivers the portions and presents the bill.
 * Where the students read the menu, inform the companion, prepare the order, add the choices, call and signal the waiter, describe the order, talk, eat and pay the bill.
 * @author Martim Neves
 * @author Tiago Dias
 */
public class Table extends Thread{
	/**
   	 * Checks if the order has been described
   	 */
    private boolean orderDescribed = false;
    
    /**
   	 * Checks if everybody just finished eating
   	 */
    public boolean everyBodyFinished = false;
    
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
    private MemFIFO<Integer> queue;
    
    /**
   	 * A reference to the stub of the general repository    
   	 */
    private final GeneralReposStub repos;     

    /**
     * Constructor 
     * @param repos reference to the stub of the logger
     */
    public Table(GeneralReposStub repos){
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
     * Waiter gets the pad and waits for the student to describe the order.
     * Waiter notifies the student that he has the pad.
     * Updates internal state of the waiter to TAKING_THE_ORDER.
     */
    public synchronized void getThePad(){
        ((Waiter) Thread.currentThread()).setWaiterState(WaiterState.TAKING_THE_ORDER);
        repos.setWaiterState(((Waiter) Thread.currentThread()).getWaiterState());

        while(!this.orderDescribed){
            try {
                wait();
            } 
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Waits while there's no one to be served.
     * When a student is waiting to be served the waiter serves and notifies him.
     */
    public synchronized void deliverPortion(){
        int student_served=0;

        while(this.studentWaiting == 0){
            try {
                wait();
            } 
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.studentWaiting--;
        
        try {
            student_served = queue.read();
        } 
        catch (MemException e) {
            System.out.println("No One to be served");
            System.out.println("But counted one as delivered");
        }

        students[student_served].setServedByWaiter(true);
        repos.setNPortion(1);
        notifyAll();
    }

    /**
     * Checks if all the clients have been served.
     * Updates internal state of the waiter to WAITING_FOR_PORTION.
     * @return if all the clients have been served.
     */
    public synchronized boolean haveAllClientsBeenServed(){
        ((Waiter) Thread.currentThread()).setWaiterState(WaiterState.WAITING_FOR_PORTION);
        repos.setWaiterState(((Waiter) Thread.currentThread()).getWaiterState());
        
        if(this.studentServed==SimPar.STUDENTS){
            this.studentServed=0;
            return true;
        }
        else
            return false;
    }

    /**
     * Waiter presents the bill and waits for the student to pay.
     * Waiter notifies the student that the bill is ready.
     * Updates internal state of the waiter to RECEIVING_PAYMENT.
     */
    public synchronized void presentTheBill(){
        this.billIsReady = true;
        notifyAll();
        
        ((Waiter) Thread.currentThread()).setWaiterState(WaiterState.RECEIVING_PAYMENT);
        repos.setWaiterState(((Waiter) Thread.currentThread()).getWaiterState());

        while(!this.billIsPaid){
            try {
                wait();
            } 
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Student informs companion of his course choice.
     * Notifies the companion that he has chosen.
     * Updates internal state of the student to CHATTING_WITH_COMPANIONS.
     */
    public synchronized void informCompanion(){
        int sID;
        sID = ((Student) Thread.currentThread()).getID();
        students[sID] =  ((Student) Thread.currentThread());
        students[sID].setStudentState(StudentState.CHATTING_WITH_COMPANIONS);
        repos.setStudentState(sID, ((Student) Thread.currentThread()).getStudentState());
        
        this.studentSelectedCourses++;
        notifyAll();
        
        while(!this.firstStudentJoinedTalk){
            try {
                wait();
            } 
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Increases the number of requests received by the first student.
     * Updates internal state of the student to ORGANIZING_THE_ORDER.
     */
    public synchronized void prepareTheOrder(){
        ((Student) Thread.currentThread()).setStudentState(StudentState.ORGANIZING_THE_ORDER);
        int studentId = ((Student) Thread.currentThread ()).getID();
        repos.setStudentState(studentId, ((Student) Thread.currentThread()).getStudentState());
        this.studentSelectedCourses++;
    }
    
    /**
     * Student chats with his companions.
     * Updates internal state of the student to CHATTING_WITH_COMPANIONS.
     */
    public synchronized void joinTheTalk(){
        int sID;
        sID = ((Student) Thread.currentThread()).getID();
        students[sID] =  ((Student) Thread.currentThread());
        students[sID].setStudentState(StudentState.CHATTING_WITH_COMPANIONS);
        repos.setStudentState(sID, ((Student) Thread.currentThread()).getStudentState());

        this.firstStudentJoinedTalk = true;
        notifyAll();
    }

    /**
     * Updates internal state of the student to CHATTING_WITH_COMPANIONS.
     * Waits for everybody to finished eating.
     */
    public synchronized void hasEverbodyFinished(){
        ((Student) Thread.currentThread()).setStudentState(StudentState.CHATTING_WITH_COMPANIONS);

        while(!this.everyBodyFinished){
            try {
                wait();
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Students wait until everyone is served and then start eating.
     * Updates internal state of the student to ENJOYING_THE_MEAL.
     */
    public synchronized void startEating(){
        ((Student) Thread.currentThread()).setStudentState(StudentState.ENJOYING_THE_MEAL);
        int studentId = ((Student) Thread.currentThread ()).getID();
        repos.setStudentState(studentId, ((Student) Thread.currentThread()).getStudentState());
        
        ((Student) Thread.currentThread()).studentEating();
    }

    /**
     * Updates internal state of the student to CHATTING_WITH_COMPANIONS.
     */
    public synchronized void endEating(){
        ((Student) Thread.currentThread()).setStudentState(StudentState.CHATTING_WITH_COMPANIONS);
        int studentId = ((Student) Thread.currentThread ()).getID();
        repos.setStudentState(studentId, ((Student) Thread.currentThread()).getStudentState());
        int sID = ((Student) Thread.currentThread()).getID();
        this.studentFinishedEating++;
        students[sID].setServedByWaiter(false);

        if(this.studentFinishedEating==SimPar.STUDENTS){
            ((Student) Thread.currentThread()).setLastStudent(true);
            System.out.printf("Student[%d] was the last to eat\n", sID);
            this.everyBodyFinished = true;
            this.studentFinishedEating=0;
            notifyAll();
        }
        
    }

    /**
     * Student waits until the bill is ready and then pays it.
     * Notifies the waiter that the bill is payed.
     */
    public synchronized void honourTheBill(){
        while(!this.billIsReady){
            try {
                wait();
            } 
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        this.billIsPaid = true;
        notifyAll();
    }

    /**
     * Student waits to be informed by his companions and increases the number of requests received by the first student.
     * Updates internal state of the student to ORGANIZING_THE_ORDER.
     */
    public synchronized void addUpOnesChoice(){
        ((Student) Thread.currentThread()).setStudentState(StudentState.ORGANIZING_THE_ORDER);
        int studentId = ((Student) Thread.currentThread ()).getID();
        repos.setStudentState(studentId, ((Student) Thread.currentThread()).getStudentState());

        
        while(this.studentSelectedCourses < SimPar.STUDENTS){
            try {
                wait();
            } 
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Checks if everybody has chosen.
     * @return if everybody has chosen.
     */
    public synchronized boolean hasEverybodyChosen(){
        if(this.studentSelectedCourses==SimPar.STUDENTS){
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * Student calls the waiter when everyone has chosen and waits for the waiter to get the pad.
     * Notifies the waiter that the order has been described.
     * Updates internal state of the student to ORGANIZING_THE_ORDER.
     */
    public synchronized void describeTheOrder(){
        ((Student) Thread.currentThread()).setStudentState(StudentState.ORGANIZING_THE_ORDER);
        int studentId = ((Student) Thread.currentThread ()).getID();
        repos.setStudentState(studentId, ((Student) Thread.currentThread()).getStudentState());
        
        this.orderDescribed = true;
        notifyAll();
    }

    /**
     * The student waits to be served.
     * @param sID student id
     */
    public synchronized void waitingToBeServed(int sID){ 
        try {
            queue.write(sID);
        } catch (MemException e1) {
            e1.printStackTrace();
        }
        this.studentWaiting++;
        notifyAll();
        
        while(!students[sID].servedByWaiter()){
            try {
                wait();
            } 
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        this.everyBodyFinished = false;
        this.studentServed++;
    }
}
