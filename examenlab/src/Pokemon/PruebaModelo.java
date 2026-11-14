
package Pokemon;

public class PruebaModelo {
    public static void main(String[] args) {
        Catalogo cat = new Catalogo();
        Pokemon charmander = cat.getPokemones().get(0);
        Pokemon bulbasaur = cat.getPokemones().get(4);

        System.out.println("Fuego vs Planta: " + Tipo.FUEGO.getMultiplicador(Tipo.PLANTA) + " (esperado 2.0)");
        System.out.println("Planta vs Electrico: " + Tipo.PLANTA.getMultiplicador(Tipo.ELECTRICO) + " (esperado 0.5)");

        Pokemon defensor = new PokemonPlanta("Prueba", 100, 10, 10, 5);
        System.out.println("Daño ejemplo: " + charmander.calcularDaño(20, defensor) + " (esperado 68)");

        Equipo equipo = new Equipo();
        equipo.agregar(charmander);
        Pokemon copia = equipo.getPokemones().get(0);
        copia.recibirDaño(30);
        System.out.println("Original PS: " + charmander.getPsActuales() + " (esperado 90)");
        System.out.println("Copia PS: " + copia.getPsActuales() + " (esperado 60)");

        equipo.agregar(charmander);
        System.out.println("Tercera copia: " + equipo.agregar(charmander) + " (esperado false)");

        copia.recuperar();
        System.out.println("Tras recuperar: " + copia.getPsActuales() + " (esperado 82)");

        Entrenador ash = new Entrenador("Ash");
        ash.getEquipo().agregar(charmander);
        ash.getEquipo().agregar(bulbasaur);
        byte[] datos = GestorSerializacion.guardar(ash);
        ash.getEquipo().quitar(0);
        Entrenador restaurado = GestorSerializacion.restaurar(datos);
        System.out.println("Restaurado: " + restaurado.getNombre() + " con "
                + restaurado.getEquipo().size() + " Pokémon (esperado 2)");
        System.out.println(restaurado.getEquipo().getPokemones());
    }
}
