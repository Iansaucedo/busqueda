/**
 * Accion para el problema GPF (grid pathfinding)
 * Puede ser: ir a derecha|arriba|izquierda|abajo
 */
package busqueda.GPF;

/**
 * @author Ines
 * @version 2022.09.*
 */
public class AccionGPF {

	// Enum para representar las posibles acciones
	public enum Direccion {
		ARRIBA, ABAJO, IZQUIERDA, DERECHA
	}

	private final Direccion direccion;

	/**
	 * Constructor
	 * 
	 * @param direccion La dirección de la acción
	 */
	public AccionGPF(Direccion direccion) {
		this.direccion = direccion;
	}

	/**
	 * Obtiene la dirección de la acción
	 * 
	 * @return La dirección
	 */
	public Direccion getDireccion() {
		return direccion;
	}

	/**
	 * Obtiene el cambio en X para esta acción
	 * 
	 * @return El cambio en la coordenada X
	 */
	public int getDeltaX() {
		return switch (direccion) {
			case ARRIBA -> -1;
			case ABAJO -> 1;
			case IZQUIERDA, DERECHA -> 0;
		};
	}

	/**
	 * Obtiene el cambio en Y para esta acción
	 * 
	 * @return El cambio en la coordenada Y
	 */
	public int getDeltaY() {
		return switch (direccion) {
			case ARRIBA, ABAJO -> 0;
			case IZQUIERDA -> -1;
			case DERECHA -> 1;
		};
	}

	@Override
	public String toString() {
		return switch (direccion) {
			case ARRIBA -> "Arriba";
			case ABAJO -> "Abajo";
			case IZQUIERDA -> "Izquierda";
			case DERECHA -> "Derecha";
		};
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		AccionGPF that = (AccionGPF) obj;
		return direccion == that.direccion;
	}

	@Override
	public int hashCode() {
		return direccion != null ? direccion.hashCode() : 0;
	}
}
