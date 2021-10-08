package aula6_1;

public abstract class Alimento implements Comparable<Alimento> {

    private double proteina;
    private double calorias;
    private double peso;

    public Alimento(double proteina, double calorias, double peso) {

        this.proteina = proteina;
        this.calorias = calorias;
        this.peso = peso;
    }

    public double getProteina() {

        return proteina;
    }

    public double getCalorias() {

        return calorias;
    }

    public double getPeso() {

        return peso;
    }

    @Override
    public int compareTo(Alimento alimento) {

        return (int) (calorias - alimento.calorias);
    }

    @Override
    public int hashCode() {

        final int prime = 31;
        int result = 1;
        long temp;
        temp = Double.doubleToLongBits(calorias);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(peso);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(proteina);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Alimento other = (Alimento) obj;
        if (Double.doubleToLongBits(calorias) != Double.doubleToLongBits(other.calorias))
            return false;
        if (Double.doubleToLongBits(peso) != Double.doubleToLongBits(other.peso))
            return false;
        if (Double.doubleToLongBits(proteina) != Double.doubleToLongBits(other.proteina))
            return false;
        return true;
    }

    @Override
    public String toString() {

        return "Alimento [proteina=" + proteina + ", calorias=" + calorias + ", peso=" + peso + "]";
    }
}
