package aula5_2;

public class Bicicleta extends Veiculo {

    private int ano;

    public Bicicleta(int rodas, String marca, String cor, int ano) {

        super(rodas, marca, cor);
        this.ano = ano;
    }

    public int getAno() {
        return ano;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ano;
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
        Bicicleta other = (Bicicleta) obj;
        if (ano != other.ano)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Bicicleta [ano=" + ano + "]";
    }
}
