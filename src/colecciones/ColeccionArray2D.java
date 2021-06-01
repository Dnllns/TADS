package colecciones;


import java.util.AbstractCollection;
import java.util.Iterator;
import java.util.NoSuchElementException;


/**
 * @author Daniel Alonso Báscones
 *
 */

public class ColeccionArray2D<E> extends AbstractCollection<E> implements Iterable<E>{
	
	
	protected E lista2D[][];
	protected int dimX;
	protected int dimY;
	

	/**
	 * Constructor
	 * @param contenido
	 */
	public ColeccionArray2D(E[][] contenido) {
		//Lista de 2 dimensiones
		this.lista2D= (E[][]) contenido;
		this.dimX=lista2D[0].length;
		this.dimY=lista2D.length;
		
	}

	/**
	 * Obtiene el metodo Un obteto de tipo iterador
	 */
	@Override
	public Iterator<E> iterator() {
		return new Iterator2D();
	}

	/**
	 * Obtiene el tamaño de la lista
	 */
	@Override
	public int size() {
		//dim Y * dim X
		return lista2D.length*lista2D[0].length;
	}

	
	/**
	 * Clase que implementa el iterador
	 * @author Daniel Alonso Bácones
	 *
	 */
	private class Iterator2D implements Iterator<E> {
		

		private int posY;
		private int posX;
		private int elementosDevueltos;
		private int elementosTotales;	
		
		public Iterator2D() {
						
			//Posicion actual de la lista 2D en la iteracion
			this.posX=-1;
			this.posY=0;
		
			//tamaño de la lista 1D
			this.elementosTotales=lista2D.length*lista2D[0].length;

			
			//Elementos devueltos por el iterador
			this.elementosDevueltos=0;
		}

		@Override
		public boolean hasNext() {
			return elementosDevueltos < elementosTotales;
		}

		@Override
		public E next() {
			
			if(posY == dimY-1) {
				
				/*
				 * Aqui estamos controlando la ultima fila!
				 * ----------------------------------------
				 */
				
				if(posX > dimX-1 ) { 					
					/*
					 * Caso
					 * No quedan mas elementos el la fila
					 */
					throw new NoSuchElementException();
					
				} else { 
					/* 
					 * Caso
					 * Todavía quedan elementos en la fila
					 */
					posX++;	
				}
			} else {	
				
				/*
				 * Para el resto de filas != a la ultima
				 * -------------------------------------
				 */
				
				if(posX == dimX-1) {
					
					/*
					 * Caso
					 * Ultima posicion de la fila => cambio de fila
					 */
					posY++; 
					posX=0; 
					
				} else { 
					/*
					 * Caso
					 * Actualizar posicion en la fila
					 */
					posX++;
				}
			}
			
			//Actualizar contador de elementos devueltos
			this.elementosDevueltos++;
	
			return lista2D[posY][posX];		
		}
		
		
		/**
		 * Elimina un elemento de la coleccion
		 */
		@Override
		public void remove() {
			lista2D[posY][posX]=null;		
		}
		
		
	}
}
