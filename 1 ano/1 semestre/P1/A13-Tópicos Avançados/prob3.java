import java.util.*;
import java.io.*;

public class prob3 {

    static Scanner k = new Scanner(System.in);

    public static void main(String[] args) throws IOException {

        int opt;

        Bot[] bots = new Bot[1000];

        do {
            System.out.println("Micro-Rato 2013 - Gestão da prova:");
            System.out.println("1 - Adicionar informação relativa a um robô");
            System.out.println("2 - Imprimir informação dos robôs em prova");
            System.out.println("3 - Vencedor da prova e tempos médios de prova");
            System.out.println("4 - Média do número de elementos por equipa");
            System.out.println("5 - Mostrar o nome dos robôs em maiúsculas");
            System.out.println("6 - Alterar informação de um robô");
            System.out.println("7 - Remover robôs da competição");
            System.out.println("8 - Gravar informação da prova num ficheiro");
            System.out.println("9 - Terminar o programa");
            System.out.print("Opção -> ");
            opt = k.nextInt();

            switch (opt) {

                case 1:
                    bots = addBot(bots);
                    break;

                case 2:
                    printBots(bots);
                    break;

                case 3:
                    winner(bots);
                    break;

                case 4:
                    avgNel(bots);
                    break;

                case 5:
                    capsNames(bots);
                    break;

                case 6:
                    bots = changeBots(bots);
                    break;

                case 7:
                    bots = remBots(bots);
                    break;

                case 8:
                    stamp(bots);
                    break;

                case 9:
                    break;

                default:
                    System.out.println("Opção não válida, coloque outra.");
                    break;
            }
        } while (opt != 9);
    }

    //modulo para adicionar bots
    public static Bot[] addBot(Bot[] b) {

        int cnt = contador(b);

        if (cnt == b.length) {
            System.out.println("Este ano não podem entrar mais bots.");
        } else {

            b[cnt] = new Bot();

            System.out.print("\nNome do Bot: ");
            while (k.nextLine().length() != 0) ;
            b[cnt].name = k.nextLine();

            System.out.print("Nº de elementos: ");
            b[cnt].nel = k.nextInt();

            System.out.print("Tempo de prova em segundos: ");
            b[cnt].time = k.nextInt();
            System.out.println();
        }

        return b;
    }

    //modulo para imprimir informações
    public static void printBots(Bot[] b) {

        int cnt = contador(b);

        for (int i = 0; i < cnt; i++) {
            System.out.printf("Bot #%d:\n", i + 1);
            printBot(b[i]);
            System.out.println();
        }
    }

    //modulo para imprimir o vencedor e tempos médios de prova de todos os bots
    public static void winner(Bot[] b) {

        int cnt = contador(b);

        Bot win = new Bot();

        double avg = 0;

        //calcula o melhor bot e a média
        for (int i = 0; i < cnt; i++) {

            //melhor bot
            if (b[i].time < win.time) {
                win = b[i];
            }

            //media
            avg += b[i].time;
        }

        avg /= cnt;

        System.out.println("Melhor bot:");
        printBot(win);
        System.out.printf("\nMédia de tempos: %4.2f segundos.\n\n", avg);
    }

    //modulo para calcular a media de numero de elementos na equipa
    public static void avgNel(Bot[] b) {

        int cnt = contador(b);

        double avg = 0;

        for (int i = 0; i < cnt; i++) {
            avg += b[i].nel;
        }

        avg /= cnt;

        System.out.printf("\nMédia de elementos por equipa: %4.2f.\n\n", avg);
    }

