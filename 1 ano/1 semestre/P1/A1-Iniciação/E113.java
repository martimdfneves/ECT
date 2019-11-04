import java.util.*;

public class E113 {

	public static void main(String[] args) {
		
		//declaração do teclado
		Scanner keyboard = new Scanner(System.in);

		//declaração das variáveis
		double loc1x, loc1y, loc2x, loc2y, dist, scale;

		//escala
		System.out.print("A quantos km corresponde 1 cm? ");
		scale = keyboard.nextDouble();

		//coordenadas da localidade 1
		System.out.println("Quais as coordenadas da localidade 1?");
		System.out.print("X1: ");
		loc1x = keyboard.nextDouble();
		System.out.print("Y1: ");
		loc1y = keyboard.nextDouble();

		//coordenadsa da localidade 2
		System.out.println("Quais as coordenadas da localidade 2?");
		System.out.print("X2: ");
		loc2x = keyboard.nextDouble();
		System.out.print("Y2: ");
		loc2y = keyboard.nextDouble();

		dist = scale * Math.sqrt(Math.pow((loc2x - loc1x), 2) + Math.pow(loc2y - loc1y, 2));

		System.out.printf("A distância entre as localidades 1 e 2 é de %5.2f km.\n", dist);

	}
}
