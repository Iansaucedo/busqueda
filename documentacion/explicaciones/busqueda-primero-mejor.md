# Implementación de BusquedaPrimeroMejor

## Resumen

La clase `BusquedaPrimeroMejor` es una implementación unificada que soporta múltiples algoritmos de búsqueda informada: Búsqueda de Coste Uniforme, Búsqueda Primero el Mejor Voraz, y A*. Esta implementación demuestra el poder del polimorfismo y la parametrización en el diseño de algoritmos.

## Objetivo

Crear una clase que implemente eficientemente tres algoritmos de búsqueda diferentes mediante la parametrización del criterio de ordenación y la función heurística.

## Arquitectura y Decisiones de Diseño

### 1. **Unificación de Algoritmos**

```java
public class BusquedaPrimeroMejor<Estado,Accion> extends Busqueda<Estado,Accion> {
    private Heuristico<Estado> h;
    private Comparator<Nodo<Estado,Accion>> comp;
}
```

**Algoritmos Implementados:**
- **Coste Uniforme**: `Criterio.g` con heurístico trivial
- **Greedy**: `Criterio.h` con heurístico específico  
- **A***: `Criterio.f` con heurístico específico

**Justificación:**
- **DRY Principle**: No repetir código común
- **Flexibilidad**: Un solo código para múltiples algoritmos
- **Mantenimiento**: Cambios centralizados
- **Testing**: Una implementación, múltiples pruebas

### 2. **Sistema de Criterios**

```java
enum Criterio { g, h, f }
```

**Comparación de Nodos:**
- **g(n)**: Coste acumulado desde el inicio
- **h(n)**: Estimación heurística al objetivo  
- **f(n)**: g(n) + h(n) (función de evaluación A*)

**Implementación en ComparaNodos:**
```java
public int compare(Nodo<Estado,Accion> n1, Nodo<Estado,Accion> n2) {
    double valor1, valor2;
    switch(crit) {
        case g: 
            valor1 = n1.getG(); valor2 = n2.getG(); 
            break;
        case h: 
            valor1 = n1.getH(); valor2 = n2.getH(); 
            break;
        case f: 
            valor1 = n1.getF(); valor2 = n2.getF(); 
            break;
    }
    return Double.compare(valor1, valor2);
}
```

### 3. **Constructores Sobrecargados**

#### Constructor para Coste Uniforme
```java
public BusquedaPrimeroMejor(Problema<Estado,Accion> p) {
    super(p);
    frontera = new FronteraPrioridad<Estado,Accion>(Criterio.g);
    h = new Heuristico<Estado>();  // h trivial (siempre 0)
    comp = new ComparaNodos<Estado,Accion>(Criterio.g);
}
```

#### Constructor para Greedy y A*
```java
public BusquedaPrimeroMejor(Problema<Estado,Accion> p, Criterio c, Heuristico<Estado> h) {
    super(p);
    frontera = new FronteraPrioridad<Estado,Accion>(c);
    this.h = h;
    comp = new ComparaNodos<Estado,Accion>(c);
}
```

**Justificación:**
- **Convenience**: Constructor simple para caso común (UCS)
- **Flexibilidad**: Constructor parametrizado para casos avanzados
- **Claridad**: Intención evidente en cada constructor

## Implementación de Métodos Clave

### 1. **Método inicia() - Inicialización Especializada**

```java
@Override
protected void inicia() {
    frontera.clear();
    Nodo<Estado,Accion> inicial = new Nodo<Estado,Accion>(prob.getInicio());
    // CRÍTICO: Calcular h explícitamente
    inicial.setH(h.calculaH(inicial.getEstado()));
    frontera.aniade(inicial);
    explorados.clear();
}
```

**Por qué Override:**
- **Cálculo de h**: Los nodos necesitan valor heurístico calculado
- **Orden de Operaciones**: h debe establecerse antes de añadir a frontera
- **Consistencia**: Todos los nodos deben tener h calculado

### 2. **Método expandir() - Expansión con Heurística**

```java
@Override
protected List<Nodo<Estado,Accion>> expandir(Nodo<Estado,Accion> actual) {
    List<Accion> accAplicables = prob.acciones(actual.getEstado());
    List<Nodo<Estado,Accion>> nsucesores = new LinkedList<Nodo<Estado,Accion>>();
    
    for (Accion a : accAplicables) {
        Estado suc = prob.resul(actual.getEstado(), a);
        Nodo<Estado,Accion> hijo = new Nodo<Estado,Accion>(
            suc, actual, a, prob.coste(actual.getEstado(), a, suc)
        );
        // CRÍTICO: Calcular heurística para cada hijo
        hijo.setH(h.calculaH(hijo.getEstado()));
        nsucesores.add(hijo);
    }
    return nsucesores;
}
```

**Justificación del Override:**
- **Cálculo de h**: Cada nodo hijo necesita su valor heurístico
- **Eficiencia**: h se calcula una vez al crear el nodo
- **Correctitud**: Sin h correcto, los algoritmos fallan

### 3. **Método tratarRepetidos() - Corazón de la Optimización**

```java
@Override
protected void tratarRepetidos(List<Nodo<Estado,Accion>> hijos) {
    for (Nodo<Estado,Accion> hijo : hijos) {
        if (explorados.get(hijo.getEstado()) == null) {
            if (noRepeOPeorEnFrontera(hijo)) {
                frontera.aniade(hijo);
            }
        }
    }
}
```

**Diferencias con la Versión Base:**
- **Versión Base**: Solo verifica si el nodo ya existe
- **Versión BPM**: Compara calidad de nodos repetidos

