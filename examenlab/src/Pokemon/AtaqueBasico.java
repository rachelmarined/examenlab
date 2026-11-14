
package Pokemon;

public class AtaqueBasico implements Movimiento{
    
    public static final int POTENCIA = 10;

    @Override
    public void ejecutar(Pokemon atacante, Pokemon defensor) {
        int daño = atacante.calcularDaño(POTENCIA, defensor);
        defensor.recibirDaño(daño);
    }

    @Override
    public String getNombre() {
        return "Ataque básico";
    }
}
