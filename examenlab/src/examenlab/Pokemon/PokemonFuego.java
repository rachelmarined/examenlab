
package examenlab.Pokemon;

import examenlab.Pokemon.Pokemon;

public class PokemonFuego extends Pokemon{
    public PokemonFuego(String nombre, int ps, int ataque, int defensa, int velocidad){
        super(nombre, Tipo.FUEGO, 5, ps, ataque, defensa, velocidad);
    }
    
    @Override
    public void usarHabilidadEspecial(Pokemon defensor){
        if(usosHabilidad > 0){
            int daño = calcularDaño(20, defensor);
            defensor.recibirDaño(daño);
            usosHabilidad--;
        }
    }
}
