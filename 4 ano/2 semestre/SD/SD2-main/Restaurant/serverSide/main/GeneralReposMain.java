package serverSide.main;

import java.net.SocketTimeoutException;
import commInfra.ServerCom;
import genclass.GenericIO;
import serverSide.entities.ServiceProviderAgent;
import serverSide.sharedRegions.*;

/**
 * Server side of the General Repository Shared Region.
 *
 * Request serialization variant.
 * It waits for the start of the message exchange.
 * @author Martim Neves
 * @author Tiago Dias
 */
public class GeneralReposMain{
  
	/**
	 * Main method.
	 * @param args runtime arguments
	 */
	public static void main (String[] args){
		ServerCom serverCom, sconi;                                        
      
		serverCom = new ServerCom (SimPar.LOGGER_PORT);
		serverCom.start ();                             
		serverCom.setSoTimeout(10000);
		GenericIO.writelnString ("Service is established!");
		GenericIO.writelnString ("Server is listening for service requests.");

		GeneralRepos generalRepos = new GeneralRepos(SimPar.LOG_FILE_NAME);
		SharedRegionInterface sharedRegionInterface = new GeneralReposMessageExchange(generalRepos);
       
		while (!sharedRegionInterface.hasShutdown()){ 
			try {
				sconi = serverCom.accept ();                                    
				ServiceProviderAgent serviceProviderAgent = new ServiceProviderAgent (sconi, sharedRegionInterface);            // start a service provider agent to address
				serviceProviderAgent.start ();      
			} 
			catch(SocketTimeoutException ste) {}
		}
       
		GenericIO.writelnString ("\033[41m Service is closed! \033[0m");
    }
}
