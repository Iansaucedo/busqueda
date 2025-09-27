package busqueda;

import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Clase que realiza una busqueda primero el mejor:
 * busqueda general en grafos con frontera cola de prioridad
 * Segun el criterio de ordenacion puede ser:
 * - g(): busqueda coste uniforme
 * - h(): busqueda primero el mejor voraz
 * - f(): busqueda A*
 * Para cualquiera de los 2 ultimos, por defecto h=0, aunque puede ser otro
 * Generaliza (y hace innecesaria) la clase BusquedaCosteUniforme
 * 
 * @author Ines
 * @version 2025.09.*
 */
public class BusquedaPrimeroMejor<Estado, Accion> extends Busqueda<Estado, Accion> {
	private Heuristico<Estado> h;// heuristico que se va a usar
	private Comparator<Nodo<Estado, Accion>> comp;// comparador para comparar nodos (elegir el mejor)

	/**
	 * Constructor SIN criterio de prioridad
	 * Instancia la frontera a una cola de prioridad ordenada por g():
	 * BUSQUEDA DE COSTE UNIFORME
	 * 
	 * @param p, el problema para el que se va a realizar la busqueda
	 */
	public BusquedaPrimeroMejor(Problema<Estado, Accion> p) {
		super(p);
		frontera = new FronteraPrioridad<Estado, Accion>(Criterio.g);
		h = new Heuristico<Estado>();// h trivial
		comp = new ComparaNodos<Estado, Accion>(Criterio.g);// comparar segun g
	}

	/**
	 * Constructor con criterio de prioridad c (g(), h() o f()) y heuristico h
	 * BUSQUEDA PRIMERO EL MEJOR:
	 * - si c es g(), busqueda de coste uniforme
	 * - si c es h(), busqueda primero el mejor voraz
	 * - si c es f(), A*
	 * 
	 * @param p, el Problema a resolver
	 * @param c, el criterio de ordenacion de la frontera (puede ser g(), f() o h())
	 * @param h, la funcion heuristica a utilizar
	 */
	public BusquedaPrimeroMejor(Problema<Estado, Accion> p, Criterio c, Heuristico<Estado> h) {
		super(p);
		frontera = new FronteraPrioridad<Estado, Accion>(c);
		this.h = h;
		comp = new ComparaNodos<Estado, Accion>(c);
	}

	/**
	 * Metodo inicia() modificado para calcular valor de H de nodo inicial
	 */
	@Override
	protected void inicia() {
		frontera.clear();
		Nodo<Estado, Accion> inicial = new Nodo<Estado, Accion>(prob.getInicio());
		// al crear el nodo no se calcula h, hay que hacerlo explicitamente
		inicial.setH(h.calculaH(inicial.getEstado()));
		frontera.aniade(inicial);
		explorados.clear();
	}

	/**
	 * Metodo expandir modificado para calcular valor de H de los nodos hijos
	 */
	@Override
	protected List<Nodo<Estado, Accion>> expandir(Nodo<Estado, Accion> actual) {
		List<Accion> accAplicables = prob.acciones(actual.getEstado()); // ACCIONES(actual.estado)
		// creamos un Nodo para cada accion aplicable
		List<Nodo<Estado, Accion>> nsucesores = new LinkedList<Nodo<Estado, Accion>>();
		for (Accion a : accAplicables) {
			Estado suc = prob.resul(actual.getEstado(), a); // estado al que se llega
			Nodo<Estado, Accion> hijo = new Nodo<Estado, Accion>(suc, actual, a, prob.coste(actual.getEstado(), a, suc));
			// Calcular el valor heuristico para el hijo
			hijo.setH(h.calculaH(hijo.getEstado()));
			nsucesores.add(hijo); // nodo correspondiente
		}
		return nsucesores;
	}

	/**
	 * Metodo tratarRepetidos
	 * Se modifica el generico para que, si encuentra un nodo "repetido" en la
	 * frontera,
	 * en lugar de descartar el hijo (el mas nuevo), deje en la frontera el mejor de
	 * ambos nodos
	 * (segun el criterio de comparacion)
	 * 
	 * @param lista de nodos hijos a tratar
	 */
	@Override
	protected void tratarRepetidos(List<Nodo<Estado, Accion>> hijos) {
		// Recorrer la lista de sucesores (hijos) para añadir los "interesantes" a la
		// frontera
		for (Nodo<Estado, Accion> hijo : hijos) {
			// Comprobar si un hijo está en explorados
			if (explorados.get(hijo.getEstado()) == null) {
				// El hijo no está en explorados, verificar si está en frontera
				if (noRepeOPeorEnFrontera(hijo)) {
					// No está repetido en frontera O está pero es peor, añadir el hijo
					frontera.aniade(hijo);
				}
			}
			// Si está en explorados, se descarta (no se añade a la frontera)
		}
	}// fin tratarRepetidos()

	/**
	 * Metodo auxiliar para comprobar si un nodo no esta repetido en la frontera
	 * o si esta repetido pero es peor que el nodo dado
	 * 
	 * @param hijo el nodo a comprobar
	 * @return true si el nodo no esta en frontera o si esta pero es peor (en cuyo
	 *         caso lo elimina)
	 */
	private boolean noRepeOPeorEnFrontera(Nodo<Estado, Accion> hijo) {
		// Buscar si hay un nodo en la frontera con el mismo estado
		Nodo<Estado, Accion> nodoEnFrontera = frontera.contieneNodo(hijo);

		if (nodoEnFrontera == null) {
			// No hay nodo con el mismo estado en la frontera
			return true;
		} else {
			// Hay un nodo con el mismo estado, comparar cuál es mejor
			if (comp.compare(hijo, nodoEnFrontera) < 0) {
				// El hijo es mejor que el nodo en frontera, eliminar el peor
				frontera.frontera.remove(nodoEnFrontera);
				return true;
			} else {
				// El nodo en frontera es mejor o igual, no añadir el hijo
				return false;
			}
		}
	}

}
