import java.util.*;
import static java.lang.Math.*;

public class E801 {

	public static void main(String[] args) {
	
		Scanner k = new Scanner(System.in);

		String frase;
		int cMin = 0;
		int cMax = 0;
		int cDig = 0;
		int vogs = 0;
		int cons = 0;

		System.out.println("Análise de uma frase");
		
		System.out.print("Frase de entrada -> ");
		frase = k.nextLine();

		for (int i = 0; i < frase.length(); i++) {
			
			if (Character.isUpperCase(frase.charAt(i))) {
				cMax++;
			
			} else if (Character.isLowerCase(frase.charAt(i))) {
				cMin++;
			
			} else if (Character.isDigit(frase.charAt(i))) {
				cDig++;
			
			}

			if (isVowel(frase.charAt(i))) {
				vogs++;
			
			} else if (!isVowel(frase.charAt(i))) {
				cons++;

			}
		}

		System.out.printf("Número de caracteres minúsculos -> %2d\nNúmero de caracteres maiúsculos -> %2d\n", cMin, cMax);
		System.out.printf("Número de caracteres numéricos -> %2d\nNúmero de vogais -> %2d\nNúmero de consoantes -> %2d\n", cDig, vogs, cons);
	}

	//modulo para ver se é vogal ou não
	public static boolean isVowel(char c) {

		boolean vogal;
		c = Character.toLowerCase(c);

		if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u') {
			
			vogal = true;
		} else {
			
			vogal = false;
		}

		return vogal;
	}
}