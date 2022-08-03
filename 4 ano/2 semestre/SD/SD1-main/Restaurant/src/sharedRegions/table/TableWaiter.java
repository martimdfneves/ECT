package sharedRegions.table;

/**
 * Interface class with the Waiter methods corresponding to his table states
 * @author Martim Neves
 * @author Tiago Dias
 */
public interface TableWaiter {
	/**
     * Waiter gets the pad and waits for the student to describe the order.
     * Waiter notifies the student that he has the pad.
     * Updates internal state of the waiter to TAKING_THE_ORDER.
     */
    public void getThePad();
    
    /**
     * Waits while there's no one to be served.
     * When a student is waiting to be served the waiter serves and notifies him.
     */
    public void deliverPortion();
    
    /**
     * Checks if all the clients have been served.
     * Updates internal state of the waiter to WAITING_FOR_PORTION.
     * @return if all the clients have been served.
     */
    public boolean haveAllClientsBeenServed();
    
    /**
     * Waiter presents the bill and waits for the student to pay.
     * Waiter notifies the student that the bill is ready.
     * Updates internal state of the waiter to RECEIVING_PAYMENT.
     */
    public void presentTheBill();
}
