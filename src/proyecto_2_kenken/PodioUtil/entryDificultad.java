/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto_2_kenken.PodioUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 *
 * @author amontero
 */
public class entryDificultad {
    private String dificultad;
    private ArrayList<entryTablero> tableros;
    
    void setDificultad(String pDificultad){
       dificultad = pDificultad;
    }
    void setTableros(ArrayList<entryTablero> pTableros){
        tableros = pTableros;
    }
    
    private static void sortTableros(ArrayList<entryTablero> tableros) {
        Collections.sort(tableros, new Comparator<entryTablero>() {
            @Override
            public int compare(entryTablero t1, entryTablero t2) {
                return extractSize(t1.getSize()) - extractSize(t2.getSize());
            }

            private int extractSize(String size) {
                try {
                    return Integer.parseInt(size.split("x")[0]);
                } catch (NumberFormatException e) {
                    // Handle the exception if the size is not in the expected format
                    return Integer.MAX_VALUE; // You can return a default value or handle it as per your logic
                }
            }
        });
    }
}
