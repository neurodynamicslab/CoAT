
package ndl.coat;

import ij.ImagePlus;
import ij.ImageStack;
import ij.gui.OvalRoi;
import ij.gui.Roi;
import ij.io.FileSaver;
import ij.plugin.ZProjector;
import ij.process.FloatBlitter;
import ij.process.FloatProcessor;
import ij.process.ImageProcessor;
import java.awt.Rectangle;
import java.io.File;
import ndl.ndllib.ImageDifferentials;
import ndl.ndllib.JVectorCmpImg;
import ndl.ndllib.JVectorSpace;
import ndl.ndllib.Natural_NeighInter;
import ndl.ndllib.SurfaceFit;

/**
 *
 * @author balaji
 */
public class jVectorFieldCalculator implements Runnable{

    /**
     * @return the useNNI
     */
    public boolean isUseNNI() {
        return useNNI;
    }

    /**
     * @param useNNI the useNNI to set
     */
    public void setUseNNI(boolean useNNI) {
        this.useNNI = useNNI;
    }

    private boolean useNNI = true;

    /**
     * @return the toSave
     */
    public boolean isToSave() {
        return toSave;
    }

    /**
     * @param toSave the toSave to set
     */
    public void setToSave(boolean toSave) {
        this.toSave = toSave;
    }

    private static final Object finished = new Object();
    
    public jVectorFieldCalculator(){
        
    }

    /**
     * @return the polyX
     */
    public int getPolyX() {
        return polyX;
    }

    /**
     * @param polyX the polyX to set
     */
    public void setPolyX(int polyX) {
        this.polyX = polyX;
    }

    /**
     * @return the polyY
     */
    public int getPolyY() {
        return polyY;
    }

    /**
     * @param polyY the polyY to set
     */
    public void setPolyY(int polyY) {
        this.polyY = polyY;
    }

    /**
     * fldrName is the place holder for pathname with file separator
     * @return the fldrName
     */
    private String getFldrName() {
        return getPathName()+getFileSeparator();
    }


    /**
     * @return the pathName
     */
    public String getPathName() {
        return pathName;
    }

    /**
     * @param pathName the pathName to set
     */
    public void setPathName(String pathName) {
        this.pathName = pathName;
    }

    /**
     * @return the fileSeparator
     */
    public String getFileSeparator() {
        if(fileSeparator != null)
            return fileSeparator;
        else
            return File.pathSeparator;
        
    }

    /**
     * @param fileSeparator the fileSeparator to set
     */
    public void setFileSeparator(String fileSeparator) {
        this.fileSeparator = fileSeparator;
    }

    /**
     * @return the fit
     */
    public SurfaceFit getFit() {
        return fit;
    }

    /**
     * @param fit the fit to set
     */
    public void setFit(SurfaceFit fit) {
        this.fit = new SurfaceFit(fit);
    }

    /**
     * @return the VecFld
     */
    public JVectorSpace getVecFld() {
        return VecFld;
    }

    /**
     * @param VecFld the VecFld to set
     */
    public void setVecFld(JVectorSpace VecFld) {
        this.VecFld = VecFld;
    }

    /**
     * @return the sampledGrpRoi
     */
    public Roi getSampledRoi() {
        return sampledRoi;
    }

    /**
     * @param sampledGrpRoi the sampledGrpRoi to set
     */
    public void setSampledRoi(Roi sampledRoi) {
        this.sampledRoi = sampledRoi;
    }

    /**
     * @return the suffix
     */
    public String getSuffix() {
        return suffix;
    }

    /**
     * @param suffix the suffix to set
     */
    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    /**
     * @return the xPoolCtrjFormFld
     */
    public int getxPoolCtrjFormFld() {
        return xPoolCtrjFormFld;
    }

    /**
     * @param xPoolCtrjFormFld the xPoolCtrjFormFld to set
     */
    public void setxPoolCtrjFormFld(int xPoolCtrjFormFld) {
        this.xPoolCtrjFormFld = xPoolCtrjFormFld;
    }

