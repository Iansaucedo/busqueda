package busqueda;

import java.util.LinkedList;

/**
 * Frontera LIFO (pila)
 * @author Ines
 * @version 2018.10.*
 */
public class FronteraLIFO<Estado,Accion> extends Frontera<Estado,Accion> {
	
	/**
	 * Constructor
	 * Construye un contenedor (lista LIFO o pila) vacio
	 */
	public FronteraLIFO() {
		frontera = new LinkedList<Nodo<Estado,Accion>>();
	}

	/**
	 * @see busqueda.Frontera#primero()
	 */
	@Override
	public Nodo<Estado,Accion> primero() {
		return ((LinkedList<Nodo<Estado,Accion>>) frontera).removeFirst();	// pop()
	}

	/**
	 * @see busqueda.Frontera#aniade(busqueda.Nodo)
	 */
	@Override
	public void aniade(Nodo<Estado,Accion> n) {
		((LinkedList<Nodo<Estado,Accion>>) frontera).addFirst(n); // push(n)
		
	}

}
