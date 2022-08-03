package sharedRegions.table;

/**
 * Interface class with the Student methods corresponding to his table states
 * @author Martim Neves
 * @author Tiago Dias
 */
public interface TableStudent {
	 /**
     * Student informs companion of his course choice.
     * Notifies the companion that he has chosen.
     * Updates internal state of the student to CHATTING_WITH_COMPANIONS.
     */
    public void informCompanion();
    
    /**
     * Increases the number of requests received by the first student.
     * Updates internal state of the student to ORGANIZING_THE_ORDER.
     */
    public void prepareTheOrder();
    
    /**
     * Student chats with his companions.
     * Updates internal state of the student to CHATTING_WITH_COMPANIONS.
     */
    public void joinTheTalk();
    
    /**
     * Updates internal state of the student to CHATTING_WITH_COMPANIONS.
     * Waits for everybody to finished eating.
     */
    public void hasEverbodyFinished();
    
    /**
     * Students wait until everyone is served and then start eating.
     * Updates internal state of the student to ENJOYING_THE_MEAL.
     */
    public void startEating();
    
    /**
     * Updates internal state of the student to CHATTING_WITH_COMPANIONS.
     */
    public void endEating();
    
    /**
     * Student waits until the bill is ready and then pays it.
     * Notifies the waiter that the bill is payed.
     */
    public void honourTheBill();
    
    /**
     * Student waits to be informed by his companions and increases the number of requests received by the first student.
     * Updates internal state of the student to ORGANIZING_THE_ORDER.
     */
    public void addUpOnesChoice();
    
    /**
     * Checks if everybody has chosen.
     * @return if everybody has chosen.
     */
    public boolean hasEverybodyChosen();
    
    /**
     * Student calls the waiter when everyone has chosen and waits for the waiter to get the pad.
     * Notifies the waiter that the order has been described.
     * Updates internal state of the student to ORGANIZING_THE_ORDER.
     */
    public void describeTheOrder();
}
