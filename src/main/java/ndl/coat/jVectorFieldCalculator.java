
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
import ndl.ndllib.ImageDifferentials;
import ndl.ndllib.JVectorCmpImg;
import ndl.ndllib.JVectorSpace;
import ndl.ndllib.SurfaceFit;

/**
 *
 * @author balaji
 */
public class jVectorFieldCalculator implements Runnable{

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
     * @return the fldrName
     */
    public String getFldrName() {
        return fldrName;
    }

    /**
     * @param fldrName the fldrName to set
     */
    public void setFldrName(String fldrName) {
        this.fldrName = fldrName;
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
        return fileSeparator;
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
    public Roi getSampledGrpRoi() {
        return sampledGrpRoi;
    }

    /**
     * @param sampledGrpRoi the sampledGrpRoi to set
     */
    public void setSampledGrpRoi(Roi sampledGrpRoi) {
        this.sampledGrpRoi = sampledGrpRoi;
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
    private String fldrName;
    private String pathName;
    private String fileSeparator;
    private SurfaceFit fit;
    private JVectorSpace VecFld;
    private Roi sampledGrpRoi;
    private String suffix;
    private int xPoolCtrjFormFld;
    private int yPoolCtrjFormFld;
    private int poolRadjFormFld;
    private boolean genConv;
    private boolean genDiv;
    private boolean autoGenPool;
    private static int instanceCount = 1;
    @Override
    public void run() {
            instanceCount++;
            int polyXOrder = getPolyX();//5;
            int polyYOrder = getPolyY();
            setFldrName(getPathName() + getFileSeparator());//File.separator;
        
        
        
        //System.out.printf("Polynomial Order in (x,y) format:(%d,%d)",polyXOrder,polyYOrder);
       
//        fit.setPreScale(this.reSzImgjChkBx.isSelected());
//        fit.setScaleBy(Double.parseDouble(this.scalingfactorJFormFld.getText()));
//        
//        fit.setGaussFilt(this.gaussjChkBx.isSelected());
//        fit.setGaussRad(Double.parseDouble(this.gauRadjFormFld.getText()));
//        
//        fit.setSelectPixels(this.res2SeljChkBx.isSelected());
//        fit.setUseSelection(this.useSeljChBx.isSelected());
        
/* Make sure to pass a preset fit object */
        ImagePlus[] vecSurface = getSurfaces(polyXOrder,polyYOrder, getVecFld(), getSampledGrpRoi());
        int count  = 0;
        for(ImagePlus imp : vecSurface){
            FileSaver fs  = new FileSaver(imp);
            fs.saveAsTiff(getPathName() +"Ave_VectorSurface"+"Comp_#"+count++);
        }

    ImageStack diffVel =  new ImageStack(getVecFld().getxRes(),getVecFld().getyRes(),2);
    
  /**
   * Check for the sampledGrpRoi when auto determine OC is not checked. 
   * This throws a null pointer when it is not checked as there is no sampledGrpRoi in that case.
   */  
   int x = 0 ,y = 0;
   if(  getSampledGrpRoi() != null){
    x =     getSampledGrpRoi().getBounds().x;
    y =     getSampledGrpRoi().getBounds().y;
   }
    FloatProcessor vecxS, vecyS;
    vecxS = new FloatProcessor(getVecFld().getxRes(),getVecFld().getyRes());
    vecyS = new FloatProcessor(getVecFld().getxRes(),getVecFld().getyRes());
   
    vecSurface[0].setRoi(getSampledGrpRoi());
    vecSurface[1].setRoi(getSampledGrpRoi());
    
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
    fs.saveAsTiff(getFldrName()+getSuffix()+"Divergence_diffVel");
    var velProj = new FileSaver(velProjections);
    velProj.saveAsTiff(getFldrName()+getSuffix()+"Convergence_vel");
    
  //  fit.setPreScale(false);
  //  fit.setGaussFilt(false);
    if(isGenConv() || isGenDiv()){
            ImagePlus finalVelImg = GenerateConvergenceImages(velProjections.getProcessor(), getSampledGrpRoi());
            fs = new FileSaver(finalVelImg);
            fs.saveAsTiff(getFldrName()+getSuffix()+"forPres");

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
                fs.saveAsTiff(getFldrName()+getSuffix()+"ConvPres");

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
                fs.saveAsTiff(getFldrName()+getSuffix()+"DivPres");

            }
      }
      synchronized (jVectorFieldCalculator.finished){
          instanceCount--;
          //if(instanceCount == 0)
          jVectorFieldCalculator.getFinished().notify();
      }
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
    private ImagePlus GenerateConvergenceImages(ImageProcessor converImg, Roi sampledGrpRoi) {

            converImg.setRoi(sampledGrpRoi);
           
//            int polyXOrder  = this.x_polyOrderJCmbBx.getSelectedIndex();
//            int polyYOrder  = this.y_polyOrderJCmbBx.getSelectedIndex();
            
            
//            getFit().setPolyOrderX(getPolyX()-1);
//            getFit().setPolyOrderY(getPolyY()-1);
            getFit().setPreScale(false);
            getFit().setGaussFilt(false);
            if(sampledGrpRoi != null ){
                getFit().setUseSelection(true);
                getFit().setSelectPixels(true);
            }
            
            
            ImagePlus surfaceOut = this.getSurface(getPolyX()-1, getPolyY()-1, converImg, sampledGrpRoi);
           
            OvalRoi Pool;

   
            if(this.isAuotGenPool()){                                   //Check for pool roi or parameters
                Rectangle rect;
                if(sampledGrpRoi != null){
                    rect = sampledGrpRoi.getBounds();
                    
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
    public static Object getFinished() {
        return finished;
    }
}
