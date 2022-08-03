package clientSide.entities;

import clientSide.stubs.*;
import genclass.*;

/**
 * Waiter thread
 * Implements the life-cycle of the Waiter and stores the variables referent to
 * the waiter and his current state.
 * @author Martim Neves
 * @author Tiago Dias
 */
public class Waiter extends Thread{
	
	/**
     * The waiter ID
     */
    private int waiterID;
    
    /**
     * Current Waiter state
     */
    private int state;
    
    /**
     * Reference to the stub of the kitchen
     */
    private KitchenStub kitchen;
    
    /**
     * Checks if the waiter can go home
     */
    private boolean canGoHome = false;
    
    /**
     * Reference to the stub of the bar
     */
    private BarStub bar;
    
    /**
     * Reference to the stub of the table
     */
    private TableStub table;

    /**
     * Waiter constructor
     * 
     * @param waiterID waiter id
     * @param state current student state
     * @param kitchen reference to the stub of the kitchen
     * @param bar reference to the stub of the bar
     * @param table reference to the stub of the table
     */
    public Waiter(int waiterID, int state, KitchenStub kitchen, BarStub bar, TableStub table){
        this.setWaiterID(waiterID);
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
        int action;

        while(!this.CanGoHome()){
            action = bar.lookAround();
            switch(action){
                case 0:
                    bar.saluteTheClient();
                    bar.returningToTheBar();
                    break;
                case 1:
                    table.getThePad();
                    kitchen.handNoteToTheChef();
                    bar.returningToTheBar();
                    break;
                case 2:
                    if(!kitchen.haveAllClientsBeenServed()){
                        kitchen.collectPortion();
                        table.deliverPortion();
                    }
                    bar.returningToTheBar();
                    break;
                case 3:
                    bar.prepareTheBill();
                    table.presentTheBill();
                    bar.returningToTheBar();
                    break;
                case 4:
                    bar.sayGoodbye();
                    break;
            }
        }
        System.out.println("\033[42m Waiter closes the restaurant \033[0m");
        kitchen.shutdown();
        table.shutdown();
        bar.shutdown();
        GenericIO.writelnString("Waiter End Of Life");
    }
    
    /**
     * Get the waiter ID 
     * @return waiterID
     */
    public int getWaiterID() {
        return waiterID;
    }

    /**
     * Set the waiter ID 
     * @param waiterID waiter ID
     */
    public void setWaiterID(int waiterID) {
        this.waiterID = waiterID;
    }

    /**
     * Get the canGoHome flag  
     * @return canGoHome flag    
     */
    public boolean CanGoHome() {
        return canGoHome;
    }

    /**
     * Set the canGoHome flag      
     */
    public void setCanGoHome() {
        this.canGoHome = true;
    }

    /**
     * Set the waiter state
     * @param state an integer that represents the current waiter state
     */
    public void setState(int state){
        this.state = state;
    }

    /**
     * Get the current waiter state 
     * @return waiter state
     */
    public int getWaiterState(){
        return this.state;
    }
}
