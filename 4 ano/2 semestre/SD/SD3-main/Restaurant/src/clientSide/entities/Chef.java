package clientSide.entities;

import java.rmi.RemoteException;
import commInfra.ReturnValue;
import interfaces.BarInterface;
import interfaces.KitchenInterface;

/**
 * Chef thread:
 * Implements the life-cycle of the Chef and stores the variables referent to
 * the chef and his current state.
 * @author Martim Neves
 * @author Tiago Dias
 */
public class Chef extends Thread{
	
	 /**
     * The Chef ID
     */
    private int chefID;
    
    /**
     * Current Chef state
     */
    private int state;
    
    /**
     * Reference to the bar
     */
    private BarInterface bar;
    
    /**
     * Reference to the kitchen
     */
    private KitchenInterface kitchen;

    /**
     * Chef constructor
     * 
     * @param chefID the Chef ID
     * @param kitchen reference to the kitchen
     * @param bar reference to the bar
     */
    public Chef(int chefID, KitchenInterface kitchen, BarInterface bar){
        this.setChefID(chefID);
        state = ChefState.WAITING_FOR_AN_ORDER;
        this.kitchen = kitchen;
        this.bar = bar;
    }

    /**
     * Implements the life cycle of the chef
     */
    @Override
    public void run(){
        boolean firstCourse = true;
        watchTheNews();
        startPreparation();

        do{
            if(!firstCourse){
                continuePreparation();
            }
            else{
                firstCourse = false;
            }
            proceedToPresentation();
            alertTheWaiter();

            while(!haveAllPortionsBeenDelivered()){
                haveNextPortionReady();
                alertTheWaiter();
            }
        }
        while(!hasTheOrderBeenCompleted());
        
        cleanUp();
        System.out.println("\033[42m Chef cleaned up the kitchen \033[0m");
        System.out.println("Chef End Of Life");
    }
    
    /**
     * Get the chef ID 
     * @return chefID
     */
    public int getChefID() {
        return chefID;
    }

    /**
     * Set the chef ID
     * @param chefID an integer that identifies the chef
     */
    public void setChefID(int chefID) {
        this.chefID = chefID;
    }

    /**
     * Get the chef state 
     * @return chef state
     */
    public int getChefState(){
        return this.state;
    }
    
    /**
     * Set the chef current state 
     * @param state an integer that identifies the current state of the chef
     */
    public void setState(int state){
        this.state = state;
    }
    
    /**
     *   
     */
    
    private void watchTheNews() {
    	ReturnValue ret = null;
    	try
        { ret = kitchen.watchTheNews();
        }
        catch (RemoteException e)
        { 
          System.exit (1);
        }
    	this.state = ret.getStateValue();
    }
    
    private void startPreparation() {
    	ReturnValue ret = null;
    	try
        { ret = kitchen.startPreparation();
        }
        catch (RemoteException e)
        { 
          System.exit (1);
        }
    	this.state = ret.getStateValue();
    }
    
    private void continuePreparation() {
    	ReturnValue ret = null;
    	try
        { ret = kitchen.continuePreparation();
        }
        catch (RemoteException e)
        { 
          System.exit (1);
        }
    	this.state = ret.getStateValue();
    }
    
    private void proceedToPresentation() {
    	ReturnValue ret = null;
    	try
        { ret = kitchen.proceedToPresentation();
        }
        catch (RemoteException e)
        { 
          System.exit (1);
        }
    	this.state = ret.getStateValue();
    }
    
    private boolean haveAllPortionsBeenDelivered() {
    	boolean ret = false;
    	try
        { ret = kitchen.haveAllPortionsBeenDelivered();
        }
        catch (RemoteException e)
        { 
          System.exit (1);
        }
    	return ret;
    }
    
    private void haveNextPortionReady() {
    	ReturnValue ret = null;
    	try
        { ret = kitchen.haveNextPortionReady();
        }
        catch (RemoteException e)
        { 
          System.exit (1);
        }
    	this.state = ret.getStateValue();
    }
    
    private boolean hasTheOrderBeenCompleted() {
    	boolean ret = false;
    	try
        { ret = kitchen.hasTheOrderBeenCompleted();
        }
        catch (RemoteException e)
        { 
          System.exit (1);
        }
    	return ret;
    }
    
    private void cleanUp() {
    	ReturnValue ret = null;
    	try
        { ret = kitchen.cleanUp();
        }
        catch (RemoteException e)
        { 
          System.exit (1);
        }
    	this.state = ret.getStateValue();
    }
    
    private void alertTheWaiter() {
    	ReturnValue ret = null;
    	try
        { ret = bar.alertTheWaiter();
        }
        catch (RemoteException e)
        { 
          System.exit (1);
        }
    	this.state = ret.getStateValue();
    }
}
