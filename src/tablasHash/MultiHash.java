package tablasHash;

import java.util.AbstractMap;
import java.util.HashSet;
import java.util.Set;

public class MultiHash<K, V> extends AbstractMap<K, V> {

	private int tam;
	private Nodo<K, V> array[];
	private final int INIT_SIZE = 10;
	private final float CRECIMIENTO = 1.5F;
	private final float MAX_CAP = 0.75F;
	private String metDispersion = "EXPL";

	@SuppressWarnings("hiding")
	protected class Nodo<K, V> implements Entry<K, V> {

		private K key;
		private V val;

		public Nodo(K k, V v) {
			key = k;
			val = v;
		}

		@Override
		public K getKey() {
			return key;
		}

		@Override
		public V getValue() {
			return val;
		}

		@Override
		public V setValue(V arg0) {
			V oldValue = val;
			val = arg0;
			return oldValue;
		}

	}

	/**
	 * ----------------------------------------------------
	 * CONSTRUCTORES
	 * ----------------------------------------------------
	 */
	
	@SuppressWarnings("unchecked")
	public MultiHash() {
		// Declare and setup array
		array = new Nodo[INIT_SIZE];
		for (int i = 0; i < INIT_SIZE; i++) {
			array[i] = null;
		}

		// Setup size
		tam = 0;
	}
	
	@SuppressWarnings("unchecked")
	public MultiHash(String dispersion) {
		
		//setup dispersion method
		metDispersion=dispersion;
		
		// Declare and setup array
		array = new Nodo[INIT_SIZE];
		for (int i = 0; i < INIT_SIZE; i++) {
			array[i] = null;
		}

		// Setup size
		tam = 0;
	}

	@SuppressWarnings("unchecked")
	public MultiHash(int tam) {
		// Declare and setup array
		array = new Nodo[tam];
		for (int i = 0; i < tam; i++) {
			array[i] = null;
		}

		// Setup size
		tam = 0;
	}
	
	/**
	 * -----------------------------------------------------
	 * CLASICOS
	 * -----------------------------------------------------
	 */
	

	public V get(Object key) {

		// Se comprueba si la clave existe en el mapa
		if (!this.containsKey(key))
			return null;
		
		//Se busca en todas las posiciones posibles hasta
		//volver a la inicial
		int intento=0;
		int start = dispersion((K)key, intento);
		int currPos = -1;
		boolean primeraPasada = false;
		boolean fin = false;
		
		while (!fin) {

			//Se calcula la siguiente posicion de dispersion
			currPos = dispersion((K)key, intento++);
			
			
			if(start == currPos && primeraPasada)
				fin = true;
			
			//La primera iteracion romperia el bucle
			//se pone este flag en esta posicion para evitarlo
			primeraPasada = true;


			
			
			if (array[currPos].getKey().equals(key)) {
				return array[currPos].getValue();
			}	
		}
		
		return null;
	}

	@Override
	public V put(K key, V value) {

		// Se almacena el valor antigo
		V oldValue = get(key);

		if (oldValue != null)
			// Si la clave existe machacamos con el nuevo valor
			array[buscarPosicion(key)].setValue(value);
		else {

			// insertamos una entrada nueva
			// ----------------------------

			// Si el array esta al limite de capacidad hay que redimensionar
			if (getCapacity() >= MAX_CAP) {
				redimensionar();
				put(key, value);
			} else {
				
				
				int intento=0;
				int start = dispersion((K)key, intento);
				int currPos = -1;
				boolean primeraPasada = false;
				boolean fin = false;
				
				while (!fin) {

					//Se calcula la siguiente posicion de dispersion
					currPos = dispersion((K)key, intento++);
					
					if(start == currPos && primeraPasada)
						fin = true;
					
					//La primera iteracion romperia el bucle
					//se pone este flag en esta posicion para evitarlo
					primeraPasada = true;
					
					if (array[currPos] == null) {
						array[currPos] = new Nodo<K, V>(key, value);
						tam++;
						break;
					}	
				}
				
			}
		}

		return oldValue;
	}

