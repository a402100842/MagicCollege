package magiccollege.scene;

import java.util.ArrayList;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.input.touch.detector.ScrollDetector;
import org.andengine.input.touch.detector.ScrollDetector.IScrollDetectorListener;
import org.andengine.input.touch.detector.SurfaceScrollDetector;
import org.andengine.opengl.util.GLState;

import magiccollege.Enum.ESceneID;
import magiccollege.manager.ResourceManager;
import magiccollege.manager.SceneManager;

public class HelpScene extends MyScene implements IOnSceneTouchListener,
IScrollDetectorListener {
	ResourceManager rm = ResourceManager.getInstance();
	SceneManager sm = SceneManager.getInstance();
	HelpInfoScene helpInfoScene;
	
	private SurfaceScrollDetector mScrollDetector;
	
	private ArrayList<Sprite> helparray = new ArrayList<Sprite>();
	private ArrayList<Text> textarray = new ArrayList<Text>();

	@Override
	public void createScene() {
		// TODO Auto-generated method stub
		createBackground();
		createText();
		
		Sprite back = new Sprite(690,365,rm.options_back_region,rm.vbo){
				@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
					
					if(pSceneTouchEvent.isActionDown()){
						HelpScene.this.onBackKeyPressed();
						return true;
					}
					 return false;
	    }};
	    Sprite card = new Sprite(72,20,rm.card_region,rm.vbo){
			@Override
		  public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				
				if(pSceneTouchEvent.isActionUp()){
					helpInfoScene = new HelpInfoScene(textarray.get(0));
					helpInfoScene.createScene();
					SceneManager.getInstance().setScene(helpInfoScene);
					
					return true;
				}
				 return false;
	    }};
	    Sprite cardgroup = new Sprite(72,110,rm.cardgroup_region,rm.vbo){
			@Override
		  public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {

				if(pSceneTouchEvent.isActionUp()){
					helpInfoScene = new HelpInfoScene(textarray.get(1));
					helpInfoScene.createScene();
					SceneManager.getInstance().setScene(helpInfoScene);
					
					return true;
				}
				 return false;
	    }};
	    Sprite fight = new Sprite(72,200,rm.fight_region,rm.vbo){
			@Override
		  public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				

				if(pSceneTouchEvent.isActionUp()){
					helpInfoScene = new HelpInfoScene(textarray.get(2));
					helpInfoScene.createScene();
					SceneManager.getInstance().setScene(helpInfoScene);
					
					return true;
				}
				 return false;
	    }};
	    Sprite block = new Sprite(72,290,rm.block_region,rm.vbo){
			@Override
		  public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				

				if(pSceneTouchEvent.isActionUp()){
					helpInfoScene = new HelpInfoScene(textarray.get(3));
					helpInfoScene.createScene();
					SceneManager.getInstance().setScene(helpInfoScene);
					
					return true;
				}
				 return false;
	    }};
	  
	    
	    Sprite generalskill = new Sprite(72,380,rm.generalskill_region,rm.vbo){
			@Override
		  public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {

				if(pSceneTouchEvent.isActionUp()){
					helpInfoScene = new HelpInfoScene(textarray.get(4));
					helpInfoScene.createScene();
					SceneManager.getInstance().setScene(helpInfoScene);
					
					return true;
				}
				 return false;
	    }};
	   
	    Sprite superskill = new Sprite(72,470,rm.superskill_region,rm.vbo){
			@Override
		  public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				

				if(pSceneTouchEvent.isActionUp()){
					helpInfoScene = new HelpInfoScene(textarray.get(5));
					helpInfoScene.createScene();
					SceneManager.getInstance().setScene(helpInfoScene);
					
					return true;
				}
				 return false;
	    }};
	    
	    Sprite fightground = new Sprite(72,560,rm.fightground_region,rm.vbo){
			@Override
		  public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				

				if(pSceneTouchEvent.isActionUp()){
					helpInfoScene = new HelpInfoScene(textarray.get(6));
					helpInfoScene.createScene();
					SceneManager.getInstance().setScene(helpInfoScene);
					
					return true;
				}
				 return false;
	    }};
	    
	    Sprite helpstate= new Sprite(72,650,rm.helpstate_region,rm.vbo){
			@Override
		  public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				

				if(pSceneTouchEvent.isActionUp()){
					helpInfoScene = new HelpInfoScene(textarray.get(7));
					helpInfoScene.createScene();
					SceneManager.getInstance().setScene(helpInfoScene);
					
					return true;
				}
				 return false;
	    }};
	    
	     Sprite abnormal = new Sprite(72,740,rm.abnormalstate_region,rm.vbo){
			@Override
		  public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				

				if(pSceneTouchEvent.isActionUp()){
					helpInfoScene = new HelpInfoScene(textarray.get(8));
					helpInfoScene.createScene();
					SceneManager.getInstance().setScene(helpInfoScene);
					
					return true;
				}
				 return false;
	    }};
	    Sprite system= new Sprite(72,830,rm.system_region ,rm.vbo){
			@Override
		  public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				

				if(pSceneTouchEvent.isActionUp()){
					helpInfoScene = new HelpInfoScene(textarray.get(9));
					helpInfoScene.createScene();
					SceneManager.getInstance().setScene(helpInfoScene);
					
					return true;
				}
				 return false;
	    }};
	    
	  
	    
	    Sprite store = new Sprite(72,920,rm.store_region,rm.vbo){
			@Override
		  public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				

				if(pSceneTouchEvent.isActionUp()){
					helpInfoScene = new HelpInfoScene(textarray.get(10));
					helpInfoScene.createScene();
					SceneManager.getInstance().setScene(helpInfoScene);
					
					return true;
				}
				 return false;
	    }};
	  
	    
	    helparray.add(card);
	    helparray.add(cardgroup);
	    helparray.add(fight);
	    helparray.add(block);
	    
	   
	    helparray.add(generalskill);
	    helparray.add(superskill);
	    
	    helparray.add(fightground);
	    
	    helparray.add(helpstate);
	    helparray.add(abnormal);
	    
	    helparray.add(system);
	    helparray.add(store);
	    
	    
		
		attachChild(back);
		attachChild(card);
		attachChild(cardgroup);
		attachChild(fight);
		attachChild(block);		
		attachChild(generalskill);
		attachChild(superskill);
		attachChild(fightground);
		attachChild(helpstate);
		attachChild(abnormal);
		attachChild(system);
		attachChild(store);
		
		registerTouchArea(back);
		registerTouchArea(card);
		registerTouchArea(cardgroup);
		registerTouchArea(fight);
		registerTouchArea(block);
		registerTouchArea(generalskill);
		registerTouchArea(superskill);
		registerTouchArea(fightground);
		registerTouchArea(helpstate);
		registerTouchArea(abnormal);	
		registerTouchArea(system);
		registerTouchArea(store);
		
		
		
		
		 setTouchAreaBindingOnActionDownEnabled(true);
		 enableTouchDetectors(); // NEW
		
		
	}

	@Override
	public void onBackKeyPressed() {
		rm.unloadHelpTexture();
		sm.setScene(ESceneID.options);
	}

	@Override
	public ESceneID getSceneID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void disposeScene() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onScroll(final ScrollDetector pScollDetector, final int pPointerID,
	    final float pDistanceX, final float pDistanceY) {
	       
	    	if(pDistanceY<0){
	    		moveUp();
	    	}else{
	    		moveDown();
	    	}
	    	comeBackHere();
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
	
	/**
	 * ����������
	 */
	private void enableTouchDetectors() {
	    this.mScrollDetector = new SurfaceScrollDetector(this);
	  
	    setOnSceneTouchListener(this);
	    setTouchAreaBindingOnActionDownEnabled(true);
	}
	
	private void moveUp(){
			for(int i = 0 ; i < helparray.size(); i++){
				helparray.get(i).setPosition(helparray.get(i).getX(),helparray.get(i).getY()-90);
			}
	}
	private void moveDown(){
			for(int i = 0 ; i < helparray.size(); i++){
				helparray.get(i).setPosition(helparray.get(i).getX(),helparray.get(i).getY()+90);
			}
	}
	private void comeBackHere(){
		if(helparray.get(0).getY()>0){
			helparray.get(0).setPosition(helparray.get(0).getX(),0);
			for(int i = 1; i < helparray.size(); i++){
				helparray.get(i).setPosition(helparray.get(0).getX(),helparray.get(0).getY()+90*i);
			}
		}
		int size = helparray.size();
		if(helparray.get(size-1).getY()<480){
			helparray.get(size-1).setPosition(helparray.get(size-1).getX(),390);
			for(int i = size-1 ; i > 0; i--){
				helparray.get(i-1).setPosition(helparray.get(size-1).getX(),helparray.get(size-1).getY()-90*(size-i));
			}
			
		}
	}
	
	private void createText(){
		Text t0 = new Text(72,0,rm.helpFont,"�����ơ�:\n\n����\n  ÿ�ſ��ƶ����Լ��Ĺ�������Э����\n������,�Ϸ�������Ϊ����������ߵ�\n" +
				"����ΪЭ��,�ұߵ�����Ϊ��������ս\n���У����������������ڹ���ʱ�Ե�\n����ɵ��˺���\n\nHPֵ:\n  ��HPֵС�ڵ���0ʱ�����ƾͻ�ӳ�\n�Ͻ���ÿ�ſ��Ƶ�HPֵ���������޵ģ�\n��<��>HP���ᳬ�����ޣ�<����>HP��\n�Գ������ޡ�\n\n" +
				"����:\n  ÿ�ſ�����3�����ܣ������кܶ���\n�࣬��������Ҳ���в�ͬ��һ�㼼��\n���ж�ʱ��������ͨ����ǰ��������\n�����ܲ��ռ���˵����\n\n����:\n  ���еĿ��Ʒ�Ϊ4���������ֱ�\nΪ����У��������У��������У���͡��麣����" +
				"\nһ�����������ֻ��ʹ�����ֲ�ͬ\n�����Ŀ��ơ����и������������\n��չӦ�þ����ڴ���",rm.vbo);
		
		Text t1 = new  Text(72,0,rm.helpFont,"�����顿:\n\n�������:\n  һ�����飬������1�ſ��ƣ����10\n" +
				"�ſ�����ɣ�һ�����������ֻ��\nʹ�����ֲ�ͬ�����Ŀ��ơ�\n\n���׿���:\n  ��ʼ���ֻ�ܱ���1�׿��飬���ŵ�\n��������,���𲽿��ſɱ��濨" +
				"����\n�����������ͬʱ����4�׿��飬\n�����ڿ����������������л�ʹ��\n���顣",rm.vbo);
		Text t2 = new  Text(72,0,rm.helpFont,"��ս����:\n\n�غ�:\n  ս����ʼ����ҺͶ��ֻ����ж���\nһ���غ���һ���ж�����һ���غ���\n���Է��ж���" +
				"�����ڽ���ս��֮ǰѡ\n�����ֻ��ߺ��֡�һ���غ��е��ж�\n���·�Ϊ�����׶Σ����ƽ׶Ρ�����\n�׶Ρ�����׶Ρ�ս���׶Ρ�\n\n�ƶ�:\nս����ʼ��˫�����������еĿ���\n���ڸ��Ե��ƶ��У�ÿ�ε��Լ��Ļ�\n" +
				"�Ͽ�ʼʱ������ƶ��������ȡһ��\n�ƽ������ơ�\n\n����:\n��ҿ����໥�����Է������ƣ�����\n����Ϊ5�ţ��������������򲻻��\n�ơ�\n\nս��:\nս���ֳƳ��ϣ��ǿ��ƽ�ս������\n" +
				"ֻ����ս���ϵĿ��ƣ��������Լ���\n�ϵ�ս���׶η������ܺ���ͨ������\n��ͨ�����Ƕ��Լ���ǰ�������ģ���\n����ǰ���ез����ƣ����������ˣ�\n����HPֵ������ֵ��ͬ��������\n�������򣬶Եз�Ӣ������˺���\n\n�ж�:\n" +
				"ÿ�ſ��ƿ�ʼ���м���ǰ����ͨ����\n�ͼ��ܷ����������Ϊһ���ж���\n\n����:\n" +
				"ָ��ǰ�ж��Ŀ��ơ�\n\nĹ��:\n" +
				"ս���У�ս��������ֵС�ڵ���0��\n���ƽ���Ĺ�ء�\n\n���ƽ׶�:\n" +
				"��һ���غϵ����ƽ׶Σ�����ƶ���\n�����ȡһ���ƽ������ơ�\n\n���ƽ׶�:\n" +
				"����ѡ��������0��1��2�ſ��Ʒ�\n��ս����\n\n����׶�:\n" +
				"�С��ϳ�ʱ�������ļ��ܵĿ�������\n�������ܣ���ÿ�ſ��Ƽ�����<Э��\n״̬>��������ĳЩ<��������>��\n\nս���׶�:\n" +
				"��ս���ϵĿ��ƻ����η������ܺͽ�\n����ͨ������\n\nӢ������ֵ:\n" +
				"ÿ��Ӣ�۶�������ֵ��ս�������Ӣ\n�۵�����ֵС�ڵ���0����ս��ʧ�ܡ�\n\nʤ������:\n" +
				"���ʹ�з������п��ƶ�����Ĺ�ػ�\n��Ӣ������ֵС�ڵ���0������Ϊս\n��ʤ����",rm.vbo);
		Text t3 = new  Text(72,0,rm.helpFont,"���ؿ���:\n\n��ͼ:\n" +
				"��ҿ��Դ��˵������ϵ�PLAY��ť\n������Ϸ����Ϸ���������ŵ�ͼ��\nÿ�ŵ�ͼ�������NPC��\n\n�ؿ�:\n" +
				"ÿ�ŵ�ͼ�����ؿ���ĳЩNPC�ǹ�\n���������ߣ�ֻ��������NPC������\nս��ʤ������ܽ�����һ�ŵ�ͼ����\n�عؿ����⣩��\n\n����:\n" +
				"��ͨһ���ؿ���һ���ѶȾ��ܻ��һ\n���ǡ���������һ���������Զһ�ϡ\n�п��ơ�\n\n����:\n" +
				"��ͨһ���ؿ���һ���ѶȾ��ܻ��һ\n�ſ��ơ�\n\n���عؿ�:\n" +
				"һЩ��ͼ�а������عؿ�����ͨ����\n���عؿ����Ի��ϡ�п���\n������Գ����",rm.vbo);
		Text t4 = new  Text(72,0,rm.helpFont,"��ͨ�ü��ܡ�:\n\n����:\n" +
				"��û˵�����󣬽�һ��������Ƶ�Э\n�������������е�������ʽΪ+1��\n��-1��ʹ��ӽ�<Э��״̬>������Э��\n" +
				"����߿��Ƶ������Ĳ�ֵ��С�ڣ���\n�����ұ߿��Ƶ�Э���Ĳ�ֵ������ѡ\n��Э�����е���������ѡ������������\n" +
				"ѡ���Ѿ������ڿ��Ƴ�Ϊ<Э��״̬>\n����������\n\n�����:\n" +
				"���������ķ�����С�",rm.vbo);
		Text t5 = new  Text(72,0,rm.helpFont,"���������ܡ�:\n\n������:\n" +
				"һ����һ�ſ��Ƶ�Э����������߿�\n�Ƶ�������ֵ�����ڣ�������������\n" +
				"�ұ߿��Ƶ�Э����ֵ������������һ\n�Ρ������󡱣���������ǰ���Ŀ���\n" +
				"�ͻصз���<�ƶ�>��",rm.vbo);
		Text t6 = new  Text(72,0,rm.helpFont,"����������:\n\n�����ս:\n" +
				"��ҿ��Դ��������ϵľ�������ť��\n�������ս��ѡ�����ߵĶ��ֲ��Ҿ�\n���Է�ͬ��󼴿ɽ���ս����\n\nս��:\n" +
				"���ƽ׶���ʱΪ30�룬�����뵥��\nս����ͬ��\n\n����:\n" +
				"һ�ֻ��ң����Զһ�ϡ�п��ơ���\n���սսʤ���ּ��ɻ�á�",rm.vbo);
		Text t7 = new  Text(72,0,rm.helpFont,"��Э��״̬��\n\n" +
				"��һ�ſ��Ƶ�Э����������߿���\n�����������������������ұ߿��Ƶ�\n" +
				"Э�������Ϊһ��Э��״̬��һ��\n����������ӵ��2��Э��״̬����\n" +
				"״̬��������������Ч����",rm.vbo);
		Text t8 = new  Text(72,0,rm.helpFont,"���쳣״̬��:\n\n����:\n" +
				"����״̬�����ܱ���������ֹһ��\n��ʽ��HP<�ָ�>�����ǲ�Ӱ��HP\n<����>������ֱ�����ƴӳ����뿪��\n\n����:\n" +
				"ʧȥ�ж�������1�غϡ�\n\n����:\n" +
				"���������ܣ���ͨ��������������\nӢ�ۡ�����1�غϡ�\n\n���:\n" +
				"���ܽ�����ͨ����������1�غϡ�\n\n�ж�:\n" +
				"ÿ���ж��������ܵ�һ�����Ķ���\n��������ֱ�����ƴӳ����뿪��",rm.vbo);
		Text t9 = new  Text(72,0,rm.helpFont,"��ϵͳ���á�:\n\n" +
				"���������Ƿ�ʼ�������֡�ս��\nʱ����Ч������Ч���ȣ������뿴\n�����˵��µ�ϵͳ����",rm.vbo);
		Text t10 = new  Text(72,0,rm.helpFont,"���̳ǡ�:\n\n" +
				"����ʹ�û��Ҷһ����ơ�",rm.vbo);
		
		textarray.add(t0);
		textarray.add(t1);
		textarray.add(t2);
		textarray.add(t3);
		textarray.add(t4);
		textarray.add(t5);
		textarray.add(t6);
		textarray.add(t7);
		textarray.add(t8);
		textarray.add(t9);
		textarray.add(t10);
		
		
		
		
	}
	private void createBackground()
	{
		Sprite background = new Sprite(0, 0, ResourceManager.getInstance().help_background_region, ResourceManager.getInstance().vbo)
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
