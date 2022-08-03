package serverSide.sharedRegions;

import commInfra.Message;
import serverSide.entities.*;;

/**
 *  Interface to the Kitchen.
 *
 *  It is responsible to validate and process the incoming message, execute the corresponding method on the
 *  Kitchen and generate the outgoing message.
 *  Implementation of a client-server model of type 2 (server replication).
 *  Communication is based on a communication channel under the TCP protocol.
 */
public class KitchenMessageExchange implements SharedRegionInterface{
	/**
	 *  Reference to the bar.
	 */
    private Kitchen kitchen;

    /**
     *  Variable for shutdown.
     */
    private boolean shutdown;

    /**
     *  Instantiation of an interface to the kitchen.
     *  @param kitchen reference to the kitchen
     */
    public KitchenMessageExchange(Kitchen kitchen){
        this.kitchen = kitchen;
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

        Object res = null;
        Object[] state;

        switch(message.getOperation()){
            case 28:
                chef = (Chef) Thread.currentThread();
                chef.setChefID((int) message.getStateFields()[0]);
                chef.setChefState((int) message.getStateFields()[1]);
                kitchen.startPreparation();
                state = new Object[]{chef.getChefID(),chef.getChefState()};
                break;
            case 29:
                chef = (Chef) Thread.currentThread();
                chef.setChefID((int) message.getStateFields()[0]);
                chef.setChefState((int) message.getStateFields()[1]);
                kitchen.proceedToPresentation();
                state = new Object[]{chef.getChefID(),chef.getChefState()};
                break;
            case 30:
                chef = (Chef) Thread.currentThread();
                chef.setChefID((int) message.getStateFields()[0]);
                chef.setChefState((int) message.getStateFields()[1]);
                kitchen.haveNextPortionReady();
                state = new Object[]{chef.getChefID(),chef.getChefState()};
                break;
            case 31:
                chef = (Chef) Thread.currentThread();
                chef.setChefID((int) message.getStateFields()[0]);
                chef.setChefState((int) message.getStateFields()[1]);
                kitchen.continuePreparation();
                state = new Object[]{chef.getChefID(),chef.getChefState()};
                break;
            case 32:
                chef = (Chef) Thread.currentThread();
                chef.setChefID((int) message.getStateFields()[0]);
                chef.setChefState((int) message.getStateFields()[1]);
                kitchen.cleanUp();
                state = new Object[]{chef.getChefID(),chef.getChefState()};
                break;
            case 33:
                chef = (Chef) Thread.currentThread();
                chef.setChefID((int) message.getStateFields()[0]);
                chef.setChefState((int) message.getStateFields()[1]);
                res = kitchen.hasTheOrderBeenCompleted();
                state = new Object[]{chef.getChefID(),chef.getChefState()};
                break;
            case 34:
                chef = (Chef) Thread.currentThread();
                chef.setChefID((int) message.getStateFields()[0]);
                chef.setChefState((int) message.getStateFields()[1]);
                res = kitchen.haveAllPortionsBeenDelivered();
                state = new Object[]{chef.getChefID(),chef.getChefState()};
                break;
            case 35:
                waiter = (Waiter) Thread.currentThread();
                waiter.setWaiterID((int) message.getStateFields()[0]);
                waiter.setWaiterState((int) message.getStateFields()[1]);
                kitchen.handNoteToTheChef();
                state = new Object[]{waiter.getWaiterID(),waiter.getWaiterState()};
                break;
            case 36:
                waiter = (Waiter) Thread.currentThread();
                waiter.setWaiterID((int) message.getStateFields()[0]);
                waiter.setWaiterState((int) message.getStateFields()[1]);
                kitchen.collectPortion();
                state = new Object[]{waiter.getWaiterID(),waiter.getWaiterState()};
                break;
            case 37:
                chef = (Chef) Thread.currentThread();
                chef.setChefID((int) message.getStateFields()[0]);
                chef.setChefState((int) message.getStateFields()[1]);
                kitchen.watchTheNews();
                state = new Object[]{chef.getChefID(),chef.getChefState()};
                break;
            case 38:
                waiter = (Waiter) Thread.currentThread();
                waiter.setWaiterID((int) message.getStateFields()[0]);
                waiter.setWaiterState((int) message.getStateFields()[1]);
                res = kitchen.haveAllClientsBeenServed();
                state = new Object[]{waiter.getWaiterID(),waiter.getWaiterState()};
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