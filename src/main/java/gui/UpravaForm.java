package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.plaf.basic.BasicArrowButton;
import komponenty.FileChooser;
import komponenty.ImageFileView;
import komponenty.ImageFilter;
import komponenty.ImagePreview;
import komponenty.ScrollPaneSSCCE;
import komponenty.StarRater;
import org.jdesktop.swingx.JXLabel;
import sk.ics.upjs.hikeapp.DaOFactory;
import sk.ics.upjs.hikeapp.Fotka;
import sk.ics.upjs.hikeapp.FotkaDaO;
import sk.ics.upjs.hikeapp.Tura;
import sk.ics.upjs.hikeapp.TuraDaO;

public class UpravaForm extends javax.swing.JFrame implements ActionListener {

    private JTextField nazovField = new JTextField();
    private JTextField bodyTuryField = new JTextField();
    private JTextField pohorieField = new JTextField();
    private JTextField roField = new JTextField();
    private JTextField casField = new JTextField();
    private JTextField dlzkaField = new JTextField();
    private JTextField cielField = new JTextField();
    private JCheckBox offTrackBox = new JCheckBox();
    private JXLabel body = new JXLabel();
    private BasicArrowButton east = new BasicArrowButton(BasicArrowButton.EAST);
    private BasicArrowButton west = new BasicArrowButton(BasicArrowButton.WEST);
    private JTextArea popis = new JTextArea();
    private JLabel fotkaLabel = new JLabel();
    private ScrollPaneSSCCE fotkyTable;
    private SpinnerModel sm = new SpinnerNumberModel(1, 1, 5, 1);
    private JSpinner spin = new JSpinner(sm);
    private JTextArea fotkyArea = new JTextArea(5, 10);
    private JButton upravButton = new JButton("Uprav");
    private JButton vymazFotkuButton = new JButton("Zmaž fotku");
    private JButton vymazTuruButton = new JButton("Vymaž túru");
    private JButton pridajFotkyButton = new JButton("Pridaj fotky");
    private FileChooser fc = new FileChooser();
    private List<File> fotky = new LinkedList<File>();
    private StarRater sr = new StarRater(5, 0, 0);

    private TuraDaO tury = DaOFactory.INSTANCE.getTuraDaO();
    private FotkaDaO fotos = DaOFactory.INSTANCE.getFotky();

    private LinkedList<String> bodyTury = new LinkedList<String>();
    private StringBuilder ret = new StringBuilder();

    private int idx;
    private String bod;
    private boolean vypinac1 = false;
    private boolean vypinac2 = true;
    private Dimension velkostBodyLabel = new Dimension();

    private StringBuilder chybovyVypis = new StringBuilder();
    private int pocitadloFotiek = 1;

    private long IdU;
    private long idT;
    private Tura turaNaUpravu;
    private JTable table;
    private int idxFotkyNaMazanie;
    private ArrayList<Fotka> zoznamFotiek;
    private ArrayList<ImageIcon> zoznamObrazkov;

    public UpravaForm() {
        initComponents();

    }

