package sharedRegions.bar;

/**
 * Interface class with the Chef methods corresponding to his bar states
 * @author Martim Neves
 * @author Tiago Dias
 */
public interface BarChef {
	/**
     * Notifies a new order in the requests queue.
     * Update the chef state to DELVERING_THE_PORTION
     */
	 public void alertTheWaiter();
}
