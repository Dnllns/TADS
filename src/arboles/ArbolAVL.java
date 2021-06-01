package arboles;


import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Clase que implementa un Arbol AVL (con reequilibrio)
 * 
 * @author Daniel Alonso Báscones
 * @author Alberto García Gutiérrez
 *
 * @param <E>
 */
public class ArbolAVL<E> extends ArbolBB<E> {

	/**
	 * Constructor
	 */
	public ArbolAVL() {
		super();
	}

	/**
	 * Inserta un dato en el arbol
	 * 
	 * @param e el dato que se quiere insertar
	 * @return true si ha insertado, false sinó
	 */
	@Override
	public boolean add(E e) {

		// Buscar el nodo e en el arbol
		// Si se encuentra se obtiene una lista con el mismo (pos 0) y su padre (pos 1)
		List<Nodo> busqueda = super.buscar(super.raiz, e);

		if (busqueda.get(0) != null)
			// Si el nodo ya existe en el arbol, no se añade
			return false;
		else {
			// El nodo no existe, le cuelgo del padre y incremento el contador
			super.insertar(busqueda.get(1), e);
			numElementos++;

			// Reequilibrar el arbol
			for (int i = 1; i < busqueda.size() - 1; i++) {
				if (calcularFactorEquilibrio(busqueda.get(i)) == 2 || calcularFactorEquilibrio(busqueda.get(i)) == -2) {
					reequilibrioAVL(busqueda.get(i), busqueda.get(i + 1));
				}
			}
			return true;
		}
	}

	/**
	 * Elimina un datos del arbol
	 * 
	 * @param o el dato que se desea eliminar
	 * @return true si se ha eliminado, false sinó
	 */
	@Override

	public boolean remove(Object o) {

		// Se intenta castear o a el tipo generico
		E borrado;
		try {
			borrado = (E) o;
		} catch (ClassCastException cce) {
			// ha habido un error durante el casteo
			System.out.println(cce.getLocalizedMessage());
			return false;
		}

		// Se busca el nodo que contiene el dato a eliminar
		List<Nodo> encontrado = super.buscar(super.raiz, borrado);

		if (encontrado.get(0) == null)
			// Si el dato no existe se devuelve false
			return false;
		else {
			// El dato existe

			// Se elimina el nodo y se decrementa el contador
			eliminar(encontrado.get(0), encontrado.get(1), borrado);
			numElementos--;

			// Reequilibrio del arbol
			for (int i = 1; i < encontrado.size() - 1; i++) {
				if (calcularFactorEquilibrio(encontrado.get(i)) == 2
						|| calcularFactorEquilibrio(encontrado.get(i)) == -2) {
					reequilibrioAVL(encontrado.get(i), encontrado.get(i + 1));
				}
			}

			return true;
		}
	}

	// Métodos solicitados que NO se incluyen en AbstractSet
	// (Son propios de los árboles)

