package clientSide.stubs;

import clientSide.entities.*;
import commInfra.*;

/**
 * Stub to the kitchen.
 *
 * It instantiates a remote reference to the kitchen.
 * Implementation of a client-server model of type 2 (server replication).
 * Communication is based on a communication channel under the TCP protocol.
 * @author Martim Neves
 * @author Tiago Dias
 */
public class KitchenStub {
	
	/**
     * Name of the platform where the kitchen server is located.
     */ 
	private String serverHostName;

	/**
     * Number of server listening port.
     */
	private int serverPortNumb;

	/**
     * Stub instantiation.
     *
     * @param hostName name of the platform where the kitchen server is located.
     * @param port Number of server listening port.
     */
    public KitchenStub(String hostName, int port){
       serverHostName = hostName;
       serverPortNumb = port;
    }

    /**
     * Chef starts preparation.
     * Notifies the waiter that preparation just started.
     */
    public void startPreparation(){
        Chef c = (Chef) Thread.currentThread();
        CommunicationChannel com = new CommunicationChannel(serverHostName, serverPortNumb);
        Object[] params = new Object[0];
    	Object[] state_fields = new Object[2];
        state_fields[0] = c.getChefID();
    	state_fields[1] = c.getChefState();

        Message m_toServer = new Message(28, params, 0, state_fields, 2, null);                                                          
        Message m_fromServer;

        while (!com.open ()){ 
        	try{ 
        		Thread.currentThread ();
        		Thread.sleep ((long) (10));
        	}
        	catch (InterruptedException e) {}
        }
        
        com.writeObject (m_toServer);
        m_fromServer = (Message) com.readObject();                 
        c.setState((int) m_fromServer.getStateFields()[1]);
        com.close ();
    }

    /**
     * Chef presents the dishes.
     */
    public void proceedToPresentation(){
        Chef c = (Chef) Thread.currentThread();
        CommunicationChannel com = new CommunicationChannel(serverHostName, serverPortNumb);
        Object[] params = new Object[0];
    	Object[] state_fields = new Object[2];
        state_fields[0] = c.getChefID();
    	state_fields[1] = c.getChefState();

        Message m_toServer = new Message(29, params, 0, state_fields, 2, null);                                                          
        Message m_fromServer;

        while (!com.open ()){ 
        	try{ 
        		Thread.currentThread ();
        		Thread.sleep ((long) (10));
        	}
        	catch (InterruptedException e) {}
        }
        
        com.writeObject (m_toServer);
        m_fromServer = (Message) com.readObject();                 
        c.setState((int) m_fromServer.getStateFields()[1]);
        com.close ();
    }

    /**
     * Chef dishes the portions.
     */
    public void haveNextPortionReady(){
        Chef c = (Chef) Thread.currentThread();
        CommunicationChannel com = new CommunicationChannel(serverHostName, serverPortNumb);
        Object[] params = new Object[0];
    	Object[] state_fields = new Object[2];
        state_fields[0] = c.getChefID();
    	state_fields[1] = c.getChefState();

        Message m_toServer = new Message(30, params, 0, state_fields, 2, null);                                                          
        Message m_fromServer;

        while (!com.open ()){ 
        	try{ 
        		Thread.currentThread ();
        		Thread.sleep ((long) (10));
        	}
        	catch (InterruptedException e) {}
        }
        
        com.writeObject (m_toServer);
        m_fromServer = (Message) com.readObject();                 
        c.setState((int) m_fromServer.getStateFields()[1]);
        com.close ();
    }

