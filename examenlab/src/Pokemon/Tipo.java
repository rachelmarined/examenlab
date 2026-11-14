
package Pokemon;

public enum Tipo {
    FUEGO("Fuego"),
    AGUA("Agua"),
    PLANTA("Planta"),
    ELECTRICO("Eléctrico");
    
    private static final double[][] TABLA = {
        /* fuego     */ {0.5, 0.5, 2.0, 1.0},
        /* agua      */ {2.0, 0.5, 0.5, 1.0},
        /* planta    */ {0.5, 2.0, 0.5, 1.0},
        /* electrico */ {1.0, 2.0, 0.5, 0.5}
    };

    private final String texto;

    Tipo(String texto) {
        this.texto = texto;
    }

    public double getMultiplicador(Tipo defensor) {
        return TABLA[this.ordinal()][defensor.ordinal()];
    }

    @Override
    public String toString() {
        return texto;
    }
}
