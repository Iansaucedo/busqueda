package busqueda;

import java.util.Comparator;

/**
 * Clase ComparaNodos
 * Comparador para nodos (en funcion de un criterio dado, que puede ser g(), h() o f()
 * (el valor por defecto para comparar es g()))
 * @author Ines
 * @version 2021.10.*
 *
 */
public class ComparaNodos<Estado,Accion> implements Comparator<Nodo<Estado,Accion>> {
	private Criterio c;
	
	/**
	 * Constructor por defecto
	 * El criterio de comparacion es g
	 */
	public ComparaNodos(){
		this.c = Criterio.g;
	}
	
	/**
	 * Constructor
	 * @param c, el criterio de ordenacion/comparacion
	 */
	public ComparaNodos( Criterio c ){
		this.c = c;
	}

	/**
	 * Metodo compare() (heredado de la interfaz Comparator)
	 * @return numero negativo si n1<n2; positivo si n1>n2 y 0 si n1==n2 segun el criterio dado;
	 * @see java.util.Comparator#compare(Object, Object)
	 */
	@Override
	public int compare(Nodo<Estado,Accion> n1, Nodo<Estado,Accion> n2) {
		if (c == Criterio.g) {
			return (int) Math.signum(n1.getG() - n2.getG());
		} else if (c == Criterio.h) {
			return (int) Math.signum(n1.getH() - n2.getH());
		} else {
			return (int) Math.signum(n1.getF() - n2.getF());
		}
	}
}
