/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package proyecto_2_kenken.ventanas;

import java.awt.Color;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import proyecto_2_kenken.*;
import proyecto_2_kenken.classes.*;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import javax.swing.Icon;
import javax.swing.ImageIcon;
/**
 *
 * @author amontero
 */
public class VentanaJugar extends javax.swing.JFrame {
    public Lists listaJugadas = new Lists();
    public Lists jugadasDeshechas = new Lists();
    public List<List<JButton>> lstBotones = new ArrayList<>();
    public List<JButton> sublstBotones = new ArrayList<>();
    public List<Integer> indicesVisitados = new ArrayList<>();
    public List<Cell> lstCells = new ArrayList<>();
    public int valornuevo;
    
    public String jugador;
    public String dificultad;
    public Partida partidaActual;
    public boolean posicion;
    public boolean sonido;
    public String reloj;
    
    
    public Duration tiempoJuego;
    public Duration tiempoTranscurrido;
    public Timer timer = new Timer(true);
    public boolean rstFlag = false;
    public int indiceActual;
    
    /**
     * Creates new form VentanaJugar
     */
    public VentanaJugar() {
        initComponents();
        
        btnIniciar.setEnabled(false);
        btnTerminar.setEnabled(false);
        btnOtraPartida.setEnabled(false);
        btnRehacer.setEnabled(false);
        btnDeshacer.setEnabled(false);
        btnBorrarCasilla.setEnabled(false);
        btnReiniciar.setEnabled(false);
        btnValidar.setEnabled(false);
        
        ReadConfig readconfig = new ReadConfig();
        readconfig.readConfiguration();
        
        dificultad = readconfig.getDificultad();
        System.out.println("\nDificultad seleccionada: " + dificultad);
        lblDificultad.setText(dificultad);
        
        sonido = readconfig.getSonido();
        System.out.println("Sonido al terminar juego: " + sonido);
        
        reloj = readconfig.getReloj();
        System.out.println("Reloj seleccionado: " + reloj);
        lblReloj.setText(reloj+":");
        
        posicion = readconfig.getPosicion();
        System.out.println("Posicion de los botones (True der / False izq): " + posicion);
        
        btnsDer.setVisible(false);
        btnsIzq.setVisible(false);
        
        tiempoJuego = readconfig.getDuration();
        System.out.println("Tiempo para el timer: " + tiempoJuego);
        if (tiempoJuego == null){
            tiempoJuego = Duration.ofHours(0).plusMinutes(0).plusSeconds(0);
        }
        
        btnTerminar.setOpaque(true);
        btnTerminar.setBorderPainted(false);
        
        btnIniciar.setOpaque(true);
        btnIniciar.setBorderPainted(false);
        
        btnOtraPartida.setOpaque(true);
        btnOtraPartida.setBorderPainted(false);
        
        btnDeshacer.setOpaque(true);
        btnDeshacer.setBorderPainted(false);
        
        btnRehacer.setOpaque(true);
        btnRehacer.setBorderPainted(false);
        
        btnBorrarCasilla.setOpaque(true);
        btnBorrarCasilla.setBorderPainted(false);
        
        btnValidar.setOpaque(true);
        btnValidar.setBorderPainted(false);
        
        btnReiniciar.setOpaque(true);
        btnReiniciar.setBorderPainted(false);
        
        btnPodio.setOpaque(true);
        btnPodio.setBorderPainted(false);
        
        //Icon i = new ImageIcon("borrador.png");
        //btnBorrarCasilla.setIcon(i);
    }
    /**
     * carga un tablero con una partida
     * @param dificultad dificultad de juego
     */
    public void cargarTablero(String dificultad){
            // Creamos las celdas
            sublstBotones.add(btn11);
            sublstBotones.add(btn12);
            sublstBotones.add(btn13);
            sublstBotones.add(btn14);
            sublstBotones.add(btn15);
            sublstBotones.add(btn16);
            lstBotones.add(sublstBotones);
            sublstBotones = new ArrayList<>();

            sublstBotones.add(btn21);
            sublstBotones.add(btn22);
            sublstBotones.add(btn23);
            sublstBotones.add(btn24);
            sublstBotones.add(btn25);
            sublstBotones.add(btn26);
            lstBotones.add(sublstBotones);
            sublstBotones = new ArrayList<>();

            sublstBotones.add(btn31);
            sublstBotones.add(btn32);
            sublstBotones.add(btn33);
            sublstBotones.add(btn34);
            sublstBotones.add(btn35);
            sublstBotones.add(btn36);
            lstBotones.add(sublstBotones);
            sublstBotones = new ArrayList<>();

            sublstBotones.add(btn41);
            sublstBotones.add(btn42);
            sublstBotones.add(btn43);
            sublstBotones.add(btn44);
            sublstBotones.add(btn45);
            sublstBotones.add(btn46);
            lstBotones.add(sublstBotones);
            sublstBotones = new ArrayList<>();

            sublstBotones.add(btn51);
            sublstBotones.add(btn52);
            sublstBotones.add(btn53);
            sublstBotones.add(btn54);
            sublstBotones.add(btn55);
            sublstBotones.add(btn56);
            lstBotones.add(sublstBotones);
            sublstBotones = new ArrayList<>();

            sublstBotones.add(btn61);
            sublstBotones.add(btn62);
            sublstBotones.add(btn63);
            sublstBotones.add(btn64);
            sublstBotones.add(btn65);
            sublstBotones.add(btn66);
            lstBotones.add(sublstBotones);

            System.out.println(lstBotones);
            
            ReadPartidaXML reader = new ReadPartidaXML();
            List<Partida> listaPartidas = reader.parseKenKenPartidas("kenken_partidas3.xml", dificultad);
            lstCells = null;
            Random random = new Random();
            
            while (indicesVisitados.size()<listaPartidas.size()){
                indiceActual = random.nextInt(listaPartidas.size());
                if (!indicesVisitados.contains(indiceActual)){
                    System.out.println("Indice partida a jugar: " + indiceActual);
                    indicesVisitados.add(indiceActual);
                    Partida partida = listaPartidas.get(indiceActual);
                    lstCells = partida.getCells();
                    partidaActual = partida;
                    break;
                    
                }
            }
            if (indicesVisitados.size() == listaPartidas.size()) {
                System.out.println("Reset de partidas visitadas");
                indicesVisitados.clear();
            }
            
            int i = 1;
            for (Cell cell : lstCells){
                System.out.println(i + " " + cell.toString());
                i++;
            }
            System.out.println("////////////////////////////////////////////");

            for (Cell cell : lstCells){
                int jtv = cell.getJailTargetValue();
                char operation = cell.getOperation();
                int row = cell.getRow();
                int column = cell.getColumn();
                String txt = Integer.toString(jtv) + operation;

                switch(operation){
                    case '+':
                        System.out.println("Caso +");
                        SwingUtilities.invokeLater(() -> {
                            JButton btnP = lstBotones.get(row).get(column);
                            btnP.setBackground(Color.RED);
                            btnP.setText(txt);
                            btnP.setOpaque(true);
                        });
                        break;
                    case '-':
                        System.out.println("Caso -");
                        SwingUtilities.invokeLater(() -> {
                            JButton btnM = lstBotones.get(row).get(column);
                            btnM.setBackground(Color.BLUE);
                            btnM.setText(txt);
                            btnM.setOpaque(true);
                        });
                        break;
                    case 'x':
                        System.out.println("Caso x");
                        SwingUtilities.invokeLater(() -> {
                            JButton btnX = lstBotones.get(row).get(column);
                            btnX.setBackground(Color.ORANGE);
                            btnX.setText(txt);
                            btnX.setOpaque(true);
                        });
                        break;
                    case '/':
                        System.out.println("Caso /");
                        SwingUtilities.invokeLater(() -> {
                            JButton btnD = lstBotones.get(row).get(column);
                            btnD.setBackground(Color.GREEN);
                            btnD.setText(txt);
                            btnD.setOpaque(true);
                        });
                        break;
                    case 'n':
                        System.out.println("Caso n");
                        SwingUtilities.invokeLater(() -> {
                            JButton btnN = lstBotones.get(row).get(column);
                            btnN.setBackground(Color.WHITE);
                            btnN.setText(txt);
                            btnN.setOpaque(true);
                        });
                        break;
                } 
            } 
        }
    /**
     * resetea el tablero
     * @param dificultad dificultad de juego
     * @param indiceActual partida actual
     */
    public void resetTablero(String dificultad, int indiceActual){
            // Creamos las celdas
            sublstBotones.add(btn11);
            sublstBotones.add(btn12);
            sublstBotones.add(btn13);
            sublstBotones.add(btn14);
            sublstBotones.add(btn15);
            sublstBotones.add(btn16);
            lstBotones.add(sublstBotones);
            sublstBotones = new ArrayList<>();

            sublstBotones.add(btn21);
            sublstBotones.add(btn22);
            sublstBotones.add(btn23);
            sublstBotones.add(btn24);
            sublstBotones.add(btn25);
            sublstBotones.add(btn26);
            lstBotones.add(sublstBotones);
            sublstBotones = new ArrayList<>();

            sublstBotones.add(btn31);
            sublstBotones.add(btn32);
            sublstBotones.add(btn33);
            sublstBotones.add(btn34);
            sublstBotones.add(btn35);
            sublstBotones.add(btn36);
            lstBotones.add(sublstBotones);
            sublstBotones = new ArrayList<>();

            sublstBotones.add(btn41);
            sublstBotones.add(btn42);
            sublstBotones.add(btn43);
            sublstBotones.add(btn44);
            sublstBotones.add(btn45);
            sublstBotones.add(btn46);
            lstBotones.add(sublstBotones);
            sublstBotones = new ArrayList<>();

            sublstBotones.add(btn51);
            sublstBotones.add(btn52);
            sublstBotones.add(btn53);
            sublstBotones.add(btn54);
            sublstBotones.add(btn55);
            sublstBotones.add(btn56);
            lstBotones.add(sublstBotones);
            sublstBotones = new ArrayList<>();

            sublstBotones.add(btn61);
            sublstBotones.add(btn62);
            sublstBotones.add(btn63);
            sublstBotones.add(btn64);
            sublstBotones.add(btn65);
            sublstBotones.add(btn66);
            lstBotones.add(sublstBotones);

            System.out.println(lstBotones);
            
            ReadPartidaXML reader = new ReadPartidaXML();
            List<Partida> listaPartidas = reader.parseKenKenPartidas("kenken_partidas3.xml", dificultad);
            
            Partida partida = listaPartidas.get(indiceActual);
            lstCells = partida.getCells();
            partidaActual = partida;
            
            int i = 1;
            for (Cell cell : lstCells){
                System.out.println(i + " " + cell.toString());
                i++;
            }
            System.out.println("////////////////////////////////////////////");

            for (Cell cell : lstCells){
                int jtv = cell.getJailTargetValue();
                char operation = cell.getOperation();
                int row = cell.getRow();
                int column = cell.getColumn();
                String txt = Integer.toString(jtv) + operation;

                switch(operation){
                    case '+':
                        System.out.println("Caso +");
                        SwingUtilities.invokeLater(() -> {
                            JButton btnP = lstBotones.get(row).get(column);
                            btnP.setBackground(Color.RED);
                            btnP.setText(txt);
                            btnP.setOpaque(true);
                        });
                        break;
                    case '-':
                        System.out.println("Caso -");
                        SwingUtilities.invokeLater(() -> {
                            JButton btnM = lstBotones.get(row).get(column);
                            btnM.setBackground(Color.BLUE);
                            btnM.setText(txt);
                            btnM.setOpaque(true);
                        });
                        break;
                    case 'x':
                        System.out.println("Caso x");
                        SwingUtilities.invokeLater(() -> {
                            JButton btnX = lstBotones.get(row).get(column);
                            btnX.setBackground(Color.ORANGE);
                            btnX.setText(txt);
                            btnX.setOpaque(true);
                        });
                        break;
                    case '/':
                        System.out.println("Caso /");
                        SwingUtilities.invokeLater(() -> {
                            JButton btnD = lstBotones.get(row).get(column);
                            btnD.setBackground(Color.GREEN);
                            btnD.setText(txt);
                            btnD.setOpaque(true);
                        });
                        break;
                    case 'n':
                        System.out.println("Caso n");
                        SwingUtilities.invokeLater(() -> {
                            JButton btnN = lstBotones.get(row).get(column);
                            btnN.setBackground(Color.WHITE);
                            btnN.setText(txt);
                            btnN.setOpaque(true);
                        });
                        break;
                } 
            } 
        }
    /**
     * inicia el reloj de la partida
     */
    public void startReloj(){
        if ("Cronometro".equals(reloj)){
            System.out.println("Cronometro inicio...");

            if (timer == null) {
                timer = new Timer(true);
            }
            int pHoras = tiempoJuego.toHoursPart();
            int pMinutos = tiempoJuego.toMinutesPart();
            int pSegundos = tiempoJuego.toSecondsPart();
            
            tiempoTranscurrido = Duration.ofHours(pHoras).plusMinutes(pMinutos).plusSeconds(pSegundos);
            Instant tiempoInicio = Instant.now().minus(tiempoTranscurrido);
            
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    Instant now = Instant.now();
                    tiempoTranscurrido = Duration.between(tiempoInicio, now);
                    updateTimerLabel();
                }
            }, 0, 1000);
        }
        if ("Timer".equals(reloj)){
            System.out.println("Timer inicio...");
            
            int pHoras = tiempoJuego.toHoursPart();
            int pMinutos = tiempoJuego.toMinutesPart();
            int pSegundos = tiempoJuego.toSecondsPart();
            
            tiempoTranscurrido = Duration.ofHours(pHoras).plusMinutes(pMinutos).plusSeconds(pSegundos);
            Instant tiempoInicio = Instant.now().plus(tiempoTranscurrido);
            if (timer == null) {
            timer = new Timer(true);
            } else {
                // If the timer was previously canceled, create a new instance
                timer = new Timer(true);
            } timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    Instant now = Instant.now();
                    tiempoTranscurrido = Duration.between(now, tiempoInicio);
                    updateTimerLabel();

                    if (tiempoTranscurrido.isNegative() || tiempoTranscurrido.isZero()) {
                        int opt = JOptionPane.showConfirmDialog(rootPane, "Se acabo el tiempo! Desea continuar el juego?");
                        if (opt == JOptionPane.YES_OPTION){
                            reloj = "Cronometro";
                            lblReloj.setText("Cronometro:");
                            System.out.println("TiempoJuego: " + tiempoJuego);
                            timer.cancel();
                            timer = new Timer(true);
                            rstFlag = true;
                            startReloj();
                        } else {
                            timer.cancel();
                        }
                        
                    }
                }
            }, 0, 1000);
        } if ("No Usar".equals(reloj)){
            lblReloj.setText("");
            lblTiempo.setText("");
        }
    }
    
    private void updateTimerLabel() {
        long hours = tiempoTranscurrido.toHours();
        long minutes = tiempoTranscurrido.toMinutesPart();
        long seconds = tiempoTranscurrido.toSecondsPart();

        String tiempoStr = String.format("%02d:%02d:%02d", hours, minutes, seconds);
        lblTiempo.setText(tiempoStr);
    }
    
    private void resetTimerLabel(){
        long hours = 00;
        long minutes = 00;
        long seconds = 00;

        String tiempoStr = String.format("%02d:%02d:%02d", hours, minutes, seconds);
        lblTiempo.setText(tiempoStr);
    }
    
    private void stopTimerLabel() {                                           
        if ("Cronometro".equals(reloj)){
                System.out.println("Cronometro Stop");

                if (timer != null) {
                    timer.cancel();
                    timer = null;
                }
                resetTimerLabel();
                btnIniciar.setEnabled(true);

            } if ("Timer".equals(reloj)){
                System.out.println("Timer Stop");
                if (timer != null) {
                    timer.cancel();
                    timer = null;
                }
                resetTimerLabel();
                btnIniciar.setEnabled(true);
            }
    }
    
    private void pauseTimerLabel(){
        
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        boardPanel = new javax.swing.JPanel();
        btn11 = new javax.swing.JButton();
        btn12 = new javax.swing.JButton();
        btn13 = new javax.swing.JButton();
        btn14 = new javax.swing.JButton();
        btn15 = new javax.swing.JButton();
        btn16 = new javax.swing.JButton();
        btn21 = new javax.swing.JButton();
        btn22 = new javax.swing.JButton();
        btn23 = new javax.swing.JButton();
        btn24 = new javax.swing.JButton();
        btn25 = new javax.swing.JButton();
        btn26 = new javax.swing.JButton();
        btn31 = new javax.swing.JButton();
        btn32 = new javax.swing.JButton();
        btn33 = new javax.swing.JButton();
        btn34 = new javax.swing.JButton();
        btn35 = new javax.swing.JButton();
        btn36 = new javax.swing.JButton();
        btn41 = new javax.swing.JButton();
        btn42 = new javax.swing.JButton();
        btn43 = new javax.swing.JButton();
        btn44 = new javax.swing.JButton();
        btn45 = new javax.swing.JButton();
        btn46 = new javax.swing.JButton();
        btn51 = new javax.swing.JButton();
        btn52 = new javax.swing.JButton();
        btn53 = new javax.swing.JButton();
        btn54 = new javax.swing.JButton();
        btn55 = new javax.swing.JButton();
        btn56 = new javax.swing.JButton();
        btn61 = new javax.swing.JButton();
        btn62 = new javax.swing.JButton();
        btn64 = new javax.swing.JButton();
        btn63 = new javax.swing.JButton();
        btn65 = new javax.swing.JButton();
        btn66 = new javax.swing.JButton();
        btnsIzq = new javax.swing.JPanel();
        jToggleButton1 = new javax.swing.JToggleButton();
        jToggleButton2 = new javax.swing.JToggleButton();
        jToggleButton3 = new javax.swing.JToggleButton();
        jToggleButton4 = new javax.swing.JToggleButton();
        jToggleButton5 = new javax.swing.JToggleButton();
        jToggleButton6 = new javax.swing.JToggleButton();
        btnsDer = new javax.swing.JPanel();
        jToggleButton7 = new javax.swing.JToggleButton();
        jToggleButton8 = new javax.swing.JToggleButton();
        jToggleButton9 = new javax.swing.JToggleButton();
        jToggleButton10 = new javax.swing.JToggleButton();
        jToggleButton11 = new javax.swing.JToggleButton();
        jToggleButton12 = new javax.swing.JToggleButton();
        lblDificultad = new javax.swing.JLabel();
        lblTiempo = new javax.swing.JLabel();
        lblReloj = new javax.swing.JLabel();
        panelBts = new javax.swing.JPanel();
        btnRehacer = new javax.swing.JButton();
        btnDeshacer = new javax.swing.JButton();
        btnValidar = new javax.swing.JButton();
        btnIniciar = new javax.swing.JButton();
        btnTerminar = new javax.swing.JButton();
        btnReiniciar = new javax.swing.JButton();
        btnOtraPartida = new javax.swing.JButton();
        btnBorrarCasilla = new javax.swing.JToggleButton();
        btnPodio = new javax.swing.JButton();
        lblJugador = new javax.swing.JLabel();
        txtJugador = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(51, 51, 51));

        jLabel1.setBackground(new java.awt.Color(204, 204, 204));
        jLabel1.setFont(new java.awt.Font("Helvetica Neue", 3, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(204, 204, 204));
        jLabel1.setText("DIficultad:");

        boardPanel.setBackground(new java.awt.Color(102, 102, 102));

        btn11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn11ActionPerformed(evt);
            }
        });

        btn12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn12ActionPerformed(evt);
            }
        });

        btn13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn13ActionPerformed(evt);
            }
        });

        btn14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn14ActionPerformed(evt);
            }
        });

        btn15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn15ActionPerformed(evt);
            }
        });

        btn16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn16ActionPerformed(evt);
            }
        });

        btn21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn21ActionPerformed(evt);
            }
        });

        btn22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn22ActionPerformed(evt);
            }
        });

        btn23.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn23ActionPerformed(evt);
            }
        });

        btn24.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn24ActionPerformed(evt);
            }
        });

        btn25.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn25ActionPerformed(evt);
            }
        });

        btn26.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn26ActionPerformed(evt);
            }
        });

        btn31.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn31ActionPerformed(evt);
            }
        });

        btn32.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn32ActionPerformed(evt);
            }
        });

        btn33.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn33ActionPerformed(evt);
            }
        });

        btn34.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn34ActionPerformed(evt);
            }
        });

        btn35.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn35ActionPerformed(evt);
            }
        });

        btn36.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn36ActionPerformed(evt);
            }
        });

        btn41.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn41ActionPerformed(evt);
            }
        });

        btn42.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn42ActionPerformed(evt);
            }
        });

        btn43.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn43ActionPerformed(evt);
            }
        });

        btn44.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn44ActionPerformed(evt);
            }
        });

        btn45.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn45ActionPerformed(evt);
            }
        });

        btn46.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn46ActionPerformed(evt);
            }
        });

        btn51.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn51ActionPerformed(evt);
            }
        });

        btn52.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn52ActionPerformed(evt);
            }
        });

        btn53.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn53ActionPerformed(evt);
            }
        });

        btn54.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn54ActionPerformed(evt);
            }
        });

        btn55.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn55ActionPerformed(evt);
            }
        });

        btn56.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn56ActionPerformed(evt);
            }
        });

        btn61.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn61ActionPerformed(evt);
            }
        });

        btn62.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn62ActionPerformed(evt);
            }
        });

        btn64.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn64ActionPerformed(evt);
            }
        });

        btn63.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn63ActionPerformed(evt);
            }
        });

        btn65.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn65ActionPerformed(evt);
            }
        });

        btn66.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn66ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout boardPanelLayout = new javax.swing.GroupLayout(boardPanel);
        boardPanel.setLayout(boardPanelLayout);
        boardPanelLayout.setHorizontalGroup(
            boardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(boardPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(boardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(boardPanelLayout.createSequentialGroup()
                        .addComponent(btn11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn16))
                    .addGroup(boardPanelLayout.createSequentialGroup()
                        .addComponent(btn21)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn22)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn23)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn24)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn25)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn26))
                    .addGroup(boardPanelLayout.createSequentialGroup()
                        .addComponent(btn31)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn32)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn33)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn34)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn35)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn36))
                    .addGroup(boardPanelLayout.createSequentialGroup()
                        .addComponent(btn41)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn42)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn43)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn44)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn45)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn46))
                    .addGroup(boardPanelLayout.createSequentialGroup()
                        .addComponent(btn51)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn52)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn53)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn54)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn55)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn56))
                    .addGroup(boardPanelLayout.createSequentialGroup()
                        .addComponent(btn61)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn62)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn63)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn64)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn65)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn66)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        boardPanelLayout.setVerticalGroup(
            boardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(boardPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(boardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn11, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn12, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn13, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn14, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn15, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn16, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(boardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(boardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btn22, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btn23, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btn24, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btn25, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btn26, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btn21, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(boardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn31, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn32, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn33, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn34, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn35, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn36, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(boardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn41, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn42, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn43, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn44, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn45, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn46, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(boardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn51, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn52, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn53, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn54, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn55, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn56, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(boardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn61, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn62, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn63, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn64, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn65, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn66, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnsIzq.setBackground(new java.awt.Color(102, 102, 102));

        buttonGroup1.add(jToggleButton1);
        jToggleButton1.setText("1");
        jToggleButton1.setToolTipText("");
        jToggleButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton1ActionPerformed(evt);
            }
        });

        buttonGroup1.add(jToggleButton2);
        jToggleButton2.setText("2");
        jToggleButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton2ActionPerformed(evt);
            }
        });

        buttonGroup1.add(jToggleButton3);
        jToggleButton3.setText("3");
        jToggleButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton3ActionPerformed(evt);
            }
        });

        buttonGroup1.add(jToggleButton4);
        jToggleButton4.setText("4");
        jToggleButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton4ActionPerformed(evt);
            }
        });

        buttonGroup1.add(jToggleButton5);
        jToggleButton5.setText("5");
        jToggleButton5.setToolTipText("");
        jToggleButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton5ActionPerformed(evt);
            }
        });

        buttonGroup1.add(jToggleButton6);
        jToggleButton6.setText("6");
        jToggleButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout btnsIzqLayout = new javax.swing.GroupLayout(btnsIzq);
        btnsIzq.setLayout(btnsIzqLayout);
        btnsIzqLayout.setHorizontalGroup(
            btnsIzqLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnsIzqLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(btnsIzqLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(btnsIzqLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jToggleButton6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jToggleButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jToggleButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jToggleButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jToggleButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jToggleButton1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        btnsIzqLayout.setVerticalGroup(
            btnsIzqLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnsIzqLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jToggleButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jToggleButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jToggleButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jToggleButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jToggleButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jToggleButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnsDer.setBackground(new java.awt.Color(102, 102, 102));

        buttonGroup1.add(jToggleButton7);
        jToggleButton7.setText("1");
        jToggleButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton7ActionPerformed(evt);
            }
        });

        buttonGroup1.add(jToggleButton8);
        jToggleButton8.setText("2");
        jToggleButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton8ActionPerformed(evt);
            }
        });

        buttonGroup1.add(jToggleButton9);
        jToggleButton9.setText("3");
        jToggleButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton9ActionPerformed(evt);
            }
        });

        buttonGroup1.add(jToggleButton10);
        jToggleButton10.setText("4");
        jToggleButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton10ActionPerformed(evt);
            }
        });

        buttonGroup1.add(jToggleButton11);
        jToggleButton11.setText("5");
        jToggleButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton11ActionPerformed(evt);
            }
        });

        buttonGroup1.add(jToggleButton12);
        jToggleButton12.setText("6");
        jToggleButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton12ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout btnsDerLayout = new javax.swing.GroupLayout(btnsDer);
        btnsDer.setLayout(btnsDerLayout);
        btnsDerLayout.setHorizontalGroup(
            btnsDerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnsDerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(btnsDerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnsDerLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(btnsDerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jToggleButton8, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jToggleButton9, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addGroup(btnsDerLayout.createSequentialGroup()
                        .addGroup(btnsDerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jToggleButton7)
                            .addComponent(jToggleButton12)
                            .addComponent(jToggleButton10)
                            .addComponent(jToggleButton11))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        btnsDerLayout.setVerticalGroup(
            btnsDerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnsDerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jToggleButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jToggleButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jToggleButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jToggleButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jToggleButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jToggleButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        lblDificultad.setFont(new java.awt.Font("Helvetica Neue", 3, 24)); // NOI18N
        lblDificultad.setForeground(new java.awt.Color(255, 255, 255));
        lblDificultad.setText("Test");

        lblTiempo.setFont(new java.awt.Font("Helvetica Neue", 3, 24)); // NOI18N
        lblTiempo.setForeground(new java.awt.Color(255, 255, 255));
        lblTiempo.setText("00:00:00");

        lblReloj.setBackground(new java.awt.Color(204, 204, 204));
        lblReloj.setFont(new java.awt.Font("Helvetica Neue", 3, 24)); // NOI18N
        lblReloj.setForeground(new java.awt.Color(204, 204, 204));
        lblReloj.setText("Cronometro:");

        panelBts.setBackground(new java.awt.Color(102, 102, 102));

        btnRehacer.setBackground(new java.awt.Color(102, 204, 255));
        btnRehacer.setForeground(new java.awt.Color(255, 255, 255));
        btnRehacer.setText("REHACER");
        btnRehacer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRehacerActionPerformed(evt);
            }
        });

        btnDeshacer.setBackground(new java.awt.Color(255, 153, 0));
        btnDeshacer.setForeground(new java.awt.Color(255, 255, 255));
        btnDeshacer.setText("DESHACER");
        btnDeshacer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeshacerActionPerformed(evt);
            }
        });

        btnValidar.setBackground(new java.awt.Color(153, 255, 204));
        btnValidar.setText("VALIDAR JUEGO");
        btnValidar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnValidarActionPerformed(evt);
            }
        });

        btnIniciar.setBackground(new java.awt.Color(204, 255, 102));
        btnIniciar.setText("INICIAR JUEGO");
        btnIniciar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIniciarActionPerformed(evt);
            }
        });

        btnTerminar.setBackground(new java.awt.Color(255, 51, 51));
        btnTerminar.setText("TERMINAR JUEGO");
        btnTerminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTerminarActionPerformed(evt);
            }
        });

        btnReiniciar.setBackground(new java.awt.Color(0, 0, 0));
        btnReiniciar.setForeground(new java.awt.Color(255, 255, 255));
        btnReiniciar.setText("REINICIAR");
        btnReiniciar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReiniciarActionPerformed(evt);
            }
        });

        btnOtraPartida.setBackground(new java.awt.Color(204, 153, 255));
        btnOtraPartida.setText("OTRO JUEGO");
        btnOtraPartida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOtraPartidaActionPerformed(evt);
            }
        });

        buttonGroup1.add(btnBorrarCasilla);
        btnBorrarCasilla.setText("BORRAR CASILLA");
        btnBorrarCasilla.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBorrarCasillaActionPerformed(evt);
            }
        });

        btnPodio.setBackground(new java.awt.Color(255, 204, 51));
        btnPodio.setText("PODIO");
        btnPodio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPodioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelBtsLayout = new javax.swing.GroupLayout(panelBts);
        panelBts.setLayout(panelBtsLayout);
        panelBtsLayout.setHorizontalGroup(
            panelBtsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBtsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelBtsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelBtsLayout.createSequentialGroup()
                        .addComponent(btnBorrarCasilla, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnValidar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelBtsLayout.createSequentialGroup()
                        .addComponent(btnRehacer, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnIniciar, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelBtsLayout.createSequentialGroup()
                        .addComponent(btnDeshacer, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnTerminar, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelBtsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnReiniciar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnOtraPartida, javax.swing.GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE)
                    .addComponent(btnPodio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelBtsLayout.setVerticalGroup(
            panelBtsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBtsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelBtsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelBtsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnIniciar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnOtraPartida, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnRehacer, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelBtsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelBtsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnReiniciar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnTerminar, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnDeshacer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelBtsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panelBtsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnBorrarCasilla, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnValidar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnPodio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(14, 14, 14))
        );

        lblJugador.setBackground(new java.awt.Color(204, 204, 204));
        lblJugador.setFont(new java.awt.Font("Helvetica Neue", 3, 24)); // NOI18N
        lblJugador.setForeground(new java.awt.Color(204, 204, 204));
        lblJugador.setText("Jugador:");

        txtJugador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtJugadorActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(136, 136, 136)
                        .addComponent(btnsIzq, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblJugador)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtJugador, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(108, 108, 108)
                                .addComponent(lblReloj)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblTiempo))
                            .addComponent(boardPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(24, 24, 24)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(155, 155, 155)
                                .addComponent(btnsDer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(24, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblDificultad)
                                .addContainerGap())))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(panelBts, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(lblDificultad, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblTiempo)
                            .addComponent(lblReloj)
                            .addComponent(lblJugador)
                            .addComponent(txtJugador, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(boardPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnsDer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(79, 79, 79)
                        .addComponent(btnsIzq, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelBts, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(59, 59, 59))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
/**
 * cambia el valor que se va a asignar en la casilla
 * @param evt click
 */
    private void jToggleButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton9ActionPerformed
    String nombre = jToggleButton9.getText();
    int num=Integer.parseInt(nombre);
    valornuevo = num;     // TODO add your handling code here:
    }//GEN-LAST:event_jToggleButton9ActionPerformed
/**
 * cambia el valor que se va a asignar en la casilla
 * @param evt click
 */
    private void jToggleButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton1ActionPerformed
    String nombre = jToggleButton1.getText();
    int num=Integer.parseInt(nombre);
    valornuevo = num;  // TODO add your handling code here:
    }//GEN-LAST:event_jToggleButton1ActionPerformed
/**
 * cambia el valor que se va a asignar en la casilla
 * @param evt click
 */
    private void jToggleButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton7ActionPerformed
    String nombre = jToggleButton7.getText();
    int num=Integer.parseInt(nombre);
    valornuevo = num; // TODO add your handling code here:
    }//GEN-LAST:event_jToggleButton7ActionPerformed
