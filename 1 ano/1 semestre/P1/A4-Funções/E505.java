import java.util.*;

public class E505 {

	static Scanner k = new Scanner(System.in);

	public static void main(String[] args) {
		
		int colunas, linhas;

		System.out.println("Programa para imprimir uma moldura com as medidas especificadas");

		System.out.println("Coloca as dimensões que queres:");
		
		System.out.print("Largura: ");
		colunas = k.nextInt();

		System.out.print("Altura: ");
		linhas = k.nextInt();

		linhas_cheias(colunas, linhas);
	}

	//imprime a moldura
	public static void linhas_cheias (int colunas, int linhas) {

		for (int i = 1; i <= linhas; ++i) {
			
			if (i == 1 || i == linhas) {

				for (int j = 1; j <= colunas; ++j) {
					
					System.out.print("* ");

					if (j == colunas) {
						
						System.out.println();
					}
				}
			} else {
				
				for (int j = 1; j <= colunas; ++j) {
					
					if (j == 1) {
				
						System.out.print("* ");
					
					} else if (j == colunas) {
						
						System.out.println("* ");
					} else {
						
						System.out.print("  ");
					}
				}
			}
		}
	}
}