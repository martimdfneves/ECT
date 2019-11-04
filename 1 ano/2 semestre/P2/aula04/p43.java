import static java.lang.System.*;
import number.Fraction; 


public class p43 {
 
  public static void main(String[] args) {
    if (args.length != 3) {
      err.println("Uso: java -ea p43 <fraction1> <operation> <fraction2>");
      exit(1);
    }

    String frac1 = args[0];
    String op = args[1];
    String frac2 = args[2];

    Fraction x;
    Fraction y;

 
    x = Fraction.parseFraction( frac1 );
    y = Fraction.parseFraction( frac2 );

    String result = "";
  
    switch (op) {
      case "+":
        result += x.add(y);  
        break;
      case "*":
      case "x":
        result += x.multiply(y); break;
      case "-":
        result += x.subtract(y); break;
      case "/":
        result += x.divide(y); break;
      case "=":
        result += x.equals(y); break;
      case "<":
        
      default:
        out.println("Operação inválida.");
        System.exit(1);
    }

   
    out.printf("%s %s %s = %s\n", x, op, y, result);
  }
}
