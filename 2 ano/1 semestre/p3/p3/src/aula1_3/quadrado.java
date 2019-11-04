package aula1_3;

public class quadrado {
	
	private Ponto centro;
	private double lado;
	
	public quadrado(double l, Ponto c) {
		
		lado=l;
		centro=c;
	}
	
	public quadrado(double l) {
		
		this(l, new Ponto(0.0, 0.0));
	}
	
	public quadrado() {
		
		this(1, new Ponto(0.0, 0.0));
	}
	
	public quadrado(double l, double x, double y) {
		
		this(l, new Ponto(x, y));
	}
	
	public double getArea() {
		
		return lado*lado;
	}
	
	public Ponto getCentre() {
		
		return centro;
	}
	
	public double getLado() {
		
		return lado;
	}
	
	public double getSize() {
		
		return lado*4;
	}
	
	public String toString() {
		
		return "Quadrado com centro em " + centro.toString() + " e lateral de " + lado + "unidades\n";
	}
}