import java.util.*;

public class E110 {

    public static void main(String[] args) {

        //declaração das variáveis
        double tempC, tempF;

        //declaração do teclado
        Scanner keyboard = new Scanner(System.in);

        System.out.print("Qual a temperatura em celsius: ");
        tempC = keyboard.nextDouble();

        //calculo da temperatura em fahrenheit
        tempF = 1.8 * tempC + 32;

        //printf = print formated a.k.a. aceita %d e %f, print e println não aceitam estes caracteres
        System.out.printf("A temperatura em fahrenheit é de %4.2f º.\n", tempF);

    }
}
