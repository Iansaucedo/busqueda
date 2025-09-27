# Implementación de Acciones en GPF

## Resumen

Este documento explica la implementación de las acciones de movimiento en el problema de Grid Path Finding (GPF). Las acciones representan los movimientos posibles que un agente puede realizar en una grilla bidimensional.

## Objetivo

Implementar un sistema de acciones que permita al agente moverse en las cuatro direcciones básicas: arriba, abajo, izquierda y derecha, cumpliendo con las restricciones del problema.

## Diseño de la Clase AccionGPF

### Decisiones de Diseño

#### 1. **Uso de Enum para Direcciones**
```java
public enum Direccion {
    ARRIBA, ABAJO, IZQUIERDA, DERECHA
}
```

**Justificación:**
- **Type Safety**: El compilador garantiza que solo se usen direcciones válidas
- **Legibilidad**: Los nombres son claros y autodocumentados
- **Mantenibilidad**: Fácil agregar nuevas direcciones si fuera necesario
- **Eficiencia**: Los enums son eficientes en memoria y ejecución

#### 2. **Métodos getDeltaX() y getDeltaY()**
```java
public int getDeltaX() {
    return switch (direccion) {
        case ARRIBA -> -1;
        case ABAJO -> 1;
        case IZQUIERDA, DERECHA -> 0;
    };
}

public int getDeltaY() {
    return switch (direccion) {
        case ARRIBA, ABAJO -> 0;
        case IZQUIERDA -> -1;
        case DERECHA -> 1;
    };
}
```

**Justificación:**
- **Separación de Responsabilidades**: Cada método tiene una única responsabilidad
- **Reutilización**: Los deltas se pueden usar en múltiples contextos
- **Claridad**: Es evidente qué hace cada método
- **Eficiencia**: Cálculo directo sin estructuras de datos adicionales

#### 3. **Sistema de Coordenadas**

**Convención Adoptada:**
- **X**: Representa las filas (0 = fila superior)
- **Y**: Representa las columnas (0 = columna izquierda)
- **ARRIBA**: Decrementa X (x-1)
- **ABAJO**: Incrementa X (x+1)
- **IZQUIERDA**: Decrementa Y (y-1)
- **DERECHA**: Incrementa Y (y+1)

```
Sistema de Coordenadas:
    0   1   2   3   Y
0   ┌───┬───┬───┬───┐
    │   │   │   │   │
1   ├───┼───┼───┼───┤
    │   │   │   │   │
2   ├───┼───┼───┼───┤
    │   │   │   │   │
X   └───┴───┴───┴───┘
```

**Alternativas Consideradas:**
1. **Y invertida**: Donde Y=0 está abajo (descartada por confusión)
2. **Coordenadas matemáticas**: Donde (0,0) está abajo-izquierda (descartada por complejidad)
3. **Sistema actual**: Matriz estándar donde (0,0) está arriba-izquierda

## Implementación Detallada

### Estructura de la Clase

```java
public class AccionGPF {
    private final Direccion direccion;
    
    // Constructor, getters, y métodos de utilidad
}
```

**Decisiones:**
- **`final`**: La dirección es inmutable después de la creación
- **Encapsulación**: El enum interno está protegido, solo accesible via métodos

### Métodos Implementados

#### 1. **Constructor**
```java
public AccionGPF(Direccion direccion) {
    this.direccion = direccion;
}
```
- Simple y directo
- Validación implícita por el tipo enum

#### 2. **toString()**
```java
@Override
public String toString() {
    return switch (direccion) {
        case ARRIBA -> "Arriba";
        case ABAJO -> "Abajo";
        case IZQUIERDA -> "Izquierda";
        case DERECHA -> "Derecha";
    };
}
```
- Nombres en español para mejor legibilidad en los resultados
- Utilizamos switch expression de Java 14+ para concisión

