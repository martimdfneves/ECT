import java.util.*;

public class E603 {

    static Scanner k = new Scanner(System.in);

    //array global
    static int numeros[] = new int[50];

    public static void main(String[] args) {

        //variável da escolha
        int escolha;

        System.out.println("Programa para escolher o que fazer a uma sequência.");
        System.out.println("---------------------------------------------------");

        System.out.println("\nAnálise de uma sequência de números inteiros:");
        System.out.println("\t 1 -> Ler a sequência, se colocar 0 termina o programa");
        System.out.println("\t 2 -> Escrever a sequência");
        System.out.println("\t 3 -> Calcular o máximo desta sucessão.");
        System.out.println("\t 4 -> Calcular o mínimo desta sucessão.");
        System.out.println("\t 5 -> Calcular a média desta sucessão.");
        System.out.println("\t 6 -> Detetar se é uma sequência só constituída por números pares.");
        System.out.println("\t10 -> Terminar o programa.");

        //o que acontece em cada escolha
        do {

            System.out.print("\nOpção: ");
            escolha = k.nextInt();


            switch (escolha) {

                case 10:

                    break;

                case 1:

                    leitor();
                    break;

                case 2:

                    escritor();
                    break;

                case 3:

                    maximizante();
                    break;

                case 4:

                    minimizante();
                    break;

                case 5:

                    media();
                    break;

                case 6:

                    pares();
                    break;

                default:

                    System.out.println("Escolha não aceite coloca outra.");
                    System.out.print("\nOpção: ");
                    escolha = k.nextInt();

            }

        } while (escolha != 10);

    }

    //modulo de leitura da lista
    public static void leitor() {

        for (int i = 0; i < 50; i++) {

            System.out.printf("\nvalor #%2d: ", i + 1);
            numeros[i] = k.nextInt();

            if (numeros[i] == 0) {

                break;

            }
        }
    }

    //modulo de impressao da lista
    public static void escritor() {

        for (int i = 0; i < 50; i++) {

            if (numeros[i] == 0) {

                break;

            }

            System.out.printf("\nValor #%2d: %3d", i + 1, numeros[i]);

        }

        System.out.println();
    }

    //modulo de calculo do maximizante
    public static void maximizante() {

        int max = 0;

        for (int i = 0; i < 50; i++) {

            if (numeros[i] > max) {

                max = numeros[i];

            }
        }

        System.out.printf("\nO máximo desta sequência é %3d.\n", max);
    }

    //modulo de calculo do minimizante
    public static void minimizante() {

        int min = 12431234;

        for (int i = 0; i < 50; i++) {

            if (numeros[i] == 0) {

                break;

            }

            if (numeros[i] < min) {

                min = numeros[i];

            }
        }

        System.out.printf("\nO minimo desta sequência é %3d.\n", min);
    }

    //modulo de calculo da media
    public static void media() {

        double media = 0;

        int i;

        for (i = 0; i < 50; i++) {

            if (numeros[i] == 0) {

                break;

            }

            media += numeros[i];

        }

        media /= i;

        System.out.printf("\nA média da sequência é %4.2f\n", media);
    }

    //modulo pra calcular se a lista é apenas composta por números pares
    public static void pares() {

        boolean pares = false;

        for (int j = 0; j < 50; j++) {

            for (int i = 1; i < numeros[j]; i++) {

                if (numeros[j] == 0) {

                    break;

                }

                if (numeros[j] % 2 != 0) {

                    pares = false;

                } else {

                    pares = true;

                }
            }
        }

        if (pares) {

            System.out.println("\n\nA lista é constituída apenas por números pares.");

        } else {

            System.out.println("\n\nA lista não é constituída apenas por números pares.");

        }
    }
}