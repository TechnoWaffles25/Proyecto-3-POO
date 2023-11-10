package proyecto_2_kenken;

import proyecto_2_kenken.ventanas.MenuPrincipal;
import proyecto_2_kenken.classes.*;
/**
 * clase principal del proyecto, genera una ventana inicial
 * @author Alejandro Montero, Fabricio Monge
 */
public class Proyecto_2_KenKen {
    /**
     *  crea una ventana nueva de menu principal
     * @param args sin especificación 
     */
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                MenuPrincipal gui = new MenuPrincipal(); // Create an instance of the GUI class
                gui.setVisible(true); // Make the GUI visible
            }
         });
    }
    
}