/**
 * cambia el valor que se va a asignar en la casilla
 * @param evt click
 */
    private void jToggleButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton8ActionPerformed
    String nombre = jToggleButton8.getText();
    int num=Integer.parseInt(nombre);
    valornuevo = num; // TODO add your handling code here:
    }//GEN-LAST:event_jToggleButton8ActionPerformed
/**
 * cambia el valor que se va a asignar en la casilla
 * @param evt click
 */
    private void jToggleButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton10ActionPerformed
    String nombre = jToggleButton10.getText();
    int num=Integer.parseInt(nombre);
    valornuevo = num;     // TODO add your handling code here:
    }//GEN-LAST:event_jToggleButton10ActionPerformed
/**
 * cambia el valor que se va a asignar en la casilla
 * @param evt click
 */
    private void jToggleButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton11ActionPerformed
    String nombre = jToggleButton11.getText();
    int num=Integer.parseInt(nombre);
    valornuevo = num; // TODO add your handling code here:
    }//GEN-LAST:event_jToggleButton11ActionPerformed
/**
 * cambia el valor que se va a asignar en la casilla
 * @param evt click
 */
    private void jToggleButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton12ActionPerformed
    String nombre = jToggleButton12.getText();
    int num=Integer.parseInt(nombre);
    valornuevo = num;  // TODO add your handling code here:
    }//GEN-LAST:event_jToggleButton12ActionPerformed
