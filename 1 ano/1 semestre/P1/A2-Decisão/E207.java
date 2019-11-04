import static java.lang.System.*;
import static java.lang.Math.*;
import java.util.*;

public class E207 {

  //declaração do teclado
  static Scanner k = new Scanner(in);

  public static void main(String[] args) {
    
    //declaração das variáveis
    double num1, num2, num3;

    System.out.println("Algoritmo tipo bubblesort.");
    System.out.println("--------------------------");
    System.out.println("Coloca 3 números inteiros aleatórios:");

    System.out.print("Num 1:");
    num1 = k.nextDouble();

    System.out.print("Num 2:");
    num2 = k.nextDouble();

    System.out.print("Num 3:");
    num3 = k.nextDouble();

    //sequência de testes não rentável mas únic possível neste cas
    if (num1 > num2 && num1 > num3) {

      System.out.printf("O número %.0f é o maior dos três números colocados.\n", num1);

    } else if (num2 > num1 && num2 > num3) {

      System.out.printf("O número %.0f é o maior dos três números colocados.\n", num2);

    } else if (num3 > num1 && num3 > num2) {

      System.out.printf("O número %.0f é o maior dos três números colocados.\n", num3);

    } else {

      System.out.println("Não dá para definir um número mais alto dos números colocados.");

    }

  }
}
