import static java.lang.Math.*;

import java.util.*;

public class E305 {

	public static void main(String[] args) {
		
		int num, res, i = 2;

		boolean primo = true;

		Scanner k = new Scanner(System.in);

		System.out.println("Programa para saber se o número inserido é primo.");
		System.out.println("--------------------------------------------------");

		System.out.print("Insere um número inteiro positivo: ");
		num = k.nextInt();

		if (num < 0) {
			
			System.out.println("Esse número é negativo.");

		} else if (num == 1 || num == 0) {
			
			System.out.println("Esse número é impossível de saber se é primo ou não.");

		} else if (num >= 2) {
			
			//o 2 é fodido de incluir no método e ficava confuso como a merda
			if (num == 2) {

				System.out.printf("2 é primo.\n");

			} else if (num >= 3) {

				while(i < num) {
				
					res = num % i;

					i++;

					if (res == 0) {

						primo = false;
					}
					
				}
			}
		}

		if (!primo) {
			
			System.out.printf("%d não é primo.\n", num);

		} else if (num != 2 && primo) {
		
			System.out.printf("%d é primo.\n", num);

		}

	}
}