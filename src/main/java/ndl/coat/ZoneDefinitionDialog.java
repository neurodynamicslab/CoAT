/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package ndl.coat;

import ij.ImagePlus;
import java.io.File;

/**
 *
 * @author balaji
 */
public class ZoneDefinitionDialog extends javax.swing.JDialog {

    /**
     * Creates new form ZoneDefinitionDialog
     */
    public ZoneDefinitionDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
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

        ROIs = new javax.swing.ButtonGroup();
        jScrollPaneZoneROIs = new javax.swing.JScrollPane();
        jList_ZoneROIs = new javax.swing.JList<>();
        ImageDisplayPanel = new javax.swing.JPanel();
        RoiPropertiesPanel = new javax.swing.JPanel();
        XSz_jFormattedTextField1 = new javax.swing.JFormattedTextField();
        YSz_jFormattedTextField2 = new javax.swing.JFormattedTextField();
        YCtr_jFormattedTextField1 = new javax.swing.JFormattedTextField();
        XCtr_jFormattedTextField = new javax.swing.JFormattedTextField();
        jLabelXLen = new javax.swing.JLabel();
        specify_ROIjCheckBox = new javax.swing.JCheckBox();
        Sym_jCheckBox = new javax.swing.JCheckBox();
        jLabelXCenter = new javax.swing.JLabel();
        jLabelYCenter = new javax.swing.JLabel();
        jLabelYLen = new javax.swing.JLabel();
        freehand_jToggle = new javax.swing.JToggleButton();
        oval_jToggle = new javax.swing.JToggleButton();
        delAll_jButton = new javax.swing.JButton();
        recgtangle_jToggle = new javax.swing.JToggleButton();
        addROI_jButton = new javax.swing.JButton();
        delROI_jButton = new javax.swing.JButton();
        open_jButton = new javax.swing.JButton();
        save_jButton = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        dataFile_jList = new javax.swing.JList<>();
        imagestack_jSlider = new javax.swing.JSlider();
        unbind_jButton = new javax.swing.JButton();
        bind_jButton = new javax.swing.JButton();
        play_jButton = new javax.swing.JButton();
        home_jButton = new javax.swing.JButton();
        rewind_jButton = new javax.swing.JButton();
        forward_jButton = new javax.swing.JButton();
        sameROI_jCheckBox = new javax.swing.JCheckBox();
        trial_jComboBox = new javax.swing.JComboBox<>();
        trial_jLabel = new javax.swing.JLabel();
        grp_jLabel = new javax.swing.JLabel();
        grp_jComboBox = new javax.swing.JComboBox<>();
        track_jButton = new javax.swing.JButton();
        trackall_jButton = new javax.swing.JButton();
        overlay_jToggleButton = new javax.swing.JToggleButton();
        fnameDsp_jFormattedTextField = new javax.swing.JFormattedTextField();
        trackerfile_jLabel = new javax.swing.JLabel();
        browse_jButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Define the Zone, ROIs and Qs");
        java.awt.GridBagLayout layout = new java.awt.GridBagLayout();
        layout.columnWidths = new int[] {0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0};
        layout.rowHeights = new int[] {0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0};
        getContentPane().setLayout(layout);

        jScrollPaneZoneROIs.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Regions of Interest", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        jList_ZoneROIs.setDragEnabled(true);
        jScrollPaneZoneROIs.setViewportView(jList_ZoneROIs);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 34;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 19;
        gridBagConstraints.gridheight = 24;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        getContentPane().add(jScrollPaneZoneROIs, gridBagConstraints);

        ImageDisplayPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Image Frame", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));
        ImageDisplayPanel.setPreferredSize(new java.awt.Dimension(600, 450));

