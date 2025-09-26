package busqueda;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Clase "plantilla" (y abstracta) para realizar busqueda en grafos
 * Corresponde con el algoritmo general de busqueda en grafos
 * @author Ines
 * @version 2021.11.*
 */

public abstract class Busqueda<Estado,Accion> {
	protected final Problema<Estado,Accion> prob;		// problema de busqueda en espacio de estados a resolver
	protected Frontera<Estado,Accion> frontera;			// contenedor de nodos frontera
	protected Map<Estado,Nodo<Estado,Accion>> explorados;// contenedor de nodos explorados (clave=Estado)	
	private Nodo<Estado,Accion> meta; 					// nodo meta (si se encuentra) o null (si no)
	
	/**
	 * constructor
	 */
	public Busqueda( Problema<Estado,Accion> p){
		prob=p;
		explorados=new HashMap<Estado,Nodo<Estado,Accion>>();
		// la frontera la construye alguna subclase (busqueda concreta segun criterio de ordenacion de frontera)
	}
	
	
	/**
	 * busqueda, metodo principal para realizar busqueda en espacios de estados
	 * corresponde al algoritmo de busqueda-general visto en clase
	 * @return la solucion (una lista de estados, camino desde el inicial al meta), null si no hay solucion
	 */
	public List<Nodo<Estado,Accion>> busqueda(){
		inicia();
		Nodo<Estado,Accion> actual=null;
		boolean encontrada = false;
		while(!frontera.esVacia() && !encontrada){
			// tomamos el primer nodo en la frontera y lo aniadimos a explorados
			actual=frontera.primero();
			explorados.put(actual.getEstado(),actual);
			// comprobamos si es meta
			if( prob.esMeta(actual.getEstado()))
				encontrada=true;
			else{ // no es meta: lo expandimos
				List<Nodo<Estado,Accion>>hijos = expandir(actual);
				tratarRepetidos(hijos);
			}			
		}// fin while
		if( encontrada ){
			meta = actual;
			return solucion(actual);
		}
		else
			return null;
	}// fin busqueda()
	
	// metodos "observadores" de la busqueda
	/**
	 * Metodo para saber el coste de la solucion encontrada
	 * @return el coste de la solucion (si se ha encontrado)
	 */
	public double costeSolucion(){
		return (meta != null ? meta.getG() : Double.POSITIVE_INFINITY);
	}
	
	/**
	 * metodo para saber cuantos nodos se han visitado (coste de la busqueda)
	 * @return el tamanio de explorados
	 */
	public int nodosExplorados(){
		return explorados.size();
	}
	
	/**
	 * metodo para saber cuantos nodos hay en la frontera (coste de la busqueda)
	 * @return el tamanio de frontera
	 */
	public int nodosEnFrontera(){
		return frontera.tamanio();
	}
	
	// metodos auxiliares de la busqueda
	/**
	 * metodo que inicia la frontera con el estado inicial y explorados a vacio
	 */
	protected void inicia(){
		frontera.clear();
		frontera.aniade(new Nodo<Estado,Accion>( prob.getInicio()));
		explorados.clear();
	}
	
	/**
	 * expandir
	 * @param actual, un Nodo
	 * @return La lista de hijos (Nodos) del nodo actual
	 */

	protected List<Nodo<Estado,Accion>> expandir(Nodo<Estado,Accion> actual){
		List<Accion> accAplicables= prob.acciones(actual.getEstado()); // ACCIONES(actual.estado)
		// creamos un Nodo para cada accion aplicable
		List<Nodo<Estado,Accion>> nsucesores = new LinkedList<Nodo<Estado,Accion>>();
		for( Accion a: accAplicables ){
			Estado suc = prob.resul(actual.getEstado(),a); // estado al que se llega
			nsucesores.add( new Nodo<Estado,Accion>(suc, actual, a, prob.coste(actual.getEstado(),a,suc))); // nodo correspondiente
		}
		return nsucesores;
	}


	/**
	 * tratar-repetidos basico: aniade hijos a la frontera
	 * si no hay un nodo para el mismo estado en frontera o en explorados
	 * @param hijos, la lista de nodos hijos
	 */
	protected void tratarRepetidos(List<Nodo<Estado,Accion>> hijos) {
		// recorremos lista hijos
		Iterator<Nodo<Estado,Accion>> it = hijos.iterator();
		while( it.hasNext() ){
			Nodo<Estado,Accion> hijo=it.next();
			// si el hijo no esta ya en frontera ni en explorados, lo aniadimos a la frontera
			if( !frontera.contiene(hijo) && explorados.get(hijo.getEstado()) == null ){
				frontera.aniade(hijo);
			}	
		}
	}// fin tratarRepetidos()

	/**
	 * Metodo para recuperar la solucion
	 * @param meta, un nodo meta
	 * @return el camino a la meta como lista de nodos
	 */
	private List<Nodo<Estado,Accion>> solucion( Nodo<Estado,Accion> meta ){
		return meta.caminoDesdeInicio();		
	}
}
