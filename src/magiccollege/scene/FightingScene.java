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
	protected Player curPlayer;//����ֱ��ʹ�ã���getCurrentPlayer();
	protected Player otherPlayer;//����ֱ��ʹ�ã���getOtherPlayer();
	protected int turn;
	protected int index;//��������ѭ��
	protected int skillIndex;//�������Ʒ������ܵ�ѭ��
	
	protected SkillEffect skillEffect;
	
	protected ArrayList<Card> myCard;//��ǰ�ж������ϵĿ�
	
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
		System.out.println("begin �������Զ�����");
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
	 * �����Ƴ�������Ĺ�ز�������Ч��(if any)
	 * @param card ��kill�Ŀ���
	 * @param owner ���ƵĹ�����
	 *//*
	public static void killCard(Card card, Player owner){
		if (card.getHP() <= 0){
			if (card.hasSkill(10)){//����С���ը��
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
	 * �����ƴ����ƻ�������Ĺ��
	 * @param card ��kill�Ŀ���
	 * @param owner ���ƵĹ�����
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
	 * ��Ϸ����ʱ����
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
		//����Ĺ���н�ʬ������
		int zombieNum = 0;
		for (Card theCard : p0.grave){
			if (theCard.getName() == "Zombie")
				zombieNum++;
		}
		//�ж�ʤ��
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
	 * �ӿ���鿨������
	 * */
	private void getCardToHand(){
//		System.out.println("begin �鿨");
		Player curP = getCurrentPlayer();//�õ���ǰplayer
		if (!curP.desktop.isEmpty()){//����ƶѲ�Ϊ�յĻ�
			int r = (int) (Math.random() * curP.desktop.size());//���ƶ������ѡһ�ſ�;
			Card nextCard = null;//����Ѱ�������пյ�λ��
			for (int i=0; i<DEFAULT_HAND_NUM; i++){
				Location loc = new Location(0, i);//����ÿһ��Location
				nextCard = curP.getHandGrid().get(loc);//�õ����Location�ϵĿ���if any��
				if (nextCard == null){//������Location��û�п�����˵�����Դ��ƶѳ�һ�ſ��ŵ��˴�
					final Card card = curP.desktop.remove(r);//���ƶ����Ƴ�һ�ſ�������������card
					float fromX = rm.camera.getWidth();//card�����X���꿪ʼ����
					float fromY = 10f;//card�����Y���꿪ʼ����
					float toX = 10 + i * (10 + card.getWidth());//card��Ҫ���ŵ���λ�õ�X����
					float toY = 10f;//card��Ҫ���ŵ���λ�õ�Y����
					if (turn != 0){//����ǶԷ��Ļغ�
						card.setClicked(true);//ʹ���Ʊ����ʱֻ����CardInfoScene
					}else{//������Լ��غ�
						fromY = toY = rm.camera.getHeight() - 10 - card.getHeight();//���ñ��ŵ���λ�õ�Y����
					}
					card.putSelfInGrid(curP.getHandGrid(), loc);//�����ſ��ŵ�������grid(��û��ʾ����)
					card.setWhere(Card.ON_HAND);//�����ſ���¼λ�õı�������Ϊ��ON_HAND��
					final ParallelEntityModifier entityModifier =	new ParallelEntityModifier(new IEntityModifierListener() {
						@Override
						public void onModifierStarted(final IModifier<IEntity> pModifier, final IEntity pItem) {
							
							pModifier.setAutoUnregisterWhenFinished(true);
						}

						@Override
						public void onModifierFinished(final IModifier<IEntity> pEntityModifier, final IEntity pEntity) {
							FightingScene.this.registerTouchArea(card);//ʹ���ƿ��Ա�touch
							FightingScene.this.afterGettingCard();
						}
					},
							new AlphaModifier(1, 0f, 1),//1��֮�ڴ�͸���䲻͸��
							new MoveModifier(1, fromX, toX,fromY, toY, EaseSineInOut.getInstance())//���ƶ�λ���ƶ���������ĳ��λ��
					);//�ƶ���������鿨Ч��
					card.setPosition(fromX, fromY);
					card.setAlpha(0f);
					attachChild(card);//��������ʾ����
					card.registerEntityModifier(entityModifier);
					break;//��Ϊ�ҵ��˿յ�λ�����Բ��ü�������
				}
			}
			if (nextCard != null)
				this.afterGettingCard();
		}else
			this.afterGettingCard();
	}
	
	/**
	 * �����Ƶ�����
	 * �Ƴ������ŵ�����begin֮����
	 * */
	public void setCardToGround(final Card card){
		if (card != null){
			card.setAlpha(0.5f);
			//�ҵ�һ��λ�ÿ��Էţ�����do nothing
			for (int i=0; i<DEFAULT_COLS; i++){
				Location loc = new Location(turn,i);
				if (gr.get(loc) == null){
					//new һ���¿�
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
					//���¿��ŵ�����
					tempCard.setWhere(Card.ON_GROUND);
					tempCard.putSelfInGrid(gr, loc);
					//�ж���ʾλ�ò����ƿ�Ƭͼ��
					if (turn == 0){
						tempCard.setPosition(10 + i * (10 + card.getWidth()),
								rm.camera.getHeight() - 30 - 2 * card.getHeight());
						//registerTouchArea(tempCard);//����ǶԷ��򼺷�����touch�Է��Ŀ�
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
	 * ���������Ҫ���Ŀ���ʹ������Ӧ���ʱ��ʾ��ϸ��Ϣ
	 */
	protected void beforeFighting(){
		//ʹ���ϵĿ���Ӧ���ʱֻ��ʾ��ϸ��Ϣ
		for (int i=0; i<DEFAULT_COLS; i++){
			Card card = gr.get(new Location(turn,i));
			if (card != null){
				card.setClicked(true);
				card.avantInit();
			}else
				break;
		}
		Grid<Card> handGrid = getCurrentPlayer().getHandGrid();
		//ʹ���ƵĿ����ʱֻ��ʾ��ϸ��Ϣ
		for (int i=0; i<DEFAULT_HAND_NUM; i++){
			Card card = handGrid.get(new Location(0, i));
			if (card != null){
				if (card.isClicked()){//remove����Ҫ���ƵĿ�
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
	 * �ж��������Ƿ��С����ܡ�����
	 */
	public void judge82Charge(final Card curCard){
		//System.out.println("begin ����");
		if (curCard != null){
			if (curCard.hasSkill(82) && !curCard.banTreat){
				skillEffect.charge(curCard);
				return;
			}
		}
		judgePoisonState(curCard);
	}
	
	/**
	 * �ж��������Ƿ��С��ָ�������
	 */
	protected void judge81Recover(final Card curCard){
		//System.out.println("begin �ָ�");
		if (curCard != null){
			if (curCard.hasSkill(81) && !curCard.banTreat){
				skillEffect.recover(curCard);
				return;
			}
		}
		judge82Charge(curCard);
	}
	
	/**
	 * �ж��������Ƿ��С�����������
	 */
	protected void judge19Roar(final Card curCard, final Card targetCard){
		//System.out.println("begin ����");
		if (curCard != null && targetCard != null){
			if (curCard.hasSkill(19)){
				if (curCard.attackcount2 > 0){
					curCard.attackcount2--;
					if (curCard.hasSkill(99))
						curCard.attackcount = 2;//���á���ɨ������
					skillEffect.roar(curCard, targetCard);
					return;
				}
			}
		}
		judge81Recover(curCard);
	}
	
	/**
	 * �ж��������Ƿ��С���ɨ������
	 */
	public void judge99SweepAway(final Card curCard, final Card targetCard){
		//System.out.println("begin ��ɨ");
		if (curCard != null){//important to judge if it is null
			Location curLoc = curCard.getLocation();
			if (curCard.hasSkill(99)){
				Location leftLoc = new Location(Math.abs(curLoc.getRow() - 1),curLoc.getCol() - 1);
				Location rightLoc = new Location(Math.abs(curLoc.getRow() - 1),curLoc.getCol() + 1);
				if (curCard.attackcount == 2){//��ʱӦ�ù����з���ߵĿ�(if any)
					curCard.attackcount --;
					if (gr.isValid(leftLoc)){
						Card leftCard = gr.get(leftLoc);
						if (leftCard != null){
							judge9EarthStab(curCard, leftCard);
							return;
						}
					}
				}
				if (curCard.attackcount == 1){//��ʱӦ�ù����з��ұߵĿ�(if any)
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
	 * �ж��������Ƿ��С����ơ����ܣ�
	 * ��һ�����õĺ���Ϊ{@link FightingScene#judge99SweepAway(Card, Card)}
	 */
	public void judge3SeeThrough(final Card curCard, final Card targetCard){
		//System.out.println("begin ����");
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
	 * �ж��������Ƿ��С��ͻ�������
	 */
	protected void judge15Punch(final Card curCard, final Card targetCard){
		//System.out.println("begin �ͻ�");
		if (curCard != null && targetCard != null){//important to judge if they are null
			if (curCard.hasSkill(15)){
				Skill.getInstance().punch(curCard, targetCard);
			}else
				judge3SeeThrough(curCard, targetCard);
		}else
			judge3SeeThrough(curCard, targetCard);
	}
	
	/**
	 * �ж��������Ƿ��С�ע�䡱����
	 */
	protected void judge98Injection(final Card curCard, final Card targetCard){
		//System.out.println("begin ע��");
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
	 * �ж��������Ƿ��С�˺�ѡ�����
	 */
	public void judge97Tear(final Card curCard, final Card targetCard){
		//System.out.println("begin ˺��");
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
	 * �ж��������Ƿ��С����䡱����
	 */
	protected void judge12Radiate(final Card curCard, final Card targetCard){
		//System.out.println("begin ����");
		if (curCard != null && targetCard != null){//important to judge if they are null
			if (curCard.hasSkill(12)){
				skillEffect.radiate(curCard, targetCard);
			}else
				judge97Tear(curCard, targetCard);
		}else
			judge97Tear(curCard, targetCard);
	}
	
	/**
	 * �ж��������Ƿ��С���Ѫ������
	 */
	protected void judge96Bloodsucking(final Card curCard, final Card targetCard){
		//System.out.println("begin ��Ѫ");
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
	 * �ж϶��濨���Ƿ��С�����������
	 */
	public void judge95Renascence(final Card curCard, final Card targetCard){
		//System.out.println("begin ����");
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
	 * �ж϶��濨���Ƿ��С���Ⱦ�����ܣ�
	 * ��{@link FightingScene#judge92Snarl(Card, Card)}֮�󱻵��ã�
	 * ��һ�����õĺ���Ϊ{@link FightingScene#judge95Renascence(Card, Card)}
	 */
	public void judge13Pollute(final Card curCard, final Card targetCard){
		//System.out.println("begin ��Ⱦ");
		if (curCard != null && targetCard != null){//important to judge if they are null
			if (targetCard.hasSkill(13)){
				skillEffect.pollute(curCard, targetCard);
			}else
				judge95Renascence(curCard, targetCard);
		}else
			judge95Renascence(curCard, targetCard);
	}

	/**
	 * �ж϶��濨���Ƿ��С���С����ܣ�
	 * ��һ�����õĺ���Ϊ{@link FightingScene#judge13Pollute(Card, Card)}
	 */
	public void judge92Snarl(Card curCard, Card targetCard){
		//System.out.println("begin ���");
		if (curCard != null && targetCard != null){//important to judge if they are null
			if (targetCard.hasSkill(92)){
				Skill.getInstance().snarl();//TODO ��������Ч��������ɺ����judge13Pollute(curCard, targetCard);
				judge13Pollute(curCard, targetCard);//temp����ʱcurCard�п���Ϊnull
			}else
				judge13Pollute(curCard, targetCard);
		}else
			judge13Pollute(curCard, targetCard);
	}
	
	
	/**
	 * �ж϶��濨���Ƿ��С����������ܣ�
	 * ���ڶ��濨����������˴���ȥ��targetCardΪnull
	 * @param nextFunc ��һ����Ҫ���õĺ���
	 */
	public void judge94Perdure(Card curCard, Card targetCard, final INextFunc nextFunc){
		//System.out.println("begin ����");
		Player player = getOtherPlayer();
		if (curCard == targetCard){
			player = getCurrentPlayer();
			curCard = null;
		}
		if (targetCard.hasSkill(94)){
			if (Skill.getInstance().getRandom(1) <= 0.8){//�ɹ���������
				targetCard.removeSelfFromGrid();
				int i = 0; 
				for (; i<DEFAULT_HAND_NUM; i++){
					Location loc = new Location(0, i);//����ÿһ��Location
					Grid<Card> grid = player.getHandGrid();
					if (grid.get(loc) == null){//���Location��û�п�
						targetCard.setClicked(true);
						targetCard.putSelfInGrid(grid, loc);
						targetCard.setWhere(Card.ON_HAND);
						this.attachChild(targetCard);
						this.registerTouchArea(targetCard);
					}
				}
				if (i == DEFAULT_HAND_NUM){//��������
					targetCard.setWhere(Card.ON_DESKTOP);
					player.desktop.add(targetCard);
				}
				//TODO ��ʾ����Ч�����ڽ���֮�����nextFunc.nextFunc(curCard, null);
				nextFunc.nextFunc(curCard, null, null);//temp
				return;
			}
		}
		targetCard.removeSelfFromGrid();
		player.grave.add(targetCard);
		nextFunc.nextFunc(curCard, null, null);
		
	}
	
	/**
	 * �ж϶��濨���Ƿ��С���ը�����ܣ����ô˼���ʱ����targetCard�Ѿ�������
	 * ��һ�����õĺ���Ϊ{@link FightingScene#judge94Perdure(Card, Card)}
	 */
	protected void judge10explode(final Card curCard, final Card targetCard, final INextFunc nextFunc){
		//System.out.println("begin ��ը");
		if (targetCard.hasSkill(10)){//����С���ը��
			skillEffect.explode(curCard, targetCard, nextFunc);
		}else{
			judge94Perdure(curCard, targetCard, nextFunc);
		}
	}
	
	/**
	 * ��Ѫ֮���ж��Ƿ������ã����һᴥ����������ʱӦ�����ļ���(if any)
	 * @param curCard	��ǰ�ж��Ŀ���
	 * @param targetCard	Ŀ�꿨��
	 * @param nextFunc ��һ����Ҫ���õĺ���
	 */
	public void afterCutCardHP(final Card curCard, final Card targetCard, final INextFunc nextFunc){
		//System.out.println("begin �Ƿ���ͨ����");
		if (targetCard.getHP() <= 0){//Ŀ�꿨������
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
	 * ��ͨ������Ѫ������
	 * �� {@link FightingScene#judge93Twist(Card, Card, int)}֮�󱻵��ã�
	 * ��һ�����õĺ���Ϊ{@link FightingScene#afterCutCardHP(Card, Card, int)}
	 *//*
	private void cutCardHP(final Card curCard, final Card targetCard, int cut){
		System.out.println("begin ��ͨ��Ѫ");
		skillEffect.cutHPEffect(curCard, targetCard, cut);
	}*/
	
	/**
	 * �ж϶��濨���Ƿ��С�Ť�������ܣ�
	 * �� {@link FightingScene#judge21Strong(Card, Card)}֮�󱻵��ã�
	 * ��һ�����õĺ���Ϊ{@link FightingScene#cutCardHP(Card, Card, int)}
	 */
	public void judge93Twist(final Card curCard, final Card targetCard, int cut){
		//System.out.println("begin Ť��");
		if (targetCard.hasSkill(93)){
			cut -= (int)(cut * 0.25 * (1 + targetCard.getAssistState()));
			skillEffect.twist(curCard, targetCard, cut);
		}else{
			skillEffect.cutHPEffect(curCard, targetCard, cut);
		}
	}
	
	/**
	 * �ж϶��濨���Ƿ��С�ǿ׳�����ܣ�
	 * �� {@link FightingScene#judge11Legerity(Card, Card)}֮�󱻵���
	 * ��һ�����õĺ���Ϊ{@link FightingScene#judge93Twist(Card, Card, int)}
	 */
	protected void judge21Strong(final Card curCard, final Card targetCard){
		//System.out.println("begin ǿ׳");
		int cut = curCard.getTop();
		if (targetCard.hasSkill(21)){//����С�ǿ׳��
			cut = 3 - targetCard.getAssistState();
			if (curCard.getTop() < cut)
				cut = curCard.getTop();
			skillEffect.strong(curCard, targetCard, cut);
		}else{
			judge93Twist(curCard, targetCard, cut);
		}
	}
	
	/**
	 * �ж϶��濨���Ƿ��С����顱���ܣ�
	 * Ŀǰ����Ĳ�������Ϊnull�������º������ô˺����뱣֤����Ĳ�������Ϊnull (2013_08_12 11:41)��
	 * ��1�����������У�����{@link FightingScene#judge21Strong(Card, Card)}��
	 * ��2��������δ���е����ں�ɨ״̬�ĵ�һ���У���������{@link FightingScene#judge99SweepAway(Card, Card)}��
	 * ��3��������δ���У�����{@link FightingScene#judge19Roar(Card, Card)}
	 */
	protected void judge11Legerity(Card curCard, Card targetCard){
		//System.out.println("begin ����");
		if (targetCard.hasSkill(11)){//����С����顱	
			if (Skill.getInstance().getRandom(1) < (0.4+0.2 * curCard.getAssistState())){//����δ����
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
		//��������
		judge21Strong(curCard, targetCard);
	}
	
	/**
	 * ��Ѫ֮���ж��Ƿ������ã�����  ��  �ᴥ����������ʱӦ�����ļ���
	 * @param curCard	��ǰ�ж��Ŀ���
	 * @param targetCard	ǰ������
	 * @param theCard	���������Ŀ���
	 * @param nextFunc ��һ����Ҫ���õĺ���
	 */
	public void afterSimplyCutCardHP(Card curCard, Card targetCard, final Card theCard, final INextFunc nextFunc, final INextFunc nextFunc2){
		//System.out.println("begin �Ƿ�����");
		if (theCard.getHP() <= 0){//��Ŀ�꿨������
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
	 * ��ʾ�ش�Ч���Ŀ�Ѫ������
	 * �ڵ���{@link FightingScene#judge9EarthStab(Card, Card)}֮����ã�
	 * ���������õĺ���Ϊ{@link FightingScene#afterSimplyCutCardHP(Card, Card)}
	 *//*
	private void simplyCutCardHP(final Card curCard, final Card targetCard){
		System.out.println("begin �ش̿�Ѫ");
		//TODO ��ʾЧ��������֮�����afterSimplyCutCardHP(curCard, targetCard);
		targetCard.setHP(targetCard.getHP()-curCard.getTop());
		afterSimplyCutCardHP(curCard, targetCard, new INextFunc(){

			@Override
			public void nextFunc(Card curCard, Card targetCard) {
				FightingScene.this.judge92Snarl(curCard, targetCard);
			}});
	}*/
	
	/**
	 * �ж϶��濨���Ƿ��С��ش̡����ܣ�
	 * Ŀǰ����Ĳ�������Ϊnull�������º������ô˺����뱣֤����Ĳ�������Ϊnull (2013_08_12 11:40)
	 */
	public void judge9EarthStab(Card curCard, Card targetCard){
		//System.out.println("begin �ش�");
		if (curCard.hasSkill(9)){
			skillEffect.simplyCutHPEffect(curCard, targetCard, curCard.getTop());
		}else{
			judge11Legerity(curCard, targetCard);
		}
	}
	
	/**
	 * �ж϶��濨���Ƿ��С����족���ܣ�
	 * ֻ��{@link FightingScene#judgeNumbState(Card)} ���汻���ã���ȷ������Ĳ�����Ϊnull��
	 * ��һ�����õĺ���Ϊ {@link FightingScene#judge9EarthStab(Card, Card)}
	 */
	protected void judge91Pierce(Card curCard, Card targetCard){
		//System.out.println("begin ����");
		if (curCard.hasSkill(91)){
			int change = curCard.tempTopInc;
			curCard.tempTopInc += curCard.getTop();
			change = curCard.tempTopInc - change;
			curCard.setTop(curCard.getTop() + change);
			//TODO ��ʾ����������Ч����������֮�����judge9EarthStab();
			judge9EarthStab(curCard, targetCard);//temp
		}else{
			judge9EarthStab(curCard, targetCard);
		}
	}
	
	/**
	 * �ж������Ƿ��С���ԡ�״̬���еĻ�ֱ��{@link FightingScene#judgePoisonState(Card)}��
	 * ����1�������Է���û�п��������Է�Ӣ��֮�����{@link FightingScene#judge19Roar(Card, Card)}��
	 * 		 ��2�������Է����п�������{@link FightingScene#judge91Pierce(Card, Card)}
	 * @param curCard ����ǰ�ж��Ŀ���
	 */
	protected void judgeNumbState(Card curCard){
		//System.out.println("begin ���");
		if (curCard != null){
			if (!curCard.numb){//�������״̬��������ͨ����
				Card frontCard = gr.get(new Location(Math.abs(turn - 1), index));
				if (frontCard != null){
					judge91Pierce(curCard, frontCard);
				}else{//����Ӣ��
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
	 * ��������
	 */
	public void launchSkill(final Card curCard){
		//System.out.println("begin ��������");
		if (curCard != null){
			if (skillIndex < curCard.skill.length){
				Skill.getInstance().launchSkillByKey(curCard.skill[skillIndex++], curCard);
				/* ��launchSkillByKey������ʾ��Ч����Ӧ�õ���launchSkill(Card);��;������Ӧ�ô���null*/
				/*skillIndex++; launchSkill(curCard);*/
			}else{
				judgeNumbState(curCard);
			}
		}else{
			this.endOneCardTurn(curCard);
		}
	}
	
	/**
	 * ����һ�ſ����ж�
	 */
	protected void endOneCardTurn(Card curCard){
		System.out.println("begin �����ж�");
		if (curCard != null){//�ж�����ǰ����ʼ����ǰ��Ƭ״̬��Ϣ
			if (curCard.getWhere() == Card.ON_GROUND){
				curCard.setPosition(curCard.getX(), curCard.getY() + 10);
			}
		}
		//���ûѪ���������Ϸ
		if (this.getOtherPlayer().getHP() <= 0 || this.getCurrentPlayer().getHP() <= 0){
			gameOver();
		}else{
			index++;
			this.judgeMessState();//�ֵ���һ�ſ��ж������ж����Ļ���״̬
		}
	}
	
	/**
	 * �ж��ж�Ч��
	 */
	public void judgePoisonState(Card curCard){
		System.out.println("begin �ж�");
		if (curCard != null){
			if (curCard.poison){
				int cut = 2;//��ʱ��Ϊ�ж���2��Ѫ
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
		endOneCardTurn(curCard);//����һ�ſ����ж�
	}
	
	/**
	 * �����غ�
	 */
	protected void endTurn(){
		System.out.println("begin �����غ�");
		/*�ж�˫������һ���Ŀ����Ƿ��������*/
		//����Ĺ���н�ʬ������
		int zombieNum = 0;
		for (Card theCard : p0.grave){
			if (theCard.getName() == "Zombie")
				zombieNum++;
		}
		if (p0.grave.size() - zombieNum >= p0.getCardGroup().length){
			gameOver();
			return;
		}
		
		//����Ĺ���н�ʬ������
		zombieNum = 0;
		for (Card theCard : p1.grave){
			if (theCard.getName() == "Zombie")
				zombieNum++;
		}
		if (p1.grave.size() - zombieNum >= p1.getCardGroup().length){
			gameOver();
			return;
		}
		//���ÿ�Ƭ���������쳣״̬
		Card mycard;
		for (int i=0; i < gr.getNumCols(); i++){
			mycard = gr.get(new Location(turn,i));
			if (mycard != null)
				mycard.init();
		}
		
		//�����غ�
		turn = Math.abs(turn - 1);
		//������һ������λ��
		adjustGround();
	}
	
	/**
	 * �жϻ���״̬
	 */
	public void judgeMessState(){
		System.out.println("begin ����");
		if (index >= myCard.size()){
			endTurn();
		}else{
			Card curCard = myCard.get(index);//��һ�εõ�curCard
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
					judgePoisonState(curCard);//ֱ�������ж��ж�Ч��
				}else{
					skillIndex = 0;
					launchSkill(curCard);//��ʼ������һ������
				}
			}else{
				this.endOneCardTurn(curCard);
			}
		}
	}
	
	/**
	 * 
	 * ����׶�,����Э��״̬�ͳ�������
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
			judgeMessState();//������һ�׶Σ��жϻ���״̬
			return;
		}
		Card curCard= gr.get(new Location(turn,index));
		if (curCard == null){
			index = 0;
			judgeMessState();//������һ�׶Σ��жϻ���״̬
			return;
		}
		myCard.add(curCard);
		Card leftCard,rightCard;
		//�õ���ߺ��ұߵĿ�(if any)
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
		//����<Э��״̬>����
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
		//��ʾ<Э��״̬>����,TODO ��������ʾ
		
		if (!curCard.isSuperSkillDone() && curCard.hasSkill(58)){
			/**
			 * ��������,��ɱ
			 * */
			index++;
			Skill.getInstance().assassinate(curCard);
			return;
		}else{
			/**
			 * ��������,������
			 * */
			if (rightCard != null && leftCard != null){//������ߺ��ұ߶��п�������Բ��ᷢ����������
				if (!curCard.isSuperSkillDone()){//�������û���������п��ܷ���
					int leftSum = curCard.getLeft() + leftCard.getRight();
					int rightSum = curCard.getRight() + rightCard.getLeft();
					if (leftSum == rightSum){//���㷢���������ܵ�����
						Card removeC = gr.get(new Location(Math.abs(turn-1),index));//�õ�ǰ���Ŀ�
						curCard.setSuperSkill(true);//����ǰ����û�п���ֻҪ�����ж��������Ϊ��������
						if (removeC != null){//����ŻضԷ��ƶѲ�������������
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
							//TODO ���촫��Ч������Ч��������ʱ������������
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
	 * �������ϵĿ���λ��
	 * */
	protected void adjustGround(){
		//System.out.println("begin �������Ͽ���λ��");
		Card card;
		ArrayList<Card> list = new ArrayList<Card>();//�ҵ����ϵ�ǰ�����Ʒŵ�һ����ʱ��list����
		for (int i = 0; i < DEFAULT_COLS; i++){//���ڳ���ÿһ��Location
			card = gr.get(new Location(turn,i));//�õ����Location�ϵ�card
			if (card != null){
				list.add(card);//��ʱ��list�����¼�����ſ�
			}
		}
		//System.out.println("list������˵�ǰ���������п��ƣ�sizeΪ"+list.size());
		//��list����ȡ��Card�ƶ���������ȷλ��
		if (!list.isEmpty()){
			index  = 0;
			continueAdjustGround(list);
		}else
			getCardToHand();//��ǰ����ʼ�鿨
	}
	
	/** 
	 * �����������ϵĿ���λ��
	 * */
	protected void continueAdjustGround(final ArrayList<Card> list){
		//System.out.println("continue �������Ͽ���λ�ã���ǰindexΪ"+index);
		if (index >= list.size())
			return;
		//System.out.println("list size:"+list.size());
		final Card card = list.get(index);//��list��ȡ���� i �ſ�
		//System.out.println("�õ��˵�"+index+"�ſ�");
		float toX = 10 + index * (10 + card.getWidth());//card��Ҫ�����õ�X����
		float fromX = card.getX();//cardԭ�����ڵ�X����
		float toY = 0f;//card��Ҫ�����õ�Y����
		if (turn == 0)
			toY = ResourceManager.getInstance().camera.getHeight() - 30 - 2 * card.getHeight();
		else
			toY = 30 + card.getHeight();
		float fromY = card.getY();//cardԭ�����ڵ�Y����
		
		if (index== list.size() - 1){
			final MoveModifier moveModifier = new MoveModifier(1f, fromX, toX, fromY, toY, new IEntityModifierListener(){
				@Override
				public void onModifierFinished(IModifier<IEntity> pModifier,
						IEntity pItem) {
					//System.out.println("modifier finished");
					FightingScene.this.getCardToHand();//��ǰ����ʼ�鿨
				}
				@Override
				public void onModifierStarted(IModifier<IEntity> pModifier,
						IEntity pItem) {
					pModifier.setAutoUnregisterWhenFinished(true);
				}
			}, EaseSineInOut.getInstance());//1�����ƶ�����ȷλ��
			card.moveTo(new Location(turn ,index));//card��grid����ƶ�
			//System.out.println("card��grid����ƶ�");
			card.registerEntityModifier(moveModifier);//ע��entityModifier
		}else if (Math.abs(fromX - toX) < 1){//�ж�fromX��toX�Ƿ���ȣ���������ƶ�
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
			}, EaseSineInOut.getInstance());//1�����ƶ�����ȷλ��
			card.moveTo(new Location(turn ,index));//card��grid����ƶ�
			//System.out.println("card��grid����ƶ�");
			
			card.registerEntityModifier(moveModifier);//ע��entityModifier
			//System.out.println("ע��entityModifier");

		}
	}
	
	/**
	 * �鿨֮�����֮ǰҪԤ�����õ�����
	 */
	protected void afterGettingCard(){
		//Enable touch-setting event
		if (getCurrentPlayer() == p0){//����Ǽ����ж�
			Grid<Card> handGrid = getCurrentPlayer().getHandGrid();//�õ�������grid
			for (Location loc :handGrid.getOccupiedLocations()){//����������ÿһ�ſ�
				handGrid.get(loc).setClicked(false);//ʹ����Ա����֮��ŵ�����
			}
			FightingScene.this.registerTouchArea(beginButton);//enable ��ʼ��ť
		}else//����ǶԷ��ж�
			robotLogic();//�Զ�����
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
		//����ʾ����Ѫ����Text
		p0.setHP(p0.getMaxHP());
		p0.HPtext.setPosition(rm.camera.getWidth() - 20 - p0.HPtext.getWidth(),
				rm.camera.getHeight() - 150 - p0.HPtext.getHeight());
		attachChild(p0.HPtext);
		//����ʾ�Է�Ѫ����Text
		p1.setHP(p1.getMaxHP());
		p1.HPtext.setPosition(rm.camera.getWidth() - 20 - p1.HPtext.getWidth(),
				rm.camera.getHeight() - 170 - 2 * p0.HPtext.getHeight());
		attachChild(p1.HPtext);
		//��ʾ��Ѫ����ѪЧ����text
		hpText = new Text(110,110,rm.cardFont,"-1000", 5, rm.vbo);
		hpText.setVisible(false);
		
		rm.camera.setHUD(fightHUD);
		fightHUD.attachChild(hpText);
		
		gameOverText = new Text(0, 0, rm.font, "FAILED!", 8, rm.vbo);
		
		/**
		 * ��begin��ť
		 * TODO ��һЩText����ʾ�غ�������Ϣ
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
		//��ʼ�鿨
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
