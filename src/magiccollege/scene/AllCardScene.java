package magiccollege.scene;

import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;

import magiccollege.Enum.ESceneID;
import magiccollege.manager.ResourceManager;
import magiccollege.manager.SceneManager;

public class AllCardScene extends MyScene{
	
	ResourceManager rm = ResourceManager.getInstance();
	SceneManager sm = SceneManager.getInstance();

	@Override
	public void createScene() {
		// TODO Auto-generated method stub
		
		Sprite backGround = new Sprite(0, 0, rm.allcard_background_region, rm.vbo);
		
		Sprite back = new Sprite(690,365,rm.options_back_region,rm.vbo){
				@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
					
					if(pSceneTouchEvent.isActionDown()){
						onBackKeyPressed();
						return true;
					}
					 return false;
		 }};
		attachChild(backGround);
		attachChild(back);
		
		registerTouchArea(back);
	}

	@Override
	public void onBackKeyPressed() {
		rm.unloadAllCardTexture();
		sm.setScene(ESceneID.options);
		
	}

	@Override
	public ESceneID getSceneID() {
		return ESceneID.allCard;
	}

	@Override
	public void disposeScene() {
		// TODO Auto-generated method stub
		
	}

}
