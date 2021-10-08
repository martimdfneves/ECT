import java.util.*;

public class E412 {

    public static void main(String[] args) {

        Scanner k = new Scanner(System.in);

        int ano, mes, weekday, dias, j, i;

        System.out.println("Programa para mostrar o calendário com base no ano, mês e dia da semana colocado.");

        System.out.print("\nQual é o ano? ");
        ano = k.nextInt();

        dias = 1;

        do {

            System.out.print("\nQual é o mês? ");
            mes = k.nextInt();

            if (mes > 12 || mes < 1) {

                System.out.print("\nMês não aceite. Coloca outro.");
            }

        } while (mes > 12 || mes < 1);

        do {

            System.out.print("\nColoca o número do dia da semana pelo qual o mês começa(Domingo = 1/.../Sábado = 7): ");
            weekday = k.nextInt();

            if (weekday > 7 || weekday < 1) {

                System.out.print("\nDia da semana não aceite. Coloca outro.");

            }

        } while (weekday > 7 || weekday < 0);

        System.out.print("\n\n|------------------------------------------|\n");

        switch (mes) {

            case 1:

                System.out.printf("|             JANEIRO   %4d               |\n", ano);
                dias = 31;
                break;

            case 2:

                System.out.printf("|             FEVEREIRO   %4d             |\n", ano);

                if (ano % 4 == 0 && ano % 100 == 0 || ano % 400 == 0) {

                    dias = 29;

                } else {

                    dias = 28;
                }

                break;

            case 3:

                System.out.printf("|              MARÇO   %4d                |\n", ano);
                dias = 31;
                break;

            case 4:

                System.out.printf("|              ABRIL   %4d                |\n", ano);
                dias = 30;
                break;

            case 5:

                System.out.printf("|               MAIO   %4d                |\n", ano);
                dias = 31;
                break;

            case 6:

                System.out.printf("|              JUNHO   %4d                |\n", ano);
                dias = 30;
                break;

            case 7:

                System.out.printf("|              JULHO   %4d                |\n", ano);
                dias = 31;
                break;

            case 8:

                System.out.printf("|             AGOSTO   %4d                |\n", ano);
                dias = 31;
                break;

            case 9:

                System.out.printf("|            SETEMBRO   %4d               |\n", ano);
                dias = 30;
                break;

            case 10:

                System.out.printf("|             OUTUBRO   %4d               |\n", ano);
                dias = 31;
                break;

            case 11:

                System.out.printf("|            NOVEMBRO   %4d               |\n", ano);
                dias = 30;
                break;

            case 12:

                System.out.printf("|            DEZEMBRO   %4d               |\n", ano);
                dias = 31;
                break;

        }

        System.out.print("|------------------------------------------|\n");
        System.out.print("|   Do   Se   Ter   Qua   Qui   Sex   Sá   |\n");
        System.out.print("|------------------------------------------|\n");

        for (i = 1, j = weekday; i <= dias; i++) {

            if (i != dias && i != 1) {

                if (j % 7 == 1) {

                    System.out.printf("|  %2d ", i);

                } else if (j % 7 == 0) {

                    System.out.printf("  %2d   |\n", i);

                } else {

                    System.out.printf("  %2d  ", i);

                }

            } else if (i == 1) {

                switch (j % 7) {

                    case 0:

                        System.out.printf("|\t\t\t\t      %2d   |\n", i);
                        break;

                    case 1:

                        System.out.printf("|  %2d ", i);
                        break;

                    case 2:

                        System.out.printf("|       %2d  ", i);
                        break;

                    case 3:

                        System.out.printf("|             %2d  ", i);
                        break;

                    case 4:

                        System.out.printf("|                   %2d  ", i);
                        break;

                    case 5:

                        System.out.printf("|                       	  %2d  ", i);
                        break;

                    case 6:

                        System.out.printf("|                               %2d  ", i);
                }

            } else if (i == dias) {

                switch (j % 7) {

                    case 0:

                        System.out.printf("  %2d   |\n", i);
                        break;

                    case 1:

                        System.out.printf("|  %2d\t\t\t\t\t   |\n", i);
                        break;

                    case 2:

                        System.out.printf("  %2d      \t\t\t   |\n", i);
                        break;

                    case 3:

                        System.out.printf("  %2d     \t\t\t   |\n", i);
                        break;

                    case 4:

                        System.out.printf("  %2d                     |\n", i);
                        break;

                    case 5:

                        System.out.printf("  %2d               |\n", i);
                        break;

                    case 6:

                        System.out.printf("  %2d         |\n", i);
                }

            }

            j++;
        }

        System.out.println("|------------------------------------------|\n");
    }
}
