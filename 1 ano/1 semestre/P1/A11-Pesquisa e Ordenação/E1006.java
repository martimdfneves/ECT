import java.util.*;
import static java.lang.Math.*;

public class E1006{

	static Scanner k = new Scanner(System.in);

	public static void main(String[] args) {
		
		long chave = 0;
		chave = lerchave();
		printKey(chave);

	}

	//modulo de leitura da chave
	public static long lerchave() {

		int a;
		long chave = (long) pow(2, 51);
		boolean[] pert = new boolean[50];

		for (int i = 0; i < 50; i++) {
			
			pert[i] = false;
		}

		for (int i = 0; i < 6; i++) {
		
			System.out.printf("Número %d: ", i+1);
			a = k.nextInt();
			
			while (a > 49 || a < 1 || pertenceChave(pert, a)) {
			
				System.out.print("\nJá existe na chave, ou excede os limites.\nColoque outra: ");
				a = k.nextInt();

			}

			pert[a] = true;
			chave += pow(2, a-1); 
		}

		return chave;
	}

	//verificação da chave
	public static boolean pertenceChave(boolean[] pert, int a){

		boolean volta = false;

		if (pert[a]) {
			
			volta = true;
		
		} else if (!pert[a]) {
		
			volta = false;
			pert[a-1] = true;	
		}

		return volta;
	}

	//imprime a chave
	public static void printKey(long chave) {

		String longas = (Long.toBinaryString(chave));

		System.out.println("\nChave da aposta: ");

		for (int i = 0; i < 49; i++) {
			
			//faz nova linha
			if (i % 7 == 0) {
				System.out.println();
			}

			//se pertencer imprime
			if (longas.charAt(51-i) == '1') {
				
				System.out.print("X\t");
			
			} else {

				System.out.printf("%d\t", i+1);
			}
		}

		System.out.println();
	}
}