package entities;
import sharedRegions.bar.Bar;
import sharedRegions.kitchen.Kitchen;
import sharedRegions.table.Table;
import utils.Order;

/**
 * Waiter thread
 * Implements the life-cycle of the Waiter and stores the variables referent to
 * the waiter and his current state.
 * @author Martim Neves
 * @author Tiago Dias
 */
public class Waiter extends Thread{
	
	/**
     * Current Waiter state
     */
    private WaiterStates state;
    
    /**
     * Instance of kitchen
     */
    private Kitchen kitchen;
    
    /**
    * Checks if the waiter can go home
    */
    private boolean canGoHome = false;
    
	/**
     * Instance of bar
     */
    private Bar bar;
    
    /**
     * Instance of table
     */
    private Table table;

    /**
     * Waiter constructor
     * 
     * @param state student state
     * @param kitchen instance of kitchen
     * @param bar instance of bar
     * @param table instance of table
     */
    public Waiter(WaiterStates state, Kitchen kitchen, Bar bar, Table table){
        this.state = state;
        this.kitchen = kitchen;
        this.bar = bar;
        this.table = table;
    }

    /**
     * Implements the life cycle of the waiter
     */
    @Override
    public void run(){
    	while(!this.CanGoHome()){
        	//System.out.println("Waiter: Looking around");
    		Order order = bar.lookAround();
    		
    		//System.out.println("ORDER -> "+order);
            
    		switch(order.getRequestType()){
                case SALUTE_THE_CLIENT:
                	//System.out.println("Waiter: Saluting the clients -> Student"+order.getRequesterID());
                    bar.saluteTheClient();
                    
                    //System.out.println("Waiter: Returning to the bar");
                    bar.returningToTheBar();
                    break;
                case GET_THE_PAD:
                	//System.out.println("Waiter: Getting the pad");
                    table.getThePad();
                    
                    //System.out.println("Waiter: Handing the note to the chef");
                    kitchen.handNoteToTheChef();
                    
                    //System.out.println("Waiter: Returning to the bar");
                    bar.returningToTheBar();
                    break;
                case COLLECT_PORTIONS:
                    if(!kitchen.haveAllClientsBeenServed()){
                    	//System.out.println("Waiter: Collecting the portions");
                        kitchen.collectPortion();
                        
                        //System.out.println("Waiter: Delivering the portions");
                        table.deliverPortion();
                    }
                    
                    //System.out.println("Waiter: Returning to the bar");
                    bar.returningToTheBar();
                    break;
                case PREPARE_THE_BILL:
                	//System.out.println("Waiter: Preparing the bill");
                    bar.prepareTheBill();
                    
                    //System.out.println("Waiter: Presenting the bill");
                    table.presentTheBill();
                    
                    //System.out.println("Waiter: Returning to the bar");
                    bar.returningToTheBar();
                    break;
                case SAY_GOODBYE:
                	//System.out.println("Waiter: Saying goodbye");
                    bar.sayGoodbye();
                    break;
                default:
                	break;
            }
        }
        System.out.println("Waiter End Of Life");
    }
    
    /**
     * Get the canGoHome flag      
     */
    public boolean CanGoHome(){
        return canGoHome;
    }

    /**
     * Set the canGoHome flag      
     */
    public void setCanGoHome(){
        this.canGoHome = true;
    }

    /**
     * Set the waiter state
     * 
     * @param state instance of WaiterState
     */
    public void setWaiterState(WaiterStates state){
        this.state = state;
    }

    /**
     * Get the waiter state 
     */
    public WaiterStates getWaiterState(){
        return this.state;
    }
}
