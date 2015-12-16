package gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import sk.ics.upjs.hikeapp.DaOFactory;
import sk.ics.upjs.hikeapp.Uzivatel;
import sk.ics.upjs.hikeapp.UzivatelDaO;

public class RegistrujForm extends javax.swing.JFrame {

    private JTextField menoTextField = new JTextField();
    private JPasswordField hesloField = new JPasswordField();
    private JPasswordField heslo2Field = new JPasswordField();
    private JButton registerButton = new JButton("Registruj");
    private JLabel obrazokLabel = new JLabel();
    private UzivatelDaO uzivatel = DaOFactory.INSTANCE.getUserDaO();

    public RegistrujForm() {
        initComponents();
    }

    public RegistrujForm(String meno, String heslo) {
        initComponents();
        this.setTitle("Ragistrácia užívateľa");
        menoTextField.setText(meno);
        hesloField.setText(heslo);

        BufferedImage logInObrazok1 = null;

        try {
            logInObrazok1 = ImageIO.read(new File("C:\\naMenu\\person.png"));

        } catch (IOException ex) {
            System.err.println("Neni obrazok!");
        }
        Image scaledObrazok1 = logInObrazok1.getScaledInstance(80,
                75, Image.SCALE_SMOOTH);

        obrazokLabel.setIcon(new ImageIcon(scaledObrazok1));

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int height = dim.height;
        int width = dim.width;
        this.setLocation((-this.getSize().width + width) / 2, (-this.getSize().height + height) / 2);
        dim = new Dimension(100, 25);

        menoTextField.setMinimumSize(dim);
        menoTextField.setMaximumSize(dim);
        menoTextField.setPreferredSize(dim);
        hesloField.setMaximumSize(dim);
        hesloField.setPreferredSize(dim);
        heslo2Field.setMaximumSize(dim);
        heslo2Field.setPreferredSize(dim);

        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(3, 3, 3, 3);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(obrazokLabel, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel menoLabel = new JLabel("Meno:");
        add(menoLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel hesloLabel = new JLabel("Heslo:");
        add(hesloLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        JLabel heslo2Label = new JLabel("Zopakuj heslo:");
        add(heslo2Label, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        add(menoTextField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        add(hesloField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        add(heslo2Field, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        add(registerButton, gbc);

        registerButton.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                Uzivatel u = new Uzivatel();
                String meno = menoTextField.getText();
                String heslo = hesloField.getText();
                if (meno.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(RegistrujForm.this, "Vypíš meno!", "Chyba", JOptionPane.ERROR_MESSAGE);
                    return;
                } else if (!uzivatel.overMeno(meno.trim())) {
                    JOptionPane.showMessageDialog(RegistrujForm.this, "Meno už existuje!", "Chyba", JOptionPane.ERROR_MESSAGE);
                    return;
                } else {
                    u.setMeno(meno.trim());
                }
                if (heslo.trim().length() < 6) {
                    JOptionPane.showMessageDialog(RegistrujForm.this, "Heslo musí mať aspoň 6 znakov!", "Chyba", JOptionPane.ERROR_MESSAGE);
                    return;
                } else if (heslo.trim().length() > 20) {
                    JOptionPane.showMessageDialog(RegistrujForm.this, "Príliš dlhé heslo!", "Chyba", JOptionPane.ERROR_MESSAGE);
                    return;
                } else {
                    u.setHeslo(heslo.trim());
                }
                uzivatel.vlozUzivatela(u);
                RegistrujForm.this.dispose();
                new UzivatelMenu(uzivatel.getUserId(meno)).setVisible(true);
            }

        });

        dim = new Dimension(300, 250);
        this.setPreferredSize(dim);
        this.pack();

        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                RegistrujForm.this.dispose();
                new LogInForm().setVisible(true);
            }

        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(RegistrujForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RegistrujForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RegistrujForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RegistrujForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new RegistrujForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
