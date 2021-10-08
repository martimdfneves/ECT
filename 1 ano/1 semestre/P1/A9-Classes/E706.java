import java.util.*;

import static java.lang.Math.*;

public class E706 {

    public static void main(String[] args) {

        double maxAmp = 0;
        int maxDay = 0;
        Temp[] temps = new Temp[5];

        for (int i = 0; i < 5; i++) {

            temps[i] = readTemp(i);
            temps[i].ampT = temps[i].maxT - temps[i].minT;

            if (temps[i].ampT > maxAmp) {

                maxAmp = temps[i].ampT;
                maxDay = i + 1;
            }
        }

        System.out.printf("A maior amplitude térmica foi de %4.2fºC, e ocorreu no dia %d.\n", maxAmp, maxDay);
    }

    //modulo de leitura das temperaturas
    public static Temp readTemp(int i) {

        Temp l = new Temp();

        Scanner k = new Scanner(System.in);

        do {

            //pede temperatura minima
            System.out.printf("Temperatura mínima do dia %d: ", i + 1);
            l.minT = k.nextDouble();

            //pede temperatura maxima
            System.out.printf("Temperatura maxima do dia %d: ", i + 1);
            l.maxT = k.nextDouble();

            //verificador pra ver se está tudo correto
            if (l.maxT <= l.minT || l.minT < -20 || l.maxT > 50) {

                System.out.print("Temperaturas não aceitáveis, coloque outras.\n\n");
            }

        } while (l.maxT <= l.minT || l.minT < -20 || l.maxT > 50);

        return l;

    }
}

class Temp {

    double minT;
    double maxT;
    double ampT;
}