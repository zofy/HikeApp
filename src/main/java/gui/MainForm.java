package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import komponenty.StarRater;
import sk.ics.upjs.hikeapp.Tura;
import sk.ics.upjs.hikeapp.TuraDaO;
import sk.ics.upjs.hikeapp.DaOFactory;

public class MainForm extends javax.swing.JFrame {

    private TuraDaO tury;
    private int mouseOver = -1;
    private ImageIcon img1;
    private ImageIcon img2;
    private ImageIcon img3;
    private ImageIcon img4;
    private ImageIcon img5;
    private int level;
    private String offTrack;
    private long idU;
    // rozlisuje ci idem turu prezerat alebo upravovat
    private int rozlisovacka;
    private StarRater sr;

    public MainForm() {
        initComponents();
    }

    public MainForm(final List<Tura> zoznamTur, long userId, int rozlisenie) {
        initComponents();
        this.setTitle("Zoznam túr");
        idU = userId;
        rozlisovacka = rozlisenie;
        sr = new StarRater(5, 0, 0);
        sr.setVisible(true);
        this.setMinimumSize(new Dimension(500, 250));
        this.setPreferredSize(new Dimension(500, 250));
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                MainForm.this.dispose();
                if (idU > -1) {
                    new UzivatelMenu(idU).setVisible(true);
                } else {
                    new FilterTurForm(idU).setVisible(true);
                }
            }

        });
        this.addComponentListener(new ComponentAdapter() {

            @Override
            public void componentResized(ComponentEvent e) {
                Dimension sz = e.getComponent().getSize();
                if (sz.width != 500) {
                    e.getComponent().setSize(500, sz.height);
                    MainForm.this.repaint();
                    sz = Toolkit.getDefaultToolkit().getScreenSize();
                    int height = sz.height;
                    int width = sz.width;
                    MainForm.this.setLocation((-MainForm.this.getSize().width + width) / 2, (-MainForm.this.getSize().height + height) / 2);
                }
            }

        });
        //this.setResizable(false);
        tury = DaOFactory.INSTANCE.getTuraDaO();
        turyList.setCellRenderer(new MyListCellRend());
        //turyScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        turyList.setListData(zoznamTur.toArray());
        turyList.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));;
        //this.pack();
        //this.setVisible(true);
        turyList.addMouseMotionListener(new MouseMotionAdapter() {

            @Override
            public void mouseMoved(MouseEvent e) {
                mouseOver = turyList.locationToIndex(new Point(e.getX(), e.getY()));
                repaint();
            }
        });
        turyList.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                MainForm.this.dispose();
                Tura t = zoznamTur.get(turyList.getSelectedIndex());
                if (rozlisovacka > -1) {
                    new UpravaForm(idU, t.getIdT()).setVisible(true);
                } else {
                    new TuraForm(t.getIdT(), idU).setVisible(true);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                mouseOver = -1;
                repaint();
            }

        });
        pack();
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int height = dim.height;
        int width = dim.width;
        this.setLocation((-this.getSize().width + width) / 2, (-this.getSize().height + height) / 2);

        // nacitanie obrazkov
        BufferedImage level1 = null;
        BufferedImage level2 = null;
        BufferedImage level3 = null;
        BufferedImage level4 = null;
        BufferedImage level5 = null;

        try {
            level1 = ImageIO.read(new File("C:\\logo\\lvl1.png"));
            level2 = ImageIO.read(new File("C:\\logo\\lvl2.png"));
            level3 = ImageIO.read(new File("C:\\logo\\lvl3.png"));
            level4 = ImageIO.read(new File("C:\\logo\\lvl4.png"));
            level5 = ImageIO.read(new File("C:\\logo\\lvl5.png"));

        } catch (IOException ex) {
            System.err.println("Neni obrazok!");
        }
        Image scaledObrazok1 = level1.getScaledInstance(65,
                50, Image.SCALE_SMOOTH);
        Image scaledObrazok2 = level2.getScaledInstance(65,
                50, Image.SCALE_SMOOTH);
        Image scaledObrazok3 = level3.getScaledInstance(65,
                50, Image.SCALE_SMOOTH);
        Image scaledObrazok4 = level4.getScaledInstance(50,
                50, Image.SCALE_SMOOTH);
        Image scaledObrazok5 = level5.getScaledInstance(50,
                50, Image.SCALE_SMOOTH);
        //l.setIcon(new ImageIcon(scaledObrazok));
        img1 = new ImageIcon(scaledObrazok1);
        img2 = new ImageIcon(scaledObrazok2);
        img3 = new ImageIcon(scaledObrazok3);
        img4 = new ImageIcon(scaledObrazok4);
        img5 = new ImageIcon(scaledObrazok5);
    }

    final class MyListCellRend implements ListCellRenderer<Tura> {

        DefaultListCellRenderer dcr = new DefaultListCellRenderer();

        @Override
        public Component getListCellRendererComponent(JList<? extends Tura> list, Tura tura, int index, boolean isSelected, boolean cellHasFocus) {
            JPanel panel = new JPanel(new GridBagLayout());
            panel.setPreferredSize(new Dimension(450, 70));
            panel.setBackground(Color.white);
            panel.setBorder(BorderFactory.createLineBorder(Color.black));
            panel.setLayout(new GridBagLayout());

            // nacitanie obrazku do labelu
            JLabel l = null;
            level = tura.getObtiaznost();
            if (level == 1) {
                l = new JLabel(img1);
            } else if (level == 2) {
                l = new JLabel(img2);
            } else if (level == 3) {
                l = new JLabel(img3);
            } else if (level == 4) {
                l = new JLabel(img4);
            } else if (level == 5) {
                l = new JLabel(img5);
            }
            GridBagConstraints gbc = new GridBagConstraints();

            gbc.insets = new Insets(3, 3, 3, 3);
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridheight = 2;
            gbc.anchor = GridBagConstraints.CENTER;
            panel.add(l, gbc);

            gbc.gridwidth = 1;
            gbc.gridheight = 1;
            //gbc.anchor = GridBagConstraints.CENTER;

            gbc.gridx = 1;
            gbc.gridy = 0;
            //gbc.fill = GridBagConstraints.HORIZONTAL;
            //gbc.ipady = 5;
            l = new JLabel(tura.getPohorie());
            l.setMinimumSize(new Dimension(100, 30));
            l.setPreferredSize(new Dimension(100, 30));
            l.setHorizontalAlignment(SwingConstants.CENTER);
            panel.add(l, gbc);
            gbc.gridy++;
            l = new JLabel(tura.getRocneObdobie());
            l.setMinimumSize(new Dimension(100, 30));
            l.setPreferredSize(new Dimension(100, 30));
            l.setHorizontalAlignment(SwingConstants.CENTER);
            panel.add(l, gbc);

            gbc.gridx = 2;
            gbc.gridy = 0;
            gbc.insets = new Insets(3, 5, 3, 3);
            l = new JLabel(tura.getCiel());
            l.setMinimumSize(new Dimension(115, 30));
            l.setPreferredSize(new Dimension(115, 30));
            panel.add(l, gbc);
            gbc.gridy++;

            //nastavenie StarRatera
            sr.setRating(tura.getHodnotenie());
            gbc.fill = GridBagConstraints.BOTH;
            gbc.anchor = GridBagConstraints.SOUTHWEST;
            gbc.insets = new Insets(9, 3, 0, 3);
            JLabel hodnotenieLabel = new JLabel("(" + String.valueOf(tura.getPocetHodnoteni()) + "x" + ")");
            panel.add(sr, gbc);
            gbc.anchor = GridBagConstraints.EAST;
            gbc.fill = GridBagConstraints.NONE;
            gbc.insets = new Insets(3, 3, 3, 3);
            panel.add(hodnotenieLabel, gbc);

            gbc.gridx = 3;
            gbc.gridy = 0;
            gbc.anchor = GridBagConstraints.CENTER;
            gbc.insets = new Insets(3, 3, 3, 3);
            //gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.weightx = 0.1;
            l = new JLabel(String.valueOf(tura.getCasovaNarocnost()) + " hod.");
            l.setMinimumSize(new Dimension(70, 30));
            l.setPreferredSize(new Dimension(70, 30));
            l.setHorizontalAlignment(JLabel.CENTER);
            panel.add(l, gbc);
            gbc.gridy++;

            if (tura.getDlzka() != 0) {
                l = new JLabel(String.valueOf(tura.getDlzka()) + " km");
            } else {
                l = new JLabel("---" + " km");
            }
            l.setHorizontalAlignment(JLabel.CENTER);
            l.setMinimumSize(new Dimension(70, 30));
            l.setPreferredSize(new Dimension(70, 30));
            panel.add(l, gbc);

            gbc.gridx = 4;
            gbc.gridy = 0;
            l = new JLabel("level: " + String.valueOf(tura.getObtiaznost()));
            l.setMinimumSize(new Dimension(70, 30));
            l.setPreferredSize(new Dimension(70, 30));
            panel.add(l, gbc);
            gbc.gridy++;
            if (tura.isMimoChodnika()) {
                offTrack = "<html>off track: <img src='file:C:\\logo\\yes.png'></html>";
            } else {
                offTrack = "<html>off track: <img src='file:C:\\logo\\no.png'></html>";
            }
            l = new JLabel(offTrack);
            l.setMinimumSize(new Dimension(70, 30));
            l.setPreferredSize(new Dimension(70, 30));
            panel.add(l, gbc);

            //delegat
            /*  JLabel label = (JLabel) dcr.getListCellRendererComponent(list, tura, index, isSelected, cellHasFocus);
             // Nastavit na labeli GridLayout a potom vlozit text aj star rating
            
             //format textu v JLabel
             String html = "<html><table>\n"
             + "<tr>"
             + "<td rowspan='2'><img src=%s></td>"
             + "<td style=width:80px align='left'>%s</td><td align='left'>%s hod.</td><td style=width:75px align='left'>%s km</td><td align='center'>level: %s</td>"
             + "</tr>\n"
             + "<tr><td align='center'>%s</td><td align='left'>hodnotenie: %s</td><td align='left'>off track: <img src=%s></td></tr>\n"
             + "</table></html>";
             String mimoChodnik;
             if (tura.isMimoChodnika()) {
             mimoChodnik = "file:C:\\yes.png";
             } else {
             mimoChodnik = "file:C:\\no.png";
             }
             String levelLogo = "file:C:\\logo.jpg";
             label.setText(String.format(html, levelLogo, tura.getPohorie(),
             tura.getCasovaNarocnost(), tura.getDlzka(), tura.getObtiaznost(), tura.getRocneObdobie(),
             tura.getHodnotenie(), mimoChodnik));*/

            /*DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
            
             public Component getTableCellRendererComponent(JTable table, Object value,
             boolean isSelected, boolean hasFocus, int row, int column) {
             if (row == 1) {
             if (column == 0) {
             setHorizontalAlignment(SwingConstants.RIGHT);
             } else if (column == 3) {
             setHorizontalAlignment(SwingConstants.CENTER);
             } else {
             setHorizontalAlignment(SwingConstants.CENTER);
             }
             setVerticalAlignment(SwingConstants.NORTH);
             } else if (row == 0) {
             if (column == 0) {
             setHorizontalAlignment(SwingConstants.LEFT);
             } else {
             setHorizontalAlignment(SwingConstants.CENTER);
             }
             }
             return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
             }
             };
             JXTable table = new JXTable(2, 5);
             table.setShowGrid(false);
             table.setTableHeader(null);
             table.setBackground(Color.white);
             table.setValueAt("<html><img src='file:C:\\logo.jpg'></html>", 0, 0);
             table.setValueAt(tura.getPohorie(), 0, 1);
             table.setValueAt(tura.getCiel(), 0, 2);
             table.setValueAt(tura.getCasovaNarocnost() + " hod.", 0, 3);
             table.setValueAt("level:  " + tura.getObtiaznost(), 0, 4);
             table.setValueAt(tura.getRocneObdobie(), 1, 1);
             table.setValueAt("hodnotenie: " + tura.getHodnotenie(), 1, 2);
             table.setValueAt(tura.getDlzka() + " km", 1, 3);
             String offTrack;
             if (tura.isMimoChodnika()) {
             offTrack = "<html>off track: <img src='file:C:\\yes.png'></html>";
             } else {
             offTrack = "<html>off track: <img src='file:C:\\no.png'></html>";
             }
             table.setValueAt(offTrack, 1, 4);
             table.setRowHeight(30);
             table.getColumnModel().getColumn(3).setPreferredWidth(40);
             table.setDefaultRenderer(Object.class, renderer);
             //table.getColumnModel().getColumn(0).setPreferredWidth(50);*/
            if (mouseOver == index && !isSelected) {
                panel.setBackground(Color.decode("#D7FFB8"));
            }
            return panel;
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
        java.awt.GridBagConstraints gridBagConstraints;

        turyScrollPane = new javax.swing.JScrollPane();
        turyList = new javax.swing.JList();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new java.awt.GridBagLayout());

        turyList.setCellRenderer(new MyListCellRend());
        turyList.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        turyScrollPane.setViewportView(turyList);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 362;
        gridBagConstraints.ipady = 273;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(turyScrollPane, gridBagConstraints);

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
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainForm().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList turyList;
    private javax.swing.JScrollPane turyScrollPane;
    // End of variables declaration//GEN-END:variables
}
