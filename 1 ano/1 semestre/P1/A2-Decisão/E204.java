import java.util.*;

public class E204 {

  //declaração do teclado
  static Scanner k = new Scanner(System.in);

  public static void main(String[] args) {

    //declaração das variáveis
    int mes, ano;

    System.out.println("Programa para saber quantos dias tem o mês A no ano X");
    System.out.println("\nNota: Coloque o número do mês.\n");

    System.out.println("Qual o ano que queres saber?");
    ano = k.nextInt();

    System.out.println("Qual é o mês que queres saber?");
    mes = k.nextInt();

    k.close();

    if (ano % 4 == 0 && ano % 100 == 0) {

      System.out.println("O ano " + ano + " é bissexto.");

      switch (mes) {

        case 1:
        case 3:
        case 5:
        case 7:
        case 8:
        case 10:
        case 12:

          System.out.println("O mês " + mes + " do ano " + ano +" tem 31 dias");
          break;

        case 4:
        case 6:
        case 9:
        case 11:

          System.out.println("O mês " + mes + " do ano " + ano + " tem 30 dias.");
          break;

        case 2:

          System.out.println("O mês " + mes + " do ano " + ano + " tem 29 dias.");
          break;

        default:

          System.out.println("Esse mês não existe.");
          break;

      }

    } else {

      System.out.println("O ano " + ano + " não é bissexto.");

        switch (mes) {

          case 1:
          case 3:
          case 5:
          case 7:
          case 8:
          case 10:
          case 11:
    
            System.out.println("O mês " + mes + " do ano " + ano +" tem 31 dias");
            break;

          case 4:
          case 6:
          case 9:
          case 12:
    
            System.out.println("O mês " + mes + " do ano " + ano + " tem 30 dias.");
            break;

          case 2:

            System.out.println("O mês " + mes + " do ano " + ano + " tem 28 dias.");
            break;

          default:

            System.out.println("Esse mês não existe.");
            break;

        }

    }

  }

}
