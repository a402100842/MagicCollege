package magiccollege.scene;

import java.util.ArrayList;

import magiccollege.Enum.ESceneID;
import magiccollege.manager.ResourceManager;
import magiccollege.manager.SceneManager;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.input.touch.detector.ScrollDetector;
import org.andengine.input.touch.detector.SurfaceScrollDetector;
import org.andengine.input.touch.detector.ScrollDetector.IScrollDetectorListener;
import org.andengine.opengl.util.GLState;

public class HelpInfoScene extends MyScene implements IOnSceneTouchListener,
IScrollDetectorListener {
	
	ResourceManager rm = ResourceManager.getInstance();
	
	private SurfaceScrollDetector mScrollDetector;
	
	ArrayList<Text> textarray = new  ArrayList<Text>();
	
	
	Text txt;

	public HelpInfoScene(Text txt1){
		
		txt = new Text(72,0,txt1.getFont(),txt1.getText().toString(),rm.vbo);
	}
	
	@Override
	public void createScene() {
		// TODO Auto-generated method stub
		createBackground();
		Sprite back = new Sprite(690,365,rm.options_back_region,rm.vbo){
			@Override
		   public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				
				if(pSceneTouchEvent.isActionDown()){
					onBackKeyPressed();
					return true;
				}
				 return false;
       }};
       
       textarray.add(txt);
       
       attachChild(back);
       attachChild(txt);
       
       
       registerTouchArea(back);
       registerTouchArea(txt);
		
	   enableTouchDetectors();
	   setTouchAreaBindingOnActionDownEnabled(true);
	}

	@Override
	public void onBackKeyPressed() {
		SceneManager.getInstance().setScene(ESceneID.help);
		this.dispose();
	}

	@Override
	public ESceneID getSceneID() {
		return ESceneID.helpInfo;
	}

	@Override
	public void disposeScene() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onScroll(final ScrollDetector pScollDetector, final int pPointerID,
		    final float pDistanceX, final float pDistanceY) {
		    
		    	if(pDistanceY>0){
		    		for(int i = 0; i < textarray.size(); i++){
		    			textarray.get(i).setPosition(textarray.get(i).getX(),textarray.get(i).getY()+20);
		    			comeback();
		    		}
		    	}else{
		    		for(int i = 0; i < textarray.size(); i++){
		    			textarray.get(i).setPosition(textarray.get(i).getX(),textarray.get(i).getY()-20);
		    			comeback();
		    		}
		    		
		    	}
		    	
	}
	
	public void comeback(){
				
		if(textarray.get(0).getY()<(480-textarray.get(0).getHeight())){
			System.out.println("(textarray.get(0).getY()1:"+textarray.get(0).getY());
			textarray.get(0).setPosition(textarray.get(0).getX(),(480-textarray.get(0).getHeight()));
		}
		if(textarray.get(0).getY()>0){
			System.out.println("(textarray.get(0).getY()2:"+textarray.get(0).getY());
			textarray.get(0).setPosition(textarray.get(0).getX(),0);
		}
		
	}

	@Override
	public void onScrollFinished(ScrollDetector arg0, int arg1, float arg2,
			float arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onScrollStarted(ScrollDetector arg0, int arg1, float arg2,
			float arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onSceneTouchEvent(final Scene pScene, final TouchEvent pSceneTouchEvent) {
		
	        if (pSceneTouchEvent.isActionDown()) {
	            this.mScrollDetector.setEnabled(true);
	        }
	        this.mScrollDetector.onTouchEvent(pSceneTouchEvent);

	    return true;
	}

	private void enableTouchDetectors() {
	    this.mScrollDetector = new SurfaceScrollDetector(this);
	  
	    setOnSceneTouchListener(this);
	    setTouchAreaBindingOnActionDownEnabled(true);
	}
	private void createBackground()
	{
		Sprite background = new Sprite(0, 0, rm.helpInfo_region, rm.vbo)
	    {
	        @Override
	        protected void preDraw(GLState pGLState, Camera pCamera) 
	        {
	            super.preDraw(pGLState, pCamera);
	            pGLState.enableDither();
	        }
	    };
	    this.attachChild(background);
	}

}
