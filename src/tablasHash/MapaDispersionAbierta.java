package tablasHash;


import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


/**
 * Clase que implementa un mapa con dispersion abierta, emplea un arraylist como contenedor de cubetas
 * Cada cubeta esta compuesta por una lista de claves y otra de valores
 * @author Daniel Alonso Báscones
 *
 * @param <K> clave
 * @param <V> valor
 */
public class MapaDispersionAbierta<K, V> extends AbstractMap<K, V> {


	/* ATRIBUTOS */

	/**
	 * Tamaño por defecto de la cubeta
	 */
	private int tamanoCubeta = 3;
	/**
	 * Numero de cubetas por defecto
	 */
	private int numeroCubeta = 16;
	/**
	 * Lista que contiene las cubetas
	 */
	private List<EntradaMultiple<K, V>> contenedor;
	/**
	 * El factor de crecimiento cuando se redimensiona
	 */
	private float porcentajeCrecimiento = 1.5F;


	/* CONSTRUCTORES */
	
	/**
	 * Constructor
	 * @param tamanoCubeta el tamaño de la cubeta
	 * @param numeroCubeta el numero de cubetas
	 */
	public MapaDispersionAbierta(int tamanoCubeta, int numeroCubeta) {
		this.tamanoCubeta = tamanoCubeta;
		this.numeroCubeta = numeroCubeta;
		contenedor = new ArrayList<EntradaMultiple<K, V>>();

		for (int i = 0; i < numeroCubeta; i++) 
			contenedor.add(new EntradaMultiple<K, V>());
		
	}

	/**
	 * Constructor
	 */
	public MapaDispersionAbierta() {
		contenedor = new ArrayList<EntradaMultiple<K, V>>();
		for (int i = 0; i < tamanoCubeta; i++) 
			contenedor.add(new EntradaMultiple<K, V>());	
	}
	
	
	/* METODOS */

	/**
	 * Inserta un par de clave-valor en el mapa
	 * @param key la clave
	 * @param value el valor
	 */
	@Override
	public V put (K key, V value) {

		V oldValue = get(key);		
		EntradaMultiple<K,V> cubeta = this.contenedor.get(getPosicionCubeta(key));
		
		//Si la clave existe machacamos con el nuevo valor
		if (oldValue!=null) 			
			cubeta.update(cubeta.findKey(key), value);
		
		//La clave no existe, insertamos
		else {
			
			//Si la cubeta esta llena hay que redimensionar el contenedor
			if (cubeta.elementosCubeta == tamanoCubeta) {
				redimensionar(tamanoCubeta, (int) (numeroCubeta*porcentajeCrecimiento));
				put(key, value);
			}
			else cubeta.add(key, value);
			
		}
		return oldValue;
	}
	
	/**
	 *Obtiene un elemeno del mapa a partir de su clave
	 *@param key la clave
	 *@return el valor de esa clave
	 */
	@Override
	public V get(Object key) {
		
		EntradaMultiple<K, V> cubeta = contenedor.get(getPosicionCubeta(key));
		int indice = cubeta.findKey(key);
		if (indice != -1) return cubeta.getValue(indice);
		else return null;

	}
	
	/**
	 *Elimina un elemento del mapa
	 *@param key la clave
	 *@return el valor eliminado
	 */
	@Override
	public V remove(Object key) {
		
		V valor = null;
		EntradaMultiple<K, V> cubeta = contenedor.get(getPosicionCubeta(key));
		int indice = cubeta.findKey(key);
		if (indice != -1) {
			valor = cubeta.getValue(indice);
			cubeta.remove(indice);
		}
		return valor;
	}
	
	/**
	 *Obtiene las entras que contiene el mapa
	 *@return un set con las entradas 
	 */
	@Override
	public Set<Entry<K, V>> entrySet() {
	
		Set<Entry<K, V>> set = new HashSet<Entry<K, V>>();

		//Iterador para recorrer el contenedor
		Iterator<EntradaMultiple<K, V>> itContenedor = contenedor.iterator();
		
		while (itContenedor.hasNext()) {
			
			//Obtenemos la cubeta
			EntradaMultiple<K, V> cubeta = itContenedor.next();
			
			//Recorremos la cubeta y añadimos las entradas al set
			for (int i=0; i<cubeta.claves.size(); i++) {
				set.add(new EntradaPlana<K, V>(cubeta.claves.get(i), cubeta.valores.get(i)));
			}
		}
		
		return set;
	}

	
	/* METODOS AUXILIARES */
	
	/**
	 * @param key la clave
	 * @return la posicion que ocupa en el mapa
	 */
	private int getPosicionCubeta(Object key) {
		return Math.abs(key.hashCode() % numeroCubeta);
	}
	
	/**
	 * Redimensiona el mapa para que quepan mas elementos
	 * @param tamanoCubeta el tamaño que va a tener la cubeta
	 * @param numeroCubeta el numero de cubetas
	 */
	private void redimensionar(int tamanoCubeta, int numeroCubeta) {
		
		//Creamos un nuevo mapa con el tamaño deseado
		MapaDispersionAbierta<K, V> nuevoMapa = new MapaDispersionAbierta<K, V>(tamanoCubeta, numeroCubeta);
		
		//Añado los elementos anteriores
		nuevoMapa.putAll(this);
		
		//Reasignar variables
		contenedor = nuevoMapa.contenedor;
		this.numeroCubeta=nuevoMapa.numeroCubeta;
		this.tamanoCubeta=nuevoMapa.tamanoCubeta;		
	
	}

