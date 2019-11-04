import java.util.*;

public class E1002 {

	static Scanner k = new Scanner(System.in);

	public static void main(String[] args) {
		
		int[] chave = new int[6];

		chave = lerChave(chave);

		mostraChave(chave);
	}

	//modulo de leitura da chave
	public static int[] lerChave(int[] chave) {

		for (int i = 0; i < chave.length; i++) {
			
			do{
				System.out.printf("Elemento %d da chave: ", i + 1);
				chave[i] = k.nextInt();

				if ((chave[i] > 49 || chave[i] < 1) || pertenceChave(chave, i)) {
					
					System.out.println("Elemento não aceite, coloque outro.");
				}
			}while(chave[i] > 49 || chave[i] < 1 || pertenceChave(chave, i));
		}

		return chave;
	}

	//modulo de teste de verificação se um numero pertence ou não à chave
	public static boolean pertenceChave (int[] chave, int fix){

		boolean pertence = false;

		for (int i = 0; i < fix; i++) {
			
			if (chave[i] == chave[fix]) {
				
				pertence = true;
			}
		}
		return pertence;
	}

	//modulo para imprimir a chave
	public static void mostraChave(int[] chave) {

		System.out.println("\n\t\tAposta do totoloto:");

		int j = 0;
		for (int i = 0; i < 49; i++) {
			
			if (i % 7 == 0) {
				
				System.out.println();
			}

			if (j < 6) {
				
				if (chave[j] == i + 1) {
					
					System.out.print("X\t");
					j++;
				
				} else {

					System.out.printf("%2d\t", i + 1);
				} 
			} else {
				System.out.printf("%2d\t", i+1);
			}
		}

		System.out.println();
	}
}