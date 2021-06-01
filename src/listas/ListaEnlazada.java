package listas;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

//LISTA – IMPLEMENTACION ENLAZADA

public class ListaEnlazada<E> implements List<E> {

	// Clase Link
	@SuppressWarnings("hiding")
	class Link<E> {

		private E element;
		private Link<E> next;

		// Constructors

		Link(E it, Link<E> nextval) {
			element = it;
			next = nextval;
		}

		Link(Link<E> nextval) {
			next = nextval;
		}

		// Metodos

		// Return next field
		Link<E> next() {
			return next;
		}

		// Set next field
		Link<E> setNext(Link<E> nextval) {
			return next = nextval;
		}

		// Return element field
		E element() {
			return element;
		}

		// Set element field
		E setElement(E it) {
			return element = it;
		}
	}

	private Link<E> head; // Pointer to list header
	private Link<E> tail; // Pointer to last element
	protected Link<E> curr; // Access to current element
	int cnt; // Size of list

	// Constructores

	ListaEnlazada(int size) {
		this();
	} // Constructor -- Ignore size

	ListaEnlazada() {
		curr = tail = head = new Link<E>(null); // Create header
		cnt = 0;
	}

	// Metodos

	public void clear() {
		head.setNext(null);
		// Drop access to links
		curr = tail = head = new Link<E>(null); // Create header
		cnt = 0;
	}

	/** Insert ”it ” at current position */
	public void insert(E it) {
		curr.setNext(new Link<E>(it, curr.next()));
		if (tail == curr)
			tail = curr.next(); // New tail
		cnt++;
	}

	// Append it to list
	public void append(E it) {
		tail = tail.setNext(new Link<E>(it, null));
		cnt++;
	}

	public E remove() {
		
          if (curr.next() == null) 
        	  return null;
        
          E it = curr.next().element();
          if (tail == curr.next())
        	  tail = curr;
          
          curr.setNext(curr.next().next());
          cnt--;
          return it;
    }

	public void moveToStart() {
		curr = head;
	}

	public void moveToEnd() {
		curr = tail;
	}

	public void prev() {
		if (curr == head) { // No previous element
			return;
		}
		Link<E> temp = head;

		// Recorrer hasta encontrar el elemento anterior.
		while (temp.next() != curr) {
			temp = temp.next();
		}
		curr = temp;
	}

	/** Move curr one step right ; no change if now at end */
	public void next() {
		if (curr != tail)
			curr = curr.next();
	}

	public int length() {
		return cnt;
	}

	/**
	 *
	 * @return The position of the current element
	 */
	public int currPos() {
		Link<E> temp = head;
		int i;
		for (i = 0; curr != temp; i++) {
			temp = temp.next();
		}
		return i;
	}

	public void moveToPos(int pos) {
		assert (pos >= 0) && (pos <= cnt) : "Position out of range";
		curr = head;
		for (int i = 0; i < pos; i++)
			curr = curr.next();
	}

	public E getValue() {
		if (curr.next() == null)
			return null;
		return curr.next().element();
	}

	/**
	 * METODOS A IMPLEMENTAR
	 */

	@Override
	public boolean add(E arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void add(int arg0, E arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean addAll(Collection<? extends E> arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addAll(int arg0, Collection<? extends E> arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean contains(Object arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsAll(Collection<?> arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public E get(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int indexOf(Object arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterator<E> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int lastIndexOf(Object arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ListIterator<E> listIterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ListIterator<E> listIterator(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean remove(Object arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public E remove(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean removeAll(Collection<?> arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean retainAll(Collection<?> arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public E set(int arg0, E arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<E> subList(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object[] toArray() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T[] toArray(T[] arg0) {
		// TODO Auto-generated method stub
		return null;
	}
}
