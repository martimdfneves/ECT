package serverSide.main;

import java.net.SocketTimeoutException;
import java.rmi.AlreadyBoundException;
import java.rmi.NoSuchObjectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import commInfra.SimPar;
import genclass.GenericIO;
import interfaces.KitchenInterface;
import interfaces.GeneralReposInterface;
import interfaces.RegisterInterface;
import serverSide.objects.Kitchen;

/**
 *    Instantiation and registering of a kitchen object.
 *
 *    Implementation of a client-server model of type 2 (server replication).
 *    Communication is based on Java RMI.
 * @author Martim Neves
 * @author Tiago Dias
 */
public class KitchenMain{
	
	/**
	 *  Flag signaling the end of operations.
	 */

	 private static boolean end = false;
  
	 /**
	   *  Main method.
	   *
	   *     @param args runtime arguments
	   *        args[0] - port number for listening to service requests
	   *        args[1] - name of the platform where is located the RMI registering service
	   *        args[2] - port number where the registering service is listening to service requests
	   */

	   public static void main (String[] args)
	   {
	      int portNumb = -1;                                             // port number for listening to service requests
	      String rmiRegHostName;                                         // name of the platform where is located the RMI registering service
	      int rmiRegPortNumb = -1;                                       // port number where the registering service is listening to service requests

	      if (args.length != 3)
	         { GenericIO.writelnString ("Wrong number of parameters!");
	           System.exit (1);
	         }
	      try
	      { portNumb = Integer.parseInt (args[0]);
	      }
	      catch (NumberFormatException e)
	      { GenericIO.writelnString ("args[0] is not a number!");
	        System.exit (1);
	      }
	      if ((portNumb < 4000) || (portNumb >= 65536))
	         { GenericIO.writelnString ("args[0] is not a valid port number!");
	           System.exit (1);
	         }
	      rmiRegHostName = args[1];
	      try
	      { rmiRegPortNumb = Integer.parseInt (args[2]);
	      }
	      catch (NumberFormatException e)
	      { GenericIO.writelnString ("args[2] is not a number!");
	        System.exit (1);
	      }
	      if ((rmiRegPortNumb < 4000) || (rmiRegPortNumb >= 65536))
	         { GenericIO.writelnString ("args[2] is not a valid port number!");
	           System.exit (1);
	         }
	    
	    /* create and install the security manager */

	    if (System.getSecurityManager () == null)
	       System.setSecurityManager (new SecurityManager ());
	    GenericIO.writelnString ("Security manager was installed!");

	    /* get a remote reference to the general repository object */

	    String nameEntryGeneralRepos = "GeneralRepository";            // public name of the general repository object
	    GeneralReposInterface reposStub = null;                        // remote reference to the general repository object
	    Registry registry = null;                                      // remote reference for registration in the RMI registry service

	    try
	    { registry = LocateRegistry.getRegistry (rmiRegHostName, rmiRegPortNumb);
	    }
	    catch (RemoteException e)
	    { GenericIO.writelnString ("RMI registry creation exception: " + e.getMessage ());
	      e.printStackTrace ();
	      System.exit (1);
	    }
	    GenericIO.writelnString ("RMI registry was created!");

	    try
	    { reposStub = (GeneralReposInterface) registry.lookup (nameEntryGeneralRepos);
	    }
	    catch (RemoteException e)
	    { GenericIO.writelnString ("GeneralRepos lookup exception: " + e.getMessage ());
	      e.printStackTrace ();
	      System.exit (1);
	    }
	    catch (NotBoundException e)
	    { GenericIO.writelnString ("GeneralRepos not bound exception: " + e.getMessage ());
	      e.printStackTrace ();
	      System.exit (1);
	    }
	    
	    /* instantiate a kitchen object */

	    Kitchen kitchen = new Kitchen (reposStub);                 // kitchen object
	    KitchenInterface kitchenStub = null;                   // remote reference to the kitchen object

	    try
	    { kitchenStub = (KitchenInterface) UnicastRemoteObject.exportObject (kitchen, portNumb);
	    }
	    catch (RemoteException e)
	    { GenericIO.writelnString ("Kitchen stub generation exception: " + e.getMessage ());
	      e.printStackTrace ();
	      System.exit (1);
	    }
	    GenericIO.writelnString ("Stub was generated!");

	    /* register it with the general registry service */

	    String nameEntryBase = "RegisterHandler";           // public name of the object that enables the registration                                                               // of other remote objects
	    String nameEntryObject = "Kitchen";                     // public name of the Kitchen object
	     
	    RegisterInterface reg = null;                       // remote reference to the object that enables the registration
	                                                        // of other remote objects

	    try
	    { reg = (RegisterInterface) registry.lookup (nameEntryBase);
	    }
	    catch (RemoteException e)
	    { GenericIO.writelnString ("RegisterRemoteObject lookup exception: " + e.getMessage ());
	      e.printStackTrace ();
	      System.exit (1);
	    }
	    catch (NotBoundException e)
	    { GenericIO.writelnString ("RegisterRemoteObject not bound exception: " + e.getMessage ());
	      e.printStackTrace ();
	      System.exit (1);
	    }

	    try
	    { reg.bind (nameEntryObject, kitchenStub);
	    }
	    catch (RemoteException e)
	    { GenericIO.writelnString ("Kitchen registration exception: " + e.getMessage ());
	      e.printStackTrace ();
	      System.exit (1);
	    }
	    catch (AlreadyBoundException e)
	    { GenericIO.writelnString ("Kitchen already bound exception: " + e.getMessage ());
	      e.printStackTrace ();
	      System.exit (1);
	    }
	    GenericIO.writelnString ("\033[42m Kitchen object was registered! \033[0m");
	    
	    /* wait for the end of operations */

	    GenericIO.writelnString ("\033[42m Kitchen is in operation! \033[0m");
	    try
	    { while (!end)
	        synchronized (Class.forName ("serverSide.main.KitchenMain"))
	        { try
	          { (Class.forName ("serverSide.main.KitchenMain")).wait ();
	          }
	          catch (InterruptedException e)
	          { GenericIO.writelnString ("KitchenMain main thread was interrupted!");
	          }
	        }
	    }
	    catch (ClassNotFoundException e)
	    { GenericIO.writelnString ("The data type KitchenMain was not found (blocking)!");
	      e.printStackTrace ();
	      System.exit (1);
	    }

	    /* server shutdown */

	    boolean shutdownDone = false;                                  // flag signaling the shutdown of the kitchen service

	    try
	    { reg.unbind (nameEntryObject);
	    }
	    catch (RemoteException e)
	    { GenericIO.writelnString ("Kitchen deregistration exception: " + e.getMessage ());
	      e.printStackTrace ();
	      System.exit (1);
	    }
	    catch (NotBoundException e)
	    { GenericIO.writelnString ("Kitchen not bound exception: " + e.getMessage ());
	      e.printStackTrace ();
	      System.exit (1);
	    }
	    GenericIO.writelnString ("\033[41m Kitchen was deregistered! \033[0m");

	    try
	    { shutdownDone = UnicastRemoteObject.unexportObject (kitchen, true);
	    }
	    catch (NoSuchObjectException e)
	    { GenericIO.writelnString ("Kitchen unexport exception: " + e.getMessage ());
	      e.printStackTrace ();
	      System.exit (1);
	    }

	    if (shutdownDone)
	       GenericIO.writelnString ("\033[41m Kitchen was shutdown!  \033[0m");
	   }   
    
	   /**
	    *  Close of operations.
	    */

	    public static void shutdown ()
	    {
	       end = true;
	       try
	       { synchronized (Class.forName ("serverSide.main.KitchenMain"))
	         { (Class.forName ("serverSide.main.KitchenMain")).notify();
	         }
	       }
	      catch (ClassNotFoundException e)
	      { GenericIO.writelnString ("The data type KitchenMain was not found (waking up)!");
	        e.printStackTrace ();
	        System.exit (1);
	      }
	   }
}