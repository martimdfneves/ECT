package clientSide.entities;

import genclass.*;
import clientSide.stubs.BarStub;
import clientSide.stubs.KitchenStub;

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
     * Reference to the stub of the bar
     */
    private BarStub bar;
    
    /**
     * Reference to the stub of the kitchen
     */
    private KitchenStub kitchen;

    /**
     * Chef constructor
     * 
     * @param chefID the Chef ID
     * @param state current Chef state
     * @param kitchen reference to the stub of the kitchen
     * @param bar reference to the stub of the bar
     */
    public Chef(int chefID, int state, KitchenStub kitchen, BarStub bar){
        this.setChefID(chefID);
        this.state = state;
        this.kitchen = kitchen;
        this.bar = bar;
    }

    /**
     * Implements the life cycle of the chef
     */
    @Override
    public void run(){
        boolean firstCourse = true;
        kitchen.watchTheNews();
        kitchen.startPreparation();

        do{
            if(!firstCourse){
                kitchen.continuePreparation();
            }
            else{
                firstCourse = false;
            }
            kitchen.proceedToPresentation();
            bar.alertTheWaiter();

            while(!kitchen.haveAllPortionsBeenDelivered()){
                kitchen.haveNextPortionReady();
                bar.alertTheWaiter();
            }
        }
        while(!kitchen.hasTheOrderBeenCompleted());
        
        kitchen.cleanUp();
        System.out.println("\033[42m Chef cleaned up the kitchen \033[0m");
        GenericIO.writelnString("Chef End Of Life");
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
}
