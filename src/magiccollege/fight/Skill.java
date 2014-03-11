package magiccollege.fight;

import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.color.Color;

import magiccollege.Enum.EState;
import magiccollege.manager.ResourceManager;
import magiccollege.manager.SceneManager;
import magiccollege.scene.CardInfoScene;
import magiccollege.scene.FightingScene;
import magiccollege.scene.NetFightingScene;

public class Skill {
	private static final Skill INSTANCE = new Skill();
	private SceneManager sm;
	private ResourceManager rm;
	private int index;
	private int index2;
	private ArrayList<Card> otherCards;
	private ArrayList<Card> otherCards2;
	private Grid<Card> gr;
	
	private Skill(){
		index = index2 = 0;
		otherCards = new ArrayList<Card>();
		otherCards2 = new ArrayList<Card>();
		gr = FightingScene.gr;
		sm = SceneManager.getInstance();
		rm = ResourceManager.getInstance();
	}
	
	public static Skill getInstance(){
		return INSTANCE;
	}
	
	public double getRandom(final int border){
		FightingScene scene = sm.getFightingScene();
		if (scene instanceof NetFightingScene){
			return rm.randoms.get(rm.ranIndex++);
		}else
			return Math.random() * border;
	}
	
	/**得到对方场上卡阵 */
	public void getotherCards(Location curL){
		otherCards.clear();
		Card otherCard;
		for (int i=0; i < gr.getNumCols(); i++){
			Location loc = new Location(Math.abs(curL.getRow()-1),i);
			otherCard = gr.get(loc);
			if (otherCard != null)
				otherCards.add(otherCard);
		}
		
	}
	
	/**得到对方场上卡阵 */
	public void getOtherCards2(Location curL){
		otherCards2.clear();
		Card otherCard;
		for (int i=0; i < gr.getNumCols(); i++){
			Location loc = new Location(Math.abs(curL.getRow()-1),i);
			otherCard = gr.get(loc);
			if (otherCard != null)
				otherCards2.add(otherCard);
		}
	}
	
	/**
	 * 得到我方场上卡阵
	 * @param curCard
	 */
	public void getMyCards(final Card curCard){
		Card mycard;
		otherCards.clear();
		int turn = curCard.getLocation().getRow();
		for (int i=0; i < gr.getNumCols(); i++){
			mycard = gr.get(new Location(turn,i));
			if (mycard != null)
				otherCards.add(mycard);
		}
	}
	
	public void launchSkillByKey(final int key, final Card curCard){
		switch (key){
		case 0:
			sm.getFightingScene().launchSkill(curCard);
			break;
		case 1:
			shoot(curCard);
			break;
		case 2:
			iceArrow(curCard);
			break;
	/*	case 3:
			seeThrough();
			break;*/
		case 4:
			fascinate(curCard);
			break;
		case 5:
			iceRain(curCard);
			break;
		case 6:
			perform(curCard);
			break;
		case 7:
			magicShield(curCard);
			break;
		case 8:
			earthCrack(curCard);
			break;/*
		case 9:
			earthStab();
			break;
		case 10:
			explode();
			break;
		case 11:
			legerity();
			break;
		case 12:
			radiate();
			break;
		case 13:
			pollute();
			break;*/
		case 14:
			metal(curCard);
			break;
/*		case 15:
			punch();
			break;*/
		case 16:
			singing(curCard);
			break;
		case 17:
			electricShock(curCard);
			break;
		case 18:
			trade(curCard);
			break;
/*		case 19:
			roar();
			break;*/
		case 20:
			coercion(curCard);
			break;
/*		case 21:
			strong();
			break;*/
		
		case 26:
			flameStrike(curCard);
			break;
		
		case 28:
			SkillEffect.getInstance().evolve(curCard);
			break;
		
		case 33:
			shelter(curCard);
			break;
		
		case 35:
			treat(curCard);
			break;
		case 36:
			heal(curCard);
			break;
		case 37:
			cure(curCard);
			break;
		case 38:
			toxin(curCard);
			break;
		case 39:
			callZombie(curCard);
			break;
		case 40:
			corpse(curCard);
			break;
		
		case 43:
			imagination(curCard);
			break;
		
		case 46:
			revive(curCard);
			break;
		case 47:
			immune(curCard);
			break;
		case 48:
			prevent(curCard);
			break;
		
		case 51:
			magicShield(curCard);
			break;
		case 52:
			commandment(curCard);
			break;
		case 53:
			massesPower(curCard);
			break;
		
		case 56:
			SkillEffect.getInstance().releaseEnergy(curCard);
			break;
		
	/*	case 58:
			assassinate(curCard);
			break;*/
		case 59:
			lurk(curCard);
			break;
	
		case 61:
			antiPollute(curCard);
			break;	
			
			/*
		case 81:
			recover():
			break;
		case 82:
			charge();
			break;
			*/
		case 99:
			sweepAway(curCard);
			break;
			
		case 101:
		case 102:
		case 103:
		case 104:
		case 105:
		case 106:
		case 107:
		case 108:
			makeAdjust(curCard);
			break;
			
		case 201:
		case 202:
		case 203:
			makeReverseAdjust(curCard, null);
			break;
		default:
			sm.getFightingScene().launchSkill(curCard);
			break;
			//throw new IllegalStateException("The Skill " + key + " not exist!");
		}
	}

	public static String findSkillNameByKey(int key){
		String str = new String();
		switch(key){
		case 0:
			str = "";
			break;
		case 1:
			str = "射击";
			break;
		case 2:
			str = "冰箭";
			break;
		case 3:
			str = "看破";
			break;
		case 4:
			str = "迷魂";
			break;
		case 5:
			str = "冰雨";
			break;
		case 6:
			str = "演奏";
			break;
		case 7:
			str = "魔盾";
			break;
		case 8:
			str = "地裂";
			break;
		case 9:
			str = "地刺";
			break;
		case 10:
			str = "爆炸";
			break;
		case 11:
			str = "轻灵";
			break;
		case 12:
			str = "辐射";
			break;
		case 13:
			str = "污染";
			break;
		case 14:
			str = "金属";
			break;
		case 15:
			str = "猛击";
			break;
		case 16:
			str = "歌颂";
			break;
		case 17:
			str = "电击";
			break;
		case 18:
			str = "交易";
			break;
		case 19:
			str = "咆哮";
			break;
		case 20:
			str = "威压";
			break;
		case 21:
			str = "强壮";
			break;
		
		case 26:
			str = "烈焰风暴";
			break;
		
		case 28:
			str = "进化";
			break;
		
		
		case 33:
			str = "庇护";
			break;
		
		case 35:
			str = "治疗";
			break;
		case 36:
			str = "治愈";
			break;
		case 37:
			str = "祛病";
			break;
		case 38:
			str = "毒素";
			break;
		case 39:
			str = "召唤";
			break;
		case 40:
			str = "尸毒";
			break;
		
		
		case 43:
			str = "影像";
			break;
		
		case 46:
			str = "复活";
			break;
		case 47:
			str = "免疫";
			break;
		case 48:
			str = "预防";
			break;
		
		
		case 51:
			str = "防火墙";
			break;
		case 52:
			str = "戒律";
			break;
		case 53:
			str = "群众的力量";
			break;
		
		case 56:
			str = "放能";
			break;
		
		case 58:
			str = "刺杀";
			break;
		case 59:
			str = "潜行";
			break;
		
		case 61:
			str = "防污";
			break;
		
		case 81:
			str = "恢复";
			break;
		case 82:
			str = "充能";
			break;
			
		case 91:
			str = "洞察";
			break;
		case 92:
			str = "吼叫";
			break;
		case 93:
			str = "扭曲";
			break;
		case 94:
			str = "长生";
			break;
		case 95:
			str = "新生";
			break;
		case 96:
			str = "吸血";
			break;
		case 97:
			str = "撕裂";
			break;
		case 98:
			str = "注射";
			break;
		case 99:
			str = "横扫";
			break;
			
		case 101:
			str = "修整";
			break;
		case 102:
			str = "权衡";
			break;
		case 103:
			str = "数学";
			break;
		case 104:
			str = "实验";
			break;
		case 105:
			str = "勘察";
			break;
		case 106:
			str = "修饰";
			break;
		case 107:
			str = "重载";
			break;
		case 108:
			str = "举荐";
			break;
			
		case 201:
			str = "黑心";
			break;
		case 202:
			str = "阴谋";
			break;
		case 203:
			str = "蛊惑";
			break;
			
		default:
			str =  "";
			break;
		}
		return str;
	}
	
