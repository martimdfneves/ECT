package clientSide.entities;

import clientSide.stubs.BarStub;
import clientSide.stubs.TableStub;
import serverSide.main.SimPar;

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
    private int state;
    
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
     * The student ID
     */
    private int sID;
    
    /**
     * Student seat at the table
     */
    private int tableSeat; 
    
    /**
     * Reference to the stub of the bar.
     */
    private BarStub bar;
    
    /**
     * Reference to the stub of the table
     */
    private TableStub table;

    
    /**
     * Student constructor
     *
     * @param id student id
     * @param state current student state
     * @param bar reference to the stub of the bar.
     * @param table reference to the stub of the table.
     */
    public Student(int id, int state, BarStub bar, TableStub table){
        this.sID=id;
        this.state = state;
        this.bar = bar;
        this.table = table;
    }

    /**
     * Implements the life cycle of the student
     */
    @Override
    public void run(){
        walkABit();
        bar.enter();
        bar.readTheMenu();

        if(bar.FirstStudent(this.sID)){
            table.prepareTheOrder();
            while(!table.hasEverybodyChosen()){
                table.addUpOnesChoice();
            }
            
            bar.callTheWaiter();
            table.describeTheOrder();
            table.joinTheTalk();
        }
        else{
            table.informCompanion();
        }

        int courses = 0;
        while(courses < SimPar.COURSES){
            table.waitingToBeServed(this.sID);
            table.startEating();
            table.endEating();
           
            table.hasEverbodyFinished();
            bar.signalTheWaiter(this.sID);
            courses++;
        }

        if(bar.shouldHaveArrivedEarlier(this.sID)){
            table.honourTheBill();
            System.out.printf("\033[42m Student[%d] payed the bill \033[0m\n", this.sID);
        }

        bar.exit();
        System.out.printf("Student[" + this.sID + "] End Of Life\n");
    }    
    
    /**
     * Student walks outside for a random time
     * Internal operation
     */
    public void walkABit() {
        try {
            sleep((long) (3 + 100 * Math.random()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
    * Student start eating for a random time interval 
    * Internal operation
    */
    public void studentEating() {
        try {
            sleep((long) (1 + 100 * Math.random()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Get the lastStudent flag    
     * @return lastStudent flag  
     */
    public boolean isLastStudent(){
        return lastStudent;
    }

    /**
     * Set lastStudent boolean flag  
     * @param lastStudent the boolean value of the flag 
     */
    public void setLastStudent(boolean lastStudent){
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
    public void setTableSeat(int tableSeat) {
        this.tableSeat = tableSeat;
    }

    /**
     * Get the servedByWaiter flag
     * @return servedByWaiter flag
     */
    public boolean servedByWaiter() {
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
    public void setSalutedByWaiter(){
        this.salutedByWaiter = true;
    }

    /**
     * Set the current student state 
     * @param state an integer that represents the current student state
     */
    public void setState(int state){
        this.state = state;
    }

    /**
     * Get the student state 
     * @return student state
     */
    public int getStudentState(){
        return this.state; 
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

}
