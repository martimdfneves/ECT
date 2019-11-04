import java.util.*;
import java.io.*;

public class E505 {

	public static void main(String[] args) {
		if(args.length != 2) {
			System.err.println("Usage: java -ea p55 <source-file> <column-number>");
			System.exit(1);
		}

		File fix = new File(args[0]);

		if(!fix.exists()) {
			System.err.println("ERROR: input file \"" + args[0] + "\" does not exist!");
			System.exit(2);
		}

		if(fix.isDirectory()) {
			System.err.println("ERROR: input file \"" + args[0] + "\" is a directory!");
			System.exit(3);
		}

		if(!fix.canRead()) {
			System.err.println("ERROR: cannot read from input file \"" + args[0] + "\"!");
			System.exit(4);
		}

		try {
			
			int coluna = Integer.valueOf(args[1]).intValue();
		
			if(coluna < 1) {
				System.err.printf("ERRO: coluna inválida.\n", args[1]);
				System.exit(5);
			}

			escreveCol(fix, coluna);
		} catch (NumberFormatException e) {
			System.out.println("ERRO: coluna inválida");
			System.exit(1);
		} catch (RuntimeException e) {
			System.err.println("ERRO: deu pro torto a ler do ficheiro \"" + fix + "\"!");
			System.exit(2);
		}

	}

	public static void escreveCol(File ficheiro, int coluna) {
		assert ficheiro != null && coluna > 0;

		try {
			Scanner kfile = new Scanner(ficheiro);

			while(kfile.hasNextLine()) {
				System.out.println(tiraPalavra(kfile.nextLine(), coluna));
			}

			kfile.close();
		} catch (IOException erro) {
			throw new RuntimeException(erro);
		}
	}

	public static String tiraPalavra(String palavra, int coluna) {
		assert palavra != null && coluna > 0;

		String aEscrever = "";
		int var5 = 0;
		boolean var6 = false;

		int i;
		for(i = 0; i < palavra.length() && var5 < coluna; ++i) {
			if(!var6 && !separador(palavra.charAt(i))) {
				++var5;
				var6 = true;
			} else if(var6 && separador(palavra.charAt(i))) {
				var6 = false;
			}
		}

		if(var5 == coluna) {
			int j;
			for(j = i; j < palavra.length() && !separador(palavra.charAt(j)); ++j) {
				;
			}

			aEscrever = palavra.substring(i - 1, j);
		}

		return aEscrever;
	}

	static boolean separador(char separador) {
		return separador == 32 || separador == 9;
	}
}