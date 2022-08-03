package clientSide.stubs;

import clientSide.entities.*;
import commInfra.*;

/**
* Stub to the bar.
*
* It instantiates a remote reference to the bar.
* Implementation of a client-server model of type 2 (server replication).
* Communication is based on a communication channel under the TCP protocol.
* @author Martim Neves
* @author Tiago Dias
*/
public class BarStub {
	
	/**
     * Name of the platform where the bar server is located.
     */
    private String serverHostName;

    /**
     * Number of server listening port.
     */
    private int serverPortNumb;

    /**
     * Stub instantiation.
     *
     * @param hostName name of the platform where the bar server is located.
     * @param port Number of server listening port.
     */  
    public BarStub(String hostName, int port){
       serverHostName = hostName;
       serverPortNumb = port;
    }

    /**
     * Salutes the client and waits for the student to read the menu.
     * Notifies the student that he has been saluted.
     */
    public void saluteTheClient(){
        Waiter w = (Waiter) Thread.currentThread();
        CommunicationChannel com = new CommunicationChannel(serverHostName, serverPortNumb);
        Object[] params = new Object[0];
    	Object[] state_fields = new Object[2];
        state_fields[0] = w.getWaiterID();
    	state_fields[1] = w.getWaiterState();

        Message m_toServer = new Message(0, params, 0, state_fields, 2, null);                                                          
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
     * Notifies a new order in the requests queue.
     */
    public void alertTheWaiter(){
        Chef c = (Chef) Thread.currentThread();
        CommunicationChannel com = new CommunicationChannel(serverHostName, serverPortNumb);
        Object[] params = new Object[0];
    	Object[] state_fields = new Object[2];
        state_fields[0] = c.getChefID();
    	state_fields[1] = c.getChefState();

        Message m_toServer = new Message(1, params, 0, state_fields, 2, null);                                                          
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
     * Waiter returns to the bar.
     */ 
    public void returningToTheBar(){
        Waiter w = (Waiter) Thread.currentThread();
        CommunicationChannel com = new CommunicationChannel(serverHostName, serverPortNumb);
        Object[] params = new Object[0];
    	Object[] state_fields = new Object[2];
        state_fields[0] = w.getWaiterID();
    	state_fields[1] = w.getWaiterState();

        Message m_toServer = new Message(2, params, 0, state_fields, 2, null);                                                          
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
     * Waiter prepares the bill.
     */
    public void prepareTheBill(){
        Waiter w = (Waiter) Thread.currentThread();
        CommunicationChannel com = new CommunicationChannel(serverHostName, serverPortNumb);
        Object[] params = new Object[0];
    	Object[] state_fields = new Object[2];
        state_fields[0] = w.getWaiterID();
    	state_fields[1] = w.getWaiterState();

        Message m_toServer = new Message(3, params, 0, state_fields, 2, null);                                                          
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
     * Waiter is waiting for a request.
     * @return result the communication result
     */
    public int lookAround(){
        Waiter w = (Waiter) Thread.currentThread();
        CommunicationChannel com = new CommunicationChannel(serverHostName, serverPortNumb);
        Object[] params = new Object[0];
    	Object[] state_fields = new Object[2];
        state_fields[0] = w.getWaiterID();
    	state_fields[1] = w.getWaiterState();

        Message m_toServer = new Message(4, params, 0, state_fields, 2, null);                                                          
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
        int result = (int) m_fromServer.getReturnValue();
        com.close ();

        return result;
    }

    /**
     * Waiter says goodbye to students.
     */
    public void sayGoodbye(){
        Waiter w = (Waiter) Thread.currentThread();
        CommunicationChannel com = new CommunicationChannel(serverHostName, serverPortNumb);
        Object[] params = new Object[0];
    	Object[] state_fields = new Object[3];
        state_fields[0] = w.getWaiterID();
    	state_fields[1] = w.getWaiterState();

        Message m_toServer = new Message(5, params, 0, state_fields, 2, null);                                                          
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
        w.setCanGoHome();
        com.close ();
    }

    /**
     * Student signals the waiter to bring the next course.
     * Notifies the waiter that all the people already ate.
     * @param sID student id
     */
    public void signalTheWaiter(int sID){
        Student s = (Student) Thread.currentThread();

    	CommunicationChannel com = new CommunicationChannel (serverHostName, serverPortNumb);
    	Object[] params = new Object[0];
    	Object[] state_fields = new Object[3];
    	state_fields[0] = s.getID();
    	state_fields[1] = s.getStudentState();
    	state_fields[2] = s.isLastStudent();
    	
        Message m_toServer = new Message(6, params, 0, state_fields, 3, null);                                                          
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
     * Student calls the waiter when everyone has chosen.
     * Notifies a new order in the requests queue.
     */
    public void callTheWaiter(){
        Student s = (Student) Thread.currentThread();

    	CommunicationChannel com = new CommunicationChannel (serverHostName, serverPortNumb);
    	Object[] params = new Object[0];
    	Object[] state_fields = new Object[2];
    	state_fields[0] = s.getID();
    	state_fields[1] = s.getStudentState();
    	
        Message m_toServer = new Message(7, params, 0, state_fields, 2, null);                                                          
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
     * Student enters the restaurant.
     * Notifies the waiter that a student has entered.
     * Adds a request of type SALUTE_THE_CLIENT to the queue.
     */
    public void enter(){
        Student s = (Student) Thread.currentThread();

    	CommunicationChannel com = new CommunicationChannel (serverHostName, serverPortNumb);
    	Object[] params = new Object[0];
    	Object[] state_fields = new Object[2];
    	state_fields[0] = s.getID();
    	state_fields[1] = s.getStudentState();
    	
        Message m_toServer = new Message(8, params, 0, state_fields, 2, null);                                                          
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
     * Checks if the student was the first to enter
     * @param sID student id
     * @return true if it was the first to enter, false otherwise
     */
    public boolean FirstStudent(int sID){
        Student s = (Student) Thread.currentThread();
    	CommunicationChannel com = new CommunicationChannel (serverHostName, serverPortNumb);
    	Object[] params = new Object[0];
    	Object[] state_fields = new Object[2];
    	state_fields[0] = s.getID();
    	state_fields[1] = s.getStudentState();
    	
        Message m_toServer = new Message(9, params, 0, state_fields, 2, null);                                                          
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
     * Student leaves the restaurant.
     * Notifies the waiter that a student has left.
     * Adds a request of type SAY_GOODBYE to the queue.
     */
    public void exit(){
        Student s = (Student) Thread.currentThread();

    	CommunicationChannel com = new CommunicationChannel (serverHostName, serverPortNumb);
    	Object[] params = new Object[0];
    	Object[] state_fields = new Object[2];
    	state_fields[0] = s.getID();
    	state_fields[1] = s.getStudentState();
    	
        Message m_toServer = new Message(10, params, 0, state_fields, 2, null);                                                          
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
     * Gets the id of the student who arrived last.
     * Notifies a new order in the requests queue.
     * @param sID student id
     * @return boolean if the student was the last to enter the restaurant
     */
    public boolean shouldHaveArrivedEarlier(int sID){
        Student s = (Student) Thread.currentThread();
    	CommunicationChannel com = new CommunicationChannel (serverHostName, serverPortNumb);
    	Object[] params = new Object[0];
    	Object[] state_fields = new Object[2];
    	state_fields[0] = s.getID();
    	state_fields[1] = s.getStudentState();
    	
        Message m_toServer = new Message(11, params, 0, state_fields, 2, null);                                                          
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
     * Student reads the menu.
     * Notifies the waiter that he has read the menu.
     */
    public void readTheMenu(){
        Student s = (Student) Thread.currentThread();

    	CommunicationChannel com = new CommunicationChannel (serverHostName, serverPortNumb);
    	Object[] params = new Object[0];
    	Object[] state_fields = new Object[2];
    	state_fields[0] = s.getID();
    	state_fields[1] = s.getStudentState();
    	
        Message m_toServer = new Message(12, params, 0, state_fields, 2, null);                                                          
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
