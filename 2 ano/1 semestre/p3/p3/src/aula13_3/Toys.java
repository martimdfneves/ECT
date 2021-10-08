package aula13_3;

import java.util.ArrayList;
import java.util.List;

public class Toys {

    private String name;
    private List<String> toys;

    public Toys(String employee) {

        name = employee.split(" ")[0];
        toys = new ArrayList<>();
    }

    public void add(String toy) {

        toys.add(toy);
    }

    public void remove(String toy) {

        toys.remove(toy);
    }

    @Override
    public String toString() {

        return "Toys{" +
                "name='" + name + '\'' +
                ", toys=" + toys +
                '}';
    }
}