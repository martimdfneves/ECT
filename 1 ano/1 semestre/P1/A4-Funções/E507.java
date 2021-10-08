import java.util.*;

public class E507 {

    static Scanner k = new Scanner(System.in);

    public static void main(String[] args) {

        int num1, num2, mdc;

        System.out.println("Programa para calcular o máximo divisor comum divisor comum");
        System.out.println("-----------------------------------------------------------\n");

        System.out.print("Qual o primeiro número: ");
        num1 = k.nextInt();

        System.out.print("Qual é o segundo número: ");
        num2 = k.nextInt();

        System.out.printf("O máximo divisor comum de %d e %d é %d.\n\n", num1, num2, mdc(num1, num2));
    }

    //modulo que calcula o max div com
    public static int mdc(int num1, int num2) {

        int mdc = 1;

        for (int i = 1; i <= num1 && i <= num2; i++) {

            if (num2 % i == 0 && num1 % i == 0) {

                mdc = i;

            }
        }

        return mdc;
    }
}