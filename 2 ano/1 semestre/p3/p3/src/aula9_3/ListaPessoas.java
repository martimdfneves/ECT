package aula9_3;

import java.util.*;
import aula1_2.*;

public class ListaPessoas {
	
	private List<Pessoa> lista;
	
	public ListaPessoas() {
		
		lista=new LinkedList<Pessoa>();
	}
	
	public boolean add (Pessoa p) {
		
		return lista.add(p);
	}
	
	public boolean remove (Pessoa p) {
		
		return lista.remove(p);
	}
	
	public int TotalPessoas() {
		
		return lista.size();
	}
	
	public Iterator<Pessoa> iterator() {
		
		return lista.iterator();
	}
}