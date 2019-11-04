import java.util.*;
import java.io.*;

public class E904 {

	static Scanner k = new Scanner(System.in);

	public static void main(String[] args) throws IOException{

		String notas[] = new String[21];
		String nome;
		File nots;

		//para que não apareça null
		for (int i = 0; i < 21; i++) {
			notas[i] = "";
		}

		System.out.println("Programa para fazer um histograma de notas com valor entre 0 e 20;");
		System.out.println("------------------------------------------------------------------");

		//pedir o ficheiro
		do {
			System.out.print("Qual o nome do ficheiro (com extensão): ");
			nome = k.next();

			nots = new File(nome);

			while(!nots.isFile() || !nots.canRead()) {

				System.out.println("Ficheiro não aceite, coloque outro.");
			}
		} while(!nots.isFile() || !nots.canRead());

		//organizar as notas
		for (int i = 0; i < 21; i++) {

			//o scanner tem de ser colocado dentro do ciclo caso contrário só lê os 0's			
			Scanner infile = new Scanner(nots);

			while (infile.hasNextInt()) {
				
				if (infile.nextInt() == i) {
					
					notas[i] += "*";
				}
			}

			infile.close();
		}

		impressor(notas);

	}

	//modulo de impressão do histograma
	public static void impressor (String[] nots) {

		System.out.println("\n\nHistograma de Notas:");
		System.out.println("----------------------------------------------------------------");
	
		for (int i = 20; i >= 0; i--) {
			
			System.out.printf("%2d | %s\n", i, nots[i]);
		}
	}

}