package aula4_1;

import aula1_2.*;

public class Professor extends Pessoa{
	
	private static int func=0;
	private int nfunc;
	private Data admissao;
	
	public Professor(int cardnumber, Data date, String name, Data admissao) {
		
		super(cardnumber, date, name);
		nfunc=func++;
		this.admissao = admissao;
	}
	
	public Professor(int cardnumber, Data date,String name) {
		
		this(cardnumber,date,name, new Data());
	}

	public int getNfunc() {
		
		return nfunc;
	}

	public Data getAdmissao() {
		
		return admissao;
	}

	@Override
	public String toString() {
		
		return "Professor [nfunc=" + nfunc + ", admissao=" + admissao + "]";
	}
}
