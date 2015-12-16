package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
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
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import komponenty.ScrollPaneSSCCE;
import komponenty.StarRater;
import org.jdesktop.swingx.JXLabel;
import sk.ics.upjs.hikeapp.TuraDaO;
import sk.ics.upjs.hikeapp.DaOFactory;
import sk.ics.upjs.hikeapp.FotkaDaO;
import sk.ics.upjs.hikeapp.Tura;

public class TuraForm extends javax.swing.JFrame {
    
    private TuraDaO tury;
    private FotkaDaO fotos;
    private JLabel fotkaLabel;
    private List<ImageIcon> zoznamPano;
    private List<ImageIcon> zoznam;
    private JXLabel popisLabel;
    private JPanel panel;
    private ScrollPaneSSCCE s;
    private JTable fotkyTable;
    private long idU;
    private StarRater sr;
    private Tura tura;
    private List<Double> okraje;
    
    public TuraForm() {
        initComponents();
    }
    
    public TuraForm(long idT, long userId) {
        initComponents();
        tury = DaOFactory.INSTANCE.getTuraDaO();
        fotos = DaOFactory.INSTANCE.getFotkaDaO();
        idU = userId;
        tura = tury.dajTuru(idT);
        
        this.setTitle(tura.getNazov());
        fotkaLabel = new JLabel();
        zoznamPano = spracujPano(fotos.dajFotkyDanejTury(idT));
        panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        //nastavenie StarRatera
        sr = new StarRater(5, 0, 0);
        sr.setRating(tura.getHodnotenie());
        BufferedImage logInObrazok1 = null;
        
        try {
            logInObrazok1 = ImageIO.read(new File("C:\\logo\\mm.png"));
            
        } catch (IOException ex) {
            System.err.println("Neni obrazok!");
        }
        Image scaledObrazok1 = logInObrazok1.getScaledInstance(600,
                240, Image.SCALE_SMOOTH);
        
        fotkaLabel.setIcon(new ImageIcon(scaledObrazok1));
        zoznam = this.spracujFotky(fotos.dajFotkyDanejTury(idT));
        //
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(3, 3, 3, 3);
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 5;
        panel.add(fotkaLabel, gbc);
        s = new ScrollPaneSSCCE((ArrayList<ImageIcon>) zoznam);
        fotkyTable = s.getTable();
        fotkyTable.addMouseListener(new MouseAdapter() {
            
            @Override
            public void mouseClicked(MouseEvent e) {
                TuraForm.this.zmenFotku(fotkyTable.getSelectedColumn());
            }
            
        });
        
        Dimension d = new Dimension(600, 130);
        s.setPreferredSize(d);
        s.setMaximumSize(d);

        // Fotky
        gbc.gridwidth = 3;
        gbc.gridheight = 1;
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(s, gbc);

        // Pohorie
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        JLabel pohorieLabel = new JLabel(tura.getPohorie());
        pohorieLabel.setFont(new Font("Calibri", Font.BOLD, 16));
        panel.add(pohorieLabel, gbc);

        // Ciel
        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        JLabel cielLabel = new JLabel(tura.getCiel());
        cielLabel.setFont(new Font("Calibri", Font.BOLD, 16));
        panel.add(cielLabel, gbc);

        // Obtiaznost
        gbc.gridx = 2;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        JLabel obtiaznostLabel = new JLabel("Obtiažnosť: " + tura.getObtiaznost());
        obtiaznostLabel.setFont(new Font("Calibri", Font.BOLD, 16));
        panel.add(obtiaznostLabel, gbc);

        // Casova Narocnost
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        JLabel casoNarocnostLabel = new JLabel("Časová náročnosť: " + tura.getCasovaNarocnost() + " hod.");
        casoNarocnostLabel.setFont(new Font("Calibri", Font.BOLD, 14));
        panel.add(casoNarocnostLabel, gbc);

        // Dlzka tury
        gbc.gridx = 3;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        String dlzka = "";
        if (tura.getDlzka() == 0) {
            dlzka = "Dĺžka: -- km";
        } else {
            dlzka = "Dĺžka: " + tura.getDlzka() + " km";
        }
        JLabel dlzkaLabel = new JLabel(dlzka);
        dlzkaLabel.setFont(new Font("Calibri", Font.BOLD, 14));
        panel.add(dlzkaLabel, gbc);

        // Popis
        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.ipady = 40;
        popisLabel = new JXLabel();
        vypis(this.spracujPopisDoListu(tury.dajPopis(idT)));
        popisLabel.setFont(new Font("Calibri", Font.BOLD, 14));
        popisLabel.setTextAlignment(JXLabel.TextAlignment.CENTER);
        popisLabel.setLineWrap(true);
        popisLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panel.add(popisLabel, gbc);
        gbc.ipady = 0;
        
        gbc.gridx = 1;
        gbc.gridy = 7;
        gbc.gridwidth = 1;
        JLabel popisTury = new JLabel("Popis:");
        d = new Dimension(175, 25);
        popisTury.setMinimumSize(d);
        popisTury.setPreferredSize(d);
        panel.add(popisTury, gbc);
        
        gbc.gridx = 2;
        gbc.gridy = 7;
        JLabel medzera = new JLabel();
        d = new Dimension(250, 25);
        medzera.setMinimumSize(d);
        medzera.setPreferredSize(d);
        panel.add(medzera, gbc);
        
        gbc.gridy = 7;
        gbc.gridx = 3;
        JLabel hodnotenieLabel = new JLabel("Hodnotenie:");
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(hodnotenieLabel, gbc);
        
        gbc.gridy = 7;
        gbc.gridx = 3;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(sr, gbc);

        // Detail tury
        gbc.gridx = 1;
        gbc.gridy = 8;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        d = new Dimension(600, 300);
        JTextArea detail = new JTextArea(tury.dajDetail(idT));
        detail.setEditable(false);
        detail.setLineWrap(true);
        JScrollPane scrollPopis = new JScrollPane(detail);
        scrollPopis.setPreferredSize(d);
        scrollPopis.setMaximumSize(d);
        scrollPopis.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPopis.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        panel.add(scrollPopis, gbc);
        
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.setContentPane(scrollPane);
        
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            
            @Override
            public void windowClosing(WindowEvent e) {
                long pocetHodnoteni = tura.getPocetHodnoteni();
                if (sr.getSelection() > 0) {
                    float rating = (tura.getHodnotenie() * pocetHodnoteni + sr.getSelection()) / (pocetHodnoteni + 1);
                    tury.ohodnotTuru(tura.getIdT(), rating, pocetHodnoteni + 1);
                }
                TuraForm.this.dispose();
                if (idU > -1) {
                    new UzivatelMenu(idU).setVisible(true);
                } else {
                    new FilterTurForm(idU).setVisible(true);
                }
            }
            
        });
        d = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((d.width - this.getSize().width) / 2, (d.height - this.getSize().height) / 2);
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
            /*            height = (int) (bimg.getIconHeight() / (4));
             width = bimg.getIconWidth() / 3;*/
            width = 600;
            height = 400;
            img = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            ImageIcon icon = new ImageIcon(img);
            upraveneFotky.add(icon);
        }
        return upraveneFotky;
    }
    
    public LinkedList<String> spracujPopisDoListu(String popis) {
        LinkedList<String> p = new LinkedList<String>();
        StringBuilder ret = new StringBuilder();
        for (int i = 0; i < popis.length(); i++) {
            if (popis.charAt(i) == '-') {
                p.add(ret.toString());
                ret.delete(0, ret.length());
            } else {
                ret.append(popis.charAt(i));
            }
        }
        p.add(ret.toString());
        return p;
    }
    
    public void vypis(LinkedList<String> bodyTury) {
        StringBuilder ret = new StringBuilder();
        int dlzka = 0;
        int pocetRiadkov = 0;
        for (String bod : bodyTury) {
            dlzka += bod.length();
            if (dlzka < 50) {
                if (ret.length() == 0) {
                    ret.append(bod);
                } else {
                    ret.append(" - " + bod);
                }
            } else {
                pocetRiadkov++;
                ret.append(" - ");
                ret.append('\n');
                ret.append(" - " + bod);
                dlzka = bod.length();
            }
        }
        Dimension d = new Dimension(550, pocetRiadkov * 25);
        popisLabel.setMinimumSize(d);
        popisLabel.setPreferredSize(d);
        popisLabel.setMaximumSize(d);
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