	public static String findSkillDescriptionBeKey(int key){
		String str = new String();
		switch (key){
		case 0:
			str =  "";
			break;
		case 1:
			str =  "对敌方HP最少的（1+x）张卡\n造成2（1+x）点物理伤害";
			break;
		case 2:
			str = "对敌方一张卡造成x点魔法伤害，\n并有0.3（1+x)几率使其进入冻结状态";
			break;
		case 3:
			str = "造成普通攻击伤害时\n对敌方英雄造成等量伤害";
			break;
		case 4:
			str = "0.3（1+x）几率使敌方\n一张卡进入混乱状态";
			break;
		case 5:
			str = "对敌方所有卡造成x点魔法伤害，\n并有0.15（1+x)几率使其进入冻结状态";
			break;
		case 6:
			str = "恢复己方英雄（20+10x）点血量";
			break;
		case 7:
			str = "反射（1+x）次魔法伤害";
			break;
		case 8:
			str = "（0.3+x/4）几率困住敌方\n2张卡，使其进入冻结状态";
			break;
		case 9:
			str = "无视敌方防御技能，\n杀死敌方卡牌时不触发其效果";
			break;
		case 10:
			str = "死亡时对敌方所有卡牌\n造成x点物理伤害";
			break;
		case 11:
			str = "（0.4+0.2x）几率\n闪避普通攻击";
			break;
		case 12:
			str = "攻击命中一张卡后使其\n及其左右两张卡进入禁疗状态";
			break;
		case 13:
			str = "受到普通攻击时有0.3（1+x）几率\n使攻击者及其左右两张卡进入中毒状态";
			break;
		case 14:
			str = "免疫魔法伤害和异常状态";
			break;
		case 15:
			str = "攻击命中一张卡后\n对其发动“逆调整”";
			break;
		case 16:
			str = "对己方场上所有卡牌发动“调整“,\n上场时和<协助状态>数量改变时发动";
			break;
		case 17:
			str = "对敌方2张卡造成x点魔法伤害，\n并有0.3（1+x)几率使其进入麻痹状态";
			break;
		case 18:
			str = "对敌方（1+x）张卡造成（1+x）点\n魔法伤害并使自身恢复等量HP";
			break;
		case 19:
			str = "额外进行 x 次普通攻击";
			break;
		case 20:
			str = "使敌方所有卡牌减少\n（1+x）点攻击力";
			break;
		case 21:
			str = "受到普通攻击时\n最多受到3-x点伤害";
			break;
		
			
		case 101:
		case 102:
		case 103:
		case 104:
		case 105:
		case 106:
		case 107:
		case 108:
			str = "对随机一张己方卡牌发动“调整”";
			break;
			
		case 201:
		case 202:
		case 203:
			str = "对随机一张对方卡牌发动“逆调整”";
		default:
			str =  "";
			break;
		}
		return str;
	}
	
	//-------------------------------------------
	// skill methods
	//-------------------------------------------
	
	/**
	 * 射击：对敌方HP最少的（1+x）张卡造成2（1+x）点物理伤害
	 * */
	public void shoot(final Card curCard){
		getotherCards(curCard.getLocation());
		if (!otherCards.isEmpty()){
			Comparator<Card> comparator = new Comparator<Card>(){
				@Override
				public int compare(Card c0, Card c1) {

					return c0.getHP()-c1.getHP();
				}
			};
			Collections.sort(otherCards,comparator);
			index = otherCards.size();
			int x = curCard.getAssistState();
			if (x + 1 < index)
				index = x;
			else
				index--;
			continueShoot(curCard);
		}else
			sm.getFightingScene().launchSkill(curCard);
	}
	
	public void continueShoot(final Card curCard){
		if (index >= 0){
			final Card theCard = otherCards.get(index);
			SkillEffect.getInstance().shoot(curCard, theCard);
		}else{
			sm.getFightingScene().launchSkill(curCard);
		}
	}
	
	public void continueShoot2(final Card curCard, final Card theCard){
		int cut = 2 * (1 + curCard.getAssistState());
		sm.getFightingScene().setHPText(curCard, null, theCard, -cut, new INextFunc(){
			@Override
			public void nextFunc(Card curCard, Card targetCard,
					INextFunc nextFunc2) {
				
				sm.getFightingScene().afterCutCardHP(curCard, theCard, new INextFunc(){
					@Override
					public void nextFunc(Card curCard, Card targetCard,
							INextFunc nextFunc2) {
						index--;
						continueShoot(curCard);
						
					}});
				
			}}, null);
	}
	
	/**
	 * 冰箭：对敌方一张卡造成2*x+1点魔法伤害，并有0.3（1+x)几率使其进入冻结状态
	 * */
	public void iceArrow(final Card curCard){
		getotherCards(curCard.getLocation());
		if (!otherCards.isEmpty()){
			int r = (int)(getRandom(otherCards.size()));
			Card theCard = otherCards.get(r);
			SkillEffect.getInstance().iceArrow(curCard, theCard);
		}else
			sm.getFightingScene().launchSkill(curCard);
	}
	
	public void continueIceArrow(Card curCard, Card theCard){
		int x = curCard.getAssistState();
		int cut = x * 2 + 1;
		if (theCard.reflection-- > 0){
			SkillEffect.getInstance().reflection(curCard, theCard, cut, new INextFunc(){

				@Override
				public void nextFunc(Card curCard, Card targetCard,
						INextFunc nextFunc2) {
					sm.getFightingScene().launchSkill(curCard);//反射完之后发动下一个技能（这时curCard可能已经为null）
				}});//显示反射效果
		}else{
			final double probability = 0.3*(1+x);
			this.judgeMagicImmune(curCard, theCard, cut, new INextFunc(){
				@Override
				public void nextFunc(Card curCard, Card targetCard,
						INextFunc nextFunc2) {
					Skill.this.judgeStateImmune(EState.freeze, probability, curCard, null, targetCard, new INextFunc(){
						@Override
						public void nextFunc(Card curCard, Card targetCard,
								INextFunc nextFunc2) {
							sm.getFightingScene().launchSkill(curCard);
						}});
				}});
		}
	}
	
