/**
 * Clase Problema para Grid Path Finding (practica de Int Sist Inteligentes)
 * Modela el problema de buscar rutas como un problema de busqueda en espacio de estados,
 * asi que es una clase que hereda de Problema<Estado,Accion>
*/
package busqueda.GPF;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import busqueda.Problema;

/**
 * @author Ines
 * @version 2025.09.*
 *
 */
public class ProblemaGPF extends Problema<EstadoGPF, AccionGPF> {
	// atributos
	private int[][] grid; // cuadricula
	private int gridNFilas; // dimensiones cuadricula
	private int gridNCols;
	private EstadoGPF inicio; // estado inicial
	private EstadoGPF meta; // estado final

	// CONSTRUCTORES
	/**
	 * Constructor a partir de datos de un problema
	 * 
	 * @param g,     la cuadricula
	 * @param iniX,  fila de la posicion de inicio
	 * @param iniY,  columna de la posicion de inicio
	 * @param metaX, fila de la posicion meta
	 * @param metaY, columna de la posicion meta
	 */
	public ProblemaGPF(int[][] g, int iniX, int iniY, int metaX, int metaY) {
		setGrid(g);
		setGridNFilas(g.length);
		int nc = (g.length > 0 ? g[0].length : 0);
		setGridNCols(nc);
		setInicio(iniX, iniY);
		setMeta(metaX, metaY);
	}

	/**
	 * Constructor a partir de fichero de texto
	 * 
	 * @param el nombre el fichero, un String
	 */
	public ProblemaGPF(String fichProblema) {
		leeDeFichero(fichProblema);
	}

	/**
	 * Constructor "aleatorio" a partir de parametros
	 * 
	 * @param nFilas,     numero de filas de la cuadricula
	 * @param nCols,      numero de columnas
	 * @param pObstaculo, la probabilidad con que una casilla sera un obstaculo
	 * @param costeMin,   coste minimo de transitar por una casilla
	 * @param costeMax,   coste maximo de transitar por una casilla
	 */
	public ProblemaGPF(int nFilas, int nCols, double pObstaculo, int costeMin, int costeMax) {
		int[][] g = new int[nFilas][nCols];
		Random r = new Random(); // generador de numeros aleatorios
		// posicion inicial
		int iniX = r.nextInt(nFilas);
		int iniY = r.nextInt(nCols);
		// meta
		int metaX = r.nextInt(nFilas);
		int metaY = r.nextInt(nCols);
		// grid
		for (int i = 0; i < nFilas; i++) {
			for (int j = 0; j < nCols; j++)
				// bloqueo casilla (no ini y no meta) con probabilidad pObstaculo
				if (i != iniX && i != metaX && j != iniY && j != metaY && (r.nextDouble() <= pObstaculo))
					g[i][j] = 0;
				else // si no, coste entre costeMin y costeMax
					g[i][j] = costeMin + r.nextInt(costeMax);
		}
		// copiar en atributos
		setGrid(g);
		setGridNFilas(nFilas);
		setGridNCols(nCols);
		setInicio(iniX, iniY);
		setMeta(metaX, metaY);
	}

	// MODIFICADORES Y OBSERVADORES
	/**
	 * @return the grid
	 */
	public int[][] getGrid() {
		return grid;
	}

	/**
	 * @param grid the grid to set
	 */
	public void setGrid(int[][] grid) {
		this.grid = grid;
	}

	/**
	 * @return the gridNFilas
	 */
	public int getGridNFilas() {
		return gridNFilas;
	}

	/**
	 * @param gridNFilas the gridNFilas to set
	 */
	public void setGridNFilas(int gridNFilas) {
		this.gridNFilas = gridNFilas;
	}

	/**
	 * @return the gridNCols
	 */
	public int getGridNCols() {
		return gridNCols;
	}

	/**
	 * @param gridNCols the gridNCols to set
	 */
	public void setGridNCols(int gridNCols) {
		this.gridNCols = gridNCols;
	}

	/**
	 * @param x, fila de posicion de inicio
	 * @param y, columna de posicion de inicio
	 */
	private void setInicio(int x, int y) {
		if (0 <= x && x < getGridNFilas() && 0 <= y && y < getGridNCols())
			inicio = new EstadoGPF(x, y);
	}

