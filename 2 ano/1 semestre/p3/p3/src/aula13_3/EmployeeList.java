package aula13_3;

import java.util.HashSet;
import java.util.Set;

public class EmployeeList {

    private Set<String> employees;

    public EmployeeList() {

        employees = new HashSet<>();
    }

    public void add(String employee) {

        employees.add(employee);
    }

    public void remove(String employee) {

        employees.remove(employee);
    }

    @Override
    public String toString() {

        return "EmployeeList{" +
                "employees=" + employees +
                '}';
    }
}