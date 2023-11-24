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
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JToggleButton;
/**
 *
 * @author amontero
 */
public class VentanaJugar extends javax.swing.JFrame {
    public Lists listaJugadas = new Lists();
    public Lists jugadasDeshechas = new Lists();
    
    public List<Integer> indicesVisitados = new ArrayList<>();
    public List<Cell> lstCells = new ArrayList<>();
    public List<List<JButton>> gridButtons;
    public List<Partida> listPartidas;
    public int valornuevo;
    
    public String jugador;
    public String dificultad;
    public int sizeTablero;
    public Partida partidaActual;
    public boolean posicion;
    public boolean sonido;
    public String reloj;
    
    public ButtonGroup btnGroupNumPad;
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
        
        sizeTablero = readconfig.getSizeTablero();
        sonido = readconfig.getSonido();
        System.out.println("Sonido al terminar juego: " + sonido);
        
        reloj = readconfig.getReloj();
        System.out.println("Reloj seleccionado: " + reloj);
        lblReloj.setText(reloj+":");
        
        posicion = readconfig.getPosicion();
        System.out.println("Posicion de los botones (True der / False izq): " + posicion);
        
        btnsDer.setVisible(false);
        btnsDer.setLayout(new GridLayout(0,1));
        
        btnsIzq.setVisible(false);
        btnsIzq.setLayout(new GridLayout(0,1));
        
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
        
