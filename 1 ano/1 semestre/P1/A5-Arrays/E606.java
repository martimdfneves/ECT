import java.util.*;

public class E606 {

	public static void main(String[] args) {

		Scanner k = new Scanner(System.in);

		char letra[] = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','x','y','w','z'};

		boolean aparece[] = new boolean[26];

		System.out.println("Programa para saber quais as letras que aparecem numa palavra.\n");

		System.out.print("Coloca a palavra que queres: ");
	
		String aveiro = k.next();

		//verificar se uma letra aparece numa string
		for (int i = 0; i < aveiro.length(); i++) {
			
			for (int j = 0; j < letra.length; j++) {

				if (Character.toLowerCase(aveiro.charAt(i)) == letra[j]) {

					aparece[j] = true;

				}

			}

		}

		//diz quais as letras que apareceram
		for (int l = 0; l < 26; l++) {

			if (aparece[l]) {

				System.out.printf("A letra %c aparece.\n", letra[l]);

			}
		}
	}
}