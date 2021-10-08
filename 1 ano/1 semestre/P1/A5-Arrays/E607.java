import java.util.*;

public class E607 {

    static final int N = 100;

    static Scanner k = new Scanner(System.in);

    public static void main(String[] args) {

        int numero[] = new int[N];

        int r, contagem;

        System.out.println("Programa para verificar que números aparecem e quantas vezes aparecem.");
        System.out.println("\n\nColoca os números(para terminares antes de atingires os 100 coloca um número negativo): ");

        //pede a lista
        for (r = 0; r < numero.length; r++) {

            System.out.printf("#%3d: ", r + 1);

            numero[r] = k.nextInt();

            if (numero[r] < 0) {

                break;

            }
        }

        //testa para ver qual numero aparece e quantas vezes aparece
        for (int i = 0; i < r - 1; i++) {

            contagem = 0;

            for (int j = 0; j < r; j++) {

                if (numero[i] == numero[j] && j < i) {

                    break;

                } else if (numero[i] == numero[j]) {

                    contagem++;

                }

            }

            if (contagem == 0) {

                continue;

            }

            System.out.printf("O número %5d ocorre %3d vezes.\n", numero[i], contagem);
        }
    }
}