	public void judgeStateImmune(final EState state, final double pProbability, final Card curCard, final Card targetCard, final Card theCard, final INextFunc nextFunc){
		if (theCard == null){
			nextFunc.nextFunc(curCard, targetCard, null);
		}else{
			if (theCard.state_immune){
				nextFunc.nextFunc(curCard, targetCard, null);
			}else{
				double probability = getRandom(1);
				if (probability < pProbability){
					Rectangle rect = new Rectangle(theCard.getWidth()/3, theCard.getHeight()/3, theCard.getWidth()/3, theCard.getHeight()/3, rm.vbo);
					rect.setAlpha(0.2f);
					switch (state){
					case banTreat:
						if (!theCard.banTreat){
							theCard.banTreat = true;
							rect.setColor(Color.BLACK);
							rect.setTag(EState.BANTREAT_TAG);
							theCard.attachChild(rect);
						}
						break;
					case freeze:
						if (!theCard.freeze){
							theCard.freeze = true;
							rect.setColor(Color.BLUE);
							rect.setTag(EState.FREEZE_TAG);
							theCard.attachChild(rect);
						}
						break;
					case mess:
						if (!theCard.mess){
							theCard.mess = true;
							rect.setColor(Color.PINK);
							rect.setTag(EState.MESS_TAG);
							theCard.attachChild(rect);
						}
						break;
					case numb:
						if (!theCard.numb){
							theCard.numb = true;
							rect.setColor(Color.YELLOW);
							rect.setTag(EState.NUMB_TAG);
							theCard.attachChild(rect);
						}
						break;
					case poison:
						if (!theCard.poison){
							theCard.poison = true;
							rect.setColor(Color.CYAN);
							rect.setTag(EState.POISON_TAG);
							theCard.attachChild(rect);
						}
						break;
					default:
						throw new IllegalStateException("NO this state!");
					}
				}
				nextFunc.nextFunc(curCard, targetCard, null);
			}
		}
	}
	
	public void judgeMagicImmune(final Card curCard, final Card theCard, final int cut, final INextFunc nextFunc){
		if (theCard.magic_immune){
			SkillEffect.getInstance().magicImmune(curCard, theCard, cut, nextFunc);
		}else{
			sm.getFightingScene().setHPText(curCard, theCard, theCard, -cut, new INextFunc(){
				@Override
				public void nextFunc(Card curCard, Card targetCard,
						INextFunc nextFunc2) {
					sm.getFightingScene().afterCutCardHP(curCard, targetCard, nextFunc2);
				}}, nextFunc);
		}
	}
	
	/**
	 * 看破：造成普通攻击伤害时对敌方英雄造成等量伤害
	 * */
	public void seeThrough(){
		
	}
	
	/**
	 * 迷魂：0.3（1+x）几率使敌方一张卡进入混乱状态
	 * */
	public void fascinate(Card curCard){
		getotherCards(curCard.getLocation());
		if (!otherCards.isEmpty()){
			int r = (int)(getRandom(otherCards.size()));
			Card theCard = otherCards.get(r);
			SkillEffect.getInstance().fascinate(curCard, theCard);
		}else
			sm.getFightingScene().launchSkill(curCard);
	}
	
	public void continueFascinate(Card curCard, Card theCard){
		if (theCard.reflection-- > 0){
			SkillEffect.getInstance().reflection(curCard, theCard, 0, new INextFunc(){
				@Override
				public void nextFunc(Card curCard, Card targetCard,
						INextFunc nextFunc2) {
					sm.getFightingScene().launchSkill(curCard);
				}});
		}else{
			int x = curCard.getAssistState();
			double probability = 0.3*(1+x);
			this.judgeStateImmune(EState.mess, probability, curCard, theCard, theCard, new INextFunc(){
				@Override
				public void nextFunc(Card curCard, Card targetCard,
						INextFunc nextFunc2) {
					sm.getFightingScene().launchSkill(curCard);
				}});
		}
	}
	
	
	/**
	 * 冰雨：对敌方所有卡造成x+1点魔法伤害，并有0.15（1+x)几率使其进入冻结状态
	 * */
	public void iceRain(final Card curCard){
		getotherCards(curCard.getLocation());
		if (!otherCards.isEmpty()){
			index = otherCards.size() - 1;
			int x = curCard.getAssistState();
			int cut = 1 + x;
			final double probability = 0.15*(1+x);
			SkillEffect.getInstance().iceRain(curCard, cut, probability);
		}else
			sm.getFightingScene().launchSkill(curCard);
	}

	public void continueIceRain(final Card curCard, final int cut, final double probability){
		if (index >= 0){
			final Card theCard = otherCards.get(index);
			
			if (theCard.reflection-- > 0){
				SkillEffect.getInstance().reflection(curCard, theCard, cut, new INextFunc(){
					@Override
					public void nextFunc(Card curCard, Card targetCard,
							INextFunc nextFunc2) {
						index--;
						continueIceRain(curCard, cut, probability);//curCard可能为Null
					}});//显示反射效果
			}else{
				this.judgeMagicImmune(curCard, theCard, cut, new INextFunc(){
					@Override
					public void nextFunc(Card curCard, Card targetCard,
							INextFunc nextFunc2) {
						Skill.this.judgeStateImmune(EState.freeze, probability, curCard, null, targetCard, new INextFunc(){
							@Override
							public void nextFunc(Card curCard, Card targetCard,
									INextFunc nextFunc2) {
								index--;
								continueIceRain(curCard, cut, probability);
							}});
					}});
			}
		}else{
			sm.getFightingScene().launchSkill(curCard);
		}
	}
	
	/**
	 * 演奏：恢复己方英雄（20+10x）点血量
	 * */
	public void perform(Card curCard){
		SkillEffect.getInstance().perform(curCard);
	}
	
	/**
	 * 魔盾：反射（1+x）次魔法伤害
	 * */
	public void magicShield(Card curCard){
		int x = curCard.getAssistState();
		curCard.reflection = 1 + x;
		SkillEffect.getInstance().magicShield(curCard);
	}
	
	/**
	 * 地裂：（0.3+x/4）几率困住敌方2张卡，使其进入冻结状态
	 * */
	public void earthCrack(final Card curCard){
		getotherCards(curCard.getLocation());
		if (!otherCards.isEmpty()){
			ArrayList<Card> list = new ArrayList<Card>();
			
			if (otherCards.size() > 1){
				int r = (int)(getRandom(otherCards.size()));
				list.add(otherCards.get(r));
				otherCards.remove(r);
			}
			
			int r = (int)(getRandom(otherCards.size()));
			list.add(otherCards.get(r));
			otherCards.remove(r);
			
			otherCards.clear();
			otherCards.addAll(list);
			index = list.size() - 1;
			continueEarthCrack(curCard);
		}else
			sm.getFightingScene().launchSkill(curCard);
	}
	
	public void continueEarthCrack(final Card curCard){
		if (index >= 0){
			final Card theCard = otherCards.get(index);
			SkillEffect.getInstance().earthCrack(curCard, theCard);
		}else
			sm.getFightingScene().launchSkill(curCard);
	}
	
