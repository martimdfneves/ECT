package main;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import entities.*;
import utils.SimPar;

/**
 * Logger for the program.
 * Implements the LoggerInterface interface, with a logger that writes the logs
 * to a file.
 * Also has the intervening entities and the shared regions needed to print the logs.
 * 
 * @author Martim Neves
 * @author Tiago Dias
 */
public class Logger {
    
	/**
	 * name of log File
	 */
    private final String logFileName;
    
    /**
     * State of the chef
     */
    private ChefStates chefState;

    /**
     * State of the waiter
     */
    private WaiterStates waiterState;

    /**
     * State of the students
     */
    private StudentStates[] studentState;

    /**
     * Number of the course
     */
    private int nCourse = 0;

    /**
     * Number of the portion
     */
    private int nPortion = 0;

    /**
     * Flag for students order
     */
    private int orderID = -1;

    /**
     * Array with the seats ID
     */
    private String[] seatsID = new String[SimPar.students_number];
    
    /**
     * Auxiliary order flag
     */
    private int orderFlag = 0;
    
    /**
     * Logger constructor
     * 
     * @param logFileName name of the logging file
     */
    public Logger (String logFileName){
        for(int i = 0; i < SimPar.students_number; i++){
        	seatsID[i] = "-";
        }
        
    	this.logFileName = logFileName;

        waiterState = WaiterStates.APPRAISING_SITUATION;
        chefState = ChefStates.WAITING_FOR_AN_ORDER;
        studentState = new StudentStates[SimPar.students_number];
        
        for (int i = 0; i < SimPar.students_number; i ++) {
            studentState[i] = StudentStates.GOING_TO_THE_RESTAURANT;
        }   
        initLog();
    }

    /**
     * Updates waiter state
     * @param state instance of WaiterStates
     */
	public synchronized void updateWaiterState(WaiterStates state){
		waiterState = state;
		printLog();
	}

	/**
     * Updates student state
     * @param id student id
     * @param state instance of StudentStates
     */
    public synchronized void updateStudentState(int id, StudentStates state){
        if(state == StudentStates.TAKING_A_SEAT_AT_THE_TABLE){
            studentState[id] = state;
            setStudentsOrder(id);
        }
        studentState[id] = state;
        printLog();
    }

    /**
     * Updates chef state
     * @param state instance of ChefStates
     */
	public synchronized void updateChefState(ChefStates state){
		chefState = state;
		printLog();
	}

	/**
	 *  Set nCourse number.
	 *  @param number number to add to nCourse.
	 */
   	public synchronized void setNCourse (int number){
   		nCourse += number;
	}

   	/**
	 * Set nPortion number.
	 * @param number number to add to nPortion.
	 */
   	public synchronized void setNPortion (int number){
         if(nCourse == 0 && nPortion == 0 && number == 1){
        	 nCourse++;
         }
         nPortion += number;

         if(nPortion == 8){
        	 nPortion = 1;
             nCourse++;
         }
    }

   	/**
	 * Set student order.
	 * @param id student id
	 */
    public synchronized void setStudentsOrder (int id){
		if (id != orderID){
            String s=Integer.toString(id);  
            seatsID[orderFlag] = s;
            orderFlag++;
            orderID = id;
            printLog();
        }
	}

    /**
     * Cleans the log file and logs the description of the problem and the header,
     * and the initial state of the log file.
     */
	private void initLog(){
		try{
			FileWriter fw = new FileWriter(logFileName);
            BufferedWriter bw = new BufferedWriter(fw);
            
            // Header
            // 1st line
            bw.write("                                        The Restaurant - Description of the internal state\n");
            
            bw.write(" Chef");
            bw.write(" Waiter");
            
            for (int i = 0; i < this.studentState.length; i++) {
                bw.write("  Stu" +i);
            }
            
            bw.write("   NCourse");
            bw.write("   NPortion");
            
            bw.write("                      Table\n");
            
            //2nd line
            bw.write("State State ");
            for(int i = 0; i < 7; i++) {
            	bw.write(" State");
            }
            
            bw.write("                         ");
            for(int i = 0; i < 7; i++) {
            	bw.write("Seat"+i+" ");
            }
            // end header
            
            bw.newLine();
            bw.write(chefState.toString()+" ");
            bw.write(waiterState.toString()+"  ");
            
            for (int i = 0; i < this.studentState.length; i++) {
            	bw.write(studentState[i].toString()+" ");
            }
            bw.write("     "+nCourse);
            bw.write("          "+nPortion);	            
            
            // Seats on table		
            int index = 0; 
						
			bw.write("        ");
			for(int i = 0; i < seatsID.length; i++) {
				if(index < 7) {
					if( (index % 2 == 0) && (index != 0) ) {
						bw.write(" -    ");
					}
					else {
						bw.write(" -    ");					
					}
				}
				index++;
			}
            
            bw.newLine();
            bw.close();
            fw.close();
		}catch (IOException e) {
            System.out.println("initLog error - Could not write to logger file.");
            System.exit(1);
		}
		printLog();
	}

	/**
     * Logs current state of entities or other messages according to the requested type
     */
    private void printLog (){
    	try {
    		FileWriter fw = new FileWriter(logFileName, true);
			BufferedWriter bw = new BufferedWriter(fw);
			
			// Entities States
			bw.write(chefState.toString()+" ");
            bw.write(waiterState.toString()+"  ");
			            
            for (int i = 0; i < studentState.length; i++) {
            	bw.write(studentState[i].toString()+" ");
            }
            
            bw.write("     "+nCourse);
            bw.write("          "+nPortion);
			            
            // Seats on table		
            int index = 0; 
						
            bw.write("        ");
			for(int i = 0; i < seatsID.length; i++) {
				if(index < 7) {
					if( (index % 2 == 0) && (index != 0) ) {
						bw.write(" "+seatsID[i]+"    ");
					}
					else {
						bw.write(" "+seatsID[i]+"    ");					
					}
				}
				index++;
			}
			
			bw.newLine();
            bw.close();
            fw.close();
    	}catch(IOException e){
            System.out.println("printLog error - Could not write to logger file.");
            System.exit(1);
        }
    }
}
