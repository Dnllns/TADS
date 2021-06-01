package tablasHash;

import java.util.AbstractMap;
import java.util.HashSet;
import java.util.Set;

public class HashMapArray<K, V> extends AbstractMap<K, V> {

	private int tam;
	private Nodo<K, V> array[];
	private final int INIT_SIZE = 20;
	private final float CRECIMIENTO = 1.5F;
	private final float MAX_CAP = 0.75F;

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

	@SuppressWarnings("unchecked")
	public HashMapArray() {
		// Declare and setup array
		array = new Nodo[INIT_SIZE];
		for (int i = 0; i < INIT_SIZE; i++) {
			array[i] = null;
		}

		// Setup size
		tam = 0;
	}

	@SuppressWarnings("unchecked")
	public HashMapArray(int tam) {
		// Declare and setup array
		array = new Nodo[tam];
		for (int i = 0; i < tam; i++) {
			array[i] = null;
		}

		// Setup size
		tam = 0;
	}

	public V get(Object key) {

		// Se comprueba si la clave existe en el mapa
		if (!this.containsKey(key))
			return null;

		// Se busca la posicion de la clave
		int hash = key.hashCode() % array.length;

		for (int i = 0; i < array.length; i++) {
			int currPos = (i + hash) % array.length;
			if (array[currPos].getKey().equals(key)) {
				return array[(i + hash) % array.length].getValue();
			}
		}

		return null;
	}

	/**
	 * Inserta un par de clave-valor en el mapa
	 * 
	 * @param key   la clave
	 * @param value el valor
	 */
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
				int calc = exploracionLineal(key);
				array[calc] = new Nodo<K, V>(key, value);
				tam++;
			}
		}

		return oldValue;
	}

	/**
	 * Implementacion mediante exploracion lineal
	 * 
	 * @param key
	 * @return
	 */
	private int exploracionLineal(K key) {

		// Se calcula la posicion a partir del hash de la tabla
		int hash = key.hashCode() % array.length;

		// Se busca la siguiente posicion vacia posterior a el en el array
		for (int i = 0; i < array.length; i++) {
			int currPos = Math.abs((i + hash) % array.length);
			if (array[currPos] == null) {
				return currPos;
			}
		}

		// No hay hueco
		return -1;
	}

	public V remove(Object key) {

		// Se almacena el valor antigo
		V value = get(key);

		// Si la clave existe se elimina la entrada
		if (value != null) {

			// Se elimina la entrada del array
			array[buscarPosicion((K) key)] = null;

			// Se vuelven a insertar el resto de elemnetos en un nuevo mapa
			HashMapArray<K, V> h = new HashMapArray<K, V>(array.length);
			h.putAll(this);
			// Se remplaza el array original por el del mapa nuevo
			this.array = h.array;

			// Se ajusta el tamaño del array
			tam--;
		}

		return value;
	}

	/**
	 * Implementacion mediante exploracion lineal
	 * 
	 * @param key
	 * @return
	 */
	private int buscarPosicion(K key) {

		// Se busca la posicion a partir del hash de la tabla
		int hash = key.hashCode() % array.length;

		// Se busca a partir de la siguiente posicion vacia posterior a el en el array
		for (int i = 0; i < array.length; i++) {
			int currPos = (i + hash) % array.length;
			if (array[currPos].getKey().equals(key)) {
				return (i + hash) % array.length;
			}
		}

		// No encontrado
		return -1;
	}

	private float getCapacity() {
		return (float) (tam / array.length);
	}

	private void redimensionar() {
		int nuevoTam = (int) (array.length * CRECIMIENTO);
		HashMapArray<K, V> nuevo = new HashMapArray<K, V>(nuevoTam);
		nuevo.putAll(this);
		this.array = nuevo.array;
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

	// ------------------------------------------

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
