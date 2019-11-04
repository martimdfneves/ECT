package aula2_1;

public class Socio {
	
	private int nCC;
	private String nome;
	private Data birthdate;
	private Data datainscricao;
	private int nsocio;
	private static int seqSoc = 1;
	private int maxloans;
	private int loans;
	
	public Socio(Data data, Data data2, String n, int cc, int loans) {
		nsocio = seqSoc++;
		datainscricao = data;
		nome = n;
		nCC = cc;
		birthdate = data2;
		maxloans = loans;
		this.loans = 0;
	}

	public int getnCC() {
		return nCC;
	}

	public String getNome() {
		return nome;
	}
	
	public Data getBirthdate() {
		return birthdate;
	}

	public Data getDatainscricao() {
		return datainscricao;
	}

	public int getNsocio() {
		return nsocio;
	}

	public int getLoans() {
		return loans;
	}
	
	public boolean newLoan() {
		
		if (loans < maxloans)
		{
			loans++;
			return true;
		}	
		return false;
	}
	
	public void returnvideo() {
		
		loans--;
	}
}
