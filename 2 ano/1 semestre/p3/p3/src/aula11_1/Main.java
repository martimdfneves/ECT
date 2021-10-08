package aula11_1;

import java.util.stream.*;
import java.util.Map.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;

import aula1_2.*;
import aula3_2.*;

public class Main {

    public static void main(String[] args) throws IOException {

        List<Pessoa> vp = new ArrayList<Pessoa>();
        for (int i = 0; i < 10; i++)
            vp.add(new Pessoa("Beb� no Vector " + i, 1000 + i, Data.today()));

        Iterator<Pessoa> it = vp.iterator();
        printSet(vp.iterator());

        List<Pessoa> lp = new LinkedList<Pessoa>();
        while (it.hasNext())
            lp.add(it.next());

        Iterator<Pessoa> lista = lp.iterator();
        while (lista.hasNext())
            System.out.println(lista.next());

        List<Figura> Listfig = new LinkedList<Figura>();
        Listfig.add(new Circulo(1, 3, 1));
        Listfig.add(new Quadrado(3, 4, 2));
        Listfig.add(new Retangulo(1, 2, 2, 5));

        printSet(Listfig.iterator());

        System.out.println("Soma da Area de Lista de Figuras: " + sumArea(Listfig));

        List<Retangulo> retList = new LinkedList<Retangulo>();
        retList.add(new Quadrado(3, 4, 2));
        retList.add(new Retangulo(1, 2, 2, 5));
        System.out.println("Soma da Area de Lista de Quadrados: " + sumArea(retList));

        HashSet<String> set = new HashSet<>();
        Path file = Paths.get("SampleText.txt");
        System.out.println(file.toAbsolutePath().toString());
        List<String> linhas = Files.readAllLines(file);
        String line[];
        int total_palavras = 0;
        for (String linha : linhas) {

            line = linha.split(" ");
            for (String word : line) {

                total_palavras++;
                if (!set.contains(word))
                    set.add(word);
            }
        }

        System.out.println("N�mero Total de Palavras: " + total_palavras);
        System.out.println("N�mero de Diferentes Palavras: " + set.size());

        HashMap<String, Integer> set2 = new HashMap<>();
        linhas = Files.readAllLines(file);
        for (String linha : linhas) {

            line = linha.split(" ");
            for (String word : line)
                if (!set2.containsKey(word))
                    set2.put(word, 1);
                else
                    set2.put(word, set2.get(word) + 1);
        }

        for (String key : set2.keySet())
            System.out.println(key + "\t" + set2.get(key));

        TreeMap<String, Integer> tree = new TreeMap<>(Comparator.naturalOrder());
        linhas = Files.readAllLines(file);
        for (String linha : linhas) {

            line = linha.split(" ");
            for (String word : line)
                if (!tree.containsKey(word))
                    tree.put(word, 1);
                else
                    tree.put(word, tree.get(word) + 1);
        }

        for (String key : tree.keySet())
            System.out.println(key + "\t" + tree.get(key));

        System.out.println(set2.keySet().stream().map(key -> key + "\t" + set2.get(key)).collect(Collectors.joining("\n")));

        System.out.println(tree.keySet().stream().map(key -> key + "\t" + tree.get(key)).collect(Collectors.joining("\n")));
    }

    public static double sumArea(List<? extends Figura> list) {

        double sum = 0;
        for (Figura fig : list) {

            sum += fig.area();
            System.out.println(fig);
        }

        return sum;
    }

    public static <T> void printSet(Iterator<T> iter) {

        while (iter.hasNext())
            System.out.println(iter.next());
    }
}