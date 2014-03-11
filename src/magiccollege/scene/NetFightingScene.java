package magiccollege.scene;

import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

import java.io.IOException;
import java.util.ArrayList;

import magiccollege.fight.Card;
import magiccollege.fight.Player;
import magiccollege.fight.Skill;
import magiccollege.fight.SkillEffect;
import magiccollege.main.MyData;
import magiccollege.manager.ResourceManager;
import magiccollege.manager.SceneManager;
import magiccollege.net.clientMessage.AfterAttackClientMessage;
import magiccollege.net.clientMessage.AfterCalculateClientMessage;
import magiccollege.net.clientMessage.BeforeCalculateClientMessage;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.ParallelEntityModifier;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.modifier.IModifier;
import org.andengine.util.modifier.ease.EaseSineInOut;

public class NetFightingScene extends FightingScene {
	private boolean done = true;
	
	/**
	 * 判断混乱状态
	 */
	@Override
	public void judgeMessState(){
		done = false;
		super.judgeMessState();
	}
	
//	/**
//	 * 游戏结束时调用
//	 */
//	@Override
//	protected void gameOver(){
//		super.gameOver();
//		if (turn == 0){
//			GameOverClientMessage gocm = new GameOverClientMessage();
//			gocm.set(String.valueOf(MyData.getInstance().getId()), rm.battleKey, rm.randoms);
//			try {
//				rm.serverConnector.sendClientMessage(gocm);
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//		
//	}
	
