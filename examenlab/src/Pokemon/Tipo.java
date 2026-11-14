
package Pokemon;

public enum Tipo {
    FUEGO, AGUA, PLANTA, ELECTRICO;
    
    public double getMultiplicador(Tipo defensor){
        switch (this){
            case FUEGO:
                if(defensor == FUEGO) return 0.5;
                if(defensor == AGUA) return 0.5;
                if (defensor == PLANTA) return 2.0;
                return 1.0;
            case AGUA:
                if (defensor == FUEGO) return 2.0;
                if (defensor == AGUA) return 0.5;
                if (defensor == PLANTA) return 0.5;
                return 1.0;
            case PLANTA:
                if (defensor == FUEGO) return 0.5;
                if (defensor == AGUA) return 2.0;
                if (defensor == PLANTA) return 0.5;
            case ELECTRICO:
                if (defensor == FUEGO) return 1.0;
                if (defensor == AGUA) return 2.0;
                if (defensor == PLANTA) return 0.5;
                if (defensor == ELECTRICO) return 0.5;
                return 1.0;
            default:
                return 1.0;
        }
    }
}
