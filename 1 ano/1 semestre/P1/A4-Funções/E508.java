import java.util.*;
import static java.lang.Math.*;

public class E508 {

	static Scanner k = new Scanner(System.in);

	public static void main(String[] args) {
		
		double min_num, max_num, jump;

		System.out.println("Programa para imprimir uma tabela com o resultado de 2 polinomios.");
		System.out.println("------------------------------------------------------------------\n");

		do {

			System.out.print("Qual o valor mais baixo: ");
			min_num = k.nextDouble();


			System.out.print("Qual o valor mais alto: ");
			max_num = k.nextDouble();

			if (max_num <= min_num) {
				
				System.out.println("\nIntervalo não aceite.\nColoque outro intervalo.\n");
			}
		} while (max_num <= min_num);

		System.out.print("Qual o valor do salto para cada número: ");
		jump = k.nextDouble();
		
		System.out.println("\nListagem dos números:\n\n");

		k.close();

		System.out.println("\n|-------------------------------------------------------|");
		System.out.println("|     x     |   5x² + 10x + 3   |   7x³ + 3x² + 5x + 2  |");

		//impressor da tabela
		for (double i = min_num; i < max_num; i += jump) {
			
			System.out.println("|-------------------------------------------------------|");
			System.out.printf("|%9.2f  |%15.2f    |   %13.3f       |\n", i, poly2(i), poly3(i));
		
		}

		System.out.println("|-------------------------------------------------------|");
		System.out.printf("|%9.2f  |%15.2f    |   %13.3f       |\n", max_num, poly2(max_num), poly3(max_num));
		System.out.println("|-------------------------------------------------------|\n\n");

	}

	//calcula o valor do polinomio de 3grau
	public static double poly3 (double num) {

		double polinomio;

		polinomio = 7 * pow(num, 3) + 3 * pow(num, 2) + 5 * num + 2;

		return polinomio;
	}

	//calcula o valor do polinomio de 2grau
	public static double poly2 (double num) {

		double polinomio;

		polinomio = 5 * pow(num, 2) + 10 * num + 3;

		return polinomio;

	}
}