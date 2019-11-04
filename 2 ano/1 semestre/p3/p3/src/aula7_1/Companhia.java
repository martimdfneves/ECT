package aula7_1;

public class Companhia {
	
	private String sigla;
	private String nome;
	
	public Companhia(String sigla, String nome) {
		
		this.sigla = sigla;
		this.nome = nome;
	}

	public String getSigla() {
		
		return sigla;
	}

	public String getNome() {
		
		return nome;
	}

	@Override
	public int hashCode() {
		
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((sigla == null) ? 0 : sigla.hashCode());
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
		Companhia other = (Companhia) obj;
		if (nome == null)
		{
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (sigla == null)
		{
			if (other.sigla != null)
				return false;
		} else if (!sigla.equals(other.sigla))
			return false;
		return true;
	}

	@Override
	public String toString() {
		
		return nome;
	}
}