package magiccollege.scene;

import java.util.ArrayList;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.ParallelEntityModifier;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.color.Color;
import org.andengine.util.modifier.IModifier;
import org.andengine.util.modifier.ease.EaseSineInOut;

import magiccollege.Enum.ESceneID;
import magiccollege.fight.Card;
import magiccollege.fight.INextFunc;
import magiccollege.fight.Player;
import magiccollege.fight.Skill;
import magiccollege.fight.SkillEffect;
import magiccollege.manager.ResourceManager;
import magiccollege.manager.SceneManager;
import info.gridworld.grid.BoundedGrid;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

public class FightingScene extends MyScene{
	public static final int DEFAULT_ROWS = 2;
	public static final int DEFAULT_COLS = 5;
	public static final int DEFAULT_HAND_NUM = 5;
	public static Grid<Card> gr = new BoundedGrid<Card>(DEFAULT_ROWS, DEFAULT_COLS);
	protected Player p0,p1;
	protected Player curPlayer;//不能直接使用，用getCurrentPlayer();
	protected Player otherPlayer;//不能直接使用，用getOtherPlayer();
	protected int turn;
	protected int index;//用来控制循环
	protected int skillIndex;//用来控制发动技能的循环
	
	protected SkillEffect skillEffect;
	
	protected ArrayList<Card> myCard;//当前行动方场上的卡
	
	protected Sprite beginButton;
	protected Scene gameOverScene;
	protected Text gameOverText;
	public Text hpText;
	protected ResourceManager rm;
	protected HUD fightHUD;
	
	public FightingScene(Player pp1, Player pp2) {
		p0 = pp1;
		p1 = pp2;

		turn = 0;
		index = 0;
		skillIndex = 0;
		rm = ResourceManager.getInstance();
		
		fightHUD = new HUD();
		
		myCard = new ArrayList<Card>();
	}
		
	public void setTurn(int t){
		turn = t;
	}
	
