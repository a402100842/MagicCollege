package magiccollege.scene;

import magiccollege.Enum.ESceneID;
import magiccollege.manager.ResourceManager;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.text.Text;
import org.andengine.util.color.Color;


public class LoadingScene extends MyScene{
	final Text text;
	public LoadingScene(){
		super();
		//创建显示loading的文本 
		text = new Text(0,0,ResourceManager.getInstance().font, "Loading...", ResourceManager.getInstance().vbo);
		this.setAlpha(.5f);
	}
	@Override
	public void createScene() {
		//设置白色背景
		this.setBackground(new Background(Color.WHITE));
		//添加文本
		this.attachChild(text);
		//调整文本位置
		text.setPosition((ResourceManager.getInstance().camera.getWidth() - text.getWidth())/2,
				(ResourceManager.getInstance().camera.getHeight() - text.getHeight())/2);
		//注册更新监听器，使loading...有动态效果
		this.registerUpdateHandler(new TimerHandler(0.4f, true, new ITimerCallback(){
			@Override
			public void onTimePassed(TimerHandler arg0) {
				if(text.getText().equals("Loading."))
					text.setText("Loading..");
				else if(text.getText().equals("Loading.."))
					text.setText("Loading...");
				else if(text.getText().equals("Loading..."))
					text.setText("Loading.");
			}}));
	}

	@Override
	public void onBackKeyPressed() {
		return;//不做任何事
	}

	@Override
	public ESceneID getSceneID() {
		return ESceneID.loading;
	}

	@Override
	public void disposeScene() {
		this.detachChildren();
		this.clearUpdateHandlers();
	}
}