	public V remove(Object key) {

		// Se almacena el valor antigo
		V value = get(key);

		// Si la clave existe se elimina la entrada
		if (value != null) {

			// Se elimina la entrada del array
			array[buscarPosicion((K) key)] = null;

			// Se vuelven a insertar el resto de elemnetos en un nuevo mapa
			MultiHash<K, V> h = new MultiHash<K, V>(array.length);
			h.putAll(this);
			// Se remplaza el array original por el del mapa nuevo
			this.array = h.array;

			// Se ajusta el tamaño del array
			tam--;
		}
		return value;
	}

	public String toString() {

		StringBuilder s = new StringBuilder();

		for (int i = 0; i < array.length; i++) {

			if (array[i] == null) {
				s.append("v[" + i + "]={NULL}\n");
			} else {
				s.append("v[" + i + "]={" + array[i].getKey() + ", " + array[i].getValue() + "}\n");
			}
		}
		return s.toString();
	}
	
	
	/**
	 * ----------------------------------------------------------
	 * UTILLERIA
	 * ----------------------------------------------------------
	 **/
	
	private float getCapacity() {
		return (float) (tam / array.length);
	}

	private void redimensionar() {
		int nuevoTam = (int) (array.length * CRECIMIENTO);
		MultiHash<K, V> nuevo = new MultiHash<K, V>(nuevoTam);
		nuevo.putAll(this);
		this.array = nuevo.array;
	}

	private int buscarPosicion(K key) {

		// Se busca a partir de la siguiente posicion vacia posterior a el en el array
		
		int intento=0;
		int start = dispersion((K)key, intento);
		int currPos = -1;
		boolean primeraPasada = false;
		boolean fin = false;
		
		while (!fin) {

			//Se calcula la siguiente posicion de dispersion
			currPos = dispersion((K)key, intento++);
			
			
			if(start == currPos && primeraPasada)
				fin = true;
			
			//La primera iteracion romperia el bucle
			//se pone este flag en esta posicion para evitarlo
			primeraPasada = true;
			
			if (array[currPos].getKey().equals(key)) {
				return currPos;
			}	
		}
		
		// No encontrado
		return -1;
	}
	
	/**
	 * ----------------------------------------------------------
	 * DISPERSIONES
	 * ----------------------------------------------------------
	 **/
	
	
	private int dispersion(K key, int intento) {
		
		int result = -1;
		
		switch(metDispersion) {
			case ("EXPL"):
				result = exploracionLineal(key, intento);
			break;
			case ("CUAD"):
				result = exploracionCuadratica(key, intento);
			break;
			case ("COCI"):
				result = desplazamientoCociente(key, intento);
			break;
			default:
				result = exploracionLineal(key, intento);
			break;
			
		}
		
		return result;
	}
	
	private int exploracionLineal(K key, int intento) {
		// Se calcula la posicion a partir del hash de la tabla
		int hash = key.hashCode();
		return Math.abs((intento + hash) % array.length);
	}
	
	private int exploracionCuadratica(K key, int intento) {
		// Se calcula la posicion a partir del hash de la tabla
		int hash = key.hashCode();
		return Math.abs((intento*intento + hash) % array.length);
	}
	
	private int desplazamientoCociente(K key, int intento) {
		
		//(h(k) + i · d(k)) % M
		int hash = key.hashCode();
		int d = hash / array.length;
		int mod = hash % array.length;
		
		if (mod == 0) {
			d = 1;
		}
		
		return Math.abs((intento*d + hash) % array.length);
	}
	

	/**
	 * ------------------------------------------------------
	 * OBLIGATORIOS
	 * ------------------------------------------------------
	 */

	@Override
	public int size() {
		return tam;
	}

	@Override
	public Set<Entry<K, V>> entrySet() {

		Set<Entry<K, V>> s = new HashSet<Entry<K, V>>();
		for (int i = 0; i < array.length; i++)
			if (array[i] != null)
				s.add(array[i]);

		return s;
	}

}
