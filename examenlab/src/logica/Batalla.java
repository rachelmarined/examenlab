package logica;

import Pokemon.Catalogo;
import Pokemon.Entrenador;
import Pokemon.Pokemon;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 *
 * @author XPS 13
 */
public class Batalla {

    public static final String NOMBRE_RIVAL = "Rival CPU";

    private final String nombreJugador;
    private final List<Pokemon> equipoJugador;
    private final List<Pokemon> equipoRival;
    private final List<String> mensajes;
    private final Random random;
    private int activoJugador;
    private int activoRival;
    private int ronda;
    private EstadoBatalla estado;

    public Batalla(Entrenador jugador, Catalogo catalogo) {
        this(jugador, catalogo, new Random());
    }

    public Batalla(Entrenador jugador, Catalogo catalogo, Random random) {
        this(jugador.getNombre(), jugador.getEquipo().getPokemones(), catalogo.getPokemones(), random);
    }

    public Batalla(String nombreJugador, List<Pokemon> equipo, List<Pokemon> catalogo) {
        this(nombreJugador, equipo, catalogo, new Random());
    }

    public Batalla(String nombreJugador, List<Pokemon> equipo, List<Pokemon> catalogo, Random random) {
        if (equipo == null || equipo.isEmpty()) {
            throw new IllegalArgumentException("El equipo debe tener al menos un Pokémon.");
        }
        if (catalogo == null || catalogo.isEmpty()) {
            throw new IllegalArgumentException("El catálogo está vacío.");
        }
        this.estado = EstadoBatalla.PREPARACION;
        this.nombreJugador = nombreJugador;
        this.random = random;
        this.mensajes = new ArrayList<>();
        this.equipoJugador = copiarEquipo(equipo);
        this.equipoRival = generarEquipoRival(catalogo, equipo.size());
        this.activoJugador = siguienteDisponible(equipoJugador, 0);
        this.activoRival = siguienteDisponible(equipoRival, 0);
        if (activoJugador == -1) {
            throw new IllegalArgumentException("El equipo no tiene Pokémon disponibles.");
        }
        this.ronda = 1;

        mensajes.add("¡Comienza la batalla! " + nombreJugador + " vs " + NOMBRE_RIVAL + ".");
        mensajes.add(nombreJugador + " envía a " + getActivoJugador().getNombre() + ".");
        mensajes.add(NOMBRE_RIVAL + " envía a " + getActivoRival().getNombre() + ".");
        estado = EstadoBatalla.ESPERANDO_ACCION;
    }

    public static int sumaPS(List<Pokemon> equipo, int indice) {
        if (indice >= equipo.size()) {
            return 0;
        }
        return equipo.get(indice).getPsActuales() + sumaPS(equipo, indice + 1);
    }

    public static int siguienteDisponible(List<Pokemon> equipo, int indice) {
        if (indice >= equipo.size()) {
            return -1;
        }
        if (!equipo.get(indice).estaDebilitado()) {
            return indice;
        }
        return siguienteDisponible(equipo, indice + 1);
    }

    public void ejecutarRonda(AccionBatalla accion, int indiceCambio) {
        String error = validarAccion(accion, indiceCambio);
        if (error != null) {
            throw new IllegalArgumentException(error);
        }
        estado = EstadoBatalla.RESOLVIENDO_RONDA;
        mensajes.add("===== Ronda " + ronda + " =====");

        Turno turnoJugador = new Turno(true, accion, indiceCambio);
        Turno turnoRival = elegirTurnoRival();

        boolean jugadorPrimero = ReglasBatalla.actuaPrimeroJugador(
                turnoJugador.accion, getActivoJugador().getVelocidad(),
                turnoRival.accion, getActivoRival().getVelocidad());

        if (jugadorPrimero) {
            ejecutarTurno(turnoJugador);
            ejecutarTurno(turnoRival);
        } else {
            ejecutarTurno(turnoRival);
            ejecutarTurno(turnoJugador);
        }

        cerrarRonda();
    }

    public void elegirReemplazo(int indice) {
        if (estado != EstadoBatalla.ELEGIR_REEMPLAZO) {
            throw new IllegalStateException("No hay que elegir un reemplazo en este momento.");
        }
        String error = motivoCambioInvalido(equipoJugador, activoJugador, indice);
        if (error != null) {
            throw new IllegalArgumentException(error);
        }
        activoJugador = indice;
        mensajes.add(nombreJugador + " envía a " + getActivoJugador().getNombre() + ".");
        estado = EstadoBatalla.ESPERANDO_ACCION;
    }

