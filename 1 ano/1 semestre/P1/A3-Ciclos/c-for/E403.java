import java.util.*;

public class E403 {

	public static void main(String[] args) {
		
		int num, fat;

		Scanner k = new Scanner(System.in);

		System.out.println("Programa para calcular o fatorial de um número entre 1 e 10.");
		System.out.println();
		
		System.out.print("Qual o número que queres? ");
		num = k.nextInt();

		//testa
		if (num > 10 || num < 1) {
			
			System.out.println("Número não aceitável.");
			System.out.print("Escolhe um número entre 1 e 10: ");
			num = k.nextInt();

		}

		fat = 1;

		for (int i = 1; i <= num; ++i) {
			
			fat *= i;

		}

		System.out.printf("O fatorial de %d é %d.\n\n", num, fat);

	}
}