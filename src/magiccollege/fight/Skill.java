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
	
	/**�õ��Է����Ͽ��� */
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
	
	/**�õ��Է����Ͽ��� */
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
	 * �õ��ҷ����Ͽ���
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
			str = "���";
			break;
		case 2:
			str = "����";
			break;
		case 3:
			str = "����";
			break;
		case 4:
			str = "�Ի�";
			break;
		case 5:
			str = "����";
			break;
		case 6:
			str = "����";
			break;
		case 7:
			str = "ħ��";
			break;
		case 8:
			str = "����";
			break;
		case 9:
			str = "�ش�";
			break;
		case 10:
			str = "��ը";
			break;
		case 11:
			str = "����";
			break;
		case 12:
			str = "����";
			break;
		case 13:
			str = "��Ⱦ";
			break;
		case 14:
			str = "����";
			break;
		case 15:
			str = "�ͻ�";
			break;
		case 16:
			str = "����";
			break;
		case 17:
			str = "���";
			break;
		case 18:
			str = "����";
			break;
		case 19:
			str = "����";
			break;
		case 20:
			str = "��ѹ";
			break;
		case 21:
			str = "ǿ׳";
			break;
		
		case 26:
			str = "����籩";
			break;
		
		case 28:
			str = "����";
			break;
		
		
		case 33:
			str = "�ӻ�";
			break;
		
		case 35:
			str = "����";
			break;
		case 36:
			str = "����";
			break;
		case 37:
			str = "�";
			break;
		case 38:
			str = "����";
			break;
		case 39:
			str = "�ٻ�";
			break;
		case 40:
			str = "ʬ��";
			break;
		
		
		case 43:
			str = "Ӱ��";
			break;
		
		case 46:
			str = "����";
			break;
		case 47:
			str = "����";
			break;
		case 48:
			str = "Ԥ��";
			break;
		
		
		case 51:
			str = "����ǽ";
			break;
		case 52:
			str = "����";
			break;
		case 53:
			str = "Ⱥ�ڵ�����";
			break;
		
		case 56:
			str = "����";
			break;
		
		case 58:
			str = "��ɱ";
			break;
		case 59:
			str = "Ǳ��";
			break;
		
		case 61:
			str = "����";
			break;
		
		case 81:
			str = "�ָ�";
			break;
		case 82:
			str = "����";
			break;
			
		case 91:
			str = "����";
			break;
		case 92:
			str = "���";
			break;
		case 93:
			str = "Ť��";
			break;
		case 94:
			str = "����";
			break;
		case 95:
			str = "����";
			break;
		case 96:
			str = "��Ѫ";
			break;
		case 97:
			str = "˺��";
			break;
		case 98:
			str = "ע��";
			break;
		case 99:
			str = "��ɨ";
			break;
			
		case 101:
			str = "����";
			break;
		case 102:
			str = "Ȩ��";
			break;
		case 103:
			str = "��ѧ";
			break;
		case 104:
			str = "ʵ��";
			break;
		case 105:
			str = "����";
			break;
		case 106:
			str = "����";
			break;
		case 107:
			str = "����";
			break;
		case 108:
			str = "�ټ�";
			break;
			
		case 201:
			str = "����";
			break;
		case 202:
			str = "��ı";
			break;
		case 203:
			str = "�ƻ�";
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
			str =  "�Եз�HP���ٵģ�1+x���ſ�\n���2��1+x���������˺�";
			break;
		case 2:
			str = "�Եз�һ�ſ����x��ħ���˺���\n����0.3��1+x)����ʹ����붳��״̬";
			break;
		case 3:
			str = "�����ͨ�����˺�ʱ\n�Եз�Ӣ����ɵ����˺�";
			break;
		case 4:
			str = "0.3��1+x������ʹ�з�\nһ�ſ��������״̬";
			break;
		case 5:
			str = "�Եз����п����x��ħ���˺���\n����0.15��1+x)����ʹ����붳��״̬";
			break;
		case 6:
			str = "�ָ�����Ӣ�ۣ�20+10x����Ѫ��";
			break;
		case 7:
			str = "���䣨1+x����ħ���˺�";
			break;
		case 8:
			str = "��0.3+x/4��������ס�з�\n2�ſ���ʹ����붳��״̬";
			break;
		case 9:
			str = "���ӵз��������ܣ�\nɱ���з�����ʱ��������Ч��";
			break;
		case 10:
			str = "����ʱ�Եз����п���\n���x�������˺�";
			break;
		case 11:
			str = "��0.4+0.2x������\n������ͨ����";
			break;
		case 12:
			str = "��������һ�ſ���ʹ��\n�����������ſ��������״̬";
			break;
		case 13:
			str = "�ܵ���ͨ����ʱ��0.3��1+x������\nʹ�����߼����������ſ������ж�״̬";
			break;
		case 14:
			str = "����ħ���˺����쳣״̬";
			break;
		case 15:
			str = "��������һ�ſ���\n���䷢�����������";
			break;
		case 16:
			str = "�Լ����������п��Ʒ�����������,\n�ϳ�ʱ��<Э��״̬>�����ı�ʱ����";
			break;
		case 17:
			str = "�Եз�2�ſ����x��ħ���˺���\n����0.3��1+x)����ʹ��������״̬";
			break;
		case 18:
			str = "�Եз���1+x���ſ���ɣ�1+x����\nħ���˺���ʹ����ָ�����HP";
			break;
		case 19:
			str = "������� x ����ͨ����";
			break;
		case 20:
			str = "ʹ�з����п��Ƽ���\n��1+x���㹥����";
			break;
		case 21:
			str = "�ܵ���ͨ����ʱ\n����ܵ�3-x���˺�";
			break;
		
			
		case 101:
		case 102:
		case 103:
		case 104:
		case 105:
		case 106:
		case 107:
		case 108:
			str = "�����һ�ż������Ʒ�����������";
			break;
			
		case 201:
		case 202:
		case 203:
			str = "�����һ�ŶԷ����Ʒ������������";
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
	 * ������Եз�HP���ٵģ�1+x���ſ����2��1+x���������˺�
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
	 * �������Եз�һ�ſ����2*x+1��ħ���˺�������0.3��1+x)����ʹ����붳��״̬
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
					sm.getFightingScene().launchSkill(curCard);//������֮�󷢶���һ�����ܣ���ʱcurCard�����Ѿ�Ϊnull��
				}});//��ʾ����Ч��
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
	 * ���ƣ������ͨ�����˺�ʱ�Եз�Ӣ����ɵ����˺�
	 * */
	public void seeThrough(){
		
	}
	
	/**
	 * �Ի꣺0.3��1+x������ʹ�з�һ�ſ��������״̬
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
	 * ���꣺�Եз����п����x+1��ħ���˺�������0.15��1+x)����ʹ����붳��״̬
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
						continueIceRain(curCard, cut, probability);//curCard����ΪNull
					}});//��ʾ����Ч��
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
	 * ���ࣺ�ָ�����Ӣ�ۣ�20+10x����Ѫ��
	 * */
	public void perform(Card curCard){
		SkillEffect.getInstance().perform(curCard);
	}
	
	/**
	 * ħ�ܣ����䣨1+x����ħ���˺�
	 * */
	public void magicShield(Card curCard){
		int x = curCard.getAssistState();
		curCard.reflection = 1 + x;
		SkillEffect.getInstance().magicShield(curCard);
	}
	
	/**
	 * ���ѣ���0.3+x/4��������ס�з�2�ſ���ʹ����붳��״̬
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
	 * �ش̣����ӵз��������ܣ�ɱ���з�����ʱ��������Ч��
	 * */
	public void earthStab(){
		
	}
	
	/**
	 * ��ը������ʱ�Եз����п������x+3�������˺�
	 * @param targetCard ��ǰ���������Ŀ�
	 * @param curCard ��ʹtargetCard�����Ŀ�
	 * */
	public void explode(Card curCard, Card targetCard, final INextFunc nextFunc){
		getOtherCards2(targetCard.getLocation());
		index2 = otherCards2.size() - 1;
		continueExplode(curCard, targetCard, nextFunc);
	}
	
	public void continueExplode(Card curCard, Card targetCard, INextFunc nextFunc){
		if (index2 >= 0){
			int cut = targetCard.getAssistState() + 3;
			final Card theCard = otherCards2.get(index2);//�����ܵ��˺��Ŀ�
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
	 * ���飺��0.4+0.2x������������ͨ����
	 */
	public void legerity(){
		
	}
	
	
	/**
	 *	����������ħ���˺����쳣״̬
	 * */
	public void metal(final Card curCard){
		curCard.magic_immune = true;
		curCard.state_immune = true;
		SkillEffect.getInstance().immune(curCard, curCard);
	}
	
	/**
	 * ����
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
	 * �����
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
	 * ���̣��Լ����������п��Ʒ��������������ϳ�ʱ��<Э��״̬>�����ı�ʱ����
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
	 * ������Եз�2�ſ����(x + 1)��ħ���˺�������0.3��1+x)����ʹ��������״̬
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
	 * ���ף��Եз���1+x���ſ����1+x��ħ���˺���ʹ����ָ�����HP
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
	 * �������������x����ͨ����
	 * */
	public void roar(){
		
	}
	
	/**
	 * ��ѹ��ʹ�з����п��Ƽ���1+x�㹥����
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
	 * ǿ׳���ܵ���ͨ����ʱ����ܵ�3-x���˺�
	 * */
	public void strong(){
	
	}
	
	/**
	 * ��ĳ�ſ�������������
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
	 * ��ĳ�ſ��������������
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
	 * ���䣺��������һ�ſ���ʹ�估���������ſ��������״̬
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
	 * ��Ⱦ���ܵ���ͨ����ʱ��0.3��1+x������ʹ�����߼����������ſ������ж�״̬
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
	 * �ͻ�����������һ�ſ�����䷢�����������
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
	 * ����籩���Եз����п�����ɣ�x + 3����ħ���˺�
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
						continueFlameStrike(curCard, cut);//curCard����ΪNull
					}});//��ʾ����Ч��
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
	 * �������غϿ�ʼʱ��������(x+1)�㹥������x��HP
	 */
	public void evolve(final Card curCard) {
		int y = curCard.getAssistState() + 1;
		curCard.setHP(curCard.getHP() + y);
		curCard.setTop(curCard.getTop() + y);
		sm.getFightingScene().launchSkill(curCard);
	}

	/**
	 * �ӻ������Ӽ������п�(x+3)��HP���ϳ�ʱ��<Э��״̬>�����ı�ʱ����
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
	 * ���ƣ�ʹ����HP��͵�һ�ſ��ָ�(x+1)*2��HP
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
	 * ������ʹ�������п��ƻָ�(x+2)��HP
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
	 * ���������1+x���ż������Ƶģ�1+x�����쳣״̬
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
	 * ���أ��Եз�Ӣ�����10*(1+x)���˺�
	 */
	public void toxin(final Card curCard){
		int cut = 10 * (1 + curCard.getAssistState());
		SkillEffect.getInstance().toxin(sm.getFightingScene().getOtherPlayer(), curCard, -cut);
	}
	
	/**
	 * �ٻ����ڶԷ������ٻ�1ֻ��ʬ����ౣ�֣�x+1��ֻ��ʬ
	 */
	public void callZombie(final Card curCard){
		int x = curCard.getAssistState();
		otherCards.clear();
		Card otherCard;
		Location emptyLoc = null;//�����Ž�ʬ��λ��
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
		//���¿��ŵ�����
		tempCard.setClicked(true);
		tempCard.setAlpha(0.2f);//////////////
		tempCard.setWhere(Card.ON_GROUND);
		tempCard.putSelfInGrid(gr, loc);
		//�ж���ʾλ�ò����ƿ�Ƭͼ��
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
	 * ʬ����ʹ�з����п��ƽ����ж�״̬
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
	 * Ӱ�������õз�һ�ſ���һ��ܣ�ֱ���¸������غϿ�ʼǰ
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
	 * �����Ĺ��1��û�С�������ܵĿ����ٻ�������
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
	 * ����
	 */
	public void immune(final Card curCard){
		curCard.magic_immune = true;
		curCard.state_immune = true;
		SkillEffect.getInstance().immune(curCard, curCard);
	}
	
	/**
	 * 	Ԥ��
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
	 * ���ɣ�ʹ��1+x���ż����������������(30 + 10 * x��%�Ĺ�������ֱ���¸������غϿ�ʼǰ
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
	 * 53Ⱥ�ڵ���������������ÿ��һ�ſ�����������1�㹥����������Ĺ��ÿ��һ�ſ�����������(x+1)��HP
	 */
	public void massesPower(final Card curCard){
		getMyCards(curCard);
		SkillEffect.getInstance().massesPower(curCard);
	}
	
	public void continueMassesPower(final Card curCard){
		int x = curCard.getAssistState();
		//���ӹ�����
		int change = curCard.tempTopInc;
		curCard.tempTopInc += otherCards.size();
		change = curCard.tempTopInc - change;
		curCard.setTop(curCard.getTop() + change);
		//����HP
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
	 * 56���ܣ��ж���ʼǰ��������(x+1)��HP����������(x+1)�㹥����
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
	 * 58��ɱ
	 */
	public void assassinate(final Card curCard){
		curCard.setSuperSkill(true);
		getotherCards(curCard.getLocation());
//		int r;
		Card theCard;
		ArrayList<Location> locs = sm.getFightingScene().getOtherPlayer().getHandGrid().getOccupiedLocations();
		if (!otherCards.isEmpty()){//��������п�
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
						//TODO ��ʾ����Ч��
						sm.getFightingScene().continueCalculate();
					}
				}});
		}else if (!locs.isEmpty()){//��������п�
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
						//TODO ��ʾ����Ч��
						sm.getFightingScene().continueCalculate();
					}
				}});
		}else//�Է����Ϻ����ƶ�û�п�
			sm.getFightingScene().continueCalculate();
	}
	
	/**
	 * Ǳ�У�����ħ���˺�
	 */
	public void lurk(final Card curCard){
		curCard.magic_immune = true;
		SkillEffect.getInstance().lurk(curCard);
	}
	
	/**
	 * ���ۣ������쳣״̬
	 */
	public void antiPollute(final Card curCard){
		curCard.state_immune = true;
		SkillEffect.getInstance().antiPollute(curCard);
	}
}
