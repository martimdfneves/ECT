import java.util.*;

public class E601 {

	static Scanner k = new Scanner(System.in);

	public static void main(String[] args) {
		
		int num;

		System.out.println("Programa para ler uma lista de valores e imprimi-la pela ordem inversa.");
		System.out.println("-----------------------------------------------------------------------");

		System.out.print("Quantos valores vai ter esta lista: ");
		num = k.nextInt();

		int valores[] = new int[num];

		//lê os valores
		for (int i = 0; i < num; i++) {
		
			System.out.printf("\nQual o valor #%3d: ", i + 1);
			valores[i] = k.nextInt();

		}

		//imprime os valores ao contrario do que foram colocados
		System.out.println("\nLista invertida dos valores inseridos");

		for (int i = num -1; i >= 0; i--) {
			
			System.out.printf("\nNúmero %3d: %3d\n", i + 1, valores[i]);
		
		}

	}
}