package serverSide.objects;

import java.rmi.RemoteException;
import clientSide.entities.WaiterState;
import clientSide.entities.ChefState;
import clientSide.entities.StudentState;
import commInfra.*;
import genclass.GenericIO;
import interfaces.BarInterface;
import interfaces.GeneralReposInterface;
import serverSide.main.BarMain;
/**
 * Where the students enter and wait to be saluted. 
 * Where the waiter salutes the students when they enter, waits for a request, prepares the bill and says goodbye to the students when they leave.
 * @author Martim Neves
 * @author Tiago Dias
 */
public class Bar implements BarInterface{
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
   	 * List with the student saluted boolean status
   	 */
    private final boolean[] saluted;
    
    /**
   	 * List with the student read menu boolean status
   	 */
    private final boolean[] readMenu;
    
    /**
   	 * List with the student seat order
   	 */
    private final int[] order;
    
    /**
   	 * An queue that stores the students that have been saluted by the waiter 
   	 */
    private MemFIFO<Integer> queue;
    
    /**
   	 * A reference to the stub of the general repository
   	 */
    private final GeneralReposInterface repos;   
    
    /**
     *   Number of entity groups requesting the shutdown.
     */
    private int nEntities;
    
    /**
     * Checks if the waiter can go home
     */
    private boolean canGoHome = false;
    
    /**
   	 * List with the student last student boolean status
   	 */
    private final boolean[]lastStudent;

    /**
     * Constructor
     * @param repos reference to the stub of the logger
     */
    public Bar(GeneralReposInterface repos){
        saluted = new boolean[SimPar.STUDENTS];
        readMenu = new boolean[SimPar.STUDENTS];
        lastStudent = new boolean[SimPar.STUDENTS];
        order = new int[SimPar.STUDENTS];
        
        for(int i=0; i<SimPar.STUDENTS;i++){
            saluted[i] = false;
            readMenu[i] = false;
            lastStudent[i] = false;
            order[i] = -1;
        }

        try {
            queue = new MemFIFO<>(new Integer[SimPar.STUDENTS]);
        } 
        catch (MemException e) {
            e.printStackTrace();
        }
        this.repos = repos;
        nEntities = 0;
    }

