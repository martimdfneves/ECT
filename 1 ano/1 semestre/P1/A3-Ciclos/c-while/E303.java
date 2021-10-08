import static java.lang.Math.*;
import static java.lang.System.*;

import java.util.*;

public class E303 {

    public static void main(String[] args) {

        double first_num, nums, avg, lower, higher, count;

        Scanner k = new Scanner(in);

        System.out.println("Programa para calcular a média de x números.");
        System.out.println("--------------------------------------------");
        System.out.println("Coloque os números que quiser para calcular a média e descubrir qual é o maior e o menor");

        first_num = k.nextDouble();
        avg = first_num;
        lower = first_num;
        higher = first_num;
        count = 1;

        //como calcular a média, o mais alto, o mais baixo e a quantidade de números
        do {

            nums = k.nextDouble();

            if (nums > higher) {

                higher = nums;

            }

            if (nums < lower) {

                lower = nums;

            }

            avg += nums;

            count++;

        } while (nums != first_num);

        avg = avg / count;

        System.out.println("O número mais alto é " + higher + " e o número mais baixo é " + lower + " e a média é " + avg + ".");
    }
}