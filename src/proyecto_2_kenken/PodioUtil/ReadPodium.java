/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto_2_kenken.PodioUtil;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.time.Duration;
/**
 *
 * @author amontero
 */
public class ReadPodium {
    private PodiumData podiumData;
    
    public void readPodium() {
        try (FileInputStream fileInputStream = new FileInputStream("podio.dat");
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
       
            podiumData = (PodiumData) objectInputStream.readObject();
            System.out.println("Actual: " + podiumData.getActual());
            System.out.println("Facil: " + podiumData.getFacil());
            System.out.println("Medio: " + podiumData.getMedio());
            System.out.println("Dificil: " + podiumData.getDificil());
            
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    public entryDificultad getActual(){
        return podiumData.getActual();
    }
    public entryDificultad getFacil(){
        return podiumData.getFacil();
    }
    public entryDificultad getMedio(){
        return podiumData.getMedio();
    }
    public entryDificultad getDificil(){
        return podiumData.getDificil();
    }
}
