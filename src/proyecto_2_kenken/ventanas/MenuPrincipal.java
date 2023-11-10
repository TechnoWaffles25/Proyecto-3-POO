package proyecto_2_kenken.ventanas;

import java.awt.Desktop;
import proyecto_2_kenken.ventanas.MenuConfiguracion;
import java.io.File;
import java.io.IOException;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import proyecto_2_kenken.ventanas.*;
/**
 * Clase de interfaz gráfica para el menú principal
 * @author Alejandro Montero, Fabricio Monge 
 */
public class MenuPrincipal extends javax.swing.JFrame {

    public MenuPrincipal() {
        initComponents();
        
        windowJugar.setOpaque(true);
        windowJugar.setBorderPainted(false);
        btnConfig.setOpaque(true);
        btnConfig.setBorderPainted(false);
        btnAyuda.setOpaque(true);
        btnAyuda.setBorderPainted(false);
        btnSalir.setOpaque(true);
        btnSalir.setBorderPainted(false);
        btnAcercaDe.setOpaque(true);
        btnAcercaDe.setBorderPainted(false);
        
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        windowJugar = new javax.swing.JButton();
        btnConfig = new javax.swing.JButton();
        btnAcercaDe = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        btnAyuda = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(51, 51, 51));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 3, 48)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(204, 204, 204));
        jLabel1.setText("KenKen: Juego Aritmético");

        windowJugar.setBackground(new java.awt.Color(153, 255, 102));
        windowJugar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        windowJugar.setForeground(new java.awt.Color(51, 51, 51));
        windowJugar.setText("JUGAR");
        windowJugar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                windowJugarActionPerformed(evt);
            }
        });

        btnConfig.setBackground(new java.awt.Color(102, 204, 255));
        btnConfig.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnConfig.setForeground(new java.awt.Color(51, 51, 51));
        btnConfig.setText("CONFIGURACION");
        btnConfig.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfigActionPerformed(evt);
            }
        });

        btnAcercaDe.setBackground(new java.awt.Color(153, 255, 204));
        btnAcercaDe.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnAcercaDe.setForeground(new java.awt.Color(51, 51, 51));
        btnAcercaDe.setText("ACERCA DE");
        btnAcercaDe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAcercaDeActionPerformed(evt);
            }
        });

        btnSalir.setBackground(new java.awt.Color(255, 102, 102));
        btnSalir.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnSalir.setForeground(new java.awt.Color(51, 51, 51));
        btnSalir.setText("SALIR");
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });

        btnAyuda.setBackground(new java.awt.Color(102, 204, 255));
        btnAyuda.setFont(new java.awt.Font("Helvetica Neue", 1, 13)); // NOI18N
        btnAyuda.setText("AYUDA");
        btnAyuda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAyudaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(140, 140, 140)
                .addComponent(jLabel1)
                .addContainerGap(12, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(windowJugar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnConfig, javax.swing.GroupLayout.DEFAULT_SIZE, 201, Short.MAX_VALUE)
                            .addComponent(btnSalir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnAcercaDe, javax.swing.GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE)
                            .addComponent(btnAyuda, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(31, 31, 31)
                .addComponent(windowJugar, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnConfig, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAyuda, javax.swing.GroupLayout.DEFAULT_SIZE, 81, Short.MAX_VALUE))
                .addGap(7, 7, 7)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAcercaDe, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(46, Short.MAX_VALUE))
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
 * crea la ventana de juego
 * @param evt click
 */
    private void windowJugarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_windowJugarActionPerformed
        SwingUtilities.invokeLater(() -> {
            VentanaJugar ventana = new VentanaJugar();
            ventana.setVisible(true);
        });
    }//GEN-LAST:event_windowJugarActionPerformed
/**
 * crea la ventana de configuracion y despliega un menú de confirmación antes de mostrarla
 * @param evt click
 */
    private void btnConfigActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfigActionPerformed
        File configdat = new File("config.dat");
        if (configdat.exists()){
            int showConfirmDialog = JOptionPane.showConfirmDialog(this, "Ya existe una configuracion! Si continua el juego actual sera perdido. Desea continuar?");
            
           
            if (showConfirmDialog == JOptionPane.YES_OPTION) {
                MenuConfiguracion wndwConfig = new MenuConfiguracion();
                wndwConfig.setVisible(true);
            } 
        } else {
            MenuConfiguracion wndwConfig = new MenuConfiguracion();
            wndwConfig.setVisible(true);
        }
        
        
        
    }//GEN-LAST:event_btnConfigActionPerformed

    private void btnAcercaDeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAcercaDeActionPerformed
        JOptionPane.showMessageDialog(rootPane, "Informacion del programa"
                + "\nAutores: Alejandro Montero, Fabricio Monge"
                + "\nNombre del proyecto: KenKen juego de logica"
                + "\nFecha de creacion: Viernes 20 de Octubre"
                + "\nVersion de Java: Java 21 2023-09-19");
    }//GEN-LAST:event_btnAcercaDeActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        System.exit(0);
    }//GEN-LAST:event_btnSalirActionPerformed

    private void btnAyudaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAyudaActionPerformed

        if (Desktop.isDesktopSupported()) {
            try {
                File myFile = new File("Manual de Usuario.pdf");
                Desktop.getDesktop().open(myFile);
            } catch (IOException ex) {
                System.out.println("error al abrir el manual");}
        }
    }//GEN-LAST:event_btnAyudaActionPerformed
/**
 * main de la clase, hace la ventana visible
 * @param args sin especificación 
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
            java.util.logging.Logger.getLogger(MenuPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MenuPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MenuPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MenuPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MenuPrincipal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAcercaDe;
    private javax.swing.JButton btnAyuda;
    private javax.swing.JButton btnConfig;
    private javax.swing.JButton btnSalir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton windowJugar;
    // End of variables declaration//GEN-END:variables
}
