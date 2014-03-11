package magiccollege.manager;

import java.util.ArrayList;
import java.util.Iterator;

import magiccollege.Enum.EDirection;
import magiccollege.Enum.EMapID;
import magiccollege.fight.Card;
import magiccollege.fight.Player;
import magiccollege.main.MainGame;
import magiccollege.net.GameConstants;
import magiccollege.scene.CardInfoScene;
import magiccollege.scene.FightingScene;
import magiccollege.scene.MyScene;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.BoundCamera;
import org.andengine.engine.camera.hud.controls.AnalogOnScreenControl;
import org.andengine.engine.camera.hud.controls.AnalogOnScreenControl.IAnalogOnScreenControlListener;
import org.andengine.engine.camera.hud.controls.BaseOnScreenControl;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.TextMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ColorMenuItemDecorator;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TickerText;
import org.andengine.entity.text.TickerText.TickerTextOptions;
import org.andengine.extension.multiplayer.protocol.client.connector.ServerConnector;
import org.andengine.extension.multiplayer.protocol.shared.SocketConnection;
import org.andengine.extension.physics.box2d.FixedStepPhysicsWorld;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.extension.tmx.TMXLayer;
import org.andengine.extension.tmx.TMXLoader;
import org.andengine.extension.tmx.TMXObject;
import org.andengine.extension.tmx.TMXObjectGroup;
import org.andengine.extension.tmx.TMXTiledMap;
import org.andengine.extension.tmx.util.exception.TMXLoadException;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.HorizontalAlign;
import org.andengine.util.debug.Debug;

import android.content.res.AssetManager;
import android.graphics.Color;
import android.opengl.GLES20;
import android.util.SparseIntArray;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;


public class ResourceManager implements GameConstants{
	private static final ResourceManager INSTANCE = new ResourceManager();
	
	public Engine engine;
	public MainGame activity;
	public BoundCamera camera;
	public VertexBufferObjectManager vbo;
	public TextureManager textureManager;
	
	private EMapID targetMapID;
	private TMXTiledMap tiledMap;//TMX地图
	private final FixtureDef boxFixtureDef = PhysicsFactory.createFixtureDef(0, 0, 1f);
	private final FixtureDef doorFixtureDef = PhysicsFactory.createFixtureDef(0, 0, 0, true);
	
	private BitmapTextureAtlas onScreenControlTexture;//控制盘缓冲池
	private ITextureRegion onScreenControlBaseTextureRegion;//控制盘图像裁剪区域
	private ITextureRegion onScreenControlKnobTextureRegion;//控制盘指针图像裁剪区域
	public AnalogOnScreenControl analogOnScreenControl;//控制盘
	
	private BitmapTextureAtlas splashTextureAtlas;//启动画面
	public ITextureRegion splash_region;
	
	private BitmapTextureAtlas menuTextureAtlas;//菜单
	public ITextureRegion menu_background_region;//菜单背景区域
	public ITextureRegion menu_play_region;//play按钮区域
	public ITextureRegion menu_options_region;//options按钮区域
	
	private BitmapTextureAtlas hudTextureAtlas;//HUD按钮等图片缓冲池
	public ITiledTextureRegion cardGroup_button_region;//卡组设置按钮区域
	public ITiledTextureRegion setting_button_region;//setting按钮（菜单按钮）区域
	
	private BitmapTextureAtlas fightingTextureAtlas;//战斗相关图片
	public ITextureRegion fighting_begin_region;//开始按钮区域
	
	public BitmapTextureAtlas cardTextureAtlas;
	public ITextureRegion cxlr;
	public ITextureRegion mry;
	public ITextureRegion bf;
	public ITextureRegion df;
	public ITextureRegion hnr;
	public ITextureRegion jqr;
	public ITextureRegion yysr;
	public ITextureRegion js;
	public ITextureRegion ry;
	public ITextureRegion jshou;
	public ITextureRegion lglr;
	public ITextureRegion ljss;
	public ITextureRegion dew;
	public ITextureRegion xm;
	public ITextureRegion sr;
	public ITextureRegion cyq;
	public ITextureRegion xhs;
	public ITextureRegion lcdf;
	public ITextureRegion yjs;
	public ITextureRegion fy;
	public ITextureRegion ty;
	public ITextureRegion sys;
	public ITextureRegion dlyz;
	public ITextureRegion ymzzz;
	public ITextureRegion sjs;
	public ITextureRegion cxy;
	public ITextureRegion xkn;
	public ITextureRegion dfs;
	public ITextureRegion zk;
	public ITextureRegion nyyjz;
	public ITextureRegion qxz;
	public ITextureRegion gfs;
	public ITextureRegion hjbhz;
	
	public ITextureRegion temp_card_region;
	
	public EDirection playerDirection = EDirection.unmove;
	public final float playerVelocity = 3f;
	private BitmapTextureAtlas playerTextureAtlas;//player图像缓冲池
	private TiledTextureRegion playerRegion;//player图像裁剪区域
	public ITextureRegion player_portrait_region;//player头像区域
	public AnimatedSprite player;
	private float playerX;
	private float playerY;
	private boolean isPlayerXYset = false;
	private Body playerBody;
	private final FixtureDef playerFixtureDef = PhysicsFactory.createFixtureDef(0, 0, 0.1f);
	public final static long[] PLAYER_ANIMATE_VELOCITY = new long[]{100, 100, 100, 100};
	
	private BuildableBitmapTextureAtlas npcTextureAtlas;//npc图像缓冲池
	
	private PhysicsWorld physicsWorld ;
	
	public Font font;//空心字体
	private BitmapTextureAtlas mainFontTexture;//菜单和loadingScene所用字体的内存空间
	public Font cardFont;//卡牌上面数字所用字体
	private BitmapTextureAtlas cardFontTexture;//卡牌上面数字所用字体的内存空间
	private Text promptText;//sign交互文本
	public Font dialogFont;//npc对话字体
	private BitmapTextureAtlas dialogFontTexture;//对话字体所用的内存空间

	public Player user;
	public Player user2;//for test purpose
	public Player otherPlayer;
	public String otherPlayerID;
	
	public boolean showSkillEffect = true;//是否显示技能效果
	
	/**
	 * 网络相关
	 */
	public ServerConnector<SocketConnection> serverConnector = null;
	public boolean canFight = true;//可以在optionsMenu设置是否接受网络战斗;
	public TimerHandler timerHandler = null;
	public String battleKey;
	public SparseIntArray cardHashMap = new SparseIntArray();
	public ArrayList<Double> randoms = new ArrayList<Double>();
	public int ranIndex;
	
	/**
	 * Option的资源
	 */
	private BitmapTextureAtlas optionsTextureAtlas;//option
	public ITextureRegion options_background_region;//option背景区域
	public ITextureRegion options_setting_region;//setting按钮区域
	public ITextureRegion options_help_region;//help按钮区域
	public ITextureRegion options_allcard_region;//allcard背景区域
	public ITextureRegion options_exit_region;//exit按钮区域
	public ITextureRegion options_person_region;//person按钮区域
	public ITextureRegion options_back_region;//back背景区域
	
