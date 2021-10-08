import java.io.*;
import java.util.*;

public class prob4 {

    static Scanner k = new Scanner(System.in);

    public static void main(String[] args) throws IOException {

        int opt;

        Aluno[] al = new Aluno[1000];

        do {
            System.out.println("Serviços Académicos - Gestão de uma pauta:");
            System.out.println("\t1 - Ler ficheiro com números mec. e pedir informação de notas");
            System.out.println("\t2 - Imprimir no terminal a informação da disciplina");
            System.out.println("\t3 - Calcular estatísticas das notas finais");
            System.out.println("\t4 - Alterar as notas de um aluno");
            System.out.println("\t5 - Imprimir um histograma de notas");
            System.out.println("\t6 - Gravar num ficheiro a informação da disciplina (ordenada)");
            System.out.println("\t7- Terminar o programa");
            System.out.print("Opção -> ");
            opt = k.nextInt();

            switch (opt) {

                case 1:
                    al = read(al);
                    break;

                case 2:
                    printData(al);
                    break;

                case 3:
                    stats(al);
                    break;

                case 4:
                    al = mudStats(al);
                    break;

                case 5:
                    hist(al);
                    break;

                case 6:
                    stamp(al);
                    break;

                case 7:
                    break;

                default:
                    System.out.println("Opção não válida, coloque outra.");
                    break;
            }
        } while (opt != 7);
    }

    //modulo para ler ficheiro e colocar as notas
    public static Aluno[] read(Aluno[] al) throws IOException {

        String filename;
        File fix;
        int num = 0;

        while (k.nextLine().length() != 0) ;
        do {
            System.out.print("Nome do ficheiro com os nmecs: ");
            filename = k.nextLine();

            fix = new File(filename);

            if (!fix.exists()) {
                System.out.println("O ficheiro inserido não existe. Coloque outro.\n");
            }
        } while (!fix.exists() || !fix.canRead());

        Scanner file = new Scanner(fix);

        while (file.hasNext()) {
            al[num] = new Aluno();
            al[num].nmec = Integer.parseInt(file.nextLine());
            num++;
        }

        System.out.println("\nSe o aluno faltou insira o código 77.\n");
        for (int i = 0; i < num; i++) {

            do {
                System.out.printf("Nota do aluno %d em época normal: ", al[i].nmec);
                al[i].nn = k.nextInt();

                System.out.printf("Nota do aluno %d em época de recurso: ", al[i].nmec);
                al[i].nr = k.nextInt();

                //se faltou ao normal
                if (al[i].nn == 77) {

                    //e faltou a recurso
                    if (al[i].nr == 77) {
                        al[i].nf = 0;

                        //nao faltou a recurso
                    } else {
                        al[i].nf = al[i].nr;
                    }

                    //nao faltou a normal
                } else if (al[i].nn != 77) {

                    //nao foi a recuso
                    if (al[i].nr == 77) {
                        al[i].nf = al[i].nn;

                        //foi a recurso
                    } else if (al[i].nr != 77) {

                        //melhorou
                        if (al[i].nr > al[i].nn) {
                            al[i].nf = al[i].nr;

                            //manteve ou piorou
                        } else if (al[i].nn == al[i].nr || al[i].nn > al[i].nr) {
                            al[i].nf = al[i].nn;
                        }
                    }
                }

            } while (!(al[i].nf <= 20 && al[i].nf >= 0) && al[i].nf != 77);
        }

        System.out.println("\n\n");
        return al;
    }

    //modulo para imprimir as informações da disciplina
    public static void printData(Aluno[] al) {

        System.out.println("Pauta de uma disciplina");
        System.out.println("--------------------------");

        for (int i = 0; i < contador(al); i++) {

            System.out.printf("|%8d|%4d|%4d|%4d|\n", al[i].nmec, al[i].nn, al[i].nr, al[i].nf);
        }

        System.out.println("--------------------------\n\n");

    }

    //modulo para calcular as estatísticas das notas finais
    public static void stats(Aluno[] al) {

        int cnt = contador(al);
        double avg = 0;
        Aluno best = new Aluno();

        for (int i = 0; i < cnt; i++) {

            if (al[i].nf != 77) {
                avg += al[i].nf;

                if (al[i].nf > best.nf) {
                    best = al[i];
                }
            }
        }

        avg /= cnt;
        System.out.printf("\nMédia das notas finais: %4.2f\nMelhor aluno:\n", avg);
        pritnAluno(best);
        System.out.println("\n\n");
    }

