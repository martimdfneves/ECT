package serverSide.sharedRegions;

import commInfra.Message;
import serverSide.entities.*;;

/**
 * Interface to the Bar.
 * It is responsible to validate and process the incoming message, execute the corresponding method on the
 * Student and generate the outgoing message.
 * Implementation of a client-server model of type 2 (server replication).
 * Communication is based on a communication channel under the TCP protocol.
 */
public class BarMessageExchange implements SharedRegionInterface{
	
	/**
	 *  Reference to the bar.
	 */
	private Bar bar;

	/**
	 * Variable for shutdown.
	 */
    private boolean shutdown;

    /**
     * Instantiation of an interface to the bar.
     * @param bar reference to the bar
     */
    public BarMessageExchange(Bar bar){
        this.bar = bar;
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
        Chef chef;
        Waiter waiter;
        Student student;

        Object res = null;
        Object[] state;

        switch(message.getOperation()){
            case 0:
                waiter = (Waiter) Thread.currentThread();
                waiter.setWaiterID((int) message.getStateFields()[0]);
                waiter.setWaiterState((int) message.getStateFields()[1]);
                bar.saluteTheClient();
                state = new Object[]{waiter.getWaiterID(),waiter.getWaiterState()};
                break;
            case 1:
                chef = (Chef) Thread.currentThread();
                chef.setChefID((int) message.getStateFields()[0]);
                chef.setChefState((int) message.getStateFields()[1]);
                bar.alertTheWaiter();
                state = new Object[]{chef.getChefID(),chef.getChefState()};
                break;
            case 2:
                waiter = (Waiter) Thread.currentThread();
                waiter.setWaiterID((int) message.getStateFields()[0]);
                waiter.setWaiterState((int) message.getStateFields()[1]);
                bar.returningToTheBar();
                state = new Object[]{waiter.getWaiterID(),waiter.getWaiterState()};
                break;
            case 3:
                waiter = (Waiter) Thread.currentThread();
                waiter.setWaiterID((int) message.getStateFields()[0]);
                waiter.setWaiterState((int) message.getStateFields()[1]);
                bar.prepareTheBill();
                state = new Object[]{waiter.getWaiterID(),waiter.getWaiterState()};
                break;
            case 4:
                waiter = (Waiter) Thread.currentThread();
                waiter.setWaiterID((int) message.getStateFields()[0]);
                waiter.setWaiterState((int) message.getStateFields()[1]);
                res = bar.lookAround();
                state = new Object[]{waiter.getWaiterID(),waiter.getWaiterState()};
                break;
            case 5:
                waiter = (Waiter) Thread.currentThread();
                waiter.setWaiterID((int) message.getStateFields()[0]);
                waiter.setWaiterState((int) message.getStateFields()[1]);
                bar.sayGoodbye();
                state = new Object[]{waiter.getWaiterID(),waiter.getWaiterState()};
                break;
            case 6:
                student = (Student) Thread.currentThread();
                student.setID((int) message.getStateFields()[0]);
                student.setStudentState((int) message.getStateFields()[1]);
                student.setLastStudent((boolean) message.getStateFields()[2]);
                bar.signalTheWaiter(student.getID());
                state = new Object[]{student.getID(),student.getStudentState()};
                break;
            case 7:
                student = (Student) Thread.currentThread();
                student.setID((int) message.getStateFields()[0]);
                student.setStudentState((int) message.getStateFields()[1]);
                bar.callTheWaiter();
                state = new Object[]{student.getID(),student.getStudentState()};
                break;
            case 8:
                student = (Student) Thread.currentThread();
                student.setID((int) message.getStateFields()[0]);
                student.setStudentState((int) message.getStateFields()[1]);
                bar.enter();
                state = new Object[]{student.getID(),student.getStudentState()};
                break;
            case 9:
                student = (Student) Thread.currentThread();
                student.setID((int) message.getStateFields()[0]);
                student.setStudentState((int) message.getStateFields()[1]);
                res = bar.FirstStudent(student.getID());
                state = new Object[]{student.getID(),student.getStudentState()};
                break;
            case 10:
                student = (Student) Thread.currentThread();
                student.setID((int) message.getStateFields()[0]);
                student.setStudentState((int) message.getStateFields()[1]);
                bar.exit();
                state = new Object[]{student.getID(),student.getStudentState()};
                break;
            case 11:
                student = (Student) Thread.currentThread();
                student.setID((int) message.getStateFields()[0]);
                student.setStudentState((int) message.getStateFields()[1]);
                res = bar.shouldHaveArrivedEarlier(student.getID());
                state = new Object[]{student.getID(),student.getStudentState()};
                break;
            case 12:
                student = (Student) Thread.currentThread();
                student.setID((int) message.getStateFields()[0]);
                student.setStudentState((int) message.getStateFields()[1]);
                bar.readTheMenu();
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