	public void setHPText(final Card curCard, final Card targetCard, final Card theCard, final int change, final INextFunc nextFunc, final INextFunc nextFunc2){
		float x = theCard.getX() + theCard.getWidth() / 2;
		float y = theCard.getY() + theCard.getHeight() / 2;
		hpText.setPosition(x, y);
		hpText.setText("" + change);
		if (change<0)
			hpText.setColor(Color.RED);
		else
			hpText.setColor(Color.GREEN);
		hpText.registerEntityModifier(new DelayModifier(0.5f, new IEntityModifierListener(){

			@Override
			public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
				theCard.setHP(theCard.getHP() + change);
				hpText.setVisible(false);
				
				nextFunc.nextFunc(curCard, targetCard, nextFunc2);
			}

			@Override
			public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
				hpText.setVisible(true);
				arg0.setAutoUnregisterWhenFinished(true);
			}}));
	}
	
	
	protected void robotLogic(){
		System.out.println("begin 机器人自动出牌");
		Player robot = getCurrentPlayer();
		for (Location loc : robot.getHandGrid().getOccupiedLocations()){
			Card card = robot.getHandGrid().get(loc);
			this.setCardToGround(card);
			card.setClicked(true);
		}
		this.beforeFighting();
		this.calculate();
	}
	
	/**
	 * 将卡牌场上送入墓地并触发其效果(if any)
	 * @param card 被kill的卡牌
	 * @param owner 卡牌的归属方
	 *//*
	public static void killCard(Card card, Player owner){
		if (card.getHP() <= 0){
			if (card.hasSkill(10)){//如果有“爆炸”
				Skill.getInstance().launchSkillByKey(10, SceneManager.getInstance().getFightingScene().getCurrentPlayer(), 
						SceneManager.getInstance().getFightingScene().getOtherPlayer(), gr, card);
			}
			
			card.setWhere(Card.ON_GRAVE);
			card.removeSelfFromGrid();
			card.reInit();
			SceneManager.getInstance().getFightingScene().unregisterTouchArea(card);
			card.detachSelf();
			owner.grave.add(card);
		}
	}*/
	
	/**
	 * 将卡牌从手牌或场上送入墓地
	 * @param card 被kill的卡牌
	 * @param owner 卡牌的归属方
	 *//*
	public static void simplyKillCard(Card card, Player owner){
		if (card.getHP() <= 0){
			card.setWhere(Card.ON_GRAVE);
			card.removeSelfFromGrid();
			card.reInit();
			SceneManager.getInstance().getFightingScene().unregisterTouchArea(card);
			card.detachSelf();
			owner.grave.add(card);
		}
	}*/
	
	/**
	 * 游戏结束时调用
	 */
	protected void gameOver(){
		gameOverScene = new Scene();
		gameOverScene.setBackground(new Background(0,0,0));
		gameOverScene.setBackgroundEnabled(false);///////////
		gameOverScene.setOnSceneTouchListener(new IOnSceneTouchListener(){
			@Override
			public boolean onSceneTouchEvent(Scene scene, TouchEvent pTouchEvent) {
				if (pTouchEvent.isActionDown()){
					scene.setOnSceneTouchListener(null);
					SceneManager.getInstance().loadGameScene();
					/*
					disposeScene();
					System.exit(0);
					*/
					return true;
				}
				return false;
			}});
		//计算墓地中僵尸的数量
		int zombieNum = 0;
		for (Card theCard : p0.grave){
			if (theCard.getName() == "Zombie")
				zombieNum++;
		}
		//判断胜负
		if (p0.getHP() <= 0){
			gameOverText.setText("FAILED!");
		}else if(p0.grave.size() - zombieNum >= p0.getCardGroup().length){
			gameOverText.setText("FAILED!");
		}else{
			gameOverText.setText("WIN!!!");
		}
		gameOverText.setPosition(rm.camera.getWidth() / 2, rm.camera.getHeight() / 2);
		gameOverScene.attachChild(gameOverText);
		this.setChildScene(gameOverScene);
	}

	/**
	 * 从卡组抽卡到手牌
	 * */
	private void getCardToHand(){
//		System.out.println("begin 抽卡");
		Player curP = getCurrentPlayer();//得到当前player
		if (!curP.desktop.isEmpty()){//如果牌堆不为空的话
			int r = (int) (Math.random() * curP.desktop.size());//从牌堆中随机选一张卡;
			Card nextCard = null;//用来寻找手牌中空的位置
			for (int i=0; i<DEFAULT_HAND_NUM; i++){
				Location loc = new Location(0, i);//遍历每一个Location
				nextCard = curP.getHandGrid().get(loc);//得到这个Location上的卡（if any）
				if (nextCard == null){//如果这个Location上没有卡，则说明可以从牌堆抽一张卡放到此处
					final Card card = curP.desktop.remove(r);//从牌堆中移除一张卡，并赋给变量card
					float fromX = rm.camera.getWidth();//card从这个X坐标开始出现
					float fromY = 10f;//card从这个Y坐标开始出现
					float toX = 10 + i * (10 + card.getWidth());//card将要被放到的位置的X坐标
					float toY = 10f;//card将要被放到的位置的Y坐标
					if (turn != 0){//如果是对方的回合
						card.setClicked(true);//使卡牌被点击时只弹出CardInfoScene
					}else{//如果是自己回合
						fromY = toY = rm.camera.getHeight() - 10 - card.getHeight();//设置被放到的位置的Y坐标
					}
					card.putSelfInGrid(curP.getHandGrid(), loc);//将这张卡放到手牌区grid(还没显示出来)
					card.setWhere(Card.ON_HAND);//将这张卡记录位置的变量设置为“ON_HAND”
					final ParallelEntityModifier entityModifier =	new ParallelEntityModifier(new IEntityModifierListener() {
						@Override
						public void onModifierStarted(final IModifier<IEntity> pModifier, final IEntity pItem) {
							
							pModifier.setAutoUnregisterWhenFinished(true);
						}

						@Override
						public void onModifierFinished(final IModifier<IEntity> pEntityModifier, final IEntity pEntity) {
							FightingScene.this.registerTouchArea(card);//使卡牌可以被touch
							FightingScene.this.afterGettingCard();
						}
					},
							new AlphaModifier(1, 0f, 1),//1秒之内从透明变不透明
							new MoveModifier(1, fromX, toX,fromY, toY, EaseSineInOut.getInstance())//从牌堆位置移动到手牌区某个位置
					);//移动卡牌制造抽卡效果
					card.setPosition(fromX, fromY);
					card.setAlpha(0f);
					attachChild(card);//将卡牌显示出来
					card.registerEntityModifier(entityModifier);
					break;//因为找到了空的位置所以不用继续找了
				}
			}
			if (nextCard != null)
				this.afterGettingCard();
		}else
			this.afterGettingCard();
	}
	
	/**
	 * 从手牌到场上
	 * 移除操作放到按了begin之后处理
	 * */
	public void setCardToGround(final Card card){
		if (card != null){
			card.setAlpha(0.5f);
			//找到一个位置可以放，否则do nothing
			for (int i=0; i<DEFAULT_COLS; i++){
				Location loc = new Location(turn,i);
				if (gr.get(loc) == null){
					//new 一个新卡
					Card tempCard = new Card(card){
						@Override
						public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
							if (pSceneTouchEvent.isActionDown()){
								FightingScene scene = SceneManager.getInstance().getFightingScene();
								if (this.isClicked()){
									SceneManager.getInstance().setScene(new CardInfoScene(this, scene));
									return true;
								}else{
									this.removeSelfFromGrid();
									scene.unregisterTouchArea(this);
									this.detachSelf();
									this.dispose();
									card.setAlpha(1f);
									card.setClicked(false);
									return true;
								}		
							}
							return false;
						}};
					//将新卡放到场上
					tempCard.setWhere(Card.ON_GROUND);
					tempCard.putSelfInGrid(gr, loc);
					//判断显示位置并绘制卡片图形
					if (turn == 0){
						tempCard.setPosition(10 + i * (10 + card.getWidth()),
								rm.camera.getHeight() - 30 - 2 * card.getHeight());
						//registerTouchArea(tempCard);//如果是对方则己方不能touch对方的卡
					}
					else
						tempCard.setPosition(10 + i * (10 + card.getWidth()), 30 + card.getHeight());
					registerTouchArea(tempCard);
					attachChild(tempCard);
					
					break;
				}
			}
		}
	}
	
	/**
	 * 清除手牌区要出的卡；使卡牌响应点击时显示详细信息
	 */
	protected void beforeFighting(){
		//使场上的卡响应点击时只显示详细信息
		for (int i=0; i<DEFAULT_COLS; i++){
			Card card = gr.get(new Location(turn,i));
			if (card != null){
				card.setClicked(true);
				card.avantInit();
			}else
				break;
		}
		Grid<Card> handGrid = getCurrentPlayer().getHandGrid();
		//使手牌的卡点击时只显示详细信息
		for (int i=0; i<DEFAULT_HAND_NUM; i++){
			Card card = handGrid.get(new Location(0, i));
			if (card != null){
				if (card.isClicked()){//remove掉将要出牌的卡
					this.unregisterTouchArea(card);
					card.detachSelf();
					card.removeSelfFromGrid();
					card.dispose();
				}else
					card.setClicked(true);
			}
		}
	}
	
	/**
	 * 判断自身卡牌是否有“充能”技能
	 */
	public void judge82Charge(final Card curCard){
		//System.out.println("begin 充能");
		if (curCard != null){
			if (curCard.hasSkill(82) && !curCard.banTreat){
				skillEffect.charge(curCard);
				return;
			}
		}
		judgePoisonState(curCard);
	}
	
	/**
	 * 判断自身卡牌是否有“恢复”技能
	 */
	protected void judge81Recover(final Card curCard){
		//System.out.println("begin 恢复");
		if (curCard != null){
			if (curCard.hasSkill(81) && !curCard.banTreat){
				skillEffect.recover(curCard);
				return;
			}
		}
		judge82Charge(curCard);
	}
	
	/**
	 * 判断自身卡牌是否有“咆哮”技能
	 */
	protected void judge19Roar(final Card curCard, final Card targetCard){
		//System.out.println("begin 咆哮");
		if (curCard != null && targetCard != null){
			if (curCard.hasSkill(19)){
				if (curCard.attackcount2 > 0){
					curCard.attackcount2--;
					if (curCard.hasSkill(99))
						curCard.attackcount = 2;//重置“横扫”计数
					skillEffect.roar(curCard, targetCard);
					return;
				}
			}
		}
		judge81Recover(curCard);
	}
	
	/**
	 * 判断自身卡牌是否有“横扫”技能
	 */
	public void judge99SweepAway(final Card curCard, final Card targetCard){
		//System.out.println("begin 横扫");
		if (curCard != null){//important to judge if it is null
			Location curLoc = curCard.getLocation();
			if (curCard.hasSkill(99)){
				Location leftLoc = new Location(Math.abs(curLoc.getRow() - 1),curLoc.getCol() - 1);
				Location rightLoc = new Location(Math.abs(curLoc.getRow() - 1),curLoc.getCol() + 1);
				if (curCard.attackcount == 2){//此时应该攻击敌方左边的卡(if any)
					curCard.attackcount --;
					if (gr.isValid(leftLoc)){
						Card leftCard = gr.get(leftLoc);
						if (leftCard != null){
							judge9EarthStab(curCard, leftCard);
							return;
						}
					}
				}
				if (curCard.attackcount == 1){//此时应该攻击敌方右边的卡(if any)
					curCard.attackcount --;
					if (gr.isValid(rightLoc)){
						Card rightCard = gr.get(rightLoc);
						if (rightCard != null){
							judge9EarthStab(curCard, rightCard);
							return;
						}
					}
				}
				Location frontLoc = new Location(Math.abs(curLoc.getRow() - 1),curLoc.getCol());
				Card frontCard = gr.get(frontLoc);
				judge19Roar(curCard, frontCard);
			}else
				judge19Roar(curCard, targetCard);
		}else
			judge19Roar(curCard, targetCard);
	}
	
	/**
	 * 判断自身卡牌是否有“看破”技能；
	 * 下一个调用的函数为{@link FightingScene#judge99SweepAway(Card, Card)}
	 */
	public void judge3SeeThrough(final Card curCard, final Card targetCard){
		//System.out.println("begin 看破");
		if (curCard != null && targetCard != null){//important to judge if they are null
			if (curCard.hasSkill(3)){
				skillEffect.seeThrough(getOtherPlayer(), curCard, targetCard, -curCard.getTop(), new INextFunc(){
					@Override
					public void nextFunc(Card curCard, Card targetCard,
							INextFunc nextFunc2) {
						judge99SweepAway(curCard, targetCard);
					}});
			}else
				judge99SweepAway(curCard, targetCard);
		}else
			judge99SweepAway(curCard, targetCard);
	}
	
	/**
	 * 判断自身卡牌是否有“猛击”技能
	 */
	protected void judge15Punch(final Card curCard, final Card targetCard){
		//System.out.println("begin 猛击");
		if (curCard != null && targetCard != null){//important to judge if they are null
			if (curCard.hasSkill(15)){
				Skill.getInstance().punch(curCard, targetCard);
			}else
				judge3SeeThrough(curCard, targetCard);
		}else
			judge3SeeThrough(curCard, targetCard);
	}
	
	/**
	 * 判断自身卡牌是否有“注射”技能
	 */
	protected void judge98Injection(final Card curCard, final Card targetCard){
		//System.out.println("begin 注射");
		if (curCard != null && targetCard != null){//important to judge if they are null
			if (curCard.hasSkill(98)){
				//TODO 
				judge15Punch(curCard, targetCard);//temp
			}else
				judge15Punch(curCard, targetCard);
		}else
			judge15Punch(curCard, targetCard);
	}
	
	/**
	 * 判断自身卡牌是否有“撕裂”技能
	 */
	public void judge97Tear(final Card curCard, final Card targetCard){
		//System.out.println("begin 撕裂");
		if (curCard != null && targetCard != null){//important to judge if they are null
			if (curCard.hasSkill(97)){
				//TODO 
				judge98Injection(curCard, targetCard);//temp
			}else
				judge98Injection(curCard, targetCard);
		}else
			judge98Injection(curCard, targetCard);
	}
	
	/**
	 * 判断自身卡牌是否有“辐射”技能
	 */
	protected void judge12Radiate(final Card curCard, final Card targetCard){
		//System.out.println("begin 辐射");
		if (curCard != null && targetCard != null){//important to judge if they are null
			if (curCard.hasSkill(12)){
				skillEffect.radiate(curCard, targetCard);
			}else
				judge97Tear(curCard, targetCard);
		}else
			judge97Tear(curCard, targetCard);
	}
	
	/**
	 * 判断自身卡牌是否有“吸血”技能
	 */
	protected void judge96Bloodsucking(final Card curCard, final Card targetCard){
		//System.out.println("begin 吸血");
		if (curCard != null && targetCard != null){//important to judge if they are null
			if (curCard.hasSkill(96)){
				//TODO 
				judge12Radiate(curCard, targetCard);//temp
			}else
				judge12Radiate(curCard, targetCard);
		}else
			judge12Radiate(curCard, targetCard);
	}
	
	/**
	 * 判断对面卡牌是否有“新生”技能
	 */
	public void judge95Renascence(final Card curCard, final Card targetCard){
		//System.out.println("begin 新生");
		if (curCard != null && targetCard != null){//important to judge if they are null
			if (targetCard.hasSkill(95)){
				//TODO 
				judge96Bloodsucking(curCard, targetCard);//temp
			}else
				judge96Bloodsucking(curCard, targetCard);
		}else
			judge96Bloodsucking(curCard, targetCard);
	}
	
	/**
	 * 判断对面卡牌是否有“污染”技能；
	 * 在{@link FightingScene#judge92Snarl(Card, Card)}之后被调用；
	 * 下一个调用的函数为{@link FightingScene#judge95Renascence(Card, Card)}
	 */
	public void judge13Pollute(final Card curCard, final Card targetCard){
		//System.out.println("begin 污染");
		if (curCard != null && targetCard != null){//important to judge if they are null
			if (targetCard.hasSkill(13)){
				skillEffect.pollute(curCard, targetCard);
			}else
				judge95Renascence(curCard, targetCard);
		}else
			judge95Renascence(curCard, targetCard);
	}

	/**
	 * 判断对面卡牌是否有“吼叫”技能；
	 * 下一个调用的函数为{@link FightingScene#judge13Pollute(Card, Card)}
	 */
	public void judge92Snarl(Card curCard, Card targetCard){
		//System.out.println("begin 吼叫");
		if (curCard != null && targetCard != null){//important to judge if they are null
			if (targetCard.hasSkill(92)){
				Skill.getInstance().snarl();//TODO 发动技能效果并在完成后调用judge13Pollute(curCard, targetCard);
				judge13Pollute(curCard, targetCard);//temp；此时curCard有可能为null
			}else
				judge13Pollute(curCard, targetCard);
		}else
			judge13Pollute(curCard, targetCard);
	}
	
	
	/**
	 * 判断对面卡牌是否有“长生”技能；
	 * 由于对面卡牌死亡，因此传下去的targetCard为null
	 * @param nextFunc 下一个将要调用的函数
	 */
	public void judge94Perdure(Card curCard, Card targetCard, final INextFunc nextFunc){
		//System.out.println("begin 长生");
		Player player = getOtherPlayer();
		if (curCard == targetCard){
			player = getCurrentPlayer();
			curCard = null;
		}
		if (targetCard.hasSkill(94)){
			if (Skill.getInstance().getRandom(1) <= 0.8){//成功发动技能
				targetCard.removeSelfFromGrid();
				int i = 0; 
				for (; i<DEFAULT_HAND_NUM; i++){
					Location loc = new Location(0, i);//遍历每一个Location
					Grid<Card> grid = player.getHandGrid();
					if (grid.get(loc) == null){//这个Location上没有卡
						targetCard.setClicked(true);
						targetCard.putSelfInGrid(grid, loc);
						targetCard.setWhere(Card.ON_HAND);
						this.attachChild(targetCard);
						this.registerTouchArea(targetCard);
					}
				}
				if (i == DEFAULT_HAND_NUM){//手牌已满
					targetCard.setWhere(Card.ON_DESKTOP);
					player.desktop.add(targetCard);
				}
				//TODO 显示技能效果并在结束之后调用nextFunc.nextFunc(curCard, null);
				nextFunc.nextFunc(curCard, null, null);//temp
				return;
			}
		}
		targetCard.removeSelfFromGrid();
		player.grave.add(targetCard);
		nextFunc.nextFunc(curCard, null, null);
		
	}
	
	/**
	 * 判断对面卡牌是否有“爆炸”技能；调用此技能时表明targetCard已经死亡；
	 * 下一个调用的函数为{@link FightingScene#judge94Perdure(Card, Card)}
	 */
	protected void judge10explode(final Card curCard, final Card targetCard, final INextFunc nextFunc){
		//System.out.println("begin 爆炸");
		if (targetCard.hasSkill(10)){//如果有“爆炸”
			skillEffect.explode(curCard, targetCard, nextFunc);
		}else{
			judge94Perdure(curCard, targetCard, nextFunc);
		}
	}
	
	/**
	 * 扣血之后判断是否死亡用，并且会触发卡牌死亡时应发动的技能(if any)
	 * @param curCard	当前行动的卡牌
	 * @param targetCard	目标卡牌
	 * @param nextFunc 下一个需要调用的函数
	 */
	public void afterCutCardHP(final Card curCard, final Card targetCard, final INextFunc nextFunc){
		//System.out.println("begin 是否普通死亡");
		if (targetCard.getHP() <= 0){//目标卡牌死亡
			this.unregisterTouchArea(targetCard);
			targetCard.setWhere(Card.ON_GRAVE);
			//targetCard.removeSelfFromGrid();
			targetCard.reInit();
			rm.engine.runOnUpdateThread(new Runnable(){
				@Override
				public void run() {
					targetCard.detachSelf();
				}});
			
			FightingScene.this.judge10explode(curCard, targetCard, nextFunc);	
		}else{
			nextFunc.nextFunc(curCard, targetCard, null);
		}
	}
	
	/**
	 * 普通攻击扣血函数；
	 * 在 {@link FightingScene#judge93Twist(Card, Card, int)}之后被调用；
	 * 下一个调用的函数为{@link FightingScene#afterCutCardHP(Card, Card, int)}
	 *//*
	private void cutCardHP(final Card curCard, final Card targetCard, int cut){
		System.out.println("begin 普通扣血");
		skillEffect.cutHPEffect(curCard, targetCard, cut);
	}*/
	
	/**
	 * 判断对面卡牌是否有“扭曲”技能；
	 * 在 {@link FightingScene#judge21Strong(Card, Card)}之后被调用；
	 * 下一个调用的函数为{@link FightingScene#cutCardHP(Card, Card, int)}
	 */
	public void judge93Twist(final Card curCard, final Card targetCard, int cut){
		//System.out.println("begin 扭曲");
		if (targetCard.hasSkill(93)){
			cut -= (int)(cut * 0.25 * (1 + targetCard.getAssistState()));
			skillEffect.twist(curCard, targetCard, cut);
		}else{
			skillEffect.cutHPEffect(curCard, targetCard, cut);
		}
	}
	
	/**
	 * 判断对面卡牌是否有“强壮”技能；
	 * 在 {@link FightingScene#judge11Legerity(Card, Card)}之后被调用
	 * 下一个调用的函数为{@link FightingScene#judge93Twist(Card, Card, int)}
	 */
	protected void judge21Strong(final Card curCard, final Card targetCard){
		//System.out.println("begin 强壮");
		int cut = curCard.getTop();
		if (targetCard.hasSkill(21)){//如果有“强壮”
			cut = 3 - targetCard.getAssistState();
			if (curCard.getTop() < cut)
				cut = curCard.getTop();
			skillEffect.strong(curCard, targetCard, cut);
		}else{
			judge93Twist(curCard, targetCard, cut);
		}
	}
	
	/**
	 * 判断对面卡牌是否有“轻灵”技能；
	 * 目前传入的参数不会为null，若有新函数调用此函数须保证传入的参数不会为null (2013_08_12 11:41)；
	 * （1）若攻击命中，调用{@link FightingScene#judge21Strong(Card, Card)}；
	 * （2）若攻击未命中但是在横扫状态的第一击中，继续调用{@link FightingScene#judge99SweepAway(Card, Card)}；
	 * （3）若攻击未命中，调用{@link FightingScene#judge19Roar(Card, Card)}
	 */
	protected void judge11Legerity(Card curCard, Card targetCard){
		//System.out.println("begin 轻灵");
		if (targetCard.hasSkill(11)){//如果有“轻灵”	
			if (Skill.getInstance().getRandom(1) < (0.4+0.2 * curCard.getAssistState())){//攻击未命中
				INextFunc nextFunc;
				if (curCard.attackcount == 1)
					nextFunc = new INextFunc(){
						@Override
						public void nextFunc(Card curCard, Card targetCard,
								INextFunc nextFunc2) {
							judge99SweepAway(curCard, targetCard);							
						}};
				else
					nextFunc = new INextFunc(){
					@Override
					public void nextFunc(Card curCard, Card targetCard,
							INextFunc nextFunc2) {
						judge19Roar(curCard, targetCard);						
					}};
				skillEffect.legerity(curCard, targetCard, nextFunc);
				return;
			}
		}
		//攻击命中
		judge21Strong(curCard, targetCard);
	}
	
	/**
	 * 扣血之后判断是否死亡用，并且  不  会触发卡牌死亡时应发动的技能
	 * @param curCard	当前行动的卡牌
	 * @param targetCard	前方卡牌
	 * @param theCard	可能死亡的卡牌
	 * @param nextFunc 下一个需要调用的函数
	 */
	public void afterSimplyCutCardHP(Card curCard, Card targetCard, final Card theCard, final INextFunc nextFunc, final INextFunc nextFunc2){
		//System.out.println("begin 是否死亡");
		if (theCard.getHP() <= 0){//若目标卡牌死亡
			Player player = getOtherPlayer();
			if (targetCard == theCard)
				targetCard = null;
			if (curCard == theCard){
				curCard = null;
				player = getCurrentPlayer();
			}
			this.unregisterTouchArea(theCard);
			theCard.setWhere(Card.ON_GRAVE);
			theCard.removeSelfFromGrid();
			theCard.reInit();
			player.grave.add(theCard);
			
			rm.engine.runOnUpdateThread(new Runnable(){
				@Override
				public void run() {
					theCard.detachSelf();
				}});
			
			nextFunc.nextFunc(curCard, targetCard, nextFunc2);
			
		}
		else{
			
			nextFunc.nextFunc(curCard, targetCard, nextFunc2);
		}
	}
	
	
	/**
	 * 显示地刺效果的扣血函数；
	 * 在调用{@link FightingScene#judge9EarthStab(Card, Card)}之后调用；
	 * 接下来调用的函数为{@link FightingScene#afterSimplyCutCardHP(Card, Card)}
	 *//*
	private void simplyCutCardHP(final Card curCard, final Card targetCard){
		System.out.println("begin 地刺扣血");
		//TODO 显示效果，完了之后调用afterSimplyCutCardHP(curCard, targetCard);
		targetCard.setHP(targetCard.getHP()-curCard.getTop());
		afterSimplyCutCardHP(curCard, targetCard, new INextFunc(){

			@Override
			public void nextFunc(Card curCard, Card targetCard) {
				FightingScene.this.judge92Snarl(curCard, targetCard);
			}});
	}*/
	
	/**
	 * 判断对面卡牌是否有“地刺”技能；
	 * 目前传入的参数不会为null，若有新函数调用此函数须保证传入的参数不会为null (2013_08_12 11:40)
	 */
	public void judge9EarthStab(Card curCard, Card targetCard){
		//System.out.println("begin 地刺");
		if (curCard.hasSkill(9)){
			skillEffect.simplyCutHPEffect(curCard, targetCard, curCard.getTop());
		}else{
			judge11Legerity(curCard, targetCard);
		}
	}
	
	/**
	 * 判断对面卡牌是否有“洞察”技能；
	 * 只在{@link FightingScene#judgeNumbState(Card)} 里面被调用，可确保传入的参数不为null；
	 * 下一个调用的函数为 {@link FightingScene#judge9EarthStab(Card, Card)}
	 */
	protected void judge91Pierce(Card curCard, Card targetCard){
		//System.out.println("begin 洞察");
		if (curCard.hasSkill(91)){
			int change = curCard.tempTopInc;
			curCard.tempTopInc += curCard.getTop();
			change = curCard.tempTopInc - change;
			curCard.setTop(curCard.getTop() + change);
			//TODO 显示攻击力上升效果并在完了之后调用judge9EarthStab();
			judge9EarthStab(curCard, targetCard);//temp
		}else{
			judge9EarthStab(curCard, targetCard);
		}
	}
	
	/**
	 * 判断自身是否有“麻痹”状态，有的话直接{@link FightingScene#judgePoisonState(Card)}；
	 * 否则（1）若正对方向没有卡，攻击对方英雄之后调用{@link FightingScene#judge19Roar(Card, Card)}；
	 * 		 （2）若正对方向有卡，调用{@link FightingScene#judge91Pierce(Card, Card)}
	 * @param curCard 自身当前行动的卡牌
	 */
	protected void judgeNumbState(Card curCard){
		//System.out.println("begin 麻痹");
		if (curCard != null){
			if (!curCard.numb){//不是麻痹状态，可以普通攻击
				Card frontCard = gr.get(new Location(Math.abs(turn - 1), index));
				if (frontCard != null){
					judge91Pierce(curCard, frontCard);
				}else{//攻击英雄
					Player other = getOtherPlayer();
					skillEffect.setPlayerHpText(other, curCard, frontCard, -curCard.getTop(), new INextFunc(){
						@Override
						public void nextFunc(Card curCard, Card targetCard,
								INextFunc nextFunc2) {
							judge19Roar(curCard, targetCard);
						}});
				}
				return;
			}
		}
		judgePoisonState(curCard);
	}
	
	/**
	 * 发动技能
	 */
	public void launchSkill(final Card curCard){
		//System.out.println("begin 发动技能");
		if (curCard != null){
			if (skillIndex < curCard.skill.length){
				Skill.getInstance().launchSkillByKey(curCard.skill[skillIndex++], curCard);
				/* 在launchSkillByKey里面显示完效果后应该调用launchSkill(Card);若途中死亡应该传入null*/
				/*skillIndex++; launchSkill(curCard);*/
			}else{
				judgeNumbState(curCard);
			}
		}else{
			this.endOneCardTurn(curCard);
		}
	}
	
	/**
	 * 结束一张卡的行动
	 */
	protected void endOneCardTurn(Card curCard){
		System.out.println("begin 结束行动");
		if (curCard != null){//行动结束前，初始化当前卡片状态信息
			if (curCard.getWhere() == Card.ON_GROUND){
				curCard.setPosition(curCard.getX(), curCard.getY() + 10);
			}
		}
		//玩家没血，则结束游戏
		if (this.getOtherPlayer().getHP() <= 0 || this.getCurrentPlayer().getHP() <= 0){
			gameOver();
		}else{
			index++;
			this.judgeMessState();//轮到下一张卡行动，先判断它的混乱状态
		}
	}
	
	/**
	 * 判断中毒效果
	 */
	public void judgePoisonState(Card curCard){
		System.out.println("begin 中毒");
		if (curCard != null){
			if (curCard.poison){
				int cut = 2;//暂时认为中毒扣2滴血
				setHPText(curCard, curCard, curCard, -cut, new INextFunc(){
					@Override
					public void nextFunc(Card curCard, Card targetCard,
							INextFunc nextFunc2) {
						afterSimplyCutCardHP(curCard, targetCard, curCard, new INextFunc(){
							@Override
							public void nextFunc(Card curCard, Card targetCard,
									INextFunc nextFunc2) {
								endOneCardTurn(curCard);
							}}, null);
					}}, null);
				return;
			}
		}
		endOneCardTurn(curCard);//结束一张卡的行动
	}
	
	/**
	 * 结束回合
	 */
	protected void endTurn(){
		System.out.println("begin 结束回合");
		/*判断双方任意一方的卡牌是否被消灭殆尽*/
		//计算墓地中僵尸的数量
		int zombieNum = 0;
		for (Card theCard : p0.grave){
			if (theCard.getName() == "Zombie")
				zombieNum++;
		}
		if (p0.grave.size() - zombieNum >= p0.getCardGroup().length){
			gameOver();
			return;
		}
		
		//计算墓地中僵尸的数量
		zombieNum = 0;
		for (Card theCard : p1.grave){
			if (theCard.getName() == "Zombie")
				zombieNum++;
		}
		if (p1.grave.size() - zombieNum >= p1.getCardGroup().length){
			gameOver();
			return;
		}
		//重置卡片相关增益和异常状态
		Card mycard;
		for (int i=0; i < gr.getNumCols(); i++){
			mycard = gr.get(new Location(turn,i));
			if (mycard != null)
				mycard.init();
		}
		
		//结束回合
		turn = Math.abs(turn - 1);
		//调整另一方场上位置
		adjustGround();
	}
	
	/**
	 * 判断混乱状态
	 */
	public void judgeMessState(){
		System.out.println("begin 混乱");
		if (index >= myCard.size()){
			endTurn();
		}else{
			Card curCard = myCard.get(index);//第一次得到curCard
			curCard.setPosition(curCard.getX(), curCard.getY() - 10);
			if (curCard.getWhere() == Card.ON_GROUND){
				if (curCard.mess){
					Player curp = this.getCurrentPlayer();
					skillEffect.setPlayerHpText(curp, curCard, null, -curCard.getTop(), new INextFunc(){
						@Override
						public void nextFunc(Card curCard, Card targetCard,
								INextFunc nextFunc2) {
							judgePoisonState(curCard);
						}});
					
				}else if (curCard.freeze){
					judgePoisonState(curCard);//直接跳到判断中毒效果
				}else{
					skillIndex = 0;
					launchSkill(curCard);//开始发动第一个技能
				}
			}else{
				this.endOneCardTurn(curCard);
			}
		}
	}
	
	/**
	 * 
	 * 计算阶段,计算协助状态和超级技能
	 **/
	protected void calculate(){
		//System.out.println("begin calculating");
		myCard.clear();
		index = 0;
		continueCalculate();
	}
	
	public void continueCalculate(){
		if (index >= DEFAULT_COLS){
			index = 0;
			judgeMessState();//进入下一阶段，判断混乱状态
			return;
		}
		Card curCard= gr.get(new Location(turn,index));
		if (curCard == null){
			index = 0;
			judgeMessState();//进入下一阶段，判断混乱状态
			return;
		}
		myCard.add(curCard);
		Card leftCard,rightCard;
		//得到左边和右边的卡(if any)
		if (index == 0){
			leftCard = null;
		}
		else {
			leftCard = gr.get(new Location(turn,index-1));
		}
		if (index == DEFAULT_COLS-1){
			rightCard = null;
		}
		else {
			rightCard = gr.get(new Location(turn,index+1));
		}
		//计算<协助状态>数量
		int assistState = 0;
		if (leftCard != null){
			if (leftCard.getRight() == curCard.getLeft()){
				assistState++;
			}
		}
		if (rightCard != null){
			if (rightCard.getLeft() == curCard.getRight()){
				assistState++;
			}
		}
		if (curCard.getAssistState() != assistState)
			curCard.setSpecialSkill(false);
		curCard.setAssistState(assistState);
		curCard.attackcount2 = assistState;
		//显示<协助状态>数量,TODO 用星星显示
		
		if (!curCard.isSuperSkillDone() && curCard.hasSkill(58)){
			/**
			 * 超级技能,刺杀
			 * */
			index++;
			Skill.getInstance().assassinate(curCard);
			return;
		}else{
			/**
			 * 超级技能,传送阵
			 * */
			if (rightCard != null && leftCard != null){//除非左边和右边都有卡否则绝对不会发动超级技能
				if (!curCard.isSuperSkillDone()){//如果从来没发动过才有可能发动
					int leftSum = curCard.getLeft() + leftCard.getRight();
					int rightSum = curCard.getRight() + rightCard.getLeft();
					if (leftSum == rightSum){//满足发动超级技能的条件
						Card removeC = gr.get(new Location(Math.abs(turn-1),index));//得到前方的卡
						curCard.setSuperSkill(true);//无论前方有没有卡，只要满足判断条件则记为发动过了
						if (removeC != null){//将其放回对方牌堆并重置所有属性
							removeC.reInit();
							removeC.removeSelfFromGrid();
							getOtherPlayer().desktop.add(new Card(removeC){
								@Override
								public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
									if (pSceneTouchEvent.isActionDown()){
										if (this.isClicked()){
											SceneManager.getInstance().setScene(new CardInfoScene(this, FightingScene.this));
											return true;
										}else{
											this.setClicked(true);
											FightingScene.this.setCardToGround(this);			
											return true;
										}
									}
									return false;
								}});
							index++;
							//TODO 制造传送效果并在效果结束的时候调用下面语句
							this.unregisterTouchArea(removeC);removeC.detachSelf();removeC.detachChildren();removeC.dispose();continueCalculate();//temp
							return;
						}
					}
				}
			}
		}
		
		index++;
		this.continueCalculate();
	}
	
	/** 
	 * 调整场上的卡牌位置
	 * */
	protected void adjustGround(){
		//System.out.println("begin 调整场上卡牌位置");
		Card card;
		ArrayList<Card> list = new ArrayList<Card>();//找到场上当前方卡牌放到一个临时的list里面
		for (int i = 0; i < DEFAULT_COLS; i++){//对于场上每一个Location
			card = gr.get(new Location(turn,i));//得到这个Location上的card
			if (card != null){
				list.add(card);//临时的list里面记录下这张卡
			}
		}
		//System.out.println("list里面存了当前方场上所有卡牌，size为"+list.size());
		//从list里面取出Card移动到场上正确位置
		if (!list.isEmpty()){
			index  = 0;
			continueAdjustGround(list);
		}else
			getCardToHand();//当前方开始抽卡
	}
	
	/** 
	 * 继续调整场上的卡牌位置
	 * */
	protected void continueAdjustGround(final ArrayList<Card> list){
		//System.out.println("continue 调整场上卡牌位置，当前index为"+index);
		if (index >= list.size())
			return;
		//System.out.println("list size:"+list.size());
		final Card card = list.get(index);//从list里取出第 i 张卡
		//System.out.println("得到了第"+index+"张卡");
		float toX = 10 + index * (10 + card.getWidth());//card将要被放置的X坐标
		float fromX = card.getX();//card原本所在的X坐标
		float toY = 0f;//card将要被放置的Y坐标
		if (turn == 0)
			toY = ResourceManager.getInstance().camera.getHeight() - 30 - 2 * card.getHeight();
		else
			toY = 30 + card.getHeight();
		float fromY = card.getY();//card原本所在的Y坐标
		
		if (index== list.size() - 1){
			final MoveModifier moveModifier = new MoveModifier(1f, fromX, toX, fromY, toY, new IEntityModifierListener(){
				@Override
				public void onModifierFinished(IModifier<IEntity> pModifier,
						IEntity pItem) {
					//System.out.println("modifier finished");
					FightingScene.this.getCardToHand();//当前方开始抽卡
				}
				@Override
				public void onModifierStarted(IModifier<IEntity> pModifier,
						IEntity pItem) {
					pModifier.setAutoUnregisterWhenFinished(true);
				}
			}, EaseSineInOut.getInstance());//1秒内移动到正确位置
			card.moveTo(new Location(turn ,index));//card在grid层的移动
			//System.out.println("card在grid层的移动");
			card.registerEntityModifier(moveModifier);//注册entityModifier
		}else if (Math.abs(fromX - toX) < 1){//判断fromX和toX是否相等，相等则不用移动
			index++;
			continueAdjustGround(list);
		}
		else{
			final MoveModifier moveModifier = new MoveModifier(0.5f, fromX, toX, fromY, toY, new IEntityModifierListener(){
				@Override
				public void onModifierFinished(IModifier<IEntity> pModifier,
						IEntity pItem) {
					index++;
					continueAdjustGround(list);
				}
				@Override
				public void onModifierStarted(IModifier<IEntity> pModifier,
						IEntity pItem) {
					pModifier.setAutoUnregisterWhenFinished(true);
				}
			}, EaseSineInOut.getInstance());//1秒内移动到正确位置
			card.moveTo(new Location(turn ,index));//card在grid层的移动
			//System.out.println("card在grid层的移动");
			
			card.registerEntityModifier(moveModifier);//注册entityModifier
			//System.out.println("注册entityModifier");

		}
	}
	
	/**
	 * 抽卡之后出牌之前要预先做好的事情
	 */
	protected void afterGettingCard(){
		//Enable touch-setting event
		if (getCurrentPlayer() == p0){//如果是己方行动
			Grid<Card> handGrid = getCurrentPlayer().getHandGrid();//得到手牌区grid
			for (Location loc :handGrid.getOccupiedLocations()){//对于手牌区每一张卡
				handGrid.get(loc).setClicked(false);//使其可以被点击之后放到场上
			}
			FightingScene.this.registerTouchArea(beginButton);//enable 开始按钮
		}else//如果是对方行动
			robotLogic();//自动出牌
	}
	
	public Player getCurrentPlayer(){
		if (turn == 0){
			curPlayer = p0;
		}
		else{
			curPlayer = p1;
		}
		return curPlayer;
	}
	
	public Player getOtherPlayer(){
		if (turn == 1){
			otherPlayer = p0;
			if (p0 == null)
				System.out.println("p0 == null!");
		}
		else{
			otherPlayer = p1;
			if (p1 == null)
				System.out.println("p1 == null!");
		}
		if (otherPlayer == null)
			System.out.println("otherPlayer == null!");
		return otherPlayer;
	}
	
	@Override
	public void createScene() {
		//画显示己方血量的Text
		p0.setHP(p0.getMaxHP());
		p0.HPtext.setPosition(rm.camera.getWidth() - 20 - p0.HPtext.getWidth(),
				rm.camera.getHeight() - 150 - p0.HPtext.getHeight());
		attachChild(p0.HPtext);
		//画显示对方血量的Text
		p1.setHP(p1.getMaxHP());
		p1.HPtext.setPosition(rm.camera.getWidth() - 20 - p1.HPtext.getWidth(),
				rm.camera.getHeight() - 170 - 2 * p0.HPtext.getHeight());
		attachChild(p1.HPtext);
		//显示扣血、加血效果的text
		hpText = new Text(110,110,rm.cardFont,"-1000", 5, rm.vbo);
		hpText.setVisible(false);
		
		rm.camera.setHUD(fightHUD);
		fightHUD.attachChild(hpText);
		
		gameOverText = new Text(0, 0, rm.font, "FAILED!", 8, rm.vbo);
		
		/**
		 * 画begin按钮
		 * TODO 画一些Text来显示回合数等信息
		 */
		beginButton = new Sprite(rm.camera.getWidth() - 130, rm.camera.getHeight() - 130,
				rm.fighting_begin_region, rm.vbo){
				@Override
				public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
					if (pSceneTouchEvent.isActionDown()){
						FightingScene scene = SceneManager.getInstance().getFightingScene();
						scene.unregisterTouchArea(this);
						scene.beforeFighting();
						scene.calculate();
						return true;
					}
					return false;
				}
			};
		attachChild(beginButton);
		
		skillEffect = SkillEffect.getInstance();
		
        SceneManager.getInstance().setScene(this);
		//开始抽卡
		getCardToHand();
	}

	@Override
	public void onBackKeyPressed() {
		// TODO Auto-generated method stub
		System.exit(0);
	}

	@Override
	public ESceneID getSceneID() {
		return ESceneID.fighting;
	}

	@Override
	public void disposeScene() {
		
		gameOverText.detachSelf();
		gameOverScene.detachSelf();
		gameOverScene.dispose();
		this.clearChildScene();
		this.clearTouchAreas();
		this.clearUpdateHandlers();
		this.detachChildren();
		this.dispose();
		gr .removeAll();
		fightHUD.detachChildren();
		fightHUD.dispose();
		fightHUD = null;
		p0 = null;
		p1 = null;
		curPlayer = null;
		otherPlayer = null;
		beginButton = null;
		gameOverScene = null;
		gameOverText = null;
		hpText = null;
		myCard = null;
	}

	public HUD getHUD(){
		return fightHUD;
	}
	
	public int getTurn(){
		return turn;
	}
}
