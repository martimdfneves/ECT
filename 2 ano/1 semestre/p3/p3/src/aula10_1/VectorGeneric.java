package aula10_1;

import java.util.*;

public class VectorGeneric <T> implements Iterable<T> {
	
	private T vector[];
	private int size;
	
	public VectorGeneric() {
		
		vector = (T[]) new Object[1];
		size = 0;
	}
	
	public boolean totalElem() {
		
		return vector.length==size;
	}
	
	private void extendArray() {
		
		T tmp[] = (T[]) new Object[vector.length * 2];
		System.arraycopy(vector, 0, tmp, 0, vector.length);
		vector = tmp;
	}
	
	public boolean addElem(T elem) {
		
		try {
			
			if (totalElem()) {
				
				extendArray();
				vector[size++] = elem;
			}
			else
				vector[size++] = elem;
			return true;
		}
		catch (Exception e) {
			
			return false;
		}
	}
	
	public boolean removeElem(T elem) {
		
		boolean found = false;
		for (int i = 0; i < size; i++) {
			
			if (found)
				vector[i] = vector[i+1];
			else if (elem.equals(vector[i])) {
				
				vector[i] = vector[i+1];
				found = true;
			}
		}
		return found;
	}
	
	public class VetorIterator implements Iterator<T> {
		
		private int index;
		
		public VetorIterator() {
			
			index=0;
		}
		
		@Override
		public void remove() {
			
			removeElem(vector[index]);
		}
		
		@Override
		public boolean hasNext() {
			
			return index < size;
		}

		@Override
		public T next() {
			
			return vector[index++];
		}	
	}
	
	public Iterator<T> iterator() {
		
		return this.new VetorIterator();
	}
}