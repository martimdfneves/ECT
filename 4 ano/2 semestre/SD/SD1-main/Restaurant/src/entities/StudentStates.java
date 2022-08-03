package entities;

/**
 * Enum class that enumerates all student states
 * @author Martim Neves
 * @author Tiago Dias
 */
public enum StudentStates {
    
	/**
     * Going to the restaurant state - Initial state
     */
    GOING_TO_THE_RESTAURANT("GGTRT"),

    /**
     * Taking a seat at the table state
     */
    TAKING_A_SEAT_AT_THE_TABLE("TKSTT"),

    /**
     * Selecting the courses state
     */
    SELECTING_THE_COURSES("SELCS"),

    /**
     * Organizing the order state
     */
    ORGANIZING_THE_ORDER("OGODR"),

    /**
     * Chatting with companion state
     */
    CHATTING_WITH_COMPANIONS("CHTWC"),

    /**
     * Enjoying the meal state
     */
    ENJOYING_THE_MEAL("EJYML"),

    /**
     * Paying the bill state
     */
    PAYING_THE_BILL("PYTBL"),

    /**
    * Going home state - final state
    */
    GOING_HOME("GGHOM");

    /**
     * State description
     */
    private final String description; 
	
    /**
     * Constructor
     */
    private StudentStates(String description){
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
