package aula2_2;

import java.util.*;
import java.io.*;

public class SopaDeLetras {

    private char sopa[][];
    private ArrayList<String> palavras;
    private ArrayList<String> resultado;
    private Scanner sc;
    private final int length;

    public SopaDeLetras(String file) {

        resultado = new ArrayList<String>();
        sopa = new char[80][80];

        try {

            this.sc = new Scanner(new File(file));
        } catch (FileNotFoundException e) {

            System.out.println("Ficheiro inserido n�o encontrado.\n");
            System.exit(0);
        }

        String line1 = sc.nextLine();
        int height = 0;
        length = line1.length();

        int abs;
        for (abs = 0; abs < length; abs++) {

            sopa[height][abs] = line1.charAt(abs);
        }
        height++;
        palavras = new ArrayList<String>(length);

        while (sc.hasNextLine()) {

            String linha = sc.nextLine();
            if (!linha.contains(",")) {

                for (abs = 0; abs < length; abs++) {

                    sopa[height][abs] = linha.charAt(abs);
                }
                height++;
            } else {

                for (String palavra : linha.split(", ")) {

                    palavras.add(palavra.toUpperCase());
                }
            }
        }
    }

    public void jogar() {

        int abs, ord;
        for (ord = 0; ord < length; ord++) {

            for (abs = 0; abs < length; abs++) {

                find(abs, ord);
            }
        }
    }

    private void find(int abs, int ord) {

        for (String palavra : palavras) {

            if (sopa[ord][abs] == palavra.charAt(0)) {

                Position now = new Position(abs, ord);

                if (abs + palavra.length() - 1 < length) {

                    String dir = "right";
                    if (gotWords(palavra, dir, now, new Position(abs + palavra.length(), ord))) {

                        resultado.add(palavra + "\t" + now + "\t" + dir);
                    }
                }

                if (abs - palavra.length() + 1 >= 0) {

                    String dir = "left";
                    if (gotWords(palavra, dir, now, new Position(abs - palavra.length(), ord))) {

                        resultado.add(palavra + "\t" + now + "\t" + dir);
                    }
                }

                if (ord + palavra.length() - 1 < length) {

                    String dir = "down";
                    if (gotWords(palavra, dir, now, new Position(abs, ord + palavra.length()))) {

                        resultado.add(palavra + "\t" + now + "\t" + dir);
                    }
                }

                if (ord - palavra.length() + 1 >= 0) {

                    String dir = "up";
                    if (gotWords(palavra, dir, now, new Position(abs, ord - palavra.length()))) {

                        resultado.add(palavra + "\t" + now + "\t" + dir);
                    }
                }

                if (abs + palavra.length() - 1 < length && ord + palavra.length() - 1 < length) {

                    String dir = "downright";
                    if (gotWords(palavra, dir, now, new Position(abs + palavra.length(), ord + palavra.length()))) {

                        resultado.add(palavra + "\t" + now + "\t" + dir);
                    }
                }

                if (abs + palavra.length() - 1 < length && ord - palavra.length() + 1 >= 0) {

                    String dir = "upright";
                    if (gotWords(palavra, dir, now, new Position(abs + palavra.length(), ord - palavra.length()))) {

                        resultado.add(palavra + "\t" + now + "\t" + dir);
                    }
                }

                if (abs - palavra.length() + 1 >= 0 && ord + palavra.length() - 1 < length) {

                    String dir = "downleft";
                    if (gotWords(palavra, dir, now, new Position(abs - palavra.length(), ord + palavra.length()))) {

                        resultado.add(palavra + "\t" + now + "\t" + dir);
                    }
                }

                if (abs - palavra.length() + 1 >= 0 && ord - palavra.length() + 1 >= 0) {

                    String dir = "upleft";
                    if (gotWords(palavra, dir, now, new Position(abs - palavra.length(), ord - palavra.length()))) {

                        resultado.add(palavra + "\t" + now + "\t" + dir);
                    }
                }
            }
        }
    }

    private boolean gotWords(String palavra, String dir, Position limBaixo, Position limAlto) {

        int xPlus = 0;
        int yPlus = 0;

        int x = limBaixo.getX();
        int y = limBaixo.getY();

        if (dir.contains("up")) {

            yPlus = -1;
        } else if (dir.contains("down")) {

            yPlus = 1;
        }

        if (dir.contains("left")) {

            xPlus = -1;
        } else if (dir.contains("right")) {

            xPlus = 1;
        }

        String tempWord = "";

        for (int i = 0; i < palavra.length(); i++) {

            tempWord += sopa[y][x];
            x += xPlus;
            y += yPlus;
        }

        return tempWord.equals(palavra);
    }

    public void fim() {

        for (String resultado : this.resultado) {

            System.out.println(resultado);
        }
        sc.close();
    }
}
