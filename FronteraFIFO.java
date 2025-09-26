package busqueda;

import java.util.LinkedList;

/**
 * Frontera FIFO (cola)
 * @author Ines
 * @version 2018.10.*
 */

public class FronteraFIFO<Estado,Accion> extends Frontera<Estado,Accion> {
	
	/**
	 * Constructor
	 * Construye un contenedor (lista FIFO o cola) vacio
	 */
	public FronteraFIFO (){
		frontera = new LinkedList<Nodo<Estado,Accion>>();
	}

	/**
	 * @see busqueda.Frontera#primero()
	 */
	@Override
	public Nodo<Estado,Accion> primero() {
		return ((LinkedList<Nodo<Estado,Accion>>) frontera).removeFirst();//poll();
	}

	/**
	 * @see busqueda.Frontera#aniade(busqueda.Nodo)
	 */
	@Override
	public void aniade(Nodo<Estado,Accion> n) {
		((LinkedList<Nodo<Estado,Accion>>) frontera).addLast(n); // offer(n);

	}


}