	public void continueEarthCrack2(final Card curCard, final Card theCard){
		if (theCard.reflection-- > 0){
			SkillEffect.getInstance().reflection(curCard, theCard, 0, new INextFunc(){
				@Override
				public void nextFunc(Card curCard, Card targetCard,
						INextFunc nextFunc2) {
					index--;
					continueEarthCrack(curCard);
				}});
		}else{
			int x = curCard.getAssistState();
			double probability = (0.3+x/4);
			this.judgeStateImmune(EState.freeze, probability, curCard, theCard, theCard, new INextFunc(){
				@Override
				public void nextFunc(Card curCard, Card targetCard,
						INextFunc nextFunc2) {
					index--;
					continueEarthCrack(curCard);
				}});
		}
	}
	
	
	/**
	 * 地刺：无视敌方防御技能，杀死敌方卡牌时不触发其效果
	 * */
	public void earthStab(){
		
	}
	
	/**
	 * 爆炸：死亡时对敌方所有卡牌造成x+3点物理伤害
	 * @param targetCard 当前正在死亡的卡
	 * @param curCard 促使targetCard死亡的卡
	 * */
	public void explode(Card curCard, Card targetCard, final INextFunc nextFunc){
		getOtherCards2(targetCard.getLocation());
		index2 = otherCards2.size() - 1;
		continueExplode(curCard, targetCard, nextFunc);
	}
	
	public void continueExplode(Card curCard, Card targetCard, INextFunc nextFunc){
		if (index2 >= 0){
			int cut = targetCard.getAssistState() + 3;
			final Card theCard = otherCards2.get(index2);//即将受到伤害的卡
			sm.getFightingScene().setHPText(curCard,targetCard,theCard,-cut,new INextFunc(){
				@Override
				public void nextFunc(Card curCard, Card targetCard,
						INextFunc nextFunc2) {
					sm.getFightingScene().afterSimplyCutCardHP(curCard, targetCard, theCard, new INextFunc(){
						@Override
						public void nextFunc(Card curCard, Card targetCard,
								INextFunc nextFunc2) {
							index2--;
							continueExplode(curCard, targetCard, nextFunc2);
						}},nextFunc2);
				}},nextFunc);
		}else{
				sm.getFightingScene().judge94Perdure(curCard, targetCard, nextFunc);
		}
	}
	
	/**
	 * 轻灵：（0.4+0.2x）几率闪避普通攻击
	 */
	public void legerity(){
		
	}
	
	
	/**
	 *	金属：免疫魔法伤害和异常状态
	 * */
	public void metal(final Card curCard){
		curCard.magic_immune = true;
		curCard.state_immune = true;
		SkillEffect.getInstance().immune(curCard, curCard);
	}
	
	/**
	 * 调整
	 * @param pCard target card
	 * */
	public void adjust(final Card curCard, final Card targetCard, final Card pCard, final INextFunc nextFunc){
		Location curLoc = pCard.getLocation();
		Location leftLoc = curLoc.getAdjacentLocation(Location.LEFT);
		Location rightLoc = curLoc.getAdjacentLocation(Location.RIGHT);
		Card leftCard = null;
		Card rightCard = null;
		int leftDif = 100;
		int rightDif = 100;
		if (gr.isValid(leftLoc)){
			leftCard = gr.get(leftLoc);
			if (leftCard != null)
				leftDif = pCard.getLeft() - leftCard.getRight();
		}
		if (gr.isValid(rightLoc)){
			rightCard = gr.get(rightLoc);
			if (rightCard != null)
				rightDif = pCard.getRight() - rightCard.getLeft();
		}
		if (Math.abs(leftDif) <= Math.abs(rightDif)){
			if (leftCard != null){
				if (leftDif < 0){
					pCard.setLeft(pCard.getLeft() + 1);
				}else if (leftDif > 0){
					pCard.setLeft(pCard.getLeft() - 1);
				}
			}else if (rightCard != null){
				if (rightDif < 0){
					pCard.setRight(pCard.getRight() + 1);
				}else if (rightDif > 0){
					pCard.setRight(pCard.getRight() - 1);
				}
			}
		}else{
			if (rightCard != null){
				if (rightDif < 0){
					pCard.setRight(pCard.getRight() + 1);
				}else if (rightDif > 0){
					pCard.setRight(pCard.getRight() - 1);
				}
			}else if (leftCard != null){
				if (leftDif < 0){
					pCard.setLeft(pCard.getLeft() + 1);
				}else if (leftDif > 0){
					pCard.setLeft(pCard.getLeft() - 1);
				}
			}
		}
		nextFunc.nextFunc(curCard, targetCard, null);
	}
	
	/**
	 * 逆调整
	 * */
	public void reverseAdjust(final Card curCard, final Card targetCard, final Card pCard, final INextFunc nextFunc){
		Location curLoc = pCard.getLocation();
		Location leftLoc = curLoc.getAdjacentLocation(Location.LEFT);
		Location rightLoc = curLoc.getAdjacentLocation(Location.RIGHT);
		Card leftCard = null;
		Card rightCard = null;
		int leftDif = 100;
		int rightDif = 100;
		if (gr.isValid(leftLoc)){
			leftCard = gr.get(leftLoc);
			if (leftCard != null)
				leftDif = pCard.getLeft() - leftCard.getRight();
		}
		if (gr.isValid(rightLoc)){
			rightCard = gr.get(rightLoc);
			if (rightCard != null)
				rightDif = pCard.getRight() - rightCard.getLeft();
		}
		if (Math.abs(leftDif) < Math.abs(rightDif)){
			if (leftCard != null){
				if (leftDif <= 0){
					pCard.setLeft(pCard.getLeft() - 1);
				}else
					pCard.setLeft(pCard.getLeft() + 1);
			}else if (rightCard != null){
				if (rightDif <= 0){
					pCard.setRight(pCard.getRight() - 1);
				}else
					pCard.setRight(pCard.getRight() + 1);
			}
		}else{
			if (rightCard != null){
				if (rightDif <= 0){
					pCard.setRight(pCard.getRight() - 1);
				}else
					pCard.setRight(pCard.getRight() + 1);
			}else if (leftCard != null){
					if (leftDif <= 0){
						pCard.setLeft(pCard.getLeft() - 1);
					}else
						pCard.setLeft(pCard.getLeft() + 1);
			}
		}
		nextFunc.nextFunc(curCard, targetCard, null);
	}
		
	/**
	 * 歌颂：对己方场上所有卡牌发动“调整”，上场时和<协助状态>数量改变时发动
	 * */
	public void singing(final Card curCard){
		if (!curCard.isSpecialSkillDone()){
			curCard.setSpecialSkill(true);
			getMyCards(curCard);
			index = otherCards.size() - 1;
			SkillEffect.getInstance().singing(curCard);
		}else
			sm.getFightingScene().launchSkill(curCard);
	}
	
	public void continueSinging(final Card curCard){
		if (index >= 0){
			Card theCard = otherCards.get(index);
			SkillEffect.getInstance().adjust(curCard, null, theCard, new INextFunc(){
				@Override
				public void nextFunc(Card curCard, Card targetCard,
						INextFunc nextFunc2) {
					index--;
					continueSinging(curCard);
				}});
		}else
			sm.getFightingScene().launchSkill(curCard);
	}
	
