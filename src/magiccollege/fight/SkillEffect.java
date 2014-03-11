package magiccollege.fight;

import info.gridworld.grid.Location;
import magiccollege.manager.ResourceManager;
import magiccollege.manager.SceneManager;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.RotationModifier;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.particle.SpriteParticleSystem;
import org.andengine.entity.particle.emitter.PointParticleEmitter;
import org.andengine.entity.particle.emitter.RectangleParticleEmitter;
import org.andengine.entity.particle.initializer.AccelerationParticleInitializer;
import org.andengine.entity.particle.initializer.AlphaParticleInitializer;
import org.andengine.entity.particle.initializer.BlendFunctionParticleInitializer;
import org.andengine.entity.particle.initializer.ColorParticleInitializer;
import org.andengine.entity.particle.initializer.RotationParticleInitializer;
import org.andengine.entity.particle.initializer.VelocityParticleInitializer;
import org.andengine.entity.particle.modifier.AlphaParticleModifier;
import org.andengine.entity.particle.modifier.ColorParticleModifier;
import org.andengine.entity.particle.modifier.ExpireParticleInitializer;
import org.andengine.entity.particle.modifier.ScaleParticleModifier;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.util.modifier.IModifier;

import android.opengl.GLES20;

public class SkillEffect {
	private static final SkillEffect INSTANCE = new SkillEffect();
	
	private ResourceManager rm;
	private SceneManager sm;
	private BitmapTextureAtlas particleTextureAtlas;
	private Skill skill;
	
	private ITextureRegion mParticleRegion;
	private ITextureRegion magicShieldRegion;
	private ITextureRegion iceArrowRegion;
	private ITextureRegion chargeRegion;
	private ITextureRegion restoreRegion;
	private ITextureRegion roarRegion;
	private ITextureRegion performRegion;
	private ITextureRegion earthCrackRegion;
	private ITextureRegion radiateRegion;
	private ITextureRegion polluteRegion;
	private ITextureRegion metalRegion;

	private TextureRegion preventRegion;

	private TextureRegion cureRegion;

	private TextureRegion lurkRegion;

//	private TextureRegion punchRegion;

	private TextureRegion singRegion;

	private TextureRegion tradeRegion;

	private TextureRegion coercionRegion;

	private TextureRegion strongRegion;

	private TextureRegion flameStrikeRegion;

	private TextureRegion shelterRegion;

	private TextureRegion healRegion;

	private TextureRegion evolveRegion;

	private TextureRegion callZombieRegion;

	private TextureRegion imaginationRegion;

	private TextureRegion electricShockRegion;

	private TextureRegion reviveRegion;

	private TextureRegion massesPowerRegion;

	private TextureRegion commandmentRegion;

	private TextureRegion releaseEnergyRegion;

	private TextureRegion assassinateRegion;
	
	/**
	 * 调整
	 */
	public void adjust(final Card curCard, final Card theCard){
		if (!rm.showSkillEffect){
			skill.adjust(curCard, null, theCard, new INextFunc(){
				@Override
				public void nextFunc(Card curCard, Card targetCard,
						INextFunc nextFunc2) {
					sm.getFightingScene().launchSkill(curCard);
				}});
			return;
		}
		//TODO
		skill.adjust(curCard, null, theCard, new INextFunc(){
			@Override
			public void nextFunc(Card curCard, Card targetCard,
					INextFunc nextFunc2) {
				sm.getFightingScene().launchSkill(curCard);
			}});
	}
	
	/**
	 * 防污：免疫异常状态
	 */
	public void antiPollute(final Card curCard){
		if (!rm.showSkillEffect){
			sm.getFightingScene().launchSkill(curCard);
			return;
		}
		//TODO
		sm.getFightingScene().launchSkill(curCard);
	}
	
