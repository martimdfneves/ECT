package aula3_3;

public class PesadoPassageiros extends Veiculo {

    public PesadoPassageiros(int cc, int pot, int lot, String cartac, double pesoB) throws IllegalAccessException {

        super(cc, pot, lot, cartac, pesoB);
        if (cartac.equals("A") || cartac.equals("B") || cartac.equals("C"))
            throw new IllegalAccessException("Carta errada.");
    }

    public PesadoPassageiros(PesadoPassageiros pp) throws IllegalAccessException {

        this(pp.getCilindrada(), pp.getPotencia(), pp.getLotacao(), pp.getCarta(), pp.getPesoBruto());
    }

    @Override
    public String toString() {

        return "Ve�culo ligeiro:\n\t-> Pot�ncia: " + super.getPotencia() + "\n\t-> Cilindrada: " + super.getCilindrada() + "\n\t-> Peso Bruto: " + super.getPesoBruto();
    }

    @Override
    public boolean canDrive(Condutor c) {

        return (c.getCarta()).equals("A") || getCarta().equals("B") || getCarta().equals("C") ? false : true;
    }
}
