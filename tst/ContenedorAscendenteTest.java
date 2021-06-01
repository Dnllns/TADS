
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Iterator;
import org.junit.Test;
import colecciones.ContenedorAscendente;

public class ContenedorAscendenteTest {

	ContenedorAscendente<Integer> ContenedorAscendenteComparables;
	ContenedorAscendente<SimpleStudent> ContenedorAscendenteNoComparables;
	
	/**
	 * Test que comprueba el recorrido cuando la estructura almacena como
	 * ultimo elemento uno superior al resto. En este ejemplo se emplean clases
	 * definidas como Comparables.
	 */
	@Test
	public void testComparables() {

		// Ejemplo que acaba en elemento superior
		Integer[] arrayEnteros = {1, 2, 3, 4, 5, 2, 3, 4, 3, 6};
		ContenedorAscendenteComparables = new ContenedorAscendente<Integer>(arrayEnteros);
	
		// Se define el array de esperados (es la respuesta correcta)
		Integer[] esperados = {1,2,3,4,5,6};
		Integer[] obtenidos = new Integer[6]; // El array de obtenidos se inicia vac�o
		
		// Se llena el array de obtenidos con el resultado de la iteraci�n
		int i=0;
		Iterator<Integer> itAs = ContenedorAscendenteComparables.iterator();
		while(itAs.hasNext()){
			obtenidos[i] = itAs.next();
			i++;
		}

		// Se comprueba si el array de esperados y el de obtenidos tienen el mismo contenido
		assertTrue(Arrays.equals(obtenidos, esperados));

	}
	
	/**
	 * Test que comprueba el recorrido cuando la estructura almacena como
	 * ultimo elemento uno no superior al resto (y por tanto no incluido en la iteracion).
	 * En este ejemplo se emplean clases definidas como Comparables.
	 */
	@Test
	public void testComparables2() {
		
		// Ejemplo que acaba en elementos no superiores
		Integer[] arrayEnteros = {1, 3, 2, 4, 5, 6, 3, 8, 2, 4};
		ContenedorAscendenteComparables = new ContenedorAscendente<Integer>(arrayEnteros);

		// Se define el array de esperados (es la respuesta correcta)
		Integer[] obtenidos = new Integer[6];
		Integer[] esperados = {1,3,4,5,6,8}; // El array de obtenidos se inicia vac�o
		
		// Se llena el array de obtenidos con el resultado de la iteraci�n
		int i=0;
		Iterator<Integer> itAs = ContenedorAscendenteComparables.iterator();
		while(itAs.hasNext()){
			obtenidos[i] = itAs.next();
			i++;
		}
		
		// Se comprueba si el array de esperados y el de obtenidos tienen el mismo contenido
		assertTrue(Arrays.equals(obtenidos, esperados));
		
	}

	/**
	 * Test que comprueba el recorrido cuando la estructura almacena como
	 * ultimo elemento uno superior al resto. En este ejemplo se facilita un
	 * Comparator para indicar el orden a considerar en los elementos.
	 */
	@Test
	public void testNoComparables() {
		
		// Ejemplo que acaba en un elemento superior
		SimpleStudent[] arraySimpleStudents = new SimpleStudent[6];
		arraySimpleStudents[0] = new SimpleStudent("Pedro",1);
		arraySimpleStudents[1] = new SimpleStudent("Luis",2);
		arraySimpleStudents[2] = new SimpleStudent("Mar�a",3);
		arraySimpleStudents[3] = new SimpleStudent("Gonzalo",1);
		arraySimpleStudents[4] = new SimpleStudent("Marta",2);
		arraySimpleStudents[5] = new SimpleStudent("Juan",5);
		
		ContenedorAscendenteNoComparables = new ContenedorAscendente<SimpleStudent>
			(arraySimpleStudents, new SimpleStudentComparator());

		// Se define el array de esperados (es la respuesta correcta)
		SimpleStudent[] esperados = new SimpleStudent[4];
		esperados[0] = arraySimpleStudents[0];
		esperados[1] = arraySimpleStudents[1];
		esperados[2] = arraySimpleStudents[2];
		esperados[3] = arraySimpleStudents[5];
		// El array de obtenidos se inicia vac�o
		SimpleStudent[] obtenidos = new SimpleStudent[4];
		
		// Se llena el array de obtenidos con el resultado de la iteraci�n
		int i=0;
		Iterator<SimpleStudent> itAs = ContenedorAscendenteNoComparables.iterator();
		while(itAs.hasNext()){
			obtenidos[i] = itAs.next();
			i++;
		}
		
		// Se comprueba si el array de esperados y el de obtenidos tienen el mismo contenido		
		assertTrue(Arrays.equals(obtenidos, esperados));
		
	}
	
	/**
	 * Test que comprueba el recorrido cuando la estructura almacena como
	 * ultimo elemento uno no superior al resto (y por tanto no incluido en la iteracion).
	 * En este ejemplo se facilita un Comparator para indicar el orden a
	 * considerar en los elementos.
	 */
	@Test
	public void testNoComparables2() {
		
		// Ejemplo que acaba en elementos no superiores
		SimpleStudent[] arraySimpleStudents = new SimpleStudent[8];
		arraySimpleStudents[0] = new SimpleStudent("Pedro",1);
		arraySimpleStudents[1] = new SimpleStudent("Luis",2);
		arraySimpleStudents[2] = new SimpleStudent("Mar�a",3);
		arraySimpleStudents[3] = new SimpleStudent("Gonzalo",1);
		arraySimpleStudents[4] = new SimpleStudent("Marta",2);
		arraySimpleStudents[5] = new SimpleStudent("Juan",5);
		arraySimpleStudents[6] = new SimpleStudent("Marcos",4);
		arraySimpleStudents[7] = new SimpleStudent("Mateo",2);
		
		ContenedorAscendenteNoComparables = new ContenedorAscendente<SimpleStudent>
			(arraySimpleStudents, new SimpleStudentComparator());

		// Se define el array de esperados (es la respuesta correcta)
		SimpleStudent[] esperados = new SimpleStudent[4];
		esperados[0] = arraySimpleStudents[0];
		esperados[1] = arraySimpleStudents[1];
		esperados[2] = arraySimpleStudents[2];
		esperados[3] = arraySimpleStudents[5];

		SimpleStudent[] obtenidos = new SimpleStudent[4];

		// Se llena el array de obtenidos con el resultado de la iteraci�n
		int i=0;
		Iterator<SimpleStudent> itAs = ContenedorAscendenteNoComparables.iterator();
		while(itAs.hasNext()){
			obtenidos[i] = itAs.next();
			i++;
		}
		
		// Se comprueba si el array de esperados y el de obtenidos tienen el mismo contenido
		assertTrue(Arrays.equals(obtenidos, esperados));
		
	}
	
	// TODO - Test de excepciones
	// TODO - ordenados
	
}
