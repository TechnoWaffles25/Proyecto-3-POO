package proyecto_2_kenken;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import proyecto_2_kenken.classes.*;

/**
 * Clase que se encarga de leer el archivo XML con partidas y hace una lista de partidas
 * correspondiente a la dificultad y tamaño del tablero.
 */
public class ReadPartidaXML {
    /**
     * Crea una lista de objetos Partida, con las celdas y los valores correspondientes.
     * @param fileName nombre del archivo de partidas.
     * @param difficulty dificultad escogida.
     * @param tableroSize tamaño del tablero.
     * @return lista de partidas.
     */
    public List<Partida> parseKenKenPartidas(String fileName, String difficulty, int tableroSize) {
        try {
            File xmlFile = new File(fileName);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(xmlFile);
            
            List<Partida> listaPartidas = new ArrayList<>();
            Element root = doc.getDocumentElement();
            String nTableroSize = null;
            switch (tableroSize){
                case 3:
                    nTableroSize = "tres";
                    break;
                case 4:
                    nTableroSize = "cuatro";
                    break;
                case 5:
                    nTableroSize = "cinco";
                    break;
                case 6:
                    nTableroSize = "seis";
                    break;
                case 7:
                    nTableroSize = "siete";
                    break;
                case 8:
                    nTableroSize = "ocho";
                    break;
                case 9:
                    nTableroSize = "nueve";
                    break;
            }
            // Buscar el elemento que corresponde al tamaño del tablero
            NodeList sizeList = root.getElementsByTagName(nTableroSize);
            if (sizeList.getLength() > 0) {
                Element sizeElement = (Element) sizeList.item(0);

                // Buscar la dificultad dentro del elemento del tamaño del tablero
                NodeList difficultyList = sizeElement.getElementsByTagName(difficulty);
                if (difficultyList.getLength() > 0) {
                    Element difficultyElement = (Element) difficultyList.item(0);

                    // Buscar todas las partidas dentro de la dificultad
                    NodeList partidaList = difficultyElement.getElementsByTagName("partida");
                    for (int i = 0; i < partidaList.getLength(); i++) {
                        Element partidaElement = (Element) partidaList.item(i);

                        List<Cell> cells = new ArrayList<>();
                        NodeList cellList = partidaElement.getElementsByTagName("cell");
                        for (int j = 0; j < cellList.getLength(); j++) {
                            Element cellElement = (Element) cellList.item(j);
                            String cellData = cellElement.getTextContent();
                            String[] cellParts = cellData.split(",");

                            if (cellParts.length == 5) {
                                int jailTargetValue = Integer.parseInt(cellParts[0]);
                                char operation = cellParts[1].charAt(0);
                                int row = Integer.parseInt(cellParts[2]);
                                int col = Integer.parseInt(cellParts[3]);
                                int targetValue = Integer.parseInt(cellParts[4]);

                                Cell cell = new Cell(jailTargetValue, operation, row, col, targetValue);
                                cells.add(cell);
                            }
                        }

                        Partida partida = new Partida(cells);
                        listaPartidas.add(partida);
                    }
                }
            }
            return listaPartidas;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null; // Retornar null en caso de error o si no se encuentra ninguna partida
    }
}
