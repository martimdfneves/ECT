import java.util.*;

public class E810 {

    static Scanner k = new Scanner(System.in);

    static Pessoa p = new Pessoa();

    public static void main(String[] args) {

        readData();

        imc();

        if (p.sexo == 'f') {

            if (p.imc < 19.1) {

                p.comment = "Abaixo do Peso";

            } else if (p.imc >= 19.1 && p.imc <= 25.8) {

                p.comment = "Peso Ideal";

            } else if (p.imc >= 25.9 && p.imc <= 27.3) {

                p.comment = "Peso um pouco Acima";

            } else if (p.imc >= 27.4 && p.imc <= 32.3) {

                p.comment = "Acima do Peso";

            } else if (p.imc > 32.3) {

                p.comment = "Obesidade";
            }
        } else if (p.sexo == 'm') {

            if (p.imc < 20.7) {

                p.comment = "Abaixo do Peso";

            } else if (p.imc >= 20.7 && p.imc <= 26.4) {

                p.comment = "Peso Ideal";

            } else if (p.imc >= 26.5 && p.imc <= 27.8) {

                p.comment = "Peso um pouco Acima";

            } else if (p.imc >= 27.9 && p.imc <= 31.1) {

                p.comment = "Acima do Peso";

            } else if (p.imc > 31.1) {

                p.comment = "Obesidade";
            }
        }

        impressor();

    }

    //modulo de leitura dos dados de uma pessoa
    public static void readData() {

        System.out.print("Nome: ");
        p.nome = k.nextLine();

        do {

            System.out.print("Sexo: ");
            p.sexo = k.next().charAt(0);

            p.sexo = Character.toLowerCase(p.sexo);

            if (p.sexo != 'f' && p.sexo != 'm') {

                System.out.println("Sexo inválido");
            }
        } while (p.sexo != 'f' && p.sexo != 'm');

        System.out.print("Altura: ");
        p.altura = k.nextDouble() / 100;

        System.out.print("Peso: ");
        p.peso = k.nextDouble();
    }

    //modulo de calculo do IMC
    public static void imc() {

        p.imc = p.peso / Math.pow(p.altura, 2);
    }

    //modulo da impressão
    public static void impressor() {

        System.out.println("|---------------------------- ----------------|");
        System.out.println("|     Cálculo do Indice de Massa Corporal     |");
        System.out.printf("| Nome: %20s                  |\n", p.nome);
        System.out.printf("| Sexo: %9s                             |\n", p.sexo == 'f' ? "Feminino" : "Masculino");
        System.out.printf("| IMC: %6.2f                                 |\n", p.imc);
        System.out.printf("| Comentário : %15s                |\n", p.comment);
        System.out.println("|---------------------------------------------|");

    }


}

class Pessoa {

    String nome;
    char sexo;
    double altura;
    double peso;
    double imc;
    String comment;
}