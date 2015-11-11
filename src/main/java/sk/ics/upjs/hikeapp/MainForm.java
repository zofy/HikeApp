/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.ics.upjs.hikeapp;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListCellRenderer;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

/**
 *
 * @author Patrik
 */
public class MainForm extends javax.swing.JFrame {

    private MysqlTuraDaO tury = new MysqlTuraDaO();
    private int mouseOver = -1;

    public MainForm() {
        initComponents();
    }

    public MainForm(List<Tura> zoznamTur) {
        initComponents();
        turyList.setCellRenderer(new MyListCellRend());
        turyList.setListData(zoznamTur.toArray());
        turyList.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));;
        this.pack();
        this.setVisible(true);
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
    }

    final class MyListCellRend implements ListCellRenderer<Tura> {

        DefaultListCellRenderer dcr = new DefaultListCellRenderer();

        @Override
        public Component getListCellRendererComponent(JList<? extends Tura> list, Tura tura, int index, boolean isSelected, boolean cellHasFocus) {
            //delegat
            JLabel label = (JLabel) dcr.getListCellRendererComponent(list, tura, index, isSelected, cellHasFocus);
            //format textu v JLabel
            String html = "<html><table>\n"
                    +"<col width='150'> <col width='150'> <col width='1000'> <col width='150'>"
                    + "<tr><td align='right'>%s</td><td align='left'>%s hod.</td><td align='left'>%s km</td><td align='left'>level: %s</td></tr>\n"
                    + "<tr><td align='right'>%s</td><td align='left'>hodnotenie: %s</td><td align='left'>off track: %s</td></tr>\n"
                    + "</table></html>";
            label.setText(String.format(html, tura.getPohorie(),
                    tura.getCasovaNarocnost(), tura.getDlzka(), tura.getObtiaznost(), tura.getRocneObdobie(),
                    tura.getHodnotenie(), tura.isMimoChodnika()));
            Border border = BorderFactory.createLineBorder(Color.BLACK);
            label.setBorder(border);

            if (mouseOver == index && !isSelected) {
                label.setForeground(Color.red);
                label.setBackground(Color.lightGray);
            }
            /* nefunguje bo netbeans je debilny
             ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("resources/logo.jpg"));
             Image img = icon.getImage();
             label.setIcon(new ImageIcon(img.getScaledInstance(40, 40, 0)));
             */
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
            .addComponent(turyScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 385, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(turyScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 296, Short.MAX_VALUE)
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
