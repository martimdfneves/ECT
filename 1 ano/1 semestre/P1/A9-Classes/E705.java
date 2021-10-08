import java.util.*;

import static java.lang.Math.*;

public class E705 {

    static Scanner k = new Scanner(System.in);

    public static void main(String[] args) {

        Condutor registo = new Condutor();

        registo = leitorDeInformacao();

        impressor(registo);
    }

    //le os dados do condutor
    public static Condutor leitorDeInformacao() {

        Condutor c = new Condutor();
        char resp;

        //pede o nome
        System.out.print("Nome: ");
        c.nome = k.nextLine();

        //pede o sexo
        do {
            System.out.print("Sexo: ");
            c.sexo = k.next().charAt(0);

            if (c.sexo != 'f' && c.sexo != 'F' && c.sexo != 'm' && c.sexo != 'M') {

                System.out.print("Sexo não existente, coloque outro.\nOpções válidas: f v F (feminino) / m v M (masculino).\n");
            }

        } while (c.sexo != 'f' && c.sexo != 'F' && c.sexo != 'm' && c.sexo != 'M');

        //pede o peso
        System.out.print("Peso: ");
        c.peso = k.nextDouble();

        //pede a quantidade de bebida ingerida
        System.out.print("Bebida ingerida (ml): ");
        c.bebIng = k.nextDouble();

        //pede o teor alcoolico medio da bebida
        System.out.print("Teor alcoólico (médio, em %): ");
        c.teor = k.nextDouble();

        //pergunta se está em jejum
        do {

            System.out.print("Está em jejum? ");
            resp = k.next().charAt(0);

            if (resp != 'n' && resp != 'N' && resp != 's' && resp != 'S') {

                System.out.print("Resposta não aceitável, tente outra.\nOpções válidas: n v N (não) / s v S (sim)");
            }

        } while (resp != 'n' && resp != 'N' && resp != 's' && resp != 'S');

        return c;
    }

    //calcula a taxa de alcoolemia no sangue
    public static double tAS(Condutor c) {

        double tas = ((0.8) * c.bebIng * (c.teor / 100)) / c.peso * coef(c.sexo, c.jejum);

        return tas;
    }

    //define o coeficiente de alcool no sangue
    public static double coef(char sex, boolean com) {

        double coef = 0;

        if (com) {

            if (sex == 'm' || sex == 'M') {

                coef = 0.7;

            } else if (sex == 'f' || sex == 'F') {

                coef = 0.6;

            }
        } else if (!com) {

            coef = 1.1;
        }

        return coef;
    }

    //imprime os resultados
    public static void impressor(Condutor c) {

        if (c.sexo == 'm' || c.sexo == 'M') {

            System.out.printf("O condutor %s tem uma taxa de alcoolemia no sangue de %4.2f g/ml.\n", c.nome, tAS(c));

        } else if (c.sexo == 'f' || c.sexo == 'F') {

            System.out.printf("A condutora %s tem uma taxa de alcoolemia no sangue de %4.2f g/ml.\n", c.nome, tAS(c));
        }

    }
}

class Condutor {

    String nome;
    char sexo;
    double peso;
    double bebIng;
    double teor;
    boolean jejum;
}