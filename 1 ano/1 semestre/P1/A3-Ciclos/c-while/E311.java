import java.util.*;

public class E311 {

    public static void main(String[] args) {

        int nums;

        boolean impar = true;

        Scanner k = new Scanner(System.in);

        System.out.println("Ínicio de lista: ");

        nums = k.nextInt();

        while (nums != 0) {

            if (nums % 2 == 0) {

                impar = false;

            }

            nums = k.nextInt();

        }

        if (!impar) {

            System.out.println("A lista não é exclusivamente composta por números ímpares.");

        } else if (impar) {

            System.out.println("A lista é exclusivamente composta por números ímpares.");
        }
    }
}