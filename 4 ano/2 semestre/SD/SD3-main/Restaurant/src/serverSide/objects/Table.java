package serverSide.objects;

import java.rmi.RemoteException;
import clientSide.entities.WaiterState;
import clientSide.entities.StudentState;
import commInfra.*;
import genclass.GenericIO;
import interfaces.TableInterface;
import interfaces.GeneralReposInterface;
import serverSide.main.TableMain;

/**
 * Where the waiter gets the pad, delivers the portions and presents the bill.
 * Where the students read the menu, inform the companion, prepare the order, add the choices, call and signal the waiter, describe the order, talk, eat and pay the bill.
 * @author Martim Neves
 * @author Tiago Dias
 */
public class Table implements TableInterface{
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
   	 * List with the students served boolean status
   	 */
    private final boolean[] served;
    
    /**
   	 * An queue that stores the students that are waiting to be served
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
     * Constructor 
     * @param repos reference to the stub of the logger
     */
    public Table(GeneralReposInterface repos){
        served = new boolean[SimPar.STUDENTS];
        
        for(int i=0; i<SimPar.STUDENTS;i++){
            served[i] = false;
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
     * Waiter gets the pad and waits for the student to describe the order.
     * Waiter notifies the student that he has the pad.
     * Updates internal state of the waiter to TAKING_THE_ORDER.
     */
    public synchronized ReturnValue getThePad(){
        try {
			repos.setWaiterState(WaiterState.TAKING_THE_ORDER);
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

        while(!this.orderDescribed){
            try {
                wait();
            } 
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return new ReturnValue(false, -1, WaiterState.TAKING_THE_ORDER);
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

        served[student_served] = true;

        notifyAll();
    }

    /**
     * Checks if all the clients have been served.
     * Updates internal state of the waiter to WAITING_FOR_PORTION.
     * @return if all the clients have been served.
     */
    public synchronized ReturnValue haveAllClientsBeenServed(){
        try {
			repos.setWaiterState(WaiterState.WAITING_FOR_PORTION);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        if(this.studentServed==SimPar.STUDENTS){
            this.studentServed=0;
            return new ReturnValue(true, -1, WaiterState.WAITING_FOR_PORTION);
        }
        else
        	return new ReturnValue(false, -1, WaiterState.WAITING_FOR_PORTION);
    }

    /**
     * Waiter presents the bill and waits for the student to pay.
     * Waiter notifies the student that the bill is ready.
     * Updates internal state of the waiter to RECEIVING_PAYMENT.
     */
    public synchronized ReturnValue presentTheBill(){
        this.billIsReady = true;
        notifyAll();
        
        try {
			repos.setWaiterState(WaiterState.RECEIVING_PAYMENT);
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

        while(!this.billIsPaid){
            try {
                wait();
            } 
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return new ReturnValue(false, -1, WaiterState.RECEIVING_PAYMENT);
    }

    /**
     * Student informs companion of his course choice.
     * Notifies the companion that he has chosen.
     * Updates internal state of the student to CHATTING_WITH_COMPANIONS.
     */
    public synchronized ReturnValue informCompanion(int sID){
        try {
			repos.setStudentState(sID, (StudentState.CHATTING_WITH_COMPANIONS));
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
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
        return new ReturnValue(false, -1, StudentState.CHATTING_WITH_COMPANIONS);
    }

    /**
     * Increases the number of requests received by the first student.
     * Updates internal state of the student to ORGANIZING_THE_ORDER.
     */
    public synchronized ReturnValue prepareTheOrder(int studentId){
        try {
			repos.setStudentState(studentId, (StudentState.ORGANIZING_THE_ORDER));
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        this.studentSelectedCourses++;
        return new ReturnValue(false, -1, StudentState.ORGANIZING_THE_ORDER);
    }
    
    /**
     * Student chats with his companions.
     * Updates internal state of the student to CHATTING_WITH_COMPANIONS.
     */
    public synchronized ReturnValue joinTheTalk(int sID){
        try {
			repos.setStudentState(sID, StudentState.CHATTING_WITH_COMPANIONS);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        this.firstStudentJoinedTalk = true;
        notifyAll();
        return new ReturnValue(false, -1, StudentState.CHATTING_WITH_COMPANIONS);
    }

    /**
     * Updates internal state of the student to CHATTING_WITH_COMPANIONS.
     * Waits for everybody to finished eating.
     * @param sID student id
     */
    public synchronized ReturnValue hasEverbodyFinished(int sID){

    	try {
			repos.setStudentState(sID, StudentState.CHATTING_WITH_COMPANIONS);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
        while(!this.everyBodyFinished){
            try {
                wait();
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return new ReturnValue(false, -1, StudentState.CHATTING_WITH_COMPANIONS);
    }

    /**
     * Students wait until everyone is served and then start eating.
     * Updates internal state of the student to ENJOYING_THE_MEAL.
     */
    public synchronized ReturnValue startEating(int studentId){
        try {
			repos.setStudentState(studentId, StudentState.ENJOYING_THE_MEAL);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        try
        { Thread.sleep ((long) (1 + 100 * Math.random ()));
        }
        catch (InterruptedException e) {}
        return new ReturnValue(false, -1, StudentState.ENJOYING_THE_MEAL);
    }

    /**
     * Updates internal state of the student to CHATTING_WITH_COMPANIONS.
     */
    public synchronized ReturnValue endEating(int studentId){
        try {
			repos.setStudentState(studentId, StudentState.CHATTING_WITH_COMPANIONS);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        this.studentFinishedEating++;
        served[studentId] = false;

        if(this.studentFinishedEating==SimPar.STUDENTS){
            //((Student) Thread.currentThread()).setLastStudent(true);
            System.out.printf("Student[%d] was the last to eat\n", studentId);
            this.everyBodyFinished = true;
            this.studentFinishedEating=0;
            notifyAll();
            return new ReturnValue(true, -1, StudentState.CHATTING_WITH_COMPANIONS);
        }
        return new ReturnValue(false, -1, StudentState.CHATTING_WITH_COMPANIONS);
        
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
    public synchronized ReturnValue addUpOnesChoice(int studentId){
        try {
			repos.setStudentState(studentId, StudentState.ORGANIZING_THE_ORDER);
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

        
        while(this.studentSelectedCourses < SimPar.STUDENTS){
            try {
                wait();
            } 
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return new ReturnValue(false, -1, StudentState.ORGANIZING_THE_ORDER);
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
    public synchronized ReturnValue describeTheOrder(int studentId){
        try {
			repos.setStudentState(studentId, StudentState.ORGANIZING_THE_ORDER);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        this.orderDescribed = true;
        notifyAll();
        return new ReturnValue(false, -1, StudentState.ORGANIZING_THE_ORDER);
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
        
        while(!served[sID]){
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
    
    /**
     *   Operation server shutdown.
     *
     *   New operation.
     *
     *     @throws RemoteException if either the invocation of the remote method, or the communication with the registry
     *                             service fails
     */
    
    public synchronized void shutdown () throws RemoteException
    {
        nEntities += 1;
        if (nEntities >= SimPar.E_Tab) {
        	
	        try
	    	{ repos.shutdown();
	    	}
	    	catch (RemoteException e)
	    	{ GenericIO.writelnString ("Customer generator remote exception on GeneralRepos shutdown: " + e.getMessage ());
	          System.exit (1);
	    	}
	        TableMain.shutdown ();
        }
        notifyAll ();                                       
    }
}
