import java.util.*;

public class prob2 {

	static Scanner k = new Scanner(System.in);

	public static void main(String[] args) {
		
		int opt;
		Pilot[] p = new Pilot[10];

		do{
			System.out.println("Gestão de uma prova automóvel:");
			System.out.println("\t1 -> Inserir informação dos pilotos");
			System.out.println("\t2 -> Listar pilotos ordenados pelo número da viatura");
			System.out.println("\t3 -> Alterar informação de um piloto");
			System.out.println("\t4 -> Remover piloto com base no número da viatura");
			System.out.println("\t5 -> Listar pilotos pelo nome");
			System.out.println("\t6 -> Remover piloto(s) com base no nome");
			System.out.println("\t7 -> Validar matrículas");
			System.out.println("\t8 -> Terminar programa");
			System.out.print("Opção -> ");
			opt = k.nextInt();

			switch (opt) {
				
				case 1:
					p = insert(p);
					break;

				case 2:
					listVI(p);
					break;

				case 3:
					p = mudPil(p);
					break;
					
				case 4:
					p = remPilNum(p);
					break;

				case 5:
					listNa(p);
					break;

				case 6:
					p = remPilNom(p);
					break;

				case 7:
					p = valMat(p);
					break;

				case 8:
					break;

				default:
					System.out.println("Opção não aceite, coloque outra.");
					break;
			}
		}while(opt != 8);
	}

	//modulo para inserir pilotos
	public static Pilot[] insert(Pilot[] p){

		String n;

		for (int i = 0; i < 10; i++){
			while(k.nextLine().length() != 0);
			System.out.print("Nome: ");
			n = k.nextLine();

			if (n.isEmpty()) {
				break;
			} else {
				p[i] = new Pilot();
				p[i].name = n;
				p[i].nv = i+1;
			}
			System.out.print("Matrícula: ");
			p[i].mat = k.next(); 
		}

		return p;
	}

	//modulo para listar por ordem decrescente do numero da viatura
	public static void listVI(Pilot[] p){

		System.out.println("Lista dos pilotos por ordem numérica da viatura");
		System.out.println("------------------------------------------");
		for (int i = contador(p)-1; i >= 0; i--) {
			printPilot(p[i]);
			System.out.println();
		}
	}

	//modulo para alterar informação de um piloto com base no numero da viatura
	public static Pilot[] mudPil(Pilot[] p){
		
		int num;
		int cnt = contador(p);
		int pos = -1;
		boolean existe = false;

		System.out.print("Número da viatura: ");
		num = k.nextInt();

		for (int i = 0; i < cnt; i++) {
			if (num == p[i].nv) {
				pos = i;
				existe = true;
				break;
			}
		}

		if (!existe) {
			System.out.println("Esse número não existe na lista.");
		} else {

			while(k.nextLine().length() != 0);
			System.out.print("Nome: ");
			p[pos].name = k.nextLine();

			System.out.print("Matrícula: ");
			p[pos].mat = k.nextLine();
		}

		return p;
	}

	//modulo para remover um piloto com base no numero da viatura
	public static Pilot [] remPilNum(Pilot[] p){
		int num;
		int cnt = contador(p);
		boolean existe = true;

		System.out.print("Número da viatura: ");
		num = k.nextInt();

		if (num > cnt) {
			System.out.println("Esse número não existe na lista.");
		} else {

			for (int i = 0; i < cnt-1; i++) {
				
				if (!existe) {
					
					p[i] = p[i+1];
					p[i+1] = null;

				}else if(existe && p[i].nv == num){

					existe = false;
					p[i] = p[i+1];
					p[i+1] = null;
				}
			}
		}

		return p;
	}

	//modulo para listar pilotos por ordem alfabética
	public static void listNa(Pilot[] p){

		boolean swap;
		int cnt = contador(p);
		Pilot tmp = new Pilot();

		do{
			swap = false;

			for (int i = 0; i < cnt-1; i++) {
				
				//para percorrer o nome completo caso os dois nomes sejam muito semelhantes
				for (int j = 0; j < p[i].name.length() && j < p[i+1].name.length(); j++) {
					
					if (p[i].name.charAt(i) > p[i+1].name.charAt(i)) {
						
						tmp = p[i];
						p[i] = p[i+1];
						p[i+1] = tmp;
						swap = true;
					}
				}
			}
		}while(swap);

		System.out.println("Lista dos pilotos por ordem alfabética");
		System.out.println("------------------------------------------");
		for (int i = 0; i < cnt; i++) {
			printPilot(p[i]);
			System.out.println();
		}
	}

