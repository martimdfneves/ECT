package aula7_1;

import java.io.*;

public class Test {

    public static void main(String[] args) throws IOException {

        AeroPorto novo = new AeroPorto("voos.txt", "companhias.txt");
        novo.printScreen();
        novo.printDelayAverage();
        novo.saveCity();
        novo.saveAll();
        novo.saveAllBin();
        novo.readBin();
    }
}
