package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import commInfra.ReturnValue;

/**
 *   Operational interface of a remote object of type Kitchen.
 *
 *     It provides the functionality to access Kitchen.
 */

public interface KitchenInterface extends Remote{

	/**
     * Chef starts preparation.
     * Notifies the waiter that preparation just started.
     * @throws RemoteException if either the invocation of the remote method, or the communication with the registry
     *                           service fails
     */
	
    public ReturnValue startPreparation() throws RemoteException;
    
    /**
     * Chef presents the dishes.
     * @throws RemoteException if either the invocation of the remote method, or the communication with the registry
     *                           service fails
     */
    public ReturnValue proceedToPresentation() throws RemoteException;
    
    /**
     * Chef dishes the portions.
     * @throws RemoteException if either the invocation of the remote method, or the communication with the registry
     *                           service fails
     */
    public ReturnValue haveNextPortionReady() throws RemoteException;
    
    /**
     * Chef continues course preparation.
     * @throws RemoteException if either the invocation of the remote method, or the communication with the registry
     *                           service fails
     */ 
    public ReturnValue continuePreparation() throws RemoteException;
    
    /**
     * Chef clean up and closes his service.
     * @throws RemoteException if either the invocation of the remote method, or the communication with the registry
     *                           service fails
     */
    public ReturnValue cleanUp() throws RemoteException;
    
    /**
	 * Checks if the order has been completed
     * @return if the order has been completed
     * @throws RemoteException if either the invocation of the remote method, or the communication with the registry
     *                           service fails
     */
    public boolean hasTheOrderBeenCompleted() throws RemoteException;
    
    /**
	 * Checks if all portions been delivered.
     * @return if all portions been delivered.
     * @throws RemoteException if either the invocation of the remote method, or the communication with the registry
     *                           service fails
     */
    public boolean haveAllPortionsBeenDelivered() throws RemoteException;
    
    /**
     * Waiter hands the note to the chef and waits for the chef to start the preparation.
     * Notifies the chef that the note has been handed.
     * @throws RemoteException if either the invocation of the remote method, or the communication with the registry
     *                           service fails
     */
    public ReturnValue handNoteToTheChef() throws RemoteException;
    
    /**
     * Notifies the chef that the waiter has collected the portion.
     * @throws RemoteException if either the invocation of the remote method, or the communication with the registry
     *                           service fails
     */
    public ReturnValue collectPortion() throws RemoteException;
    
    /**
     * Chef waits for the note to be delivered by the waiter.
     * @throws RemoteException if either the invocation of the remote method, or the communication with the registry
     *                           service fails
     */
    public ReturnValue watchTheNews() throws RemoteException;
    
    /**
     *	 Checks if all clients were served.
     *     @return boolean that indicates if all clients were served.
     *     @throws RemoteException if either the invocation of the remote method, or the communication with the registry
     *                           service fails
     */
    public ReturnValue haveAllClientsBeenServed() throws RemoteException;
    
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