    public String validarAccion(AccionBatalla accion, int indiceCambio) {
        if (!estado.permiteAcciones()) {
            return "No puedes elegir una acción ahora (" + estado.getDescripcion() + ").";
        }
        if (accion == null) {
            return "Selecciona una acción.";
        }
        return motivoAccionInvalida(equipoJugador, activoJugador, accion, indiceCambio);
    }

    public List<AccionBatalla> accionesValidas() {
        if (!estado.permiteAcciones()) {
            return new ArrayList<>();
        }
        return accionesValidas(equipoJugador, activoJugador);
    }

    public List<Integer> getIndicesCambioValidos() {
        if (estado != EstadoBatalla.ESPERANDO_ACCION && estado != EstadoBatalla.ELEGIR_REEMPLAZO) {
            return new ArrayList<>();
        }
        return indicesCambioValidos(equipoJugador, activoJugador);
    }

    public String getResumen() {
        StringBuilder sb = new StringBuilder();
        switch (estado) {
            case VICTORIA:
                sb.append("¡Victoria! Felicidades, ").append(nombreJugador).append(".");
                break;
            case DERROTA:
                sb.append("Derrota. ").append(nombreJugador).append(", suerte para la próxima.");
                break;
            case EMPATE:
                sb.append("Empate. ").append(nombreJugador).append(" y ").append(NOMBRE_RIVAL)
                        .append(" quedaron igualados.");
                break;
            default:
                sb.append("La batalla sigue en curso.");
        }
        sb.append("\nRondas jugadas: ").append(ronda);
        sb.append("\nPokémon debilitados de ").append(nombreJugador).append(": ")
                .append(contarDebilitados(equipoJugador)).append("/").append(equipoJugador.size());
        sb.append("\nPokémon debilitados de ").append(NOMBRE_RIVAL).append(": ")
                .append(contarDebilitados(equipoRival)).append("/").append(equipoRival.size());
        sb.append("\nEn pie de ").append(nombreJugador).append(": ").append(listarEnPie(equipoJugador));
        sb.append("\nEn pie de ").append(NOMBRE_RIVAL).append(": ").append(listarEnPie(equipoRival));
        return sb.toString();
    }

    public EstadoBatalla getEstado() {
        return estado;
    }

    public int getRonda() {
        return ronda;
    }

    public Pokemon getActivoJugador() {
        return equipoJugador.get(activoJugador);
    }

    public Pokemon getActivoRival() {
        return equipoRival.get(activoRival);
    }

    public List<String> getMensajes() {
        return Collections.unmodifiableList(mensajes);
    }

    public String getNombreJugador() {
        return nombreJugador;
    }

    public String getNombreRival() {
        return NOMBRE_RIVAL;
    }

    public List<Pokemon> getEquipoJugador() {
        return Collections.unmodifiableList(equipoJugador);
    }

    public List<Pokemon> getEquipoRival() {
        return Collections.unmodifiableList(equipoRival);
    }

    private List<Pokemon> copiarEquipo(List<Pokemon> equipo) {
        List<Pokemon> copia = new ArrayList<>();
        for (Pokemon pokemon : equipo) {
            copia.add(pokemon.clone());
        }
        return copia;
    }

    private List<Pokemon> generarEquipoRival(List<Pokemon> catalogo, int cantidad) {
        List<Pokemon> rival = new ArrayList<>();
        for (int i = 0; i < cantidad; i++) {
            Pokemon elegido = catalogo.get(random.nextInt(catalogo.size()));
            rival.add(elegido.clone());
        }
        return rival;
    }

    private Turno elegirTurnoRival() {
        List<AccionBatalla> opciones = accionesValidas(equipoRival, activoRival);
        AccionBatalla accion = opciones.get(random.nextInt(opciones.size()));
        int indice = -1;
        if (accion == AccionBatalla.CAMBIAR) {
            List<Integer> indices = indicesCambioValidos(equipoRival, activoRival);
            indice = indices.get(random.nextInt(indices.size()));
        }
        return new Turno(false, accion, indice);
    }

