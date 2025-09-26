package busqueda;

import java.util.LinkedList;
import java.util.List;

/**
  * Clase abstracta para un Problema de busqueda en espacio de estados
 * @author ines
 * @version 2018.10.*
 */

public abstract class Problema<Estado,Accion> {
	
	// ESPACIO DE ESTADOS
	// estado inicial
	/**
	 * getInicio()
	 * @return el estado inicio de la busqueda
	 */
	public abstract Estado getInicio();
		
	// modelo de transicion: acciones y resultado
	/**
	 * acciones
	 * @param eactual, un Estado
	 * @return ACCIONES(e), las acciones aplicables en el estado actual
	 */
	public abstract List<Accion> acciones(Estado eactual);
	
	/**
	 * resul (modelo de transicion)
	 * @param e, un Estado
	 * @param a, una Accion
	 * @return RESUL(e,a), el Estado resultante de aplicar la accion a en el estado e
	 */
	public abstract Estado resul (Estado e, Accion a );
	
	/**
	 * aplicable: decide si una accion es aplicable en un estado
	 * @param e, un Estado
	 * @param a, una Accion
	 * @return true si a pertenece a ACCIONES(e)
	 */
	public abstract boolean aplicable(Estado e, Accion a);
	
	
	// TEST DE META
	/**
	 * esMeta, comprueba si un estado es meta
	 * @param e, un Estado
	 * @return cierto, si el estado cumple el test de meta, falso en otro caso
	 */
	public abstract boolean esMeta( Estado e );
		
	// COSTES
	/**
	 * coste
	 * @param e1, un estado
	 * @param a, una accion
	 * @param e2, otro estado
	 * @return c, el coste de pasar de e1 a e2 aplicando la accion a
	 */
	public abstract double coste(Estado e1, Accion a, Estado e2);
	
	
	// METODOS PARA EXPANDIR UN NODO
	/**
	 * sucesores
	 * @param eactual, el Estado actual
	 * @return los estados alcanzables desde eactual (sus sucesores)
	 */
	public List<Estado> sucesores(Estado eactual){
		List<Estado> sucesores = new LinkedList<Estado>();
		List<Accion> accAplicables= acciones(eactual);
		for( Accion a: accAplicables ) {
			sucesores.add(resul(eactual,a));
		}
		return sucesores;
	}

	

}
