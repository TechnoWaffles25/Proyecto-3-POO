/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto_2_kenken.PodioUtil;

import java.time.Duration;

/**
 *
 * @author amontero
 */
public class entryClass {
    // atributos
    private String position;
    private String playerName;
    private Duration time;
    
    // setters getters
    void setPosition(String pPosition){
        position = pPosition;
    }
    void setPlayerName(String pPlayer){
        playerName = pPlayer;
    }
    void setTime(Duration pTime){
        time = pTime;
    }
    String getPosition(){
        return position;
    }
    String getPlayerName(){
        return playerName;
    }
    Duration getDuration(){
        return time;
    }
}
