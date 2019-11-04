import java.util.*;

public class E1108{

	static Scanner k = new Scanner(System.in);

	public static void main(String[] args) {
		
		Book[] livro = new Book[200];
		for (int i = 0; i < 200; i++) {
			livro[i] = new Book();
		}

		int opt;
		int contador = 0;

		do{
			System.out.println("Gestão de uma Biblioteca:");
			System.out.println("1 -> Introduzir um livro");
			System.out.println("2 -> Remover um livro");
			System.out.println("3 -> Apagar a biblioteca");
			System.out.println("4 -> Verificação de cotas repetidas");
			System.out.println("5 -> Alterar o estado de aquisição de um livro");
			System.out.println("6 -> Listar os livros requisitados");
			System.out.println("7 -> Listar os livros ordenados pela cota");
			System.out.println("8 -> Listar os livros ordenados pela data");
			System.out.println("9 -> Terminar o programa");
			System.out.print("Opção -> ");
			opt = k.nextInt();

			switch (opt) {
				
				case 1:
					livro = putbook(livro);
					break;

				case 2:
					livro = removeBook(livro);
					break;

				case 3:
					livro = remAll(livro);
					break;

				case 4:
					livro = verCout(livro);
					break;

				case 5:
					livro = statBook(livro);
					break;

				case 6:
					listReq(livro);
					break;

				case 7:
					listCout(livro);
					break;

				case 8:
					listDate(livro);
					break;

				case 9:
					break;

				default:
					System.out.println("Opção não aceite, coloque outra");

			}
		}while(opt != 9);
	}

	//modulo de inserção de um livro
	public static Book[] putbook(Book[] b){

		int cnt = contador(b);

		if (cnt == b.length - 1 ) {
		
			System.out.println("Não podem ser adicionados mais livros à biblioteca.");
		
		} else {

			while(k.nextLine().length()!=0);
			do{
				System.out.print("Cota: ");
				b[cnt].cota = k.nextLine();

				if (b[cnt].cota.length() > 20 || !b[cnt].cota.matches("[a-zA-Z0-9]*")) {
					System.out.println("Cota não válida, coloque outra.");
				}
			}while(b[cnt].cota.length() > 20 || !b[cnt].cota.matches("[a-zA-Z0-9]*"));

			do{
				System.out.print("Título: ");
				b[cnt].title = k.nextLine();

				if (b[cnt].title.length() > 60) {
					System.out.println("Título não válido, coloque outro.");
				}
			}while(b[cnt].title.length() > 60);

			do{
				System.out.print("Autor: ");
				b[cnt].autor = k.nextLine();

				if (b[cnt].autor.length() > 40) {
					System.out.println("Autor não válido, coloqe outro.");
				}
			}while(b[cnt].autor.length() > 40);

			System.out.print("Data: ");
			b[cnt].date = k.nextLine();

			do{
				System.out.print("Estado do livro: ");
				b[cnt].state = Character.toUpperCase(k.next().charAt(0));

				if (b[cnt].state != 'C' && b[cnt].state != 'L' && b[cnt].state != 'R') {
					System.out.println("Estado não aceitável, apenas L (livre), C (condicionado) e R (requesitado) são aceites.\nColoque outro.");
				}
			}while(b[cnt].state != 'C' && b[cnt].state != 'L' && b[cnt].state != 'R');
		}

		return b;
	}

	//modulo de remoção de um livro com base na cota
	public static Book[] removeBook(Book[] b){

		String cota;
		int cnt = contador(b);
		int li = -1;
		boolean existe = false;
		
		Book[] temps = b;

		System.out.print("Qual a cota do livro que quer remover? ");
		cota = k.nextLine();

		for (int i = 0; i < cnt; i++) {
			if (cota == b[i].cota) {
				li = i;
				break;
			}
		}

		if (li == -1) {
			System.out.println("O livro que procura não está na base de dados.");
		} else {

			System.out.printf("O livro %s foi removido.\n", b[li].title);

			for (int i = li + 1; i < cnt; i++) {
				
				if (b[li].cota == cota && !existe) {
					
					b[li] = null;
					existe = true;
				
				} else if (existe) {
					
					b[i-1] = temps[i];	
				}
			}
		}

		return b;
	}

	//modulo para apagar toda a biblioteca
	public static Book[] remAll(Book[] b){

		for (int i = 0; i < b.length; i++) {
			b[i] = null;
		}

		return b;
	}

	//modulo de verificação das cotas
	public static Book[] verCout(Book[] b){

		int cnt = contador(b);
		int opt;
		boolean swap;

		do {

			swap = false;

			for (int i = 0; i < cnt-1; i++) {
				
				if (b[i].cota == b[i+1].cota) {
					
					do{
						System.out.printf("O livro %s e %s têm a mesma cota.", b[i].title, b[i+1].title); 
						System.out.printf("Qual deles quer mudar a cota:\n\t1) %s\n\t2) %s\n\nOpção? ", b[i].title, b[i+1].title);
						opt = k.nextInt();

						if (opt != 1 && opt != 2) {
							System.out.println("Opção não aceite, por favor coloque outra.");
						}
					}while(opt != 1 && opt != 2);

					if(opt == 1){
						
						do{
							System.out.print("Nova cota:");
							b[i].cota = k.nextLine();

							if (b[i].cota.length() > 20 || !b[i].cota.matches("[a-zA-Z0-9]*")) {
								System.out.println("Cota não válida, coloque outra.\n");
							}
						}while(b[i].cota.length() > 20 || !b[i].cota.matches("[a-zA-Z0-9]*"));
					
					} else if (opt == 2) {
						
						do{
							System.out.print("Nova cota:");
							b[i+1].cota = k.nextLine();

							if (b[i+1].cota.length() > 20 || !b[i+1].cota.matches("[a-zA-Z0-9]*")) {
								System.out.println("Cota não válida, coloque outra.\n");
							}
						}while(b[i+1].cota.length() > 20 || !b[i+1].cota.matches("[a-zA-Z0-9]*"));	
					}

					swap = true;
				}
			}
		}while(swap);

		return b;
	}