    /**
     * @return the yPoolCtrjFormFld
     */
    public int getyPoolCtrjFormFld() {
        return yPoolCtrjFormFld;
    }

    /**
     * @param yPoolCtrjFormFld the yPoolCtrjFormFld to set
     */
    public void setyPoolCtrjFormFld(int yPoolCtrjFormFld) {
        this.yPoolCtrjFormFld = yPoolCtrjFormFld;
    }

    /**
     * @return the poolRadjFormFld
     */
    public int getPoolRadjFormFld() {
        return poolRadjFormFld;
    }

    /**
     * @param poolRadjFormFld the poolRadjFormFld to set
     */
    public void setPoolRadjFormFld(int poolRadjFormFld) {
        this.poolRadjFormFld = poolRadjFormFld;
    }

    /**
     * @return the genConv
     */
    public boolean isGenConv() {
        return genConv;
    }

    /**
     * @param genCov the genConv to set
     */
    public void setGenConv(boolean genCov) {
        this.genConv = genCov;
    }

    /**
     * @param genDiv the genDiv to set
     */
    public void setGenDiv(boolean genDiv) {
        this.genDiv = genDiv;
    }

    /**
     * @return the autoGenPool
     */
    public boolean isAutoGenPool() {
        return autoGenPool;
    }

