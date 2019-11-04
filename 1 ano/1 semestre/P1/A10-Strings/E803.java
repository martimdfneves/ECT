import java.util.*;

public class E803 {

	public static void main(String[] args) {
		
		Scanner k = new Scanner(System.in);

		String fala;
		int palavras;

		System.out.print("Frase-> ");
		fala = k.nextLine();

		palavras = contador(fala);

		System.out.printf("A frase tem %d palvras.\n", palavras);
	}

	//modulo pra contar as palavras
	public static int contador(String l) {

		int pal = 1;

		for (int i = 0; i < l.length(); i++) {
			
			if (l.charAt(i) == ' ') {
				
				if (l.charAt(i + 1) != ' ') {
				
					pal++;	
				}
			}
		}

		return pal;
	}
}