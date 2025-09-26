/**
 * Estado en el problema de Grid Path Finding (casilla)
 * !OJO! las casillas empiezan en (0,0), en el extremo superior izquierdo (el grid se ve como un array)
 */
package busqueda.GPF;



/**
 * @author Ines
 * @version 2019.10.*
 */
public class EstadoGPF {
	
	// atributos: posiciones de la casilla
	private int X; // indice de fila
	private int Y; // indice de columna

	/**
	 * Constructor
	 * @param posX, la X de la casilla (fila)
	 * @param posY, la Y de la casilla (columna)
	 */
	public EstadoGPF( int posX, int posY ) {
		X=posX;
		Y=posY;
	}
	
	//observadores
	/** Observador de la coordenada X
	 * @return X
	 */
	public int getX(){
		return X;
	}
	
	/** Observador de la coordenada Y
	 * @return Y
	 */
	public int getY(){
		return Y;
	}

	/* (non-Javadoc)
	 * @see busqueda.Estado#equals()
	 */
	@Override
	public boolean equals( Object otro ) {
		if( otro instanceof EstadoGPF)
			return (X==((EstadoGPF)otro).getX() && Y==((EstadoGPF)otro).getY());
		else
			return false;
	}
	
	/* (non-Javadoc)
	 * @see busqueda.Estado#toString()
	 */
	@Override
	public String toString(){
		return "["+ getX()+", "+getY()+"]";		
	}
	
	/* (non-Javadoc)
	 * @see busqueda.Estado#hashCode()
	 */
	@Override
	public int hashCode(){
		return toString().hashCode();
	}

}
