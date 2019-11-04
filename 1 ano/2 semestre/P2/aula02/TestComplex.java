import static java.lang.System.*;
import java.util.*;

public class TestComplex {
  // Exemplo simples de utilização da class Complex
  public static void main(String[] args) {
	  
	Scanner sc = new Scanner(System.in);
	  
	double x=0, y=0;  
	  
	System.out.print("Digite o valor de x: ");  
	x=sc.nextDouble();
	System.out.print("Digite o valor de y: ");  
	y=sc.nextDouble();
    Complex a = new Complex(x, y);

    // Vamos usar métodos do objeto a
    out.println("(" + a.real() + " + " + a.imag() + "i)");
    out.println("  parte real = " + a.real());
    out.println("  parte imaginaria = " + a.imag());
    out.println("  modulo = " + a.abs());
    out.printf("  argumento =  %2.2f\n", a.arg());
  }

}
