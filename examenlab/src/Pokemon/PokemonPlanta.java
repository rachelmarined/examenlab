
package Pokemon;

public class PokemonPlanta extends Pokemon{
    public PokemonPlanta(String nombre, int ps, int ataque, int defensa, int velocidad){
        super(nombre, Tipo.PLANTA, 5, ps, ataque, defensa, velocidad);
    }

    @Override
    public void usarHabilidadEspecial(Pokemon defensor){
        if(usosHabilidad > 0){
            int daño = calcularDaño(18, defensor);
            defensor.recibirDaño(daño);
            int absorcion = daño / 2;
            psActuales = Math.min(psMax, psActuales + absorcion);
            usosHabilidad--;
        }
    }
}
