
package Pokemon;

public class PokemonPlanta extends Pokemon{
    public PokemonPlanta(String nombre, int ps, int ataque, int defensa, int velocidad){
        super(nombre, Tipo.PLANTA, 5, ps, ataque, defensa, velocidad);
    }

    @Override
    public void usarHabilidadEspecial(Pokemon defensor){
        if(puedeUsarHabilidad()){
            int daño = calcularDaño(18, defensor);
            int psAntes = defensor.getPsActuales();
            defensor.recibirDaño(daño);
            int dañoReal = psAntes - defensor.getPsActuales();

            int absorcion = dañoReal / 2;                        
            psActuales = Math.min(psMax, psActuales + absorcion);
            usosHabilidad--;
        }
    }
    
    @Override
    public String getNombreHabilidad(){ return "Absorber"; }

    @Override
    public String getDescripcionHabilidad(){ return "Daño moderado (potencia 18) y absorbe la mitad del daño como PS"; }
}
