package magiccollege.net;

import info.gridworld.grid.Location;

import java.io.IOException;
import java.util.ArrayList;

import magiccollege.Enum.ESceneID;
import magiccollege.fight.Card;
import magiccollege.main.MyData;
import magiccollege.manager.ResourceManager;
import magiccollege.manager.SceneManager;
import magiccollege.net.clientMessage.AcceptFightClientMessage;
import magiccollege.net.clientMessage.BeginAttackClientMessage;
import magiccollege.net.clientMessage.BeginFightClientMessage;
import magiccollege.net.clientMessage.SelectHandClientMessage;
import magiccollege.net.serverMessage.AcceptFightServerMessage;
import magiccollege.net.serverMessage.AfterCalculateServerMessage;
import magiccollege.net.serverMessage.BeforeCalculateServerMessage;
import magiccollege.net.serverMessage.BeginAttackServerMessage;
import magiccollege.net.serverMessage.BeginFightServerMessage;
import magiccollege.net.serverMessage.FightRequestServerMessage;
import magiccollege.net.serverMessage.GetCardServerMessage;
import magiccollege.net.serverMessage.LogOnServerMessage;
import magiccollege.net.serverMessage.QueryNameServerMessage;
import magiccollege.scene.FightingScene;
import magiccollege.scene.NetFightingScene;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.TextMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ColorMenuItemDecorator;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TickerText;
import org.andengine.entity.text.TickerText.TickerTextOptions;
import org.andengine.extension.multiplayer.protocol.adt.message.server.IServerMessage;
import org.andengine.extension.multiplayer.protocol.client.IServerMessageHandler;
import org.andengine.extension.multiplayer.protocol.client.IServerMessageReader.ServerMessageReader;
import org.andengine.extension.multiplayer.protocol.client.connector.ServerConnector;
import org.andengine.extension.multiplayer.protocol.shared.SocketConnection;
import org.andengine.util.HorizontalAlign;

public class MyServerMessageReader extends ServerMessageReader<SocketConnection> implements GameConstants{
	private ResourceManager rm = ResourceManager.getInstance();
	private SceneManager sm = SceneManager.getInstance();
	
