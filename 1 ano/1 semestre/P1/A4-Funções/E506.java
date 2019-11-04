import java.util.*;

public class E506 {

	static Scanner k = new Scanner(System.in);

	public static void main(String[] args) {
		
		int num;

		System.out.println("Programa para imprimir a tabuada de um número.");
		System.out.println("----------------------------------------------");

		//garante que o número está entre 0 e 100
		do {

			System.out.print("Qual é o número (0-100): ");
			num = k.nextInt();

			if (num > 101 || num < 1) {
				
				System.out.println("Número não aceite, colque outro.\n");
			}

		} while (num < 1 || num > 101);

		//pra fechar o scanner, por agora caga nessa merda
		k.close();

		//imprime a tabuada até 10 do numero
		impressor(num);

	}

	//calcula o valor
	public static int tabuada (int num, int tabulador) {

		int tabuada;

		tabuada = num * tabulador;

		return tabuada;

	}

	//imprime a puta do valor
	public static void impressor (int num) {

		System.out.println("\n\n|------------------------------------|");
		System.out.printf("|           TABUADA DO %3d           |\n", num);

		for (int i = 1; i <= 10; ++i) {

			System.out.printf("|------------------------------------|\n");
			System.out.printf("|    %4d    x    %3d    =  %5d    |\n", num, i, tabuada(num, i));

		}

		System.out.println("|------------------------------------|\n\n\n");
	}
}