package clientSide.entities;

import java.rmi.RemoteException;
import commInfra.ReturnValue;
import interfaces.KitchenInterface;
import interfaces.BarInterface;
import interfaces.TableInterface;

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
     * Reference to the kitchen
     */
    private KitchenInterface kitchen;
    
    /**
     * Reference to the bar
     */
    private BarInterface bar;
    
    /**
     * Reference to the table
     */
    private TableInterface table;

    /**
     * Waiter constructor
     * 
     * @param waiterID waiter id
     * @param kitchen reference to the kitchen
     * @param bar reference to the bar
     * @param table reference to the table
     */
    public Waiter(int waiterID, KitchenInterface kitchen, BarInterface bar, TableInterface table){
        this.setWaiterID(waiterID);
        state = WaiterState.APPRAISING_SITUATION;
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

        while(!canGoHome()){
            action = lookAround();
            switch(action){
                case 0:
                    saluteTheClient();
                    returningToTheBar();
                    break;
                case 1:
                    getThePad();
                    handNoteToTheChef();
                    returningToTheBar();
                    break;
                case 2:
                    if(!haveAllClientsBeenServed()){
                        collectPortion();
                        deliverPortion();
                    }
                    returningToTheBar();
                    break;
                case 3:
                    prepareTheBill();
                    presentTheBill();
                    returningToTheBar();
                    break;
                case 4:
                    sayGoodbye();
                    break;
            }
        }
        System.out.println("\033[42m Waiter closes the restaurant \033[0m");
        System.out.println("Waiter End Of Life");
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
    
    /**
     *
     */
    
    private int lookAround() {
    	ReturnValue ret = null;
    	try
        { ret = bar.lookAround();
        }
        catch (RemoteException e)
        { 
          System.exit (1);
        }
    	this.state = ret.getStateValue();
    	return ret.getIntValue();
    }
    
    private void saluteTheClient() {
    	ReturnValue ret = null;
    	try
        { ret = bar.saluteTheClient();
        }
        catch (RemoteException e)
        { 
          System.exit (1);
        }
    	this.state = ret.getStateValue();
    }
    
    private void returningToTheBar() {
    	ReturnValue ret = null;
    	try
        { ret = bar.returningToTheBar();
        }
        catch (RemoteException e)
        { 
          System.exit (1);
        }
    	this.state = ret.getStateValue();
    }
    
    private void prepareTheBill() {
    	ReturnValue ret = null;
    	try
        { ret = bar.prepareTheBill();
        }
        catch (RemoteException e)
        { 
          System.exit (1);
        }
    	this.state = ret.getStateValue();
    }
    
    private void sayGoodbye() {
    	ReturnValue ret = null;
    	try
        { ret = bar.sayGoodbye();
        }
        catch (RemoteException e)
        { 
          System.exit (1);
        }
    	this.state = ret.getStateValue();
    }
    
    private boolean canGoHome() {
    	boolean ret = false;
    	try
        { ret = bar.canGoHome();
        }
        catch (RemoteException e)
        { 
          System.exit (1);
        }
    	return ret;
    }
    
    private void getThePad() {
    	ReturnValue ret = null;
    	try
        { ret = table.getThePad();
        }
        catch (RemoteException e)
        { 
          System.exit (1);
        }
    	this.state = ret.getStateValue();
    }
    
    private void deliverPortion() {
    	try
        { table.deliverPortion();
        }
        catch (RemoteException e)
        { 
          System.exit (1);
        }
    }
    
    private void presentTheBill() {
    	ReturnValue ret = null;
    	try
        { ret = table.presentTheBill();
        }
        catch (RemoteException e)
        { 
          System.exit (1);
        }
    	this.state = ret.getStateValue();
    }
    
    private void handNoteToTheChef() {
    	ReturnValue ret = null;
    	try
        { ret = kitchen.handNoteToTheChef();
        }
        catch (RemoteException e)
        { 
          System.exit (1);
        }
    	this.state = ret.getStateValue();
    }
    
    private boolean haveAllClientsBeenServed() {
    	ReturnValue ret = null;
    	try
        { ret = kitchen.haveAllClientsBeenServed();
        }
        catch (RemoteException e)
        { 
          System.exit (1);
        }
    	this.state = ret.getStateValue();
    	return ret.getBooleanValue();
    }
    
    private void collectPortion() {
    	ReturnValue ret = null;
    	try
        { ret = kitchen.collectPortion();
        }
        catch (RemoteException e)
        { 
          System.exit (1);
        }
    	this.state = ret.getStateValue();
    }
}