	//modulo para remover pilotos com base no nome
	public static Pilot[] remPilNom(Pilot[] p){

		String nome;
		int cnt = contador(p);
		int z = 0;
		int rem;
		int pos[] = new int[10];
		pos[1] = 0;
		boolean existe = false;
		boolean passa = false;

		while(k.nextLine().length()!=0);
		System.out.print("Nome do piloto(s) a remover: ");
		nome = k.nextLine();

		for (int i = 0; i < cnt; i++) {
			if (p[i].name.indexOf(nome)!=-1) {
				pos[z] = i;
				z++;
				existe = true;
			}
		}

		if (!existe) {
			System.out.println("O nome inserido não existe na lista.");
		} else {
			if (pos[1] == 0) {
				System.out.printf("O condutor %d foi removido.\n", p[pos[0]].nv);

				for (int i = 0; i < cnt-1; i++) {
					
					if (!existe) {
						
						p[i] = p[i+1];
						p[i+1] = null;

					}else if(existe && p[i].nv == pos[0]+1){

						existe = false;
						p[i] = p[i+1];
						p[i+1] = null;
					}
				}	
			} else {
			
				do{
					System.out.println("Quem é para remover:\n");

					for (int i = 0; i < z; i++) {
						printPilot(p[pos[i]]);
						System.out.println();
					}

					System.out.print("\nNúmero a ser removido:");
					rem = k.nextInt();

					for (int i = 0; i < z; i++) {
						if (p[pos[i]].nv == rem) {
							passa = true;
						}
					}

					if (passa) {
						System.out.printf("O condutor %d foi removido.\n", rem);

						for (int i = 0; i < cnt-1; i++) {
						
							if (!existe) {
								
								p[i] = p[i+1];
								p[i+1] = null;

							}else if(existe && p[i].nv == rem){

								existe = false;
								p[i] = p[i+1];
								p[i+1] = null;
							}
						}	
					} else {
						System.out.printf("O condutor %d não está presente na lista acima logo não pode ser apagado.", rem);
					}
				}while(!passa);
			}
		}

		return p;
	}

	//modulo para validar as matriculas
	public static Pilot[] valMat(Pilot[] p){

		int cnt = contador(p);
		int valCounter = 0;

		boolean validada = false;

		String[] padrao = { "AA-00-00", "00-AA-00", "00-00-AA"};

		do{

			validada = false;

			for (int i = 0; i < padrao.length; i++) {
				if (matchPattern(p[valCounter].mat, padrao[i])) {
					validada = true;
					break;
				}
			}

			if (validada) {
				
				valCounter++;
				System.out.printf("A matrícula da viatura %d é válida.\n\n", valCounter);
			
			} else {

				System.out.printf("Matrícula de %d não válida, coloque outra: ", valCounter+1);
				while(k.nextLine().length()!=0);
				p[valCounter].mat = k.nextLine();
			}
		}while(valCounter < cnt);
		
		return p;
	}

	//modulo para impressão de um piloto
	public static void printPilot(Pilot p){

		System.out.printf("Número: %d\n", p.nv);
		System.out.printf("Nome: %s\n", p.name);
		System.out.printf("Matrícula: %s\n", p.mat);
	}

	//modulo para contagem de pilotos
	public static int contador(Pilot[] p){

		int num;

		for (num = 0; num < p.length; num++) {
			if (p[num] == null) {
				break;
			}
		}

		return num;
	}

	//modulo pra descobrir se pertence
	public static boolean matchPattern(String frase, String pattern) {

		boolean pertence = true;

		if (frase.length() != pattern.length()) {
			
			pertence = false;
		} else {

			for (int i = 0; i < frase.length(); i++) {
				
				if (pattern.charAt(i) == 'A') {
					
					if (Character.isDigit(frase.charAt(i))) {
						
						pertence = false;
					} 
				} else if (pattern.charAt(i) == '0') {
					
					if (!Character.isDigit(frase.charAt(i))) {
						
						pertence = false;
					}
				} else if (pattern.charAt(i) == '-') {
					
					if (frase.charAt(i) != '-') {
						
						pertence = false;
					}
				}
			}
		}

		return pertence;
	}
}

class Pilot {

	int nv;
	String name;
	String mat;
}