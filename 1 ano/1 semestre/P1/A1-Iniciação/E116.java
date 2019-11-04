import java.util.*;

public class E116 {

	public static void main(String[] args) {
		
		//delaração do teclado
		Scanner keyboard = new Scanner(System.in);

		//declaração das variáveis
		double d1, d2, d3, d4, gt;

		System.out.print("Quanto darstou o turista no primeiro dia? "); 
		d1 = keyboard.nextDouble();

		d2 = d1 + d1 * 0.2;
		d3 = d2 + d2 * 0.2;
		d4 = d3 + d3 * 0.2;
	
		gt = d1 + d2 + d3 + d4;

		System.out.printf("O turista gastou um total de %4.2f € na sua viagem de 4 dias.\n", gt);
	}
}
