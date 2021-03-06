package algoteg.PruebasUnitarias;

import algoteg.Exceptions.MoverEjercitoException;
import algoteg.modelo.Jugador;
import algoteg.modelo.Pais;
import algoteg.modelo.Partida;
import algoteg.modelo.Tarjeta;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class JugadorTest {
    Jugador jugador1 = new Jugador(1, "azul");
    Pais paisMock = mock(Pais.class);
    Tarjeta tarjetaMock = mock(Tarjeta.class);

    Tarjeta tarjetaArg = new Tarjeta(paisMock, "Barco");
    Tarjeta tarjetaAle = new Tarjeta(paisMock, "Barco");
    Tarjeta tarjetaJap = new Tarjeta(paisMock, "Barco");
    Tarjeta tarjetaEsp = new Tarjeta(paisMock, "Canion");
    Tarjeta tarjetaUru = new Tarjeta(paisMock, "Globo");


    @Test
    public void unJugadorTieneTresTarjetasConMismoDibujoLasCanjeaYObtieneCuatroTropasACambio(){
        jugador1.addTarjeta(tarjetaAle);
        jugador1.addTarjeta(tarjetaArg);
        jugador1.addTarjeta(tarjetaJap);

        jugador1.canjearTarjetas(tarjetaArg, tarjetaJap, tarjetaAle);
        assertEquals(4, jugador1.getEjercitoParaIncorporar());
    }

    @Test
    public void unJugadorTieneTresTarjetasConDiferenteDibujoLasCanjeaYObtieneCuatroTropasACambio(){
        jugador1.addTarjeta(tarjetaArg);
        jugador1.addTarjeta(tarjetaEsp);
        jugador1.addTarjeta(tarjetaUru);

        jugador1.canjearTarjetas(tarjetaArg, tarjetaEsp, tarjetaUru);
        assertEquals(4, jugador1.getEjercitoParaIncorporar());
    }

    @Test
    public void unJugadorTieneDosTarjetasConIgualDibujoYUnaConDiferenteLasIntentaCanjearYNoObtieneTropasACambio(){
        jugador1.addTarjeta(tarjetaArg);
        jugador1.addTarjeta(tarjetaAle);
        jugador1.addTarjeta(tarjetaUru);

        jugador1.canjearTarjetas(tarjetaArg, tarjetaAle, tarjetaUru);
        assertEquals(0, jugador1.getEjercitoParaIncorporar());
    }

    @Test
    public void unJugadorTieneTresTarjetasYAlCanjearlasSeQuedaConCero(){
        jugador1.addTarjeta(tarjetaMock);
        jugador1.addTarjeta(tarjetaMock);
        jugador1.addTarjeta(tarjetaMock);

        when(jugador1.canjearTarjetas(tarjetaMock,tarjetaMock,tarjetaMock)).thenReturn(true);

        jugador1.canjearTarjetas(tarjetaMock,tarjetaMock,tarjetaMock);
        assertEquals(0, jugador1.getTarjetasEnSuPoder());
    }

    @Test
    public void unJugadorTieneDosTarjetaIgualesYUnaDiferenteLasIntentaCanjearYSigueTeniendoTresTarjetasEnSuPoder(){
        jugador1.addTarjeta(tarjetaArg);
        jugador1.addTarjeta(tarjetaAle);
        jugador1.addTarjeta(tarjetaUru);

        jugador1.canjearTarjetas(tarjetaArg, tarjetaAle, tarjetaUru);
        assertEquals(3, jugador1.getTarjetasEnSuPoder());
    }

    @Test
    public void unJugadorActivaUnaTarjetaDeUnPaisQueConquistoYRecibeDosTropas(){

        when(paisMock.getNombre()).thenReturn("Argentina");
        jugador1.addTarjeta(tarjetaArg);
        jugador1.addPaisConquistado(paisMock);
        jugador1.activarTarjeta(tarjetaArg);

        assertEquals(2, jugador1.getEjercitoParaIncorporar());
    }

    @Test
    public void unJugadorIntentaActivarUnaTarjetaDeUnPaisQueNoConquistoYNoRecibeTropas(){

        when(paisMock.getNombre()).thenReturn("Argentina");
        jugador1.activarTarjeta(tarjetaArg);

        assertEquals(0, jugador1.getEjercitoParaIncorporar());
    }

    @Test
    public void unJugadorActivaUnaTarjetaDePaisConquistadoYCanjeaTresTarjetasIgualesRecibeSeisTropas(){
        jugador1.addTarjeta(tarjetaMock);
        jugador1.addTarjeta(tarjetaMock);
        jugador1.addTarjeta(tarjetaArg);

        when(paisMock.getNombre()).thenReturn("Argentina");
        when(tarjetaMock.getDibujo()).thenReturn("Barco");

        jugador1.addPaisConquistado(paisMock);
        jugador1.activarTarjeta(tarjetaArg);

        jugador1.canjearTarjetas(tarjetaArg, tarjetaMock, tarjetaMock);

        assertEquals(6, jugador1.getEjercitoParaIncorporar());
    }

    @Test
    public void unJugadorCanjeaTresTarjetasIgualesYLuegoIntentaActivarUnaTarjetaDePaisConquistadoQueYaNoPosee(){
        when(paisMock.getNombre()).thenReturn("Argentina");
        when(tarjetaMock.getDibujo()).thenReturn("Barco");

        jugador1.addTarjeta(tarjetaMock);
        jugador1.addTarjeta(tarjetaMock);
        jugador1.addTarjeta(tarjetaArg);

        jugador1.addPaisConquistado(paisMock);
        jugador1.canjearTarjetas(tarjetaArg, tarjetaMock, tarjetaMock);
        jugador1.activarTarjeta(tarjetaArg);

        assertEquals(4, jugador1.getEjercitoParaIncorporar());
    }

    @Test
    public void unJugadorTieneDosTropasParaIncorporarLasAgregaAUnPaisQueConquistoYSeQuedaConCero(){
        when(paisMock.getNombre()).thenReturn("Argentina");
        jugador1.addPaisConquistado(paisMock);
        jugador1.addTarjeta(tarjetaArg);
        jugador1.activarTarjeta(tarjetaArg);

        jugador1.addEjercitoEnPais(paisMock, 2);

        assertEquals(0, jugador1.getEjercitoParaIncorporar());
    }

    @Test
    public void unJugadorTieneDosTropasParaIncorporarEIntentaAgregalasAUnPaisQueNoConquisto(){
        Pais otroPaisMock = mock(Pais.class);

        when(paisMock.getNombre()).thenReturn("Argentina");
        jugador1.addPaisConquistado(paisMock);
        jugador1.addTarjeta(tarjetaArg);
        jugador1.activarTarjeta(tarjetaArg);

        jugador1.addEjercitoEnPais(otroPaisMock, 2);

        assertEquals(2, jugador1.getEjercitoParaIncorporar());
    }

    @Test
    public void unJugadorTieneDosTropasParaIncorporarEIntentaAgregarTresAUnPaisQueConquisto(){
        Pais pais = new Pais("Argentina", jugador1);
        Tarjeta tarjetaArg = new Tarjeta(pais,"barco");
        jugador1.addPaisConquistado(pais);
        jugador1.addTarjeta(tarjetaArg);
        jugador1.activarTarjeta(tarjetaArg);

        assertEquals(2, jugador1.getEjercitoParaIncorporar());
        jugador1.addEjercitoEnPais(pais, 3);
        assertEquals(2, pais.getEjercitoActual());
    }

    @Test
    public void unJugadorRealizaDosCanjesDeTarjetaYRecibe4Y7Tropas(){
        when(tarjetaMock.getDibujo()).thenReturn("Barco");
        when(tarjetaMock.compararTarjetas(tarjetaMock,tarjetaMock)).thenReturn(true);
        jugador1.addTarjeta(tarjetaMock);
        jugador1.addTarjeta(tarjetaMock);
        jugador1.addTarjeta(tarjetaMock);
        jugador1.canjearTarjetas(tarjetaMock, tarjetaMock, tarjetaMock);

        jugador1.addTarjeta(tarjetaMock);
        jugador1.addTarjeta(tarjetaMock);
        jugador1.addTarjeta(tarjetaMock);
        jugador1.canjearTarjetas(tarjetaMock, tarjetaMock, tarjetaMock);

        assertEquals(11, jugador1.getEjercitoParaIncorporar());
    }

    @Test
    public void unJugadorRealizaTresCanjesDeTarjetaYRecibe4Y7Y10Tropas(){
        when(tarjetaMock.getDibujo()).thenReturn("Barco");
        when(tarjetaMock.compararTarjetas(tarjetaMock,tarjetaMock)).thenReturn(true);
        jugador1.addTarjeta(tarjetaMock);
        jugador1.addTarjeta(tarjetaMock);
        jugador1.addTarjeta(tarjetaMock);
        jugador1.canjearTarjetas(tarjetaMock, tarjetaMock, tarjetaMock);

        jugador1.addTarjeta(tarjetaMock);
        jugador1.addTarjeta(tarjetaMock);
        jugador1.addTarjeta(tarjetaMock);
        jugador1.canjearTarjetas(tarjetaMock, tarjetaMock, tarjetaMock);

        jugador1.addTarjeta(tarjetaMock);
        jugador1.addTarjeta(tarjetaMock);
        jugador1.addTarjeta(tarjetaMock);
        jugador1.canjearTarjetas(tarjetaMock, tarjetaMock, tarjetaMock);

        assertEquals(21, jugador1.getEjercitoParaIncorporar());
    }

    @Test
    public void unJugadorRealizaCuatroCanjesDeTarjetaYRecibe4Y7Y10Y15Tropas(){
        when(tarjetaMock.getDibujo()).thenReturn("Barco");
        when(tarjetaMock.compararTarjetas(tarjetaMock,tarjetaMock)).thenReturn(true);
        jugador1.addTarjeta(tarjetaMock);
        jugador1.addTarjeta(tarjetaMock);
        jugador1.addTarjeta(tarjetaMock);
        jugador1.canjearTarjetas(tarjetaMock, tarjetaMock, tarjetaMock);

        jugador1.addTarjeta(tarjetaMock);
        jugador1.addTarjeta(tarjetaMock);
        jugador1.addTarjeta(tarjetaMock);
        jugador1.canjearTarjetas(tarjetaMock, tarjetaMock, tarjetaMock);

        jugador1.addTarjeta(tarjetaMock);
        jugador1.addTarjeta(tarjetaMock);
        jugador1.addTarjeta(tarjetaMock);
        jugador1.canjearTarjetas(tarjetaMock, tarjetaMock, tarjetaMock);

        jugador1.addTarjeta(tarjetaMock);
        jugador1.addTarjeta(tarjetaMock);
        jugador1.addTarjeta(tarjetaMock);
        jugador1.canjearTarjetas(tarjetaMock, tarjetaMock, tarjetaMock);

        assertEquals(36, jugador1.getEjercitoParaIncorporar());
    }

    @Test
    public void partidaLeAsignaNuevePaisesAlSextoJugador(){
        List<String> colores = List.of("azul", "rojo", "rosa", "naranja","verde","negro");
        int cantidadTotalDeJugadores =6;
        Partida partida = new Partida(cantidadTotalDeJugadores);
        List<Jugador> jugadores = new ArrayList<>();
        for(int i = 0; i<cantidadTotalDeJugadores; i++){
            jugadores.add(new Jugador(i, colores.get(i)));
        }
        jugadores.forEach(partida::agregarJugador);

        partida.iniciarPartida();
        int paisesConquistados = jugadores.get(5).getCantidadPaisesConquistados();

        assertEquals(9, paisesConquistados);
    }

    @Test
    public void partidaLeAsignaOchoPaisesAlPrimerJugador(){
        List<String> colores = List.of("azul", "rojo", "rosa", "naranja","verde","negro");
        int cantidadTotalDeJugadores =6;
        Partida partida = new Partida(cantidadTotalDeJugadores);
        List<Jugador> jugadores = new ArrayList<>();
        for(int i = 0; i<cantidadTotalDeJugadores; i++){
            jugadores.add(new Jugador(i, colores.get(i)));
        }
        jugadores.forEach(partida::agregarJugador);

        partida.iniciarPartida();
        int paisesConquistados = jugadores.get(0).getCantidadPaisesConquistados();

        assertEquals(8, paisesConquistados);
    }

    @Test
    public void cuandoUnJugadorTieneAlgunPaisConquistadoEstaVivo(){
        jugador1.addPaisConquistado(paisMock);
        assertTrue(jugador1.estaVivo());
    }

    @Test
    public void cuandoUnJugadorNoTienePaisesConquistadosYaNoEstaVivo(){
        assertFalse(jugador1.estaVivo());
    }

    /*@Test
    public void unJugadorMueve2TropasEntrePaisesLimitrofes() throws MoverEjercitoException {
       Pais pais = new Pais("Argentina", null);
        List<Pais> limitrofes = new ArrayList<>();
        when(paisMock.getPaisesLimitrofes()).thenReturn(limitrofes);
        when(paisMock.getEjercitoActual()).thenReturn(5);

        jugador1.addPaisConquistado(paisMock);
        jugador1.addPaisConquistado(pais);

        limitrofes.add(pais);

        jugador1.move(paisMock, pais, 2);

        assertEquals(2, pais.getEjercitoActual());
    }*/

}
