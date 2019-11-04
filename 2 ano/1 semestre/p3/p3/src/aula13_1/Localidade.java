package aula13_1;

public class Localidade extends Regiao {
	
	private TipoLocalidade tipo;
	
	public Localidade(String nome, int populacao, TipoLocalidade tipo) {
		
		super(nome,populacao);
		this.tipo=tipo;
	}

	public TipoLocalidade getTipo() {
		
		return tipo;
	}

	@Override
	public int hashCode() {
		
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
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
		Localidade other = (Localidade) obj;
		if (tipo != other.tipo)
			return false;
		return true;
	}

	@Override
	public String toString() {
		
		return "Localidade [tipo=" + tipo + ", getNome()=" + getNome() + ", getPopulacao()=" + getPopulacao()
				+ ", toString()=" + super.toString() + ", getClass()=" + getClass() + "]";
	}
}