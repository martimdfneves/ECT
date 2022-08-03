package main;
import entities.*;
import sharedRegions.bar.Bar;
import sharedRegions.kitchen.Kitchen;
import sharedRegions.table.Table;
import utils.SimPar;

/**
 * Main program class:
 * Main function launches all threads: students, chef and waiter.
 * Uses the simulation parameters.
 * The shared regions are also initialized, as well as the logger and its parameters.
 * 
 * @author Martim Neves
 * @author Tiago Dias
 */

public class Restaurant {
    
	public static void main(String[] args){
		System.out.println("Started");  
		
        Logger logger = new Logger (SimPar.logFileName);
        System.out.println("Logger Created");

        // Shared regions
        Bar bar = new Bar(logger);
        Kitchen kitchen = new Kitchen(logger);
        Table table = new Table(logger);
        System.out.println("Shared Regions Created");

        // Entities
        Chef chef = new Chef(ChefStates.WAITING_FOR_AN_ORDER, kitchen, bar);
        Waiter waiter = new Waiter(WaiterStates.APPRAISING_SITUATION, kitchen, bar, table);
        Student[] students = new Student[SimPar.students_number];
        
        for(int i=0;i<students.length;i++) {
            students[i] = new Student(StudentStates.GOING_TO_THE_RESTAURANT, bar, table);
        }
        System.out.println("Entities created");    
        
        System.out.println("------------------------------SIMULATION BEGIN------------------------------");
        chef.start();
        chef.setName("Chef");
        waiter.start();
        waiter.setName("Waiter");
        
        for(int i=0;i<students.length;i++){
            students[i].start();
            students[i].setName("Student["+i+"]");
        }

        for(int i=0;i<students.length;i++){
            try {
                students[i].join();
            } catch (InterruptedException e) {
            	System.out.println("Main Program - One thread of Student"+i+" was interrupted.");
                System.exit(1);
            } 
        }
        
        try {
            chef.join();
        } catch (InterruptedException e) {
        	System.out.println("Main Program - One thread of Chef was interrupted.");
            System.exit(1);
        }
        
        try {
            waiter.join();
        } catch (InterruptedException e) {
        	 System.out.println("Main Program - One thread of Waiter was interrupted.");
             System.exit(1);
        }

    }
}
