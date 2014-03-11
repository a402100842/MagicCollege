package magiccollege.scene;

import magiccollege.Enum.ESceneID;
import org.andengine.entity.scene.Scene;

public abstract class MyScene extends Scene{
	/*
	protected DisplayMetrics mDisplayMetrics;
	
	protected MyScene(){
		ResourceManager.getInstance().activity.getWindowManager().getDefaultDisplay().getMetrics(mDisplayMetrics);
	}
	
	public float getDisplayHeight() {
        return mDisplayMetrics.heightPixels * mDisplayMetrics.scaledDensity;
    }

    public float getDisplayWidth() {
        return mDisplayMetrics.widthPixels * mDisplayMetrics.scaledDensity;
    }
    */
	public abstract void createScene();
	
	public abstract void onBackKeyPressed();
	
	public abstract ESceneID getSceneID();

	public abstract void disposeScene();
}
