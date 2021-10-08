package aula13_1;

import java.util.*;

public class Pais extends Estado {

    private List<Regiao> regioes;
    private int poptotal;

    public Pais(String nome, Localidade capital, List<Regiao> regioes, int poptotal) {

        super(nome, 0, capital);
        this.regioes = regioes;
        this.poptotal = poptotal;
        pop();
    }

    private void pop() {

        for (Regiao regiao : regioes) {

            poptotal += regiao.getPopulacao();
        }
    }

    public Pais(String string, Localidade capital2) {

        this(string, capital2, new ArrayList<>(), 0);
    }

    public Pais(String string) {

        this(string, null, new ArrayList<>(), 0);
    }

    public List<Regiao> getRegioes() {

        return regioes;
    }

    public void addRegiao(Regiao est1) {

        regioes.add(est1);
        poptotal += est1.getPopulacao();
    }

    @Override
    public int hashCode() {

        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + poptotal;
        result = prime * result + ((regioes == null) ? 0 : regioes.hashCode());
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
        Pais other = (Pais) obj;
        if (poptotal != other.poptotal)
            return false;
        if (regioes == null) {
            if (other.regioes != null)
                return false;
        } else if (!regioes.equals(other.regioes))
            return false;
        return true;
    }

    @Override
    public String toString() {

        return "Pais [regioes=" + regioes + ", poptotal=" + poptotal + ", getCapital()=" + getCapital()
                + ", toString()=" + super.toString() + ", getNome()=" + getNome() + ", getPopulacao()=" + getPopulacao()
                + ", getClass()=" + getClass() + "]";
    }
}