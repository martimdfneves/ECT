package aula5_2;

import static java.lang.System.*;

public class Test {

    public static void main(String args[]) {

        Motorizado a1 = new Automovel("audi", "azul", 1999, 250, 250, 220, 2.0, 40, "11-11-AA", 5);
        CarroPolicia ap = new CarroPolicia("audi", "azul", 1999, 250, 250, 220, 2.0, 80, "11-11-AA", 5, Tipo.INEM, "1");

        out.println(a1.getPotencia());
        out.println(a1.getConsumo());
        out.println(a1.getCombustivel());
        out.println(ap.getTipo());
        out.println(ap.getID());
    }
}