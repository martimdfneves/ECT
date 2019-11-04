import java.util.*;

public class E117 {

	public static void main(String[] args) {
		
		//declaração do teclado
		Scanner keyboard = new Scanner(System.in);

		//delcaração das variáveis
		double iva, totalliq, valorpd, disc;

		//iva
		System.out.print("Qual o valor do iva? ");
		iva = keyboard.nextDouble() / 100;

		//desconto
		System.out.print("Qual o valor do desconto? ");
		disc = keyboard.nextDouble();

		//valor dos produtos
		System.out.print("Qual o valor dos produtos? ");
		valorpd = keyboard.nextDouble();

		//valor da fatura
		totalliq = valorpd + valorpd * iva - disc;

		System.out.printf("A fatura terá um valor de %4.2f €.\n", totalliq);

	}
}
