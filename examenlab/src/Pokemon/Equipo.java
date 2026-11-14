
package Pokemon;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Equipo implements Serializable{
    private List<Pokemon> integrantes;
    private final int MAX_INTEGRANTES = 6;

    public Equipo() {
        integrantes = new ArrayList<>();
    }

    public boolean agregar(Pokemon p){
        if(integrantes.size() >= MAX_INTEGRANTES) return false;
        
        long count = integrantes.stream().filter(pk -> pk.getNombre().equals(p.getNombre())).count();
        if(count >= 2) return false;

        integrantes.add(p.clone());
        return true;
    }

    public boolean quitar(int indice){
        if(indice >= 0 && indice < integrantes.size()){
            integrantes.remove(indice);
            return true;
        }
        return false;
    }

    public List<Pokemon> getPokemones(){
        return integrantes;
    }

    public int size(){
        return integrantes.size();
    }
}
