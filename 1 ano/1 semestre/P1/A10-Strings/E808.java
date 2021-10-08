import java.util.*;

public class E808 {

    public static void main(String[] args) {

        Scanner k = new Scanner(System.in);

        int base, num;
        String numi;
        boolean possivel = true;

        do {

            System.out.print("Base: ");
            base = k.nextInt();

            if (base < 2 || base > 10) {

                System.out.print("Base não aceitável, por favor coloque outra.\n");
            }
        } while (base < 2 || base > 10);

        System.out.print("Número nessa base: ");
        numi = k.next();

        //verifica se o número está numa base aceitável
        for (int i = 0; i < numi.length(); i++) {

            if (Character.getNumericValue(numi.charAt(i)) >= base) {

                possivel = false;
            }
        }

        if (!possivel) {

            System.out.print("Número numa base não aceitável, o programa vai terminar.\n");

        } else if (possivel) {

            num = baseToNum(numi, base);

            System.out.printf("O número %s na base %d é %d.\n", numi, base, num);
        }
    }

    //modulo da mudança de base
    public static int baseToNum(String num, int base) {

        int c = 0;
        int i = 0;

        for (int j = num.length() - 1; j >= 0; j--) {

            c += (Character.getNumericValue(num.charAt(i)) * Math.pow(base, j));
        }

        return c;
    }
}
