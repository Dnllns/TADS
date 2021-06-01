package arboles;



import java.util.AbstractSet;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.LinkedList;


/**
 * Clase abstracta que implementa un arbol no binario sin posibilidad de contener elementos repetidos
 * @author Daniel Alonso
 * @param <E> Elemento generico
 */
public class ArbolGenerico<E> extends AbstractSet<E> {

	/* Atributos */

	/** El numero de elementos en el arbol */
	private int length = 0;
	/** El nodo raiz */
	private Nodo root = null;

	
	/**
	 * Clase auxiliar del arbol, Implementa un nodo contenido en el arbol
	 * Tiene referencias a padre e hijos, asi como metodos de utilidad
	 * @author Daniel Alonso
	 */
	private class Nodo {

		/*Atributos*/
		
		/** El elemento almacenado en el nodo */
		private E element;
		/** Referencia al padre del nodo */
		private Nodo father;
		/** Lista con los hijos del nodo */
		private List<Nodo> children;

		/*Metodos*/
		
		/**
		 * Constructor
		 * @param elemento el elemento contenido en el nodo 
		 */
		public Nodo(E elemento) {
			
			//Asignamos los atributos
			this.element=elemento;
						
			//Inicializamos la lista de hijos
			children = new LinkedList<Nodo>();
		}

		/**
		 * Obtener el elemento contenido en el nodo
		 * @return el elemento E
		 */
		private E getElement() {
			return element;
		}

		/**
		 * Se obtienen los hijos de un nodo
		 * @return lista con los hijos
		 */
		private List<Nodo> getChildren() {
			return children;
		}
		
		/**
		 * Se obtienen los descendientes de un nodo
		 * @return lista con los descendientes
		 */
		private List<Nodo> getDescendats(){
			
			List<Nodo> descendientes = new LinkedList<Nodo>();
			
			//Obtenemos descendientes hasta que sean hoja 
			List<Nodo> hijos = getChildren();
			descendientes.addAll(hijos);
			
			for (Nodo hijo : hijos ) {
				descendientes.addAll(hijo.getDescendats());
			}
			
			return descendientes;			
		}
		
		/**
		 * Se le asigna al nodo un nodo hijo 
		 * @param hijo
		 */
		private void addChild(Nodo hijo) {
			children.add(hijo);
			hijo.father = this;
		}
		
		/**
		 * Se calcula la altura de un nodo
		 * Implementacion recursiva
		 * @return altura del nodo en el arbol
		 */
		private int nodeHeight() {

		    //Obtenemos sus hijos
		    List<Nodo> hijos = this.getChildren();

		    //Comprobamos la altura de cada hijo
		    int altura=0;
		    for (Nodo hijo : hijos) {
		    	int alturaHijo = hijo.nodeHeight();
		        
		    	//Si el hijo tiene mas altura que la actual del nodo, reasignamos la altura
		    	if(altura < alturaHijo) 
		        	altura = alturaHijo;
		    }

		    //Devuelve la altura de su hijo + 1
		    return altura+1;
		}
		
		/** 
		 * Se obtienen una lista de nodos ordenados en preorder
		 * @return lista de nodos en preorder
		 */
		private List<Nodo> nodePreorder(){
			
			List<Nodo> lista = new LinkedList<Nodo>();
			lista.add(this);
			List<Nodo> hijos = this.getChildren();
			for (Nodo hijo : hijos) {
				lista.addAll(hijo.nodePreorder());
			}
			return lista;

		}
	
		
		/**
		 * Realiza una busqueda del elemento entre los descendientes del nodo
		 * Implementacion recursiva
		 * @param buscado el elemento que se desea buscar
		 * @return el nodo que contiene el elemento
		 */
		private Nodo searchChild(Object buscado){
			
			//Si eeste nodo contiene el elemento buscado
		    if (this.getElement()==buscado)
		    	//return este nodo
		        return this;
		    
		    //Obtenemos los hijos del nodo
		    List<Nodo> hijos = this.getChildren(); 
		    Nodo nodoBuscado = null;
 
		    //Se busca en sus hijos hasta encontrarlo o recorrerlos todos
		    for (Nodo hijo : hijos) {		   
		    	if(nodoBuscado == null) nodoBuscado = hijo.searchChild(buscado);
		    	else break;		    	
		    }
		    
		    //Se devuelve el resultado de la busqueda
		    return nodoBuscado;
		 }
		
	}
	
