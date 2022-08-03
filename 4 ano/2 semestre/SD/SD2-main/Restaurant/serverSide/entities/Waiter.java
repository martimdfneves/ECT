package serverSide.entities;

/**
* Waiter Thread
* Class used to simulate a Waiter's life cycle   
*/
public interface Waiter {

	/**
     * Set the waiter ID
     * @param id waiter ID
     */
    public void setWaiterID(int id);

    /**
     * Get the waiter ID 
     * @return waiter ID
     */
    public int getWaiterID();

    /**
     * Get the canGoHome flag  
     * @return canGoHome flag    
     */
    public boolean CanGoHome();

    /**
     * Set true the canGoHome flag      
     */
    public void setCanGoHome();

    /**
     * Set the waiter state
     * @param state Waiter state
     */
    public void setWaiterState(int state);

    /**
     * Get the waiter state 
     * @return waiter state
     */
    public int getWaiterState();

}
