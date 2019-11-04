import java.io.*;
import java.util.*;

public class prob1{

	static Scanner k = new Scanner(System.in);

	public static void main(String[] args) throws IOException {
		
		int opt;

		Dias[] dias = new Dias[31];
		for (int i = 0; i < dias.length; i++){
			dias[i] = new Dias();
			dias[i].temp = 50;
		}

		do{
			System.out.println("Estação metereológica");
			System.out.println("\t1 -> Ler ficheiro de dados");
			System.out.println("\t2 -> Acrescentar medida");
			System.out.println("\t3 -> Listar valores de temperatura e humidade");
			System.out.println("\t4 -> Listar medidas ordenadas por valor de temperatura");
			System.out.println("\t5 -> Listar medidas ordenadas por valor de humidade");
			System.out.println("\t6 -> Calcular valores médios de temperatura e humidade");
			System.out.println("\t7 -> Calcular máximos e mínimos de temperatura e humidade");
			System.out.println("\t8 -> Caldular histograma de temperaturas e humidade");
			System.out.println("\t9 -> Terminar o programa");
			System.out.print("Opção ->");
			opt = k.nextInt();

			switch (opt) {
				
				case 1:
					dias = readFile(dias);
					break;

				case 2:
					dias = putData(dias);
					break;

				case 3:
					listAll(dias);
					break;

				case 4:
					listTemp(dias);
					break;

				case 5:
					listHumid(dias);
					break;

				case 6:
					avg(dias);
					break;

				case 7:
					maxMin(dias);
					break;

				case 8:
					histo(dias);
					break;

				case 9:
					break;

				default:
					System.out.println("Opção não aceite, coloque outra.");
					break;
			}
		}while(opt != 9);
	}

	//modulo de leitura do ficheiro
	public static Dias[] readFile(Dias[] d) throws IOException{

		File fix;
		String namefile;
		boolean temp = true;
		int i = 0;

		do{
			System.out.print("Qual o nome do ficheiro com os dados? ");
			namefile = k.next();

			fix = new File(namefile);

			if (!fix.exists()) {
				System.out.println("O ficheiro não existe, coloque outro.");
			}
		}while(!fix.exists());

		Scanner file = new Scanner(fix);

		while(file.hasNext()){

			if (temp) {
				d[i].temp = Integer.parseInt(file.next());
				temp = false;
			
			} else if(!temp) {
				d[i].humid = Integer.parseInt(file.next());
				temp = true;
				i++;
			}
		}

		file.close();

		return d;
	}

	//modulo para colocar uma medida
	public static Dias[] putData(Dias[] d){

		int cnt = contador(d);

		if (cnt == 31) {
			System.out.println("Não é possível inserir mais dados.");
		} else{

			do{
				System.out.printf("Temperatura do dia %d: ", cnt+1);
				d[cnt].temp = k.nextInt();

				if (d[cnt].temp > 40 || d[cnt].temp < -10) {
					System.out.print("Temperatura não aceite, coloque outra.");
				}
			}while(d[cnt].temp > 40 || d[cnt].temp < -10);

			do{
				System.out.printf("Humidade do dia %d: ", cnt+1);
				d[cnt].humid = k.nextInt();

				if (d[cnt].humid > 100 || d[cnt].humid < 0) {
					System.out.print("Humidade não aceite, coloque outra.");					
				}
			}while(d[cnt].humid > 100 || d[cnt].humid < 0);
		}

		return d;
	}

	//modulo para listar todos os dados colhidos
	public static void listAll(Dias[] d){

		for (int i = 0; i < contador(d); i++) {
			System.out.printf("Dia %d:\n", i+1);
			printDay(d[i]);
		}
	}

	//modulo para listar todos os dados ordenados por ordem crescente de temperatura
	public static void listTemp(Dias[] d){

		int cnt = contador(d);
		boolean swap;
		Dias tmp = new Dias();

		do{
			swap = false;

			for (int i = 0; i < cnt; i++) {
				
				if (d[i].temp > d[i+1].temp) {
					tmp = d[i];
					d[i] = d[i+1];
					d[i+1] = tmp;
					swap = true;
				}
			}
		}while(swap);

		System.out.println("Lista ordenada pela temperatura:");
		for (int i = 0; i < cnt; i++) {
			System.out.printf("Dia %d:\n", i+1);	
			printDay(d[i]);
		}
	}