        panel.setLayout(new GridLayout(sizeTablero, sizeTablero));
        updateGrid();
    }
    /**
     * carga un tablero con una partida
     * @param dificultad dificultad de juego
     */
    public void updateNumPad(boolean lado){
        btnGroupNumPad = new ButtonGroup();
        Dimension buttonSize = new Dimension(60, 65);
        
        for (int i = 1; i <= sizeTablero; i++) {
            JToggleButton numberButton = new JToggleButton(String.valueOf(i));
            numberButton.setPreferredSize(buttonSize);
            numberButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Acción a realizar cuando se hace clic en el botón
                        System.out.println("Botón presionado: " + numberButton.getText());
                    }
                });
            // Your action listener here...
            // Remember to add listeners to both button instances if needed

            btnGroupNumPad.add(numberButton);
            btnGroupNumPad.add(numberButton);
            if (lado){
                btnsDer.add(numberButton);
            } else {
                btnsIzq.add(numberButton);
            }   
        }

        // Repack and repaint the frame
        this.pack();
        this.repaint();
    }
    
    public void updateGrid() {
        panel.removeAll(); // Eliminar botones existentes
        gridButtons = new ArrayList<>(); // Inicializar la lista de listas
        Dimension buttonSize = new Dimension(60, 65);
        
        for (int row = 0; row < sizeTablero; row++) {
            List<JButton> buttonRow = new ArrayList<>(); // Lista para la fila actual
            for (int col = 0; col < sizeTablero; col++) {
                JButton button = new JButton(String.valueOf(row * sizeTablero + col + 1));
                button.setPreferredSize(buttonSize);
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Acción a realizar cuando se hace clic en el botón
                        System.out.println("Botón presionado: " + button.getText());
                    }
                });
                buttonRow.add(button); // Añadir el botón a la fila
                panel.add(button);
            }
            gridButtons.add(buttonRow); // Añadir la fila a la lista de listas
        }

        panel.revalidate();
        panel.repaint();
    }

    public void cargarTablero(){
            ReadPartidaXML reader = new ReadPartidaXML();
            List<Partida> listaPartidas = reader.parseKenKenPartidas("kenken_partidas3.xml", dificultad, sizeTablero);
            
            if (listaPartidas == null || listaPartidas.isEmpty()) {
                System.out.println("No hay partidas disponibles.");
                return; // Salir del método si no hay partidas
            }
            
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
            System.out.println("Tamaño de gridButtons: " + gridButtons.size());
            for (Cell cell : lstCells){
                int jtv = cell.getJailTargetValue();
                char operation = cell.getOperation();
                int row = cell.getRow();
                int column = cell.getColumn();
                String txt = Integer.toString(jtv) + operation;
                System.out.println("Accediendo a gridButtons en fila " + row + " y columna " + column);
                
                switch(operation){
                    case '+':
                        System.out.println("Caso +");
                        SwingUtilities.invokeLater(() -> {
                            JButton btnP = gridButtons.get(row).get(column);
                            btnP.setBackground(Color.RED);
                            btnP.setText(txt);
                            btnP.setOpaque(true);
                        });
                        break;
                    case '-':
                        System.out.println("Caso -");
                        SwingUtilities.invokeLater(() -> {
                            JButton btnM = gridButtons.get(row).get(column);
                            btnM.setBackground(Color.BLUE);
                            btnM.setText(txt);
                            btnM.setOpaque(true);
                        });
                        break;
                    case 'x':
                        System.out.println("Caso x");
                        SwingUtilities.invokeLater(() -> {
                            JButton btnX = gridButtons.get(row).get(column);
                            btnX.setBackground(Color.ORANGE);
                            btnX.setText(txt);
                            btnX.setOpaque(true);
                        });
                        break;
                    case '/':
                        System.out.println("Caso /");
                        SwingUtilities.invokeLater(() -> {
                            JButton btnD = gridButtons.get(row).get(column);
                            btnD.setBackground(Color.GREEN);
                            btnD.setText(txt);
                            btnD.setOpaque(true);
                        });
                        break;
                    case 'n':
                        System.out.println("Caso n");
                        SwingUtilities.invokeLater(() -> {
                            JButton btnN = gridButtons.get(row).get(column);
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
    /*public void resetTablero(String dificultad, int indiceActual){
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
        }*/
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
        btnsIzq = new javax.swing.JPanel();
        btnsDer = new javax.swing.JPanel();
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
        panel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(51, 51, 51));

        jLabel1.setBackground(new java.awt.Color(204, 204, 204));
        jLabel1.setFont(new java.awt.Font("Helvetica Neue", 3, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(204, 204, 204));
        jLabel1.setText("Dificultad:");

        btnsIzq.setBackground(new java.awt.Color(102, 102, 102));

        javax.swing.GroupLayout btnsIzqLayout = new javax.swing.GroupLayout(btnsIzq);
        btnsIzq.setLayout(btnsIzqLayout);
        btnsIzqLayout.setHorizontalGroup(
            btnsIzqLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 35, Short.MAX_VALUE)
        );
        btnsIzqLayout.setVerticalGroup(
            btnsIzqLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 341, Short.MAX_VALUE)
        );

        btnsDer.setBackground(new java.awt.Color(102, 102, 102));

        javax.swing.GroupLayout btnsDerLayout = new javax.swing.GroupLayout(btnsDer);
        btnsDer.setLayout(btnsDerLayout);
        btnsDerLayout.setHorizontalGroup(
            btnsDerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 35, Short.MAX_VALUE)
        );
        btnsDerLayout.setVerticalGroup(
            btnsDerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 341, Short.MAX_VALUE)
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

        panel.setBackground(new java.awt.Color(102, 102, 102));

        javax.swing.GroupLayout panelLayout = new javax.swing.GroupLayout(panel);
        panel.setLayout(panelLayout);
        panelLayout.setHorizontalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 491, Short.MAX_VALUE)
        );
        panelLayout.setVerticalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 395, Short.MAX_VALUE)
        );

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
                            .addComponent(panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(7, 7, 7)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(155, 155, 155)
                                .addComponent(btnsDer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(24, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
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
                    .addComponent(btnsDer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnsIzq, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addComponent(panelBts, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(164, 164, 164))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

/** devuleve la casilla ultima casilla con la que se interactuó a su estado anterior
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
        
        JButton btn = gridButtons.get(jRow).get(jCol);
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
        
        JButton btn = gridButtons.get(jRow).get(jCol);
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
        
        gridButtons.clear();
        listaJugadas.clearList();
        jugadasDeshechas.clearList();
        
        if (posicion == true){
            btnsDer.setVisible(true);
            btnsIzq.setVisible(false);
        } else {
            btnsIzq.setVisible(true);
            btnsDer.setVisible(false);
        }
        updateNumPad(posicion);
        cargarTablero();
        
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
            gridButtons.clear();
            listaJugadas.clearList();
            jugadasDeshechas.clearList();
            
            stopTimerLabel();
            resetTimerLabel();
            if (rstFlag == true){
                rstFlag = false;
                reloj = "Timer";
                lblReloj.setText("Timer: ");
            }
            //scargarTablero(dificultad);
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
            gridButtons.clear();
            listaJugadas.clearList();
            jugadasDeshechas.clearList();
            
            stopTimerLabel();
            resetTimerLabel();
            if (rstFlag == true){
                rstFlag = false;
                reloj = "Timer";
                lblReloj.setText("Timer: ");
            }
            //resetTablero(dificultad, indiceActual);
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
            
            JButton btnP = gridButtons.get(row).get(column);
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
        // Logica para ver si el juego clasifica en algun podio
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
    private javax.swing.JLabel lblDificultad;
    private javax.swing.JLabel lblJugador;
    private javax.swing.JLabel lblReloj;
    private javax.swing.JLabel lblTiempo;
    private javax.swing.JPanel panel;
    private javax.swing.JPanel panelBts;
    private javax.swing.JTextField txtJugador;
    // End of variables declaration//GEN-END:variables
}
