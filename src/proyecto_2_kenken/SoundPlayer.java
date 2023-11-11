
package proyecto_2_kenken;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
/**
 * Clase utilizada para reproducir audios en el programa
 * @author Alejandro Montero, Fabricio Monge
 */
public class SoundPlayer {
    /**
     * funcion que abre el audio y lo reproduce en caso de error envia un mensaje en consola
     * @param pathToSound ruta del audio
     */
    public void playSound(String pathToSound){
        try {
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(new File(pathToSound));
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e){
            e.printStackTrace();
            System.out.println("Excepcion en el audio...");
        }
        
    }
}
