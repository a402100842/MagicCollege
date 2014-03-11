package magiccollege.manager;

import java.net.Socket;

import magiccollege.Enum.ESceneID;
import magiccollege.fight.Player;
import magiccollege.net.GameConstants;
import magiccollege.net.MyServerMessageReader;
import magiccollege.net.listener.MySocketConnectionServerConnectorListener;
import magiccollege.scene.AllCardScene;
import magiccollege.scene.CardGroupScene;
import magiccollege.scene.FightingScene;
import magiccollege.scene.GameScene;
import magiccollege.scene.HelpScene;
import magiccollege.scene.LoadingScene;
import magiccollege.scene.MainMenuScene;
import magiccollege.scene.MyRecordScene;
import magiccollege.scene.MyScene;
import magiccollege.scene.NetFightingScene;
import magiccollege.scene.OptionsScene;
import magiccollege.scene.SettingScene;
import magiccollege.scene.SplashScene;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.extension.multiplayer.protocol.client.connector.ServerConnector;
import org.andengine.extension.multiplayer.protocol.shared.SocketConnection;
import org.andengine.ui.IGameInterface.OnCreateSceneCallback;

import android.util.Log;

public class SceneManager implements GameConstants{
	private SplashScene splashScene;
	public MainMenuScene menuScene;
	private GameScene gameScene;
	private LoadingScene loadingScene;
	private FightingScene fightingScene;
	private OptionsScene optionsScene;
	private SettingScene settingScene;
	private AllCardScene allCardScene;
	private HelpScene helpScene;
	private MyRecordScene myRecordScene;
	private CardGroupScene cardGroupScene;
	
	public static final SceneManager INSTANCE = new SceneManager();
	
	private ESceneID currentSceneID = ESceneID.splash;
	private MyScene currentScene;
	
	private ResourceManager rm = ResourceManager.getInstance();
	
	public void createSplashScene(OnCreateSceneCallback pOnCreateSceneCallback){
		rm.loadSplash();
	    splashScene = new SplashScene();
	    currentScene = splashScene;
	    splashScene.createScene();
	    pOnCreateSceneCallback.onCreateSceneFinished(splashScene);
	}
	
	public void disposeSplashScene(){
	    splashScene.disposeScene();
	    splashScene = null;
	    rm.unloadSplash();
	}

	public void createMenuScene()
	{
		rm.loadMenu();
	    loadingScene = new LoadingScene();
	    loadingScene.createScene();
	    setScene(loadingScene);
	   	new Thread(new Runnable(){
			@Override
			public void run() {
				try {
					//		rm.serverConnector = new ServerConnector<SocketConnection>(SocketConnection.create(new InetSocketAddress(SERVER_IP, SERVER_PORT), 5000), new MySocketConnectionServerConnectorListener());
					rm.serverConnector = new ServerConnector<SocketConnection>(new SocketConnection(new Socket(SERVER_IP, SERVER_PORT)), new MyServerMessageReader(), new MySocketConnectionServerConnectorListener());
					rm.serverConnector.start();
					
				} catch (Exception e) {
					disposeSplashScene();
					Log.e("Init Client","登陆失败!" + e.toString());
					System.exit(0);
				}
			}}).start();
	}
	
	public void continueCreateMenuScene(){
		 menuScene = new MainMenuScene();
		 menuScene.createScene();
		 disposeSplashScene();
		 rm.loadUserData();
		 createOptionsScene(rm.user,menuScene);
		 setScene(menuScene);
	}
	
	public void createCardGroupScene(Player p){
		
		rm.reloadCardGroupTexture();
		cardGroupScene = new CardGroupScene(p);
		cardGroupScene.createScene();
		setScene(cardGroupScene);
	}
	
	/**
	 * 生成OptionsScene
	 */
	public void createOptionsScene(Player p,MyScene ms){
		 optionsScene = new OptionsScene(ms);
		 rm.loadOptionsTexture();
		 optionsScene.createScene();
		 createSettingScene();
		 createAllCardScene();
		 createHelpScene();
		 createMyRecordScene();
	}
	public void createSettingScene(){
		settingScene = new SettingScene();
		rm.loadSettingTexture();
		settingScene.createScene();
	}
	public void createAllCardScene(){
		allCardScene = new AllCardScene();
		rm.loadAllcardTexture();
		allCardScene.createScene();
	}
	public void createHelpScene(){
		helpScene = new HelpScene();
		rm.loadHelpTexture();
		helpScene.createScene();
	}
	public void createMyRecordScene(){
		myRecordScene = new MyRecordScene();
		rm.loadPersonTexture();
		myRecordScene.createScene();
	}
	
	public void createGameScene(){
	    setScene(loadingScene);
	    rm.unloadMenuTextures();
	    rm.loadCardGroupTexture();
	    rm.engine.registerUpdateHandler(new TimerHandler(1f, new ITimerCallback() 
	    {
	        public void onTimePassed(final TimerHandler pTimerHandler) 
	        {
	        	rm.engine.unregisterUpdateHandler(pTimerHandler);
	        	rm.loadGameTexture();
	            
	            gameScene = new GameScene(rm.camera);
	            gameScene.createScene();
	            rm.setGame(gameScene);
	            optionsScene.setBackScene(gameScene);
	            setScene(gameScene);
	        }
	    }));
	}
	