	/**
	 * Options中的Setting资源
	 */
	private BuildableBitmapTextureAtlas settingTextureAtlas;
	public ITextureRegion setting_background_region;
	public ITextureRegion on_region;
	public ITextureRegion off_region;
	/**
	 * allcard中的资源
	 */
	private BuildableBitmapTextureAtlas allcardTextureAtlas;
	public ITextureRegion allcard_background_region;
	
	/**
	 * help中的资源
	 */
	private BuildableBitmapTextureAtlas helpTextureAtlas;
	private BuildableBitmapTextureAtlas helpTextureAtlas2;
	public ITextureRegion help_background_region;
	public ITextureRegion card_region;
	public ITextureRegion cardgroup_region;
	public ITextureRegion block_region;
	public ITextureRegion fightground_region;
	public ITextureRegion fight_region;
	public ITextureRegion generalskill_region;
	public ITextureRegion helpstate_region;
	public ITextureRegion store_region;
	public ITextureRegion system_region;
	public ITextureRegion superskill_region;
	public ITextureRegion abnormalstate_region;
	public ITextureRegion helpInfo_region;
	
	
	private BitmapTextureAtlas helpFontTexture;
	public Font helpFont;
	
	/**
	 *person record中的资源
	 */
	private BuildableBitmapTextureAtlas recordTextureAtlas;
	public ITextureRegion record_background_region;
	
