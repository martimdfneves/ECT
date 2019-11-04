import java.util.*;

public class E1107{

	static Scanner k = new Scanner(System.in);

	public static void main(String[] args) {

		Turma[] tms = new Turma[10];
		for (int i = 0; i < 10; i++) {
			tms[i] = new Turma();
		}

		int opt;
		int turma;

			do{
			System.out.println("Gestão de Turmas:");
			System.out.println("1 -> Inserir informação numa turma");
			System.out.println("2 -> Mostrar informação de um aluno");
			System.out.println("3 -> Alterar informação de um aluno");
			System.out.println("4 -> Listar os alunos ordenados por nº mec");
			System.out.println("5 -> Listar os alunos ordenados por nota final");
			System.out.println("6 -> Média das notas finais");
			System.out.println("7 -> Melhor aluno");
			System.out.println("0 -> Terminar programa");
			System.out.print("Opção -> ");
			opt = k.nextInt();

			switch (opt) {
				
				case 1:
					do{
						System.out.print("Turma -> ");
						turma = k.nextInt();
					}while(turma < 0 || turma > 10);
					
					tms[turma] = insertTurma();
					break;

				case 2:					
					do{
						System.out.print("Turma -> ");
						turma = k.nextInt();
					}while(turma < 0 || turma > 10);

					mostraAluno(tms[turma]);
					break;

				case 3:
					do{
						System.out.print("Turma -> ");
						turma = k.nextInt();
					}while(turma < 0 || turma > 10);

					mudAluno(tms[turma]);
					break;

				case 4:
					do{
						System.out.print("Turma -> ");
						turma = k.nextInt();
					}while(turma < 0 || turma > 10);

					listnmec(tms[turma]);
					break;

				case 5:
					do{
						System.out.print("Turma -> ");
						turma = k.nextInt();
					}while(turma < 0 || turma > 10);

					listnf(tms[turma]);
					break;

				case 6:
					do{
						System.out.print("Turma -> ");
						turma = k.nextInt();
					}while(turma < 0 || turma > 10);

					avg(tms[turma]);
					break;

				case 7:
					do{
						System.out.print("Turma -> ");
						turma = k.nextInt();
					}while(turma < 0 || turma > 10);

					melhor(tms[turma]);
					break;

				case 0:
					break;
			
				default:
					System.out.println("Opção não aceite, coloque outra.");
					break;
			}
		}while(opt != 0);
	}

	//modulo de inserção de dados numa turma
	public static Turma insertTurma(){

		Turma t = new Turma();
		/*

		isto é outra maneira de inicializar os alunos de cada turma
		for (int i = 0; i < 20; i++) {
			t.al[i] = new Aluno();
		}*/

		t.init();

		int mec;

		System.out.println("Para terminar a leitura dos t.al coloque o nmec = 0.\n\n");

		for (int i = 0; i < 20; i++) {
			

			System.out.print("Nº. mecanográfico: ");
			mec = k.nextInt();

			if (mec == 0) {
			
				break;
			
			} else {
			
				t.al[i].nmec = mec;
			
				while(k.nextLine().length()!=0);
				System.out.print("Nome: ");
				t.al[i].name = k.nextLine();
				
				
				System.out.println("Notas:");
				System.out.print("\tMT1: ");
				t.al[i].notas[0] = k.nextDouble();
				System.out.print("\tTP: ");
				t.al[i].notas[1] = k.nextDouble();
				System.out.print("\tMT2: ");
				t.al[i].notas[2] = k.nextDouble();				
				System.out.print("\tEF: ");
				t.al[i].notas[3] = k.nextDouble();

				t.al[i].nf = t.al[i].notas[0] * 0.1 + t.al[i].notas[1] * 0.3 + t.al[i].notas[2] * 0.1 + t.al[i].notas[3] * 0.5;
			}
		}

		return t;

	}

	//modulo de mostragem de um aluno com base no nmec
	public static void mostraAluno(Turma t){
		
		int num;
		int alumi = 0;
		boolean existe = false;

		for (int i = 0; i < 20; i++) {
			if (t.al[i] == null) {
				break;
			}
			alumi++;
		}


		System.out.print("Número para ser encontrado: ");
		num = k.nextInt();

		for (int i = 0; i < alumi; i++) {
			
			if (t.al[i].nmec == num) {
				printAluno(t.al[i]);
				existe = true;
				break;
			}
		}

		if (!existe) {
			System.out.println("O aluno não está nesta turma.\n");
		}
	}

