package aula3_2;

public abstract class Figura implements Comparable <Figura>{
	
	private Ponto centro;
	public Figura(Ponto c)
	{
		centro = c;
	}
	
	public abstract double area();
	public abstract double perimetro();
	
	@Override
	public boolean equals(Object obj) {
		
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Figura other = (Figura) obj;
		if (centro == null)
		{
			if (other.centro != null)
				return false;
		} else if (!centro.equals(other.centro))
			return false;
		return true;
	}
	
	public Ponto getCentre() {
		
		return centro;
	}
}
