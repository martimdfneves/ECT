package aula7_1;

public class Voo {

    private Hora hora;
    private String flyID;
    private Companhia companhia;
    private String origem;
    private Hora atraso;
    private String obs;

    public Voo(String hora, String flyID, Companhia companhia, String origem, String atraso) {

        String h[] = hora.split(":");
        this.hora = new Hora(Integer.parseInt(h[0]), Integer.parseInt(h[1]));
        this.flyID = flyID;
        this.companhia = companhia;
        this.origem = origem;
        h = atraso.split(":");
        this.atraso = new Hora(Integer.parseInt(h[0]), Integer.parseInt(h[1]));
        makeObs();
    }

    public Voo(String hora, String flyID, Companhia companhia, String origem) {

        this(hora, flyID, companhia, origem, "0:0");
    }

    private void makeObs() {

        if (atraso.equals(new Hora(0, 0)))
            obs = "";
        else
            obs = "Previsto: " + Hora.addTimes(hora, atraso);
    }

    public Hora getHora() {

        return hora;
    }

    public String getFlyID() {

        return flyID;
    }

    public Companhia getCompanhia() {

        return companhia;
    }

    public String getOrigem() {

        return origem;
    }

    public Hora getAtraso() {

        return atraso;
    }

    public String getObs() {

        return obs;
    }

    @Override
    public int hashCode() {

        final int prime = 31;
        int result = 1;
        result = prime * result + ((atraso == null) ? 0 : atraso.hashCode());
        result = prime * result + ((companhia == null) ? 0 : companhia.hashCode());
        result = prime * result + ((flyID == null) ? 0 : flyID.hashCode());
        result = prime * result + ((hora == null) ? 0 : hora.hashCode());
        result = prime * result + ((obs == null) ? 0 : obs.hashCode());
        result = prime * result + ((origem == null) ? 0 : origem.hashCode());
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
        Voo other = (Voo) obj;
        if (atraso == null) {
            if (other.atraso != null)
                return false;
        } else if (!atraso.equals(other.atraso))
            return false;
        if (companhia == null) {
            if (other.companhia != null)
                return false;
        } else if (!companhia.equals(other.companhia))
            return false;
        if (flyID == null) {
            if (other.flyID != null)
                return false;
        } else if (!flyID.equals(other.flyID))
            return false;
        if (hora == null) {
            if (other.hora != null)
                return false;
        } else if (!hora.equals(other.hora))
            return false;
        if (obs == null) {
            if (other.obs != null)
                return false;
        } else if (!obs.equals(other.obs))
            return false;
        if (origem == null) {
            if (other.origem != null)
                return false;
        } else if (!origem.equals(other.origem))
            return false;
        return true;
    }

    @Override
    public String toString() {

        return hora.toString() + "\t" + flyID + "\t" + companhia.toString() + "\t" + origem + (obs.equals("") ? "\n" : "\t" + atraso.toString() + "\t" + obs + "\n");
    }
}