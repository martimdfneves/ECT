package sharedRegions.kitchen;
import entities.*;
import main.Logger;
import utils.SimPar;

/**
 * Where the chef watches the news, starts and continues the preparation, presents the portions, alerts the waiter and cleans up.
 * Where the waiter hands the note to the chef and collects the portions .
 * @author Martim Neves
 * @author Tiago Dias
 */
public class Kitchen implements KitchenChef, KitchenWaiter{
	
	/**
   	 * The number of portions delivered 
   	 */
    private int portionsDelivered;
    
    /**
   	 * The number of courses delivered 
   	 */
    private int coursesDelievered = 0;
    
    /**
   	 * Checks if the preparation started 
   	 */
    private boolean startedPrep = false; 
    
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
   	 * An reference to the general repository
   	 */
    private final Logger logger; 

    /**
	 * Constructor
	 *
	 * @param repos reference to the general repository
	 */
    public Kitchen(Logger logger){
        this.logger = logger;  
    }

    /**
     * Chef starts preparation.
     * Notifies the waiter that preparation just started.
     * Update the chef state to PREPARING_THE_COURSE.
     */
    @Override
    public synchronized void startPreparation(){
    	Chef chef = ((Chef)Thread.currentThread());
		
    	if(chef.getChefState() != ChefStates.PREPARING_THE_COURSE) {
			chef.setChefState(ChefStates.PREPARING_THE_COURSE);
			logger.updateChefState(ChefStates.PREPARING_THE_COURSE);
		}
		
        startedPrep = true;
        // Notifies the waiter that the preparation just started
        notifyAll();
    }

    /**
     * Update the chef state to DISHING_THE_PORTIONS.
     */
    @Override
    public synchronized void proceedToPresentation(){
    	Chef chef = ((Chef)Thread.currentThread());
		
    	if(chef.getChefState() != ChefStates.DISHING_THE_PORTIONS) {
			chef.setChefState(ChefStates.DISHING_THE_PORTIONS);
			logger.updateChefState(ChefStates.DISHING_THE_PORTIONS);
		}
        portionsDelivered = 0;
    }

    /**
     * Update the chef state to DISHING_THE_PORTIONS.
     */
    @Override
    public synchronized void haveNextPortionReady(){
    	Chef chef = ((Chef)Thread.currentThread());
    	
    	if(chef.getChefState() != ChefStates.DISHING_THE_PORTIONS) {
			chef.setChefState(ChefStates.DISHING_THE_PORTIONS);
			logger.updateChefState(ChefStates.DISHING_THE_PORTIONS);
		}
        portionCollected = false;
    }
    
    /**
     * Chef continues course preparation.
     * Update the chef state to PREPARING_THE_COURSE.
     */
    @Override
    public synchronized void continuePreparation(){
    	Chef chef = ((Chef)Thread.currentThread());
		
    	if(chef.getChefState() != ChefStates.PREPARING_THE_COURSE) {
			chef.setChefState(ChefStates.PREPARING_THE_COURSE);
			logger.updateChefState(ChefStates.PREPARING_THE_COURSE);
		}        
        coursesDelievered++;
        
        if(coursesDelievered == (SimPar.nCourses-1)){
            orderDone = true;
        }
    }
    
    /**
     * Chef clean up and closes his service.
     */
    @Override
    public synchronized void cleanUp(){
    	Chef chef = ((Chef)Thread.currentThread());
		
    	if(chef.getChefState() != ChefStates.CLOSING_SERVICE) {
			chef.setChefState(ChefStates.CLOSING_SERVICE);
			logger.updateChefState(ChefStates.CLOSING_SERVICE);
		}
    }

    /**
	 * Checks if the order has been completed
     * @return if the order has been completed
     */
    @Override
    public synchronized boolean hasTheOrderBeenCompleted(){
        return orderDone;
    }

    /**
	 * Checks if all portions been delivered.
     * @return if all portions been delivered.
     */
    @Override
    public synchronized boolean haveAllPortionsBeenDelivered(){
        if(portionsDelivered == (SimPar.students_number-1)){
            return true;
        }
        else{
           while(!portionCollected){
        	   // Waits for the portion to be collected
                try {
                    wait();
                } catch (InterruptedException e) {
            	 	System.out.println("haveAllPortionsBeenDelivered - One thread of Chef was interrupted.");
     	            System.exit(1);
                }
            }
            portionsDelivered++;
            return false;
        }
    }

    /**
     * Waiter hands the note to the chef and waits for the chef to start the preparation.
     * Notifies the chef that the note has been handed.
     * Updates internal state of the waiter to PLACING_THE_ORDER.
     */
    @Override
    public synchronized void handNoteToTheChef(){
    	Waiter waiter = ((Waiter)Thread.currentThread());
    	
		if(waiter.getWaiterState() != WaiterStates.PLACING_THE_ORDER) {
			waiter.setWaiterState(WaiterStates.PLACING_THE_ORDER);
			logger.updateWaiterState(WaiterStates.PLACING_THE_ORDER);
		}
        
		handedNoteToChef = true;
		// Notifies the chef that the note has been delivered
        notifyAll();

        // Waits for the chef to start the preparation
        while(!this.startedPrep){
            try {
                wait();
            } catch (InterruptedException e) {
            	 System.out.println("handNoteToTheChef - One thread of Waiter was interrupted.");
            	 System.exit(1);
            }
        }
    }

    /**
     * Notifies the chef that the waiter has collected the portion
     * Updates internal state of the waiter to WAITING_FOR_PORTION.
     */
    @Override
    public synchronized void collectPortion(){
    	Waiter waiter = ((Waiter)Thread.currentThread());
		
    	if(waiter.getWaiterState() != WaiterStates.WAITING_FOR_PORTION) {
			waiter.setWaiterState(WaiterStates.WAITING_FOR_PORTION);
		}
        
		portionCollected = true;
		// Notifies the chef that he's collecting a portion
		notifyAll();
    }

    /**
     * Chef waits for the note to be delivered by the waiter.
     * Update the chef state to WAITING_FOR_AN_ORDER.
     */
    @Override
    public synchronized void watchTheNews(){
    	Chef chef = ((Chef)Thread.currentThread());
		
    	if(chef.getChefState() != ChefStates.WAITING_FOR_AN_ORDER) {
			chef.setChefState(ChefStates.WAITING_FOR_AN_ORDER);
			logger.updateChefState(ChefStates.WAITING_FOR_AN_ORDER);
		}
		
    	// Waits for the note to be delivered by the waiter
        while(!handedNoteToChef){
            try {
                wait();
            } catch (InterruptedException e) {
            	System.out.println("watchTheNews - One thread of Chef was interrupted.");
	            System.exit(1);
            }
        }
    }

    /**
     * Checks if all the clients have been served.
     * @return if all the clients have been served
     */
    @Override
    public synchronized boolean haveAllClientsBeenServed(){
    	Waiter waiter = ((Waiter)Thread.currentThread());
		
    	if(waiter.getWaiterState() != WaiterStates.WAITING_FOR_PORTION) {
			waiter.setWaiterState(WaiterStates.WAITING_FOR_PORTION);
			logger.updateWaiterState(WaiterStates.WAITING_FOR_PORTION);
		}
        
        if(portionsDelivered == (SimPar.students_number)){
            return true;
        }
        return false;
    }
}
