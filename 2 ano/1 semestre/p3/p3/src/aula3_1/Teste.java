package aula3_1;

import aula1_2.*;

public class Teste {

    public static void main(String[] args) {
        Estudante est = new Estudante(9855678, new Data(18, 7, 1974), "Andreia");
        Bolseiro bls = new Bolseiro(8976543, new Data(11, 5, 1976), "Maria");
        bls.setBolsa(745);
        System.out.println("Estudante:" + est.getName());
        System.out.println(est);

        System.out.println("Bolseiro:" + bls.getName() + ", NMec: " + bls.getNmec()
                + ", Bolsa:" + bls.bolsa());
        System.out.println(bls);
    }
}
