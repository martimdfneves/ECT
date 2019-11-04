import java.util.*;
import static java.lang.Math.*;

public class E1106{

	static Scanner k = new Scanner(System.in);

	public static void main(String[] args) {
	
		Aluno[] al = new Aluno[20];
		for (int i = 0; i < 20; i++) {
			al[i] = new Aluno();
		}

		int opt;

		do {
			System.out.println("Gestão de uma turma:");
			System.out.println("\t1 - Inserir informação da turma");
			System.out.println("\t2 - Mostrar informação de um aluno");
			System.out.println("\t3 - Alterar informação de um aluno");
			System.out.println("\t4 - Listar os alunos ordenados por nº. mecanográfico");
			System.out.println("\t5 - Listar os alunos por nota final");
			System.out.println("\t6 - Média de notas finais");
			System.out.println("\t7 - Melhor aluno");	
			System.out.println("\t0 - Terminar programa");
			
			System.out.print("Opção: ");
			opt = k.nextInt();

			switch (opt) {
			
				case 0:
					break;

				case 1:
					al = lerAluno();
					break;

				case 2:
					mostraAluno(al);
					break;

				case 3:
					al = mudar(al);
					break;

				case 4:
					listNmec(al);
					break;

				case 5:
					listNfin(al);
					break;

				case 6:
					avgAlunos(al);
					break;

				case 7:
					melhorAluno(al);
					break;

				default:
					System.out.println("Opção não aceite, coloque outra.");
					break;
			}
		}while(opt != 0);
	}

	//modulo de leitura da turma por aluno
	public static Aluno[] lerAluno() {

		Aluno[] alunos = new Aluno[20];
		for (int i = 0; i < 20; i++) {
			alunos[i] = new Aluno();
		}

		int mec;

		System.out.println("Para terminar a leitura dos alunos coloque o nmec = 0.\n\n");

		for (int i = 0; i < 20; i++) {
			

			System.out.print("Nº. mecanográfico: ");
			mec = k.nextInt();

			if (mec == 0) {
			
				break;
			
			} else {
			
				alunos[i].nmec = mec;
			
				while(k.nextLine().length()!=0);
				System.out.print("Nome: ");
				alunos[i].name = k.nextLine();
				
				
				System.out.println("Notas:");
				System.out.print("\tMT1: ");
				alunos[i].notas[0] = k.nextDouble();
				System.out.print("\tTP: ");
				alunos[i].notas[1] = k.nextDouble();
				System.out.print("\tMT2: ");
				alunos[i].notas[2] = k.nextDouble();				
				System.out.print("\tEF: ");
				alunos[i].notas[3] = k.nextDouble();

				alunos[i].nf = alunos[i].notas[0] * 0.1 + alunos[i].notas[1] * 0.3 + alunos[i].notas[2] * 0.1 + alunos[i].notas[3] * 0.5;
			}
		}

		return alunos;
	}

	//modulo de impressao de um aluno com base no nmec
	public static void mostraAluno(Aluno[] alunos){

		int num;
		int alumi = 0;
		boolean existe = false;

		for (int i = 0; i < 20; i++) {
			if (alunos[i] == null) {
				break;
			}
			alumi++;
		}


		System.out.print("Número para ser encontrado: ");
		num = k.nextInt();

		for (int i = 0; i < alumi; i++) {
			
			if (alunos[i].nmec == num) {
				printAluno(alunos[i]);
				existe = true;
				break;
			}
		}

		if (!existe) {
			System.out.println("O aluno não está nesta turma.\n");
		}
	}

