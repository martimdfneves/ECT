import java.util.*;

public class E805 {

	public static void main(String[] args) {

		Scanner k = new Scanner(System.in);

		int base, num;

		do {
			System.out.print("Qual a base? ");
			base = k.nextInt();

			if (base < 2 || base > 10) {

				System.out.print("Base não aceitável, coloque outra.\n\n");				
			}
		} while(base < 2 || base > 10);

		System.out.print("Qual o número? ");
		num = k.nextInt();

		String based = numToBase(num, base);

		System.out.printf("O número %d na base %d é %s.\n", num, base, based);		
	}

	//modulo de mudança de base
	public static String numToBase(int num, int base) {

		///base intermédia
		String bas1 = "";

		//base final
		String bas2 = "";

		//cria o invertido do resultado
		while(num >= base) {

			bas1 += num % base;
			num /= base;
		}

		bas1 += base - num;

		//transforma no resutlado correto
		for(int i = bas1.length() - 1; i>= 0; i--) {

			bas2 += bas1.charAt(i);
		}

		return bas2;
	}
}