package busqueda;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Frontera ordenada por prioridad
 * (el criterio de comparacion de nodos puede ser g(), h() o f())
 * @author ines
 * @version 2018.10.*
 */
public class FronteraPrioridad<Estado,Accion> extends Frontera<Estado,Accion> {
	
	// CONSTRUCTORES
	/**
	 * Constructor por defecto
	 * Crea la cola vacia, para ordenarla segun g()
	 */
	public FronteraPrioridad() {
		Comparator<Nodo<Estado,Accion>> comp = new ComparaNodos<Estado,Accion>();// comparador por defecto, segun g
		frontera = new PriorityQueue<Nodo<Estado,Accion>>(10,comp); // cola de prioridad ordenada por comp
	}
	
	/**
	 * Constructor a partir de un criterio
	 * Ordena la cola segun el criterio que se da
	 * @param crit el Criterio de ordenacion de la cola de prioridad
	 */
	public FronteraPrioridad(Criterio crit){
		Comparator<Nodo<Estado,Accion>> comp = new ComparaNodos<Estado,Accion>(crit);
		frontera = new PriorityQueue<Nodo<Estado,Accion>>(10,comp);
	}
	
	/**
	 * Constructor a partir de un comparador de nodos
	 * Ordena la cola segun el criterio del comparador
	 * @param comp el ComparaNodos utilizado para ordenar la cola
	 */
	public FronteraPrioridad( ComparaNodos<Estado,Accion> comp ){
		frontera = new PriorityQueue<Nodo<Estado,Accion>>(10,comp);		
	}
	
	// METODOS HEREDADOS DE LA INTERFAZ FRONTERA
	/**
	 * @see busqueda.Frontera#primero()
	 */
	@Override
	public Nodo<Estado,Accion> primero() {
		return frontera.poll();
	}

	/**
	 * @see busqueda.Frontera#aniade(busqueda.Nodo)
	 */
	@Override
	public void aniade(Nodo<Estado,Accion> n) {
		frontera.offer(n);
	}

}

	

