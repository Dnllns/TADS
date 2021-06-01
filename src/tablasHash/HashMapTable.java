package tablasHash;


import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

/**
 * Clase que implementa una tabla bidimensional (de un par de claves)
 * 
 * @author Daniel Alonso Báscones
 *
 * @param <R> fila (clave 1)
 * @param <C> columna (clave 2)
 * @param <V> valor asociado
 */
public class HashMapTable<R, C, V> implements Table<R, C, V> {

	Map<R, Map<C, Cell<R, C, V>>> mapaTabla = new HashMap<R, Map<C, Cell<R, C, V>>>();

	/**
	 * Asocia el valor especificado con su correspondiente par de claves. Si la
	 * tabla ya contiene una asociacion con esas mismas claves, el valor anterior se
	 * reemplaza por el valor introducido como parámetro
	 * 
	 * @param row    Clave de fila con la que se asocia el valor
	 * @param column Clave de columna con la que se asocia el valor
	 * @param value  Valor a asociar a las dos claves determinadas
	 * @return Valor previamente asociado a ambas claves o nulo si no existía esa
	 *         asociación
	 */
	@Override
	public V put(R row, C column, V value) {
		V oldValue = this.get(row, column);
		if (mapaTabla.get(row) == null) {
			mapaTabla.put(row, new HashMap<C, Cell<R, C, V>>());
		}

		mapaTabla.get(row).put(column, new HashMapCell(row, column, value));
		return oldValue;
	}

	/**
	 * Elimina la asociación entre las dos claves y su valor, si existía
	 * anteriormente. Se eliminarán los 3 elementos de la tabla, no solo el valor.
	 * 
	 * @param row    Clave del fila de la asociación a ser eliminada
	 * @param column Clave del columna de la asociación a ser eliminada
	 * @return Valor previamente asociado a ambas claves o nulo si no existía esa
	 *         asociación
	 */
	@Override
	public V remove(R row, C column) {
		V oldValue = this.get(row, column);
		mapaTabla.get(row).remove(column);
		if (mapaTabla.get(row).isEmpty()) {
			mapaTabla.remove(row);
		}
		return oldValue;
	}

	/**
	 * Devuelve el valor correspondiente asociado a una determinada combinación de
	 * claves de fila y de columna o null si no existe esa asociación.
	 * 
	 * @param row    Clave del fila de la asociación correspondiente al valor a
	 *               recuperar
	 * @param column Clave de columna de la asociación correspondiente al valor a
	 *               recuperar
	 * @return Valor asociado a las dos claves facilitadas o null si no existe esa
	 *         asociación
	 */
	@Override
	public V get(Object row, Object column) {

		Map<C, Cell<R, C, V>> mapaFila = mapaTabla.get(row);

		if (mapaFila != null && mapaFila.get(column) != null) {
			// La fila y la columna existen
			return mapaFila.get(column).getValue();
		}
		return null;
	}

	/**
	 * Devuelve verdadero si la tabla contienen una asociación que incluya las
	 * claves de fila y de columna que se especifican
	 * 
	 * @param row    Clave del fila de la asociación que se pretende consultar
	 * @param column Clave de columna de la asociación que se pretende consultar
	 * @return true si existe la asociacion y false en otro caso
	 */
	@Override
	public boolean containsKeys(Object row, Object column) {

		if (mapaTabla.containsKey(row) && mapaTabla.get(row).containsKey(column))
			// Existe la fila y la columna
			return true;
		else
			return false;
	}

