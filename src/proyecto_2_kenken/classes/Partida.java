
package proyecto_2_kenken.classes;

import java.util.List;

/**
 * Clase de partida de KenKen
 * @author Alejandro Montero, Fabricio Monge
 */
public class Partida {
    private List<Cell> cells;
    /**
     * constructor
     * @param pCells lista de celdas de una partida
     */
    public Partida(List<Cell> pCells){
        cells = pCells;
    }
    /**
     * devuelve las celdas de una partida
     * @return celdas
     */
    public List<Cell> getCells(){
        return cells;
    }
}