	/*Metodos*/
	
	/* Obligatorios de AbstractSet */
	/*-----------------------------------------------------------------------------------*/

	/**
	 * Iterador que devolverá los elementos contenidos uno a uno en el mismo orden que se
	 * obtendría al realizar un recorrido en preorder. Para el resto del
	 * comportamiento se seguirá lo indicado en el interfaz Set.
	 */
	@Override
	public Iterator<E> iterator() {
		return preOrderTraverse().iterator();
	}

	
	/**
	 * Obtiene el numero de elementos contenidos en el arbol
	 */
	@Override
	public int size() {
		return length;
	}

	/* A implementar por el alumno */
	/*-----------------------------------------------------------------------------------*/

	/**
	 * Permite realizar la operación de inclusión de un elemento en el árbol. Se
	 * pasarán dos parámetros: el elemento a insertar y el elemento que se
	 * considerará su padre. Cada nueva inserción sobre un padre se considerará
	 * realizada siempre a la derecha de los hijos ya incluidos. Si el padre es
	 * nulo, se considerará el elemento como la raíz del árbol. Si el padre no está
	 * incluido en el árbol previamente o se quiere añadir una raíz dos veces, se
	 * lanzará la excepción IllegalArgumentException. Para el resto del
	 * comportamiento se seguirá lo indicado en el interfaz Set para el método add.
	 * 
	 * @param padre el elemento que hace de padre
	 * @param hijo el elemento a insertar
	 * @return true si se ha insertado, false sinó
	 */
	public boolean add(E padre, E hijo) {

		if (padre == null) {							
			//Se quiere añadir una raiz
			if (root != null) 							
				//Ya existe una raiz
				//Elemento no insertado
				throw new IllegalArgumentException();
			//Se crea la raiz
			root = new Nodo(hijo);
			//Se incrementa el tamaño del arbol
			this.length++;
			//Elemento insertado correctamente
			return true;
		}

		else { 
			// No se quiere añadir una raiz
			
			//Se comprueba si ya existe ese elemento en el arbol
			if(this.contains(hijo)) 
				return false;
			
			// Buscamos el padre desde la raiz
			Nodo nodoPadre = root.searchChild(padre);
			
			if (nodoPadre==null) 
				//El padre no existe
				//Elemento no insertado
				throw new IllegalArgumentException();
			else {	
				//El padre existe
				//Le añadimos un hijo mas
				nodoPadre.addChild(new Nodo(hijo)); 
				//Actualizamos el tamaño del arbol
				this.length++;
				//Elemento Insertado correctamente
				return true;
			}
		}
	}

	/**
	 * Permite realizar la operación de eliminación de un elemento en el árbol. En
	 * caso de que el elemento tuviera nodos hijo, pasarán a ser hijos del padre del
	 * elemento eliminado. En caso de ser la raíz, se elegirá al primero de sus
	 * hijos (el situado más a la izquierda) para ser la nueva raíz. Los hijos de la
	 * raíz original pasarán a estar a la derecha de los hijos iniciales de la nueva
	 * raíz. Para el resto del comportamiento se seguirá lo indicado en el interfaz
	 * Set.
	 */
	@Override
	public boolean remove(Object objeto) {
		
		//eliminar la raiz
		if (objeto.equals(root.getElement())) {
			//La raiz tiene hijos
			if (root.getChildren().size()!=0) {
				//Obtenemos los hijos de la raiz original
				List<Nodo> hijosRaiz = root.getChildren();
				
				//primer hijo pasa a ser la raiz
				root = hijosRaiz.get(0);
				
				//Añadimos los hijos de la antigua raiz a la nueva
				hijosRaiz.remove(0);
				root.getChildren().addAll(hijosRaiz);
				length--;
			}
			//La raiz no tiene hijos
			else root = null;
			return true;
		}
		
		//Eliminar un elemento no raiz
		else{
			
			Nodo nodoEliminado = root.searchChild(objeto);

			if (nodoEliminado == null) return false;
			
			//Obtenemos sus hijos
			List<Nodo> hijosNodo = nodoEliminado.getChildren();

			//Eliminamos este nodo de los hijos de su padre
			Nodo padre = nodoEliminado.father;
			padre.getChildren().remove(nodoEliminado);
			
			//Añadimos sus hijos al padre
			padre.getChildren().addAll(hijosNodo);
			
			//Redicimos el tamaño del arbol
			length--;
			
			return true;
		}
	
	}

