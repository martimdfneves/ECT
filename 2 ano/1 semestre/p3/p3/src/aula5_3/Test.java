package aula5_3;

import java.util.*;
import java.io.*;

import static java.lang.System.*;

public class Test {

    final static Scanner sc = new Scanner(in);

    public static void main(String args[]) throws IOException {

        Agenda a = new Agenda();
        int opt;
        String file;
        do {

            opt = menu();
            if (opt == 1) {

                a.addContact();
            } else if (opt == 2) {

                out.print("ficheiro: ");
                file = sc.nextLine();
                a.addContactFromFile(file);
            } else if (opt == 3) {

                out.print("ficheiro: ");
                file = sc.nextLine();
                a.loadContacts(file);
            } else if (opt == 4) {

                a.printContactos();
            } else if (opt == 0) {

                out.println("bye bye");
            } else {

                out.println("op��o no aceitvel");
            }
        } while (opt != 0);
    }

    public static int menu() {

        out.println("Menu:");
        out.println("\t1-> Adicionar contacto manual");
        out.println("\t2-> Adicionar contacto por ficheiro");
        out.println("\t3-> Guardar contactos  num ficheiro");
        out.println("\t4-> Imprimir contactos");
        out.println("\t0-> sair");
        out.print("\nOp��o: ");
        return Integer.parseInt(sc.nextLine());
    }
}