/**
 * cambia el valor que se va a asignar en la casilla
 * @param evt click
 */
    private void jToggleButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton2ActionPerformed
     String nombre = jToggleButton2.getText();
    int num=Integer.parseInt(nombre);
    valornuevo = num;// TODO add your handling code here:
    }//GEN-LAST:event_jToggleButton2ActionPerformed
/**
 * cambia el valor que se va a asignar en la casilla
 * @param evt click
 */
    private void jToggleButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton3ActionPerformed
    String nombre = jToggleButton3.getText();
    int num=Integer.parseInt(nombre);
    valornuevo = num; // TODO add your handling code here:
    }//GEN-LAST:event_jToggleButton3ActionPerformed
/**
 * cambia el valor que se va a asignar en la casilla
 * @param evt click
 */
    private void jToggleButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton4ActionPerformed
     String nombre = jToggleButton4.getText();
    int num=Integer.parseInt(nombre);
    valornuevo = num; // TODO add your handling code here:
    }//GEN-LAST:event_jToggleButton4ActionPerformed
/**
 * cambia el valor que se va a asignar en la casilla
 * @param evt click
 */
    private void jToggleButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton5ActionPerformed
    String nombre = jToggleButton5.getText();
    int num=Integer.parseInt(nombre);
    valornuevo = num;  // TODO add your handling code here:
    }//GEN-LAST:event_jToggleButton5ActionPerformed
