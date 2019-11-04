package aula1_3;

public class Circulo {
	
	private Ponto centro;
	private double raio;
	
	public Circulo(double r, Ponto c) {
		
		centro=c;
		raio=r;
	}
	
	public Circulo(double r) {
		
		this(r, new Ponto(0.0, 0.0));
	}
		
	public Circulo(double x, double y) {
		
		this(1, new Ponto(x, y));
	}
	
	public Circulo() {
		
		this(1, new Ponto(0,0));
	}
	
	public Circulo(double r, double x, double y) {
		
		this(r, new Ponto(x,y));
	}
	
	public double getArea() {
		
		return Math.PI * raio * raio;
	}
	
	public double getSize() {
		
		return Math.PI * 2 * raio;
	}
	
	public double getRay() {
		
		return raio;
	}
	
	public Ponto getCentre() {
		
		return centro;
	}
	
	public String toString() {
		
		return "Circulo com centro em " + centro.toString() + " e raio de " + raio + " unidades.\n"; 
	}
	
	public boolean equals(Circulo toCompare) {
		
		return (this.raio == toCompare.raio);
	}
	
	public boolean interception(Circulo toKnow) {
		
		return (centro.dist(toKnow.getCentre()) <= raio);
	}
}
