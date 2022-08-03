package clientSide.entities;

import java.rmi.RemoteException;
import commInfra.ReturnValue;
import commInfra.SimPar;
import interfaces.BarInterface;
import interfaces.TableInterface;

/**
 * Student thread:
 * Implements the life-cycle of a Student and stores the variables referent to
 * that student and his current state.
 * @author Martim Neves
 * @author Tiago Dias
 */
public class Student extends Thread{
	
	/**
     * Current Student state
     */
    private int state;
    
    /**
     * The student ID
     */
    private int sID;
    
    /**
     * Reference to the bar.
     */
    private BarInterface bar;
    
    /**
     * Reference to the table
     */
    private TableInterface table;
    
    private boolean last = false;

    
    /**
     * Student constructor
     *
     * @param id student id
     * @param bar reference to the bar.
     * @param table reference to the table.
     */
    public Student(int id, BarInterface bar, TableInterface table){
        this.sID=id;
        state = StudentState.GOING_TO_THE_RESTAURANT;
        this.bar = bar;
        this.table = table;
    }

    /**
     * Implements the life cycle of the student
     */
    @Override
    public void run(){
        walkABit();
        enter();
        readTheMenu();

        if(FirstStudent()){
            prepareTheOrder();
            while(!hasEverybodyChosen()){
                addUpOnesChoice();
            }
            
            callTheWaiter();
            describeTheOrder();
            joinTheTalk();
        }
        else{
            informCompanion();
        }

        int courses = 0;
        while(courses < SimPar.COURSES){
            waitingToBeServed();
            startEating();
            last = endEating();
           
            hasEverbodyFinished();
            last = signalTheWaiter();
            courses++;
        }

        if(shouldHaveArrivedEarlier()){
            honourTheBill();
            System.out.printf("\033[42m Student[%d] payed the bill \033[0m\n", this.sID);
        }

        exit();
        System.out.printf("Student[" + this.sID + "] End Of Life\n");
    }    
    
    /**
     * Student walks outside for a random time
     * Internal operation
     */
    public void walkABit() {
        try {
            sleep((long) (3 + 100 * Math.random()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Set the current student state 
     * @param state an integer that represents the current student state
     */
    public void setState(int state){
        this.state = state;
    }

    /**
     * Get the student state 
     * @return student state
     */
    public int getStudentState(){
        return this.state; 
    }

    /**
     * Get the student ID 
     * @return student id
     */
    public int getID(){
        return this.sID;
    }
    
    /**
     * Set the student ID 
     * @param id student id
     */
    public void setID(int id){
        this.sID = id;
    }
    
    /**
    *
    */
   private void enter() {
   	ReturnValue ret = null;
   	try
       { ret = bar.enter(this.sID);
       }
       catch (RemoteException e)
       { 
         System.exit (1);
       }
   	this.state = ret.getStateValue();
   }
   
   private void readTheMenu() {
	   	ReturnValue ret = null;
	   	try
	       { ret = bar.readTheMenu(this.sID);
	       }
	       catch (RemoteException e)
	       { 
	         System.exit (1);
	       }
   	this.state = ret.getStateValue();
   }
   
   private boolean FirstStudent() {
	   	boolean ret = false;
	   	try
	       { ret = bar.FirstStudent(this.sID);
	       }
	       catch (RemoteException e)
	       { 
	         System.exit (1);
	       }
	   	return ret;
   }
   
   private void callTheWaiter() {
	   	ReturnValue ret = null;
	   	try
	       { ret = bar.callTheWaiter(this.sID);
	       }
	       catch (RemoteException e)
	       { 
	         System.exit (1);
	       }
  	this.state = ret.getStateValue();
  }
   
   private boolean signalTheWaiter() {
	   	ReturnValue ret = null;
	   	try
	       { ret = bar.signalTheWaiter(this.sID, last);
	       }
	       catch (RemoteException e)
	       { 
	         System.exit (1);
	       }
  	this.state = ret.getStateValue();
  	return ret.getBooleanValue();
  }
   
   private boolean shouldHaveArrivedEarlier() {
	   	ReturnValue ret = null;
	   	try
	       { ret = bar.shouldHaveArrivedEarlier(this.sID);
	       }
	       catch (RemoteException e)
	       { 
	         System.exit (1);
	       }
  	this.state = ret.getStateValue();
  	return ret.getBooleanValue();
  }
   
   private void exit() {
	   	ReturnValue ret = null;
	   	try
	       { ret = bar.exit(this.sID);
	       }
	       catch (RemoteException e)
	       { 
	         System.exit (1);
	       }
 	this.state = ret.getStateValue();
 }
   
   private void prepareTheOrder() {
	   	ReturnValue ret = null;
	   	try
	       { ret = table.prepareTheOrder(this.sID);
	       }
	       catch (RemoteException e)
	       { 
	         System.exit (1);
	       }
 	this.state = ret.getStateValue();
 }
   
   private boolean hasEverybodyChosen() {
	   	boolean ret = false;
	   	try
	       { ret = table.hasEverybodyChosen();
	       }
	       catch (RemoteException e)
	       { 
	         System.exit (1);
	       }
	return ret;
}
   
   private void addUpOnesChoice() {
	   	ReturnValue ret = null;
	   	try
	       { ret = table.addUpOnesChoice(this.sID);
	       }
	       catch (RemoteException e)
	       { 
	         System.exit (1);
	       }
	this.state = ret.getStateValue();
}
   
   private void describeTheOrder() {
	   	ReturnValue ret = null;
	   	try
	       { ret = table.describeTheOrder(this.sID);
	       }
	       catch (RemoteException e)
	       { 
	         System.exit (1);
	       }
	this.state = ret.getStateValue();
}
   
   private void joinTheTalk() {
	   	ReturnValue ret = null;
	   	try
	       { ret = table.joinTheTalk(this.sID);
	       }
	       catch (RemoteException e)
	       { 
	         System.exit (1);
	       }
	this.state = ret.getStateValue();
}
   
   private void informCompanion() {
	   	ReturnValue ret = null;
	   	try
	       { ret = table.informCompanion(this.sID);
	       }
	       catch (RemoteException e)
	       { 
	         System.exit (1);
	       }
	this.state = ret.getStateValue();
}
   
   private void waitingToBeServed() {
	   	try
	       { table.waitingToBeServed(this.sID);
	       }
	       catch (RemoteException e)
	       { 
	         System.exit (1);
	       }
}
   
   private void startEating() {
	   	ReturnValue ret = null;
	   	try
	       { ret = table.startEating(this.sID);
	       }
	       catch (RemoteException e)
	       { 
	         System.exit (1);
	       }
	this.state = ret.getStateValue();
}
   
   private boolean endEating() {
	   	ReturnValue ret = null;
	   	try
	       { ret = table.endEating(this.sID);
	       }
	       catch (RemoteException e)
	       { 
	         System.exit (1);
	       }
	this.state = ret.getStateValue();
	return ret.getBooleanValue();
}
   
   private void hasEverbodyFinished() {
	   	try
	       { table.hasEverbodyFinished(this.sID);
	       }
	       catch (RemoteException e)
	       { 
	         System.exit (1);
	       }
}
   
   private void honourTheBill() {
	   	try
	       { table.honourTheBill();
	       }
	       catch (RemoteException e)
	       { 
	         System.exit (1);
	       }
}
}
