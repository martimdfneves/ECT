import java.util.*;

public class E802 {

    static Scanner k = new Scanner(System.in);

    public static void main(String[] args) {

        String nome;

        System.out.print("Nome da organização: ");
        nome = k.nextLine();

        acronimo(nome);
    }

    //modulo pra fazer acronimos
    public static void acronimo(String z) {

        System.out.printf("\nacrónimo(\"%s\") -> ", z);

        for (int i = 0; i < z.length(); i++) {

            if (Character.isUpperCase(z.charAt(i))) {

                System.out.printf("%c", z.charAt(i));
            }
        }

        System.out.println();
    }
}