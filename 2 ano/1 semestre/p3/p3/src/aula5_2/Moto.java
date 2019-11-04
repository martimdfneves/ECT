package aula5_2;

public class Moto extends Motorizado{
	
	public Moto(int ano, int rodas, int cc, int potencia, int v, double consumo, double combustivel, String capacete, String marca, String selo, String cor) {
		
		super(rodas, marca, cor, ano, cc, potencia, v, combustivel, consumo, selo);
	}
	
	public String toString() {
		
		return "Moto -> ano: " + super.getAno()+ ", cc: " + super.getCc() + ", vmax: " + super.getVmax() + ", matricula: " + super.getMatricula();
	}
}
