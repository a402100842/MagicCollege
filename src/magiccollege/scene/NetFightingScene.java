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
	 * �жϻ���״̬
	 */
	@Override
	public void judgeMessState(){
		done = false;
		super.judgeMessState();
	}
	
//	/**
//	 * ��Ϸ����ʱ����
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
	 * �������ϵĿ���λ��
	 * */
	@Override
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
	 * �����������ϵĿ���λ��
	 * */
	protected void continueAdjustGround(final ArrayList<Card> list){
		//System.out.println("continue �������Ͽ���λ�ã���ǰindexΪ"+index);
		if (index >= list.size()){
			done = true;
			return;
		}
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
			//new һ���¿�
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
			//���¿��ŵ�����
			tempCard.setWhere(Card.ON_GROUND);
			tempCard.putSelfInGrid(gr, loc);
			//�ж���ʾλ�ò����ƿ�Ƭͼ��
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
	 * �����Ƶ�����
	 * �Ƴ������ŵ�����begin֮����
	 * */
	@Override
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
									
									rm.cardHashMap.delete(card.getLocation().getCol());
									
									return true;
								}		
							}
							return false;
						}};
					//���¿��ŵ�����
					tempCard.setWhere(Card.ON_GROUND);
					tempCard.putSelfInGrid(gr, loc);
					
					rm.cardHashMap.append(card.getLocation().getCol(), loc.getCol());
					
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
	
	@Override
	protected void afterGettingCard(){
		//Enable touch-setting event
		Grid<Card> handGrid = getCurrentPlayer().getHandGrid();//�õ�������grid
		for (Location loc :handGrid.getOccupiedLocations()){//����������ÿһ�ſ�
			handGrid.get(loc).setClicked(false);
		}
		if (getCurrentPlayer() == p0){//����Ǽ����ж�
			NetFightingScene.this.registerTouchArea(beginButton);//enable ��ʼ��ť
		}
	}
	
	/**
	 * �ӿ���鿨������
	 * */
	public void getCardToHand(final int r){
//		System.out.println("begin �鿨");
		Player curP = getCurrentPlayer();//�õ���ǰplayer
		if (!curP.desktop.isEmpty()){//����ƶѲ�Ϊ�յĻ�
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
							NetFightingScene.this.registerTouchArea(card);//ʹ���ƿ��Ա�touch
							NetFightingScene.this.afterGettingCard();
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
	
	public boolean isDone(){
		return done;
	}

	public NetFightingScene(Player pp1, Player pp2) {
		super(pp1, pp2);
	}

	@Override
	public void createScene() {
		// ����ʾ����Ѫ����Text
		p0.setHP(p0.getMaxHP());
		p0.HPtext.setPosition(rm.camera.getWidth() - 20 - p0.HPtext.getWidth(),
				rm.camera.getHeight() - 150 - p0.HPtext.getHeight());
		attachChild(p0.HPtext);
		// ����ʾ�Է�Ѫ����Text
		p1.setHP(p1.getMaxHP());
		p1.HPtext.setPosition(rm.camera.getWidth() - 20 - p1.HPtext.getWidth(),
				rm.camera.getHeight() - 170 - 2 * p0.HPtext.getHeight());
		attachChild(p1.HPtext);
		// ��ʾ��Ѫ����ѪЧ����text
		hpText = new Text(110, 110, rm.cardFont, "-1000", 5, rm.vbo);
		hpText.setVisible(false);

		rm.camera.setHUD(fightHUD);
		fightHUD.attachChild(hpText);

		gameOverText = new Text(0, 0, rm.font, "FAILED!", 8, rm.vbo);

		/**
		 * ��begin��ť TODO ��һЩText����ʾ�غ�������Ϣ
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
