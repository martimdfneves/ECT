import java.util.*;

public class E602 {

    static Scanner k = new Scanner(System.in);

    public static void main(String[] args) {

        int num = 101;
        int i, count;

        System.out.println("Programa para ler uma lista composta por 100 números.");
        System.out.println("-----------------------------------------------------");

        int numeros[] = new int[num];

        count = 0;

        //pede a lista
        for (i = 1; i < num; i++) {

            System.out.printf("\nvalor #%d: ", i);
            numeros[i] = k.nextInt();

            //calcula quantos são iguais
            for (int j = 0; j < i; j++) {

                if (numeros[i] == numeros[j]) {

                    count = count + 1;
                    break;

                }
            }

            //dá o sinal de paragem
            if (numeros[i] < 0) {

                break;

            }

        }

        System.out.printf("\nA lista contém %2d valores iguais.\n", count);
    }
}