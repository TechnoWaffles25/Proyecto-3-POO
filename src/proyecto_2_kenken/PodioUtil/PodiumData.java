package proyecto_2_kenken.PodioUtil;

import java.io.Serializable;

/**
 * Clase de configuracion del podio
 * @author Alejandro Montero, Fabricio Monge
 */
public class PodiumData implements Serializable{
    private static final long serialVersionUID = 1234567890L;
    private entryDificultad actual;
    private entryDificultad facil;
    private entryDificultad medio;
    private entryDificultad dificil;
    
    /**
     * constructor
     * @param pDificultad dificultad de juego
     * @param pReloj para usar un reloj
     * @param pPosicion posicion de casillas de valores
     * @param pSonido si se decide usar sonidos
     * @param pDuration duracion de tiempo
     * @param pSizeTablero tamanno del tablero
     */
    PodiumData(entryDificultad pActual, entryDificultad pFacil, entryDificultad pMedio, entryDificultad pDificil){
        actual = pActual;
        facil = pFacil;
        medio = pMedio;
        dificil = pDificil;
    }
    
    entryDificultad getActual(){
        return actual;
    }
    entryDificultad getFacil(){
        return facil;
    }
    entryDificultad getMedio(){
        return medio;
    }
    entryDificultad getDificil(){
        return dificil;
    }
}