	/**
	 * Devuelve la lista de elementos almacenados en el árbol en el orden en el que
	 * aparecerían en un recorrido en inorden
	 * 
	 * @return Lista con el contenido completo del arbol
	 */
	public List<E> inOrden() {

		// Solo se considerarán válidas las implementaciones ITERATIVAS

		// En esta pila vamos a ir guardando los nodos hasta que sea
		// el momento de sacarlos a la salida
		Deque<Nodo> pilaSimulada = new ArrayDeque<Nodo>(); 
		
		// Esta es la lista que se devuelve con los nodos en inorden
		List<E> salida = new ArrayList<E>(); 
		
		// Este HashMap lo utilizamos para saber si ya es el momento de sacara el nodo, o que operación hacer con él
		Map<Nodo, Integer> contadoresNodo = new HashMap<Nodo, Integer>(); 
		
		// Este es el nodo sobre el que se esta actuado en un instante dado
		Nodo nodoActual; 

		// Se usa una pila para almacenar el nodo raiz
		pilaSimulada.push(super.raiz);

		while (!pilaSimulada.isEmpty()) {

			nodoActual = pilaSimulada.pop(); // Sacamos el primer nodo de la pila para trabajar con el, para tratarlo

			// Se comprueba si el nodo existe en el mapa. Si no existe es que es la
			// primera vez que se pasa por él y aún no se ha hecho nada
			if (!contadoresNodo.containsKey(nodoActual)) {

				// Añadir al mapa nodoActual con key 1, ahora ya sabemos que con este nodo se ha hecho algo
				contadoresNodo.put(nodoActual, 1);

				// Apilar el nodo actual
				// De momento lo devolvemos a la pila, porque sólo hemos pasado 1 vez
				pilaSimulada.push(nodoActual); 

				// Apilar su izquierdo si existe
				if (nodoActual.getIzq() != null) {
					pilaSimulada.push(nodoActual.getIzq());
				}

			} else {

				// Si hemos llegado aquí, es que ya se había hecho algo con el nodo				
				
				// Añadir al mapa el nodoActual con key incrementada en 1 (incrementamos el contador para saber que hemos terminado.)
				contadoresNodo.put(nodoActual, contadoresNodo.get(nodoActual) + 1); 

				// Añadir a la salida el valor del nodo, como es inorden, lo añadimos a la
				// salida en este paso, y no lo volvemos a apilar
				salida.add(nodoActual.getDato());
				
				// Apilar el derecho si existe
				if (nodoActual.getDer() != null) {
					pilaSimulada.push(nodoActual.getDer());
				}
			}
		}

		return salida;
	}

	/**
	 * Devuelve la lista de elementos almacenados en el árbol en el orden en el que
	 * aparecerían en un recorrido en preorden 
	 * 
	 * @return Lista con el contenido completo del arbol
	 */
	public List<E> preOrden() {

		// Solo se considerarán válidas las implementaciones ITERATIVAS

		Deque<Nodo> pila = new ArrayDeque<Nodo>(); // En esta pila vamos a ir guardando los nodos hasta que sea el
													// momento de sacarlos a la salida
		List<E> salida = new ArrayList<E>(); // Esta es la lista que se devuelve con los nodos en preorden
		Map<Nodo, Integer> contadoresNodo = new HashMap<Nodo, Integer>(); // Este HashMap lo utilizamos para saber si ya
																			// es el momento de sacara el nodo, o que
																			// operaación hacer con él
		Nodo nodoActual;

		// Se usa una pila para almacenar el nodo raiz
		pila.push(super.raiz);

		while (!pila.isEmpty()) {

			nodoActual = pila.pop(); // Sacamos el primer nodo de la pila para trabajar con el, para tratarlo

			// ?? Se comprueba si el nodo existe en el mapa. Si no existe es que es la
			// primera vez que se pasa por él y aún no se ha hecho nada
			if (!contadoresNodo.containsKey(nodoActual)) {
				contadoresNodo.put(nodoActual, 1);

				salida.add(nodoActual.getDato()); // Como el recorrido es en preorden, el nodo se añade nada más pasar
													// por él
				pila.push(nodoActual); // Se vuelve a meter el nodo, por si luego queremos sacar el nodo de su derecha
										// (si lo tiene)
				// Apilar su izquierdo si existe
				if (nodoActual.getIzq() != null) {
					pila.push(nodoActual.getIzq());
				}

			} else {
				contadoresNodo.put(nodoActual, contadoresNodo.get(nodoActual) + 1); // Si es la segunda vez que se pasa
																					// intentamos sacar el de la derecha
																					// si lo tiene
				// Apilar su derecho si existe
				if (nodoActual.getDer() != null) {
					pila.push(nodoActual.getDer());
				}
			}

		}

		return salida;
	}