	/** 
	 * 调整场上的卡牌位置
	 * */
	@Override
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
		}else{
			if (getTurn() == 0){
				AfterAttackClientMessage aacm = new AfterAttackClientMessage();
				aacm.set(String.valueOf(MyData.getInstance().getId()), rm.battleKey, getOtherPlayer().desktop.size(), getCurrentPlayer().desktop.size());
				try {
					rm.serverConnector.sendClientMessage(aacm);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	@Override
	/** 
	 * 继续调整场上的卡牌位置
	 * */
	protected void continueAdjustGround(final ArrayList<Card> list){
		//System.out.println("continue 调整场上卡牌位置，当前index为"+index);
		if (index >= list.size()){
			done = true;
			return;
		}
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
					if (getTurn() == 0){
						AfterAttackClientMessage aacm = new AfterAttackClientMessage();
						aacm.set(String.valueOf(MyData.getInstance().getId()), rm.battleKey, getOtherPlayer().desktop.size(), getCurrentPlayer().desktop.size());
						try {
							rm.serverConnector.sendClientMessage(aacm);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					done = true;
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
	
	@Override
	public void continueCalculate(){
		if (index >= DEFAULT_COLS){
			index = 0;
			done = true;
			if (turn != 0){
				AfterCalculateClientMessage accm = new AfterCalculateClientMessage();
				accm.set(String.valueOf(MyData.getInstance().getId()), rm.battleKey);
				try {
					rm.serverConnector.sendClientMessage(accm);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return;
		}
		Card curCard= gr.get(new Location(turn,index));
		if (curCard == null){
			index = 0;
			done = true;
			if (turn != 0){
				AfterCalculateClientMessage accm = new AfterCalculateClientMessage();
				accm.set(String.valueOf(MyData.getInstance().getId()), rm.battleKey);
				try {
					rm.serverConnector.sendClientMessage(accm);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return;
		}
		myCard.add(curCard);
		
		///////////////////////////////
		for (int i = 0; i < 10; i++)
			rm.randoms.add(Math.random());
		///////////////////////////////
		
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
							Player otherPlayer = getOtherPlayer();
							if (otherPlayer == p0){
								otherPlayer.desktop.add(new Card(removeC){
									@Override
									public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
										if (pSceneTouchEvent.isActionDown()){
											if (this.isClicked()){
												SceneManager.getInstance().setScene(new CardInfoScene(this, NetFightingScene.this));
												return true;
											}else{
												this.setClicked(true);
												NetFightingScene.this.setCardToGround(this);			
												return true;
											}
										}
										return false;
									}});
							}else{
								otherPlayer.desktop.add(new Card(removeC){
									@Override
									public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
										if (pSceneTouchEvent.isActionDown()){
												SceneManager.getInstance().setScene(new CardInfoScene(this, NetFightingScene.this));
												return true;
										}
										return false;
									}});
							}
								
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
	 * p1
	 */
	public void setCardToGround(){
		done = false;
		Grid<Card> handGrid = getCurrentPlayer().getHandGrid();
		for (int i = 0; i < rm.cardHashMap.size(); i++){
			int key = rm.cardHashMap.keyAt(i);
			int value = rm.cardHashMap.get(key);
			Card card = handGrid.get(new Location(0, key));
			card.setClicked(true);
			Location loc = new Location(turn, value);
			//new 一个新卡
			Card tempCard = new Card(card){
				@Override
				public boolean onAreaTouched(
						final TouchEvent pSceneTouchEvent,
						final float pTouchAreaLocalX,
						final float pTouchAreaLocalY) {
					if (pSceneTouchEvent.isActionDown()) {
						FightingScene scene = SceneManager.getInstance().getFightingScene();
						SceneManager.getInstance().setScene(new CardInfoScene(this, scene));
						return true;
					}
					return false;
				}
			};
			//将新卡放到场上
			tempCard.setWhere(Card.ON_GROUND);
			tempCard.putSelfInGrid(gr, loc);
			//判断显示位置并绘制卡片图形
			if (turn == 0){
				tempCard.setPosition(10 + value * (10 + card.getWidth()),
						rm.camera.getHeight() - 30 - 2 * card.getHeight());
			}
			else
				tempCard.setPosition(10 + value * (10 + card.getWidth()), 30 + card.getHeight());
			registerTouchArea(tempCard);
			attachChild(tempCard);
		}
		beforeFighting();
		calculate();
	}
	
	/**
	 * 从手牌到场上
	 * 移除操作放到按了begin之后处理
	 * */
	@Override
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
									
									rm.cardHashMap.delete(card.getLocation().getCol());
									
									return true;
								}		
							}
							return false;
						}};
					//将新卡放到场上
					tempCard.setWhere(Card.ON_GROUND);
					tempCard.putSelfInGrid(gr, loc);
					
					rm.cardHashMap.append(card.getLocation().getCol(), loc.getCol());
					
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
	
	@Override
	protected void afterGettingCard(){
		//Enable touch-setting event
		Grid<Card> handGrid = getCurrentPlayer().getHandGrid();//得到手牌区grid
		for (Location loc :handGrid.getOccupiedLocations()){//对于手牌区每一张卡
			handGrid.get(loc).setClicked(false);
		}
		if (getCurrentPlayer() == p0){//如果是己方行动
			NetFightingScene.this.registerTouchArea(beginButton);//enable 开始按钮
		}
	}
	
	/**
	 * 从卡组抽卡到手牌
	 * */
	public void getCardToHand(final int r){
//		System.out.println("begin 抽卡");
		Player curP = getCurrentPlayer();//得到当前player
		if (!curP.desktop.isEmpty()){//如果牌堆不为空的话
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
							NetFightingScene.this.registerTouchArea(card);//使卡牌可以被touch
							NetFightingScene.this.afterGettingCard();
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
	
	public boolean isDone(){
		return done;
	}

	public NetFightingScene(Player pp1, Player pp2) {
		super(pp1, pp2);
	}

	@Override
	public void createScene() {
		// 画显示己方血量的Text
		p0.setHP(p0.getMaxHP());
		p0.HPtext.setPosition(rm.camera.getWidth() - 20 - p0.HPtext.getWidth(),
				rm.camera.getHeight() - 150 - p0.HPtext.getHeight());
		attachChild(p0.HPtext);
		// 画显示对方血量的Text
		p1.setHP(p1.getMaxHP());
		p1.HPtext.setPosition(rm.camera.getWidth() - 20 - p1.HPtext.getWidth(),
				rm.camera.getHeight() - 170 - 2 * p0.HPtext.getHeight());
		attachChild(p1.HPtext);
		// 显示扣血、加血效果的text
		hpText = new Text(110, 110, rm.cardFont, "-1000", 5, rm.vbo);
		hpText.setVisible(false);

		rm.camera.setHUD(fightHUD);
		fightHUD.attachChild(hpText);

		gameOverText = new Text(0, 0, rm.font, "FAILED!", 8, rm.vbo);

		/**
		 * 画begin按钮 TODO 画一些Text来显示回合数等信息
		 */
		beginButton = new Sprite(rm.camera.getWidth() - 130,
				rm.camera.getHeight() - 130, rm.fighting_begin_region, rm.vbo) {
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent,
					final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				if (pSceneTouchEvent.isActionDown()) {
					FightingScene scene = SceneManager.getInstance().getFightingScene();
					scene.unregisterTouchArea(this);
					//sendMessage
					ArrayList<Integer> message = new ArrayList<Integer>();
					for (int i = 0; i< rm.cardHashMap.size(); i++){
						int key = rm.cardHashMap.keyAt(i);
						int value = rm.cardHashMap.get(key);
						message.add(key);
						message.add(value);
					}
					BeforeCalculateClientMessage bccm = new BeforeCalculateClientMessage();
					bccm.set(String.valueOf(MyData.getInstance().getId()), rm.battleKey, message);
					try {
						rm.serverConnector.sendClientMessage(bccm);
					} catch (IOException e) {
						e.printStackTrace();
					}
					done = false;
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
	}

}
