import static java.lang.System.*;
import static java.lang.Math.*;

import java.util.*;

public class E205 {

    //declaração do teclado
    static Scanner k = new Scanner(in);

    public static void main(String[] args) {

        //declaração das variáveis
        int p1x, p1y, p2x, p2y, p3x, p3y, p4x, p4y;
        double ladA, ladB, ladC;
        double angle1, angle2;

        System.out.println("Programa para saber se 4 pontos podem formar um quadrado.");
        System.out.println("---------------------------------------------------------");
        System.out.println("\nColoca as coordenadas de cada ponto:");

        System.out.print("p1(x): ");
        p1x = k.nextInt();
        System.out.print("p1(y): ");
        p1y = k.nextInt();


        System.out.print("p2(x): ");
        p2x = k.nextInt();
        System.out.print("p2(y): ");
        p2y = k.nextInt();

        System.out.print("p3(x): ");
        p3x = k.nextInt();
        System.out.print("p3(y): ");
        p3y = k.nextInt();

        System.out.print("p4(x): ");
        p4x = k.nextInt();
        System.out.print("p4(y): ");
        p4y = k.nextInt();

        ladA = sqrt(pow(p2x - p1x, 2) + pow(p2y - p1y, 2));
        ladB = sqrt(pow(p4x - p1x, 2) + pow(p4y - p1y, 2));
        ladC = sqrt(pow(p3x - p2x, 2) + pow(p3y - p2y, 2));

        angle1 = ladA / ladB;
        angle2 = ladC / ladB;

        if (ladA == ladB && ladA == ladC && angle2 == 1 && angle2 == 1) {

            System.out.println("Os pontos formam uma figura geométrica com a forma de um quadrado.\n");

        } else {

            System.out.println("Os pontos não forma uma figura geométrica com a forma de um quadrado.\n");

        }

    }

}
