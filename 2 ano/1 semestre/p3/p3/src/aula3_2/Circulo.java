package aula3_2;

public class Circulo extends Figura {
	
private int raio;
	
	public Circulo(int raio, Ponto centro) {
		
		super(centro);
		this.raio = raio;
	}
	
	public Circulo(int raio, int x, int y) {
		
		this(raio, new Ponto(x, y));
	}
	
	public Circulo(int r) {
		
		this(r, new Ponto(0,0));
	}
	
	public Circulo() {
		
		this(1, 0, 0);
	}
	
	public Circulo(Circulo c) {
		
		this(c.raio, c.getCentre());
	}
	
	@Override
	public boolean equals(Object obj) {
		
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Circulo other = (Circulo) obj;
		if (raio != other.raio)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		
		return "Circulo de raio: " + raio + " e centro: " + this.getCentre();
	}
	
	@Override
	public double area() {
		
		return Math.PI * Math.pow(raio, 2);
	}
	
	@Override
	public double perimetro() {
		
		return 2 * Math.PI * raio;
	}
}
