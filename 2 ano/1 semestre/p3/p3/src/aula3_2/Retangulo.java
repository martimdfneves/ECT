package aula3_2;

public class Retangulo extends Figura{
	
	private double comprimento;
	private double largura;
	
	public Retangulo(double comp, double larg, Ponto c) {
		
		super(c);
		comprimento = comp;
		largura = larg;
	}
	
	public Retangulo(double comp, double larg, int x, int y) {
		
		this(comp, larg, new Ponto(x, y));
	}
	
	public Retangulo(double comp, double larg) {
		
		this(comp, larg, new Ponto(0, 0));
	}
	
	public Retangulo(Retangulo r) {
		
		this(r.comprimento, r.largura, r.getCentre());
	}
	
	public double getLarg() {
		
		return largura;
	}
	
	public double getComp() {
		
		return comprimento;
	}

	@Override
	public boolean equals(Object obj) {
		
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Retangulo other = (Retangulo) obj;
		if (Double.doubleToLongBits(comprimento) != Double.doubleToLongBits(other.comprimento))
			return false;
		if (Double.doubleToLongBits(largura) != Double.doubleToLongBits(other.largura))
			return false;
		return true;
	}

	@Override
	public String toString() {
		
		return "Retangulo de comprimento " + comprimento + ", largura " + largura + " e centro " + this.getCentre();
	}
	
	@Override
	public double area() {
		
		return comprimento * largura;
	}
	
	@Override
	public double perimetro() {
		
		return 2 * comprimento + 2 * largura;
	}
}
