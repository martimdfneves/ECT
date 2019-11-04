package aula1_2;

import java.util.*;

public class E2 {
	
	static final Scanner sc = new Scanner(System.in);
	static Lista lista = new Lista(9);

	public static void main(String[] args) {
		
		int x=-1;
		
		do {
			menu();
			try {
				x = Integer.parseInt(sc.nextLine());
			} 
			catch (NumberFormatException e) {
				
				System.out.printf("Não é uma opção válida, tente outra vez.\n");
			}
			finally {
				
				switch(x) {
				
					case 0:
						System.out.printf("Programa terminado\n");
						break;
					case 1:
						lista.addPerson();
						break;
					case 2:
						lista.remove();
						break;
					case 3:
						lista.print();
						break;
					case 4:
						lista.sortCC();
						break;
					case 5:
						lista.sortN();
						break;
					default:
						break;	
				}
			}
			
		}while (x != 0);
	}
	
	public static void menu() {
		
		int size = lista.getListSize();
		
		System.out.print("\n\n\n");
		System.out.printf("|-------------------------------------------------|\n");
		System.out.printf("|                       Menu                      |\n");
		System.out.printf("|-------------------------------------------------|\n");
		System.out.printf("|    1   -> Adicionar pessoa  (%d pessoas)         |\n", size);
		System.out.printf("|-------------------------------------------------|\n");
		System.out.printf("|    2   -> Remover pessoa  (%d pessoas)           |\n", size);
		System.out.printf("|-------------------------------------------------|\n");
		System.out.printf("|    3   -> Mostrar lista                         |\n");
		System.out.printf("|-------------------------------------------------|\n");
		System.out.printf("|    4   -> Organizar por número de CC            |\n");
		System.out.printf("|-------------------------------------------------|\n");
		System.out.printf("|    5   -> Organizar por nome                    |\n");
		System.out.printf("|-------------------------------------------------|\n");
		System.out.printf("|    0   -> Sair                      	    	  |\n");
		System.out.printf("|-------------------------------------------------|\n");
		System.out.printf("\n\nOpção: ");
	}
}