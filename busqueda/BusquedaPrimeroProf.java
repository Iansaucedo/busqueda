package busqueda;

/**
 * Clase que realiza la busqueda primero en profundidad
 * @author Ines
 * @version 2018.10
 */

public class BusquedaPrimeroProf<Estado,Accion> extends Busqueda<Estado,Accion> {
	/**
	 * Constructor: instancia la frontera a contenedor LIFO
	 */
	public BusquedaPrimeroProf( Problema<Estado,Accion> p) {
		super(p);
		frontera = new FronteraLIFO<Estado,Accion>();
		}
// el resto igual que en la busqueda general
	}