package serverSide.entities;

/**
 * Interface with chef methods
 * @author Martim Neves
 * @author Tiago Dias
 */
public interface Chef {
	
	/**
     * Set the chef ID
     * @param id chef ID
     */
    public void setChefID(int id);

    /**
     * Get the chef ID 
     * @return chef ID
     */
    public int getChefID();

	/**
     * Set the chef state
     * @param state chef state
     */
    public void setChefState(int state);

    /**
     * Get the chef state 
     * @return chef state
     */
    public int getChefState();

}