	/**
	 * editing cardGroup资源
	 */
	private BuildableBitmapTextureAtlas cardGroupAtlas;
	public ITextureRegion editing_comeBackRegion;
	public ITextureRegion editing_BackGroundTextureRegion;
	
	
	public void loadCardGroupTexture(){
		
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/card/");
		cardGroupAtlas = new BuildableBitmapTextureAtlas(ResourceManager.getInstance().textureManager, 1024, 1024);
		editing_comeBackRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(cardGroupAtlas,activity.getAssets(),"comeback.png");
		editing_BackGroundTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(cardGroupAtlas,activity,"cardgroup_background.png");
		try {
			cardGroupAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 0));
		} catch (TextureAtlasBuilderException e) {
			e.printStackTrace();
		}
		
	}
	
	public void reloadCardGroupTexture(){
//		int length = user.myCards.size();
//		cardTextureAtlas = new BitmapTextureAtlas(
//				textureManager, 64 * length, 86 );
		cardTextureAtlas.load();
		cardGroupAtlas.load();
	}
	
	public void unloadCardGroupTexture(){
		cardTextureAtlas.unload();
//		cardTextureAtlas = null;
		cardGroupAtlas.unload();
	}
	
	public void loadOptionsTexture(){
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/option/");
		optionsTextureAtlas = new BitmapTextureAtlas(getInstance().textureManager,1024,1024,TextureOptions.DEFAULT);
		options_background_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(optionsTextureAtlas, activity, "option_background.png",0,0);
		options_setting_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(optionsTextureAtlas, activity, "setting.png",200,100);
		options_allcard_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(optionsTextureAtlas,activity, "allcard.png",200,200);
		options_help_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(optionsTextureAtlas, activity, "help.png",200,300);
		
		options_exit_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(optionsTextureAtlas, activity, "exit.png",690,65);
		options_person_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(optionsTextureAtlas, activity, "person.png",690,215);
		options_back_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(optionsTextureAtlas, activity, "back.png",690,365);
		//optionsTextureAtlas.load();
	}
	
	public void unloadOptionsTextures(){
		optionsTextureAtlas.unload();
	}
	
	public void reloadOptionsTextures(){
		optionsTextureAtlas.load();
	}
	
	/**
	 * 读取setting资源
	 */
	public void loadSettingTexture(){
		
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/option/");
		settingTextureAtlas = new BuildableBitmapTextureAtlas(ResourceManager.getInstance().textureManager, 1024, 1024);
		setting_background_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(settingTextureAtlas, activity.getAssets(), "settingScene.png");
		on_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(settingTextureAtlas, activity, "on.png");
		off_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(settingTextureAtlas,activity, "off.png");
		try {
			settingTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 0));
		} catch (TextureAtlasBuilderException e) {
			e.printStackTrace();
		}
		//settingTextureAtlas.load();
	}
	
	public void reloadSettingTexture(){
		settingTextureAtlas.load();
	}
	
	public void unloadSettingTexture(){
		settingTextureAtlas.unload();
	}
	
	/**
	 * 读入allcard的数据
	 */
	
	public void loadAllcardTexture(){
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/option/");
		allcardTextureAtlas = new BuildableBitmapTextureAtlas(ResourceManager.getInstance().textureManager, 1024, 1024);
		allcard_background_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(allcardTextureAtlas, activity.getAssets(), "allcard_background.png");
		
		try {
			allcardTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 0));
		} catch (TextureAtlasBuilderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//allcardTextureAtlas.load();
	}
	
	public void reloadAllCardTexture(){
		allcardTextureAtlas.load();
	}
	
	public void unloadAllCardTexture(){
		allcardTextureAtlas.unload();
	}
	
	/**
	 * 读入help需要的数据
	 */
	public void loadHelpTexture(){
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/option/help/");
		helpTextureAtlas = new BuildableBitmapTextureAtlas(ResourceManager.getInstance().textureManager, 1024, 2048);
		helpTextureAtlas2 = new BuildableBitmapTextureAtlas(ResourceManager.getInstance().textureManager, 1024, 1024);
		help_background_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(helpTextureAtlas, activity.getAssets(), "background_help.png");
		              
		card_region 			= BitmapTextureAtlasTextureRegionFactory.createFromAsset(helpTextureAtlas, activity.getAssets(),  "card_help.png");
		cardgroup_region 		= BitmapTextureAtlasTextureRegionFactory.createFromAsset(helpTextureAtlas, activity.getAssets(),  "cardgroup_help.png"); 
		fight_region 			= BitmapTextureAtlasTextureRegionFactory.createFromAsset(helpTextureAtlas, activity.getAssets(),  "fight_help.png");
		block_region 			= BitmapTextureAtlasTextureRegionFactory.createFromAsset(helpTextureAtlas, activity.getAssets(), "block.png");
		generalskill_region		= BitmapTextureAtlasTextureRegionFactory.createFromAsset(helpTextureAtlas, activity.getAssets(), "generalskill_help.png");
		superskill_region		= BitmapTextureAtlasTextureRegionFactory.createFromAsset(helpTextureAtlas, activity.getAssets(),  "superskill_help.png");
		fightground_region	    = BitmapTextureAtlasTextureRegionFactory.createFromAsset(helpTextureAtlas, activity.getAssets(),  "fightground_help.png");
		helpstate_region 		= BitmapTextureAtlasTextureRegionFactory.createFromAsset(helpTextureAtlas, activity.getAssets(),  "help_state_help.png");
		abnormalstate_region    = BitmapTextureAtlasTextureRegionFactory.createFromAsset(helpTextureAtlas, activity.getAssets(),  "abnormal_state__help.png");
		system_region		    = BitmapTextureAtlasTextureRegionFactory.createFromAsset(helpTextureAtlas, activity.getAssets(),  "system_help.png");
		store_region 			= BitmapTextureAtlasTextureRegionFactory.createFromAsset(helpTextureAtlas, activity.getAssets(),  "store_help.png");	   
		helpInfo_region         = BitmapTextureAtlasTextureRegionFactory.createFromAsset(helpTextureAtlas2, activity.getAssets(),  "helpInfo.png");	   
		try {
			helpTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 0));
			helpTextureAtlas2.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 0));
		} catch (TextureAtlasBuilderException e) {
			e.printStackTrace();
		}	    
		
		FontFactory.setAssetBasePath("font/");
		helpFontTexture = new BitmapTextureAtlas(textureManager,1024,1024,TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		helpFont =  FontFactory.createFromAsset(activity.getFontManager(), helpFontTexture, activity.getAssets(), "helpFont.ttf", 30, true, Color.BLACK);
		helpFont.load();
		
	    //helpTextureAtlas.load();
	    //helpTextureAtlas2.load();
		
	}
	
	public void reloadHelpTexture(){
		//helpFont.load();
	    helpTextureAtlas.load();
	    helpTextureAtlas2.load();
	}
	
	public void unloadHelpTexture(){
		//helpFont.unload();
	    helpTextureAtlas.unload();
	    helpTextureAtlas2.unload();
	}
	
	/**
	 * 读入person record的数据
	 */
	
	public void loadPersonTexture(){
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/option/");
		recordTextureAtlas = new BuildableBitmapTextureAtlas(ResourceManager.getInstance().textureManager, 1024, 1024);
	    record_background_region 			= BitmapTextureAtlasTextureRegionFactory.createFromAsset(recordTextureAtlas, activity.getAssets(),  "myrecord.png");
	    try {
			recordTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 0));
			
		} catch (TextureAtlasBuilderException e) {
			e.printStackTrace();
		}	  
	    //recordTextureAtlas.load();
	}
	
	public void reloadRecordTexture(){
		recordTextureAtlas.load();
	}
	
	public void unloadRecordTexture(){
		recordTextureAtlas.unload();
	}
	
	private void loadCardTextures(){
		// 读入所有卡牌图片
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/card/");
		cardTextureAtlas = new BitmapTextureAtlas(textureManager, 66 * 34, 90);
		AssetManager am = activity.getAssets();
		temp_card_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(cardTextureAtlas, am, "testCard.png", 0, 0);
		cxlr = BitmapTextureAtlasTextureRegionFactory.createFromAsset(cardTextureAtlas, am, "chaoxilieren.png", 66, 0);
		mry = BitmapTextureAtlasTextureRegionFactory.createFromAsset(cardTextureAtlas, am, "meirenyu.png", 66 * 2, 0);
		bf = BitmapTextureAtlasTextureRegionFactory.createFromAsset(cardTextureAtlas, am, "bingfa.png", 66 * 3, 0);
		df = BitmapTextureAtlasTextureRegionFactory.createFromAsset(cardTextureAtlas, am, "difa.png", 66 * 4, 0);
		hnr = BitmapTextureAtlasTextureRegionFactory.createFromAsset(cardTextureAtlas, am, "henengren.png", 66 * 5, 0);
		jqr = BitmapTextureAtlasTextureRegionFactory.createFromAsset(cardTextureAtlas, am, "jiqiren.png", 66 * 6, 0);
		yysr = BitmapTextureAtlasTextureRegionFactory.createFromAsset(cardTextureAtlas, am, "youyinshiren.png", 66 * 7, 0);
		js = BitmapTextureAtlasTextureRegionFactory.createFromAsset(cardTextureAtlas, am, "jianshang.png", 66 * 8, 0);
		ry = BitmapTextureAtlasTextureRegionFactory.createFromAsset(cardTextureAtlas, am, "renyuan.png", 66 * 9, 0);
		jshou = BitmapTextureAtlasTextureRegionFactory.createFromAsset(cardTextureAtlas, am, "jiaoshou.png", 66 * 10, 0);
		lglr = BitmapTextureAtlasTextureRegionFactory.createFromAsset(cardTextureAtlas, am, "lagelangri.png", 66 * 11, 0);
		ljss = BitmapTextureAtlasTextureRegionFactory.createFromAsset(cardTextureAtlas, am, "lianjinshushi.png", 66 * 12, 0);
		dew = BitmapTextureAtlasTextureRegionFactory.createFromAsset(cardTextureAtlas, am, "daerwen.png", 66 * 13, 0);
		xm = BitmapTextureAtlasTextureRegionFactory.createFromAsset(cardTextureAtlas, am, "xinmo.png", 66 * 14, 0);
		sr = BitmapTextureAtlasTextureRegionFactory.createFromAsset(cardTextureAtlas, am, "shuren.png", 66 * 15, 0);
		cyq = BitmapTextureAtlasTextureRegionFactory.createFromAsset(cardTextureAtlas, am, "chenyinque.png", 66 * 16, 0);
		xhs = BitmapTextureAtlasTextureRegionFactory.createFromAsset(cardTextureAtlas, am, "xiaohushi.png", 66 * 17, 0);
		lcdf = BitmapTextureAtlasTextureRegionFactory.createFromAsset(cardTextureAtlas, am, "linchuangdaifu.png", 66 * 18, 0);
		yjs = BitmapTextureAtlasTextureRegionFactory.createFromAsset(cardTextureAtlas, am, "yaojishi.png", 66 * 19, 0);
		fy = BitmapTextureAtlasTextureRegionFactory.createFromAsset(cardTextureAtlas, am, "fayi.png", 66 * 20, 0);
		ty = BitmapTextureAtlasTextureRegionFactory.createFromAsset(cardTextureAtlas, am, "tianyan.png", 66 * 21, 0);
		sys = BitmapTextureAtlasTextureRegionFactory.createFromAsset(cardTextureAtlas, am, "sheyingshi.png", 66 * 22, 0);
		dlyz = BitmapTextureAtlasTextureRegionFactory.createFromAsset(cardTextureAtlas, am, "duoluoyizhe.png", 66 * 23, 0);
		ymzzz = BitmapTextureAtlasTextureRegionFactory.createFromAsset(cardTextureAtlas, am, "yimiaozhizaozhe.png", 66 * 24, 0);
		sjs = BitmapTextureAtlasTextureRegionFactory.createFromAsset(cardTextureAtlas, am, "shejishi.png", 66 * 25, 0);
		cxy = BitmapTextureAtlasTextureRegionFactory.createFromAsset(cardTextureAtlas, am, "chengxuyuan.png", 66 * 26, 0);
		xkn = BitmapTextureAtlasTextureRegionFactory.createFromAsset(cardTextureAtlas, am, "xinkenan.png", 66 * 27, 0);
		dfs = BitmapTextureAtlasTextureRegionFactory.createFromAsset(cardTextureAtlas, am, "dafashi.png", 66 * 28, 0);
		zk = BitmapTextureAtlasTextureRegionFactory.createFromAsset(cardTextureAtlas, am, "zhengke.png", 66 * 29, 0);
		nyyjz = BitmapTextureAtlasTextureRegionFactory.createFromAsset(cardTextureAtlas, am, "nengyuanyanjiuzhe.png", 66 * 30, 0);
		qxz = BitmapTextureAtlasTextureRegionFactory.createFromAsset(cardTextureAtlas, am, "qianxingzhe.png", 66 * 31, 0);
		gfs = BitmapTextureAtlasTextureRegionFactory.createFromAsset(cardTextureAtlas, am, "guofangsheng.png", 66 * 32, 0);
		hjbhz = BitmapTextureAtlasTextureRegionFactory.createFromAsset(cardTextureAtlas, am, "huanjingbaohuzhe.png", 66 * 33, 0);
	}

	/**
	 * 从文件中读取数据
	 * TODO database
	 * 在{@link SceneManager#createMenuScene()}处被调用
	 */
	public void loadUserData(){
		loadCardTextures();
		//此处读入所在的地图
		targetMapID = EMapID.eastDormitory;
		
		ArrayList<Card> myCards = new ArrayList<Card>();//创建卡牌仓库
		
		int[] cardSkillKey = new int[]{1, 201, 3, 0};//读入卡牌技能
		Card card = new Card(0, "chengxuyuan", 20, 5, 5, 3, 4, cardSkillKey, findTextureRegionByCardName("chaoxilieren"));//创建一张卡牌
		Card card1 = new Card(1, "guofangsheng", 15, 5, 5, 6, 5, cardSkillKey, findTextureRegionByCardName("meirenyu"));
		int[] cardSkillKey2 = new int[]{2, 4, 202, 0};
		Card card2 = new Card(2, "linchuangdaifu", 20, 5, 5, 2, 6, cardSkillKey2, findTextureRegionByCardName("bingfa"));
		Card card3 = new Card(3, "yimiaozhizaozhe", 25, 5, 5,3, 3, cardSkillKey2, findTextureRegionByCardName("difa"));
		myCards.add(card);//仓库读入一张卡牌
		myCards.add(card1);
		myCards.add(card2);
		myCards.add(card3);
		int currentCardGroupNo = 0;//TODO 读入当前卡组号（即第几个卡组）
		int [][] cardGroups = new int[4][];//读入当前卡组内容
		int[] cardGroup1 = new int[]{0,1,2,3};
		cardGroups[0] = cardGroup1;
		cardGroups[1] = new int[]{0};
		cardGroups[2] = new int[]{0};
		cardGroups[3] = new int[]{0};
		int playerHP = 100;//读入PlayerHP
		ArrayList<String> dialogs = new ArrayList<String> ();
		dialogs.add(new String("被我发现了，来战斗吧！"));//读入npc对话文本
		user = new Player(myCards, cardGroups, currentCardGroupNo, playerHP, dialogs);
		
		ArrayList<Card> myCards2 = new ArrayList<Card>();
		myCards2.add(card);//仓库读入一张卡牌
		myCards2.add(card1);
		myCards2.add(card2);
		myCards2.add(card3);
		int currentCardGroupNo2 = 1;//TODO 读入当前卡组号（即第几个卡组）
		int [][] cardGroups2 = new int[4][];//读入当前卡组内容
		int[] cardGroup2 = new int[]{0,1,2,3};
		cardGroups2[0] = new int[]{0};
		cardGroups2[1] = cardGroup2;
		cardGroups2[2] = new int[]{0};
		cardGroups2[3] = new int[]{0};
		user2 = new Player(myCards2, cardGroups2, currentCardGroupNo2, playerHP, dialogs);
	}
	
	public void loadSplash(){
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/menu/");
		splashTextureAtlas = new BitmapTextureAtlas(textureManager, 256, 256, TextureOptions.BILINEAR);
		splash_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(splashTextureAtlas, activity, "splash.png", 0, 0);
		splashTextureAtlas.load();
	}
	
	public void loadMenu(){
		loadMenuGraphics();
		loadMenuAudio();
	    loadMenuFonts();
	}
	
	private void loadMenuFonts(){
		FontFactory.setAssetBasePath("font/");
		mainFontTexture = new BitmapTextureAtlas(textureManager,1024,1024,TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		font = FontFactory.createStrokeFromAsset(activity.getFontManager(), mainFontTexture, activity.getAssets(), "font.ttf", 50, true, Color.WHITE, 2, Color.BLACK);
	    font.load();
	    
	    cardFontTexture = new BitmapTextureAtlas(textureManager,256,256,TextureOptions.BILINEAR_PREMULTIPLYALPHA);
	    cardFont = FontFactory.createFromAsset(activity.getFontManager(), cardFontTexture, activity.getAssets(), "cardFont.ttf", 20, true, Color.WHITE);
	    cardFont.load();
	    
	    dialogFontTexture = new BitmapTextureAtlas(textureManager,256,256,TextureOptions.BILINEAR_PREMULTIPLYALPHA);
	    dialogFont = FontFactory.createFromAsset(activity.getFontManager(), dialogFontTexture, activity.getAssets(), "dialogFont.ttf", 30, true, Color.WHITE);
	    dialogFont.load();
		
	    promptText = new Text(camera.getCenterX(),camera.getCenterY(),font,"default!!!!!!!!!!!!!!",vbo);
	}
	
	private void loadMenuGraphics(){
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/menu/");
		menuTextureAtlas = new BitmapTextureAtlas(textureManager,1024,1024,TextureOptions.DEFAULT);
		menu_background_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "menu_background.png",0,0);
		menu_play_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "play.png",0,480);
		menu_options_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "options.png",400,480);
		menuTextureAtlas.load();
		
	}
	
	private void loadMenuAudio(){
		//todo
	}
	
	public void loadGameTexture(){
		loadPlayerTexture();
		loadControlTexture();
		loadHUDtexture();
	}
	
	public void setGame(MyScene pScene){
		this.setGame(pScene, targetMapID);
	}
	
	public void setGame(MyScene pScene, EMapID pMapID){
		loadPhysics(pScene, pMapID);
		loadTMXMap(pScene, pMapID);
		
		//设置第一次进入游戏时Player默认的XY坐标
		if (!isPlayerXYset){
			switch (pMapID){
			case eastDormitory:
				playerX = (camera.getBoundsWidth() - playerRegion.getTextureX()) / 2;
				playerY = (camera.getBoundsHeight() - playerRegion.getTextureY()) / 2;
				break;
			case eastCampus:
				break;
			default:
				break;
			}
			isPlayerXYset = true;
		}
		loadPlayer(pScene, playerX, playerY);
		
		loadControl(pScene);
	}
	
	public void loadPhysics(MyScene pScene, EMapID pMapID){
		physicsWorld= new FixedStepPhysicsWorld(60, new Vector2(0, 0), false, 8, 1);
		pScene.registerUpdateHandler(physicsWorld);

		ContactListener contactListener = null;
		switch (pMapID){
		case eastDormitory:
			contactListener = new ContactListener(){
		        @Override
		        public void beginContact(Contact contact){
		            final Fixture x1 = contact.getFixtureA();
		            final Fixture x2 = contact.getFixtureB();
		            final Object o1 = x1.getBody().getUserData();
		            final Object o2 = x2.getBody().getUserData();
		            //如果进入地图切换点
		            if (o1 != null && o2 != null){
		            	if (o1.equals("door_campus_south") || o2.equals("door_campus_south")){
		            		targetMapID = EMapID.eastCampus;
		            		playerX = 200;
		            		playerY = 200;
		            		SceneManager.getInstance().gameSceneSwitch();
		            		return;
		            	}else if (o1.equals("door_campus_west") || o2.equals("door_campus_west")){
		            		targetMapID = EMapID.eastCampus;
		            		playerX = 100;
		            		playerY = 100;
		            		SceneManager.getInstance().gameSceneSwitch();
		            		return;
		            	}
		            }
		            
		            //如果遇到npc
		            if (o1 != null && o2 != null){
		            	if (o1 instanceof Player){
		            		otherPlayer = (Player) o1;
		            		
		            		//npc对话文本	
		            		Text dialogText = new TickerText(10, 10, dialogFont, otherPlayer.dialogs.get(0), new TickerTextOptions(HorizontalAlign.CENTER, 10), vbo);
		            		//黑色半透明对话背景
		            		Rectangle rect = new Rectangle(0, 0, camera.getWidth(), camera.getHeight()/2, vbo);
		            		rect.setColor(0,0,0);	rect.setAlpha(0.7f);
		            		//对话scene
		            		final MenuScene dialogScene = new MenuScene(camera);
		            		dialogScene.attachChild(rect);//添加背景
		            		dialogScene.attachChild(dialogText);//添加对话文本
		            		//确认按钮
		            		final IMenuItem yesMenuItem = new ColorMenuItemDecorator(new TextMenuItem(1, dialogFont, "yes", vbo), 
		            				new org.andengine.util.color.Color(1,0,0), new org.andengine.util.color.Color(0,1,0));
		            		dialogScene.addMenuItem(yesMenuItem);
		            		//否认按钮
		            		final IMenuItem quitMenuItem = new ColorMenuItemDecorator(new TextMenuItem(2, dialogFont, "no", vbo), 
		            				new org.andengine.util.color.Color(1,0,0), new org.andengine.util.color.Color(0,1,0));
		            		dialogScene.addMenuItem(quitMenuItem);
		            		//使按钮按下去会变色
		            		dialogScene.buildAnimations();
		            		//设置按钮位置
		            		yesMenuItem.setPosition(camera.getWidth() - 100, camera.getHeight()/2 - 100);
		            		quitMenuItem.setPosition(camera.getWidth() - 100, camera.getHeight()/2 - 50);
		            		//对话scene背景不可见
		            		dialogScene.setBackgroundEnabled(false);
		            		//设置按钮触发事件
		            		dialogScene.setOnMenuItemClickListener(new IOnMenuItemClickListener(){
								@Override
								public boolean onMenuItemClicked(final MenuScene pMenuScene, final IMenuItem pMenuItem, final float pMenuItemLocalX, final float pMenuItemLocalY) {
									switch(pMenuItem.getID()) {
									case 1://按了确认按钮
										dialogScene.back();//取消对话
//										if (!canFight){
//											activity.toastOnUIThread("请先设置开启网络对战!");
//											return true;
//										}
//										QueryNameClientMessage qncm = new QueryNameClientMessage();
//										qncm.set(MyData.getInstance().getId(), "btf");//for test
//										try {
//											ResourceManager.this.serverConnector.sendClientMessage(qncm);
//										} catch (IOException e) {
//											e.printStackTrace();
//										}
										
										
										//进入战斗
										SceneManager.getInstance().createFightingScene(user, user2);
										return true;
									case 2://按了否认按钮
										dialogScene.back();//取消对话
										return true;
									default:
										break;
									}
									return false;
								}});
		            		//添加对话scene
		            		analogOnScreenControl.setChildScene(dialogScene);
		            		return;
		            	}else if (o2 instanceof Player){
		            		//TODO
		            	}
		            }
		            
		            //如果遇到指示牌
		            if (o1 != null && o2 != null){
		            	if (o1.equals("campus_south") || o2.equals("campus_south")){
		            		promptText.setText("campus_south");
		            	}else if (o1.equals("campus_west") || o2.equals("campus_west")){
		            		promptText.setText("campus_west");
		            	}else if (o1.equals("shensi") || o2.equals("shensi")){
		            		promptText.setText("慎思园");
		            	}else if (o1.equals("zhishan") || o2.equals("zhishan")){
		            		promptText.setText("至善园");
		            	}else if (o1.equals("mingde") || o2.equals("mingde")){
		            		promptText.setText("明德园");
		            	}else if (o1.equals("gezhi") || o2.equals("gezhi")){
		            		promptText.setText("格致园");
		            	}else if (o1.equals("mada") || o2.equals("mada")){
		            		promptText.setText("coming soon");
		            	}
		            	if (!promptText.hasParent()){
			            	camera.getHUD().attachChild(promptText);
		            	}
		            }
		        }
		        @Override
		        public void endContact(Contact contact){
		        	camera.getHUD().detachChild(promptText);
		        	MenuScene dialogScene = (MenuScene) analogOnScreenControl.getChildScene();
		        	if (dialogScene != null){
		        		dialogScene.back();
		        	}
		        }
				@Override
				public void postSolve(Contact arg0, ContactImpulse arg1) {
				}
				@Override
				public void preSolve(Contact arg0, Manifold arg1) {
				}
		    };
			break;
		case eastCampus:
			
			break;
		default:
			break;
		}
		
		if (contactListener != null)
			physicsWorld.setContactListener(contactListener);
		
	}
	
	private void loadHUDtexture(){
		hudTextureAtlas = new BitmapTextureAtlas(textureManager, 256, 144);
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/menu/");
		cardGroup_button_region = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(hudTextureAtlas, activity.getAssets(),"cardGroupButton.png", 0, 0, 1, 2);
		setting_button_region = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(hudTextureAtlas, activity.getAssets(),"settingButton.png", 128, 0, 1, 2);
		hudTextureAtlas.load();
	}
	
	private void loadPlayerTexture(){
		playerTextureAtlas = new BitmapTextureAtlas(
				textureManager, 128, 256);
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/game/character/");
		playerRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(playerTextureAtlas, activity.getAssets(),"player.png", 0, 0, 4, 4);
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/game/portrait/");
		player_portrait_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(playerTextureAtlas, 
				activity.getAssets(), "portrait1.png", 0, 192);
		playerTextureAtlas.load();
		
		npcTextureAtlas =new BuildableBitmapTextureAtlas(textureManager, 1024, 1024, TextureOptions.NEAREST);
		npcTextureAtlas.load();
	}
	
	/**
	 * @param pScene current game scene
	 * Warning: should be invoked after loadPhysics method
	 **/
	private void loadPlayer(MyScene pScene, float pPlayerX, float pPlayerY){
		player = new AnimatedSprite(pPlayerX, pPlayerY, 32, 32, playerRegion,
				vbo);
		camera.setChaseEntity(player);
		
		playerBody = PhysicsFactory.createBoxBody(physicsWorld,player,BodyType.DynamicBody,playerFixtureDef);
		playerBody.setUserData("player");
		physicsWorld.registerPhysicsConnector(new PhysicsConnector(player, playerBody, true, false){
			@Override
			public void onUpdate(float pSecondElapsed){
				super.onUpdate(pSecondElapsed);
				camera.updateChaseEntity();
			}
		});
		pScene.attachChild(player);
		pScene.registerUpdateHandler(new IUpdateHandler() {
			@Override
			public void reset() {
				
			}
			@Override
			public void onUpdate(float pSecondsElapsed) {
				switch (playerDirection) {
				case right:
					player.animate(PLAYER_ANIMATE_VELOCITY, 8, 11, true);
					break;
				case left:
					player.animate(PLAYER_ANIMATE_VELOCITY, 4, 7, true);
					break;
				case up:
					player.animate(PLAYER_ANIMATE_VELOCITY, 12, 15, true);
					break;
				case down:
					player.animate(PLAYER_ANIMATE_VELOCITY, 0, 3, true);
					break;
				default:
					player.stopAnimation();
					break;
				}
			}
		});
	}
	
	private void loadControlTexture(){
		onScreenControlTexture = new BitmapTextureAtlas(
				textureManager, 256, 128, TextureOptions.DEFAULT);
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/game/");
		onScreenControlBaseTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(onScreenControlTexture, activity,
						"onscreen_control_base.png", 0, 0);
		onScreenControlKnobTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(onScreenControlTexture, activity,
						"onscreen_control_knob.png", 128, 0);
		onScreenControlTexture.load();
	}
	
	/**
	 * @param pScene current game scene
	 * Warning: should be invoked after loadPlayer method
	 **/
	public void loadControl(MyScene pScene){
				// AndEngine中的控制方向的窗口 
				analogOnScreenControl = new AnalogOnScreenControl(
						40, camera.getHeight() - onScreenControlBaseTextureRegion.getHeight() - 30, camera,
						onScreenControlBaseTextureRegion, onScreenControlKnobTextureRegion, 0.1f, 200, vbo,
						new IAnalogOnScreenControlListener() {
							@Override
							public void onControlChange(
									final BaseOnScreenControl pBaseOnScreenControl,
									final float pValueX, final float pValueY) {
									float x = 0, y = 0;

									if (pValueX > 0.5f) {
										playerDirection = EDirection.right;
										x = playerVelocity;
									} else if (pValueX < -0.5f) {
										playerDirection = EDirection.left;
										x = -playerVelocity;
									} else if (pValueY > 0.5f) {
										playerDirection = EDirection.down;
										y = playerVelocity;
									} else if (pValueY < -0.5f) {
										playerDirection = EDirection.up;
										y = -playerVelocity;
									} else {
										playerDirection = EDirection.unmove;
									}
									Vector2 v = new Vector2(x,y);
									playerBody.setLinearVelocity(v);
							}

							@Override
							public void onControlClick(final AnalogOnScreenControl pAnalogOnScreenControl) {
								
							}
						});
				analogOnScreenControl.getControlBase().setBlendFunction(
						GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
				analogOnScreenControl.getControlBase().setAlpha(0.5f);
				analogOnScreenControl.getControlBase().setScaleCenter(0, 128);
				analogOnScreenControl.getControlBase().setScale(1.25f);
				analogOnScreenControl.getControlKnob().setScale(1.25f);
				analogOnScreenControl.refreshControlKnobPosition();

				pScene.setChildScene(analogOnScreenControl);
	}
	
	public void loadTMXMap(MyScene pScene, EMapID pMapID) {
		//加载TMX地图
		TMXLoader loader = new TMXLoader(activity.getAssets(),engine.getTextureManager(),vbo);
		try {
			switch (pMapID){
				case eastDormitory:
					tiledMap = loader.loadFromAsset("eastDormitory.tmx");
					break;
				case eastCampus:
					tiledMap = loader.loadFromAsset("eastCampus.tmx");
					break;
				default:
					break;
			}
		} catch (TMXLoadException e) {
				e.printStackTrace();
		}
		
		//加载非对象层
		for (int i = 0; i < tiledMap.getTMXLayers().size(); i++){
				TMXLayer layer = tiledMap.getTMXLayers().get(i);
				if (!layer.getTMXLayerProperties().containsTMXProperty("wall", "true")){
					pScene.attachChild(layer);
				}
		}
		
		//创建不能走的区域
		for (final TMXObjectGroup group : this.tiledMap.getTMXObjectGroups()){//对于每一个对象层
			if (group.getTMXObjectGroupProperties().containsTMXProperty("wall", "true")){//如果包含wall属性是true
				for (final TMXObject object : 	group.getTMXObjects()){//则对它里面的每一个对象创建一个物理BODY
					final Rectangle rect = new Rectangle(object.getX(),object.getY(),object.getWidth(),object.getHeight(), vbo);			
					PhysicsFactory.createBoxBody(physicsWorld, rect, BodyType.StaticBody, boxFixtureDef);
					rect.setVisible(false);
					pScene.attachChild(rect);
				}
			}
			if (group.getTMXObjectGroupProperties().containsTMXProperty("sign", "true")){
				for (final TMXObject object : 	group.getTMXObjects()){
					final Rectangle rect = new Rectangle(object.getX(),object.getY(),object.getWidth(),object.getHeight(), vbo);			
					final Body localBody = PhysicsFactory.createBoxBody(physicsWorld, rect, BodyType.StaticBody, boxFixtureDef);
					rect.setVisible(false);
					pScene.attachChild(rect);
					switch (pMapID){
					case eastDormitory:
						if (object.getTMXObjectProperties().containsTMXProperty("campus_west", "true")){
							localBody.setUserData("campus_west");
						}else if (object.getTMXObjectProperties().containsTMXProperty("campus_south", "true")){
							localBody.setUserData("campus_south");
						}else if (object.getTMXObjectProperties().containsTMXProperty("shensi", "true")){
							localBody.setUserData("shensi");
						}else if (object.getTMXObjectProperties().containsTMXProperty("zhishan", "true")){
							localBody.setUserData("zhishan");
						}else if (object.getTMXObjectProperties().containsTMXProperty("mingde", "true")){
							localBody.setUserData("mingde");
						}else if (object.getTMXObjectProperties().containsTMXProperty("gezhi", "true")){
							localBody.setUserData("gezhi");
						}else if (object.getTMXObjectProperties().containsTMXProperty("mada", "true")){
							localBody.setUserData("mada");
						}
						break;
					case eastCampus:
					
						break;
					default:
						break;
					}
				}
			}
			if (group.getTMXObjectGroupProperties().containsTMXProperty("door", "true")){
				for (final TMXObject object : 	group.getTMXObjects()){
					final Rectangle rect = new Rectangle(object.getX(),object.getY(),object.getWidth(),object.getHeight(), vbo);			
					final Body localBody = PhysicsFactory.createBoxBody(physicsWorld, rect, BodyType.StaticBody, doorFixtureDef);
					rect.setVisible(false);
					pScene.attachChild(rect);
					switch (pMapID){
					case eastDormitory:
						if (object.getTMXObjectProperties().containsTMXProperty("campus_west", "true")){
							localBody.setUserData("door_campus_west");
						}else if (object.getTMXObjectProperties().containsTMXProperty("campus_south", "true")){
							localBody.setUserData("door_campus_south");
						}
						break;
					case eastCampus:
					
						break;
					default:
						break;
					}
				}
			}	
			if (group.getTMXObjectGroupProperties().containsTMXProperty("npc", "true")){
				for (final TMXObject object : 	group.getTMXObjects()){
					BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/game/character/");
					TiledTextureRegion npc_region = null;
					switch (pMapID){
					case eastDormitory:
						if (object.getTMXObjectProperties().containsTMXProperty("girl1", "true")){
							npc_region = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(npcTextureAtlas,
									activity.getAssets(),"girl1.png", 4, 4);		
						}else if (object.getTMXObjectProperties().containsTMXProperty("npc2", "true")){
							
						}
						break;
					case eastCampus:
					
						break;
					default:
						break;
					}
					if (npc_region != null){
						final AnimatedSprite npc = new AnimatedSprite(object.getX(),object.getY(),object.getWidth(),object.getHeight(),npc_region, vbo);
						/*
						pScene.registerUpdateHandler(new IUpdateHandler() {
							float timeElapsed = 0;
							@Override
							public void onUpdate(float pSecondsElapsed) {
								timeElapsed += pSecondsElapsed;
								if (timeElapsed > 1000)
									npc.animate(new long[]{500}, 0, 1, false);
								else if (timeElapsed > 2000)
									npc.animate(new long[]{500}, 4, 5, false);
								else if (timeElapsed > 3000)
									npc.animate(new long[]{500}, 8, 9, false);
								else{
									npc.animate(new long[]{500}, 12, 13, false);
									reset();
								}
							}

							@Override
							public void reset() {
								timeElapsed = 0;
							}});*/
						Body npcBody = PhysicsFactory.createBoxBody(physicsWorld, npc, BodyType.StaticBody, boxFixtureDef);
						pScene.attachChild(npc);
						try {
							npcTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 1));
						} catch (TextureAtlasBuilderException e) {
							Debug.e(e);
						}
						
						switch (pMapID){
						case eastDormitory:
							if (object.getTMXObjectProperties().containsTMXProperty("girl1", "true")){
								npcBody.setUserData(user2);
							}else if (object.getTMXObjectProperties().containsTMXProperty("npc2", "true")){
								
							}
							break;
						case eastCampus:
							
							break;
						default:
							break;
						}
					}
				}
			}	
		}
		camera.setBounds(0, 0, tiledMap.getTMXLayers().get(0).getWidth(),
				tiledMap.getTMXLayers().get(0).getHeight());
		camera.setBoundsEnabled(true);

	}

	
	public void loadFightingTexture(Player p0,Player p1){
		//TODO
		//加载开始按钮
		fightingTextureAtlas = new BitmapTextureAtlas(
				textureManager, 128, 128);
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/game/fight/");
		fighting_begin_region = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(fightingTextureAtlas, activity.getAssets(),
						"begin.png", 0, 0);
		fightingTextureAtlas.load();
		
		//计算卡牌数量从而预留出足够的内存空间放卡牌图片
		int length = p0.getCardGroup().length;
		if (p1.getCardGroup().length > length)
			length = p1.getCardGroup().length;
		//卡宽64，高86
//		cardTextureAtlas = new BitmapTextureAtlas(
//				textureManager, 64 * length, 2 * 86);//用完记得释放
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/card/");
		
		//实例化p0的卡组
		p0.mCardGroup.clear();
		for (int i = 0; i < p0.getCardGroup().length; i++){
			int localKey = p0.getCardGroup()[i];
			for (int j = 0; j < p0.myCards.size(); j++){
				Card card = p0.myCards.get(j);
				if (card.key != localKey)
					continue;
//				ITextureRegion card_region = BitmapTextureAtlasTextureRegionFactory
//					.createFromAsset(cardTextureAtlas, activity.getAssets(),"testCard.png", 64 * i, 0);//testCard.png 须根据卡名变化
				card = new Card(card,findTextureRegionByCardName(card.getName())){
				@Override
				public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
					if (pSceneTouchEvent.isActionDown()){
						FightingScene scene = SceneManager.getInstance().getFightingScene();		
						if (this.isClicked()){
							SceneManager.getInstance().setScene(new CardInfoScene(this, scene));
							return true;
						}else{
							this.setClicked(true);
							scene.setCardToGround(this);			
							return true;
						}
					}
					return false;
				}};
				p0.mCardGroup.add(card);
			}
		}
		
		//此处先默认P1为机器人因此不用重写onAreaTouched
		p1.mCardGroup.clear();
		for (int i = 0; i < p1.getCardGroup().length; i++){
			int localKey = p1.getCardGroup()[i];
			for (int j = 0; j < p1.myCards.size(); j++){
				Card card = p1.myCards.get(j);
				if (card.key != localKey)
					continue;
//				ITextureRegion card_region = BitmapTextureAtlasTextureRegionFactory
//					.createFromAsset(cardTextureAtlas, activity.getAssets(),"testCard.png", 64 * i, 86);//testCard.png 须根据卡名变化
				card = new Card(card, findTextureRegionByCardName(card.getName())) {
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
				p1.mCardGroup.add(card);
			}
		}
		cardTextureAtlas.load();
	}
	
	public void loadNetFightingTexture(Player p0,Player p1){
		//TODO
		//加载开始按钮
		fightingTextureAtlas = new BitmapTextureAtlas(
				textureManager, 128, 128);
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/game/fight/");
		fighting_begin_region = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(fightingTextureAtlas, activity.getAssets(),
						"begin.png", 0, 0);
		fightingTextureAtlas.load();
		
//		//计算卡牌数量从而预留出足够的内存空间放卡牌图片
//		int length = p0.getCardGroup().length;
//		if (p1.getCardGroup().length > length)
//			length = p1.getCardGroup().length;
//		//卡宽64，高86
//		cardTextureAtlas = new BitmapTextureAtlas(
//				textureManager, 64 * length, 2 * 86);//用完记得释放
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/card/");
		
		//实例化p0的卡组
		p0.mCardGroup.clear();
		for (int i = 0; i < p0.getCardGroup().length; i++){
			int localKey = p0.getCardGroup()[i];
			for (int j = 0; j < p0.myCards.size(); j++){
				Card card = p0.myCards.get(j);
				if (card.key != localKey)
					continue;
//				ITextureRegion card_region = BitmapTextureAtlasTextureRegionFactory
//					.createFromAsset(cardTextureAtlas, activity.getAssets(),"testCard.png", 64 * i, 0);//testCard.png 须根据卡名变化
				card = new Card(card,findTextureRegionByCardName(card.getName())){
				@Override
				public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
					if (pSceneTouchEvent.isActionDown()){
						FightingScene scene = SceneManager.getInstance().getFightingScene();		
						if (this.isClicked()){
							SceneManager.getInstance().setScene(new CardInfoScene(this, scene));
							return true;
						}else{
							this.setClicked(true);
							scene.setCardToGround(this);			
							return true;
						}
					}
					return false;
				}};
				p0.mCardGroup.add(card);
			}
		}
		
		//此处P1为对方
		p1.mCardGroup.clear();
		for (int i = 0; i < p1.getCardGroup().length; i++){
			int localKey = p1.getCardGroup()[i];
			for (int j = 0; j < p1.myCards.size(); j++){
				Card card = p1.myCards.get(j);
				if (card.key != localKey)
					continue;
//				ITextureRegion card_region = BitmapTextureAtlasTextureRegionFactory
//					.createFromAsset(cardTextureAtlas, activity.getAssets(),"testCard.png", 64 * i, 86);//testCard.png 须根据卡名变化
				card = new Card(card, findTextureRegionByCardName(card.getName())) {
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
				p1.mCardGroup.add(card);
			}
		}
		cardTextureAtlas.load();
	}
	
	public void unloadTMXMap(){
		tiledMap = null;
		System.gc();
	}
	
	public void unloadPhysicsWorld(){
		engine.runOnUpdateThread(new Runnable()
	    {
	        public void run()
	        {
	            Iterator<Body> localIterator = physicsWorld.getBodies();
	            while (true)
	            {
	                if (!localIterator.hasNext())
	                {
	                    physicsWorld.clearForces();
	                    physicsWorld.clearPhysicsConnectors();
	                    physicsWorld.reset();
	                    physicsWorld.dispose();
	                    System.gc();
	                    return;
	                }
	                try
	                {
	                    final Body localBody = (Body) localIterator.next();
	                    physicsWorld.destroyBody(localBody);
	                } 
	                catch (Exception localException)
	                {
	                    Debug.e(localException);
	                }
	            }
	        }
	    });
	}
	
	public void unloadMenuTextures()
	{
	    menuTextureAtlas.unload();
	}
	    
	public void reloadMenuTextures()
	{
	    menuTextureAtlas.load();
	}
	
	public void unloadSplash(){
		splashTextureAtlas.unload();
		splash_region = null;
	}

	public void unloadGameTextures() {
		onScreenControlTexture.unload();
		playerTextureAtlas.unload();
		npcTextureAtlas.unload();
		hudTextureAtlas.unload();
	}
	
	/**
	 * 从fightingScene返回时调用
	 */
	public void reloadGameTextures(){
		onScreenControlTexture.load();
		playerTextureAtlas.load();
		npcTextureAtlas.load();
		hudTextureAtlas.load();
	}
	
	public void unloadGame(){
		unloadTMXMap();
		unloadPhysicsWorld();
	}

	public void unloadFightingTexture() {
		cardTextureAtlas.unload();
		fightingTextureAtlas.unload();
//		cardTextureAtlas = null;
		fightingTextureAtlas = null;
		System.gc();
	}
	
	public ITextureRegion findTextureRegionByCardName(String cardName){
		if (cardName.equals("chaoxilieren"))
			return cxlr;
		else if (cardName.equals("meirenyu"))
			return mry;
		else if (cardName.equals("bingfa"))
			return bf;
		else if (cardName.equals("difa"))
			return df;
		else if (cardName.equals("henengren"))
			return hnr;
		else if (cardName.equals("jiqiren"))
			return jqr;
		else if (cardName.equals("youyinshiren"))
			return yysr;
		else if (cardName.equals("jianshang"))
			return js;
		else if (cardName.equals("renyuan"))
			return ry;
		else if (cardName.equals("jiaoshou"))
			return jshou;
		else if (cardName.equals("lagelangri"))
			return lglr;
		else if (cardName.equals("lianjinshushi"))
			return ljss;
		else if (cardName.equals("daerwen"))
			return dew;
		else if (cardName.equals("xinmo"))
			return xm;
		else if (cardName.equals("shuren"))
			return sr;
		else if (cardName.equals("chenyinque"))
			return cyq;
		else if (cardName.equals("xiaohushi"))
			return xhs;
		else if (cardName.equals("linchuangdaifu"))
			return lcdf;
		else if (cardName.equals("yaojishi"))
			return yjs;
		else if (cardName.equals("fayi"))
			return fy;
		else if (cardName.equals("tianyan"))
			return ty;
		else if (cardName.equals("sheyingshi"))
			return sys;
		else if (cardName.equals("duoluoyizhe"))
			return dlyz;
		else if (cardName.equals("yimiaozhizaozhe"))
			return ymzzz;
		else if (cardName.equals("shejishi"))
			return sjs;
		else if (cardName.equals("chengxuyuan"))
			return cxy;
		else if (cardName.equals("xinkenan"))
			return xkn;
		else if (cardName.equals("dafashi"))
			return dfs;
		else if (cardName.equals("zhengke"))
			return zk;
		else if (cardName.equals("nengyuanyanjiuzhe"))
			return nyyjz;
		else if (cardName.equals("qianxingzhe"))
			return qxz;
		else if (cardName.equals("guofangsheng"))
			return gfs;
		else if (cardName.equals("huanjingbaohuzhe"))
			return hjbhz;
		else
			return temp_card_region;
	}

	public static ResourceManager Factory(MainGame pGame, BoundCamera pCamera){
		getInstance().activity = pGame;
		getInstance().engine = pGame.getEngine();
		getInstance().camera = pCamera;
		getInstance().vbo = pGame.getVertexBufferObjectManager();
		getInstance().textureManager = pGame.getTextureManager();
		return getInstance();
	}
	
	public static ResourceManager getInstance(){
		return INSTANCE;
	}

}