	//modulo de mudança de estado dos livros
	public static Book[] statBook(Book[] b){

		String cota;
		char ans;
		int cnt = contador(b);
		int li;
		boolean existe = false;

		System.out.print("Cota do livro: ");
		cota = k.nextLine();

		for (li = 0; li < cnt; li++) {
			if (cota == b[li].cota) {
				existe = true;
				break;
			}
		}

		if (!existe) {	
			System.out.print("O livro não existe.\n");
		} else {

			if (b[li].state == 'C' || b[li].state == 'R') {
				
				System.out.printf("O livro está %s.\n", b[li].state == 'R' ? "requisitado" : "condicionado");

				do{
					System.out.print("Quer libertar o livro?(s/n) ");
					ans = Character.toLowerCase(k.next().charAt(0));

					if (ans == 's' && ans == 'n') {
						System.out.println("Resposta não aceite. Coloque outra.");
					}
				}while(ans == 's' && ans == 'n');
				
				if (ans == 's') {
					
					b[li].state = 'L';
				}

			} else {

				System.out.println("O livro está livre.");

				do{
					System.out.print("Quer condicionar, requisitar ou manter o livro livre? ");
					ans = Character.toUpperCase(k.next().charAt(0));

					if (ans != 'R' && ans != 'C' && ans != 'L') {
						System.out.println("Opção não válida. Opções válidas:\nr -> requisitar\nc -> condicionar\nl-> manter livre");
					}
				}while(ans != 'R' && ans != 'C' && ans != 'L');

				b[li].state = ans;
			}
		}
		return b;
	}

	//modulo de listagem dos requesitados
	public static void listReq(Book[] b){

		int cnt = contador(b);

		for (int i = 0; i < cnt; i++) {
			System.out.println();
			printBook(b[i]);
		}
	}

	//modulo de listagem por ordem crescente da cota
	public static void listCout(Book[] b){

		int cnt = contador(b);
		boolean swap;
		Book tmp = new Book();

		do{

			swap = false;

			for (int i = 0; i < cnt-1; i++) {
				
				if (Integer.parseInt(b[i].cota) > Integer.parseInt(b[i+1].cota)) {
					tmp = b[i];
					b[i] = b[i+1];
					b[i+1] = tmp;
					swap = true;
				}
			}
		}while(swap);

		for (int i = 0; i < cnt; i++) {
			System.out.println();
			printBook(b[i]);
		}
	}

	//modulo de listagem por ordem crescente da data
	public static void listDate(Book[] b){

		int cnt = contador(b);
		boolean swap;
		Book tmp = new Book();

		do{

			swap = false;

			for (int i = 0; i < cnt-1; i++) {
				
				if ((b[i].date.charAt(6) > b[i+1].date.charAt(6)) || //decada
				
				((b[i].date.charAt(6) == b[i+1].date.charAt(6)) && (b[i].date.charAt(7) > b[i+1].date.charAt(7))) || //ano
				
				((b[i].date.charAt(6) == b[i+1].date.charAt(6)) && (b[i].date.charAt(7) == b[i+1].date.charAt(7)) && (b[i].date.charAt(3)>b[i+1].date.charAt(3))) || //primeiro algarismo do mes
				
				((b[i].date.charAt(6) == b[i+1].date.charAt(6)) && (b[i].date.charAt(7) == b[i+1].date.charAt(7)) && (b[i].date.charAt(3)==b[i+1].date.charAt(3)) && 
					(b[i].date.charAt(4) > b[i+1].date.charAt(4))) || //segundo algarismo do mes
				
				((b[i].date.charAt(6) == b[i+1].date.charAt(6)) && (b[i].date.charAt(7) == b[i+1].date.charAt(7)) && (b[i].date.charAt(3)==b[i+1].date.charAt(3)) && 
					(b[i].date.charAt(4) == b[i+1].date.charAt(4)) && (b[i].date.charAt(0) > b[i+1].date.charAt(0))) || //primeiro algarismo do dia
				
				((b[i].date.charAt(6) == b[i+1].date.charAt(6)) && (b[i].date.charAt(7) == b[i+1].date.charAt(7)) && (b[i].date.charAt(3)==b[i+1].date.charAt(3)) && 
					(b[i].date.charAt(4) == b[i+1].date.charAt(4)) && (b[i].date.charAt(0) == b[i+1].date.charAt(0)) && (b[i].date.charAt(1) > b[i+1].date.charAt(1)))) 
				//segundo algarismo do dia
				{
		
					tmp = b[i];
					b[i] = b[i+1];
					b[i+1] = tmp;
					swap = true;
				}
			}
		}while(swap);

		for (int i = 0; i < cnt; i++) {
			System.out.println();
			printBook(b[i]);
		}	
	}

	//modulo de impressão de um livro
	public static void printBook(Book b){

		System.out.printf("Título: %s\n", b.title);
		System.out.printf("Autor: %s\n", b.cota);
		System.out.printf("Autor; %s\n", b.autor);
		System.out.printf("Data: %s\n", b.date);
		System.out.printf("Estado: %c\n\n", b.state);
	} 

	//modulo de contagem
	public static int contador(Book[] b){

		int cnt = 0;

		for (; cnt < b.length; cnt++) {
			if (b[cnt].cota == null) {
				break;
			}
		}

		return cnt;
	}
}

class Book{

	String cota, autor, title, date;
	char state;
}