	/**
	 * 潜行：免疫魔法伤害
	 */
	public void lurk(final Card curCard){
		if (!rm.showSkillEffect){
			sm.getFightingScene().launchSkill(curCard);
			return;
		}
		float fromX = curCard.getX()+curCard.getWidth()/4;
		float fromY = curCard.getY()+curCard.getHeight()/4;
		
		final Text skillName =new Text(rm.camera.getWidth()/2,rm.camera.getHeight()/2,
				rm.font,"潜行",rm.vbo);
		
		final Sprite strong = new Sprite(fromX, fromY,
				lurkRegion,rm.vbo);
		
		strong.registerEntityModifier(new SequenceEntityModifier(new IEntityModifierListener() {
			
			@Override
			public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
			}
			
			@Override
			public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
				rm.engine.runOnUpdateThread(new Runnable(){
					@Override
					public void run() {
						strong.detachSelf();
						strong.dispose();
						skillName.detachSelf();
						skillName.dispose();
					}});

				sm.getFightingScene().launchSkill(curCard);
			}
		},new ScaleModifier(0.1f,0.1f,0.5f),
		new RotationModifier(0.15f,0f,60f),
		new RotationModifier(0.15f,60f,0f),
		new ScaleModifier(0.1f,0.5f,1f),
		new ScaleModifier(0.1f,1f,1.2f),
		new AlphaModifier(0.25f, 1, 0)));

		sm.getFightingScene().attachChild(skillName);
		sm.getFightingScene().attachChild(strong);

	}
	
	/**
	 * 刺杀
	 */
	public void assassinate(final Card curCard, final Card theCard, final INextFunc nextFunc){
		if (!rm.showSkillEffect){
			nextFunc.nextFunc(curCard, theCard, null);
			return;
		}
		float fromX = curCard.getX()+curCard.getWidth()/4;
		float fromY = curCard.getY()+curCard.getHeight()/4;
		float toX = theCard.getX() + theCard.getWidth()/2; 
		float toY = theCard.getY() + theCard.getHeight()/2;
		
		double dis = Math.sqrt((toX-fromX)*(toX-fromX)+(toY-fromY)*(toY-fromY));
		double rotation = Math.acos((fromY-toY)/dis);
		float rot = (float) Math.toDegrees(rotation);
		if (toX < fromX) rot = -rot;
		
		final Text skillName =new Text(rm.camera.getWidth()/2,rm.camera.getHeight()/2,
				rm.font,"刺杀",rm.vbo);
		
		final Sprite punch = new Sprite(fromX, fromY,
				assassinateRegion,rm.vbo);
		
		punch.registerEntityModifier(new SequenceEntityModifier(new IEntityModifierListener() {
			
			@Override
			public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
			}
			
			@Override
			public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
				rm.engine.runOnUpdateThread(new Runnable(){
					@Override
					public void run() {
						punch.detachSelf();
						punch.dispose();
						skillName.detachSelf();
						skillName.dispose();
					}});
				nextFunc.nextFunc(curCard, theCard, null);
			}
		},new RotationModifier(0.01f, 0f, rot),
		new MoveModifier(0.25f, fromX, toX, fromY, toY),
		new MoveModifier(0.25f,toX,fromX,toY,fromY),
		new AlphaModifier(0.25f, 1, 0)
		));
		
		sm.getFightingScene().attachChild(skillName);
		sm.getFightingScene().attachChild(punch);
	}
	
	/**
	 * 放能：行动开始前减少自身(x+1)点HP，增加自身(x+1)点攻击力
	 */
	public void releaseEnergy(final Card curCard){
		if (!rm.showSkillEffect){
			skill.releaseEnergy(curCard);
			return;
		}
		float fromX = curCard.getX()+curCard.getWidth()/2;
		float fromY = curCard.getY()+curCard.getHeight()/2;
		
		final Text skillName =new Text(rm.camera.getWidth()/2,rm.camera.getHeight()/2,
				rm.font,"放能",rm.vbo);
		
		final SpriteParticleSystem particleSystem = new SpriteParticleSystem(
				new PointParticleEmitter(fromX, fromY), 
				12, 19, 22, releaseEnergyRegion, rm.vbo);
		
		particleSystem.addParticleInitializer(new AlphaParticleInitializer<Sprite>(0));
		particleSystem.addParticleInitializer(new BlendFunctionParticleInitializer<Sprite>(GLES20.GL_ALPHA_BITS, GLES20.GL_ONE));//GL_SRC_ALPHA
		particleSystem.addParticleInitializer(new VelocityParticleInitializer<Sprite>(-350,350,-255,255));
		particleSystem.addParticleInitializer(new AccelerationParticleInitializer<Sprite>(-200,200,-200,200));
		particleSystem.addParticleInitializer(new RotationParticleInitializer<Sprite>(0.0f, 180.0f));
		particleSystem.addParticleInitializer(new ExpireParticleInitializer<Sprite>(2));

		particleSystem.addParticleModifier(new ScaleParticleModifier<Sprite>(0, 5, 1.0f, 2.0f));
		particleSystem.addParticleModifier(new AlphaParticleModifier<Sprite>(0, 1, 0, 1));
		particleSystem.addParticleModifier(new AlphaParticleModifier<Sprite>(5, 6, 1, 0));
	
		particleSystem.registerEntityModifier(new SequenceEntityModifier(new IEntityModifierListener() {
			@Override
			public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {}
			@Override
			public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
				rm.engine.runOnUpdateThread(new Runnable(){
					@Override
					public void run() {
						particleSystem.detachSelf();
						particleSystem.dispose();
						skillName.detachSelf();
						skillName.dispose();
					}});
				skill.releaseEnergy(curCard);
			}
		},new DelayModifier(1)) );
		
		sm.getFightingScene().attachChild(particleSystem);
		sm.getFightingScene().attachChild(skillName);
		
	}
	
	/**
	 * 群众的力量：己方场上每有一张卡牌增加自身1点攻击力，己方墓地每有一张卡牌增加自身(x+1)点HP
	 */
	public void massesPower(final Card curCard){
		if (!rm.showSkillEffect){
			skill.continueMassesPower(curCard);
			return;
		}
		float fromX = curCard.getX()+curCard.getWidth()/4;
		float fromY = curCard.getY()+curCard.getHeight()/4;
		
		final Text skillName =new Text(rm.camera.getWidth()/2,rm.camera.getHeight()/2,
				rm.font,"群众的力量",rm.vbo);
		
		final Sprite strong = new Sprite(fromX, fromY,
				massesPowerRegion,rm.vbo);
		
		strong.registerEntityModifier(new SequenceEntityModifier(new IEntityModifierListener() {
			
			@Override
			public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
			}
			
			@Override
			public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
				rm.engine.runOnUpdateThread(new Runnable(){
					@Override
					public void run() {
						strong.detachSelf();
						strong.dispose();
						skillName.detachSelf();
						skillName.dispose();
					}});
				skill.continueMassesPower(curCard);
			}
		},new ScaleModifier(0.1f,0.1f,0.5f),
		new RotationModifier(0.15f,0f,60f),
		new RotationModifier(0.15f,60f,0f),
		new ScaleModifier(0.1f,0.5f,1f),
		new ScaleModifier(0.1f,1f,1.6f),
		new AlphaModifier(0.25f, 1, 0)));

		sm.getFightingScene().attachChild(skillName);
		sm.getFightingScene().attachChild(strong);
		
	}
	
	/**
	 * 戒律：使（1+x）张己方卡牌增加自身的30（1+x）%的攻击力，直到下个己方回合开始前
	 */
	public void commandment(final Card curCard){
		if (!rm.showSkillEffect){
			skill.continueCommandment(curCard);
			return;
		}
		float fromX = curCard.getX()+curCard.getWidth()/4;
		float fromY = curCard.getY()+curCard.getHeight()/4;
		
		final Text skillName =new Text(rm.camera.getWidth()/2,rm.camera.getHeight()/2,
				rm.font,"戒律",rm.vbo);
		
		final Sprite strong = new Sprite(fromX, fromY,
				commandmentRegion,rm.vbo);
		
		strong.registerEntityModifier(new SequenceEntityModifier(new IEntityModifierListener() {
			
			@Override
			public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
			}
			
			@Override
			public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
				rm.engine.runOnUpdateThread(new Runnable(){
					@Override
					public void run() {
						strong.detachSelf();
						strong.dispose();
						skillName.detachSelf();
						skillName.dispose();
					}});
				skill.continueCommandment(curCard);
			}
		},new ScaleModifier(0.1f,0.1f,0.5f),
		new RotationModifier(0.15f,0f,60f),
		new RotationModifier(0.15f,60f,0f),
		new ScaleModifier(0.1f,0.5f,1f),
		new ScaleModifier(0.1f,1f,1.2f),
		new AlphaModifier(0.25f, 1, 0)));

		sm.getFightingScene().attachChild(skillName);
		sm.getFightingScene().attachChild(strong);

	}
	
	/**
	 * 预防
	 */
	public void prevent(final Card curCard, final Card theCard){
		if (!rm.showSkillEffect){
			sm.getFightingScene().launchSkill(curCard);
			return;
		}
		float fromX = curCard.getX()+curCard.getWidth()/4;
		float fromY = curCard.getY()+curCard.getHeight()/4;
		float toX = theCard.getX()+theCard.getWidth()/4 ;
		float toY = theCard.getY()+theCard.getWidth()/4 ;
		
		final Text skillName =new Text(rm.camera.getWidth()/2,rm.camera.getHeight()/2,
				rm.font,"预防",rm.vbo);
		
		final Sprite strong = new Sprite(fromX, fromY,
				preventRegion,rm.vbo);
		final Sprite strong2 = new Sprite(toX, toY,
				preventRegion,rm.vbo);
		
		strong.registerEntityModifier(new SequenceEntityModifier(new IEntityModifierListener() {
			
			@Override
			public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
			}
			
			@Override
			public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
				rm.engine.runOnUpdateThread(new Runnable(){
					@Override
					public void run() {
						strong.detachSelf();
						strong.dispose();
						strong2.detachSelf();
						strong2.dispose();
						skillName.detachSelf();
						skillName.dispose();
					}});
				immune(curCard, theCard);
			}
		},new ScaleModifier(0.1f,0.1f,0.5f),
		new ScaleModifier(0.1f,0.5f,1f),
		new ScaleModifier(0.1f,1f,1.6f),
		new AlphaModifier(0.25f, 1, 0)));

		strong2.registerEntityModifier(new SequenceEntityModifier(new IEntityModifierListener() {
			
			@Override
			public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
			}
			
			@Override
			public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
				rm.engine.runOnUpdateThread(new Runnable(){
					@Override
					public void run() {
					}});
			}
		},new ScaleModifier(0.1f,0.1f,0.5f),
		new ScaleModifier(0.1f,0.5f,1f),
		new ScaleModifier(0.1f,1f,1.6f),
		new AlphaModifier(0.25f, 1, 0)));
		
		sm.getFightingScene().attachChild(skillName);
		sm.getFightingScene().attachChild(strong);
		sm.getFightingScene().attachChild(strong2);

	}
	
	/**
	 * 免疫
	 */
	public void immune(final Card curCard, final Card theCard){
		if (!rm.showSkillEffect){
			sm.getFightingScene().launchSkill(curCard);
			return;
		}
		float fromX = theCard.getX();
		float fromY = theCard.getY();
//		float toX = targetCard.getX() + targetCard.getWidth()/2; 
//		float toY = targetCard.getY() + targetCard.getHeight()/2;
		
		final Text skillName =new Text(rm.camera.getWidth()/2,rm.camera.getHeight()/2,
				rm.font,"免疫",rm.vbo);
		
		final Sprite metal = new Sprite(fromX, fromY,
				metalRegion,rm.vbo);
		
		metal.registerEntityModifier(new SequenceEntityModifier(new IEntityModifierListener() {
			
			@Override
			public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
			}
			
			@Override
			public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
				rm.engine.runOnUpdateThread(new Runnable(){
					@Override
					public void run() {
						metal.detachSelf();
						metal.dispose();
						skillName.detachSelf();
						skillName.dispose();
					}});
				sm.getFightingScene().launchSkill(curCard);
			}
		},new ScaleModifier(0.5f,1f,1.5f),
		new AlphaModifier(0.5f, 1, 0),
		new DelayModifier(0.5f)));
		
		sm.getFightingScene().attachChild(skillName);
		sm.getFightingScene().attachChild(metal);

	}
	
	/**
	 * 复活
	 */
	public void revive(final Card curCard, final Player curPlayer){
		if (!rm.showSkillEffect){
			skill.continueRevive(curCard, curPlayer);
			return;
		}
		float fromX = curCard.getX()+curCard.getWidth()/4;
		float fromY = curCard.getY()+curCard.getHeight()/4;
		
		final Text skillName =new Text(rm.camera.getWidth()/2,rm.camera.getHeight()/2,
				rm.font,"复活",rm.vbo);
		
		final Sprite strong = new Sprite(fromX, fromY,
				reviveRegion,rm.vbo);
		
		strong.registerEntityModifier(new SequenceEntityModifier(new IEntityModifierListener() {
			
			@Override
			public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
			}
			
			@Override
			public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
				rm.engine.runOnUpdateThread(new Runnable(){
					@Override
					public void run() {
						strong.detachSelf();
						strong.dispose();
						skillName.detachSelf();
						skillName.dispose();
					}});
				skill.continueRevive(curCard, curPlayer);
			}
		},new ScaleModifier(0.1f,0.1f,0.5f),
		new ScaleModifier(0.1f,0.5f,1f),
		new ScaleModifier(0.1f,1f,1.6f),
		new AlphaModifier(0.25f, 1, 0)));

		sm.getFightingScene().attachChild(skillName);
		sm.getFightingScene().attachChild(strong);

	}
	
	/**
	 * 影像
	 */
	public void imagination(final Card curCard, final Card theCard){
		if (!rm.showSkillEffect){
			skill.continueImagination(curCard, theCard);
			return;
		}
		float fromX = curCard.getX()+curCard.getWidth()/4;
		float fromY = curCard.getY()+curCard.getHeight()/4;

		
		final Text skillName =new Text(rm.camera.getWidth()/2,rm.camera.getHeight()/2,
				rm.font,"影像",rm.vbo);
		
		final Sprite strong = new Sprite(fromX, fromY,
				imaginationRegion,rm.vbo);
		
		strong.registerEntityModifier(new SequenceEntityModifier(new IEntityModifierListener() {
			
			@Override
			public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
			}
			
			@Override
			public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
				rm.engine.runOnUpdateThread(new Runnable(){
					@Override
					public void run() {
						strong.detachSelf();
						strong.dispose();
						skillName.detachSelf();
						skillName.dispose();
					}});
				skill.continueImagination(curCard, theCard);
			}
		},new ScaleModifier(0.1f,1f,0.1f),
		new ScaleModifier(0.1f,0.1f,1f),
		new ScaleModifier(0.1f,0.8f,1f),
		new AlphaModifier(0.25f, 1, 0)));

		sm.getFightingScene().attachChild(skillName);
		sm.getFightingScene().attachChild(strong);

	}
	
	/**
	 * 尸毒：使敌方所有卡牌进入中毒状态
	 */
	public void corpse(final Card curCard){
		if (!rm.showSkillEffect){
			skill.continueCorpse(curCard);
			return;
		}
		//TODO 显示效果
		skill.continueCorpse(curCard);
	}
	
	/**
	 * 召唤
	 */
	public void callZombie(final Card curCard, final Location loc){
		if (!rm.showSkillEffect){
			skill.continueCallZombie(curCard, loc);
			return;
		}
		float fromX = curCard.getX()+curCard.getWidth()/4;
		float fromY = curCard.getY()+curCard.getHeight()/4;
		
		final Text skillName =new Text(rm.camera.getWidth()/2,rm.camera.getHeight()/2,
				rm.font,"召唤",rm.vbo);
		
		final Sprite strong = new Sprite(fromX, fromY,
				callZombieRegion,rm.vbo);
		
		strong.registerEntityModifier(new SequenceEntityModifier(new IEntityModifierListener() {
			
			@Override
			public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
			}
			
			@Override
			public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
				rm.engine.runOnUpdateThread(new Runnable(){
					@Override
					public void run() {
						strong.detachSelf();
						strong.dispose();
						skillName.detachSelf();
						skillName.dispose();
					}});
				skill.continueCallZombie(curCard, loc);
			}
		},new ScaleModifier(0.1f,0.5f,0.8f),
		new RotationModifier(0.15f,0f,60f),
		new ScaleModifier(0.1f,0.8f,1f),
		new AlphaModifier(0.25f, 1, 0)));

		sm.getFightingScene().attachChild(skillName);
		sm.getFightingScene().attachChild(strong);
	}
	
	/**
	 * 毒素
	 */
	public void toxin(final Player player, final Card curCard, final int change){
		if (!rm.showSkillEffect){
			player.setHP(player.getHP() + change);
			sm.getFightingScene().launchSkill(curCard);
			return;
		}
		//TODO 
		setPlayerHpText(player, curCard, null, change, new INextFunc(){
			@Override
			public void nextFunc(Card curCard, Card targetCard,
					INextFunc nextFunc2) {
				sm.getFightingScene().launchSkill(curCard);
			}});
	}
	
	/**
	 * 祛病
	 */
	public void cure(final Card curCard, final Card theCard){
		if (!rm.showSkillEffect){
			skill.continueCure2(curCard, theCard);
			return;
		}
		float fromX = curCard.getX()+curCard.getWidth()/4;
		float fromY = curCard.getY()+curCard.getHeight()/4;
		float toX = theCard.getX()+theCard.getWidth()/4 ;
		float toY = theCard.getY()+theCard.getWidth()/4 ;
		
		final Text skillName =new Text(rm.camera.getWidth()/2,rm.camera.getHeight()/2,
				rm.font,"祛病",rm.vbo);
		
		final Sprite strong = new Sprite(fromX, fromY,
				cureRegion,rm.vbo);
		final Sprite strong2 = new Sprite(toX, toY,
				cureRegion,rm.vbo);
		
		strong.registerEntityModifier(new SequenceEntityModifier(new IEntityModifierListener() {
			
			@Override
			public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
			}
			
			@Override
			public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
				rm.engine.runOnUpdateThread(new Runnable(){
					@Override
					public void run() {
						strong.detachSelf();
						strong.dispose();
						strong2.detachSelf();
						strong2.dispose();
						skillName.detachSelf();
						skillName.dispose();
					}});
				skill.continueCure2(curCard, theCard);
			}
		},new ScaleModifier(0.1f,0.1f,0.5f),
		new RotationModifier(0.15f,0f,60f),
		new RotationModifier(0.15f,60f,0f),
		new ScaleModifier(0.1f,0.5f,1f),
		new ScaleModifier(0.1f,1f,1.6f),
		new AlphaModifier(0.25f, 1, 0)));

		strong2.registerEntityModifier(new SequenceEntityModifier(new IEntityModifierListener() {
			
			@Override
			public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
			}
			
			@Override
			public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
				rm.engine.runOnUpdateThread(new Runnable(){
					@Override
					public void run() {
					}});
			}
		},new ScaleModifier(0.1f,0.1f,0.5f),
		new RotationModifier(0.15f,0f,60f),
		new RotationModifier(0.15f,60f,0f),
		new ScaleModifier(0.1f,0.5f,1f),
		new ScaleModifier(0.1f,1f,1.6f),
		new AlphaModifier(0.25f, 1, 0)));
		
		sm.getFightingScene().attachChild(skillName);
		sm.getFightingScene().attachChild(strong);
		sm.getFightingScene().attachChild(strong2);

	}
	
	/**
	 * 治愈
	 */
	public void heal(final Card curCard){
		if (!rm.showSkillEffect){
			skill.continueHeal(curCard);
			return;
		}
		float fromX = curCard.getX()+curCard.getWidth()/4;
		float fromY = curCard.getY()+curCard.getHeight()/4;

		
		final Text skillName =new Text(rm.camera.getWidth()/2,rm.camera.getHeight()/2,
				rm.font,"治愈",rm.vbo);
		
		final SpriteParticleSystem particleSystem = new SpriteParticleSystem(
				new PointParticleEmitter(fromX, fromY), 
				4, 12, 20, healRegion, rm.vbo);
		
		particleSystem.addParticleInitializer(new ColorParticleInitializer<Sprite>(1, 1, 1));
		particleSystem.addParticleInitializer(new AlphaParticleInitializer<Sprite>(0));
		particleSystem.addParticleInitializer(new VelocityParticleInitializer<Sprite>(-400,400,0,0));
		particleSystem.addParticleInitializer(new AccelerationParticleInitializer<Sprite>(-15,15,-15,15));
		particleSystem.addParticleInitializer(new RotationParticleInitializer<Sprite>(0.0f, 0f));
		particleSystem.addParticleInitializer(new ExpireParticleInitializer<Sprite>(2));

		particleSystem.addParticleModifier(new ScaleParticleModifier<Sprite>(0, 5, 1.0f, 2.0f));
		particleSystem.addParticleModifier(new AlphaParticleModifier<Sprite>(0, 1, 0.5f, 1));
		particleSystem.addParticleModifier(new ColorParticleModifier<Sprite>(0f, 1.0f, 1.0f, 0.5f, 1.0f, 0.5f, 1.0f, 0.5f));
		particleSystem.addParticleModifier(new ColorParticleModifier<Sprite>(1f, 2.0f, 0.5f, 2f, 0.5f, 2f, 0.5f, 2f));
		
		particleSystem.registerEntityModifier(new SequenceEntityModifier(new IEntityModifierListener() {
			@Override
			public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {}
			@Override
			public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
				rm.engine.runOnUpdateThread(new Runnable(){
					@Override
					public void run() {
						particleSystem.detachSelf();
						particleSystem.dispose();
						skillName.detachSelf();
						skillName.dispose();
					}});
				skill.continueHeal(curCard);
			}
		},new DelayModifier(1)) );
		
		sm.getFightingScene().attachChild(particleSystem);
		sm.getFightingScene().attachChild(skillName);

	}
	
	/**
	 * 治疗
	 */
	public void treat(final Card curCard, final Card theCard, final int change, final INextFunc nextFunc){
		if (!rm.showSkillEffect){
			sm.getFightingScene().setHPText(curCard, theCard, theCard, change, new INextFunc(){
				@Override
				public void nextFunc(Card curCard, Card targetCard,
						INextFunc nextFunc2) {
					nextFunc.nextFunc(curCard, targetCard, nextFunc2);
				}}, null);
			return;
		}
		float fromX = theCard.getX()+theCard.getWidth()/2;
		float fromY = theCard.getY()+theCard.getHeight();
		
		final Text skillName =new Text(rm.camera.getWidth()/2,rm.camera.getHeight()/2,
				rm.font,"治疗",rm.vbo);
		
		final SpriteParticleSystem particleSystem = new SpriteParticleSystem(
				new PointParticleEmitter(fromX, fromY), 
				3, 4, 360, mParticleRegion, rm.vbo);
		
		particleSystem.addParticleInitializer(new ColorParticleInitializer<Sprite>(0, 1, 0));
		particleSystem.addParticleInitializer(new AlphaParticleInitializer<Sprite>(0));
	//	particleSystem.addParticleInitializer(new BlendFunctionParticleInitializer<Sprite>(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE));
		particleSystem.addParticleInitializer(new VelocityParticleInitializer<Sprite>(0, -50));
		particleSystem.addParticleInitializer(new AccelerationParticleInitializer<Sprite>(-25,25,0,0));
		particleSystem.addParticleInitializer(new RotationParticleInitializer<Sprite>(0.0f, 360.0f));
		particleSystem.addParticleInitializer(new ExpireParticleInitializer<Sprite>(2));

		particleSystem.addParticleModifier(new ScaleParticleModifier<Sprite>(0, 1.5f, 1.0f, 2.0f));
		particleSystem.addParticleModifier(new AlphaParticleModifier<Sprite>(0, 0.5f, 0, 1));
		particleSystem.addParticleModifier(new AlphaParticleModifier<Sprite>(0.5f, 1.5f, 1, 0));

		particleSystem.registerEntityModifier(new SequenceEntityModifier(new IEntityModifierListener() {
			@Override
			public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {}
			@Override
			public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
				rm.engine.runOnUpdateThread(new Runnable(){
					@Override
					public void run() {
						particleSystem.detachSelf();
						particleSystem.dispose();
						skillName.detachSelf();
						skillName.dispose();
					}});
				sm.getFightingScene().setHPText(curCard, theCard, theCard, change, new INextFunc(){
					@Override
					public void nextFunc(Card curCard, Card targetCard,
							INextFunc nextFunc2) {
						nextFunc.nextFunc(curCard, targetCard, nextFunc2);
					}}, null);
			}
		},new DelayModifier(1.5f)) );
		
		sm.getFightingScene().attachChild(particleSystem);
		sm.getFightingScene().attachChild(skillName);
	}
	
	/**
	 * 庇护
	 * @param curCard
	 */
	public void shelter(final Card curCard){
		if (!rm.showSkillEffect){
			skill.continueShelter(curCard);
			return;
		}
		float fromX = curCard.getX()+curCard.getWidth()/4;
		float fromY = curCard.getY()+curCard.getHeight()/4;
		
		final Text skillName =new Text(rm.camera.getWidth()/2,rm.camera.getHeight()/2,
				rm.font,"庇护",rm.vbo);
		
		final Sprite strong = new Sprite(fromX, fromY,
				shelterRegion,rm.vbo);
		
		strong.registerEntityModifier(new SequenceEntityModifier(new IEntityModifierListener() {
			
			@Override
			public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
			}
			
			@Override
			public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
				rm.engine.runOnUpdateThread(new Runnable(){
					@Override
					public void run() {
						strong.detachSelf();
						strong.dispose();
						skillName.detachSelf();
						skillName.dispose();
					}});
				skill.continueShelter(curCard);
			}
		},new ScaleModifier(0.1f,0.5f,1f),
		new ScaleModifier(0.15f,1f,1.1f),
		new ScaleModifier(0.1f,1.1f,1.3f),
		new AlphaModifier(0.25f, 1, 0)));

		sm.getFightingScene().attachChild(skillName);
		sm.getFightingScene().attachChild(strong);

	}
	
	/**
	 * 进化
	 */
	public void evolve(final Card curCard) {
		if (!rm.showSkillEffect){
			skill.evolve(curCard);
			return;
		}
		float fromX = curCard.getX()+curCard.getWidth()/4;
		float fromY = curCard.getY()+curCard.getHeight()/4;
		
		final Text skillName =new Text(rm.camera.getWidth()/2,rm.camera.getHeight()/2,
				rm.font,"进化",rm.vbo);
		
		final Sprite strong = new Sprite(fromX, fromY,
				evolveRegion,rm.vbo);
		
		strong.registerEntityModifier(new SequenceEntityModifier(new IEntityModifierListener() {
			
			@Override
			public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
			}
			
			@Override
			public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
				rm.engine.runOnUpdateThread(new Runnable(){
					@Override
					public void run() {
						strong.detachSelf();
						strong.dispose();
						skillName.detachSelf();
						skillName.dispose();
					}});
				skill.evolve(curCard);
			}
		},new ScaleModifier(0.1f,0.5f,1f),
		new ScaleModifier(0.15f,1f,1.1f),
		new ScaleModifier(0.1f,1.1f,1.3f),
		new AlphaModifier(0.25f, 1, 0)));

		sm.getFightingScene().attachChild(skillName);
		sm.getFightingScene().attachChild(strong);

	}
	
	/**
	 * 烈焰风暴
	 */
	public void flameStrike(final Card curCard, final int cut){
		if (!rm.showSkillEffect){
			skill.continueFlameStrike(curCard, cut);
			return;
		}
		float fromX = curCard.getX()+curCard.getWidth()/4;
		float fromY = curCard.getY()+curCard.getHeight()/4;

		
		final Text skillName =new Text(rm.camera.getWidth()/2,rm.camera.getHeight()/2,
				rm.font,"烈焰风暴",rm.vbo);
		
		final SpriteParticleSystem particleSystem = new SpriteParticleSystem(
				new PointParticleEmitter(fromX, fromY), 
				2, 12, 20, flameStrikeRegion, rm.vbo);
		
		particleSystem.addParticleInitializer(new ColorParticleInitializer<Sprite>(1, 1, 1));
		particleSystem.addParticleInitializer(new AlphaParticleInitializer<Sprite>(0));
		particleSystem.addParticleInitializer(new VelocityParticleInitializer<Sprite>(-150,150,-150,150));
		particleSystem.addParticleInitializer(new AccelerationParticleInitializer<Sprite>(-15,15,-15,15));
		particleSystem.addParticleInitializer(new RotationParticleInitializer<Sprite>(0.0f, 360.0f));
		particleSystem.addParticleInitializer(new ExpireParticleInitializer<Sprite>(2));

		particleSystem.addParticleModifier(new ScaleParticleModifier<Sprite>(0, 5, 1.0f, 2.0f));
		particleSystem.addParticleModifier(new AlphaParticleModifier<Sprite>(0, 1, 0, 1));
		particleSystem.addParticleModifier(new AlphaParticleModifier<Sprite>(5, 6, 1, 0));
		particleSystem.addParticleModifier(new ColorParticleModifier<Sprite>(0f, 1.0f, 1.0f, 0.5f, 1.0f, 0.5f, 1.0f, 0.5f));
		particleSystem.addParticleModifier(new ColorParticleModifier<Sprite>(1f, 2.0f, 0.5f, 2f, 0.5f, 2f, 0.5f, 2f));
		
		particleSystem.registerEntityModifier(new SequenceEntityModifier(new IEntityModifierListener() {
			@Override
			public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {}
			@Override
			public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
				rm.engine.runOnUpdateThread(new Runnable(){
					@Override
					public void run() {
						particleSystem.detachSelf();
						particleSystem.dispose();
						skillName.detachSelf();
						skillName.dispose();
					}});
				skill.continueFlameStrike(curCard, cut);
			}
		},new DelayModifier(1)) );
		
		sm.getFightingScene().attachChild(particleSystem);
		sm.getFightingScene().attachChild(skillName);
	
	}
	
	/**
	 * 扭曲
	 */
	public void twist(final Card curCard, final Card targetCard, int cut){
		if (!rm.showSkillEffect){
			cutHPEffect(curCard, targetCard, cut);
			return;
		}
		//TODO 显示效果
		cutHPEffect(curCard, targetCard, cut);
	}
	
	/**
	 * 强壮
	 */
	public void strong(final Card curCard, final Card targetCard, final int cut){
		if (!rm.showSkillEffect){
			sm.getFightingScene().judge93Twist(curCard, targetCard, cut);
			return;
		}
		float fromX = curCard.getX()+curCard.getWidth()/4;
		float fromY = curCard.getY()+curCard.getHeight()/4;
		
		final Text skillName =new Text(rm.camera.getWidth()/2,rm.camera.getHeight()/2,
				rm.font,"强壮",rm.vbo);
		
		final Sprite strong = new Sprite(fromX, fromY,
				strongRegion,rm.vbo);
		
		strong.registerEntityModifier(new SequenceEntityModifier(new IEntityModifierListener() {
			
			@Override
			public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
			}
			
			@Override
			public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
				rm.engine.runOnUpdateThread(new Runnable(){
					@Override
					public void run() {
						strong.detachSelf();
						strong.dispose();
						skillName.detachSelf();
						skillName.dispose();
					}});
				sm.getFightingScene().judge93Twist(curCard, targetCard, cut);
			}
		},new ScaleModifier(0.1f,0.5f,1f),
		new ScaleModifier(0.35f,1f,0.11f),
		new ScaleModifier(0.1f,0.11f,1.1f),
		new AlphaModifier(0.25f, 1, 0)));

		sm.getFightingScene().attachChild(skillName);
		sm.getFightingScene().attachChild(strong);

	}
	
	/**
	 * 威压
	 */
	public void coercion(final Card curCard){
		if (!rm.showSkillEffect){
			skill.continueCoercion(curCard);
			return;
		}
		float fromX = curCard.getX();
		float fromY = curCard.getY();
		
		final Text skillName =new Text(rm.camera.getWidth()/2,rm.camera.getHeight()/2,
				rm.font,"威压",rm.vbo);
		
		final Sprite coercion = new Sprite(fromX, fromY,
				coercionRegion,rm.vbo);
		
		coercion.registerEntityModifier(new SequenceEntityModifier(new IEntityModifierListener() {
			
			@Override
			public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
			}
			
			@Override
			public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
				rm.engine.runOnUpdateThread(new Runnable(){
					@Override
					public void run() {
						coercion.detachSelf();
						coercion.dispose();
						skillName.detachSelf();
						skillName.dispose();
					}});
				skill.continueCoercion(curCard);
			}
		},new ScaleModifier(0.1f,0.5f,1f),
		new RotationModifier(0.15f, 0, 360),
		new RotationModifier(0.25f, 360,-30),
		new RotationModifier(0.15f, -30, 360),
		new AlphaModifier(0.25f, 1, 0)));

		sm.getFightingScene().attachChild(skillName);
		sm.getFightingScene().attachChild(coercion);
		
	}
	
	/**
	 * 交易
	 */
	public void trade(final Card curCard, final Card theCard, final int cut){
		if (!rm.showSkillEffect){
			skill.continueTrade2(curCard, theCard, cut);
			return;
		}
		float fromX = curCard.getX();
		float fromY = curCard.getY();
		float toX = theCard.getX() ;
		float toY = theCard.getY() ;

		
		final Text skillName =new Text(rm.camera.getWidth()/2,rm.camera.getHeight()/2,
				rm.font,"交易",rm.vbo);
		
		final Sprite trade1 = new Sprite(fromX, fromY,
				tradeRegion,rm.vbo);
		final Sprite trade2 = new Sprite(toX, toY,
				tradeRegion,rm.vbo);
		
		trade1.registerEntityModifier(new SequenceEntityModifier(new IEntityModifierListener() {
			
			@Override
			public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
			}
			
			@Override
			public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
				rm.engine.runOnUpdateThread(new Runnable(){
					@Override
					public void run() {
						trade1.detachSelf();
						trade1.dispose();
						trade2.detachSelf();
						trade2.dispose();
						skillName.detachSelf();
						skillName.dispose();
					}});
				skill.continueTrade2(curCard, theCard, cut);
			}
		},new ScaleModifier(0.1f,0.5f,1f),
		new RotationModifier(0.15f, 0, 30),
		new RotationModifier(0.25f, 30,-30),
		new RotationModifier(0.15f, -30, 0),
		new RotationModifier(0.15f, 0, 30),
		new RotationModifier(0.2f, 30,-30),
		new RotationModifier(0.15f, -30, 0),
		new AlphaModifier(0.25f, 1, 0)));
		
		trade2.registerEntityModifier(new SequenceEntityModifier(new IEntityModifierListener() {
			@Override
			public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
			}
			@Override
			public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
			}
		},new ScaleModifier(0.1f,0.5f,1f),
		new RotationModifier(0.15f, 0, 30),
		new RotationModifier(0.25f, 30,-30),
		new RotationModifier(0.15f, -30, 0),
		new RotationModifier(0.15f, 0, 30),
		new RotationModifier(0.2f, 30,-30),
		new RotationModifier(0.15f, -30, 0),
		new AlphaModifier(0.25f, 1, 0)));
		
		sm.getFightingScene().attachChild(skillName);
		sm.getFightingScene().attachChild(trade1);
		sm.getFightingScene().attachChild(trade2);

	}
	
	/**
	 * 电击
	 * @param probability 
	 * @param cut 
	 */
	public void electricShock(final Card curCard, final Card theCard, final int cut, final double probability){
		if (!rm.showSkillEffect){
			skill.continueElectricShock2(curCard, theCard, cut, probability);
			return;
		}
		float fromX = curCard.getX()+curCard.getWidth()/4;
		float fromY = curCard.getY()+curCard.getHeight()/4;
		float toX = theCard.getX()+theCard.getWidth()/4 ;
		float toY = theCard.getY()+theCard.getWidth()/4 ;
		
		final Text skillName =new Text(rm.camera.getWidth()/2,rm.camera.getHeight()/2,
				rm.font,"电击",rm.vbo);
		
		final Sprite strong = new Sprite(fromX, fromY,
				electricShockRegion,rm.vbo);
		final Sprite strong2 = new Sprite(toX, toY,
				electricShockRegion,rm.vbo);
		
		strong.registerEntityModifier(new SequenceEntityModifier(new IEntityModifierListener() {
			
			@Override
			public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
			}
			
			@Override
			public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
				rm.engine.runOnUpdateThread(new Runnable(){
					@Override
					public void run() {
						strong.detachSelf();
						strong.dispose();
						strong2.detachSelf();
						strong2.dispose();
						skillName.detachSelf();
						skillName.dispose();
					}});
				skill.continueElectricShock2(curCard, theCard, cut, probability);
			}
		},new ScaleModifier(0.1f,1f,0.1f),
		new ScaleModifier(0.1f,0.1f,1f),
		new ScaleModifier(0.1f,0.8f,1f),
		new AlphaModifier(0.25f, 1, 0)));
		
		strong2.registerEntityModifier(new SequenceEntityModifier(new IEntityModifierListener() {
			
			@Override
			public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
			}
			
			@Override
			public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
				rm.engine.runOnUpdateThread(new Runnable(){
					@Override
					public void run() {
					}});
			}
		},new ScaleModifier(0.1f,1f,0.1f),
		new ScaleModifier(0.1f,0.1f,1f),
		new ScaleModifier(0.1f,0.8f,1f),
		new AlphaModifier(0.25f, 1, 0)));

		sm.getFightingScene().attachChild(skillName);
		sm.getFightingScene().attachChild(strong);
		sm.getFightingScene().attachChild(strong2);
		
	}
	
	/**
	 * 歌颂：对己方场上所有卡牌发动“调整”，上场时和<协助状态>数量改变时发动
	 * */
	public void singing(final Card curCard){
		if (!rm.showSkillEffect){
			skill.continueSinging(curCard);
			return;
		}
		float fromX = curCard.getX()+curCard.getWidth()/2;
		float fromY = curCard.getY()+curCard.getHeight()/2;
		
		final Text skillName =new Text(rm.camera.getWidth()/2,rm.camera.getHeight()/2,
				rm.font,"歌颂",rm.vbo);
		
		final SpriteParticleSystem particleSystem = new SpriteParticleSystem(
				new PointParticleEmitter(fromX, fromY), 
				22, 22, 220, performRegion, rm.vbo);
		final Sprite sing = new Sprite(curCard.getX(), curCard.getY(),
				singRegion,rm.vbo);
		
		particleSystem.addParticleInitializer(new ColorParticleInitializer<Sprite>(0, 1, 0));
		particleSystem.addParticleInitializer(new AlphaParticleInitializer<Sprite>(0));
		particleSystem.addParticleInitializer(new BlendFunctionParticleInitializer<Sprite>(GLES20.GL_ALPHA_BITS, GLES20.GL_ONE));//GL_SRC_ALPHA
		particleSystem.addParticleInitializer(new VelocityParticleInitializer<Sprite>(-350,350,-255,255));
		particleSystem.addParticleInitializer(new AccelerationParticleInitializer<Sprite>(-100,100,-100,100));
		particleSystem.addParticleInitializer(new RotationParticleInitializer<Sprite>(0.0f, 0.0f));
		particleSystem.addParticleInitializer(new ExpireParticleInitializer<Sprite>(2));

		particleSystem.addParticleModifier(new ScaleParticleModifier<Sprite>(0, 5, 1.0f, 2.0f));
		particleSystem.addParticleModifier(new AlphaParticleModifier<Sprite>(0, 1, 0, 1));
		particleSystem.addParticleModifier(new AlphaParticleModifier<Sprite>(5, 6, 1, 0));
		particleSystem.addParticleModifier(new ColorParticleModifier<Sprite>(0f, 1.0f, 0.0f, 0.5f, 1.0f, 0.0f, 0.0f, 0.5f));
		particleSystem.addParticleModifier(new ColorParticleModifier<Sprite>(1f, 2.0f, 0.5f, 0f, 0.0f, 0.5f, 0.5f, 1f));
		
		particleSystem.registerEntityModifier(new SequenceEntityModifier(new IEntityModifierListener() {
			@Override
			public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {}
			@Override
			public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
				rm.engine.runOnUpdateThread(new Runnable(){
					@Override
					public void run() {
						particleSystem.detachSelf();
						particleSystem.dispose();
						skillName.detachSelf();
						skillName.dispose();
						sing.detachSelf();
						sing.dispose();
					}});
				skill.continueSinging(curCard);
			}
		},new DelayModifier(2)) );
		
		sing.registerEntityModifier(new SequenceEntityModifier(new IEntityModifierListener() {
			@Override
			public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
			}
			@Override
			public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {}
		},new ScaleModifier(0.1f,0.5f,1f),
		new RotationModifier(0.15f, 0, 30),
		new RotationModifier(0.25f, 30,-30),
		new RotationModifier(0.15f, -30, 0),
		new RotationModifier(0.15f, 0, 30),
		new RotationModifier(0.25f, 30,-30),
		new RotationModifier(0.15f, -30, 0),
		new AlphaModifier(1, 1, 0)));
		sm.getFightingScene().attachChild(particleSystem);
		sm.getFightingScene().attachChild(skillName);
		sm.getFightingScene().attachChild(sing);
	}
	
	/**
	 * 调整
	 * */
	public void adjust(final Card curCard, final Card targetCard, final Card theCard, final INextFunc nextFunc){
		if (!rm.showSkillEffect){
			skill.adjust(curCard, targetCard, theCard, nextFunc);
			return;
		}
		//TODO 显示效果
		skill.adjust(curCard, targetCard, theCard, nextFunc);
	}
	
	/**
	 * 逆调整
	 * */
	public void reverseAdjust(final Card curCard, final Card targetCard, final Card theCard, final INextFunc nextFunc){
		if (!rm.showSkillEffect){
			skill.reverseAdjust(curCard, targetCard, theCard, nextFunc);
			return;
		}
		//TODO 显示效果
		skill.reverseAdjust(curCard, targetCard, theCard, nextFunc);
	}
	
	/**
	 * 污染
	 */
	public void pollute(final Card curCard, final Card targetCard){
		if (!rm.showSkillEffect){
			skill.pollute(curCard, targetCard);
			return;
		}
	//	float fromX = curCard.getX()+curCard.getWidth()/2;
	//	float fromY = curCard.getY()+curCard.getHeight()/2;
		float toX = targetCard.getX() + targetCard.getWidth()/2; 
		float toY = targetCard.getY() + targetCard.getHeight()/2;
		
		final Text skillName =new Text(rm.camera.getWidth()/2,rm.camera.getHeight()/2,
				rm.font,"污染",rm.vbo);
		
		final SpriteParticleSystem particleSystem = new SpriteParticleSystem(
				new PointParticleEmitter(toX, toY), 
				10, 10, 50, polluteRegion, rm.vbo);
		
		particleSystem.addParticleInitializer(new ColorParticleInitializer<Sprite>(1, 1, 1));
		particleSystem.addParticleInitializer(new AlphaParticleInitializer<Sprite>(0));
//		particleSystem.addParticleInitializer(new BlendFunctionParticleInitializer<Sprite>(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE));
		particleSystem.addParticleInitializer(new VelocityParticleInitializer<Sprite>(-50,50,-50,50));
		particleSystem.addParticleInitializer(new AccelerationParticleInitializer<Sprite>(-15,15,-5,5));
		particleSystem.addParticleInitializer(new RotationParticleInitializer<Sprite>(0.0f, 0.0f));
		particleSystem.addParticleInitializer(new ExpireParticleInitializer<Sprite>(2));

		particleSystem.addParticleModifier(new ScaleParticleModifier<Sprite>(0, 2.5f, 1.0f, 2.0f));
		particleSystem.addParticleModifier(new AlphaParticleModifier<Sprite>(0, 1f, 0, 1));
		particleSystem.addParticleModifier(new AlphaParticleModifier<Sprite>(1f, 1.5f, 1, 0));
		particleSystem.addParticleModifier(new ColorParticleModifier<Sprite>(0f, 1.0f, 1.0f, 0.5f, 1.0f, 0.5f, 1.0f, 0.5f));
		particleSystem.addParticleModifier(new ColorParticleModifier<Sprite>(1f, 2.0f, 0.5f, 2f, 0.5f, 2f, 0.5f, 2f));
		
		particleSystem.registerEntityModifier(new SequenceEntityModifier(new IEntityModifierListener() {
			@Override
			public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {}
			@Override
			public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
				rm.engine.runOnUpdateThread(new Runnable(){
					@Override
					public void run() {
						particleSystem.detachSelf();
						particleSystem.dispose();
						skillName.detachSelf();
						skillName.dispose();
					}});
				skill.pollute(curCard, targetCard);
			}
		},new DelayModifier(1.5f)) );
		
		sm.getFightingScene().attachChild(particleSystem);
		sm.getFightingScene().attachChild(skillName);

	}
	
	/**
	 * 辐射
	 */
	public void radiate(final Card curCard, final Card targetCard){
		if (!rm.showSkillEffect){
			skill.radiate(curCard, targetCard);
			return;
		}
		float fromX = curCard.getX()+curCard.getWidth()/2;
		float fromY = curCard.getY()+curCard.getHeight()/2;
		float toX = targetCard.getX() + targetCard.getWidth()/2; 
		float toY = targetCard.getY() + targetCard.getHeight()/2;
	//	float toX = 222; 
	//	float toY = 222;
		float time = 0.5f;
		float velocityX = (toX - fromX) / time,
				velocityY = (toY - fromY) / time ;
		
		double dis = Math.sqrt((toX-fromX)*(toX-fromX)+(toY-fromY)*(toY-fromY));
		double rotation = Math.acos((fromY-toY)/dis);
		float rot = (float) Math.toDegrees(rotation);
		if (toX < fromX) rot = -rot;
		
		final Text skillName =new Text(rm.camera.getWidth()/2,rm.camera.getHeight()/2,
				rm.font,"辐射",rm.vbo);
		
		final SpriteParticleSystem particleSystem = new SpriteParticleSystem(
				new RectangleParticleEmitter(fromX, fromY, curCard.getWidth()/2, curCard.getHeight()/2), 
				1, 2, 7, radiateRegion, rm.vbo);
		
		particleSystem.addParticleInitializer(new VelocityParticleInitializer<Sprite>(velocityX, velocityY));
		particleSystem.addParticleInitializer(new AccelerationParticleInitializer<Sprite>(0, 0));
		particleSystem.addParticleInitializer(new ColorParticleInitializer<Sprite>(0.45f,0.45f,0.45f));
		particleSystem.addParticleInitializer(new RotationParticleInitializer<Sprite>(rot, rot));
		particleSystem.addParticleInitializer(new ExpireParticleInitializer<Sprite>(1.5f));
		
		particleSystem.addParticleModifier(new ScaleParticleModifier<Sprite>(0, time, 1f, 1f));
		particleSystem.addParticleModifier(new AlphaParticleModifier<Sprite>(time/2,time, 1, 0));
		
		particleSystem.registerEntityModifier(new SequenceEntityModifier(new IEntityModifierListener() {
			@Override
			public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {}
			@Override
			public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
				rm.engine.runOnUpdateThread(new Runnable(){
					@Override
					public void run() {
						particleSystem.detachSelf();
						particleSystem.dispose();
						skillName.detachSelf();
						skillName.dispose();
					}});
				skill.radiate(curCard, targetCard);
			}
		},new DelayModifier(1)) );
		
		sm.getFightingScene().attachChild(particleSystem);
		sm.getFightingScene().attachChild(skillName);

	}
	
	/**
	 * 轻灵
	 */
	public void legerity(final Card curCard, final Card targetCard, final INextFunc nextFunc){
		if (!rm.showSkillEffect){
			nextFunc.nextFunc(curCard, targetCard, null);
			return;
		}
		final float fromX = targetCard.getX();
		final float fromY = targetCard.getY();
		final Text skillName =new Text(rm.camera.getWidth()/2,rm.camera.getHeight()/2,
				rm.font,"轻灵",rm.vbo);
		
		targetCard.registerEntityModifier(new SequenceEntityModifier(new IEntityModifierListener() {
			@Override
			public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
				arg0.setAutoUnregisterWhenFinished(true);
			}
			@Override
			public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
				rm.engine.runOnUpdateThread(new Runnable(){
					@Override
					public void run() {
						skillName.detachSelf();
						skillName.dispose();
					}});
				nextFunc.nextFunc(curCard, targetCard, null);
			}
			},new MoveModifier(0.1f, fromX, fromX, fromY, fromY - 10),
			new DelayModifier(0.5f),
			new MoveModifier(0.1f,  fromX, fromX, fromY - 10, fromY)));

		sm.getFightingScene().attachChild(skillName);
	}
	
	/**
	 * 咆哮
	 */
	public void roar(final Card curCard, final Card targetCard){
		if (!rm.showSkillEffect){
			sm.getFightingScene().judge9EarthStab(curCard, targetCard);
			return;
		}
		float fromX = curCard.getX()+curCard.getWidth()/2;
		float fromY = curCard.getY()+curCard.getHeight()/2;

		float time = 0.5f;

		final Sprite roarSprite = new Sprite(fromX, fromY, roarRegion, rm.vbo);
		
		final Text skillName =new Text(rm.camera.getWidth()/2,rm.camera.getHeight()/2,
				rm.font,"咆哮",rm.vbo);

		
		roarSprite.registerEntityModifier(new SequenceEntityModifier(new IEntityModifierListener() {
			@Override
			public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {}
			@Override
			public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
				rm.engine.runOnUpdateThread(new Runnable(){
					@Override
					public void run() {
						roarSprite.detachSelf();
						roarSprite.dispose();
						skillName.detachSelf();
						skillName.dispose();
					}});
				sm.getFightingScene().judge9EarthStab(curCard,targetCard);
			}
			},new ScaleModifier(1, 1, 1.5f),
			new DelayModifier(time)));

		sm.getFightingScene().attachChild(roarSprite);
		sm.getFightingScene().attachChild(skillName);
	}
	
	/**
	 * 恢复
	 */
	public void recover(final Card curCard){
		int change = (curCard.getAssistState() + 1) * 2;
		if (curCard.getHP() + change > curCard.getMaxHP())
			change = curCard.getMaxHP() - curCard.getHP();
		final int change2 = change;
		if (!rm.showSkillEffect){
			sm.getFightingScene().setHPText(curCard, curCard, curCard, change, new INextFunc(){
				@Override
				public void nextFunc(Card curCard, Card targetCard,
						INextFunc nextFunc2) {
					sm.getFightingScene().judge82Charge(curCard);
				}}, null);
			return;
		}
		float fromX = curCard.getX()+curCard.getWidth()/2;
		float fromY = curCard.getY()+curCard.getHeight()/2;

		final Text skillName =new Text(rm.camera.getWidth()/2,rm.camera.getHeight()/2,
				rm.font,"恢复",rm.vbo);

		final Sprite restore = new Sprite(fromX, fromY,
				restoreRegion,rm.vbo);

		restore.registerEntityModifier(new SequenceEntityModifier(new IEntityModifierListener() {

			@Override
			public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
			}

			@Override
			public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
				rm.engine.runOnUpdateThread(new Runnable(){
					@Override
					public void run() {
						restore.detachSelf();
						restore.dispose();
						skillName.detachSelf();
						skillName.dispose();
					}});
				sm.getFightingScene().setHPText(curCard, curCard, curCard, change2, new INextFunc(){
					@Override
					public void nextFunc(Card curCard, Card targetCard,
							INextFunc nextFunc2) {
						sm.getFightingScene().judge82Charge(curCard);
					}}, null);
			}
			},new ScaleModifier(0.5f,1f,1.5f),
			new RotationModifier(0.5f, 0, 360),
			new AlphaModifier(0.5f, 1, 0)));

		sm.getFightingScene().attachChild(restore);
		sm.getFightingScene().attachChild(skillName);
	}
	
	/**
	 * 充能
	 */
	public void charge(final Card curCard){
		final int change = (curCard.getAssistState() + 1) * 2;
		if (!rm.showSkillEffect){
			sm.getFightingScene().setHPText(curCard, curCard, curCard, change, new INextFunc(){
				@Override
				public void nextFunc(Card curCard, Card targetCard,
						INextFunc nextFunc2) {
					sm.getFightingScene().judgePoisonState(curCard);
				}}, null);
			return;
		}
		float fromX = curCard.getX()+curCard.getWidth()/2;
		float fromY = curCard.getY()+curCard.getHeight()/2;

		final Text skillName =new Text(rm.camera.getWidth()/2,rm.camera.getHeight()/2,
				rm.font,"充能",rm.vbo);

		final Sprite magicShield = new Sprite(fromX, fromY,
				chargeRegion,rm.vbo);

		magicShield.registerEntityModifier(new SequenceEntityModifier(new IEntityModifierListener() {

			@Override
			public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
			}

			@Override
			public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
				rm.engine.runOnUpdateThread(new Runnable(){
					@Override
					public void run() {
						magicShield.detachSelf();
						magicShield.dispose();
						skillName.detachSelf();
						skillName.dispose();
					}});
				sm.getFightingScene().setHPText(curCard, curCard, curCard, change, new INextFunc(){
					@Override
					public void nextFunc(Card curCard, Card targetCard,
							INextFunc nextFunc2) {
						sm.getFightingScene().judgePoisonState(curCard);
					}}, null);
			}
			},new ScaleModifier(0.5f,1f,1.5f),
			new AlphaModifier(0.5f, 1, 0),
			new DelayModifier(0.5f)));

		sm.getFightingScene().attachChild(magicShield);
		sm.getFightingScene().attachChild(skillName);
	}
	
	/**
	 * 地裂
	 */
	public void earthCrack(final Card curCard, final Card theCard){
		if (!rm.showSkillEffect){
			skill.continueEarthCrack2(curCard, theCard);
			return;
		}
		float fromX = theCard.getX();//+curCard.getWidth()/2
		float fromY = theCard.getY();//+curCard.getHeight()/2
		
		final Text skillName =new Text(rm.camera.getWidth()/2,rm.camera.getHeight()/2,
				rm.font,"地裂",rm.vbo);
		
		final Sprite magicShield = new Sprite(fromX, fromY,
				earthCrackRegion,rm.vbo);
		
		magicShield.registerEntityModifier(new SequenceEntityModifier(new IEntityModifierListener() {
			
			@Override
			public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
			}
			
			@Override
			public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
				rm.engine.runOnUpdateThread(new Runnable(){
					@Override
					public void run() {
						magicShield.detachSelf();
						magicShield.dispose();
						skillName.detachSelf();
						skillName.dispose();
					}});
				skill.continueEarthCrack2(curCard, theCard);
			}
		},new AlphaModifier(0.5f,0f,1f),
		new ScaleModifier(0.5f,1f,1.5f),
		new DelayModifier(0.25f)));
		
		sm.getFightingScene().attachChild(magicShield);
		sm.getFightingScene().attachChild(skillName);

	}
	
	/**
	 * 演奏
	 */
	public void perform(final Card curCard){
		int x = curCard.getAssistState();
		final Player curp = sm.getFightingScene().getCurrentPlayer();
		int change;
		if ((curp.getHP() + 20 + 10 * x) >= curp.getMaxHP()){
			change = curp.getMaxHP() - curp.getHP();
		}else
			change = 20 + 10 * x;
		final int change2 = change;
		if (!rm.showSkillEffect){
			curp.setHP(curp.getHP() + change);
			sm.getFightingScene().launchSkill(curCard);
			return;
		}
		float fromX = curCard.getX()+curCard.getWidth()/2;
		float fromY = curCard.getY()+curCard.getHeight()/2;
		
		final Text skillName =new Text(rm.camera.getWidth()/2,rm.camera.getHeight()/2,
				rm.font,"演奏",rm.vbo);
		
		final SpriteParticleSystem particleSystem = new SpriteParticleSystem(
				new PointParticleEmitter(fromX, fromY), 
				2, 2, 20, performRegion, rm.vbo);
		
		particleSystem.addParticleInitializer(new ColorParticleInitializer<Sprite>(0, 1, 0));
		particleSystem.addParticleInitializer(new AlphaParticleInitializer<Sprite>(0));
		//particleSystem.addParticleInitializer(new BlendFunctionParticleInitializer<Sprite>(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE));
		particleSystem.addParticleInitializer(new VelocityParticleInitializer<Sprite>(-150,150,-5,5));
		particleSystem.addParticleInitializer(new AccelerationParticleInitializer<Sprite>(-25,25,-5,5));
		particleSystem.addParticleInitializer(new RotationParticleInitializer<Sprite>(0.0f, 0.0f));
		particleSystem.addParticleInitializer(new ExpireParticleInitializer<Sprite>(2));

		particleSystem.addParticleModifier(new ScaleParticleModifier<Sprite>(0, 5, 1.0f, 2.0f));
		particleSystem.addParticleModifier(new AlphaParticleModifier<Sprite>(0, 1, 0, 1));
		particleSystem.addParticleModifier(new AlphaParticleModifier<Sprite>(5, 6, 1, 0));
		particleSystem.addParticleModifier(new ColorParticleModifier<Sprite>(0f, 1.0f, 0.0f, 0.5f, 1.0f, 0.0f, 0.0f, 0.5f));
		particleSystem.addParticleModifier(new ColorParticleModifier<Sprite>(1f, 2.0f, 0.5f, 0f, 0.0f, 0.5f, 0.5f, 1f));
		
		particleSystem.registerEntityModifier(new SequenceEntityModifier(new IEntityModifierListener() {
			@Override
			public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {}
			@Override
			public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
				rm.engine.runOnUpdateThread(new Runnable(){
					@Override
					public void run() {
						particleSystem.detachSelf();
						particleSystem.dispose();
						skillName.detachSelf();
						skillName.dispose();
					}});
				setPlayerHpText(curp, curCard, null, change2, new INextFunc(){
					@Override
					public void nextFunc(Card curCard, Card targetCard,
							INextFunc nextFunc2) {
						sm.getFightingScene().launchSkill(curCard);
					}});
			}
		},new DelayModifier(1.5f)) );
		
		sm.getFightingScene().attachChild(particleSystem);
		sm.getFightingScene().attachChild(skillName);
	}
	
	/**
	 * 看破
	 */
	public void seeThrough(final Player player, final Card curCard, final Card targetCard, final int change, final INextFunc nextFunc){
		if (!rm.showSkillEffect){
			player.setHP(player.getHP() + change);
			sm.getFightingScene().judge99SweepAway(curCard, targetCard);
			return;
		}
		//TODO 
		setPlayerHpText(player, curCard, targetCard, change, nextFunc);
	}
	
	/**
	 * 冰雨
	 * @param curCard
	 * @param theCard
	 */
	public void iceRain(final Card curCard, final int cut, final double probability){
		if (!rm.showSkillEffect){
			skill.continueIceRain(curCard, cut, probability);
			return;
		}
		final Text skillName = new Text(rm.camera.getWidth()/2 - 50,rm.camera.getHeight()/2,
				rm.font,"冰雨",rm.vbo);

		final SpriteParticleSystem particleSystem = new SpriteParticleSystem(new RectangleParticleEmitter(rm.camera.getWidth()/2, 0, rm.camera.getWidth(), 1), 30, 70, 300, mParticleRegion, rm.vbo);
			
		particleSystem.addParticleInitializer(new VelocityParticleInitializer<Sprite>(-15, -15, 220, 220));
		particleSystem.addParticleInitializer(new AccelerationParticleInitializer<Sprite>(0, 0));
		particleSystem.addParticleInitializer(new ColorParticleInitializer<Sprite>(0.45f,0.45f,0.45f));
			
		particleSystem.addParticleModifier(new ScaleParticleModifier<Sprite>(0.5f, 1.5f, 1, 0.25f));
		
		particleSystem.registerEntityModifier(new SequenceEntityModifier(new IEntityModifierListener() {
				@Override
				public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {}
				
				@Override
				public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
					rm.engine.runOnUpdateThread(new Runnable(){
						@Override
						public void run() {
							particleSystem.detachSelf();
							particleSystem.dispose();
							skillName.detachSelf();
							skillName.dispose();
						}});
					skill.continueIceRain(curCard, cut, probability);
				}
			},new ScaleModifier(1,1f,2.5f),
			new DelayModifier(1)));
			
		sm.getFightingScene().attachChild(particleSystem);
		sm.getFightingScene().attachChild(skillName);
	}
	
	/**
	 * 迷魂
	 */
	public void fascinate(final Card curCard, final Card theCard){
		if (!rm.showSkillEffect){
			skill.continueFascinate(curCard, theCard);
			return;
		}
		final Text skillName = new Text(rm.camera.getWidth()/2,rm.camera.getHeight()/2,
				rm.font,"迷魂",rm.vbo);
		
		RectangleParticleEmitter emitter = new RectangleParticleEmitter(rm.camera.getWidth(), rm.camera.getHeight()/2, 1, rm.camera.getHeight());

		final SpriteParticleSystem particleSystem = new SpriteParticleSystem(emitter, 20, 190, 360, 
				mParticleRegion, rm.vbo);

		particleSystem.addParticleInitializer(new ColorParticleInitializer<Sprite>(0.45f, 0.45f, 0.45f));
		particleSystem.addParticleInitializer(new AlphaParticleInitializer<Sprite>(0));
		particleSystem.addParticleInitializer(new BlendFunctionParticleInitializer<Sprite>(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE));
		particleSystem.addParticleInitializer(new VelocityParticleInitializer<Sprite>(-150, -115, -115, 5));
		particleSystem.addParticleInitializer(new RotationParticleInitializer<Sprite>(0, 360.0f));
		particleSystem.addParticleInitializer(new ExpireParticleInitializer<Sprite>(12));

		particleSystem.addParticleModifier(new ScaleParticleModifier<Sprite>(0, 1, 1.0f, 3.0f));
		particleSystem.addParticleModifier(new ColorParticleModifier<Sprite>(0, 2, 0, 0.45f, 0, 0.45f, 0, 0.45f));
		particleSystem.addParticleModifier(new ColorParticleModifier<Sprite>(3, 5, 0.45f, 0, 0.45f, 0, 0.45f, 0));
		particleSystem.addParticleModifier(new ColorParticleModifier<Sprite>(6, 9, 0, 0.45f, 0.45f, 0.45f, 0, 0.45f));
		particleSystem.addParticleModifier(new AlphaParticleModifier<Sprite>(0, 1, 0, 0.25f));
		particleSystem.addParticleModifier(new AlphaParticleModifier<Sprite>(2, 5, 0.25f, 0));
		particleSystem.addParticleModifier(new AlphaParticleModifier<Sprite>(6, 7, 0.25f, 0));
		particleSystem.addParticleModifier(new AlphaParticleModifier<Sprite>(8, 20, 0.25f, 0));

		particleSystem.registerEntityModifier(new SequenceEntityModifier(new IEntityModifierListener() {
			
			@Override
			public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
			}
			
			@Override
			public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
				rm.engine.runOnUpdateThread(new Runnable(){
					@Override
					public void run() {
						particleSystem.detachSelf();
						particleSystem.dispose();
						skillName.detachSelf();
						skillName.dispose();
					}});
				skill.continueFascinate(curCard, theCard);
			}
		},new ScaleModifier(1.5f,1f,2.5f),
		new DelayModifier(1)));
		
		sm.getFightingScene().attachChild(skillName);
		sm.getFightingScene().attachChild(particleSystem);
	}
	
	/**
	 * 魔盾（主动技能）
	 * @param curCard
	 */
	public void magicShield(final Card curCard){
		if (!rm.showSkillEffect){
			sm.getFightingScene().launchSkill(curCard);
			return;
		}
		float fromX = curCard.getX()+curCard.getWidth()/2;
		float fromY = curCard.getY()+curCard.getHeight()/2;
		final Text skillName =new Text(rm.camera.getWidth()/2,rm.camera.getHeight()/2,
		rm.font,"魔盾",rm.vbo);
		float time = 0.5f;
		final Sprite magicShield = new Sprite(fromX, fromY,
		magicShieldRegion,rm.vbo);

		magicShield.registerEntityModifier(new SequenceEntityModifier(new IEntityModifierListener() {
			@Override
			public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
			}
			
			@Override
			public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
				rm.engine.runOnUpdateThread(new Runnable(){
					@Override
					public void run() {
						magicShield.detachSelf();
						magicShield.dispose();
						skillName.detachSelf();
						skillName.dispose();
					}});
				sm.getFightingScene().launchSkill(curCard);
			}
			},new ScaleModifier(time,1f,1.5f),
			new AlphaModifier(time, 1, 0),
			new DelayModifier(time)));
		
		sm.getFightingScene().attachChild(skillName);
		sm.getFightingScene().attachChild(magicShield);
	}
	
	/**
	 * 冰箭
	 * @param curCard
	 * @param theCard
	 */
	public void iceArrow(final Card curCard, final Card theCard){
		if (!rm.showSkillEffect){
			skill.continueIceArrow(curCard, theCard);
			return;
		}
		float fromX = curCard.getX()+curCard.getWidth()/2;
		float fromY = curCard.getY()+curCard.getHeight()/2;
		float toX = theCard.getX() + theCard.getWidth()/2; 
		float toY = theCard.getY() + theCard.getHeight()/2;

		 final Text skillName = new Text(rm.camera.getWidth()/2,rm.camera.getHeight()/2,
		 rm.font,"冰箭",rm.vbo);
		
		float time = 0.5f;
		float velocityX = (toX - fromX) / time,
		velocityY = (toY - fromY) / time ;

		double dis = Math.sqrt((toX-fromX)*(toX-fromX)+(toY-fromY)*(toY-fromY));
		double rotation = Math.acos((fromY-toY)/dis);
		float rot = (float) Math.toDegrees(rotation);
		if (toX < fromX) rot = -rot;

		final SpriteParticleSystem particleSystem = new SpriteParticleSystem(
		new RectangleParticleEmitter(fromX, fromY, 1, 1), 
		1, 1, 1, iceArrowRegion, rm.vbo);

		particleSystem.addParticleInitializer(new VelocityParticleInitializer<Sprite>(velocityX, velocityY));
		particleSystem.addParticleInitializer(new AccelerationParticleInitializer<Sprite>(0, 0));
		particleSystem.addParticleInitializer(new ColorParticleInitializer<Sprite>(0.45f,0.45f,0.95f));
		particleSystem.addParticleInitializer(new RotationParticleInitializer<Sprite>(rot, rot));
		particleSystem.addParticleInitializer(new ExpireParticleInitializer<Sprite>(2f));

		particleSystem.addParticleModifier(new ScaleParticleModifier<Sprite>(time/2, time, 1, 1.5f));
		particleSystem.addParticleModifier(new AlphaParticleModifier<Sprite>(time/2,time, 1, 0));

		particleSystem.registerEntityModifier(new SequenceEntityModifier(new IEntityModifierListener() {
			@Override
			public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {}
			@Override
			public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
				rm.engine.runOnUpdateThread(new Runnable(){
					@Override
					public void run() {
						particleSystem.detachSelf();
						particleSystem.dispose();
						skillName.detachSelf();
						skillName.dispose();
					}});
				skill.continueIceArrow(curCard, theCard);
			}
			},new DelayModifier(2)) );

		sm.getFightingScene().attachChild(particleSystem);
		sm.getFightingScene().attachChild(skillName);
	}
	
	public void setPlayerHpText(final Player player, final Card curCard, final Card targetCard, final int change, final INextFunc nextFunc){
		float fromX = player.HPtext.getX();
		float fromY = player.HPtext.getY();
		float toX = fromX + player.HPtext.getWidth(); 
		float toY = fromY + player.HPtext.getHeight();
		float time = 0.3f;
		float velocityX = (toX - fromX) / time,
				velocityY = (toY - fromY) / time ;
		double dis = Math.sqrt((toX-fromX)*(toX-fromX)+(toY-fromY)*(toY-fromY));
		double rotation = Math.acos((fromY-toY)/dis);
		float rot = (float) Math.toDegrees(rotation);
		if (toX < fromX) rot = -rot;
		
		final SpriteParticleSystem particleSystem = new SpriteParticleSystem(
				new RectangleParticleEmitter(fromX, fromY, 1, 1), 
				100, 150, 201, mParticleRegion, rm.vbo);
		
		particleSystem.addParticleInitializer(new VelocityParticleInitializer<Sprite>(velocityX, velocityY));
		particleSystem.addParticleInitializer(new AccelerationParticleInitializer<Sprite>(-550, 0));
		if (change < 0)
			particleSystem.addParticleInitializer(new ColorParticleInitializer<Sprite>(1f,0.1f,0.1f));
		else
			particleSystem.addParticleInitializer(new ColorParticleInitializer<Sprite>(0.1f,1f,0.1f));
		particleSystem.addParticleInitializer(new RotationParticleInitializer<Sprite>(rot, rot));
		particleSystem.addParticleInitializer(new ExpireParticleInitializer<Sprite>(time));
		
		particleSystem.addParticleModifier(new ScaleParticleModifier<Sprite>(0,0, 0.5f, 0.5f));
//		particleSystem.addParticleModifier(new ScaleParticleModifier<Sprite>(time/2, time/4*3, 1, 0.5f));
		particleSystem.addParticleModifier(new ScaleParticleModifier<Sprite>(time/4*3, time, 0.5f, 0.21f));
		particleSystem.addParticleModifier(new AlphaParticleModifier<Sprite>(time/4*3,time, 1, 0));
		
		particleSystem.registerEntityModifier(new SequenceEntityModifier(new IEntityModifierListener() {
			@Override
			public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {}
			@Override
			public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
				rm.engine.runOnUpdateThread(new Runnable(){
					@Override
					public void run() {
						particleSystem.detachSelf();
						particleSystem.dispose();
					}});
				player.setHP(player.getHP() + change);
				nextFunc.nextFunc(curCard, targetCard, null);
			}
		},new DelayModifier(time)) );
		sm.getFightingScene().attachChild(particleSystem);
	}
	
	/**
	 * 射击
	 * @param curCard
	 * @param theCard
	 */
	public void shoot(final Card curCard, final Card theCard){
		if (!rm.showSkillEffect){
			skill.continueShoot2(curCard, theCard);
			return;
		}
		//TODO 显示效果
		skill.continueShoot2(curCard, theCard);
	}
	
	public void explode(final Card curCard, final Card targetCard, final INextFunc nextFunc){
		if (!rm.showSkillEffect){
			skill.explode(curCard, targetCard, nextFunc);
			return;
		}
		float fromX = curCard.getX()+curCard.getWidth()/2;
		float fromY = curCard.getY()+curCard.getHeight()/2;

		final Text skillName =new Text(rm.camera.getWidth()/2,rm.camera.getHeight()/2,
				rm.font,"爆炸",rm.vbo);

		final SpriteParticleSystem particleSystem = new SpriteParticleSystem(
		new PointParticleEmitter(fromX, fromY), 
		60, 60, 360, mParticleRegion, rm.vbo);

		particleSystem.addParticleInitializer(new ColorParticleInitializer<Sprite>(1, 0, 0));
		particleSystem.addParticleInitializer(new AlphaParticleInitializer<Sprite>(0));
		particleSystem.addParticleInitializer(new BlendFunctionParticleInitializer<Sprite>(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE));
		particleSystem.addParticleInitializer(new VelocityParticleInitializer<Sprite>(-100, 100, -100, 100));
		particleSystem.addParticleInitializer(new AccelerationParticleInitializer<Sprite>(-25,25,-25,25));
		particleSystem.addParticleInitializer(new RotationParticleInitializer<Sprite>(0.0f, 360.0f));
		particleSystem.addParticleInitializer(new ExpireParticleInitializer<Sprite>(2));

		particleSystem.addParticleModifier(new ScaleParticleModifier<Sprite>(0, 5, 1.0f, 2.0f));
		particleSystem.addParticleModifier(new ColorParticleModifier<Sprite>(0, 3, 1, 1, 0, 0.5f, 0, 0));
		particleSystem.addParticleModifier(new ColorParticleModifier<Sprite>(4, 6, 1, 1, 0.5f, 1, 0, 1));
		particleSystem.addParticleModifier(new AlphaParticleModifier<Sprite>(0, 1, 0, 1));
		particleSystem.addParticleModifier(new AlphaParticleModifier<Sprite>(5, 6, 1, 0));

		particleSystem.registerEntityModifier(new SequenceEntityModifier(new IEntityModifierListener() {
			@Override
			public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {}
			@Override
			public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
				rm.engine.runOnUpdateThread(new Runnable(){
					@Override
					public void run() {
						particleSystem.detachSelf();
						particleSystem.dispose();
						skillName.detachSelf();
						skillName.dispose();
					}});
				skill.explode(curCard, targetCard, nextFunc);
			}
			},new DelayModifier(3)) );

		sm.getFightingScene().attachChild(particleSystem);
		sm.getFightingScene().attachChild(skillName);
	}
	
	/***
	 * 普通攻击扣血效果
	 */
	public void cutHPEffect(final Card curCard, final Card targetCard, final int cut){
		if (!rm.showSkillEffect){
			sm.getFightingScene().setHPText(curCard, targetCard, targetCard, -cut, new INextFunc(){

				@Override
				public void nextFunc(Card curCard, Card targetCard,
						INextFunc nextFunc2) {
					sm.getFightingScene().afterCutCardHP(curCard, targetCard, new INextFunc(){

						@Override
						public void nextFunc(Card curCard, Card targetCard, INextFunc nextFunc2) {
							sm.getFightingScene().judge92Snarl(curCard, targetCard);
						}});
				}}, null);
			return;
		}
		
		float fromX = targetCard.getX();
		float fromY = targetCard.getY();
		float toX = fromX + targetCard.getWidth(); 
		float toY = fromY + targetCard.getHeight();
		float time = 0.3f;
		float velocityX = (toX - fromX) / time,
				velocityY = (toY - fromY) / time ;
		double dis = Math.sqrt((toX-fromX)*(toX-fromX)+(toY-fromY)*(toY-fromY));
		double rotation = Math.acos((fromY-toY)/dis);
		float rot = (float) Math.toDegrees(rotation);
		if (toX < fromX) rot = -rot;
		
		final SpriteParticleSystem particleSystem = new SpriteParticleSystem(
				new RectangleParticleEmitter(fromX, fromY, 1, 1), 
				100, 150, 201, mParticleRegion, rm.vbo);
		
		particleSystem.addParticleInitializer(new VelocityParticleInitializer<Sprite>(velocityX, velocityY));
		particleSystem.addParticleInitializer(new AccelerationParticleInitializer<Sprite>(0, 0));
		particleSystem.addParticleInitializer(new ColorParticleInitializer<Sprite>(1f,0.1f,0.1f));
		particleSystem.addParticleInitializer(new RotationParticleInitializer<Sprite>(rot, rot));
		particleSystem.addParticleInitializer(new ExpireParticleInitializer<Sprite>(time));
		
		particleSystem.addParticleModifier(new ScaleParticleModifier<Sprite>(0,0, 0.5f, 0.5f));
//		particleSystem.addParticleModifier(new ScaleParticleModifier<Sprite>(time/2, time/4*3, 1, 0.5f));
		particleSystem.addParticleModifier(new ScaleParticleModifier<Sprite>(time/4*3, time, 0.5f, 0.21f));
		particleSystem.addParticleModifier(new AlphaParticleModifier<Sprite>(time/4*3,time, 1, 0));
		
		particleSystem.registerEntityModifier(new SequenceEntityModifier(new IEntityModifierListener() {
			@Override
			public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {}
			@Override
			public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
				rm.engine.runOnUpdateThread(new Runnable(){

					@Override
					public void run() {
						particleSystem.detachSelf();
						particleSystem.dispose();
					}});
				sm.getFightingScene().setHPText(curCard, targetCard, targetCard, -cut, new INextFunc(){

					@Override
					public void nextFunc(Card curCard, Card targetCard,
							INextFunc nextFunc2) {
						sm.getFightingScene().afterCutCardHP(curCard, targetCard, new INextFunc(){

							@Override
							public void nextFunc(Card curCard, Card targetCard, INextFunc nextFunc2) {
								sm.getFightingScene().judge92Snarl(curCard, targetCard);
							}});
					}}, null);
			}
		},new DelayModifier(time)) );
		sm.getFightingScene().attachChild(particleSystem);
	}
	
	/***
	 * 地刺攻击扣血效果
	 */
	public void simplyCutHPEffect(final Card curCard, final Card targetCard, final int cut){
		if (!rm.showSkillEffect){
			sm.getFightingScene().setHPText(curCard, targetCard, targetCard, -cut, new INextFunc(){

				@Override
				public void nextFunc(Card curCard, Card targetCard,
						INextFunc nextFunc2) {
					sm.getFightingScene().afterSimplyCutCardHP(curCard, targetCard, targetCard, new INextFunc(){

						@Override
						public void nextFunc(Card curCard, Card targetCard, INextFunc nextFunc2) {
							sm.getFightingScene().judge92Snarl(curCard, targetCard);
						}}, nextFunc2);
				}}, null);
			
			return;
		}
		
		float fromX = targetCard.getX();
		float fromY = targetCard.getY();
		float toX = fromX + targetCard.getWidth(); 
		float toY = fromY + targetCard.getHeight();
		float time = 0.3f;
		float velocityX = (toX - fromX) / time,
				velocityY = (toY - fromY) / time ;
		double dis = Math.sqrt((toX-fromX)*(toX-fromX)+(toY-fromY)*(toY-fromY));
		double rotation = Math.acos((fromY-toY)/dis);
		float rot = (float) Math.toDegrees(rotation);
		if (toX < fromX) rot = -rot;
		
		final SpriteParticleSystem particleSystem = new SpriteParticleSystem(
				new RectangleParticleEmitter(fromX, fromY, 1, 1), 
				100, 150, 201, mParticleRegion, rm.vbo);
		
		particleSystem.addParticleInitializer(new VelocityParticleInitializer<Sprite>(velocityX, velocityY));
		particleSystem.addParticleInitializer(new AccelerationParticleInitializer<Sprite>(-550, 0));
		particleSystem.addParticleInitializer(new ColorParticleInitializer<Sprite>(1f,0.1f,0.1f));
		particleSystem.addParticleInitializer(new RotationParticleInitializer<Sprite>(rot, rot));
		particleSystem.addParticleInitializer(new ExpireParticleInitializer<Sprite>(time));
		
		particleSystem.addParticleModifier(new ScaleParticleModifier<Sprite>(0,0, 0.5f, 0.5f));
//		particleSystem.addParticleModifier(new ScaleParticleModifier<Sprite>(time/2, time/4*3, 1, 0.5f));
		particleSystem.addParticleModifier(new ScaleParticleModifier<Sprite>(time/4*3, time, 0.5f, 0.21f));
		particleSystem.addParticleModifier(new AlphaParticleModifier<Sprite>(time/4*3,time, 1, 0));
		
		particleSystem.registerEntityModifier(new SequenceEntityModifier(new IEntityModifierListener() {
			@Override
			public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {}
			@Override
			public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
				rm.engine.runOnUpdateThread(new Runnable(){

					@Override
					public void run() {
						particleSystem.detachSelf();
						particleSystem.dispose();
					}});
				sm.getFightingScene().setHPText(curCard, targetCard, targetCard, -cut, new INextFunc(){

					@Override
					public void nextFunc(Card curCard, Card targetCard,
							INextFunc nextFunc2) {
						sm.getFightingScene().afterSimplyCutCardHP(curCard, targetCard, targetCard, new INextFunc(){

							@Override
							public void nextFunc(Card curCard, Card targetCard, INextFunc nextFunc2) {
								sm.getFightingScene().judge92Snarl(curCard, targetCard);
							}}, nextFunc2);
					}}, null);
			}
		},new DelayModifier(time)) );
		sm.getFightingScene().attachChild(particleSystem);
	}
	
	public void magicImmune(final Card curCard, final Card theCard, final int cut, final INextFunc nextFunc){
		if (!rm.showSkillEffect){
			nextFunc.nextFunc(curCard, theCard, null);
			return;
		}
		//TODO 显示效果
		nextFunc.nextFunc(curCard, theCard, null);
	}
	
	/**
	 * 魔法反射效果
	 * @param curCard
	 * @param theCard
	 * @param cut
	 * @param nextFunc
	 */
	public void reflection(final Card curCard, final Card theCard, final int cut, final INextFunc nextFunc){
		if (curCard == null){
			nextFunc.nextFunc(curCard, null, null);
			return;
		}
		if (!rm.showSkillEffect){
			sm.getFightingScene().setHPText(curCard, null, curCard, -cut, new INextFunc(){
				@Override
				public void nextFunc(Card curCard, Card targetCard,
						INextFunc nextFunc2) {
					sm.getFightingScene().afterSimplyCutCardHP(curCard, null, curCard, nextFunc2, null);
				}}, nextFunc);
			return;
		}
		float fromX = theCard.getX()+theCard.getWidth()/2;
		float fromY = theCard.getY()+theCard.getHeight()/2;
		final Text skillName =new Text(rm.camera.getWidth()/2,rm.camera.getHeight()/2,
		rm.font,"反射",rm.vbo);
		float time = 0.5f;
		final Sprite magicShield = new Sprite(fromX, fromY,
		magicShieldRegion,rm.vbo);

		magicShield.registerEntityModifier(new SequenceEntityModifier(new IEntityModifierListener() {
			@Override
			public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
			}
			
			@Override
			public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
				rm.engine.runOnUpdateThread(new Runnable(){
					@Override
					public void run() {
						magicShield.detachSelf();
						magicShield.dispose();
						skillName.detachSelf();
						skillName.dispose();
					}});
				sm.getFightingScene().setHPText(curCard, null, curCard, -cut, new INextFunc(){
					@Override
					public void nextFunc(Card curCard, Card targetCard,
							INextFunc nextFunc2) {
						sm.getFightingScene().afterSimplyCutCardHP(curCard, null, curCard, nextFunc2, null);
					}}, nextFunc);
			}
			},new ScaleModifier(time,1f,1.5f),
			new AlphaModifier(time, 1, 0),
			new DelayModifier(time)));
		
		sm.getFightingScene().attachChild(skillName);
		sm.getFightingScene().attachChild(magicShield);
	}
	
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * 构造函数
	 */
	private SkillEffect(){
		rm = ResourceManager.getInstance(); 
		sm = SceneManager.getInstance();
		skill = Skill.getInstance();
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/skillEffect/");
		particleTextureAtlas = new BitmapTextureAtlas(rm.textureManager,613,243);
		mParticleRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(particleTextureAtlas, rm.activity,
						"particle_point.png", 0, 0);
		magicShieldRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(particleTextureAtlas, rm.activity,
						"dun.png", 0, 32);
		
		iceArrowRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(particleTextureAtlas, rm.activity,
						"iceArrow.png", 32, 0);
		
		chargeRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(particleTextureAtlas, rm.activity,
						"charge.png", 36, 0);
		restoreRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(particleTextureAtlas, rm.activity,
						"restore.png", 36, 24);
		roarRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(particleTextureAtlas, rm.activity,
						"roar.png", 84, 0);
		performRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(particleTextureAtlas, rm.activity,
						"perform.png", 0, 67);
		
		earthCrackRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(particleTextureAtlas, rm.activity,
						"earthCrack.png", 142, 0);
		
		radiateRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(particleTextureAtlas, rm.activity,
						"radiate.png", 123, 0);
		
		polluteRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(particleTextureAtlas, rm.activity,
						"pollute.png", 30, 72);
		
		metalRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(particleTextureAtlas, rm.activity,
						"metal.png", 222, 0);
						
//		punchRegion = BitmapTextureAtlasTextureRegionFactory
//				.createFromAsset(particleTextureAtlas, rm.activity,
//						"punch.png", 0, 117);

		singRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(particleTextureAtlas, rm.activity,
						"sing.png", 60, 117);
						
		tradeRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(particleTextureAtlas, rm.activity,
						"trade.png", 132, 117);
						
		coercionRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(particleTextureAtlas, rm.activity,
						"coercion.png", 222, 117);

		strongRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(particleTextureAtlas, rm.activity,
						"strong.png", 302, 117);

		flameStrikeRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(particleTextureAtlas, rm.activity,
						"flameStrike.png", 282, 0);

		shelterRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(particleTextureAtlas, rm.activity,
						"shelter.png", 282, 24);

		healRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(particleTextureAtlas, rm.activity,
						"heal.png", 342, 0);

		evolveRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(particleTextureAtlas, rm.activity,
						"evolve.png", 342, 54);

		callZombieRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(particleTextureAtlas, rm.activity,
						"callZombie.png", 545, 117);

		imaginationRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(particleTextureAtlas, rm.activity,
						"imagination.png", 370, 117);

		electricShockRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(particleTextureAtlas, rm.activity,
						"electricShock.png", 387, 0);

		reviveRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(particleTextureAtlas, rm.activity,
						"revive.png", 425, 0);

		massesPowerRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(particleTextureAtlas, rm.activity,
						"massesPower.png", 394, 117);

		commandmentRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(particleTextureAtlas, rm.activity,
						"commandment.png", 485, 0);

		releaseEnergyRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(particleTextureAtlas, rm.activity,
						"releaseEnergy.png", 392, 73);

		assassinateRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(particleTextureAtlas, rm.activity,
						"assassinate.png", 464, 117);		

		lurkRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(particleTextureAtlas, rm.activity,
						"lurk.png", 485, 60);		

		cureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(particleTextureAtlas, rm.activity,
						"cure.png", 302, 185);		

		preventRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(particleTextureAtlas, rm.activity,
						"prevent.png", 394, 187);		

		/*antiPolluteRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(particleTextureAtlas, rm.activity,
						"antiPollute.png", 0, 117);	*/	
					
		particleTextureAtlas.load();
	}
	
	public static SkillEffect getInstance(){
		return INSTANCE;
	}
}