
package proyecto_2_kenken.classes;

import javax.swing.JButton;
/**
 * clase que almacena los datos de cada celda que se encuentra en el tablero en juego 
 * @author Alejandro Montero, Fabricio Monge
 */
public class Cell extends JButton{
    private int jailTargetValue;
    private char operation;
    private int row;
    private int col;
    private int targetValue;
    private int currentValue;
    
    /**
     * constructor de celda
     * @param pJailTargetVal el valor al que se desea llegar
     * @param pOp operacion
     * @param pRow fila
     * @param pCol columna
     * @param pVal valor que debe tener la casilla
     */
    public Cell (int pJailTargetVal, char pOp, int pRow, int pCol, int pVal){
        jailTargetValue = pJailTargetVal;
        operation = pOp;
        row = pRow;
        col = pCol;
        targetValue = pVal;
    }
    
    public int getRow(){
        return row;
    }
    public int getColumn(){
        return col;
    }
    public int getJailTargetValue(){
        return jailTargetValue;
    }
    public char getOperation(){
        return operation;
    }
    public int getTargetValue(){
        return targetValue;
    }
    public int getCurrentValue(){
        return currentValue;
    }
    @Override
    public String toString() {
        return "Cell [Row: " + row + ", Column: " + col + ", Jail Target Value: " + jailTargetValue +
               ", Operation: " + operation + ", Target Value: " + targetValue + ", Current Value: " + currentValue + "]";
    }
}
