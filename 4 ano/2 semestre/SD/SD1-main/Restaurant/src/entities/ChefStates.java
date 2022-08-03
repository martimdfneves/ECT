package entities;

/**
 * Enum class that enumerates all chef states
 * @author Martim Neves
 * @author Tiago Dias
 */
public enum ChefStates {
	
	/**
     * Waiting for and order state - Initial state
     */
    WAITING_FOR_AN_ORDER("WAFOR"),

    /**
     * Preparing the course state
     */
    PREPARING_THE_COURSE("PRPCS"),

	/**
     * Dishing the portions state
     */
    DISHING_THE_PORTIONS("DSHPT"),

    /**
     * Delivering the portions state
     */
    DELIVERING_THE_PORTIONS("DLVPT"),
    
    /**
     * Closing service state - final state
     */
    CLOSING_SERVICE("CLSSV");

	/**
     * State description
     */
    private final String description;
    
    /**
     * Constructor
     */
    private ChefStates(String description){
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