	/**
	 * Devuelve la lista de elementos almacenados en el árbol en el orden en el que
	 * aparecerían en un recorrido en posorden
	 * 
	 * @return Lista con el contenido completo del arbol
	 */
	public List<E> posOrden() {

		// Solo se considerarán válidas las implementaciones ITERATIVAS

		Deque<Nodo> pila = new ArrayDeque<Nodo>(); // En esta pila vamos a ir guardando los nodos hasta que sea el
													// momento de sacarlos a la salida
		List<E> salida = new ArrayList<E>(); // Esta es la lista que se devuelve con los nodos en posorden
		Map<Nodo, Integer> contadoresNodo = new HashMap<Nodo, Integer>(); // Este HashMap lo utilizamos para saber si ya
																			// es el momento de sacara el nodo, o que
																			// operaación hacer con él
		Nodo nodoActual;

		pila.push(super.raiz);

		while (!pila.isEmpty()) {

			nodoActual = pila.pop(); // Sacamos el primer nodo de la pila para trabajar con el, para tratarlo

			// ?? Se comprueba si el nodo existe en el mapa. Si no existe es que es la
			// primera vez que se pasa por él y aún no se ha hecho nada
			if (!contadoresNodo.containsKey(nodoActual)) {
				contadoresNodo.put(nodoActual, 1);
			} else {
				contadoresNodo.put(nodoActual, contadoresNodo.get(nodoActual) + 1);
			}

			// Si no se ha pasado por el nodo
			if (contadoresNodo.get(nodoActual) == 1) {
				pila.push(nodoActual); // Se vuelve a guardar el nodo, para sacar el de su derecha si lo tiene
				// Apilar su izquierdo si existe
				if (nodoActual.getIzq() != null) {
					pila.push(nodoActual.getIzq());
				}
			} else if (contadoresNodo.get(nodoActual) == 2) {
				pila.push(nodoActual); // Se vuelve a guardar el nodo, que se añade a la salida después que sus hijos
				// Apilar su derecho si existe
				if (nodoActual.getDer() != null) {
					pila.push(nodoActual.getDer());
				}
			} else {
				salida.add(nodoActual.getDato()); // Se añade a la salida después de hacer comprobaciones a derecha e
													// izquierda
			}
		}

		return salida;
	}

	/**
	 * Altura dentro del arbol a la que se encuentra insertado el nodo que contiene
	 * un determinado dato pasado por parametro. En caso de que el dato no esté
	 * contenido en el árbol, se devuelve el valor -1.
	 * 
	 * @param dato sobre el que se quiere calcular la altura
	 * @return altura del nodo que contiene el dato (ver definición en teoria)
	 */
	public int altura(E dato) {

		List<Nodo> camino = buscar(super.raiz, dato);
		return calcularAltura(camino.get(0));
	}

	/**
	 * Método auxiliar que sirve para calcular la altura pasando un nodo por
	 * parámetro. El método es recursivo.
	 * 
	 * @param nodo sobre el que se quiere calcular la altura
	 * @return altura del nodo
	 */
	private int calcularAltura(Nodo nodo) {

		if (nodo == null) {
			// En caso de ser nulo, no tiene altura
			return -1;
		} else {
			// Calcular la altura de los dos caminos
			int alturaDer = calcularAltura(nodo.getDer());
			int alturaIzq = calcularAltura(nodo.getIzq());
			// Comprobar alturas, a ver cual es el mas largo
			int mayor = (alturaIzq < alturaDer) ? alturaDer : alturaIzq;
			return mayor + 1;
		}
	}

	/**
	 * Profundidad dentro del arbol a la que se encuentra insertado el nodo que
	 * contiene un determinado dato pasado por parametro. En caso de que el dato no
	 * esté contenido en el árbol, se devuelve el valor -1.
	 * 
	 * @param dato sobre el que se quiere calcular la profundidad
	 * @return profundidad del nodo que contiene el dato (ver definición en teoria)
	 */
	public int profundidad(E dato) {

		// Obtener la ruta desde la raiz hasta dato
		List<Nodo> camino = buscar(raiz, dato);

		// La profundidad es el numero de nodos menos el primero (raiz) y el ultimo
		// (dato)

		if (camino.get(0) == null)
			return -1;
		else
			return camino.size() - 2;
	}

	// ---------------------------------------------------------
	// Metodos que permiten realizar el re-equilibrado del árbol
	// Se sugiere re-escribir el método de búsqueda del ArbolBB
	// ---------------------------------------------------------

