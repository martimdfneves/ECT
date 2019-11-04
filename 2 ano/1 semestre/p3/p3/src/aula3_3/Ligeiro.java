package aula3_3;

public class Ligeiro extends Veiculo{
	
	public Ligeiro (int cc, int pot, int lot, String cartac, double pesoB) throws IllegalAccessException {
		
		super(cc, pot, lot, cartac, pesoB);
		if (cartac.equals("A"))
			throw new IllegalAccessException("Carta errada.");
	}
	
	public Ligeiro(Ligeiro l) throws IllegalAccessException {
		
		this(l.getCilindrada(), l.getPotencia(), l.getLotacao(), l.getCarta(), l.getPesoBruto());
	}
	
	@Override
	public String toString() {
		
		return "Veículo ligeiro:\n\t-> Potência: " + super.getPotencia() + "\n\t-> Cilindrada: " + super.getCilindrada() + "\n\t-> Peso Bruto: " + super.getPesoBruto();
	}
	
	@Override
	public boolean canDrive(Condutor c) {
		
		return (c.getCarta()).equals("A") ? false : true;
	}
}