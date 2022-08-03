package serverSide.objects;

import java.rmi.RemoteException;
import clientSide.entities.WaiterState;
import clientSide.entities.ChefState;
import clientSide.entities.StudentState;
import commInfra.*;
import genclass.GenericIO;
import interfaces.KitchenInterface;
import interfaces.GeneralReposInterface;
import serverSide.main.KitchenMain;
/**
 * Where the chef watches the news, starts and continues the preparation, presents the portions, alerts the waiter and cleans up.
 * Where the waiter hands the note to the chef and collects the portions .
 * @author Martim Neves
 * @author Tiago Dias
 */
public class Kitchen  implements KitchenInterface{
	/**
   	 * The number of portions delivered 
   	 */
    private int portionsDelivered;
    
    /**
   	 * The number of courses delivered 
   	 */
    private int coursesDelivered = 0;
    
    /**
   	 * Checks if the preparation started 
   	 */
    private boolean StartedPrep = false; 
    
    /**
   	 * Checks if the note was handed to the chef
   	 */
    private boolean handedNoteToChef = false;
    
    /**
   	 * Checks if the portion was collected
   	 */
    private boolean portionCollected = false;
    /**
   	 * Checks if the order is done
   	 */
    private boolean orderDone = false;
    
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
	 * @param repos reference of the general repository
	 */
    public Kitchen(GeneralReposInterface repos){
        this.repos = repos;
        nEntities = 0;
    }

    /**
     * Chef starts preparation.
     * Notifies the waiter that preparation just started.
     * Update the chef state to PREPARING_THE_COURSE.
     */
    public synchronized ReturnValue startPreparation(){
        try {
			repos.setChefState(ChefState.PREPARING_THE_COURSE);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        this.StartedPrep = true;
        notifyAll();
        return new ReturnValue(false, -1, ChefState.PREPARING_THE_COURSE);
    }

    /**
     * Update the chef state to DISHING_THE_PORTIONS.
     */
    public synchronized ReturnValue proceedToPresentation(){
        try {
			repos.setChefState(ChefState.DISHING_THE_PORTIONS);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        this.portionsDelivered = 0;
        return new ReturnValue(false, -1, ChefState.DISHING_THE_PORTIONS);
    }

    /**
     * Update the chef state to DISHING_THE_PORTIONS.
     */
    public synchronized ReturnValue haveNextPortionReady(){
        try {
			repos.setChefState(ChefState.DISHING_THE_PORTIONS);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        this.portionCollected = false;
        return new ReturnValue(false, -1, ChefState.DISHING_THE_PORTIONS);
    }
    
    /**
     * Chef continues course preparation.
     * Update the chef state to PREPARING_THE_COURSE.
     */
    public synchronized ReturnValue continuePreparation(){
        try {
			repos.setChefState(ChefState.PREPARING_THE_COURSE);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        this.coursesDelivered++;
        
        if(this.coursesDelivered==(SimPar.COURSES -1)){
            this.orderDone = true;
        }
        return new ReturnValue(false, -1, ChefState.PREPARING_THE_COURSE);
    }

    /**
     * Chef clean up and closes his service.
     */
    public synchronized ReturnValue cleanUp(){
        try {
			repos.setChefState(ChefState.CLOSING_SERVICE);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return new ReturnValue(false, -1, ChefState.CLOSING_SERVICE);
    }

    /**
	 * Checks if the order has been completed
     * @return if the order has been completed
     */
    public synchronized boolean hasTheOrderBeenCompleted(){
        return this.orderDone;
    }

    /**
   	 * Checks if all portions been delivered.
   	 * @return if all portions been delivered.
   	 */
    public synchronized boolean haveAllPortionsBeenDelivered(){
        if(this.portionsDelivered == (SimPar.STUDENTS)){
            return true;
        }
        else{
           while(!this.portionCollected){
                try {
                    wait();
                } 
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            this.portionsDelivered++;
            return false;
        }
    }

    /**
     * Waiter hands the note to the chef and waits for the chef to start the preparation.
     * Notifies the chef that the note has been handed.
     * Updates internal state of the waiter to PLACING_THE_ORDER.
     */
    public synchronized ReturnValue handNoteToTheChef(){
        try {
			repos.setWaiterState(WaiterState.PLACING_THE_ORDER);
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

        this.handedNoteToChef = true;
        notifyAll();

        while(!this.StartedPrep){
            try {
                wait();
            } 
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return new ReturnValue(false, -1, WaiterState.PLACING_THE_ORDER);
    }

    /**
     * Notifies the chef that the waiter has collected the portion
     * Updates internal state of the waiter to WAITING_FOR_PORTION.
     */
    public synchronized ReturnValue collectPortion(){
    	try {
			repos.setWaiterState(WaiterState.WAITING_FOR_PORTION);
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

        this.portionCollected = true;
        notifyAll();
        return new ReturnValue(false, -1, WaiterState.WAITING_FOR_PORTION);
    }

    /**
     * Chef waits for the note to be delivered by the waiter.
     * Update the chef state to WAITING_FOR_AN_ORDER.
     */
    public synchronized ReturnValue watchTheNews(){
        try {
			repos.setChefState(ChefState.WAITING_FOR_AN_ORDER);
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

        while(!this.handedNoteToChef){
            try {
                wait();
            } 
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return new ReturnValue(false, -1, ChefState.WAITING_FOR_AN_ORDER);
    }

    /**
     * Checks if all the clients have been served.
     * @return if all the clients have been served
     */
    public synchronized ReturnValue haveAllClientsBeenServed(){
        try {
			repos.setWaiterState(WaiterState.WAITING_FOR_PORTION);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        if(this.portionsDelivered==(SimPar.STUDENTS)){
        	return new ReturnValue(true, -1, WaiterState.WAITING_FOR_PORTION);
        }
        else{
        	return new ReturnValue(false, -1, WaiterState.WAITING_FOR_PORTION);
        }
    }
    
    public synchronized void shutdown () throws RemoteException
    {
        nEntities += 1;
        if (nEntities >= SimPar.E_Kit) {
        	
	        try
	    	{ repos.shutdown();
	    	}
	    	catch (RemoteException e)
	    	{ GenericIO.writelnString ("Customer generator remote exception on GeneralRepos shutdown: " + e.getMessage ());
	          System.exit (1);
	    	}
	        KitchenMain.shutdown ();
        }
        notifyAll ();                                       
    }
}
