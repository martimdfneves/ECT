package aula7_1;

import java.util.*;
import java.io.IOException;
import java.nio.file.*;

public class Companhias {

    private HashMap<String, Companhia> companhias;

    public Companhias(String compFile) throws IOException {

        Path file = Paths.get(compFile);
        List<String> lines = Files.readAllLines(file);
        companhias = new HashMap<>();

        for (String line : lines) {

            if (line.equals("Sigla\tCompanhia")) {

            } else {

                String comp[] = line.split("\t");
                companhias.put(comp[0], new Companhia(comp[0], comp[1]));
            }
        }
    }

    public Companhia[] getCompanhias() {

        return (companhias.values()).toArray(new Companhia[companhias.size()]);
    }

    public Companhia getCompany(String sigla) {

        return companhias.get(sigla);
    }
}