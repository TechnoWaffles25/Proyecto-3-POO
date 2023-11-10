package proyecto_2_kenken.classes;

import java.util.List;
import java.util.List;
/**
 * clase de paritdas de kenken
 * @author Alejandro Montero, Fabricio Monge 
 */
public class KenKen {
    private List<Partida> partidas;
    /**
     * devuelve las partidas de la lista de partidas
     * @return partidas
     */
    public List<Partida> getPartidas(){
        return partidas;
    }
    /**
     * cambia las partidas actuales
     * @param pPartidas lista nueva de partidas
     */
    public void setPartidas(List<Partida> pPartidas){
       partidas = pPartidas;
    }
}