	public MyServerMessageReader(){
		this.registerMessage(FLAG_SERVERMESSAGE_LOGON, LogOnServerMessage.class,  new IServerMessageHandler<SocketConnection>() {
			@Override
			public void onHandleMessage(
					ServerConnector<SocketConnection> arg0,
					IServerMessage arg1) throws IOException {
					sm.continueCreateMenuScene();
//					LogOnServerMessage losm = (LogOnServerMessage) arg1;
//					rm.activity.toastOnUIThread("" + losm.getMessage());
				}});
		
		this.registerMessage(FLAG_SERVERMESSAGE_QUERYNAME, QueryNameServerMessage.class,  new IServerMessageHandler<SocketConnection>() {
			@Override
			public void onHandleMessage(
					ServerConnector<SocketConnection> arg0,
					IServerMessage arg1) throws IOException {
		//		rm.activity.toastOnUIThread("received");
				QueryNameServerMessage qnsm = (QueryNameServerMessage) arg1;
				rm.otherPlayerID = qnsm.getID();
				int msg = qnsm.get();
				switch (msg){
				case 5:
					rm.activity.toastOnUIThread("自己打自己是不行的哟！");
					break;
				case 6:
					rm.activity.toastOnUIThread("这个人还没有出生呢！");
					break;
				case 7:
					rm.activity.toastOnUIThread("这个懒虫还在睡觉吧？");
					break;
				case 8:
					if (sm.getCurrentSceneType() == ESceneID.game && rm.canFight){
						rm.camera.setHUD(null);
						rm.camera.setChaseEntity(null);
						float[] center = sm.getLoadingScene().getSceneCenterCoordinates();
						rm.camera.setCenter(center[0], center[1]);
					    sm.setScene(ESceneID.loading);
						TimerHandler th = new TimerHandler(10f, new ITimerCallback(){
							@Override
							public void onTimePassed(TimerHandler arg0) {
								rm.engine.unregisterUpdateHandler(arg0);
								rm.activity.toastOnUIThread("对方似乎没有回应呢？");
								rm.camera.setHUD(sm.getGameScene().getHUD());
								rm.camera.setChaseEntity(rm.player);
								rm.timerHandler = null;
								sm.setScene(ESceneID.game);
							}} );
						rm.timerHandler = th;
						rm.engine.registerUpdateHandler(th);
					}
				}
			}});
		
		this.registerMessage(FLAG_SERVERMESSAGE_FIGHTREQUEST, FightRequestServerMessage.class, new IServerMessageHandler<SocketConnection>(){
			@Override
			public void onHandleMessage(
					ServerConnector<SocketConnection> arg0,
					IServerMessage arg1) throws IOException {
				if (sm.getCurrentSceneType() != ESceneID.game || !rm.canFight)
					return;
				if (rm.analogOnScreenControl.hasChildScene())//正在对话
					return;
				FightRequestServerMessage frsm = (FightRequestServerMessage) arg1;
				final String name = frsm.getName();
				rm.otherPlayerID = frsm.getID();
				//npc对话文本	
        		Text dialogText = new TickerText(10, 10, rm.dialogFont, "玩家<"+name+">邀请你进行对战！", new TickerTextOptions(HorizontalAlign.CENTER, 10), rm.vbo);
        		//黑色半透明对话背景
        		Rectangle rect = new Rectangle(0, 0, rm.camera.getWidth(), rm.camera.getHeight()/2, rm.vbo);
        		rect.setColor(0,0,0);	rect.setAlpha(0.7f);
        		//对话scene
        		final MenuScene dialogScene = new MenuScene(rm.camera);
        		dialogScene.attachChild(rect);//添加背景
        		dialogScene.attachChild(dialogText);//添加对话文本
        		//确认按钮
        		final IMenuItem yesMenuItem = new ColorMenuItemDecorator(new TextMenuItem(1, rm.dialogFont, "yes", rm.vbo), 
        				new org.andengine.util.color.Color(1,0,0), new org.andengine.util.color.Color(0,1,0));
        		dialogScene.addMenuItem(yesMenuItem);
        		//否认按钮
        		final IMenuItem quitMenuItem = new ColorMenuItemDecorator(new TextMenuItem(2, rm.dialogFont, "no", rm.vbo), 
        				new org.andengine.util.color.Color(1,0,0), new org.andengine.util.color.Color(0,1,0));
        		dialogScene.addMenuItem(quitMenuItem);
        		//使按钮按下去会变色
        		dialogScene.buildAnimations();
        		//设置按钮位置
        		yesMenuItem.setPosition(rm.camera.getWidth() - 100, rm.camera.getHeight()/2 - 100);
        		quitMenuItem.setPosition(rm.camera.getWidth() - 100, rm.camera.getHeight()/2 - 50);
        		//对话scene背景不可见
        		dialogScene.setBackgroundEnabled(false);
        		//设置按钮触发事件
        		dialogScene.setOnMenuItemClickListener(new IOnMenuItemClickListener(){
					@Override
					public boolean onMenuItemClicked(final MenuScene pMenuScene, final IMenuItem pMenuItem, final float pMenuItemLocalX, final float pMenuItemLocalY) {
						switch(pMenuItem.getID()) {
						case 1://按了确认按钮
							dialogScene.back();//取消对话
							AcceptFightClientMessage afcm = new AcceptFightClientMessage();
							afcm.set(String.valueOf(MyData.getInstance().getId()), rm.otherPlayerID);
							try {
								rm.serverConnector.sendClientMessage(afcm);
							} catch (IOException e) {
								e.printStackTrace();
							}
							//TODO 如果没错误的话进入战斗，有错误的话要处理
							sm.createNetFightingScene(rm.user2, rm.user);
							return true;
						case 2://按了否认按钮
							dialogScene.back();//取消对话
							return true;
						default:
							break;
						}
						return false;
					}});
        		Scene childScene = rm.analogOnScreenControl.getChildScene();
        		if (childScene != null)
        			childScene.back();
        		rm.analogOnScreenControl.setChildScene(dialogScene);
			}});
		
		this.registerMessage(FLAG_SERVERMESSAGE_ACCEPTFIGHT, AcceptFightServerMessage.class, new IServerMessageHandler<SocketConnection>(){
			@Override
			public void onHandleMessage(
					ServerConnector<SocketConnection> pServerConnector,
					IServerMessage pServerMessage) throws IOException {
				AcceptFightServerMessage afsm = (AcceptFightServerMessage) pServerMessage;
				if (rm.timerHandler == null || !afsm.getOthersID().equals(rm.otherPlayerID)){
					//TODO 返回错误信息
					return;
				}
				rm.timerHandler.setTimerCallbackTriggered(true);
				rm.engine.unregisterUpdateHandler(rm.timerHandler);
				rm.timerHandler = null;
				rm.battleKey = afsm.getBattleKey();
				rm.activity.toastOnUIThread("请等待对方选择先手/后手");
				sm.createNetFightingScene(rm.user, rm.user2);
				
				BeginFightClientMessage bfcm = new BeginFightClientMessage();
				bfcm.set(afsm.getMyID(), rm.otherPlayerID, rm.battleKey, sm.getFightingScene().getCurrentPlayer().desktop.size());
				pServerConnector.sendClientMessage(bfcm);
			}});
		
		this.registerMessage(FLAG_SERVERMESSAGE_BEGINFIGHT, BeginFightServerMessage.class, new IServerMessageHandler<SocketConnection>(){
			@Override
			public void onHandleMessage(
					ServerConnector<SocketConnection> pServerConnector,
					IServerMessage pServerMessage) throws IOException {
				rm.activity.toastOnUIThread("请选择先手/后手");
				BeginFightServerMessage bfsm = (BeginFightServerMessage) pServerMessage;
				rm.battleKey = bfsm.getBattleID();
				System.out.println(bfsm.getBattleID());
				
				//npc对话文本	
        		Text dialogText = new TickerText(10, 10, rm.dialogFont, "请选择先手/后手", new TickerTextOptions(HorizontalAlign.CENTER, 10), rm.vbo);
        		
        		//对话scene
        		final MenuScene dialogScene = new MenuScene(rm.camera);
        		
        		dialogScene.attachChild(dialogText);//添加对话文本
        		//确认按钮
        		final IMenuItem yesMenuItem = new ColorMenuItemDecorator(new TextMenuItem(1, rm.dialogFont, "先手", rm.vbo), 
        				new org.andengine.util.color.Color(1,0,0), new org.andengine.util.color.Color(0,1,0));
        		dialogScene.addMenuItem(yesMenuItem);
        		//否认按钮
        		final IMenuItem quitMenuItem = new ColorMenuItemDecorator(new TextMenuItem(2, rm.dialogFont, "后手", rm.vbo), 
        				new org.andengine.util.color.Color(1,0,0), new org.andengine.util.color.Color(0,1,0));
        		dialogScene.addMenuItem(quitMenuItem);
        		//使按钮按下去会变色
        		dialogScene.buildAnimations();
        		//对话scene背景不可见
        		dialogScene.setBackgroundEnabled(false);
        		//设置按钮触发事件
        		dialogScene.setOnMenuItemClickListener(new IOnMenuItemClickListener(){
					@Override
					public boolean onMenuItemClicked(final MenuScene pMenuScene, final IMenuItem pMenuItem, final float pMenuItemLocalX, final float pMenuItemLocalY) {
						SelectHandClientMessage shcm = new SelectHandClientMessage();
						switch(pMenuItem.getID()) {
						case 1://按了先手按钮
							dialogScene.back();//取消对话
							shcm.set((short)1, rm.battleKey, sm.getFightingScene().getCurrentPlayer().desktop.size());
							try {
								rm.serverConnector.sendClientMessage(shcm);
							} catch (IOException e) {
								e.printStackTrace();
							}
							return true;
						case 2://按了后手按钮
							dialogScene.back();//取消对话
							shcm.set((short)2, rm.battleKey, sm.getFightingScene().getCurrentPlayer().desktop.size());
							try {
								rm.serverConnector.sendClientMessage(shcm);
							} catch (IOException e) {
								e.printStackTrace();
							}
							return true;
						default:
							break;
						}
						return false;
					}});
        		sm.getFightingScene().getHUD().setChildScene(dialogScene);
			}});
		
		this.registerMessage(FLAG_SERVERMESSAGE_GETCARD, GetCardServerMessage.class, new IServerMessageHandler<SocketConnection>(){
			@Override
			public void onHandleMessage(
					ServerConnector<SocketConnection> pServerConnector,
					IServerMessage pServerMessage) throws IOException {
				rm.cardHashMap.clear();
				rm.randoms.clear();
				rm.ranIndex = 0;
				GetCardServerMessage gcsm = (GetCardServerMessage) pServerMessage;
				NetFightingScene scene = (NetFightingScene) sm.getFightingScene();
//				while (!scene.isDone()){
//					System.out.println("not done");
//					try {
//						Thread.sleep(1000);
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					}
//				}
				if (gcsm.getMessage() == 3){//对方先出牌
					scene.setTurn(1);
					scene.getCardToHand(gcsm.getR());
				}else{//己方先出牌
					scene.setTurn(0);
					scene.getCardToHand(gcsm.getR());
				}
			}});
		