/**
 * cambia el valor que se va a asignar en la casilla
 * @param evt click
 */
    private void jToggleButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton6ActionPerformed
    String nombre = jToggleButton6.getText();
    int num=Integer.parseInt(nombre);
    valornuevo = num;  // TODO add your handling code here:
    }//GEN-LAST:event_jToggleButton6ActionPerformed
/**
 * logica utilizada para interactuar con los botones dependiendo del caso 
 * @param evt  click
 */
    private void btn11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn11ActionPerformed
    if (valornuevo > 0 && valornuevo < 7){    
        String text= ""+valornuevo;
        String valoranterior=btn11.getText();
        listaJugadas.agregarJugada(0,0,valoranterior);
        btn11.setText(text);
        btn11.setForeground(Color.black);} 
    if (btnBorrarCasilla.isSelected()){
        for (Cell cell : lstCells){
           if (cell.getRow()== 0 && cell.getColumn()== 0 ){
                int jtv = cell.getJailTargetValue();
                char operation = cell.getOperation();
                String txt = Integer.toString(jtv) + operation;
                btn11.setText(txt);
           }
        }
    }
    }//GEN-LAST:event_btn11ActionPerformed
/**
 * logica utilizada para interactuar con los botones dependiendo del caso 
 * @param evt  click
 */
    private void btn12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn12ActionPerformed
    if (valornuevo > 0 && valornuevo < 7){    
        String text= ""+valornuevo;
        String valoranterior=btn12.getText();
        listaJugadas.agregarJugada(0,1,valoranterior);
        btn12.setText(text);
        btn12.setForeground(Color.black);} 
    if (btnBorrarCasilla.isSelected()){
        for (Cell cell : lstCells){
           if (cell.getRow()== 0 && cell.getColumn()== 1 ){
                int jtv = cell.getJailTargetValue();
                char operation = cell.getOperation();
                String txt = Integer.toString(jtv) + operation;
                btn12.setText(txt);
           }
        }
    }
    }//GEN-LAST:event_btn12ActionPerformed
/**
 * logica utilizada para interactuar con los botones dependiendo del caso 
 * @param evt  click
 */
    private void btn13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn13ActionPerformed
    if (valornuevo > 0 && valornuevo < 7){    
        String text= ""+valornuevo;
        String valoranterior=btn13.getText();
        listaJugadas.agregarJugada(0,2,valoranterior);
        btn13.setText(text);
        btn13.setForeground(Color.black);}     
    if (btnBorrarCasilla.isSelected()){
        for (Cell cell : lstCells){
           if (cell.getRow()== 0 && cell.getColumn()== 2 ){
                int jtv = cell.getJailTargetValue();
                char operation = cell.getOperation();
                String txt = Integer.toString(jtv) + operation;
                btn13.setText(txt);
           }
        }
    }
    }//GEN-LAST:event_btn13ActionPerformed
