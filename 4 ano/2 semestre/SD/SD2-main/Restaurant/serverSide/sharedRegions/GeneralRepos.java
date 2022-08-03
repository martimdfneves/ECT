package serverSide.sharedRegions;

import java.util.Objects;

import serverSide.main.SimPar;
import serverSide.entities.*;
import genclass.GenericIO;
import genclass.TextFile;

/**
 * General Repository for the program.
 * Implements the LoggerInterface interface, with a logger that writes the logs
 * to a file.
 * Also has the intervening entities and the shared regions needed to print the logs.
 * 
 * @author Martim Neves
 * @author Tiago Dias
 */
public class GeneralRepos{
    
	/**
	 * Name of log File
	 */
	private final String logFileName;

	/**
     * State of the chef
     */
	private int chefState;

	/**
     * State of the waiter
     */
	private int waiterState;

    /**
     * State of the students
     */
	private int[] studentState;
	
	/**
     * Number of the course
     */
    private int NCourse = 0;

    /**
     * Number of the portion
     */
    private int NPortion = 0;

    /**
     * Flag for students order
     */
    private int orderID = -1;

    /**
     * Array with the seats ID
     */
    private String[] order = new String[SimPar.STUDENTS];
    
    /**
     * Auxiliary order flag
     */
    private int orderFlag = 0;

    /**
     * Auxiliary match flag
     */
    boolean match = false;

    /**
     * Logger constructor
     * @param logFileName name of the logging file
     */
    public GeneralRepos (String logFileName){
        for(int y =0; y< SimPar.STUDENTS; y++){
            order[y] = "_";
        }
        if ((logFileName == null) || Objects.equals(logFileName, ""))
            this.logFileName = "logger";
        else this.logFileName = logFileName;

        waiterState = WaiterState.APPRAISING_SITUATION;
        chefState = ChefState.WAITING_FOR_AN_ORDER;
        studentState = new int[SimPar.STUDENTS];
        for (int i= 0; i < SimPar.STUDENTS;i ++)
            studentState[i] = StudentState.GOING_TO_THE_RESTAURANT;

        reportInitialStatus();
    }

    /**
     * Updates waiter state
     * @param state the new waiter state
     */
    public synchronized void setWaiterState (int state){
		waiterState = state;
		reportStatus();
	}

    /**
     * Updates student state
     * @param id student id
     * @param state the new student state
     */
    public synchronized void setStudentState (int id, int state){
        if(state == StudentState.TAKING_A_SEAT_AT_THE_TABLE){
            studentState[id] = state;
            setStudentsOrder(id);
        }
        studentState[id] = state;
		reportStatus();
    }

    /**
     * Updates chef state
     * @param state the new chef state
     */
	public synchronized void setChefState (int state){
		chefState = state;
		reportStatus();
	}

	/**
	 *  Set nCourse number.
	 *  @param number number to add to nCourse.
	 */
	public synchronized void setNCourse (int number){
		NCourse += number;
	}

	/**
	 * Set nPortion number.
	 * @param number number to add to nPortion.
	 */
	public synchronized void setNPortion (int number){
         if(NCourse == 0 && NPortion == 0 && number == 1){
             NCourse++;
         }
         NPortion +=number;

         if(NPortion == 8){
             NPortion = 1;
             NCourse++;
         }
    }

	/**
	 * Set student order.
	 * @param id student id
	 */
	public synchronized void setStudentsOrder (int id){
		if (id != orderID){
            String s=Integer.toString(id);  
            order[orderFlag] = s;
            orderFlag++;
            orderID = id; 
            reportStatus();
        }
	}

	 /**
     * Cleans the log file and logs the description of the problem and the header,
     * and the initial state of the log file.
     */
	private void reportInitialStatus (){
		TextFile log = new TextFile();                      

		if (!log.openForWriting(".", logFileName)){ 
			GenericIO.writelnString("The operation of creating the file " + logFileName + " failed!");
			System.exit(1);
         }
      
		log.writelnString("                                          The Restaurant - Description of the internal state\n");
		log.writelnString("Chef   Waiter  Stu0   Stu1   Stu2   Stu3   Stu4   Stu5   Stu6  NCourse  NPortion           Table");
		log.writelnString("State  State  State  State  State  State  State  State  State                     Seat0 Seat1 Seat2 Seat3 Seat4 Seat5 Seat6");
		
		if (!log.close()){ 
			GenericIO.writelnString("The operation of closing the file " + logFileName + " failed!");
			System.exit(1);
         }
		reportStatus();
	}

	/**
     * Logs current state of entities or other messages according to the requested type
     */
    private void reportStatus (){
        TextFile log = new TextFile();                      

        String lineStatus = "";                              

        if (!log.openForAppending(".", logFileName)){
            GenericIO.writelnString("The operation of opening for appending the file " + logFileName + " failed!");
            System.exit (1);
        }

        switch(chefState){ 
	        case 0:     lineStatus += "WAFOR ";break;
	        case 1:   lineStatus += "PRPCS ";break;
	        case 2: lineStatus += "DSHPT ";break;
	        case 3:      lineStatus += "DLVPT ";break;
	        case 4:         lineStatus += "CLSSV ";break;
        }

        switch (waiterState){ 
	        case 0:     lineStatus += " APPST ";break;
	        case 1:   lineStatus += " PRSMN ";break;
	        case 2:         lineStatus += " TKODR ";break;
	        case 3:      lineStatus += " PCODR ";break;
	        case 4:     lineStatus += " WTFPT ";break;
	        case 5:   lineStatus += " PRCBL ";break;
	        case 6:         lineStatus += " RECPM ";break;
        }

        for (int i = 0; i < SimPar.STUDENTS; i++)
            switch (studentState[i]){ 
	            case 0:  lineStatus += " GGTRT ";break;
	            case 1:         lineStatus += " TKSTT ";break;
	            case 2:        lineStatus += " SELCS ";break;
	            case 3:   lineStatus += " OGODR ";break;
	            case 4:  lineStatus += " CHTWC ";break;
	            case 5:         lineStatus += " EJYML ";break;
	            case 6:        lineStatus += " PYTBL ";break;
	            case 7:   lineStatus += " GGHOM ";break;
            }

        lineStatus += String.format(" %4s     %4s ", NCourse, NPortion);//" " + inQueue + "    " + inFlight + "    " + inDestination;
        
        lineStatus += String.format("    %4s  %4s  %4s  %4s  %4s  %4s  %4s  ", order[0], order[1], order[2], order[3], order[4], order[5], order[6]);

        log.writelnString (lineStatus);
        if (!log.close()){ 
            GenericIO.writelnString("The operation of closing the file " + logFileName + " failed!");
            System.exit(1);
        }
    }

    /**
	 *  Write a specific state line at the end of the logging file, for example an message informing that
	 *  the plane has arrived.
	 *  @param message message to write in the logging file
	 */
	public synchronized void reportSpecificStatus (String message){
		TextFile log = new TextFile ();                      

		if (!log.openForAppending (".", logFileName)){
			GenericIO.writelnString ("The operation of opening for appending the file " + logFileName + " failed!");
			System.exit (1);
		}

		log.writelnString (message);
		if (!log.close ()){ 
			GenericIO.writelnString ("The operation of closing the file " + logFileName + " failed!");
			System.exit (1);
		}
	}
}


