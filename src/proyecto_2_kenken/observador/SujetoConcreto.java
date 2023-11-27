/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto_2_kenken.observador;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author fabri
 */
public class SujetoConcreto {
    private List<observador> observadores = new ArrayList<>();
    
    public void agregarObservador(observador observador) {
        observadores.add(observador);
    }

    public void quitarObservador(observador observador) {
        observadores.remove(observador);
    }

    private void notificarObservadores(String dificultad, String reloj, boolean lado, boolean sonido,Duration tiempoJuego, int sizeTablero) {
        for (observador observador : observadores) {
            observador.actualizar(dificultad, reloj, lado, sonido, tiempoJuego,sizeTablero);
        }
    }
}
