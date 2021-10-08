import java.util.*;

public class E604 {

    static Scanner k = new Scanner(System.in);

    static int notas[] = new int[100];
    static int valores[] = new int[21];

    public static void main(String[] args) {

        System.out.println("Programa para fazer um histograma de notas com valor entre 0 e 20;");
        System.out.println("------------------------------------------------------------------");

        System.out.println("Coloca as notas.\n");

        leitor_notas();

        organizador_notas();

        impressor();

    }

    //modulo de leitura das notas
    public static void leitor_notas() {

        for (int i = 0; i < 100; i++) {

            System.out.printf("Valor da nota #%2d: ", i + 1);
            notas[i] = k.nextInt();

            do {

                if (notas[i] > 20) {

                    System.out.printf("Essa nota é demasiado alta.\nColoca outra nota.\nValor da nota #%2d: ", i + 1);
                    notas[i] = k.nextInt();

                }

            } while (notas[i] > 20);

            if (notas[i] < 0) {

                break;

            }

        }

    }

    //modulo de organização das notas
    public static void organizador_notas() {

        for (int i = 0; i < 21; i++) {

            valores[i] = 0;

        }

        for (int i = 0; i < 100; i++) {

            if (notas[i] == 0) {

                break;

            }

            switch (notas[i]) {

                case 0:

                    valores[0] += 1;
                    break;

                case 1:

                    valores[1] += 1;
                    break;

                case 2:

                    valores[2] += 1;
                    break;

                case 3:

                    valores[3] += 1;
                    break;

                case 4:

                    valores[4] += 1;
                    break;

                case 5:

                    valores[5] += 1;
                    break;

                case 6:

                    valores[6] += 1;
                    break;

                case 7:

                    valores[7] += 1;
                    break;

                case 8:

                    valores[8] += 1;
                    break;

                case 9:

                    valores[9] += 1;
                    break;

                case 10:

                    valores[10] += 1;
                    break;

                case 11:

                    valores[11] += 1;
                    break;

                case 12:

                    valores[12] += 1;
                    break;

                case 13:

                    valores[13] += 1;
                    break;

                case 14:

                    valores[14] += 1;
                    break;

                case 15:

                    valores[15] += 1;
                    break;
                case 16:

                    valores[16] += 1;
                    break;

                case 17:

                    valores[17] += 1;
                    break;

                case 18:

                    valores[18] += 1;
                    break;

                case 19:

                    valores[19] += 1;
                    break;

                case 20:

                    valores[20] += 1;
                    break;

            }

        }

    }

    //modulo de impressão do histograma
    public static void impressor() {

        System.out.println("\n\nHistograma de Notas:");
        System.out.println("----------------------------------------------------------------");

        for (int i = 20; i >= 0; i--) {

            System.out.printf("%2d | ", i);

            for (int j = 1; j <= valores[i] && j <= 50; j++) {

                System.out.print("*");

            }

            System.out.println();
        }
    }

}