    //modulo para alterar as notas de um aluno com base no seu nmec
    public static Aluno[] mudStats(Aluno[] al) {

        int nmec;
        boolean existe = false;

        System.out.print("Nº mecanográfico do aluno a ser mudado: ");
        nmec = k.nextInt();

        for (int i = 0; i < contador(al); i++) {

            if (nmec == al[i].nmec) {

                do {
                    System.out.printf("Nota do aluno %d em época normal: ", al[i].nmec);
                    al[i].nn = k.nextInt();

                    System.out.printf("Nota do aluno %d em época de recurso: ", al[i].nmec);
                    al[i].nr = k.nextInt();

                    //se faltou ao normal
                    if (al[i].nn == 77) {

                        //e faltou a recurso
                        if (al[i].nr == 77) {
                            al[i].nf = 0;

                            //nao faltou a recurso
                        } else {
                            al[i].nf = al[i].nr;
                        }

                        //nao faltou a normal
                    } else if (al[i].nn != 77) {

                        //nao foi a recuso
                        if (al[i].nr == 77) {
                            al[i].nf = al[i].nn;

                            //foi a recurso
                        } else if (al[i].nr != 77) {

                            //melhorou
                            if (al[i].nr > al[i].nn) {
                                al[i].nf = al[i].nr;

                                //manteve ou piorou
                            } else if (al[i].nn == al[i].nr || al[i].nn > al[i].nr) {
                                al[i].nf = al[i].nn;
                            }
                        }
                    }

                } while (!(al[i].nf <= 20 && al[i].nf >= 0) && al[i].nf != 77);
            }
        }

        if (!existe) {
            System.out.printf("O aluno %d não existe.\n\n", nmec);
        }
        return al;
    }

    //modulo para imprimir o histograma das notas
    public static void hist(Aluno[] al) {

        int[] notas = new int[21];
        int posmax = 0;

        //calcula o histograma
        for (int i = 0; i < contador(al); i++) {
            notas[al[i].nf]++;

            if (notas[al[i].nf] > posmax) {
                posmax = notas[al[i].nf];
            }
        }

        System.out.println("\n\n");
        //imprime o histograma (altura)
        for (int i = posmax + 1; i >= 0; i--) {

            if (i > 1) {
                //imprime o histograma (largura)
                for (int j = 0; j < 21; j++) {
                    if (notas[j] >= i - 1) {
                        System.out.print(" * ");
                    } else {
                        System.out.print("   ");
                    }
                }
                System.out.println();

            } else if (i == 1) {
                System.out.println("------------------------------------------------------------------");

            } else if (i == 0) {
                for (int j = 0; j < 21; j++) {
                    System.out.printf("%2d ", j);
                }
            }
        }
        System.out.println("\n\n");
    }

    //modulo para gravar num ficheiro os dados ordenados por ordem crescente da nota final
    public static void stamp(Aluno[] al) throws IOException {

        int cnt = contador(al);
        Aluno tmp = new Aluno();
        boolean remo;
        String filename;

        //ordenar
        do {
            remo = false;

            for (int i = 0; i < cnt - 1; i++) {
                if (al[i].nf < al[i + 1].nf) {
                    tmp = al[i];
                    al[i] = al[i + 1];
                    al[i + 1] = tmp;
                    remo = true;
                }
            }
        } while (remo);

        System.out.print("Nome do ficheiro em que quer gravar: ");
        while (k.nextLine().length() != 0) ;
        filename = k.nextLine();

        File fix = new File(filename);
        PrintWriter escreve = new PrintWriter(fix);

        //escreve no ficheiro
        for (int i = 0; i < cnt; i++) {
            if (al[i].nf < 10) {
                break;
            }
            escreve.printf("Nºmec: %d\tNota final: %d\n", al[i].nmec, al[i].nf);
        }

        escreve.close();
    }

    //modulo de impressão de um aluno
    public static void pritnAluno(Aluno al) {

        System.out.printf("Nºmec: %d\tNota Final: %d\n", al.nmec, al.nf);
    }

    //modulo de contagem de quantos alunos existem
    public static int contador(Aluno[] al) {

        int num;

        for (num = 0; num < al.length; num++) {
            if (al[num] == null) {
                break;
            }
        }

        return num;
    }
}

class Aluno {
    int nmec;
    int nn;
    int nr;
    int nf;
}