package serverSide.entities;

import commInfra.Message;
import commInfra.ServerCom;
import serverSide.sharedRegions.SharedRegionInterface;

/**
 * Class with all the methods and attributes of the entities.
 */
public class ServiceProviderAgent extends Thread implements Chef, Waiter, Student{
	
	/**
	 * Communication channel.
	 */
	private ServerCom com;
    
	/**
     * Reference to the provided service.
     */
    private SharedRegionInterface shi;

   /**
    * Instantiation.
    *
    * @param com communication channel
    * @param shi reference to provided service
    */
    public ServiceProviderAgent (ServerCom com, SharedRegionInterface shi){
       this.com = com;
       this.shi = shi;
    }

    /**
     * Life cycle of the service provider agent.
     */
    @Override
    public void run (){
       Message message = (Message) com.readObject();
       message = shi.processAndReply(message);
       if (message != null) {
    	   com.writeObject(message);
       }
    }

    /** 
     * Student State
     */
    private int studentState;
    
    /**
     * Flag to determine if the student has been saluted by the waiter
     */
    private boolean salutedByWaiter = false;
    
    /**
     * Flag to determine if the student has read the menu
     */
    private boolean readTheMenu = false;
    
    /**
     * Flag to determine if the student has been served by the waiter 
     */
    private boolean servedByWaiter = false;
    
    /**
     * Flag to determine if this student was the last to eat 
     */
    private boolean lastStudent = false;
    
    /**
     * Student ID
     */
    private int sID;
    
    /**
     * Student seat at the table
     */
    private int tableSeat;

    /**
     * Get the lastStudent flag    
     * @return lastStudent flag  
     */
    public boolean isLastStudent() {
        return lastStudent;
    }

    /**
     * Set lastStudent boolean flag  
     * @param lastStudent the boolean value of the flag 
     */
    public void setLastStudent(boolean lastStudent) {
        this.lastStudent = lastStudent;
    }

    /**
     * Get the readTheMenu flag    
     * @return readTheMenu flag  
     */
    public boolean getReadTheMenu() {
        return readTheMenu;
    }

    /**
     * Set true the readTheMenu flag       
     */
    public void setReadTheMenu() {
        this.readTheMenu = true;
    }

    /**
     * Get the student table seat number 
     * @return student table seat number
     */
    public int getTableSeat() {
        return tableSeat;
    }

    /**
     * Set the table seat number 
     * @param tableSeat the seat number of the student
     */
    public void setTableSeat(int tableSeat){
        this.tableSeat = tableSeat;
    }

    /**
     * Get the servedByWaiter flag
     * @return servedByWaiter flag
     */
    public boolean servedByWaiter(){
        return servedByWaiter;
    }

    /**
     * Set the servedByWaiter flag
     * @param served the served boolean value
     */
    public void setServedByWaiter(boolean served) {
        this.servedByWaiter = served;
    }

    /**
     * Get the salutedByWaiter flag
     * @return salutedByWaiter flag
     */
    public boolean getSalutedByWaiter() {
        return salutedByWaiter;
    }

    /**
     * Set true the salutedByWaiter flag
     */
    public void setSalutedByWaiter() {
        this.salutedByWaiter = true;
    }

    /**
     * Set the current student state 
     * @param state an integer that represents the current student state
     */
    public void setStudentState(int state){
        this.studentState = state;
    }

    /**
     * Get the student state 
     * @return student state
     */
    public int getStudentState(){
        return this.studentState; 
    }

    /**
     * Get the student ID 
     * @return student id
     */
    public int getID(){
        return this.sID;
    }

    /**
     * Set the student ID 
     * @param id student id
     */
    public void setID(int id){
        this.sID = id;
    }

    /**
     * Student start eating for a random time interval 
     * Internal operation
     */
    public void studentEating(){
        try {
            sleep((long) (1 + 100 * Math.random()));
        } 
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * The waiter ID
     */
    private int waiterID;

    /**
     * Current Waiter state
     */
    private int waiterState;
    
    /**
     * Checks if the waiter can go home
     */
    private boolean canGoHome = false;

    /**
     * Get the waiter ID 
     * @return waiterID
     */
    public int getWaiterID() {
        return waiterID;
    }

    /**
     * Set the waiter ID 
     * @param waiterID waiter ID
     */
    public void setWaiterID(int waiterID) {
        this.waiterID = waiterID;
    }

    /**
     * Get the canGoHome flag  
     * @return canGoHome flag    
     */
    public boolean CanGoHome() {
        return canGoHome;
    }

    /**
     * Set the canGoHome flag      
     */
    public void setCanGoHome() {
        this.canGoHome = true;
    }

    /**
     * Set the waiter state
     * @param state an integer that represents the current waiter state
     */
    public void setWaiterState(int state){
        this.waiterState = state;
    }

    /**
     * Get the current waiter state 
     * @return waiter state
     */
    public int getWaiterState(){
        return this.waiterState;
    }

    /**
     * The Chef ID
     */
    private int chefID;

    /**
     * Current Chef state
     */
    private int chefState;

    /**
     * Get the chef ID 
     * @return chefID
     */
    public int getChefID() {
        return chefID;
    }

    /**
     * Set the chef ID
     * @param chefID an integer that identifies the chef
     */
    public void setChefID(int chefID) {
        this.chefID = chefID;
    }

    /**
     * Set the chef current state 
     * @param state an integer that identifies the current state of the chef
     */
    public void setChefState(int state){
        this.chefState = state;
    }

    /**
     * Get the chef state 
     * @return chef state
     */
    public int getChefState() {
        return this.chefState;
    }
}