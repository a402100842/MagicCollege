package magiccollege.scene;

import magiccollege.Enum.ESceneID;
import magiccollege.manager.ResourceManager;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.util.GLState;

public class SplashScene extends MyScene{
	private Sprite splash;
	
	@Override
	public void createScene() {
		splash = new Sprite(0, 0, ResourceManager.getInstance().splash_region, ResourceManager.getInstance().vbo)
		{
		    @Override
		    protected void preDraw(GLState pGLState, Camera pCamera) 
		    {
		       super.preDraw(pGLState, pCamera);
		       pGLState.enableDither();
		    }
		};
		        
		splash.setScale(1.5f);
		splash.setPosition((ResourceManager.getInstance().camera.getWidth() - splash.getWidth())/2,
				(ResourceManager.getInstance().camera.getHeight() - splash.getHeight())/2);
		attachChild(splash);
	}

	@Override
	public void onBackKeyPressed() {
		//do nothing
	}

	@Override
	public ESceneID getSceneID() {
		return ESceneID.splash;
	}

	@Override
	public void disposeScene() {
		splash.detachSelf();
	    splash.dispose();
	    this.detachSelf();
	    this.dispose();
	}

}
