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
	 * 允许滚动监控
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
		Text t0 = new Text(72,0,rm.helpFont,"【卡牌】:\n\n力：\n  每张卡牌都有自己的攻击力，协力，\n，助力,上方的数字为攻击力，左边的\n" +
				"数字为协力,右边的数字为助力，在战\n斗中，攻击力决定卡牌在攻击时对敌\n方造成的伤害。\n\nHP值:\n  当HP值小于等于0时，卡牌就会从场\n上进入每张卡牌的HP值都是有上限的，\n复<恢>HP不会超过上限，<增加>HP可\n以超过上限。\n\n" +
				"技能:\n  每张卡牌有3个技能，技能有很多种\n类，发动条件也各有不同。一般技能\n在行动时、进行普通攻击前触发，其\n他技能参照技能说明。\n\n势力:\n  所有的卡牌分为4个势力，分别\n为“东校”、“南校”、“北校”和“珠海”。" +
				"\n一个卡组里最多只能使用两种不同\n势力的卡牌。还有更多关于势力的\n扩展应用敬请期待。",rm.vbo);
		
		Text t1 = new  Text(72,0,rm.helpFont,"【卡组】:\n\n卡组组成:\n  一个卡组，由至少1张卡牌，最多10\n" +
				"张卡牌组成，一个卡组里最多只能\n使用两种不同势力的卡牌。\n\n多套卡组:\n  初始玩家只能保留1套卡组，随着等\n级的增长,会逐步开放可保存卡" +
				"组数\n量，玩家最多可同时保存4套卡组，\n可以在卡组配置主界面中切换使用\n卡组。",rm.vbo);
		Text t2 = new  Text(72,0,rm.helpFont,"【战斗】:\n\n回合:\n  战斗开始后，玩家和对手互相行动，\n一个回合中一方行动，下一个回合则\n换对方行动。" +
				"可以在进入战斗之前选\n择先手或者后手。一个回合中的行动\n大致分为三个阶段：摸牌阶段、出牌\n阶段、计算阶段、战斗阶段。\n\n牌堆:\n战斗开始后，双方卡组中所有的卡牌\n都在各自的牌堆中，每次当自己的回\n" +
				"合开始时，会从牌堆中随机抽取一张\n牌进入手牌。\n\n手牌:\n玩家可以相互看到对方的手牌，手牌\n上限为5张，若手牌已满，则不会抽\n牌。\n\n战场:\n战场又称场上，是卡牌交战的区域，\n" +
				"只有在战场上的卡牌，才能在自己回\n合的战斗阶段发动技能和普通攻击。\n普通攻击是对自己正前方发动的，如\n果正前方有敌方卡牌，则对其造成伤，\n害其HP值削减数值等同于自身攻击\n力；否则，对敌方英雄造成伤害。\n\n行动:\n" +
				"每张卡牌开始进行计算前到普通攻击\n和技能发动结束后成为一次行动。\n\n自身:\n" +
				"指当前行动的卡牌。\n\n墓地:\n" +
				"战斗中，战场上生命值小于等于0的\n卡牌进入墓地。\n\n摸牌阶段:\n" +
				"在一个回合的摸牌阶段，会从牌堆中\n随机抽取一张牌进入手牌。\n\n出牌阶段:\n" +
				"可以选择将手牌中0、1、2张卡牌放\n入战场。\n\n计算阶段:\n" +
				"有“上场时发动”的技能的卡牌依次\n发动技能；对每张卡牌计算其<协助\n状态>数；发动某些<超级技能>。\n\n战斗阶段:\n" +
				"在战场上的卡牌会依次发动技能和进\n行普通攻击。\n\n英雄生命值:\n" +
				"每个英雄都有生命值，战斗中如果英\n雄的生命值小于等于0，则战斗失败。\n\n胜负规则:\n" +
				"如果使敌方的所有卡牌都进入墓地或\n者英雄生命值小于等于0，则判为战\n斗胜利。",rm.vbo);
		Text t3 = new  Text(72,0,rm.helpFont,"【关卡】:\n\n地图:\n" +
				"玩家可以戳菜单界面上的PLAY按钮\n进入游戏，游戏中有若干张地图，\n每张地图上有许多NPC。\n\n关卡:\n" +
				"每张地图有许多关卡，某些NPC是关\n卡的守卫者，只有与所有NPC触发对\n战并胜利后才能进入下一张地图（隐\n藏关卡除外）。\n\n星数:\n" +
				"打通一个关卡的一个难度就能获得一\n颗星。星数满足一定条件可以兑换稀\n有卡牌。\n\n奖励:\n" +
				"打通一个关卡的一个难度就能获得一\n张卡牌。\n\n隐藏关卡:\n" +
				"一些地图中包含隐藏关卡，打通所有\n隐藏关卡可以获得稀有卡牌\n《程序猿》。",rm.vbo);
		Text t4 = new  Text(72,0,rm.helpFont,"【通用技能】:\n\n调整:\n" +
				"若没说明对象，将一张随机卡牌的协\n力或者助力进行调整，方式为+1或\n者-1，使其接近<协助状态>。若（协力\n" +
				"与左边卡牌的助力的差值）小于（助\n力与右边卡牌的协力的差值），则选\n择协力进行调整，否则选择助力。不会\n" +
				"选择已经与相邻卡牌成为<协助状态>\n的那种力。\n\n逆调整:\n" +
				"“调整”的反向进行。",rm.vbo);
		Text t5 = new  Text(72,0,rm.helpFont,"【超级技能】:\n\n传送阵:\n" +
				"一旦（一张卡牌的协力加上其左边卡\n牌的助力的值）等于（其助力加上其\n" +
				"右边卡牌的协力的值），立即发动一\n次“传送阵”，将自身正前方的卡牌\n" +
				"送回敌方的<牌堆>。",rm.vbo);
		Text t6 = new  Text(72,0,rm.helpFont,"【竞技场】:\n\n网络对战:\n" +
				"玩家可以从主界面上的竞技场按钮进\n入网络对战。选择在线的对手并且经\n过对方同意后即可进入战斗。\n\n战斗:\n" +
				"出牌阶段限时为30秒，其他与单机\n战斗相同。\n\n荣誉:\n" +
				"一种货币，可以兑换稀有卡牌。网\n络对战战胜对手即可获得。",rm.vbo);
		Text t7 = new  Text(72,0,rm.helpFont,"【协助状态】\n\n" +
				"若一张卡牌的协力等于其左边卡牌\n的助力，或者其助力等于右边卡牌的\n" +
				"协力，则记为一个协助状态，一张\n卡牌最多可以拥有2个协助状态。此\n" +
				"状态将极大提升技能效果。",rm.vbo);
		Text t8 = new  Text(72,0,rm.helpFont,"【异常状态】:\n\n禁疗:\n" +
				"特殊状态，不能被消除。防止一切\n形式的HP<恢复>，但是不影响HP\n<增加>。持续直到卡牌从场上离开。\n\n冻结:\n" +
				"失去行动。持续1回合。\n\n混乱:\n" +
				"不发动技能，普通攻击将攻击自身\n英雄。持续1回合。\n\n麻痹:\n" +
				"不能进行普通攻击。持续1回合。\n\n中毒:\n" +
				"每次行动结束后受到一定量的毒伤\n害。持续直到卡牌从场上离开。",rm.vbo);
		Text t9 = new  Text(72,0,rm.helpFont,"【系统设置】:\n\n" +
				"可以设置是否开始背景音乐、战斗\n时的音效、粒子效果等，详情请看\n游侠菜单下的系统设置",rm.vbo);
		Text t10 = new  Text(72,0,rm.helpFont,"【商城】:\n\n" +
				"可以使用货币兑换卡牌。",rm.vbo);
		
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
