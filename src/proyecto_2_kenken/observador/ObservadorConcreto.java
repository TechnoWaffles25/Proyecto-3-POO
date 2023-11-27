/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto_2_kenken.observador;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.time.Duration;
import proyecto_2_kenken.Data;

/**
 *
 * @author fabri
 */
public class ObservadorConcreto implements observador {
    private String nombre;
    
    public ObservadorConcreto(String nombre) {
        this.nombre = nombre;
    }
    @Override
    public void actualizar(String dificultad, String reloj, boolean lado, boolean sonido,Duration tiempoJuego, int sizeTablero) {
        Data config = new Data(dificultad.toLowerCase(), reloj, lado, sonido, tiempoJuego, sizeTablero);
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("config.dat"))) {
                oos.writeObject(config); // Escribimos la configuracion cada vez q salimos
                System.out.println("Dificultad: " + dificultad + "\nReloj: " + reloj + "\nLado Panel: " + sonido + "\nSonido: " + sonido + "\nTiempo Juego: " + tiempoJuego + "\nSize Tablero: " + sizeTablero);
            } catch (IOException e) {
                e.printStackTrace();
            }
        System.out.println(nombre + " ha recibido el cambio de configuración ");
    }
}
