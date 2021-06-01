package listas;


import java.util.AbstractList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


/**
 * Clase que implementa una lista redimensionable
 * @author Daniel Alonso Báscones
 */
public class ListaRedimensionable<E> extends AbstractList<E> {

	
	/*
	 * Atributos de la clase
	 * ---------------------
	 */
	
	/**
	 * Lista que contiene los elementos de la clase
	 */
	private LinkedList<E> lista;

	
	/*
	 * Constructores
	 * --------------------
	 */
	
	/**
	 * Constructor
	 * @param contenido lista con los elemntos de incializacion
	 */
	public ListaRedimensionable(Collection<E> contenido) {
		this.lista = new LinkedList<E>(contenido);
	}
	
	/**
	 * Constructor
	 */
	public ListaRedimensionable() {
		this.lista = new LinkedList<E>();	
	}

	
	/*
	 * Métodos
	 * ------------
	 */
	
	/**
	 * Obtiene el elemento almacenado en essa posicion
	 * @param index la posicion a obtener
	 * @return E el elemento que ocupa la posicion index
	 */
	@Override
	public E get(int index) {
		
		
		/* Obtener una posicion existente */
		if(this.lista.size() > 0 && index <= this.lista.size()-1) {
			// Se obtiene una posicion entre 0 y el maxIndex existente 
			return this.lista.get(index);
		}
		
		/* Obtener una posicion inexistente */
		else {
			this.add(index, null);
			return null;
		}
		
	}

	/**
	 * Obtiene el tamaño de la lista
	 * @return el tamaño de la lista
	 */
	@Override
	public int size() {
		return this.lista.size();
	}
	
	/**
	 * Modifica el valor de una posicion,
	 * Rellena de nulos si es necesario
	 * @param index la posicion a insertar
	 * @param element el elemento a insertar
	 * @return el elemento que ocupaba esa posicion
	 */
	@Override
	public E set(int index, E element) {
		
		
		/* Setear posicion negativa */
		if ( index < 0) {
			//Se añade el elemento a la posicion vacía			
			this.add(index, element);
			return null;
		}
		
		/* Setear una posicion positiva */
		else {
			
			
			/* La posicion esta ocupada */
			if(lista.size() > index) {
				//Se inserta el elemento en la posicion deseada
				//se devuelve el elemento que ocupaba esa posicion
				return lista.set(index, element);			
			}
			
			/* La posicion no existe */
			else {
				
				//Se añade el elemento a la posicion vacía
				this.add(index, element);
				return null;
				
			}
			
		}		
	}
	
	/**
	 * Inserta los elementos en una posicion determinada de la lista
	 * @param index la poscion que ocupa en la lista
	 * @param element el elemento a insertar
	 */
	public void add(int index, E element) {
		

		
		/*
		 * Si la lista esta vacia
		 * ------------------------
		 */
		
		if (lista.size() == 0) {
			
			/* Index <= 0 */
			if(index <= 0) 
				//Se añade en la primera posicion
				lista.add(element);
			
			/* Index > 0 */
			else {
				//Se rellena la lista de nulos hasta llegar a la posicion dedeada
				for (int i=lista.size(); i<index; i++) 
					lista.add(null);
				lista.add(element);
			}
			
			/*Index < 0 */
			
			if(index < 0) {
				//se rellena de nulos epezando por el principio
				// desde 0 hasta index
				for (int i=-1; i>index; i--) 
					lista.addFirst(null);
				lista.addFirst(element);
			}			
		}
		
		/*
		 * Si la lista esta llena
		 * ------------------------
		 */
		
		else {
			
			/* Si el index es negativo */
			if(index < 0) {
				//se rellena de nulos epezando por el principio
				// desde 0 hasta index
				for (int i=-1; i>index; i--) 
					lista.addFirst(null);
				lista.addFirst(element);
			}
			
			
			/* Si el indice es positivo */
			else {
			
				/* El indice es menor que el tamaño de la lista	 */
				if( lista.size() > index ) {
					//Insercion intermedia
					lista.add(index, element);
				}
				
				/* El indice es mayor que el tamaño de la lista */
				else {
					
					//Se rellena de nulos hasta llegar a la posicion deseada
					for (int i=lista.size(); i<index; i++) 
						lista.add(null);
					lista.add(element);
				}
			}
		}		
	}
	
	
	/**
	 * Elimina el elemento que se especifica en el index
	 * @param index la posicion del elemento a eliminar
	 * @return el elemento eliminado
	 */
	public E remove(int index) {
		
		/* 
		 * Se elimina un index inexistente 
		 * ---------------------------------
		 */
		
		
		/* Caso index superior a tamaño de la lista */		
		if(index > lista.size()) {
			
			//Se añaden los elementos nulos necesarios por detras
			//excepto el ultimo, que es el que se eliminaria
			//se retorna el eliminado
			this.add(index-1, null);
			return null;
		}
		
		/* Caso index < 0 */
		else if(index < 0) {
			//Se añaden los elementos nulos necesarios por delante
			//excepto el ultimo, que es el que se eliminaria
			//se retorna el eliminado
			this.add(index+1, null);
			return null;
		}
		
		/* 
		 * Se elimina un index existente 
		 * ---------------------------------
		 */
		
		/* Se elimina el ultimo elemento de la lista */
		else if (index == lista.size()-1) 
			//Se elimina la ultima posicion de la lista
			//se devuelve el elemento que ocupaba esa posicion anteriormente
			return lista.pollLast();
		
		/* Se elimina el primer elemento de la lista */
		else if (index == 0 ) 
			//Se elimina la primera posicion de la lista 
			//se devuelve el elemento que ocupaba esa posicion anteriormente
			return lista.poll();
		
		/* Se elimina por el medio */
		else 
			//Se pone a null esa posicion y se devuelve el elemento
			//que ocupaba esa posicion anteriormente
			return this.lista.set(index, null);
		
	}
	
