package commInfra;

/**
 * Definition of the simulation parameters.
 */
public final class SimPar{
	/**
     * Max Number of Students that will be served 
     */
    public static final int STUDENTS = 7;

    /**
     * Number of courses that the students will have
     */
    public static final int COURSES = 2;
    
    /**
     * Default name of the logger file
     */
    public static final String LOG_FILE_NAME = "log.txt";
    
    /**
     * Default bar host machine
     */
    public final static String BAR_HOST_NAME = "l040101-ws02.ua.pt";
    
    /**
     * Default bar host port
     */
    public final static int BAR_PORT = 22202;
    
    /**
     * Default kitchen host machine
     */
    public final static String KITCHEN_HOST_NAME = "l040101-ws03.ua.pt";
    
    /**
     * Default kitchen host port
     */
    public final static int KITCHEN_PORT = 22203;
    
    /**
     * Default table host machine
     */
    public final static String TABLE_HOST_NAME = "l040101-ws04.ua.pt";
    
    /**
     * Default table host port
     */
    public final static int TABLE_PORT = 22204;
    
    /**
     * Default logger host machine
     */
    public final static String LOGGER_HOST_NAME = "l040101-ws01.ua.pt";
    
    /**
     * Default logger host port
     */
    public final static int LOGGER_PORT = 22201;
    
    /**
     * Default RMI host machine
     */
    public final static String RMI_HOST_NAME = "l040101-ws08.ua.pt";
    
    /**
     *   Default RMI host port
     */
    public final static int RMI_PORT = 22208;
    
    /**
     *   Default Register host port
     */
    public final static int REG_PORT = 22200;
    
    /**
     *   Number of entities requesting shutdown in the kitchen.
     */

    public static final int E_Kit = 2;
     
    /**
     *   Number of entities requesting shutdown in the bar.
     */

    public static final int E_Bar = 3;
      
    /**
     *   Number of entities requesting shutdown in the table.
     */

    public static final int E_Tab = 2;
    
    /**
     *   Number of entities requesting shutdown in the general repository.
     */

    public static final int E_Repos = 3;

    /**
     * It can not be instantiated.
     */
    private SimPar(){ }
}
