package aula2_1;

import java.util.*;

public class ListaSocios {

    private Hashtable<Integer, Aluno> listaAl;
    private Hashtable<Integer, Funcionario> listFunc;

    public ListaSocios() {
        listaAl = new Hashtable<Integer, Aluno>();
        listFunc = new Hashtable<Integer, Funcionario>();
    }

    public void putFunc(Funcionario s) {

        listFunc.put(s.getNsocio(), s);
    }

    public void putAluno(Aluno s) {

        listaAl.put(s.getNsocio(), s);
    }

    public Funcionario remFunc(int s) {

        return listFunc.remove(s);
    }

    public Aluno remAl(int s) {

        return listaAl.remove(s);
    }

    public Funcionario getFunc(int s) {

        return listFunc.get(s);
    }

    public Aluno getAluno(int s) {

        return listaAl.get(s);
    }

    public boolean temAluno(int s) {

        return listaAl.contains(s);
    }

    public boolean temFunc(int s) {

        return listFunc.contains(s);
    }

    public int lastSocNumb() {

        return listaAl.size() + listFunc.size();
    }

    public boolean containsAlunSoc(int s) {

        return listaAl.containsKey(s);
    }

    public boolean containsFunSoc(int s) {

        return listFunc.containsKey(s);
    }
}
