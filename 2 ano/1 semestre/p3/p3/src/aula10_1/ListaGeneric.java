package aula10_1;

import java.util.*;

public class ListaGeneric <T> implements Iterable<T> {
	
	private LinkedList<T> lista;
	
	public ListaGeneric() {
		
		lista=new LinkedList<T>();
	}
	
	public boolean addElem(T elem) {
		
		return lista.add(elem);
	}
	
	public boolean removeElem(T elem) {
		
		return lista.remove(elem);
	}
	
	public int totalElem() {
		
		return lista.size();
	}
	
	public class ListIterator<T> implements Iterator<T> {
		
		int index;
		
		public ListIterator() {
			
			index = 0;
		}
		
		@Override
		public boolean hasNext() {
			
			return lista.size() == index;
		}

		@Override
		public T next() {
			
			T a = (T)lista.get(index);
			index++;
			return a;
		}
		
	}

	@Override
	public Iterator<T> iterator() {
		
		return new ListIterator<>();
	}
}