package aula1_2;

public class Pessoa {
	
	private int cc;
	private Data data;
	private String nome;
	private int date;
	
	public Pessoa(int cardnumber,Data date,String name) {
		
		cc=cardnumber;
		data=date;
		nome=name;	
	}
	
	public Pessoa(int cardnumber,int date,String name) {
		
		cc=cardnumber;
		this.date=date;
		nome=name;	
	}
	
	public String toString() {
		
		return "cc=" + cc + ", nome=" + nome + ", dataNasc=" + data;
	}
	
	public String getName() {
		
		return nome;
	}
	
	public Data getDate() {
		
		return data;
	}

	public int getNumber() {
	
		return cc;
	}
	
	public int CompareCC(Pessoa toCompare) {
		
		if(toCompare.cc < this.cc)
			return -1;
		else if (toCompare.cc == this.cc)
			return 0;
		else
			return 1;
	}

	public int CompareNome(Pessoa toCompare) {
		
		return (this.nome).compareTo(toCompare.nome);
	}
	
}
