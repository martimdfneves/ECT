import java.util.*;

public class E804 {

    public static void main(String[] args) {

        Scanner k = new Scanner(System.in);

        int pat;
        String pad3;

        System.out.print("Frase: ");
        pad3 = k.nextLine();

        String[] pad = new String[3];
        pad[0] = "AA-00-00";
        pad[1] = "00-AA-00";
        pad[2] = "00-00-AA";

        boolean aceite = false;

        for (int i = 0; i < 3; i++) {

            if (matchPattern(pad3, pad[i])) {

                aceite = true;
            }
        }

        if (aceite) {

            System.out.printf("A matrícula %s é aceite em Portugal.\n\n", pad3);

        } else {

            System.out.printf("A matrícula %s não é aceite em Portugal.\n\n", pad3);

        }
    }

    //modulo pra descobrir se pertence
    public static boolean matchPattern(String frase, String pattern) {

        boolean pertence = true;

        for (int i = 0; i < frase.length(); i++) {

            if (pattern.charAt(i) == 'A') {

                if (Character.isDigit(frase.charAt(i))) {

                    pertence = false;
                }
            } else if (pattern.charAt(i) == '0') {

                if (!Character.isDigit(frase.charAt(i))) {

                    pertence = false;
                }
            } else if (pattern.charAt(i) == '-') {

                if (frase.charAt(i) != '-') {

                    pertence = false;
                }
            }
        }

        return pertence;
    }
}