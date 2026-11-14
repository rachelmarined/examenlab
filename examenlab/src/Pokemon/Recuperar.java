
package Pokemon;

public class Recuperar implements Movimiento{
    @Override
    public void ejecutar(Pokemon atacante, Pokemon defensor){
        atacante.recuperar();
    }

    @Override
    public String getNombre(){
        return "Recuperar";
    }
}