	//modulo prar listar todos os dados ordenados por ordem decrescente de humidade
	public static void listHumid(Dias[] d){

		int cnt = contador(d);
		boolean swap;
		Dias tmp = new Dias();

		do{
			swap = false;

			for (int i = 0; i < cnt; i++) {
				
				if (d[i].humid < d[i+1].humid) {
					tmp = d[i];
					d[i] = d[i+1];
					d[i+1] = tmp;
					swap = true;
				}
			}
		}while(swap);

		System.out.println("Lista ordenada pela humidade:");
		for (int i = 0; i < cnt; i++) {
			System.out.printf("Dia %d:\n", i+1);	
			printDay(d[i]);
		}
	}

	//modulo para calcular e imprimir a media de temperatura e humidade
	public static void avg(Dias[] d){

		int cnt = contador(d);
		double mediaTemp = 0;
		double mediaHumid = 0;

		//media das temperaturas
		for (int i = 0; i < cnt; i++) {
			
			mediaTemp += d[i].temp;
		}

		mediaTemp /= cnt;

		System.out.printf("Temperatura média: %4.2f\n", mediaTemp);

		//media das humidades
		for (int i = 0; i < cnt; i++) {
			
			mediaHumid += d[i].humid;
		}

		mediaHumid /= cnt;

		System.out.printf("Humidade média: %4.2f\n", mediaHumid);
	}

	//modulo para calcular e imprimir os maximos e minimos de temperatura e humidade
	public static void maxMin(Dias[] d){

		Dias max = new Dias();
		Dias min = new Dias();
		int cnt = contador(d);

		max.temp = -10;
		max.humid = 0;
		min.temp = 40;
		min.humid = 100;

		for (int i = 0; i < cnt; i++) {
			if (d[i].temp > max.temp) {
				max.temp = d[i].temp;		
			}
			if (d[i].humid > max.humid) {
				max.humid = d[i].humid;		
			}
			if (d[i].temp < min.temp) {
				min.temp = d[i].temp;		
			}
			if (d[i].humid < min.humid) {
				min.humid = d[i].humid;		
			}
		}

		System.out.printf("Temperatura máxima: %d\nHumidade máxima: %d\n", max.temp,max.humid);
		System.out.printf("Temperatura mínima: %d\nHumidade mínima: %d\n", min.temp,min.humid);
	}

	//modulo para calcular e imprimir o histograma horizontal
	public static void histo(Dias[] d){

		int cnt = contador(d);
		int[] temps = new int[51];
		int[] humids = new int[101];
		
		//histograma das temperaturas
		for (int i = 0; i < cnt; i++) {
			temps[d[i].temp+10]++;
		}

		System.out.println("Histograma da temperatura");
		System.out.println("--------------------------------------------------");
		for (int i = 0; i < 51; i++) {
			System.out.printf("%2d | ", i-10);
			for (int j = 0; j < temps[i]; j++) {
				System.out.print("*");
			}
			System.out.println();
		}

		//histograma das humidades
		for (int i = 0; i < cnt; i++) {
			humids[d[i].humid]++;
		}

		System.out.println("Histograma da temperatura");
		System.out.println("--------------------------------------------------");
		for (int i = 0; i < 101; i++) {
			System.out.printf("%3d | ", i);
			for (int j = 0; j < humids[i]; j++) {
				System.out.print("*");
			}
			System.out.println();
		}
	}

	//modulo para contagem
	public static int contador(Dias[] d){

		int num;

		for (num = 0; num < d.length; num++) {
			if (d[num].temp == 50) {
				break;
			}
		}

		return num;
	}

	//modulo de impressão dos dados de um dia
	public static void printDay(Dias d){

		System.out.printf("\tTemperatura: %d ºC\n", d.temp);
		System.out.printf("\tHumidade: %d\n", d.humid);
	}	
}

class Dias {

	int temp;
	int humid;
}