	/**
	 * Devuelve verdadero si la tabla contienen un valor igual al que se especifica
	 * como parámetro, sin importar las claves a las que esté asociado.
	 * 
	 * @param value valora a buscar
	 * @return true si la tabla contiene ese valor y falso en caso contrario
	 */
	@Override
	public boolean containsValue(V value) {

		Iterator<Entry<R, Map<C, Cell<R, C, V>>>> iteradorPrincipal = mapaTabla.entrySet().iterator();
		Iterator<Entry<C, Cell<R, C, V>>> iteradorSecundario;
		Map<C, Cell<R, C, V>> mapaNext;
		Cell<R, C, V> celda;
		while (iteradorPrincipal.hasNext()) {
			mapaNext = iteradorPrincipal.next().getValue();
			iteradorSecundario = mapaNext.entrySet().iterator();
			while (iteradorSecundario.hasNext()) {
				celda = iteradorSecundario.next().getValue();

				if (value == null) {
					if (celda.getValue() == null)
						return true;
				} else {
					if (celda.getValue().equals(value))
						return true;
				}
			}
		}
		return false;
	}

	/**
	 * Devuelve una vista de todas las asociaciones que coniciden en emplear la
	 * misma clave de fila. Por cada asociación de clave de fila / clave de columna
	 * / valor en la tabla original, el mapa devuelto contendrá la correspondiente
	 * asociación de clave de columna / valor.
	 * 
	 * Si no existe ninguna asociación que contenga esa clave de fila, se devolverá
	 * un mapa vacío.
	 * 
	 * @param rowKey clave de fila que se debe recuperar de la tabla
	 * @return mapa correspondiente asociando las claves de columna a su valor
	 */
	@Override
	public Map<C, V> row(R rowKey) {
		Map<C, V> mapaColumnaValor = new HashMap<C, V>();
		Map<C, Cell<R, C, V>> mapaFila;
		if (!(mapaTabla.containsKey(rowKey))) {
			return null;
		}
		mapaFila = mapaTabla.get(rowKey);
		Iterator<Entry<C, Cell<R, C, V>>> iterador = mapaFila.entrySet().iterator();
		Cell<R, C, V> celda;
		while (iterador.hasNext()) {
			celda = iterador.next().getValue();
			mapaColumnaValor.put(celda.getColumnKey(), celda.getValue());
		}
		return mapaColumnaValor;
	}

	/**
	 * Devuelve una vista de todas las asociaciones que coniciden en emplear la
	 * misma clave de columna. Por cada asociación de clave de fila / clave de
	 * columna / valor en la tabla original, el mapa devuelto contendrá la
	 * correspondiente asociación de clave de fila / valor.
	 * 
	 * Si no existe ninguna asociación que contenga esa clave de columna, se
	 * devolverá un mapa vacío.
	 * 
	 * @param columnKey clave de columna que se debe recuperar de la tabla
	 * @return mapa correspondiente asociando las claves de fila a su valor
	 */
	@Override
	public Map<R, V> column(C columnKey) {
		Map<R, V> mapaFilaValor = new HashMap<R, V>();
		Map<C, Cell<R, C, V>> mapaFila;
		Iterator<Entry<R, Map<C, Cell<R, C, V>>>> iterador = mapaTabla.entrySet().iterator();
		Cell<R, C, V> celda;
		while (iterador.hasNext()) {
			mapaFila = iterador.next().getValue();
			if (mapaFila.containsKey(columnKey)) {
				celda = mapaFila.get(columnKey);
				mapaFilaValor.put(celda.getRowKey(), celda.getValue());
			}
		}
		return mapaFilaValor;
	}

	/**
	 * Devuelve el contenido completo de la tabla en forma de una conjunto de
	 * tripletas de clave de fila / clave de columna / valor. Se tratará de una
	 * vista, por lo que los cambios sobre los datos de la colección se realizarán
	 * también sobre los datos contenidos en la tabla.
	 * 
	 * Cada una de las tripletas de datos se almacenará en una clase que implemente
	 * el interfaz Table.Cell (incluido en este fichero).
	 * 
	 * La colección de celdas no deberá soportar los metodos de add o addAll.
	 * 
	 * @return contenido
	 */
	@Override
	public Collection<Cell<R, C, V>> cellSet() {
		Collection<Cell<R, C, V>> coleccionCeldas = new HashSet<Cell<R, C, V>>();
		Iterator<Entry<R, Map<C, Cell<R, C, V>>>> iteradorPrincipal = mapaTabla.entrySet().iterator();
		Iterator<Entry<C, Cell<R, C, V>>> iteradorSecundario;
		Map<C, Cell<R, C, V>> mapaNext;
		while (iteradorPrincipal.hasNext()) {
			mapaNext = iteradorPrincipal.next().getValue();
			iteradorSecundario = mapaNext.entrySet().iterator();
			while (iteradorSecundario.hasNext()) {
				coleccionCeldas.add(iteradorSecundario.next().getValue());
			}
		}
		return coleccionCeldas;
	}

