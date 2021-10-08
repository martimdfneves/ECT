import java.util.*;

import static java.lang.Math.*;

public class E312 {

    public static void main(String[] args) {

        int num, secret, tentativas;

        char resposta;

        boolean ng = false;

        Scanner k = new Scanner(System.in);

        System.out.println("Jogo do adivinha o número (agora entre 0 e 100)");
        System.out.println("-----------------------------------------------");

        do {

            secret = (int) (random() * 100.0 + 1);
            tentativas = 0;

            do {

                System.out.print("\nColoque um número: ");
                num = k.nextInt();
                System.out.println();

                if (num > secret) {

                    System.out.printf("O número %d é maior do que o número escolhido.\nTenta outra vez.\n", num);

                } else if (num < secret) {

                    System.out.printf("O número %d é menor do que o número escolhido.\nTenta outra vez.\n", num);
                }

                ++tentativas;

            } while (num != secret);

            System.out.printf("Parabéns, ao fim de %d tentativas conseguiste acertar no número.\n", tentativas);

            System.out.print("Novo jogo (s/n)? ");
            resposta = k.next().charAt(0);


            if (resposta == 's') {

                ng = true;

            } else if (resposta == 'n') {

                ng = false;

            } else {

                System.out.print("Isso não é resposta.\nNovo jogo (s/n)? ");
                resposta = k.next().charAt(0);

            }


        } while (ng);
    }
}