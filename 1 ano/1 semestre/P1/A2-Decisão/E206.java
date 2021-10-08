import java.util.*;

public class E206 {

    public static void main(String[] args) {

        //declaração das variáveis
        int num;

        Scanner k = new Scanner(System.in);

        System.out.print("Coloca um número para testar se é par ou ímpar: ");
        num = k.nextInt();

        //teste de paridade
        if (num % 2 == 0) {

            System.out.printf("O número %d é par.\n\n", num);

        } else if (num % 2 != 0) {

            System.out.printf("O número %d é ímpar.\n\n", num);

        }

    }
}