	/**
	 * Devuelve el numero de sociaciones de clave de columna / clave de fila / valor
	 * que se encuentran almacenadas en el mapa.
	 * 
	 * @return numero de sociaciones almacenadas en el mapa.
	 */
	@Override
	public int size() {
		int tamano = 0;
		Iterator<Entry<R, Map<C, Cell<R, C, V>>>> iterador = mapaTabla.entrySet().iterator();
		Map<C, Cell<R, C, V>> mapaNext;
		while (iterador.hasNext()) {
			mapaNext = iterador.next().getValue();
			tamano += mapaNext.size();
		}
		return tamano;
	}

	/**
	 * Devuelve true si la tabla no contiene ninguna asociacion de datos y false en
	 * caso contrario.
	 * 
	 * @return true si la tabla no contiene ninguna asociacion de datos y false en
	 *         caso contrario.
	 */
	@Override
	public boolean isEmpty() {
		return mapaTabla.isEmpty();
	}

	/**
	 * Elimina todas las asociaciones de datos incluidas en el mapa.
	 */
	@Override
	public void clear() {
		mapaTabla.clear();

	}

	/**
	 * Clase que permite consultar las claves de fila o columna y consultar y
	 * actualizar el correspondiente valor.
	 */
	public class HashMapCell implements Table.Cell<R, C, V> {

		R row;
		C column;
		V value;

		/**
		 * Constructor
		 * 
		 * @param row,    la fila (clave 1)
		 * @param column, la columna (clave 2)
		 * @param value,  el valor asociado
		 */
		public HashMapCell(R row, C column, V value) {
			this.row = row;
			this.column = column;
			this.value = value;
		}

		/**
		 * Obtiene la clave de fila
		 * 
		 * @return row, la clave de fila
		 */
		@Override
		public R getRowKey() {
			return row;
		}

		/**
		 * Obtiene la clave de columna
		 * 
		 * @return column, la clave de columna
		 */
		@Override
		public C getColumnKey() {
			return column;
		}

		/**
		 * Obtiene el valor asociado
		 * 
		 * @return valor, el valor
		 */
		@Override
		public V getValue() {
			return value;
		}

		/**
		 * Setea el valor pasado por parametro
		 * 
		 * @param value, el valor
		 * @return oldValue, el valor anterior
		 */
		@Override
		public V setValue(V value) {
			V oldValue = this.getValue();
			this.value = value;
			return oldValue;
		}

		/**
		 * Obtiene el hash asociado al par de claves
		 * 
		 * @return hash
		 */
		@Override
		public int hashCode() {
			return Objects.hash(row, column);
		}

		/**
		 * Compara el objeto actual con el pasado por parametro
		 * 
		 * @param o, el objeto a comparar
		 * @return true, si son iguales
		 * @return false, si son diferentes
		 */
		@SuppressWarnings("unchecked")
		@Override
		public boolean equals(Object o) {
			if (this == o) {
				return true;
			}
			if (o == null) {
				return false;
			}
			if (o instanceof HashMapTable.HashMapCell) {
				return this.getRowKey().equals(((Cell<R, C, V>) o).getRowKey())
						&& this.getColumnKey().equals(((Cell<R, C, V>) o).getColumnKey());
			}
			return false;
		}
	}

}