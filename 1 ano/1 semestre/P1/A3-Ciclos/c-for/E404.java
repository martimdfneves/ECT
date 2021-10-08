import java.util.*;

import static java.lang.Math.*;

public class E404 {

    public static void main(String[] args) {

        double num, term, pie, comp;

        Scanner k = new Scanner(System.in);

        pie = PI / 4;

        System.out.printf("Programa para calcular o valor de um x número de termos e comparálo com %.15f (\u03c0/4).\n", pie);

        System.out.print("Qual o número de termos que quer: ");
        num = k.nextDouble();
        System.out.println("\n\n");

        k.close();

        term = 1;

        for (int i = 1; i <= num; ++i) {

            //fórmula de leibnitz
            term = term + (pow(-1, i) / (2 * i + 1));

        }

        comp = pie - term;

        System.out.printf("O valor de %.1f termos na série de leibnitz é igual a %1.15f e a diferença entre \u03c0/4 e valor do termo é igual a %1.15f.\n\n", num, term, comp);

    }
}