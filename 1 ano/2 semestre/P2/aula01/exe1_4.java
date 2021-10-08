import static java.lang.System.*;

import java.util.*;

public class exe1_4 {
    static final Scanner k = new Scanner(in);

    public static void main(String[] args) {

        while (true) {

            String frase = null;

            System.out.print("Frase: ");
            frase = k.nextLine();
            if (frase.length() == 0) {
                break;
            } else {
                String conv = converse(frase);
                System.out.println(conv);
            }
        }
    }

    public static String converse(String frs) {

        String fr2 = "";

        for (int i = 0; i < frs.length(); i++) {
            if (frs.charAt(i) == 'r' || frs.charAt(i) == 'R') ;
            else if (frs.charAt(i) == 'l') {
                fr2 += 'u';
            } else if (frs.charAt(i) == 'L') {
                fr2 += 'U';
            } else {
                fr2 += frs.charAt(i);
            }
        }
        return fr2;
    }
}
