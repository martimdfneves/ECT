package clientSide.main;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import clientSide.entities.Student;
import commInfra.SimPar;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import genclass.GenericIO;
import interfaces.TableInterface;
import interfaces.GeneralReposInterface;
import interfaces.BarInterface;

/**
 * Client side of the Restaurant (student)
 * 
 * @author Martim Neves
 * @author Tiago Dias
 */
public class StudentMain {
	
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
        
        String nameEntryTable = "Table";  
        String nameEntryBar = "Bar";
        
        TableInterface table = null;
        BarInterface bar = null;
        
        Registry registry = null;
    	
        Student[] students = new Student[SimPar.STUDENTS];

        try
        { registry = LocateRegistry.getRegistry (rmiRegHostName, rmiRegPortNumb);
        }
        catch (RemoteException e)
        { GenericIO.writelnString ("RMI registry creation exception: " + e.getMessage ());
          e.printStackTrace ();
          System.exit (1);
        }
        
        //Lookup all the stubs
        

        try
        { table = (TableInterface) registry.lookup (nameEntryTable);
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
        
        for (int i = 0; i < SimPar.STUDENTS; i++)
        	students[i] = new Student(i, bar, table);

         /* start of the simulation */

        for (int i = 0; i < SimPar.STUDENTS; i++)
        	students[i].start ();
        
        /* wait for the end */
        for (int i = 0; i < SimPar.STUDENTS; i++)
        { try
        { students[i].join ();
        }
        catch (InterruptedException e) {}
        System.out.println("\033[41m The Student "+(i)+" just terminated \033[0m");
        }
        
        try
        { table.shutdown ();
        }
        catch (RemoteException e)
        { GenericIO.writelnString ("Student generator remote exception on Table shutdown: " + e.getMessage ());
          System.exit (1);
        }
       try
        { bar.shutdown ();
        }
        catch (RemoteException e)
        { GenericIO.writelnString ("Student generator remote exception on Bar shutdown: " + e.getMessage ());
          System.exit (1);
        }
        
       System.out.println("\033[44m End of the Simulation \033[0m");
    }
}
