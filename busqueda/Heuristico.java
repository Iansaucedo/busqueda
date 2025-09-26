package busqueda;

/**
 * Clase Heuristico
 * Operador para calcular el heuristico de un estado (por defecto h=0)
 * @author Ines
 * @version 2018.10.*
 */

public class Heuristico<Estado> {

	/**
	 * Heuristico trivial
	 * @param un Estado e
	 * @return el valor h(e), que siempre es 0
	 */	
	public double calculaH(Estado e){
		return 0;
	}

}
