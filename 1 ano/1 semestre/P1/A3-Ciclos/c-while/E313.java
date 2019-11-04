import java.util.*;

public class E313 {

	public static void main(String[] args) {
		
		int number, revertido, init;

		Scanner k = new Scanner(System.in);

		System.out.println("Programa para reverter um número.");
		System.out.println("---------------------------------");


		System.out.print("Coloca o teu número: ");
		number = k.nextInt();

		if (number < 0) {
			 
			System.out.printf("Coloca um número maior ou igual a 0.\n");
			System.out.print("Coloca o teu número: ");
			number = k.nextInt();

		}

		init = number;

		revertido = 0;

		//método de inverter números
		while(number != 0) {

			revertido *= 10;
			revertido = revertido + number % 10;
			number /= 10;

		}

		System.out.printf("O número revertido de %d é %d.\n\n", init, revertido);

	}
}