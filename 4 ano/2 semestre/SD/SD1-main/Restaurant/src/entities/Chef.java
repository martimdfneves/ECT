package entities;
import sharedRegions.bar.Bar;
import sharedRegions.kitchen.Kitchen;

/**
 * Chef thread:
 * Implements the life-cycle of the Chef and stores the variables referent to
 * the chef and his current state.
 * @author Martim Neves
 * @author Tiago Dias
 */
public class Chef extends Thread{
	
	/**
    * Current Chef state
    */
    private ChefStates state;
    
    /**
     * Instance of bar
     */
    private Bar bar;
    
    /**
     * Instance of kitchen
     */
    private Kitchen kitchen;

    /**
     * Chef constructor
     * 
     * @param state initial chef state
     * @param kitchen instance of kitchen
     * @param bar instance of bar
     */
    public Chef(ChefStates state, Kitchen kitchen, Bar bar){
        this.state = state;
        this.kitchen = kitchen;
        this.bar = bar;
    }

	/**
     * Implements the life cycle of the chef
     */
    @Override
    public void run(){
    	// To start the preparation
		boolean startPreparation = true;
        
		// While there is no orders
        //System.out.println("Chef: Watching the news");
        kitchen.watchTheNews();
        
        // Start the preparation by course (finishes when the last portion from the deserts was delivered)
        do{
            if(startPreparation){
            	// Start preparing an course (the preparation is done course by course)
            	//System.out.println("Chef: Starting preparation");
                kitchen.startPreparation();
            }
            else{
            	// Continuing the preparation of the next course
				//System.out.println("Chef: Continuing preparation");
            	kitchen.continuePreparation();
            }
            
            // Beginning the presentation of the portions
            //System.out.println("Chef: Proceeding to presentation");
            kitchen.proceedToPresentation();
            
            // Alert the waiter that a portion from the course is done
            //System.out.println("Chef: Alerting the waiter");
            bar.alertTheWaiter();

            //System.out.println("Chef: Checking if all portions have been delivered");
            while(!kitchen.haveAllPortionsBeenDelivered()){
            	//System.out.println("Chef: Have next portion ready");
                kitchen.haveNextPortionReady();
                
                //System.out.println("Chef: Alerting the waiter");
                bar.alertTheWaiter();
            }
            // All the portions from the current course has been delivered
            startPreparation = false;
            //System.out.println("Chef: Checking if the order has been completed");
        } 
        while(!kitchen.hasTheOrderBeenCompleted());
        
        // Cleaning the kitchen, the work is done
 		//System.out.println("Chef: Cleaning up");
        kitchen.cleanUp();
        
        System.out.println("Chef End Of Life");
    }
    
    /**
     * Set the chef state
     * 
     * @param state instance of ChefStates
     */
    public void setChefState(ChefStates state){
        this.state = state;
    }
    
    /**
     * Get the chef state 
     * 
     * @return chef state
     */
    public ChefStates getChefState(){
        return state;
    }
}
