/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto_2_kenken.classes;

/**
 * clase de nodo, utilizada para crear una nueva lista
 * @author Alejandro Montero, Fabricio Monge 
 */
public class Nodo {
    private String valoranterior;
    private int coll;
    private int row;
    public Nodo predecesor;
    public Nodo sucesor;
    /**
     * constructor
     * @param columna columna
     * @param fila fila
     * @param numerocambiado numero que se cambió al hacer la jugada 
     */
    Nodo(int columna, int fila, String numerocambiado){
        coll=columna;
        row=fila;
        valoranterior=numerocambiado;
        predecesor=null;
        sucesor=null;
    }
    public int getCol(){
        return coll;
    }
    public int getRow(){
        return row;
    }
    public String getValorAnterior(){
        return valoranterior;
    }
    @Override 
    public String toString(){
        System.out.println("Valor anterior: " + valoranterior);
        System.out.println("Columna: " + coll);
        System.out.println("Fila: " + row);
        //System.out.println("Predecesor: " + predecesor.toString());
        //System.out.println("Sucesor: " + sucesor.toString());
        return "";
    }
}
