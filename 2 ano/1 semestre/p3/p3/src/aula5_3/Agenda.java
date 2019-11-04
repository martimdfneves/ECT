package aula5_3;

import java.io.*;
import java.util.*;
import aula1_2.*;

public class Agenda {
	
	private List<Contacto> contactos;
	private Scanner scan;
	private InterfaceAgenda agenda;
	public Agenda() {
		
		contactos = new ArrayList<Contacto>();
	}
	
	public void addContact() {
		
		try {
			Contacto c;
			scan = new Scanner(System.in);
			System.out.print("Nome: ");
			String nome = scan.nextLine();
			System.out.print("Contacto: ");
			int cont = Integer.parseInt(scan.nextLine());
			String data[];
			try {
				
				System.out.print("Data de Nascimento (dd/mm/yyyy): ");
				data = (scan.nextLine()).split("/");
				c = new Contacto(cont, new Data(Integer.parseInt(data[0]), Integer.parseInt(data[1]), Integer.parseInt(data[2])),nome);
				contactos.add(c);
			}
			catch (RuntimeException e) {
				
				System.out.print("Erro na data");
			}
		}
		catch (NumberFormatException e) {
			
			System.out.println("Numeros onde deviam de estar letras.");
		}
	}
	
	public void loadContacts(String filename) throws IOException {
		
		try {
			
			scan = new Scanner(new File(filename));
		}
		catch (FileNotFoundException e) {
			
			System.out.println("Ficheiro não encontrado");
			return;
		}
		String type = scan.nextLine();
		if (type.equals("Nokia")) {
			
			agenda = new Nokia();
		}
		else if (type.equals("CSV")){ 
			
			agenda = new CSV();
		}
		else if (type.equals("vCard")) {
			
			agenda = new VCard();
		}
		else {
			
			throw new IllegalArgumentException("Tipo de ficheiro errado");
		}
		agenda.saveAgenda(new File(filename), contactos.toArray(new Contacto[contactos.size()]));
	}
	
	public void addContactFromFile(String filename) throws IOException {
		
		try {
			
			scan = new Scanner(new File(filename));
		}
		catch (FileNotFoundException e) {
			
			System.out.println("ficheiro não encontrado");
			return;
		}
		String type = scan.nextLine();
		if (type.equals("Nokia")) {
			
			agenda = new Nokia();
		}
		else if (type.equals("CSV")) {
			
			agenda = new CSV();
		}
		else if (type.equals("vCard")) {
			
			agenda = new VCard();
		}
		else {
			
			throw new IllegalArgumentException("Tipo de ficheiro errado");
		}
		contactos.addAll(agenda.loadAgenda(scan));
	}
	
	public void printContactos() {
		
		Iterator<Contacto> iterator = contactos.iterator();
		Contacto contacto;
		while(iterator.hasNext()) {
			
			contacto = iterator.next();
			System.out.println(contacto);
		}
	}
}
