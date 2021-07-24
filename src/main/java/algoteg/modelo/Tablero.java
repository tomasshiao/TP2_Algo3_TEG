package algoteg.modelo;

import algoteg.Exceptions.*;

import java.util.*;

public class Tablero {

    private List<Continente> continentes;
    private List<Pais> paises;

    public Tablero(List<Continente> continentes, List<Pais>paises){
        this.continentes = continentes;
        this.paises = paises;
    }
    public void setContinentes(List<Continente> continentes){
        this.continentes = continentes;
    }
    public void setPaises(List<Pais>paises){
        this.paises = paises;
    }

    /*******
     * Realiza un ataque
     * @param jugadorAtacante Jugador Jugador atacante.
     * @param paisAtacante Pais país atacante.
     * @param paisDefensor Pais país defensor.
     * @param numeroTropas int número de tropas con la que se ataca.
     * @return Pais país ganador.
     * *********/
    public Pais atacar(Jugador jugadorAtacante, Pais paisAtacante, Pais paisDefensor, int numeroTropas) throws AtaqueInvalidoException {
        if(!paisAtacante.getPaisesLimitrofes().contains(paisDefensor)) {
            String exceptionType = "NoLimitrofe";
           throw new AtaqueInvalidoException(exceptionType);
        }
        if(paisAtacante.getEjercitoActual() < numeroTropas){
            String exceptionType = "TropasInsuficientes";
            throw new AtaqueInvalidoException(exceptionType);
        }
        if(!jugadorAtacante.getPaisesConquistados().contains(paisAtacante)){
            String exceptionType = "PaisNoMePertenece";
            throw new AtaqueInvalidoException(exceptionType);
        }
        if(jugadorAtacante.getPaisesConquistados().contains(paisDefensor)){
            String exceptionType = "ConquistarPaisPropio";
            throw new AtaqueInvalidoException(exceptionType);
        }
        LanzadorDados lanzadorDados = new LanzadorDados();
        List<GeneradorRandom> dadosAtacante = lanzadorDados.obtenerDadosAtacante(paisAtacante, numeroTropas);
        List<GeneradorRandom> dadosDefensor = lanzadorDados.obtenerDadosDefensor(paisDefensor);
        Batalla batalla = new Batalla();
        return batalla.obtenerVictoriosoDeGuerra(dadosAtacante, dadosDefensor, paisAtacante, paisDefensor, numeroTropas);
    }

    public List<Pais> getPaises(){
        return paises;
    }

    public Continente getContinente(String nombreContinente) {
        for(Continente c: this.continentes){
            String nombre = c.getNombre().toLowerCase();
            if(nombre.equals(nombreContinente))
                return c;
        }
        return null;//implementar continente no encontrado error
    }
    public List<Continente> getContinentesGobernadosPor(Jugador jugador){
        List<Continente> continentesGobernados = new ArrayList<>();
            continentes.forEach(continente -> {
                if (continente.esGobernante(jugador)) {
                    continentesGobernados.add(continente);
                }
            });
        return continentesGobernados;
    }
    public void moverEjercito(Pais paisOrigen, Pais paisDestino, int cantidadTropas, Jugador jugador) throws MoverEjercitoException {
        if(!paisOrigen.getPaisesLimitrofes().contains(paisDestino)) {
            String exceptionType = "NoLimitrofe";
            throw new MoverEjercitoException(exceptionType);
        }
        if(paisOrigen.getEjercitoActual() < cantidadTropas){
            String exceptionType = "TropasInsuficientes";
            throw new MoverEjercitoException(exceptionType);
        }
        if(!jugador.tienePais(paisOrigen) || !jugador.tienePais(paisDestino)) {
            String exceptionType = "PaisNoMePertenece";
            throw new MoverEjercitoException(exceptionType);
        }
        if(paisOrigen.getEjercitoActual() <= cantidadTropas){
            String exceptionType = "TropasInsuficientes";
            throw new MoverEjercitoException(exceptionType);
        }

        if(paisOrigen.getEjercitoActual() > cantidadTropas) {
            paisOrigen.reducirEjercito(cantidadTropas);
            paisDestino.agregarEjercito(cantidadTropas);
        }
    }

}