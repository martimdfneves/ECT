package aula2_2;

public class E2 {
	
	public static void main(String[] args) {
		
		try {
			
			for (int i = 0; i < args.length; i++) {
				
				System.out.printf("Jogo 1 (%s):\n\n", args[i]);
				SopaDeLetras jogo = new SopaDeLetras(args[i]);
				jogo.jogar();
				jogo.fim();
			}
		}
		catch (ArrayIndexOutOfBoundsException e) {
			
			System.out.println("No foram colocados argumentos.\n");
		}
	}

}