    //modulo para imprimir os nomes dos bots em CAPS
    public static void capsNames(Bot[] b) {

        int cnt = contador(b);

        boolean swap;

        Bot tmp = new Bot();

        //ordenar
        do {

            swap = false;

            for (int i = 0; i < cnt - 1; i++) {
                for (int j = 0; j < b[i].name.length() || j < b[i].name.length(); j++) {
                    if (b[i].name.charAt(j) > b[i + 1].name.charAt(j)) {
                        tmp = b[i];
                        b[i] = b[i + 1];
                        b[i + 1] = tmp;
                        swap = true;
                        break;
                    } else if (b[i].name.charAt(j) < b[i + 1].name.charAt(j)) {
                        break;
                    }
                }
            }
        } while (swap);

        //imprimir
        System.out.println();
        for (int i = 0; i < cnt; i++) {
            System.out.printf("Bot #%d: ", i + 1);
            for (int j = 0; j < b[i].name.length(); j++) {
                System.out.print(Character.toUpperCase(b[i].name.charAt(j)));
            }
            System.out.println();
        }
        System.out.println();
    }

    //modulo para mudar informação de um bot como base no nome
    public static Bot[] changeBots(Bot[] b) {

        int cnt = contador(b);
        int pos = -1;

        String name;

        boolean existe = false;

        System.out.print("\nNome do bot a a modificar: ");
        while (k.nextLine().length() != 0) ;
        name = k.nextLine();

        for (int i = 0; i < cnt; i++) {
            if (b[i].name.indexOf(name) != -1) {
                pos = i;
                existe = true;
                break;
            }
        }

        if (!existe) {
            System.out.println("O nome não consta na lista.\n");
        } else {

            System.out.print("Novo nome do bot: ");
            b[pos].name = k.nextLine();

            System.out.print("Novo tempo do bot em segundos: ");
            b[pos].time = k.nextInt();

            System.out.print("Elementos da equipa: ");
            b[pos].nel = k.nextInt();

            System.out.println();
        }

        return b;
    }

    //modulo para remover bots que tenham gasto mais tempo que o valor inserido pelo utilizador
    public static Bot[] remBots(Bot[] b) {

        int rem;
        int cnt = contador(b);

        boolean remo;

        System.out.print("Qual o tempo máximo permitido em segundos: ");
        rem = k.nextInt();

        do {
            remo = false;

            for (int i = 0; i < cnt; i++) {
                if (remo) {

                    b[i] = b[i + 1];
                    b[i + 1] = null;

                } else if (i == cnt - 1) {

                    if (!remo && b[i].time > rem) {

                        b[i] = null;
                        cnt--;

                    } else if (remo) {

                        b[i] = null;
                        cnt--;
                    }
                } else if (b[i].time > rem && !remo) {

                    b[i] = b[i + 1];
                    b[i + 1] = null;
                    remo = true;
                }
            }
        } while (remo);

        return b;
    }

    //modulo para gravar as informações da prova num ficheiro à escolha do utilizador
    public static void stamp(Bot[] b) throws IOException {

        String filename;

        int cnt = contador(b);

        System.out.print("Nome do ficheiro em que quer gravar os dados: ");
        while (k.nextLine().length() != 0) ;
        filename = k.nextLine();

        File fix = new File(filename);

        PrintWriter fill = new PrintWriter(fix);

        for (int i = 0; i < cnt; i++) {

            fill.printf("Bot #%d:\n", i + 1);
            fill.printf("\tNome: %s\n", b[i].name);
            fill.printf("\tNº elementos: %d\n", b[i].nel);
            fill.printf("\tTempo em segundos: %d\n\n\n", b[i].time);
        }

        fill.close();
    }

    //modulo de impressão de um bot
    public static void printBot(Bot b) {

        int h, min, seg;

        h = (int) b.time / 3600;

        min = (int) (b.time - h * 3600) / 60;

        seg = b.time - h * 3600 - min * 60;

        System.out.printf("Nome: %s\n", b.name);
        System.out.printf("Nº de elementos: %d\n", b.nel);
        System.out.printf("Tempo de prova: %d:%d:%d\n", h, min, seg);
    }

    //modulo de contagem de bots
    public static int contador(Bot[] b) {

        int num;

        for (num = 0; num < b.length; num++) {
            if (b[num] == null) {
                break;
            }
        }

        return num;
    }
}

class Bot {
    String name;
    int time;
    int nel;
}