
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Iterator;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import colecciones.ColeccionArray2D;

public class ColecciónArray2DTest {

	protected Integer[][] arrayInteger;
	protected ColeccionArray2D<Integer> colecciónInteger;

	String[][] arrayString;
	ColeccionArray2D<String> colecciónString;

	@BeforeEach
	void setUp() throws Exception {

		// Creamos un array bidimensional de enteros
		arrayInteger = new Integer[][] { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 }, { 10, 11, 12 } };

		System.out.println("arrayInteger es "
				+ Arrays.deepToString(arrayInteger));

		arrayString = new String[][] { { "1", "2", "3" }, { "4", "5", "6" }, { "7", "8", "9" }, { "10", "11", "12" } };

		System.out.println("arrayString es "
				+ Arrays.deepToString(arrayString));

	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void pruebaColecciónArray2D_Integer() {

		// Obtenemos una colección a partir del array bidimensional
		colecciónInteger =	new ColeccionArray2D<Integer>(arrayInteger);

		// Comprobamos la colección
		assertEquals(12, colecciónInteger.size());

		Integer[] control = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 };

		int i = 0;
		for (Integer elem : colecciónInteger) {
			assertEquals(control[i],elem);
			i++;
		}
	}


	@Test
	void pruebaColecciónArray2D_Integer_Modificado() {

		pruebaColecciónArray2D_Integer();

		// Cambiamos algún elemento del array
		arrayInteger[0][0] = -1;
		arrayInteger[1][1] = -5;
		arrayInteger[2][2] = -9;

		// Comprobamos que también ha cambiado la colección
		assertEquals(12, colecciónInteger.size());

		/*
		 * Como se puede ver, la colección es una VISTA sobre los datos que 
		 * le hemos pasado en el constructor. Al cambiar los datos a los que hace
		 * referencia, también cambian en nuestra colección.
		 */
		Integer[] control = { -1, 2, 3, 4, -5, 6, 7, 8, -9, 10, 11, 12 };

		/* La iteración con for-each llama internamente al metodo Iterator de la 
		 * coleccion
		 */
		int i = 0;
		for (Integer elem : colecciónInteger) {
			assertEquals(control[i],elem);
			i++;
		}

	}

	@Test
	void pruebaColecciónArray2D_String() {

		System.out.println("arrayInteger es "
				+ Arrays.deepToString(arrayInteger));

		// Obtenemos una colección a partir del array bidimensional
		colecciónString =	new ColeccionArray2D<String>(arrayString);

		// Comprobamos la colección
		assertEquals(12, colecciónString.size());

		String[] control = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" };

		int i = 0;
		for (String elem : colecciónString) {
			assertEquals(control[i],elem);
			i++;
		}
	}


	@Test
	public void testMetodosNoImplementados_add() {

		colecciónInteger =	new ColeccionArray2D<Integer>(arrayInteger);

		// Metodo que debería lanzar la excepción: el metodo añadir no debería estar soportado
		Executable metodo = () -> colecciónInteger.add(5);
		// Se comprueba la excepcion lanzada 
		assertThrows(UnsupportedOperationException.class, metodo);

	}

	@Test
	public void testMetodosNoImplementados_remove() {

		colecciónInteger =  new ColeccionArray2D<Integer>(arrayInteger);

		/* 
		 * Notar que aunque no hemos implementado el metodo remove() ni el contains(),
		 * la clase AbstactCollection ya nos proporciona esos metodos, basandose en el 
		 * iterado que SÍ hemos tenido que implementar nosotros.
		 */

		colecciónInteger.remove(5);
		assertFalse(colecciónInteger.contains(5));
	}


	@Test
	public void iteratorRemove() {

		int i = 0;
		colecciónString = new ColeccionArray2D<String>(arrayString);

		// Se adelante el iterador hasta el elemento "4"
		Iterator<String> it = colecciónString.iterator();
		for ( ; i < 5 && it.hasNext(); i++) {
			System.out.println(it.next());
		}

		// Se elimina el 4
		// El elemento que se elimina tiene que ser el último devuelto
		it.remove();

		// Notese que el alumno NO necesita implementar el método contains() 
		// y sin embargo se puede ejecutar, ya que está incluido en la implementación de AbstractColletion

		assertFalse(colecciónString.contains("5"));
		assertTrue(it.hasNext());
		System.out.println(colecciónString);

		for ( ; i < 12 && it.hasNext(); i++) {
			System.out.println(it.next());
		}

		it.remove(); // Se elimina el ultimo devuelto
		assertFalse(colecciónString.contains("12"));
		assertFalse(it.hasNext());
		System.out.println(colecciónString);

	}

	// OPCIONAL Para los que implemente el método set() 
	/*
	@Test
	public void metodoSet() {

		colecciónString = new ColeccionArray2D<String>(arrayString);

		String expulsado = colecciónString.set(8,"25");

		assertTrue(colecciónString.contains("8"));
		assertEquals("9",expulsado);
		assertFalse(colecciónString.contains("9"));

		Iterator<String> it = colecciónString.iterator();
		for(int i=0; i<8 && it.hasNext(); i++) {
			System.out.println(it.next());
		}

		String modificado = it.next();
		assertEquals("25",modificado);

	}
	*/

}