	/**
	 * Obtiene el numero de cubetas que tiene el mapa
	 * @return el numero de cubetas
	 */
	public int getNumeroCubetas() {
		return numeroCubeta;
	}

	/* CLASES AUXILIARES */
	
	/**
	 * Clase que implementa una entrada multiple usada como recipiente para alamacenar una lista
	 * de pares clave-valor en el mapa
	 * @author Daniel Alonso Báscones
	 *
	 * @param <K> clave
	 * @param <V> valor
	 */
	@SuppressWarnings("hiding")
	private class EntradaMultiple<K, V> {

		/* ATRIBUTOS */
		
		/**
		 * Lista que contiene las claves almacenadas
		 */
		private List<K> claves; 
		/**
		 * Lista que contiene los valores almacenados
		 */
		private List<V> valores;
		/**
		 * Numero de elementos que hay almacenados en la EntradaMultiple
		 */
		private int elementosCubeta; 

		
		/* CONSTRUCTOR */
		
		/**
		 * Constructor
		 */
		public EntradaMultiple() {
			claves = new ArrayList<K>();
			valores = new ArrayList<V>();
			elementosCubeta = 0;
		}
		
		
		/* METODOS */
		
		/**
		 * Obtiene la posicion en la que se encuentra una clave en la lista
		 * @param key la clave 
		 * @return -1 si no se encuentra, >= 0 si se encuentra
		 */
		protected int findKey(Object key) {		
			return claves.indexOf(key);
		}
		
		/**
		 * Inserta en la listas correspondientes un nuevo par clave, valor
		 * @param key objeto que hace de clave
		 * @param value objeto que hace de valor
		 */
		protected void add(K key, V value) {
			claves.add(key);
			valores.add(value);
			elementosCubeta++;
		}
		
		/**
		 * Elimina un objeto de una posicion de la lista
		 * @param index la posicion en la lista
		 */
		protected void remove(int index) {
			claves.remove(index);
			valores.remove(index);
			elementosCubeta--;
		}
		
		/**
		 * Actualiza el objeto de una posicion de la lista
		 * @param index la posicion en la lista
		 * @param value el objeto que ocupa la posicion index en la lista
		 */
		protected void update(int index, V value) {
			valores.set(index, value);
		}
		
		/**
		 * Obtiene un objeto almacenado en la lista
		 * @param index la posicion en la lista
		 * @return el objeto que ocupa la posicion index en la lista
		 */
		protected V getValue(int index) {
			return valores.get(index);
		}
		
	}

	/**
	 * Clase auxiliar que extiende de SimpleEntry
	 * @author Daniel Alonso Báscones
	 *
	 * @param <K> Clave
	 * @param <V> Valor
	 */
	@SuppressWarnings("hiding")
	private class EntradaPlana<K, V> extends SimpleEntry<K, V> {

		private static final long serialVersionUID = 1L;

		/**
		 * @param clave el objeto clave
		 * @param valor el objeto valor
		 */
		public EntradaPlana(K clave, V valor) {
			super(clave, valor);
		}
	}
	
	/* METODOS EXTRA */
	
	/**
	 * Obtiene una respresentacion de el mapa en formato String
	 */
	@Override
	public String toString() {
		String map ="";
		for (EntradaMultiple<K, V> cubeta : contenedor) {
			String cub ="";
			for (int i=0; i<tamanoCubeta; i++) {
				cub += "(" + cubeta.claves.get(i) + "|" + cubeta.valores.get(i).toString() + "), ";
			}
			map += "\t[" + cub + "]\n";
		}
		
		return "{\n" + map + "\n}";
	}
	
	/**
	 * Obtiene el numero de elementos que hay en el mapa
	 */
	@Override
	public int size() {
		
		int count = 0;
		
		for (EntradaMultiple<K,V> cubeta : contenedor) 
			count+=cubeta.claves.size();
		
		return count;
	}
	
	/**
	 * Comprueba si el mapa esta vacío
	 * @return true si lo esta
	 */
	@Override
	public boolean isEmpty() {
		return size()==0;
	}

	/**
	 * Obtiene una coleccion de valores
	 * @return una coleccion de valores
	 */
	@Override
	public Collection<V> values(){
		List<V> valores = new ArrayList<V>();
		for (EntradaMultiple<K, V> cubeta : contenedor) {
			valores.addAll(cubeta.valores);
		}
		return valores;	
	}
	
	/**
	 * Obtiene un set con todas las claves dek mapa
	 * @return un set que contiene las claves
	 */
	@Override
	public Set<K> keySet(){
		HashSet<K> s = new HashSet<K>();
		for (EntradaMultiple<K, V> cubeta : contenedor) {
			s.addAll(cubeta.claves);
		}
		return s;
	}

	
	/**
	 * Comprueba si un valor existe en el mapa
	 * @param value el valor
	 * return true si existe
	 */
	@Override
	public boolean containsValue(Object value) {
		return values().contains(value);
	}

	/**
	 * Comprueba si una clave existe en el mapa
	 * @param key la clave
	 * @return true si existe
	 */
	@Override
	public boolean containsKey(Object key) {
		return keySet().contains(key);
	}
	
	
	/**
	 * Vacía el mapa
	 */
	@Override
	public void clear() {
		contenedor = new ArrayList<EntradaMultiple<K, V>>();

		for (int i = 0; i < numeroCubeta; i++) 
			contenedor.add(new EntradaMultiple<K, V>());	
	}

	
}