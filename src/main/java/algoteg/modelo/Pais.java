package algoteg.modelo;

import java.util.*;

public class Pais {

    String nombre;
    int cantidadEjercito;
    Jugador gobernante;
    List<Pais> paisesLimitrofes;

    public Pais(String nombre, Jugador ocupante){
        this.nombre = nombre;
        this.gobernante = ocupante;
        cantidadEjercito = 0;
    }

    public void setJugador(Jugador jugador){
        this.gobernante = jugador;
    }

    public void setPaisesLimitrofes(List<Pais> listaPaises) {
        this.paisesLimitrofes = listaPaises;
    }

    public List<Pais> getPaisesLimitrofes(){
        return this.paisesLimitrofes;
    }

    public void agregarEjercito(int cantidad){
        cantidadEjercito += cantidad;
    }

    public void reducirEjercito(int cantidad){
        cantidadEjercito -= cantidad;
    }

    public int getEjercitoActual(){
        return this.cantidadEjercito;
    }

    public String getNombre(){ return this.nombre;}

    public boolean noTengoTropas(){
        return (this.cantidadEjercito == 0);
    }

    private void conquistar(Jugador jugador){
        if(this.noTengoTropas()){
            this.agregarEjercito(1);
            this.setJugador(jugador);
            jugador.addPaisConquistado(this);
        }
    }

    public void ocupar(Pais paisDefensor){
        paisDefensor.conquistar(this.gobernante);
    }

    public boolean esGobernadoPor(Jugador jugador) {
        return (gobernante.equals(jugador));
    }


}
