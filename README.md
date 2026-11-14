# Batallas Pokémon

Simulador de batallas Pokémon por turnos para el Laboratorio de Programación II
(Examen 1 – Parcial 1). Proyecto Java con interfaz Swing organizado en los paquetes
`Pokemon`, `logica`, `control`, `vista` y `examenlab`.

## Requisitos

- JDK 17 o superior.
- NetBeans 12 o superior (opcional).

## Ejecución en NetBeans

1. `File → Open Project…`
2. Seleccionar la carpeta `examenlab` (contiene `build.xml`).
3. Verificar en `Properties → Run` que la clase principal sea `examenlab.Examenlab`.
4. Ejecutar con `Run Project (F6)`.

## Ejecución sin IDE

Desde la carpeta del proyecto:

```bat
javac -encoding UTF-8 -d build\classes src\Pokemon\*.java src\logica\*.java src\control\*.java src\vista\*.java src\examenlab\*.java
java -cp build\classes examenlab.Examenlab
```

También se puede ejecutar la demostración del modelo por consola:

```bat
java -cp build\classes Pokemon.PruebaModelo
```

## Estructura

```
examenlab/
├─ build.xml
├─ manifest.mf
├─ nbproject/
└─ src/
   ├─ examenlab/
   │  └─ Examenlab.java
   ├─ Pokemon/
   │  ├─ Tipo.java
   │  ├─ Pokemon.java
   │  ├─ PokemonFuego.java
   │  ├─ PokemonAgua.java
   │  ├─ PokemonPlanta.java
   │  ├─ PokemonElectrico.java
   │  ├─ Movimiento.java
   │  ├─ AtaqueBasico.java
   │  ├─ HabilidadEspecial.java
   │  ├─ Recuperar.java
   │  ├─ Equipo.java
   │  ├─ Entrenador.java
   │  ├─ Catalogo.java
   │  ├─ GestorSerializacion.java
   │  └─ PruebaModelo.java
   ├─ logica/
   │  ├─ AccionBatalla.java
   │  ├─ EstadoBatalla.java
   │  ├─ ReglasBatalla.java
   │  ├─ Batalla.java
   │  └─ ValidadorNombre.java
   ├─ control/
   │  └─ ControladorJuego.java
   └─ vista/
      ├─ Estilo.java
      ├─ LogoPokeball.java
      ├─ PanelInicio.java
      ├─ PanelEquipo.java
      ├─ PanelBatalla.java
      ├─ PanelResultado.java
      └─ VentanaPrincipal.java
```

## Reglas de la batalla

1. El entrenador se registra con un nombre de 3 a 10 caracteres (letras, números y espacios).
2. El equipo admite de 1 a 6 Pokémon, con un máximo de 2 copias del mismo Pokémon.
3. En cada ronda el jugador elige una acción:
   - **Atacar**: potencia 10.
   - **Habilidad especial**: 3 usos por Pokémon; el efecto depende del tipo.
   - **Recuperar**: 25 % de los PS máximos, 2 usos por Pokémon.
   - **Cambiar**: tiene prioridad sobre el resto de las acciones.
4. Actúa primero el Pokémon con mayor velocidad; en caso de empate actúa el jugador.
5. La efectividad de los tipos es la siguiente:

   | Atacante \ Defensor | Fuego | Agua | Planta | Eléctrico |
   |---------------------|:-----:|:----:|:------:|:---------:|
   | Fuego               | 0.5   | 0.5  | 2.0    | 1.0       |
   | Agua                | 2.0   | 0.5  | 0.5    | 1.0       |
   | Planta              | 0.5   | 2.0  | 0.5    | 0.5       |
   | Eléctrico           | 1.0   | 2.0  | 0.5    | 0.5       |

6. Gana quien deje al rival sin Pokémon disponibles. Si se alcanzan 50 rondas,
   gana quien tenga la mayor suma de PS restantes; si la suma es igual, hay empate.

## Requisitos del examen

- **Clonable**: `Pokemon` implementa `Cloneable` y `clone()` devuelve una copia
  independiente que se usa al agregar Pokémon al equipo, al generar el equipo rival
  y al agregar Pokémon personalizados al catálogo.
- **Serializable**: `Entrenador`, `Equipo` y `Pokemon` son serializables.
  `GestorSerializacion` guarda y restaura el entrenador en memoria mediante
  `ByteArrayOutputStream` / `ByteArrayInputStream`, sin crear archivos.
- **Recursividad**: `Equipo` valida el límite de copias de forma iterativa, mientras
  que `Batalla.sumaPS(...)` y `Batalla.siguienteDisponible(...)` resuelven la suma de
  PS y la búsqueda del siguiente Pokémon disponible de forma recursiva.

El menú **Pruebas** de la ventana principal permite verificar los tres requisitos
sin salir de la aplicación.

## Uso de la interfaz

1. **Inicio**: registrar el entrenador.
2. **Equipo**: agregar o quitar Pokémon del catálogo, crear un Pokémon personalizado,
   guardar y restaurar el equipo en memoria y comenzar la batalla.
3. **Batalla**: elegir Atacar, Habilidad, Recuperar o Cambiar en cada ronda; el registro
   muestra el detalle de los turnos.
4. **Resultado**: resumen de la partida con el estado de ambos equipos.