    public UpravaForm(long userId, long idTury) {
        initComponents();
        Dimension frameSize = new Dimension(450, 300);
        this.setMinimumSize(frameSize);
        this.setPreferredSize(frameSize);
        IdU = userId;
        idT = idTury;
        turaNaUpravu = tury.dajTuru(idT);
        bodyTury = turaNaUpravu.getPopis();
        vypis(bodyTury);
        idx = bodyTury.size() - 1;
        zoznamObrazkov = (ArrayList<ImageIcon>) spracujFotky(fotos.dajFotkyDanejTury(idT));
        fotkyTable = new ScrollPaneSSCCE(zoznamObrazkov);
        zoznamFotiek = (ArrayList<Fotka>) fotos.dajAtributyFotkyDanejTury(idT);

        fotkyTable = new ScrollPaneSSCCE(zoznamObrazkov);
        table = fotkyTable.getTable();
        table.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                idxFotkyNaMazanie = table.getSelectedColumn();
                System.out.println(idxFotkyNaMazanie);
            }

        });

        inicializujSa();

        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                UpravaForm.this.dispose();
                new UzivatelMenu(IdU).setVisible(true);
            }

        });
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((dim.width - this.getSize().width) / 2, (dim.height - this.getSize().height) / 2);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(pridajFotkyButton)) {
            fc.addChoosableFileFilter(new ImageFilter());
            fc.setAcceptAllFileFilterUsed(false);

            //Add custom icons for file types.
            fc.setFileView(new ImageFileView());

            //Add the preview pane.
            fc.setAccessory(new ImagePreview(fc));
            fc.setMultiSelectionEnabled(true);
            int returnVal = fc.showDialog(UpravaForm.this, "Vlož");

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File[] obrazky = fc.getSelectedFiles();
                for (File obr : obrazky) {
                    fotky.add(obr);
                    fotkyArea.append(pocitadloFotiek + ".) " + obr.getName()
                            + "." + "\n");
                    pocitadloFotiek++;
                }
            }

            fotkyArea.setCaretPosition(fotkyArea.getDocument().getLength());

            //Reset the file chooser for the next time it's shown.
            fc.setSelectedFile(null);
        }
        if (e.getSource().equals(upravButton)) {
            if (nazovField.getText().trim().isEmpty()) {
                chybovyVypis.append("Zadaj názov túry!\n");
            }
            if (bodyTury.isEmpty()) {
                chybovyVypis.append("Zadaj body túry!\n");
            }
            if (pohorieField.getText().trim().isEmpty()) {
                chybovyVypis.append("Zadaj názov pohoria!\n");
            }
            if (roField.getText().trim().isEmpty()) {
                chybovyVypis.append("Zadaj ročné obdobie!\n");
            }
            if (cielField.getText().trim().isEmpty()) {
                chybovyVypis.append("Zadaj cieľ túry!\n");
            }
            if (!overPolicko(casField.getText())) {
                chybovyVypis.append("Nesprávny formát pre trvanie túry!");
            }
            if (casField.getText().trim().isEmpty()) {
                chybovyVypis.append("Zadaj časovú náročnosť túry!\n");
            }
            if (!dlzkaField.getText().isEmpty()) {
                if (!overPolicko(dlzkaField.getText())) {
                    chybovyVypis.append("Nesprávny formát pre dĺžku túry!");
                }
            }
            if (chybovyVypis.length() != 0) {
                JOptionPane.showMessageDialog(this, chybovyVypis.toString());
                chybovyVypis.delete(0, chybovyVypis.length());
                return;
            }

            turaNaUpravu.setPohorie(pohorieField.getText());
            turaNaUpravu.setRocneObdobie(roField.getText());
            turaNaUpravu.setObtiaznost((int) spin.getValue());
            turaNaUpravu.setCasovaNarocnost(Double.parseDouble(casField.getText().trim().replaceAll(",", ".")));
            if (!dlzkaField.getText().isEmpty()) {
                turaNaUpravu.setDlzka(Double.parseDouble(dlzkaField.getText().trim().replaceAll(",", ".")));
            }
            // nastavit hodnotenie
            if (sr.getSelection() > 0) {
                turaNaUpravu.setHodnotenie(sr.getSelection());
                turaNaUpravu.setPocetHodnoteni(turaNaUpravu.getPocetHodnoteni() + 1);
            }
            //
            turaNaUpravu.setMimoChodnika(offTrackBox.isSelected());
            turaNaUpravu.setCiel(cielField.getText());
            turaNaUpravu.setNazov(nazovField.getText());
            turaNaUpravu.setPopis(bodyTury);
            turaNaUpravu.setDetail(popis.getText());

            tury.upravTuru(turaNaUpravu);
            // este pridat aj fotky
            if (!fotky.isEmpty()) {
                fotos.pridajFotky(fotky, turaNaUpravu.getIdT());
            }

            this.dispose();
            new UzivatelMenu(IdU).setVisible(true);
        }
        if (e.getSource().equals(east)) {
            if (vypinac1) {
                idx = idx + 1;
                vypinac1 = false;
            }
            bod = bodyTuryField.getText();

            if (idx != bodyTury.size() - 1) {
                if (bod.equals("")) {
                    bodyTury.remove(idx);
                } else {
                    bodyTury.set(idx, bod);
                }
                idx++;
                bodyTuryField.setText(bodyTury.get(idx));
            } else {
                if (vypinac2) {
                    if (!bodyTuryField.getText().isEmpty()) {
                        bodyTury.add(bod);
                        bodyTuryField.setText("");
                        idx++;
                    }
                } else {
                    if (bod.equals(bodyTury.get(bodyTury.size() - 1))) {
                        bodyTuryField.setText("");
                        vypinac2 = true;
                    } else if (!bod.equals(bodyTury.get(bodyTury.size() - 1))) {
                        bodyTury.set(bodyTury.size() - 1, bod);
                        bodyTuryField.setText("");
                        vypinac2 = true;
                    }
                }
            }
            vypis(bodyTury);
            //System.out.println(idx);
        }
        if (e.getSource().equals(west)) {
            if (bodyTury.size() != 0) {
                vypinac1 = true;
                vypinac2 = false;
                bod = bodyTuryField.getText();
                if (idx != bodyTury.size() - 1) {
                    if (bod.equals("")) {
                        bodyTury.remove(idx + 1);
                    } else {
                        bodyTury.set(idx + 1, bod);
                    }
                }
                if (idx == -1) {
                    idx++;
                }
                idx--;
                bodyTuryField.setText(bodyTury.get(idx + 1));
                vypis(bodyTury);
            }
        }
        if (e.getSource().equals(vymazFotkuButton)) {
            fotos.vymazFotku(idT, zoznamFotiek.get(idxFotkyNaMazanie).getId());
            table.getModel().setValueAt(null, 0, idxFotkyNaMazanie);
            table.repaint();
        }
        if (e.getSource().equals(vymazTuruButton)) {
            tury.vymazTuru(turaNaUpravu);
            this.dispose();
            new UzivatelMenu(IdU).setVisible(true);
        }
    }

    public boolean overPolicko(String text) {
        String s = text.trim();
        for (int i = 0; i < s.length(); i++) {
            if ((int) s.charAt(i) > 57) {
                return false;
            } else if ((int) s.charAt(i) < 48) {
                if ((int) s.charAt(i) != 46 && (int) s.charAt(i) != 44) {
                    return false;
                }
            }
        }
        return true;
    }

    public void vypis(LinkedList<String> list) {
        int dlzka = 0;
        int pocetRiadkov = 0;
        for (String bod : bodyTury) {
            dlzka += bod.length();
            //System.out.println(dlzka);
            if (dlzka < 50) {
                if (ret.length() == 0) {
                    ret.append(bod);
                } else {
                    ret.append("-" + bod);
                }
            } else {
                pocetRiadkov++;
                ret.append("-");
                ret.append('\n');
                ret.append("-" + bod);
                dlzka = bod.length();
            }
        }
        velkostBodyLabel.setSize(600, pocetRiadkov * 25);
        body.setMinimumSize(velkostBodyLabel);
        body.setText(ret.toString());
        ret.delete(0, ret.length());
    }

    public void inicializujSa() {
        setTitle("Nová túra");
        upravButton.addActionListener(this);
        vymazFotkuButton.addActionListener(this);
        vymazTuruButton.addActionListener(this);
        pridajFotkyButton.addActionListener(this);
        fotkyArea.setEditable(false);
        body.setLineWrap(true);
        JScrollPane scrollFotkyArea = new JScrollPane(fotkyArea);
        scrollFotkyArea.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollFotkyArea.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        Dimension d = new Dimension(150, 25);
        nazovField.setPreferredSize(d);
        nazovField.setMinimumSize(d);
        bodyTuryField.setPreferredSize(d);
        bodyTuryField.setMinimumSize(d);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(3, 3, 3, 3);

        BufferedImage logInObrazok1 = null;

        try {
            logInObrazok1 = ImageIO.read(new File("C:\\logo\\mm.png"));

        } catch (IOException ex) {
            System.err.println("Neni obrazok!");
        }
        Image scaledObrazok1 = logInObrazok1.getScaledInstance(600,
                240, Image.SCALE_SMOOTH);

        fotkaLabel.setIcon(new ImageIcon(scaledObrazok1));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 6;
        //fotkaLabel.setBorder(BorderFactory.createLineBorder(Color.black));
        panel.add(fotkaLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        d = new Dimension(600, 130);
        fotkyTable.setPreferredSize(d);
        panel.add(fotkyTable, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 5;
        gbc.gridy = 2;
        panel.add(vymazFotkuButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        d = new Dimension(100, 25);
        gbc.anchor = GridBagConstraints.CENTER;
        JLabel nazov = new JLabel("Názov túry");
        //nazov.setBorder(BorderFactory.createLineBorder(Color.black));
        nazov.setMaximumSize(d);
        panel.add(nazov, gbc);

        JLabel bodyTury = new JLabel("Body túry");
        //bodyTury.setBorder(BorderFactory.createLineBorder(Color.black));
        bodyTury.setMaximumSize(d);
        gbc.gridy++;
        panel.add(bodyTury, gbc);

        gbc.gridwidth = 2;
        gbc.gridy = 2;
        gbc.gridx = 2;
        gbc.ipadx = 20;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        nazovField.setText(turaNaUpravu.getNazov());
        panel.add(nazovField, gbc);
        gbc.gridy++;
        gbc.insets = new Insets(3, 3, 3, 1);
        panel.add(bodyTuryField, gbc);
        gbc.ipadx = 0;

        gbc.insets = new Insets(3, 3, 3, 3);
        gbc.gridwidth = 1;

        gbc.fill = GridBagConstraints.NONE;
        gbc.gridy = 3;
        gbc.gridx = 2;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(west, gbc);
        west.addActionListener(this);

        gbc.gridy = 3;
        gbc.gridx = 3;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(3, 0, 3, 3);
        panel.add(east, gbc);
        east.addActionListener(this);

        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(3, 3, 3, 3);

        gbc.gridy = 4;
        gbc.gridx = 1;
        gbc.ipady = 50;
        //d.setSize(350, 150);
        body.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        //body.setPreferredSize(d);
        //body.setMinimumSize(d);
        gbc.gridwidth = 4;
        gbc.fill = GridBagConstraints.BOTH;
        body.setFont(new Font("Calibri", Font.BOLD, 14));
        body.setTextAlignment(JXLabel.TextAlignment.CENTER);
        panel.add(body, gbc);
        gbc.ipady = 0;

        gbc.gridwidth = 1;

        gbc.gridy = 5;
        gbc.gridx = 0;
        JLabel pohorie = new JLabel("Pohorie");
        //pohorie.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panel.add(pohorie, gbc);

        gbc.gridy = 6;
        gbc.gridx = 0;
        JLabel ro = new JLabel("Ročné obdobie");
        //ro.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panel.add(ro, gbc);

        gbc.gridy = 6;
        gbc.gridx = 1;
        d.setSize(100, 25);
        roField.setText(turaNaUpravu.getRocneObdobie());
        roField.setPreferredSize(d);
        panel.add(roField, gbc);

        gbc.gridy = 5;
        gbc.gridx = 1;
        d.setSize(100, 25);
        pohorieField.setPreferredSize(d);
        pohorieField.setText(turaNaUpravu.getPohorie());
        panel.add(pohorieField, gbc);

        gbc.gridy = 5;
        gbc.gridx = 3;
        gbc.ipadx = 30;
        d.setSize(115, 25);
        cielField.setPreferredSize(d);
        cielField.setText(turaNaUpravu.getCiel());
        panel.add(cielField, gbc);
        gbc.ipadx = 0;

        gbc.gridy = 5;
        gbc.gridx = 2;
        JLabel ciel = new JLabel("Cieľ");
        //ciel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panel.add(ciel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        panel.add(pridajFotkyButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        gbc.gridheight = 2;
        //gbc.ipady = 40;
        gbc.fill = GridBagConstraints.BOTH;
        panel.add(scrollFotkyArea, gbc);
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.ipady = 0;

        gbc.gridy = 6;
        gbc.gridx = 2;
        JLabel level = new JLabel("Obtiažnosť");
        //level.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panel.add(level, gbc);

        gbc.gridy = 6;
        gbc.gridx = 3;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.WEST;
        spin.setValue(turaNaUpravu.getObtiaznost());
        panel.add(spin, gbc);

        gbc.gridy = 6;
        gbc.gridx = 3;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel off = new JLabel("Off track        ");
        //off.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panel.add(off, gbc);

        gbc.gridy = 6;
        gbc.gridx = 3;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST;
        offTrackBox.setSelected(turaNaUpravu.isMimoChodnika());
        panel.add(offTrackBox, gbc);

        gbc.gridy = 5;
        gbc.gridx = 4;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.ipadx = 10;
        gbc.fill = GridBagConstraints.BOTH;
        JLabel cas = new JLabel("Trvanie (hod.)");
        //cas.setHorizontalAlignment(SwingConstants.RIGHT);
        //cas.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panel.add(cas, gbc);

        gbc.gridy = 6;
        gbc.gridx = 4;
        JLabel dlzka = new JLabel("Dĺžka (km)");
        //dlzka.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        //dlzka.setHorizontalAlignment(SwingConstants.RIGHT);
        panel.add(dlzka, gbc);

        gbc.ipadx = 20;
        gbc.gridy = 5;
        gbc.gridx = 5;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        casField = new JTextField();
        d = new Dimension(15, 24);
        casField.setMaximumSize(d);
        casField.setPreferredSize(d);
        casField.setText(String.valueOf(turaNaUpravu.getCasovaNarocnost()));
        panel.add(casField, gbc);

        gbc.gridy = 6;
        gbc.gridx = 5;
        gbc.fill = GridBagConstraints.NONE;
        dlzkaField = new JTextField();
        dlzkaField.setMaximumSize(d);
        dlzkaField.setPreferredSize(d);
        if (turaNaUpravu.getDlzka() != 0) {
            dlzkaField.setText(String.valueOf(turaNaUpravu.getDlzka()));
        }
        panel.add(dlzkaField, gbc);
        gbc.ipadx = 0;

        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridy = 9;
        gbc.gridx = 0;
        JLabel popisTuryLabel = new JLabel("Popis túry");
        //popisTury.setBorder(BorderFactory.createLineBorder(Color.black));
        panel.add(popisTuryLabel, gbc);

        gbc.gridy = 9;
        gbc.gridx = 3;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel hodnotenieLabel = new JLabel("Hodnotenie:");
        panel.add(hodnotenieLabel, gbc);

        gbc.gridy = 9;
        gbc.gridx = 4;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.WEST;
        sr.setRating(turaNaUpravu.getHodnotenie());
        panel.add(sr, gbc);

        gbc.gridx = 0;
        gbc.gridy = 10;
        gbc.gridwidth = 6;
        d = new Dimension(600, 300);
        popis.setPreferredSize(d);
        popis.setText(turaNaUpravu.getDetail());
        panel.add(popis, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 5;
        gbc.gridy = 11;
        panel.add(upravButton, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 11;
        panel.add(vymazTuruButton, gbc);

        JScrollPane scroll = new JScrollPane(panel);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        this.setContentPane(scroll);

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
            java.util.logging.Logger.getLogger(UpravaForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UpravaForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UpravaForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UpravaForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new UpravaForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
