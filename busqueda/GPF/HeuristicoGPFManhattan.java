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
	 * 
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
	 * 
	 * @param un Estado e (del problema de grid pathfinding)
	 * @return el valor h(e), que es la distancia Manhattan al objetivo
	 */
	@Override
	public double calculaH(EstadoGPF e) {
		// Calcular la distancia Manhattan entre el estado actual y el estado meta
		// Distancia Manhattan = |x1 - x2| + |y1 - y2|
		int deltaX = Math.abs(e.getX() - meta.getX());
		int deltaY = Math.abs(e.getY() - meta.getY());
		return deltaX + deltaY;
	}

}
