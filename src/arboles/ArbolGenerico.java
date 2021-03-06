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
	 * Iterador que devolver?? los elementos contenidos uno a uno en el mismo orden que se
	 * obtendr??a al realizar un recorrido en preorder. Para el resto del
	 * comportamiento se seguir?? lo indicado en el interfaz Set.
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
	 * Permite realizar la operaci??n de inclusi??n de un elemento en el ??rbol. Se
	 * pasar??n dos par??metros: el elemento a insertar y el elemento que se
	 * considerar?? su padre. Cada nueva inserci??n sobre un padre se considerar??
	 * realizada siempre a la derecha de los hijos ya incluidos. Si el padre es
	 * nulo, se considerar?? el elemento como la ra??z del ??rbol. Si el padre no est??
	 * incluido en el ??rbol previamente o se quiere a??adir una ra??z dos veces, se
	 * lanzar?? la excepci??n IllegalArgumentException. Para el resto del
	 * comportamiento se seguir?? lo indicado en el interfaz Set para el m??todo add.
	 * 
	 * @param padre el elemento que hace de padre
	 * @param hijo el elemento a insertar
	 * @return true si se ha insertado, false sin??
	 */
	public boolean add(E padre, E hijo) {

		if (padre == null) {							
			//Se quiere a??adir una raiz
			if (root != null) 							
				//Ya existe una raiz
				//Elemento no insertado
				throw new IllegalArgumentException();
			//Se crea la raiz
			root = new Nodo(hijo);
			//Se incrementa el tama??o del arbol
			this.length++;
			//Elemento insertado correctamente
			return true;
		}

		else { 
			// No se quiere a??adir una raiz
			
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
				//Le a??adimos un hijo mas
				nodoPadre.addChild(new Nodo(hijo)); 
				//Actualizamos el tama??o del arbol
				this.length++;
				//Elemento Insertado correctamente
				return true;
			}
		}
	}

	/**
	 * Permite realizar la operaci??n de eliminaci??n de un elemento en el ??rbol. En
	 * caso de que el elemento tuviera nodos hijo, pasar??n a ser hijos del padre del
	 * elemento eliminado. En caso de ser la ra??z, se elegir?? al primero de sus
	 * hijos (el situado m??s a la izquierda) para ser la nueva ra??z. Los hijos de la
	 * ra??z original pasar??n a estar a la derecha de los hijos iniciales de la nueva
	 * ra??z. Para el resto del comportamiento se seguir?? lo indicado en el interfaz
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
				
				//A??adimos los hijos de la antigua raiz a la nueva
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
			
			//A??adimos sus hijos al padre
			padre.getChildren().addAll(hijosNodo);
			
			//Redicimos el tama??o del arbol
			length--;
			
			return true;
		}
	
	}

	/**
	 * Devolver?? una lista con todos aquellos elementos que sean descendientes (directos o
	 * indirectos) del elemento que se pasa como par??metro. No se especifica un
	 * orden concreto. En caso de no estar contenido el elemento sobre el que se
	 * consulta, se devolver?? una lista vac??a.
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
		
		//A??adimos a la lista los elementos de cada nodo
		List<E> descendientes = new LinkedList<E>();						
		for (Nodo n : nodosDescendientes) 
			descendientes.add(n.getElement());
		
		return descendientes;
	}

	/**
	 * Devolver?? una lista con todos los elementos contenidos en el ??rbol, en el
	 * orden en que aparecen en un recorrido en anchura del mismo. En caso de que el
	 * ??rbol est?? vac??o, se devolver?? una lista vac??a
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
			salida.add(actual.getElement());	//Lo a??adimos a la salida
			cola.addAll(actual.getChildren());  //Si tiene hijos los a??adimos a la cola
		}

		return salida;
	}

	/**
	 * Devolver?? una lista con todos los elementos contenidos en el ??rbol, en el
	 * orden en que aparecen en un recorrido en profundidad en preorden del mismo.
	 * En caso de que el ??rbol est?? vac??o, se devolver?? una lista vac??a.
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
	 * Devolver?? un entero que indicar?? la altura del elemento pasado como par??metro
	 * dentro del ??rbol. Si el elemento no se encuentra en el ??rbol se devolver?? un -1
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
