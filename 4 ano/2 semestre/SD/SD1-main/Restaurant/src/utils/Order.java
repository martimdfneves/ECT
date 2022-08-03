package utils;

/**
 * Auxiliar class that describes an order with requester id and request type. 
 * @author Martim Neves
 * @author Tiago Dias
 */

public class Order {
	
	// Who makes the order:
	// [0-6] - student
	// [7] - chef
	
	/**
     * Id of the requester
     */
	private int requesterID;
	
	/**
     * Type of the request
     */
	private RequestType requestType;	
	
	/**
     * Order constructor
     * @param requestID is the id of the requester
     * @param requestType is the type of the request
     */
	public Order(int requestID, RequestType requestType) {
		this.requesterID = requestID;
		this.requestType = requestType;
	}

	/**
     * Gets the id of the entity that made the order
     * @return the id of the entity that made the order
     */
	public int getRequesterID() {
		return requesterID;
	}

	/**
     * Sets the id of the entity that made the order
     * @param requesterID is the id of the entity that made the order
     */
	public void setRequesterID(int requesterID) {
		this.requesterID = requesterID;
	}

	/**
     * Gets the request type of the order
     * @return the request type of the order
     */
	public RequestType getRequestType() {
		return requestType;
	}

	/**
     * Sets the request type of the order
     * @param requestType is the request type of the order
     */
	public void setRequestType(RequestType requestType) {
		this.requestType = requestType;
	}

	/**
     *Returns a String object representing the current order
     * @return order description
     */
	@Override
	public String toString() {
		return "Order [requesterID=" + requesterID + ", requestType=" + requestType + "]";
	}
}
