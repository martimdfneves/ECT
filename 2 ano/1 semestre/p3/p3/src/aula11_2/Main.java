package aula11_2;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import aula3_2.Circulo;
import aula3_2.Figura;
import aula3_2.Quadrado;
import aula3_2.Retangulo;

public class Main {

    public static void main(String[] args) {

        List<Figura> figList = new ArrayList<>();
        figList.add(new Circulo(1, 3, 1));        // a = pi
        figList.add(new Quadrado(3, 4, 2));        // a = 4
        figList.add(new Retangulo(1, 2, 2, 5));    // a = 10 / maior figura
        figList.add(new Quadrado(1));            // a = 1
        figList.add(new Quadrado(2));            // a = 4
        figList.add(new Retangulo(1, 1));        // a = 1
        System.out.println(maiorFiguraJ8(figList).toString());
        System.out.println(maiorFiguraJ8P(figList).toString());
        System.out.println(areaTotalJ8(figList));                // -> 23.qualquer coisa
        System.out.println(areaTotalJ8(figList, "Quadrado"));    // -> 9
    }

    public static Figura maiorFiguraJ8(List<Figura> figs) {
        return figs.stream().max(Comparator.naturalOrder()).get();
    }

    public static Figura maiorFiguraJ8P(List<Figura> figs) {
        return figs.stream().max(new Comparator<Figura>() {
            @Override
            public int compare(Figura o1, Figura o2) {
                if (o1.perimetro() > o2.perimetro())
                    return 1;
                else if (o1.perimetro() < o2.perimetro())
                    return -1;
                else
                    return 0;
            }
        }).get();
    }

    public static double areaTotalJ8(List<Figura> figs) {
        return figs.stream().mapToDouble(f -> f.area()).sum();
    }

    public static double areaTotalJ8(List<Figura> figs, String subtipoNome) {
        return figs.stream().filter(f -> f.getClass().getSimpleName().equals(subtipoNome)).mapToDouble(f -> f.area()).sum();
    }
}