/**
 * logica utilizada para interactuar con los botones dependiendo del caso 
 * @param evt  click
 */
    private void btn14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn14ActionPerformed
    if (valornuevo > 0 && valornuevo < 7){    
        String text= ""+valornuevo;
        String valoranterior=btn14.getText();
        listaJugadas.agregarJugada(0,3,valoranterior);
        btn14.setText(text);
        btn14.setForeground(Color.black);}   
    if (btnBorrarCasilla.isSelected()){
        for (Cell cell : lstCells){
           if (cell.getRow()== 0 && cell.getColumn()== 3 ){
                int jtv = cell.getJailTargetValue();
                char operation = cell.getOperation();
                String txt = Integer.toString(jtv) + operation;
                btn14.setText(txt);
           }
        }
    }
    }//GEN-LAST:event_btn14ActionPerformed
/**
 * logica utilizada para interactuar con los botones dependiendo del caso 
 * @param evt  click
 */
    private void btn15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn15ActionPerformed
    if (valornuevo > 0 && valornuevo < 7){    
        String text= ""+valornuevo;
        String valoranterior=btn15.getText();
        listaJugadas.agregarJugada(0,4,valoranterior);
        btn15.setText(text);
        btn15.setForeground(Color.black);}  
    if (btnBorrarCasilla.isSelected()){
        for (Cell cell : lstCells){
           if (cell.getRow()== 0 && cell.getColumn()== 4 ){
                int jtv = cell.getJailTargetValue();
                char operation = cell.getOperation();
                String txt = Integer.toString(jtv) + operation;
                btn15.setText(txt);
           }
        }
    }
    }//GEN-LAST:event_btn15ActionPerformed
/**
 * logica utilizada para interactuar con los botones dependiendo del caso 
 * @param evt  click
 */
    private void btn16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn16ActionPerformed
    if (valornuevo > 0 && valornuevo < 7){    
        String text= ""+valornuevo;
        String valoranterior=btn16.getText();
        listaJugadas.agregarJugada(0,5,valoranterior);
        btn16.setText(text);
        btn16.setForeground(Color.black);} 
    
    if (btnBorrarCasilla.isSelected()){
        for (Cell cell : lstCells){
           if (cell.getRow()== 0 && cell.getColumn()== 5 ){
                int jtv = cell.getJailTargetValue();
                char operation = cell.getOperation();
                String txt = Integer.toString(jtv) + operation;
                btn16.setText(txt);
           }
        }
    }
    }//GEN-LAST:event_btn16ActionPerformed
/**
 * logica utilizada para interactuar con los botones dependiendo del caso 
 * @param evt  click
 */
    private void btn21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn21ActionPerformed
     if (valornuevo > 0 && valornuevo < 7){    
        String text= ""+valornuevo;
        String valoranterior=btn21.getText();
        listaJugadas.agregarJugada(1,0,valoranterior);
        btn21.setText(text);
        btn21.setForeground(Color.black);}  
     if (btnBorrarCasilla.isSelected()){
        for (Cell cell : lstCells){
           if (cell.getRow()== 1 && cell.getColumn()== 0 ){
                int jtv = cell.getJailTargetValue();
                char operation = cell.getOperation();
                String txt = Integer.toString(jtv) + operation;
                btn21.setText(txt);
           }
        }
    }
    }//GEN-LAST:event_btn21ActionPerformed
/**
 * logica utilizada para interactuar con los botones dependiendo del caso 
 * @param evt  click
 */
    private void btn22ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn22ActionPerformed
     if (valornuevo > 0 && valornuevo < 7){    
        String text= ""+valornuevo;
        String valoranterior=btn22.getText();
        listaJugadas.agregarJugada(1,1,valoranterior);
        btn22.setText(text);
        btn22.setForeground(Color.black);}
      if (btnBorrarCasilla.isSelected()){
        for (Cell cell : lstCells){
           if (cell.getRow()== 1 && cell.getColumn()== 1 ){
                int jtv = cell.getJailTargetValue();
                char operation = cell.getOperation();
                String txt = Integer.toString(jtv) + operation;
                btn22.setText(txt);
           }
        }}
    }//GEN-LAST:event_btn22ActionPerformed
/**
 * logica utilizada para interactuar con los botones dependiendo del caso 
 * @param evt  click
 */
    private void btn23ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn23ActionPerformed
    if (valornuevo > 0 && valornuevo < 7){    
        String text= ""+valornuevo;
        String valoranterior=btn23.getText();
        listaJugadas.agregarJugada(1,2,valoranterior);
        btn23.setText(text);
        btn23.setForeground(Color.black);}   
     if (btnBorrarCasilla.isSelected()){
        for (Cell cell : lstCells){
           if (cell.getRow()== 1 && cell.getColumn()== 2 ){
                int jtv = cell.getJailTargetValue();
                char operation = cell.getOperation();
                String txt = Integer.toString(jtv) + operation;
                btn23.setText(txt);
           }
        }}
    }//GEN-LAST:event_btn23ActionPerformed
/**
 * logica utilizada para interactuar con los botones dependiendo del caso 
 * @param evt  click
 */
    private void btn24ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn24ActionPerformed
     if (valornuevo > 0 && valornuevo < 7){    
        String text= ""+valornuevo;
        String valoranterior=btn24.getText();
        listaJugadas.agregarJugada(1,3,valoranterior);
        btn24.setText(text);
        btn24.setForeground(Color.black);}    
      if (btnBorrarCasilla.isSelected()){
        for (Cell cell : lstCells){
           if (cell.getRow()== 1 && cell.getColumn()== 3 ){
                int jtv = cell.getJailTargetValue();
                char operation = cell.getOperation();
                String txt = Integer.toString(jtv) + operation;
                btn24.setText(txt);
           }
        }}
    }//GEN-LAST:event_btn24ActionPerformed
/**
 * logica utilizada para interactuar con los botones dependiendo del caso 
 * @param evt  click
 */
    private void btn25ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn25ActionPerformed
    if (valornuevo > 0 && valornuevo < 7){    
        String text= ""+valornuevo;
        String valoranterior=btn25.getText();
        listaJugadas.agregarJugada(1,4,valoranterior);
        btn25.setText(text);
        btn25.setForeground(Color.black);}  
     if (btnBorrarCasilla.isSelected()){
        for (Cell cell : lstCells){
           if (cell.getRow()== 1 && cell.getColumn()== 4 ){
                int jtv = cell.getJailTargetValue();
                char operation = cell.getOperation();
                String txt = Integer.toString(jtv) + operation;
                btn25.setText(txt);
           }
        }}
    }//GEN-LAST:event_btn25ActionPerformed
/**
 * logica utilizada para interactuar con los botones dependiendo del caso 
 * @param evt  click
 */
    private void btn26ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn26ActionPerformed
    if (valornuevo > 0 && valornuevo < 7){    
        String text= ""+valornuevo;
        String valoranterior=btn26.getText();
        listaJugadas.agregarJugada(1,5,valoranterior);
        btn26.setText(text);
        btn26.setForeground(Color.black);} 
     if (btnBorrarCasilla.isSelected()){
        for (Cell cell : lstCells){
           if (cell.getRow()== 1 && cell.getColumn()== 5 ){
                int jtv = cell.getJailTargetValue();
                char operation = cell.getOperation();
                String txt = Integer.toString(jtv) + operation;
                btn26.setText(txt);
           }
        }}
    }//GEN-LAST:event_btn26ActionPerformed
/**
 * logica utilizada para interactuar con los botones dependiendo del caso 
 * @param evt  click
 */
    private void btn31ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn31ActionPerformed
    if (valornuevo > 0 && valornuevo < 7){    
        String text= ""+valornuevo;
        String valoranterior=btn31.getText();
        listaJugadas.agregarJugada(2,0,valoranterior);
        btn31.setText(text);
        btn31.setForeground(Color.black);} 
     if (btnBorrarCasilla.isSelected()){
        for (Cell cell : lstCells){
           if (cell.getRow()== 2 && cell.getColumn()== 0 ){
                int jtv = cell.getJailTargetValue();
                char operation = cell.getOperation();
                String txt = Integer.toString(jtv) + operation;
                btn31.setText(txt);
           }
        }}
    }//GEN-LAST:event_btn31ActionPerformed
/**
 * logica utilizada para interactuar con los botones dependiendo del caso 
 * @param evt  click
 */
    private void btn32ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn32ActionPerformed
     if (valornuevo > 0 && valornuevo < 7){    
        String text= ""+valornuevo;
       String valoranterior=btn32.getText();
        listaJugadas.agregarJugada(2,1,valoranterior);
        btn32.setText(text);
        btn32.setForeground(Color.black);}  
     if (btnBorrarCasilla.isSelected()){
        for (Cell cell : lstCells){
           if (cell.getRow()== 2&& cell.getColumn()== 1 ){
                int jtv = cell.getJailTargetValue();
                char operation = cell.getOperation();
                String txt = Integer.toString(jtv) + operation;
                btn32.setText(txt);
           }
        }}
    }//GEN-LAST:event_btn32ActionPerformed
