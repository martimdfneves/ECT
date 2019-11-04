import java.util.*;

public class E701 {

	static Scanner k = new Scanner(System.in);

	public static void main(String[] args) {
		
		Hora inicio = new Hora();
		Hora fim = new Hora();

		String dinit = "inicial";
		inicio.horas = 9;
		inicio.minutos = 0;
		inicio.segundos = 0;

		String dfim = "final";

		System.out.println("A que horas acaba a aula?");
		fim = lerHora();

		System.out.println("Classe aula:");
		printHora(inicio, dinit);
		printHora(fim, dfim);
	}

	//modulo pra imprimir a hora
	public static void printHora(Hora in, String j) {

		System.out.printf("Hora %s: %2d:%2d:%2d\n", j, in.horas, in.minutos, in.segundos);
	}

	//modulo pra ler a hora
	public static Hora lerHora() {

		Hora h = new Hora();

		//ler as horas
		do {
			
			System.out.print("Hora: ");
			h.horas = k.nextInt();

			if (h.horas < 0 || h.horas >= 24) {

				System.out.println("\nHora não aceitável, coloque a outra");
			
			}
		} while(h.horas < 0 || h.horas >= 24);

		//ler os minutos
		do {

			System.out.print("Minutos: ");
			h.minutos = k.nextInt();

			if (h.minutos < 0 || h.minutos >= 60) {
				
				System.out.println("\nHora não aceitável, coloque a outra");
			
			}
		} while(h.minutos < 0 || h.minutos >= 60);

		//ler os segundos
		do {

			System.out.print("Segundos: ");
			h.segundos = k.nextInt();

			if (h.segundos < 0 || h.segundos >= 60) {
				
				System.out.println("\nHora não aceitável, coloque a outra");
			
			}
		} while(h.segundos < 0 || h.segundos >= 60);

		return h;
	}
}

//classe da hora
class Hora {

	int horas;
	int minutos;
	int segundos;
}