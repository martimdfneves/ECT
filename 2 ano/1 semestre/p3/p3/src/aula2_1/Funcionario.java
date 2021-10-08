package aula2_1;

public class Funcionario extends Socio {

    private int nfunc;
    private int nfiscal;

    public Funcionario(Data data, Data data2, String n, int cc, int loans, int nfunc, int nfiscal) {

        super(data, data2, n, cc, loans);
        this.nfunc = nfunc;
        this.nfiscal = nfiscal;
    }

    public int getNfunc() {
        return nfunc;
    }

    public int getNfiscal() {
        return nfiscal;
    }

    @Override
    public String toString() {

        return " S�cio n�: " + super.getNsocio() + " -> Funcion�rio n�: " + nfunc + ", Nome: " + super.getNome() + " N�mero fiscal: " + nfiscal;
    }


}
