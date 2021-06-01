package colecciones;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;


/**
 * @author Daniel Alonso Báscones
 *
 */


/**
 * Esta clase extiende la clase ColeccionArray2D, implementada en prácticas anteriores.
 * Por lo tanto, deberá seguir implementando el funcionamiento de la clase ya implementada (herencia), 
 * incluyendo varios otros comportamientos nuevos, pensados como utilidades de consulta sobre la
 * estructura de datos en 2D.  
 *
 * @param <E> Tipo de datos contenidos en el array 2D que 
 */
public class ColeccionArray2DUtilidades <E> extends ColeccionArray2D <E> {

	public ColeccionArray2DUtilidades(E[][] contenido) {
		super(contenido);
	}

	/**
	 * Este método permite localizar el elemento más frecuente en la colección.
	 * Es decir el elemento que aparece más veces repetido en el contenido de la misma
	 *  
	 * @return Elemento con mayor frecuencia en el contenido de la coleccion
	 */
	public E masFrecuente() {
		
		
		/** La posicion en el ArrayList del elemento mas frecuente */
		int posMasFrecuente=-1;

		/** Lista que contiene la frecuncia de cada elemento */
	    List<Item> frecuencias = new ArrayList<>();

	    /** Utilizo el iterador para desplazarme por el array*/
	    Iterator<E> it = this.iterator();
	    
	    
	    
	    /*
	     * Proceso de busqueda del que mas veces aparece
	     */
	    while (it.hasNext()) {
	    	
	    	//obtener el elemento
	    	E e = it.next();

	    	if(posMasFrecuente!=-1) {
	    		
	    		/*Ya existe un elemento mas frecuente*/
	    		
	    		if( e.equals(frecuencias.get(posMasFrecuente).item) ) 
	    			//En el caso de que el acual sea el mas frecuente, actualizo su frecuencia
	    			frecuencias.get(posMasFrecuente).frecuencia++;	    			
	    			
	    		else {
	    			
	    			//El elemento actual no es el mas frecuente
	    			//Le buscamos en la lista y actualizamos la frecuencia
	    			
	    			Iterator<Item> frecIterator = frecuencias.iterator();
	    			boolean encontrado = false;
	    			int pos = 0;
	    			while (frecIterator.hasNext() && !encontrado) {
	    				
	    				if(frecIterator.next().item.equals(e)) {
	    					//Una vez localizado el elemento actualizamos su frecuencia
	    					encontrado=true;
	    					frecuencias.get(pos).frecuencia++;
	    					
	    					if (frecuencias.get(pos).frecuencia > frecuencias.get(posMasFrecuente).frecuencia ) 
			    				//Comprobamos si con la nueva frecuencia es mas frecuente que el anterior mas frecuenete
		    					// Si es asi ahora va a ser el mas frecuente
		    					posMasFrecuente = pos;
	    				} 
	    				
	    				pos++;
	    			}	 			
	    			
	    			if(!encontrado)
	    				//El elemento actual ees nuevo -> no existe en la lista de frecuencias
	    				frecuencias.add(new Item(e,1));
	    		}
	    		
	    	} else {
	    		
	    		/* Si es el primer elemento mirado va a ser siempre el mas freceunte*/ 
	    		posMasFrecuente=0;
	    		frecuencias.add(new Item(e, 1));
	    	}
	    		
	    }		
	    
	    /* Se retorna el mas frecuente*/
    	return frecuencias.get(posMasFrecuente).item;
	}
	
	
	/**
	 * Clase de apollo para el metodo masFrecuente()
	 * @author dnllns
	 *
	 */
	class Item{

		E item;
		int frecuencia;
		
		public Item(E item, int frec) {
			this.item=item;
			this.frecuencia=frec;
		}
	}

	
	
	
	//Usando un HashMap
	//public E masFrecuente() {
	//		
	//		/** El elemento que mas veces se repite*/
	//		E masFrecuente = null;
	//		
	//		/** Las veces que aparece repetido*/
	//		int vecesAparece = 0;
	//		
	//		/** Utilizo un hashmap para ir almacenando los elementos y acceder a los elementos en O(1)*/
	//	    HashMap<E, Integer> mapa = new HashMap<E, Integer>();
	//	    
	//	    /** Utilizo el iterador para desplazarme por el array*/
	//	    Iterator<E> it = this.iterator();
	//	    
	//	    
	//	    /*
	//	     * Proceso de busqueda del que mas veces aparece
	//	     */
	//	    while (it.hasNext()) {
	//	    	
	//	    	//obtener el elemento
	//	    	E e = it.next();
	//	    	
	//	    	//Buscarlo en el mapa
	//	    	Integer veces = mapa.get(e);
	//
	//	    	//Si esta guardado incremento las veces que aprece
	//	    	if(veces!=null) {
	//	    		
	//	    		//obtengo las veces que ha aparecido anteriormente
	//	    		mapa.put(e, ++veces);
	//	    		
	//	    		//Compruebo si ahora este elemento es el que mas aparece
	//	    		if(veces>vecesAparece) {
	//	    			//Actualizo quien es el ms frecuente
	//	    			masFrecuente=e;
	//	    			//incremento las veces que aprece
	//	    			vecesAparece++;
	//	    		}
	//	    		
	//	    	}else {
	//	    		mapa.put(e,1);
	//	    	}
	//	    		
	//	    }		
	//	    
	//	    
	//	    /*
	//	     * se retorna el mas frecuente
	//	     */
	//    	return masFrecuente;
	//	}

	
	
