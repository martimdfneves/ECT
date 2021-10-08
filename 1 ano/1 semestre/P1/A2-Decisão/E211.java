import static java.lang.Math.*;

import java.util.Scanner;

public class E211 {

    public static void main(String[] args) {

        //declaração do teclado
        Scanner k = new Scanner(System.in);

        //declaração das variáveis
        double coefA, coefB, coefC, rt1, rt2, discBin, im1;

        System.out.println("Programa para calcular as ráizes de um polinómio de grau2.");
        System.out.println("----------------------------------------------------------");

        System.out.println("Coloque os coeficientes dna ordem que lhe são apresentados.");

        System.out.print("coeficiente A (x²): ");
        coefA = k.nextDouble();

        System.out.print("coeficiente B (x⁰): ");
        coefB = k.nextDouble();

        System.out.print("coeficiente C (x⁻¹): ");
        coefC = k.nextDouble();

        //caso a função seja de 1 grau
        if (coefA == 0) {

            System.out.println("O polinómio introduzido não é de 2º grau, logo o programa parou.");

        } else {

            System.out.printf("O polinómio é igual a (%4.2f)x² + (%4.2f)x + (%4.2f)\n", coefA, coefB, coefC);

            discBin = pow(coefB, 2) - 4 * coefA * coefC;

            //caso tenha uma raiz real de multiplicidade 2
            if (discBin == 0) {

                rt1 = -coefB / (2 * coefA);

                System.out.printf("O polinómio tem apenas uma raíz real igual a (%4.2f).\n", rt1);

                //caso tenha 2 raizes reais
            } else if (discBin > 0) {

                rt1 = (-coefB + sqrt(discBin)) / (2 * coefA);
                rt2 = (-coefB - sqrt(discBin)) / (2 * coefA);

                System.out.printf("O polinómio tem 2 raízes reais iguais a (%4.2f) e (%4.2f).\n", rt1, rt2);

                //caso tenha raizes imaginarias
            } else if (discBin < 0) {

                rt1 = -coefB / (2 * coefA);
                im1 = sqrt(-discBin) / (2 * coefA);

                System.out.printf("O polinómio tem 2 raízes imaginárias iguais a (%4.2f + %4.2fi) e (%4.2f - %4.2fi).\n", rt1, im1, rt1, im1);
            }

        }
    }
}
