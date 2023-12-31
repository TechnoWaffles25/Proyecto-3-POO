/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto_2_kenken.PodioUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 *
 * @author amontero
 */
public class entryDificultad implements Serializable{
    private String dificultad;
    private ArrayList<entryTablero> tableros;
    
    
    public void setDificultad(String pDificultad){
       dificultad = pDificultad;
    }
    public void setTableros(ArrayList<entryTablero> pTableros){
        tableros = pTableros;
    }
    public ArrayList<entryTablero> getTableros(){
        return tableros;
    }
    public String getDificultad(){
        return dificultad;
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
                    return Integer.MAX_VALUE;
                }
            }
        });
    }
}
