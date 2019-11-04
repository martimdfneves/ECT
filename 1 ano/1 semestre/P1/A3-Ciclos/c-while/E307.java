import java.util.*;

public class E307 {

	public static void main(String[] args) {
		
		Scanner k = new Scanner(System.in);

		int num1, num2, sum, num1_i, num2_i;

		System.out.println("Programa para fazer uma conta com a tabuada russa.");
		System.out.println("--------------------------------------------------");

		System.out.print("Qual o primeiro número: ");
		num1 = k.nextInt();

		System.out.print("Qual o segundo número: ");
		num2 = k.nextInt();

		//cabeçalho da tabela
		System.out.println("\n|-----------------------|");
		System.out.println("|  X   |   Y   |  SOMA  |");
		System.out.println("|------|-------|--------|");
	
		//definição dos valores iniciais das variáveis
		sum = 0;
		num2_i = num2;
		num1_i = num1;

		//teste para saber qual o tipo que vai aparecer inicialmente
		if (num1_i % 2 == 0) {

			System.out.printf("| %4d |  %4d |  NÃO   |\n", num1 , num2);			

		} else {

			System.out.printf("| %4d |  %4d |  SIM   |\n", num1, num2);	
			sum += num2;		

		}

		//teste para saber  o que vai aparecer a seguir e somar
		do {
			
			num1 = (int) num1 / 2;
			num2 = (int) num2 * 2;

			if (num1 % 2 == 0) {

				System.out.printf("| %4d |  %4d |  NÃO   |\n", num1 , num2);


			} else {

				System.out.printf("| %4d |  %4d |  SIM   |\n", num1, num2);
				
				sum += num2;

			}
		
		} while(num1 != 1);

		//olha acabou caralho
		System.out.println("|-----------------------|");
		
		System.out.printf("\nSoma (%d * %d) = %d\n\n", num1_i, num2_i, sum);
	}
}