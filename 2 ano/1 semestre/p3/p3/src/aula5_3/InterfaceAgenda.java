package aula5_3;

import java.io.*;
import java.util.*;

public interface InterfaceAgenda {

    public void saveAgenda(File file, Contacto[] p) throws IOException;

    public ArrayList<Contacto> loadAgenda(Scanner file) throws IOException;
}
