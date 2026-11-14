package control;

import Pokemon.Catalogo;
import Pokemon.Entrenador;
import Pokemon.Equipo;
import Pokemon.GestorSerializacion;
import Pokemon.Pokemon;
import Pokemon.PokemonAgua;
import Pokemon.PokemonElectrico;
import Pokemon.PokemonFuego;
import Pokemon.PokemonPlanta;
import Pokemon.Tipo;
import logica.Batalla;
import logica.ValidadorNombre;

import java.util.List;

public class ControladorJuego {

    public static final int MAX_EQUIPO = 6;

    private Entrenador entrenador;
    private Catalogo catalogo = new Catalogo();
    private Batalla batalla;
    private byte[] respaldo;

    public void crearEntrenador(String nombre) {
        String error = ValidadorNombre.validar(nombre);
        if (error != null) {
            throw new IllegalArgumentException(error);
        }
        this.entrenador = new Entrenador(ValidadorNombre.normalizar(nombre));
        this.batalla = null;
    }

    public boolean hayEntrenador() {
        return entrenador != null;
    }

    public Entrenador getEntrenador() {
        return entrenador;
    }

    public List<Pokemon> getCatalogo() {
        return catalogo.getPokemones();
    }

    public void reiniciarCatalogo() {
        this.catalogo = new Catalogo();
    }

    public void agregarAlEquipo(int indiceCatalogo) {
        asegurarEntrenador();
        List<Pokemon> disponibles = catalogo.getPokemones();
        if (indiceCatalogo < 0 || indiceCatalogo >= disponibles.size()) {
            throw new IllegalArgumentException("Selecciona un Pokémon del catálogo.");
        }
        Pokemon elegido = disponibles.get(indiceCatalogo);
        Equipo equipo = entrenador.getEquipo();
        if (equipo.estaLleno()) {
            throw new IllegalStateException("El equipo ya tiene " + MAX_EQUIPO + " integrantes.");
        }
        if (equipo.contarCopias(elegido) >= Equipo.MAX_COPIAS) {
            throw new IllegalStateException("Ya tienes " + Equipo.MAX_COPIAS + " copias de "
                    + elegido.getNombre() + " en el equipo.");
        }
        equipo.agregar(elegido);
    }

    public void quitarDelEquipo(int indice) {
        asegurarEntrenador();
        if (!entrenador.getEquipo().quitar(indice)) {
            throw new IllegalArgumentException("Selecciona un Pokémon de tu equipo para quitarlo.");
        }
    }

    public boolean hayEquipo() {
        return entrenador != null && entrenador.getEquipo().size() > 0;
    }

    public int guardarEquipo() {
        asegurarEquipo();
        byte[] datos = GestorSerializacion.guardar(entrenador);
        if (datos == null) {
            throw new IllegalStateException("No se pudo guardar el equipo.");
        }
        respaldo = datos;
        return datos.length;
    }

    public int restaurarEquipo() {
        if (!hayRespaldo()) {
            throw new IllegalStateException("Todavía no hay un equipo guardado.");
        }
        Entrenador guardado = GestorSerializacion.restaurar(respaldo);
        if (guardado == null) {
            throw new IllegalStateException("No se pudo restaurar el equipo guardado.");
        }
        asegurarEntrenador();
        Equipo equipo = entrenador.getEquipo();
        while (equipo.size() > 0) {
            equipo.quitar(0);
        }
        for (Pokemon pokemon : guardado.getEquipo().getPokemones()) {
            if (!equipo.agregar(pokemon)) {
                throw new IllegalStateException("El equipo guardado no cumple los límites del equipo.");
            }
        }
        return equipo.size();
    }

    public boolean hayRespaldo() {
        return respaldo != null && respaldo.length > 0;
    }

    public int tamanoRespaldo() {
        return respaldo == null ? 0 : respaldo.length;
    }

    public void agregarAlCatalogo(String nombre, Tipo tipo) {
        String error = ValidadorNombre.validar(nombre);
        if (error != null) {
            throw new IllegalArgumentException(error);
        }
        if (tipo == null) {
            throw new IllegalArgumentException("Selecciona un tipo para el Pokémon.");
        }
        String limpio = ValidadorNombre.normalizar(nombre);
        Pokemon nuevo;
        switch (tipo) {
            case FUEGO:
                nuevo = new PokemonFuego(limpio, 90, 13, 9, 10);
                break;
            case AGUA:
                nuevo = new PokemonAgua(limpio, 95, 12, 10, 9);
                break;
            case PLANTA:
                nuevo = new PokemonPlanta(limpio, 95, 12, 10, 8);
                break;
            case ELECTRICO:
            default:
                nuevo = new PokemonElectrico(limpio, 85, 13, 8, 13);
                break;
        }
        catalogo.agregarPersonalizado(nuevo);
    }

    public void comenzarBatalla() {
        asegurarEquipo();
        this.batalla = new Batalla(entrenador, catalogo);
    }

    public Batalla getBatalla() {
        return batalla;
    }

    public void nuevaPartida() {
        this.batalla = null;
    }

    private void asegurarEntrenador() {
        if (entrenador == null) {
            throw new IllegalStateException("Primero registra un entrenador.");
        }
    }

    private void asegurarEquipo() {
        asegurarEntrenador();
        if (entrenador.getEquipo().size() == 0) {
            throw new IllegalStateException("Agrega al menos un Pokémon a tu equipo.");
        }
    }
}
