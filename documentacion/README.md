# Documentación del Sistema de Búsqueda en Grafos

Este proyecto implementa un sistema completo de algoritmos de búsqueda en espacios de estados, aplicado al problema de búsqueda de rutas en grillas (Grid Path Finding - GPF).

## Estructura de la Documentación

- **[README.md](README.md)** - Este documento principal
- **[diagramas/](diagramas/)** - Diagramas UML de clases y secuencia
- **[explicaciones/](explicaciones/)** - Documentación detallada de cada componente

## Propósito del Proyecto

El sistema está diseñado para demostrar y comparar diferentes algoritmos de búsqueda:

### Algoritmos Implementados
1. **Búsqueda Primero en Anchura (BFS)** - Búsqueda ciega, encuentra solución óptima
2. **Búsqueda Primero en Profundidad (DFS)** - Búsqueda ciega, no garantiza optimalidad
3. **Búsqueda de Coste Uniforme** - Búsqueda informada, garantiza solución óptima
4. **Búsqueda Primero el Mejor Voraz** - Búsqueda heurística, rápida pero no óptima
5. **Búsqueda A*** - Búsqueda heurística, óptima y eficiente

## Arquitectura del Sistema

### Componentes Principales

#### 1. **Framework de Búsqueda Genérico**
- `Busqueda<Estado,Accion>` - Clase abstracta base
- `Problema<Estado,Accion>` - Define el espacio de estados
- `Nodo<Estado,Accion>` - Representa nodos en el árbol de búsqueda
- `Frontera<Estado,Accion>` - Manejo de la frontera de búsqueda

#### 2. **Implementaciones Específicas de Búsqueda**
- `BusquedaPrimeroAnchura` - Implementa BFS
- `BusquedaPrimeroProf` - Implementa DFS  
- `BusquedaPrimeroMejor` - Implementa búsquedas informadas (UCS, Greedy, A*)

#### 3. **Problema Específico: Grid Path Finding (GPF)**
- `ProblemaGPF` - Implementación del problema de rutas en grillas
- `EstadoGPF` - Representa posiciones (x,y) en la grilla
- `AccionGPF` - Representa movimientos (arriba, abajo, izquierda, derecha)
- `HeuristicoGPFManhattan` - Heurístico de distancia Manhattan

## Flujo de Ejecución

```
1. Cargar problema desde archivo
2. Crear instancia del algoritmo de búsqueda
3. Ejecutar búsqueda()
4. Mostrar resultado: camino, coste, nodos explorados
```

## Métricas de Evaluación

Para cada algoritmo se mide:
- **Coste de la solución** - Suma de costes de las acciones
- **Nodos explorados** - Eficiencia del algoritmo
- **Nodos en frontera** - Uso de memoria
- **Optimalidad** - Si encuentra la mejor solución

## Decisiones de Diseño

### 1. **Patrón Template Method**
La clase `Busqueda` utiliza este patrón para definir el algoritmo general, permitiendo que las subclases personalicen partes específicas.

### 2. **Uso de Genéricos**
Permite reutilizar el framework para diferentes tipos de problemas sin modificar el código base.

### 3. **Separación de Responsabilidades**
- **Problema**: Define el espacio de estados
- **Búsqueda**: Implementa la estrategia de exploración
- **Frontera**: Maneja la cola de nodos pendientes
- **Heurístico**: Proporciona estimaciones de coste

## Resultados Esperados

Los algoritmos deben mostrar estas características:

| Algoritmo | Optimalidad | Completitud | Complejidad Temporal | Complejidad Espacial |
|-----------|-------------|-------------|---------------------|----------------------|
| BFS | Sí (si costes uniformes) | Sí | O(b^d) | O(b^d) |
| DFS | No | Sí (si finito) | O(b^m) | O(bm) |
| UCS | Sí | Sí | O(b^(C*/ε)) | O(b^(C*/ε)) |
| Greedy | No | Sí | O(b^m) | O(b^m) |
| A* | Sí (con h admisible) | Sí | O(b^d) | O(b^d) |

Donde:
- b = factor de ramificación
- d = profundidad de la solución óptima
- m = profundidad máxima del espacio
- C* = coste de la solución óptima
- ε = menor coste de acción

## Archivos de Configuración

Los problemas se definen en archivos de texto con formato:
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
...
```

## Enlaces Rápidos

- [Diagrama de Clases Principal](diagramas/diagrama-clases-principal.puml)
- [Implementación de Acciones](explicaciones/implementacion-acciones.md)
- [Algoritmo BusquedaPrimeroMejor](explicaciones/busqueda-primero-mejor.md)
- [Heurístico Manhattan](explicaciones/heuristico-manhattan.md)
- [Análisis de Resultados](explicaciones/analisis-resultados.md)
