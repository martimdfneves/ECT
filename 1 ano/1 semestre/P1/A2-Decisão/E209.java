import static java.lang.System.*;

import java.util.*;

public class E209 {

    //declaração do teclado
    static Scanner k = new Scanner(in);

    public static void main(String[] args) {

        //declaração das variáveis
        double temp_type, tempC, tempF;

        //escolha da escala
        System.out.println("Escolha o tipo de conversão:");
        System.out.println("0 --> Celsius-Fahrenheit");
        System.out.println("1 --> Fahrenheit-Celius");
        System.out.print("\nTipo de conversão: ");
        temp_type = k.nextDouble();


        if (temp_type == 0) {

            System.out.print("Qual a temperatura em graus Celsius? ");
            tempC = k.nextDouble();

            tempF = 1.8 * tempC + 32;

            System.out.printf("A tempertura em Fahrenheit é de " + tempF + "º.\n");

        } else if (temp_type == 1) {

            System.out.print("Qual a temperatura em graus Fahrenheit? ");
            tempF = k.nextDouble();

            tempC = (5 / 9) * (tempF - 32);

            System.out.print("A temperatura em Celsius é de " + tempC + "º.\n");

        } else {

            System.out.println("O tipo de conversão inserido não é comportado no Programa.");

        }

    }

}
