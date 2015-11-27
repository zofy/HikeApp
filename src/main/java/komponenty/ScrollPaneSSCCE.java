package komponenty;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.basic.*;
import javax.swing.table.DefaultTableCellRenderer;

public class ScrollPaneSSCCE extends JPanel {

    public ScrollPaneSSCCE(ArrayList<ImageIcon> zoznamFotiek) {
        setLayout(new BorderLayout());
        Dimension d = new Dimension(600, 150);
        this.setMinimumSize(d);
        /*BufferedImage logInObrazok1 = null;
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
        d.height, Image.SCALE_SMOOTH);
        Image scaledObrazok2 = logInObrazok2.getScaledInstance(65,
        d.height, Image.SCALE_SMOOTH);
        Image scaledObrazok3 = logInObrazok3.getScaledInstance(65,
        d.height, Image.SCALE_SMOOTH);
        ImageIcon img1 = new ImageIcon(scaledObrazok1);
        ImageIcon img2 = new ImageIcon(scaledObrazok2);
        ImageIcon img3 = new ImageIcon(scaledObrazok3);*/
        /* ArrayList<ImageIcon> zoznamFotiek = new ArrayList<ImageIcon>();
        zoznamFotiek.add(img1);
        zoznamFotiek.add(img2);
        zoznamFotiek.add(img3);*/
        JTable table = new JTable(1, zoznamFotiek.size());
        
        class ImageRenderer extends DefaultTableCellRenderer {

            JLabel lbl = new JLabel();

            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                lbl.setIcon((Icon) value);
                return lbl;
            }
        }
        
        table.setShowGrid(false);
        table.setTableHeader(null);
        table.setBackground(Color.white);
        table.setRowHeight(d.height);
        table.setDefaultRenderer(Object.class, new ImageRenderer());
        for (int i = 0; i < zoznamFotiek.size(); i++) {
            table.setValueAt(zoznamFotiek.get(i), 0, i);
        }
        
        /*for (int i = 0; i < 3; i++) {
         table.getColumnModel().getColumn(i).setPreferredWidth(50);
         table.getColumnModel().getColumn(i).setMaxWidth(50);
         }*/
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        add(scrollPane);

        JScrollBar horizontal = scrollPane.getHorizontalScrollBar();

        BasicArrowButton west = new BasicArrowButton(BasicArrowButton.WEST);

        west.setAction(
                new ActionMapAction("", horizontal, "negativeUnitIncrement"));
        add(west, BorderLayout.WEST);

        BasicArrowButton east = new BasicArrowButton(BasicArrowButton.EAST);

        east.setAction(
                new ActionMapAction("", horizontal, "positiveUnitIncrement"));
        add(east, BorderLayout.EAST);

    }

    /*    private static void createAndShowUI() {
    JFrame frame = new JFrame("ScrollPaneSSCCE");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    ScrollPaneSSCCE s = new ScrollPaneSSCCE();
    frame.add(s, BorderLayout.SOUTH);
    frame.setSize(400, 300);
    frame.setLocationByPlatform(true);
    frame.setVisible(true);
    frame.pack();
    }
    
    public static void main(String[] args) {
    EventQueue.invokeLater(new Runnable() {
    public void run() {
    createAndShowUI();
    }
    });
    }*/
}
