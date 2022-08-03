package sharedRegions.kitchen;

/**
 * Interface class with the Waiter methods corresponding to his kitchen states
 * @author Martim Neves
 * @author Tiago Dias
 */
public interface KitchenWaiter {

	/**
     * Waiter hands the note to the chef and waits for the chef to start the preparation.
     * Notifies the chef that the note has been handed.
     * Updates internal state of the waiter to PLACING_THE_ORDER.
     */
    public void handNoteToTheChef();
    
    /**
     * Notifies the chef that the waiter has collected the portion.
     * Updates internal state of the waiter to WAITING_FOR_PORTION.
     */
    public void collectPortion();
    
    /**
     * Checks if all the clients have been served.
     * @return if all the clients have been served
     */
    public boolean haveAllClientsBeenServed();
}