	//modulo de mudança de um alunocom base no nmec
	public static Turma mudAluno(Turma t){

		int alumi = 0;
		int num;
		boolean existe = false;

		for (int i = 0; i < 20; i++) {
			if (t.al[i].name == null) {
				break;
			}
			alumi++;
		}

		System.out.print("Nº. mecanográfico: ");
		num = k.nextInt();

		//verifica se está nos registos
		for (int i = 0; i < alumi; i++) {
			if (num == t.al[i].nmec) {
				System.out.printf("O aluno %d vai ser substituído.\n", i+1);

				while(k.nextLine().length()!=0);
				System.out.print("Nome: ");
				t.al[i].name = k.nextLine();

				System.out.println("Notas:");
				System.out.print("MT1: ");
				t.al[i].notas[0] = k.nextDouble();
				System.out.print("TP: ");
				t.al[i].notas[1] = k.nextDouble();
				System.out.print("MT2: ");
				t.al[i].notas[2] = k.nextDouble();				
				System.out.print("EF: ");
				t.al[i].notas[3] = k.nextDouble();

				t.al[i].nf = t.al[i].notas[0] * 0.1 + t.al[i].notas[1] * 0.3 + t.al[i].notas[2] * 0.1 + t.al[i].notas[3] * 0.5;
			
				existe = true;
				break;
			}
		}

		//caso não esteja nos registos
		if (!existe) {
			
			//e estes estejam cheios
			if (alumi == 19) {
			
				System.out.println("O aluno em questão não existe na turma e não pode ser adicionado.");
			
			//caso os registos ainda tenham espaço
			} else {
			
				System.out.println("O aluno terá de ser adicionado à turma.");

				while(k.nextLine().length()!=0);
				System.out.print("Nome: ");
				t.al[alumi].name = k.nextLine();

				System.out.println("Notas:");
				System.out.print("MT1: ");
				t.al[alumi].notas[0] = k.nextDouble();
				System.out.print("TP: ");
				t.al[alumi].notas[1] = k.nextDouble();
				System.out.print("MT2: ");
				t.al[alumi].notas[2] = k.nextDouble();				
				System.out.print("EF: ");
				t.al[alumi].notas[3] = k.nextDouble();

				t.al[alumi].nf = t.al[alumi].notas[0] * 0.1 + t.al[alumi].notas[1] * 0.3 + t.al[alumi].notas[2] * 0.1 + t.al[alumi].notas[3] * 0.5;
				
			}
		}

		return t;
	}

	//modulo de listagem de uma turma por ordem de nmec
	public static void listnmec(Turma t){

		boolean swap;
		int alumi;
		Aluno tmp = new Aluno();

		for (alumi = 0; alumi < 20; alumi++) {
			if (t.al[alumi].name == null) {
				break;		
			}	
		}

		do{
			swap = false;

			for (int i = 0; i < alumi-1; i++) {
				if (t.al[i].nmec > t.al[i+1].nmec) {
					tmp = t.al[i];
					t.al[i] = t.al[i+1];
					t.al[i+1] = tmp;
					swap = true;
				}
			}
		}while(swap);

		for (int i = 0; i < alumi; i++) {
			printAluno(t.al[i]);
		}
	}

	//modulo de listagem de uma turma por ordem da nota final
	public static void listnf(Turma t){

		boolean swap;
		int alumi;
		Aluno tmp = new Aluno();

		for (alumi = 0; alumi < 20; alumi++) {
			if (t.al[alumi].name == null) {
				break;		
			}	
		}

		do{
			swap = false;

			for (int i = 0; i < alumi-1; i++) {
				if (t.al[i].nmec > t.al[i+1].nmec) {
					tmp = t.al[i];
					t.al[i] = t.al[i+1];
					t.al[i+1] = tmp;
					swap = true;
				}
			}
		}while(swap);

		for (int i = 0; i < alumi; i++) {
			printAluno(t.al[i]);
		}
	}

	//modulo de calculo e impressão da media de uma turma
	public static void avg(Turma t){

		int alumi;
		double avg = 0;

		for (alumi = 0; alumi < 20; alumi++) {
			if (t.al[alumi].name == null) {
				break;
			}
		}

		for (int i = 0; i < alumi; i++) {
			avg += t.al[i].nf;
		}

		avg /= alumi;

		System.out.printf("A média das notas é de %4.2f.\n\n", avg);
	}

	//modulo de immpressão do melhor aluno de uma turma
	public static void melhor(Turma t){

		int alumi;
		Aluno melhor = t.al[0];

		for (alumi = 0; alumi < 20; alumi++) {
			if (t.al[alumi].name == null) {
				break;
			}
		}

		for (int i = 0; i < alumi; i++) {
			if (t.al[i].nf > melhor.nf) {
				melhor = t.al[i];
			}
		}

		System.out.println("Melhor aluno:");
		printAluno(melhor);
	}

	//modulo de impressão de um aluno
	public static void printAluno(Aluno al){

		System.out.printf("Nº. mecanográfico: %d\n", al.nmec);
		System.out.printf("Nome: %s\n", al.name);
		System.out.print("Notas:\n");
		System.out.printf("\tMT1: %4.2f\n", al.notas[0]);
		System.out.printf("\tTP: %4.2f\n", al.notas[1]);
		System.out.printf("\tMT2: %4.2f\n", al.notas[2]);
		System.out.printf("\tEF: %4.2f\n", al.notas[3]);
		System.out.printf("\nNota Final: %4.2f\n\n\n", al.nf);
	}
}

class Turma{

	Aluno[] al = new Aluno[20];

	//método para inicializar os alunos na turma sem ter de tar a fazer um ciclo mais chato
	public Aluno[] init(){

		for (int i = 0; i < al.length; i++) {
			this.al[i] = new Aluno();
		}

		return al;
	}
}

class Aluno{

	String name;
	int nmec;
	double[] notas = new double[4];
	double nf;
}