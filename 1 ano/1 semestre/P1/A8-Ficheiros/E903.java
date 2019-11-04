import java.util.*;
import java.io.*;

public class E903 {

	static Scanner k = new Scanner(System.in);

	public static void main(String[] args) throws IOException{

		//array dos números
		int numeros[] = new int[50];
		
		//variável da escolha
		int escolha;

		//colocara lista a zero
		for (int i = 0; i < 50; i++) {
			
			numeros[i] = 0;
		}

		System.out.println("Programa para escolher o que fazer a uma sequência.");
		System.out.println("---------------------------------------------------");
		
		System.out.println("\nAnálise de uma sequência de números inteiros:");
		System.out.println("\t 1 -> Ler a sequência, se colocar 0 termina o programa");
		System.out.println("\t 2 -> Escrever a sequência");
		System.out.println("\t 3 -> Calcular o máximo desta sucessão.");
		System.out.println("\t 4 -> Calcular o mínimo desta sucessão.");
		System.out.println("\t 5 -> Calcular a média desta sucessão.");
		System.out.println("\t 6 -> Detetar se é uma sequência só constituída por números pares.");
		System.out.println("\t 7 -> Ler uma sequência de números de um ficheiro de texto.");
		System.out.println("\t 8 -> Adicionar números à sequência existente.");
		System.out.println("\t 9 -> Gravar a sequência num ficheiro.");
		System.out.println("\t10 -> Terminar o programa.");
		
		//o que acontece em cada escolha
		do {

			System.out.print("\nOpção: ");
			escolha = k.nextInt();


			switch (escolha) {

				case 10:

					break;

				case 1:

					numeros = leitor();
					break;

				case 2:

					escritor(numeros);
					break;

				case 3:

					maximizante(numeros);
					break;

				case 4:

					minimizante(numeros);
					break;

				case 5:

					media(numeros);
					break;

				case 6:

					pares(numeros);
					break;

				case 7:

					numeros = readFile();
					break;

				case 8:

					numeros = addNums(numeros);
					break;

				case 9:

					stampFile(numeros);
					break;

				default:

					System.out.println("Escolha não aceite coloca outra.");
					System.out.print("\nOpção: ");
					escolha = k.nextInt();

			}

		} while(escolha != 10);

	}

	//modulo de leitura da lista
	public static int[] leitor() {

		int numeros[] = new int[50];

		for (int i = 0; i < 50; i++) {

			System.out.printf("valor #%2d: ", i + 1);
			numeros[i] = k.nextInt();

			if (numeros[i] == 0) {
							
				break;
			
			}
		}

		return numeros;
	}

	//modulo de impressao da lista
	public static void escritor(int[] numeros) {

		for (int i = 0; i < 50; i++) {
			
			if (numeros[i] == 0) {
				
				break;

			}

			System.out.printf("\nValor #%2d: %3d",i + 1, numeros[i]);
			
		}

		System.out.println();
	}

	//modulo de calculo do maximizante
	public static void maximizante (int[] numeros) {
		
		int max = 0;

		for (int i = 0; i < 50; i++) {
			
			if (numeros[i] > max) {
				
				max = numeros[i];

			}
		}

		System.out.printf("\nO máximo desta sequência é %3d.\n",max);
	}

	//modulo de calculo do minimizante
	public static void minimizante (int[] numeros) {
		
		int min = 12431234;

		for (int i = 0; i < 50; i++) {
			
			if (numeros[i] == 0) {
				
				break;

			}

			if (numeros[i] < min) {
				
				min = numeros[i];

			}
		}

		System.out.printf("\nO minimo desta sequência é %3d.\n",min);
	}

	//modulo de calculo da media
	public static void media (int[] numeros) {

		double media = 0;

		int i;

		for (i = 0; i < 50; i++) {
			
			if (numeros[i] == 0) {
				
				break;

			}

			media += numeros[i];

		}

		media /= i;

		System.out.printf("\nA média da sequência é %4.2f\n", media);
	}

	//modulo pra calcular se a lista é apenas composta por números pares
	public static void pares (int[] numeros) {

		boolean pares = false;

		for (int j = 0; j < 50; j++) {
			
			for (int i = 1; i < numeros[j]; i++) {
				
				if (numeros[j] == 0) {
				
					break;

				}	

				if (numeros[j] % 2 != 0) {
					
					pares = false;

				} else {

					pares = true;

				}
			}
		}

		if (pares) {
			
			System.out.println("\n\nA lista é constituída apenas por números pares.");

		} else {
			
			System.out.println("\n\nA lista não é constituída apenas por números pares.");

		}
	}

	//modulo de leitura de ficheiro
	public static int[] readFile () throws IOException {

		File temp;
		String nome;

		int numeros[] = new int[50];

		int i = 0;

		do {

			System.out.print("Nome do ficheiro a ser lido (com extensão): ");
			nome = k.next();

			temp = new File(nome);

			if (!temp.isFile() || !temp.canRead()) {
				
				System.out.println("Ficheiro não existente, coloque outro.");
			}
		} while(!temp.isFile() || !temp.canRead());

		Scanner infile = new Scanner(temp);

		while (infile.hasNext()) {

			String num = infile.next();
			System.out.printf("\n %s", num);

			if (!infile.hasNext()) {
				
				break;
			}

			numeros[i] = Integer.parseInt(num);

			i++;
		}

		infile.close();

		return numeros;
	}

	//modulo para adicionar à sequência existente
	public static int[] addNums(int numeros[]) {

		for (int i = 0;i < 50 ; i++) {
			
			if (numeros[i] == 0) {
				
				System.out.printf("valor #%2d: ", i + 1);
				numeros[i] = k.nextInt();

				if (numeros[i] == 0) {
								
					break;
				
				}
			}
		}

		return numeros;
	} 

	//modulo para gravar a sequência num ficheiro
	public static void stampFile(int[] numeros) throws IOException {

		File rite;
		String nome;	
	
		System.out.print("Qual o nome do ficheiro em que a lista vai ser guardada? ");
		nome = k.next();

		rite = new File(nome);

		PrintWriter prt = new PrintWriter(rite);

		for (int i = 0; i < 50; i++) {
			
			if (numeros[i] == 0) {
				
				break;
			}

			prt.println(numeros[i]);
		}

		prt.close();
	} 
}