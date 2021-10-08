/**
 * Diogo Daniel Soares Ferreira, 2014
 * <diogodanielsoaresferreira@ua.pt>
 * Nº Mec. 76504
 */
//~ package p1;

import java.io.*;

import static java.lang.System.*;

import java.util.Scanner;

public class Ptesteex4 {

    static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        int a;
        int[][] alunos = null;

        do {
            out.println("Serviços Académicos - Gestão de uma pauta:");
            out.println("\t1 - Ler ficheiro com números mec. e pedir informação de notas");
            out.println("\t2 - Imprimir no terminal a informação da disciplina");
            out.println("\t3 - Calcular estatísticas das notas finais");
            out.println("\t4 - Alterar as notas de um aluno");
            out.println("\t5 - Imprimir um histograma de notas");
            out.println("\t6 - Gravar num ficheiro a informação da disciplina");
            out.println("\t7 - Terminar o programa");
            out.print("Opção ->");
            a = sc.nextInt();

            switch (a) {

                case 1:
                    alunos = lerFich();
                    break;
                case 2:
                    print(alunos);
                    break;
                case 3:
                    stats(alunos);
                    break;
                case 4:
                    change(alunos);
                    break;
                case 5:
                    printHist(alunos);
                    break;
                case 6:
                    save(alunos);
                    break;
                case 7:
                    break;
                default:
                    out.println("Operação inválida!");
                    break;
            }
        } while (a != 7);

    }

    public static int[][] lerFich() throws IOException {
        File ficheiro;
        int n = 0, i = 0, tmp;
        int[][] alunos;

        while (sc.nextLine().length() != 0) ;
        do {
            out.print("\nInsira o nome de um ficheiro válido: ");
            String temp = sc.nextLine();
            ficheiro = new File(temp);
        } while (!ficheiro.exists() || !ficheiro.isFile() || !ficheiro.canRead());

        Scanner ler = new Scanner(ficheiro);

        while (ler.hasNextInt()) {
            ler.nextInt();
            n++;
        }
        alunos = new int[n][4];
        ler.close();

        Scanner ler2 = new Scanner(ficheiro);

        while (ler2.hasNextInt()) {
            alunos[i][0] = ler2.nextInt();
            do {
                out.print("\nInsira a nota do exame final (entre 0 e 20, ou 77 se faltou) do aluno com o número " + alunos[i][0] + ": ");
                tmp = sc.nextInt();
            } while ((tmp < 0 || tmp > 20) && tmp != 77);
            alunos[i][1] = tmp;
            do {
                out.print("\nInsira a nota do exame de recurso (entre 0 e 20, ou 77 se faltou) do aluno com o número " + alunos[i][0] + ": ");
                tmp = sc.nextInt();
            } while ((tmp < 0 || tmp > 20) && tmp != 77);
            alunos[i][2] = tmp;
            if (alunos[i][1] == 77 && alunos[i][2] == 77) alunos[i][3] = 77;
            else if (alunos[i][1] > alunos[i][2]) alunos[i][3] = alunos[i][1];
            else alunos[i][3] = alunos[i][2];
            if (alunos[i][3] == 77) out.print("\nO aluno faltou aos exames");
            else out.print("\nA nota final do aluno é de " + alunos[i][3] + ".");
            i++;
        }

        ler2.close();

        out.println();
        return alunos;
    }

    public static void print(int[][] alunos) {

        out.println("Pauta da disciplina");
        out.println("------------------------");
        for (int i = 0; i < alunos.length; i++) {
            out.printf("|%8d|%4d|%4d|%4d|\n", alunos[i][0], alunos[i][1], alunos[i][2], alunos[i][3]);
        }
    }

    public static void stats(int[][] alunos) {
        int sum = 0, best = 0, apr = 0, rep = 0, pos = -1;

        for (int i = 0; i < alunos.length; i++) {
            sum += alunos[i][3];
            if (alunos[i][3] > best) {
                best = alunos[i][3];
                pos = i;
            }
            if (alunos[i][3] < 9.5 || alunos[i][3] == 77) rep++;
            else apr++;
        }
        double med = sum / alunos.length;
        out.printf("\nA nota média foi de %4.1f, o melhor aluno foi o número %d com a nota final de %d. Houve %d alunos aprovados e %d reprovados.\n", med, alunos[pos][0], best, apr, rep);
    }

    public static void change(int[][] alunos) {
        int al, pos = -1;

        out.print("\nIntroduza o nº do aluno que deseja alterar as notas: ");
        al = sc.nextInt();

        for (int i = 0; i < alunos.length; i++) {
            if (alunos[i][0] == al) {
                pos = i;
                break;
            }
        }

        if (pos == -1) {
            out.print("\nAluno não encontrado!\n");
        } else {

            do {
                out.print("\nInsira a nota do exame final (entre 0 e 20, ou 77 se faltou) do aluno com o número " + alunos[pos][0] + ": ");
                alunos[pos][1] = sc.nextInt();
            } while ((alunos[pos][1] < 0 || alunos[pos][1] > 20) && alunos[pos][1] != 77);
            do {
                out.print("\nInsira a nota do exame de recurso (entre 0 e 20, ou 77 se faltou) do aluno com o número " + alunos[pos][0] + ": ");
                alunos[pos][2] = sc.nextInt();
            } while ((alunos[pos][2] < 0 || alunos[pos][1] > 20) && alunos[pos][2] != 77);

            if (alunos[pos][1] == 77 && alunos[pos][2] == 77) alunos[pos][3] = 77;
            else if (alunos[pos][1] > alunos[pos][2]) alunos[pos][3] = alunos[pos][1];
            else alunos[pos][3] = alunos[pos][2];

            if (alunos[pos][3] == 77) out.print("\nO aluno faltou aos exames\n");
            else out.print("\nA nota final do aluno é de " + alunos[pos][3] + ".\n");
        }
    }

    public static void printHist(int[][] alunos) {
        int[] notas = new int[21];
        int max = 0;

        for (int i = 0; i < 21; i++) {
            notas[i] = 0;
        }

        for (int i = 0; i < alunos.length; i++) {
            notas[alunos[i][3]]++;
        }

        for (int i = 0; i < 21; i++) {
            if (notas[i] > max) max = notas[i];
        }

        out.println("Histograma de uma disciplina");

        for (int i = max; i > 0; i--) {
            for (int j = 0; j < 21; j++) {
                out.print("  ");
                if (notas[j] >= i) out.print("*");
                else out.print(" ");
            }
            out.print("\n");
        }
        out.println("-----------------------------------------------------------------");
        out.println("  0  1  2  3  4  5  6  7  8  9  10 11 12 13 14 15 16 17 18 19 20 ");

    }

    public static void save(int[][] alunos) throws IOException {
        int j = 0;
        int[][] aprovados = new int[alunos.length][4];
        boolean troca = false;
        File ficheiro;

        for (int i = 0; i < alunos.length; i++) {
            if (alunos[i][3] >= 9.5) {
                aprovados[j] = alunos[i];
                j++;
            }
        }

        do {
            troca = false;

            for (int i = 0; i < alunos.length - 1; i++) {
                if (aprovados[i + 1] == null) break;

                if (aprovados[i][3] < aprovados[i + 1][3]) {
                    troca = true;
                    int[][] tmp = new int[1][4];
                    tmp[0] = aprovados[i];
                    aprovados[i] = aprovados[i + 1];
                    aprovados[i + 1] = tmp[0];
                }
            }

        } while (troca);

        while (sc.nextLine().length() != 0) ;
        out.print("\nQual é o ficheiro onde deseja gravar? ");
        String tmp = sc.nextLine();
        ficheiro = new File(tmp);

        PrintWriter write = new PrintWriter(ficheiro);

        for (int i = 0; i < aprovados.length; i++) {
            if (aprovados[i][0] == 0) break;
            write.println("\n" + aprovados[i][0] + "\t" + aprovados[i][1] + "\t" + aprovados[i][2] + "\t" + aprovados[i][3]);
        }
        write.close();
    }

}
