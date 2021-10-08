import java.util.*;

import static java.lang.Math.*;


public class E405 {

    public static void main(String[] args) {

        int num1, num2, sols;
        double hip, tru_hip;

        System.out.println("Programa para calcular as primeiras soluções inteiras do teorema de pitágoras com valres menores a 100.");
        System.out.println("\n\n");

        num1 = 1;
        num2 = 2;
        hip = sqrt(pow(num1, 2) + pow(num2, 2));
        sols = 0;

        do {

            num2++;

            num1 = 1;

            for (; num1 < num2; num1++) {

                hip = sqrt(pow(num1, 2) + pow(num2, 2));

                tru_hip = hip - (int) hip;

                if (tru_hip == 0) {

                    System.out.printf("%3d  %3d  %3.1f\n", num1, num2, hip);

                } else {
                    ;
                }
            }

            sols++;

        } while (hip < 100);

        System.out.printf("\n# soluções : %d", sols);

        System.out.println("\n\n");
    }
}