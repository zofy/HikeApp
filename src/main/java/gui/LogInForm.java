package gui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import sk.ics.upjs.hikeapp.DaOFactory;
import sk.ics.upjs.hikeapp.UzivatelDaO;

public class LogInForm extends javax.swing.JFrame implements MouseListener {

    private JButton loginButton;
    private JButton registerButton;
    private JTextField menoTextField;
    private JPasswordField hesloTextField;
    private JLabel hostLabel;
    private UzivatelDaO uzivatel;
    private long hostConstant = -1;

    public LogInForm() {
        initComponents();
        setTitle("Prihlásenie");
        loginButton = new JButton("Login");
        registerButton = new JButton("Registruj");
        menoTextField = new JTextField(11);
        hesloTextField = new JPasswordField(11);
        hostLabel = new JLabel("<html><u>Hosť</u></html>");
        uzivatel = DaOFactory.INSTANCE.getUserDaO();

        JPanel content = new JPanel(new GridBagLayout());
        content.setBackground(Color.white);
        //content.setBorder(new EmptyBorder(20, 20, 20, 20));
        this.setContentPane(content);
        this.add(new LoginPane());
        hostLabel.addMouseListener(this);
        loginButton.addMouseListener(this);
        registerButton.addMouseListener(this);

        pack();
        Dimension dim = new Dimension(420, 340);
        this.setMinimumSize(dim);
        dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((dim.width - this.getSize().width) / 2, (dim.height - this.getSize().height) / 2);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource().equals(hostLabel)) {
            this.dispose();
            new FilterTurForm(hostConstant).setVisible(true);
        }
        if (e.getSource().equals(loginButton)) {
            if (uzivatel.overUzivatela(menoTextField.getText().trim(), hesloTextField.getText().trim())) {
                this.dispose();
                new UzivatelMenu(uzivatel.getUserId(menoTextField.getText())).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Nesprávne meno alebo heslo!", "Chyba", JOptionPane.ERROR_MESSAGE);
            }
        }
        if (e.getSource().equals(registerButton)) {
            this.dispose();
            new RegistrujForm(menoTextField.getText(), hesloTextField.getText()).setVisible(true);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (e.getSource().equals(hostLabel)) {
            hostLabel.setForeground(Color.BLUE);
            hostLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getSource().equals(hostLabel)) {
            hostLabel.setForeground(Color.BLACK);
        }
    }

    public class LoginPane extends JPanel {

        public LoginPane() {
            setLayout(new GridBagLayout());
            setBackground(Color.white);

            BufferedImage logInObrazok = null;
            try {
                logInObrazok = ImageIO.read(new File("C:\\logo\\loginLogo.jpg"));
            } catch (IOException ex) {
                System.err.println("Neni obrazok!");
            }
            Image scaledObrazok = logInObrazok.getScaledInstance(350,
                    220, Image.SCALE_SMOOTH);
            ImageIcon img = new ImageIcon(scaledObrazok);

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridwidth = 5;
            JLabel l = new JLabel(img);
            add(l, gbc);

            // vynulovat
            gbc.ipadx = 0;
            gbc.ipady = 0;
            gbc.gridwidth = 1;

            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.ipadx = 80;
            l = new JLabel();
            add(l, gbc);
            // vynulovat
            gbc.ipadx = 0;

            gbc.gridx = 1;
            gbc.gridy = 1;
            gbc.anchor = GridBagConstraints.WEST;
            add(new JLabel("Meno:"), gbc);
            gbc.gridy = 2;
            add(new JLabel("Heslo:"), gbc);

            gbc.gridx = 2;
            gbc.gridy = 1;
            gbc.gridwidth = 3;
            gbc.weightx = 1;
            add(menoTextField, gbc);
            gbc.gridy++;
            add(hesloTextField, gbc);

            gbc.gridx = 2;
            gbc.gridy++;
            gbc.gridwidth = 1;
            gbc.weightx = 0;
            gbc.fill = GridBagConstraints.NONE;
            gbc.anchor = GridBagConstraints.EAST;
            add(loginButton, gbc);

            gbc.gridx = 3;
            gbc.anchor = GridBagConstraints.WEST;
            add(registerButton, gbc);

            gbc.gridy++;
            gbc.ipady = 10;
            gbc.fill = GridBagConstraints.BOTH;
            JLabel medzera = new JLabel();
            add(medzera, gbc);

            gbc.gridy--;
            gbc.ipady = 0;
            gbc.fill = GridBagConstraints.NONE;

            gbc.gridx = 4;
            gbc.anchor = GridBagConstraints.CENTER;
            add(hostLabel, gbc);
        }

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
    // End of variables declaration//GEN-END:variables
}
