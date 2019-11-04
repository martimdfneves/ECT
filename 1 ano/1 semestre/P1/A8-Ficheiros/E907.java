import java.util.*;
import java.io.*;

public class E907 {

	public static void main(String[] args) throws IOException {
		
		Scanner k = new Scanner(System.in);

		File read;
		String name;

		do {
			System.out.print("Qual o ficheiro para ler? ");
			name = k.next();

			read = new File(name);

			if (!read.exists()) {
				
				System.out.print("Ficheiro não existente, coloque outro.");
			}
		} while (!read.exists());

		Scanner kfile = new Scanner(read);

		while(kfile.hasNext()) {

			while (kfile.hasNext()) {

				String linha = kfile.nextLine();
				String imprimi = "";

				for (int i = 0; i < linha.length(); i++) {
						
					if (i >= 2) {
						
						if (linha.charAt(i - 2) != '.' && linha.charAt(i - 1) != '.') {
							
							imprimi += Character.toLowerCase(linha.charAt(i));
						
						} else if (linha.charAt(i - 2) == '.' || linha.charAt(i - 1) == '.') {
							
							imprimi += Character.toUpperCase(linha.charAt(i));	
						}
					} else if (i == 0) {

						imprimi += Character.toUpperCase(linha.charAt(0));
					
					} else if (i == 1) {

							imprimi += Character.toLowerCase(linha.charAt(1));
					}
				}

				System.out.print(imprimi);
			}
		}

		System.out.println();
	}
}