import java.util.*;

public class exe1_6 {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        int rand, num, pont = 0;

        rand = (int) (Math.random() * (100 + 1));

        do {

            System.out.print("Digite a sua tentativa: ");
            num = sc.nextInt();

            if (num > rand) {
                System.out.println("A sua tentativa está demasiado alta! Tente de novo");
            } else if (num < rand) {
                System.out.println("A sua tentativa está demasiado baixa! Tente de novo");
            } else {
                System.out.println("Parabéns, acertou no número!");
            }

            pont++;

        } while (rand != num);

        System.out.printf("Muito bem, acertou em apenas %d tentativas", pont);

    }
}

