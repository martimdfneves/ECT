public class Tarefa {
	
	private Data inicio;
	private Data fim;
	private String nome;
		
	public Tarefa (Data ini, Data fim, String tar) {
		inicio=ini;
		this.fim=fim;
		nome=tar;
		System.out.println(nome);
		System.out.println(inicio);
		System.out.println(fim);
		System.out.println(ini.compareTo(fim));
		
		assert ini.compareTo(fim)==-1:"data invalida";
		assert tar!="":"nome invalido";
	}
	
	public Data fim() {
		return this.fim;	
	}
	public Data inicio() {
		return inicio;	
	}
	public String nome() {
		return nome;	
	}
 /** Devolve esta data segundo a norma ISO 8601. */
	public String toString() {
		return String.format("%s --- %s: %s", inicio, fim, nome);
  }
}


