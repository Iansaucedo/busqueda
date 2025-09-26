package busqueda;
import java.util.Comparator;
import java.util.Iterator;
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
 * @author Ines
 * @version 2025.09.*
 */
public class BusquedaPrimeroMejor<Estado,Accion> extends Busqueda<Estado,Accion> {
	private Heuristico<Estado> h;// heuristico que se va a usar
	private Comparator<Nodo<Estado,Accion>> comp;// comparador para comparar nodos (elegir el mejor)

	/**
	 * Constructor SIN criterio de prioridad
	 * Instancia la frontera a una cola de prioridad ordenada por g():
	 * BUSQUEDA DE COSTE UNIFORME
	 * @param p, el problema para el que se va a realizar la busqueda
	 */
	public BusquedaPrimeroMejor(Problema<Estado,Accion> p) {
		super(p);
		frontera = new FronteraPrioridad<Estado,Accion>(Criterio.g);
		h = new Heuristico<Estado>();// h trivial
		comp = new ComparaNodos<Estado,Accion>(Criterio.g);// comparar segun g
	}
	
	/**
	 * Constructor con criterio de prioridad c (g(), h() o f()) y heuristico h
	 * BUSQUEDA PRIMERO EL MEJOR:
	 * - si c es g(), busqueda de coste uniforme
	 * - si c es h(), busqueda primero el mejor voraz
	 * - si c es f(), A*
	 * @param p, el Problema a resolver
	 * @param c, el criterio de ordenacion de la frontera (puede ser g(), f() o h())
	 * @param h, la funcion heuristica a utilizar
	 */
	public BusquedaPrimeroMejor(Problema<Estado,Accion> p, Criterio c, Heuristico<Estado> h){
		super(p);
		frontera = new FronteraPrioridad<Estado,Accion>(c);
		this.h = h;
		comp = new ComparaNodos<Estado,Accion>(c);
	}
	
	/**
	 * Metodo inicia() modificado para calcular valor de H de nodo inicial
	 */
	@Override
	protected void inicia(){
		frontera.clear();
		Nodo<Estado,Accion> inicial = new Nodo<Estado,Accion>( prob.getInicio());
		// al crear el nodo no se calcula h, hay que hacerlo explicitamente
		inicial.setH(h.calculaH(inicial.getEstado()));
		frontera.aniade(inicial);
		explorados.clear();
	}

	
	/**
	 * Metodo tratarRepetidos
	 * Se modifica el generico para que, si encuentra un nodo "repetido" en la frontera,
	 * en lugar de descartar el hijo (el mas nuevo), deje en la frontera el mejor de ambos nodos
	 * (segun el criterio de comparacion)
	 * @param lista de nodos hijos a tratar
	 */
	@Override
	protected void tratarRepetidos(List<Nodo<Estado,Accion>> hijos){
		// TODO Hay que completarlo 
		/* INDICACIONES:
		 * - aqui hay que recorrer la lista de sucesores (hijos) para añadir los "interesantes" a la frontera
		 * - para comprobar si un hijo esta en explorados, basta utilizar el metodo Map.get, recordando que la clave del mapa es el estado
		 * - para comprobar si la frontera no contiene un nodo para el estado del hijo o si lo contiene pero es peor (en cuyo caso, el "peor" se borra),
		 * se recomienda utilizar el metodo auxiliar noRepeOPeorEnFrontera( Nodo hijo )
		 */
	}// fin tratarRepetidos()
	

}
