
package Pokemon;

public class PokemonAgua extends Pokemon{
    public PokemonAgua(String nombre, int ps, int ataque, int defensa, int velocidad) {
        super(nombre, Tipo.AGUA, 5, ps, ataque, defensa, velocidad);
    }

    @Override
    public void usarHabilidadEspecial(Pokemon defensor){
        if(puedeUsarHabilidad()){
            int daño = calcularDaño(18, defensor);
            defensor.recibirDaño(daño);
            int curacion = (int) (psMax * 0.10);
            psActuales = Math.min(psMax, psActuales + curacion);
            usosHabilidad--;
        }
    }
    
     @Override
    public String getNombreHabilidad(){ return "Hidrobomba"; }

    @Override
    public String getDescripcionHabilidad(){ return "Daño moderado (potencia 18) y recupera 10% de PS máximos"; }
}
