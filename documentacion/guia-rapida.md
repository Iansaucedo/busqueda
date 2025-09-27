# Guía Rápida del Sistema de Búsqueda

## Inicio Rápido

### 1. Ejecutar el Sistema
```bash
# Compilar todo
javac -cp . busqueda/**/*.java

# Ejecutar pruebas completas
java -cp . busqueda.GPF.Tester

# Probar un problema específico  
java -cp . busqueda.GPF.TesterProblema p6x6.txt
```

### 2. Algoritmos Disponibles

| Algoritmo | Clase | Uso Recomendado |
|-----------|-------|-----------------|
| **A*** | `BusquedaPrimeroMejor(p, Criterio.f, hM)` | **Uso general** |
| **UCS** | `BusquedaPrimeroMejor(p)` | Garantía óptima |
| **Greedy** | `BusquedaPrimeroMejor(p, Criterio.h, hM)` | Velocidad |
| **BFS** | `BusquedaPrimeroAnchura(p)` | Costes uniformes |
| **DFS** | `BusquedaPrimeroProf(p)` | Memoria limitada |

## Interpretación de Resultados

### Ejemplo de Salida
```
PROBLEMA: p6x6.txt
Estado inicial: [4, 5] → Meta: [4, 1]

A* CON MANHATTAN:
Camino solucion: [4, 5] Arriba [3, 5] ... [4, 1]
Coste de la solucion: 16.0    ← Coste total óptimo
Nodos explorados: 21          ← Eficiencia del algoritmo  
Nodos en frontera: 1          ← Uso de memoria
```

### Métricas Clave
- **Coste**: Menor es mejor
- **Nodos explorados**: Menor es más eficiente
- **Nodos frontera**: Menor usa menos memoria

## Cuándo Usar Cada Algoritmo

### A* (Recomendado)
```java
Heuristico<EstadoGPF> h = new HeuristicoGPFManhattan(problema);
Busqueda<EstadoGPF, AccionGPF> busqueda = 
    new BusquedaPrimeroMejor<>(problema, Criterio.f, h);
```
**Usar cuando**: Necesites el mejor balance velocidad/calidad

### Greedy (Rápido)
```java
Busqueda<EstadoGPF, AccionGPF> busqueda = 
    new BusquedaPrimeroMejor<>(problema, Criterio.h, h);
```
**Usar cuando**: Velocidad es más importante que optimalidad

### UCS (Óptimo)
```java
Busqueda<EstadoGPF, AccionGPF> busqueda = 
    new BusquedaPrimeroMejor<>(problema);
```
**Usar cuando**: Necesites garantía absoluta de optimalidad

## Formato de Archivos de Problema

```
NUMERO DE FILAS
6
NUMERO DE COLUMNAS  
6
INICIO
[4, 5]
FIN
[4, 1]
CUADRICULA
2 1 1 3 1 2
0 1 4 0 1 1
1 1 3 0 2 1
2 3 1 0 0 1  
1 1 1 1 0 1
0 2 1 1 0 0
```

**Valores en Cuadrícula:**
- `0`: Obstáculo (no transitable)
- `1-9`: Casilla libre con coste de tránsito

## Personalización

### Crear Nuevo Problema
```java
int[][] grid = {
    {1, 1, 1},
    {0, 1, 0}, 
    {1, 1, 1}
};
ProblemaGPF problema = new ProblemaGPF(grid, 0, 0, 2, 2);
```

### Crear Nuevo Heurístico
```java
public class MiHeuristico extends Heuristico<EstadoGPF> {
    @Override
    public double calculaH(EstadoGPF estado) {
        // Tu lógica aquí
        return estimacion;
    }
}
```

## Visualización de Resultados

### Cuadrícula con Coordenadas
```
   | 0  1  2  3  4  5 
---+------------------
 0 | 2  1  1  3  1  2     ← Fila 0
 1 | 0  1  4  0  1  1     ← Fila 1  
 2 | 1  1  3  0  2  1     ← Fila 2
 3 | 2  3  1  0  0  1     ← Fila 3
 4 | 1  1  1  1  0  1     ← Fila 4
 5 | 0  2  1  1  0  0     ← Fila 5
```

### Interpretación del Camino
```
[4, 5] Arriba [3, 5] Arriba [2, 5] ...
  ↑      ↑       ↑        ↑      ↑
 Pos    Acción  Nueva   Acción Nueva
Actual          Pos            Pos
```

## Troubleshooting

### Problema: "No se ha encontrado solución"
**Causas**:
- No existe camino al objetivo
- Inicio o meta en obstáculo
- Archivo de problema malformado

### Problema: Rendimiento Lento
**Soluciones**:
- Usar A* en lugar de BFS
- Verificar que heurístico sea admisible
- Reducir tamaño del problema para pruebas

### Problema: Solución Subóptima
**Causas**:
- Usar Greedy o DFS
- Heurístico no admisible
- Bug en implementación

## Benchmark Rápido

Para evaluar rendimiento:
```java
long inicio = System.currentTimeMillis();
List<Nodo<EstadoGPF, AccionGPF>> solucion = busqueda.busqueda();
long tiempo = System.currentTimeMillis() - inicio;

System.out.println("Tiempo: " + tiempo + "ms");
System.out.println("Nodos: " + busqueda.nodosExplorados());
System.out.println("Coste: " + busqueda.costeSolucion());
```

## Casos de Estudio

### Caso 1: Navegación GPS
- **Algoritmo**: A* con distancia real
- **Estado**: Coordenadas geográficas  
- **Acciones**: Segmentos de carretera

### Caso 2: Videojuegos
- **Algoritmo**: A* con Manhattan/Euclidiana
- **Estado**: Posición del personaje
- **Acciones**: Movimientos permitidos

### Caso 3: Robótica
- **Algoritmo**: A* con heurísticas físicas
- **Estado**: Configuración del robot
- **Acciones**: Movimientos actuadores

## Extensiones Futuras

### Algoritmos Avanzados
- **IDA***: A* iterativo con memoria limitada
- **Theta***: Búsqueda con línea de vista
- **JPS**: Jump Point Search para grillas

### Heurísticas Avanzadas
- **Differential**: Basada en diferencias
- **Pattern Database**: Pre-calculadas
- **Adaptive**: Que aprenden

### Optimizaciones  
- **Bidirectional**: Búsqueda desde ambos extremos
- **Hierarchical**: Multi-resolución
- **Anytime**: Mejoran la solución gradualmente

---

**Consejo**: Siempre comienza con A* + Manhattan. Es el mejor punto de partida para la mayoría de problemas de búsqueda de rutas.
