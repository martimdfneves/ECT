package serverSide.entities;

/**
 * Definition of the internal states of the waiter during his life cycle.
 * @author Martim Neves
 * @author Tiago Dias
 */
public final class WaiterState {
	
	/**
     * Appraising situation state - Initial and final state
     */
    public static final int APPRAISING_SITUATION = 0;

    /**
     * Presenting the menu state
     */
    public static final int PRESENTING_THE_MENU = 1;

    /**
     * Taking the order state
     */
    public static final int TAKING_THE_ORDER = 2;

    /**
     * Placing the order state
     */
    public static final int PLACING_THE_ORDER = 3;

    /**
     * Waiting for portion state
     */
    public static final int WAITING_FOR_PORTION = 4;

    /**
     * Processing the bill state
     */
    public static final int PROCESSING_THE_BILL = 5;

    /**
     * Receiving payment state
     */
    public static final int RECEIVING_PAYMENT = 6;
    
    /**
     *   It can not be instantiated.
     */
    private WaiterState(){}
}