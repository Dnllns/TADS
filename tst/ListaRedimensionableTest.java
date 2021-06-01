
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.util.Arrays;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import listas.ListaRedimensionable;

public class ListaRedimensionableTest {

	ListaRedimensionable<Character> listaCreciente;

	@Before
	public void setUp() throws Exception {
		listaCreciente = new ListaRedimensionable<Character>();
	}

	@After
	public void tearDown() throws Exception {
		listaCreciente.clear();
	}

	@Test
	public void testRedimensionAñadirInicio() {

		listaCreciente.add(-1, 'a');
		listaCreciente.add(-3, 'b');
		listaCreciente.add(-2, 'c');
		listaCreciente.add(-3, 'd');

		// [d, null, null, c, null, b, null, null, a, null]
		assertEquals(10, listaCreciente.size());

		assertTrue(listaCreciente.contains('a'));
		assertEquals(8, listaCreciente.indexOf('a'));
		assertTrue(listaCreciente.contains('b'));
		assertEquals(5, listaCreciente.indexOf('b'));
		assertTrue(listaCreciente.contains('c'));
		assertEquals(3, listaCreciente.indexOf('c'));
		assertTrue(listaCreciente.contains('d'));
		assertEquals(0, listaCreciente.indexOf('d'));
		System.out.println(listaCreciente);

	}

	/**
	 * 
	 */
	@Test
	public void testRedimensionAñadirFin() {

		listaCreciente.add(1, 'a');
		listaCreciente.add(4, 'b');
		listaCreciente.add(6, 'c');
		listaCreciente.add(9, 'd');

		// [null, a, null, null, b, null, c, null, null, d]
		assertEquals(10, listaCreciente.size());

		assertTrue(listaCreciente.contains('a'));
		assertEquals(1, listaCreciente.indexOf('a'));
		assertTrue(listaCreciente.contains('b'));
		assertEquals(4, listaCreciente.indexOf('b'));
		assertTrue(listaCreciente.contains('c'));
		assertEquals(6, listaCreciente.indexOf('c'));
		assertTrue(listaCreciente.contains('d'));
		assertEquals(9, listaCreciente.indexOf('d'));

	}

	/**
	 * 
	 */
	@Test
	public void testRedimensionEstablecerInicio() {

		// Se rellena la lista (con elementos nulos en medio)
		testRedimensionEstablecerMedio();

		// [null, g, e, null, b, null, c, f, null, d]

		// Se inserta en posiciones externas
		assertEquals(null, listaCreciente.set(-5, 'h'));

		// [h, null, null, null, null, null, g, e, null, b, null, c, f, null, d]
		assertEquals(15, listaCreciente.size());
		assertTrue(listaCreciente.contains('h'));
		assertEquals(0, listaCreciente.indexOf('h'));

	}

	@Test
	public void testRedimensionEstablecerMedio() {

		testRedimensionAñadirFin();
		// [null, a, null, null, b, null, c, null, null, d]

		// Se modifican algunas posiciones (sin aumentar tamaño)
		assertEquals(null, listaCreciente.set(2, 'e'));
		assertEquals(null, listaCreciente.set(7, 'e'));

		// [null, a, e, null, b, null, c, e, null, d]
		assertEquals(10, listaCreciente.size());

		assertTrue(listaCreciente.contains('e'));
		assertEquals(2, listaCreciente.indexOf('e'));
		assertEquals(7, listaCreciente.lastIndexOf('e'));

		// Se modifican posiciones que contenían datos
		assertEquals((Character) 'e', listaCreciente.set(7, 'f'));
		assertEquals((Character) 'a', listaCreciente.set(1, 'g'));

		// [null, g, e, null, b, null, c, f, null, d]
		assertEquals(10, listaCreciente.size());

		// Se comprueba el contenido
		assertTrue(listaCreciente.contains('e'));
		assertEquals(2, listaCreciente.indexOf('e'));
		assertTrue(listaCreciente.contains('f'));
		assertEquals(7, listaCreciente.indexOf('f'));
		assertTrue(listaCreciente.contains('g'));
		assertEquals(1, listaCreciente.indexOf('g'));
		assertFalse(listaCreciente.contains('a'));
		assertEquals(-1, listaCreciente.indexOf('a'));

	}

	/**
	 * 
	 */
	@Test
	public void testRedimensionObtenerFin() {

		assertTrue(listaCreciente.add('f'));
		assertTrue(listaCreciente.add('d'));
		assertTrue(listaCreciente.add('b'));

		// [f, d, b]
		assertEquals(3, listaCreciente.size());
		assertEquals((Character) 'f', listaCreciente.get(0));
		assertEquals((Character) 'd', listaCreciente.get(1));
		assertEquals((Character) 'b', listaCreciente.get(2));

		// [f, d, b, null]
		assertEquals(null, listaCreciente.get(4));
		assertEquals(5, listaCreciente.size());

		// [f, d, b, null, null, null, null, null, null, null, null, null, null]
		assertEquals(null, listaCreciente.get(11));
		assertEquals(12, listaCreciente.size());

	}

	/**
	 * 
	 */
	@Test
	public void testEliminarFin() {

		testRedimensionAñadirFin();
		// [null, a, null, null, b, null, c, null, null, d]

		assertEquals(null, listaCreciente.remove(12));
		// [null, a, null, null, b, null, c, null, null, d, null, null]
		assertEquals(12, listaCreciente.size());

		assertEquals(null, listaCreciente.remove(11));
		// [null, a, null, null, b, null, c, null, null, d, null]
		assertEquals(11, listaCreciente.size());

		assertEquals((Character) 'd', listaCreciente.get(9));

	}

	/**
	 * 
	 */
	@Test
	public void testIgualdad_acabaNull() {

		List<Character> control = Arrays.asList('a', null, 'b', 'c', null, 'd', null);

		// Se rellena la lista (con elementos nulos en medio)
		testRedimensionAñadirFin();

		// se comprueba su igualdad
		assertTrue(listaCreciente.equals(control));

	}

	/**
	 * 
	 */
	@Test
	public void testDesigualdad_menor() {

		// Se comprueba la desigualdad con una lista menor que la creada
		List<Character> control = Arrays.asList('a', 'b', 'c');

		// Se rellena la lista (con elementos nulos en medio)
		testRedimensionAñadirFin();

		// se comprueba su desigualdad
		assertFalse(listaCreciente.equals(control));

	}

}
