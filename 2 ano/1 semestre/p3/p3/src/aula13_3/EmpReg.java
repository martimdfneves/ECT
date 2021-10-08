package aula13_3;

import java.util.HashMap;
import java.util.Map;

public class EmpReg {

    private Map<String, Integer> register;

    public EmpReg() {

        register = new HashMap<>();
    }

    public void add(String employee) {

        String empName = employee.split(" ")[0];
        if (register.containsKey(empName))
            register.put(empName, register.get(empName) + 1);
        else
            register.put(empName, 1);
    }

    public void remove(String employee) {

        if (register.containsKey(employee)) {

            int next = register.get(employee);
            if (next == 0) {

                register.remove(employee);
                return;
            }
            register.put(employee, next);
        }
    }

    @Override
    public String toString() {

        return "EmpReg{" +
                "register=" + register +
                '}';
    }
}