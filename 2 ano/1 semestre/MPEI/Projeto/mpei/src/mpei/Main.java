package mpei;

import java.io.*;
import java.util.*;

public class Main {
	
	static Scanner sc = new Scanner (System.in);
	static int stocksize,counthash,minhash;
	
	public static void main (String[] args) throws IOException {
	
	try {	
		do {
			System.out.println("Qual o tamanho do Armazém?");
			stocksize =sc.nextInt();
		} while(stocksize<=0);
		do {
		System.out.println("Qual o número de Hash Functions?");
		counthash=sc.nextInt();
		} while(counthash<=0);
		do {
		System.out.println("Qual o número de Minhash?");
		minhash=sc.nextInt();
		} while(minhash<=0);
		System.out.println("");
		System.out.println("");}
	
	catch(Exception e) {
		System.out.println("Digite um inteiro maior que 0");
	}
	
	Armazem mpei=new Armazem(stocksize,counthash,minhash);
	
	String Pcref,Pcref1,Pcref2;
	int op=0;
	do {
			System.out.println("---------------------MENU---------------------");
			System.out.println("1-Adicionar computador");
			System.out.println("2-Remover computador");
			System.out.println("3-Verificar se existe este computador em stock");
			System.out.println("4-Comparar dois computadores");
			System.out.println("5-Sair");
			System.out.print("Digite a sua opção: ");
			op=sc.nextInt();
			System.out.println("");
			try {
				switch(op) {
					case 1:
						System.out.println("Qual o computador que pretende adicionar?");
						Pcref=sc.next();
						File one=new File(Pcref + ".txt");
						if(one.isFile() && one.exists()) {
							mpei.addPc(Pcref + ".txt");
							System.out.println("Adicionado com sucesso!");
							System.out.println("");}
						else {System.out.println("Ficheiro inexistente!");}
					break;
					case 2:
						System.out.println("Qual o computador que pretende remover?");
						Pcref=sc.next();
						File two=new File(Pcref + ".txt");
						if(two.isFile() && two.exists()) {
							if(mpei.numElem(Pcref)>0) {
								mpei.removePc(Pcref + ".txt");
								System.out.println("Removido com sucesso!");
								System.out.println("");}
							else {System.out.println("Artigo inexistente");}
						}
						else {System.out.println("Ficheiro inexistente!");}
					break;
					case 3:
						System.out.println("Qual o computador que pretende verificar?");
						Pcref=sc.next();
						File three=new File(Pcref + ".txt");
						if(three.isFile() && three.exists()) {
							mpei.checkStock(Pcref + ".txt");}
						else {System.out.println("Ficheiro inexistente!");}
					break;
					case 4:
						System.out.println("Qual o primeiro computador para comparar?");
						Pcref1=sc.next();
						System.out.println("Qual o segundo computador para comparar?");
						Pcref2=sc.next();
						File four=new File(Pcref1 + ".txt");
						File five=new File(Pcref2 + ".txt");
						if(four.isFile() && four.exists()) {
							if(five.isFile() && five.exists()) {
								mpei.compare(Pcref1 + ".txt",Pcref2 + ".txt");
								System.out.println("");
								System.out.println("");}}
						else {System.out.println("Ficheiro inexistente!");}
					break;
					case 5:
						System.out.println("Obrigado pela visita, volte sempre!");
						System.exit(0);
					break;
				}
		}
		catch (Exception i) {
			System.out.println("Instrução inválida!");
		} 
	} while(op != 5);
	}
}