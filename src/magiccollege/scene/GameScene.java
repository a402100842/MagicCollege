package magiccollege.scene;

import org.andengine.engine.camera.BoundCamera;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.ButtonSprite.OnClickListener;
import org.andengine.entity.sprite.Sprite;

import magiccollege.Enum.ESceneID;
import magiccollege.manager.ResourceManager;
import magiccollege.manager.SceneManager;

public class GameScene extends MyScene{

	private HUD gameHUD;
	private BoundCamera camera;
	private ResourceManager rm;
	
	public GameScene(BoundCamera pCamera){
		camera = pCamera;
		rm = ResourceManager.getInstance();
		gameHUD = null;
	}
	
	private void createHUD(){
		gameHUD = new HUD();
		
		//player头像，宽100，高100
		Sprite portrait = new Sprite(camera.getWidth() - 140, camera.getHeight() - 110, 100, 100 ,rm.player_portrait_region, rm.vbo);
		gameHUD.attachChild(portrait);
		
		//卡组按钮、菜单按钮
		ButtonSprite cardGroupButton = new ButtonSprite(camera.getWidth() - 160 - rm.cardGroup_button_region.getWidth(),
				camera.getHeight() - 10 - rm.cardGroup_button_region.getHeight(), rm.cardGroup_button_region, rm.vbo, new OnClickListener(){
					@Override
					public void onClick(final ButtonSprite pButtonSprite, final float pTouchAreaLocalX, final float pTouchAreaLocalY)
					{
						SceneManager.getInstance().createCardGroupScene(rm.user);
						rm.camera.setHUD(null);
						rm.camera.setChaseEntity(null);
						float[] center = SceneManager.getInstance().getCardGroupScene().getSceneCenterCoordinates();
						rm.camera.setCenter(center[0], center[1]);
					}});
		ButtonSprite settingButton = new ButtonSprite(camera.getWidth() - 180 -  rm.setting_button_region.getWidth() - rm.cardGroup_button_region.getWidth(),
				camera.getHeight() - 10 - rm.setting_button_region.getHeight(), rm.setting_button_region, rm.vbo, new OnClickListener(){
					@Override
					public void onClick(final ButtonSprite pButtonSprite, final float pTouchAreaLocalX, final float pTouchAreaLocalY)
					{
						rm.reloadOptionsTextures();
						rm.camera.setHUD(null);
						rm.camera.setChaseEntity(null);
						float[] center = SceneManager.getInstance().getOptionsScene().getSceneCenterCoordinates();
						rm.camera.setCenter(center[0], center[1]);
						SceneManager.getInstance().setScene(ESceneID.options);
					}});
		//添加卡组按钮、菜单按钮
		gameHUD.attachChild(settingButton);
		gameHUD.registerTouchArea(settingButton);
		gameHUD.attachChild(cardGroupButton);
		gameHUD.registerTouchArea(cardGroupButton);
		camera.setHUD(gameHUD);
	}
	
	public HUD getHUD(){
		return gameHUD;
	}
	
	@Override
	public void createScene() {
		createHUD();
	}

	@Override
	public void onBackKeyPressed() {
		SceneManager.getInstance().loadMenuScene();
	}

	@Override
	public ESceneID getSceneID() {
		return ESceneID.game;
	}

	@Override
	public void disposeScene() {
		gameHUD.detachChildren();
		gameHUD.clearChildScene();
		gameHUD.clearTouchAreas();
		gameHUD.clearUpdateHandlers();
		detachChildren();
	    clearChildScene();
	    clearUpdateHandlers();
	}

}
