package serverSide.entities;

/**
* Student Thread.
* Class used to simulate a student's life cycle      
*/
public interface Student{
	
	/**
     * Get the readTheMenu flag    
     * @return readTheMenu flag  
     */
    public boolean getReadTheMenu();

    /**
    * Set true the readTheMenu flag      
    */
    public void setReadTheMenu();

    /**
     * Get the student table seat number 
     * @return student table seat number
     */
    public int getTableSeat();
    
    /**
     * Set table seat number 
     * @param tableSeat seat of the student
     */
    public void setTableSeat(int tableSeat);

    /**
     * Get the servedByWaiter flag
     * @return servedByWaiter flag
     */
    public boolean servedByWaiter();

    /**
     * Set the servedByWaiter flag
     * @param served the served boolean value
     */
    public void setServedByWaiter(boolean served);

    /**
     * Get the salutedByWaiter flag
     * @return salutedByWaiter flag
     */
    public boolean getSalutedByWaiter();

    /**
     * Set saluted by waiter boolean
     */
    public void setSalutedByWaiter(); 

    /**
     * Set the student state 
     * @param state student state
     */
    public void setStudentState(int state);

    /**
     * Get Student state
     * @return student state 
     */
    public int getStudentState();

    /**
     * Get Student ID 
     * @return student id
     */
    public int getID();
    
    /**
     * Set Student ID 
     * @param id student id
     */
    public void setID(int id);

    /**
    * Simulate the student eating with a random time interval 
    */
    public void studentEating();

    /**
     * Checks if he's the lastStudent 
     * @return lastStudent flag
     */
    public boolean isLastStudent();

    /**
     * Set the lastStudent value
     * @param lastStudent value of the lastStudent flag
     */
    public void setLastStudent(boolean lastStudent);
}