	/**
	 * 电击：对敌方2张卡造成(x + 1)点魔法伤害，并有0.3（1+x)几率使其进入麻痹状态
	 * */
	public void electricShock(final Card curCard){
		getotherCards(curCard.getLocation());
		if (!otherCards.isEmpty()){
			ArrayList<Card> list = new ArrayList<Card>();
			if (otherCards.size() > 1){
				int r = (int)(getRandom(otherCards.size()));
				list.add(otherCards.get(r));
				otherCards.remove(r);
			}
			
			int r = (int)(getRandom(otherCards.size()));
			list.add(otherCards.get(r));
			otherCards.remove(r);
			
			otherCards.clear();
			otherCards.addAll(list);
			index = list.size() - 1;
			int x = curCard.getAssistState();
			int cut = 1 + x;
			final double probability = 0.3*(1+x);
			continueElectricShock(curCard, cut, probability);
		}else
			sm.getFightingScene().launchSkill(curCard);
	}
	
	public void continueElectricShock(final Card curCard, final int cut, final double probability){
		if (index >= 0){
			final Card theCard = otherCards.get(index);
			SkillEffect.getInstance().electricShock(curCard, theCard, cut, probability);
		}else
			sm.getFightingScene().launchSkill(curCard);
	}
	
	public void continueElectricShock2(final Card curCard, final Card theCard, final int cut, final double probability){
		if (theCard.reflection-- > 0){
			SkillEffect.getInstance().reflection(curCard, theCard, cut, new INextFunc(){
				@Override
				public void nextFunc(Card curCard, Card targetCard,
						INextFunc nextFunc2) {
					index--;
					continueElectricShock(curCard, cut, probability);
				}});
		}else{
			this.judgeMagicImmune(curCard, theCard, cut, new INextFunc(){
				@Override
				public void nextFunc(Card curCard, Card targetCard,
						INextFunc nextFunc2) {
					Skill.this.judgeStateImmune(EState.numb, probability, curCard, null, targetCard, new INextFunc(){
						@Override
						public void nextFunc(Card curCard, Card targetCard,
								INextFunc nextFunc2) {
							index--;
							continueElectricShock(curCard, cut, probability);
						}});
				}});
		}
	}
	
	/**
	 * 交易：对敌方（1+x）张卡造成1+x点魔法伤害并使自身恢复等量HP
	 * */
	public void trade(final Card curCard){
		getotherCards(curCard.getLocation());
		if (!otherCards.isEmpty()){
			int x = curCard.getAssistState();
			int i = otherCards.size();
			if (x + 1 < i)
				i = x;
			else
				i--;
			ArrayList<Card> list = new ArrayList<Card>();
			for (; i >= 0; i--){
				int r = (int) (getRandom(otherCards.size()));
				list.add(otherCards.get(r));
				otherCards.remove(r);
			}
			otherCards.clear();
			otherCards.addAll(list);
			index = otherCards.size() - 1;
			int cut = 1 + x;
			continueTrade(curCard, cut);
		}else
			sm.getFightingScene().launchSkill(curCard);
	}
	
	public void continueTrade(final Card curCard, final int cut){
		if (index >= 0){
			final Card theCard = otherCards.get(index);
			SkillEffect.getInstance().trade(curCard, theCard, cut);;
		}else
			sm.getFightingScene().launchSkill(curCard);
	}
	
	public void continueTrade2(final Card curCard, final Card theCard, final int cut){
		if (theCard.reflection-- > 0){
			SkillEffect.getInstance().reflection(curCard, theCard, cut, new INextFunc(){
				@Override
				public void nextFunc(Card curCard, Card targetCard,
						INextFunc nextFunc2) {
					index--;
					continueTrade(curCard, cut);
				}});
		}else{
			final int curHP = theCard.getHP();
			this.judgeMagicImmune(curCard, theCard, cut, new INextFunc(){
				@Override
				public void nextFunc(Card curCard, Card targetCard,
						INextFunc nextFunc2) {
					int change = curHP - theCard.getHP();
					sm.getFightingScene().setHPText(curCard, null, curCard, change, new INextFunc(){
						@Override
						public void nextFunc(Card curCard, Card targetCard,
								INextFunc nextFunc2) {
							index--;
							continueTrade(curCard, cut);
						}}, null);
				}});
		}
	}
	
	/**
	 * 咆哮：额外进行x次普通攻击
	 * */
	public void roar(){
		
	}
	
	/**
	 * 威压：使敌方所有卡牌减少1+x点攻击力
	 * */
	public void coercion(Card curCard){
		getotherCards(curCard.getLocation());
		if (!otherCards.isEmpty()){
			SkillEffect.getInstance().coercion(curCard);
		}else
			sm.getFightingScene().launchSkill(curCard);
	}
	
	public void continueCoercion(Card curCard){
		int x = curCard.getAssistState();
		for (Card theCard : otherCards){
			theCard.setTop(theCard.getTop() - 1 + x);
		}
		sm.getFightingScene().launchSkill(curCard);
	}
	
	/**
	 * 强壮：受到普通攻击时最多受到3-x点伤害
	 * */
	public void strong(){
	
	}
	
	/**
	 * 对某张卡发动“调整”
	 */
	private void makeAdjust(final Card curCard){
		getMyCards(curCard);
		if (!otherCards.isEmpty()){
			int r = (int) (getRandom(otherCards.size()));
			Card theCard = otherCards.get(r);
			SkillEffect.getInstance().adjust(curCard, theCard);
		}else
			sm.getFightingScene().launchSkill(curCard);
	}
	
	/**
	 * 对某张卡发动“逆调整”
	 */
	private void makeReverseAdjust(final Card curCard, final Card targetCard){
		getotherCards(curCard.getLocation());
		if (!otherCards.isEmpty()){
			int r = (int) (getRandom(otherCards.size()));
			Card theCard = otherCards.get(r);
			SkillEffect.getInstance().reverseAdjust(curCard, targetCard, theCard, new INextFunc(){
				@Override
				public void nextFunc(Card curCard, Card targetCard,
						INextFunc nextFunc2) {
					sm.getFightingScene().launchSkill(curCard);
				}});
		}else
			sm.getFightingScene().launchSkill(curCard);
	}
	
	public void snarl(){
		//TODO
	}
	
	public void sweepAway(final Card curCard) {
		curCard.attackcount = 2;
		sm.getFightingScene().launchSkill(curCard);
	}
	
	/**
	 * 辐射：攻击命中一张卡后使其及其左右两张卡进入禁疗状态
	 * */
	public void radiate(final Card curCard, final Card targetCard){
		Location curLoc = targetCard.getLocation();
		Location leftLoc = curLoc.getAdjacentLocation(Location.LEFT);
		Location rightLoc = curLoc.getAdjacentLocation(Location.RIGHT);
		otherCards.clear();
		if (gr.isValid(rightLoc)){
			Card card = gr.get(rightLoc);
			if (card != null)
				otherCards.add(card);
		}
		if (gr.isValid(leftLoc)){
			Card card = gr.get(leftLoc);
			if (card != null)
				otherCards.add(card);
		}
		otherCards.add(targetCard);
		index = otherCards.size() - 1;
		continueRadiate(curCard, targetCard);
	}
	
