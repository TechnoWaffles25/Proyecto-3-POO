
package proyecto_2_kenken;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.time.Duration;

/**
 * clase utilizada para leer la configuracion que se utilizará para el juego de KenKen
 * @author Alejandro Montero, Fabricio Monge 
 */
public class ReadConfig {
    private Data config;
    /**
     * envía la configuracion escogida, y almacenada en un objeto Data a un archivo de configuración.
     */
    public void readConfiguration() {
        try (FileInputStream fileInputStream = new FileInputStream("config.dat");
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
       
            config = (Data) objectInputStream.readObject();
            System.out.println("Dificultad: " + config.getDificultad());
            System.out.println("Reloj: " + config.getReloj());
            System.out.println("Lado Panel: " + config.getPosicion());
            System.out.println("Sonido: " + config.getSonido());
            System.out.println("Tiempo Juego: " + config.getDuration());
            System.out.println("Tamaño Tablero: " + config.getSizeTablero());
            
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    public String getDificultad(){
        return config.getDificultad();
    }
    public String getReloj(){
        return config.getReloj();
    }
    public boolean getPosicion(){
        return config.getPosicion();
    }
    public boolean getSonido(){
        return config.getSonido();
    }
    public Duration getDuration(){
        return config.getDuration();
    }
    public String getSizeTablero(){
        return config.getSizeTablero();
    }
}
