/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ndl.coat;
import ndl.ndllib.*;
//import NDL_JavaClassLib.MultiFileDialog;
import java.awt.Dimension;
import ij.ImagePlus;
import ij.ImageStack;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
//import NDL_JavaClassLib.*;
import ij.gui.Roi;
import ij.io.RoiEncoder;
import ij.plugin.filter.ThresholdToSelection;
import ij.process.FloatProcessor;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import ndl.ndllib.SurfaceFit;
/**
 *
 * @author balam
 */
public class VectorAnalysisMDI extends javax.swing.JFrame implements ActionListener, PropertyChangeListener{

    /**
     * Creates new form VectorAnalysisMDI
     */
    DataManager dManager = new DataManager();
    private ImagePlus hmapStk;
    private ImageStack stk;
    private int nGrps;
    private int nAnimals;
    private int nTrial;
    //private ComboBoxModel<String> TrialModel;
    private boolean estimateOC;
    private final SurfaceFit fit;
    private JVector[][] OccCtrs;
    
    ArrayList allThreads = new ArrayList();
    private int activeCount;
    Thread threadMonitor;
    public VectorAnalysisMDI() {
       
        Dimension d = this.getMaximumSize();
        this.setSize(d);
        this.setTitle("Vector Analysis for Navigation");
        initComponents();
        
        this.AnimalGrpModel = (DefaultTableModel)this.AnimalGrpSummaryTable.getModel();
        this.FileAssignmentModel = (DefaultTableModel)this.FileAssignmentTable.getModel();
        this.FileDetailModel = (DefaultTableModel)this.FileDetail_Table.getModel();
        this.TrialNoModel =  new extTableModel((DefaultTableModel)this.Trial_No_Table.getModel());
        this.Trial_No_Table.setModel(TrialNoModel);
        this.grpNames = new ArrayList<>();
        this.trialNames = new ArrayList<>();
        this.rel2absPathMaps = new ConcurrentHashMap();
        
        this.treeModel = (DefaultTreeModel)this.expDgnTree.getModel();
        expRoot = new DefaultMutableTreeNode("Experimental Design");
        trialRoot = new DefaultMutableTreeNode("Trials View");
        grpRoot =new DefaultMutableTreeNode("Groups View");
        
        treeModel.setRoot(expRoot);
        treeModel.insertNodeInto(trialRoot, expRoot, 0);
        treeModel.insertNodeInto(grpRoot, expRoot, 1);
        expDgnTree.setEditable(true);
        
        fit = new SurfaceFit(this.x_polyOrderJCmbBx.getSelectedIndex()+1, this.y_polyOrderJCmbBx.getSelectedIndex()+1);
        fit.setUseSelection(this.useSeljChBx.isSelected());
        fit.setSelectPixels(this.res2SeljChkBx.isSelected());
        //treeModel.reload();
        
        threadMonitor = new Thread(){
            @Override
            public void  run(){
                synchronized(jVectorFieldCalculator.getFinishedStatus()){
                    while(jVectorFieldCalculator.getInstanceCount() > 0 && activeCount > 0){
                        try {
                            //StatusMessageBox.append("Waiting for "+activeCount + "threads to end \n");
                            setStatusMessage("Waiting for "+activeCount + " threads and " +jVectorFieldCalculator.getInstanceCount() +" calculations to end\n");
                            //Thread.sleep(100);
                            jVectorFieldCalculator.getFinishedStatus().wait();
                            activeCount--;
                            //not a correct implementation. Rewrite the wait logic.....................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................
                        } catch (InterruptedException ex) {
                            Logger.getLogger(VectorAnalysisMDI.class.getName()).log(Level.SEVERE, null, ex);
                            setStatusMessage("Exception Occured:  \n" +ex.getMessage() +"\n");
                            RunGrp_Button.setEnabled(true);
                        }
                    }
                }
                setStatusMessage("All threads are complete \n");
                RunGrp_Button.setEnabled(true);
            }
            
        };
        
        
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        compJRadGrp = new javax.swing.ButtonGroup();
        vectJChkBx = new javax.swing.ButtonGroup();
        jScrollPane4 = new javax.swing.JScrollPane();
        DeskTopPanel = new javax.swing.JPanel();
        InfoTab = new javax.swing.JTabbedPane();
        ExpDef_jPanel = new javax.swing.JPanel();
        jLabel_Number_of_GrpTxt = new javax.swing.JLabel();
        jFormattedTextField_NoOfGrps = new javax.swing.JFormattedTextField();
        jLabel_NoOfAnimals = new javax.swing.JLabel();
        SampleSizeSel = new javax.swing.JCheckBox();
        jScrollPane3 = new javax.swing.JScrollPane();
        AnimalGrpSummaryTable = new javax.swing.JTable();
        jScrollPane1 = new javax.swing.JScrollPane();
        Trial_No_Table = new javax.swing.JTable();
        upDateButton = new javax.swing.JButton();
        nAnimals_Text = new javax.swing.JFormattedTextField();
        jLabel1 = new javax.swing.JLabel();
        jFormattedText_nTrials = new javax.swing.JFormattedTextField();
        jLabel2 = new javax.swing.JLabel();
        reset_AnGrTr_Button = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        AllGrpsinAllTrialCheckBox = new javax.swing.JCheckBox();
        DataFiles_jPanel = new javax.swing.JPanel();
        AddFiles_Button = new javax.swing.JButton();
        RemoveFile_Button = new javax.swing.JButton();
        jScrollPane6 = new javax.swing.JScrollPane();
        FileDetail_Table = new javax.swing.JTable();
        Assign_Button = new javax.swing.JButton();
        jScrollPane7 = new javax.swing.JScrollPane();
        FileAssignmentTable = new javax.swing.JTable();
        GrpSelComboBox = new javax.swing.JComboBox<>();
        AnimalSelComboBox = new javax.swing.JComboBox<>();
        TrialSelComboBox = new javax.swing.JComboBox<>();
        SelDesLabel = new javax.swing.JLabel();
        GrpLabel = new javax.swing.JLabel();
        TrialLabel = new javax.swing.JLabel();
        AnimalLabel = new javax.swing.JLabel();
        OpenFileAssignmentsButton = new javax.swing.JButton();
        SaveFileAssignmentsButton = new javax.swing.JButton();
        xResTxtField = new javax.swing.JFormattedTextField();
        yResTxtField = new javax.swing.JFormattedTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        expFileNoJText = new javax.swing.JTextField();
        assignFileJTxt = new javax.swing.JTextField();
        remainingDatafilesJText = new javax.swing.JTextField();
        jButtonFileAssignRest = new javax.swing.JButton();
        jCombo_dataSeparator = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        jFormatTxt_rootFolder = new javax.swing.JFormattedTextField();
        jLabel14 = new javax.swing.JLabel();
        jButtonBrowseRoot = new javax.swing.JButton();
        jButtonRemoveAssignments = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        chkBoxRemoveAssignedFiles = new javax.swing.JCheckBox();
        AnalysisDesign_jPanel = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        expDgnTree = new javax.swing.JTree();
        RunGrp_Button = new javax.swing.JButton();
        HeatMap_Button = new javax.swing.JButton();
        QuadAna_Button = new javax.swing.JButton();
        GenConMaps_Button = new javax.swing.JButton();
        GenCurlMaps_Button = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        PlatXjFtTxt = new javax.swing.JFormattedTextField();
        PlatYjFtTxt1 = new javax.swing.JFormattedTextField();
        ocXjFtTxt2 = new javax.swing.JFormattedTextField();
        ocYjFtTxt3 = new javax.swing.JFormattedTextField();
        CheckBoxBoolean = new javax.swing.JCheckBox();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        gaussjChkBx = new javax.swing.JCheckBox();
        jLabel21 = new javax.swing.JLabel();
        scalingfactorJFormFld = new javax.swing.JFormattedTextField();
        jLabel22 = new javax.swing.JLabel();
        gauRadjFormFld = new javax.swing.JFormattedTextField();
        reSzImgjChkBx = new javax.swing.JCheckBox();
        useTan2jChkBx = new javax.swing.JCheckBox();
        ScaleY_JChkBx = new javax.swing.JCheckBox();
        jLabel24 = new javax.swing.JLabel();
        aspectRatiojFmtFld = new javax.swing.JFormattedTextField();
        useRelVelJChkBx = new javax.swing.JCheckBox();
        jChkBxAssym = new javax.swing.JCheckBox();
        jCheckBox2 = new javax.swing.JCheckBox();
        jLabel15 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jFrtTxtFld = new javax.swing.JFormattedTextField();
        jFmtTxtFldRadX = new javax.swing.JFormattedTextField();
        jPanel3 = new javax.swing.JPanel();
        autoPoolRoijChkBx = new javax.swing.JCheckBox();
        jLabel18 = new javax.swing.JLabel();
        xPoolCtrjFormFld = new javax.swing.JFormattedTextField();
        jLabel19 = new javax.swing.JLabel();
        yPoolCtrjFormFld = new javax.swing.JFormattedTextField();
        jLabel20 = new javax.swing.JLabel();
        poolRadjFormFld = new javax.swing.JFormattedTextField();
        jCheckBox3 = new javax.swing.JCheckBox();
        saveVelocityjchkBx = new javax.swing.JCheckBox();
        jPanel4 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        x_polyOrderJCmbBx = new javax.swing.JComboBox<>();
        jLabel17 = new javax.swing.JLabel();
        y_polyOrderJCmbBx = new javax.swing.JComboBox<>();
        genVeljChkBx1 = new javax.swing.JCheckBox();
        genAccjChkBx = new javax.swing.JCheckBox();
        useSeljChBx = new javax.swing.JCheckBox();
        res2SeljChkBx = new javax.swing.JCheckBox();
        genConvJChkBx = new javax.swing.JCheckBox();
        genDivjChkBx = new javax.swing.JCheckBox();
        CompforVectorFldjChkBx2 = new javax.swing.JCheckBox();
        AlongJRadBtn = new javax.swing.JRadioButton();
        OrtoJRadBtn = new javax.swing.JRadioButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        ImageDisplay_Panel = new javax.swing.JPanel();
        ProgIndPanel = new javax.swing.JPanel();
        jProgressBarDataAssignment = new javax.swing.JProgressBar();
        jProgressBarTP = new javax.swing.JProgressBar();
        jLabel25 = new javax.swing.JLabel();
        jProgressBarGP = new javax.swing.JProgressBar();
        jLabel26 = new javax.swing.JLabel();
        jProgressBarDP = new javax.swing.JProgressBar();
        jLabel27 = new javax.swing.JLabel();
        jProgressBarFR = new javax.swing.JProgressBar();
        jLabel29 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        MessageBox_Panel = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jScrollPane8 = new javax.swing.JScrollPane();
        StatusMessageBox = new javax.swing.JTextArea();
        menuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        openMenuItem = new javax.swing.JMenuItem();
        saveMenuItem = new javax.swing.JMenuItem();
        saveAsMenuItem = new javax.swing.JMenuItem();
        jFolderOptions = new javax.swing.JMenuItem();
        ImportMenuItem = new javax.swing.JMenuItem();
        exitMenuItem = new javax.swing.JMenuItem();
        mapsMenu = new javax.swing.JMenu();
        residencemapMenuItem = new javax.swing.JMenuItem();
        analysisMenu = new javax.swing.JMenu();
        jMenuItemGrpID = new javax.swing.JMenuItem();
        jMenuItemExpDes = new javax.swing.JMenuItem();
        jMenuItemMeasure = new javax.swing.JMenuItem();
        jMenuItemCompute = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        editMenu = new javax.swing.JMenu();
        cutMenuItem = new javax.swing.JMenuItem();
        copyMenuItem = new javax.swing.JMenuItem();
        pasteMenuItem = new javax.swing.JMenuItem();
        deleteMenuItem = new javax.swing.JMenuItem();
        helpMenu = new javax.swing.JMenu();
        contentMenuItem = new javax.swing.JMenuItem();
        aboutMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("CoAT");
        setBounds(new java.awt.Rectangle(0, 0, 1275, 775));
        setLocation(new java.awt.Point(0, 0));
        setMaximizedBounds(new java.awt.Rectangle(0, 0, 1275, 800));
        setMinimumSize(new java.awt.Dimension(600, 500));
        setName("Frame"); // NOI18N
        setPreferredSize(new java.awt.Dimension(1275, 775));
        setSize(new java.awt.Dimension(1275, 775));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane4.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        jScrollPane4.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPane4.setViewportBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 0), 1, true));
        jScrollPane4.setPreferredSize(new java.awt.Dimension(600, 700));

        DeskTopPanel.setMinimumSize(new java.awt.Dimension(600, 500));
        DeskTopPanel.setPreferredSize(new java.awt.Dimension(740, 675));
        DeskTopPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        InfoTab.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        InfoTab.setPreferredSize(new java.awt.Dimension(740, 500));

        ExpDef_jPanel.setAutoscrolls(true);
        ExpDef_jPanel.setPreferredSize(new java.awt.Dimension(733, 500));

        jLabel_Number_of_GrpTxt.setText("Number of Groups");

        jFormattedTextField_NoOfGrps.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("0"))));
        jFormattedTextField_NoOfGrps.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFormattedTextField_NoOfGrps.setText("2");
        jFormattedTextField_NoOfGrps.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jFormattedTextField_NoOfGrpsActionPerformed(evt);
            }
        });

        jLabel_NoOfAnimals.setText("Number of Animals per Grp");

        SampleSizeSel.setSelected(true);
        SampleSizeSel.setText("Same N for all grps");
        SampleSizeSel.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        SampleSizeSel.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SampleSizeSelItemStateChanged(evt);
            }
        });

        AnimalGrpSummaryTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "S No", "Group Name", "Animals"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Integer.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        AnimalGrpSummaryTable.setEnabled(false);
        AnimalGrpSummaryTable.setShowGrid(true);
        jScrollPane3.setViewportView(AnimalGrpSummaryTable);

        Trial_No_Table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "", ""
            }
        ));
        Trial_No_Table.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        Trial_No_Table.setColumnSelectionAllowed(true);
        Trial_No_Table.setEnabled(false);
        jScrollPane1.setViewportView(Trial_No_Table);
        Trial_No_Table.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        upDateButton.setText("Finalise  Animals Grp Trial #s");
        upDateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                upDateButtonActionPerformed(evt);
            }
        });

        nAnimals_Text.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("0"))));
        nAnimals_Text.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        nAnimals_Text.setText("5");

        jLabel1.setText("Number of animals (N) ");

        jFormattedText_nTrials.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("0"))));
        jFormattedText_nTrials.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFormattedText_nTrials.setText("2");

        jLabel2.setText("Number of Trials");

        reset_AnGrTr_Button.setText("Reset Animals Grp Trial #s ");
        reset_AnGrTr_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reset_AnGrTr_ButtonActionPerformed(evt);
            }
        });

        jLabel3.setText("Group Selection Table:");

        AllGrpsinAllTrialCheckBox.setSelected(true);
        AllGrpsinAllTrialCheckBox.setText(" All groups are there in all trials");
        AllGrpsinAllTrialCheckBox.setEnabled(false);
        AllGrpsinAllTrialCheckBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                AllGrpsinAllTrialCheckBoxItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout ExpDef_jPanelLayout = new javax.swing.GroupLayout(ExpDef_jPanel);
        ExpDef_jPanel.setLayout(ExpDef_jPanelLayout);
        ExpDef_jPanelLayout.setHorizontalGroup(
            ExpDef_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ExpDef_jPanelLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(ExpDef_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel_NoOfAnimals, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 728, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 728, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(ExpDef_jPanelLayout.createSequentialGroup()
                        .addGroup(ExpDef_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel_Number_of_GrpTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 564, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(ExpDef_jPanelLayout.createSequentialGroup()
                                .addComponent(SampleSizeSel, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 345, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(ExpDef_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(nAnimals_Text, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jFormattedText_nTrials, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jFormattedTextField_NoOfGrps, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(ExpDef_jPanelLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(AllGrpsinAllTrialCheckBox)
                .addGap(18, 18, 18)
                .addComponent(upDateButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(reset_AnGrTr_Button)
                .addGap(138, 138, 138))
        );
        ExpDef_jPanelLayout.setVerticalGroup(
            ExpDef_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ExpDef_jPanelLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(ExpDef_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel_Number_of_GrpTxt)
                    .addComponent(jFormattedTextField_NoOfGrps, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1)
                .addGroup(ExpDef_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jFormattedText_nTrials, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(2, 2, 2)
                .addGroup(ExpDef_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(SampleSizeSel)
                    .addGroup(ExpDef_jPanelLayout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addGroup(ExpDef_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(nAnimals_Text, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(2, 2, 2)
                .addComponent(jLabel_NoOfAnimals)
                .addGap(6, 6, 6)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addGroup(ExpDef_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AllGrpsinAllTrialCheckBox)
                    .addComponent(reset_AnGrTr_Button)
                    .addComponent(upDateButton))
                .addGap(5, 5, 5)
                .addComponent(jLabel3)
                .addGap(6, 6, 6)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(55, 55, 55))
        );

        InfoTab.addTab("Experiment Definition", ExpDef_jPanel);

        DataFiles_jPanel.setPreferredSize(new java.awt.Dimension(640, 700));

        AddFiles_Button.setText(" Add Files");
        AddFiles_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddFiles_ButtonActionPerformed(evt);
            }
        });

        RemoveFile_Button.setText("Remove Files");
        RemoveFile_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RemoveFile_ButtonActionPerformed(evt);
            }
        });

        FileDetail_Table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Path", "File name", "Start Frame", "End Frame", "FPS"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        FileDetail_Table.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        FileDetail_Table.setColumnSelectionAllowed(true);
        jScrollPane6.setViewportView(FileDetail_Table);
        FileDetail_Table.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        Assign_Button.setText("Assign");
        Assign_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Assign_ButtonActionPerformed(evt);
            }
        });

        FileAssignmentTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "File name", "Animal", "Group", "Trial Number"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        FileAssignmentTable.setCellSelectionEnabled(true);
        FileAssignmentTable.setDoubleBuffered(true);
        FileAssignmentTable.setDragEnabled(true);
        FileAssignmentTable.setEditingColumn(1);
        FileAssignmentTable.setEditingRow(1);
        FileAssignmentTable.setFillsViewportHeight(true);
        FileAssignmentTable.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        FileAssignmentTable.setShowGrid(true);
        jScrollPane7.setViewportView(FileAssignmentTable);

        GrpSelComboBox.setEditable(true);
        GrpSelComboBox.setDoubleBuffered(true);
        GrpSelComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GrpSelComboBoxActionPerformed(evt);
            }
        });

        AnimalSelComboBox.setEnabled(false);

        TrialSelComboBox.setEditable(true);
        TrialSelComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TrialSelComboBoxActionPerformed(evt);
            }
        });

        SelDesLabel.setText("Select Grp, Trial and Animal for assigning the data files");

        GrpLabel.setText("Group");

        TrialLabel.setText("Trial");

        AnimalLabel.setText("Animal");

        OpenFileAssignmentsButton.setText("Open File Assignments");
        OpenFileAssignmentsButton.setToolTipText("Open test file with assignments shown in table below(no headers) tab sep col and new line separators for rows");
        OpenFileAssignmentsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OpenFileAssignmentsButtonActionPerformed(evt);
            }
        });

        SaveFileAssignmentsButton.setText("Save Assignments");
        SaveFileAssignmentsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SaveFileAssignmentsButtonActionPerformed(evt);
            }
        });

        xResTxtField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("0"))));
        xResTxtField.setText("1920");
        xResTxtField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                xResTxtFieldActionPerformed(evt);
            }
        });

        yResTxtField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("0"))));
        yResTxtField.setText("1080");
        yResTxtField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                yResTxtFieldActionPerformed(evt);
            }
        });

        jLabel4.setText("x Res");

        jLabel5.setText("yRes");

        jLabel6.setText("Total Number of Files Expected");

        jLabel7.setText("Total Number of Files Assigned");

        jLabel8.setText("Remainaing data files that require assignment/selection");

        expFileNoJText.setEnabled(false);

        assignFileJTxt.setEnabled(false);

        remainingDatafilesJText.setEnabled(false);

        jButtonFileAssignRest.setText("Reset File Assignments");
        jButtonFileAssignRest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonFileAssignRestActionPerformed(evt);
            }
        });

        jCombo_dataSeparator.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "\"\\t\" (Tab)", "  (Space)", "\",\" (Comma)", "\";\"  (Semi-colon)", "(user def: Clear and type the charecter ) " }));
        jCombo_dataSeparator.setSelectedIndex(1);
        jCombo_dataSeparator.setToolTipText("The user can choose one of the listed separators or can provide new. Click to enter and new string");

        jLabel13.setText("Choose the data separator ");

        jFormatTxt_rootFolder.setBackground(new java.awt.Color(204, 255, 204));
        jFormatTxt_rootFolder.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Root Folder for Data Files ", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(204, 255, 204))); // NOI18N
        jFormatTxt_rootFolder.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jFormatTxt_rootFolder.setEnabled(false);

        jButtonBrowseRoot.setText("Browse");
        jButtonBrowseRoot.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBrowseRootActionPerformed(evt);
            }
        });

        jButtonRemoveAssignments.setText("Remove Assignments");
        jButtonRemoveAssignments.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRemoveAssignmentsActionPerformed(evt);
            }
        });

        jButton1.setText("Relativise");
        jButton1.setEnabled(false);

        chkBoxRemoveAssignedFiles.setSelected(true);
        chkBoxRemoveAssignedFiles.setText(" Remove Files  After Assignment");

        javax.swing.GroupLayout DataFiles_jPanelLayout = new javax.swing.GroupLayout(DataFiles_jPanel);
        DataFiles_jPanel.setLayout(DataFiles_jPanelLayout);
        DataFiles_jPanelLayout.setHorizontalGroup(
            DataFiles_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DataFiles_jPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(DataFiles_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(DataFiles_jPanelLayout.createSequentialGroup()
                        .addComponent(AnimalLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(AnimalSelComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(TrialLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(TrialSelComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(GrpLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(GrpSelComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel14)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(DataFiles_jPanelLayout.createSequentialGroup()
                        .addGroup(DataFiles_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jScrollPane7, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, DataFiles_jPanelLayout.createSequentialGroup()
                                .addGroup(DataFiles_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(SaveFileAssignmentsButton)
                                    .addComponent(Assign_Button, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(DataFiles_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(DataFiles_jPanelLayout.createSequentialGroup()
                                        .addComponent(OpenFileAssignmentsButton)
                                        .addGroup(DataFiles_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(DataFiles_jPanelLayout.createSequentialGroup()
                                                .addGap(61, 61, 61)
                                                .addComponent(AddFiles_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(RemoveFile_Button))
                                            .addGroup(DataFiles_jPanelLayout.createSequentialGroup()
                                                .addGap(18, 18, 18)
                                                .addComponent(chkBoxRemoveAssignedFiles, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addComponent(jFormatTxt_rootFolder))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(DataFiles_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton1)
                                    .addComponent(jButtonBrowseRoot, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(38, 38, 38)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(DataFiles_jPanelLayout.createSequentialGroup()
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 691, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(DataFiles_jPanelLayout.createSequentialGroup()
                .addGroup(DataFiles_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(DataFiles_jPanelLayout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addGroup(DataFiles_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(DataFiles_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(assignFileJTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(remainingDatafilesJText, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(DataFiles_jPanelLayout.createSequentialGroup()
                                .addComponent(expFileNoJText, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButtonRemoveAssignments)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButtonFileAssignRest))))
                    .addGroup(DataFiles_jPanelLayout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(SelDesLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 311, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(DataFiles_jPanelLayout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCombo_dataSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(xResTxtField, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(yResTxtField, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        DataFiles_jPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel6, jLabel7});

        DataFiles_jPanelLayout.setVerticalGroup(
            DataFiles_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DataFiles_jPanelLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(SelDesLabel)
                .addGroup(DataFiles_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(DataFiles_jPanelLayout.createSequentialGroup()
                        .addGroup(DataFiles_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(TrialSelComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(AnimalSelComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(GrpSelComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(AnimalLabel)
                            .addComponent(TrialLabel)
                            .addComponent(GrpLabel)
                            .addComponent(AddFiles_Button)
                            .addComponent(RemoveFile_Button))
                        .addGroup(DataFiles_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(DataFiles_jPanelLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(DataFiles_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(DataFiles_jPanelLayout.createSequentialGroup()
                                        .addComponent(jLabel14)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addGroup(DataFiles_jPanelLayout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addGroup(DataFiles_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(SaveFileAssignmentsButton)
                                            .addComponent(OpenFileAssignmentsButton)
                                            .addComponent(chkBoxRemoveAssignedFiles)))))
                            .addGroup(DataFiles_jPanelLayout.createSequentialGroup()
                                .addGroup(DataFiles_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(DataFiles_jPanelLayout.createSequentialGroup()
                                        .addGap(12, 12, 12)
                                        .addComponent(Assign_Button))
                                    .addGroup(DataFiles_jPanelLayout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jFormatTxt_rootFolder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(DataFiles_jPanelLayout.createSequentialGroup()
                        .addGap(0, 4, Short.MAX_VALUE)
                        .addComponent(jButtonBrowseRoot, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(DataFiles_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(DataFiles_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel13)
                        .addComponent(jCombo_dataSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(DataFiles_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4)
                        .addComponent(xResTxtField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel5)
                        .addComponent(yResTxtField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(28, 28, 28)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addGroup(DataFiles_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, DataFiles_jPanelLayout.createSequentialGroup()
                        .addGroup(DataFiles_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(expFileNoJText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButtonRemoveAssignments)
                            .addComponent(jButtonFileAssignRest))
                        .addGap(6, 6, 6)
                        .addComponent(assignFileJTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(remainingDatafilesJText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(DataFiles_jPanelLayout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(6, 6, 6)
                        .addComponent(jLabel7)
                        .addGap(6, 6, 6)
                        .addComponent(jLabel8)))
                .addContainerGap(50, Short.MAX_VALUE))
        );

        DataFiles_jPanelLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {assignFileJTxt, expFileNoJText, jLabel6, jLabel7, jLabel8, remainingDatafilesJText});

        InfoTab.addTab("Data Files", DataFiles_jPanel);

        AnalysisDesign_jPanel.setPreferredSize(new java.awt.Dimension(740, 900));

        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("Experiment");
        javax.swing.tree.DefaultMutableTreeNode treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Probe Trial 1");
        javax.swing.tree.DefaultMutableTreeNode treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Grp 1");
        javax.swing.tree.DefaultMutableTreeNode treeNode4 = new javax.swing.tree.DefaultMutableTreeNode("<click to choose  a datafile>");
        treeNode3.add(treeNode4);
        treeNode2.add(treeNode3);
        treeNode1.add(treeNode2);
        expDgnTree.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        expDgnTree.setAutoscrolls(true);
        expDgnTree.setDoubleBuffered(true);
        expDgnTree.setDragEnabled(true);
        expDgnTree.setEditable(true);
        expDgnTree.setLargeModel(true);
        expDgnTree.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                expDgnTreeMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(expDgnTree);

        RunGrp_Button.setText("Run Grp Analysis");
        RunGrp_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RunGrp_ButtonActionPerformed(evt);
            }
        });

        HeatMap_Button.setText("Generate Heat Map");

        QuadAna_Button.setText("Quadrant Analysis");

        GenConMaps_Button.setText("Generate Convergence Maps");

        GenCurlMaps_Button.setText("Generate Curl Maps");

        jLabel9.setText("Platform X Co-Ordinate");

        jLabel10.setText("Platform Y Co-Ordinate");

        jLabel11.setText("OC X Co-Ordinate");

        jLabel12.setText("OC Y Co-Ordinate");

        PlatXjFtTxt.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        PlatXjFtTxt.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        PlatXjFtTxt.setText("120");

        PlatYjFtTxt1.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        PlatYjFtTxt1.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        PlatYjFtTxt1.setText("120");

        ocXjFtTxt2.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        ocXjFtTxt2.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        ocXjFtTxt2.setText("120");

        ocYjFtTxt3.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        ocYjFtTxt3.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        ocYjFtTxt3.setText("120");

        CheckBoxBoolean.setSelected(true);
        CheckBoxBoolean.setText("Select the check box to auto estimate OC");
        CheckBoxBoolean.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CheckBoxBooleanActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout AnalysisDesign_jPanelLayout = new javax.swing.GroupLayout(AnalysisDesign_jPanel);
        AnalysisDesign_jPanel.setLayout(AnalysisDesign_jPanelLayout);
        AnalysisDesign_jPanelLayout.setHorizontalGroup(
            AnalysisDesign_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AnalysisDesign_jPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(AnalysisDesign_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(AnalysisDesign_jPanelLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(AnalysisDesign_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(HeatMap_Button)
                            .addComponent(GenConMaps_Button)
                            .addComponent(QuadAna_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(RunGrp_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(GenCurlMaps_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(AnalysisDesign_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AnalysisDesign_jPanelLayout.createSequentialGroup()
                                .addGroup(AnalysisDesign_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(AnalysisDesign_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING)))
                                .addGap(33, 33, 33)
                                .addGroup(AnalysisDesign_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(ocXjFtTxt2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(ocYjFtTxt3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(PlatXjFtTxt, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(PlatYjFtTxt1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(CheckBoxBoolean, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(71, 71, 71))
                    .addGroup(AnalysisDesign_jPanelLayout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 728, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        AnalysisDesign_jPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {GenConMaps_Button, GenCurlMaps_Button, HeatMap_Button, QuadAna_Button, RunGrp_Button});

        AnalysisDesign_jPanelLayout.setVerticalGroup(
            AnalysisDesign_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AnalysisDesign_jPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 425, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(AnalysisDesign_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(AnalysisDesign_jPanelLayout.createSequentialGroup()
                        .addGroup(AnalysisDesign_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(AnalysisDesign_jPanelLayout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addGap(4, 4, 4)
                                .addComponent(jLabel10))
                            .addGroup(AnalysisDesign_jPanelLayout.createSequentialGroup()
                                .addComponent(PlatXjFtTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(4, 4, 4)
                                .addComponent(PlatYjFtTxt1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(AnalysisDesign_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AnalysisDesign_jPanelLayout.createSequentialGroup()
                                .addComponent(ocXjFtTxt2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(ocYjFtTxt3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AnalysisDesign_jPanelLayout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel12)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(CheckBoxBoolean))
                    .addGroup(AnalysisDesign_jPanelLayout.createSequentialGroup()
                        .addComponent(RunGrp_Button)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(QuadAna_Button)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(HeatMap_Button)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(GenConMaps_Button)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(GenCurlMaps_Button)))
                .addContainerGap(96, Short.MAX_VALUE))
        );

        InfoTab.addTab("Design Tree", AnalysisDesign_jPanel);

        jPanel1.setPreferredSize(new java.awt.Dimension(732, 900));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Pre Processing", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        gaussjChkBx.setSelected(true);
        gaussjChkBx.setText("Use Gaussian Blur");
        gaussjChkBx.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                gaussjChkBxItemStateChanged(evt);
            }
        });

        jLabel21.setText("Scaling Factor");

        scalingfactorJFormFld.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        scalingfactorJFormFld.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        scalingfactorJFormFld.setText("1.00");
        scalingfactorJFormFld.setToolTipText("The spatial scale used to scale the images (1< && > 0  for down sizing > 1 for magnifying)");
        scalingfactorJFormFld.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                scalingfactorJFormFldActionPerformed(evt);
            }
        });

        jLabel22.setText("Filter Radius");

        gauRadjFormFld.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        gauRadjFormFld.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        gauRadjFormFld.setText("1.0");
        gauRadjFormFld.setToolTipText("The radius of the 2D Gaussian Blur it is symetrical ");

        reSzImgjChkBx.setText("Resize images");
        reSzImgjChkBx.setToolTipText("Enable this to resize images before surface fit");
        reSzImgjChkBx.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                reSzImgjChkBxItemStateChanged(evt);
            }
        });

        useTan2jChkBx.setText("Treat moving away and into platform differently");
        useTan2jChkBx.setToolTipText("By default the software uses tan inverse without differentiating vectors differing by 180 deg. If checked then tan2  inverse is used that differentiates these vectors");

        ScaleY_JChkBx.setSelected(true);
        ScaleY_JChkBx.setText("Scale Y");

        jLabel24.setText("Pixel Aspect Ratio (x/y)");

        aspectRatiojFmtFld.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.000"))));
        aspectRatiojFmtFld.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        aspectRatiojFmtFld.setText("1.00");
        aspectRatiojFmtFld.setToolTipText("Horizontal By Vertical");
        aspectRatiojFmtFld.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aspectRatiojFmtFldActionPerformed(evt);
            }
        });

        useRelVelJChkBx.setSelected(true);
        useRelVelJChkBx.setText(" Use Relative Velocity");
        useRelVelJChkBx.setToolTipText("Selecting this nomalises the Peak velocity of Each Data File to Float.Max");

        jChkBxAssym.setText("Assymetric");

        jCheckBox2.setText("Orientation data from file");
        jCheckBox2.setEnabled(false);

        jLabel15.setText("x Radius");

        jLabel23.setText("y Radius");

        jFrtTxtFld.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFrtTxtFld.setText("0");

        jFmtTxtFldRadX.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFmtTxtFldRadX.setText("0");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(14, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(ScaleY_JChkBx)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(useTan2jChkBx)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(aspectRatiojFmtFld, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(useRelVelJChkBx)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(gaussjChkBx)
                                .addGap(76, 76, 76)
                                .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(reSzImgjChkBx)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel21)
                                .addGap(26, 26, 26))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jChkBxAssym)
                                .addGap(26, 26, 26)
                                .addComponent(jLabel15)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jFmtTxtFldRadX, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel23)
                                .addGap(14, 14, 14))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jCheckBox2)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jFrtTxtFld)
                            .addComponent(scalingfactorJFormFld, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(gauRadjFormFld, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(useRelVelJChkBx)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(reSzImgjChkBx)
                    .addComponent(jLabel21)
                    .addComponent(scalingfactorJFormFld))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(gauRadjFormFld)
                    .addComponent(gaussjChkBx))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jChkBxAssym)
                    .addComponent(jLabel15)
                    .addComponent(jLabel23)
                    .addComponent(jFrtTxtFld, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jFmtTxtFldRadX, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox2)
                .addGap(8, 8, 8)
                .addComponent(useTan2jChkBx)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ScaleY_JChkBx)
                    .addComponent(jLabel24)
                    .addComponent(aspectRatiojFmtFld, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Post Process (Results/Output)"));

        autoPoolRoijChkBx.setSelected(true);
        autoPoolRoijChkBx.setText("Auto determine pool ROI");

        jLabel18.setText("x Ctr");

        xPoolCtrjFormFld.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getIntegerInstance())));
        xPoolCtrjFormFld.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        xPoolCtrjFormFld.setText("0");
        xPoolCtrjFormFld.setMinimumSize(new java.awt.Dimension(60, 25));

        jLabel19.setText("y Ctr");

        yPoolCtrjFormFld.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getIntegerInstance())));
        yPoolCtrjFormFld.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        yPoolCtrjFormFld.setText("0");

        jLabel20.setText("Radius");

        poolRadjFormFld.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getIntegerInstance())));
        poolRadjFormFld.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        poolRadjFormFld.setText("0");

        jCheckBox3.setText("Save magnitude of velocity Vs Radial diststance");

        saveVelocityjchkBx.setText("Save Velocity Maps");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(43, 43, 43)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(saveVelocityjchkBx)
                            .addComponent(jCheckBox3)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(autoPoolRoijChkBx)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(xPoolCtrjFormFld, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(42, 42, 42)
                                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(yPoolCtrjFormFld, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(59, 59, 59)
                        .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35)
                        .addComponent(poolRadjFormFld, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(49, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(autoPoolRoijChkBx)
                .addGap(8, 8, 8)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(xPoolCtrjFormFld, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel19)
                    .addComponent(yPoolCtrjFormFld))
                .addGap(12, 12, 12)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(poolRadjFormFld, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jCheckBox3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(saveVelocityjchkBx)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Surface Fit Setting", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        jLabel16.setText("Order of fit for the x (horizontal axis)");

        x_polyOrderJCmbBx.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" }));
        x_polyOrderJCmbBx.setSelectedIndex(4);
        x_polyOrderJCmbBx.setToolTipText("Determines the degree of the polynomial to be used in surface fit generation. ");

        jLabel17.setText("Order of fit for the y (vertical axis)");

        y_polyOrderJCmbBx.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" }));
        y_polyOrderJCmbBx.setSelectedIndex(4);
        y_polyOrderJCmbBx.setToolTipText("Determines the degree of the polynomial to be used in surface fit generation. ");

        genVeljChkBx1.setText("Use Velocity as is");

        genAccjChkBx.setSelected(true);
        genAccjChkBx.setText("Generate Accelaration Surfaces");

        useSeljChBx.setSelected(true);
        useSeljChBx.setText(" Use Selection( Only use the region that is sampled)");

        res2SeljChkBx.setText("Restrict selection to pixels (Use only the pixels). ");
        res2SeljChkBx.setToolTipText("(This would be a sub-grp of above)");
        res2SeljChkBx.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                res2SeljChkBxActionPerformed(evt);
            }
        });

        genConvJChkBx.setSelected(true);
        genConvJChkBx.setText("Generate Convergence Image");

        genDivjChkBx.setText("Generate Divergence Image");

        vectJChkBx.add(CompforVectorFldjChkBx2);
        CompforVectorFldjChkBx2.setSelected(true);
        CompforVectorFldjChkBx2.setText("Use Components for Convergence");

        compJRadGrp.add(AlongJRadBtn);
        AlongJRadBtn.setSelected(true);
        AlongJRadBtn.setText("Along ");

        compJRadGrp.add(OrtoJRadBtn);
        OrtoJRadBtn.setText("Ortogonal");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(x_polyOrderJCmbBx, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(y_polyOrderJCmbBx, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(genAccjChkBx)
                            .addComponent(useSeljChBx)
                            .addComponent(genDivjChkBx)
                            .addComponent(res2SeljChkBx)
                            .addComponent(genVeljChkBx1)
                            .addComponent(genConvJChkBx)
                            .addComponent(CompforVectorFldjChkBx2))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                            .addComponent(AlongJRadBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(OrtoJRadBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(61, 61, 61))))
                .addContainerGap(8, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(x_polyOrderJCmbBx, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16))
                .addGap(26, 26, 26)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(y_polyOrderJCmbBx, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17))
                .addGap(18, 18, 18)
                .addComponent(genVeljChkBx1)
                .addGap(18, 18, 18)
                .addComponent(genAccjChkBx)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(useSeljChBx)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(res2SeljChkBx)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(genConvJChkBx)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(genDivjChkBx)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(CompforVectorFldjChkBx2)
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AlongJRadBtn)
                    .addComponent(OrtoJRadBtn))
                .addContainerGap(70, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(7, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 214, Short.MAX_VALUE))
        );

        InfoTab.addTab("Analysis Setting", jPanel1);

        DeskTopPanel.add(InfoTab, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 710, 700));

        jScrollPane4.setViewportView(DeskTopPanel);
        DeskTopPanel.getAccessibleContext().setAccessibleName("");
        var parentSize = DeskTopPanel.getParent().getSize();
        DeskTopPanel.setSize((int)parentSize.getWidth()/2,(int)parentSize.getHeight()/2);

        getContentPane().add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 720, 580));

        jScrollPane5.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        jScrollPane5.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPane5.setAutoscrolls(true);
        jScrollPane5.setPreferredSize(new java.awt.Dimension(600, 700));

        ImageDisplay_Panel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        ImageDisplay_Panel.setMaximumSize(new java.awt.Dimension(650000, 6500000));
        ImageDisplay_Panel.setMinimumSize(new java.awt.Dimension(0, 0));
        ImageDisplay_Panel.setName("ImageDisplay Panel"); // NOI18N
        ImageDisplay_Panel.setPreferredSize(new java.awt.Dimension(525, 700));

        ProgIndPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Progress Indicator", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));
        ProgIndPanel.setPreferredSize(new java.awt.Dimension(500, 100));

        jProgressBarDataAssignment.setToolTipText("Indicates the progress of data assignment");
        jProgressBarDataAssignment.setStringPainted(true);

        jProgressBarTP.setStringPainted(true);

        jLabel25.setText("Trials Processed");

        jProgressBarGP.setStringPainted(true);

        jLabel26.setText("Grps Processed");

        jProgressBarDP.setStringPainted(true);

        jLabel27.setText("Data processed");

        jProgressBarFR.setToolTipText("Indicates the progress of data assignment");
        jProgressBarFR.setStringPainted(true);

        jLabel29.setText("Data Assignment Status");

        jLabel28.setText("Files Read");

        javax.swing.GroupLayout ProgIndPanelLayout = new javax.swing.GroupLayout(ProgIndPanel);
        ProgIndPanel.setLayout(ProgIndPanelLayout);
        ProgIndPanelLayout.setHorizontalGroup(
            ProgIndPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ProgIndPanelLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(ProgIndPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel29)
                    .addComponent(jLabel28)
                    .addComponent(jProgressBarDataAssignment, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jProgressBarFR, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ProgIndPanelLayout.createSequentialGroup()
                        .addGroup(ProgIndPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(ProgIndPanelLayout.createSequentialGroup()
                                .addComponent(jLabel25)
                                .addGap(0, 45, Short.MAX_VALUE))
                            .addComponent(jProgressBarTP, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(ProgIndPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel26)
                            .addGroup(ProgIndPanelLayout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jProgressBarGP, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(28, 28, 28)
                        .addGroup(ProgIndPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel27)
                            .addComponent(jProgressBarDP, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(17, 17, 17))
        );
        ProgIndPanelLayout.setVerticalGroup(
            ProgIndPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ProgIndPanelLayout.createSequentialGroup()
                .addComponent(jLabel29)
                .addGap(12, 12, 12)
                .addComponent(jProgressBarDataAssignment, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(jLabel28)
                .addGap(5, 5, 5)
                .addComponent(jProgressBarFR, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addGroup(ProgIndPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(ProgIndPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jLabel27))
                .addGap(6, 6, 6)
                .addGroup(ProgIndPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jProgressBarGP, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jProgressBarDP, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jProgressBarTP, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 17, Short.MAX_VALUE))
        );

        MessageBox_Panel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Video/Image/Graph Display", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));
        MessageBox_Panel.setAutoscrolls(true);
        MessageBox_Panel.setPreferredSize(new java.awt.Dimension(500, 500));

        javax.swing.GroupLayout MessageBox_PanelLayout = new javax.swing.GroupLayout(MessageBox_Panel);
        MessageBox_Panel.setLayout(MessageBox_PanelLayout);
        MessageBox_PanelLayout.setHorizontalGroup(
            MessageBox_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 480, Short.MAX_VALUE)
        );
        MessageBox_PanelLayout.setVerticalGroup(
            MessageBox_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 377, Short.MAX_VALUE)
        );

        jButton5.setText("jButton2");

        jButton6.setText("jButton2");

        jButton7.setText("jButton2");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(352, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout ImageDisplay_PanelLayout = new javax.swing.GroupLayout(ImageDisplay_Panel);
        ImageDisplay_Panel.setLayout(ImageDisplay_PanelLayout);
        ImageDisplay_PanelLayout.setHorizontalGroup(
            ImageDisplay_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ImageDisplay_PanelLayout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 31, Short.MAX_VALUE))
            .addGroup(ImageDisplay_PanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ImageDisplay_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(MessageBox_Panel, javax.swing.GroupLayout.PREFERRED_SIZE, 490, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ProgIndPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 490, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        ImageDisplay_PanelLayout.setVerticalGroup(
            ImageDisplay_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ImageDisplay_PanelLayout.createSequentialGroup()
                .addComponent(ProgIndPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(MessageBox_Panel, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jScrollPane5.setViewportView(ImageDisplay_Panel);

        getContentPane().add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 0, 530, 580));

        jScrollPane8.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPane8.setViewportBorder(javax.swing.BorderFactory.createTitledBorder("Status Messages"));
        jScrollPane8.setAutoscrolls(true);
        jScrollPane8.setViewportView(null);

        StatusMessageBox.setColumns(20);
        StatusMessageBox.setLineWrap(true);
        StatusMessageBox.setRows(5);
        StatusMessageBox.setWrapStyleWord(true);
        StatusMessageBox.setBorder(null);
        StatusMessageBox.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        StatusMessageBox.setDragEnabled(true);
        jScrollPane8.setViewportView(StatusMessageBox);

        getContentPane().add(jScrollPane8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 580, 1250, 130));

        menuBar.setAutoscrolls(true);
        menuBar.setMinimumSize(new java.awt.Dimension(300, 23));
        menuBar.setPreferredSize(new java.awt.Dimension(400, 23));

        fileMenu.setMnemonic('f');
        fileMenu.setText("File");

        openMenuItem.setMnemonic('o');
        openMenuItem.setText("Open");
        fileMenu.add(openMenuItem);

        saveMenuItem.setMnemonic('s');
        saveMenuItem.setText("Save");
        fileMenu.add(saveMenuItem);

        saveAsMenuItem.setMnemonic('a');
        saveAsMenuItem.setText("Save As ...");
        saveAsMenuItem.setDisplayedMnemonicIndex(5);
        fileMenu.add(saveAsMenuItem);

        jFolderOptions.setText("Folder Preferences");
        jFolderOptions.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jFolderOptionsActionPerformed(evt);
            }
        });
        fileMenu.add(jFolderOptions);

        ImportMenuItem.setText("Import Data");
        ImportMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ImportMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(ImportMenuItem);

        exitMenuItem.setMnemonic('x');
        exitMenuItem.setText("Exit");
        exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        mapsMenu.setText("Maps");

        residencemapMenuItem.setText("Create Residence Map");
        residencemapMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                residencemapMenuItemActionPerformed(evt);
            }
        });
        mapsMenu.add(residencemapMenuItem);

        menuBar.add(mapsMenu);

        analysisMenu.setText("Analysis");

        jMenuItemGrpID.setText("Identify Groups");
        analysisMenu.add(jMenuItemGrpID);

        jMenuItemExpDes.setText("Experimental Design");
        analysisMenu.add(jMenuItemExpDes);

        jMenuItemMeasure.setText("Select Measures");
        analysisMenu.add(jMenuItemMeasure);

        jMenuItemCompute.setText("Compute");
        jMenuItemCompute.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemComputeActionPerformed(evt);
            }
        });
        analysisMenu.add(jMenuItemCompute);

        menuBar.add(analysisMenu);

        jMenu1.setText("Video/Image");

        jMenuItem1.setText("Zones");
        jMenu1.add(jMenuItem1);

        jMenuItem6.setText("Video Properties");
        jMenu1.add(jMenuItem6);

        menuBar.add(jMenu1);

        jMenu2.setText("Tracking");
        menuBar.add(jMenu2);

        editMenu.setMnemonic('e');
        editMenu.setText("Edit");
        editMenu.setEnabled(false);

        cutMenuItem.setMnemonic('t');
        cutMenuItem.setText("Cut");
        editMenu.add(cutMenuItem);

        copyMenuItem.setMnemonic('y');
        copyMenuItem.setText("Copy");
        editMenu.add(copyMenuItem);

        pasteMenuItem.setMnemonic('p');
        pasteMenuItem.setText("Paste");
        editMenu.add(pasteMenuItem);

        deleteMenuItem.setMnemonic('d');
        deleteMenuItem.setText("Delete");
        editMenu.add(deleteMenuItem);

        menuBar.add(editMenu);

        helpMenu.setMnemonic('h');
        helpMenu.setText("Help");
        helpMenu.setEnabled(false);

        contentMenuItem.setMnemonic('c');
        contentMenuItem.setText("Contents");
        helpMenu.add(contentMenuItem);

        aboutMenuItem.setMnemonic('a');
        aboutMenuItem.setText("About");
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        setJMenuBar(menuBar);

        setBounds(0, 0, 1276, 748);
    }// </editor-fold>//GEN-END:initComponents

    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuItemActionPerformed
        System.exit(0);
    }//GEN-LAST:event_exitMenuItemActionPerformed

    private void ImportMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ImportMenuItemActionPerformed
        // TODO add your handling code here:
        //Choose data file
        //Open datafile
        //Initialise the data using DataTrace
        //Calculate the velocity using differentiate function
        //Generate the residence time weighted heat map,velocity map, component along search center and orthogonal
        //Differentiate and produce the differentials
        File startDirectory = new File(this.jFormatTxt_rootFolder.getText());
        var inpDataDialog = new MultiFileDialog(null,true,startDirectory);
        //inpDataDialog.setStartDirectory(startDirectory);
        inpDataDialog.setVisible(true);
        
        var fNames = inpDataDialog.getSelectionArray();
        
        if(fNames.length >0)
            populateDataFileList(fNames);
        
    }//GEN-LAST:event_ImportMenuItemActionPerformed

    private void populateDataFileList(String[] fNames) {
               
        
        //File rootFolder = new File(fNames[0]).getParentFile();
        
        String [] failedFiles = new String[fNames.length];
        if(rel2absPathMaps == null)
            this.rel2absPathMaps = new ConcurrentHashMap();
        
        int presentCount = FileDetailModel.getRowCount();           //Assumes empty table occupies 0 rows. Need to ensure that
        FileDetailModel.setRowCount(presentCount+fNames.length);
        
        int sCount = presentCount, fCount = 0; //count of successfully opened files and count of files failed to open
        
        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
        cellRenderer.setHorizontalAlignment(SwingConstants.TRAILING);
        this.FileDetail_Table.getColumnModel().getColumn(0).setCellRenderer(cellRenderer);
        
        Path startpath, path2Display,path;
        File stFolder = null ;
        
        String userStartpath = this.jFormatTxt_rootFolder.getText();
        if( ! userStartpath.isBlank())
         stFolder = new File(userStartpath);
        //else
        
        startpath = (stFolder!= null && stFolder.exists()) ? stFolder.toPath() : new File(fNames[0]).toPath().getParent();
        String path2root = startpath.toString();
        
        this.jFormatTxt_rootFolder.setText(path2root);
        
        //dManager = new DataManager();
        //dManager.setInPath(rootFolder.getName());
        //dManager.setInPath(path2root);
        //dManager.setDataFileNames(fNames);
        
        
        for(var name : fNames){
            var file = new File(name);
            if (file.exists()){
                //dManager.getDataFileNames()[sCount]= name;
                path = file.toPath();
                if(path.getRoot().equals(startpath.getRoot()))
                     path2Display = startpath.relativize(path.getParent());
                else
                     path2Display = file.getParentFile().toPath();
                
                String justName = file.getName();
                String key = path2Display+File.separator+justName;
                this.FileDetailModel.setValueAt(path2Display.toFile().getPath()/*file.getParent()*/, sCount,0);
                this.FileDetailModel.setValueAt(file.getName(), sCount,1);
                rel2absPathMaps.put(key, name);
                sCount++;
            }
            else
                failedFiles[fCount++] = name;
        }
        
        //dManager.readData(); 
    }

    private void residencemapMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_residencemapMenuItemActionPerformed
        generateResidenceMap(dManager);
    }//GEN-LAST:event_residencemapMenuItemActionPerformed

    private void generateResidenceMap(DataManager manager) {
        // TODO add your handling code here:
        int xRes = manager.getXRes();
        int yRes = manager.getYRes();
        int count = 0;
        JHeatMapArray rMaps[] = new JHeatMapArray[manager.getFileCount()];
        JVectorCmpImg [] rmapImages = new JVectorCmpImg[manager.getFileCount()];
        
        //dManager.aveHMap = new FloatProcessor(dManager.getXRes(),dManager.getYRes(),residenceMap.to1DArray());
        
        for(var timeTrace : manager.getTimeData()){
            
            JHeatMapArray residenceMap = new JHeatMapArray(xRes,yRes);
            residenceMap.setTimeSeries(timeTrace);
            
            rmapImages[count] = new JVectorCmpImg(xRes,yRes,1);
            rmapImages[count].addScalar(residenceMap);
            //rMaps[count] = residenceMap;
            count++;
        }
        
        String folderpath = manager.getOutPath();
        if(!folderpath.isBlank())
            folderpath = folderpath + File.separator+"Residence Maps";
        else
            folderpath = System.getProperty("user.dir")+File.separator+"Residence Maps";
        
        File newDir = new File(folderpath);
        //boolean mkdir = false;
        if(!newDir.isDirectory())
                        newDir.mkdir();
        //folderpath = (mkdir) ? folderpath : manager.getOutPath();
        int fileCount = 0;
        this.hmapStk = new ImagePlus("HeatMaps" );
        this.stk = new ImageStack(manager.getXRes(),manager.getYRes());
        String label;
        for(var rmap : rmapImages){
            var tmpName = (manager.getDataFileNames()[fileCount]);
            label  = "HMap of "+ tmpName.substring(1+tmpName.lastIndexOf(File.separator));  
            stk.addSlice(rmap.getImages()[0].getProcessor());
            stk.setSliceLabel(label,fileCount+1);
            rmap.saveImages(folderpath,label);
            fileCount++;
        }
        hmapStk.setStack(stk);
        hmapStk.show();
    }

    private void jFolderOptionsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFolderOptionsActionPerformed
        // TODO add your handling code here:
        //Design GUI to let user select data folders, resutls folder etc.
        //These data are stored in datamanager. 
        //These options along with any other project specific details can be stored and retrived 
        //when the user saves the project. 
        JFileChooser fileChooser = new JFileChooser();
        if(!dManager.getInPath().isBlank()){
            File startDirectory = new File(dManager.getInPath());
            if(startDirectory.isDirectory())
                fileChooser.setCurrentDirectory(startDirectory);
        }
        fileChooser.setMultiSelectionEnabled(false);
        fileChooser.setDialogType(JFileChooser.DIRECTORIES_ONLY & JFileChooser.OPEN_DIALOG);
        int retCode = fileChooser.showOpenDialog(this);
        if(retCode == JFileChooser.APPROVE_OPTION)
            dManager.setInPath(fileChooser.getSelectedFile().getAbsolutePath());
        
        JFileChooser fileChooser2 = new JFileChooser();
        if(!dManager.getOutPath().isBlank()){
            File startDirectory = new File(dManager.getOutPath());
            if(startDirectory.isDirectory())
                fileChooser.setCurrentDirectory(startDirectory);
        }
        fileChooser2.setMultiSelectionEnabled(false);
        fileChooser2.setDialogType(JFileChooser.DIRECTORIES_ONLY & JFileChooser.SAVE_DIALOG );
         retCode = fileChooser.showSaveDialog(this);
            if(retCode == JFileChooser.APPROVE_OPTION)
                dManager.setOutPath(fileChooser.getSelectedFile().getAbsolutePath());
    }//GEN-LAST:event_jFolderOptionsActionPerformed

    private void jMenuItemComputeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemComputeActionPerformed
        // TODO add your handling code here:
        dManager.readData();
        this.generateResidenceMap(dManager);      
        int count = 0 ;
        var accelField = dManager.getAccelarationField();
        JVectorCmpImg [] rMaps,velocityMaps,accelMaps;
        velocityMaps = new JVectorCmpImg[dManager.getFileCount()];
        accelMaps = new JVectorCmpImg[dManager.getFileCount()];
        rMaps = new JVectorCmpImg[dManager.getFileCount()];
        JVectorSpace aField;
        JHeatMapArray rMap;
        for(JVectorSpace vField : dManager.getVelocityField()){
            
            velocityMaps[count] = new JVectorCmpImg(vField);
            accelMaps[count] = new JVectorCmpImg(accelField[count]); 
            velocityMaps[count].saveImages(dManager.getOutPath(), dManager.getDataFileNames()[count]+"Vel");
            accelMaps[count].saveImages(dManager.getOutPath(),dManager.getDataFileNames()[count]+"Acc");
            
        }
        
         
        
    }//GEN-LAST:event_jMenuItemComputeActionPerformed

    private void res2SeljChkBxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_res2SeljChkBxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_res2SeljChkBxActionPerformed

    private void aspectRatiojFmtFldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aspectRatiojFmtFldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_aspectRatiojFmtFldActionPerformed

    private void reSzImgjChkBxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_reSzImgjChkBxItemStateChanged

        this.scalingfactorJFormFld.setEnabled(reSzImgjChkBx.isSelected());
    }//GEN-LAST:event_reSzImgjChkBxItemStateChanged

    private void scalingfactorJFormFldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_scalingfactorJFormFldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_scalingfactorJFormFldActionPerformed

    private void gaussjChkBxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_gaussjChkBxItemStateChanged

        this.gauRadjFormFld.setEnabled(gaussjChkBx.isSelected());
    }//GEN-LAST:event_gaussjChkBxItemStateChanged

    private void CheckBoxBooleanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CheckBoxBooleanActionPerformed

        estimateOC = ( CheckBoxBoolean.isSelected());

    }//GEN-LAST:event_CheckBoxBooleanActionPerformed

    private void RunGrp_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RunGrp_ButtonActionPerformed
        
       
        
        
        //27th Mar: Would be nice to segregate the progress bar updating to separate function that 
        //takes the progressbar and its value as argument and updates it in a separate thread rather than 
        //creating a thread on the fly everytime. 

        //Form threadGrp relevant data managers
        //Get average images
        //Process both individual as well as threadGrp through
        // steps of i) interpolation - surface fit , bilinear and gaussian blur
        //         ii) differentiation -
        //        iii) divergence maps -
        //expData = new ArrayList<ArrayList>();

        //Reading parameters and populate design tree
        
        this.setStatusMessage("Starting new calculation:+\n");
        
        int xRes = Integer.parseInt(this.xResTxtField.getText());
        int yRes = Integer.parseInt(this.yResTxtField.getText());
        int xPlt = Integer.parseInt(this.PlatXjFtTxt.getText());
        int yPlt = Integer.parseInt(this.PlatYjFtTxt1.getText());
        int  xOC = Integer.parseInt(this.ocXjFtTxt2.getText());
        int  yOC = Integer.parseInt(this.ocYjFtTxt3.getText());

        String dataSeparator;// ((String)this.jCombo_dataSeparator.getSelectedItem());
        var selIdx = this.jCombo_dataSeparator.getSelectedIndex();
        
        this.RunGrp_Button.setEnabled(false);
        
        switch(selIdx){
            case 0:     //Tab
            dataSeparator = "\t";
            break;
            case 1: //Space
            dataSeparator = " ";
            break;
            case 2: //Comma
            dataSeparator = ",";
            break;
            case 3: //semicolon
            dataSeparator = ";";
            break;
            default:
            dataSeparator = (String)jCombo_dataSeparator.getSelectedItem();
        }
        //Distribute the files to trials and groups
        //ensures clearing of unassigned trials and grps.
        grpNames.clear();
        trialNames.clear();

        String g,t;
        var  tModel = FileAssignmentTable.getModel();
        for(int Count  = FileAssignmentTable.getModel().getRowCount()-1 ; Count >= 0 ; Count --){

            g = (String)tModel.getValueAt(Count, 2);            //grp name
            t = (String)tModel.getValueAt(Count, 3);            //trial name

            if (!grpNames.contains(g))                          //Check if these names are there already if not add them
                grpNames.add(g);
            
            if(!trialNames.contains(t))
                trialNames.add(t);

        }

        //Prepare the Data structures to take in the data set
        //Trials data is an arraylist of grps. Grps are of the type DataManager.
        TrialData = new ArrayList<>();
        DataManager grpData;

        //Prepare the exp tree display

        DefaultMutableTreeNode trialNode,grpNode;
        trialRoot = new DefaultMutableTreeNode("Experiment");

        treeModel = (DefaultTreeModel) expDgnTree.getModel();
        treeModel.setRoot(trialRoot);

        nTrial = trialNames.size();
        nGrps = grpNames.size();

        //        var trialProg =  this.jProgressBarDataAssignment.getModel();
        //        trialProg.setMaximum(nTrial);

        this.jProgressBarDataAssignment.setMinimum(0);
        this.jProgressBarDataAssignment.setMaximum((nTrial)*(nGrps));
        this.jProgressBarTP.setValue(0);
        this.jProgressBarDataAssignment.setValue(0);
        
        //jProgressBarDataAssignment.setEnabled(true);

        for(int trialCount = 0 ; trialCount < nTrial ; trialCount++){

            trialNode = new DefaultMutableTreeNode(trialNames.get(trialCount));
            treeModel.insertNodeInto(trialNode,trialRoot, trialCount);
            ArrayList <DataManager> trialData = new ArrayList<>();
            for(int grpCount = 0 ; grpCount < nGrps ; grpCount++){
                grpData = new DataManager();
                trialData.add(grpCount,grpData);
                grpNode = new DefaultMutableTreeNode(grpNames.get(grpCount));
                treeModel.insertNodeInto(grpNode, trialNode,grpCount);
                jProgressBarDataAssignment.setValue((grpCount+1)*(trialCount+1));
            }
            TrialData.add(trialCount, trialData);

        }

        treeModel.reload();

        int nFiles = FileAssignmentTable.getRowCount();
        if(nFiles <= 0 )
            return;
        String fName = "", grpName, trialName, fnameKey;
        //        int aUID;
        int gUID;
        int tUID;
        int [][] nFileAssigned;
        String [] errorlist = new String[nFiles];
        int unassigned = 0;

        nFileAssigned = new int[trialNames.size()][grpNames.size()];
        DefaultMutableTreeNode fileLeaf,trNode;

        //jProgressBarDataAssignment.setEnabled(true);

        jProgressBarFR.setMinimum(0);
        jProgressBarFR.setMaximum(nFiles-1);
        jProgressBarFR.setValue(0);

        for(int Count = 0 ; Count < nFiles ; Count++){

            fnameKey = (String)FileAssignmentTable.getValueAt(Count,0);
            fName = this.rel2absPathMaps.get(fnameKey);             //get the file name with full path if it is relativised
            if(fName == null){
                //javax.swing.JOptionPane.showMessageDialog(this, "fileName is null the key "+fnameKey+" did not fetch a file");
                errorlist[unassigned++] = fnameKey;
                return;
            }
            grpName = (String)FileAssignmentTable.getValueAt(Count, 2);
            trialName = (String)FileAssignmentTable.getValueAt(Count,3);
            gUID = grpNames.indexOf(grpName);
            tUID = trialNames.indexOf(trialName);
            //aName =  Need to set the animal ID here

            nFileAssigned[tUID][gUID]++;
            TrialData.get(tUID).get(gUID).addDataFile(/*aUID,*/fName); //Need to retrive aUID coresponding to aName
            fileLeaf = new DefaultMutableTreeNode(fName);
            trNode = ((DefaultMutableTreeNode)treeModel.getChild(trialRoot, tUID));
            grpNode = ((DefaultMutableTreeNode)treeModel.getChild(trNode, gUID));
            treeModel.insertNodeInto(fileLeaf,grpNode, grpNode.getChildCount());

            //int currVal = Count;
            
            this.UpdateProgress(100*(Count+1)/nFiles, jProgressBarFR,"%");
            
//            SwingWorker upFR = new SwingWorker(){
//                @Override
//                protected Object doInBackground() throws Exception {
//                    jProgressBarFR.setValue(currVal);
//                    //jProgressBarDataAssignment.setValue(currVal);
//                    //System.out.print("Current File # \n"+currVal);
//                    return 0;
//                }
//            };
//            new Thread(threadGrp,upFR).start();
            //upFR.execute();
        }

        //Use the errorlist to show the list of files that could not be read.
        //Data Assignment complete

        //Calculation of velocity and accelaration begins
        //DataManager currManager;

        fit.setPreScale(this.reSzImgjChkBx.isSelected());
        fit.setScaleBy(Double.parseDouble(this.scalingfactorJFormFld.getText()));

        fit.setGaussFilt(this.gaussjChkBx.isSelected());
        fit.setGaussRad(Double.parseDouble(this.gauRadjFormFld.getText()));

        fit.setUseSelection(this.useSeljChBx.isSelected());
        fit.setSelectPixels(this.res2SeljChkBx.isSelected());

        jProgressBarTP.setMinimum(0);
        jProgressBarTP.setMaximum(nTrial);
        jProgressBarTP.setValue(0);
        jProgressBarTP.setStringPainted(true);

        jProgressBarGP.setMinimum(0);
        jProgressBarGP.setMaximum(nGrps);
        jProgressBarGP.setValue(0);
        jProgressBarGP.setStringPainted(true);

        jProgressBarDP.setMinimum(0);
        jProgressBarDP.setMaximum(100);
        //                jProgressBarDP.setMaximum(0);
        //                jProgressBarDP.setMaximum(0);
        jProgressBarDP.setValue(0);
        jProgressBarDP.setStringPainted(true);

        OccCtrs = new JVector[nTrial][nGrps];
        ArrayList<ArrayList<DataManager>>[] tempData;
        tempData = new ArrayList[1];
        tempData[0] = TrialData;
        String[] fnames = new String[1];
        fnames[0] = fName;
        SwingWorker worker = new SwingWorker(){
            @Override
        protected Object doInBackground() throws Exception{
                for(int tCount = 0 ; tCount < nTrial ; tCount++){
                    int tc = tCount+1;
//                    UpdateProgress(tc,jProgressBarTP,"Trial #",nTrial+"");
                    for(int gCount = 0 ; gCount < nGrps ; gCount++){

                        int g = gCount+1, t = tCount+1;
//                        UpdateProgress(g,jProgressBarGP,"Grp #",nGrps+"");
                        setStatusMessage("Currently Processing"+"Trial #"+t +"Grp #" + g + "\n");
                        if(nFileAssigned[tCount][gCount] == 0 )     /** This condition should never occur need to check **/
                            continue;
                        DataManager currManager;
                        /*  Prepare the datamanager to organise the data. Data Manger instance stores the data for the group. */
                        //var temp = tempData[0].get(tCount);
                        currManager = tempData[0].get(tCount).get(gCount);
                        currManager.setXRes(xRes);
                        currManager.setYRes(yRes);
                        currManager.setScaleforAspectRatio(ScaleY_JChkBx.isSelected());
                        currManager.setPixelAspectRatio(Double.parseDouble(aspectRatiojFmtFld.getText()));
                        currManager.setDataSep(dataSeparator);
                        currManager.setLineSep('\n');   /* Modify this if the data is not line by line for e.g. separated by : */
                        currManager.setUseRelativeVelocity(useRelVelJChkBx.isSelected());
                        
                        File tmpFile = new File(fnames[0]);
                        String outPath = tmpFile.getParent()+ File.separator+trialNames.get(tCount)+File.separator+grpNames.get(gCount);
                        /* Path points to a folder named after the trail name containg another folder corresponding to threadGrp*/
                        //                        currManager.setOutPath(outPath);
                        //                        Thread dataProcessingThd = new Thread(currManager);
                        //                        dataProcessingThd.start();
                        currManager.setOutPath(outPath);
                        
                        //currManager.run();  //Incorrect invocation
                        
                        DataManager[] tempMan = new DataManager[1];
                        tempMan[0] = currManager;
                        
                        Thread tempThread = new Thread( currManager,"DataReader#"+t*g);
                        allThreads.add(tempThread);
                        tempThread.start();
                        setStatusMessage("DataReader#"+t*g+" started"+"Thread Count "+"\n");
                        
                        jVecFieldImgGenerator(tempMan[0], xRes, yRes, xOC, yOC); // Need to refactor this to remove the redundant  arguments
                                                                                 // Data manager has xRes, yRes and xOC and yOC;
                        calAveFlds(currManager,gCount,tCount,xRes,yRes);
                        calIndividualFlds(currManager);
                        /**
                         * calIndFlds(tempMan[0]) 
                         */
                        UpdateProgress(g,jProgressBarGP,"Grp #",nGrps+"complete");
                    }
                    UpdateProgress(tc,jProgressBarTP,"Trial #",nTrial+"complete");
                }
                return null;
            }

           
        };
       
//        worker.execute();
        Thread tp = new Thread( worker,"RunGrp");
        allThreads.add(tp);
        tp.start();
        setStatusMessage("RunGrp started"+"\n");
        
//        boolean live = true;
//        while(live){
//            for(Object tp1 : allThreads){
//                
//            }
//        }
      
    }//GEN-LAST:event_RunGrp_ButtonActionPerformed
    private void calIndividualFlds(DataManager manager){
        
        
    }
    private void setStatusMessage(String message, boolean toAppend){
       SwingWorker messenger = new SwingWorker(){
           @Override
           protected Object doInBackground() throws Exception {
               if(toAppend)
                    StatusMessageBox.append(message);
               else
                    StatusMessageBox.setText(message);
             return message;
           }
       };
       messenger.execute();
    }
    private void setStatusMessage(String message){
        setStatusMessage(message,true);
    }
    private void UpdateProgress(int tc, JProgressBar progressBar, String prefix,String suffix) {
                SwingWorker upTP = new SwingWorker(){
                    @Override
                    protected Object doInBackground() throws Exception {
                        progressBar.setValue(tc);
                        progressBar.setString(prefix +(tc)+" of "+suffix);
                        return null;
                    }
                };
                upTP.execute();
   }
    private void UpdateProgress(int tc, JProgressBar progressBar, String string) {
                   SwingWorker upTP = new SwingWorker(){
                       @Override
                       protected Object doInBackground() throws Exception {
                           progressBar.setValue(tc);
                           progressBar.setString(tc+string);
                           return null;
                       }
                   };
                   upTP.execute();
      }
    private void expDgnTreeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_expDgnTreeMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_expDgnTreeMouseClicked

    private void jButtonRemoveAssignmentsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRemoveAssignmentsActionPerformed
        // TODO add your handling code here:
        int[] selIdx = this.FileAssignmentTable.getSelectedRows();
        int nSelected = selIdx.length;
        if(nSelected > 0)
        for(int count = nSelected -1 ; count >= 0 ; count --)
        this.FileAssignmentModel.removeRow(selIdx[count]);
        else
        javax.swing.JOptionPane.showMessageDialog(this, "No file assignments are selected to remove: "+nSelected);
    }//GEN-LAST:event_jButtonRemoveAssignmentsActionPerformed

    private void jButtonBrowseRootActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBrowseRootActionPerformed

        JFileChooser fc =  new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fc.setCurrentDirectory(new File(this.jFormatTxt_rootFolder.getText()));
        int status = fc.showOpenDialog(this);

        if(status == JFileChooser.APPROVE_OPTION){
            this.jFormatTxt_rootFolder.setText(fc.getSelectedFile().getPath());
        }

    }//GEN-LAST:event_jButtonBrowseRootActionPerformed

    private void jButtonFileAssignRestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonFileAssignRestActionPerformed
        // TODO add your handling code here:
        this.FileAssignmentModel.setRowCount(0);
    }//GEN-LAST:event_jButtonFileAssignRestActionPerformed

    private void yResTxtFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_yResTxtFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_yResTxtFieldActionPerformed

    private void xResTxtFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_xResTxtFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_xResTxtFieldActionPerformed

    private void SaveFileAssignmentsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SaveFileAssignmentsButtonActionPerformed
        // TODO add your handling code here:
        //Add code to read the table
        //Prompt for file name
        //write to the file
        JFileChooser fc = new JFileChooser();
        fc.showSaveDialog(this);
        FileWriter file2Write;
        var saveFile = fc.getSelectedFile();
        if(saveFile == null)
            return;
        else
        
            try {
                file2Write = new FileWriter(saveFile);
            }catch (IOException ex) {
            javax.swing.JOptionPane.showMessageDialog(this,"Error opeing or creating "+saveFile.getAbsolutePath()+" file for saving");
            return;
            }
        
        int nEntries = FileAssignmentTable.getModel().getRowCount();
       
        for(int Count  = 0 ; Count < nEntries ; Count++){
            var fnameKey = (String)FileAssignmentTable.getValueAt(Count,0);
            var fName = this.rel2absPathMaps.get(fnameKey);             //get the file name with full path if it is relativised
            if(fName == null){
                //javax.swing.JOptionPane.showMessageDialog(this, "fileName is null the key "+fnameKey+" did not fetch a file");
                //errorlist[unassigned++] = fnameKey;
                //return;
            }else{
                String string2write = fName + ',' + (String)FileAssignmentTable.getValueAt(Count,1)
                + ',' + (String)FileAssignmentTable.getValueAt(Count,2)
                + ',' + (String)FileAssignmentTable.getValueAt(Count,3)+'\n';
                try {
                    file2Write.write(string2write);
                } catch (IOException ex) {
                    //Logger.getLogger(VectorAnalysisMDI.class.getName()).log(Level.SEVERE, null, ex);
                    javax.swing.JOptionPane.showMessageDialog(this,"Error writing " + string2write +" to "+saveFile.getAbsolutePath());
                }
            }
        }
        try {
            file2Write.close();
        } catch (IOException ex) {
             javax.swing.JOptionPane.showMessageDialog(this,"Error closing " +saveFile.getAbsolutePath());
        }
    }//GEN-LAST:event_SaveFileAssignmentsButtonActionPerformed

    private void OpenFileAssignmentsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OpenFileAssignmentsButtonActionPerformed

        JFileChooser Fc = new JFileChooser();
        Fc.setMultiSelectionEnabled(false);         //list all the sssignments in one input file

        int status = Fc.showOpenDialog(this);

        if(status != JFileChooser.APPROVE_OPTION)
            return;

        this.jButtonRemoveAssignmentsActionPerformed(evt);
        if(rel2absPathMaps == null)
            this.rel2absPathMaps = new ConcurrentHashMap();

        File asFile = Fc.getSelectedFile();
        FileReader reader;
        String Line = "", segs [] ,fName, GrpId, TrailId, startFrame, endFrame;
        int c;
        int recordLength = 4;           //length of each line describing the data in the information file
        if(asFile.exists()){

            try {
                reader = new FileReader(asFile);
                while(  (c = reader.read()) != -1){
                    if(c == '\n' ){
                        segs = Line.split(""+",");
                        DefaultTableModel TB = (DefaultTableModel) FileAssignmentTable.getModel();

                        if(segs.length < recordLength){
                            javax.swing.JOptionPane.showMessageDialog(this,
                                "Format mismatch for importing file assignments: read ("+segs.length+") expected ("+recordLength+")"
                                +'\n'+ Line);
                            return;

                        }
                        TB.addRow(segs);
                        //                        var pathString = segs[0];
                        //                        Path path = new File(pathString).toPath();
                        //                        path.getParent().toString();
                        var fileName = segs[0].trim();
                        var aName = segs[1].trim();
                        var gName = segs[2].trim();
                        var tName = segs[3].trim();
                        String prev = rel2absPathMaps.put(fileName,fileName);
                        if(prev != null)
                        javax.swing.JOptionPane.showMessageDialog(null, "Found previous entry for file: removed and updated");
                        if(grpNames.indexOf(gName)== -1)
                            grpNames.add(gName);
                        if(trialNames.indexOf(tName)== -1)
                            trialNames.add(tName);
                        Line = "";
                    }else{
                        if(c > 31) Line += (char)c;
                    }
                }
            } catch (FileNotFoundException ex) {
                //Logger.getLogger(VectorAnalysisMDI.class.getName()).log(Level.SEVERE, null, ex);
                System.out.print("Unreadable file exception during reading the file :\n" + asFile+'\n');

            } catch (IOException ex) {
                Logger.getLogger(VectorAnalysisMDI.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }//GEN-LAST:event_OpenFileAssignmentsButtonActionPerformed

    private void TrialSelComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TrialSelComboBoxActionPerformed
        // TODO add your handling code here:
        //String Trialname = (String) TrialSelComboBox.getSelectedItem();
        this.GrpSelComboBox.removeAllItems();
        int selIdx = TrialSelComboBox.getSelectedIndex();
        boolean isPresent = false;
        extTableModel tmodel = (extTableModel)Trial_No_Table.getModel();
        Object test;
        if(selIdx >= 0){
            for(int Count = 1 ; Count <= nGrps ; Count ++){
                test = tmodel.getValueAt(selIdx, Count);
                if(test != null)
                isPresent = (Boolean)test;
                else{
                    javax.swing.JOptionPane.showMessageDialog(this,"the pointer in null");
                }

                //isPresent = (Boolean) this.Trial_No_Table.getValueAt(selIdx, Count);
                if (isPresent)
                this.GrpSelComboBox.addItem((String)this.Trial_No_Table.getColumnName(Count) );
            }
        }
    }//GEN-LAST:event_TrialSelComboBoxActionPerformed

    private void GrpSelComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GrpSelComboBoxActionPerformed
        // TODO add your handling code here:
        this.AnimalSelComboBox.removeAllItems();
        int trialSelIdx = TrialSelComboBox.getSelectedIndex();
        int grpSelIdx = GrpSelComboBox.getSelectedIndex();
        ArrayList selList = new ArrayList();
        //boolean isPresent = false;
        extTableModel tmodel = (extTableModel)Trial_No_Table.getModel();
        Object test;
        if(trialSelIdx >= 0){
            for(int Count = 1 ; Count <= nGrps ; Count ++){
                test = tmodel.getValueAt(trialSelIdx, Count);
                if(test != null){
                    if((Boolean)test)
                    selList.add(Count);
                }
                else{
                    javax.swing.JOptionPane.showMessageDialog(this,"the pointer is null");
                }
            }
        }
        //isPresent = (Boolean) this.Trial_No_Table.getValueAt(selIdx, Count);
        if(grpSelIdx >= 0){
            var grpID = (Integer)selList.get(grpSelIdx);
            this.AnimalSelComboBox.removeAllItems();
            int nAni;
            nAni = (Integer)this.AnimalGrpSummaryTable.getValueAt(grpID-1, 2);
            for(int animalCount = 1 ; animalCount <= nAni ; animalCount++)
            this.AnimalSelComboBox.addItem(""+animalCount );
        }
    }//GEN-LAST:event_GrpSelComboBoxActionPerformed

    private void Assign_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Assign_ButtonActionPerformed

        int [] selectedFiles = this.FileDetail_Table.getSelectedRows();
        int grpSelIdx =  GrpSelComboBox.getSelectedIndex();
        int trialSelIdx =  TrialSelComboBox.getSelectedIndex();
        int aniSelIdx =    AnimalSelComboBox.getSelectedIndex();                //TO DO UID field  for datamanager ??

        String asnString[] = new String[4];
        Path parent, root,dataFile;
        root = new File(this.jFormatTxt_rootFolder.getText()).toPath();

        for( int Idx : selectedFiles){

            //            parent = root.resolve((String) FileDetail_Table.getValueAt(Idx, 0));
            //            dataFile = parent.resolve((String)FileDetail_Table.getValueAt(Idx, 1));

            asnString[0] =  /*dataFile.toString();*/(String) FileDetail_Table.getValueAt(Idx, 0) +File.separator+ (String)FileDetail_Table.getValueAt(Idx, 1);
            asnString[1] = (String) AnimalSelComboBox.getSelectedItem();
            asnString[2] = (String) GrpSelComboBox.getSelectedItem();
            asnString[3] = (String) TrialSelComboBox.getSelectedItem();
            FileAssignmentModel.addRow(asnString);
            //At this point there is no cross checking if the file is assigned multiple times.
            //Also there is no simple way to assign the animal #
        }
        FileAssignmentTable.setModel(FileAssignmentModel);
        
        if(this.chkBoxRemoveAssignedFiles.isSelected())
                this.RemoveFile_ButtonActionPerformed(evt);
        

        //Create data organisation and mapping in Data manager.
        // Animal UID maps to file. One animal ID might map to more than one data file (resolve it using
            //reverse map constructed from file name to trialID.
            //Also create a data file info class that stores the start frame, end frame resolution, pool roi, quadrant roi, platform roi and
            //
            //group UID maps to animal
            //Trials maps to groups
            //Make grps
            //assign files
            //update table
    }//GEN-LAST:event_Assign_ButtonActionPerformed

    private void RemoveFile_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RemoveFile_ButtonActionPerformed
        // TODO add your handling code here:
        
//        boolean disable = false;
//        if(evt.getActionCommand().contains("Assign_Button"))              //For future implementation. 
//            disable = true;
        
        int[] nSel = this.FileDetail_Table.getSelectedRows();
        int nFiles2Del = nSel.length;
        
        if(nFiles2Del == 0 ){
            int choice =
            javax.swing.JOptionPane.showConfirmDialog(this,
                "No file is selected. Do you want to delete all ?","Confim",JOptionPane.YES_NO_CANCEL_OPTION);
            if (choice == JOptionPane.YES_OPTION){
                this.FileDetailModel.setRowCount(0);
                this.rel2absPathMaps.clear();
            }
        }else{
            for(int count = nFiles2Del-1 ; count >= 0 ; count--){     //remove from bottom of the list so that the order
                var key2Remove = (String)FileDetailModel.getValueAt(nSel[count],1);
                this.rel2absPathMaps.remove(key2Remove);
                this.FileDetailModel.removeRow(nSel[count]);        //and hence the serial number will not change.
            }
        }

    }//GEN-LAST:event_RemoveFile_ButtonActionPerformed

    private void AddFiles_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddFiles_ButtonActionPerformed

        this.ImportMenuItemActionPerformed(evt);
    }//GEN-LAST:event_AddFiles_ButtonActionPerformed

    private void AllGrpsinAllTrialCheckBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_AllGrpsinAllTrialCheckBoxItemStateChanged
        // TODO add your handling code here:
        if(AllGrpsinAllTrialCheckBox.isSelected()){

            this.Trial_No_Table.removeAll();
            this.updateGrpTrialSelTable();
            Trial_No_Table.setEnabled(false);
        }else{
            Trial_No_Table.setEnabled(true);
        }
    }//GEN-LAST:event_AllGrpsinAllTrialCheckBoxItemStateChanged

    private void reset_AnGrTr_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reset_AnGrTr_ButtonActionPerformed
        // TODO add your handling code here:
        this.ulockExpDefInputUI();
    }//GEN-LAST:event_reset_AnGrTr_ButtonActionPerformed

    private void upDateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_upDateButtonActionPerformed
        if(this.AnimalGrpSummaryTable.getRowCount() == 0)
        this.updateAnimalGrpSummaryTable();
        if(this.Trial_No_Table.getRowCount() == 0)
        this.updateGrpTrialSelTable();
        lockExpDefInputUI();

        TrialSelComboBox.removeAllItems();
        //GrpSelComboBox.removeAllItems();
        //AnimalSelComboBox.removeAllItems();
        if(Trial_No_Table.getRowCount() != nTrial){
            javax.swing.JOptionPane.showMessageDialog(this, "The no of trials "+Trial_No_Table.getRowCount()+"  and grp name entry does not match "+ nTrial);
            return;
        }
        for(int Count  = 0 ; Count < nTrial ; Count++)
        TrialSelComboBox.addItem((String)this.Trial_No_Table.getValueAt(Count,0));
    }//GEN-LAST:event_upDateButtonActionPerformed

    private void SampleSizeSelItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SampleSizeSelItemStateChanged
        // TODO add your handling code here:
        if(updateAnimalGrpSummaryTable())return;
        //this.updateGrpTrialSelTable();
    }//GEN-LAST:event_SampleSizeSelItemStateChanged

    private void jFormattedTextField_NoOfGrpsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFormattedTextField_NoOfGrpsActionPerformed

        if(updateAnimalGrpSummaryTable())return;
        //this.updateGrpTrialSelTable();
    }//GEN-LAST:event_jFormattedTextField_NoOfGrpsActionPerformed

    private boolean updateAnimalGrpSummaryTable() throws NumberFormatException, HeadlessException {
        boolean sameSampleSz = this.SampleSizeSel.getModel().isSelected();
        boolean tableEmpty = true;
        if (readnGrps()) {
                return true;
            }
        if(this.AnimalGrpSummaryTable.getRowCount() == 0)
            tableEmpty = true;
        if (tableEmpty || ! sameSampleSz) {
            
            int nRows = this.AnimalGrpModel.getRowCount();
            
            this.AnimalGrpModel.setRowCount(nGrps);
            this.nAnimals = Integer.parseInt(nAnimals_Text.getText());
            if(nRows <= nGrps)
                for(int Count = nRows ; Count < nGrps ; Count ++){
                    String tmp = "Grp# "+(Count+1);
                    if(Count < grpNames.size())
                        grpNames.set(Count, tmp);
                    else 
                        this.grpNames.add(Count,tmp);
                    
                    this.AnimalGrpModel.setValueAt(""+ (Count+1), Count, 0);
                    this.AnimalGrpModel.setValueAt(tmp,Count,1);
                    this.AnimalGrpModel.setValueAt(this.nAnimals, Count, 2);
                    
                }
            this.AnimalGrpSummaryTable.setEnabled(true);
            this.nAnimals_Text.setEnabled(false);
            updateGrpTrialSelTable();
        }else {
            this.AnimalGrpSummaryTable.setEnabled(false);
            this.nAnimals_Text.setEnabled(true);
           
        }
        
        //updateGrpTrialSelTable();
        
        return false;
    }

    private void updateGrpTrialSelTable() throws NumberFormatException {
        nTrial = Integer.parseInt(this.jFormattedText_nTrials.getText());
        
        Class [] Format = new Class[nGrps+1];
        String [] nColID = new String[nGrps+1];
        nColID[0] = "Trial Name";
        Format[0] = String.class;
        
        for(int Count = 1 ; Count <= nGrps ; Count ++){
            Format[Count] = Boolean.class;
            nColID[Count] = grpNames.get(Count-1);
        }
        
        this.TrialNoModel.setTableFormat(Format);
        this.TrialNoModel.setColumnCount(nGrps+1);
        
        this.TrialNoModel.setColumnIdentifiers(nColID);
        this.TrialNoModel.setRowCount(nTrial);
        
       
        for(int Count = 0 ; Count < nTrial ; Count ++){
            String tmp = "Trial# "+(Count+1);
            if(Count < trialNames.size())
                trialNames.set(Count,tmp);
            else
                this.trialNames.add(Count,tmp );
            this.TrialNoModel.setValueAt(tmp, Count, 0);
            for(int gCount = 1 ; gCount <= nGrps ; gCount++)
                this.TrialNoModel.setValueAt(Boolean.TRUE,Count, gCount);
        }
        this.Trial_No_Table.setModel(TrialNoModel);
    }

    private void lockExpDefInputUI() {
        // TODO add your handling code here:
        //Lock the UI diable all the input columns and make the tables uneditable
        
        this.jFormattedTextField_NoOfGrps.setEditable(false);
        this.jFormattedText_nTrials.setEditable(false);
        this.nAnimals_Text.setEditable(false);
        this.SampleSizeSel.setEnabled(false);
        this.AnimalGrpSummaryTable.setEnabled(false);
        this.Trial_No_Table.setEnabled(false);
        
        
    }
    private void ulockExpDefInputUI(){
        this.jFormattedTextField_NoOfGrps.setEditable(true);
        this.jFormattedText_nTrials.setEditable(true);
        this.nAnimals_Text.setEditable(true);
        this.SampleSizeSel.setEnabled(true);
        this.AnimalGrpSummaryTable.setEnabled(true);
       // this.Trial_No_Table.setEnabled(true);
    }

    private void calAveFlds(DataManager currManager, int gCount, int tCount,int xRes, int yRes) {
                
        var timeLapsed = false;
        var startTime = System.currentTimeMillis();
        while (!currManager.isVectorFldsReady() && !timeLapsed){
            timeLapsed = (System.currentTimeMillis() - startTime) > 1000;// timeOut in milliseconds
            try {                   
                    Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(VectorAnalysisMDI.class.getName()).log(Level.SEVERE, null, ex);
            }
            setStatusMessage("Waiting for the data to be read before calculating averages..."+"\n");
            //System.out.print("Waiting for the data to be read before calculating averages..."+"\n");
        }

        if(timeLapsed){
            setStatusMessage("Timed Out waiting for calculating averages..."+"\n");
            setStatusMessage("Exiting the calculations.."+"\n");
            this.RunGrp_Button.setEnabled(true);
            return ;
        }


        JVector OC = currManager.findOC(xRes, yRes);
        OccCtrs[tCount][gCount] = OC;
        this.ocXjFtTxt2.setText(""+OC.getComponent(0));
        this.ocYjFtTxt3.setText(""+OC.getComponent(1));

        currManager.computeAve(0, OC,true);
        currManager.saveAverage("grp#_"+gCount+"_",true);
//currManager.computeAve(1, Plt,true);
// currManager.saveAverage("threadGrp#_comp_Plt"+gCount+"_",false);
//currManager.computeAve(3,null,true);
        currManager.computeAve(1, OC,true);
        currManager.saveAverage("grp#_comp_OC"+gCount+"_",false);
        
        //Retrive the average and run through for the covnergence divergence estimates.
        //selectout Reg
        //compute surface
        //compute divergence
        //selctout reg
        //Id peaks
        //measure center,width and intensity
        //compute accuracy, undertainity and intensity (rel and abs).
        /*** Surface fit on average*/
        //read this numbers through gui
        //var temp  =  currManager.getAveResMap();
        //Roi sampledGrpRoi = getSampledROI( 1,currManager);
        currManager.computeAve(3, null,false);
        Roi sampledGrpRoi = getSampledROI( 1, currManager.getAveResMap());
        
        String velFldrName = currManager.getOutPath()+File.separator+"Ave Velocity";
        File velFolder =  new File(velFldrName);
        if(!velFolder.exists())
            velFolder.mkdir();
        
        calculateVectorFldProperties(currManager.getAveVelFld(), sampledGrpRoi,true,velFolder.getAbsolutePath(),"VelCmpAlgAve_"+"T_#"+tCount+"G_#"+gCount);
        
        String newFolder = currManager.getOutPath()+File.separator+"Ave Accelaration";
        File accFolder =  new File(newFolder);
        if(!accFolder.exists())
            accFolder.mkdir();
        
        calculateVectorFldProperties(currManager.getAveAccFld(), sampledGrpRoi,true,accFolder.getAbsolutePath(),"AccCmpAlgAve_"+"T_#"+tCount+"G_#"+gCount);
        /**/
        RoiEncoder encoder = new RoiEncoder(currManager.getOutPath()+File.separator+"Select.roi");
        try {
            encoder.write(sampledGrpRoi);
        } catch (IOException ex) {
            Logger.getLogger(VectorAnalysisMDI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private JVector jVecFieldImgGenerator(DataManager currManager, int xRes, int yRes, int xOC, int yOC) throws InterruptedException{
        JVector OC;
        JVectorSpace[] vFields;
        JVectorSpace[] aFields;
        boolean timeLapsed = false;
        System.out.print("I am inside the jVectField function \n");
        long startTime = System.currentTimeMillis();
        while (!currManager.isVectorFldsReady() && !timeLapsed){
                    System.out.print(timeLapsed+"\n");
                    timeLapsed = (System.currentTimeMillis() - startTime) > 1000;// timeOut in milliseconds
                    try {                   
                        Thread.sleep(100);
                    }catch (InterruptedException ex) {
                        Logger.getLogger(VectorAnalysisMDI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    this.setStatusMessage("Waiting for the data to be read for generating images..."+"\n",true);
                    this.StatusMessageBox.append("Waiting for the data to be read for generating images..."+"\n");
                   // System.out.print("Waiting for the data to be read for generating images..."+"/n");
       }
        
        if(timeLapsed){
            this.StatusMessageBox.append("Timed Out waiting for generating images..."+"\n");
            System.out.print("Timed Out waiting for generating images..."+"\n");
            return null;
        }
        // Having read all the data files estimate the occupancy center. Also allow the user to enter.
        
        // OC = (this.CheckBoxBoolean.isSelected()) ?    findOC(currManager, xRes, yRes) : new JVector(xOC,yOC) ;
        OC = (this.CheckBoxBoolean.isSelected()) ? currManager.findOC( xRes, yRes) : new JVector(xOC,yOC);
        //Generate the velocity and accelaration fields
        
       
        vFields = currManager.getVelocityField();
        aFields = currManager.getAccelarationField();
        
        //JVectorSpace vSpace;
        
        SwingWorker worker = new SwingWorker(){
            int dataCount = 0;
            int totSize = vFields.length;
            @Override
            protected Object doInBackground() throws Exception {
                System.out.print("Starting the dataloop...\n");
                for(JVectorSpace vSpace : vFields){                     //This is essentially looping through each data file
                    vSpace.setUseTan2(useTan2jChkBx.isSelected());
                    aFields[dataCount].setUseTan2(useTan2jChkBx.isSelected());

                    var tmpName = (currManager.getDataFileNames()[dataCount]);
                    var label  = "Vel of "+ tmpName.substring(1+tmpName.lastIndexOf(File.separator));
                    var label_acc = "Acc of "+ tmpName.substring(1+tmpName.lastIndexOf(File.separator));

                    if(saveVelocityjchkBx.isSelected()){
                        var vImgs = new JVectorCmpImg(vSpace);
                        var aImgs = new JVectorCmpImg(aFields[dataCount]);
                        vImgs.saveImages(currManager.getOutPath()+File.separator+ "Velocity as Cmps",label);
                        aImgs.saveImages(currManager.getOutPath()+File.separator+ "Accelaration as Cmps",label_acc);
                    }
                    if(CompforVectorFldjChkBx2.isSelected()){
                        if(AlongJRadBtn.isSelected()){
                            var vAlCmpImgs = new JVectorCmpImg(vSpace.getProjections2point(OC, true));
                            var aAlCmpImgs = new JVectorCmpImg(aFields[dataCount].getProjections2point(OC,true));
                            vAlCmpImgs.saveImages(currManager.getOutPath()+File.separator +"Vel Proj Along","Cmp_"+label);
                            aAlCmpImgs.saveImages(currManager.getOutPath()+File.separator+ "Accelaration Proj Along","Cmp_"+label_acc);
                        }else{
                            var vAlCmpImgs = new JVectorCmpImg(vSpace.getProjections2point(OC, false));
                            var aAlCmpImgs = new JVectorCmpImg(aFields[dataCount].getProjections2point(OC,false));
                            vAlCmpImgs.saveImages(currManager.getOutPath()+File.separator +"Vel Proj Ortho","Cmp_"+label);
                            aAlCmpImgs.saveImages(currManager.getOutPath()+File.separator+ "Accelaration Proj Ortho","Cmp_"+label_acc);
                        }
                    }
                    dataCount++;
                    UpdateProgress(100*(dataCount/totSize),jProgressBarDP,"% complete");
                }
                return null;
            }
            
        };
       //worker.addPropertyChangeListener(this);
       worker.execute();
        //Compute the averages first for original vectors, then for vectors along platform, along OC. 
        return OC;
    }

    /***
     * Call this function to calculate the vector field properties of an Vector space. Typically this in turn instantiates a 
     * jVectorFieldCalulator Object to calculate these in a separate thread. 
     * @param VecFld  The vector space for which we need to calculate the properties
     * @param sampledGrpRoi The roi representing the sampled sub space 
     * @param isDivergence  set this to true for calculating divergence surface (addition to convergence)
     * @param pathName  pathname to store the resulting files
     * @param suffix    suffix that will be added to the result file. Typically this will be mapped to data file name prefix/suffix. 
     */
    public void calculateVectorFldProperties(JVectorSpace VecFld, Roi sampledGrpRoi, boolean isDivergence,String pathName,String suffix) {
        
        System.out.println("Entering field calc...");
        jVectorFieldCalculator calculator = new jVectorFieldCalculator();
        calculator.setVecFld(VecFld);
        calculator.setPolyX(x_polyOrderJCmbBx.getSelectedIndex()+1);
        calculator.setPolyY(y_polyOrderJCmbBx.getSelectedIndex()+1);
        calculator.setSuffix(suffix);
        calculator.setPathName(pathName);
        calculator.setFileSeparator(File.separator);
//        int polyXOrder = this.x_polyOrderJCmbBx.getSelectedIndex()+1;//5;
//        int polyYOrder = this.y_polyOrderJCmbBx.getSelectedIndex()+1;
//        String fldrName = pathName + File.separator;
        
//        System.out.printf("Polynomial Order in (x,y) format:(%d,%d)",polyXOrder,polyYOrder);
       
        fit.setPreScale(this.reSzImgjChkBx.isSelected());
        fit.setScaleBy(Double.parseDouble(this.scalingfactorJFormFld.getText()));
        
        fit.setGaussFilt(this.gaussjChkBx.isSelected());
        fit.setGaussRad(Double.parseDouble(this.gauRadjFormFld.getText()));
        
        fit.setSelectPixels(this.res2SeljChkBx.isSelected());
        fit.setUseSelection(this.useSeljChBx.isSelected());
        
//        fit.setPolyOrderX(x_polyOrderJCmbBx.getSelectedIndex()+1);
//        fit.setPolyOrderX(y_polyOrderJCmbBx.getSelectedIndex()+1);
        
        calculator.setFit(fit);
        
        
//        ImagePlus[] vecSurface = getSurfaces(polyXOrder,polyYOrder,VecFld,sampledGrpRoi);
//        int count  = 0;
//        for(ImagePlus imp : vecSurface){
//            FileSaver fs  = new FileSaver(imp);
//            fs.saveAsTiff(pathName +"Ave_VectorSurface"+"Comp_#"+count++);
//        }
//
//    ImageStack diffVel =  new ImageStack(VecFld.getxRes(),VecFld.getyRes(),2);
    
  /**
   * Check for the sampledGrpRoi when auto determine OC is not checked. 
   * This throws a null pointer when it is not checked as there is no sampledGrpRoi in that case.
   */  
//   int x = 0 ,y = 0;
   if(sampledGrpRoi != null)
       calculator.setSampledGrpRoi(sampledGrpRoi);
//    x = sampledGrpRoi.getBounds().x;
//    y = sampledGrpRoi.getBounds().y;
   
//    FloatProcessor vecxS, vecyS;
//    vecxS = new FloatProcessor(VecFld.getxRes(),VecFld.getyRes());
//    vecyS = new FloatProcessor(VecFld.getxRes(),VecFld.getyRes());
//   
//    vecSurface[0].setRoi(sampledGrpRoi);
//    vecSurface[1].setRoi(sampledGrpRoi);
//    
//    vecxS.insert(this.getDifferentials(vecSurface[0].crop(), false).getProcessor(),x,y);
//    vecyS.insert(this.getDifferentials(vecSurface[1].crop(), true).getProcessor(),x,y);
//    
//
//    diffVel.setProcessor(vecxS, 1);
//    diffVel.setProcessor(vecyS, 2);
//    
//    ImagePlus Projections = new ImagePlus();
//    Projections.setStack(diffVel);
//    ZProjector projector = new ZProjector();
//    projector.setMethod(ZProjector.SUM_METHOD);
//    projector.setImage(Projections);
//    projector.doProjection();
//    ImagePlus velProjections = projector.getProjection();

//   
//    var img = new ImagePlus("VelCon");
//    img.setStack(diffVel);
//    var fs = new FileSaver(img);
//    fs.saveAsTiff(fldrName+suffix+"Divergence_diffVel");
//    var velProj = new FileSaver(velProjections);
//    velProj.saveAsTiff(fldrName+suffix+"Convergence_vel");
//    
//  //  fit.setPreScale(false);
//  //  fit.setGaussFilt(false);
//    ImagePlus finalVelImg = GenerateConvergenceImages(velProjections.getProcessor(), sampledGrpRoi);
//    fs = new FileSaver(finalVelImg);
//    fs.saveAsTiff(fldrName+suffix+"forPres");
     calculator.setGenConv(this.genConvJChkBx.isSelected());
     calculator.setGenDiv(this.genConvJChkBx.isSelected());
     calculator.setAutoGenPool(this.autoPoolRoijChkBx.isSelected());
//    float LThld, HThld;
//    if(this.genConvJChkBx.isSelected()){
//        LThld = Float.NEGATIVE_INFINITY;
//        HThld = 0;
//        ImageProcessor ConvIP = finalVelImg.getProcessor().duplicate();
//        
//        ConvIP.setThreshold(LThld, HThld);
//        var mask = ConvIP.createMask();
//        mask.add(-254);
//        
//        FloatBlitter fb = new FloatBlitter((FloatProcessor)ConvIP);
//        fb.copyBits(mask, 0, 0, FloatBlitter.MULTIPLY);
//        ConvIP.multiply(-1);
//        
//        var Img  = new ImagePlus("Conv");
//        Img.setProcessor(ConvIP);
//        fs = new FileSaver(Img);
//        fs.saveAsTiff(fldrName+suffix+"ConvPres");
//        
//    }
//    if(this.genDivjChkBx.isSelected()){
//        LThld = 0;
//        HThld = Float.POSITIVE_INFINITY;
//        ImageProcessor DivIP = finalVelImg.getProcessor().duplicate();
//        
//        DivIP.setThreshold(LThld, HThld);
//        var mask = DivIP.createMask();
//        mask.add(-254);
//
//        FloatBlitter fb = new FloatBlitter((FloatProcessor)DivIP);
//        fb.copyBits(mask, 0, 0, FloatBlitter.MULTIPLY);
//        
//        var Img  = new ImagePlus("Div");
//        Img.setProcessor(DivIP);
//        fs = new FileSaver(Img);
//        fs.saveAsTiff(fldrName+suffix+"DivPres");
//     
//    }
    //calculator.run();
    Thread thread = new Thread(calculator,""+jVectorFieldCalculator.getInstanceCount());
    thread.start();
    activeCount++;
    if(activeCount == 1){
        Thread monitor = new Thread(threadMonitor);
        setStatusMessage("Starting Monitor..");
        monitor.start();
    }
    
 }
/** starting from here..**
    private ImagePlus GenerateConvergenceImages(ImageProcessor converImg, Roi sampledGrpRoi) {

            converImg.setRoi(sampledGrpRoi);
           
            int polyXOrder  = this.x_polyOrderJCmbBx.getSelectedIndex();
            int polyYOrder  = this.y_polyOrderJCmbBx.getSelectedIndex();
            
            
            fit.setPolyOrderX(polyYOrder);
            fit.setPolyOrderY(polyYOrder);
            fit.setPreScale(false);
            fit.setGaussFilt(false);
            if(sampledGrpRoi != null ){
                fit.setUseSelection(true);
                fit.setSelectPixels(true);
            }
            
            
            ImagePlus surfaceOut = this.getSurface(polyXOrder, polyYOrder, converImg, sampledGrpRoi);
           
            OvalRoi Pool;

   
            if(this.autoPoolRoijChkBx.isSelected()){                                   //Check for pool roi or parameters
                Rectangle rect;
                if(sampledGrpRoi != null){
                    rect = sampledGrpRoi.getBounds();
                    
                }else{
                    rect = surfaceOut.getRoi().getBounds();
                }
                Pool = new OvalRoi(rect.x,rect.y,rect.width,rect.height);
            }else{
                int xCtr = Integer.parseInt(this.xPoolCtrjFormFld.getText());
                int yCtr = Integer.parseInt(this.yPoolCtrjFormFld.getText());
                int dia = 2 * Integer.parseInt(this.poolRadjFormFld.getText());
                Pool = new OvalRoi(xCtr,yCtr,dia,dia);
            }
            surfaceOut.getProcessor().setValue(0);
            surfaceOut.getProcessor().fillOutside(Pool);

        return surfaceOut;
    }
    private ImagePlus[] getSurfaces(int polyX, int polyY, JVectorSpace space, Roi sel){
        int nCmp = space.getnComp();
        ImagePlus[] surfaces = new ImagePlus[nCmp];
        JVectorCmpImg images = new JVectorCmpImg(space);
        ImageProcessor[] cmpImages = images.getProcessorArray();
        int count = 0;
        for(ImageProcessor ip : cmpImages){
                //var ByIp = ip.convertToByteProcessor();
                surfaces[count++] = getSurface(polyX,polyY,ip,sel);
        }
       
       return surfaces;
    }
    private ImagePlus getSurface(int polyXOrder, int polyYOrder, ImageProcessor cmpIP, Roi selection){
        ImagePlus surface = new ImagePlus();
        //SurfaceFit fit = new SurfaceFit(polyXOrder, polyYOrder);
        
//        fit.setPreScale(this.reSzImgjChkBx.isSelected());
//        fit.setScaleBy(Double.parseDouble(this.scalingfactorJFormFld.getText()));
//        
//        fit.setGaussFilt(this.gaussjChkBx.isSelected());
//        fit.setGaussRad(Double.parseDouble(this.gauRadjFormFld.getText()));
//        fit.setSelectPixels(this.res2SeljChkBx.isSelected());
//        fit.setUseSelection(this.useSeljChBx.isSelected());
        FloatProcessor frame; 
        //frame.setBackgroundValue(0);
        
        //int selWidth, selHeight;
        //selection = (this.useSeljChBx.isSelected()) ? selection : null ;
        cmpIP.setRoi(selection);
        FloatProcessor selInFrame = fit.FitSurface(cmpIP,selection); //null, false square/rectangle region of interest as such 
                                                                      // sel, false square/rectangle region of interest with 0 for pixels of unmasked
                                                                      //sel, true just the pixels that are selected by roi mask
       System.out.println("The dimension after fitting is (X x Y) "+selInFrame.getWidth()+" x "+selInFrame.getHeight());
        if( selection != null  ){
            var selX =  selection.getBounds().x ;
            var selY =  selection.getBounds().y ;
            frame = new FloatProcessor(cmpIP.getWidth(),cmpIP.getHeight());
            frame.insert(selInFrame,selX,selY);
            surface.setProcessor(frame);
        }
        else{
            surface.setProcessor(selInFrame);
        }
        return surface;
    }
    private ImagePlus getDifferentials(ImagePlus imp, boolean vertical){
        ImagePlus differentials = new ImagePlus();
        ImageDifferentials Diff = new ImageDifferentials();
        if (vertical) 
            differentials.setProcessor(Diff.DifferentialY(imp.getProcessor()));
        else
            differentials.setProcessor(Diff.DifferentialX(imp.getProcessor()));
        return differentials;
    }
    
    **...till here could be commented out**/
    private ij.gui.Roi getSampledROI(int thershold, JHeatMapArray aveResMap) {
        ij.gui.Roi roi ;
        FloatProcessor ip;
//        currManager.computeAve(3, null,true);
//        var aveResMap = currManager.getAveResMap();
        ip = new FloatProcessor(aveResMap.getxRes(),aveResMap.getyRes(),aveResMap.to1DArray());
        ip.setThreshold(thershold, Float.MAX_VALUE, 0);
        roi = new ThresholdToSelection().convert(ip);
        return roi;
    }
//    private JVector findOC(DataManager currManager, int xRes, int yRes) {
//        int xOC;
//        int yOC;
//        //this.generateResidenceMap(currManager);
//        //timeTrace = currManager.getTimeData();
//        currManager.computeAve(3, null,false);        //Just compute the residence map
//        var heatMap = currManager.getAveResMap();
//        heatMap.convertTimeSeriestoArray(xRes, yRes);
//        JVectorCmpImg heatMapImg = new JVectorCmpImg(xRes,yRes,1);
//        heatMapImg.addScalar(heatMap);
//        var AveHMap_imp = heatMapImg.getImages()[0];
//        //AveHMap_imp.show();
//        var ip = AveHMap_imp.getProcessor().duplicate();
//        double sigma = (xRes > yRes) ? yRes/40 : xRes/40 ;
//        ip.blurGaussian(sigma);
//        ip.setAutoThreshold("MaxEntropy dark");
////        ip.createMask();
//        var lThld = ip.getMinThreshold();
//        var hThld = ip.getMaxThreshold();
//        System.out.println("The thlds are " + lThld + "," + hThld);
//        var stat = new FloatStatistics(ip,ImageStatistics.CENTER_OF_MASS+ImageStatistics.LIMIT,null);
//        
//        xOC = (int) stat.xCenterOfMass;
//        yOC = (int) stat.yCenterOfMass;
//        this.ocXjFtTxt2.setText(""+xOC);
//        this.ocYjFtTxt3.setText(""+yOC);
//        return new JVector(xOC,yOC);
//    }

    private boolean readnGrps() throws NumberFormatException, HeadlessException {
        if (!jFormattedTextField_NoOfGrps.isEditValid()) {
            javax.swing.JOptionPane.showMessageDialog(null, "The number of grps entry is invalid");
            return true;
        }
        this.nGrps = Integer.parseInt(jFormattedTextField_NoOfGrps.getText());
        return false;
    }
    

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        boolean found = false;
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                               
                if ("System".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    found = true;
                    break;
                }
            }
            if(!found)
                javax.swing.UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());            
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(VectorAnalysisMDI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VectorAnalysisMDI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VectorAnalysisMDI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VectorAnalysisMDI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                
                new VectorAnalysisMDI().setVisible(true);
            }
        });
    }
    private DefaultTreeModel treeModel; 
    private final DefaultMutableTreeNode expRoot;
    private  DefaultMutableTreeNode trialRoot;
    private final DefaultMutableTreeNode grpRoot;
    
    
    private ConcurrentHashMap <String,String> rel2absPathMaps;
    private final ArrayList<String> grpNames;
    private final ArrayList<String> trialNames;
    private ArrayList </*of groups*/ArrayList</*which in turn array of animal's data*/DataManager> >TrialData;
    //private JVectorCmpImg heatMaps;
    private final DefaultTableModel AnimalGrpModel;
    private final DefaultTableModel FileAssignmentModel;
    private final DefaultTableModel FileDetailModel;
    private final extTableModel TrialNoModel;
    
    //data strunctures for storing and processing average data
    
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AddFiles_Button;
    private javax.swing.JCheckBox AllGrpsinAllTrialCheckBox;
    private javax.swing.JRadioButton AlongJRadBtn;
    private javax.swing.JPanel AnalysisDesign_jPanel;
    private javax.swing.JTable AnimalGrpSummaryTable;
    private javax.swing.JLabel AnimalLabel;
    private javax.swing.JComboBox<String> AnimalSelComboBox;
    private javax.swing.JButton Assign_Button;
    private javax.swing.JCheckBox CheckBoxBoolean;
    private javax.swing.JCheckBox CompforVectorFldjChkBx2;
    private javax.swing.JPanel DataFiles_jPanel;
    private javax.swing.JPanel DeskTopPanel;
    private javax.swing.JPanel ExpDef_jPanel;
    private javax.swing.JTable FileAssignmentTable;
    private javax.swing.JTable FileDetail_Table;
    private javax.swing.JButton GenConMaps_Button;
    private javax.swing.JButton GenCurlMaps_Button;
    private javax.swing.JLabel GrpLabel;
    private javax.swing.JComboBox<String> GrpSelComboBox;
    private javax.swing.JButton HeatMap_Button;
    private javax.swing.JPanel ImageDisplay_Panel;
    private javax.swing.JMenuItem ImportMenuItem;
    private javax.swing.JTabbedPane InfoTab;
    private javax.swing.JPanel MessageBox_Panel;
    private javax.swing.JButton OpenFileAssignmentsButton;
    private javax.swing.JRadioButton OrtoJRadBtn;
    private javax.swing.JFormattedTextField PlatXjFtTxt;
    private javax.swing.JFormattedTextField PlatYjFtTxt1;
    private javax.swing.JPanel ProgIndPanel;
    private javax.swing.JButton QuadAna_Button;
    private javax.swing.JButton RemoveFile_Button;
    private javax.swing.JButton RunGrp_Button;
    private javax.swing.JCheckBox SampleSizeSel;
    private javax.swing.JButton SaveFileAssignmentsButton;
    private javax.swing.JCheckBox ScaleY_JChkBx;
    private javax.swing.JLabel SelDesLabel;
    private javax.swing.JTextArea StatusMessageBox;
    private javax.swing.JLabel TrialLabel;
    private javax.swing.JComboBox<String> TrialSelComboBox;
    private javax.swing.JTable Trial_No_Table;
    private javax.swing.JMenuItem aboutMenuItem;
    private javax.swing.JMenu analysisMenu;
    private javax.swing.JFormattedTextField aspectRatiojFmtFld;
    private javax.swing.JTextField assignFileJTxt;
    private javax.swing.JCheckBox autoPoolRoijChkBx;
    private javax.swing.JCheckBox chkBoxRemoveAssignedFiles;
    private javax.swing.ButtonGroup compJRadGrp;
    private javax.swing.JMenuItem contentMenuItem;
    private javax.swing.JMenuItem copyMenuItem;
    private javax.swing.JMenuItem cutMenuItem;
    private javax.swing.JMenuItem deleteMenuItem;
    private javax.swing.JMenu editMenu;
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JTree expDgnTree;
    private javax.swing.JTextField expFileNoJText;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JFormattedTextField gauRadjFormFld;
    private javax.swing.JCheckBox gaussjChkBx;
    private javax.swing.JCheckBox genAccjChkBx;
    private javax.swing.JCheckBox genConvJChkBx;
    private javax.swing.JCheckBox genDivjChkBx;
    private javax.swing.JCheckBox genVeljChkBx1;
    private javax.swing.JMenu helpMenu;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButtonBrowseRoot;
    private javax.swing.JButton jButtonFileAssignRest;
    private javax.swing.JButton jButtonRemoveAssignments;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JCheckBox jCheckBox3;
    private javax.swing.JCheckBox jChkBxAssym;
    private javax.swing.JComboBox<String> jCombo_dataSeparator;
    private javax.swing.JFormattedTextField jFmtTxtFldRadX;
    private javax.swing.JMenuItem jFolderOptions;
    private javax.swing.JFormattedTextField jFormatTxt_rootFolder;
    private javax.swing.JFormattedTextField jFormattedTextField_NoOfGrps;
    private javax.swing.JFormattedTextField jFormattedText_nTrials;
    private javax.swing.JFormattedTextField jFrtTxtFld;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel_NoOfAnimals;
    private javax.swing.JLabel jLabel_Number_of_GrpTxt;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItemCompute;
    private javax.swing.JMenuItem jMenuItemExpDes;
    private javax.swing.JMenuItem jMenuItemGrpID;
    private javax.swing.JMenuItem jMenuItemMeasure;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JProgressBar jProgressBarDP;
    private javax.swing.JProgressBar jProgressBarDataAssignment;
    private javax.swing.JProgressBar jProgressBarFR;
    private javax.swing.JProgressBar jProgressBarGP;
    private javax.swing.JProgressBar jProgressBarTP;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JMenu mapsMenu;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JFormattedTextField nAnimals_Text;
    private javax.swing.JFormattedTextField ocXjFtTxt2;
    private javax.swing.JFormattedTextField ocYjFtTxt3;
    private javax.swing.JMenuItem openMenuItem;
    private javax.swing.JMenuItem pasteMenuItem;
    private javax.swing.JFormattedTextField poolRadjFormFld;
    private javax.swing.JCheckBox reSzImgjChkBx;
    private javax.swing.JTextField remainingDatafilesJText;
    private javax.swing.JCheckBox res2SeljChkBx;
    private javax.swing.JButton reset_AnGrTr_Button;
    private javax.swing.JMenuItem residencemapMenuItem;
    private javax.swing.JMenuItem saveAsMenuItem;
    private javax.swing.JMenuItem saveMenuItem;
    private javax.swing.JCheckBox saveVelocityjchkBx;
    private javax.swing.JFormattedTextField scalingfactorJFormFld;
    private javax.swing.JButton upDateButton;
    private javax.swing.JCheckBox useRelVelJChkBx;
    private javax.swing.JCheckBox useSeljChBx;
    private javax.swing.JCheckBox useTan2jChkBx;
    private javax.swing.ButtonGroup vectJChkBx;
    private javax.swing.JFormattedTextField xPoolCtrjFormFld;
    private javax.swing.JFormattedTextField xResTxtField;
    private javax.swing.JComboBox<String> x_polyOrderJCmbBx;
    private javax.swing.JFormattedTextField yPoolCtrjFormFld;
    private javax.swing.JFormattedTextField yResTxtField;
    private javax.swing.JComboBox<String> y_polyOrderJCmbBx;
    // End of variables declaration//GEN-END:variables

    @Override
    public void actionPerformed(ActionEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
//        if("progress" == evt.getPropertyName()){
//            int nVal = (Integer)evt.getNewValue();
//            jProgressBarDP.setValue(nVal);
//            jProgressBarDP.setString("Data Tracker");
//            
//        }
        
    }

  
   class extTableModel<C extends Class> extends DefaultTableModel{
       ArrayList< Class > TableFormat = new ArrayList();
        public  extTableModel(DefaultTableModel model){
            
           super(model.getRowCount(),model.getColumnCount());
       }
       @Override
       public Class<?> getColumnClass(int columnIdx){
           if( columnIdx < this.getColumnCount()){
               
               return TableFormat.get(columnIdx);
           }
           else    
            return super.getColumnClass(columnIdx);
       }
       public void setTableFormat(C [] format){
           for(C c : format)
            TableFormat.add(c);
       }
       public ArrayList<Class> getTableFormat(){
           return TableFormat;
       }
    }
}
