package busqueda;

import java.util.Iterator;
import java.util.Queue;

/**
 * Interfaz para el contenedor de nodos (frontera)
 * Lo hacemos iterable para poder recorrerlo/modificarlo con iteradores
 * @author ines
 * @version 2018.10.*
 */
public abstract class Frontera<Estado,Accion> implements Iterable<Nodo<Estado,Accion>>{
	// ATRIBUTO
	protected Queue<Nodo<Estado,Accion>> frontera; // contenedor secuencial (LIFO, FIFO o con prioridad) de nodos frontera
		
	// METODOS DE FRONTERA
	/**
	 * @return Nodo<Estado,Accion>, el primero de la frontera
	 */
	public abstract Nodo<Estado,Accion> primero();
	
	/**
	 * aniade un nodo nuevo a la frontera (donde proceda)
	 * @param Nodo<Estado,Accion> n
	 */
	public abstract void aniade( Nodo<Estado,Accion> n );
	
	/**
	 * contiene
	 * @param n, un Nodo
	 * @return cierto si contiene un nodo para el mismo estado que n, falso en otro caso
	 */
	public boolean contiene( Nodo<Estado,Accion> n ){
		return frontera.contains(n); // la comparacion de nodos se basa en el estado
	}
	
	/**
	 * contieneNodo
	 * @param n, un Nodo
	 * @return otron, un Nodo en la frontera cuyo estado es el mismo que el de n (null si no lo hay)
	 */
	public Nodo<Estado,Accion> contieneNodo(Nodo<Estado,Accion> n) {
		Iterator<Nodo<Estado,Accion>> it = frontera.iterator();
		boolean esta = false;
		Nodo<Estado,Accion> el=null;
		while (it.hasNext() && !esta ){
			el = it.next();
			if(el.getEstado().equals(n.getEstado()))
				esta=true;			
		}
		return ( esta ? el : null );
	}
	
	/**
	 * metodo para dejar el contenedor vacio
	 */
	public void clear(){
		frontera.clear();
	}
	
	/**
	 * @return cierto si el contenedor es vacio, false en otro caso
	 */
	public boolean esVacia(){
		return frontera.isEmpty();
	}

	/**
	 * Observador del tamanio
	 * @return el tamanio del contenedor frontera
	 */
	public int tamanio() {
		return frontera.size();
	}
	
	// IMPLEMENTACION INTERFAZ ITERABLE
	public Iterator<Nodo<Estado,Accion>> iterator() {
		return frontera.iterator();
	}
}
