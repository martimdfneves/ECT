package aula5_2;

public class CarroPolicia extends Automovel implements Policia {

    private Tipo t;
    private String id;

    public CarroPolicia(String marca, String cor, int ano, int cc, int potencia, int vmax, double combustivel, double consumo, String matricula, int lotacao, Tipo t, String id) {

        super(marca, cor, ano, cc, potencia, vmax, combustivel, consumo, matricula, lotacao);
        this.t = t;
        this.id = id;
    }

    public Tipo getTipo() {
        return t;
    }

    public String getID() {
        return id;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((t == null) ? 0 : t.hashCode());
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
        CarroPolicia other = (CarroPolicia) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (t != other.t)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "CarroPolicia [t=" + t + ", id=" + id + "]";
    }
}
