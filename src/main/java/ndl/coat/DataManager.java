/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ndl.coat;

import ij.process.FloatStatistics;
import ij.process.ImageStatistics;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import ndl.ndllib.*;
/**
 * The current version is designed assuming the user would be doing the analysis of different 
 * groups outside of this class. This class manages all the data files corresponding to one group
 * in a trial.
 * It is assumed that the user groups the experimental files and runs the analysis in individual groups. 
 * Later versions will/might incorporate the experimental design. 
 * Notionally the index refers to animal ID (#)aUID.
 * 
 * 
 * @author balam
 */
public class DataManager extends Object implements Runnable {

    /**
     * @return the VectorFldsReady
     */
    public synchronized boolean isVectorFldsReady() {
        return VectorFldsReady;
    }

    /**
     * @param VectorFldsReady the VectorFldsReady to set
     */
    public synchronized void setVectorFldsReady(boolean VectorFldsReady) {
        this.VectorFldsReady = VectorFldsReady;
//       
//            this.vectorFlag.notifyAll();
//      
       
    }

    private boolean VectorFldsReady = false;
    private final Object vectorFlag = new Object();

    /**
     * @return the timeData
     */
    public synchronized DataTrace_ver_3[] getTimeData() {
        return timeData;
    }

    /**
     * @param timeData the timeData to set
     */
    private void setTimeData(DataTrace_ver_3[] timeData) {
        this.timeData = timeData;
    }

    private boolean newData;
    private boolean useTan2Prj;
    private boolean useRelativeVelocity;
    private boolean rescaleIndividual;
    
    private boolean AveReady;

    /**
     * @return the lineSep
     */
    private char getLineSep() {
        return lineSep;
    }

    /**
     * @param lineSep the lineSep to set
     */
    public void setLineSep(char lineSep) {
        this.lineSep = lineSep;
    }

    /**
     * @return the dataSep
     */
    private String getDataSep() {
        return dataSep;
    }

    /**
     * @param dataSep the dataSep to set
     */
    public void setDataSep(String dataSep) {
        this.dataSep = dataSep;
    }

    /**
     * @return the aveVelFld
     */
    public synchronized JVectorSpace getAveVelFld() {
        return aveVelFld;
    }

    /**
     * @return the aveAccFld
     */
    public JVectorSpace getAveAccFld() {
        return aveAccFld;
    }

    /**
     * @return the aveResMap
     */
    public JHeatMapArray getAveResMap() {
        return aveResMap;
    }    

    /**
     * @param accelaration the accelaration to set
     */
    private void setAccelaration(DataTrace_ver_3[] accelaration) {
        this.accelaration = accelaration;
    }

    /**
     * @return the DataFileNames
     */
    public synchronized String[] getDataFileNames() {
        String [] fileNames = new String[DataFileNames.size()];
        int Count = 0;
        for(String name : DataFileNames)
            fileNames[Count++]=name;
        return fileNames;
    }
    /**
     * @param FileNames the DataFileNames to set. 
     * Expect the full path name as given by File().getAbsolutePath()
     */

    /**
     * @return the velocityField
     */
    public synchronized JVectorSpace[] getVelocityField() {
        return velocityField;
    }

    /**
     * @return the accelarationField
     */
    public synchronized JVectorSpace[] getAccelarationField() {
        return accelarationField;
    } 
    public synchronized DataTrace_ver_3[] getAccelaration(){
        return accelaration;
    }
    /**
     * @param velocity the velocity[] to set
     */
    public synchronized void setVelocity(DataTrace_ver_3[] velocity) {
        this.velocity = velocity;
    }
    /**
     * @return the XRes: the resolution in 'x' dimension (width) of the image
     */
    public synchronized int getXRes() {
        return XRes;
    }

    /**
     * @param XRes  Use this to set the resolution in 'x' dimension (width) of the image
     */
    public synchronized void setXRes(int XRes) {
        this.XRes = XRes;
    }

    /**
     * @return the YRes: the resolution in 'y' dimension (height) of the image
     */
    public synchronized int getYRes() {
        return YRes;
    }

    /**
     * @param YRes Use this to set the resolution in 'x' dimension (width) of the image
     */
    public synchronized void setYRes(int YRes) {
        this.YRes = YRes;
    }
    
