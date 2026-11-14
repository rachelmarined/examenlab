package logica;

/**
 *
 * @author XPS 13
 */
public enum EstadoBatalla {

    PREPARACION("Preparando la batalla"),
    ESPERANDO_ACCION("Esperando acción del entrenador"),
    RESOLVIENDO_RONDA("Resolviendo la ronda"),
    ELEGIR_REEMPLAZO("Elige un Pokémon de reemplazo"),
    VICTORIA("Victoria"),
    DERROTA("Derrota"),
    EMPATE("Empate");

    private final String descripcion;

    EstadoBatalla(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public boolean esFinal() {
        return this == VICTORIA || this == DERROTA || this == EMPATE;
    }

    public boolean permiteAcciones() {
        return this == ESPERANDO_ACCION;
    }

    @Override
    public String toString() {
        return descripcion;
    }
}