		this.registerMessage(FLAG_SERVERMESSAGE_BEFORECALCULATE, BeforeCalculateServerMessage.class, new IServerMessageHandler<SocketConnection>(){
			@Override
			public void onHandleMessage(
					ServerConnector<SocketConnection> pServerConnector,
					IServerMessage pServerMessage) throws IOException {
				BeforeCalculateServerMessage bcsm = (BeforeCalculateServerMessage) pServerMessage;
				ArrayList<Integer> tempList = bcsm.get();
				for (int i = 0; i < tempList.size(); i += 2){
					rm.cardHashMap.append(tempList.get(i), tempList.get(i+1));
				}
				NetFightingScene scene = (NetFightingScene) sm.getFightingScene();
				scene.setCardToGround();
			}});
		
		this.registerMessage(FLAG_SERVERMESSAGE_AFTERCALCULATE, AfterCalculateServerMessage.class, new IServerMessageHandler<SocketConnection>(){
			@Override
			public void onHandleMessage(
					ServerConnector<SocketConnection> pServerConnector,
					IServerMessage pServerMessage) throws IOException {
				NetFightingScene scene = (NetFightingScene) sm.getFightingScene();
//				while (!scene.isDone()){
//					try {
//						Thread.sleep(1000);
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					}
//				}
				
				int turn = Math.abs(scene.getTurn() - 1);
				for (int i = 0; i < FightingScene.DEFAULT_COLS; i++){
					Card card = FightingScene.gr.get(new Location(turn, i));
					if (card != null){
						rm.randoms.add(Math.random());
						rm.randoms.add(Math.random());
					}
				}
				BeginAttackClientMessage bacm = new BeginAttackClientMessage();
				bacm.set(String.valueOf(MyData.getInstance().getId()), rm.battleKey, rm.randoms);
				try {
					rm.serverConnector.sendClientMessage(bacm);
				} catch (IOException e) {
					e.printStackTrace();
				}
				scene.judgeMessState();
			}});
		
		this.registerMessage(FLAG_SERVERMESSAGE_BEGINATTACK, BeginAttackServerMessage.class, new IServerMessageHandler<SocketConnection>(){
			@Override
			public void onHandleMessage(
					ServerConnector<SocketConnection> pServerConnector,
					IServerMessage pServerMessage) throws IOException {
				BeginAttackServerMessage basm = (BeginAttackServerMessage) pServerMessage;
//				String str = new String();////////////////
				ArrayList<Integer> list = basm.get();
				for (int i = 0; i < list.size(); i++){
					double d = (double)list.get(i)/20;
					rm.randoms.add(d);
//					str = str + d + ", ";////////////////
				}
//				rm.randoms.addAll(dList);
//				rm.activity.toastOnUIThread(str);////////////////////
				sm.getFightingScene().judgeMessState();
			}});
	}
}
