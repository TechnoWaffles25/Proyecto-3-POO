/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto_2_kenken.observador;

import java.time.Duration;

/**
 *
 * @author fabri
 */
public interface observador {
    void actualizar(String dificultad, String reloj, boolean lado, boolean sonido,Duration tiempoJuego, int sizeTablero);
}
