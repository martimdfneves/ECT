import java.util.*;

public class exe1_5 {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int a[] = new int[10];
        int i = 0, soma = 0;
        double media = 0;

        for (i = 0; i < a.length; i++) {
            System.out.printf("Digite o valor na posição %d: ", i);
            a[i] = sc.nextInt();
            if (a[i] == 0) {
                break;
            }

        }


        if (a[0] == 0) {
            System.out.println("O array está vazio portanto a soma é 0 e não é possível calcular a média");
        } else {

            for (int j = 0; j < i; j++) {
                soma += a[j];
            }

            media = soma / i;
            System.out.printf("A soma é %d \n", soma);
            System.out.printf("A média é %2.2f", media);
        }


    }
}

