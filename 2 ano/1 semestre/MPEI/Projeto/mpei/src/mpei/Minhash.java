package mpei;

import java.util.*;
import java.io.*;

public class Minhash {

    private int numhash;

    public Minhash(int numhash) {
        this.numhash = numhash;

    }

    private Set getMinHashValue(Set x) {
        Set<Integer> valormin = new TreeSet<Integer>(); // array para o valor min. de hash de cada palavra
        Iterator<String> iterator = x.iterator();


        while (iterator.hasNext()) {
            String palavra = iterator.next();
            int min = Integer.MAX_VALUE;
            for (int i = 0; i < numhash; i++) {

                palavra = palavra + i;
                int tmp = Math.abs(palavra.hashCode()); // seed da palavra

                if (tmp < min) {  // guarda a seed com o menor valor
                    min = tmp;
                }
                valormin.add(min);// guarda no array


            }
        }
        return valormin;
    }


    private double JaccardDist(Set x, Set y) {// calculo da distancia

        Set<Integer> x_valor = getMinHashValue(x);
        Set<Integer> y_valor = getMinHashValue(y);

        Set<Integer> intersept = new HashSet<>(); // interse��o
        intersept.addAll(x_valor);
        intersept.retainAll(y_valor);

        Set<Integer> union = new HashSet<>();
        union.addAll(x_valor);
        union.addAll(y_valor);


        return 1 - (double) intersept.size() / (double) union.size();


    }

    public double compare(String name1, String name2) throws IOException { // leitura dos ficheiros e guarda em sets

        Scanner sc = new Scanner(new File(name1));

        Set x = new HashSet<String>(); // dividir em palavra a palavra as frases e meter no set

        while (sc.hasNextLine()) {
            x.add(sc.nextLine());
        }

        String conteudo1 = x.toString();
        System.out.print("\n");
        System.out.print(conteudo1);


        sc = new Scanner(new File(name2));

        Set y = new HashSet<String>(); // dividir em palavra a palvra as frases e meter no set

        while (sc.hasNextLine()) {
            y.add(sc.nextLine());
        }
        String conteudo2 = y.toString();
        System.out.print("\n");
        System.out.print(conteudo2);
        System.out.print("\n");
        sc.close();
        return JaccardDist(x, y);

    }


}