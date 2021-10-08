import java.util.*;

public class E309 {

    public static void main(String[] args) {

        double nums;

        //se não percebes que são os intervalos desiste do curso
        int bet_100_1000, bet_m1000_m100, pos, neg, listed;

        Scanner k = new Scanner(System.in);

        System.out.println("Programa para saber as quantidade de números em certos intervalos");
        System.out.println("-----------------------------------------------------------------");

        System.out.println("Coloque a lista de números reais, sempre que acaba de colocar um clique enter. (termine com o valor 0)\n");

        //declarar que inicialmente não há merda nenhuma
        bet_m1000_m100 = listed = bet_100_1000 = pos = neg = 0;

        System.out.println("Ínicio da lista: ");
        nums = k.nextDouble();

        //aumentar a merda consoante as cenas
        do {

            listed++;

            if (nums <= 1000 && nums >= 100) {

                bet_100_1000++;

            } else if (nums >= -1000 && nums <= -100) {

                bet_m1000_m100++;

            } else if (nums > 0) {

                pos++;

            } else if (nums < 0) {

                neg++;

            }

            nums = k.nextDouble();

        } while (nums != 0);

        System.out.printf("A lista teve %d números, dos quais %d eram positivos, %d eram negativos, %d pertenciam ao intevalo [100;1000] e %d pertencem ao intervalo[-1000;-100].\n", listed, pos, neg, bet_100_1000, bet_m1000_m100);
    }
}