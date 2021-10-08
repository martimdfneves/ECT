import java.util.*;

public class E402 {

    public static void main(String[] args) {

        int num, tab;

        Scanner k = new Scanner(System.in);

        System.out.print("Queres fazer a tabuada de que número entre 0 e 100: ");
        num = k.nextInt();

        //testa pra ver se tá entre 100 e 0
        if (num < 0 || num > 100) {

            System.out.println("Número não aceite. Coloca outro número que esteja entre 0 e 100.");
            System.out.print("Queres fazer a tabuada de que número entre 0 e 100: ");
            num = k.nextInt();

        }

        k.close();

        //a.k.a. como gastar recursos da máquina
        System.out.println("\n\n");

        //adivinha
        System.out.println("|------------------------------------|");
        System.out.printf("|           TABUADA DO %3d           |\n", num);

        for (int i = 1; i <= 10; ++i) {

            tab = num * i;

            System.out.printf("|------------------------------------|\n");
            System.out.printf("|    %4d    x    %3d    =    %3d    |\n", num, i, tab);

        }

        System.out.println("|------------------------------------|\n");
    }
}