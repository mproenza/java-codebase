/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * frmCambiarPassword.java
 *
 * Created on 02-sep-2011, 10:01:50
 */
package org.base.security.gui;

import org.base.security.auth.exception.DSecurityException;
import org.base.security.service.DbServise;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

/**
 *
 * @author Luis Valdes Guerrero <lvaldes@grm.desoft.cu>
 */
public class frmCambiarPassword extends javax.swing.JPanel {

    private String usuario;
    private JDialog dialogo;

    /** Creates new form frmCambiarPassword */
    public frmCambiarPassword(JDialog dialogo, String usuario) {
        initComponents();
        this.usuario = usuario;
        this.dialogo = dialogo;
        this.dialogo.setTitle("Introduzca la contraseña");
        txtPass.addKeyListener(new FocusNextOnEnterActionListener());
        txtVerificar.addKeyListener(new FocusNextOnEnterActionListener());
        btnAceptar.registerKeyboardAction(btnAceptar.getActionForKeyStroke(
                                      KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, false)),
                                      KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false),
                                      JComponent.WHEN_FOCUSED);
 
        btnAceptar.registerKeyboardAction(btnAceptar.getActionForKeyStroke(
                                      KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, true)),
                                      KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true),
                                      JComponent.WHEN_FOCUSED);
        btnCancelar.registerKeyboardAction(btnCancelar.getActionForKeyStroke(
                                      KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, false)),
                                      KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false),
                                      JComponent.WHEN_FOCUSED);
        btnCancelar.registerKeyboardAction(btnCancelar.getActionForKeyStroke(
                                      KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, true)),
                                      KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true),
                                      JComponent.WHEN_FOCUSED);
        btnCancelar.registerKeyboardAction(btnCancelar.getActionForKeyStroke(
                                      KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, false)),
                                      KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false),
                                      JComponent.WHEN_IN_FOCUSED_WINDOW);
        btnCancelar.registerKeyboardAction(btnCancelar.getActionForKeyStroke(
                                      KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, true)),
                                      KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, true),
                                      JComponent.WHEN_IN_FOCUSED_WINDOW);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        txtPass = new javax.swing.JPasswordField();
        txtVerificar = new javax.swing.JPasswordField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        btnAceptar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        txtPass.setNextFocusableComponent(txtVerificar);

        txtVerificar.setNextFocusableComponent(btnAceptar);

        jLabel1.setText("Contraseña:");

        jLabel2.setText("Verificar:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtPass, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtVerificar, javax.swing.GroupLayout.DEFAULT_SIZE, 137, Short.MAX_VALUE)))
                .addContainerGap(16, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtVerificar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnAceptar.setText("Aceptar");
        btnAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceptarActionPerformed(evt);
            }
        });

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(53, 53, 53)
                        .addComponent(btnAceptar)
                        .addGap(18, 18, 18)
                        .addComponent(btnCancelar)))
                .addContainerGap(15, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAceptar)
                    .addComponent(btnCancelar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed
        if (Arrays.equals(txtPass.getPassword(), txtVerificar.getPassword())) {
            try {
                new DbServise().cambiarPassword(usuario, String.valueOf(txtPass.getPassword()));
                JOptionPane.showMessageDialog(null, "Contraseña actualizada", "!Información!", JOptionPane.INFORMATION_MESSAGE);
                this.dialogo.dispose();
            } catch (DSecurityException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "!Información!", JOptionPane.INFORMATION_MESSAGE);
                txtPass.setText("");
                txtVerificar.setText("");
                txtPass.requestFocusInWindow();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Las contraseñas no coinciden", "!Información!", JOptionPane.INFORMATION_MESSAGE);
            txtPass.setText("");
            txtVerificar.setText("");
            txtPass.requestFocusInWindow();
        }
    }//GEN-LAST:event_btnAceptarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        this.dialogo.dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPasswordField txtPass;
    private javax.swing.JPasswordField txtVerificar;
    // End of variables declaration//GEN-END:variables
}