    private void ejecutarTurno(Turno turno) {
        Pokemon actor = turno.esJugador ? getActivoJugador() : getActivoRival();
        if (actor.estaDebilitado()) {
            mensajes.add(etiqueta(actor, turno.esJugador) + " está debilitado y pierde su turno.");
            return;
        }
        Pokemon objetivo = turno.esJugador ? getActivoRival() : getActivoJugador();
        switch (turno.accion) {
            case ATACAR:
                atacar(actor, objetivo, turno.esJugador);
                break;
            case HABILIDAD:
                usarHabilidad(actor, objetivo, turno.esJugador);
                break;
            case RECUPERAR:
                recuperar(actor, turno.esJugador);
                break;
            case CAMBIAR:
                cambiar(turno.esJugador, turno.indiceCambio);
                break;
        }
    }

    private void atacar(Pokemon actor, Pokemon objetivo, boolean esJugador) {
        int psAntes = objetivo.getPsActuales();
        int danio = actor.calcularDaño(ReglasBatalla.POTENCIA_ATAQUE_BASICO, objetivo);
        objetivo.recibirDaño(danio);
        mensajes.add(etiqueta(actor, esJugador) + " usa " + AccionBatalla.ATACAR.getTexto() + ".");
        informarGolpe(actor, objetivo, !esJugador, psAntes);
    }

    private void usarHabilidad(Pokemon actor, Pokemon objetivo, boolean esJugador) {
        int psObjetivoAntes = objetivo.getPsActuales();
        int psActorAntes = actor.getPsActuales();
        actor.usarHabilidadEspecial(objetivo);
        mensajes.add(etiqueta(actor, esJugador) + " usa su habilidad especial.");
        informarGolpe(actor, objetivo, !esJugador, psObjetivoAntes);
        int curados = actor.getPsActuales() - psActorAntes;
        if (curados > 0) {
            mensajes.add(etiqueta(actor, esJugador) + " recupera " + curados + " PS ("
                    + actor.getPsActuales() + "/" + actor.getPsMax() + ").");
        }
    }

    private void recuperar(Pokemon actor, boolean esJugador) {
        int psAntes = actor.getPsActuales();
        actor.recuperar();
        int curados = actor.getPsActuales() - psAntes;
        mensajes.add(etiqueta(actor, esJugador) + " usa Recuperar y gana " + curados + " PS ("
                + actor.getPsActuales() + "/" + actor.getPsMax() + ").");
    }

    private void cambiar(boolean esJugador, int indice) {
        if (esJugador) {
            Pokemon anterior = getActivoJugador();
            activoJugador = indice;
            mensajes.add(nombreJugador + " retira a " + anterior.getNombre()
                    + " y envía a " + getActivoJugador().getNombre() + ".");
        } else {
            Pokemon anterior = getActivoRival();
            activoRival = indice;
            mensajes.add(NOMBRE_RIVAL + " retira a " + anterior.getNombre()
                    + " y envía a " + getActivoRival().getNombre() + ".");
        }
    }

    private void informarGolpe(Pokemon actor, Pokemon objetivo, boolean objetivoEsJugador, int psAntes) {
        double multiplicador = actor.getTipo().getMultiplicador(objetivo.getTipo());
        String efectividad = ReglasBatalla.mensajeEfectividad(multiplicador);
        if (efectividad != null) {
            mensajes.add(efectividad);
        }
        int perdidos = psAntes - objetivo.getPsActuales();
        mensajes.add(etiqueta(objetivo, objetivoEsJugador) + " pierde " + perdidos + " PS ("
                + objetivo.getPsActuales() + "/" + objetivo.getPsMax() + ").");
        if (objetivo.estaDebilitado()) {
            mensajes.add("¡" + etiqueta(objetivo, objetivoEsJugador) + " se ha debilitado!");
        }
    }

    private void cerrarRonda() {
        boolean jugadorTieneDisponibles = siguienteDisponible(equipoJugador, 0) != -1;
        boolean rivalTieneDisponibles = siguienteDisponible(equipoRival, 0) != -1;
        EstadoBatalla resultado = ReglasBatalla.resultadoPorEquipos(jugadorTieneDisponibles, rivalTieneDisponibles);
        if (resultado != null) {
            terminar(resultado);
            return;
        }

        if (getActivoRival().estaDebilitado()) {
            activoRival = siguienteDisponible(equipoRival, 0);
            mensajes.add(NOMBRE_RIVAL + " envía a " + getActivoRival().getNombre() + ".");
        }

        if (ReglasBatalla.seAlcanzoLimiteRondas(ronda)) {
            int psJugador = sumaPS(equipoJugador, 0);
            int psRival = sumaPS(equipoRival, 0);
            mensajes.add("Se alcanzó el límite de " + ReglasBatalla.MAX_RONDAS + " rondas. PS restantes: "
                    + nombreJugador + " " + psJugador + " - " + NOMBRE_RIVAL + " " + psRival + ".");
            terminar(ReglasBatalla.resultadoPorPS(psJugador, psRival));
            return;
        }

        ronda++;
        if (getActivoJugador().estaDebilitado()) {
            estado = EstadoBatalla.ELEGIR_REEMPLAZO;
            mensajes.add(nombreJugador + ", elige qué Pokémon enviar al combate.");
        } else {
            estado = EstadoBatalla.ESPERANDO_ACCION;
        }
    }

