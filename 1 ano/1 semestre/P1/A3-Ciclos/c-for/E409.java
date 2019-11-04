import java.util.*;

public class E409 {

	public static void main(String[] args) {
		
		int num, par, sum, test_num, i;

		Scanner k = new Scanner(System.in);

		System.out.println("Programa para calcular a soma dos primeiros N números pares.");

		do {

			System.out.print("Quantos números pares?(o programa só calcula até ao 1000º número par) ");
			num = k.nextInt();

		} while (num < 0 && num >= 1000);

		System.out.println("\nNúmeros pares: ");

		for (i = 1, sum = 0, test_num = 1; i <= num; test_num++) {
			
			if (test_num % 2 == 0) {
				
				System.out.printf("%3d\n", test_num);

				sum += test_num;

				i++;

			}
		}

		System.out.printf("\nA soma dos primeiros %d números pares é igual a %d.\n", num, sum);

	}
}