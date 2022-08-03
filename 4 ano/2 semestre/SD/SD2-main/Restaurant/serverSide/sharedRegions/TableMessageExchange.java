package serverSide.sharedRegions;

import commInfra.Message;
import serverSide.entities.*;;

/**
 * Interface to the Table.
 *
 * It is responsible to validate and process the incoming message, execute the corresponding method on the
 * Table and generate the outgoing message.
 * Implementation of a client-server model of type 2 (server replication).
 * Communication is based on a communication channel under the TCP protocol.
 */
public class TableMessageExchange implements SharedRegionInterface{
   
	/**
     * Reference to the table.
     */
	private Table table;

	 /**
      * Variable for shutdown.
      */
    private boolean shutdown;

    /**
     * Instantiation of an interface to the table.
     * @param table reference to the table
     */
    public TableMessageExchange(Table table){
        this.table = table;
        this.shutdown  = false;
    }

    /**
     *  Processing of the incoming messages.
     *
     *  Validation, execution of the corresponding method and generation of the outgoing message.
     *  @param message service request
     *  @return message
     */
    public Message processAndReply(Message message){
        Waiter waiter;
        Student student;

        Object res = null;
        Object[] state;

        switch(message.getOperation()){
            case 13:
                waiter = (Waiter) Thread.currentThread();
                waiter.setWaiterID((int) message.getStateFields()[0]);
                waiter.setWaiterState((int) message.getStateFields()[1]);
                table.getThePad();
                state = new Object[]{waiter.getWaiterID(),waiter.getWaiterState()};
                break;
            case 14:
                waiter = (Waiter) Thread.currentThread();
                waiter.setWaiterID((int) message.getStateFields()[0]);
                waiter.setWaiterState((int) message.getStateFields()[1]);
                table.deliverPortion();
                state = new Object[]{waiter.getWaiterID(),waiter.getWaiterState()};
                break;
            case 15:
                waiter = (Waiter) Thread.currentThread();
                waiter.setWaiterID((int) message.getStateFields()[0]);
                waiter.setWaiterState((int) message.getStateFields()[1]);
                res = table.haveAllClientsBeenServed();
                state = new Object[]{waiter.getWaiterID(),waiter.getWaiterState()};
                break;
            case 16:
                waiter = (Waiter) Thread.currentThread();
                waiter.setWaiterID((int) message.getStateFields()[0]);
                waiter.setWaiterState((int) message.getStateFields()[1]);
                table.presentTheBill();
                state = new Object[]{waiter.getWaiterID(),waiter.getWaiterState()};
                break;
            case 17:
                student = (Student) Thread.currentThread();
                student.setID((int) message.getStateFields()[0]);
                student.setStudentState((int) message.getStateFields()[1]);
                table.informCompanion();
                state = new Object[]{student.getID(),student.getStudentState()};
                break;
            case 18:
                student = (Student) Thread.currentThread();
                student.setID((int) message.getStateFields()[0]);
                student.setStudentState((int) message.getStateFields()[1]);
                table.prepareTheOrder();
                state = new Object[]{student.getID(),student.getStudentState()};
                break;
            case 19:
                student = (Student) Thread.currentThread();
                student.setID((int) message.getStateFields()[0]);
                student.setStudentState((int) message.getStateFields()[1]);
                table.joinTheTalk();
                state = new Object[]{student.getID(),student.getStudentState()};
                break;
            case 20:
                student = (Student) Thread.currentThread();
                student.setID((int) message.getStateFields()[0]);
                student.setStudentState((int) message.getStateFields()[1]);
                table.hasEverbodyFinished();
                state = new Object[]{student.getID(),student.getStudentState()};
                break;
            case 21:
                student = (Student) Thread.currentThread();
                student.setID((int) message.getStateFields()[0]);
                student.setStudentState((int) message.getStateFields()[1]);
                table.startEating();
                state = new Object[]{student.getID(),student.getStudentState()};
                break;
            case 22:
                student = (Student) Thread.currentThread();
                student.setID((int) message.getStateFields()[0]);
                student.setStudentState((int) message.getStateFields()[1]);
                table.endEating();
                state = new Object[]{student.getID(),student.getStudentState(),student.isLastStudent()};
                break;
            case 23:
                student = (Student) Thread.currentThread();
                student.setID((int) message.getStateFields()[0]);
                student.setStudentState((int) message.getStateFields()[1]);
                table.honourTheBill();
                state = new Object[]{student.getID(),student.getStudentState()};
                break;
            case 24:
                student = (Student) Thread.currentThread();
                student.setID((int) message.getStateFields()[0]);
                student.setStudentState((int) message.getStateFields()[1]);
                table.addUpOnesChoice();
                state = new Object[]{student.getID(),student.getStudentState()};
                break;
            case 25:
                student = (Student) Thread.currentThread();
                student.setID((int) message.getStateFields()[0]);
                student.setStudentState((int) message.getStateFields()[1]);
                res = table.hasEverybodyChosen();
                state = new Object[]{student.getID(),student.getStudentState()};
                break;
            case 26:
                student = (Student) Thread.currentThread();
                student.setID((int) message.getStateFields()[0]);
                student.setStudentState((int) message.getStateFields()[1]);
                table.describeTheOrder();
                state = new Object[]{student.getID(),student.getStudentState()};
                break;
            case 27:
                student = (Student) Thread.currentThread();
                student.setID((int) message.getStateFields()[0]);
                student.setStudentState((int) message.getStateFields()[1]);
                table.waitingToBeServed(student.getID());
                state = new Object[]{student.getID(),student.getStudentState()};
                break;
            case 99:
                this.shutdown = true;
                message = null;
                state = new Object[]{};
                break;

            default:
                throw new IllegalArgumentException();
        }

        if (message != null){
            message.setStateFields(state);
            message.setSizeStateFields(state.length);
            message.setReturnValue(res);
        }

        return message;
    }
    
    /**
     *  Gets the shutdown flag
     *  @return shutdown flag
     */
    public boolean hasShutdown(){
        return this.shutdown;
    }
} 