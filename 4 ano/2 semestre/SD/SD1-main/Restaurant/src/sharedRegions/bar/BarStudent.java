package sharedRegions.bar;

/**
 * Interface class with the Student methods corresponding to his bar states
 * @author Martim Neves
 * @author Tiago Dias
 */
public interface BarStudent {

	/**
     * Student signals the waiter to bring the next course.
     * Notifies the waiter that all the people already ate.
     * Updates internal state of the student to CHATTING_WITH_COMPANION.
     */
    public void signalTheWaiter();
    
    /**
     * Student calls the waiter when everyone has chosen.
     * Notifies a new order in the requests queue.
     * Updates internal state of the student to ORGANIZING_THE_ORDER.
     */
    public void callTheWaiter();
    
    /**
     * Student enters the restaurant, updates internal state of student to TAKING_A_SEAT_AT_THE_TABLE.
     * Notifies the waiter that a student has entered.
     * Adds a request of type SALUTE_THE_CLIENT to the queue.
     */
    public void enter();
    
    /**
     * Student leaves the restaurant, updates internal state of student to GOING_HOME.
     * Notifies the waiter that a student has left.
     * Adds a request of type SAY_GOODBYE to the queue.
     */
    public void exit();
    
    /**
     * Gets the id of the student who arrived last.
     * Notifies a new order in the requests queue.
     * Updates internal state of the student to PAYING_THE_BILL.
     * 
     * @return boolean if the student was the last to enter the restaurant
     */
    public boolean shouldHaveArrivedEarlier(int sID);
    
    /**
     * Student reads the menu.
     * Notifies the waiter that he has read the menu.
     * Updates internal state of the student to SELECTING_THE_COURSES.
     */
    public void readTheMenu();
}
