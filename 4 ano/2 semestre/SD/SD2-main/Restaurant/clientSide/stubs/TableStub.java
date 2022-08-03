package clientSide.stubs;

import clientSide.entities.*;
import commInfra.*;

/**
 *  Stub to the table.
 *
 *    It instantiates a remote reference to the table.
 *    Implementation of a client-server model of type 2 (server replication).
 *    Communication is based on a communication channel under the TCP protocol.
 * @author Martim Neves
 * @author Tiago Dias
 */
public class TableStub {
	
	/**
    * Name of the platform where the table server is located.
    */
    private String serverHostName;
    
    /**
     * Number of server listening port.
     */
    private int serverPortNumb;

    /**
     * Stub instantiation.
     *
     * @param hostName name of the platform where the table server is located.
     * @param port number of server listening port.
     */
    public TableStub (String hostName, int port){
       serverHostName = hostName;
       serverPortNumb = port;
    }

    /**
     * Waiter gets the pad and waits for the student to describe the order.
     * Waiter notifies the student that he has the pad.
     */
    public void getThePad(){
        Waiter w = (Waiter) Thread.currentThread();
        CommunicationChannel com = new CommunicationChannel(serverHostName, serverPortNumb);
        Object[] params = new Object[0];
    	Object[] state_fields = new Object[2];
        state_fields[0] = w.getWaiterID();
    	state_fields[1] = w.getWaiterState();

        Message m_toServer = new Message(13, params, 0, state_fields, 2, null);                                                          
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
     * Waits while there's no one to be served.
     * When a student is waiting to be served the waiter serves and notifies him.
     */
    public void deliverPortion(){
        Waiter w = (Waiter) Thread.currentThread();
        CommunicationChannel com = new CommunicationChannel(serverHostName, serverPortNumb);
        Object[] params = new Object[0];
    	Object[] state_fields = new Object[2];
        state_fields[0] = w.getWaiterID();
    	state_fields[1] = w.getWaiterState();

        Message m_toServer = new Message(14, params, 0, state_fields, 2, null);                                                          
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
     * Checks if all the clients have been served.
     * @return if all the clients have been served.
     */
    public boolean haveAllClientsBeenServed(){
        Waiter w = (Waiter) Thread.currentThread();
        CommunicationChannel com = new CommunicationChannel(serverHostName, serverPortNumb);
        Object[] params = new Object[0];
    	Object[] state_fields = new Object[2];
        state_fields[0] = w.getWaiterID();
    	state_fields[1] = w.getWaiterState();

        Message m_toServer = new Message(15, params, 0, state_fields, 2, null);                                                          
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
     * Waiter presents the bill and waits for the student to pay.
     * Waiter notifies the student that the bill is ready.
     */
    public void presentTheBill(){
        Waiter w = (Waiter) Thread.currentThread();
        CommunicationChannel com = new CommunicationChannel(serverHostName, serverPortNumb);
        Object[] params = new Object[0];
    	Object[] state_fields = new Object[2];
        state_fields[0] = w.getWaiterID();
    	state_fields[1] = w.getWaiterState();

        Message m_toServer = new Message(16, params, 0, state_fields, 2, null);                                                          
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
     * Student informs companion of his course choice.
     * Notifies the companion that he has chosen.
     */
    public void informCompanion(){
        Student s = (Student) Thread.currentThread();

    	CommunicationChannel com = new CommunicationChannel (serverHostName, serverPortNumb);
    	Object[] params = new Object[0];
    	Object[] state_fields = new Object[2];
    	state_fields[0] = s.getID();
    	state_fields[1] = s.getStudentState();
    	
        Message m_toServer = new Message(17, params, 0, state_fields, 2, null);                                                          
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
        s.setState((int) m_fromServer.getStateFields()[1]);
        com.close ();
    }

    /**
     * Increases the number of requests received by the first student.
     */
    public void prepareTheOrder(){
        Student s = (Student) Thread.currentThread();

    	CommunicationChannel com = new CommunicationChannel (serverHostName, serverPortNumb);
    	Object[] params = new Object[0];
    	Object[] state_fields = new Object[2];
    	state_fields[0] = s.getID();
    	state_fields[1] = s.getStudentState();
    	
        Message m_toServer = new Message(18, params, 0, state_fields, 2, null);                                                          
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
        s.setState((int) m_fromServer.getStateFields()[1]);
        com.close ();
    }
    
    /**
     * Student chats with his companions.
     */
    public void joinTheTalk(){
        Student s = (Student) Thread.currentThread();

    	CommunicationChannel com = new CommunicationChannel (serverHostName, serverPortNumb);
    	Object[] params = new Object[0];
    	Object[] state_fields = new Object[2];
    	state_fields[0] = s.getID();
    	state_fields[1] = s.getStudentState();
    	
        Message m_toServer = new Message(19, params, 0, state_fields, 2, null);                                                          
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
        s.setState((int) m_fromServer.getStateFields()[1]);
        com.close ();
    }

    /**
     * Waits for everybody to finished eating.
     */ 
    public void hasEverbodyFinished(){
        Student s = (Student) Thread.currentThread();

    	CommunicationChannel com = new CommunicationChannel (serverHostName, serverPortNumb);
    	Object[] params = new Object[0];
    	Object[] state_fields = new Object[2];
    	state_fields[0] = s.getID();
    	state_fields[1] = s.getStudentState();
    	
        Message m_toServer = new Message(20, params, 0, state_fields, 2, null);                                                          
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
        s.setState((int) m_fromServer.getStateFields()[1]);
        com.close ();
    }
    /**
     * Students wait until everyone is served and then start eating.
     */
    public void startEating(){
        Student s = (Student) Thread.currentThread();

    	CommunicationChannel com = new CommunicationChannel (serverHostName, serverPortNumb);
    	Object[] params = new Object[0];
    	Object[] state_fields = new Object[2];
    	state_fields[0] = s.getID();
    	state_fields[1] = s.getStudentState();
    	
        Message m_toServer = new Message(21, params, 0, state_fields, 2, null);                                                          
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
        s.setState((int) m_fromServer.getStateFields()[1]);
        com.close ();
    }

    /**
     * Students finish eating.
     */ 
    public void endEating(){
        Student s = (Student) Thread.currentThread();

    	CommunicationChannel com = new CommunicationChannel (serverHostName, serverPortNumb);
    	Object[] params = new Object[0];
    	Object[] state_fields = new Object[3];
    	state_fields[0] = s.getID();
    	state_fields[1] = s.getStudentState();
    	state_fields[2] = s.isLastStudent();
    	
        Message m_toServer = new Message(22, params, 0, state_fields, 3, null);                                                          
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
       
        s.setState((int) m_fromServer.getStateFields()[1]);
        s.setLastStudent((boolean) m_fromServer.getStateFields()[2]);
        
        com.close ();
    }

    /**
     * Student waits until the bill is ready and then pays it.
     * Notifies the waiter that the bill is payed.
     */
    public void honourTheBill(){
        Student s = (Student) Thread.currentThread();

    	CommunicationChannel com = new CommunicationChannel (serverHostName, serverPortNumb);
    	Object[] params = new Object[0];
    	Object[] state_fields = new Object[2];
    	state_fields[0] = s.getID();
    	state_fields[1] = s.getStudentState();
    	
        Message m_toServer = new Message(23, params, 0, state_fields, 2, null);                                                          
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
        s.setState((int) m_fromServer.getStateFields()[1]);
        com.close ();
    }

    /**
     * Student waits to be informed by his companions and increases the number of requests received by the first student.
     */
    public void addUpOnesChoice(){
        Student s = (Student) Thread.currentThread();

    	CommunicationChannel com = new CommunicationChannel (serverHostName, serverPortNumb);
    	Object[] params = new Object[0];
    	Object[] state_fields = new Object[2];
    	state_fields[0] = s.getID();
    	state_fields[1] = s.getStudentState();
    	
        Message m_toServer = new Message(24, params, 0, state_fields, 2, null);                                                          
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
        s.setState((int) m_fromServer.getStateFields()[1]);
        com.close ();
    }

    /**
     * Checks if everybody has chosen.
     * @return if everybody has chosen.
     */
    public boolean hasEverybodyChosen(){
        Student s = (Student) Thread.currentThread();

    	CommunicationChannel com = new CommunicationChannel (serverHostName, serverPortNumb);
    	Object[] params = new Object[0];
    	Object[] state_fields = new Object[2];
    	state_fields[0] = s.getID();
    	state_fields[1] = s.getStudentState();
    	
        Message m_toServer = new Message(25, params, 0, state_fields, 2, null);                                                          
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
       
        s.setState((int) m_fromServer.getStateFields()[1]);
        
        boolean result = (Boolean) m_fromServer.getReturnValue();
        
        com.close ();
        return result;
    }

    /**
     * Student calls the waiter when everyone has chosen and waits for the waiter to get the pad.
     * Notifies the waiter that the order has been described.
     */
    public void describeTheOrder(){
        Student s = (Student) Thread.currentThread();

    	CommunicationChannel com = new CommunicationChannel (serverHostName, serverPortNumb);
    	Object[] params = new Object[0];
    	Object[] state_fields = new Object[2];
    	state_fields[0] = s.getID();
    	state_fields[1] = s.getStudentState();
    	
        Message m_toServer = new Message(26, params, 0, state_fields, 2, null);                                                          
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
        s.setState((int) m_fromServer.getStateFields()[1]);
        com.close ();
    }

    /**
     * Student blocks while not served by waiter
     * @param sID student id
     */
    public void waitingToBeServed(int sID){
        Student s = (Student) Thread.currentThread();

    	CommunicationChannel com = new CommunicationChannel (serverHostName, serverPortNumb);
    	Object[] params = new Object[0];
    	Object[] state_fields = new Object[2];
    	state_fields[0] = s.getID();
    	state_fields[1] = s.getStudentState();
    	
        Message m_toServer = new Message(27, params, 0, state_fields, 2, null);                                                          
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
        s.setState((int) m_fromServer.getStateFields()[1]);
        com.close ();
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
