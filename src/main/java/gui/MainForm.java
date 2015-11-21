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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import org.jdesktop.swingx.JXTable;
import sk.ics.upjs.hikeapp.Tura;
import sk.ics.upjs.hikeapp.TuraDaO;
import sk.ics.upjs.hikeapp.TuraDaOFactory;

public class MainForm extends javax.swing.JFrame {

    private TuraDaO tury;
    private int mouseOver = -1;
    private ImageIcon img1;
    private ImageIcon img2;
    private ImageIcon img3;
    private int level;

    public MainForm() {
        initComponents();
    }

    public MainForm(List<Tura> zoznamTur) {
        initComponents();
        this.setTitle("Hike");
        tury = TuraDaOFactory.INSTANCE.getTuraDaO();
        turyList.setCellRenderer(new MyListCellRend());
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
        BufferedImage logInObrazok1 = null;
        BufferedImage logInObrazok2 = null;
        BufferedImage logInObrazok3 = null;

        try {
            logInObrazok1 = ImageIO.read(new File("C:\\logo\\lvl1.png"));
            logInObrazok2 = ImageIO.read(new File("C:\\logo\\lvl2.png"));
            logInObrazok3 = ImageIO.read(new File("C:\\logo\\lvl3.png"));
        } catch (IOException ex) {
            System.err.println("Neni obrazok!");
        }
        Image scaledObrazok1 = logInObrazok1.getScaledInstance(65,
                50, Image.SCALE_SMOOTH);
        Image scaledObrazok2 = logInObrazok2.getScaledInstance(65,
                50, Image.SCALE_SMOOTH);
        Image scaledObrazok3 = logInObrazok3.getScaledInstance(65,
                50, Image.SCALE_SMOOTH);
        //l.setIcon(new ImageIcon(scaledObrazok));
        img1 = new ImageIcon(scaledObrazok1);
        img2 = new ImageIcon(scaledObrazok2);
        img3 = new ImageIcon(scaledObrazok3);
    }

    final class MyListCellRend implements ListCellRenderer<Tura> {

        DefaultListCellRenderer dcr = new DefaultListCellRenderer();

        @Override
        public Component getListCellRendererComponent(JList<? extends Tura> list, Tura tura, int index, boolean isSelected, boolean cellHasFocus) {
            JPanel label = new JPanel(new GridBagLayout());
            label.setBackground(Color.white);
            label.setLayout(new GridBagLayout());
            // nacitanie obrazku do labelu
            JLabel l = null;
            level = tura.getObtiaznost();
            if (level == 1) {
                l = new JLabel(img1);
            } else if (level == 2) {
                l = new JLabel(img2);
            } else if (level == 3) {
                l = new JLabel(img3);
            } else {
                l = new JLabel(img3);
            }
            GridBagConstraints gbc = new GridBagConstraints();

            gbc.insets = new Insets(2, 2, 2, 2);
            gbc.gridx = 0;
            gbc.gridy = 0;
            //gbc.ipadx = 50;
            //gbc.ipady = 50;
            gbc.gridheight = 2;
            //l.setBorder(BorderFactory.createLineBorder(Color.black));
            label.add(l, gbc);

            gbc.ipadx = 0;
            gbc.ipady = 0;
            gbc.gridwidth = 1;
            gbc.gridheight = 1;

            gbc.gridx = 1;
            gbc.gridy = 0;
            gbc.fill = GridBagConstraints.BOTH;
            gbc.ipady = 10;
            l = new JLabel(tura.getPohorie());
            l.setHorizontalAlignment(SwingConstants.RIGHT);
            //l.setBorder(BorderFactory.createLineBorder(Color.black));
            label.add(l, gbc);
            gbc.gridy++;
            l = new JLabel(tura.getRocneObdobie());
            l.setHorizontalAlignment(SwingConstants.RIGHT);
            //l.setBorder(BorderFactory.createLineBorder(Color.black));
            label.add(l, gbc);
            // vynulovat
            gbc.ipadx = 0;

            gbc.gridx = 2;
            gbc.gridy = 0;
            //gbc.anchor = GridBagConstraints.WEST;
            l = new JLabel(tura.getCiel());
            //l.setBorder(BorderFactory.createLineBorder(Color.black));
            label.add(l, gbc);
            gbc.gridy++;
            l = new JLabel("hodnotenie: " + String.valueOf(tura.getHodnotenie()));
            //l.setBorder(BorderFactory.createLineBorder(Color.black));
            label.add(l, gbc);

            gbc.gridx = 3;
            gbc.gridy = 0;
            //gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.weightx = 1;
            l = new JLabel(String.valueOf(tura.getCasovaNarocnost()) + " hod.");
            //l.setBorder(BorderFactory.createLineBorder(Color.black));
            label.add(l, gbc);
            gbc.gridy++;
            l = new JLabel(String.valueOf(tura.getDlzka()) + " km");
            //l.setBorder(BorderFactory.createLineBorder(Color.black));
            label.add(l, gbc);

            gbc.gridx = 4;
            gbc.gridy = 0;
            l = new JLabel("level: " + String.valueOf(tura.getObtiaznost()));
            //l.setBorder(BorderFactory.createLineBorder(Color.black));
            label.add(l, gbc);
            gbc.gridy++;
            l = new JLabel("off track: " + String.valueOf(tura.isMimoChodnika()));
            //l.setBorder(BorderFactory.createLineBorder(Color.black));
            label.add(l, gbc);

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
                label.setForeground(Color.red);
                label.setBackground(new Color(175, 238, 238));
            }
            return label;
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

        turyScrollPane = new javax.swing.JScrollPane();
        turyList = new javax.swing.JList();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        turyList.setCellRenderer(new MyListCellRend());
        turyList.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        turyScrollPane.setViewportView(turyList);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(turyScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 385, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(turyScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE)
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