	/**
	 * Todos los objetos en Java tienen asignado un código individual y único
	 * a lo largo de la ejecución de un programa. Esto permite identificar unívocamente
	 * cada objeto y realizar organizaciones y busquedas más eficientes de los datos. Veremos estas
	 * más adelante en la asignatura.
	 * 
	 * El código mencionado se llama Hash Code y se puede obtener de cualquier objeto con la llamada:
	 * nombreObjeto.hashCode();
	 * Este método devuelve un numero entero (único) para cada objeto.
	 * 
	 * Se pide que este método devuelva el sumatorio de los hashCode de todos los objetos que almacena
	 * en su interior.
	 * 
	 * @return sumatorio de los códigos hash de todos los elementos contenidos en la colección
	 */
	public int sumaHash() {

		//Instanciar un iterador para recorrer la coleccion
		Iterator<E> it = this.iterator();
		
		int suma=0;
		//voy almacenando en la variable suma las sumas de los hash
		while (it.hasNext()) 
			suma+=it.next().hashCode();
		
		return suma;
		
	}

	/**
	 * En este metodo se solicita comprobar la diferencia del valor del codigo hash de uno de los
	 * elementos contenidos en la colección con el resto de elementos que aparecen en la misma.
	 * 
	 * Si consideramos que el codigo hash está relacionado con el contenido de cada objeto, esto 
	 * nos permitiría conocer "como de similar" es el objeto seleccionado con el resto de los contenidos.  
	 * 
	 * @param posicion que ocupa el elemento que se quiere comprobar, en una iteracion secuencial en la coleccion
	 * @return array de enteros que incluye la diferencia del código hash del elemento que ocupa esa posición 
	 * en una iteración secuencial, con el código hash del elemento elegido como referencia (ver parametro de entrada).
	 */
	public int[] diferenciasHash(int posicion) {
	
		
		/*
		 * Obtener el elemento buscado y hashearlo
		 */
		//Instancio un iterador para recorrer la coleccion
		Iterator<E> it = this.iterator();
		
		//Recorrer el harray hasta situarse antes del objeto de la posicion deseado
		int contador = 0;
		while (contador<posicion && it.hasNext()) {
			it.next();
			contador++;
		}
		
		//Obtener el objeto deseado y hashearlo;
		int hashBuscado = it.next().hashCode();
		
		/*
		 * A partir de aqui empezar a calcular la diferencia de elementos
		 * desde posicion hasta el final
		 */
		
		//Instanciar el array de retorno
		int[] array = new int[this.size()];
		
		//Duplico la variable posicion actual para no machacarla despues
		int posicionActual = posicion;
		
		//Establezco el valor actual de la resta de su hash con su hash -> 0
		//Incremento posicion actual
		array[posicionActual++] = 0;
		
		//Obtengo las restas desde este elemento hasta el final
		while (posicionActual < this.size()) 
			array[posicionActual++] = Math.abs(it.next().hashCode() - hashBuscado);
		
		/*
		 * Ahora queda obtener los elementos desde el principio a la posicion
		 */
		
		//Vuelvo a instanciar el iterador
		it = this.iterator();
		
		//Recorro el array de inicio hasta posicion
		contador = 0;
		while(contador < posicion) 
			array[contador++] = Math.abs(it.next().hashCode() - hashBuscado);

		
		/*
		 * Devolver array de restas 
		 */

		return array;
		
	
	}

	/**
	 * Matriz de diferencias -> O(n^2)
	 *  
	 * @return
	 */
	public int[][] diferenciasHash() {

		/**
		 * diferencia de hashes todos con todos
		 */


		//Variable que almacenara los hashes
		int[][] resultados = new int[this.size()][this.size()];
		
		
		
		Iterator<E> itComparados = this.iterator();
		Iterator<E> itRestas;


		int countX=0;
		int countY=0;
		int cmpHash;
				
		
		while (itComparados.hasNext()) {
		
			/*
			 * Bucle externo, 
			 * Su funcion es obtener el hash de los numeros de referencia
			 */
			
			//Obtengo el hash de comparacion actual
			cmpHash = itComparados.next().hashCode();
			
			//Instancio de nuevo el iterador
			itRestas=this.iterator();
			
			while (itRestas.hasNext()) {
				
				/*
				 * Bucle interno,
				 * Su funcion restar al hash de referencia el resto de hashes
				 */

				
				//Asignar resultado de la resta en el array
				//Incrementar la variable de posicion de columna
				resultados[countY][countX++]=Math.abs(cmpHash-itRestas.next().hashCode());

			}
			
			/*
			 *Modificar  las variables de posicion para el array
			 */
			
			//Las X se ponen a 0 (comienzo de columna)
			countX=0;
			//Incrementar la variable Y (Cambio de fila)
			countY++;
		}
		

		return resultados;
		
		
	
	}
	
