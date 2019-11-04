import java.util.*;
import static java.lang.Math.*;
import java.io.*;

public class E902 {

	//scanner do teclado
	static Scanner k = new Scanner(System.in);

	public static void main(String[] args) throws IOException {
	
		double num;

		//como colocar o ficheiro com o nome de quando o programa é chamado
		File file = new File(args[0]);

		Scanner kfile = new Scanner(file);

		Statistics registo = new Statistics();

		//updater
		while(kfile.hasNextDouble()){

			num = kfile.nextDouble();

			updateStats(num, registo);

		}

		kfile.close();
		
		System.out.printf("\nMínimo da lista: %4.2f\nMaximo da Lista: %4.2f\nMédia da lista: %4.2f\n", registo.min, registo.max, mean(registo));
		System.out.printf("Números na lista: %3d\nSomatório da lista: %6.2f\nSomatório dos quadrados: %7.2f\n", registo.count, registo.sum, registo.sqsum);
		System.out.printf("Variância da lista: %4.2f\n", variance(registo));
	}

	//função que dá update dos stats da lista
	public static void updateStats(double num, Statistics lista) {

		//renova o minimo se necessario
		if (num > lista.max) {
			
			lista.max = num;
		
		//renova o maximo se necessario
		} else if (num < lista.min) {
			
			lista.min = num;

		}

		//renova o contador da lista
		lista.count += 1;

		//renova o somatorio da lista
		lista.sum += num;

		//renova o somatorio dos quadrados da lista
		lista.sqsum += pow(num, 2);

	}

	//modulo pra calculo da media
	public static double mean(Statistics lista) {

		double media = lista.sum / lista.count;

		return media;
	}

	//modulo pra calculo da variancia
	public static double variance(Statistics lista) {

		double variancia = sqrt((lista.sqsum / lista.count) - pow(mean(lista), 2));

		return variancia;
	}
}

class Statistics {

	double min;
	double max;
	int count;
	double sum;
	double sqsum;
}