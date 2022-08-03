package serverSide.stubs;

import commInfra.Message;
import commInfra.CommunicationChannel;

/**
 * Stub to the general repository.
 *
 * It instantiates a remote reference to the general repository.
 * Implementation of a client-server model of type 2 (server replication).
 * Communication is based on a communication channel under the TCP protocol.
 * @author Martim Neves
 * @author Tiago Dias
 */
public class GeneralReposStub {
	
	/**
    *  Name of the computational system where the server is located.
    */
    private String serverHostName;

    /**
    *  Number of the listening port at the computational system where the server is located.
    */
    private int serverPortNumb;

    /**
    * Instantiation of a remote reference
    *
    * @param hostName name of the computational system where the server is located
    * @param port number of the listening port at the computational system where the server is located
    */
    public GeneralReposStub (String hostName, int port){
       serverHostName = hostName;
       serverPortNumb = port;
    }
    
    /**
	 * Set waiter state.
	 * @param state waiter state
	 */
	public void setWaiterState (int state){
        CommunicationChannel com = new CommunicationChannel (serverHostName, serverPortNumb);
    	Object[] params = new Object[1];
    	Object[] state_fields = new Object[0];
    	params[0] = state;
    	
        Message m_toServer = new Message(39, params, 1, state_fields, 0, null);                                                          
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
        com.close ();                                  
	}

    /**
	 * Set student state.
	 * @param id student id
	 * @param state student state
	 */
    public void setStudentState (int id, int state){
        CommunicationChannel com = new CommunicationChannel (serverHostName, serverPortNumb);
    	Object[] params = new Object[2];
    	Object[] state_fields = new Object[0];
    	params[0] = id;
    	params[1] = state;
    	
        Message m_toServer = new Message(40, params, 2, state_fields, 0, null);                                                          
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
        com.close ();                                  
    }

     /**
	 * Set chef state.
	 * @param state chef state
	 */
    public void setChefState (int state){
        CommunicationChannel com = new CommunicationChannel (serverHostName, serverPortNumb);
    	Object[] params = new Object[1];
    	Object[] state_fields = new Object[0];
    	params[0] = state;
    	
        Message m_toServer = new Message(41, params, 1, state_fields, 0, null);                                                          
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
        com.close ();                                  
	}

    /**
	 * Set Ncourse number.
	 * @param number number to add to Ncourse
	 */
	public void setNCourse (int number){
        CommunicationChannel com = new CommunicationChannel (serverHostName, serverPortNumb);
    	Object[] params = new Object[1];
    	Object[] state_fields = new Object[0];
    	params[0] = number;
    	
        Message m_toServer = new Message(42, params, 1, state_fields, 0, null);                                                          
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
        com.close ();                                  
    }

    /**
	 * Set Nportion number.
	 * @param number number to add to Ncourse
	 */
	public void setNPortion (int number){
        CommunicationChannel com = new CommunicationChannel (serverHostName, serverPortNumb);
    	Object[] params = new Object[1];
    	Object[] state_fields = new Object[0];
    	params[0] = number;
    	
        Message m_toServer = new Message(43, params, 1, state_fields, 0, null);                                                          
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
        com.close ();                                  
    }

    /**
	 * Set passenger state.
	 * @param id passenger id
	 */
	public void setStudentsOrder (int id){
        CommunicationChannel com = new CommunicationChannel (serverHostName, serverPortNumb);
    	Object[] params = new Object[1];
    	Object[] state_fields = new Object[0];
    	params[0] = id;
    	
        Message m_toServer = new Message(44, params, 1, state_fields, 0, null);                                                          
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
        com.close ();                                  
	}

	/**
     * Logs current state of entities or other messages according to the requested type
     */
	private void reportStatus (){
        CommunicationChannel com = new CommunicationChannel (serverHostName, serverPortNumb);
    	Object[] params = new Object[0];
    	Object[] state_fields = new Object[0];
    	
        Message m_toServer = new Message(46, params, 1, state_fields, 0, null);                                                          
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
        com.close ();                                  
    }

    /**
	 * Write a specific state line at the end of the logging file, for example an message informing that
	 * the plane has arrived.
	 * @param message message to write in the logging file
	 */
	public void reportSpecificStatus (String message){
        CommunicationChannel com = new CommunicationChannel (serverHostName, serverPortNumb);
    	Object[] params = new Object[1];
    	Object[] state_fields = new Object[0];
    	params[0] = message;
    	
        Message m_toServer = new Message(47, params, 1, state_fields, 0, null);                                                          
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
        com.close ();                                  
	}

    /**
    * Method called to shutdown the General Repository server
    */
    public void shutdown(){
        CommunicationChannel com = new CommunicationChannel (serverHostName, serverPortNumb);
        Object[] params = new Object[0];
        Object[] state_fields = new Object[0];
     
        Message m_toServer = new Message(99, params, 0, state_fields, 0, null);                                                          
        Message m_fromServer;            
        
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
