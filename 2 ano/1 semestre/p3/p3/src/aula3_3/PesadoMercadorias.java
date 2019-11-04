package aula3_3;

public class PesadoMercadorias extends Veiculo{
	
	public PesadoMercadorias (int cc, int pot, int lot, String cartac, double pesoB) throws IllegalAccessException {
		
		super(cc, pot, lot, cartac, pesoB);
		if (cartac.equals("A")||cartac.equals("B"))
			throw new IllegalAccessException("Carta errada.");
	}
	
	public PesadoMercadorias(PesadoMercadorias pm) throws IllegalAccessException {
		
		this(pm.getCilindrada(), pm.getPotencia(), pm.getLotacao(), pm.getCarta(), pm.getPesoBruto());
	}
	
	@Override
	public String toString() {
		
		return "Veículo ligeiro:\n\t-> Potência: " + super.getPotencia() + "\n\t-> Cilindrada: " + super.getCilindrada() + "\n\t-> Peso Bruto: " + super.getPesoBruto();
	}
	
	@Override
	public boolean canDrive(Condutor c) {
		
		return (c.getCarta()).equals("A")||getCarta().equals("B") ? false : true;
	}
}
