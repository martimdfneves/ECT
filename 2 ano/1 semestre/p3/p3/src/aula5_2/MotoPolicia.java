package aula5_2;

public class MotoPolicia extends Moto implements Policia{
	
	private Tipo t;
	private String id;
	
	public MotoPolicia (int ano, int rodas, int cc, int potencia, int v, double consumo, double combustivel, String capacete, String marca, String selo, String cor, Tipo t, String id) {
		
		super(ano, rodas, cc, potencia, v, consumo, combustivel, capacete, marca, selo, cor);
		this.t=t;
		this.id=id;
	}

	public Tipo getTipo() {
		return t;
	}
	
	public String getID() {
		return id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((t == null) ? 0 : t.hashCode());
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
		MotoPolicia other = (MotoPolicia) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (t != other.t)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		
		return "Veículo de Policia:" + id;
	}
}
