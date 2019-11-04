package aula5_2;

public class Motorizado extends Veiculo{
	
	private int ano;
	private int cc;
	private int potencia;
	private int vmax;
	private double combustivel;
	private double consumo;
	private String matricula;
	
	public Motorizado(int rodas,String marca,String cor,int ano,int cc,int potencia,int vmax,double combustivel,double consumo,String matricula) {
		
		super(rodas, marca, cor);
		this.ano = ano;
		this.cc = cc;
		this.potencia = potencia;
		this.vmax = vmax;
		this.combustivel = combustivel;
		this.consumo = consumo;
		this.matricula = matricula;
	}

	public int getAno() {
		return ano;
	}

	public int getCc() {
		return cc;
	}

	public int getPotencia() {
		return potencia;
	}

	public int getVmax() {
		return vmax;
	}

	public double getCombustivel() {
		return combustivel;
	}

	public double getConsumo() {
		return consumo;
	}

	public String getMatricula() {
		return matricula;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ano;
		result = prime * result + cc;
		long temp;
		temp = Double.doubleToLongBits(combustivel);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(consumo);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((matricula == null) ? 0 : matricula.hashCode());
		result = prime * result + potencia;
		result = prime * result + vmax;
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
		Motorizado other = (Motorizado) obj;
		if (ano != other.ano)
			return false;
		if (cc != other.cc)
			return false;
		if (Double.doubleToLongBits(combustivel) != Double.doubleToLongBits(other.combustivel))
			return false;
		if (Double.doubleToLongBits(consumo) != Double.doubleToLongBits(other.consumo))
			return false;
		if (matricula == null) {
			if (other.matricula != null)
				return false;
		} else if (!matricula.equals(other.matricula))
			return false;
		if (potencia != other.potencia)
			return false;
		if (vmax != other.vmax)
			return false;
		return true;
	}
}