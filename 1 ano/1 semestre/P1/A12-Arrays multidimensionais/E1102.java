import java.util.*;

import static java.lang.Math.*;

public class E1102 {

    static Scanner k = new Scanner(System.in);

    public static void main(String[] args) {

        //só exite pra dar valor ao array
        int contador = 123123123;

        //contador de pontos
        int counter;

        //define os pontos
        Ponto[] pont = new Ponto[contador];
        Ponto maisDist = new Ponto();

        //variaveis pra calcular a distancia
        double maxDist = 0;
        double distSum = 0;

        System.out.println("Quando colocar nas coordenadas (0,0) o programa acaba.");


        for (counter = 1; ; counter++) {

            pont[counter - 1] = lerPonto(counter);

            if (pont[counter - 1].x == 0 && pont[counter - 1].y == 0) {
                break;
            }
        }

        for (int i = 0; i < counter; i++) {

            pont[i].dist = dist(pont[i]);

            distSum += pont[i].dist;

            if (pont[i].dist > maxDist) {

                maisDist = pont[i];
                maxDist = pont[i].dist;

            }
        }

        pont = ordemCresc(pont, counter);

        System.out.printf("\n\nA soma das distâncias dos %3d pontos à origem é %5.2f.\n", counter, distSum);
        System.out.print("O ponto mais afastado da origram foi ");
        printPont(maisDist);
        System.out.println("Impressão dos pontos por ordem de afastamento:");

        for (int j = 1; j < counter; j++) {

            System.out.print("Ponto %d: ");
            printPont(pont[j]);
        }

    }

    //modulo de leitura do ponto
    public static Ponto lerPonto(int n) {

        Ponto p = new Ponto();

        System.out.printf("Quais as coordenadas do ponto %d?\n", n);

        System.out.print("Abcissa: ");
        p.x = k.nextDouble();

        System.out.print("Ordenada: ");
        p.y = k.nextDouble();

        return p;
    }

    //modulo da escrita das coordenadas de um ponto
    public static void printPont(Ponto n) {

        System.out.printf("(%4.2f, %4.2f)\n", n.x, n.y);
    }

    //modulo de calculo das distancias
    public static double dist(Ponto p) {

        double dist = sqrt(pow(p.x, 2) + pow(p.y, 2));

        return dist;
    }

    //modulo de organização dos pontos
    public static Ponto[] ordemCresc(Ponto[] numeros, int counter) {

        boolean swap;
        Ponto tmp = new Ponto();

        do {
            swap = false;

            for (int i = 0; i + 1 < counter; i++) {

                if (numeros[i].dist > numeros[i + 1].dist) {

                    tmp = numeros[i];
                    numeros[i] = numeros[i + 1];
                    numeros[i + 1] = tmp;
                    swap = true;
                }
            }
        } while (swap);

        return numeros;
    }
}

class Ponto {

    double x;
    double y;
    double dist;
}