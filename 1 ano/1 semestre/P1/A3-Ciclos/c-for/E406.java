import java.util.*;

public class E406 {

	public static void main(String[] args) {
		
		int altura, largura;

		Scanner k = new Scanner(System.in);

		System.out.println("Programa para imprimir um retângulo de *'s com as medidas especificadas.");

		System.out.print("\nQual a altura do retângulo? ");
		altura = k.nextInt();


		System.out.print("E qual a largura do retângulo? ");
		largura = k.nextInt();

		k.close();

		System.out.println();

		for (int i = 1; i <= altura; i++) {
			
			for (int j = 1; j <= largura; j++) {
				
				System.out.print("* ");

			}

			System.out.println();
		}

		System.out.println();
	}
}