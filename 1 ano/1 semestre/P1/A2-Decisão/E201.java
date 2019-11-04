import java.util.*;

public class E201 {

	public static void main(String[] args) {
		
		//declaração do teclado
		Scanner keyboard = new Scanner(System.in);

		//declaração das variáveis
		double tp1, tp2, api, ep, nf;

		System.out.print("Qual a nota do aluno no teste 1? ");
		tp1 = keyboard.nextDouble() * 0.15;

		System.out.print("Qual a nota do aluno no teste 2? ");
		tp2 = keyboard.nextDouble() * 0.15;

		System.out.print("Qual a nota do aluno no projeto? ");
		api = keyboard.nextDouble() * 0.3;

		System.out.print("Qual a nota do aluno no exame final? ");
		ep = keyboard.nextDouble() * 0.4;

		nf = tp1 + tp2 + api + ep;

		//ver se o aluno passou
		if (nf >= 9.5) {

			System.out.printf("A nota final do aluno é de %4.2f, e foi aprovado.\n", nf);
		
		} else {
		
			System.out.printf("A nota final do aluno é de %4.2f, e não foi aprovado.\n", nf);
			
		}

	}
}