**Por qué es Crítico:**
- **Optimalidad**: A* puede fallar sin esta optimización
- **Eficiencia**: Evita explorar caminos subóptimos
- **Correctitud**: Mantiene invariante de que frontera contiene mejores rutas conocidas

### 4. **Método Auxiliar noRepeOPeorEnFrontera()**

```java
private boolean noRepeOPeorEnFrontera(Nodo<Estado,Accion> hijo) {
    Nodo<Estado,Accion> nodoEnFrontera = frontera.contieneNodo(hijo);
    
    if (nodoEnFrontera == null) {
        return true;  // No existe, añadir
    } else {
        if (comp.compare(hijo, nodoEnFrontera) < 0) {
            // Hijo es mejor, eliminar el peor
            frontera.frontera.remove(nodoEnFrontera);
            return true;
        } else {
            // Nodo en frontera es mejor o igual
            return false;
        }
    }
}
```

**Algoritmo:**
1. **Buscar** nodo con mismo estado en frontera
2. **Si no existe**: Añadir hijo
3. **Si existe y hijo es mejor**: Reemplazar
4. **Si existe y hijo es peor/igual**: Rechazar hijo

**Justificación:**
- **Garantía de Optimalidad**: A* necesita esta lógica
- **Mejora de Eficiencia**: Evita redundancia
- 🔄 **Mantener Invariante**: Frontera siempre contiene mejores rutas

## Comparación de Comportamientos

| Aspecto | Coste Uniforme | Greedy | A* |
|---------|----------------|---------|-----|
| **Criterio** | g(n) | h(n) | f(n) = g(n) + h(n) |
| **Heurística** | h(n) = 0 | h(n) específico | h(n) específico |
| **Garantía** | Óptimo | No óptimo | Óptimo* |
| **Eficiencia** | Baja | Alta | Media-Alta |
| **Uso de Memoria** | Alto | Medio | Medio-Alto |

*Con heurística admisible

## Casos de Uso y Resultados

### Ejemplo de Ejecución en GPF:

```
PROBLEMA: p6x6.txt
Estado inicial: [4, 5] → Meta: [4, 1]

COSTE UNIFORME:
- Camino: [4,5] → [3,5] → [2,5] → [1,5] → [1,4] → [0,4] → [0,3] → [0,2] → [0,1] → [1,1] → [2,1] → [3,1] → [4,1]
- Coste: 16.0
- Nodos explorados: 21
- Nodos en frontera: 1

GREEDY (Manhattan):
- Camino: [4,5] → [3,5] → [2,5] → [1,5] → [1,4] → [0,4] → [0,3] → [0,2] → [1,2] → [2,2] → [3,2] → [4,2] → [4,1]
- Coste: 19.0
- Nodos explorados: 23
- Nodos en frontera: 3

A* (Manhattan):
- Camino: [4,5] → [3,5] → [2,5] → [1,5] → [1,4] → [0,4] → [0,3] → [0,2] → [0,1] → [1,1] → [2,1] → [3,1] → [4,1]
- Coste: 16.0
- Nodos explorados: 21  
- Nodos en frontera: 1
```

## 🔬 Análisis de la Implementación

### Fortalezas

1. **Arquitectura Elegante**
   - Un código, múltiples algoritmos
   - Reutilización máxima de componentes
   - Separación clara de responsabilidades

2. **Eficiencia**
   - Frontera de prioridad optimizada
   - Tratamiento inteligente de repetidos
   - Cálculo eficiente de heurísticas

3. **Correctitud**
   - Implementación fiel a los algoritmos teóricos
   - Manejo correcto de casos borde
   - Garantías de optimalidad preservadas

4. **Mantenibilidad**
   - Código limpio y bien documentado
   - Fácil debugging y testing
   - Extensible para nuevos algoritmos

### Consideraciones de Rendimiento

1. **Complejidad Temporal**
   - **UCS/A***: O(b^(C*/ε)) donde C* es coste óptimo, ε menor coste
   - **Greedy**: O(b^m) donde m es profundidad máxima

2. **Complejidad Espacial**
   - Dominada por la frontera de prioridad
   - Mejora significativa vs BFS/DFS naivos

3. **Optimizaciones Implementadas**
   - Eliminación proactiva de nodos subóptimos
   - Uso eficiente de HashMap para explorados
   - Cálculo lazy de heurísticas

## Extensibilidad Futura

La arquitectura permite fácilmente:

1. **Nuevos Criterios**
```java
enum Criterio { g, h, f, weighted_f, depth_first_f }
```

2. **Heurísticas Compuestas**
```java
public class HeuristicoCompuesto<Estado> extends Heuristico<Estado> {
    private List<Heuristico<Estado>> heuristicos;
    private double[] pesos;
}
```

3. **Algoritmos Avanzados**
   - **IDA***: A* iterativo con profundidad limitada
   - **Weighted A***: A* con factor de peso en heurística
   - **Bidirectional Search**: Búsqueda desde ambos extremos

## Conclusión

La implementación de `BusquedaPrimeroMejor` representa un excelente ejemplo de:

- **📐 Diseño Orientado a Objetos**: Herencia, polimorfismo, encapsulación
- **🔄 Patrones de Diseño**: Template Method, Strategy Pattern
- **Optimización Algorítmica**: Estructuras de datos eficientes
- **Correctitud Formal**: Adherencia a fundamentos teóricos

Esta clase sirve como núcleo del framework de búsqueda y demuestra cómo una implementación bien diseñada puede unificar múltiples algoritmos complejos manteniendo claridad y eficiencia.
