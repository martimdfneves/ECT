package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import commInfra.ReturnValue;

/**
 *   Operational interface of a remote object of type Bar.
 *
 *     It provides the functionality to access Bar.
 */

public interface BarInterface extends Remote{

	
	/**
     * Salutes the client and waits for the student to read the menu.
     * Notifies the student that he has been saluted.
     * @throws RemoteException if either the invocation of the remote method, or the communication with the registry
     *                             service fails
     */
    public ReturnValue saluteTheClient() throws RemoteException;
    
    /**
     * Notifies a new order in the requests queue.
     * @throws RemoteException if either the invocation of the remote method, or the communication with the registry
     *                             service fails
     */
    public ReturnValue alertTheWaiter() throws RemoteException;
    
    /**
     * Waiter returns to the bar.
     * @throws RemoteException if either the invocation of the remote method, or the communication with the registry
     *                             service fails
     */ 
    public ReturnValue returningToTheBar() throws RemoteException;
    
    /**
     * Waiter prepares the bill.
     * @throws RemoteException if either the invocation of the remote method, or the communication with the registry
     *                             service fails
     */
    public ReturnValue prepareTheBill() throws RemoteException;
    
    /**
     * Waiter is waiting for a request.
     * @return result the communication result
     * @throws RemoteException if either the invocation of the remote method, or the communication with the registry
     *                             service fails
     */
    public ReturnValue lookAround() throws RemoteException;
    
    /**
     * Waiter says goodbye to students.
     * @throws RemoteException if either the invocation of the remote method, or the communication with the registry
     *                             service fails
     */
    public ReturnValue sayGoodbye() throws RemoteException;
    
    /**
     * Student signals the waiter to bring the next course.
     * Notifies the waiter that all the people already ate.
     * @param sID student id
     * @param last if the student was the last to eat
     * @throws RemoteException if either the invocation of the remote method, or the communication with the registry
     *                             service fails
     */
    public ReturnValue signalTheWaiter(int sID, boolean last) throws RemoteException;
    
    /**
     * Student calls the waiter when everyone has chosen.
     * Notifies a new order in the requests queue.
     * @param studentId id of the student
     * @throws RemoteException if either the invocation of the remote method, or the communication with the registry
     *                             service fails
     */
    public ReturnValue callTheWaiter(int studentId) throws RemoteException;
    
    /**
     * Student enters the restaurant.
     * Notifies the waiter that a student has entered.
     * @param sID student id
     * @throws RemoteException if either the invocation of the remote method, or the communication with the registry
     *                             service fails
     */
    public ReturnValue enter(int sID) throws RemoteException;
    
    /**
     * Checks if the student was the first to enter
     * @param sID student id
     * @return true if it was the first to enter, false otherwise
     * @throws RemoteException if either the invocation of the remote method, or the communication with the registry
     *                             service fails
     */
    public boolean FirstStudent(int sID) throws RemoteException;
    
    /**
     * Student leaves the restaurant.
     * Notifies the waiter that a student has left.
     * @param sID student id
     * @throws RemoteException if either the invocation of the remote method, or the communication with the registry
     *                             service fails
     */
    public ReturnValue exit(int sID) throws RemoteException;
    
    /**
     * Gets the id of the student who arrived last.
     * Notifies a new order in the requests queue.
     * @param sID student id
     * @return boolean if the student was the last to enter the restaurant
     * @throws RemoteException if either the invocation of the remote method, or the communication with the registry
     *                             service fails
     */
    public ReturnValue shouldHaveArrivedEarlier(int sID) throws RemoteException;
    
    /**
     * Student reads the menu.
     * Notifies the waiter that he has read the menu.
     * @param sID student id
     * @throws RemoteException if either the invocation of the remote method, or the communication with the registry
     *                             service fails
     */
    public ReturnValue readTheMenu(int sID) throws RemoteException;
    
    /**
     * Get canGoHome Flag.
     * return canGoHome 
     * @throws RemoteException if either the invocation of the remote method, or the communication with the registry
     *                             service fails
     */
    public boolean canGoHome() throws RemoteException;
    
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