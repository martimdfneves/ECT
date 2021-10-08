import java.util.*;

import static java.lang.Math.*;

public class E605 {

    public static void main(String[] args) {

        int seq_num;

        double media = 0;
        double desv_pad = 0;

        Scanner k = new Scanner(System.in);


        System.out.println("Programa para ler uma lista.");
        System.out.println("----------------------------");

        System.out.print("Quantos valores vai ter esta lista? ");
        seq_num = k.nextInt();

        double nums[] = new double[seq_num];

        System.out.print("\nColoque os valores.\n\n");

        //pede a lista
        for (int i = 0; i < seq_num; i++) {

            System.out.printf("Valor #%3d: ", i + 1);
            nums[i] = k.nextDouble();

        }

        //calculo da media
        for (int i = 0; i < seq_num; i++) {

            media += nums[i];

        }

        media /= seq_num;

        //calculo do desvio padrao
        for (int i = 0; i < seq_num; i++) {

            desv_pad += pow(nums[i] - media, 2);

        }

        desv_pad = sqrt(desv_pad / seq_num - 1);

        System.out.printf("\nA média desta lista é %4.2f e o desvio-padrão é %4.2f.\n\n\n", media, desv_pad);

    }

}