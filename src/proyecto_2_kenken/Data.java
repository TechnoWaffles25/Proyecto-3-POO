package proyecto_2_kenken;

import java.io.Serializable;
import java.time.Duration;
/**
 * Clase de cofiguracion del programa
 * @author Alejandro Monter, Fabricio Monge 
 */
public class Data implements Serializable{
    private static final long serialVersionUID = 123456789L;
    private final String dificultad;
    private final String reloj;
    private final boolean posicion;
    private final boolean sonido;
    private final Duration duration;
    private final int sizeTablero;
    /**
     * constructor
     * @param pDificultad dificultad de juego
     * @param pReloj para usar un reloj
     * @param pPosicion posicion de casillas de valores
     * @param pSonido si se decide usar sonidos
     * @param pDuration duracion de tiempo
     * @param pSizeTablero tamanno del tablero
     */
    public Data(String pDificultad, String pReloj, boolean pPosicion, boolean pSonido, Duration pDuration, int pSizeTablero){
        dificultad = pDificultad;
        reloj = pReloj;
        posicion = pPosicion;
        duration = pDuration;
        sonido = pSonido;
        sizeTablero = pSizeTablero;
    }
    
    public String getDificultad(){
        return dificultad;
    }
    public String getReloj(){
        return reloj;
    }
    public boolean getPosicion(){
        return posicion;
    }
    public boolean getSonido(){
        return sonido;
    }
    public Duration getDuration(){
        return duration;
    }
    public int getSizeTablero(){
        return sizeTablero;
    }
    
}