	/**
	 * Devolverá una lista con todos aquellos elementos que sean descendientes (directos o
	 * indirectos) del elemento que se pasa como parámetro. No se especifica un
	 * orden concreto. En caso de no estar contenido el elemento sobre el que se
	 * consulta, se devolverá una lista vacía.
	 * 
	 * @param padre el elemento padre
	 * @return una lista con los hijos
	 */
	public List<E> descendants(E padre) {
		
		//Buscamos el nodo padre
		Nodo nodoPadre = root.searchChild(padre);					
		
		//Obtenemos una lista con los nodos descendientes
		List<Nodo> nodosDescendientes;
		if (nodoPadre != null)
			nodosDescendientes = nodoPadre.getDescendats();	
		else
			nodosDescendientes = new LinkedList<Nodo>();
		
		//Añadimos a la lista los elementos de cada nodo
		List<E> descendientes = new LinkedList<E>();						
		for (Nodo n : nodosDescendientes) 
			descendientes.add(n.getElement());
		
		return descendientes;
	}

	/**
	 * Devolverá una lista con todos los elementos contenidos en el árbol, en el
	 * orden en que aparecen en un recorrido en anchura del mismo. En caso de que el
	 * árbol esté vacío, se devolverá una lista vacía
	 * 
	 * @return Lista de elementos en orden de anchura
	 */
	public List<E> breathFirstTraverse() {
		
		
		List<E> salida = new LinkedList<E>();		//Lista con los elementos en anchura
		//Control de arbol vacio
		if (this.size()==0) return salida;

		Queue<Nodo> cola = new LinkedList<Nodo>();	//Cola de recorrido
		cola.add(root);
		
		while(!cola.isEmpty()) {				//Mientras la cola tenga elementos loop
			Nodo actual = cola.poll();			//Extraemos el primer elemento de la cola para trabajar con el
			salida.add(actual.getElement());	//Lo añadimos a la salida
			cola.addAll(actual.getChildren());  //Si tiene hijos los añadimos a la cola
		}

		return salida;
	}

	/**
	 * Devolverá una lista con todos los elementos contenidos en el árbol, en el
	 * orden en que aparecen en un recorrido en profundidad en preorden del mismo.
	 * En caso de que el árbol esté vacío, se devolverá una lista vacía.
	 * 
	 * @return lista de elementos en orden preorden
	 */
	public List<E> preOrderTraverse() {
		
		//Obtenemos una lista de nodos en recorrido preorder 
		 List<E> recorrido = new LinkedList<E>();
		//Control de arbol vacio
		if (this.size()==0) return recorrido;
		 
		 List<Nodo> listaNodos = root.nodePreorder();
		 
		 //Almacenamos los elementos en una lista
		 for (Nodo n : listaNodos) {
			 recorrido.add(n.getElement());
		 }
		 
		 //Devolvemos la lista
		 return recorrido; 
	}
	

	/**
	 * Devolverá un entero que indicará la altura del elemento pasado como parámetro
	 * dentro del árbol. Si el elemento no se encuentra en el árbol se devolverá un -1
	 * 
	 * @param elemento el elemento del que se desea conocer la altura
	 * @return la altura del elemento en el arbol
	 */
	public int height(E elemento) {
		
		//Obtenemos el nodo que contiene el elemento
		Nodo buscado = root.searchChild(elemento);
		//El nodo no existe en el arbol
		if (buscado == null) return -1;
		//Obtenemos la altura del nodo
		else return buscado.nodeHeight() -1;
		
	}
	
}