/**
 * logica utilizada para interactuar con los botones dependiendo del caso 
 * @param evt  click
 */
    private void btn33ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn33ActionPerformed
    if (valornuevo > 0 && valornuevo < 7){    
        String text= ""+valornuevo;
        String valoranterior=btn33.getText();
        listaJugadas.agregarJugada(2,2,valoranterior);
        btn33.setText(text);
        btn33.setForeground(Color.black);} 
    if (btnBorrarCasilla.isSelected()){
        for (Cell cell : lstCells){
           if (cell.getRow()== 2 && cell.getColumn()== 2 ){
                int jtv = cell.getJailTargetValue();
                char operation = cell.getOperation();
                String txt = Integer.toString(jtv) + operation;
                btn33.setText(txt);
           }
        }}
    }//GEN-LAST:event_btn33ActionPerformed
/**
 * logica utilizada para interactuar con los botones dependiendo del caso 
 * @param evt  click
 */
    private void btn34ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn34ActionPerformed
    if (valornuevo > 0 && valornuevo < 7){    
        String text= ""+valornuevo;
        String valoranterior=btn34.getText();
        listaJugadas.agregarJugada(2,3,valoranterior);
        btn34.setText(text);
        btn34.setForeground(Color.black);}
    if (btnBorrarCasilla.isSelected()){
        for (Cell cell : lstCells){
           if (cell.getRow()== 2 && cell.getColumn()== 3 ){
                int jtv = cell.getJailTargetValue();
                char operation = cell.getOperation();
                String txt = Integer.toString(jtv) + operation;
                btn34.setText(txt);
           }
        }}
    }//GEN-LAST:event_btn34ActionPerformed
/**
 * logica utilizada para interactuar con los botones dependiendo del caso 
 * @param evt  click
 */
    private void btn35ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn35ActionPerformed
     if (valornuevo > 0 && valornuevo < 7){    
        String text= ""+valornuevo;
        String valoranterior=btn35.getText();
        listaJugadas.agregarJugada(2,4,valoranterior);
        btn35.setText(text);
        btn35.setForeground(Color.black);}
     if (btnBorrarCasilla.isSelected()){
        for (Cell cell : lstCells){
           if (cell.getRow()== 2 && cell.getColumn()== 4 ){
                int jtv = cell.getJailTargetValue();
                char operation = cell.getOperation();
                String txt = Integer.toString(jtv) + operation;
                btn35.setText(txt);
           }
        }}
    }//GEN-LAST:event_btn35ActionPerformed
/**
 * logica utilizada para interactuar con los botones dependiendo del caso 
 * @param evt  click
 */
    private void btn36ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn36ActionPerformed
     if (valornuevo > 0 && valornuevo < 7){    
        String text= ""+valornuevo;
        String valoranterior=btn36.getText();
        listaJugadas.agregarJugada(2,5,valoranterior);
        btn36.setText(text);
        btn36.setForeground(Color.black);}
     if (btnBorrarCasilla.isSelected()){
        for (Cell cell : lstCells){
           if (cell.getRow()== 2 && cell.getColumn()== 5 ){
                int jtv = cell.getJailTargetValue();
                char operation = cell.getOperation();
                String txt = Integer.toString(jtv) + operation;
                btn36.setText(txt);
           }
        }}
    }//GEN-LAST:event_btn36ActionPerformed
/**
 * logica utilizada para interactuar con los botones dependiendo del caso 
 * @param evt  click
 */
    private void btn41ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn41ActionPerformed
    if (valornuevo > 0 && valornuevo < 7){    
        String text= ""+valornuevo;
        String valoranterior=btn41.getText();
        listaJugadas.agregarJugada(3,0,valoranterior);
        btn41.setText(text);
        btn41.setForeground(Color.black);}   
    if (btnBorrarCasilla.isSelected()){
        for (Cell cell : lstCells){
           if (cell.getRow()== 3 && cell.getColumn()== 0 ){
                int jtv = cell.getJailTargetValue();
                char operation = cell.getOperation();
                String txt = Integer.toString(jtv) + operation;
                btn41.setText(txt);
           }
        }}
    }//GEN-LAST:event_btn41ActionPerformed
/**
 * logica utilizada para interactuar con los botones dependiendo del caso 
 * @param evt  click
 */
    private void btn42ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn42ActionPerformed
    if (valornuevo > 0 && valornuevo < 7){    
        String text= ""+valornuevo;
        String valoranterior=btn42.getText();
        listaJugadas.agregarJugada(3,1,valoranterior);
        btn42.setText(text);
        btn42.setForeground(Color.black);}   
    if (btnBorrarCasilla.isSelected()){
        for (Cell cell : lstCells){
           if (cell.getRow()== 3 && cell.getColumn()== 1 ){
                int jtv = cell.getJailTargetValue();
                char operation = cell.getOperation();
                String txt = Integer.toString(jtv) + operation;
                btn42.setText(txt);
           }
        }}
    }//GEN-LAST:event_btn42ActionPerformed
/**
 * logica utilizada para interactuar con los botones dependiendo del caso 
 * @param evt  click
 */
    private void btn43ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn43ActionPerformed
    if (valornuevo > 0 && valornuevo < 7){    
        String text= ""+valornuevo;
        String valoranterior=btn43.getText();
        listaJugadas.agregarJugada(3,2,valoranterior);
        btn43.setText(text);
        btn43.setForeground(Color.black);} 
    if (btnBorrarCasilla.isSelected()){
        for (Cell cell : lstCells){
           if (cell.getRow()== 3 && cell.getColumn()== 2 ){
                int jtv = cell.getJailTargetValue();
                char operation = cell.getOperation();
                String txt = Integer.toString(jtv) + operation;
                btn43.setText(txt);
           }
        }}
    }//GEN-LAST:event_btn43ActionPerformed
/**
 * logica utilizada para interactuar con los botones dependiendo del caso 
 * @param evt  click
 */
    private void btn44ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn44ActionPerformed
     if (valornuevo > 0 && valornuevo < 7){    
        String text= ""+valornuevo;
        String valoranterior=btn44.getText();
        listaJugadas.agregarJugada(3,3,valoranterior);
        btn44.setText(text);
        btn44.setForeground(Color.black);} 
     if (btnBorrarCasilla.isSelected()){
        for (Cell cell : lstCells){
           if (cell.getRow()== 3 && cell.getColumn()== 3 ){
                int jtv = cell.getJailTargetValue();
                char operation = cell.getOperation();
                String txt = Integer.toString(jtv) + operation;
                btn44.setText(txt);
           }
        }}
    }//GEN-LAST:event_btn44ActionPerformed
/**
 * logica utilizada para interactuar con los botones dependiendo del caso 
 * @param evt  click
 */
    private void btn45ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn45ActionPerformed
    if (valornuevo > 0 && valornuevo < 7){    
        String text= ""+valornuevo;
       String valoranterior=btn45.getText();
        listaJugadas.agregarJugada(3,4,valoranterior);
        btn45.setText(text);
        btn45.setForeground(Color.black);}
if (btnBorrarCasilla.isSelected()){
        for (Cell cell : lstCells){
           if (cell.getRow()== 3 && cell.getColumn()== 4 ){
                int jtv = cell.getJailTargetValue();
                char operation = cell.getOperation();
                String txt = Integer.toString(jtv) + operation;
                btn45.setText(txt);
           }
        }}    
    }//GEN-LAST:event_btn45ActionPerformed
/**
 * logica utilizada para interactuar con los botones dependiendo del caso 
 * @param evt  click
 */
    private void btn46ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn46ActionPerformed
    if (valornuevo > 0 && valornuevo < 7){    
        String text= ""+valornuevo;
        String valoranterior=btn46.getText();
        listaJugadas.agregarJugada(3,5,valoranterior);
        btn46.setText(text);
        btn46.setForeground(Color.black);}    
    if (btnBorrarCasilla.isSelected()){
        for (Cell cell : lstCells){
           if (cell.getRow()== 3 && cell.getColumn()== 5 ){
                int jtv = cell.getJailTargetValue();
                char operation = cell.getOperation();
                String txt = Integer.toString(jtv) + operation;
                btn46.setText(txt);
           }
        }}
    }//GEN-LAST:event_btn46ActionPerformed
/**
 * logica utilizada para interactuar con los botones dependiendo del caso 
 * @param evt  click
 */
    private void btn51ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn51ActionPerformed
    if (valornuevo > 0 && valornuevo < 7){    
        String text= ""+valornuevo;
        String valoranterior=btn51.getText();
        listaJugadas.agregarJugada(4,0,valoranterior);
        btn51.setText(text);
        btn51.setForeground(Color.black);}  
    if (btnBorrarCasilla.isSelected()){
        for (Cell cell : lstCells){
           if (cell.getRow()== 4 && cell.getColumn()== 0 ){
                int jtv = cell.getJailTargetValue();
                char operation = cell.getOperation();
                String txt = Integer.toString(jtv) + operation;
                btn51.setText(txt);
           }
        }}
    }//GEN-LAST:event_btn51ActionPerformed
/**
 * logica utilizada para interactuar con los botones dependiendo del caso 
 * @param evt  click
 */
    private void btn52ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn52ActionPerformed
    if (valornuevo > 0 && valornuevo < 7){    
        String text= ""+valornuevo;
        String valoranterior=btn52.getText();
        listaJugadas.agregarJugada(4,1,valoranterior);
        btn52.setText(text);
        btn52.setForeground(Color.black);}  
    if (btnBorrarCasilla.isSelected()){
        for (Cell cell : lstCells){
           if (cell.getRow()== 4 && cell.getColumn()== 1 ){
                int jtv = cell.getJailTargetValue();
                char operation = cell.getOperation();
                String txt = Integer.toString(jtv) + operation;
                btn52.setText(txt);
           }
        }}
    }//GEN-LAST:event_btn52ActionPerformed
