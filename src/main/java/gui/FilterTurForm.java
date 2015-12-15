package gui;

import gui.MainForm;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Enumeration;
import java.util.List;
import java.util.Stack;
import java.util.Vector;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import sk.ics.upjs.hikeapp.TuraDaO;
import sk.ics.upjs.hikeapp.DaOFactory;

public class FilterTurForm extends javax.swing.JFrame {

    private TuraDaO tury;
    private MyButtonGroup obtiaznostButtonGroup;
    private Long idU;

    public FilterTurForm() {
        initComponents();
    }

    public FilterTurForm(Long userId) {
        initComponents();
        this.setTitle("Výber túry");
        idU = userId;
        // mame vlastny ButtonGroup , kt ma funkciu ze vrati ktory radioButton je zvoleny
        obtiaznostButtonGroup = new MyButtonGroup();
        obtiaznostButtonGroup.add(obtiaznostRadioButton1);
        obtiaznostButtonGroup.add(obtiaznostRadioButton2);
        obtiaznostButtonGroup.add(obtiaznostRadioButton3);
        obtiaznostButtonGroup.add(obtiaznostRadioButton4);
        obtiaznostButtonGroup.add(obtiaznostRadioButton5);

        //naplni PohorieComboBox
        tury = DaOFactory.INSTANCE.getTuraDaO();
        List<String> zoznam = tury.dajZoznamPohori();
        Vector zoznamCB = new Vector();
        zoznamCB.add("<Pohorie>");
        for (String pohorie : zoznam) {
            zoznamCB.add(pohorie);
        }
        DefaultComboBoxModel model = new DefaultComboBoxModel(zoznamCB);
        pohorieComboBox.setModel(model);
        //naplni rocneObdobieComboBox

        Vector zoznamROCB = new Vector();
        zoznamROCB.add("<RocneObdobie>");
        zoznamROCB.add("Jar");
        zoznamROCB.add("Leto");
        zoznamROCB.add("Jeseň");
        zoznamROCB.add("Zima");
        DefaultComboBoxModel modelRO = new DefaultComboBoxModel(zoznamROCB);
        rocneObdobieComboBox.setModel(modelRO);

        // pri skonceni okna sa vrati do menu
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                FilterTurForm.this.dispose();
                if (idU > -1) {
                    new UzivatelMenu(idU).setVisible(true);
                } else {
                    new LogInForm().setVisible(true);
                }
            }

        });

        pack();
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int height = dim.height;
        int width = dim.width;
        this.setLocation((-this.getSize().width + width) / 2, (-this.getSize().height + height) / 2);
        setVisible(true);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pohorieLabel = new javax.swing.JLabel();
        pohorieComboBox = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        rocneObdobieComboBox = new javax.swing.JComboBox();
        obtiaznostLabel = new javax.swing.JLabel();
        obtiaznostRadioButton1 = new javax.swing.JRadioButton();
        obtiaznostRadioButton2 = new javax.swing.JRadioButton();
        obtiaznostRadioButton3 = new javax.swing.JRadioButton();
        obtiaznostRadioButton4 = new javax.swing.JRadioButton();
        obtiaznostRadioButton5 = new javax.swing.JRadioButton();
        casovaNarLabel = new javax.swing.JLabel();
        casovaNarTextField = new javax.swing.JTextField();
        mimoChodnikLabel = new javax.swing.JLabel();
        mimoChodnikCheckBox = new javax.swing.JCheckBox();
        hodLabel = new javax.swing.JLabel();
        hladajButton = new javax.swing.JButton();
        cielLabel = new javax.swing.JLabel();
        cielField = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pohorieLabel.setText("Pohorie");

        pohorieComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "<Pohorie>" }));
        pohorieComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pohorieComboBoxActionPerformed(evt);
            }
        });

        jLabel1.setText("Ročné obdobie");

        rocneObdobieComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "<Ročné Obdobie>" }));
        rocneObdobieComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rocneObdobieComboBoxActionPerformed(evt);
            }
        });

        obtiaznostLabel.setText("Max. obtiažnosť");

        obtiaznostRadioButton1.setText("1");
        obtiaznostRadioButton1.setToolTipText("");

        obtiaznostRadioButton2.setText("2");
        obtiaznostRadioButton2.setToolTipText("");
        obtiaznostRadioButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                obtiaznostRadioButton2ActionPerformed(evt);
            }
        });

        obtiaznostRadioButton3.setText("3");
        obtiaznostRadioButton3.setToolTipText("");

        obtiaznostRadioButton4.setText("4");
        obtiaznostRadioButton4.setToolTipText("");

        obtiaznostRadioButton5.setText("5");
        obtiaznostRadioButton5.setToolTipText("");
        obtiaznostRadioButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                obtiaznostRadioButton5ActionPerformed(evt);
            }
        });

        casovaNarLabel.setText("Max. trvanie túry");

        mimoChodnikLabel.setText("Off track");

        mimoChodnikCheckBox.setSelected(true);
        mimoChodnikCheckBox.setText("áno");

        hodLabel.setText("hod.");

        hladajButton.setText("Hľadaj");
        hladajButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hladajButtonActionPerformed(evt);
            }
        });

        cielLabel.setText("Cieľ");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE)
                        .addGap(165, 165, 165))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(pohorieLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(obtiaznostLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(casovaNarLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(mimoChodnikLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cielLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(obtiaznostRadioButton1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(obtiaznostRadioButton2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(obtiaznostRadioButton3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(obtiaznostRadioButton4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(obtiaznostRadioButton5)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(10, 10, 10)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(mimoChodnikCheckBox)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(hladajButton))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(casovaNarTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(hodLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addGap(11, 11, 11)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(rocneObdobieComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(cielField, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(pohorieComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                .addGap(0, 0, Short.MAX_VALUE))))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(pohorieLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pohorieComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cielField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cielLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rocneObdobieComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(obtiaznostLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(obtiaznostRadioButton1)
                    .addComponent(obtiaznostRadioButton2)
                    .addComponent(obtiaznostRadioButton3)
                    .addComponent(obtiaznostRadioButton4)
                    .addComponent(obtiaznostRadioButton5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(casovaNarLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(casovaNarTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(hodLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(mimoChodnikLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mimoChodnikCheckBox)
                    .addComponent(hladajButton))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void pohorieComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pohorieComboBoxActionPerformed
        pohorieComboBox.repaint();
    }//GEN-LAST:event_pohorieComboBoxActionPerformed

    private void rocneObdobieComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rocneObdobieComboBoxActionPerformed
        rocneObdobieComboBox.repaint();
    }//GEN-LAST:event_rocneObdobieComboBoxActionPerformed
    // Ked sa podari pridat ButtonGroup do Formu tak to potom pouzijeme ako obtiaznostBG.getSelectedValue();

    public class MyButtonGroup extends ButtonGroup {

        public String getSelectedValue() {
            int i = 1;
            for (Enumeration<AbstractButton> buttons = this.getElements(); buttons.hasMoreElements();) {
                AbstractButton button = buttons.nextElement();
                if (button.isSelected()) {
                    return String.valueOf(i);
                }
                i++;
            }
            return null;
        }
    }

    private void hladajButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hladajButtonActionPerformed
        Stack<String> nazvyAtributov = new Stack<String>();
        Stack<String> hodnotyAtributov = new Stack<String>();
        if (!String.valueOf(pohorieComboBox.getSelectedItem()).equals("<Pohorie>")) {
            nazvyAtributov.push("Pohorie");
            hodnotyAtributov.push(String.valueOf(pohorieComboBox.getSelectedItem()));
        }
        if (!String.valueOf(rocneObdobieComboBox.getSelectedItem()).equals("<RocneObdobie>")) {
            nazvyAtributov.push("RocneObdobie");
            hodnotyAtributov.push(String.valueOf(rocneObdobieComboBox.getSelectedItem()));
        }
        if (obtiaznostButtonGroup.getSelectedValue() != null) {
            nazvyAtributov.push("obtiaznost");
            hodnotyAtributov.push(obtiaznostButtonGroup.getSelectedValue());
        }
        if (!overPolicko(String.valueOf(casovaNarTextField.getText()).trim())) {
            JOptionPane.showMessageDialog(this, "Zlý formát pre čas!");
            return;
        }
        if (!String.valueOf(casovaNarTextField.getText()).trim().equals("")) {
            nazvyAtributov.push("casovaNarocnost");
            hodnotyAtributov.push(String.valueOf(casovaNarTextField.getText().trim().replace(",", ".")));
        }
        if (!mimoChodnikCheckBox.isSelected()) {
            nazvyAtributov.push("mimoChodnik");
            hodnotyAtributov.push("false");
        }
        if (!cielField.getText().trim().isEmpty()) {
            nazvyAtributov.push("ciel");
            hodnotyAtributov.push(cielField.getText().trim());
        }
        this.dispose();
        new MainForm(tury.dajVybraneTury(nazvyAtributov, hodnotyAtributov), idU, -1).setVisible(true);

    }//GEN-LAST:event_hladajButtonActionPerformed

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
    private void obtiaznostRadioButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_obtiaznostRadioButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_obtiaznostRadioButton2ActionPerformed

    private void obtiaznostRadioButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_obtiaznostRadioButton5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_obtiaznostRadioButton5ActionPerformed

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
            java.util.logging.Logger.getLogger(FilterTurForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FilterTurForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FilterTurForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FilterTurForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FilterTurForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel casovaNarLabel;
    private javax.swing.JTextField casovaNarTextField;
    private javax.swing.JTextField cielField;
    private javax.swing.JLabel cielLabel;
    private javax.swing.JButton hladajButton;
    private javax.swing.JLabel hodLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JCheckBox mimoChodnikCheckBox;
    private javax.swing.JLabel mimoChodnikLabel;
    private javax.swing.JLabel obtiaznostLabel;
    private javax.swing.JRadioButton obtiaznostRadioButton1;
    private javax.swing.JRadioButton obtiaznostRadioButton2;
    private javax.swing.JRadioButton obtiaznostRadioButton3;
    private javax.swing.JRadioButton obtiaznostRadioButton4;
    private javax.swing.JRadioButton obtiaznostRadioButton5;
    private javax.swing.JComboBox pohorieComboBox;
    private javax.swing.JLabel pohorieLabel;
    private javax.swing.JComboBox rocneObdobieComboBox;
    // End of variables declaration//GEN-END:variables
}
