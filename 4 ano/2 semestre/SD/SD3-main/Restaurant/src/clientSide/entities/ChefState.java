package clientSide.entities;

/**
 * Definition of the internal states of the chef during his life cycle.
 * @author Martim Neves
 * @author Tiago Dias
 */
public final class ChefState {
    
	/**
     * Waiting for and order state - Initial state
     */
    public static final int WAITING_FOR_AN_ORDER = 0;

    /**
     * Preparing the course state
     */
    public static final int PREPARING_THE_COURSE = 1;

    /**
     * Dishing the portions state
     */
    public static final int DISHING_THE_PORTIONS = 2;

    /**
     * Delivering the portions state
     */
    public static final int DELIVERING_THE_PORTIONS = 3;

    /**
     * Closing service state - final state
     */
    public static final int CLOSING_SERVICE = 4;
    
    /**
     * It can not be instantiated.
     */
    private ChefState(){}
}