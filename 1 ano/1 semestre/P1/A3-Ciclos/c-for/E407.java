import java.util.*;

public class E407 {

	public static void main(String[] args) {
		
		int altura, largura;

		Scanner k = new Scanner(System.in);

		System.out.println("Programa para imprimir uma Moldura com as medidas específicadas");
		System.out.println();

		System.out.print("Qual a altura da moldura? ");
		altura = k.nextInt();

		System.out.print("Qual a largura da moldura? ");
		largura = k.nextInt();

		for (int i = 1; i <= altura; i++) {
			
			if (i == 1 || i == altura) {
				
				for (int j = 1; j <= largura; j++) {
					
					System.out.print("* ");

				}

				System.out.println();

			} else {

				for (int j = 1; j <= largura; j++) {
					
					if (j == 1 || j == largura) {
						
						System.out.print("* ");

					} else {

						System.out.print("  ");

					}
				}

				System.out.println();

			}
		}
	}
}