    /**
     * Chef continues course preparation.
     */ 
    public void continuePreparation(){
        Chef c = (Chef) Thread.currentThread();
        CommunicationChannel com = new CommunicationChannel(serverHostName, serverPortNumb);
        Object[] params = new Object[0];
    	Object[] state_fields = new Object[2];
        state_fields[0] = c.getChefID();
    	state_fields[1] = c.getChefState();

        Message m_toServer = new Message(31, params, 0, state_fields, 2, null);                                                          
        Message m_fromServer;

        while (!com.open ()){ 
        	try{ 
        		Thread.currentThread ();
        		Thread.sleep ((long) (10));
        	}
        	catch (InterruptedException e) {}
        }
        
        com.writeObject (m_toServer);   
        m_fromServer = (Message) com.readObject();                 
        c.setState((int) m_fromServer.getStateFields()[1]);
        com.close ();
    }

    /**
     * Chef clean up and closes his service.
     */
    public void cleanUp(){
        Chef c = (Chef) Thread.currentThread();
        CommunicationChannel com = new CommunicationChannel(serverHostName, serverPortNumb);
        Object[] params = new Object[0];
    	Object[] state_fields = new Object[2];
        state_fields[0] = c.getChefID();
    	state_fields[1] = c.getChefState();

        Message m_toServer = new Message(32, params, 0, state_fields, 2, null);                                                          
        Message m_fromServer;

        while (!com.open ()){ 
        	try{ 
        		Thread.currentThread ();
        		Thread.sleep ((long) (10));
        	}
        	catch (InterruptedException e) {}
        }
        
        com.writeObject (m_toServer);
        m_fromServer = (Message) com.readObject();                 
        c.setState((int) m_fromServer.getStateFields()[1]);
        com.close ();
    }

    /**
	 * Checks if the order has been completed
     * @return if the order has been completed
     */
    public boolean hasTheOrderBeenCompleted(){
        Chef c = (Chef) Thread.currentThread();
        CommunicationChannel com = new CommunicationChannel(serverHostName, serverPortNumb);
        Object[] params = new Object[0];
    	Object[] state_fields = new Object[2];
        state_fields[0] = c.getChefID();
    	state_fields[1] = c.getChefState();

        Message m_toServer = new Message(33, params, 0, state_fields, 2, null);                                                          
        Message m_fromServer;

        while (!com.open ()){ 
        	try{ 
        		Thread.currentThread ();
        		Thread.sleep ((long) (10));
        	}
        	catch (InterruptedException e) {}
        }
        
        com.writeObject (m_toServer);
        
        m_fromServer = (Message) com.readObject();                 

        c.setState((int) m_fromServer.getStateFields()[1]);
        boolean result = (Boolean) m_fromServer.getReturnValue();
        com.close ();

        return result;
    }

    /**
	 * Checks if all portions been delivered.
     * @return if all portions been delivered.
     */
    public boolean haveAllPortionsBeenDelivered(){
        Chef c = (Chef) Thread.currentThread();
        CommunicationChannel com = new CommunicationChannel(serverHostName, serverPortNumb);
        Object[] params = new Object[0];
    	Object[] state_fields = new Object[2];
        state_fields[0] = c.getChefID();
    	state_fields[1] = c.getChefState();

        Message m_toServer = new Message(34, params, 0, state_fields, 2, null);                                                          
        Message m_fromServer;

        while (!com.open ()){ 
        	try{ 
        		Thread.currentThread ();
        		Thread.sleep ((long) (10));
        	}
        	catch (InterruptedException e) {}
        }
        
        com.writeObject (m_toServer);        
        m_fromServer = (Message) com.readObject();                 

        c.setState((int) m_fromServer.getStateFields()[1]);
        boolean result = (Boolean) m_fromServer.getReturnValue();
        com.close ();

        return result;
    }
    
    /**
     * Waiter hands the note to the chef and waits for the chef to start the preparation.
     * Notifies the chef that the note has been handed.
     */
    public void handNoteToTheChef(){
        Waiter w = (Waiter) Thread.currentThread();
        CommunicationChannel com = new CommunicationChannel(serverHostName, serverPortNumb);
        Object[] params = new Object[0];
    	Object[] state_fields = new Object[2];
        state_fields[0] = w.getWaiterID();
    	state_fields[1] = w.getWaiterState();

        Message m_toServer = new Message(35, params, 0, state_fields, 2, null);                                                          
        Message m_fromServer;

        while (!com.open ()){ 
        	try{ 
        		Thread.currentThread ();
        		Thread.sleep ((long) (10));
        	}
        	catch (InterruptedException e) {}
        }
        
        com.writeObject (m_toServer);
        m_fromServer = (Message) com.readObject();                 
        w.setState((int) m_fromServer.getStateFields()[1]);
        com.close ();
    }

