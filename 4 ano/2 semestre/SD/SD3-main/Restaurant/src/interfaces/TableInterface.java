package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import commInfra.ReturnValue;

/**
 *   Operational interface of a remote object of type Table.
 *
 *     It provides the functionality to access Table.
 */

public interface TableInterface extends Remote{

	/**
     * Waiter gets the pad and waits for the student to describe the order.
     * Waiter notifies the student that he has the pad.
     * @throws RemoteException if either the invocation of the remote method, or the communication with the registry
     *                           service fails
     */
    public ReturnValue getThePad() throws RemoteException;
    
    /**
     * Waits while there's no one to be served.
     * When a student is waiting to be served the waiter serves and notifies him.
     * @throws RemoteException if either the invocation of the remote method, or the communication with the registry
     *                           service fails
     */
    public void deliverPortion() throws RemoteException;
    
    /**
     * Checks if all the clients have been served.
     * @return if all the clients have been served.
     * @throws RemoteException if either the invocation of the remote method, or the communication with the registry
     *                           service fails
     */
    public ReturnValue haveAllClientsBeenServed() throws RemoteException;
    
    /**
     * Waiter presents the bill and waits for the student to pay.
     * Waiter notifies the student that the bill is ready.
     * @throws RemoteException if either the invocation of the remote method, or the communication with the registry
     *                           service fails
     */
    public ReturnValue presentTheBill() throws RemoteException;
    
    /**
     * Student informs companion of his course choice.
     * Notifies the companion that he has chosen.
     * @param sID id of the student
     * @throws RemoteException if either the invocation of the remote method, or the communication with the registry
     *                           service fails
     */
    public ReturnValue informCompanion(int sID) throws RemoteException;
    
    /**
     * Increases the number of requests received by the first student.
     * @param studentId id of the student
     * @throws RemoteException if either the invocation of the remote method, or the communication with the registry
     *                           service fails
     */
    public ReturnValue prepareTheOrder(int studentId) throws RemoteException;
    
    /**
     * Student chats with his companions.
     * @param sID id of the student
     * @throws RemoteException if either the invocation of the remote method, or the communication with the registry
     *                           service fails
     */
    public ReturnValue joinTheTalk(int sID) throws RemoteException;
    
    /**
     * Waits for everybody to finished eating.
     * @param sID student id
     * @throws RemoteException if either the invocation of the remote method, or the communication with the registry
     *                           service fails
     */ 
    public ReturnValue hasEverbodyFinished(int sID) throws RemoteException;
    
    /**
     * Students wait until everyone is served and then start eating.
     * @param studentId id of the student
     * @throws RemoteException if either the invocation of the remote method, or the communication with the registry
     *                           service fails
     */
    public ReturnValue startEating(int studentId) throws RemoteException;
    
    /**
     * Students finish eating.
     * @param studentId id of the student
     * @throws RemoteException if either the invocation of the remote method, or the communication with the registry
     *                           service fails
     */ 
    public ReturnValue endEating(int studentId) throws RemoteException;
    
    /**
     * Student waits until the bill is ready and then pays it.
     * Notifies the waiter that the bill is payed.
     * @throws RemoteException if either the invocation of the remote method, or the communication with the registry
     *                           service fails
     */
    public void honourTheBill() throws RemoteException;
    
    /**
     * Student waits to be informed by his companions and increases the number of requests received by the first student.
     * @param studentId id of the student
     * @throws RemoteException if either the invocation of the remote method, or the communication with the registry
     *                           service fails
     */
    public ReturnValue addUpOnesChoice(int studentId) throws RemoteException;
    
    /**
     * Checks if everybody has chosen.
     * @return if everybody has chosen.
     * @throws RemoteException if either the invocation of the remote method, or the communication with the registry
     *                           service fails
     */
    public boolean hasEverybodyChosen() throws RemoteException;
    
    /**
     * Student calls the waiter when everyone has chosen and waits for the waiter to get the pad.
     * Notifies the waiter that the order has been described.
     * @param studentId id of the student
     * @throws RemoteException if either the invocation of the remote method, or the communication with the registry
     *                           service fails
     */
    public ReturnValue describeTheOrder(int studentId) throws RemoteException;
    
    /**
     * Student blocks while not served by waiter.
     * @param sID student id.
     * @throws RemoteException if either the invocation of the remote method, or the communication with the registry
     *                           service fails
     */
    public void waitingToBeServed(int sID) throws RemoteException;
	
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
