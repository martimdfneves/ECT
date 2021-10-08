package aula5_2;

public class Automovel extends Motorizado {

    private int lotacao;

    public Automovel(String marca, String cor, int ano, int cc, int potencia, int vmax, double combustivel, double consumo, String matricula, int lotacao) {

        super(4, marca, cor, ano, cc, potencia, vmax, combustivel, consumo, matricula);
        this.lotacao = lotacao;
    }

    public int getLotacao() {
        return lotacao;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + lotacao;
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
        Automovel other = (Automovel) obj;
        if (lotacao != other.lotacao)
            return false;
        return true;
    }
}
