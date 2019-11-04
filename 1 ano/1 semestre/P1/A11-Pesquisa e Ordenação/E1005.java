import java.util.*;

public class E1005{
	
	static Scanner k = new Scanner(System.in);
	static boolean[] pert = new boolean[50];

	public static void main(String[] args) {
		
		lerchave();
		printKey();

	}

	//modulo de leitura da chave
	public static void lerchave() {

		int i = 0;
		int a;

		for (int j = 0; j < 50; j++) {
			pert[i] = false;
		}

		do{
			System.out.printf("Número %d: ", i+1);
			a = k.nextInt();

			i++;

			if (a > 49 || a < 1 || pertenceChave(a)) {
				System.out.println("Já existe na chave, ou excede os limites");
				i--;
			}

		}while((a > 49 || a < 1 || pertenceChave(a)) && i<6);
	}

	//verificação da chave
	public static boolean pertenceChave(int a){

		boolean volta = false;

		if (pert[a-1]) {
			
			volta = true;
		
		} else if (!pert[a]) {
		
			volta = false;
			pert[a-1] = true;	
		}

		return volta;
	}

	//imprime a chave
	public static void printKey() {

		System.out.println();

		for (int i = 0; i < 50; i++) {
			
			System.out.printf("|%2d", i+1);
		}

		System.out.println();

		for (int i = 0; i < 50; i++) {
			
			if (pert[i]) {
			
				System.out.print("|T ");
			
			} else if (!pert[i]) {
				
				System.out.print("|  ");
			} 
		}

		System.out.println();
	}
}