package aula6_1;

public class Carne extends Alimento {
	
	private VariedadeDeCarne variedade;
	
	public Carne(VariedadeDeCarne v, double proteina, double calorias, double peso) {
		
		super(proteina, calorias, peso);
		v=variedade;
	}

	public VariedadeDeCarne getVariedade() {
		
		return variedade;
	}

	@Override
	public int hashCode() {
		
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((variedade == null) ? 0 : variedade.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Carne other = (Carne) obj;
		if (variedade != other.variedade)
			return false;
		return true;
	}

	@Override
	public String toString() {
		
		return "Carne "+variedade+", "+super.toString();
	}

}

enum VariedadeDeCarne{
	
	vaca, frango, porco, peru, outra;
}