    /**
     * Salutes the client and waits for the student to read the menu.
     * Notifies the student that he has been saluted.
     * Updates waiter state to PRESENTING_THE_MENU.
     */
    public synchronized ReturnValue saluteTheClient(){
        int student_saluted=0;

        try {
            student_saluted = queue.read();
        } 
        catch (MemException e) {
            e.printStackTrace();
        }

        try {
			repos.setWaiterState(WaiterState.PRESENTING_THE_MENU);
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

        saluted[student_saluted] = true;
        notifyAll();

        order[student_saluted] = this.studentsSeat;
        this.studentsSeat++;

        while(!readMenu[student_saluted]){
            try {
                wait();
            } 
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return new ReturnValue(false, -1, WaiterState.PRESENTING_THE_MENU);
    }

    /**
     * Notifies a new order in the requests queue.
     * Update the chef state to DELVERING_THE_PORTION
     */
    public synchronized ReturnValue alertTheWaiter(){
        try {
        	repos.setNPortion(1);
			repos.setChefState(ChefState.DELIVERING_THE_PORTIONS);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        this.portionReady++;
        this.waiterIsRequested++; 
        notifyAll();
        return new ReturnValue(false, -1, ChefState.DELIVERING_THE_PORTIONS);
    }

    /**
     * Waiter returns to the bar.
     * Updates waiter state to APPRAISING_SITUATION.
     */
    public synchronized ReturnValue returningToTheBar(){
        try {
			repos.setWaiterState(WaiterState.APPRAISING_SITUATION);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return new ReturnValue(false, -1, WaiterState.APPRAISING_SITUATION);
    }

    /**
     * Waiter prepares the bill.
     * Updates waiter state to PROCESSING_THE_BILL.
     */
    public synchronized ReturnValue prepareTheBill(){
        try {
			repos.setWaiterState(WaiterState.PROCESSING_THE_BILL);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return new ReturnValue(false, -1, WaiterState.PROCESSING_THE_BILL);
    }
    
    /**
     *   Get canGoHome Flag.
     *
     *     @return canGoHome
     */
    
    public boolean canGoHome(){ return canGoHome; }

    /**
     * Waiter is waiting for a request.
     * Updates waiter state to APPRAISING_SITUATION.
     * @return the oldest order
     */
    public synchronized ReturnValue lookAround(){
    	
    	try {
			repos.setWaiterState(WaiterState.APPRAISING_SITUATION);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        while(this.waiterIsRequested == 0){
            try {
                wait();
            } 
            catch (InterruptedException e) {}
        }
        this.waiterIsRequested--; 
        
        if(this.studentAtDoor > 0){
            this.studentAtDoor--; 
            return new ReturnValue(false, 0, WaiterState.APPRAISING_SITUATION);
        }
        else if(this.orderDone){
            this.orderDone = false;
            return new ReturnValue(false, 1, WaiterState.APPRAISING_SITUATION);
        }
        else if(this.portionReady > 0){
            this.portionReady--; 
            return new ReturnValue(false, 2, WaiterState.APPRAISING_SITUATION);
        }
        else if(this.payBill == true){
            this.payBill = false;
            return new ReturnValue(false, 3, WaiterState.APPRAISING_SITUATION);
        }
        else if(this.allStudentsLeft){
            canGoHome = true;
        	return new ReturnValue(false, 4, WaiterState.APPRAISING_SITUATION);
        }
        else{
        	return new ReturnValue(false, -1, WaiterState.APPRAISING_SITUATION);
        }
    }

    /**
     * Waiter says goodbye to students.
     * Updates waiter state to APPRAISING_SITUATION.
     */
    public synchronized ReturnValue sayGoodbye(){
        try {
			repos.setWaiterState(WaiterState.APPRAISING_SITUATION);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        canGoHome = true;
        return new ReturnValue(false, -1, WaiterState.APPRAISING_SITUATION);
    }

    /**
     * Student signals the waiter to bring the next course.
     * Notifies the waiter that all the people already ate.
     * Updates internal state of the student to CHATTING_WITH_COMPANION.
     * @param sID student id
     */
    public synchronized ReturnValue signalTheWaiter(int sID, boolean last){
        try {
			repos.setStudentState(sID, StudentState.CHATTING_WITH_COMPANIONS);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        if(last){
            System.out.printf("Student[%d] was the last to eat, will alert the waiter\n", sID);
            
            this.courses ++;
            this.waiterIsRequested++; 
            notifyAll();
            return new ReturnValue(false, -1, StudentState.CHATTING_WITH_COMPANIONS);
        }
        return new ReturnValue(true, -1, StudentState.CHATTING_WITH_COMPANIONS);
    }

    /**
     * Student calls the waiter when everyone has chosen.
     * Notifies a new order in the requests queue.
     * Updates internal state of the student to ORGANIZING_THE_ORDER.
     * @param studentId id of the student
     */
    public synchronized ReturnValue callTheWaiter(int studentId){
        try {
			repos.setStudentState(studentId, StudentState.ORGANIZING_THE_ORDER);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        this.orderDone = true;
        
        this.waiterIsRequested++;
        notifyAll();
        return new ReturnValue(false, -1, StudentState.ORGANIZING_THE_ORDER);
    }

    /**
     * Student enters the restaurant, updates internal state of student to TAKING_A_SEAT_AT_THE_TABLE.
     * Notifies the waiter that a student has entered.
     * Adds a request of type SALUTE_THE_CLIENT to the queue.
     * @param sID student id
     */
    public synchronized ReturnValue enter(int sID){

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

        while(!saluted[sID]){
            try {
                wait();
            } 
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        try {
			repos.setStudentState(sID, StudentState.TAKING_A_SEAT_AT_THE_TABLE);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return new ReturnValue(false, -1, StudentState.TAKING_A_SEAT_AT_THE_TABLE);
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
     * @param sID student id
     */
    public synchronized ReturnValue exit(int sID){
        try {
			repos.setStudentState(sID, StudentState.GOING_HOME);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        this.studentsDone++;

        if(this.studentsDone==SimPar.STUDENTS){
            this.allStudentsLeft = true;
            this.waiterIsRequested++; 
            notifyAll();
        }
        return new ReturnValue(false, -1, StudentState.GOING_HOME);
    }

    /**
     * Gets the id of the student who arrived last.
     * Notifies a new order in the requests queue.
     * Updates internal state of the student to PAYING_THE_BILL.
     * @param sID student id
     * @return boolean if the student was the last to enter the restaurant
     */
    public synchronized ReturnValue shouldHaveArrivedEarlier(int sID){
        if(this.lastStudentID == sID){
            try {
				repos.setStudentState(sID, StudentState.PAYING_THE_BILL);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
            this.waiterIsRequested++;
            this.payBill = true;
            notifyAll();
            return new ReturnValue(true, -1, StudentState.PAYING_THE_BILL); 
        }
        else
        	return new ReturnValue(false, -1, StudentState.PAYING_THE_BILL);
    }

    /**
     * Student reads the menu.
     * Notifies the waiter that he has read the menu.
     * Updates internal state of the student to SELECTING_THE_COURSES.
     * @param sID student id
     */
    public synchronized ReturnValue readTheMenu(int sID){
    	readMenu[sID] = true;
        notifyAll();

        try {
			repos.setStudentState(sID, StudentState.SELECTING_THE_COURSES);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return new ReturnValue(false, -1, StudentState.SELECTING_THE_COURSES);
    }
    
    public synchronized void shutdown () throws RemoteException
    {
        nEntities += 1;
        if (nEntities >= SimPar.E_Bar) {
        	
	        try
	    	{ repos.shutdown();
	    	}
	    	catch (RemoteException e)
	    	{ GenericIO.writelnString ("Customer generator remote exception on GeneralRepos shutdown: " + e.getMessage ());
	          System.exit (1);
	    	}
	        BarMain.shutdown ();
        }
        notifyAll ();                                       
    }
}
