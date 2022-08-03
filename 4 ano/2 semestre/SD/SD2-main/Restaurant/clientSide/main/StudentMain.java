package clientSide.main;

import clientSide.entities.Student;
import clientSide.entities.StudentState;
import clientSide.stubs.BarStub;
import clientSide.stubs.TableStub;
import serverSide.main.SimPar;

/**
 * Client side of the Restaurant (student)
 * 
 * @author Martim Neves
 * @author Tiago Dias
 */
public class StudentMain {
	/**
     * Main Student launcher
     * @param args runtime arguments
     */
    public static void main(String[] args){
        Student[] students = new Student[SimPar.STUDENTS];

        TableStub table;
        BarStub bar;

        table = new TableStub(SimPar.TABLE_HOST_NAME, SimPar.TABLE_PORT);
        bar = new BarStub(SimPar.BAR_HOST_NAME, SimPar.BAR_PORT);
        
        for(int i = 0; i< SimPar.STUDENTS; i++){
            students[i] = new Student(i, StudentState.GOING_TO_THE_RESTAURANT, bar, table);
        }

        for(int i = 0; i< SimPar.STUDENTS; i++){
            students[i].start(); 
        }

        for(int i = 0; i< SimPar.STUDENTS; i++){
            try {
                students[i].join(); 
            } 
            catch (Exception e) {}

            System.out.println("\033[41m The Student[" + i + "] thread has been terminated \033[0m");
        }
    }
}
