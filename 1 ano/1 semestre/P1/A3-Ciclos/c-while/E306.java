import static java.lang.Math.*;
import java.util.*;

public class E306 {

	public static void main(String[] args) {
		
		int num1, num2, mdc;

		Scanner k = new Scanner(System.in);

		System.out.println("Programa para calcular o mdc de dois números.");
		System.out.println("---------------------------------------------");

		System.out.print("Qual o primeiro número: ");
		num1 = k.nextInt();

		System.out.print("Qual o segundo número: ");
		num2 = k.nextInt();

		mdc = 0;

		//método de calcular
		if (num1 < num2) {
			
			mdc = num2 - num1;

			while(mdc > num1) {
				
				mdc -= num1;

			}

		} else if (num1 > num2) {
			
			mdc = num1 - num2;

			while(mdc > num2) {
				
				mdc -= num2;

			}

		} else {

			System.out.println("Os números são iguais.");

		}

		System.out.printf("O MDC de %d e %d é %d.\n\n", num1, num2, mdc);

	}
}