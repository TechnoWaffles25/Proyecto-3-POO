package proyecto_2_kenken.PodioUtil;

import java.io.*;
import java.time.Duration;
import java.util.ArrayList;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.IOException;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;

public class PodiumController {

    private static final String PODIUM_FILE = "podio.dat";
    private Podium podium;

    public PodiumController() {
        loadPodium();
    }

    public void updatePodium(String dificultad, String size, String playerName, Duration time) {
        entryDificultad nivelDificultad = getNivelDificultad(dificultad);
        entryTablero tablero = getTablero(nivelDificultad, size);

        entryClass newEntry = new entryClass(playerName, time);
        tablero.updatePodium(newEntry);

        savePodium(); // Guardar cambios después de actualizar
        exportPodiumToPDF("podio_kenken.pdf");
    }

    private entryDificultad getNivelDificultad(String dificultad) {
    entryDificultad nivelDificultad;
    switch (dificultad.toLowerCase()) {
        case "facil":
            nivelDificultad = podium.getFacil();
            if (nivelDificultad == null) {
                nivelDificultad = new entryDificultad();
                nivelDificultad.setDificultad("facil");
                nivelDificultad.setTableros(new ArrayList<>());
                podium.setFacil(nivelDificultad);
            }
            break;
        case "medio":
            nivelDificultad = podium.getMedio();
            if (nivelDificultad == null) {
                nivelDificultad = new entryDificultad();
                nivelDificultad.setDificultad("medio");
                nivelDificultad.setTableros(new ArrayList<>());
                podium.setMedio(nivelDificultad);
            }
            break;
        case "dificil":
            nivelDificultad = podium.getDificil();
            if (nivelDificultad == null) {
                nivelDificultad = new entryDificultad();
                nivelDificultad.setDificultad("dificil");
                nivelDificultad.setTableros(new ArrayList<>());
                podium.setDificil(nivelDificultad);
            }
            break;
        default:
            throw new IllegalArgumentException("Dificultad no reconocida: " + dificultad);
    }
    return nivelDificultad;
}



    private entryTablero getTablero(entryDificultad nivelDificultad, String size) {
        ArrayList<entryTablero> tableros = nivelDificultad.getTableros();
        for (entryTablero tablero : tableros) {
            if (tablero.getSize().equals(size)) {
                return tablero;
            }
        }
        // Si no se encuentra el tablero, crear uno nuevo y agregarlo
        entryTablero newTablero = new entryTablero();
        newTablero.setSize(size);
        tableros.add(newTablero);
        return newTablero;
    }

    private void loadPodium() {
        File file = new File(PODIUM_FILE);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                podium = (Podium) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                // Si ocurre un error al cargar, inicializa y guarda un nuevo Podium
                podium = new Podium();
                savePodium();
            }
        } else {
            podium = new Podium(); // Crear un nuevo podio si el archivo no existe
            savePodium();
        }
    }

    private void savePodium() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(PODIUM_FILE))) {
            oos.writeObject(podium);
        } catch (IOException e) {
            e.printStackTrace();
            // Manejar la excepción adecuadamente
        }
    }

    public void exportPodiumToPDF(String filePath) {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.beginText();
                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 12);
                contentStream.setLeading(14.5f);
                contentStream.newLineAtOffset(25, 700);
                contentStream.showText("PODIO GENERAL POR NIVEL Y CUADRÍCULA");
                contentStream.newLine();

                // Iterar sobre los niveles (dificultades)
                for (entryDificultad nivel : new entryDificultad[]{podium.getFacil(), podium.getMedio(), podium.getDificil()}) {
                    if (nivel != null) {
                        contentStream.showText("NIVEL " + nivel.getDificultad().toUpperCase() + ":");
                        contentStream.newLine();
                        for (entryTablero tablero : nivel.getTableros()) {
                            // Iterar sobre los tamaños de tablero
                            contentStream.showText(tablero.getSize() + ":");
                            contentStream.newLine();
                            // ORO
                            if (tablero.getOro() != null) {
                                contentStream.showText("ORO " + tablero.getOro().getPlayerName() + " " + formatDuration(tablero.getOro().getDuration()));
                                contentStream.newLine();
                            }
                            // PLATA
                            if (tablero.getPlata() != null) {
                                contentStream.showText("PLATA " + tablero.getPlata().getPlayerName() + " " + formatDuration(tablero.getPlata().getDuration()));
                                contentStream.newLine();
                            }
                            // BRONCE
                            if (tablero.getBronce() != null) {
                                contentStream.showText("BRONCE " + tablero.getBronce().getPlayerName() + " " + formatDuration(tablero.getBronce().getDuration()));
                                contentStream.newLine();
                            }
                        }
                    }
                }

                contentStream.endText();
            }

            document.save(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String formatDuration(Duration duration) {
        long hours = duration.toHours();
        long minutes = duration.toMinutesPart();
        long seconds = duration.toSecondsPart();
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}
