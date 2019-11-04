import java.util.*;

public class E502 {

	static Scanner k = new Scanner(System.in);

	static double numer;

	public static void main(String[] args) {

		double num;

		double num1, num2;

		double x1, x2, x3, d, a;

		//pede o número para ser elevado ao quadrado, resultado por uma função e calculado o fatorial 
		System.out.print("Coloca um número para ser elevado ao quadrado: ");
		num = k.nextDouble();

		//elevado ao quadrado
		System.out.printf("\nO quadrado de %4.2f é %4.2f.\n", num, square(num));

		//imagem do número numa função numérica
		System.out.printf("\nO resultado de %4.2f pela função f(x) = 1/(1+n²) é %4.2f.\n", num, func(num));

		//fatorial do número
		System.out.printf("\nO fatorial de %4.2f é %4.2f.\n", num, fatorial(num));

		//testa o máximo
		System.out.println("\nColoca dois números completamente aleatórios: ");
		System.out.print("num1: ");
		num1 = k.nextDouble();
		System.out.print("num2: ");
		num2 = k.nextDouble();

		if (max(num1, num2) == 0) {

			System.out.println("\nNão dá para definir um número maximo.");

		} else {

			System.out.printf("\nNúmero máximo: %4.2f\n", max(num1, num2));

		}

		//calcular a imagem de um número num polinómio
		System.out.print("\nAgora coloca os valores de cada termo do polinómio e o valor da variavel a ser calculada.\n");
		System.out.print("a(x³): ");
		x3 = k.nextDouble();
		System.out.print("b(x²): ");
		x2 = k.nextDouble();
		System.out.print("c(x¹): ");
		x1 = k.nextDouble();
		System.out.print("d(x⁰): ");
		d = k.nextDouble();
		System.out.print("x(var): ");
		a = k.nextDouble();

		System.out.printf("\nO resultado de %4.2f pelo polinómio %4.2fx³ + %4.2fx² + %4.2fx + %4.2f é %4.2f.\n\n", a, x3, x2, x1, d, poly3(a, x3, x2, x1, d));


		int nomi;

		System.out.print("\nColoca um valor inteiro positivo: ");
		nomi = k.nextInt();

		//garante qur o valor é positivo
		getIntPos(nomi);

		System.out.println("Programa para determinar se um número pertence a um dado intervalo.\n");

		//testa para ver se pertence a um intervalo
		getIntRange();

		System.out.println("Program apra imprimir uma mensagem n vezes.");
		System.out.print("Quantas vezes queres repetir: ");
		int vezes = k.nextInt();

		System.out.print("Qual a mensagem que queres repetir: ");
		String crrazy = k.next();

		//imprime a frase as vezes pedidas
		printNtimes(vezes, crrazy);

	}

	//eleva ao quadrado
	public static double square (double num) {

		double squar_num;

		squar_num = num * num;

		return squar_num;
	}

	//multiplica pela função
	public static double func (double num) {

		double fn;

		fn = 1 / (1 + square(num));

		return fn;
	}

	//testar o maximo
	public static double max (double num1, double num2) {

		double max;

		if (num1 > num2) {
			
			max = num1;

		} else if (num2 > num1) {
			
			max = num2;

		} else {
			
			max = 0;
		}

		return max;
	}

	//eleva um numero a uma certa potencia
	public static double power (double base, double expoente) {

		double rez;

		rez = base;

		for (int i = 1; i <= expoente; ++i) {
			
			rez *= base;

		}

		return rez;
	}

	//imagem de um polinomio numa função dada
	public static double poly3 (double x, double a, double b, double c, double d) {

		double rez;

		rez = a * power(x, 3) + b * power(x, 2) + c * x + d;

		return rez;

	}

	//calculo do fatorial de um numero
	public static double fatorial (double num) {

		double fatorial;

		fatorial = 1;

		for (int i = 1; i <= num; ++i) {
			
			fatorial *= i;

		}

		return fatorial;

	}

	//pde um número positivo
	public static void getIntPos (int num) {

		int lock;

		do {

			System.out.print("Coloca Um número inteiro positivo: ");
			lock = k.nextInt();

		} while (lock <= 0);

	}

	//pede um intervalo e valores até que esteja fora do intervalo
	public static void getIntRange () {

		int inter1, inter2;

		do {

			System.out.print("Qual o valor mínimo do intervalo: ");
			inter1 = k.nextInt();

			System.out.print("Qual o valor máximo do intervalo: ");
			inter2 = k.nextInt();

			if (inter1 >= inter2) {
			 	
				System.out.println("Intervalo não aceite.\nColoca outro intervalo.\n");

			} 

		} while (inter1 >= inter2);

		//numeros

		do {

			System.out.print("Coloca o número: ");
			numer = k.nextDouble();

			if (numer <= inter2 && numer >= inter1) {
				
				System.out.printf("Pertence ao intervalo [%4d;%4d].\n\n", inter1, inter2);
			
			} else {

				System.out.printf("Não pertence ao intervalo [%4d;%4d].\n\n", inter1, inter2);
		
			}

		} while (numer > inter2 || numer < inter1);
	
	}

	//imprime uma frase as vezes pedidas
	public static void printNtimes (int n, String crazy) {

		for (int i = 1; i <= n; i++) {
		
			System.out.println(crazy);

		}

	}

}
