package aula5_3;

import java.io.*;
import java.util.*;

import aula1_2.*;

public class Nokia implements InterfaceAgenda {

    public void saveAgenda(File file, Contacto[] contactos) throws IOException {
        PrintWriter printer = new PrintWriter(file);
        printer.println("Nokia");
        for (Contacto contacto : contactos) {
            printer.println(contacto.getName());
            printer.println(contacto.getNumb());
            printer.println(contacto.getDate());
        }
        printer.close();
    }

    public ArrayList<Contacto> loadAgenda(Scanner file) throws IOException {
        ArrayList<Contacto> l = new ArrayList<Contacto>();
        String nome;
        int numb;
        String data[];
        while (file.hasNextLine()) {
            nome = file.nextLine();
            numb = Integer.parseInt(file.nextLine());
            data = file.nextLine().split("/");
            l.add(new Contacto(numb, new Data(Integer.parseInt(data[0]), Integer.parseInt(data[1]), Integer.parseInt(data[2])), nome));
            if (file.hasNext())
                file.nextLine();
        }
        return l;
    }
}
