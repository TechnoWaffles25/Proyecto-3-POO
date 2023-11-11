/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto_2_kenken.PodioUtil;

/**
 *
 * @author amontero
 */
public class entryTablero {
    private int tableroPos;
    private String size;
    private entryClass oro;
    private entryClass plata;
    private entryClass bronce;
    private entryClass newEntry;
    
    void setPosition(int pPos){
        tableroPos = pPos;
    }
    void setSize(String pSize){
        size = pSize;
    }
    void setOro(entryClass pOro){
        oro = pOro;
    }
    void setPlata(entryClass pPlata){
        plata = pPlata;
    }
    void setBronce(entryClass pBronce){
        bronce = pBronce;
    }
    void setNewEntry(entryClass pEval){
        newEntry = pEval;
    }
    int getPosition(){
        return tableroPos;
    }
    String getSize(){
        return size;
    }
    entryClass getOro(){
        return oro;
    }
    entryClass getPlata(){
        return plata;
    }
    entryClass getBronce(){
        return bronce;
    }
    entryClass getNewEntry(){
        return newEntry;
    }
    public void updatePodium(entryClass pNewEntry) {
        setNewEntry(pNewEntry);
        // Check and place the new entry in the correct position
        if (oro == null || newEntry.getDuration().compareTo(oro.getDuration()) < 0) {
            // New entry is better or equal to oro, shift oro to plata and plata to bronce
            bronce = plata;
            plata = oro;
            oro = newEntry;
        } else if (plata == null || newEntry.getDuration().compareTo(plata.getDuration()) < 0) {
            // New entry is better or equal to plata, shift plata to bronce
            bronce = plata;
            plata = newEntry;
        } else if (bronce == null || newEntry.getDuration().compareTo(bronce.getDuration()) < 0) {
            // New entry is better or equal to bronce
            bronce = newEntry;
        }

        // Clear the new entry after updating the podium
        newEntry = null;
    }
}