	public void continueRadiate(final Card curCard, final Card targetCard){
		if (index >= 0){
			double pProbability = 1;
			judgeStateImmune(EState.banTreat, pProbability, curCard, targetCard, otherCards.get(index), new INextFunc(){
				@Override
				public void nextFunc(Card curCard, Card targetCard,
						INextFunc nextFunc2) {
					index--;
					continueRadiate(curCard, targetCard);
				}});
		}else
			sm.getFightingScene().judge97Tear(curCard, targetCard);
	}

	/**
	 * 污染：受到普通攻击时有0.3（1+x）几率使攻击者及其左右两张卡进入中毒状态
	 * */
	public void pollute(final Card curCard, final Card targetCard){
		Location curLoc = curCard.getLocation();
		Location leftLoc = curLoc.getAdjacentLocation(Location.LEFT);
		Location rightLoc = curLoc.getAdjacentLocation(Location.RIGHT);
		otherCards.clear();
		if (gr.isValid(rightLoc)){
			Card card = gr.get(rightLoc);
			if (card != null)
				otherCards.add(card);
		}
		if (gr.isValid(leftLoc)){
			Card card = gr.get(leftLoc);
			if (card != null)
				otherCards.add(card);
		}
		otherCards.add(curCard);
		index = otherCards.size() - 1;
		continuePollute(curCard, targetCard);
	}
	
	public void continuePollute(final Card curCard, final Card targetCard){
		if (index >= 0){
			double pProbability = (targetCard.getAssistState() + 1) * 0.3;
			judgeStateImmune(EState.poison, pProbability, curCard, targetCard, otherCards.get(index), new INextFunc(){
				@Override
				public void nextFunc(Card curCard, Card targetCard,
						INextFunc nextFunc2) {
					index--;
					continuePollute(curCard, targetCard);
				}});
		}else
			sm.getFightingScene().judge95Renascence(curCard, targetCard);
	}

	/**
	 * 猛击：攻击命中一张卡后对其发动“逆调整”
	 * */
	public void punch(final Card curCard, final Card targetCard){
		getotherCards(curCard.getLocation());
		int r = (int) (getRandom(otherCards.size()));
		Card theCard = otherCards.get(r);
		SkillEffect.getInstance().reverseAdjust(curCard, targetCard, theCard, new INextFunc(){
			@Override
			public void nextFunc(Card curCard, Card targetCard,
					INextFunc nextFunc2) {
				sm.getFightingScene().judge3SeeThrough(curCard, targetCard);
			}});
	}
	
	/**
	 * 烈焰风暴：对敌方所有卡牌造成（x + 3）点魔法伤害
	 */
	public void flameStrike(final Card curCard){
		getotherCards(curCard.getLocation());
		if (!otherCards.isEmpty()){
			index = otherCards.size() - 1;
			int x = curCard.getAssistState();
			int cut = 3 + x;
			SkillEffect.getInstance().flameStrike(curCard, cut);
		}else
			sm.getFightingScene().launchSkill(curCard);
	}
	
	public void continueFlameStrike(final Card curCard, final int cut){
		if (index >= 0){
			final Card theCard = otherCards.get(index);
			
			if (theCard.reflection-- > 0){
				SkillEffect.getInstance().reflection(curCard, theCard, cut, new INextFunc(){
					@Override
					public void nextFunc(Card curCard, Card targetCard,
							INextFunc nextFunc2) {
						index--;
						continueFlameStrike(curCard, cut);//curCard可能为Null
					}});//显示反射效果
			}else{
				this.judgeMagicImmune(curCard, theCard, cut, new INextFunc(){
					@Override
					public void nextFunc(Card curCard, Card targetCard,
							INextFunc nextFunc2) {
						index--;
						continueFlameStrike(curCard, cut);
					}});
			}
		}else{
			sm.getFightingScene().launchSkill(curCard);
		}
	}
	
	/**
	 * 进化：回合开始时增加自身(x+1)点攻击力和x点HP
	 */
	public void evolve(final Card curCard) {
		int y = curCard.getAssistState() + 1;
		curCard.setHP(curCard.getHP() + y);
		curCard.setTop(curCard.getTop() + y);
		sm.getFightingScene().launchSkill(curCard);
	}

	/**
	 * 庇护：增加己方所有卡(x+3)点HP，上场时和<协助状态>数量改变时发动
	 */
	public void shelter(final Card curCard){
		if (!curCard.isSpecialSkillDone()){
			curCard.setSpecialSkill(true);
			getMyCards(curCard);
			index = otherCards.size() - 1;
			SkillEffect.getInstance().shelter(curCard);
		}else
			sm.getFightingScene().launchSkill(curCard);
	}
	
	public void continueShelter(final Card curCard){
		if (index >= 0){
			int change = curCard.getAssistState() + 3;
			Card theCard = otherCards.get(index);
			sm.getFightingScene().setHPText(curCard, theCard, theCard, change, new INextFunc(){
				@Override
				public void nextFunc(Card curCard, Card targetCard,
						INextFunc nextFunc2) {
					index--;
					continueShelter(curCard);
				}}, null);
		}else
			sm.getFightingScene().launchSkill(curCard);
	}
	
	/**
	 * 治疗：使己方HP最低的一张卡恢复(x+1)*2点HP
	 */
	public void treat(final Card curCard){
		int turn = curCard.getLocation().getRow();
		int minHP = Card.MAX_CARD_HP;
		Card minHPcard = null;
		Card temp = null;
		for (int i=0; i < gr.getNumCols(); i++){
			temp = gr.get(new Location(turn,i));
			if (temp != null && temp.getHP() < minHP){
				minHP = temp.getHP();
				minHPcard = temp;
			}
		}
		if (minHPcard != null){
			int change = (curCard.getAssistState() + 1) * 2;
			int dif = minHPcard.getMaxHP() - minHPcard.getHP();
			if (dif > 0){
				if (change > dif)
					change = dif;
			}else
				change = 0;
			SkillEffect.getInstance().treat(curCard, minHPcard, change, new INextFunc(){
				@Override
				public void nextFunc(Card curCard, Card targetCard,
						INextFunc nextFunc2) {
					sm.getFightingScene().launchSkill(curCard);
				}});
		}else
			sm.getFightingScene().launchSkill(curCard);
	}
	
	/**
	 * 治愈：使己方所有卡牌恢复(x+2)点HP
	 */
	public void heal(final Card curCard){
		getMyCards(curCard);
		index = otherCards.size() - 1;
		SkillEffect.getInstance().heal(curCard);
	}
	
	public void continueHeal(final Card curCard){
		if (index >= 0){
			Card theCard = otherCards.get(index);
			int change = (curCard.getAssistState() + 2);
			int dif = theCard.getMaxHP() - theCard.getHP();
			if (dif > 0){
				if (change > dif)
					change = dif;
			}else
				change = 0;
			SkillEffect.getInstance().treat(curCard, theCard, change, new INextFunc(){
				@Override
				public void nextFunc(Card curCard, Card targetCard,
						INextFunc nextFunc2) {
					index--;
					continueHeal(curCard);
				}});
		}else
			sm.getFightingScene().launchSkill(curCard);
	}
	
