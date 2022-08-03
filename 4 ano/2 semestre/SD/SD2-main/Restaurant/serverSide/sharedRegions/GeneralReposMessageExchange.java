package serverSide.sharedRegions;

import genclass.GenericIO;
import commInfra.Message;

/**
 * Interface to the General Repository.
 *
 * It is responsible to validate and process the incoming message, execute the corresponding method on the
 * general repository and generate the outgoing message.
 * Implementation of a client-server model of type 2 (server replication).
 * Communication is based on a communication channel under the TCP protocol.
 */
public class GeneralReposMessageExchange implements SharedRegionInterface{
	/**
	 * Reference to the General Repository.
	 */
	private GeneralRepos generalRepos;

	/**
	 * Variable for shutdown.
	 */
    private boolean shutdown;

    /**
     *  Instantiation of an interface to the General Repository.
     *  @param generalRepos reference to the General Repository
     */
    public GeneralReposMessageExchange(GeneralRepos generalRepos){
        this.generalRepos = generalRepos;
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
        Object[] state;

        switch(message.getOperation()){
            case 39:
			    GenericIO.writelnString ("-> setWaiterState");
			    generalRepos.setWaiterState((int) message.getParams()[0]);
			    break;
            case 40:
			    GenericIO.writelnString ("-> setStudentState");
			    generalRepos.setStudentState((int) message.getParams()[0],(int) message.getParams()[1]);
			    break;
            case 41:
			    GenericIO.writelnString ("-> setChefState");
			    generalRepos.setChefState((int) message.getParams()[0]);
			    break;
            case 42:
			    GenericIO.writelnString ("-> setNCourse");
			    generalRepos.setNCourse((int) message.getParams()[0]);
			    break;
            case 43:
			    GenericIO.writelnString ("-> setNPortion");
			    generalRepos.setNPortion((int) message.getParams()[0]);
			    break;
            case 44:
			    GenericIO.writelnString ("-> setStudentsOrder");
			    generalRepos.setStudentsOrder((int) message.getParams()[0]);
			    break;
            case 45:
			    GenericIO.writelnString ("-> reportInitialStatus");
			    break;
            case 46:
			    GenericIO.writelnString ("-> reportStatus");
			    break;
            case 47:
			    GenericIO.writelnString ("-> reportSpecificStatus");
			    generalRepos.reportSpecificStatus((String) message.getParams()[0]);
			    break;

            case 99:
                this.shutdown = true;
                message = null;
                state = new Object[]{};
                break;
            default:
                throw new IllegalArgumentException();
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