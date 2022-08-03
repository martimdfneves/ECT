package serverSide.main;

import java.net.SocketTimeoutException;

import commInfra.ServerCom;
import genclass.GenericIO;
import serverSide.sharedRegions.Kitchen;
import serverSide.sharedRegions.KitchenMessageExchange;
import serverSide.sharedRegions.SharedRegionInterface;
import serverSide.stubs.GeneralReposStub;
import serverSide.entities.ServiceProviderAgent;

/**
 * Server side of the Kitchen Shared Region.
 *
 * Request serialization variant.
 * It waits for the start of the message exchange.
 * @author Martim Neves
 * @author Tiago Dias
 */
public class KitchenMain{
  
	/**
	 * Main method.
	 * @param args runtime arguments
	 */
	public static void main (String[] args){
    	ServerCom serverCom, sconi;                                        
       
    	serverCom = new ServerCom (SimPar.KITCHEN_PORT);
    	serverCom.start ();                             
    	serverCom.setSoTimeout(10000);
    	GenericIO.writelnString ("Service is established!");
    	GenericIO.writelnString ("Server is listening for service requests.");

    	GeneralReposStub generalReposStub = new GeneralReposStub(SimPar.LOGGER_HOST_NAME, SimPar.LOGGER_PORT);
    	Kitchen kitchen = new Kitchen(generalReposStub);
    	SharedRegionInterface sharedRegionInterface = new KitchenMessageExchange(kitchen);

    	while (!sharedRegionInterface.hasShutdown()){ 
    		try {
    			sconi = serverCom.accept ();                                     
    			ServiceProviderAgent serviceProviderAgent = new ServiceProviderAgent (sconi, sharedRegionInterface);            // start a service provider agent to address
    			serviceProviderAgent.start ();      
    		} 
    		catch(SocketTimeoutException ste) {}
    	}
     
    	GenericIO.writelnString("\033[41m Service is closed! \033[0m");
    }
}
