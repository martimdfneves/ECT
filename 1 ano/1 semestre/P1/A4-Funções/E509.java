import java.util.*;

public class E509 {

    static Scanner keys = new Scanner(System.in);

    public static void main(String args[]) {

        int num, primos;

        System.out.println("Programa para verificar quais os números primos entre 1 e x.");
        System.out.println("------------------------------------------------------------\n");

        //testa pra ver se o max é positivo
        do {

            System.out.print("Qual o número máximo que queres ver? ");
            num = keys.nextInt();

            if (num <= 1) {

                System.out.println("Número não aceite, coloque um número positivo.\n");

            }

        } while (num <= 1);

        //o 2é aquele madfaka
        System.out.println("\nNúmeros primos: ");

        primos = 2;

        System.out.println("\nPrimo # 1:  2");

        for (int i = 2; i <= num; i++) {

            if (primo(i)) {

                System.out.printf("Primo #%2d: %2d\n", primos, i);
                primos++;

            }

        }

        System.out.println();

    }

    //testa se é primo
    public static boolean primo(int num) {

        boolean prime = true;

        if (num % 2 == 0) {

            prime = false;

        }

        for (int i = 2; i < num; i++) {

            if (num % i == 0) {

                prime = false;

                break;

            } else {

                prime = true;

            }

        }

        return prime;

    }

}
