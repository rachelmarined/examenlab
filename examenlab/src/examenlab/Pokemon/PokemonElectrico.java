
package examenlab.Pokemon;

public class PokemonElectrico extends Pokemon{
    public PokemonElectrico(String nombre, int ps, int ataque, int defensa, int velocidad){
        super(nombre, Tipo.ELECTRICO, 5, ps, ataque, defensa, velocidad);
    }

    @Override
    public void usarHabilidadEspecial(Pokemon defensor){
        if(usosHabilidad > 0){
            int potencia = 17;
            int daño = calcularDaño(potencia, defensor);
            if(Math.random() < 0.25){
                daño = (int) (daño * 1.5);
            }
            defensor.recibirDaño(daño);
            usosHabilidad--;
        }
    }
}
