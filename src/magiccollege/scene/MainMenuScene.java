package magiccollege.scene;

import magiccollege.Enum.ESceneID;
import magiccollege.manager.ResourceManager;
import magiccollege.manager.SceneManager;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.util.GLState;

public class MainMenuScene extends MyScene implements IOnMenuItemClickListener{

	private MenuScene menuChildScene;
	private Sprite backGround;
	private final int MENU_PLAY = 0;
	private final int MENU_OPTIONS = 1;

	@Override
	public void createScene() {
		createBackground();
		createMenuChildScene();
	}

	@Override
	public void onBackKeyPressed() {
		if (ResourceManager.getInstance().serverConnector != null)
			ResourceManager.getInstance().serverConnector.terminate();
		System.exit(0);
	}

	@Override
	public ESceneID getSceneID() {
		return ESceneID.menu;
	}

	@Override
	public void disposeScene() {
		// TODO Auto-generated method stub
		menuChildScene.back();
		menuChildScene.dispose();
		backGround.detachSelf();
		backGround.dispose();
	}
	private void createMenuChildScene()
	{
	    menuChildScene = new MenuScene(ResourceManager.getInstance().camera);
	    menuChildScene.setPosition(0, 0);
	    
	    final IMenuItem playMenuItem = new ScaleMenuItemDecorator(
	    		new SpriteMenuItem(MENU_PLAY, ResourceManager.getInstance().menu_play_region, 
	    				ResourceManager.getInstance().vbo), 1.2f, 1);
	    final IMenuItem optionsMenuItem = new ScaleMenuItemDecorator(
	    		new SpriteMenuItem(MENU_OPTIONS, ResourceManager.getInstance().menu_options_region, 
	    				ResourceManager.getInstance().vbo), 1.2f, 1);
	    
	    menuChildScene.addMenuItem(playMenuItem);
	    menuChildScene.addMenuItem(optionsMenuItem);
	    
	    menuChildScene.buildAnimations();
	    menuChildScene.setBackgroundEnabled(false);
	    
	    playMenuItem.setPosition(playMenuItem.getX(), playMenuItem.getY() - 10);
	    optionsMenuItem.setPosition(optionsMenuItem.getX(), optionsMenuItem.getY() + 10);
	    
	    menuChildScene.setOnMenuItemClickListener(this);
	    
	    this.setChildScene(menuChildScene);
	}
	private void createBackground()
	{
		backGround = new Sprite(0, 0, ResourceManager.getInstance().menu_background_region, ResourceManager.getInstance().vbo)
	    {
	        @Override
	        protected void preDraw(GLState pGLState, Camera pCamera) 
	        {
	            super.preDraw(pGLState, pCamera);
	            pGLState.enableDither();
	        }
	    };
	    this.attachChild(backGround);
	}

	@Override
	public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem,
			float pMenuItemLocalX, float pMenuItemLocalY) {
		
		switch(pMenuItem.getID()){
        case MENU_PLAY:
        	//TODO
        	SceneManager.getInstance().createGameScene();
        	
        	//SceneManager.getInstance().createFightingScene(ResourceManager.getInstance().user,ResourceManager.getInstance().user2);
            return true;
        case MENU_OPTIONS:
        		ResourceManager.getInstance().reloadOptionsTextures();
        		SceneManager.getInstance().setScene(ESceneID.options);
            return true;
        default:
            return false;
		}
	}
}
