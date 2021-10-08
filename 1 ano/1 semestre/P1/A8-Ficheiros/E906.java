import java.io.*;
import java.util.*;

public class E906 {

    public static void main(String[] args) throws IOException {

        File entrada = new File(args[0]);
        File exit = new File(args[1]);

        Scanner kfile = new Scanner(entrada);

        if (exit.exists()) {

            System.out.println("O ficheiro para escrita já existe.\nO programa vai ser terminado.");
            return;

        } else if (!entrada.exists()) {

            System.out.println("O ficheiro de entrada não existe.\nO programa vai ser terminado.");
            return;

        } else if (!entrada.isFile()) {

            System.out.printf("%s não é ficheiro.\n", args[0]);
            return;

        } else if (!entrada.exists()) {

            System.out.printf("%s não é possível de ler.\n", args[0]);
            return;

        } else if (entrada.exists()) {

            Scanner k = new Scanner(entrada);

            if (!entrada.canRead()) {

                System.out.printf("%s não é possível de ler.\n", args[0]);
                return;
            }

            k.close();

        }

        PrintWriter rit = new PrintWriter(exit);

        while (kfile.hasNext()) {

            rit.println(kfile.nextLine());
        }

        kfile.close();
        rit.close();
    }
}