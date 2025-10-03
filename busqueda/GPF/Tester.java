package busqueda.GPF;

/**
 * Clase de prueba minimal
 * @author Ines
 * @version 2021.10.*
 */
import busqueda.Busqueda;
import busqueda.BusquedaPrimeroAnchura;
import busqueda.BusquedaPrimeroMejor;
import busqueda.BusquedaPrimeroProf;
import busqueda.Criterio;
import busqueda.Heuristico;
import busqueda.Nodo;
import busqueda.Problema;
import java.util.List;

public class Tester {

	public static void main(String[] argv) {

		// OPCION 1: Para probar todas las busquedas con un problema concreto
		// pruebaUnProblema("p8x8.txt");
		// pruebaUnProblema("p6x6.txt");
		// pruebaUnProblema("prob10x15-8.txt");

		// OPCION 2: Probar todas las busquedas con un conjunto de problemas y recopilar
		// resultados relevantes
		String[] problemas = {
				"p5x5.txt",
				"p6x6.txt",
				"p8x8.txt",
				"p10x10.txt",
				"prob10x10-0.txt",
				"prob10x10-1.txt",
				"prob10x10-2.txt",
				"prob10x10-3.txt",
				"prob10x10-4.txt",
				"prob10x10-5.txt",
				"prob10x10-6.txt",
				"prob10x10-7.txt",
				"prob10x10-8.txt",
				"prob10x10-9.txt",
				"prob10x15-0.txt",
				"prob10x15-1.txt",
				"prob10x15-2.txt",
				"prob10x15-3.txt",
				"prob10x15-4.txt",
				"prob10x15-5.txt",
				"prob10x15-6.txt",
				"prob10x15-7.txt",
				"prob10x15-8.txt",
				"prob10x15-9.txt",
				"prob15x15-0.txt",
				"prob15x15-1.txt",
				"prob15x15-2.txt",
				"prob15x15-3.txt",
				"prob15x15-4.txt",
				"prob15x15-5.txt",
				"prob15x15-6.txt",
				"prob15x15-7.txt",
				"prob15x15-8.txt",
				"prob15x15-9.txt"
		};

		System.out.println("=".repeat(80));
		System.out.println("PROBANDO TODOS LOS PROBLEMAS DISPONIBLES");
		System.out.println("=".repeat(80));

		for (String problema : problemas) {
			System.out.println("\n" + "=".repeat(80));
			System.out.println("PROBLEMA: " + problema);
			System.out.println("=".repeat(80));
			try {
				pruebaUnProblema(problema);
			} catch (Exception e) {
				System.out.println("Error procesando " + problema + ": " + e.getMessage());
			}
		}

		System.out.println("\n" + "=".repeat(80));
		System.out.println("FIN DE TODAS LAS PRUEBAS");
		System.out.println("=".repeat(80));
	}

