/**
 * Diogo Daniel Soares Ferreira, 2014
 * <diogodanielsoaresferreira@ua.pt>
 * Nº Mec. 76504
 */
//~ package p1;

import static java.lang.System.*;

import java.util.Scanner;
import java.io.*;

public class Ptesteex3 {

    static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        int in;
        Robo[] r = new Robo[50];

        do {
            out.println("Micro-Rato 2013 - Gestão da prova:");
            out.println("1 - Adicionar informação relatica a um robô");
            out.println("2 - Imprimir informação dos robôs em prova");
            out.println("3 - Vencedor da prova e tempos médios de prova");
            out.println("4 - Média do número de elementos por equipa");
            out.println("5 - Mostrar o nome dos robôs em maiúsculas");
            out.println("6 - Alterar informação de um robô");
            out.println("7 - Remover robôs da competição");
            out.println("8 - Gravar informação da prova num ficheiro");
            out.println("9 - Terminar o programa");
            out.print("Opção -> ");
            in = sc.nextInt();

            switch (in) {

                case 1:
                    adicRobo(r);
                    break;
                case 2:
                    printRobo(r);
                    break;
                case 3:
                    venc(r);
                    break;
                case 4:
                    medEle(r);
                    break;
                case 5:
                    nameOrdMai(r);
                    break;
                case 6:
                    altInf(r);
                    break;
                case 7:
                    remRob(r);
                    break;
                case 8:
                    gravFich(r);
                    break;
                case 9:
                    break;
                default:
                    out.println("Entrada inválida. Insira novamente.");
                    break;
            }

        } while (in != 9);

    }

    public static void adicRobo(Robo[] r) {
        int n = countRobo(r);

        r[n] = new Robo();
        while (sc.nextLine().length() != 0) ;
        out.print("\n\nQual é o nome que deseja dar ao robô? ");
        r[n].nome = sc.nextLine();
        out.print("\nQual foi o seu tempo de prova (em segundos)? ");
        r[n].time = sc.nextInt();
        out.print("\nQual é o número de elementos da equipa? ");
        r[n].ele = sc.nextInt();
        out.print("\n");
    }

    public static void printRobo(Robo[] r) {
        int n = countRobo(r);

        for (int i = 0; i < n; i++) {
            out.print("\n\nRobô " + (i + 1) + ":\n\tNome: " + r[i].nome + "\n\tTempo: " + secToHour(r[i].time) + "\n\tNúmero de Elementos: " + r[i].ele + "\n");
        }
        out.println();
    }

    public static void venc(Robo[] r) {
        int n = countRobo(r), pos = -1, best = Integer.MAX_VALUE, med = 0, sum = 0;

        for (int i = 0; i < n; i++) {
            sum += (r[i].time);
            if (r[i].time < best) {
                best = r[i].time;
                pos = i;
            }
        }
        med = sum / n;

        out.println("\nO robô vencedor foi " + r[pos].nome + " e o tempo médio foi de " + secToHour(med) + ".\n");

    }

    public static void medEle(Robo[] r) {
        int n = countRobo(r), sum = 0, med;

        for (int i = 0; i < n; i++) {
            sum += r[i].ele;
        }

        med = sum / n;
        out.println("\nA média dos elementos de equipa é de " + med + "\n");
    }

    public static void nameOrdMai(Robo[] r) {
        int n = countRobo(r);
        boolean troca = false;

        do {
            for (int i = 0; i < n - 1; i++) {
                troca = false;
                if (Character.getNumericValue(r[i].nome.charAt(0)) > Character.getNumericValue(r[i + 1].nome.charAt(0))) {
                    Robo[] temp = new Robo[1];
                    temp[0] = r[i];
                    r[i] = r[i + 1];
                    r[i + 1] = temp[0];
                    troca = true;
                }
            }

        } while (troca);

        out.println("\nNomes:");
        for (int i = 0; i < n; i++) {
            out.print("\n");
            String s = r[i].nome;
            for (int j = 0; j < s.length(); j++) {
                out.print(Character.toUpperCase(s.charAt(j)));
            }
        }
        out.println();
        out.println();
    }

    public static void altInf(Robo[] r) {
        int n = countRobo(r), pos = -1;
        String name;

        out.println();
        while (sc.nextLine().length() != 0) ;
        out.print("Qual é o nome do robô que deseja alterar? ");
        name = sc.nextLine();

        for (int i = 0; i < n; i++) {
            if (r[i].nome.indexOf(name) != -1) pos = i;
        }

        if (pos == -1) out.print("\nO robô não existe.\n\n");
        else {
            out.print("\nNovo nome: ");
            r[pos].nome = sc.nextLine();
            out.print("\nTempo (em segundos): ");
            r[pos].time = sc.nextInt();
            out.print("\nNúmero de elementos: ");
            r[pos].ele = sc.nextInt();
            out.println();
        }

    }

    public static void remRob(Robo[] r) {
        int sec, n = countRobo(r);
        boolean troca = false;

        out.print("\nInsira em segundos o tempo limite de prova: ");
        sec = sc.nextInt();
        do {
            n = countRobo(r);
            troca = false;
            for (int i = 0; i < n; i++) {
                if (i != (n - 1)) {
                    if (troca) {
                        r[i] = r[i + 1];
                        r[i + 1] = null;
                    } else {
                        if (r[i].time > sec) {
                            r[i] = r[i + 1];
                            r[i + 1] = null;
                            troca = true;
                        }
                    }
                } else {
                    if (troca) r[i] = null;
                    else {
                        if (r[i] == null) ;
                        else if (r[i].time > sec) r[i] = null;
                    }
                }
            }
        } while (troca);

        out.print("\nOperação consluída com sucesso!\n");
    }

    public static void gravFich(Robo[] r) throws IOException {
        File ficheiro;
        int n = countRobo(r);

        while (sc.nextLine().length() != 0) ;
        out.print("\nQual é o nome do ficheiro onde deseja gravar os dados dos robôs? ");
        String name = sc.nextLine();
        ficheiro = new File(name);

        PrintWriter write = new PrintWriter(ficheiro);

        for (int i = 0; i < n; i++) {
            write.println(r[i].nome + "\t" + secToHour(r[i].time) + "\t" + r[i].ele + "\n");
        }
        out.println("Copiado com sucesso!\n");

        write.close();
    }

    public static int countRobo(Robo[] r) {
        int n;
        for (n = 0; ; n++) {
            if (r[n] == null) break;
        }

        return n;
    }

    public static String secToHour(int sec) {
        String time;
        int hour = 0, min = 0;

        min = sec / 60;
        sec = sec % 60;
        hour = min / 24;
        min = min % 24;

        time = hour + ":" + min + ":" + sec;

        return time;
    }
}

class Robo {

    String nome;
    int time;
    int ele;
}
