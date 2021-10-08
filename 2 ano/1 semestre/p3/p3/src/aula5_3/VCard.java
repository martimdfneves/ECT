package aula5_3;

import java.io.*;
import java.util.*;

import aula2_1.*;

public class VCard implements InterfaceAgenda {

    public void saveAgenda(File file, Contacto[] contactos) throws IOException {
        PrintWriter printer = new PrintWriter(file);
        printer.println("vCard");
        for (Contacto contacto : contactos) {
            printer.print("#" + contacto.getName() + "#");
            printer.print(contacto.getNumb() + "#");
            printer.println(contacto.getDate());
        }
        printer.close();
    }

    public ArrayList<Contacto> loadAgenda(Scanner file) throws IOException {

        ArrayList<Contacto> l = new ArrayList<Contacto>();
        String linha[];
        int numb;
        String data[];
        while (file.hasNextLine()) {

            linha = file.nextLine().split("#");
            numb = Integer.parseInt(linha[2]);
            data = linha[3].split("/");
            l.add(new Contacto(numb, new Data(Integer.parseInt(data[0]), Integer.parseInt(data[1]), Integer.parseInt(data[2])), linha[1]));
        }
        return l;
    }
}
