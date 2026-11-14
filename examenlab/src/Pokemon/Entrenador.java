
package Pokemon;

public class Entrenador{
    private String nombre;
    private Equipo equipo;

    public Entrenador(String nombre){
        this.nombre = nombre;
        this.equipo = new Equipo();
    }

    public String getNombre(){ return nombre; }
    public Equipo getEquipo(){ return equipo; }
}
