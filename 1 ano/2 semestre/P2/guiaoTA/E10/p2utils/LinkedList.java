package p2utils;

public class LinkedList<E> {
	
  private Node<E> first = null;
  private Node<E> last = null;
  private int size = 0;

  /** {@code LinkedList} constructor, empty so far.
   */
  public LinkedList() { }

  /** Returns the number of elements in the list.
   * @return Number of elements in the list
   */
  public int size() { return size; }

  /** Checks if the list is empty.
   * @return  {@code true} if list empty, otherwise {@code false}.
   */
  public boolean isEmpty() { return size == 0; }

  /** Returns the first element in the list.
   * @return  First element in the list
   */
  public E first() {
    assert !isEmpty(): "empty!";

    return first.elem;
  }

  /** Returns the last element in the list.
   * @return Last element in the list
   */
  public E last() {
    assert !isEmpty(): "empty!";

    return last.elem;
  }

  /** Adds the given element to the start of the list.
   * @param e the element to add
   */
  public void addFirst(E e) {
    first = new Node<>(e, first);
    if (isEmpty())
      last = first;
    size++;

    assert !isEmpty(): "empty!";
    assert first().equals(e) : "wrong element";
  }

  /** Adds the given element to the end of the list.
   * @param e the element to add
   */
  public void addLast(E e) {
    Node<E> newest = new Node<>(e);
    if (isEmpty())
      first = newest;
    else
      last.next = newest;
    last = newest;
    size++;

    assert !isEmpty(): "empty!";
    assert last().equals(e) : "wrong element";
  }

  /** Removes the first element in the list.
   */
  public void removeFirst() {
    assert !isEmpty(): "empty!";
    first = first.next;
    size--;
    if (isEmpty())
      last = null;
  }

  /** Removes all elements.
   */
  public void clear() {
    first = last = null;
    size = 0;
  }

  /** Returns a string representing the list contents.
   * @return A string representing the list contents
   */
  public String toString() {
    String sep = "";
    String s = "";
    for (Node<E> n = first; n != null; n = n.next) {
      s += sep + n.elem;
      sep = ", ";
    }
    return "[" + s + "]";
  }

  public int count (E e) {
	return count(first, e);  
  }
  private int count(Node<E> e, E i) {
	if (e==null) {return 0;}
	else {
		if(e.elem==i) {
			return 1+count(e.next, i);
		}
		else {
			return count(e.next, i);
		}
	}  
  }
  
  public int indexOf(E e) {
	return indexOf(first, e, 0);  
  }
  
  private int indexOf(Node<E> n, E e1, int pos) {
	if (n==null) {return -1;}
	else {
		if (n.elem.equals(e1)) {
			return pos;
		}
		else {
			return indexOf(n.next, e1, pos+=1);
		}
	}  
  }
  
  public LinkedList<E> clonereplace(E e11, E e12) {
	return clonereplace(e11, e12, first);  
  }
  @SuppressWarnings("unchecked")
  private LinkedList<E> clonereplace(E e11, E e12, Node<E> n) {
	if (n==null) {return new LinkedList<E>();}
	else {
		LinkedList clonada = clonereplace(e11, e12, n.next);
		if ((n.elem).equals(e11)) {
			clonada.addFirst(e12);
		}
		else {
			clonada.addFirst(n.elem);
		}
	return clonada;
	}  
  }
  
  public LinkedList<E> clonesublist(int start, int end){
    return clonesublist(start, end, first, 0);
  }
  
  private LinkedList<E> clonesublist(int start, int end, Node<E> n, int cicles) {
	if (start==cicles) {return new LinkedList<E>();}  
	else {
		LinkedList<E> clonada = clonesublist(start, end, n, cicles+=1);
		if (cicles >= start && cicles <= end) {
			clonada.addFirst(n.elem);
		}
	return clonada;	
	}
  }
  
  public LinkedList<E> cloneexcept(int start, int end){
	return cloneexcept(start, end, first, 0);  
  }
  
  private LinkedList<E> cloneexcept(int start, int end, Node<E> n, int cicles) {
	if (start==cicles) {return new LinkedList<E>();}  
	else {
		LinkedList<E> clonada = cloneexcept(start, end, n, cicles+=1);
		if (cicles<start || cicles>end){
			clonada.addFirst(n.elem);
		}
	return clonada;	
	}  
  }
  
  public void remove(int start, int end) {
	first=remove(start, end , first, 0);  
	size=end-start;
  }
  
  private Node<E> remove(int start, int end, Node<E> n, int cicles) {
	if (n==null){return null;}
	else {
		Node<E> recN = remove(start, end, n.next, cicles++);
      if (cicles >= start && cicles < end) {
        return recN;
      } 
      else {
        if (n.next == null) {
          last = n;
        } 
        else {
          return n.next;
        }
      }
	}  
	return n;
  }
}