	/**
	 * Realiza la rotacion simple izquierda al arbol
	 * 
	 * @param raiz la raiz del arbol
	 * @return la nueva raiz
	 */
	public Nodo rotacionSimpleIzquierda(Nodo raiz) {
		Nodo nuevaRaiz = raiz.getDer();
		raiz.setDer(nuevaRaiz.getIzq());
		nuevaRaiz.setIzq(raiz);
		return nuevaRaiz;
	}

	/**
	 * Realiza la rotacion simple derecha al arbol
	 * 
	 * @param raiz la raiz del arbol
	 * @return la nueva raiz
	 */
	public Nodo rotacionSimpleDerecha(Nodo raiz) {
		Nodo nuevaRaiz = raiz.getIzq();
		raiz.setIzq(nuevaRaiz.getDer());
		nuevaRaiz.setDer(raiz);
		return nuevaRaiz;
	}

	/**
	 * Realiza la rotacion compuesta izquierda-derecha al arbol
	 * 
	 * @param raiz la raiz del arbol
	 * @return la nueva raiz
	 */
	public Nodo rotacionCompuestaIzqDer(Nodo raiz) {
		raiz.setIzq(rotacionSimpleIzquierda(raiz.getIzq()));
		return rotacionSimpleDerecha(raiz);
	}

	/**
	 * Realiza la rotacion compuesta derecha-izquierda al arbol
	 * 
	 * @param raiz la raiz del arbol
	 * @return la nueva raiz
	 */
	public Nodo rotacionCompuestaDerIzq(Nodo raiz) {
		raiz.setDer(rotacionSimpleDerecha(raiz.getDer()));
		return rotacionSimpleIzquierda(raiz);
	}

	/**
	 * Obtiene el factor de equilibrio de un nodo
	 * 
	 * @param nodo el nodo a calcular
	 * @return el factor de equilibrio
	 */
	public int calcularFactorEquilibrio(Nodo nodo) {
		return calcularAltura(nodo.getDer()) - calcularAltura(nodo.getIzq());
	}

	/**
	 * Hace un reequilibrio en el arbol, desplazando los nodos de posicion si es
	 * necesario
	 * 
	 * @param raiz  el nodo a partir del cual hacer el reequilibrio
	 * @param el nodo padre de raiz
	 */
	public void reequilibrioAVL(Nodo raiz, Nodo padre) {

		// Dependiendo del factor de equilibrio del nodo raiz,
		// atacamos por el lado derecho o el izquierdo

		int factorEquilibrioRaiz = calcularFactorEquilibrio(raiz);
		int factorEquilibrioAux;
		Nodo rotacion = null;

		// Obtener el nodo que hará de rotacion
		// --

		if (factorEquilibrioRaiz == 2) {
			// Buscamos por el lado derecho

			factorEquilibrioAux = calcularFactorEquilibrio(raiz.getDer());

			if (factorEquilibrioAux == 1 || factorEquilibrioAux == 0) {
				rotacion = rotacionSimpleIzquierda(raiz);
			} else { // factorEquilibrioAux == -1
				rotacion = rotacionCompuestaDerIzq(raiz);
			}

		} else if (factorEquilibrioRaiz == -2) {
			// Buscamos por el lado izquierdo

			factorEquilibrioAux = calcularFactorEquilibrio(raiz.getIzq());

			if (factorEquilibrioAux == -1 || factorEquilibrioAux == 0) {
				rotacion = rotacionSimpleDerecha(raiz);
			} else { // factorEquilibrioAux == 1
				rotacion = rotacionCompuestaIzqDer(raiz);
			}
		}

		boolean esHijoDer = padre.getDer() == raiz;
		boolean esHijoIzq = padre.getIzq() == raiz;

		if (!esHijoDer && !esHijoIzq)
			// NO es hijo
			super.raiz = rotacion;
		else {
			if (esHijoDer)
				// ES hijo derecho
				padre.setDer(rotacion);
			else
				// ES hijo izquierdo
				padre.setIzq(rotacion);
		}
	}
}
