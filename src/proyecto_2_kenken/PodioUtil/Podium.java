/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */ 
package proyecto_2_kenken.PodioUtil;

import java.io.Serializable;

/**
 *
 * @author amontero
 */
public class Podium implements Serializable{
    private entryDificultad actual;
    private entryDificultad facil;
    private entryDificultad medio;
    private entryDificultad dificil;
    
    public void setActual(entryDificultad pActual){
        actual = pActual;
    }
    public void setFacil(entryDificultad pFacil){
       facil = pFacil;
    }
    public void setMedio(entryDificultad pMedio){
        medio = pMedio;
    }
    public void setDificil(entryDificultad pDificil){
        medio = pDificil;
    }
    public entryDificultad getFacil(){
        return facil;
    }
    public entryDificultad getMedio(){
        return medio;
    }
    public entryDificultad getDificil(){
        return dificil;
    }
}
