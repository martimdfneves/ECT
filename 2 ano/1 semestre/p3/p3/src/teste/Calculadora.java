package teste;

import java.util.*;
import java.math.*;

public class Calculadora {
	
	public static void main (String args[]) {
		
		Scanner sc = new Scanner(System.in);
		System.out.println("Digite um nome");
		String nome1 = sc.nextLine();
		System.out.println("Digite um nome");
		String nome2 = sc.nextLine();
		
		Random r = new Random();
		int rand = r.nextInt(100);
		if(nome1.toLowerCase().equals("maria joão pereira") && nome2.toLowerCase().equals("martim neves")) {
			
			System.out.println("100% <3");
		}
		
		else if (nome1.toLowerCase().equals("maria joão") && nome2.toLowerCase().equals("martim neves")) {
			
			System.out.println("100% <3");
		}
		
		else {
			 
			System.out.printf("%d",rand);
		}
	}
}