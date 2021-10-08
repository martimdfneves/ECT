package aula6_1;

public class Cereal extends Alimento {

    private String nome;

    public Cereal(String nome, double proteina, double calorias, double peso) {

        super(proteina, calorias, peso);
        this.nome = nome;
    }

    public String getNome() {

        return nome;
    }

    @Override
    public int hashCode() {

        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((nome == null) ? 0 : nome.hashCode());
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
        Cereal other = (Cereal) obj;
        if (nome == null) {
            if (other.nome != null)
                return false;
        } else if (!nome.equals(other.nome))
            return false;
        return true;
    }

    @Override
    public String toString() {

        return "Cereal, " + super.toString();
    }
}