        javax.swing.GroupLayout ImageDisplayPanelLayout = new javax.swing.GroupLayout(ImageDisplayPanel);
        ImageDisplayPanel.setLayout(ImageDisplayPanelLayout);
        ImageDisplayPanelLayout.setHorizontalGroup(
            ImageDisplayPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1087, Short.MAX_VALUE)
        );
        ImageDisplayPanelLayout.setVerticalGroup(
            ImageDisplayPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 185, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 33;
        gridBagConstraints.gridheight = 21;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.75;
        gridBagConstraints.weighty = 0.75;
        getContentPane().add(ImageDisplayPanel, gridBagConstraints);

        java.awt.GridBagLayout jPanel2Layout = new java.awt.GridBagLayout();
        jPanel2Layout.columnWidths = new int[] {0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0};
        jPanel2Layout.rowHeights = new int[] {0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0};
        RoiPropertiesPanel.setLayout(jPanel2Layout);

        XSz_jFormattedTextField1.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("###0"))));
        XSz_jFormattedTextField1.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        XSz_jFormattedTextField1.setText("0");
        XSz_jFormattedTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                XSz_jFormattedTextField1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        RoiPropertiesPanel.add(XSz_jFormattedTextField1, gridBagConstraints);

        YSz_jFormattedTextField2.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("###0"))));
        YSz_jFormattedTextField2.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        YSz_jFormattedTextField2.setText("0");
        YSz_jFormattedTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                YSz_jFormattedTextField2ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 10;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        RoiPropertiesPanel.add(YSz_jFormattedTextField2, gridBagConstraints);

        YCtr_jFormattedTextField1.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("###0"))));
        YCtr_jFormattedTextField1.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        YCtr_jFormattedTextField1.setText("0");
        YCtr_jFormattedTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                YCtr_jFormattedTextField1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 10;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        RoiPropertiesPanel.add(YCtr_jFormattedTextField1, gridBagConstraints);

        XCtr_jFormattedTextField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("###0"))));
        XCtr_jFormattedTextField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        XCtr_jFormattedTextField.setText("0");
        XCtr_jFormattedTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                XCtr_jFormattedTextFieldActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        RoiPropertiesPanel.add(XCtr_jFormattedTextField, gridBagConstraints);

        jLabelXLen.setText("X Len");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 4;
        RoiPropertiesPanel.add(jLabelXLen, gridBagConstraints);

        specify_ROIjCheckBox.setText("Specify ROI");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 2;
        RoiPropertiesPanel.add(specify_ROIjCheckBox, gridBagConstraints);

        Sym_jCheckBox.setText("Symmetric");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 10;
        gridBagConstraints.gridy = 2;
        RoiPropertiesPanel.add(Sym_jCheckBox, gridBagConstraints);

        jLabelXCenter.setText("X Center");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 4;
        RoiPropertiesPanel.add(jLabelXCenter, gridBagConstraints);

        jLabelYCenter.setText("Y Center");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 8;
        RoiPropertiesPanel.add(jLabelYCenter, gridBagConstraints);

        jLabelYLen.setText("Y Len");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 8;
        RoiPropertiesPanel.add(jLabelYLen, gridBagConstraints);

        ROIs.add(freehand_jToggle);
        freehand_jToggle.setText("Freehand");
        freehand_jToggle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                freehand_jToggleActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 12;
        RoiPropertiesPanel.add(freehand_jToggle, gridBagConstraints);

        ROIs.add(oval_jToggle);
        oval_jToggle.setText("Oval");
        oval_jToggle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                oval_jToggleActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        RoiPropertiesPanel.add(oval_jToggle, gridBagConstraints);

        delAll_jButton.setText("Delete All");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 14;
        RoiPropertiesPanel.add(delAll_jButton, gridBagConstraints);

        ROIs.add(recgtangle_jToggle);
        recgtangle_jToggle.setText("Rectangle");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 10;
        gridBagConstraints.gridy = 12;
        RoiPropertiesPanel.add(recgtangle_jToggle, gridBagConstraints);

        addROI_jButton.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        addROI_jButton.setText("(+)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        RoiPropertiesPanel.add(addROI_jButton, gridBagConstraints);

        delROI_jButton.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        delROI_jButton.setText("(-)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 10;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        RoiPropertiesPanel.add(delROI_jButton, gridBagConstraints);

        open_jButton.setText("Open");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        RoiPropertiesPanel.add(open_jButton, gridBagConstraints);

        save_jButton.setText("Save");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 10;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        RoiPropertiesPanel.add(save_jButton, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 34;
        gridBagConstraints.gridy = 24;
        gridBagConstraints.gridwidth = 19;
        gridBagConstraints.gridheight = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        getContentPane().add(RoiPropertiesPanel, gridBagConstraints);

        dataFile_jList.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Data Files", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));
        dataFile_jList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane2.setViewportView(dataFile_jList);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 24;
        gridBagConstraints.gridwidth = 33;
        gridBagConstraints.gridheight = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        getContentPane().add(jScrollPane2, gridBagConstraints);

        imagestack_jSlider.setMinorTickSpacing(5);
        imagestack_jSlider.setPaintTicks(true);
        imagestack_jSlider.setValue(0);
        imagestack_jSlider.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        imagestack_jSlider.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        imagestack_jSlider.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 22;
        gridBagConstraints.gridwidth = 33;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        getContentPane().add(imagestack_jSlider, gridBagConstraints);

        unbind_jButton.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        unbind_jButton.setText("Unbind");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 10;
        gridBagConstraints.gridy = 30;
        getContentPane().add(unbind_jButton, gridBagConstraints);

        bind_jButton.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        bind_jButton.setText("Bind ROis");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 30;
        getContentPane().add(bind_jButton, gridBagConstraints);

        play_jButton.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        play_jButton.setText("Pay [ > ]");
        play_jButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                play_jButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 30;
        getContentPane().add(play_jButton, gridBagConstraints);

        home_jButton.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        home_jButton.setText("Stop [ O ]");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 30;
        getContentPane().add(home_jButton, gridBagConstraints);

        rewind_jButton.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        rewind_jButton.setText("<<");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 30;
        getContentPane().add(rewind_jButton, gridBagConstraints);

        forward_jButton.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        forward_jButton.setText(">>");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 30;
        getContentPane().add(forward_jButton, gridBagConstraints);

        sameROI_jCheckBox.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        sameROI_jCheckBox.setText("same ROIs for all files");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 32;
        gridBagConstraints.gridy = 30;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        getContentPane().add(sameROI_jCheckBox, gridBagConstraints);

        trial_jComboBox.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        trial_jComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 40;
        gridBagConstraints.gridy = 30;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        getContentPane().add(trial_jComboBox, gridBagConstraints);

        trial_jLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        trial_jLabel.setText("Select the trial");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 38;
        gridBagConstraints.gridy = 30;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        getContentPane().add(trial_jLabel, gridBagConstraints);

        grp_jLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        grp_jLabel.setText("Select the group");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 44;
        gridBagConstraints.gridy = 30;
        getContentPane().add(grp_jLabel, gridBagConstraints);

        grp_jComboBox.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        grp_jComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 46;
        gridBagConstraints.gridy = 30;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        getContentPane().add(grp_jComboBox, gridBagConstraints);

        track_jButton.setText("Track");
        track_jButton.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 12;
        gridBagConstraints.gridy = 30;
        getContentPane().add(track_jButton, gridBagConstraints);

        trackall_jButton.setText("Track All");
        trackall_jButton.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 14;
        gridBagConstraints.gridy = 30;
        getContentPane().add(trackall_jButton, gridBagConstraints);

        overlay_jToggleButton.setText("Overlay Tracks");
        overlay_jToggleButton.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 28;
        gridBagConstraints.gridy = 30;
        getContentPane().add(overlay_jToggleButton, gridBagConstraints);

        fnameDsp_jFormattedTextField.setText("jFormattedTextField1");
        fnameDsp_jFormattedTextField.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 24;
        gridBagConstraints.gridy = 30;
        getContentPane().add(fnameDsp_jFormattedTextField, gridBagConstraints);

        trackerfile_jLabel.setText("Tracker File");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 20;
        gridBagConstraints.gridy = 30;
        getContentPane().add(trackerfile_jLabel, gridBagConstraints);

        browse_jButton.setText("Browse");
        browse_jButton.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 16;
        gridBagConstraints.gridy = 30;
        getContentPane().add(browse_jButton, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void freehand_jToggleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_freehand_jToggleActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_freehand_jToggleActionPerformed

    private void oval_jToggleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_oval_jToggleActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_oval_jToggleActionPerformed

    private void XCtr_jFormattedTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_XCtr_jFormattedTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_XCtr_jFormattedTextFieldActionPerformed

    private void YCtr_jFormattedTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_YCtr_jFormattedTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_YCtr_jFormattedTextField1ActionPerformed

    private void YSz_jFormattedTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_YSz_jFormattedTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_YSz_jFormattedTextField2ActionPerformed

    private void XSz_jFormattedTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_XSz_jFormattedTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_XSz_jFormattedTextField1ActionPerformed

    private void play_jButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_play_jButtonActionPerformed
        // TODO add your handling code here:
        String fileName = dataFile_jList.getSelectedValue();
        File file = new File(fileName);
        if(!file.exists())
            return;
        ImagePlus imp = new ImagePlus(file.getAbsolutePath());
    }//GEN-LAST:event_play_jButtonActionPerformed

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
            java.util.logging.Logger.getLogger(ZoneDefinitionDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ZoneDefinitionDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ZoneDefinitionDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ZoneDefinitionDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ZoneDefinitionDialog dialog = new ZoneDefinitionDialog(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel ImageDisplayPanel;
    private javax.swing.ButtonGroup ROIs;
    private javax.swing.JPanel RoiPropertiesPanel;
    private javax.swing.JCheckBox Sym_jCheckBox;
    private javax.swing.JFormattedTextField XCtr_jFormattedTextField;
    private javax.swing.JFormattedTextField XSz_jFormattedTextField1;
    private javax.swing.JFormattedTextField YCtr_jFormattedTextField1;
    private javax.swing.JFormattedTextField YSz_jFormattedTextField2;
    private javax.swing.JButton addROI_jButton;
    private javax.swing.JButton bind_jButton;
    private javax.swing.JButton browse_jButton;
    private javax.swing.JList<String> dataFile_jList;
    private javax.swing.JButton delAll_jButton;
    private javax.swing.JButton delROI_jButton;
    private javax.swing.JFormattedTextField fnameDsp_jFormattedTextField;
    private javax.swing.JButton forward_jButton;
    private javax.swing.JToggleButton freehand_jToggle;
    private javax.swing.JComboBox<String> grp_jComboBox;
    private javax.swing.JLabel grp_jLabel;
    private javax.swing.JButton home_jButton;
    private javax.swing.JSlider imagestack_jSlider;
    private javax.swing.JLabel jLabelXCenter;
    private javax.swing.JLabel jLabelXLen;
    private javax.swing.JLabel jLabelYCenter;
    private javax.swing.JLabel jLabelYLen;
    private javax.swing.JList<String> jList_ZoneROIs;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPaneZoneROIs;
    private javax.swing.JButton open_jButton;
    private javax.swing.JToggleButton oval_jToggle;
    private javax.swing.JToggleButton overlay_jToggleButton;
    private javax.swing.JButton play_jButton;
    private javax.swing.JToggleButton recgtangle_jToggle;
    private javax.swing.JButton rewind_jButton;
    private javax.swing.JCheckBox sameROI_jCheckBox;
    private javax.swing.JButton save_jButton;
    private javax.swing.JCheckBox specify_ROIjCheckBox;
    private javax.swing.JButton track_jButton;
    private javax.swing.JButton trackall_jButton;
    private javax.swing.JLabel trackerfile_jLabel;
    private javax.swing.JComboBox<String> trial_jComboBox;
    private javax.swing.JLabel trial_jLabel;
    private javax.swing.JButton unbind_jButton;
    // End of variables declaration//GEN-END:variables
}