    /**
     * Notifies the chef that the waiter has collected the portion
     */
    public void collectPortion(){
        Waiter w = (Waiter) Thread.currentThread();
        CommunicationChannel com = new CommunicationChannel(serverHostName, serverPortNumb);
        Object[] params = new Object[0];
    	Object[] state_fields = new Object[2];
        state_fields[0] = w.getWaiterID();
    	state_fields[1] = w.getWaiterState();

        Message m_toServer = new Message(36, params, 0, state_fields, 2, null);                                                          
        Message m_fromServer;

        while (!com.open ()){ 
        	try{ 
        		Thread.currentThread ();
        		Thread.sleep ((long) (10));
        	}
        	catch (InterruptedException e) {}
        }
        
        com.writeObject (m_toServer);
        m_fromServer = (Message) com.readObject();                 
        w.setState((int) m_fromServer.getStateFields()[1]);
        com.close ();
    }

    /**
     * Chef waits for the note to be delivered by the waiter.
     */
    public void watchTheNews(){
        Chef c = (Chef) Thread.currentThread();
        CommunicationChannel com = new CommunicationChannel(serverHostName, serverPortNumb);
        Object[] params = new Object[0];
    	Object[] state_fields = new Object[2];
        state_fields[0] = c.getChefID();
    	state_fields[1] = c.getChefState();

        Message m_toServer = new Message(37, params, 0, state_fields, 2, null);                                                          
        Message m_fromServer;

        while (!com.open ()){ 
        	try{ 
        		Thread.currentThread ();
        		Thread.sleep ((long) (10));
        	}
        	catch (InterruptedException e) {}
        }
        
        com.writeObject (m_toServer);   
        m_fromServer = (Message) com.readObject();                 
        c.setState((int) m_fromServer.getStateFields()[1]);
        com.close ();
    }

    /**
     *	 Checks if all clients were served
     *     @return boolean that indicates if all clients were served
     */
    public boolean haveAllClientsBeenServed(){
        Waiter w = (Waiter) Thread.currentThread();
        CommunicationChannel com = new CommunicationChannel(serverHostName, serverPortNumb);
        Object[] params = new Object[0];
    	Object[] state_fields = new Object[2];
        state_fields[0] = w.getWaiterID();
    	state_fields[1] = w.getWaiterState();

        Message m_toServer = new Message(38, params, 0, state_fields, 2, null);                                                          
        Message m_fromServer;

        while (!com.open ()){ 
        	try{ 
        		Thread.currentThread ();
        		Thread.sleep ((long) (10));
        	}
        	catch (InterruptedException e) {}
        }
        
        com.writeObject (m_toServer);
        m_fromServer = (Message) com.readObject();                 

        w.setState((int) m_fromServer.getStateFields()[1]);
        boolean result = (Boolean) m_fromServer.getReturnValue();
        com.close ();

        return result;
    }

    /**
     *   Operation server shutdown.
     *
     *   New operation.
     */
    public void shutdown(){
        CommunicationChannel com = new CommunicationChannel (serverHostName, serverPortNumb);
        Object[] params = new Object[0];
        Object[] state_fields = new Object[0];
     
        Message m_toServer = new Message(99, params, 0, state_fields, 0, null);                                                          
        
        while (!com.open ()){ 
        	try{ 
        		Thread.currentThread ();
        		Thread.sleep ((long) (10));
        	}
        	catch (InterruptedException e) {}
        }
        
        com.writeObject (m_toServer);
        com.close ();
    }
}
