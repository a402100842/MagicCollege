package magiccollege.fight;

import magiccollege.Enum.EState;
import magiccollege.manager.ResourceManager;

import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.color.Color;

import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

public class Card extends Sprite{
	public static final int MAX_CARD_HP = 9999;
	public final int key;
	private final String name;//����
	private int where;//��Ĺ�ء����顢���ơ�����
	public static final int ON_DESKTOP = 0;
	public static final int ON_GRAVE = 1;
	public static final int ON_HAND = 2;
	public static final int ON_GROUND = 3;
	private int HP,maxHP;
	private final int power;
	private int top, left, right;
	private int defaultTop, defaultLeft, defaultRight;
	public int tempTopInc;//��ʱ����������
	public int tempHpInc;
	//��������ֵ
	public int[] skill;
	//״̬
	private int assistState;//Э��״̬
	public boolean banTreat;//����״̬
	public boolean freeze;//����״̬
	public boolean mess;//����״̬
	public boolean numb;//���״̬
	public boolean poison;//�ж�״̬
	
	public boolean magic_immune;//ħ������
	public boolean state_immune;//�����쳣״̬
	public int reflection;//ħ����״̬����
	
	private boolean isSuperSkillDone;//��������ֻ����һ��
	private boolean isSpecialSkillDone;//ĳЩ�޶���ֻ�������ɴ�
	private boolean isClicked;//������ʾ��ϸ��Ϣ
	public int attackcount;//��¼ĳЩ���ܵķ������Σ��硰��ɨ��
	public int attackcount2;//�������ơ�������
	
	public Text topText;
	public Text leftText;
	public Text rightText;
	public Text HPText;

	private Grid<Card> grid;
    private Location location;
	
	public Card(final int pKey, String pName, int pHP, int pPower, int pTop, int pLeft, int pRight, int[] pSkill,
			final float pX, final float pY, final float pWidth, final float pHeight, final ITextureRegion pTextureRegion, final VertexBufferObjectManager pVertexBufferObjectManager){
		super(pX, pY, pWidth, pHeight, pTextureRegion, pVertexBufferObjectManager);
		key = pKey;
		name = pName;
		where = Card.ON_DESKTOP;
		maxHP = HP = pHP;
		power = pPower;
		defaultTop = top = pTop;
		defaultLeft = left = pLeft;
		defaultRight = right = pRight;
		skill = pSkill;
		assistState = 0;//Э��״̬
		freeze = false;//����״̬
		mess = false;//����״̬
		numb = false;//���״̬
		banTreat = false;//����״̬
		poison = false;//�ж�״̬
		isSuperSkillDone = false;
		isSpecialSkillDone = false;
		isClicked = false;
		grid = null;
		location = null;
		tempTopInc = tempHpInc = 0;
		
		ResourceManager rm = ResourceManager.getInstance();
		topText = new Text((pWidth - 10)/2, 0,rm.cardFont,""+top,2,rm.vbo);
		leftText = new Text(0, (pHeight -10)/2,rm.cardFont,""+left,2,rm.vbo);
		rightText = new Text(pWidth - 20, (pHeight -10)/2,rm.cardFont,""+right,2,rm.vbo);
		HPText = new Text((pWidth - 10)/2, pHeight - 20,rm.cardFont,""+maxHP,2,rm.vbo);
		topText.setColor(Color.GREEN);
		leftText.setColor(Color.GREEN);
		rightText.setColor(Color.GREEN);
		HPText.setColor(Color.GREEN);
		this.attachChild(topText);
		this.attachChild(leftText);
		this.attachChild(rightText);
		this.attachChild(HPText);
		avantInit();
	}
	
	public Card(final int pKey, String pName, int pHP, int pPower, int pTop, int pLeft, int pRight, int[] pSkill, final ITextureRegion pTextureRegion){
		this(pKey, pName, pHP, pPower, pTop, pLeft, pRight, pSkill, 0, 0, 
				ResourceManager.getInstance().camera.getWidth() / 7, ResourceManager.getInstance().camera.getHeight() / 5, pTextureRegion, ResourceManager.getInstance().vbo);
		}
	
	
	public Card(Card pCard, final ITextureRegion pTextureRegion){
		this(pCard.key, pCard.getName(),pCard.getHP(),pCard.getPower(),pCard.getTop(), pCard.getLeft(), pCard.getRight(), pCard.getSkill(), 
				pCard.getX(), pCard.getY(), pCard.getWidth(), pCard.getHeight(), pTextureRegion, pCard.getVertexBufferObjectManager());
	}
	
	public Card(Card pCard){
		this(pCard,pCard.getTextureRegion());
	}

	/**
	 * �غϽ���ǰ��Ƭ״̬�ĵ�������
	 */
	public void init(){
		freeze = false;//����״̬
		detachChild(EState.FREEZE_TAG);
		mess = false;//����״̬
		detachChild(EState.MESS_TAG);
		numb = false;//���״̬
		detachChild(EState.NUMB_TAG);
		
		setTop(top - tempTopInc);
		setHP(HP - tempHpInc);
		tempTopInc = tempHpInc = 0;
		
	}
	
	public void avantInit(){
		attackcount = 0;
		magic_immune = false;//ħ������
		state_immune = false;//�����쳣״̬
		reflection = 0;
	}
	
