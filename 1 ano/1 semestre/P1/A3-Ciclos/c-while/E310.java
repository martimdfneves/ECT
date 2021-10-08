import java.util.*;

public class E310 {

    public static void main(String[] args) {

        int nums, prev_num, highest, lowest;

        Scanner k = new Scanner(System.in);

        System.out.println("Quando quiseres terminar o programa coloca um número e o seu dobro imediatamente a seguir.");

        System.out.println("Inicio:");

        nums = k.nextInt();

        prev_num = highest = lowest = nums;

        do {

            prev_num = nums;

            nums = k.nextInt();

            if (nums > highest) {

                highest = nums;

            } else if (nums < lowest) {

                lowest = nums;

            }

        } while (nums != (2 * prev_num));

        System.out.printf("O programa parou porque %d é igual ao dobro de %d, o maior número registado foi %d e o menor foi %d.\n", nums, prev_num, highest, lowest);

    }
}