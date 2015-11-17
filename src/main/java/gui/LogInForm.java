package gui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import sk.ics.upjs.hikeapp.UzivatelMysqlDaO;

public class LogInForm extends javax.swing.JFrame {
    
    private UzivatelMysqlDaO uzivatelia;
    private JFrame frame = this;
    
    public LogInForm() {
        initComponents();
        this.setTitle("Hike app");
        uzivatelia = new UzivatelMysqlDaO();
        BufferedImage logInObrazok = null;
        try {
            logInObrazok = ImageIO.read(new File("C:\\loginLogo.jpg"));
        } catch (IOException ex) {
            System.err.println("Neni obrazok!");
        }
        Image scaledObrazok = logInObrazok.getScaledInstance(loginObrazokLabel.getWidth(),
                loginObrazokLabel.getHeight(), Image.SCALE_SMOOTH);
        loginObrazokLabel.setIcon(new ImageIcon(scaledObrazok));
        this.getContentPane().setBackground(Color.white);
        String hostText = "<html><h align='center'><u>Pokračuj ako hosť</u></h></html>";
        String registrujText = "<html><h align='center'><u>Registruj</u></h></html>";
        registrujLabel.setText(registrujText);
        hostLabel.setText(hostText);
        final Color farbaReg = registrujLabel.getForeground();
        final Color farbaHosta = hostLabel.getForeground();
        hostLabel.addMouseMotionListener(new MouseAdapter() {
            
            @Override
            public void mouseMoved(MouseEvent e) {
                hostLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
                hostLabel.setForeground(Color.blue);
            }
        });
        hostLabel.addMouseListener(new MouseAdapter() {
            
            @Override
            public void mouseExited(MouseEvent e) {
                hostLabel.setForeground(farbaHosta);
            }
            
            @Override
            public void mouseClicked(MouseEvent e) {
                frame.dispose();
                new FilterTurForm().setVisible(true);
            }
        });
        registrujLabel.addMouseMotionListener(new MouseAdapter() {
            
            @Override
            public void mouseMoved(MouseEvent e) {
                registrujLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
                registrujLabel.setForeground(Color.blue);
            }
        });
        registrujLabel.addMouseListener(new MouseAdapter() {
            
            @Override
            public void mouseExited(MouseEvent e) {
                registrujLabel.setForeground(farbaReg);
            }
            
            @Override
            public void mouseClicked(MouseEvent e) {
                //frame.setVisible(false);
                frame.dispose();
                new RegisterForm().setVisible(true);
            }
            
        });
        pack();
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        loginObrazokLabel = new javax.swing.JLabel();
        menoTextField = new javax.swing.JTextField();
        hesloTextField = new javax.swing.JPasswordField();
        logInButton = new javax.swing.JButton();
        menoLabel = new javax.swing.JLabel();
        hesloLabel = new javax.swing.JLabel();
        hostLabel = new javax.swing.JLabel();
        registrujLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        menoTextField.setText("Meno");
        menoTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menoTextFieldActionPerformed(evt);
            }
        });

        hesloTextField.setText("jPasswordField1");

        logInButton.setText("Log In");

        menoLabel.setText("Meno");

        hesloLabel.setText("Heslo");

        hostLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        hostLabel.setText("Hosť");

        registrujLabel.setText("Registruj");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(loginObrazokLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 373, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(106, 106, 106)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(hesloLabel)
                                        .addGap(18, 18, 18)
                                        .addComponent(hesloTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(menoLabel)
                                        .addGap(18, 18, 18)
                                        .addComponent(menoTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(logInButton, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(hostLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(41, 41, 41)
                        .addComponent(registrujLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(loginObrazokLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(menoTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(menoLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(hesloTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(hesloLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(logInButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(hostLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(registrujLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void menoTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menoTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_menoTextFieldActionPerformed

    /**
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
            java.util.logging.Logger.getLogger(LogInForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LogInForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LogInForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LogInForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LogInForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel hesloLabel;
    private javax.swing.JPasswordField hesloTextField;
    private javax.swing.JLabel hostLabel;
    private javax.swing.JButton logInButton;
    private javax.swing.JLabel loginObrazokLabel;
    private javax.swing.JLabel menoLabel;
    private javax.swing.JTextField menoTextField;
    private javax.swing.JLabel registrujLabel;
    // End of variables declaration//GEN-END:variables
}
