import static java.lang.System.*;
import static java.lang.Math.*;
import java.util.Scanner;

public class E208 {

  //declaração do teclado
  static Scanner k = new Scanner(in);

  public static void main(String[] args) {
    
    //declaração das variáveis
    double num1, num2, num3;

    System.out.println("Coloca 3 números inteiros aleatórios:");

    System.out.print("Num 1:");
    num1 = k.nextDouble();

    System.out.print("Num 2:");
    num2 = k.nextDouble();

    System.out.print("Num 3:");
    num3 = k.nextDouble();

    //sequência de testes para organização dos números
    if (num1 < num2 && num1 < num3) {
    
      System.out.printf("%3.0f\n", num1);

      if (num2 < num3) {
    
        System.out.printf("%3.0f\n%3.0f\n", num2, num3);
    
      } else if (num3 < num2) {
    
        System.out.printf("%3.0f\n%3.0f\n", num3, num2);
    
      }
    
    } else if (num2 < num1 && num2 < num3) {
    
      System.out.printf("%3.0f\n", num2);

      if (num1 < num3) {
    
        System.out.printf("%3.0f\n%3.0f\n", num1, num3);
    
      } else if (num3 < num1) {
    
        System.out.printf("%3.0f\n%3.0f\n", num3, num1);
    
      }
    
    } else if (num3 < num2 && num3 < num1) {
    
      System.out.printf("%3.0f\n", num3);

      if (num1 < num2) {
    
        System.out.printf("%3.0f\n%3.0f\n", num1, num2);
    
      } else if (num2 < num1) {
    
        System.out.printf("%3.0f\n%3.0f\n", num2, num1);
    
      }
    
    }
  
  }

}
