import static java.lang.System.*;

import java.util.*;
import java.io.*;

public class E503 {

    public static void main(String[] args) throws IOException {
        try {
            File entrada = new File(args[0]);
            File exit = new File(args[1]);

            Scanner kfile = new Scanner(entrada);
            Scanner k = new Scanner(in);
            if (exit.exists()) {
                System.out.println("Quer escrever por cima do ficheiro atual? [y/n]");
                char li = k.next().charAt(0);
                if (Character.toLowerCase(li) != 'y') {
                    System.out.println("Programa terminado com insucesso");
                    exit(4);
                }
            }

            PrintWriter rit = new PrintWriter(exit);

            while (kfile.hasNext()) {

                rit.println(kfile.nextLine());
            }

            kfile.close();
            rit.close();
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Argumentos Insuficientes");
            exit(1);
        } catch (FileNotFoundException e) {
            System.out.println("Ficheiro não encontrado");
        }
    }
}
