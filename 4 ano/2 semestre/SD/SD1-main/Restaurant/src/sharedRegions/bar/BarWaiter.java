package sharedRegions.bar;

import utils.Order;

/**
 * Interface class with the Waiter methods corresponding to his bar states
 * @author Martim Neves
 * @author Tiago Dias
 */
public interface BarWaiter {
	/**
     * Salutes the client and waits for the student to read the menu.
     * Notifies the student that he has been saluted.
     * Updates waiter state to PRESENTING_THE_MENU.
     */
	 public void saluteTheClient();
	 
	 /**
	  * Waiter returns to the bar.
	  * Updates waiter state to APPRAISING_SITUATION.
	  */
	 public void returningToTheBar();
	 
	 /**
      * Waiter prepares the bill.
      * Updates waiter state to PROCESSING_THE_BILL.
      */
	 public void prepareTheBill();
	 
	 /**
      * Waiter is waiting for a request.
      * Updates waiter state to APPRAISING_SITUATION.
      */
	 public Order lookAround();
	 
	 /**
	  * Waiter says goodbye to students.
	  * Updates waiter state to APPRAISING_SITUATION.
	  */
	 public void sayGoodbye();
}
