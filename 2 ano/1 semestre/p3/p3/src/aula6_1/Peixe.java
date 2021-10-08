package aula6_1;

public class Peixe extends Alimento {

    private TipoDePeixe tipo;

    public Peixe(TipoDePeixe t, double proteina, double calorias, double peso) {

        super(proteina, calorias, peso);
        t = tipo;
    }

    public TipoDePeixe getTipo() {

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
        Peixe other = (Peixe) obj;
        if (tipo != other.tipo)
            return false;
        return true;
    }

    @Override
    public String toString() {

        return "Peixe " + tipo + ", " + super.toString();
    }
}

enum TipoDePeixe {

    fresco, congelado;
}