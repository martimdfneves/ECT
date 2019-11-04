import java.util.*;

public class E401 {

	public static void main(String[] args) {
		
		int repets;

		Scanner k = new Scanner(System.in);

		System.out.print("Quantas vezes queres repetir a frase :\"P1 é fixe!\": ");
		repets = k.nextInt();

		for (int i = 1; i <= repets; ++i) {
			
			System.out.println("P1 é fixe!");

		}
	}
}