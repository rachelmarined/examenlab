
package Pokemon;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Equipo implements Serializable{
    private List<Pokemon> integrantes;
    private final int MAX_INTEGRANTES = 6;
    public static final int MAX_COPIAS = 2;

    public Equipo() {
        integrantes = new ArrayList<>();
    }
    
    public boolean estaLleno() {
        return integrantes.size() >= MAX_INTEGRANTES;
    }
    
    public int contarCopias(Pokemon p) {
        int cont = 0;
        for (Pokemon pk : integrantes) {
            if (pk.getNombre().equals(p.getNombre())) {
                cont++;
            }
        }
        return cont;
    }

    public boolean agregar(Pokemon p){
        if(p == null || estaLleno() || contarCopias(p) >= MAX_COPIAS){
            return false;
        }
        
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