	//modulo para mudar um aluno
	public static Aluno[] mudar(Aluno[] alunos) {

		int alumi = 0;
		int num;
		boolean existe = false;

		for (int i = 0; i < 20; i++) {
			if (alunos[i].name == null) {
				break;
			}
			alumi++;
		}

		System.out.print("Nº. mecanográfico: ");
		num = k.nextInt();

		//verifica se está nos registos
		for (int i = 0; i < alumi; i++) {
			if (num == alunos[i].nmec) {
				System.out.printf("O aluno %d vai ser substituído.\n", i+1);

				while(k.nextLine().length()!=0);
				System.out.print("Nome: ");
				alunos[i].name = k.nextLine();

				System.out.println("Notas:");
				System.out.print("MT1: ");
				alunos[i].notas[0] = k.nextDouble();
				System.out.print("TP: ");
				alunos[i].notas[1] = k.nextDouble();
				System.out.print("MT2: ");
				alunos[i].notas[2] = k.nextDouble();				
				System.out.print("EF: ");
				alunos[i].notas[3] = k.nextDouble();

				alunos[i].nf = alunos[i].notas[0] * 0.1 + alunos[i].notas[1] * 0.3 + alunos[i].notas[2] * 0.1 + alunos[i].notas[3] * 0.5;
			
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
				alunos[alumi].name = k.nextLine();

				System.out.println("Notas:");
				System.out.print("MT1: ");
				alunos[alumi].notas[0] = k.nextDouble();
				System.out.print("TP: ");
				alunos[alumi].notas[1] = k.nextDouble();
				System.out.print("MT2: ");
				alunos[alumi].notas[2] = k.nextDouble();				
				System.out.print("EF: ");
				alunos[alumi].notas[3] = k.nextDouble();

				alunos[alumi].nf = alunos[alumi].notas[0] * 0.1 + alunos[alumi].notas[1] * 0.3 + alunos[alumi].notas[2] * 0.1 + alunos[alumi].notas[3] * 0.5;
				
			}
		}

		return alunos;
	}

	//modulo de impressão dos alunos por ordem crescente de nmec
	public static void listNmec(Aluno[] alunos) {

		boolean swap;
		int alumi;
		Aluno tmp = new Aluno();

		for (alumi = 0; alumi < 20; alumi++) {
			if (alunos[alumi].name == null) {
				break;		
			}	
		}

		do{
			swap = false;

			for (int i = 0; i < alumi-1; i++) {
				if (alunos[i].nmec > alunos[i+1].nmec) {
					tmp = alunos[i];
					alunos[i] = alunos[i+1];
					alunos[i+1] = tmp;
					swap = true;
				}
			}
		}while(swap);

		for (int i = 0; i < alumi; i++) {
			printAluno(alunos[i]);
		}
	}

	//modulo de impressão dos alunos por ordem crescente da nota final
	public static void listNfin(Aluno[] alunos) {

		boolean swap;
		int alumi;
		Aluno tmp = new Aluno();

		for (alumi = 0; alumi < 20; alumi++) {
			if (alunos[alumi].name == null) {
				break;		
			}	
		}

		do{
			swap = false;

			for (int i = 0; i < alumi-1; i++) {
				if (alunos[i].nf > alunos[i+1].nf) {
					tmp = alunos[i];
					alunos[i] = alunos[i+1];
					alunos[i+1] = tmp;
					swap = true;
				}
			}
		}while(swap);

		for (int i = 0; i < alumi; i++) {
			printAluno(alunos[i]);
		}
	}

	//modulo de calculo e impressão da média das notas finais dos alunos
	public static void avgAlunos(Aluno[] alunos) {

		int alumi;
		double avg = 0;

		for (alumi = 0; alumi < 20; alumi++) {
			if (alunos[alumi].name == null) {
				break;
			}
		}

		for (int i = 0; i < alumi; i++) {
			avg += alunos[i].nf;
		}

		avg /= alumi;

		System.out.printf("A média das notas é de %4.2f.\n\n", avg);
	}

	public static void melhorAluno(Aluno[] alunos) {

		int alumi;
		Aluno melhor = alunos[0];

		for (alumi = 0; alumi < 20; alumi++) {
			if (alunos[alumi].name == null) {
				break;
			}
		}

		for (int i = 0; i < alumi; i++) {
			if (alunos[i].nf > melhor.nf) {
				melhor = alunos[i];
			}
		}

		System.out.println("Melhor aluno:");
		printAluno(melhor);
	}

	//impressão de alunos
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

class Aluno {

	int nmec;
	String name;
	double nf;
	double[] notas = new double[4];
}