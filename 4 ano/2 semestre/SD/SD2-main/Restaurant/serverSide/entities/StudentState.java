package serverSide.entities;

/**
 * Definition of the internal states of the student during his life cycle.
 * @author Martim Neves
 * @author Tiago Dias
 */
public final class StudentState {
    
	/**
     * Going to the restaurant state - Initial state
     */
    public static final int GOING_TO_THE_RESTAURANT = 0;

    /**
     * Taking a seat at the table state
     */
    public static final int TAKING_A_SEAT_AT_THE_TABLE = 1;

    /**
     * Selecting the courses state
     */
    public static final int SELECTING_THE_COURSES = 2;

    /**
     * Organizing the order state
     */
    public static final int ORGANIZING_THE_ORDER = 3;

    /**
     * Chatting with companion state
     */
    public static final int CHATTING_WITH_COMPANIONS = 4;

    /**
     * Enjoying the meal state
     */
    public static final int ENJOYING_THE_MEAL = 5;

    /**
     * Paying the bill state
     */
    public static final int PAYING_THE_BILL = 6;

    /**
     * Going home state - final state
     */
    public static final int GOING_HOME = 7;

    /**
     * It can not be instantiated.
     */
    private StudentState(){}
}
