
package Pokemon;

public class HabilidadEspecial implements Movimiento{
    
     @Override
    public void ejecutar(Pokemon atacante, Pokemon defensor){
        atacante.usarHabilidadEspecial(defensor);
    }

    @Override
    public String getNombre(){
        return "Habilidad especial";
    }
}
