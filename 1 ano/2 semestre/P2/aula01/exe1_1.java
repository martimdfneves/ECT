import java.util.*;

public class exe1_1 {
	
	public static void main (String[] args) {
		
		Scanner sc = new Scanner(System.in).useLocale(Locale.US);
		double num1, num2;
		String op;
		
		System.out.print("Insira a operação: ");
		
		num1=sc.nextDouble();
		op=sc.next();
		num2=sc.nextDouble();
		
		switch(op){
		
		case "+":
			System.out.printf("%3.2f + %3.2f = %3.2f", num1, num2, num1+num2);
			break;

        case "-":
			System.out.printf("%3.2f + %3.2f = %3.2f", num1, num2, num1-num2);
			break;
			
		case "*":
			System.out.printf("%3.2f + %3.2f = %3.2f", num1, num2, num1*num2);
			break;
			
		case "/":
			System.out.printf("%3.2f + %3.2f = %3.2f", num1, num2, num1/num2);
			break;
		}
	}
}
