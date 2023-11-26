/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto_2_kenken.classes;
import java.awt.Color;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import proyecto_2_kenken.SoundPlayer;
import proyecto_2_kenken.ventanas.*;
import proyecto_2_kenken.classes.*;

public class PlayUtilities {
    public static Lists listaJugadas = new Lists();
    public static Lists jugadasDeshechas = new Lists();
    
    public static void escribir(int i,int j, int valornuevo, List<List<JButton>> botones, List<Cell> lstCells, boolean btnborrarcasilla){
        if (btnborrarcasilla==true){
            for (Cell cell : lstCells){
                if (cell.getRow()== i && cell.getColumn()== j ){
                    int jtv = cell.getJailTargetValue();
                    char operation = cell.getOperation();
                    String txt = Integer.toString(jtv) + operation;
                    botones.get(i).get(j).setText(txt);
                }
            }
        }
        else if (valornuevo > 0 && valornuevo < 7){    
            String text= ""+valornuevo;
            String valoranterior=botones.get(i).get(j).getText();
            listaJugadas.agregarJugada(i,j,valoranterior);
            botones.get(i).get(j).setText(text);
            botones.get(i).get(j).setForeground(Color.black);} 
        }
    public static void deshacer(List<List<JButton>> botones){
                
        Nodo jugada = listaJugadas.popUltimaJugada();
        if (jugada !=null){
        int jRow = jugada.getRow();
        int jCol = jugada.getCol();
        String jValAnt = jugada.getValorAnterior();
        
        JButton btn = botones.get(jRow).get(jCol);
        String textPrev = btn.getText();
        
        jugadasDeshechas.agregarJugada(jRow, jCol, textPrev);
        btn.setText(jValAnt);}
        else{
            JOptionPane.showMessageDialog(null, "No se puede deshacer ninguna jugada");
            System.out.println("jugada = null, no se puede deshacer nada");
        }
    }
    public static void rehacer(List<List<JButton>> botones){
        Nodo jugada = jugadasDeshechas.popUltimaJugada();
        if (jugada != null){
        int jRow = jugada.getRow();
        int jCol = jugada.getCol();
        String jValAnt = jugada.getValorAnterior();
        
        System.out.println("Row: " + jRow);
        System.out.println("Col: " + jCol);
        System.out.println("ValAnt: " + jValAnt);
        
        JButton btn = botones.get(jRow).get(jCol);
        String txt = btn.getText();
        
        listaJugadas.agregarJugada(jRow, jCol, txt);
        btn.setText(jValAnt);}
        else{
            JOptionPane.showMessageDialog(null, "No se puede rehacer ninguna jugada");
            System.out.println("jugada = null, no se puede rehacer nada");
        }
    }
 
 }
