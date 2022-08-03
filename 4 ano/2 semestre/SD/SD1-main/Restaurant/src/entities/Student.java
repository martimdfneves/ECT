package entities;
import sharedRegions.bar.Bar;
import sharedRegions.table.Table;
import utils.SimPar;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Student thread:
 * Implements the life-cycle of a Student and stores the variables referent to
 * that student and his current state.
 * @author Martim Neves
 * @author Tiago Dias
 */
public class Student extends Thread{
	/**
     * Current Student state
     */
    private StudentStates state;
    
    /**
     * Checks if the student has been saluted by the waiter
     */
    private boolean salutedByWaiter = false;
    
    /**
     * Checks if the student has read the menu
     */
    private boolean readTheMenu = false;
    
    /**
     * Checks if the student has been served by the waiter 
     */
    private boolean servedByWaiter = false;
    
    /**
     * Checks if this student was the last to eat 
     */
    private boolean lastStudent = false;
    
    /**
     * Student unique id
     */
	private static int uid = 0;

    /**
     * Student ID
     */
    private int id;
    
    /**
     * Student seat at the table
     */
    private int tableSeat;
    
    /**
     * Instance of bar
     */
    private Bar bar;
    
    /**
     * Instance of table
     */
    private Table table;

    /**
     * Student constructor
     *
     * @param state student state
     * @param bar instance of bar 
     * @param table instance of table
     */
    public Student(StudentStates state, Bar bar, Table table){
        this.id = uid++;
        this.state = state;
        this.bar = bar;
        this.table = table;
    }

	/**
     * Implements the life cycle of the student
     */
    @Override
    public void run(){
    	// Walking to the restaurant
		//System.out.println("Student"+id+": Walk a bit");
        walkABit();
        
        //System.out.println("Student"+id+": Entering");
        bar.enter();
        
        //System.out.println("Student"+id+": Reading the menu");
        bar.readTheMenu();

        if(bar.FirstStudent(id)){
        	//System.out.println("Student"+id+": Preparing the order");
            table.prepareTheOrder();
            
            while(!table.hasEverybodyChosen()){
            	//System.out.println("Has everybody chosen: "+table.hasEverybodyChosen());
                table.addUpOnesChoice();
            }
            
            //System.out.println("Student"+id+": Calling the waiter");
            bar.callTheWaiter();
            
            //System.out.println("Student"+id+": Describing the order");
            table.describeTheOrder();
            
            //System.out.println("Student"+id+": Joining the talk");
            table.joinTheTalk();
        }
        else{
        	//System.out.println("Student"+id+": Informing companion");
            table.informCompanion();
        }

        int nCourse = 0;
        while(nCourse < SimPar.nCourses){
            // The student waits to be served
        	table.waitingToBeServed(id);
            
            //System.out.println("Student"+id+": Start eating");
            table.startEating();
            
            table.endEating();
            //System.out.println("Student"+id+": End eating");
            
            table.hasEverbodyFinished();
            
            //System.out.println("Student"+id+": Signaling the waiter");
            bar.signalTheWaiter();
            nCourse++;
        }

        if(bar.shouldHaveArrivedEarlier(id)){
        	//System.out.println("Student"+id+": Should have arrived earlier");
        	
        	System.out.println("Student"+id+": Honouring the bill");
            table.honourTheBill();
        }
        try {
			Thread.sleep(50);
        } catch (InterruptedException e) {
            System.out.println("run - One thread of Student was interrupted.");
            System.exit(1);
        }
        
        //System.out.println("Student"+id+": Exiting");
        bar.exit();
        
        System.out.printf("Student" +id+ ": End Of Life\n");
    }    
    
    /**
     * Student walks outside for a random time
     */
    public void walkABit() {
    	int randomWalk = ThreadLocalRandom.current().nextInt(1, 100);
		
		try {
			Thread.sleep(randomWalk);
        } catch (InterruptedException e) {
            System.out.println("run - One thread of Student was interrupted.");
            System.exit(1);
        }
    }
    
	/**
     * Get the readTheMenu flag      
     */
    public boolean getReadTheMenu(){
    	return readTheMenu;
    }

    /**
     * Set the readTheMenu flag      
     */
    public void setReadTheMenu(){
    	readTheMenu = true;
    }

    /**
     * Get the student table seat number 
     */
    public int getTableSeat(){
    	return tableSeat;
    }

    /**
     * Set the table seat number 
     * 
     * @param tableSeat the seat number of the student
     */
    public void setTableSeat(int tableSeat){
    	this.tableSeat = tableSeat;
    }

    /**
     * Get the servedByWaiter flag
     */
    public boolean servedByWaiter(){
    	return servedByWaiter;
    }

    /**
     * Set the servedByWaiter flag
     * 
     * @param served the served boolean value
     */
    public void setServedByWaiter(boolean served){
    	this.servedByWaiter = served;
    }

    /**
     * Get the saluetedByWaiter flag
     */
    public boolean getSalutedByWaiter(){
    	return salutedByWaiter;
    }

    /**
     * Set the salutedByWaiter flag
     */
    public void setSalutedByWaiter(){
    	salutedByWaiter = true;
    }

    /**
     * Set the student state 
     * 
     * @param state instance of StudentState
     */
    public void setStudentState(StudentStates state){
         this.state = state;
    }

    /**
     * Get the student state 
     */
    public StudentStates getStudentState(){
         return state; 
    }

    /**
     * Get the student ID 
     */
    public int getID(){
         return id;
    }
    
    /**
     * Checks if he's the lastStudent 
     */
    public boolean getLastStudent (){
    	return lastStudent;
    }
    
    /**
     * Set the lastStudent value
     * 
     * @param value value of the lastStudent flag
     */
    public void setLastStudent(boolean value){
        lastStudent = value;
   }
}
