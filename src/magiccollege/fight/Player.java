package magiccollege.fight;

import info.gridworld.grid.BoundedGrid;
import info.gridworld.grid.Grid;

import java.util.ArrayList;

import magiccollege.manager.ResourceManager;

import org.andengine.entity.text.Text;


public class Player {
	private int[] cardGroup;
	private int[][] cardGroups;
	public ArrayList<Card>mCardGroup;
	public ArrayList<Card> grave, hand, desktop;
	public ArrayList<Card> myCards;
	private Grid<Card> handGrid;
	private int usedGroup;
	private int HP;
	private int maxHP;
	public Text HPtext;
	public ArrayList<String> dialogs;
	
	public Player(ArrayList<Card> myC, int[][] cG, int uG, int pHP, ArrayList<String> pDialogs){
		myCards = myC;
		cardGroups = cG;
		cardGroup = cG[uG];
		usedGroup = uG;
		mCardGroup = new ArrayList<Card>();
		grave = new ArrayList<Card>();
		hand = new ArrayList<Card>();
		handGrid = new BoundedGrid<Card>(1, 5);
		maxHP = HP = pHP;
		HPtext = new Text(0, 0, ResourceManager.getInstance().font, "HP:9999", 8, ResourceManager.getInstance().vbo);
		dialogs = pDialogs;
	}
	
	/**
	 * 调用此函数之前要确保mCardGroup非null并且里面的Card有图片
	 */
	public void setDesktop(){
		desktop = new ArrayList<Card>(mCardGroup);
		grave.clear();
		hand.clear();
		handGrid.removeAll();
	}
	
	public int getHP(){
		return HP;
	}
	
	public void setHP(int hp){
		HP = hp;
		HPtext.setText("HP:"+hp);
	}
	
	public int getMaxHP(){
		return maxHP;
	}
	
	public void setMaxHP(int hp){
		maxHP = hp;
	}
	
	public int getUsedGroup(){
		return usedGroup;
	}
	
	public void setUsedGroup(int uG){
		usedGroup = uG % 4;
	}
	
	public Grid<Card> getHandGrid(){
		return handGrid;
	}
	
	public void setCardGroup(int[] pCardGroup){
		cardGroups[usedGroup] = pCardGroup;
		cardGroup = pCardGroup;
	}

	public int[] getCardGroup(){
		return cardGroup;
	}
}

