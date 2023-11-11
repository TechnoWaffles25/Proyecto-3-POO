/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto_2_kenken.PodioUtil;

/**
 *
 * @author amontero
 */
public class Podium {
    private entryDificultad actual;
    private entryDificultad facil;
    private entryDificultad medio;
    private entryDificultad dificil;
    
    void setActual(entryDificultad pActual){
        actual = pActual;
    }
    void setFacil(entryDificultad pFacil){
       facil = pFacil;
    }
    void setMedio(entryDificultad pMedio){
        medio = pMedio;
    }
    void setDificil(entryDificultad pDificil){
        medio = pDificil;
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
