
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Map.Entry;
import java.util.Set;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import tablasHash.MapaDispersionAbierta;

public class MapaDispersionAbiertaTest {

	MapaDispersionAbierta<Integer, String> mapa;

	String[] valores = { "cero", "uno", "dos", "tres", "cuatro", "cinco", "seis", "siete", "ocho", "nueve" };

	@Before
	public void setUp() throws Exception {
		mapa = new MapaDispersionAbierta<Integer, String>(3, 5);
	}

	@After
	public void tearDown() throws Exception {
		mapa.clear();
	}

	/**
	 * Se comprueba el almacenamiento correcto de claves y valores. Se comprueba
	 * también el tamaño del mapa y el numero de entradas registradas.
	 */
	@Test
	public void testPut_Espacio() {

		for (int i = 0; i <= 9; i++) {
			assertEquals(null, mapa.put(i, valores[i]));
		}

		assertTrue(mapa.keySet().containsAll(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)));

		assertEquals(10, mapa.size());
		assertEquals(5, mapa.getNumeroCubetas());

	}

	/**
	 * Se comprueba el almacenamiento correcto de claves y valores en el caso de
	 * necesitar un redimensionamiento de la tabla mientras se almacena. Se
	 * comprueba también el tamaño del mapa y el numero de entradas registradas.
	 */
	@Test
	public void testPut_SinEspacio() {

		for (int i = 0; i < 16; i++) {
			assertEquals(null, mapa.put(i, valores[i % 10]));
		}

		assertTrue(mapa.keySet().containsAll(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)));

		assertEquals(16, mapa.size());
		assertEquals(7, mapa.getNumeroCubetas());

	}

	/**
	 * Se comprueba que en el caso de almacenar un par-valor de una clave que ya
	 * existe en el mapa se actualizan correctamente los valores.
	 */
	@Test
	public void testPut_Sobreescribir() {

		testPut_Espacio();

		assertEquals(valores[5], mapa.put(5, "otro"));

		assertTrue(mapa.keySet().containsAll(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8)));
		assertFalse(mapa.values().contains(valores[5]));
		assertEquals("otro", mapa.get(5));

		assertEquals(10, mapa.size());
		assertEquals(5, mapa.getNumeroCubetas());

	}

	/**
	 * Se comprueba la eliminacion de claves y valores en función de la búsqueda por
	 * clave.
	 */
	@Test
	public void testRemove() {

		testPut_Espacio();

		assertEquals(valores[2], mapa.remove(2));
		assertFalse(mapa.keySet().contains(2));
		assertFalse(mapa.values().contains(valores[2]));

		assertEquals(9, mapa.size());
		assertEquals(5, mapa.getNumeroCubetas());

	}

	/**
	 * Se comprueba la correcta recuperación del conjunto de pares clave-valor
	 * almacenados.
	 */
	@Test
	public void testEntrySet() {

		testPut_Espacio();

		Set<Entry<Integer, String>> conjunto = mapa.entrySet();
		for (Entry<Integer, String> entrada : conjunto) {
			Integer clave = entrada.getKey();
			assertEquals(valores[clave], entrada.getValue());
		}

	}

	/**
	 * Se comprueba a recuperar los valores a partir de sus claves tras haber
	 * realizado una inserción sin necesidad de redimension.
	 */
	@Test
	public void testGet_Espacio() {

		testPut_Espacio();

		for (int i = 0; i < 9; i++) {
			assertEquals(valores[i], mapa.get(i));
		}

	}

	/**
	 * Se comprueba a recuperar los valores a partir de sus claves tras haber
	 * realizado una inserción con necesidad de redimension.
	 */
	@Test
	public void testGet_SinEspacio() {

		testPut_SinEspacio();

		for (int i = 0; i < 9; i++) {
			assertEquals(valores[i % 10], mapa.get(i));
		}

	}

	// TODO - Completar los tests probando las situaciones extremas
	// TODO - Probar a introducir otras clases (p.ej. las de profesor / alumno ...)
}
