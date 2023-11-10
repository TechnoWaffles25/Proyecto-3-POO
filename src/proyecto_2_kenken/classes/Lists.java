
package proyecto_2_kenken.classes;
import java.util.ArrayList;
/**
 *Clase para manejar listas durante y antes del juego
 * @author Alejandro Montero, Fabricio Monge
 */


public class Lists {
    public Nodo cabeza;
    public Nodo cola;

    public Lists() {
        this.cabeza = null;
        this.cola=null;
    }
    /**
     * agrega una jugada realizada
     * @param row fila 
     * @param col columna
     * @param numerocambiado numero que se eliminó
     */
    public void agregarJugada(int row, int col, String numerocambiado){
        Nodo newnodo= new Nodo(col,row,numerocambiado);
        if (cabeza==null || cola==null){
            cabeza=newnodo;
            cola=newnodo;
        }
        else{
            newnodo.predecesor=cola;
            cola.sucesor=newnodo;
            cola=newnodo;
        }
    }
    /**
     * elimina la lista
     */
    public void clearList() {
        cabeza = null;
        cola = null;
    }
    /**
     * deja ver la ultima jugada sin eliminarla
     * @return cola
     */
    public Nodo verUltimaJugada(){
        System.out.println("Cola: " + cola);
        return cola;    
    }
    /**
     * devuleve la última jugada realizada
     * @return ultima jugada
     */
    public Nodo popUltimaJugada() {
    if (cola == null) {
        System.out.println("Lista vacía, retornando null");
        return null;
    }
    if (cola.sucesor==null && cola.predecesor==null){
        Nodo nodo = cola;
        cola=null;
        return nodo;
    }
    else{
        Nodo nodo = cola;
        cola = cola.predecesor;
        cola.sucesor=null;
        return nodo;
        }
    }
}
