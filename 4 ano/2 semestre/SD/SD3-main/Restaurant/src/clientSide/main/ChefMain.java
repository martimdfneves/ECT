package clientSide.main;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import clientSide.entities.Chef;
import commInfra.SimPar;
import genclass.GenericIO;
import interfaces.KitchenInterface;
import interfaces.BarInterface;
import interfaces.GeneralReposInterface;

/**
 * Client side of the Restaurant (chef)
 * 
 * @author Martim Neves
 * @author Tiago Dias
 */
public class ChefMain {

	/**
	 *  Main method.
	 *
	 *    @param args runtime arguments
	 *        args[0] - name of the platform where is located the RMI registering service
	 *        args[1] - port number where the registering service is listening to service requests
	 */
	
    public static void main(String[] args){
    	
    	String rmiRegHostName;
	    int rmiRegPortNumb = -1;

	    /* getting problem runtime parameters */

	    if (args.length != 2)
	       { GenericIO.writelnString ("Wrong number of parameters!");
	         System.exit (1);
	       }
	    rmiRegHostName = args[0];
	    try
	    { rmiRegPortNumb = Integer.parseInt (args[1]);
	    }
	    catch (NumberFormatException e)
	    { GenericIO.writelnString ("args[1] is not a number!");
	      System.exit (1);
	    }
	    if ((rmiRegPortNumb < 4000) || (rmiRegPortNumb >= 65536))
	       { GenericIO.writelnString ("args[1] is not a valid port number!");
	         System.exit (1);
	       }
    	
    	String nameEntryKitchen = "Kitchen";  
	    String nameEntryBar = "Bar";
	    
	    KitchenInterface kitchen = null;
	    BarInterface bar = null;
	     
	    Registry registry = null;
    	
        Chef chef;

        try
	    { registry = LocateRegistry.getRegistry (rmiRegHostName, rmiRegPortNumb);
	    }
	    catch (RemoteException e)
	    { GenericIO.writelnString ("RMI registry creation exception: " + e.getMessage ());
	      e.printStackTrace ();
	      System.exit (1);
	    }
        
        //Lookup all the interfaces
	     
	    try
	    { kitchen = (KitchenInterface) registry.lookup (nameEntryKitchen);
	    }
	    catch (RemoteException e)
	    { 
	      e.printStackTrace ();
	      System.exit (1);
	    }
	    catch (NotBoundException e)
	    { 
	      e.printStackTrace ();
	      System.exit (1);
	    }
	    
	    try
	    { bar = (BarInterface) registry.lookup (nameEntryBar);
	    }
	    catch (RemoteException e)
	    { 
	      e.printStackTrace ();
	      System.exit (1);
	    }
	    catch (NotBoundException e)
	    { 
	      e.printStackTrace ();
	      System.exit (1);
	    }
	    
	    chef = new Chef(0, kitchen, bar);
	    
	    /* start of the simulation */

	    chef.start();
	     
	    /* wait for the end */
	    try
	    { chef.join ();
	    }
	    catch (InterruptedException e) {}
	    System.out.println("\033[41m The Chef "+(0)+" just terminated \033[0m");
	     
	    try
	     { kitchen.shutdown ();
	     }
	     catch (RemoteException e)
	     { GenericIO.writelnString ("Chef generator remote exception on Kitchen shutdown: " + e.getMessage ());
	       System.exit (1);
	     }
	    try
	     { bar.shutdown ();
	     }
	     catch (RemoteException e)
	     { GenericIO.writelnString ("Chef generator remote exception on Bar shutdown: " + e.getMessage ());
	       System.exit (1);
	    }
	    System.out.println("\033[44m End of the Simulation \033[0m");
    }
}
