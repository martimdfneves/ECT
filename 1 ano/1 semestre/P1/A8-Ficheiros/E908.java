import java.util.*;
import java.io.*;

public class E908 {

	public static void main(String[] args) throws IOException {
	
		File trad;
		String nome;
		Scanner k = new Scanner(System.in);

		do{
			System.out.print("Nome do ficheiro a traduzir: ");
			nome = k.next();

			trad = new File(nome);

			if (!trad.exists() || !trad.canRead()) {
				
				System.out.println("Ficheiro não existente, coloque outro.");
			}
		} while(!trad.exists() || !trad.canRead());

		Scanner kfile = new Scanner(trad);

		while(kfile.hasNext()) {

			String linhas = kfile.nextLine();
			String impr = "";

			impr += tradutor(linhas);	

			System.out.println(impr);
		}

	}

	//modulo de tradução
		public static String tradutor(String pre) {

		pre=pre.replace('l', 'u');
		pre=pre.replace('L', 'U');
		pre=pre.replace('R', ' ');
		pre=pre.replace('r', ' ');

		return pre;
	}
}