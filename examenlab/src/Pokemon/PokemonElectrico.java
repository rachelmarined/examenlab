
package Pokemon;

public class PokemonElectrico extends Pokemon{
    public PokemonElectrico(String nombre, int ps, int ataque, int defensa, int velocidad){
        super(nombre, Tipo.ELECTRICO, 5, ps, ataque, defensa, velocidad);
    }

    @Override
    public void usarHabilidadEspecial(Pokemon defensor){
        if(puedeUsarHabilidad()){
            int potencia = 17;
            int daño = calcularDaño(potencia, defensor);
            if(Math.random() < 0.25){
                daño = (int) Math.round(daño * 1.5);
            }
            defensor.recibirDaño(daño);
            usosHabilidad--;
        }
    }
    
    @Override
    public String getNombreHabilidad(){ return "Impactrueno"; }

    @Override
    public String getDescripcionHabilidad(){ return "Daño moderado (potencia 17) con 25% de golpe crítico (x1,5)"; }
}
