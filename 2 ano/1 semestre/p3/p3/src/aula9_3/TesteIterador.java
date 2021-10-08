package aula9_3;

import java.util.*;

import aula1_2.*;

public class TesteIterador {

    public static void main(String[] args) {

        VetorPessoas vp = new VetorPessoas();
        for (int i = 0; i < 10; i++)
            vp.add(new Pessoa(i, 1000 + i, Data.today() + "Bebe no Vector "));

        Iterator vec = vp.iterator();
        while (vec.hasNext())
            System.out.println(vec.next());

        ListaPessoas lp = new ListaPessoas();
        for (int i = 0; i < 10; i++)
            lp.add(new Pessoa(i, 2000 + i, Data.today() + "Bebe na Lista "));

        Iterator lista = lp.iterator();
        while (lista.hasNext())
            System.out.println(lista.next());
    }
}
