import java.util.*;
import java.io.*;

public class exe1_7 {

    public static void main(String[] args) throws IOException {

        Scanner sc = new Scanner(System.in);

        String in, out, r;

        System.out.println("Digite o nome do ficheiro a copiar: ");
        in = sc.nextLine();
        System.out.println("Digite o nome do ficheiro para onde copiar: ");
        out = sc.nextLine();

        File fin = new File(in);
        File fout = new File(out);

        if (!fin.exists()) {
            System.out.printf("O ficheiro %s nao existe, o programa vai ser terminado\n", fin);
            return;
        } else if (fin.isDirectory() || fout.isDirectory()) {
            System.out.printf("Pelo menos um dos ficheiros é um diretorio, o programa vai ser terminado\n");
            return;
        } else if (!fin.canRead() || !fin.canWrite()) {
            System.out.printf("O ficheiro nao pode ser lido ou escrito, o programa vai ser terminado\n");
            return;
        } else if (!fout.canRead() || !fout.canWrite()) {
            System.out.printf("O ficheiro nao pode ser lido ou escrito, o programa vai ser terminado\n");
            return;
        } else if (fout.exists()) {
            System.out.printf("O ficheiro para onde copiar ja existe, deseja substituir?   s-sim/n-nao\n");
            r = sc.nextLine();
            if (r == "n") {
                System.out.printf("Erro, o programa vai ser terminado\n");
                System.exit(1);
            }
        } else if (fin.exists()) {
            Scanner ler = new Scanner(fin);
            PrintWriter pr = new PrintWriter(fout);
            while (ler.hasNext()) {
                pr.println(ler.nextLine());
            }
            pr.close();
            ler.close();
        }

    }
}

