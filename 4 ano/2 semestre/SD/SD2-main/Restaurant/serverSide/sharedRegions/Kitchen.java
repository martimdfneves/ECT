package serverSide.sharedRegions;

import serverSide.entities.*;
import serverSide.main.SimPar;
import serverSide.stubs.*;

/**
 * Where the chef watches the news, starts and continues the preparation, presents the portions, alerts the waiter and cleans up.
 * Where the waiter hands the note to the chef and collects the portions .
 * @author Martim Neves
 * @author Tiago Dias
 */
public class Kitchen extends Thread {
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
    private final GeneralReposStub repos;   

    /**
	 * Constructor
	 * @param repos reference of the general repository
	 */
    public Kitchen(GeneralReposStub repos){
        this.repos = repos;
    }

    /**
     * Chef starts preparation.
     * Notifies the waiter that preparation just started.
     * Update the chef state to PREPARING_THE_COURSE.
     */
    public synchronized void startPreparation(){
        ((Chef) Thread.currentThread()).setChefState(ChefState.PREPARING_THE_COURSE);
        repos.setChefState(((Chef) Thread.currentThread()).getChefState());
        this.StartedPrep = true;
        notifyAll();
    }

    /**
     * Update the chef state to DISHING_THE_PORTIONS.
     */
    public synchronized void proceedToPresentation(){
        ((Chef) Thread.currentThread()).setChefState(ChefState.DISHING_THE_PORTIONS);
        repos.setChefState(((Chef) Thread.currentThread()).getChefState());
        this.portionsDelivered = 0;
    }

    /**
     * Update the chef state to DISHING_THE_PORTIONS.
     */
    public synchronized void haveNextPortionReady(){
        ((Chef) Thread.currentThread()).setChefState(ChefState.DISHING_THE_PORTIONS);
        repos.setChefState(((Chef) Thread.currentThread()).getChefState());
        this.portionCollected = false;
    }
    
    /**
     * Chef continues course preparation.
     * Update the chef state to PREPARING_THE_COURSE.
     */
    public synchronized void continuePreparation(){
        ((Chef) Thread.currentThread()).setChefState(ChefState.PREPARING_THE_COURSE);
        repos.setChefState(((Chef) Thread.currentThread()).getChefState());
        this.coursesDelivered++;
        
        if(this.coursesDelivered==(SimPar.COURSES-1)){
            this.orderDone = true;
        }
    }

    /**
     * Chef clean up and closes his service.
     */
    public synchronized void cleanUp(){
        ((Chef) Thread.currentThread()).setChefState(ChefState.CLOSING_SERVICE);
        repos.setChefState(((Chef) Thread.currentThread()).getChefState());
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
    public synchronized void handNoteToTheChef(){
        ((Waiter) Thread.currentThread()).setWaiterState(WaiterState.PLACING_THE_ORDER);
        repos.setWaiterState(((Waiter) Thread.currentThread()).getWaiterState());

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
    }

    /**
     * Notifies the chef that the waiter has collected the portion
     * Updates internal state of the waiter to WAITING_FOR_PORTION.
     */
    public synchronized void collectPortion(){
        ((Waiter) Thread.currentThread()).setWaiterState(WaiterState.WAITING_FOR_PORTION);

        this.portionCollected = true;
        notifyAll();
    }

    /**
     * Chef waits for the note to be delivered by the waiter.
     * Update the chef state to WAITING_FOR_AN_ORDER.
     */
    public synchronized void watchTheNews(){
        ((Chef) Thread.currentThread()).setChefState(ChefState.WAITING_FOR_AN_ORDER);
        repos.setChefState(((Chef) Thread.currentThread()).getChefState());

        while(!this.handedNoteToChef){
            try {
                wait();
            } 
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Checks if all the clients have been served.
     * @return if all the clients have been served
     */
    public synchronized boolean haveAllClientsBeenServed(){
        ((Waiter) Thread.currentThread()).setWaiterState(WaiterState.WAITING_FOR_PORTION);
        repos.setWaiterState(((Waiter) Thread.currentThread()).getWaiterState());
        
        if(this.portionsDelivered==(SimPar.STUDENTS)){
            return true;
        }
        else{
            return false;
        }
    }
}