    /**
     * @param autoGenPool the autoGenPool to set
     */
    public void setAutoGenPool(boolean autoGenPool) {
        this.autoGenPool = autoGenPool;
    }
    private int polyX;
    private int polyY;
    private String pathName;
    private String fileSeparator;
    private SurfaceFit fit;
    private JVectorSpace VecFld;
    private Roi sampledRoi;
    private String suffix;
    private int xPoolCtrjFormFld;
    private int yPoolCtrjFormFld;
    private int poolRadjFormFld;
    private boolean genConv;
    private boolean genDiv;
    private boolean autoGenPool;
    private static int instanceCount = 1;
    private boolean toSave = true;
    private int FilterType = 1; // 3 = no filter, 2 = Median and 1 = Gauss
    private float FilterRadius = 1;
    private boolean Normalise = true;
    @Override
    public void run() {
            instanceCount++;
            int polyXOrder = getPolyX();//5;
            int polyYOrder = getPolyY();        
/* Make sure to pass a preset fit object */
        ImagePlus[] vecSurface = getSurfaces(polyXOrder,polyYOrder, getVecFld(), getSampledRoi());
        int count  = 0;
        if(isToSave()){
            for(ImagePlus imp : vecSurface){
                FileSaver fs  = new FileSaver(imp);
                fs.saveAsTiff(getFldrName()+getSuffix() +"_VectorSurface"+"Comp_#"+count++);
            }
        }

    ImageStack diffVel =  new ImageStack(getVecFld().getxRes(),getVecFld().getyRes(),2);
    
  /**
   * Check for the sampledGrpRoi when auto determine OC is not checked. 
   * This throws a null pointer when it is not checked as there is no sampledGrpRoi in that case.
   */  
   int x = 0 ,y = 0;
   if(  getSampledRoi() != null){
        x =   getSampledRoi().getBounds().x;
        y =   getSampledRoi().getBounds().y;
   }
    FloatProcessor vecxS, vecyS;
    vecxS = new FloatProcessor(getVecFld().getxRes(),getVecFld().getyRes());
    vecyS = new FloatProcessor(getVecFld().getxRes(),getVecFld().getyRes());
   
    vecSurface[0].setRoi(getSampledRoi());
    vecSurface[1].setRoi(getSampledRoi());
    
    vecxS.insert(this.getDifferentials(vecSurface[0].crop(), false).getProcessor(),x,y);
    vecyS.insert(this.getDifferentials(vecSurface[1].crop(), true).getProcessor(),x,y);
    

    diffVel.setProcessor(vecxS, 1);
    diffVel.setProcessor(vecyS, 2);
    
    ImagePlus Projections = new ImagePlus();
    Projections.setStack(diffVel);
    ZProjector projector = new ZProjector();
    projector.setMethod(ZProjector.SUM_METHOD);
    projector.setImage(Projections);
    projector.doProjection();
    ImagePlus velProjections = projector.getProjection();

   
    var img = new ImagePlus("VelCon");
    img.setStack(diffVel);
    var fs = new FileSaver(img);
    fs.saveAsTiff(getFldrName()+getSuffix()+"_Divergence_diffVel");
    var velProj = new FileSaver(velProjections);
    velProj.saveAsTiff(getFldrName()+getSuffix()+"_Convergence_vel");
    
  //  fit.setPreScale(false);
  //  fit.setGaussFilt(false);
    if(isGenConv() || isGenDiv()){
            ImagePlus finalVelImg = GenerateConvergenceImages(velProjections.getProcessor(), getSampledRoi());
           // fs = new FileSaver(finalVelImg);
           // fs.saveAsTiff(getFldrName()+getSuffix()+"forPres");

            float LThld, HThld;
            if(this.isGenConv()){
                LThld = Float.NEGATIVE_INFINITY;
                HThld = 0;
                ImageProcessor ConvIP = finalVelImg.getProcessor().duplicate();

                ConvIP.setThreshold(LThld, HThld);
                var mask = ConvIP.createMask();
                mask.add(-254);

                FloatBlitter fb = new FloatBlitter((FloatProcessor)ConvIP);
                fb.copyBits(mask, 0, 0, FloatBlitter.MULTIPLY);
                ConvIP.multiply(-1);

                var Img  = new ImagePlus("Conv");
                Img.setProcessor(ConvIP);
                fs = new FileSaver(Img);
                fs.saveAsTiff(getFldrName()+getSuffix()+"_ConvPres");

            }
            if(this.isGenDiv()){
                LThld = 0;
                HThld = Float.POSITIVE_INFINITY;
                ImageProcessor DivIP = finalVelImg.getProcessor().duplicate();

                DivIP.setThreshold(LThld, HThld);
                var mask = DivIP.createMask();
                mask.add(-254);

                FloatBlitter fb = new FloatBlitter((FloatProcessor)DivIP);
                fb.copyBits(mask, 0, 0, FloatBlitter.MULTIPLY);

                var Img  = new ImagePlus("Div");
                Img.setProcessor(DivIP);
                fs = new FileSaver(Img);
                fs.saveAsTiff(getFldrName()+getSuffix()+"_DivPres");
            }
      }
      synchronized (jVectorFieldCalculator.finished){
          instanceCount--;
          //if(instanceCount == 0)
          jVectorFieldCalculator.getFinishedStatus().notify();
      }
    }
    private ImagePlus[] getSurfaces(int polyX, int polyY, JVectorSpace space, Roi sel){
        int nCmp = space.getnComp();
        ImagePlus[] surfaces = new ImagePlus[nCmp];
        JVectorCmpImg images = new JVectorCmpImg(space);
        ImageProcessor[] cmpImages = images.getProcessorArray();
        int count = 0;
        System.out.println("Sel " + (sel == null));
        for(ImageProcessor ip : cmpImages){
                //var ByIp = ip.convertToByteProcessor();
                //ip.setRoi(sel);
                
                surfaces[count++] = (isUseNNI())? getNNISurface(ip, sel):
                                            getSurface(polyX,polyY,ip,sel);
        }
       
       return surfaces;
    }
    private ImagePlus getNNISurface(ImageProcessor ip, Roi selection){
        ImagePlus NNIin = new ImagePlus();
        ImagePlus NNISurf;
        
        NNIin.setProcessor(ip);
        
        Natural_NeighInter NNI = new Natural_NeighInter(NNIin,selection);
        //System.out.println(suffix);
       // NNI.setRoi(selection);
        
        //NNI.setMask((ByteProcessor) selection.getMask());
        
        NNI.setFILTER(getFilterType());
        NNI.setNormalise(isNormalise());
        NNI.setBlurRad(getFilterRadius());
        NNI.setPath(getFldrName()+getSuffix());
        NNI.initialize();
        NNISurf = NNI.getSurface();
        
        return NNISurf;
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
        //SurfaceFit fit  = new SurfaceFit();
        getFit().setPolyOrderX(polyXOrder);
        getFit().setPolyOrderY(polyYOrder);
        FloatProcessor selInFrame = getFit().FitSurface(cmpIP,selection); //null, false square/rectangle region of interest as such 
                                                                      // sel, false square/rectangle region of interest with 0 for pixels of unmasked
                                                                      //sel, true just the pixels that are selected by roi mask
                                                                      
//       System.out.println("The dimension after fitting is (X x Y) "+selInFrame.getWidth()+" x "+selInFrame.getHeight());
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
    private ImagePlus GenerateConvergenceImages(ImageProcessor converImg, Roi sampledRoi) {

            converImg.setRoi(sampledRoi);
           
//            int polyXOrder  = this.x_polyOrderJCmbBx.getSelectedIndex();
//            int polyYOrder  = this.y_polyOrderJCmbBx.getSelectedIndex();
            
            
//            getFit().setPolyOrderX(getPolyX()-1);
//            getFit().setPolyOrderY(getPolyY()-1);
            getFit().setPreScale(false);
            getFit().setGaussFilt(false);
            if(sampledRoi != null ){
                getFit().setUseSelection(true);
                getFit().setSelectPixels(true);
            }
            ImagePlus surfaceOut;
            if(true/*intrapolate by poly*/){
                surfaceOut = (this.isUseNNI())?this.getNNISurface(converImg,  sampledRoi):this.getSurface(getPolyX()-1, getPolyY()-1, converImg, sampledRoi);
            }else{
                surfaceOut = new ImagePlus();
                //converImg.setRoi(sampledRoi);
                converImg.setBackgroundValue(0);
                converImg.fillOutside(sampledRoi);
               
               // converImg.blurGaussian(20);
                surfaceOut.setProcessor(converImg);                
            }
           
            OvalRoi Pool;

   
            if(this.isAuotGenPool()){                                   //Check for pool roi or parameters
                Rectangle rect;
                if(sampledRoi != null){
                    rect = sampledRoi.getBounds();
                    
                }else{
                    rect = surfaceOut.getRoi().getBounds();
                }
                Pool = new OvalRoi(rect.x,rect.y,rect.width,rect.height);
            }else{
                int xCtr = this.getxPoolCtrjFormFld();
                int yCtr = this.getyPoolCtrjFormFld();
                int dia = 2 * this.getPoolRadjFormFld();
                Pool = new OvalRoi(xCtr,yCtr,dia,dia);
            }
            surfaceOut.getProcessor().setValue(0);
            surfaceOut.getProcessor().fillOutside(Pool);

        return surfaceOut;
    }
    private boolean isGenDiv() {
        return genDiv;
    }

    private boolean isAuotGenPool() {
        return isAutoGenPool();
    }

    /**
     * @return the instanceCount
     */
    public static int getInstanceCount() {
        return instanceCount;
    }

    /**
     * @return the finished
     */
    public static Object getFinishedStatus() {
        return finished;
    }

    /**
     * @return the FilterType
     */
    public int getFilterType() {
        return FilterType;
    }

    /**
     * @param FilterType the FilterType to set
     */
    public void setFilterType(int FilterType) {
        this.FilterType = FilterType;
    }

    /**
     * @return the FilterRadius
     */
    public float getFilterRadius() {
        return FilterRadius;
    }

    /**
     * @param FilterRadius the FilterRadius to set
     */
    public void setFilterRadius(float FilterRadius) {
        this.FilterRadius = FilterRadius;
    }

    /**
     * @return the Normalise
     */
    public boolean isNormalise() {
        return Normalise;
    }

    /**
     * @param Normalise the Normalise to set
     */
    public void setNormalise(boolean Normalise) {
        this.Normalise = Normalise;
    }
}
