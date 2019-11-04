import java.util.*;

public class E203 {

	public static void main(String[] args) {
		
		//declaração do teclado
		Scanner keyboard = new Scanner(System.in);

		//declaração dass variáveis
		int idade;

		System.out.println("|-----------------------------------------|---------------------------------------|");
		System.out.println("|                 Idade                   |                Bilhete                |");	
		System.out.println("|-----------------------------------------|---------------------------------------|");
		System.out.println("|             Inferior a 6                |          Isento de pagamento          |");
		System.out.println("|-----------------------------------------|---------------------------------------|");
		System.out.println("|             Entre 6 e 12                |         Bilhete de cirança (4€)       |"); 
		System.out.println("|-----------------------------------------|---------------------------------------|");
		System.out.println("|            Entre 13 e 65                |           Bilhete Normal (8€)         |");
		System.out.println("|-----------------------------------------|---------------------------------------|");
		System.out.println("|             Acima de 65                 |         Bilhete de 3ª Idade (5€)      |");
		System.out.println("|-----------------------------------------|---------------------------------------|");

		System.out.print("Qual a idade do visitante? ");
		idade = keyboard.nextInt();

		if (idade < 6) {
			
			System.out.println("O visitante não paga");
		
		} else if (idade >= 6 && idade <= 12) {
			
			System.out.println("O visitante paga bilhete de criança (4€).");
		
		} else if (idade >= 13 && idade <= 65) {
		
			System.out.println("O visitante paga bilhete normal (8€).");
			
		} else if (idade > 65) {
			
			System.out.println("O visitante paga bilhete de 3ª idade (5€).");
		
		}
	}
}