	/**
	 * 和npc对话触发战斗
	 * @param p0 自己
	 * @param p1 npc
	 */
	public void createFightingScene(Player p0, Player p1){
		
		gameScene.setIgnoreUpdate(true);
		rm.camera.setHUD(null);
		rm.camera.setChaseEntity(null);
		float[] center = loadingScene.getSceneCenterCoordinates();
		rm.camera.setCenter(center[0], center[1]);
	    setScene(loadingScene);
	    
	    rm.unloadGameTextures();
        
    	fightingScene = new FightingScene(p0 ,p1);
	    rm.loadFightingTexture(p0 ,p1);
		p0.setDesktop();
		p1.setDesktop();
		fightingScene.createScene();
		
	}
	
	/**
	 * 联网战斗
	 * @param p0 自己
	 * @param p1 对手
	 */
	public void createNetFightingScene(final Player p0, final Player p1){
		
		gameScene.setIgnoreUpdate(true);
		rm.camera.setHUD(null);
		rm.camera.setChaseEntity(null);
		float[] center = loadingScene.getSceneCenterCoordinates();
		rm.camera.setCenter(center[0], center[1]);
		setScene(loadingScene);
		
		rm.unloadGameTextures();
        
    	fightingScene = new NetFightingScene(p0 ,p1);
	    rm.loadNetFightingTexture(p0 ,p1);//TODO
		p0.setDesktop();
		p1.setDesktop();
		fightingScene.createScene();
	}

	/**
	 * 从fightingScene返回时调用
	 */
	public void loadGameScene(){
		
		//setScene(loadingScene);
		rm.reloadGameTextures();
		rm.camera.setHUD(gameScene.getHUD());
		rm.camera.setChaseEntity(rm.player);
		
		fightingScene.disposeScene();
		fightingScene = null;
		rm.unloadFightingTexture();
		
		gameScene.setIgnoreUpdate(false);
		setScene(gameScene);
	}
	
	
	/**
	 * 从gameScene返回时调用
	 */
	public void loadMenuScene(){
		
		rm.camera.setHUD(null);
		rm.camera.setChaseEntity(null);
		float[] center = loadingScene.getSceneCenterCoordinates();
		rm.camera.setCenter(center[0], center[1]);
	    setScene(loadingScene);
	    
	    rm.unloadGameTextures();
	    rm.unloadGame();
	    gameScene.disposeScene();
	    rm.engine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() 
	    {
	        public void onTimePassed(final TimerHandler pTimerHandler) 
	        {
	        	rm.engine.unregisterUpdateHandler(pTimerHandler);
	        	rm.reloadMenuTextures();
	            
	            optionsScene.setBackScene(menuScene);
	            setScene(menuScene);
	        }
	    }));
	}
	
	public void gameSceneSwitch(){
		rm.camera.setHUD(null);
		rm.camera.setChaseEntity(null);
		float[] center = loadingScene.getSceneCenterCoordinates();
		rm.camera.setCenter(center[0], center[1]);
	    setScene(loadingScene);
	    
	    rm.unloadGame();
	    gameScene.disposeScene();
	    rm.engine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() 
	    {
	        public void onTimePassed(final TimerHandler pTimerHandler) 
	        {
	        	rm.engine.unregisterUpdateHandler(pTimerHandler);

	        	gameScene.createScene();
	            rm.setGame(gameScene);
	            
	            setScene(gameScene);
	        }
	    }));

	}
	
	
	public void setScene(MyScene pScene){
		rm.engine.setScene(pScene);
		currentScene = pScene;
		currentSceneID = pScene.getSceneID();
	}
	
	public void setScene(ESceneID pSceneID){
		switch(pSceneID){
			case splash:
				setScene(splashScene);
				break;
			case menu:
				setScene(menuScene);
				break;
			case game:
				setScene(gameScene);
				break;
			case loading:
				setScene(loadingScene);
				break;	
			case options:
				setScene(optionsScene);
				break;
			case allCard:
				setScene(allCardScene);
				break;
			case help:
				setScene(helpScene);
				break;
			case setting:
				setScene(settingScene);
				break;
			case myRecord:
				setScene(myRecordScene);
				break;
			default:
				break;
				
		}
	}
	
	public OptionsScene getOptionsScene(){
		return optionsScene;
	}
	
	public FightingScene getFightingScene(){
		return fightingScene;
	}
	
	public GameScene getGameScene(){
		return gameScene;
	}
	
	public LoadingScene getLoadingScene(){
		return loadingScene;
	}
	
	public static SceneManager getInstance()
    {
        return INSTANCE;
    }
	
	public ESceneID getCurrentSceneType()
    {
        return currentSceneID;
    }
    
    public MyScene getCurrentScene()
    {
        return currentScene;
    }

	public CardGroupScene getCardGroupScene() {
		return cardGroupScene;
	}
}
