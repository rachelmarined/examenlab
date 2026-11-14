
package examenlab.Pokemon;

import java.io.Serializable;

public abstract class Pokemon implements Serializable, Cloneable{
    protected String nombre;
    protected Tipo tipo;
    protected int nivel;
    protected int psMax;
    protected int psActuales;
    protected int ataque;
    protected int defensa;
    protected int velocidad;
    protected int usosHabilidad = 3;
    protected int usosRecuperacion = 2;
    
    public Pokemon(String nombre, Tipo tipo, int nivel, int psMax, int ataque, int defensa, int velocidad){
        this.nombre = nombre;
        this.tipo = tipo;
        this.nivel = nivel;
        this.psMax = psMax;
        this.psActuales = psMax;
        this.ataque = ataque;
        this.defensa = defensa;
        this.velocidad = velocidad;
    }
    
    public void recibirDaño(int cantidad){
        this.psActuales -= cantidad;
        if (this.psActuales < 0) {
            this.psActuales = 0;
        }
    }
    
    public boolean estaDebilitado(){
        return this.psActuales <= 0;
    }

    public void recuperar(){
        if(usosRecuperacion > 0 && !estaDebilitado()){
            int recuperacion = (int) Math.floor(psMax * 0.25);
            psActuales += recuperacion;
            if (psActuales > psMax) {
                psActuales = psMax;
            }
            usosRecuperacion--;
        }
    }
    
    public int calcularDaño(int potencia, Pokemon defensor){
        double mult = this.tipo.getMultiplicador(defensor.getTipo());
        double bruto = (this.ataque + this.nivel + potencia - (defensor.getDefensa() / 2.0)) * mult;
        int dañoFinal = (int) Math.round(bruto);
        return Math.max(1, dañoFinal);
    }
    
    public abstract void usarHabilidadEspecial(Pokemon defensor);
    
    @Override
    public Pokemon clone(){
        try{
            return (Pokemon) super.clone();
        }catch(CloneNotSupportedException e){
            throw new RuntimeException("El Pokémon no pudo ser clonado", e);
        }
    }
    
    public String getNombre(){ return nombre; }
    public Tipo getTipo(){ return tipo; }
    public int getNivel(){ return nivel; }
    public int getPsMax(){ return psMax; }
    public int getPsActuales(){ return psActuales; }
    public void setPsActuales(int psActuales){ this.psActuales = psActuales; }
    public int getAtaque(){ return ataque; }
    public int getDefensa(){ return defensa; }
    public int getVelocidad(){ return velocidad; }
    public int getUsosHabilidad(){ return usosHabilidad; }
    public int getUsosRecuperacion(){ return usosRecuperacion; }
}
