package aula3_3;

public class Motociclo extends Veiculo{
	
	public Motociclo(int cc, int pot, int lot, String cartac, double pesoB ) throws IllegalAccessException {
		
		super(cc, pot, lot, cartac, pesoB);
	}
	
	public Motociclo(Motociclo m) throws IllegalAccessException {
		
		this(m.getCilindrada(), m.getPotencia(), m.getLotacao(), m.getCarta(), m.getPesoBruto());
	}
	
	@Override
	public String toString() {
		
		return "Motociclo:\n\t-> Potência: " + super.getPotencia() + "\n\t-> Cilindrada: " + super.getCilindrada() + "\n\t-> Peso Bruto: " + super.getPesoBruto();
	}
	
	@Override
	public boolean canDrive(Condutor c) {
		
		return true;
	}
}
