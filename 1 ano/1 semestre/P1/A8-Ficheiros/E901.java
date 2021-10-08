import java.util.*;
import java.io.*;

public class E901 {

    public static void main(String[] args) throws IOException {

        //nome do ficheiro
        String nomef;

        //scnner de texto do teclado
        Scanner k = new Scanner(System.in);

        //ficheiro
        File fix;

        do {

            System.out.print("Qual o nome do ficheiro que quer ler? ");
            nomef = k.nextLine();

            fix = new File(nomef);

            if (!fix.isFile() || !fix.canRead()) {

                System.out.println("Ficheiro não válido.\nColoque outro.");
            }

        } while (!fix.isFile() || !fix.canRead());

        //scanner do ficheiro, tem de ser fechado
        Scanner fil = new Scanner(fix);

        //leitura do ficheiro
        while (fil.hasNextLine()) {

            System.out.println(fil.next());
        }

        fil.close();
    }
}