	/**
	 * Observador para conocer el estado meta
	 * 
	 * @return el estado meta
	 */
	public EstadoGPF getMeta() {
		return meta;
	}

	/**
	 * @param x, fila de posicion de inicio
	 * @param y, columna de posicion de inicio
	 * @return
	 */
	private void setMeta(int x, int y) {
		if (0 <= x && x < getGridNFilas() && 0 <= y && y < getGridNCols())
			meta = new EstadoGPF(x, y);
	}

	// METODOS HERADOS DE LA CLASE ABSTRACTA Problema QUE HAY QUE IMPLEMENTAR

	/*
	 * (non-Javadoc)
	 * 
	 * @see busqueda.Problema#getInicio()
	 */
	@Override
	public EstadoGPF getInicio() {
		return inicio;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see busqueda.Problema#acciones(busqueda.Estado)
	 */
	@Override
	public List<AccionGPF> acciones(EstadoGPF eactual) {
		// hay que ver cual es la lista de acciones aplicables en el estado actual
		// SUGERENCIA: puede resultar interesante utilizar el metodo aplicable()
		List<AccionGPF> lista = new LinkedList<AccionGPF>();

		// Probar todas las direcciones posibles
		AccionGPF[] todasLasAcciones = {
				new AccionGPF(AccionGPF.Direccion.ARRIBA),
				new AccionGPF(AccionGPF.Direccion.ABAJO),
				new AccionGPF(AccionGPF.Direccion.IZQUIERDA),
				new AccionGPF(AccionGPF.Direccion.DERECHA)
		};

		// Solo agregar las acciones que son aplicables
		for (AccionGPF accion : todasLasAcciones) {
			if (aplicable(eactual, accion)) {
				lista.add(accion);
			}
		}

		return lista;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see busqueda.Problema#aplicable(Estado, Accion)
	 */
	@Override
	public boolean aplicable(EstadoGPF e, AccionGPF a) {
		// Calcular la nueva posición después de aplicar la acción
		int nuevaX = e.getX() + a.getDeltaX();
		int nuevaY = e.getY() + a.getDeltaY();

		// Verificar que la nueva posición esté dentro de los límites del grid
		if (nuevaX < 0 || nuevaX >= getGridNFilas() || nuevaY < 0 || nuevaY >= getGridNCols()) {
			return false;
		}

		// Verificar que la casilla destino no sea un obstáculo (grid[x][y] > 0
		// significa libre)
		return grid[nuevaX][nuevaY] > 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see busqueda.Problema#resul(busqueda.Estado, busqueda.Accion)
	 */
	@Override
	public EstadoGPF resul(EstadoGPF e, AccionGPF a) {
		// Hay que calcular el resultado de aplicar una accion a un estado
		if (!aplicable(e, a)) {
			return null; // si la accion no es aplicable (no deberia llegar aqui nunca)
		}

		// Calcular la nueva posición
		int nuevaX = e.getX() + a.getDeltaX();
		int nuevaY = e.getY() + a.getDeltaY();

		// Crear y retornar el nuevo estado
		return new EstadoGPF(nuevaX, nuevaY);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see busqueda.Problema#esMeta(Estado)
	 */
	@Override
	public boolean esMeta(EstadoGPF e) {
		return e.equals(meta);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see busqueda.Problema#coste(Estado, Accion, Estado)
	 */
	@Override
	public double coste(EstadoGPF e1, AccionGPF a, EstadoGPF e2) {
		// En GPF, el coste solo depende de la casilla a la que se llega (e2)
		// El valor en grid[x][y] representa el coste de estar en esa casilla
		return grid[e2.getX()][e2.getY()];
	}

	// METODOS PRIVADOS (LOS QUE HACEN "EL TRABAJO SUCIO")

	/**
	 * Lee el problema de un fichero con formato determinado como sigue
	 * (cuando pone $ se refiere a una variable):
	 * NUMERO DE FILAS
	 * $NF
	 * NUMERO DE COLUMNAS
	 * $NC
	 * INICIO
	 * $Xini $Yini
	 * META
	 * $Xmeta $Ymeta
	 * CUADRICULA
	 * $Array
	 * 
	 * @param fichProblema, un String con el nombre del fichero
	 */
	private void leeDeFichero(String fichProblema) {
		Scanner s = null;
		try {
			s = new Scanner(new BufferedReader(new FileReader(fichProblema)));
			// leemos primeras lineas (num filas y columnas, inicio y meta)
			int num, num2 = 0;
			s.nextLine();// comentario "NUMERO DE FILAS"
			num = s.nextInt(); // numero de filas
			s.nextLine();// saltamos linea
			setGridNFilas(num);
			s.nextLine();// comentario "NUMERO DE COLUMNAS"
			num = s.nextInt(); // numero de columnas
			s.nextLine();// saltamos linea
			setGridNCols(num);
			s.nextLine();// comentario "INICIO"
			num = s.nextInt(); // fila de inicio
			num2 = s.nextInt(); // columna de inicio
			s.nextLine();// saltamos linea
			setInicio(num, num2);
			s.nextLine();// comentario "META"
			num = s.nextInt(); // fila de META
			num2 = s.nextInt(); // columna de META
			setMeta(num, num2);
			s.nextLine();// saltamos linea
			// leemos cuadricula
			s.nextLine();// comentario "CUADRICULA"
			grid = new int[getGridNFilas()][getGridNCols()];
			for (int i = 0; i < getGridNFilas(); i++) {
				for (int j = 0; j < getGridNCols(); j++)
					grid[i][j] = s.nextInt();
				if (i < getGridNFilas() - 1)
					s.nextLine();
			}
		} catch (FileNotFoundException e) {
			System.err.println("FileNotFoundException: " + e.getMessage());
			e.printStackTrace();
		} finally {
			if (s != null)
				s.close();
		}
	}

	/**
	 * Metodo "chapuza" para mostrar el problema por pantalla
	 */
	public void muestraProblema() {
		System.out.println("NUMERO DE FILAS");
		System.out.println(this.getGridNFilas());
		System.out.println("NUMERO DE COLUMNAS");
		System.out.println(this.getGridNCols());
		System.out.println("INICIO");
		System.out.println(this.getInicio().toString());
		System.out.println("FIN");
		System.out.println(this.getMeta().toString());
		System.out.println("CUADRICULA");

		// Print column headers
		System.out.print("   |");
		for (int j = 0; j < getGridNCols(); j++) {
			System.out.printf("%2d ", j);
		}
		System.out.println();

		// Print separator line
		System.out.print("---+");
		for (int j = 0; j < getGridNCols(); j++) {
			System.out.print("---");
		}
		System.out.println();

		// Print grid with row numbers
		for (int i = 0; i < getGridNFilas(); i++) {
			System.out.printf("%2d |", i); // Print row number with separator
			for (int j = 0; j < getGridNCols(); j++)
				System.out.printf("%2d ", grid[i][j]);
			System.out.println();
		}
	}

	/**
	 * Metodo para volcar un problema en un fichero
	 * 
	 * @param nomFichProb, el nombre del fichero
	 * @throws IOException
	 */
	public void escribeEnFichero(String nomFichProb) throws IOException {
		PrintWriter out = new PrintWriter(new FileWriter(nomFichProb));
		escribeProblema(out);
		out.close();
	}

	/**
	 * Metodo privado para volcar un problema en un fichero (el que hace el trabajo
	 * sucio)
	 * 
	 * @param out el PrintWRiter que se va a encargar de escribir el problema
	 */
	private void escribeProblema(PrintWriter out) {
		out.println("NUMERO DE FILAS");
		out.println(this.getGridNFilas());
		out.println("NUMERO DE COLUMNAS");
		out.println(this.getGridNCols());
		out.println("INICIO");
		out.println(this.getInicio().getX() + " " + this.getInicio().getY());
		out.println("FIN");
		out.println(this.getMeta().getX() + " " + this.getMeta().getY());
		out.println("CUADRICULA");
		for (int i = 0; i < getGridNFilas(); i++) {
			for (int j = 0; j < getGridNCols(); j++)
				out.print(grid[i][j] + " ");
			out.println();
		}
	}

}