    public DataManager(){
        //Path currentPath = Paths.get("");
        outPath = inPath = "";
//        fileCount = 0;
        DataFileNames = new ArrayList<>();
        //new ArrayList<>();
    }
    private final ArrayList <String> DataFileNames;
    private String inPath = "";
    private String outPath = "";
    //int fileCount  = 0;
    private DataTrace_ver_3[] timeData;
    private DataTrace_ver_3[] velocity;
    private DataTrace_ver_3[] accelaration;
    
    private JVectorSpace[] velocityField, accelarationField;
    
    private JHeatMapArray[] residenceMaps;
    
    private JHeatMapArray aveResMap;
    private JVectorSpace aveVelFld,aveAccFld;
    
    private int XRes = 0;   
    private int YRes = 0;
    private double pixelAspectRatio = 1.0;
    private boolean scaleforAspectRatio = true;
    private char lineSep = '\n';
    private String dataSep = ",";
   
 /***
     * Call this function to read the data that is present in the files listed in DataManger.DataFile array of this class.
     * The data is supposed to be in the format of x and y co-ordinates listed in a time series stored as text(ascii) file.
     * (i.e. x1 \t y1 \n x2 \t y2\n....EOF). x1,y1 co -respond to co-ordinates at time t1, x2, y2 at time t2.
     * tn+1 is the time sample immediately after tn of an uniformly sampled data. Once read these data are stored in 
     * the internal data structure DataTrace.
     */
    public synchronized void readData(){
        this.setVectorFldsReady(false);
        DataTrace_ver_3[] currData;
        currData = new DataTrace_ver_3[getDataFileNames().length];
        int count = 0;
        for (String curFile  : getDataFileNames()){
           currData[count] = new DataTrace_ver_3();           
           currData[count].populateData(curFile, getDataSep(), getLineSep(),2,false); 
           count++;
        }
        setTimeData(currData);
        computeAllFields();
        this.setVectorFldsReady(true);
    }
    private void computeAllFields(){
        //int dataCounter = 0;
        int maxFileNo = getDataFileNames().length;
        
        this.setVelocity(new DataTrace_ver_3[maxFileNo]);
        this.setAccelaration(new DataTrace_ver_3[maxFileNo]);
        
        this.velocityField = new JVectorSpace[maxFileNo];
        this.accelarationField = new JVectorSpace[maxFileNo];
        
        this.residenceMaps = new JHeatMapArray[maxFileNo];
                       
        DataTrace_ver_3 velo, acc;
        int fileCounter = 0 ;
        int Idx;
        if(this.getTimeData() == null)
          return;
        for(DataTrace_ver_3 tseries : getTimeData()){
           residenceMaps[fileCounter] = new JHeatMapArray(getXRes(), getYRes());
           getResidenceMap()[fileCounter].setTimeSeries(tseries);
           getResidenceMap()[fileCounter].convertTimeSeriestoArray();
           //var hmapArray = residenceMaps[fileCounter].getPixelArray();

           velocity[fileCounter] = tseries.differentiate(false);
           if(this.isScaleforAspectRatio())
               velocity[fileCounter].scaleYaxis(this.getPixelAspectRatio());
           accelaration[fileCounter] = velocity[fileCounter].differentiate(false); //once scaled for pixel ratio accelaration doesn't 
                                                                                   //need to be scaled       
           ArrayList<JVector> accVectors = new ArrayList<>();
           ArrayList<JVector> velVectors = new ArrayList<>();
           ArrayList<OrdXYData> posiVects = new ArrayList<>();
           //ArrayList<JVector> resiScalars = new ArrayList<>();
           velo = velocity[fileCounter];
           acc = accelaration[fileCounter];
           Idx = 0;

           for(OrdXYErrData accVect : acc){
               accVectors.add(new JVector(accVect.getXY()));
               velVectors.add(new JVector(velo.get(Idx).getXY()));
               //resiScalars.add(new JVector(hmapArray[][]));
               posiVects.add(tseries.get(Idx));
               Idx++;
           }

           accelarationField[fileCounter] = new JVectorSpace(getXRes(),getYRes(),false,posiVects,accVectors);
           velVectors.add(new JVector(velo.get(Idx).getXY()));
           posiVects.add(tseries.get(Idx));
           velocityField[fileCounter] =  new JVectorSpace(getXRes(),getYRes(),false,posiVects,velVectors);

           Idx++;
           posiVects.add(tseries.get(Idx));
//             System.out.println("The datalength is :"+Idx+","+posiVects.size()
//                                +","+velocityField[fileCounter].getSpace().size()+","+velocityField[fileCounter].getVectors().size());
           fileCounter++;
        }
    }
    /***
     * 
     * @param choice 0: for generating the average field of the vectors as such (no projections) 
     *               1: for generating the average field of projected vectors along a another vector 
     *               2: for generating the average field of projected vectors orthogonal to another vector
     *               3: calculates only the residence/number of sample  map average. 
     * @param Vector The position vector along/orthogonal to which we get the projections ( can be null for option "0" for choice).
     * @param resiNorm  True if the vector field needs to normalized for the number of samples. 
     *                  Usually it is true as otherwise they will represent incorrect magnitude.
     */
    public synchronized void computeAve(int choice, JVector Vector, boolean resiNorm){
        boolean timeOut = false;
        long timetowait = 1000; //in milliseconds
        long starttime = System.currentTimeMillis();
                
        while(!this.isVectorFldsReady()&& !timeOut){
             var elapsedTime = System.currentTimeMillis() - starttime;
             timeOut = elapsedTime >= timetowait;
            try {
                this.vectorFlag.wait();
                //Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(DataManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if(timeOut && !this.isVectorFldsReady()) 
            return;
       if( aveResMap == null) aveResMap =  new JHeatMapArray(XRes,YRes);
       if( aveVelFld == null) aveVelFld = new JVectorSpace(XRes,YRes);
       if( aveAccFld == null) aveAccFld = new JVectorSpace(XRes,YRes);
       int Idx = 0;
       JVectorSpace prjFld,accFldPrj;
        switch(choice){
            
            case 0:             //Calculate average of velocity and accelaration
                                //To do: implement normalisation to residence map
                                //similar to case 1: velocity projection.
                getAveVelFld().getSpace().clear();
                getAveVelFld().getVectors().clear();
                getAveAccFld().getSpace().clear();
                getAveAccFld().getVectors().clear();
                for(var velFld : this.velocityField)
                    getAveVelFld().fillSpace(velFld.getSpace(), velFld.getVectors(), false);
                for(var accFld : this.accelarationField)
                    getAveAccFld().fillSpace(accFld.getSpace(), accFld.getVectors(), false); 
//                for(var resFld : this.residenceMaps)
//                    getAveResMap().appendTimeSeries(resFld.getTimeSeries());  
//                getAveResMap().convertTimeSeriestoArray();
                break;
                
            case 1:                     //Calculate projections along the direction of the position vector provided
                                        //normalise for the residence time or number of samples. 
                                                       
                getAveVelFld().getSpace().clear();
                getAveVelFld().getVectors().clear();
                getAveAccFld().getSpace().clear();
                getAveAccFld().getVectors().clear();
                //int dataCounter = 0;
                for(var velFld : this.velocityField){
                    
                    if(!velFld.isProjectionStatus()){
                         velFld.setUseTan2(useTan2Prj);
                         accelarationField[Idx].setUseTan2(useTan2Prj);
                         prjFld = velFld.getProjections2point(Vector,true);                         
                         accFldPrj = accelarationField[Idx].getProjections2point(Vector,true);
                    }else{
                        prjFld = velFld.getProjection();
                        accFldPrj = accelarationField[Idx].getProjection();
                    }                                      
                                       
                    var resMap = this.residenceMaps[Idx++];
                    var norm = covertScaletoNorm(resMap.getPixelArray());
                    var scaledFldvel = (resiNorm)? prjFld.scaleVectors(norm): prjFld;  
                    var scaledAcc =(resiNorm)? accFldPrj.scaleVectors(norm):accFldPrj;
                    
                     if(this.isUseRelativeVelocity()){
                        if(!scaledFldvel.isChkMinMaxandAdd())
                            scaledFldvel.setChkMinMaxandAdd(true);
                        scaledFldvel = scaledFldvel.calibrateVectors(0,Integer.MAX_VALUE);
                        if(!scaledAcc.isChkMinMaxandAdd())
                            scaledAcc.setChkMinMaxandAdd(true);
                        scaledAcc = scaledAcc.calibrateVectors(0, Integer.MAX_VALUE);
                    }
                    
                    
                    getAveVelFld().fillSpace(scaledFldvel.getSpace(),scaledFldvel.getVectors(),false);
                    getAveAccFld().fillSpace(scaledAcc.getSpace(), scaledAcc.getVectors(), false);  
                                       
                    
                    
                }
//                for(var accFld : this.accelarationField){
//                    var accCmp = accFld.getProjections2point(Vector,true);
//                    getAveAccFld().fillSpace(accCmp.getSpace(), accCmp.getVectors(), false); 
//                }
                break;
            case 2:                     //Calculate projections ortogonal to a position vector
                                        
                
                getAveVelFld().getSpace().clear();
                getAveVelFld().getVectors().clear();
                getAveAccFld().getSpace().clear();
                getAveAccFld().getVectors().clear();
                //int dataCounter = 0;
                for(var velFld : this.velocityField){
                    if(!velFld.isProjectionStatus()){
                         velFld.setUseTan2(useTan2Prj);
                         accelarationField[Idx].setUseTan2(useTan2Prj);
                         prjFld = velFld.getProjections2point(Vector,false);
                         accFldPrj = accelarationField[Idx].getProjections2point(Vector,false);
                    }
                    else  {
                        prjFld = velFld.getProjection();
                        accFldPrj = accelarationField[Idx].getProjection();
                    }  
                    var resMap = this.residenceMaps[Idx++];
                    var norm = covertScaletoNorm(resMap.getPixelArray());
                    var scaledFldvel = (resiNorm)? prjFld.scaleVectors(norm): prjFld;  
                    var scaledAcc =(resiNorm)? accFldPrj.scaleVectors(norm):accFldPrj;
                    getAveVelFld().fillSpace(scaledFldvel.getSpace(),scaledFldvel.getVectors(),false);
                    getAveAccFld().fillSpace(scaledAcc.getSpace(), scaledAcc.getVectors(), false);                  
                }               
                break;
            default: //Calculate only the residence map
                aveResMap.getTimeSeries().clear();
                for(var resFld : this.residenceMaps)
                    getAveResMap().appendTimeSeries(resFld.getTimeSeries());  
                break;
        }       
        setAveReady(true);
    }

    private Double[][] covertScaletoNorm(double [][] norm) {
        
        if(norm == null)
            return null;
        Double [][] scale = new Double[norm.length][norm[0].length];
        int xIdx = 0, yIdx = 0;
        for(double[] X : norm){
            for(double Y : X){
                scale[xIdx][yIdx++] = 1/Y;//(Y == 0) ? 0 : 1/Y ;      //pixels that are not sampled are set to zero during normalisation
            }
            xIdx++;
            yIdx = 0;
        }
        return scale;
    }
    public void saveAverage(String prefix, boolean saveResi){
        
        if(!isAveReady())
                return;
        
        var aveVel = new  JVectorCmpImg(getAveVelFld());
        var aveAcc = new  JVectorCmpImg(getAveAccFld());
        
        if(saveResi){
            var aveRes = new  JVectorCmpImg(getXRes(),getYRes(),1);
            aveRes.addScalar(getAveResMap());
            aveRes.saveImages(outPath, prefix+"aveHMap");
        }
        
        aveAcc.saveImages(outPath, prefix+"aveAcc");
        aveVel.saveImages(outPath, prefix+"aveVel");
    }
    
    /**
     * @return the inPath
     */
    public synchronized String getInPath() {
        return inPath;
    }

    /**
     * @param inPath the inPath to set
     */
    public void setInPath(String inPath) {
        this.inPath = inPath;
    }

    /**
     * @return the outPath
     */
    public synchronized String getOutPath() {
        return outPath;
    }

    /**
     * @param outPath the outPath to set
     */
    public void setOutPath(String outPath) {
        this.outPath = outPath;
    }
    /**
     * 
     * @param fName string containing the name of the data file 
     */
    public void addDataFile(String fName){
        this.DataFileNames.add(fName);
        this.newData  = true;
    }
    /**
     * Adds the data file to the manager at a specified location
     * The already existing file is replaced and the file name is returned as string. If the index is larger the 
     * number of files, the file is simply appended returning null. Checking the return value for null tells if the new file
     * is appended or inserted.
     * @param fileNo : Index at which the new file need to be placed (will be used only if the index is within the length of the list
     * @param fName: The filename to be replaced or appended
     * @return : returns the filename of the replaced file
     */
    public String addDataFile(int fileNo, String fName){
        var maxIdx = this.DataFileNames.size()- 1;
        this.newData = true;
        if(fileNo > maxIdx){
            DataFileNames.add(fName);
//            fileCount++;
            return null;
        }else{
            return(DataFileNames.set(fileNo, fName));
        }
       
    }
    public synchronized boolean isDataReadComplete(){
        return ! this.newData;
    }
    
    public synchronized int getFileCount(){
        return this.DataFileNames.size();
    }

    /**
     * @return the residenceMaps
     */
    private JHeatMapArray[] getResidenceMap() {
        if(!newData)
            readData();                                             //Not an efficient way to calculate everything for all
                                                                    //even for a change of single file
        return residenceMaps;
    }
    /**
     * @return the residenceField
     */

    /**
     * @return the pixelAspectRatio
     */
    private double getPixelAspectRatio() {
        return pixelAspectRatio;
    }

    /**
     * @param pixelAspectRatio the pixelAspectRatio to set
     */
    public void setPixelAspectRatio(double pixelAspectRatio) {
        this.pixelAspectRatio = pixelAspectRatio;
    }

    /**
     * @return the scaleforAspectRatio
     */
    public synchronized boolean isScaleforAspectRatio() {
        return scaleforAspectRatio;
    }

    /**
     * @param scaleforAspectRatio the scaleforAspectRatio to set
     */
    public synchronized void setScaleforAspectRatio(boolean scaleforAspectRatio) {
        this.scaleforAspectRatio = scaleforAspectRatio;
    }

    /**
     * @return the useRelativeVelocity
     */
    public synchronized boolean isUseRelativeVelocity() {
        return useRelativeVelocity;
    }

    /**
     * @param useRelativeVelocity the useRelativeVelocity to set
     */
    public synchronized void setUseRelativeVelocity(boolean useRelativeVelocity) {
        this.useRelativeVelocity = useRelativeVelocity;
    }
    
    /**
     * Estimates the occupancy center of the residence map ()and returns a position vector to OC 
     * as JVector. The method used is described in Hippo ref. briefly: i) Apply Gaussian Filter of 
     * a preset radius (usually this should be in pixels corresponding to size of mice/animal). This 
     * is followed by MEM thresholding and then estimating the center of mass. 
     * 
     * @param xRes  x axis resolution
     * @param yRes  y axis resolution
     * @return JVector representing the position vector to the occupancy center defined as above.
     */
    public JVector findOC(int xRes, int yRes) {
        int xOC;
        int yOC;
        DataManager currManager = this;
        if(!this.isVectorFldsReady())
            readData();
        //this.generateResidenceMap(currManager);
        //timeTrace = currManager.getTimeData();
        currManager.computeAve(3, null,false);        //Just compute the residence map
        var heatMap = currManager.getAveResMap();
        heatMap.convertTimeSeriestoArray(xRes, yRes);
        JVectorCmpImg heatMapImg = new JVectorCmpImg(xRes,yRes,1);
        heatMapImg.addScalar(heatMap);
        var AveHMap_imp = heatMapImg.getImages()[0];
        //AveHMap_imp.show();
        var ip = AveHMap_imp.getProcessor().duplicate();
        double sigma = (xRes > yRes) ? yRes/40 : xRes/40 ;
        ip.blurGaussian(sigma);
        ip.setAutoThreshold("MaxEntropy dark");
//        ip.createMask();
        var lThld = ip.getMinThreshold();
        var hThld = ip.getMaxThreshold();
        System.out.println("The thlds are " + lThld + "," + hThld);
        var stat = new FloatStatistics(ip,ImageStatistics.CENTER_OF_MASS+ImageStatistics.LIMIT,null);
        
        xOC = (int) stat.xCenterOfMass;
        yOC = (int) stat.yCenterOfMass;
        //this.ocXjFtTxt2.setText(""+xOC);
        //this.ocYjFtTxt3.setText(""+yOC);
        return new JVector(xOC,yOC);
    }

    @Override
    public void run() {
            this.readData();     
    }

    private void setAveReady(boolean b) {
        AveReady = b;
        
    }

    private boolean isAveReady() {
       return AveReady;
    }
    
    
    
    
  }