#### 3. **equals() y hashCode()**
```java
@Override
public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;
    AccionGPF that = (AccionGPF) obj;
    return direccion == that.direccion;
}

@Override
public int hashCode() {
    return direccion != null ? direccion.hashCode() : 0;
}
```
- Implementación estándar para comparación por valor
- Necesario para usar en colecciones HashMap/HashSet
- Aprovecha el hashCode del enum que es eficiente

}

## Integración con el Problema

### En ProblemaGPF.acciones()
```java
public List<AccionGPF> acciones(EstadoGPF eactual) {
    List<AccionGPF> lista = new LinkedList<AccionGPF>();
    
    AccionGPF[] todasLasAcciones = {
        new AccionGPF(AccionGPF.Direccion.ARRIBA),
        new AccionGPF(AccionGPF.Direccion.ABAJO),
        new AccionGPF(AccionGPF.Direccion.IZQUIERDA),
        new AccionGPF(AccionGPF.Direccion.DERECHA)
    };
    
    for (AccionGPF accion : todasLasAcciones) {
        if (aplicable(eactual, accion)) {
            lista.add(accion);
        }
    }
    return lista;
}
```

### En ProblemaGPF.aplicable()
```java
public boolean aplicable(EstadoGPF e, AccionGPF a) {
    int nuevaX = e.getX() + a.getDeltaX();
    int nuevaY = e.getY() + a.getDeltaY();
    
    // Verificar límites del grid
    if (nuevaX < 0 || nuevaX >= getGridNFilas() || 
        nuevaY < 0 || nuevaY >= getGridNCols()) {
        return false;
    }
    
    // Verificar que no sea obstáculo
    return grid[nuevaX][nuevaY] > 0;
}
```

### En ProblemaGPF.resul()
```java
public EstadoGPF resul(EstadoGPF e, AccionGPF a) {
    if (!aplicable(e, a)) return null;
    
    int nuevaX = e.getX() + a.getDeltaX();
    int nuevaY = e.getY() + a.getDeltaY();
    
    return new EstadoGPF(nuevaX, nuevaY);
}
```

## Ventajas de la Implementación

### 1. **Eficiencia**
- Cálculo O(1) para deltas
- Comparación O(1) para igualdad
- Sin uso excesivo de memoria

### 2. **Mantenibilidad**
- Código limpio y legible
- Separación clara de responsabilidades
- Fácil extensión para nuevas direcciones

### 3. **Robustez**
- Type safety con enums
- Inmutabilidad de acciones
- Validación implícita

### 4. **Usabilidad**
- Salida legible en español
- API intuitiva
- Integración seamless con el framework

## Casos de Prueba

Las acciones se prueban implícitamente en cada ejecución del sistema:

```
Estado inicial: [4, 5]
Acciones aplicables: [Arriba]
Resultado de Arriba: [3, 5]

Estado [3, 5]  
Acciones aplicables: [Arriba, Abajo]
```

## Extensibilidad Futura

La implementación permite fácilmente:

1. **Movimientos Diagonales**
```java
enum Direccion {
    ARRIBA, ABAJO, IZQUIERDA, DERECHA,
    ARRIBA_IZQUIERDA, ARRIBA_DERECHA,
    ABAJO_IZQUIERDA, ABAJO_DERECHA
}
```

2. **Acciones con Costes Variables**
```java
public double getCosto() {
    return switch (direccion) {
        case ARRIBA, ABAJO, IZQUIERDA, DERECHA -> 1.0;
        default -> Math.sqrt(2); // Diagonales
    };
}
```

3. **Validación Contextual**
```java
public boolean esValidaEn(EstadoGPF estado, int[][] grid) {
    // Lógica de validación específica
}
```

## Conclusión

La implementación de AccionGPF logra un balance excelente entre:
- **Simplicidad** y **funcionalidad**
- **Eficiencia** y **claridad**  
- **Robustez** y **extensibilidad**

Esta implementación sirve como base sólida para el sistema de búsqueda y demuestra buenas prácticas de programación orientada a objetos.
