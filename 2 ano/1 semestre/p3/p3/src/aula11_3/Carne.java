package aula11_3;

public class Carne extends Alimento {

    private VariedadeCarne variedade;

    public Carne(VariedadeCarne v, double prot, double cal, double peso) {

        super(prot, cal, peso);
        variedade = v;
    }

    public VariedadeCarne getVariedade() {

        return variedade;
    }

    @Override
    public int hashCode() {

        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((variedade == null) ? 0 : variedade.hashCode());
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
        Carne other = (Carne) obj;
        if (variedade != other.variedade)
            return false;
        return true;
    }

    @Override
    public String toString() {

        return "Carne " + variedade + ", " + super.toString();
    }
}

enum VariedadeCarne {

    vaca, frango, porco, peru, outra;
}