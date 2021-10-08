import java.util.*;

public class E504 {

    static Scanner k = new Scanner(System.in);

    public static void main(String[] args) {

        int n;

        System.out.println("Programa para calcular e imprimir o fatorial de 1 até n.");
        System.out.println("--------------------------------------------------------");

        System.out.print("Qual é o valor de n que queres? ");
        n = k.nextInt();

        System.out.println();

        //dá os fatoriais
        for (int j = n; j >= 1; --j) {

            System.out.printf("%2d! = %d\n", j, factorial(j));

        }

        System.out.println();

    }

    //calcula o fatorial
    public static int factorial(int j) {

        int fatorial = 1;

        //utiliza a recursividade de funções
        for (int i = 1; i <= j; ++i) {

            fatorial *= i;
        }

        return fatorial;
    }
}