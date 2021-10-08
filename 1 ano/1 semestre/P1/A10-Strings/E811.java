import java.util.*;

public class E811 {

    static Indiv pes = new Indiv();
    static Scanner k = new Scanner(System.in);
    static double preco;
    static double insc = 0;
    static double mens = 0;

    public static void main(String[] args) {

        readData();

        pagamento();

        impressor();
    }

    //modulo para obtenção dos dados do individuo
    public static void readData() {

        System.out.print("Nome: ");
        pes.nome = k.nextLine();

        char temp;

        do {
            System.out.print("Primeira vez? (s/n) ");
            temp = k.nextLine().charAt(0);

            temp = Character.toLowerCase(temp);

            if (temp != 's' && temp != 'n') {

                System.out.println("Resposta não aceite, coloque outra");
            }

            if (temp == 's') {

                pes.priVez = true;

            } else if (temp == 'n') {

                pes.priVez = false;
            }
        } while (temp != 's' && temp != 'n');

        System.out.println("Modalidades:\n\t1-> Iniciação\n\t2-> Aperfeiçoamento");

        do {
            System.out.print("Modalidade: ");
            pes.mod = k.nextInt();

            if (pes.mod != 2 && pes.mod != 1) {

                System.out.println("Modalidade não aceitável, coloque outra.");
            }
        } while (pes.mod != 2 && pes.mod != 1);

        System.out.println("Horas por semana: 1h - 10h");

        do {
            System.out.print("Horas: ");
            pes.horas = k.nextInt();

            if (pes.horas < 1 || pes.horas > 10) {

                System.out.println("Horário não aceitável, coloque outro");
            }
        } while (pes.horas < 1 || pes.horas > 10);

        System.out.print("Tem quantos familiares inscritos? ");
        pes.fam = k.nextInt();
    }

    //modulo de calculo do pagamento
    public static void pagamento() {

        if (pes.priVez) {

            insc = 50;

        } else if (!pes.priVez) {

            insc = 30;
        }

        if (pes.mod == 1) {

            mens = pes.horas * 16;

        } else if (pes.mod == 2) {

            mens = pes.horas * 24;
        }

        if (pes.fam == 1) {

            mens *= 0.9;

        } else if (pes.fam >= 2) {

            mens *= 0.75;

        } else if (pes.fam == 0) {

            mens = mens;
        }

        preco = mens + insc;
    }

    //modulo de impressao da fatura
    public static void impressor() {

        System.out.println("Nova Inscrição para o clube de Natação");
        System.out.printf("Nome: %s\n", pes.nome);
        System.out.printf("%s\n", pes.priVez ? "Primeira Vez" : "Já Inscito");
        System.out.printf("%d %s de %s\n", pes.horas, pes.horas == 1 ? "Hora" : "Horas", pes.mod == 1 ? "Iniciação" : "Aperfeiçoamento");
        System.out.printf("%d %s\n", pes.fam, pes.fam == 1 ? "Familiar" : "Familiares");
        System.out.println("-------------------------------------------------------------");
        System.out.println("Item\t\t\tQuantidade\t\t\tValor");
        System.out.printf("Inscrição\t\t\t1\t\t\t%5.2f\n", insc);
        System.out.printf("Mensalidade\t\t\t1\t\t\t%5.2f\n", mens);
        System.out.println("-------------------------------------------------------------");
        System.out.printf("Total\t\t\t\t\t\t\t%5.2f\n", preco);


    }
}

class Indiv {

    String nome;
    int mod;
    int horas;
    int fam;
    boolean priVez;
}