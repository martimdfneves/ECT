import java.util.*;

public class E807 {

	public static void main(String[] args) {
		
		Scanner k = new Scanner(System.in);

		String pre;
		String pos;

		System.out.print("Frase -> ");
		pre = k.nextLine();

		pos = capitalize(pre);

		System.out.printf("Capitalizada -> %s\n", pos);
	}

	//modulo para colocar a primeira letra de cada frase em maiuscula
	public static String capitalize(String pre) {

		String pos = "";

		for (int i = 0; i < pre.length(); i++) {

			// primeira letra
			if (i == 0) {
				
				pos += Character.toUpperCase(pre.charAt(0)); 
			
			//caso seja espaço
			} else if (pre.charAt(i) == ' ') {
				
				pos += " " + Character.toUpperCase(pre.charAt(i + 1));
			
			//caso a anterior seja espaço
			} else if (pre.charAt(i - 1) == ' ') {

				;
				
			} else {

				pos += pre.charAt(i);
			}
		}

		return pos;
	}
}