/**
 * logica utilizada para interactuar con los botones dependiendo del caso 
 * @param evt  click
 */
    private void btn53ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn53ActionPerformed
    if (valornuevo > 0 && valornuevo < 7){    
        String text= ""+valornuevo;
        String valoranterior=btn53.getText();
        listaJugadas.agregarJugada(4,2,valoranterior);
        btn53.setText(text);
        btn53.setForeground(Color.black);}
    if (btnBorrarCasilla.isSelected()){
        for (Cell cell : lstCells){
           if (cell.getRow()== 4 && cell.getColumn()== 2 ){
                int jtv = cell.getJailTargetValue();
                char operation = cell.getOperation();
                String txt = Integer.toString(jtv) + operation;
                btn53.setText(txt);
           }
        }}
    }//GEN-LAST:event_btn53ActionPerformed
/**
 * logica utilizada para interactuar con los botones dependiendo del caso 
 * @param evt  click
 */
    private void btn54ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn54ActionPerformed
    if (valornuevo > 0 && valornuevo < 7){    
        String text= ""+valornuevo;
        String valoranterior=btn54.getText();
        listaJugadas.agregarJugada(4,3,valoranterior);
        btn54.setText(text);
        btn54.setForeground(Color.black);} 
    if (btnBorrarCasilla.isSelected()){
        for (Cell cell : lstCells){
           if (cell.getRow()== 4 && cell.getColumn()== 3 ){
                int jtv = cell.getJailTargetValue();
                char operation = cell.getOperation();
                String txt = Integer.toString(jtv) + operation;
                btn54.setText(txt);
           }
        }}
    }//GEN-LAST:event_btn54ActionPerformed
/**
 * logica utilizada para interactuar con los botones dependiendo del caso 
 * @param evt  click
 */
    private void btn55ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn55ActionPerformed
    if (valornuevo > 0 && valornuevo < 7){    
        String text= ""+valornuevo;
        String valoranterior=btn55.getText();
        listaJugadas.agregarJugada(4,4,valoranterior);
        btn55.setText(text);
        btn55.setForeground(Color.black);}  
    if (btnBorrarCasilla.isSelected()){
        for (Cell cell : lstCells){
           if (cell.getRow()== 4 && cell.getColumn()== 4 ){
                int jtv = cell.getJailTargetValue();
                char operation = cell.getOperation();
                String txt = Integer.toString(jtv) + operation;
                btn55.setText(txt);
           }
        }}
    }//GEN-LAST:event_btn55ActionPerformed
/**
 * logica utilizada para interactuar con los botones dependiendo del caso 
 * @param evt  click
 */
    private void btn56ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn56ActionPerformed
    if (valornuevo > 0 && valornuevo < 7){    
        String text= ""+valornuevo;
        String valoranterior=btn56.getText();
        listaJugadas.agregarJugada(4,5,valoranterior);
        btn56.setText(text);
        btn56.setForeground(Color.black);}  
    if (btnBorrarCasilla.isSelected()){
        for (Cell cell : lstCells){
           if (cell.getRow()== 4 && cell.getColumn()== 5 ){
                int jtv = cell.getJailTargetValue();
                char operation = cell.getOperation();
                String txt = Integer.toString(jtv) + operation;
                btn56.setText(txt);
           }
        }}
    }//GEN-LAST:event_btn56ActionPerformed
/**
 * logica utilizada para interactuar con los botones dependiendo del caso 
 * @param evt  click
 */
    private void btn61ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn61ActionPerformed
    if (valornuevo > 0 && valornuevo < 7){    
        String text= ""+valornuevo;
        String valoranterior=btn61.getText();
        listaJugadas.agregarJugada(5,0,valoranterior);
        btn61.setText(text);
        btn61.setForeground(Color.black);}
    if (btnBorrarCasilla.isSelected()){
        for (Cell cell : lstCells){
           if (cell.getRow()== 5 && cell.getColumn()== 0 ){
                int jtv = cell.getJailTargetValue();
                char operation = cell.getOperation();
                String txt = Integer.toString(jtv) + operation;
                btn61.setText(txt);
           }
        }}
    }//GEN-LAST:event_btn61ActionPerformed
/**
 * logica utilizada para interactuar con los botones dependiendo del caso 
 * @param evt  click
 */
    private void btn62ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn62ActionPerformed
    if (valornuevo > 0 && valornuevo < 7){    
        String text= ""+valornuevo;
        String valoranterior=btn62.getText();
        listaJugadas.agregarJugada(5,1,valoranterior);
        btn62.setText(text);
        btn62.setForeground(Color.black);}  
    if (btnBorrarCasilla.isSelected()){
        for (Cell cell : lstCells){
           if (cell.getRow()== 5 && cell.getColumn()== 1 ){
                int jtv = cell.getJailTargetValue();
                char operation = cell.getOperation();
                String txt = Integer.toString(jtv) + operation;
                btn62.setText(txt);
           }
        }}
    }//GEN-LAST:event_btn62ActionPerformed
/**
 * logica utilizada para interactuar con los botones dependiendo del caso 
 * @param evt  click
 */
    private void btn63ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn63ActionPerformed
    if (valornuevo > 0 && valornuevo < 7){    
        String text= ""+valornuevo;
        String valoranterior=btn63.getText();
        listaJugadas.agregarJugada(5,2,valoranterior);
        btn63.setText(text);
        btn63.setForeground(Color.black);}
    if (btnBorrarCasilla.isSelected()){
        for (Cell cell : lstCells){
           if (cell.getRow()== 5 && cell.getColumn()== 2 ){
                int jtv = cell.getJailTargetValue();
                char operation = cell.getOperation();
                String txt = Integer.toString(jtv) + operation;
                btn63.setText(txt);
           }
        }}
    }//GEN-LAST:event_btn63ActionPerformed
/**
 * logica utilizada para interactuar con los botones dependiendo del caso 
 * @param evt  click
 */
    private void btn64ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn64ActionPerformed
     if (valornuevo > 0 && valornuevo < 7){    
        String text= ""+valornuevo;
        String valoranterior=btn64.getText();
        listaJugadas.agregarJugada(5,3,valoranterior);
        btn64.setText(text);
        btn64.setForeground(Color.black);}    
     if (btnBorrarCasilla.isSelected()){
        for (Cell cell : lstCells){
           if (cell.getRow()== 5 && cell.getColumn()== 3 ){
                int jtv = cell.getJailTargetValue();
                char operation = cell.getOperation();
                String txt = Integer.toString(jtv) + operation;
                btn64.setText(txt);
           }
        }}
    }//GEN-LAST:event_btn64ActionPerformed
/**
 * logica utilizada para interactuar con los botones dependiendo del caso 
 * @param evt  click
 */
    private void btn65ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn65ActionPerformed
    if (valornuevo > 0 && valornuevo < 7){    
        String text= ""+valornuevo;
        String valoranterior=btn65.getText();
        listaJugadas.agregarJugada(5,4,valoranterior);
        btn65.setText(text);
        btn65.setForeground(Color.black);}  
    if (btnBorrarCasilla.isSelected()){
        for (Cell cell : lstCells){
           if (cell.getRow()== 5 && cell.getColumn()== 4 ){
                int jtv = cell.getJailTargetValue();
                char operation = cell.getOperation();
                String txt = Integer.toString(jtv) + operation;
                btn65.setText(txt);
           }
        }}
    }//GEN-LAST:event_btn65ActionPerformed
/**
 * logica utilizada para interactuar con los botones dependiendo del caso 
 * @param evt  click
 */
    private void btn66ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn66ActionPerformed
    if (valornuevo > 0 && valornuevo < 7){    
        String text= ""+valornuevo;
        String valoranterior=btn66.getText();
        listaJugadas.agregarJugada(5,5,valoranterior);
        btn66.setText(text);
        btn66.setForeground(Color.black);} 
    if (btnBorrarCasilla.isSelected()){
        for (Cell cell : lstCells){
           if (cell.getRow()== 5 && cell.getColumn()== 5 ){
                int jtv = cell.getJailTargetValue();
                char operation = cell.getOperation();
                String txt = Integer.toString(jtv) + operation;
                btn66.setText(txt);
           }
        }}
    }//GEN-LAST:event_btn66ActionPerformed
/**
 * devuleve la casilla ultima casilla con la que se interactuó a su estado anterior
 * @param evt click en el botón
 */
    private void btnDeshacerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeshacerActionPerformed
        btnDeshacer.setOpaque(true);
        btnDeshacer.setBorderPainted(false);
        //listaJugadas.verUltimaJugada();
        
        Nodo jugada = listaJugadas.popUltimaJugada();
        if (jugada !=null){
        int jRow = jugada.getRow();
        int jCol = jugada.getCol();
        String jValAnt = jugada.getValorAnterior();
        
        JButton btn = lstBotones.get(jRow).get(jCol);
        String textPrev = btn.getText();
        
        jugadasDeshechas.agregarJugada(jRow, jCol, textPrev);
        btn.setText(jValAnt);}
        else{
            JOptionPane.showMessageDialog(null, "No se puede deshacer ninguna jugada");
            System.out.println("jugada = null, no se puede deshacer nada");
        }
    }//GEN-LAST:event_btnDeshacerActionPerformed
