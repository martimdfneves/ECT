package aula13_1;

public class Provincia {

    private String nome;
    private int populacao;
    private String governador;

    public Provincia(String nome, int populacao, String governador) {

        this.nome = nome;
        this.populacao = populacao;
        this.governador = governador;
    }

    public String getNome() {

        return nome;
    }

    public int getPopulacao() {

        return populacao;
    }

    public String getGovernador() {

        return governador;
    }

    @Override
    public int hashCode() {

        final int prime = 31;
        int result = 1;
        result = prime * result + ((governador == null) ? 0 : governador.hashCode());
        result = prime * result + ((nome == null) ? 0 : nome.hashCode());
        result = prime * result + populacao;
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
        Provincia other = (Provincia) obj;
        if (governador == null) {
            if (other.governador != null)
                return false;
        } else if (!governador.equals(other.governador))
            return false;
        if (nome == null) {
            if (other.nome != null)
                return false;
        } else if (!nome.equals(other.nome))
            return false;
        if (populacao != other.populacao)
            return false;
        return true;
    }

    @Override
    public String toString() {

        return "Provincia [nome=" + nome + ", populacao=" + populacao + ", governador=" + governador + "]";
    }
}