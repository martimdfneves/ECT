package p2utils;

public class LinkedList<E> {

    private Node<E> first = null;
    private Node<E> last = null;
    private int size = 0;

    /**
     * {@code LinkedList} constructor, empty so far.
     */
    public LinkedList() {
    }

    /**
     * @return Number of elements in the list
     */
    public int size() {
        return size;
    }

    /**
     * Checks if the list is empty
     *
     * @return {@code true} if list empty, otherwise {@code false}.
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * @return First element in the list
     */
    public E first() {
        assert !isEmpty() : "empty!";

        return first.elem;
    }

    /**
     * @return Last element in the list
     */
    public E last() {
        assert !isEmpty() : "empty!";

        return last.elem;
    }

    /**
     * Adds a new element to the start of the list
     */
    public void addFirst(E e) {
        first = new Node<>(e, first);
        if (isEmpty())
            last = first;
        size++;

        assert !isEmpty() : "empty!";
        assert first().equals(e) : "wrong element";
    }

    /**
     * Adds a new element to the end of the list
     */
    public void addLast(E e) {
        Node<E> newest = new Node<>(e);
        if (isEmpty())
            first = newest;
        else
            last.next = newest;
        last = newest;
        size++;

        assert !isEmpty() : "empty!";
        assert last().equals(e) : "wrong element";
    }

    /**
     * Removes the first element in the list
     */
    public void removeFirst() {
        assert !isEmpty() : "empty!";
        first = first.next;
        size--;
        if (isEmpty())
            last = null;
    }

    /**
     * Removes all elements
     */
    public void clear() {
        first = last = null;
        size = 0;
    }


    /**
     * Prints all elements, one per line.
     */
    public void print() {
        print(first);
    }

    private void print(Node<E> n) {
        if (n != null) {
            System.out.println(n.elem);
            print(n.next);
        }
    }

    /**
     * Checks if the given element exists in the list
     *
     * @param e an element
     * @return {@code true} if the element exists and {@code false} otherwise
     */
    public boolean contains(E e) {
        return contains(first, e);
    }

    private boolean contains(Node<E> n, E e) {
        if (n == null) return false;
        if (n.elem == null) return e == null; //dispensável, se impedirmos elem==null
        if (n.elem.equals(e)) return true;
        return contains(n.next, e);
    }


    // inicia a clonagem
    public LinkedList<E> clone() {
        return clone(first);
    }

    // implementação da clonagem recursivamente
    private LinkedList<E> clone(Node<E> no) {
        if (no == null) {
            return new LinkedList();
        } else {
            LinkedList cloned = clone(no.next);
            cloned.addFirst(no.elem);
            return cloned;
        }
    }

    // inicia a clonagem invertidamente
    public LinkedList<E> reverse() {
        return reverse(first);
    }

    // implementa a clonagem invertida recursivamente
    private LinkedList<E> reverse(Node<E> no) {
        if (no == null) {
            return new LinkedList();
        } else {
            LinkedList invert = reverse(no.next);

            // a diferença entre esta e a outra está no método que vai adicionar
            invert.addLast(no.elem);
            return invert;
        }
    }

    public E get(int pos) {
        assert pos < size;
        assert pos >= 0;

        return get(first, pos);
    }

    // implementação recusrsiva
    // a maneira comentada retorna o mesmo resultado
    private E get(Node<E> no, int pos) {

        if (pos == 0) {
            return no.elem;
        } else {
            return get(no.next, pos - 1);
        }

        // advanced return call
        //return pos == 0 ? no.elem : get(no.next, pos - 1);
    }

    // método que concatena as listas
    public LinkedList<E> concatenate(LinkedList<E> listaDada) {
        return concatenate(first, listaDada);
    }

    // basicamente copia as referências
    private LinkedList<E> concatenate(Node<E> no, LinkedList<E> listaDada) {
        if (no == null) {
            return listaDada.clone();
        } else {
            LinkedList<E> posConcatena = this.concatenate(no.next, listaDada);
            posConcatena.addFirst(no.elem);
            return posConcatena;
        }
    }

    /* este método é especial porque a sua chamada na função
     * tem de ser do tipo node enquanto que fora dela tem de ser do tipo void
     * pois não queremos saber de como a lista fica
     * apenas queremos que seja removido o elemento alpha
     */
    public void remove(E remov) {
        assert contains(remov) : "O elemento não existe";

        first = remove(first, remov);
        size--;

        // se removermos o único elemento da lista
        if (isEmpty()) {
            last = null;
        }
    }

    private Node<E> remove(Node<E> no, E elemento) {

        // temos de utilizar o metodo equals() pois o tipo pode ser uma string
        if (no.elem.equals(elemento)) {
            return no.next;
        } else {
            no.next = remove(no.next, elemento);

            //se removermos o ultimo membro da lista
            if (no.next == null) {
                last = no;
            }

            return no;
        }
    }
}
