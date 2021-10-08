import java.util.*;

import static java.lang.Math.*;

public class E1105 {

    public static void main(String[] args) {

        Scanner k = new Scanner(System.in);

        int counter = 0;
        char opt;
        Contacto[] cont = new Contacto[100];

        //inicializa os contactos
        for (int i = 0; i < 100; i++) {
            cont[i] = new Contacto();
            cont[i] = null;
        }

        do {
            System.out.println("\n\nGestão de uma agenda:");
            System.out.println("\tI -> Inserir um contacto");
            System.out.println("\tP -> Pesquisar contacto por nome");
            System.out.println("\tE -> Eliminar um contacto");
            System.out.println("\tM -> Mostrar os contactos");
            System.out.println("\tO -> Mostrar os contactos ordenados pelo nome");
            System.out.println("\tV -> validar endereços de email");
            System.out.println("\tA -> Apagar a agenda");
            System.out.println("\tT -> Terminar o programa");
            System.out.print("Opção -> ");
            opt = k.next().charAt(0);

            switch (opt) {

                case 'I':
                    cont[counter] = insertCont(counter);
                    counter++;
                    System.out.println();
                    break;

                case 'P':
                    searchName(cont, counter);
                    break;

                case 'E':
                    cont = removeCont(cont, counter);
                    break;

                case 'M':
                    showCont(cont);
                    break;

                case 'O':
                    showOrde(cont);
                    break;

                case 'V':
                    valMail(cont);
                    break;

                case 'A':
                    cont = delAgenda();
                    break;

                case 'T':
                    break;

                default:
                    System.out.println("Opção não aceite, coloque outra.\n\n");
                    break;
            }
        } while (opt != 'T');

        k.close();
    }

    //modulo de escrita de um contacto
    public static Contacto insertCont(int num) {

        Contacto cont = new Contacto();

        Scanner k = new Scanner(System.in);

        System.out.printf("\n\nContacto #%d:\n\n", num + 1);

        System.out.print("\tNome: ");
        cont.nome = k.nextLine();

        System.out.print("\tMorada: ");
        cont.morada = k.nextLine();

        System.out.print("\tNº de tlm/tlf: ");
        cont.tel = k.nextInt();

        System.out.print("\tEmail: ");
        cont.mail = k.next();

        return cont;
    }

    //modulo de procura de um contacto com base no nome
    public static void searchName(Contacto[] cont, int counter) {

        Scanner k = new Scanner(System.in);

        String nome;
        int position = -1;

        System.out.print("Nome: ");
        nome = k.nextLine();

        //procura o nome e dá a posição da primeira ocorrencia
        for (int i = 0; i < counter; i++) {

            position = (cont[i].nome).indexOf(nome);

            if (position != -1) {
                break;
            }
        }

        if (position == -1) {

            System.out.println("O nome não existe na lista.\n");

        } else {

            System.out.println("\n\n");
            printCont(cont[position]);
            System.out.println("\n\n");
        }
    }

    //modulo de eliminação de um contacto com base no numero de tlf
    public static Contacto[] removeCont(Contacto[] cont, int counter) {

        Scanner k = new Scanner(System.in);
        int num;
        boolean existe = false;
        int position = -1;

        Contacto[] contats = cont;

        System.out.print("\nNúmero a apagar: ");
        num = k.nextInt();

        //encontra o número
        for (int i = 0; i < counter; i++) {
            if (cont[i].tel == num) {
                position = i;
                break;
            }
        }

        if (position == -1) {

            System.out.println("O número colocado não existe na agenda.\n\n");

        } else {

            System.out.printf("O contacto %d foi removido da lista.\n\n\n", position);

            for (int i = poistion + 1; i < counter; i++) {

                if (cont[i].tel == num && !existe) {

                    cont[i] = null;
                    existe = true;

                } else if (existe) {

                    cont[i - 1] = contats[i];
                }
            }
            cont[counter - 1] = null;
        }
        return cont;
    }

    //modulo de impressão dos contacto pela ordem que foram colocados
    public static void showCont(Contacto[] cont) {

        for (int i = 0; i < 100; i++) {

            if (cont[i] == null) {
                break;
            }
            System.out.printf("\nContacto #%d:\n", i + 1);
            printCont(cont[i]);
        }
    }


    //modulo de impressão dos contactos ordenados pelo nome
    public static void showOrde(Contacto[] cont) {

        Contacto tmp = new Contacto();
        boolean swap = false;

        do {
            swap = false;

            for (int i = 0; i < 99; i++) {

                if (cont[i + 1] == null) {
                    break;
                }
                if (Character.toLowerCase(cont[i].nome.charAt(0)) > Character.toLowerCase(cont[i + 1].nome.charAt(0))) {

                    tmp = cont[i + 1];
                    cont[i + 1] = cont[i];
                    cont[i] = tmp;
                    swap = true;
                    break;
                }
            }
        } while (swap);

        System.out.println("Lista ordenada: ");

        for (int i = 0; i < 100; i++) {

            if (cont[i] == null) {
                break;
            }

            System.out.printf("\nContacto #%d:\n", i + 1);
            printCont(cont[i]);
        }

    }

    //modulo de validação dos emails
    public static Contacto[] valMail(Contacto[] cont) {

        Scanner k = new Scanner(System.in);

        int arrobas = 0;

        for (int i = 0; i < 100; i++) {

            if (cont[i] == null) {
                break;
            }

            for (int j = 0; j < cont[i].mail.length(); j++) {
                if (cont[i].mail.charAt(j) == '@') {
                    arrobas++;
                }
            }

            while (arrobas != 1 || !cont[i].mail.matches("[a-zA-Z0-9@_.]*")) {
                System.out.printf("O e-mail de %s não é válido coloque outro.\n", cont[i].nome);

                System.out.print("\nNome: ");
                cont[i].mail = k.nextLine();

                arrobas = 0;
                for (int j = 0; j < cont[i].mail.length(); j++) {
                    if (cont[i].mail.charAt(j) == '@') {
                        arrobas++;
                    }
                }
            }
        }

        System.out.println("Todos os mails foram validados.\n\n");

        return cont;
    }

    //modulo para apagar a agenda
    public static Contacto[] delAgenda() {

        Contacto[] cont = new Contacto[100];

        for (int i = 0; i < 100; i++) {
            cont[i] = null;
        }

        return cont;
    }

    //modulo de impressão de um contacto
    public static void printCont(Contacto cont) {

        System.out.printf("Nome: %s\n", cont.nome);
        System.out.printf("Morada: %s\n", cont.morada);
        System.out.printf("Nº.tlm/tlf: %d\n", cont.tel);
        System.out.printf("E-mail: %s\n", cont.mail);
    }
}

class Contacto {
    String nome;
    String morada;
    int tel;
    String mail;
}