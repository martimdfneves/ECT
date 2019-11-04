import java.util.*;
import static java.lang.Math.*;

public class E1103 {

	static Scanner k = new Scanner(System.in);

	public static void main(String[] args) {

		String[] frases = new String[10];
		String a;
		int l = 0;
		int j = 0;

		do{

			System.out.printf("Frase %d: ", l+1);
			a = k.nextLine();

			if (a.equals("fim")) {
				break;
			}

			frases[l] = a;
			l++;

		}while(l < 10 || a != "fim");

		for (int i = 0; i < 10; i++) {
			
			if (frases[i] != null) {
				j++;
			}

		}

		System.out.println("Resultado:");

		for (int i = j-1 ; i >= 0; i--) {

			System.out.printf("Frase %d: ", i+1);

			for (int b = frases[i].length() - 1; b >= 0; b--) {
				
				System.out.printf("%c", frases[i].charAt(b));
			}

			System.out.println();
		}
	}
}