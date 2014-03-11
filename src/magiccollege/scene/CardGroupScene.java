package magiccollege.scene;

import java.util.ArrayList;

import magiccollege.Enum.ESceneID;
import magiccollege.fight.Card;
import magiccollege.fight.Player;
import magiccollege.manager.ResourceManager;
import magiccollege.manager.SceneManager;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.input.touch.detector.ScrollDetector;
import org.andengine.input.touch.detector.SurfaceScrollDetector;
import org.andengine.input.touch.detector.ScrollDetector.IScrollDetectorListener;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.util.GLState;
/**
 * 本程序的构造函数传入的是Player，
 * 然后对player的的mycards和Cardgroup编辑，
 * 编辑后的结果保存在cardgroup中，cardgroup中存储的是player现所用的卡牌的key，
 * 通过setCardGroup函数实现， 在按返回键时执行该函数
 *备注：我在loadUserDara中给user多加了两张卡牌用于测试，
 * @author jack
 */

public class CardGroupScene extends MyScene implements IOnSceneTouchListener,
IScrollDetectorListener {
	
	
	private SurfaceScrollDetector mScrollDetector;
	/**
	 * 基本坐标，用来确定卡槽
	 */
	private float BaseX = 150;
	private float BaseY = 0;
	
	private float []pX = {150,260,370,480,590,150,260,370,480,590};
	private float []pY = {0,0,0,0,0,150,150,150,150,150};
	
	private Sprite backGround;
	
	private static final int maxCardNum = 10;//最大手牌数量
	
	private Player player;
	
	private ResourceManager rm = ResourceManager.getInstance();
	
	private ArrayList<Card> mycards = new ArrayList<Card>();
	private ArrayList<Card> handarray = new ArrayList<Card>();
	ArrayList<Integer> cardgroup = new ArrayList<Integer>();//存储卡牌的key

	
	public CardGroupScene(Player p ){
		
		player = p;
		
	}
	
	@Override
	public void createScene() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/card/");
		createBackground();
		for(int i = 0; i < player.getCardGroup().length; i++){
			int localKey = player.getCardGroup()[i];
			cardgroup.add(localKey);
		}
		for(int i = 0; i < player.myCards.size(); i++){
			Card card = player.myCards.get(i);
//			ITextureRegion card_region = BitmapTextureAtlasTextureRegionFactory
//					.createFromAsset(rm.cardTextureAtlas, rm.activity.getAssets(),"testCard.png", 64 * i, 0);//testCard.png 须根据卡名变化
			Card tempCard  = new Card(card, rm.findTextureRegionByCardName(card.getName())){
				@Override
				public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
					
					if (pSceneTouchEvent.isActionDown()){
						
						if(this.getX()>10&&this.getX()<100){
							
							CardInfoScene cardInfoScene = new CardInfoScene(this,CardGroupScene.this);
							SceneManager.getInstance().setScene(cardInfoScene);
						}
						else{
							if(!isHand(this))
							{
								 this.setAlpha(0.5f);
							     createHandCard(this);
							     cardgroup.add(this.key);
							}
						}
							
						return true;
					}
					return false;
				}
			};
			if (isHand(tempCard)){
				createHandCard(tempCard);
				tempCard.setAlpha(0.5f);
			}
			tempCard.setPosition(BaseX+110*i,BaseY+340);
			mycards.add(tempCard);
			attachChild(tempCard);
			registerTouchArea(tempCard);
		}
		setTouchAreaBindingOnActionDownEnabled(true);
		enableTouchDetectors(); // NEW		
	}



	@Override
	public void onBackKeyPressed() {
		setCardgGroup(); //返回时保存数据
		disposeScene();
		ResourceManager.getInstance().camera.setHUD(SceneManager.getInstance().getGameScene().getHUD());
		ResourceManager.getInstance().camera.setChaseEntity(ResourceManager.getInstance().player);
		SceneManager.getInstance().setScene(ESceneID.game);
		}

	@Override
	public ESceneID getSceneID() {
		return ESceneID.editing;
	}

	@Override
	public void disposeScene() {
		ResourceManager.getInstance().unloadCardGroupTexture();
		this.dispose();
	}

	
	@Override
	public void onScroll(final ScrollDetector pScollDetector, final int pPointerID,
	    final float pDistanceX, final float pDistanceY) {
	    
	    	if(pDistanceX<0){
	    		moveToLeft();
	    	}else{
	    		moveToRight();
	    	}
	    	cardComeHere();
	    	setScale();
	    	resetScale();
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
	
	private void enableTouchDetectors() {
	    this.mScrollDetector = new SurfaceScrollDetector(this);
	  
	    setOnSceneTouchListener(this);
	    setTouchAreaBindingOnActionDownEnabled(true);
	}

	public void moveToLeft(){
		for(int i = 0 ; i < mycards.size(); i++){
			mycards.get(i).setPosition(mycards.get(i).getX()-110,mycards.get(i).getY());
		}
	}
	
	public void moveToRight(){
		for(int i = 0 ; i < mycards.size(); i++){
			mycards.get(i).setPosition(mycards.get(i).getX()+110,mycards.get(i).getY());
		}
	}

	public void setScale(){
		for(int i = 0 ; i < mycards.size(); i++){
			if(mycards.get(i).getX()<100&&mycards.get(i).getX()>10){
				mycards.get(i).setScale(1.4f);
				break;
			}
		}	
	}
	public void resetScale(){
		for(int i = 0 ; i < mycards.size(); i++){
			if(mycards.get(i).getX()>100||mycards.get(i).getX()<10){
				mycards.get(i).reset();
			}
		}
	}
	
	
	//处理滑动，禁止卡牌滑出界面
	private void cardComeHere(){
		
		if(mycards.get(0).getX()>(BaseX)){
			
			mycards.get(0).setPosition(BaseX,mycards.get(0).getY());
			
			for(int i = 1 ; i < mycards.size(); i++){
				mycards.get(i).setPosition(mycards.get(0).getX()+110*i, mycards.get(0).getY());
			}
			
		}
        if(mycards.get(mycards.size()-1).getX()<10){
        	
        	mycards.get(mycards.size()-1).setPosition(BaseX-110,mycards.get(mycards.size()-1).getY());
			
			for(int i = mycards.size()-1; i > 0 ; i--){
				mycards.get(i-1).setPosition(mycards.get(mycards.size()-1).getX()-110*(mycards.size()-i), mycards.get(mycards.size()-1).getY());
			}
			
		}
		
	}
	
	private void createHandCard(Card pCard){
		
		if(handarray.size()<maxCardNum){
			       Card tempCard = new Card(pCard){
						@Override
						public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
						
							if (pSceneTouchEvent.isActionDown()){
								unregisterTouchArea(this);
								for (int j = 0; j < mycards.size(); j++){
									if (mycards.get(j).key == this.key){
										mycards.get(j).setAlpha(1f);
										break;
									}
								}
								
								for(int j = 0; j < handarray.size();j++){
									Card removeCard = handarray.get(j);
									 if(removeCard.key==this.key){
										 removeCard.detachSelf();
										 removeCard.dispose();
										 handarray.remove(j);
										 break;
									 }
								 }
								
								for(int j = 0; j < cardgroup.size();j++){
									 if(cardgroup.get(j) == this.key){
										 cardgroup.remove(j);
										 break;
									 }
								 }
								
								setHandCard();
								
								return true;
							}
							return false;
						}
					};
				
				handarray.add(tempCard);
				setHandCard();
				attachChild(tempCard);
	 			registerTouchArea(tempCard);
				}
	}

	private void setHandCard(){
		
		for(int i = 0; i < handarray.size(); i++){
			handarray.get(i).setPosition(pX[i],pY[i]);
		}
		
	}
	
	//修改传入的player的的cardgroup，完成编辑
	public void setCardgGroup(){
		/*
		cardgroup.clear();
		for(int i = 0; i < handarray.size(); i++){
			cardgroup.add(handarray.get(i).key);
		}
		*/
		int []temp = new int[cardgroup.size()];
		for(int i = 0 ; i < cardgroup.size(); i++){
			temp[i] = cardgroup.get(i);
		}
		player.setCardGroup(temp);
	}
	
	
	//判断被点击卡牌是否在cardgroup中
	private boolean isHand(Card c){
		for(int j = 0; j < cardgroup.size();j++ ){
			if(cardgroup.get(j)==c.key)
				return true;
		}
		return false;
		
	}
	
	private void createBackground()
	{
		backGround = new Sprite(0, 0, ResourceManager.getInstance().editing_BackGroundTextureRegion, ResourceManager.getInstance().vbo)
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
	/*
	//初始化player所拥有的卡牌界面界面
	private void init(){
		for(int i = 0; i < player.getCardGroup().length; i++){
			int localKey = player.getCardGroup()[i];
			cardgroup.add(localKey);
			int index = 0;
			for(; index < player.myCards.size(); index++){
				if(player.myCards.get(index).key == localKey){
					break;
				}
			}
			if (index == player.myCards.size()){
				break;
			}
			Card card = player.myCards.get(index);
			ITextureRegion card_region = BitmapTextureAtlasTextureRegionFactory
					.createFromAsset(rm.cardTextureAtlas, rm.activity.getAssets(),"testCard.png", 64 * i, );//testCard.png 须根据卡名变化
			Card tempCard = new Card(card,card_region){
				@Override
				public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
					unregisterTouchArea(this);
					if (pSceneTouchEvent.isActionDown()){
						
						for(int j = 0; j < cardgroup.size(); j++){
							 if(cardgroup.get(j) == this.key){
								 cardgroup.remove(j);
								 break;
							 }
						 }
						
						for (int j = 0; j < mycards.size(); j++){
							if (mycards.get(j).key == this.key){
								mycards.get(j).setAlpha(1f);
								break;
							}
						}
						
						for(int j = 0; j < handarray.size(); j++){
							 Card removeCard = handarray.get(j);
							 if(removeCard.key==this.key){
								 removeCard.detachSelf();
								 removeCard.dispose();
								 handarray.remove(j);
								 break;
							 }
						 }
						
						setHandCard();
						
						return true;
					}
					return false;
				}
			};
			
			handarray.add(tempCard);
			
			
			attachChild(tempCard);
 			registerTouchArea(tempCard);
		}
		setHandCard();
	}
	 */

}