	/**
	 * 祛病：消除（1+x）张己方卡牌的（1+x）个异常状态
	 */
	public void cure(final Card curCard){
		getMyCards(curCard);
		int x = curCard.getAssistState();
		int i = otherCards.size();
		if (x + 1 < i)
			i = x;
		else
			i--;
		ArrayList<Card> list = new ArrayList<Card>();
		for (; i >= 0; i--){
			int r = (int) (getRandom(otherCards.size()));
			list.add(otherCards.get(r));
			otherCards.remove(r);
		}
		otherCards.clear();
		otherCards.addAll(list);
		index = otherCards.size() - 1;
		continurCure(curCard);
	}

	public void continurCure(final Card curCard){
		if (index >= 0){
			final Card theCard = otherCards.get(index);
			SkillEffect.getInstance().cure(curCard, theCard);;
		}else
			sm.getFightingScene().launchSkill(curCard);
	}
	
	public void continueCure2(final Card curCard, final Card theCard){
		int stateCount = curCard.getAssistState() + 1;
		for (int i = 0; i < stateCount; i++){
			if (theCard.mess){
				theCard.mess = false;
				theCard.detachChild(EState.MESS_TAG);
				continue;
			}if (theCard.freeze){
				theCard.freeze = false;
				theCard.detachChild(EState.FREEZE_TAG);
				continue;
			}if (theCard.numb){
				theCard.numb = false;
				theCard.detachChild(EState.NUMB_TAG);
				continue;
			}if (theCard.poison){
				theCard.poison = false;
				theCard.detachChild(EState.POISON_TAG);
				continue;
			}
			if (theCard.banTreat){
				theCard.banTreat = false;
				theCard.detachChild(EState.BANTREAT_TAG);
				continue;
			}
		}
		index--;
		continurCure(curCard);
	}
	
	/**
	 * 毒素：对敌方英雄造成10*(1+x)点伤害
	 */
	public void toxin(final Card curCard){
		int cut = 10 * (1 + curCard.getAssistState());
		SkillEffect.getInstance().toxin(sm.getFightingScene().getOtherPlayer(), curCard, -cut);
	}
	
	/**
	 * 召唤：在对方场上召唤1只僵尸，最多保持（x+1）只僵尸
	 */
	public void callZombie(final Card curCard){
		int x = curCard.getAssistState();
		otherCards.clear();
		Card otherCard;
		Location emptyLoc = null;//用来放僵尸的位置
		for (int i = gr.getNumCols() - 1; i >=0 ; i--){
			Location loc = new Location(Math.abs(curCard.getLocation().getRow()-1),i);
			otherCard = gr.get(loc);
			if (otherCard != null)
				otherCards.add(otherCard);
			else
				emptyLoc = loc;
		}
		int zombieNum = 0;
		for (Card theCard : otherCards){
			if (theCard.getName() == "Zombie")
				zombieNum++;
		}
		
		if (emptyLoc != null && zombieNum < x+1){
			SkillEffect.getInstance().callZombie(curCard, emptyLoc);
		}else
			sm.getFightingScene().launchSkill(curCard);
	}
	
	public void continueCallZombie(final Card curCard, final Location loc){
		Card tempCard = new Card(-1, "Zombie", 10, 0, 1, 0, 0, new int[]{0, 0, 0}, curCard.getTextureRegion()){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				if (pSceneTouchEvent.isActionDown()){
					FightingScene scene = SceneManager.getInstance().getFightingScene();
					if (this.isClicked()){
						SceneManager.getInstance().setScene(new CardInfoScene(this, scene));
						return true;
					}
				}
				return false;
			}};
		//将新卡放到场上
		tempCard.setClicked(true);
		tempCard.setAlpha(0.2f);//////////////
		tempCard.setWhere(Card.ON_GROUND);
		tempCard.putSelfInGrid(gr, loc);
		//判断显示位置并绘制卡片图形
		int turn = curCard.getLocation().getRow();
		int i = loc.getCol();
		if (turn == 1){
			tempCard.setPosition(10 + i * (10 + curCard.getWidth()),
					rm.camera.getHeight() - 30 - 2 * curCard.getHeight());
		}
		else
			tempCard.setPosition(10 + i * (10 + curCard.getWidth()), 30 + curCard.getHeight());
		sm.getFightingScene().registerTouchArea(tempCard);
		sm.getFightingScene().attachChild(tempCard);
		