    private void terminar(EstadoBatalla resultado) {
        estado = resultado;
        switch (resultado) {
            case VICTORIA:
                mensajes.add("¡" + nombreJugador + " gana la batalla!");
                break;
            case DERROTA:
                mensajes.add(NOMBRE_RIVAL + " gana la batalla. " + nombreJugador + " ha perdido.");
                break;
            default:
                mensajes.add("¡La batalla terminó en empate!");
        }
    }

    private List<AccionBatalla> accionesValidas(List<Pokemon> equipo, int activo) {
        List<AccionBatalla> validas = new ArrayList<>();
        for (AccionBatalla accion : AccionBatalla.values()) {
            if (accion == AccionBatalla.CAMBIAR) {
                if (!indicesCambioValidos(equipo, activo).isEmpty()) {
                    validas.add(accion);
                }
            } else if (motivoAccionInvalida(equipo, activo, accion, -1) == null) {
                validas.add(accion);
            }
        }
        return validas;
    }

    private List<Integer> indicesCambioValidos(List<Pokemon> equipo, int activo) {
        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < equipo.size(); i++) {
            if (motivoCambioInvalido(equipo, activo, i) == null) {
                indices.add(i);
            }
        }
        return indices;
    }

    private String motivoAccionInvalida(List<Pokemon> equipo, int activo, AccionBatalla accion, int indiceCambio) {
        Pokemon pokemon = equipo.get(activo);
        if (pokemon.estaDebilitado()) {
            return pokemon.getNombre() + " está debilitado y no puede actuar.";
        }
        switch (accion) {
            case HABILIDAD:
                if (pokemon.getUsosHabilidad() <= 0) {
                    return pokemon.getNombre() + " ya no tiene usos de su habilidad especial.";
                }
                return null;
            case RECUPERAR:
                if (pokemon.getUsosRecuperacion() <= 0) {
                    return pokemon.getNombre() + " ya no tiene usos de Recuperar.";
                }
                if (pokemon.getPsActuales() >= pokemon.getPsMax()) {
                    return pokemon.getNombre() + " ya tiene sus PS completos.";
                }
                return null;
            case CAMBIAR:
                return motivoCambioInvalido(equipo, activo, indiceCambio);
            default:
                return null;
        }
    }

    private String motivoCambioInvalido(List<Pokemon> equipo, int activo, int indice) {
        if (indice < 0 || indice >= equipo.size()) {
            return "Selecciona un Pokémon de tu equipo.";
        }
        Pokemon elegido = equipo.get(indice);
        if (elegido.estaDebilitado()) {
            return elegido.getNombre() + " está debilitado.";
        }
        if (indice == activo) {
            return elegido.getNombre() + " ya está en combate.";
        }
        return null;
    }

    private int contarDebilitados(List<Pokemon> equipo) {
        int total = 0;
        for (Pokemon pokemon : equipo) {
            if (pokemon.estaDebilitado()) {
                total++;
            }
        }
        return total;
    }

    private String listarEnPie(List<Pokemon> equipo) {
        StringBuilder sb = new StringBuilder();
        for (Pokemon pokemon : equipo) {
            if (!pokemon.estaDebilitado()) {
                if (sb.length() > 0) {
                    sb.append(", ");
                }
                sb.append(pokemon.getNombre()).append(" (").append(pokemon.getPsActuales())
                        .append("/").append(pokemon.getPsMax()).append(" PS)");
            }
        }
        return sb.length() == 0 ? "ninguno" : sb.toString();
    }

    private String etiqueta(Pokemon pokemon, boolean esJugador) {
        return pokemon.getNombre() + (esJugador ? " de " + nombreJugador : " del rival");
    }

    private static class Turno {
        private final boolean esJugador;
        private final AccionBatalla accion;
        private final int indiceCambio;

        Turno(boolean esJugador, AccionBatalla accion, int indiceCambio) {
            this.esJugador = esJugador;
            this.accion = accion;
            this.indiceCambio = indiceCambio;
        }
    }
}
