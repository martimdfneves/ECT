package aula3_3;

public class Veiculo {

    private int cilindrada;
    private int potencia;
    private int lotacao;
    private String carta;
    private double pesoBruto;

    public Veiculo(int cc, int pot, int lot, String cartac, double pesoB) {

        cilindrada = cc;
        potencia = pot;
        lotacao = lot;
        carta = cartac;
        pesoBruto = pesoB;
    }

    public int getCilindrada() {

        return cilindrada;
    }

    public int getPotencia() {

        return potencia;
    }

    public int getLotacao() {

        return lotacao;
    }

    public String getCarta() {

        return carta;
    }

    public double getPesoBruto() {

        return pesoBruto;
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Veiculo other = (Veiculo) obj;
        if (carta == null) {
            if (other.carta != null)
                return false;
        } else if (!carta.equals(other.carta))
            return false;
        if (cilindrada != other.cilindrada)
            return false;
        if (lotacao != other.lotacao)
            return false;
        if (Double.doubleToLongBits(pesoBruto) != Double.doubleToLongBits(other.pesoBruto))
            return false;
        if (potencia != other.potencia)
            return false;
        return true;
    }

    @Override
    public String toString() {

        return "Veiculo [cilindrada=" + cilindrada + ", potencia=" + potencia + ", lotacao=" + lotacao + ", carta="
                + carta + ", pesoBruto=" + pesoBruto + "]";
    }

    public boolean canDrive(Condutor c) {

        return (c.getCarta()).equals("A");
    }
}
