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
import ij.io.FileSaver;
import ij.io.RoiEncoder;
import ij.plugin.filter.ThresholdToSelection;
import ij.process.FloatProcessor;
import java.awt.Image;
import java.awt.image.BufferedImage;
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
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JScrollBar;
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
    private boolean usePltCord;
    private String ver =  "0.1";
    private File setting;
    private File logFile;
    private boolean test = false;        //set it true for debugging and testing
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
                setStatusMessage("All the threads have completed.\n");
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
        java.awt.GridBagConstraints gridBagConstraints;

        compJRadGrp = new javax.swing.ButtonGroup();
        vectJChkBx = new javax.swing.ButtonGroup();
        FilterJChkBxGrp = new javax.swing.ButtonGroup();
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
        autoEstOC = new javax.swing.JCheckBox();
        AnalysisSettingTabPanel = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        scalingfactorJFormFld = new javax.swing.JFormattedTextField();
        reSzImgjChkBx = new javax.swing.JCheckBox();
        useTan2jChkBx = new javax.swing.JCheckBox();
        ScaleY_JChkBx = new javax.swing.JCheckBox();
        useRelVelJChkBx = new javax.swing.JCheckBox();
        jCheckBox2 = new javax.swing.JCheckBox();
        aspectRatiojFmtFld = new javax.swing.JFormattedTextField();
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
        usePltCordChkBx2 = new javax.swing.JCheckBox();
        useIndROIjChkBx = new javax.swing.JCheckBox();
        jPanel1 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        gaussjChkBx = new javax.swing.JCheckBox();
        noFiltjChkBx1 = new javax.swing.JCheckBox();
        medianjChkBx = new javax.swing.JCheckBox();
        gauRadjFormFld = new javax.swing.JFormattedTextField();
        jLabel22 = new javax.swing.JLabel();
        jChkBxAssym = new javax.swing.JCheckBox();
        jFmtTxtFldRadY = new javax.swing.JFormattedTextField();
        jFmtTxtFldRadX = new javax.swing.JFormattedTextField();
        jScrollPaneProgImgDisp = new javax.swing.JScrollPane();
        ProgImgDispParent = new javax.swing.JPanel();
        ProgIndPanel = new javax.swing.JPanel();
        jProgressBarDataAssignment = new javax.swing.JProgressBar();
        jProgressBarFR = new javax.swing.JProgressBar();
        jLabel29 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jProgressBarTP = new javax.swing.JProgressBar();
        jProgressBarGP = new javax.swing.JProgressBar();
        jProgressBarDP = new javax.swing.JProgressBar();
        jLabel25 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        ImageDispPanel = new javax.swing.JPanel();
        controllerPanel = new javax.swing.JPanel();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        statMssgScrollPane = new javax.swing.JScrollPane();
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
        setBounds(new java.awt.Rectangle(0, 0, 2500, 2500));
        setLocation(new java.awt.Point(0, 0));
        setMaximizedBounds(new java.awt.Rectangle(0, 0, 2147483647, 2147483647));
        setMinimumSize(new java.awt.Dimension(600, 500));
        setName("Frame"); // NOI18N
        setSize(new java.awt.Dimension(1275, 775));
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jScrollPane4.setAutoscrolls(true);
        jScrollPane4.setMinimumSize(new java.awt.Dimension(740, 720));
        jScrollPane4.setPreferredSize(new java.awt.Dimension(750, 750));

        DeskTopPanel.setMinimumSize(new java.awt.Dimension(760, 760));
        DeskTopPanel.setPreferredSize(new java.awt.Dimension(760, 760));
        DeskTopPanel.setLayout(new java.awt.GridLayout(1, 0));

        InfoTab.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        InfoTab.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        InfoTab.setAutoscrolls(true);
        InfoTab.setPreferredSize(new java.awt.Dimension(800, 800));

        ExpDef_jPanel.setAutoscrolls(true);
        ExpDef_jPanel.setPreferredSize(new java.awt.Dimension(733, 500));
        java.awt.GridBagLayout ExpDef_jPanelLayout = new java.awt.GridBagLayout();
        ExpDef_jPanelLayout.columnWidths = new int[] {0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0};
        ExpDef_jPanelLayout.rowHeights = new int[] {0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0};
        ExpDef_jPanel.setLayout(ExpDef_jPanelLayout);

        jLabel_Number_of_GrpTxt.setText("Number of Groups");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        ExpDef_jPanel.add(jLabel_Number_of_GrpTxt, gridBagConstraints);

        jFormattedTextField_NoOfGrps.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        jFormattedTextField_NoOfGrps.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFormattedTextField_NoOfGrps.setMinimumSize(new java.awt.Dimension(40, 20));
        jFormattedTextField_NoOfGrps.setPreferredSize(new java.awt.Dimension(20, 20));
        jFormattedTextField_NoOfGrps.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jFormattedTextField_NoOfGrpsActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 26;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        ExpDef_jPanel.add(jFormattedTextField_NoOfGrps, gridBagConstraints);

        jLabel_NoOfAnimals.setText("Number of Animals per Grp");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        ExpDef_jPanel.add(jLabel_NoOfAnimals, gridBagConstraints);

        SampleSizeSel.setSelected(true);
        SampleSizeSel.setText("Same N for all grps");
        SampleSizeSel.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        SampleSizeSel.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SampleSizeSelItemStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        ExpDef_jPanel.add(SampleSizeSel, gridBagConstraints);

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

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 16;
        gridBagConstraints.gridwidth = 45;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 0.5;
        ExpDef_jPanel.add(jScrollPane3, gridBagConstraints);

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

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 24;
        gridBagConstraints.gridwidth = 45;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 0.5;
        ExpDef_jPanel.add(jScrollPane1, gridBagConstraints);

        upDateButton.setText("Finalise  Animals Grp Trial #s");
        upDateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                upDateButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 10;
        gridBagConstraints.gridy = 18;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        ExpDef_jPanel.add(upDateButton, gridBagConstraints);

        nAnimals_Text.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("0"))));
        nAnimals_Text.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        nAnimals_Text.setText("5");
        nAnimals_Text.setMinimumSize(new java.awt.Dimension(40, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 26;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        ExpDef_jPanel.add(nAnimals_Text, gridBagConstraints);

        jLabel1.setText("Number of animals (N) ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 10;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        ExpDef_jPanel.add(jLabel1, gridBagConstraints);

        jFormattedText_nTrials.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("0"))));
        jFormattedText_nTrials.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFormattedText_nTrials.setText("2");
        jFormattedText_nTrials.setMinimumSize(new java.awt.Dimension(40, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 26;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        ExpDef_jPanel.add(jFormattedText_nTrials, gridBagConstraints);

        jLabel2.setText("Number of Trials");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        ExpDef_jPanel.add(jLabel2, gridBagConstraints);

        reset_AnGrTr_Button.setText("Reset Animals Grp Trial #s ");
        reset_AnGrTr_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reset_AnGrTr_ButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 12;
        gridBagConstraints.gridy = 18;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        ExpDef_jPanel.add(reset_AnGrTr_Button, gridBagConstraints);

        jLabel3.setText("Group Selection Table:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 22;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        ExpDef_jPanel.add(jLabel3, gridBagConstraints);

        AllGrpsinAllTrialCheckBox.setSelected(true);
        AllGrpsinAllTrialCheckBox.setText(" All groups are there in all trials");
        AllGrpsinAllTrialCheckBox.setEnabled(false);
        AllGrpsinAllTrialCheckBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                AllGrpsinAllTrialCheckBoxItemStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 18;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        ExpDef_jPanel.add(AllGrpsinAllTrialCheckBox, gridBagConstraints);

        InfoTab.addTab("Experiment Definition", ExpDef_jPanel);

        DataFiles_jPanel.setAutoscrolls(true);
        DataFiles_jPanel.setMinimumSize(new java.awt.Dimension(726, 713));
        DataFiles_jPanel.setPreferredSize(new java.awt.Dimension(726, 713));
        DataFiles_jPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        AddFiles_Button.setText(" Add Files");
        AddFiles_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddFiles_ButtonActionPerformed(evt);
            }
        });
        DataFiles_jPanel.add(AddFiles_Button, new org.netbeans.lib.awtextra.AbsoluteConstraints(359, 165, 121, -1));

        RemoveFile_Button.setText("Remove Files");
        RemoveFile_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RemoveFile_ButtonActionPerformed(evt);
            }
        });
        DataFiles_jPanel.add(RemoveFile_Button, new org.netbeans.lib.awtextra.AbsoluteConstraints(486, 165, -1, -1));

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
        FileDetail_Table.setColumnSelectionAllowed(true);
        FileDetail_Table.setDoubleBuffered(true);
        FileDetail_Table.setDragEnabled(true);
        FileDetail_Table.setEditingColumn(1);
        FileDetail_Table.setEditingRow(1);
        jScrollPane6.setViewportView(FileDetail_Table);
        FileDetail_Table.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        DataFiles_jPanel.add(jScrollPane6, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 12, 701, 125));

        Assign_Button.setText("Assign");
        Assign_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Assign_ButtonActionPerformed(evt);
            }
        });
        DataFiles_jPanel.add(Assign_Button, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 199, 124, -1));

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

        DataFiles_jPanel.add(jScrollPane7, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 371, 720, 202));

        GrpSelComboBox.setEditable(true);
        GrpSelComboBox.setDoubleBuffered(true);
        GrpSelComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GrpSelComboBoxActionPerformed(evt);
            }
        });
        DataFiles_jPanel.add(GrpSelComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(277, 165, 80, -1));

        AnimalSelComboBox.setEnabled(false);
        DataFiles_jPanel.add(AnimalSelComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(55, 165, 68, -1));

        TrialSelComboBox.setEditable(true);
        TrialSelComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TrialSelComboBoxActionPerformed(evt);
            }
        });
        DataFiles_jPanel.add(TrialSelComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(166, 165, 68, -1));

        SelDesLabel.setText("Select Grp, Trial and Animal for assigning the data files");
        DataFiles_jPanel.add(SelDesLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(22, 149, 311, -1));

        GrpLabel.setText("Group");
        DataFiles_jPanel.add(GrpLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 168, 31, -1));

        TrialLabel.setText("Trial");
        DataFiles_jPanel.add(TrialLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(129, 168, 31, -1));

        AnimalLabel.setText("Animal");
        DataFiles_jPanel.add(AnimalLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 168, 43, -1));

        OpenFileAssignmentsButton.setText("Open File Assignments");
        OpenFileAssignmentsButton.setToolTipText("Open test file with assignments shown in table below(no headers) tab sep col and new line separators for rows");
        OpenFileAssignmentsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OpenFileAssignmentsButtonActionPerformed(evt);
            }
        });
        DataFiles_jPanel.add(OpenFileAssignmentsButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(148, 287, -1, -1));

        SaveFileAssignmentsButton.setText("Save Assignments");
        SaveFileAssignmentsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SaveFileAssignmentsButtonActionPerformed(evt);
            }
        });
        DataFiles_jPanel.add(SaveFileAssignmentsButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 287, -1, -1));

        xResTxtField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("0"))));
        xResTxtField.setText("1920");
        xResTxtField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                xResTxtFieldActionPerformed(evt);
            }
        });
        DataFiles_jPanel.add(xResTxtField, new org.netbeans.lib.awtextra.AbsoluteConstraints(378, 321, 77, -1));

        yResTxtField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("0"))));
        yResTxtField.setText("1080");
        yResTxtField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                yResTxtFieldActionPerformed(evt);
            }
        });
        DataFiles_jPanel.add(yResTxtField, new org.netbeans.lib.awtextra.AbsoluteConstraints(503, 321, 77, -1));

        jLabel4.setText("x Res");
        DataFiles_jPanel.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(323, 324, 49, -1));

        jLabel5.setText("yRes");
        DataFiles_jPanel.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(461, 324, -1, -1));

        jLabel6.setText("Total Number of Files Expected");
        DataFiles_jPanel.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 629, 168, 22));

        jLabel7.setText("Total Number of Files Assigned");
        DataFiles_jPanel.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 657, 168, 22));

        jLabel8.setText("Remainaing data files that require assignment/selection");
        DataFiles_jPanel.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 685, 263, 22));

        expFileNoJText.setEnabled(false);
        DataFiles_jPanel.add(expFileNoJText, new org.netbeans.lib.awtextra.AbsoluteConstraints(311, 629, 54, -1));

        assignFileJTxt.setEnabled(false);
        DataFiles_jPanel.add(assignFileJTxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(311, 657, 54, -1));

        remainingDatafilesJText.setEnabled(false);
        DataFiles_jPanel.add(remainingDatafilesJText, new org.netbeans.lib.awtextra.AbsoluteConstraints(311, 685, 54, -1));

        jButtonFileAssignRest.setText("Reset File Assignments");
        jButtonFileAssignRest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonFileAssignRestActionPerformed(evt);
            }
        });
        DataFiles_jPanel.add(jButtonFileAssignRest, new org.netbeans.lib.awtextra.AbsoluteConstraints(525, 629, -1, -1));

        jCombo_dataSeparator.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "\"\\t\" (Tab)", "  (Space)", "\",\" (Comma)", "\";\"  (Semi-colon)", "(user def: Clear and type the charecter ) " }));
        jCombo_dataSeparator.setSelectedIndex(1);
        jCombo_dataSeparator.setToolTipText("The user can choose one of the listed separators or can provide new. Click to enter and new string");
        DataFiles_jPanel.add(jCombo_dataSeparator, new org.netbeans.lib.awtextra.AbsoluteConstraints(146, 321, 159, -1));

        jLabel13.setText("Choose the data separator ");
        DataFiles_jPanel.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 324, -1, -1));

        jFormatTxt_rootFolder.setBackground(new java.awt.Color(204, 255, 204));
        jFormatTxt_rootFolder.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Root Folder for Data Files ", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(204, 255, 204))); // NOI18N
        jFormatTxt_rootFolder.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jFormatTxt_rootFolder.setEnabled(false);
        DataFiles_jPanel.add(jFormatTxt_rootFolder, new org.netbeans.lib.awtextra.AbsoluteConstraints(148, 193, 436, -1));
        DataFiles_jPanel.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(549, 199, -1, -1));

        jButtonBrowseRoot.setText("Browse");
        jButtonBrowseRoot.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBrowseRootActionPerformed(evt);
            }
        });
        DataFiles_jPanel.add(jButtonBrowseRoot, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 215, 79, 44));

        jButtonRemoveAssignments.setText("Remove Assignments");
        jButtonRemoveAssignments.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRemoveAssignmentsActionPerformed(evt);
            }
        });
        DataFiles_jPanel.add(jButtonRemoveAssignments, new org.netbeans.lib.awtextra.AbsoluteConstraints(377, 629, -1, -1));

        jButton1.setText("Relativise");
        jButton1.setEnabled(false);
        DataFiles_jPanel.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 265, -1, 44));

        chkBoxRemoveAssignedFiles.setSelected(true);
        chkBoxRemoveAssignedFiles.setText(" Remove Files  After Assignment");
        DataFiles_jPanel.add(chkBoxRemoveAssignedFiles, new org.netbeans.lib.awtextra.AbsoluteConstraints(316, 288, 256, -1));

        InfoTab.addTab("Data Files", DataFiles_jPanel);

        AnalysisDesign_jPanel.setPreferredSize(new java.awt.Dimension(740, 900));
        AnalysisDesign_jPanel.setLayout(new java.awt.GridBagLayout());

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
        expDgnTree.setMaximumSize(new java.awt.Dimension(100, 32));
        expDgnTree.setPreferredSize(new java.awt.Dimension(100, 32));
        expDgnTree.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                expDgnTreeMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(expDgnTree);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        AnalysisDesign_jPanel.add(jScrollPane2, gridBagConstraints);

        RunGrp_Button.setText("Run Grp Analysis");
        RunGrp_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RunGrp_ButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        AnalysisDesign_jPanel.add(RunGrp_Button, gridBagConstraints);

        HeatMap_Button.setText("Generate Heat Map");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        AnalysisDesign_jPanel.add(HeatMap_Button, gridBagConstraints);

        QuadAna_Button.setText("Quadrant Analysis");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        AnalysisDesign_jPanel.add(QuadAna_Button, gridBagConstraints);

        GenConMaps_Button.setText("Generate Convergence Maps");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        AnalysisDesign_jPanel.add(GenConMaps_Button, gridBagConstraints);

        GenCurlMaps_Button.setText("Generate Curl Maps");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        AnalysisDesign_jPanel.add(GenCurlMaps_Button, gridBagConstraints);

        jLabel9.setText("Platform X Co-Ordinate");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        AnalysisDesign_jPanel.add(jLabel9, gridBagConstraints);

        jLabel10.setText("Platform Y Co-Ordinate");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        AnalysisDesign_jPanel.add(jLabel10, gridBagConstraints);

        jLabel11.setText("OC X Co-Ordinate");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        AnalysisDesign_jPanel.add(jLabel11, gridBagConstraints);

        jLabel12.setText("OC Y Co-Ordinate");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        AnalysisDesign_jPanel.add(jLabel12, gridBagConstraints);

        PlatXjFtTxt.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        PlatXjFtTxt.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        PlatXjFtTxt.setText("120");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        AnalysisDesign_jPanel.add(PlatXjFtTxt, gridBagConstraints);

        PlatYjFtTxt1.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        PlatYjFtTxt1.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        PlatYjFtTxt1.setText("120");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        AnalysisDesign_jPanel.add(PlatYjFtTxt1, gridBagConstraints);

        ocXjFtTxt2.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        ocXjFtTxt2.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        ocXjFtTxt2.setText("120");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        AnalysisDesign_jPanel.add(ocXjFtTxt2, gridBagConstraints);

        ocYjFtTxt3.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        ocYjFtTxt3.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        ocYjFtTxt3.setText("120");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        AnalysisDesign_jPanel.add(ocYjFtTxt3, gridBagConstraints);

        autoEstOC.setSelected(true);
        autoEstOC.setText("Select the check box to auto estimate OC");
        autoEstOC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                autoEstOCActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        AnalysisDesign_jPanel.add(autoEstOC, gridBagConstraints);

        InfoTab.addTab("Design Tree", AnalysisDesign_jPanel);

        AnalysisSettingTabPanel.setMinimumSize(new java.awt.Dimension(710, 570));
        AnalysisSettingTabPanel.setPreferredSize(new java.awt.Dimension(732, 900));
        AnalysisSettingTabPanel.setLayout(new java.awt.GridBagLayout());

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Pre Processing", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));
        java.awt.GridBagLayout jPanel2Layout = new java.awt.GridBagLayout();
        jPanel2Layout.columnWidths = new int[] {0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0};
        jPanel2Layout.rowHeights = new int[] {0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0};
        jPanel2.setLayout(jPanel2Layout);

        jLabel24.setText("Pixel Aspect Ratio (x/y)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.gridwidth = 11;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel2.add(jLabel24, gridBagConstraints);

        jLabel21.setText("Scaling Factor");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 14;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanel2.add(jLabel21, gridBagConstraints);

        scalingfactorJFormFld.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        scalingfactorJFormFld.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        scalingfactorJFormFld.setText("1.00");
        scalingfactorJFormFld.setToolTipText("The spatial scale used to scale the images (1< && > 0  for down sizing > 1 for magnifying)");
        scalingfactorJFormFld.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                scalingfactorJFormFldActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 16;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel2.add(scalingfactorJFormFld, gridBagConstraints);

        reSzImgjChkBx.setText("Resize images");
        reSzImgjChkBx.setToolTipText("Enable this to resize images before surface fit");
        reSzImgjChkBx.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                reSzImgjChkBxItemStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 19;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel2.add(reSzImgjChkBx, gridBagConstraints);

        useTan2jChkBx.setText("Orientation Sensitive");
        useTan2jChkBx.setToolTipText("By default the software uses tan inverse without differentiating vectors differing by 180 deg. If checked then tan2  inverse is used that differentiates these vectors");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 29;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel2.add(useTan2jChkBx, gridBagConstraints);

        ScaleY_JChkBx.setSelected(true);
        ScaleY_JChkBx.setText("Scale Y");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.gridwidth = 7;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel2.add(ScaleY_JChkBx, gridBagConstraints);

        useRelVelJChkBx.setSelected(true);
        useRelVelJChkBx.setText(" Use Relative Velocity");
        useRelVelJChkBx.setToolTipText("Selecting this nomalises the Peak velocity of Each Data File to Float.Max");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 19;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        jPanel2.add(useRelVelJChkBx, gridBagConstraints);

        jCheckBox2.setText("Orientation data from file");
        jCheckBox2.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 29;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel2.add(jCheckBox2, gridBagConstraints);

        aspectRatiojFmtFld.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.000"))));
        aspectRatiojFmtFld.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        aspectRatiojFmtFld.setText("1.00");
        aspectRatiojFmtFld.setToolTipText("Horizontal By Vertical");
        aspectRatiojFmtFld.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aspectRatiojFmtFldActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 16;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel2.add(aspectRatiojFmtFld, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        AnalysisSettingTabPanel.add(jPanel2, gridBagConstraints);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Post Process (Results/Output)"));
        java.awt.GridBagLayout jPanel3Layout = new java.awt.GridBagLayout();
        jPanel3Layout.columnWidths = new int[] {0, 11, 0, 11, 0, 11, 0, 11, 0, 11, 0, 11, 0, 11, 0, 11, 0, 11, 0, 11, 0, 11, 0, 11, 0, 11, 0, 11, 0, 11, 0, 11, 0, 11, 0, 11, 0, 11, 0};
        jPanel3Layout.rowHeights = new int[] {0, 7, 0, 7, 0, 7, 0, 7, 0, 7, 0, 7, 0, 7, 0};
        jPanel3.setLayout(jPanel3Layout);

        autoPoolRoijChkBx.setSelected(true);
        autoPoolRoijChkBx.setText("Auto determine pool ROI");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 19;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel3.add(autoPoolRoijChkBx, gridBagConstraints);

        jLabel18.setText("x Ctr");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel3.add(jLabel18, gridBagConstraints);

        xPoolCtrjFormFld.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getIntegerInstance())));
        xPoolCtrjFormFld.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        xPoolCtrjFormFld.setText("0");
        xPoolCtrjFormFld.setMinimumSize(new java.awt.Dimension(60, 25));
        xPoolCtrjFormFld.setPreferredSize(new java.awt.Dimension(20, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel3.add(xPoolCtrjFormFld, gridBagConstraints);

        jLabel19.setText("y Ctr");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 14;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel3.add(jLabel19, gridBagConstraints);

        yPoolCtrjFormFld.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getIntegerInstance())));
        yPoolCtrjFormFld.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        yPoolCtrjFormFld.setText("0");
        yPoolCtrjFormFld.setMinimumSize(new java.awt.Dimension(20, 20));
        yPoolCtrjFormFld.setPreferredSize(new java.awt.Dimension(20, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 16;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel3.add(yPoolCtrjFormFld, gridBagConstraints);

        jLabel20.setText("Radius");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel3.add(jLabel20, gridBagConstraints);

        poolRadjFormFld.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getIntegerInstance())));
        poolRadjFormFld.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        poolRadjFormFld.setText("0");
        poolRadjFormFld.setPreferredSize(new java.awt.Dimension(20, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 10;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel3.add(poolRadjFormFld, gridBagConstraints);

        jCheckBox3.setText("Save magnitude of velocity Vs Radial diststance");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.gridwidth = 39;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel3.add(jCheckBox3, gridBagConstraints);

        saveVelocityjchkBx.setText("Save Velocity Maps");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.gridwidth = 19;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel3.add(saveVelocityjchkBx, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        AnalysisSettingTabPanel.add(jPanel3, gridBagConstraints);

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Surface Fit Setting", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));
        jPanel4.setLayout(new java.awt.GridBagLayout());

        jLabel16.setText("Order of fit for the x (horizontal axis)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 15;
        gridBagConstraints.ipadx = 54;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(66, 12, 0, 0);
        jPanel4.add(jLabel16, gridBagConstraints);

        x_polyOrderJCmbBx.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" }));
        x_polyOrderJCmbBx.setSelectedIndex(4);
        x_polyOrderJCmbBx.setToolTipText("Determines the degree of the polynomial to be used in surface fit generation. ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 15;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 32;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(63, 6, 0, 11);
        jPanel4.add(x_polyOrderJCmbBx, gridBagConstraints);

        jLabel17.setText("Order of fit for the y (vertical axis)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 15;
        gridBagConstraints.ipadx = 69;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(29, 12, 0, 0);
        jPanel4.add(jLabel17, gridBagConstraints);

        y_polyOrderJCmbBx.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" }));
        y_polyOrderJCmbBx.setSelectedIndex(4);
        y_polyOrderJCmbBx.setToolTipText("Determines the degree of the polynomial to be used in surface fit generation. ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 15;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 32;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(26, 6, 0, 11);
        jPanel4.add(y_polyOrderJCmbBx, gridBagConstraints);

        genVeljChkBx1.setText("Use Velocity as is");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.insets = new java.awt.Insets(6, 11, 0, 0);
        jPanel4.add(genVeljChkBx1, gridBagConstraints);

        genAccjChkBx.setSelected(true);
        genAccjChkBx.setText("Generate Accelaration Surfaces");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 12, 0, 0);
        jPanel4.add(genAccjChkBx, gridBagConstraints);

        useSeljChBx.setSelected(true);
        useSeljChBx.setText(" Use Selection( Only use the region that is sampled)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 31;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 12, 0, 0);
        jPanel4.add(useSeljChBx, gridBagConstraints);

        res2SeljChkBx.setSelected(true);
        res2SeljChkBx.setText("Restrict selection to pixels (Use only the pixels). ");
        res2SeljChkBx.setToolTipText("(This would be a sub-grp of above)");
        res2SeljChkBx.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                res2SeljChkBxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 16;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 12, 0, 0);
        jPanel4.add(res2SeljChkBx, gridBagConstraints);

        genConvJChkBx.setSelected(true);
        genConvJChkBx.setText("Generate Convergence Image");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 12, 0, 0);
        jPanel4.add(genConvJChkBx, gridBagConstraints);

        genDivjChkBx.setText("Generate Divergence Image");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(12, 12, 0, 0);
        jPanel4.add(genDivjChkBx, gridBagConstraints);

        vectJChkBx.add(CompforVectorFldjChkBx2);
        CompforVectorFldjChkBx2.setSelected(true);
        CompforVectorFldjChkBx2.setText("Use Components for Convergence");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.gridwidth = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(12, 12, 0, 0);
        jPanel4.add(CompforVectorFldjChkBx2, gridBagConstraints);

        compJRadGrp.add(AlongJRadBtn);
        AlongJRadBtn.setSelected(true);
        AlongJRadBtn.setText("Along ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.ipadx = 40;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(18, 41, 17, 0);
        jPanel4.add(AlongJRadBtn, gridBagConstraints);

        compJRadGrp.add(OrtoJRadBtn);
        OrtoJRadBtn.setText("Ortogonal");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.gridwidth = 11;
        gridBagConstraints.ipadx = 21;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(18, 6, 17, 0);
        jPanel4.add(OrtoJRadBtn, gridBagConstraints);

        usePltCordChkBx2.setText("Use Platform Cord ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 11, 0, 0);
        jPanel4.add(usePltCordChkBx2, gridBagConstraints);

        useIndROIjChkBx.setText("Use Indvidual ROI");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(6, 11, 0, 0);
        jPanel4.add(useIndROIjChkBx, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        AnalysisSettingTabPanel.add(jPanel4, gridBagConstraints);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Filter Settings"));
        java.awt.GridBagLayout jPanel1Layout = new java.awt.GridBagLayout();
        jPanel1Layout.columnWidths = new int[] {0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0};
        jPanel1Layout.rowHeights = new int[] {0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0};
        jPanel1.setLayout(jPanel1Layout);

        jLabel23.setText("y Radius");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 12;
        gridBagConstraints.gridy = 8;
        jPanel1.add(jLabel23, gridBagConstraints);

        jLabel15.setText("x Radius");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 12;
        gridBagConstraints.gridy = 6;
        jPanel1.add(jLabel15, gridBagConstraints);

        FilterJChkBxGrp.add(gaussjChkBx);
        gaussjChkBx.setText("Use Gaussian ");
        gaussjChkBx.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                gaussjChkBxItemStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.gridwidth = 3;
        jPanel1.add(gaussjChkBx, gridBagConstraints);

        FilterJChkBxGrp.add(noFiltjChkBx1);
        noFiltjChkBx1.setSelected(true);
        noFiltjChkBx1.setText("No Filter");
        noFiltjChkBx1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                noFiltjChkBx1ItemStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanel1.add(noFiltjChkBx1, gridBagConstraints);

        FilterJChkBxGrp.add(medianjChkBx);
        medianjChkBx.setText("Use Median");
        medianjChkBx.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                medianjChkBxItemStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanel1.add(medianjChkBx, gridBagConstraints);

        gauRadjFormFld.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        gauRadjFormFld.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        gauRadjFormFld.setText("10.0");
        gauRadjFormFld.setToolTipText("The radius of the 2D Gaussian Blur it is symetrical ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 14;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanel1.add(gauRadjFormFld, gridBagConstraints);

        jLabel22.setText("Filter Radius");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 10;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.ipadx = 18;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel1.add(jLabel22, gridBagConstraints);

        jChkBxAssym.setText("Assymetric");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 10;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel1.add(jChkBxAssym, gridBagConstraints);

        jFmtTxtFldRadY.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getIntegerInstance())));
        jFmtTxtFldRadY.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFmtTxtFldRadY.setText("0");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 14;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel1.add(jFmtTxtFldRadY, gridBagConstraints);

        jFmtTxtFldRadX.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFmtTxtFldRadX.setText("0");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 14;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel1.add(jFmtTxtFldRadX, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        AnalysisSettingTabPanel.add(jPanel1, gridBagConstraints);

        InfoTab.addTab("Analysis Setting", AnalysisSettingTabPanel);

        DeskTopPanel.add(InfoTab);

        jScrollPane4.setViewportView(DeskTopPanel);
        DeskTopPanel.getAccessibleContext().setAccessibleName("");
        var parentSize = DeskTopPanel.getParent().getSize();
        DeskTopPanel.setSize((int)parentSize.getWidth()/2,(int)parentSize.getHeight()/2);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 0.5;
        getContentPane().add(jScrollPane4, gridBagConstraints);

        jScrollPaneProgImgDisp.setAutoscrolls(true);
        jScrollPaneProgImgDisp.setMinimumSize(new java.awt.Dimension(650, 850));
        jScrollPaneProgImgDisp.setPreferredSize(new java.awt.Dimension(1000, 1000));

        ProgImgDispParent.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        ProgImgDispParent.setAutoscrolls(true);
        ProgImgDispParent.setMaximumSize(new java.awt.Dimension(650000, 6500000));
        ProgImgDispParent.setMinimumSize(new java.awt.Dimension(0, 0));
        ProgImgDispParent.setName("ImageDisplay Panel"); // NOI18N
        ProgImgDispParent.setPreferredSize(new java.awt.Dimension(650, 850));
        ProgImgDispParent.setLayout(new java.awt.GridBagLayout());

        ProgIndPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Progress Indicator", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));
        ProgIndPanel.setPreferredSize(new java.awt.Dimension(500, 100));
        ProgIndPanel.setLayout(new java.awt.GridBagLayout());

        jProgressBarDataAssignment.setToolTipText("Indicates the progress of data assignment");
        jProgressBarDataAssignment.setStringPainted(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 431;
        gridBagConstraints.ipady = 11;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(12, 27, 0, 22);
        ProgIndPanel.add(jProgressBarDataAssignment, gridBagConstraints);

        jProgressBarFR.setToolTipText("Indicates the progress of data assignment");
        jProgressBarFR.setStringPainted(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 431;
        gridBagConstraints.ipady = 11;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 27, 0, 22);
        ProgIndPanel.add(jProgressBarFR, gridBagConstraints);

        jLabel29.setText("Data Assignment Status");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 27, 0, 0);
        ProgIndPanel.add(jLabel29, gridBagConstraints);

        jLabel28.setText("Files Read");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 27, 0, 0);
        ProgIndPanel.add(jLabel28, gridBagConstraints);

        jPanel6.setLayout(new java.awt.GridBagLayout());

        jProgressBarTP.setStringPainted(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 118;
        gridBagConstraints.ipady = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 0, 0, 0);
        jPanel6.add(jProgressBarTP, gridBagConstraints);

        jProgressBarGP.setStringPainted(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 118;
        gridBagConstraints.ipady = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 4, 0, 0);
        jPanel6.add(jProgressBarGP, gridBagConstraints);

        jProgressBarDP.setStringPainted(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 118;
        gridBagConstraints.ipady = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 4, 0, 0);
        jPanel6.add(jProgressBarDP, gridBagConstraints);

        jLabel25.setText("Trials Processed");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(12, 27, 0, 0);
        jPanel6.add(jLabel25, gridBagConstraints);

        jLabel27.setText("Data processed");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(12, 28, 0, 0);
        jPanel6.add(jLabel27, gridBagConstraints);

        jLabel26.setText("Grps Processed");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(12, 12, 0, 0);
        jPanel6.add(jLabel26, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        ProgIndPanel.add(jPanel6, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 125;
        gridBagConstraints.ipady = 125;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        ProgImgDispParent.add(ProgIndPanel, gridBagConstraints);

        ImageDispPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Image/Graph Display", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));
        ImageDispPanel.setMinimumSize(new java.awt.Dimension(400, 400));
        ImageDispPanel.setPreferredSize(new java.awt.Dimension(500, 500));
        ImageDispPanel.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        ProgImgDispParent.add(ImageDispPanel, gridBagConstraints);

        controllerPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        controllerPanel.setLayout(new java.awt.GridBagLayout());

        jButton5.setText("jButton2");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = -37;
        gridBagConstraints.ipady = 13;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(15, 6, 18, 0);
        controllerPanel.add(jButton5, gridBagConstraints);

        jButton6.setText("Button");
        jButton6.setToolTipText("");
        jButton6.setAutoscrolls(true);
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = -37;
        gridBagConstraints.ipady = 13;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(15, 6, 18, 12);
        controllerPanel.add(jButton6, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = -37;
        gridBagConstraints.ipady = 13;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(15, 12, 18, 0);
        controllerPanel.add(jButton7, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 316;
        gridBagConstraints.ipady = 15;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        ProgImgDispParent.add(controllerPanel, gridBagConstraints);

        jScrollPaneProgImgDisp.setViewportView(ProgImgDispParent);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.75;
        gridBagConstraints.weighty = 0.75;
        getContentPane().add(jScrollPaneProgImgDisp, gridBagConstraints);

        statMssgScrollPane.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        statMssgScrollPane.setViewportBorder(javax.swing.BorderFactory.createTitledBorder("Status Messages"));
        statMssgScrollPane.setAutoscrolls(true);
        statMssgScrollPane.setMinimumSize(new java.awt.Dimension(35, 75));
        statMssgScrollPane.setPreferredSize(new java.awt.Dimension(1000, 75));
        statMssgScrollPane.setViewportView(null);

        StatusMessageBox.setColumns(20);
        StatusMessageBox.setLineWrap(true);
        StatusMessageBox.setRows(5);
        StatusMessageBox.setWrapStyleWord(true);
        StatusMessageBox.setBorder(null);
        StatusMessageBox.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        StatusMessageBox.setDragEnabled(true);
        statMssgScrollPane.setViewportView(StatusMessageBox);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.gridheight = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        getContentPane().add(statMssgScrollPane, gridBagConstraints);

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

        setBounds(0, 0, 1416, 1013);
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
               
        
        File rootFolder = new File(fNames[0]).getParentFile();
        
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
        
        dManager = new DataManager();
        dManager.setInPath(rootFolder.getName());
        dManager.setInPath(path2root);
        dManager.setDataFileNames(fNames);
        
        
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
        String dataSeparator;
        
        var selIdx = this.jCombo_dataSeparator.getSelectedIndex(); 
        
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
        dManager.setXRes(Integer.parseInt(this.xResTxtField.getText()));
        dManager.setYRes(Integer.parseInt(this.yResTxtField.getText()));
        dManager.setDataSep(dataSeparator);
       // dManager.readData(); 
    }

    private void residencemapMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_residencemapMenuItemActionPerformed
        
        generateResidenceMap(dManager);
        
    }//GEN-LAST:event_residencemapMenuItemActionPerformed

    private void generateResidenceMap(DataManager manager) {
        // TODO add your handling code here:
        int xRes = manager.getXRes();
        int yRes = manager.getYRes();
        int count = 0;
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
        
        ImagePlus temp;
        Natural_NeighInter nni;
        for(var rmap : rmapImages){
            var tmpName = (manager.getDataFileNames()[fileCount]);
            label  = "HMap of "+ tmpName.substring(1+tmpName.lastIndexOf(File.separator));  
            stk.addSlice(rmap.getImages()[0].getProcessor());
            nni = new Natural_NeighInter(rmap.getImages()[0],null);
            nni.finaliseSurface();
            temp = nni.imageOutput();
            FileSaver fs = new ij.io.FileSaver(temp);
            fs.saveAsTiff(folderpath+File.separator+"HMapSur");
            temp.show();
            stk.setSliceLabel(label,fileCount+1);
            rmap.saveImages(folderpath,label);
            
            fileCount++;
        }
        hmapStk.setStack(stk);
        if (true /*gBlur*/)hmapStk.getProcessor().blurGaussian(20/*radius of mice object in pixels*/); /** TO DO **/
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

    private void autoEstOCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_autoEstOCActionPerformed

        estimateOC = ( autoEstOC.isSelected());

    }//GEN-LAST:event_autoEstOCActionPerformed

    /***
     * @param img2Disp  Imageplus object containing the image to be displayed
     * @param displayPanel the reference to the panel in which the image needs to be displayed
     */
    private void displayImgInPanel(ImagePlus img2Disp, javax.swing.JPanel displayPanel){
        
        if(displayPanel != null && img2Disp != null){
            
            Image img = img2Disp.getImage();
            ImageIcon icon = new ImageIcon(img);
            JLabel label = new JLabel(icon);
            
            displayPanel.add(label);
        }
    
}
    private String getSetting(){
        String settingString = "";
        
         settingString +=  " The analysis was done using the following options: \n" ;
         settingString += "Root folder set to :" + this.jFormatTxt_rootFolder.getText() +"\n";
         settingString += "No of groups :" + this.nGrps + "\n";
         settingString += "No of trials :" + this.nTrial + "\n";
         //also dump the number of animals per grup distribution and group selection tables
         
         //save the file assignments
         //group names
         //trial names
         
         settingString += "FileSeparator : " + (String)this.jCombo_dataSeparator.getSelectedItem() +"\n";
         settingString += "xRes :" + this.xResTxtField.getText() +"\t";
         settingString += "yRes :" + this.yResTxtField.getText()+ "\n";
         
         //Get the assignment table and save it
         settingString += "PlatX :" + this.PlatXjFtTxt.getText() + " \t PlatY :" + this.PlatYjFtTxt1.getText() + "\n";
         settingString +=  (this.autoEstOC.isSelected()) ? "Auto est OC \n" : "OC is provided Manually. OC_x :" +this.ocXjFtTxt2.getText() + "\t" + "OC_y :" +this.ocYjFtTxt3.getText() +"\n";
         
         settingString += "Pre Processing: \n";
         settingString += " Rel Vel :" + this.useRelVelJChkBx.isSelected() + "\t Resize Images :" + this.reSzImgjChkBx.isSelected() + "\t" + "Scaling Factor : "+this.scalingfactorJFormFld.getText() + "\n";
         settingString += " Gaussian Blur :" + this.gaussjChkBx.isSelected() + "\t Filter Radius :" + this.gauRadjFormFld.getText()+ "\n";
         //add settings for assym
         settingString += " Differential Treatment of moving away and in :" + "\t" + this.useTan2jChkBx.isSelected() +"\n";
         settingString += " To scale the Y : " + this.ScaleY_JChkBx.isSelected() + "\t Pixel Aspect Ratio : " +"\t" + this.scalingfactorJFormFld.getText();
         
         settingString += " Order of fit in X = " +"\t " + (String)this.x_polyOrderJCmbBx.getSelectedItem()+ " \t Order of fit in Y = " +"\t " + (String)this.y_polyOrderJCmbBx.getSelectedItem() + "\n" ;
         settingString += " Acc surface ? :" + this.genAccjChkBx.isSelected() + "\t Use selection :" + "\t" + this.useSeljChBx.isSelected() + "\t" + "Restrict to pixel based selection :" + "\t" + this.res2SeljChkBx.isSelected();
         
         settingString += " Use Velocity Component Along : " + "\t" + this.AlongJRadBtn.isSelected();
         settingString += "\n Use Velocity :\t" + this.genVeljChkBx1.isSelected();
         settingString += "\n Use Platform Cord: \t" + this.usePltCordChkBx2.isSelected();
         
         
        return settingString;
        
    }
    
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
        String basePath = (test)? "Z:\\NDL\\balaji\\ishat\\Test data from\\Set 5\\Day7_Trajectories" 
                                : this.jFormatTxt_rootFolder.getText();  
        setting =  new File(basePath+File.separator+ "settings.txt");
        this.logFile    = new File(basePath + File.separator+ "log.txt");
        
        writeTextFile(setting, getSetting());
        
        this.setStatusMessage("Starting new calculation:+\n");
        
        int xRes = Integer.parseInt(this.xResTxtField.getText());
        int yRes = Integer.parseInt(this.yResTxtField.getText());
        //int xPlt = Integer.parseInt(this.PlatXjFtTxt.getText());
        //int yPlt = Integer.parseInt(this.PlatYjFtTxt1.getText());
        int  xOC = Integer.parseInt(this.ocXjFtTxt2.getText());
        int  yOC = Integer.parseInt(this.ocYjFtTxt3.getText());
        this.usePltCord = this.usePltCordChkBx2.isSelected();

        String dataSeparator;// ((String)this.jCombo_dataSeparator.getSelectedItem());
        
        
        var selIdx = (test) ? 2 : this.jCombo_dataSeparator.getSelectedIndex();
        
        
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
        var  tModel = (DefaultTableModel)FileAssignmentTable.getModel();
        var lastRow = FileAssignmentTable.getModel().getRowCount();
        //boolean newGrp = false, newTrial = false;
        for(int Count  =  lastRow-1; Count >= 0 ; Count --){

            g = (String)tModel.getValueAt(Count, 2);            //grp name
            t = (String)tModel.getValueAt(Count, 3);            //trial name

            if (!grpNames.contains(g))                         //Check if these names are there already if not add them
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
//Update the tree Model

        
        
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

       
        int nFiles = (test)  ? 1 : FileAssignmentTable.getRowCount();
        if(nFiles <= 0 )
            return;
        this.RunGrp_Button.setEnabled(false);
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
        if(test){
                fName = "Z:\\NDL\\balaji\\ishat\\Test data from\\Set 5\\Day7_Trajectories\\M65.csv";  
                grpName = "ggTest";
                trialName = "ttTest";
                gUID = 0;
                tUID = 0;
                nTrial = 1;
                nGrps = 1;
                tModel.addRow(new String[]{"M65.csv","",grpName,trialName});
                grpNames.add(grpName);
                trialNames.add(trialName);
                 trialNode = new DefaultMutableTreeNode(trialNames.get(0));
                treeModel.insertNodeInto(trialNode,trialRoot, 0);
                grpNode = new DefaultMutableTreeNode(grpNames.get(0));
                treeModel.insertNodeInto(grpNode, trialNode,0);
                treeModel.reload();
                //nFileAssigned[tUID][gUID]++;
                grpData = new DataManager();
                grpData.addDataFile(fName);
                ArrayList trial = new ArrayList();
                trial.add(0,grpData);
                TrialData.add(0,trial);
                //TrialData.get(tUID).get(gUID).addDataFile(/*aUID,*/fName); //Need to retrive aUID coresponding to aName
                fileLeaf = new DefaultMutableTreeNode(fName);
                trNode = ((DefaultMutableTreeNode)treeModel.getChild(trialRoot, tUID));
                grpNode = ((DefaultMutableTreeNode)treeModel.getChild(trNode, gUID));
                treeModel.insertNodeInto(fileLeaf,grpNode, grpNode.getChildCount());

                //int currVal = Count;

                this.UpdateProgress(100, jProgressBarFR,"%");
           }else{
        
                for(int Count = 0 ; Count < nFiles ; Count++){

                    fnameKey = (String)FileAssignmentTable.getValueAt(Count,0);
                    if(fnameKey == null) {
                         this.setStatusMessage("Ignoring the empty / non - readable entry at row #" + Count);
                         continue;
                    }
                    fName =  this.rel2absPathMaps.get(fnameKey);           //get the file name with full path if it is relativised

                    if(fName == null){
                        //javax.swing.JOptionPane.showMessageDialog(this, "fileName is null the key "+fnameKey+" did not fetch a file");
                        errorlist[unassigned++] = fnameKey;
                        return;
                    }
                    var errorCount = errorlist.length;
                    for(int a = 0 ;  a < errorCount ; a++){
                        this.setStatusMessage("Error reading the filename#:" + errorlist[a] +"\n");  
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
                   
                }
        }


        fit.setPreScale(this.reSzImgjChkBx.isSelected());
        fit.setScaleBy(Double.parseDouble(this.scalingfactorJFormFld.getText()));

        fit.setGaussFilt(this.gaussjChkBx.isSelected());
        fit.setAssymGauss(this.jChkBxAssym.isSelected());
        
        if(fit.isAssymGauss()){
            fit.setRadX(Double.parseDouble(jFmtTxtFldRadX.getText()));
            fit.setRadY(Double.parseDouble(jFmtTxtFldRadY.getText()));
        }else
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
        ArrayList<ArrayList<DataManager>>[] tempData; //temporary object 
        tempData = new ArrayList[1]; // to ensure we can use it inside a worker thread. 
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
//                        if(nFileAssigned[tCount][gCount] == 0 && !test )     /** This condition should never occur need to check **/
//                            continue;
                        DataManager currManager ;
                        
                        /*  Prepare the datamanager to organise the data. Data Manger instance stores the data for the group. */
                        //var temp = tempData[0].get(tCount);
                        currManager = tempData[0].get(tCount).get(gCount);
                        currManager.setGblurRadius(Double.parseDouble(gauRadjFormFld.getText()));
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
                        //calIndividualFlds(currManager);
                        /**
                         * calIndFlds(tempMan[0]) 
                         */
                        UpdateProgress(g,jProgressBarGP,"Grp #",nGrps+"complete");
                    }
                    UpdateProgress(tc,jProgressBarTP,"Trial #",nTrial+"complete");
                }
                return null;
            }

           
        };  //Worker thread for running the group
       
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

    /***
     * 
     * @param fname
     * @param settingString 
     */
    private void writeTextFile(File fname, String settingString) {
        FileWriter writer;
        try {
            writer = new FileWriter(fname);
            writer.append(settingString);
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(VectorAnalysisMDI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * 
     * @param message
     * @param toAppend 
     */
    private void setStatusMessage(String message, boolean toAppend){
       SwingWorker messenger = new SwingWorker(){
           @Override
           protected Object doInBackground() throws Exception {
               if(toAppend){
                    StatusMessageBox.append(message);
                    JScrollBar vert = statMssgScrollPane.getVerticalScrollBar();
                    int yMax = vert.getMaximum();
                    vert.setValue(yMax);
               }
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
        int[] selIdx = this.FileAssignmentTable.getSelectedRows();      //makes an implicit assumption that the seltected indexes are sorted in ascending
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

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton6ActionPerformed

    private void noFiltjChkBx1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_noFiltjChkBx1ItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_noFiltjChkBx1ItemStateChanged

    private void medianjChkBxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_medianjChkBxItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_medianjChkBxItemStateChanged
    
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
        int ocX = Integer.parseInt(this.ocXjFtTxt2.getText());
        int ocY = Integer.parseInt(this.ocYjFtTxt3.getText());
        int PltX = Integer.parseInt(this.PlatXjFtTxt.getText());
        int PltY = Integer.parseInt(this.PlatYjFtTxt1.getText());
        
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

        
        JVector OC ;
        if(this.estimateOC){
            OC = currManager.findOC(xRes, yRes);                    ///OC is that of Ave map ???
            this.ocXjFtTxt2.setText(""+OC.getComponent(0));
            this.ocYjFtTxt3.setText(""+OC.getComponent(1));
        }else{
            currManager.findOC(xRes, yRes); //this is still required for generating the sampled ROI as findOC function generates the sampled ROI
            OC = (this.usePltCord) ? new JVector(PltX,PltY):new JVector(ocX,ocY);
        }
       // OccCtrs[tCount][gCount] = OC;  ///this is not correct
        currManager.computeAve(0, OC,true);                                         ///Velocity as such
        currManager.saveAverage("grp#_"+gCount+"_",true);
//currManager.computeAve(1, Plt,true);
// currManager.saveAverage("threadGrp#_comp_Plt"+gCount+"_",false);
//currManager.computeAve(3,null,true);
        
        currManager.computeAve(3, null,false);                                      //Residence heat map
        Roi sampledGrpRoi = getSampledROI( 1, currManager.getAveResMap());
        
        currManager.computeAve(1, OC,true);                                         //Velocity projections
        currManager.saveAverage("grp#_comp_OC"+gCount+"_",false);
        
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
        
        // Block for writing individual files
        
        int fileCount = 0;
        int dataLen = currManager.getFileCount();
        var dataFileNames = currManager.getDataFileNames();        
        var VecField = currManager.getVelocityField();        
        var AccField = currManager.getAccelarationField();
        var resMaps = currManager.getResidenceMaps();
        //var curRoi;  
        
        String FldrName = currManager.getOutPath()+File.separator+"Individual"+File.separator;
        String vFldrName = FldrName + "Velocity";
        String aFldrName = FldrName + "Acceleration";
        String roiFldr = FldrName + "Rois";
        String hMapFldr = FldrName + "HMaps";
        File vFolder =  new File(vFldrName);
        File aFolder =  new File(aFldrName);
        File rFolder = new File(roiFldr);
        File hFolder = new File(hMapFldr);
        if(!vFolder.exists())
            vFolder.mkdirs();
        if(!aFolder.exists())
            aFolder.mkdirs();
        if(!rFolder.exists())
            rFolder.mkdirs();
        if(!hFolder.exists())
            hFolder.mkdirs();
        //String resultPath = vFolder.getAbsolutePath();
        //JVectorSpace vField, acField;
        String indFName ;
        
        while( fileCount < dataLen){
            var residence = resMaps[fileCount];
            var OCi = (this.useIndROIjChkBx.isSelected()) ? currManager.findOC(xRes, yRes, residence): OC;
            var curRoi = this.getSampledROI(1, residence);
            var roi2use = (this.useIndROIjChkBx.isSelected()) ? curRoi: sampledGrpRoi;     
            indFName = dataFileNames[fileCount];
            var indexFSep = indFName.lastIndexOf(File.separator);
            indexFSep = indexFSep == -1 ? 0 : indexFSep+1;
            indFName = indFName.substring(indexFSep);
            var indexExt = indFName.lastIndexOf(".");
            indexExt = indexExt == -1 ? indFName.length() : indexExt;
            indFName = indFName.substring(0,indexExt);
            
            var vFieldNorm = VecField[fileCount].getProjections2point(OCi, true).normaliseVectors(residence.getPixelArray());
            var accFieldNorm = AccField[fileCount].getProjections2point(OCi, true).normaliseVectors(residence.getPixelArray());
            
            calculateVectorFldProperties(vFieldNorm,roi2use,true,vFolder.getAbsolutePath(),"Vel_T#_"+tCount+"G#_"+gCount+indFName);
            calculateVectorFldProperties(accFieldNorm,roi2use,true,aFolder.getAbsolutePath(),"Acc_T#_"+tCount+"G#_"+gCount+indFName);
            /****Add Code for heat map generation
            
            */
            var hMap = (JHeatMapArray)residence;
            JVectorCmpImg resImg = new JVectorCmpImg(xRes,yRes,1);
            resImg.addScalar(hMap);
            resImg.saveImages(hFolder.getAbsolutePath(), "hMap_T#"+tCount+"G#_"+gCount+indFName);
            
            RoiEncoder indiEnc = new RoiEncoder(rFolder+File.separator+indFName+".roi");
            try {
                indiEnc.write(curRoi);
            }catch (IOException ex) {
                Logger.getLogger(VectorAnalysisMDI.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            fileCount++;
        }
        
//        for (var vField : VecField){
//            
//            calculateVectorFldProperties(vField, sampledGrpRoi,true,velFolder.getAbsolutePath(),"AccCmpAlgAve_"+"T_#"+tCount+"G_#"+gCount);
//        }
//        
//        for (var aField : AccField){
//            
//        }
        //Block  for writing individual files ends here
    }
    /***
     * Overloaded method for generating vector field images for a given dataset (contained in datamanager)
     * The OC is estimated for the group of images and then used for calculating the Vector fields.
     * @param currManager
     * @return
     * @throws InterruptedException 
     */
    private JVector jVecFieldImgGenerator(DataManager currManager) throws InterruptedException{
        int xRes = currManager.getXRes();
        int yRes = currManager.getYRes();
        int xOC = 0;
        int yOC = 0;
        currManager.findOC(xRes, yRes);
        return jVecFieldImgGenerator(currManager,xRes,yRes,xOC,yOC);
    }

 /**
 *  Method for generating vector field images for a given dataset (contained in datamanager)  using an external OC
 * 
 * @param currManager
 * @param xRes
 * @param yRes
 * @param xOC
 * @param yOC
 * @return
 * @throws InterruptedException 
 */
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
        OC = (this.autoEstOC.isSelected()) ? currManager.findOC( xRes, yRes) : new JVector(xOC,yOC);
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
     * Call this function to calculate the vector field properties of an Vector space. This in turn instantiates a 
     * jVectorFieldCalulator Object to calculate these in a separate thread. 
     * @param VecFld  The vector space for which we need to calculate the properties
     * @param currentRoi The roi representing the sampled sub space 
     * @param isDivergence  set this to true for calculating divergence surface (addition to convergence)
     * @param pathName  pathname to store the resulting files
     * @param suffix    suffix that will be added to the result file. Typically this will be mapped to data file name prefix/suffix. 
     */
    public void calculateVectorFldProperties(JVectorSpace VecFld, Roi currentRoi, boolean isDivergence,String pathName,String suffix){
        
        System.out.println("Entering field calc...");
        jVectorFieldCalculator calculator = new jVectorFieldCalculator();
        calculator.setVecFld(VecFld);
        calculator.setPolyX(x_polyOrderJCmbBx.getSelectedIndex()+1);
        calculator.setPolyY(y_polyOrderJCmbBx.getSelectedIndex()+1);
        calculator.setSuffix(suffix);
        calculator.setPathName(pathName);
        calculator.setFileSeparator(File.separator);
        calculator.setNormalise(this.useRelVelJChkBx.isSelected());
        if(this.noFiltjChkBx1.isSelected())
            calculator.setFilterType(-1);
        else{
            float rad = Float.parseFloat(this.gauRadjFormFld.getText());
            calculator.setFilterRadius(rad);
            fit.setGaussRad(rad);
            if(this.medianjChkBx.isSelected())
                calculator.setFilterType(2);
            else{
                calculator.setFilterType(1);
                fit.setGaussFilt(true);
            }
        }
        
                
       
        fit.setPreScale(this.reSzImgjChkBx.isSelected());
        fit.setScaleBy(Double.parseDouble(this.scalingfactorJFormFld.getText()));
        
        //fit.setGaussFilt(this.gaussjChkBx.isSelected());
        //fit.setGaussRad(Double.parseDouble(this.gauRadjFormFld.getText()));
        
        fit.setSelectPixels(this.res2SeljChkBx.isSelected());
        fit.setUseSelection(this.useSeljChBx.isSelected());
        

        
        calculator.setFit(fit);

        if(currentRoi != null)
           calculator.setSampledRoi(currentRoi);
        else
            System.out.println("Failed to generate ROI");
        calculator.setGenConv(this.genConvJChkBx.isSelected());
        calculator.setGenDiv(isDivergence/*this.genConvJChkBx.isSelected()*/);
        calculator.setAutoGenPool(this.autoPoolRoijChkBx.isSelected());

        Thread thread = new Thread(calculator,""+jVectorFieldCalculator.getInstanceCount());
        thread.start();
        activeCount++;
        if(activeCount == 1){
        Thread monitor = new Thread(threadMonitor);
        setStatusMessage("Starting Monitor..");
        monitor.start();
    }
    
 }
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
    private javax.swing.JPanel AnalysisSettingTabPanel;
    private javax.swing.JTable AnimalGrpSummaryTable;
    private javax.swing.JLabel AnimalLabel;
    private javax.swing.JComboBox<String> AnimalSelComboBox;
    private javax.swing.JButton Assign_Button;
    private javax.swing.JCheckBox CompforVectorFldjChkBx2;
    private javax.swing.JPanel DataFiles_jPanel;
    private javax.swing.JPanel DeskTopPanel;
    private javax.swing.JPanel ExpDef_jPanel;
    private javax.swing.JTable FileAssignmentTable;
    private javax.swing.JTable FileDetail_Table;
    private javax.swing.ButtonGroup FilterJChkBxGrp;
    private javax.swing.JButton GenConMaps_Button;
    private javax.swing.JButton GenCurlMaps_Button;
    private javax.swing.JLabel GrpLabel;
    private javax.swing.JComboBox<String> GrpSelComboBox;
    private javax.swing.JButton HeatMap_Button;
    private javax.swing.JPanel ImageDispPanel;
    private javax.swing.JMenuItem ImportMenuItem;
    private javax.swing.JTabbedPane InfoTab;
    private javax.swing.JButton OpenFileAssignmentsButton;
    private javax.swing.JRadioButton OrtoJRadBtn;
    private javax.swing.JFormattedTextField PlatXjFtTxt;
    private javax.swing.JFormattedTextField PlatYjFtTxt1;
    private javax.swing.JPanel ProgImgDispParent;
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
    private javax.swing.JCheckBox autoEstOC;
    private javax.swing.JCheckBox autoPoolRoijChkBx;
    private javax.swing.JCheckBox chkBoxRemoveAssignedFiles;
    private javax.swing.ButtonGroup compJRadGrp;
    private javax.swing.JMenuItem contentMenuItem;
    private javax.swing.JPanel controllerPanel;
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
    private javax.swing.JFormattedTextField jFmtTxtFldRadY;
    private javax.swing.JMenuItem jFolderOptions;
    private javax.swing.JFormattedTextField jFormatTxt_rootFolder;
    private javax.swing.JFormattedTextField jFormattedTextField_NoOfGrps;
    private javax.swing.JFormattedTextField jFormattedText_nTrials;
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
    private javax.swing.JPanel jPanel6;
    private javax.swing.JProgressBar jProgressBarDP;
    private javax.swing.JProgressBar jProgressBarDataAssignment;
    private javax.swing.JProgressBar jProgressBarFR;
    private javax.swing.JProgressBar jProgressBarGP;
    private javax.swing.JProgressBar jProgressBarTP;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPaneProgImgDisp;
    private javax.swing.JMenu mapsMenu;
    private javax.swing.JCheckBox medianjChkBx;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JFormattedTextField nAnimals_Text;
    private javax.swing.JCheckBox noFiltjChkBx1;
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
    private javax.swing.JScrollPane statMssgScrollPane;
    private javax.swing.JButton upDateButton;
    private javax.swing.JCheckBox useIndROIjChkBx;
    private javax.swing.JCheckBox usePltCordChkBx2;
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
