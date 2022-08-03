package clientSide.main;

import clientSide.entities.Chef;
import clientSide.entities.ChefState;
import clientSide.stubs.KitchenStub;
import serverSide.main.SimPar;
import clientSide.stubs.BarStub;

/**
 * Client side of the Restaurant (chef)
 * 
 * @author Martim Neves
 * @author Tiago Dias
 */
public class ChefMain {

    /**
     * Main Chef launcher
     * @param args runtime arguments
     */
    public static void main(String[] args){
        Chef chef;

        KitchenStub kitchen;
        BarStub bar;

        kitchen = new KitchenStub(SimPar.KITCHEN_HOST_NAME, SimPar.KITCHEN_PORT);
        bar = new BarStub(SimPar.BAR_HOST_NAME, SimPar.BAR_PORT);

        chef = new Chef(0, ChefState.WAITING_FOR_AN_ORDER, kitchen, bar);

        chef.start();

        try{
            chef.join();
        }catch(InterruptedException e){}

        System.out.println("\033[41m The Chef thread has been terminated \033[0m");
    }
}
