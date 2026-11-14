
package examenlab.Pokemon;

public class PokemonAgua extends Pokemon{
    public PokemonAgua(String nombre, int ps, int ataque, int defensa, int velocidad) {
        super(nombre, Tipo.AGUA, 5, ps, ataque, defensa, velocidad);
    }

    @Override
    public void usarHabilidadEspecial(Pokemon defensor){
        if(usosHabilidad > 0){
            int daño = calcularDaño(18, defensor);
            defensor.recibirDaño(daño);
            int curacion = (int) (psMax * 0.10);
            psActuales = Math.min(psMax, psActuales + curacion);
            usosHabilidad--;
        }
    }
}
