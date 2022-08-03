package clientSide.main;

import clientSide.entities.Waiter;
import clientSide.entities.WaiterState;
import clientSide.stubs.KitchenStub;
import clientSide.stubs.TableStub;
import serverSide.main.SimPar;
import clientSide.stubs.BarStub;

/**
 * Client side of the Restaurant (waiter)
 * 
 * @author Martim Neves
 * @author Tiago Dias
 */
public class WaiterMain {
	
	/**
     * Main Waiter launcher
     * @param args runtime arguments
     */
    public static void main(String[] args){
        Waiter waiter;

        KitchenStub kitchen;
        BarStub bar;
        TableStub table;

        kitchen = new KitchenStub(SimPar.KITCHEN_HOST_NAME, SimPar.KITCHEN_PORT);
        bar = new BarStub(SimPar.BAR_HOST_NAME, SimPar.BAR_PORT);
        table = new TableStub(SimPar.TABLE_HOST_NAME, SimPar.TABLE_PORT);

        waiter = new Waiter(0, WaiterState.APPRAISING_SITUATION,kitchen, bar,table);

        waiter.start();

        try{
            waiter.join();
        }catch(InterruptedException e){}

        System.out.println("\033[41m The Waiter thread has been terminated \033[0m");
    }
}
