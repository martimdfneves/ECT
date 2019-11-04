package aula1;

import java.util.*;

public class E1 {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		String input;
		String S="";
		System.out.print("String: ");
		input=sc.nextLine();
		char tmp=' ';
		
		digitos(input);
		M(input);
		m(input);
		
		String words[]=input.split(" ");
		System.out.printf("A frase tem %s palavras\n",words.length);
		
		for(int i = 0; i < input.length(); i++) {
			if(i % 2 == 0) {
				tmp = input.charAt(i);
            }
            else {
            	S += Character.toString(input.charAt(i))+Character.toString(tmp);
            	tmp = ' ';
            }
        }
        if(tmp != ' ') {
        	S += tmp;
        }

        System.out.printf("trocadas: %s\n", S);
		
		sc.close();
	}
	
	public static void digitos(String input) {
		int count=0;
		for(int i=0;i<input.length();i++) {
			if (Character.isDigit(input.charAt(i))) {
				count++;
			}
		}
		if (count>=0)
			System.out.printf("A string tem %d %s.\n", count, count == 1 ? "caracter numérico" : "caracteres numéricos");
		else
			System.out.println("A string não tem carateres numéricos.");
	}
	
	public static void M(String input) {
		boolean check=true;
		for(int i=0;i<input.length() && check;i++) {
			if(Character.isLowerCase(input.charAt(i))) {
				check=false;
			}
		}
		if(check)
			System.out.println("A string só tem letras maiúsculas");
		else
			System.out.println("A string não tem só letras maiúsculas");
	}	

	public static void m(String input) {
		boolean check=true;
		for(int i=0;i<input.length() && check;i++) {
			if(Character.isUpperCase(input.charAt(i))) {
				check=false;
			}
		}
		if(check)
			System.out.println("A string só tem letras minúsculas");
		else
			System.out.println("A string não tem só letras minúsculas");
	}
		
}