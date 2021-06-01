package arboles;

import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class Monticulo<E> extends AbstractSet<E> {

	/**
	 * Lista con los elementos del monticulo
	 */
	protected List<E> elementos;
	/**
	 * Comparador para elementos genericos
	 */
	protected Comparator<E> comparator;

	// Constructor
	// ----------------------------------------------------------------------
	public Monticulo() {
		this.elementos = new ArrayList<E>();
	}

	public Monticulo(Collection<E> c) {
		this.elementos = new ArrayList<E>();
	}

	public Monticulo(Comparator<E> c) {
		this.elementos = new ArrayList<E>();
		this.comparator = c;
	}

	public Monticulo(Collection<E> c, Comparator<E> comp) {
		this.elementos = new ArrayList<E>();
		this.comparator = comp;

		// Se insertan elementos en el monticulo
		Iterator<E> it = c.iterator();

		while (it.hasNext()) {
			this.add(it.next());
		}

	}

	// Metodos internos
	// -----------------------------------------------------------------------

	private E getHijoDer(E element) {

		// Se obtiene el lemento de la lista,
		// hay que tener en cuenta que la lista comienza en 0
		// y el monticulo comienza en 1
		int indexList = elementos.indexOf(element);
		int indexMont = indexList + 1;

		// Se obtiene la posicion del hijo en el monticulo
		int posHijoMont = (indexMont * 2) + 1;
		int posHijoList = posHijoMont - 1;

		if (elementos.size() - 1 < posHijoList) {
			// El elemento no tiene hijo derecho
			return null;
		} else {
			return elementos.get(posHijoList);
		}

	}

	private E getHijoIzq(E element) {

		// Se obtiene el lemento de la lista,
		// hay que tener en cuenta que la lista comienza en 0
		// y el monticulo comienza en 1
		int indexList = elementos.indexOf(element);
		int indexMont = indexList + 1;

		// Se obtiene la posicion del hijo en el monticulo
		int posHijoMont = (indexMont * 2);
		int posHijoList = posHijoMont - 1;

		if (elementos.size() - 1 < posHijoList) {
			// El elemento no tiene hijo derecho
			return null;
		} else {
			return elementos.get(posHijoList);
		}
	}

	private E getPadre(E element) {

		// Se obtiene la posicion del elemento
		int indexMonticulo = elementos.indexOf(element) + 1;
		// Se calcula la posicion del padre
		int indexPadreList = (indexMonticulo / 2) - 1;

		if (indexPadreList >= 0)
			return elementos.get(indexPadreList);

		else
			return null;

	}

	private void intercambiar(E e1, E e2) {

		int indexE1 = elementos.indexOf(e1);
		int indexE2 = elementos.indexOf(e2);

		E e1_aux = elementos.get(indexE1);

		elementos.set(indexE1, e2);
		elementos.set(indexE2, e1_aux);

	}

	public boolean add(E element) {

		if (elementos.contains(element))
			return false;

		// Se introduce el elemento en la ultima posicion del arbol
		elementos.add(element);

		// Se intenta flotar hasta que se encuentre su posicion
		E padre = this.getPadre(element);
		//if (padre != null) {

			while (padre != null && comparar(element, padre) > 0  ) {
				// El hijo es mayor quel padre
				// Se intercambian
				intercambiar(element, padre);

				// Se comprueba de nuevo con el nuevo padre
				padre = this.getPadre(element);
			}
		//}

		return true;
	}

	public E removeFirst() {

		if (elementos.isEmpty())
			return null;
		
		if (elementos.size() == 1) {
			E ret = elementos.get(0);
			elementos.remove(0);
			return ret;
		}
		

		// Se almacena el elemento a devolver
		E ret = elementos.get(0);
		// Se almacena el elemento a hundir
		E hun = elementos.get(elementos.size()-1);

		// Se intercambian el primero y el ultimo
		intercambiar(ret, hun);
		// se elimina el ultimo (antiguo primero)
		elementos.remove(elementos.size()-1);

		// La cima se hunde hasta llegar a su posicion
		// ---
		boolean asentado = false;
		while (!asentado) {

			// Se obtiene el mayor de los hijos
			E hIzq = getHijoIzq(hun);
			E hDer = getHijoDer(hun);

			
			//Se obtiene el hijo mayor para intercambiar 
			E max = null;

			//Control de es hoja >> FIN
			if (hIzq == null && hDer == null) 
				break;
			
			//Control solo tiene hIzq
			else if (hDer == null){
					max = hIzq;
			}
			
			//Tiene 2 hijos
			//Se obtiene el maximo
			else {
				
				int compare = comparar(hIzq, hDer);

				//Izquierdo mayor
				// Eleccion del hijo izquierdo
				if (compare > 0)
					max = hIzq;

				// Derecho mayor
				// Elecion hijo derecho
				else if (compare < 0)
					max = hDer;
				
				//Son iguales
				//Elecion aleatoria
				else 
					if (Math.random() < 0.5)
						max = hIzq;
					else
						max = hDer;
			}
			

			// Si el hijo mayor es mayor que el padre se intercambian
			if (comparar(max, hun) > 0) {
				intercambiar(max, hun);
			} else {
				asentado = true;
			}
		}

		return ret;
	}

	@Override
	public Iterator<E> iterator() {
		return elementos.iterator();
	}

//	protected List<E> inOrdenRecursivo(int index) {
//
//		List<E> listaRecorridos = new ArrayList<E>();
//
//		if (elementos.get() != null) {
//			inOrdenRecursivo(actual.getIzq(), listaRecorridos);
//			listaRecorridos.add(actual.getDato());
//			inOrdenRecursivo(actual.getDer(), listaRecorridos);
//		}
//	}

	@Override
	public int size() {
		return elementos.size();
	}

	/**
	 * Metodo que permite comparar dos elementos pasados por parámetro.
	 * 
	 * @param o1
	 * @param o2
	 * @return
	 * @throws ClassCastException
	 */
	@SuppressWarnings("unchecked")
	protected int comparar(E o1, E o2) throws ClassCastException {
		if (comparator != null) // Si hay un comparador, se emplea
			return comparator.compare(o1, o2);
		else // Si no, hay que suponer que sean Comparable
			return ((Comparable<E>) o1).compareTo(o2);
	}

}
