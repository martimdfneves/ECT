import java.io.*;
import java.util.Scanner;

public class E1004 {

    static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws IOException {

        int n = lerNum();
        String nomesDosAlunos[] = new String[n];
        int numerosDosAlunos[] = new int[n];
        int d, k = -1;
        String g = null;

        lerNom(nomesDosAlunos, numerosDosAlunos, n);

        do {

            System.out.print("Insira o número que deseja pesquisar: ");
            d = sc.nextInt();

            for (int j = 0; j < numerosDosAlunos.length; j++) {

                if (numerosDosAlunos[j] == d) {

                    k = j;
                    g = nomesDosAlunos[j];
                    break;
                }
            }

            System.out.println((k > 0 ? "O seu número corresponde ao aluno: " + g + "." : "Número não encontrado."));

        } while (d != 0);
    }

    //modulo para nomes e numeros dos alunos
    public static void lerNom(String noms[], int nums[], int n) throws IOException {

        File alunos = new File("alunos.tab");
        n = 0;

        Scanner lerAl2 = new Scanner(alunos);

        while (lerAl2.hasNextLine()) {
            nums[n] = lerAl2.nextInt();
            noms[n] = lerAl2.nextLine();
            n++;
        }


        lerAl2.close();
    }

    //modulo para ler numeros do ficheiro alunos
    public static int lerNum() throws IOException {

        int n = 0;
        File alunos = new File("alunos.tab");

        Scanner lerAl = new Scanner(alunos);

        while (lerAl.hasNext()) {
            lerAl.next();
            n++;
        }
        lerAl.close();

        return n;
    }
}
