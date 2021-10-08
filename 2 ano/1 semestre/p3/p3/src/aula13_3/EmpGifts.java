package aula13_3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmpGifts {

    private Map<String, List<String>> presents;

    public EmpGifts() {

        presents = new HashMap<>();
    }

    public void give(String toy, String employee) {

        if (presents.containsKey(employee))
            presents.get(employee).add(toy);
        else {

            List<String> empPresents = new ArrayList<>();
            empPresents.add(toy);
            presents.put(employee, empPresents);
        }
    }

    public void get(String employee) {

        if (presents.containsKey(employee)) {

            List<String> empPresents = presents.get(employee);
            empPresents.remove(empPresents.size() - 1);
            if (empPresents.size() == 0)
                presents.remove(employee);
        }
    }

    @Override
    public String toString() {

        return "EmpGifts{" +
                "presents=" + presents +
                '}';
    }
}