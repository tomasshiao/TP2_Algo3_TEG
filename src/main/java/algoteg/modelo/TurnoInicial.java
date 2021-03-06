package algoteg.modelo;

import java.util.*;

public class TurnoInicial implements Turno{
    private final Tablero tablero;

    private int cantidadJugadores;
    private Jugador jugadorActual;
    //int tropasDisponiblesPrimeraColocacion = 5;
    //int tropasDisponiblesSegundaColocacion = 3;
    private int cantidadAIncorporar;

    /****************
     * Inicializa una ronda.
     * @param tablero Tablero
     * ***************/
    public TurnoInicial(Tablero tablero, int cantidadAIncorporar) {
        this.tablero = tablero;
        this.cantidadAIncorporar = cantidadAIncorporar;

    }
    @Override
    public void setJugador(Jugador jugadorActual){
        this.jugadorActual = jugadorActual;
    }
    public Tablero getTablero() {
        return this.tablero;
    }


    @Override

    public void colocar( int cantTropas, Pais pais) {


        jugadorActual.addEjercitoEnPais(pais, cantTropas);

    }
    @Override
    public void iniciarTurno(Jugador jugador) {
        this.setJugador(jugador);
        this.setEjercitosDisponiblesParaColocar();
    }

    @Override
    public void activarTarjeta(Tarjeta tarjeta) {

    }

    @Override
    public boolean canjearTarjetas(Tarjeta tarjeta1, Tarjeta tarjeta2, Tarjeta tarjeta3) {
        return false;
    }

    public void setEjercitosDisponiblesParaColocar() {
        jugadorActual.setEjercitoDisponibleGlobal(cantidadAIncorporar);
    }

    @Override
    public boolean terminado(){return (cantidadAIncorporar ==(0));}
    @Override
    public Pais atacar(Pais paisAtacante, Pais paisDefensor, int cantidadTropas){return null;}
    @Override
    public String moverEjercito(Pais paisOrigen, Pais paisDestino, int cantidadTropas){return null;}

}