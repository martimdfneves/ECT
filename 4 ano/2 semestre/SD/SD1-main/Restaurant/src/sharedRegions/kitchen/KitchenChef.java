package sharedRegions.kitchen;

/**
 * Interface class with the Chef methods corresponding to his kitchen states
 * @author Martim Neves
 * @author Tiago Dias
 */
public interface KitchenChef {
	/**
     * Chef starts preparation.
     * Notifies the waiter that preparation just started.
     * Update the chef state to PREPARING_THE_COURSE.
     */
    public void startPreparation();
    
    /**
     * Update the chef state to DISHING_THE_PORTIONS.
     */
    public void proceedToPresentation();
    
    /**
     * Update the chef state to DISHING_THE_PORTIONS.
     */
    public void haveNextPortionReady();
    
    /**
     * Chef continues course preparation.
     * Update the chef state to PREPARING_THE_COURSE.
     */
    public void continuePreparation();
    
    /**
     * Chef clean up and closes his service.
     */
    public void cleanUp();
    
    /**
   	 * Checks if the order has been completed.
     * @return if the order has been completed.
     */
    public boolean hasTheOrderBeenCompleted();
    
    /**
   	 * Checks if all portions been delivered.
     * @return if all portions been delivered.
     */
    public boolean haveAllPortionsBeenDelivered();
    
    /**
     * Chef waits for the note to be delivered by the waiter.
     * Update the chef state to WAITING_FOR_AN_ORDER.
     */
    public void watchTheNews();
}
