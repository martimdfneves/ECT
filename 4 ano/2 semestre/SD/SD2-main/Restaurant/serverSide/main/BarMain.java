package serverSide.main;

import java.net.SocketTimeoutException;

import commInfra.ServerCom;
import genclass.GenericIO;
import serverSide.sharedRegions.Bar;
import serverSide.sharedRegions.BarMessageExchange;
import serverSide.sharedRegions.SharedRegionInterface;
import serverSide.stubs.GeneralReposStub;
import serverSide.entities.ServiceProviderAgent;

/**
 * Server side of the Bar Shared Region.
 *
 * Request serialization variant.
 * It waits for the start of the message exchange.
 * @author Martim Neves
 * @author Tiago Dias
 */
public class BarMain{
	
	/**
	 *    Main method.
	 *    @param args runtime arguments
	 */
    public static void main (String[] args){
    	ServerCom serverCom, sconi;                                        

    	serverCom = new ServerCom (SimPar.BAR_PORT);
    	serverCom.start ();                             
    	serverCom.setSoTimeout(10000);
    	GenericIO.writelnString ("Service is established!");
    	GenericIO.writelnString ("Server is listening for service requests.");

    	GeneralReposStub generalReposStub = new GeneralReposStub(SimPar.LOGGER_HOST_NAME, SimPar.LOGGER_PORT);
    	Bar bar = new Bar(generalReposStub);
    	SharedRegionInterface sharedRegionInterface = new BarMessageExchange(bar);
       
    	while (!sharedRegionInterface.hasShutdown()){ 
    		try {
    			sconi = serverCom.accept ();                                     
    			ServiceProviderAgent serviceProviderAgent = new ServiceProviderAgent (sconi, sharedRegionInterface);            // start a service provider agent to address
    			serviceProviderAgent.start ();      
    		} 
    		catch(SocketTimeoutException ste) {}
    	}

    	generalReposStub.shutdown();
    	GenericIO.writelnString("\033[41m Service is closed! \033[0m");
    }
}