	/**
	 * �����ͻ��ƶ�ʱ���ߴ�����Ĺ��ʱ����
	 */
	public void reInit(){
		assistState = 0;//Э��״̬
		freeze = false;//����״̬
		detachChild(EState.FREEZE_TAG);
		mess = false;//����״̬
		detachChild(EState.MESS_TAG);
		numb = false;//���״̬
		detachChild(EState.NUMB_TAG);
		magic_immune = false;//ħ������
		state_immune = false;//�����쳣״̬
		reflection = 0;
		banTreat = false;//����״̬
		detachChild(EState.BANTREAT_TAG);
		poison = false;//�ж�״̬
		detachChild(EState.POISON_TAG);
		isSuperSkillDone = false;
		isSpecialSkillDone = false;
		isClicked = false;
		
		attackcount = 0;
		attackcount2 = 0;
		tempTopInc = tempHpInc = 0;
		
		setHP(maxHP);
		setLeft(defaultLeft);
		setTop(defaultTop);
		setRight(defaultRight);
	}
	
	public boolean hasSkill(int key){
		for (int i = 0; i < skill.length; i++){
			if (skill[i] == key)
				return true;
		}
		return false;
	}
	
	public String getName(){
		return name;
	}
	
	public int getWhere(){
		return where;
	}
	
	public int getHP(){
		return HP;
	}
	
	public int getMaxHP(){
		return maxHP;
	}
	
	public int getPower(){
		return power;
	}
	
	public int getTop(){
		return top;
	}
	
	public int getDefaultTop(){
		return defaultTop;
	}
	
	public int getLeft(){
		return left;
	}
	
	public int getDefaultLeft(){
		return defaultLeft;
	}
	
	public int getRight(){
		return right;
	}
	
	public int getDefaultRight(){
		return defaultRight;
	}
	
	public int[] getSkill() {
		return skill;
	}
	
	public Grid<Card> getGrid(){
		return grid;
	}
	
	public Location getLocation(){
		return location;
	}
	
	public void setTop(int t){
		if (t < 0)
			top = 0;
		else
			top = t;
		topText.setText(""+top);
	}
	
	public void setLeft(int l){
		left = l;
		leftText.setText(""+left);
	}
	
	public void setRight(int r){
		right = r;
		rightText.setText(""+right);
	}
	
	public void setHP(int hp){
		HP = hp;
		HPText.setText(""+HP);
	}
	
	public void setWhere(int pPosition){
		where = pPosition;
	}
	
	public void setSkill(int[] pSkill){
		skill = pSkill;
	}
	
	public void setSuperSkill(boolean pIsSuperSkillDone){
		isSuperSkillDone = pIsSuperSkillDone;
	}
	
	public boolean isSuperSkillDone(){
		return isSuperSkillDone;
	}
	
	public void setSpecialSkill(boolean pIsSpecialSkillDone){
		isSpecialSkillDone = pIsSpecialSkillDone;
	}
	
	public boolean isSpecialSkillDone(){
		return isSpecialSkillDone;
	}
	
	public int getAssistState(){
		return assistState;
	}
	
	public void setAssistState(int ass){
		assistState = ass;
	}
	
	/*public void setClicked(){
		if (isClicked)
			isClicked = false;
		else
			isClicked = true;
	}*/
	
	public void setClicked(boolean b){
		isClicked = b;
	}
	
	public boolean isClicked(){
		return isClicked;
	}
	
	 /**
     * Moves this card to a new location. If there is another card at the
     * given location, do nothing. <br />
     * Precondition: (1) This card is contained in a grid (2)
     * <code>newLocation</code> is valid in the grid of this card
     * @param newLocation the new location
     */
    public void moveTo(Location newLocation)
    {
        if (grid == null)
            throw new IllegalStateException("This card is not in a grid.");
        if (grid.get(location) != this)
            throw new IllegalStateException(
                    "The grid contains a different card at location "
                            + location + ".");
        if (!grid.isValid(newLocation))
            throw new IllegalArgumentException("Location " + newLocation
                    + " is not valid.");

        Card other = grid.get(newLocation);
        if (newLocation.equals(location) || other != null)
            return;
        grid.remove(location);
        
        location = newLocation;
        grid.put(location, this);
    }
    
	
	 /**
    * Puts this card into a grid. If there is another card at the given
    * location, do nothing. <br />
    * Precondition: (1)<code>loc</code> is valid in <code>gr</code>
    * @param gr the grid into which this actor should be placed
    * @param loc the location into which the actor should be placed
    */
	public void putSelfInGrid(Grid<Card> gr, Location loc)
   {
       Card card = gr.get(loc);
       if (card != null)
    	   throw new IllegalStateException(
                   "The grid already contains a different card at location "
                           + loc + ".");
       gr.put(loc, this);
       grid = gr;
       location = loc;
   }
	
	/**
    * Removes this card from its grid. <br />
    * Precondition: This card is contained in a grid
    */
   public void removeSelfFromGrid()
   {
       if (grid == null)
           throw new IllegalStateException(
                   "This card is not contained in a grid.");
       if (grid.get(location) != this)
           throw new IllegalStateException(
                   "The grid contains a different card at location "
                           + location + ".");

       grid.remove(location);
       grid = null;
       location = null;
   }
}
