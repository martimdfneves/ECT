import java.util.*;

public class E410 {

    public static void main(String[] args) {

        int num, div_sum;

        Scanner k = new Scanner(System.in);

        System.out.println("Programa para saber se um número é perfeito ou não.\n");

        do {

            System.out.print("Coloca um número: ");
            num = k.nextInt();

            if (num <= 0) {

                System.out.println("Número negativo, logo não aceitável.\n");

            }

        } while (num <= 0);

        System.out.printf("Divisores de %d:\n", num);

        div_sum = 0;

        //calcula a perfeição de um número oh tanso
        for (int i = 1; i <= num; i++) {

            if (num % i == 0) {

                if (i != num) {

                    div_sum += i;

                }

                System.out.printf("%10d\n", i);


            }
        }

        if (div_sum == num) {

            System.out.printf("\nO número %d é perfeito.\n", num);

        } else {

            System.out.printf("\nO número %d não é perfeito.\n", num);

        }

    }
}