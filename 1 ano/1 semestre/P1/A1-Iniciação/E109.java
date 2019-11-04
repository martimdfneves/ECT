import java.util.*;

public class E109 {

	public static void main(String[] args) {
		
		//declaração das variáveis
		double comprimento, largura, perimetro, area;

		//declaração do teclado
		Scanner keyboard = new Scanner(System.in);

		System.out.print("Qual o comprimento do retângulo: ");
		comprimento = keyboard.nextDouble();

		System.out.print("Qual a largura do retângulo: ");
		largura = keyboard.nextDouble();
	
		//declaração do valor da área e do perímetro
		area = comprimento * largura;
		perimetro = 2 * comprimento + 2 * largura;

		//printf = print formated a.k.a. aceita %d e %f, print e println não aceitam estes caracteres
		System.out.printf("A área do retângulo é de %4.2f e o perímetro é de %4.2f.\n", area, perimetro);
	}
}
 

