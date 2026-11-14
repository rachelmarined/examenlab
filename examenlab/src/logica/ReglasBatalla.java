package logica;

/**
 *
 * @author XPS 13
 */
public final class ReglasBatalla {

    public static final int MAX_RONDAS = 50;

    public static final int POTENCIA_ATAQUE_BASICO = 10;

    public static final String MENSAJE_MUY_EFICAZ = "¡Es muy eficaz!";
    public static final String MENSAJE_POCO_EFICAZ = "No es muy eficaz…";

    private ReglasBatalla() {
    }

    public static boolean actuaPrimeroJugador(AccionBatalla accionJugador, int velocidadJugador,
                                              AccionBatalla accionRival, int velocidadRival) {
        boolean jugadorCambia = accionJugador.tienePrioridad();
        boolean rivalCambia = accionRival.tienePrioridad();
        if (jugadorCambia != rivalCambia) {
            return jugadorCambia;
        }
        return velocidadJugador >= velocidadRival;
    }

    public static boolean seAlcanzoLimiteRondas(int ronda) {
        return ronda >= MAX_RONDAS;
    }

    public static EstadoBatalla resultadoPorPS(int psJugador, int psRival) {
        if (psJugador > psRival) {
            return EstadoBatalla.VICTORIA;
        }
        if (psJugador < psRival) {
            return EstadoBatalla.DERROTA;
        }
        return EstadoBatalla.EMPATE;
    }

    public static EstadoBatalla resultadoPorEquipos(boolean jugadorTieneDisponibles,
                                                    boolean rivalTieneDisponibles) {
        if (!jugadorTieneDisponibles && !rivalTieneDisponibles) {
            return EstadoBatalla.EMPATE;
        }
        if (!rivalTieneDisponibles) {
            return EstadoBatalla.VICTORIA;
        }
        if (!jugadorTieneDisponibles) {
            return EstadoBatalla.DERROTA;
        }
        return null;
    }

    public static String mensajeEfectividad(double multiplicador) {
        if (multiplicador > 1.0) {
            return MENSAJE_MUY_EFICAZ;
        }
        if (multiplicador < 1.0) {
            return MENSAJE_POCO_EFICAZ;
        }
        return null;
    }
}
