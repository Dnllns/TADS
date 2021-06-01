package colecciones;

import java.util.AbstractCollection;
import java.util.Comparator;
import java.util.Iterator;

/**
 * @author Daniel Alonso Bascones
 */

// Se pide que esta clase extienda la clase AbstractCollection

/*
 * TODO - Responder a la siguiente pregunta (como comentario Java es suficiente)
 * Es necesario que nuestra clase implemente el interfaz Iterable<T>? Razona tu
 * respuesta
 */

public class ContenedorAscendente<E> extends AbstractCollection<E> {

	// TODO - Se necesitarán incluir los atributos necesarios para almacenar los
	// datos que va a contener esta colección.

	E[] coleccion;
	Comparator<E> comparador;

	public ContenedorAscendente(E[] contenido) {
		coleccion = contenido;
		comparador = null;
	}

	public ContenedorAscendente(E[] contenido, Comparator<E> comparador) {
		coleccion = contenido;
		this.comparador = comparador;
	}

	@SuppressWarnings("unchecked")
	private int comparar(E el1, E el2) {

		if (comparador == null)
			return ((Comparable<E>) el1).compareTo(el2);
		else
			return comparador.compare(el1, el2);
	}

	/*
	 * TODO - Responder a la siguiente pregunta (como comentario Java es suficiente)
	 * �Ser�a obligatorio implementar aqu� m�todos como: - int size() - boolean
	 * isEmpty() que ya conocemos que tienen todas las colecciones? �Por qu�? Razona
	 * tu respuesta.
	 */

	public class IteradorAscendente implements Iterator<E> {

		int contadorMayores; // Numero de elementos que se pueden devolver
		int contadorDevueltos; // Numero de elemntos devueltos
		int posicionActual; // Posicion actual del iterador
		E elMayor; // El elemnto mas mayor hasta ahora

		public IteradorAscendente() {

			// Se inicializa a -1 porque pasara a 0 en la primera interaccion del metodo
			// next()
			posicionActual = -1;
			// Se inicializa a 1 porque en la primera interacion del bucle se devuelve el
			// primer elemento
			contadorMayores = 1;
			// Se inicializa a 1 porque para tener en cuenta que el primer elemnto se
			// considera devuelto
			contadorDevueltos = 1;
			elMayor = coleccion[0];

			// Se empieza en 1 ya que el primero siempre va a ser el mayor
			for (int i = 1; i < coleccion.length; i++) {

				// Mientras el nuevo sea menor que el anterior
				// Almacenar el ultimo mayor e incrementar el numero de mayores encontrados
				if (comparar(coleccion[i], elMayor) > 0) {
					elMayor = coleccion[i];
					contadorMayores++;
				}
			}

			// Establecer como el mayor el primer elemento de la lista
			elMayor = coleccion[0];
		}

		@Override
		public boolean hasNext() {
			return contadorMayores - contadorDevueltos > 0;
		}

		@Override
		public E next() {

			posicionActual++;

			// En la primera iteracion se devuelve el primer elemento del array,
			// ya que ninguno es superior a el

			if (posicionActual == 0)
				return coleccion[0];
			else {

				// Buscar el siguiente numero mayor
				while (comparar(coleccion[posicionActual], elMayor) < 0)
					posicionActual++;

				contadorDevueltos++;
				elMayor = coleccion[posicionActual];
				return elMayor;
			}
		}

	} // Fin clase IteradorAscendente

	@Override
	public Iterator<E> iterator() {
		return new IteradorAscendente();
	}

	@Override
	public int size() {
		return coleccion.length;
	}
}