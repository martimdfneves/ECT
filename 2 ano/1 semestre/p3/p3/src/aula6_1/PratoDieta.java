package aula6_1;

public class PratoDieta extends Prato {
	
	private double MaxCalorias;
	
	public PratoDieta(String nome, double MaxCalorias) {
		
		super(nome);
		this.MaxCalorias=MaxCalorias;
	}

	public double getMaxCalorias() {
		
		return MaxCalorias;
	}

	@Override
	public int hashCode() {
		
		final int prime = 31;
		int result = super.hashCode();
		long temp;
		temp = Double.doubleToLongBits(MaxCalorias);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		PratoDieta other = (PratoDieta) obj;
		if (Double.doubleToLongBits(MaxCalorias) != Double.doubleToLongBits(other.MaxCalorias))
			return false;
		return true;
	}
	
	@Override
	public boolean addIngrediente(Alimento alimento) {
		
		if(alimento == null) return false;
		if(alimento.getCalorias() + super.getTotalCalorias() <= MaxCalorias) {
			
			return super.addIngrediente(alimento);
		}
		return false;
	}
}