		sm.getFightingScene().launchSkill(curCard);
	}

	/**
	 * 尸毒：使敌方所有卡牌进入中毒状态
	 */
	public void corpse(final Card curCard){
		getotherCards(curCard.getLocation());
		if (!otherCards.isEmpty()){
			index = otherCards.size() - 1;
			SkillEffect.getInstance().corpse(curCard);
		}else
			sm.getFightingScene().launchSkill(curCard);
	}
	
	public void continueCorpse(final Card curCard){
		if (index >=0){
			Card theCard = otherCards.get(index);
			if (!theCard.poison){
				judgeStateImmune(EState.poison, 1, curCard, theCard, theCard, new INextFunc(){
					@Override
					public void nextFunc(Card curCard, Card targetCard,
							INextFunc nextFunc2) {
						index--;
						continueCorpse(curCard);
					}});
			}else{
				index--;
				continueCorpse(curCard);
			}
		}else
			sm.getFightingScene().launchSkill(curCard);
	}
	
	/**
	 * 影像：随机获得敌方一张卡的一项技能，直到下个己方回合开始前
	 */
	public void imagination(final Card curCard){
		getotherCards(curCard.getLocation());
		if (!otherCards.isEmpty()){
			int i = (int) (getRandom(otherCards.size()));
			Card theCard = otherCards.get(i);
			SkillEffect.getInstance().imagination(curCard, theCard);
		}else
			sm.getFightingScene().launchSkill(curCard);
	}
	
	public void continueImagination(final Card curCard, final Card theCard){
		int i = (int) (getRandom(3));
		curCard.skill[3] = theCard.skill[i];
		sm.getFightingScene().launchSkill(curCard);
	}
	
	/**
	 * 复活：将墓地1张没有“复活”技能的卡牌召唤到场上
	 */
	public void revive(final Card curCard){
		int x = curCard.getAssistState();
		Player curPlayer = sm.getFightingScene().getCurrentPlayer();
		if (curPlayer.grave.isEmpty() || x <=0){
			sm.getFightingScene().launchSkill(curCard);
		}else
			SkillEffect.getInstance().revive(curCard, curPlayer);
	}
	
	public void continueRevive(final Card curCard, final Player curPlayer){
		int turn = curCard.getLocation().getRow();
		for (Card theCard : curPlayer.grave){
			if (!theCard.hasSkill(46) && theCard.getName() != "Zombie"){
				for (int col = gr.getNumCols() - 1; col >= 0 ; col--){
					Location loc = new Location(turn, col);
					Card card = gr.get(loc);
					if (card == null){
						theCard.putSelfInGrid(gr, loc);
						theCard.setWhere(Card.ON_GROUND);
						theCard.setClicked(true);
						curPlayer.grave.remove(theCard);
						if (turn == 0){
							theCard.setPosition(10 + col * (10 + curCard.getWidth()),
									rm.camera.getHeight() - 30 - 2 * curCard.getHeight());
						}else
							theCard.setPosition(10 + col * (10 + curCard.getWidth()), 30 + curCard.getHeight());
						sm.getFightingScene().registerTouchArea(theCard);
						sm.getFightingScene().attachChild(theCard);
						break;
					}
				}
			}
		}
		sm.getFightingScene().launchSkill(curCard);
	}
	
	/**
	 * 免疫
	 */
	public void immune(final Card curCard){
		curCard.magic_immune = true;
		curCard.state_immune = true;
		SkillEffect.getInstance().immune(curCard, curCard);
	}
	
	/**
	 * 	预防
	 */
	public void prevent(final Card curCard){
		getMyCards(curCard);
		if (!otherCards.isEmpty()){
			int i = (int) (getRandom(otherCards.size()));
			Card theCard = otherCards.get(i);
			theCard.magic_immune = true;
			theCard.state_immune = true;
			SkillEffect.getInstance().prevent(curCard, theCard);
		}else
			sm.getFightingScene().launchSkill(curCard);
	}
	
	/**
	 * 戒律：使（1+x）张己方卡牌增加自身的(30 + 10 * x）%的攻击力，直到下个己方回合开始前
	 */
	public void commandment(final Card curCard){
		getMyCards(curCard);
		int x = curCard.getAssistState();
		int i = otherCards.size();
		if (x + 1 < i)
			i = x;
		else
			i--;
		ArrayList<Card> list = new ArrayList<Card>();
		for (; i >= 0; i--){
			int r = (int) (getRandom(otherCards.size()));
			list.add(otherCards.get(r));
			otherCards.remove(r);
		}
		otherCards.clear();
		otherCards.addAll(list);
		index = otherCards.size() - 1;
		SkillEffect.getInstance().commandment(curCard);
	}
	
	public void continueCommandment(final Card curCard){
		if (index >= 0){
			Card theCard = otherCards.get(index);
			int change = theCard.tempTopInc;
			theCard.tempTopInc += curCard.getTop() * (30 + 10 * curCard.getAssistState()) / 100;
			change = theCard.tempTopInc - change;
			theCard.setTop(theCard.getTop() + change);
			index--;
			continueCommandment(curCard);
		}else
			sm.getFightingScene().launchSkill(curCard);
	}
	
	/**
	 * 53群众的力量：己方场上每有一张卡牌增加自身1点攻击力，己方墓地每有一张卡牌增加自身(x+1)点HP
	 */
	public void massesPower(final Card curCard){
		getMyCards(curCard);
		SkillEffect.getInstance().massesPower(curCard);
	}
	
	public void continueMassesPower(final Card curCard){
		int x = curCard.getAssistState();
		//增加攻击力
		int change = curCard.tempTopInc;
		curCard.tempTopInc += otherCards.size();
		change = curCard.tempTopInc - change;
		curCard.setTop(curCard.getTop() + change);
		//增加HP
		Player curPlayer = sm.getFightingScene().getCurrentPlayer();
		int zombieNum = 0;
		for (Card theCard : curPlayer.grave){
			if (theCard.getName() == "Zombie")
				zombieNum++;
		}
		change = curCard.tempHpInc;
		curCard.tempHpInc += (curPlayer.grave.size() - zombieNum) * (x + 1);
		change = curCard.tempHpInc - change;
		curCard.setHP(curCard.getHP() + change);
		
		sm.getFightingScene().launchSkill(curCard);
	}
	
	/**
	 * 56放能：行动开始前减少自身(x+1)点HP，增加自身(x+1)点攻击力
	 */
	public void releaseEnergy(final Card curCard){
		final int x = curCard.getAssistState();
		sm.getFightingScene().setHPText(curCard, null, curCard, -x-1, new INextFunc(){
			@Override
			public void nextFunc(Card curCard, Card targetCard,
					INextFunc nextFunc2) {
				sm.getFightingScene().afterSimplyCutCardHP(curCard, targetCard, curCard, nextFunc2, null);
			}}, new INextFunc(){
				@Override
				public void nextFunc(Card curCard, Card targetCard,
						INextFunc nextFunc2) {
					if (curCard != null){
						curCard.setTop(curCard.getTop() + x + 1);
					}
					sm.getFightingScene().launchSkill(curCard);
				}});
	}
	
	/**
	 * 58刺杀
	 */
	public void assassinate(final Card curCard){
		curCard.setSuperSkill(true);
		getotherCards(curCard.getLocation());
//		int r;
		Card theCard;
		ArrayList<Location> locs = sm.getFightingScene().getOtherPlayer().getHandGrid().getOccupiedLocations();
		if (!otherCards.isEmpty()){//如果场上有卡
//			r = (int) (Math.random() * otherCards.size());
			theCard = otherCards.get(0);
			SkillEffect.getInstance().assassinate(curCard, theCard, new INextFunc(){
				@Override
				public void nextFunc(Card curCard, Card targetCard,
						INextFunc nextFunc2) {
					if (!targetCard.hasSkill(14)){
						targetCard.reInit();
						targetCard.removeSelfFromGrid();
						sm.getFightingScene().getOtherPlayer().grave.add(targetCard);
						sm.getFightingScene().unregisterTouchArea(targetCard);
						targetCard.detachSelf();
						sm.getFightingScene().continueCalculate();
					}else{
						//TODO 显示金属效果
						sm.getFightingScene().continueCalculate();
					}
				}});
		}else if (!locs.isEmpty()){//如果手牌有卡
//			r = (int) (Math.random() * locs.size());
			theCard = sm.getFightingScene().getOtherPlayer().getHandGrid().get(locs.get(0));
			SkillEffect.getInstance().assassinate(curCard, theCard, new INextFunc(){
				@Override
				public void nextFunc(Card curCard, Card targetCard,
						INextFunc nextFunc2) {
					if (!targetCard.hasSkill(14)){
						targetCard.reInit();
						targetCard.removeSelfFromGrid();
						sm.getFightingScene().getOtherPlayer().grave.add(new Card(targetCard){
							@Override
							public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
								if (pSceneTouchEvent.isActionDown()){
									FightingScene scene = SceneManager.getInstance().getFightingScene();
									if (this.isClicked()){
										SceneManager.getInstance().setScene(new CardInfoScene(this, scene));
										return true;
									}
								}
								return false;
							}});
						sm.getFightingScene().unregisterTouchArea(targetCard);
						targetCard.detachSelf();
						targetCard.detachChildren();
						targetCard.dispose();
						sm.getFightingScene().continueCalculate();
					}else{
						//TODO 显示金属效果
						sm.getFightingScene().continueCalculate();
					}
				}});
		}else//对方场上和手牌都没有卡
			sm.getFightingScene().continueCalculate();
	}
	
	/**
	 * 潜行：免疫魔法伤害
	 */
	public void lurk(final Card curCard){
		curCard.magic_immune = true;
		SkillEffect.getInstance().lurk(curCard);
	}
	
	/**
	 * 防污：免疫异常状态
	 */
	public void antiPollute(final Card curCard){
		curCard.state_immune = true;
		SkillEffect.getInstance().antiPollute(curCard);
	}
}
