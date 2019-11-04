import java.util.*;

public class E111 {

	public static void main(String[] args) {
		
		//declaração das variáveis
		double rate, usd, eur;

		//declaração do teclado
		Scanner keyboard = new Scanner(System.in);

		System.out.print("Qual o taxa de câmbio ");
		rate = keyboard.nextDouble();

		System.out.print("Qual a quantidade em dólares? ");
		usd = keyboard.nextDouble();

		//cálculo da quantidade de euros
		eur = rate * usd;

		//printf = print formated a.k.a. aceita %d e %f, print e println não aceitam estes caracteres
		System.out.printf("%4.2f$ equivale a %4.2f€.\n", usd, eur);
	}
}
