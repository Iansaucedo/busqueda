/**
 * Heuristico Manhattan para el problema de Grid Path Finding
 */
package busqueda.GPF;

import busqueda.Heuristico;

/**
 * @author Ines
 * @version 2021.10.*
 */
public class HeuristicoGPFManhattan extends Heuristico<EstadoGPF> {
	private EstadoGPF meta; // para calcular la distancia Manhattan hay que conocer la meta

	/**
	 * Constructor
	 * @param prob, un objeto ProblemaGPF 
	 */
	public HeuristicoGPFManhattan(ProblemaGPF prob) {
		setMeta(prob.getMeta());
	}

	/**
	 * @param m el EstadoGPF para guardar en el atributo
	 */
	public void setMeta(EstadoGPF m) {
		meta = m;
	}
	
	/**
	 * Heuristico Manhattan
	 * @param un Estado e (del problema de grid pathfinding)
	 * @return el valor h(e), que es la distancia Manhattan al objetivo
	 */	
	@Override
	public double calculaH(EstadoGPF e){
		// TODO hay que completar este metodo para que devuelva el valor de h(e)
		return 0.0;
		}

}
