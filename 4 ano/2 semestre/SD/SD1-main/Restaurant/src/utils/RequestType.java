package utils;

/**
 * Enum class that enumerates all request types
 * @author Martim Neves
 * @author Tiago Dias
 */

public enum RequestType {
	
	/**
     * Salute the client type
     */
	SALUTE_THE_CLIENT("STC"), 
	
	/**
     * Get the pad type
     */
	GET_THE_PAD("GTP"), 
	
	/**
     * Collect portions type
     */
	COLLECT_PORTIONS("CP"),
	
	/**
     * Prepare the bill type
     */
	PREPARE_THE_BILL("PTB"), 
	
	/**
     * Say goodbye type
     */
	SAY_GOODBYE("SGB"),
	
	/**
     * NULL type
     */
	NULL("NULL");

	/**
     * Request type description
     */
	private final String description;

	/**
     * Constructor
     */
	private RequestType(String description) {
		this.description = description;
	}

	/**
     *Returns a String object representing the current request type
     * @return request type description
     */
	@Override
	public String toString() {
		return this.description;
	}
}