	/**
	 * Método que devuelve la posición, en una iteracion secuencial, en la que aparecería el elemento
	 * que se pasa como parámetro. Si el elemento se encuentra en la primera posición de la iteración, el valor
	 * a devolver es 0, de forma análoga a las posiciones de un array. 
	 * En caso de que el elemento no se encuentre en la colección, se devolverá un número menor que 0 para indicarlo.
	 * 
	 * En este caso se solicita al alumno que realice esta búsqueda de forma secuencial. Es decir, recorriendo el contenido
	 * hasta localizar el elemento. 
	 * 
	 * @param buscado Elemento que se solicita buscar en la coleccion
	 * @return posicion en la que aparecerá el elemento en una iteracion o número negativo si no aparecerá
	 */
	public int busquedaSecuencial(E buscado) {

		/* 
		 * Se recorre el iterador en busca del objetivo
		 */
		Iterator<E> it = this.iterator();
		int posicion=0;
		while (it.hasNext() && !it.next().equals(buscado)) {
			posicion++;
		}

		/*
		 * Condiciones de retorno
		 */
		if ( !it.hasNext() && posicion==this.size())
			//El iterador ha pasado por todos los elementos => !hasNext
			//El ultimo elemento no era el buscado, 
			//al haber llegado la variable posicion a un elemento mas  => posicion==this.size()
			return -1;
		else
			//elemento encontrado
			return posicion;	
		
	}
		
	/**
	 * Método que devuelve la posición, en una iteracion secuencial, en la que aparecería el elemento
	 * que se pasa como parámetro. Si el elemento se encuentra en la primera posición de la iteración, el valor
	 * a devolver es 0, de forma análoga a las posiciones de un array. 
	 * En caso de que el elemento no se encuentre en la colección, se devolverá un número menor que 0 para indicarlo.
	 * 
	 * En este caso se solicita al alumno que realice esta búsqueda empleando el método de 
	 * <a href=https://es.wikipedia.org/wiki/B%C3%BAsqueda_binaria> búsqueda binaria</a>. 
	 * Para ello, el alumno deberá:
	 * 1. Realizar la ordenación del contenido. Se puede alamacenar en un array o lista nueva y ordenar ésta.
	 * Ver método: Arrays.sort()
	 * 2. Realizar la busqueda binaria: comprobar los elementos de los extremos y el cento, seleccionar el subconjunto
	 * en el que se debe continuar la búsqueda y repetir. 
	 * 
	 * @param buscado Elemento que se solicita buscar en la coleccion
	 * @return Posicion en la que aparecerá el elemento en una iteracion o número negativo si no aparecerá
	 */
	
	@SuppressWarnings("unchecked")
	public int busquedaBinaria(E buscado) {
		
		/*
		 * Preparaciones previas
		 */
		
		//Instanciar un array de tipo generico
		E[] array = (E[]) Array.newInstance(buscado.getClass(), this.size());
		
	    //Rellenear el array usando el iterador
		Iterator<E> it = this.iterator();
		int c=0;
		while (it.hasNext()) 
			array[c++] = it.next();
			
		// Ordenar el array
		Arrays.sort(array);
		
		
		/* 
		 * Busqueda binaria
		 */
		
		int punteroValorMedio;
		int punteroLimiteSuperior = array.length - 1;
		int punteroLimiteInferior = 0;

		while (punteroLimiteSuperior >= punteroLimiteInferior) {
			//Mientras el array no se haya reducido al maximo
			
			//Obtener el punto medio del array para usar como referencia
			punteroValorMedio = (punteroLimiteInferior + punteroLimiteSuperior) / 2;

			if (((Comparable<E>) buscado).compareTo(array[punteroValorMedio]) == 0)
				//Elemento encontrado
				return punteroValorMedio;
			
			else if ( ((Comparable<E>) buscado).compareTo(array[punteroValorMedio]) < 0) 
				//El elemento se encuentra en la mitad inferior del array
				//Reducir el array a la mitad (inicio, mitad)
				punteroLimiteSuperior = punteroValorMedio - 1;
			
			else if ( ((Comparable<E>) buscado).compareTo(array[punteroValorMedio]) > 0 ) 
				//El elemento se encuentra en la mitad superior del array
				//Reducir el array a la mitad (mitad, final)
				punteroLimiteInferior = punteroValorMedio + 1;
			
		}
		
		
		//Elemento no encontrado 
		return -1;
	}	
	

	
}
