import java.util.*;

public class E411 {

    public static void main(String[] args) {

        System.out.println("Programa para gerar as coordenadas de um quadro de xadrez.\n\n");

        //olha o quadrozinho
        for (int num = 8; num >= 1; num--) {

            for (int i = 0; i < 8; i++) {

                char letra = (char) ('a' + i);
                System.out.printf("%c%d ", letra, num);
            }

            System.out.println();
        }

        System.out.println();

    }
}