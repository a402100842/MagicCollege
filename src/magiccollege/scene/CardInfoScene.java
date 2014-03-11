package magiccollege.scene;

import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;

import magiccollege.Enum.ESceneID;
import magiccollege.fight.Card;
import magiccollege.fight.Skill;
import magiccollege.manager.ResourceManager;
import magiccollege.manager.SceneManager;

public class CardInfoScene extends MyScene {
	private Card card;
	private ResourceManager rm;
	private MyScene backScene;
	private Text skill1text;
	private Text skill2text;
	private Text skill3text;
	
	public CardInfoScene(Card pCard, MyScene pScene){
		card = new Card(pCard);
		rm = ResourceManager.getInstance();
		backScene = pScene;	
		setBackground(new Background(0,0,0));

		card.setSize((rm.camera.getWidth()-60)/2,rm.camera.getHeight()-60);
		card.setPosition(30, (rm.camera.getHeight()-card.getHeight())/2);
		
		card.topText.setPosition((card.getWidth() - 30)/2, 30);
		card.leftText.setPosition(30, (card.getHeight() - 30)/2);
		card.rightText.setPosition(card.getWidth() - 50, (card.getHeight() - 30)/2);
		card.HPText.setPosition((card.getWidth() - 30)/2, card.getHeight() - 60);
		
		card.topText.setScale(3f);
		card.leftText.setScale(3f);
		card.rightText.setScale(3f);
		card.HPText.setScale(3f);
		attachChild(card);

		skill1text =  new Text(rm.camera.getWidth() / 2 + 30, 60, rm.cardFont, "!!", 100, rm.vbo);
		skill1text.setText(Skill.findSkillNameByKey(card.skill[0])+"\n"+Skill.findSkillDescriptionBeKey(card.skill[0]));
		attachChild(skill1text);
		
		skill2text =  new Text(rm.camera.getWidth() / 2 + 30, 120 + skill1text.getHeight(), rm.cardFont, "!!", 100, rm.vbo);
		skill2text.setText(Skill.findSkillNameByKey(card.skill[1])+"\n"+Skill.findSkillDescriptionBeKey(card.skill[1]));
		attachChild(skill2text);
		
		skill3text =  new Text(rm.camera.getWidth() / 2 + 30, 180 + 2 * skill1text.getHeight(), rm.cardFont, "!!", 100, rm.vbo);
		skill3text.setText(Skill.findSkillNameByKey(card.skill[2])+"\n"+Skill.findSkillDescriptionBeKey(card.skill[2]));
		attachChild(skill3text);
		
		this.setOnSceneTouchListener(new IOnSceneTouchListener(){
			@Override
			public boolean onSceneTouchEvent(Scene scene, TouchEvent touchEvent) {
				if (touchEvent.isActionDown()){
					CardInfoScene cardInfoScene = (CardInfoScene)scene;
					cardInfoScene.onBackKeyPressed();
					return true;
				}
				return false;
			}});
	}

	@Override
	public void createScene() {

	}

	@Override
	public void onBackKeyPressed() {
		setOnSceneTouchListener(null);
		this.detachChildren();
		card.dispose();
		skill1text.dispose();
		skill2text.dispose();
		skill3text.dispose();
		card = null;
		rm = null;
		dispose();
		
		SceneManager.getInstance().setScene(backScene);
		backScene = null;
	}

	@Override
	public ESceneID getSceneID() {
		return ESceneID.cardInfo;
	}

	@Override
	public void disposeScene() {
		
	}
}

