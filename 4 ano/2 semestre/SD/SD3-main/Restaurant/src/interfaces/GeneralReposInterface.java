package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *   Operational interface of a remote object of type General Repository.
 *
 *     It provides the functionality to access General Repository.
 */

public interface GeneralReposInterface extends Remote{
	
	/**
     * Updates waiter state
     * @param state the new waiter state
     * @throws RemoteException if either the invocation of the remote method, or the communication with the registry
     *                             service fails
     */
	
    public void setWaiterState (int state) throws RemoteException;
    
    /**
     * Updates student state
     * @param id student id
     * @param state the new student state
     * @throws RemoteException if either the invocation of the remote method, or the communication with the registry
     *                             service fails
     */
    public void setStudentState (int id, int state) throws RemoteException;
    
    /**
     * Updates chef state
     * @param state the new chef state
     * @throws RemoteException if either the invocation of the remote method, or the communication with the registry
     *                             service fails
     */
	public void setChefState (int state) throws RemoteException;
	
	/**
	 *  Set nCourse number.
	 *  @param number number to add to nCourse.
	 *  @throws RemoteException if either the invocation of the remote method, or the communication with the registry
     *                             service fails
	 */
	public void setNCourse (int number) throws RemoteException;
	
	/**
	 * Set nPortion number.
	 * @param number number to add to nPortion.
	 * @throws RemoteException if either the invocation of the remote method, or the communication with the registry
     *                             service fails
	 */
	public void setNPortion (int number) throws RemoteException;
	
	/**
	 * Set student order.
	 * @param id student id
	 * @throws RemoteException if either the invocation of the remote method, or the communication with the registry
     *                             service fails
	 */
	public void setStudentsOrder (int id) throws RemoteException;
    
	/**
     *   Operation server shutdown.
     *
     *   New operation.
     *
     *     @throws RemoteException if either the invocation of the remote method, or the communication with the registry
     *                             service fails
     */
    
    public void shutdown() throws RemoteException;
}
