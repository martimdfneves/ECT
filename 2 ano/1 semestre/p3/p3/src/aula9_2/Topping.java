package aula9_2;

public class Topping implements Gelado {
	
	private Gelado ice;
	private String top;
	
	public Topping(Gelado gel, String sab) {
		
		ice=gel;
		top=sab;
	}
	
	@Override
	public String getSabor() {
		
		return ice.getSabor();
	}
	
	@Override
	public void base(int arg) {
		
		ice.base(arg);
		System.out.printf(" com %s", top);
	}	
}