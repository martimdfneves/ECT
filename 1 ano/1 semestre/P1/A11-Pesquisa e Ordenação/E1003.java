import java.util.*;
import java.io.*;

public class E1003 {

    static Scanner k = new Scanner(System.in);

    public static void main(String[] args) throws IOException {

        int opt;
        double media;
        double[] phs = null;

        do {
            System.out.println("\n\nAnalisador de pH");
            System.out.println("\t1 -> Ler valores de pH de um ficheiro");
            System.out.println("\t2 -> Escrever valores de pH no terminal");
            System.out.println("\t3 -> Calcular ph médio das amostras");
            System.out.println("\t4 -> Calcular o número de amostras ácidas e básicas");
            System.out.println("\t5 -> Calcular o número de amostras de pH superior à média");
            System.out.println("\t6 -> Escrever valores de pH no terminal ordenados de modo crescente");
            System.out.println("\t7 -> Terminar o programa");
            System.out.print("\nOpção -> ");
            opt = k.nextInt();

            switch (opt) {

                case 1:
                    phs = readFile(phs);
                    break;

                case 2:
                    termPrint(phs);
                    break;

                case 3:
                    media = avg(phs);
                    System.out.printf("\npH médio das amostras: %4.2f\n", media);
                    break;

                case 4:
                    acdBas(phs);
                    break;

                case 5:
                    upMed(avg(phs), phs);
                    break;

                case 6:
                    ordTermPrint(phs);
                    break;

                case 7:
                    break;

                default:
                    System.out.println("Opção inválida, por favor coloque outra.");
            }
        } while (opt != 7);
    }

    //modulo de leitura de ficheiros
    public static double[] readFile(double[] phs) throws IOException {

        String nome;
        int nums = 0;

        System.out.print("Nome do ficheiro com os dados: ");
        nome = k.next();

        File phf = new File(nome);
        Scanner kfile = new Scanner(phf);

        while (kfile.hasNext()) {

            double tmp = kfile.nextDouble();

            if (tmp >= 0 && tmp <= 14) {

                nums++;
            }
        }
        phs = new double[nums];
        kfile.close();

        int numer = 0;

        Scanner kfila = new Scanner(phf);
        while (kfila.hasNext()) {

            double tmp = kfila.nextDouble();

            if (tmp >= 0 && tmp <= 14) {

                phs[numer] = tmp;
                numer++;
            }
        }
        kfila.close();

        return phs;
    }

    //modulo para escrita no terminal
    public static void termPrint(double[] phs) {

        System.out.print("\nValores do pH: ");

        for (int i = 0; i < phs.length; i++) {

            System.out.printf("Val #%2d: %4.2f\n", i + 1, phs[i]);
        }
    }

    //modulo para o calculo do ph medio
    public static double avg(double[] phs) {

        double media = 0;

        for (int i = 0; i < phs.length; i++) {

            media += phs[i];
        }

        media /= phs.length;
        return media;
    }

    //modulo para o calculo do nmr de amostra basicas e acidas
    public static void acdBas(double[] phs) {

        int basico = 0;
        int acido = 0;
        int neutro = 0;

        for (int i = 0; i < phs.length; i++) {

            if (phs[i] >= 0 && phs[i] < 7) {

                acido++;

            } else if (phs[i] > 7 && phs[i] <= 14) {

                basico++;

            } else {

                neutro++;

            }
        }

        System.out.printf("\nOs dados obtidos contêm %d %s, %d %s, %d %s.\n", acido, acido == 1 ? "amostra ácida" : "amostras ácidas", basico, basico == 1 ? "amostra básica" : "amostras básicas", neutro, neutro == 1 ? "amostra neutra" : "amostras neutras");
    }

    //modulo de calculo do numero de amostras com pH superior à media
    public static void upMed(double med, double[] phs) {

        int superiores = 0;

        for (int i = 0; i < phs.length; i++) {

            if (phs[i] > med) {

                superiores++;
            }
        }

        System.out.printf("\n%s %d %s à média.\n", superiores == 1 ? "Existe" : "Existem", superiores, superiores == 1 ? "amostra superior" : "amostras superiores");

    }

    //modulo de escrita dos valores no terminal de maneira crescente
    public static void ordTermPrint(double[] phs) {

        boolean swap = true;

        do {
            swap = false;

            for (int i = 0; i < phs.length - 1; i++) {

                if (phs[i + 1] < phs[i]) {

                    double tmp = phs[i];
                    phs[i] = phs[i + 1];
                    phs[i + 1] = tmp;
                    swap = true;
                }
            }
        } while (swap);

        for (int i = 0; i < phs.length; i++) {

            System.out.printf("Val #%2d: %4.2f\n", i + 1, phs[i]);
        }
    }
}