	/**
	 * Metodo para probar todas las busquedas (informadas y ciegas) sobre un
	 * problema concreto
	 * 
	 * @param nomFichProb, el nombre del fichero con el enunciado del problema
	 */
	private static void pruebaUnProblema(String nomFichProb) {
		// Problema a resolver
		ProblemaGPF p = new ProblemaGPF(nomFichProb);
		p.muestraProblema();

		// Creamos heuristicos
		Heuristico<EstadoGPF> h0 = new Heuristico<EstadoGPF>();
		Heuristico<EstadoGPF> hM = new HeuristicoGPFManhattan(p);
		// aqui podria aniadirse el heuristico malo a programar

		// Lanzamos diversas busquedas
		// se puede comentar o descomentar segun la búsqueda que quiera lanzarse

		// BUSQUEDA PRIMERO EN ANCHURA
		System.out.println("---------------------------");
		System.out.println("PRIMERO EN ANCHURA");
		Busqueda<EstadoGPF, AccionGPF> bAnch = new BusquedaPrimeroAnchura<EstadoGPF, AccionGPF>(p);
		List<Nodo<EstadoGPF, AccionGPF>> sol = bAnch.busqueda();
		muestraSol(sol, bAnch, p);

		// BUSQUEDA PRIMERO EN PROFUNDIDAD
		System.out.println("---------------------------");
		System.out.println("PRIMERO EN PROFUNDIDAD");
		Busqueda<EstadoGPF, AccionGPF> bProf = new BusquedaPrimeroProf<EstadoGPF, AccionGPF>(p);
		List<Nodo<EstadoGPF, AccionGPF>> solProf = bProf.busqueda();
		muestraSol(solProf, bProf, p);

		// BUSQUEDA COSTE UNIFORME
		System.out.println("---------------------------");
		System.out.println("COSTE UNIFORME. Deberia encontrar solucion optima");
		Busqueda<EstadoGPF, AccionGPF> bCU = new BusquedaPrimeroMejor<EstadoGPF, AccionGPF>(p);
		List<Nodo<EstadoGPF, AccionGPF>> solCU = bCU.busqueda();
		muestraSol(solCU, bCU, p);

		// BUSQUEDA PRIMERO EL MEJOR VORAZ CON HEURISTICO TRIVIAL
		System.out.println("---------------------------");
		System.out.println("1o MEJOR VORAZ CON H0");
		Busqueda<EstadoGPF, AccionGPF> bGBF0 = new BusquedaPrimeroMejor<EstadoGPF, AccionGPF>(p, Criterio.h, h0);
		List<Nodo<EstadoGPF, AccionGPF>> solGBF0 = bGBF0.busqueda();
		muestraSol(solGBF0, bGBF0, p);
		//
		// BUSQUEDA PRIMERO EL MEJOR VORAZ CON HEURISTICO MANHATTAN
		System.out.println("---------------------------");
		System.out.println("1o MEJOR VORAZ CON MANHATTAN. ");
		Busqueda<EstadoGPF, AccionGPF> bGBFM = new BusquedaPrimeroMejor<EstadoGPF, AccionGPF>(p, Criterio.h, hM);
		List<Nodo<EstadoGPF, AccionGPF>> solGBFM = bGBFM.busqueda();
		muestraSol(solGBFM, bGBFM, p);

		// BUSQUEDA A* CON HEURISTICO TRIVIAL (AUNQUE EN REALIDAD YA LA HEMOS HECHO)
		System.out.println("---------------------------");
		System.out.println("A* CON H0. Deberia encontrar solucion optima");
		Busqueda<EstadoGPF, AccionGPF> bAStar00 = new BusquedaPrimeroMejor<EstadoGPF, AccionGPF>(p, Criterio.f, h0);
		List<Nodo<EstadoGPF, AccionGPF>> solAStar00 = bAStar00.busqueda();
		muestraSol(solAStar00, bAStar00, p);

		// BUSQUEDA A* CON HEURISTICO MANHATTAN
		System.out.println("---------------------------");
		System.out.println("A* CON MANHATTAN. Deberia encontrar solucion optima");
		Busqueda<EstadoGPF, AccionGPF> bAStarM = new BusquedaPrimeroMejor<EstadoGPF, AccionGPF>(p, Criterio.f, hM);
		List<Nodo<EstadoGPF, AccionGPF>> solAStarM = bAStarM.busqueda();
		muestraSol(solAStarM, bAStarM, p);
	}

	/**
	 * Metodo para mostrar una solucion por pantalla
	 * 
	 * @param sol, la solucion (la rama del arbol de busqueda correspondiente al
	 *             camino entre el inicio y la meta)
	 * @param b,   la busqueda realizada (para saber numero de nodos expandidos, en
	 *             frontera...)
	 * @param p,   el problema a resolver
	 */
	private static void muestraSol(List<Nodo<EstadoGPF, AccionGPF>> sol, Busqueda<EstadoGPF, AccionGPF> b,
			Problema<EstadoGPF, AccionGPF> p) {
		if (sol == null) // para evitar excepciones por punteros nulos
			System.out.println("No se ha encontrado solucion");
		else {
			System.out.println("Camino solucion");
			for (Nodo<EstadoGPF, AccionGPF> n : sol) {
				if (n.esRaiz())
					System.out.print(n.getEstado() + " ");
				else
					System.out.print(n.getAccion() + " " + n.getEstado() + " ");
			}
			System.out.println("");
			System.out.println("Coste de la solucion: " + b.costeSolucion());
			System.out.println("Nodos explorados: " + b.nodosExplorados());
			System.out.println("Nodos en frontera: " + b.nodosEnFrontera());
		}

	}

}
