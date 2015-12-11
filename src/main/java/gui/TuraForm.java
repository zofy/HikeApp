package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import komponenty.ScrollPaneSSCCE;
import sk.ics.upjs.hikeapp.TuraDaO;
import sk.ics.upjs.hikeapp.DaOFactory;
import sk.ics.upjs.hikeapp.MysqlTuraDaO;

public class TuraForm extends javax.swing.JFrame {
    
    private MysqlTuraDaO tury;
    private JLabel fotkaLabel;
    private List<ImageIcon> zoznamPano;
    private List<ImageIcon> zoznam;
    private JLabel popisLabel;
    private JPanel panel;
    private ScrollPaneSSCCE s;
    private JTable fotkyTable;
    
    public TuraForm() {
        initComponents();
    }
    
    public TuraForm(long idT) {
        initComponents();
        idT = 1;
        tury = (MysqlTuraDaO) DaOFactory.INSTANCE.getTuraDaO();
        this.setTitle(tury.dajNazovTury(idT));
        fotkaLabel = new JLabel();
        zoznamPano = spracujPano(tury.dajFotky(idT));
        panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        BufferedImage logInObrazok1 = null;
        
        try {
            logInObrazok1 = ImageIO.read(new File("C:\\logo\\mm.png"));
            
        } catch (IOException ex) {
            System.err.println("Neni obrazok!");
        }
        Image scaledObrazok1 = logInObrazok1.getScaledInstance(550,
                240, Image.SCALE_SMOOTH);
        
        fotkaLabel.setIcon(new ImageIcon(scaledObrazok1));
        zoznam = this.spracujFotky(tury.dajFotky(idT));
        //
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(fotkaLabel, gbc);
        s = new ScrollPaneSSCCE((ArrayList<ImageIcon>) zoznam);
        fotkyTable = s.getTable();
        fotkyTable.addMouseListener(new MouseAdapter() {
            
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println(fotkyTable.getSelectedColumn());
                TuraForm.this.zmenFotku(fotkyTable.getSelectedColumn());
            }
            
        });
        
        Dimension d = new Dimension(600, 130);
        s.setPreferredSize(d);
        s.setMaximumSize(d);

        // Fotky
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(s, gbc);

        // Popis
        gbc.gridy++;
        popisLabel = new JLabel();
        vypis(tury.spracujPopisDoListu(tury.dajPopis(idT)));
        popisLabel.setFont(new Font("Serif", Font.BOLD, 12));
        popisLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panel.add(popisLabel, gbc);

        // Detail tury
        gbc.gridy++;
        JTextArea detail = new JTextArea("wefwefeffhvfjksehvfb \n"
                + "ejfhvbjehfbvjhfb\n"
                + "ewjvfbjjhvbjshvbjk\n"
                + "ewvgweveeeeeeeeeeeeeeeeeeeeee\n"
                + "eeeeee");
        panel.add(detail, gbc);
        
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.setContentPane(scrollPane);
    }
    
    public List<ImageIcon> spracujFotky(List<Image> zoznamFotiek) {
        List<ImageIcon> upraveneFotky = new ArrayList<ImageIcon>();
        for (Image img : zoznamFotiek) {
            img = img.getScaledInstance(200, 120, Image.SCALE_SMOOTH);
            ImageIcon icon = new ImageIcon(img);
            upraveneFotky.add(icon);
        }
        return upraveneFotky;
    }
    
    public List<ImageIcon> spracujPano(List<Image> zoznamFotiek) {
        ImageIcon bimg = null;
        int height = 0;
        int width = 0;
        List<ImageIcon> upraveneFotky = new ArrayList<ImageIcon>();
        for (Image img : zoznamFotiek) {
            bimg = new ImageIcon(img);
            height = (int) (bimg.getIconHeight() / (2.5));
            width = bimg.getIconWidth() / 2;
            img = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            ImageIcon icon = new ImageIcon(img);
            upraveneFotky.add(icon);
        }
        return upraveneFotky;
    }
    
    public void vypis(LinkedList<String> bodyTury) {
        StringBuilder ret = new StringBuilder();
        int dlzka = 0;
        int pocetRiadkov = 0;
        for (String bod : bodyTury) {
            System.out.println(bod);
            if (dlzka < 100) {
                if (dlzka == 0) {
                    ret.append(bod);
                } else {
                    ret.append("-" + bod);
                }
                dlzka += bod.length();
            } else {
                pocetRiadkov++;
                ret.append("- \n -" + bod);
                dlzka = bod.length();
            }
        }
        Dimension d = new Dimension(600, pocetRiadkov * 25);
        popisLabel.setMinimumSize(d);
        popisLabel.setText(ret.toString());
    }
    
    public void zmenFotku(int idx) {
        Dimension d = new Dimension(zoznamPano.get(idx).getIconWidth(), zoznamPano.get(idx).getIconHeight());
        fotkaLabel.setMinimumSize(d);
        fotkaLabel.setIcon(zoznamPano.get(idx));
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
            java.util.logging.Logger.getLogger(TuraForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TuraForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TuraForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TuraForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TuraForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
