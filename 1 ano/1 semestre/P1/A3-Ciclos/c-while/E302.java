import static java.lang.System.*;
import static java.lang.Math.*;
import java.util.*;

public class E302 {

  public static void main(String[] args) {

    double nums, total;

    Scanner k = new Scanner(in);

    System.out.println("Programa para calcular o produto de uma lista.");
    System.out.println("----------------------------------------------");
    System.out.println("Coloca os números, quando terminar coloque 0.");

    nums = total = 1;

    //cálculo da lista à medida que esta é pedida
    do {
    
      total *= nums;
      nums = k.nextDouble();

    } while (nums != 0);

    System.out.println("total = " + total);

  }
}
