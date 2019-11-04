import java.util.*;

public class E503 {

	static Scanner k = new Scanner(System.in);

	//variaveis globais, dão pra todas as funções
	static int ano, mes, dias;

	public static void main(String[] args) {

		System.out.print("Coloca o ano: ");
		ano = k.nextInt();

		System.out.print("Coloca o mês: ");
		mes = k.nextInt();

		diasMeses();

		System.out.printf("O mês %d do ano %d tem %d dias.\n", mes, ano, dias);
	}

	//modulo pra testar se o ano é bissexto
	public static boolean bissexto () {

		if (ano % 4 == 0 && ano % 400 == 0) {
			
			return true;

		} else {
			
			return false;
		}
	}

	//função que dá os dias 
	public static void diasMeses () {

		switch (mes) {
				
			case 1:
			case 3:
			case 5:
			case 7:
			case 8:
			case 10:
			case 12:

				dias = 31;
				break;

			case 4:
			case 6:
			case 9:
			case 11:

				dias = 30;
				break;

			case 2:

				if (bissexto()) {
					
					dias = 29;

				} else {
					
					dias = 28;

				}

				break;

			default:

				System.out.println("Mes não possível.\n");
				break;

		}
	}
}