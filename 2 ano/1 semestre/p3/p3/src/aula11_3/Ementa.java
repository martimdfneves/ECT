package aula11_3;

import java.io.*;
import java.util.*;

public class Ementa {
	
	private final String nome;
	private final String local;
	private final Map<DiaSemana, List<Prato>> pratos = new HashMap<>();
	
	public Ementa(String nome, String local) {
		
		this.nome = nome;
		this.local = local;
	}
	
	public Prato[] getPratPorDia(int k) {
		
		List<Prato> newList = pratos.get(DiaSemana.enumDay(k));
		Prato[] tmp = new Prato[newList.size()];
		int i = 0;
		for(Prato p : newList.toArray(new Prato[0]))
			tmp[i++] = p;
		return tmp;
	}
	
	public void addPrato(Prato prato, DiaSemana dia) {
		
		if(pratos.containsKey(dia))
			pratos.get(dia).add(prato);
		else {
			
			LinkedList<Prato> tmp = new LinkedList<>();
			tmp.add(prato);
			pratos.put(dia, tmp);
		}
	}
	
	public void storeEmenta() throws IOException {
		
		Scanner k = new Scanner(System.in);
		System.out.print("Nome do ficheiro: ");
		String filename = k.nextLine();
		PrintWriter printer = new PrintWriter(new File(filename));
		printer.print(this.toString());
		printer.close();
		k.close();
	}
	
	@Override
	public String toString() {
		
		String s = "";
		DiaSemana[] sorted = pratos.keySet().toArray(new DiaSemana[0]);
		Arrays.sort(sorted);
		for(DiaSemana dia : sorted) {
			
			List<Prato> list = pratos.get(dia);
			for(Prato p : list.toArray(new Prato[0]))
				s += p.toString()+", dia "+dia+"\n";
		}
		return s;
	}
}

enum DiaSemana {
	
	SEGUNDA(0), TERÇA(1), QUARTA(2), QUINTA(3), SEXTA(4), SÁBADO(5), DOMINGO(6);
	
	private int dia;
	
	private DiaSemana(int i) {
		
		dia = i;
	}
	
	public int getDay() {
		
		return this.dia;
	}
	
	public static DiaSemana rand() {
		
		int i = (int)(Math.random() * 6);
		for (DiaSemana dia : values())
			if (dia.getDay() == i)
				return dia;
		return DOMINGO;
	}
	
	public static DiaSemana enumDay(int n) {
		
		DiaSemana var = null;
		for(DiaSemana v : DiaSemana.values())
			if(v.getDay() == n) return v;
		return var;
	}
}