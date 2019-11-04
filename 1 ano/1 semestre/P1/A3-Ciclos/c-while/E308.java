import java.util.*;

public class E308 {

	public static void main(String[] args) {
		
		int nota, media, num_nots;

		Scanner k = new Scanner(System.in);

		System.out.println("Programa para calcular a média da notas de um aluno.");
		System.out.println("----------------------------------------------------");
		System.out.println("Nota: Para terminar o programa coloque uma nota negativa.");

		media = 0;
		num_nots = 0;
		nota = 0;

		//bruh
		while (nota >= 0) {
			
			System.out.print("Nota? ");
			nota = k.nextInt();

			if (nota < 0) {
				
				break;

			}

			media += nota;

			num_nots++;

		}

		System.out.printf("Soma = %d\n", media);
		System.out.printf("Media = %4.2f\n", (double)media / num_nots);

	}
}