/**
 * verifica si hay partidas en la lista de jugadas deshechas y la vuelve a colocar en el tablero
 * @param evt click en el botón
 */
    private void btnRehacerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRehacerActionPerformed
        btnRehacer.setOpaque(true);
        btnRehacer.setBorderPainted(false);
        
        Nodo jugada = jugadasDeshechas.popUltimaJugada();
        if (jugada != null){
        int jRow = jugada.getRow();
        int jCol = jugada.getCol();
        String jValAnt = jugada.getValorAnterior();
        
        System.out.println("Row: " + jRow);
        System.out.println("Col: " + jCol);
        System.out.println("ValAnt: " + jValAnt);
        
        JButton btn = lstBotones.get(jRow).get(jCol);
        String txt = btn.getText();
        
        listaJugadas.agregarJugada(jRow, jCol, txt);
        btn.setText(jValAnt);}
        else{
            JOptionPane.showMessageDialog(null, "No se puede rehacer ninguna jugada");
            System.out.println("jugada = null, no se puede rehacer nada");
        }
    }//GEN-LAST:event_btnRehacerActionPerformed
/**
 * carga una de las partidas, limpia las listas y muestra el tablero correspondiente
 * @param evt click en el botón
 */
    private void btnIniciarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIniciarActionPerformed
        btnIniciar.setEnabled(false);
        btnTerminar.setEnabled(true);
        btnOtraPartida.setEnabled(true);
        btnRehacer.setEnabled(true);
        btnDeshacer.setEnabled(true);
        btnBorrarCasilla.setEnabled(true);
        btnValidar.setEnabled(true);
        btnReiniciar.setEnabled(true);
        
        lstBotones.clear();
        sublstBotones.clear();
        listaJugadas.clearList();
        jugadasDeshechas.clearList();
        
        if (posicion == true){
            btnsDer.setVisible(true);
            btnsIzq.setVisible(false);
        } else {
            btnsIzq.setVisible(true);
            btnsDer.setVisible(false);
        }
        
        cargarTablero(dificultad);
        startReloj();
    }//GEN-LAST:event_btnIniciarActionPerformed
/**
 * cierra la ventana de juego y elimina todo progreso
 * @param evt click en el botón
 */
    private void btnTerminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTerminarActionPerformed
        int confirm = JOptionPane.showConfirmDialog(rootPane, "Desea terminar el juego? Su progreso no sera guardado!");
        if (partidaActual != null){
            if (confirm == JOptionPane.YES_OPTION){
            System.out.println("Terminando juego...");
            stopTimerLabel();
            resetTimerLabel();
            setVisible(false);
            }
        } else {
            JOptionPane.showMessageDialog(rootPane, "No se puede terminar el juego porque no hay ningun juego en curso");
        }
        
    }//GEN-LAST:event_btnTerminarActionPerformed
/**
 *carga una partida nueva y limpia las listas de jugadas
 * @param evt click en el botón
 */
    private void btnOtraPartidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOtraPartidaActionPerformed
        int confirm = JOptionPane.showConfirmDialog(rootPane, "Desea cargar otro tablero?");
        if (confirm == JOptionPane.YES_OPTION){
            System.out.println("Cargando otro tablero...");
            lstBotones.clear();
            sublstBotones.clear();
            listaJugadas.clearList();
            jugadasDeshechas.clearList();
            
            stopTimerLabel();
            resetTimerLabel();
            if (rstFlag == true){
                rstFlag = false;
                reloj = "Timer";
                lblReloj.setText("Timer: ");
            }
            cargarTablero(dificultad);
            startReloj();
            
        }
    }//GEN-LAST:event_btnOtraPartidaActionPerformed
/**
 * recarga el tablero y limpia las listas de jugadas
 * @param evt click en el botón
 */
    private void btnReiniciarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReiniciarActionPerformed
        int confirm = JOptionPane.showConfirmDialog(rootPane, "Desea recargar este tablero?");
        if (confirm == JOptionPane.YES_OPTION){
            System.out.println("Recargando el tablero...");
            lstBotones.clear();
            sublstBotones.clear();
            listaJugadas.clearList();
            jugadasDeshechas.clearList();
            
            stopTimerLabel();
            resetTimerLabel();
            if (rstFlag == true){
                rstFlag = false;
                reloj = "Timer";
                lblReloj.setText("Timer: ");
            }
            resetTablero(dificultad, indiceActual);
            startReloj();
            
        }
    }//GEN-LAST:event_btnReiniciarActionPerformed
/**
 * valida que las casillas tengan el valor correcto y si hay errores muestra dónde se encuentran
 * @param evt click en el botón
 */
    private void btnValidarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnValidarActionPerformed
        for (Cell cell : lstCells){
            int row = cell.getRow();
            int column = cell.getColumn();
            int targetValue = cell.getTargetValue();
            
            JButton btnP = lstBotones.get(row).get(column);
            try{
                int currentValue = Integer.parseInt(btnP.getText());
                if (currentValue != targetValue){
                    JOptionPane.showMessageDialog(rootPane, "Hay errores en la partida!");
                    btnP.setForeground(Color.red);
                    return;
                }
            } catch (NumberFormatException e){
                JOptionPane.showMessageDialog(rootPane, "El valor en el botón en " + row + ", " + column + " no es un número válido.");
                return;
            }
        }
        stopTimerLabel();
        if (sonido){
            SoundPlayer victorySound = new SoundPlayer();
            victorySound.playSound("win.wav");
            System.out.println("Reproduciendo win.wav");
        }
        if (!"No Usar".equals(reloj)){
            switch (reloj){
                case "Cronometro":
                    System.out.println("Cronometro");
                case "Timer":
                    System.out.println("Timer");
            }
        }
        
        JOptionPane.showMessageDialog(rootPane, "Has completado el tablero!");
        setVisible(false);
    }//GEN-LAST:event_btnValidarActionPerformed

    private void btnBorrarCasillaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBorrarCasillaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnBorrarCasillaActionPerformed

    private void txtJugadorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtJugadorActionPerformed
        String rcpJugador = txtJugador.getText();
        System.out.println("Jugador: " + rcpJugador);
        if (rcpJugador.length() >= 1){
            btnIniciar.setEnabled(true);
            jugador = rcpJugador;
            txtJugador.setEnabled(false);
        }
    }//GEN-LAST:event_txtJugadorActionPerformed

    private void btnPodioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPodioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnPodioActionPerformed
    
    /**
     * crea una nueva ventana de juego
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(VentanaJugar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VentanaJugar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VentanaJugar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VentanaJugar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VentanaJugar().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel boardPanel;
    private javax.swing.JButton btn11;
    private javax.swing.JButton btn12;
    private javax.swing.JButton btn13;
    private javax.swing.JButton btn14;
    private javax.swing.JButton btn15;
    private javax.swing.JButton btn16;
    private javax.swing.JButton btn21;
    private javax.swing.JButton btn22;
    private javax.swing.JButton btn23;
    private javax.swing.JButton btn24;
    private javax.swing.JButton btn25;
    private javax.swing.JButton btn26;
    private javax.swing.JButton btn31;
    private javax.swing.JButton btn32;
    private javax.swing.JButton btn33;
    private javax.swing.JButton btn34;
    private javax.swing.JButton btn35;
    private javax.swing.JButton btn36;
    private javax.swing.JButton btn41;
    private javax.swing.JButton btn42;
    private javax.swing.JButton btn43;
    private javax.swing.JButton btn44;
    private javax.swing.JButton btn45;
    private javax.swing.JButton btn46;
    private javax.swing.JButton btn51;
    private javax.swing.JButton btn52;
    private javax.swing.JButton btn53;
    private javax.swing.JButton btn54;
    private javax.swing.JButton btn55;
    private javax.swing.JButton btn56;
    private javax.swing.JButton btn61;
    private javax.swing.JButton btn62;
    private javax.swing.JButton btn63;
    private javax.swing.JButton btn64;
    private javax.swing.JButton btn65;
    private javax.swing.JButton btn66;
    private javax.swing.JToggleButton btnBorrarCasilla;
    private javax.swing.JButton btnDeshacer;
    private javax.swing.JButton btnIniciar;
    private javax.swing.JButton btnOtraPartida;
    private javax.swing.JButton btnPodio;
    private javax.swing.JButton btnRehacer;
    private javax.swing.JButton btnReiniciar;
    private javax.swing.JButton btnTerminar;
    private javax.swing.JButton btnValidar;
    private javax.swing.JPanel btnsDer;
    private javax.swing.JPanel btnsIzq;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JToggleButton jToggleButton10;
    private javax.swing.JToggleButton jToggleButton11;
    private javax.swing.JToggleButton jToggleButton12;
    private javax.swing.JToggleButton jToggleButton2;
    private javax.swing.JToggleButton jToggleButton3;
    private javax.swing.JToggleButton jToggleButton4;
    private javax.swing.JToggleButton jToggleButton5;
    private javax.swing.JToggleButton jToggleButton6;
    private javax.swing.JToggleButton jToggleButton7;
    private javax.swing.JToggleButton jToggleButton8;
    private javax.swing.JToggleButton jToggleButton9;
    private javax.swing.JLabel lblDificultad;
    private javax.swing.JLabel lblJugador;
    private javax.swing.JLabel lblReloj;
    private javax.swing.JLabel lblTiempo;
    private javax.swing.JPanel panelBts;
    private javax.swing.JTextField txtJugador;
    // End of variables declaration//GEN-END:variables
}
