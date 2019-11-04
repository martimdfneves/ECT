import java.util.*;

public class E408 {

	public static void main(String[] args) {
		
		Scanner k = new Scanner(System.in);

		int minval, maxval, j;

		System.out.println("Programa para imprimir os números impares num dado intervalo.");
		System.out.println();


		do {

			System.out.print("Limitante menor do intervalo: ");
			minval = k.nextInt();

			System.out.print("Limitante maior do intervalo: ");
			maxval = k.nextInt();

			if (minval >= maxval || minval <= 0) {
				
				System.out.println("Intervalo não aceite, coloque outro intervalo.\n");

			}
		} while (minval >= maxval || minval <= 0);

		System.out.println();

		System.out.printf("Números pertencentes ao intervalo [%3d, %3d]:\n", minval, maxval);

		j = 1;

		for (int i = minval; i <= maxval; i++) {

			if (i % 2 != 0) {
					
				System.out.printf("Ímpar #%2d: %3d\n", j, i);
				j++;
	
			}
		}
	}
}
