
package Pokemon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Catalogo {
    private List<Pokemon> pokemonesDisponibles;
    
    public Catalogo() {
        pokemonesDisponibles = new ArrayList<>();
        pokemonesDisponibles.add(new PokemonFuego("Charmander", 90, 14, 8, 11));
        pokemonesDisponibles.add(new PokemonFuego("Vulpix", 85, 13, 9, 12));
        pokemonesDisponibles.add(new PokemonAgua("Squirtle", 100, 11, 12, 9));
        pokemonesDisponibles.add(new PokemonAgua("Psyduck", 95, 12, 9, 10));
        pokemonesDisponibles.add(new PokemonPlanta("Bulbasaur", 100, 12, 10, 8));
        pokemonesDisponibles.add(new PokemonPlanta("Oddish", 90, 13, 8, 7));
        pokemonesDisponibles.add(new PokemonElectrico("Pikachu", 80, 14, 7, 15));
        pokemonesDisponibles.add(new PokemonElectrico("Voltorb", 75, 12, 9, 16));
    }
    
    public List<Pokemon> getPokemones() {
        return Collections.unmodifiableList(pokemonesDisponibles);
    }
    
    public void agregarPersonalizado(Pokemon p) {
        if (p!= null){
            pokemonesDisponibles.add(p.clone());
        }
    }
}