	/**
	 * Elimina todos los elementos null de la lista
	 */
	public void trim() {
		
		Iterator<E> it = this.iterator();
		
		/*
		 * Se recorre la lista y se eliminan de la misma los elementos nulos
		 */
		
		while (it.hasNext()) {
			E current = it.next();
			if (current.equals(null)) 
				lista.remove(current);
		}
		
	}
	
	

	/**
	 * Comprueba si un objeto es equivalente a la lista
	 * @param o el Objeto a evaluar
	 * @return boolean true si las listas son equivalentes, false sinó
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean equals( Object o) {

		/* Comprobar que es una lista */
	    if (o instanceof List<?>) {

	    	boolean recorridoActivo = true;
	    	
	    	//Declarar Los elementos a comparar
	    	E ci1=null;
	    	E ci2=null;
	    	
	    	Iterator<?> i1 = ((List<?>) o).listIterator();
	    	Iterator<E> i2 = this.lista.iterator();
	    	
	    	// Este bucle va obteniendo elementos y los compara hasta acabar con las listas
	    	while (recorridoActivo) {
	    	
	    		
	    		/* Se obtiene el siguiente elemento no null de cada lista */
	    		
	    		/**
	    		 * Nota:
	    		 * Es necesario usar la sentencia break para salir del bucle
	    		 * con esta implementacion, esto ha sido para obtener una complegidad de 
	    		 * O(n), puede que se pueda usar otra implementacion semejante.
	    		 */
	    		
		    	//lista pasada por parametro	
		    	while (i1.hasNext()) {
		    		
		    		//se recorre el iterador hasta encontrar un no null
		    		ci1 = (E) i1.next();
		    		while (ci1==null) 
		    			ci1 = (E) i1.next(); 
		    		
		    		//Una vez obtenido el elemento rompemos el bucle
		    		break;
		    	}
		    	
		    	//lista de la clase
		    	while (i2.hasNext()) {
		    		
		    		//se recorre el iterador hasta encontrar un no null
		    		ci2 = (E) i2.next();
		    		
		    		while (ci2==null) 
		    			ci2 = (E) i2.next(); 
		    		
		    		//Una vez obtenido el elemento rompemos el bucle
		    		break;
		    	}
		    	
		    	/*
		    	 * Ruptura 1
		    	 * Comparar si los objetos son el mismo
		    	 * ------------------------------------- */
		    
		    	if (!ci1.equals(ci2)) 
		    		return false;
		    		
		    	/* Ruptura 2
		    	 * Comprobar si se ha acabado de recorrer alguna lista 
		    	 * ----------------------------------------------------*/
		    	
		    	//Caso 1 lista 2 acabada
		    	else if ((i1.hasNext() && !i2.hasNext())) {
		    		
		    		// Comprobar si a la otra solo le quedan nulos
		    		while (i1.hasNext()) {
		    			if (i1.next()!=null)
		    				return false;
		    		}		    		
		    	}
		    	
		    	//Caso 2 lista 1 acabada
		    	else if (i2.hasNext() && !i1.hasNext()) {
		    		 
		    		//Comprobar si a la otra solo le quedan nulos
		    		while (i2.hasNext()) {
		    			if (i2.next()!=null)
		    				return false;
		    		}	
		    	 }
		    	
		    	/* Ruptura 3
		    	 * Se han acabado de recorrer todas las listas 
		    	 * -------------------------------------------*/
		    	
		    	else if (!i1.hasNext() && !i2.hasNext()) 
		    		//Se finaliza el bucle
		    		recorridoActivo = false;
		    	
	    	}
	    	
	    	/* Se han obtenido todos los elementos de las listas y son ambas equivalentes */
	    	return true;
	    }

	    /* Alguna condicion no se ha cumplido */
    	return false;
	
	}
	
}