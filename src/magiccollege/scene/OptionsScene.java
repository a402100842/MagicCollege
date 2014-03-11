package magiccollege.scene;

import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;

import magiccollege.Enum.ESceneID;
import magiccollege.manager.ResourceManager;
import magiccollege.manager.SceneManager;

public class OptionsScene extends MyScene{
	
	ResourceManager rm = ResourceManager.getInstance();
	SceneManager sm = SceneManager.getInstance();
	MyScene backScene;
	
	public OptionsScene(MyScene ms){
		backScene = ms;
	}
	
	public void setBackScene(MyScene bs){
		backScene = bs;
	}

	@Override
	public void createScene() {
		
		// TODO Auto-generated method stub
    Sprite background = new Sprite(0,0,rm.options_background_region,rm.vbo);
		
		Sprite setting = new Sprite(200,100,rm.options_setting_region,rm.vbo){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				
				if(pSceneTouchEvent.isActionUp()){
					rm.reloadSettingTexture();
					sm.setScene(ESceneID.setting);
					return true;
				}
				 return false;
			}};
		Sprite allcard = new Sprite(200,200,rm.options_allcard_region,rm.vbo){
				@Override
				public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
					
					if(pSceneTouchEvent.isActionUp()){
						rm.reloadAllCardTexture();
						sm.setScene(ESceneID.allCard);
						return true;
					}
					 return false;
				}};
		Sprite help = new Sprite(200,300,rm.options_help_region,rm.vbo){
					@Override
					public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
						
						if(pSceneTouchEvent.isActionUp()){
							rm.reloadHelpTexture();
							sm.setScene(ESceneID.help);
							return true;
						}
						 return false;
					}};
		Sprite exit = new Sprite(690,65,rm.options_exit_region,rm.vbo){
						@Override
						public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
							
							if(pSceneTouchEvent.isActionUp()){
								System.exit(0);
								return true;
							}
							 return false;
						}};
	   Sprite person = new Sprite(690,215,rm.options_person_region,rm.vbo){
							@Override
							public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
								
								if(pSceneTouchEvent.isActionUp()){
									rm.reloadRecordTexture();
									sm.setScene(ESceneID.myRecord);
									return true;
								}
								 return false;
							}};
	 Sprite back = new Sprite(690,365,rm.options_back_region,rm.vbo){
								@Override
								public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
									
									if(pSceneTouchEvent.isActionDown()){
										
										try {
											Thread.sleep(500);
										} catch (InterruptedException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
										onBackKeyPressed();
										return true;
									}
									 return false;
								}};
		    
		attachChild(background);
        attachChild(setting);
        attachChild(allcard);
        attachChild(help);
        attachChild(exit);
        attachChild(person);
        attachChild(back);
		
		
		registerTouchArea(setting);
		
		registerTouchArea(allcard);
		
		registerTouchArea(help);
		
		registerTouchArea(exit);
		
        registerTouchArea(person);
		
		registerTouchArea(back);
		
	}

	@Override
	public void onBackKeyPressed() {
		rm.unloadOptionsTextures();
		if (backScene instanceof GameScene){
			rm.camera.setHUD(sm.getGameScene().getHUD());
			rm.camera.setChaseEntity(rm.player);
		}
		SceneManager.getInstance().setScene(backScene);
	}	

	@Override
	public ESceneID getSceneID() {
		return ESceneID.options;
	}

	@Override
	public void disposeScene() {
		// TODO Auto-generated method stub
		
	}

}
