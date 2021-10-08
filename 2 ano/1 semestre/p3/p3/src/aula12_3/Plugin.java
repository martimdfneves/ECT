package aula12_3;

import java.io.IOException;

public interface Plugin {

    public Pessoa[] readFromFile(String filename) throws IOException;

    public void writeToFile(String filename, Pessoa[] pessoas) throws IOException;

}