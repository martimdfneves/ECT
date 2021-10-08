package aula2_1;

public class Aluno extends Socio {

    private int nmec;
    private String curso;

    public Aluno(Data data, Data data2, String n, int cc, int loans, int nmec, String curso) {

        super(data, data2, n, cc, loans);
        this.nmec = nmec;
        this.curso = curso;
    }

    public int getNmec() {
        return nmec;
    }

    public String getCurso() {
        return curso;
    }

    @Override
    public String toString() {
        return " S�cio n�: " + super.getNsocio() + " -> Aluno n�: " + nmec + ", Nome: " + super.getNome() + " Curso: " + curso;
    }

}
