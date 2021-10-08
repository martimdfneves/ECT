package aula7_1;

import java.util.*;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.io.*;

import static java.lang.System.*;

public class AeroPorto {

    private ArrayList<Voo> voos;
    private Companhias companhias;

    public AeroPorto(String flys, String comps) throws IOException {

        companhias = new Companhias(comps);

        Path file = Paths.get(flys);
        List<String> lines = Files.readAllLines(file);
        voos = new ArrayList<>();

        for (String line : lines) {

            if (line.equals("Hora\tVoo\tOrigem\tAtraso")) {

            } else {

                String comp[] = line.split("\t");
                String company = comp[1].substring(0, 2);
                try {
                    voos.add(new Voo(comp[0], comp[1], companhias.getCompany(company), comp[2], comp[3]));
                } catch (NumberFormatException e) {
                    out.println("Hora mal formatada.");
                } catch (ArrayIndexOutOfBoundsException e) {
                    voos.add(new Voo(comp[0], comp[1], companhias.getCompany(company), comp[2]));
                } catch (NullPointerException e) {
                    out.println(company);
                    out.println("Companhia n�o registada.");
                }
            }
        }
    }

    public void printScreen() {

        out.println("Hora\tVoo\tCompanhia\tOrigem\tAtraso\tObserva��es2");
        for (Voo voo : voos) {

            try {

                out.print(voo.toString());
            } catch (NullPointerException e) {
                ;
            }
        }
    }

    public void saveAll() throws IOException {

        List<String> lines = new ArrayList<>();
        Path file = Paths.get("Infopublico.txt");
        lines.add("Hora\tVoo\tCompanhia\tOrigem\tAtraso\tObs\n");

        for (Voo voo : voos) {

            try {
                lines.add(voo.toString());
            } catch (NullPointerException e) {
                ;
            }
        }

        Files.write(file, lines, Charset.forName("UTF-8"));
    }

    public ArrayList<Voo> getVoos() {

        return voos;
    }

    public void printDelayAverage() {

        Hashtable<Companhia, Integer> delays = new Hashtable<>();
        out.println("\n\n\nCompanhia\t\tAtraso m�dio");

        Companhia comps[] = companhias.getCompanhias();
        int sum, count;

        for (Companhia companhia : comps) {

            sum = 0;
            count = 0;
            for (Voo voo : voos) {

                try {

                    if (voo.getCompanhia().equals(companhia)) {

                        sum += voo.getAtraso().getTmins();
                        count++;
                    }
                } catch (NullPointerException e) {
                    ;
                }
            }
            delays.put(companhia, sum / count);
        }

        for (Companhia companhia : comps) {

            out.println(companhia.toString() + "\t" + Hora.minsToHoursStr(delays.get(companhia)));
        }
    }

    public void saveCity() throws IOException {

        Hashtable<String, Integer> origens = new Hashtable<>();
        List<String> lines = new ArrayList<>();
        int i;
        lines.add("Origem\tVoos\n");

        for (Voo voo : voos) {

            if (origens.containsKey(voo.getOrigem())) {

                origens.replace(voo.getOrigem(), origens.get(voo.getOrigem()) + 1);
            } else {

                origens.put(voo.getOrigem(), 1);
            }
        }

        String cities[] = (origens.keySet()).toArray(new String[origens.size()]);
        Iterator<Integer> iter = (origens.values()).iterator();
        int flys[] = new int[origens.size()];


        for (i = 0; i < flys.length; i++) {

            flys[i] = iter.next();
        }

        boolean swap;
        String aux;
        do {

            swap = false;
            for (i = 0; i < flys.length - 1; i++) {

                if (flys[i] < flys[i + 1]) {

                    aux = cities[i];
                    cities[i] = cities[i + 1];
                    cities[i + 1] = aux;

                    flys[i] = flys[i] + flys[i + 1];
                    flys[i + 1] = flys[i] - flys[i + 1];
                    flys[i] = flys[i] - flys[i + 1];

                    swap = true;
                }
            }
        } while (swap);

        for (i = 0; i < flys.length; i++) {

            lines.add(cities[i] + "\t" + flys[i] + "\n");
        }
        Path file = Paths.get("cidades.txt");
        Files.write(file, lines, Charset.forName("UTF-8"));
    }

    public void saveAllBin() throws IOException {

        RandomAccessFile file = new RandomAccessFile("Infopublico.bin", "rw");
        String flys = "";
        for (Voo voo : voos) {

            try {

                flys += voo.toString();
            } catch (NullPointerException e) {
                ;
            }
        }
        file.write(flys.getBytes("UTF-8"));
        file.close();
    }

    public void readBin() throws IOException {

        RandomAccessFile file = new RandomAccessFile("Infopublico.bin", "rw");
        byte data[] = new byte[(int) file.length()];
        file.readFully(data);
        file.close();
        out.println(new String(data));
    }


    @Override
    public String toString() {

        String flys = "";
        for (Voo voo : voos) {

            flys += voo.toString();
        }
        return flys;
    }
}