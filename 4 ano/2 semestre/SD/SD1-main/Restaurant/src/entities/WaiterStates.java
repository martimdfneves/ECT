package entities;

/**
 * Enum class that enumerates all waiter states
 * @author Martim Neves
 * @author Tiago Dias
 */
public enum WaiterStates {
    
	/**
     * Appraising situation state - Initial and final state
     */
    APPRAISING_SITUATION("APPST"),

    /**
     * Presenting the menu state
     */
    PRESENTING_THE_MENU("PRSMN"),

    /**
     * Taking the order state
     */
    TAKING_THE_ORDER("TKODR"),

    /**
     * Placing the order state
     */
    PLACING_THE_ORDER("PCODR"),

    /**
     * Waiting for portion state
     */
    WAITING_FOR_PORTION("WTFPT"),

    /**
     * Processing the bill state
     */
    PROCESSING_THE_BILL("PRCBL"),

    /**
     * Receiving payment state
     */
    RECEIVING_PAYMENT("RECPM");

	/**
	 * State description
	 */
    private final String description;
	
    /**
     * Constructor
     */
    private WaiterStates(String description){
        this.description = description;
    }

    /**
     * Returns a String object representing the current state
     * @return state description
     */
    @Override
    public String toString() {
        return this.description;
    }
}