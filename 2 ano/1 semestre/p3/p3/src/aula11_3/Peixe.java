package aula11_3;

public class Peixe extends Alimento {
	
	private TipoPeixe variedade;
	
	public Peixe(TipoPeixe t, double prot, double cal, double pez) {
		
		super(prot, cal, pez);
		variedade = t;		
	}

	public TipoPeixe getVariedade() {
		
		return variedade;
	}

	@Override
	public int hashCode() {
		
		final int prime = 31;
		int result = 1;
		result = prime * result + ((variedade == null) ? 0 : variedade.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Peixe other = (Peixe) obj;
		if (variedade != other.variedade)
			return false;
		return true;
	}

	@Override
	public String toString() {
		
		return "Peixe " + variedade;
	}
}

enum TipoPeixe {
	
	congelado, fresco;
}