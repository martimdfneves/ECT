import java.util.*;

public class E202 {

    public static void main(String[] args) {

        //declaração do teclado
        Scanner keyboard = new Scanner(System.in);

        //declaração das variáveis
        double num1, num2;

        System.out.print("Num1? ");
        num1 = keyboard.nextDouble();
        System.out.print("Num2? ");
        num2 = keyboard.nextDouble();

        //teste para saber se são iguais
        if (num1 == num2) {

            System.out.println("Os números inseridos são iguais.");

        } else {

            System.out.printf("O número %4.2f é o maior.\n", num1 > num2 ? num1 : num2);

        }
    }
}
