package magiccollege.scene;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.util.GLState;

import magiccollege.Enum.ESceneID;
import magiccollege.manager.ResourceManager;
import magiccollege.manager.SceneManager;

public class MyRecordScene extends MyScene{
	
	ResourceManager rm = ResourceManager.getInstance();
	
	public MyRecordScene(){
		
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
       attachChild(back);
   	   registerTouchArea(back);
		
	}

	@Override
	public void onBackKeyPressed() {
		rm.unloadRecordTexture();
		SceneManager.getInstance().setScene(ESceneID.options);
	}

	@Override
	public ESceneID getSceneID() {
		return ESceneID.myRecord;
	}

	@Override
	public void disposeScene() {
		// TODO Auto-generated method stub
		
	}
	private void createBackground()
	{
		Sprite background = new Sprite(0, 0, rm.record_background_region, rm.vbo)
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
