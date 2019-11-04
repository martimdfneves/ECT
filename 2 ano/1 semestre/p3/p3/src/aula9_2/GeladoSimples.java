package aula9_2;

public class GeladoSimples implements Gelado {
	
	private String sabor;
	private int base;
	
	public GeladoSimples(String sabor) {
		
		this.sabor=sabor;
	}
	
	@Override
	public void base(int arg) {
		
		base = arg;
		System.out.printf("\n%d %s de gelado de %s", base, base == 1 ? "bola" : "bolas" , sabor);
	}

	public String getSabor() {
		
		return sabor;
	}
}