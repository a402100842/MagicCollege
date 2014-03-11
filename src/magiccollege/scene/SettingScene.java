package magiccollege.scene;


import magiccollege.Enum.ESceneID;
import magiccollege.manager.ResourceManager;
import magiccollege.manager.SceneManager;

import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;

public class SettingScene extends MyScene{
	
	 
	
	ResourceManager rm = ResourceManager.getInstance();
	
	public boolean isVoice ;
	public boolean isSound ;
	public boolean isAutofight ;
	public boolean isParticle ;
	public boolean showSkillEffect;
	
//	private MyData myData = MyData.getInstance();
	int []setting = new int[5];
	
	private Sprite v_on;
	private Sprite v_off;
	private Sprite s_on;
	private Sprite s_off;
	private Sprite auto_on;
	private Sprite auto_off;
	private Sprite p_on;
	private Sprite p_off;
	private Sprite skill_on;
	private Sprite skill_off;

	@Override
	public void createScene() {
		// TODO Auto-generated method stub
		
		Sprite backGround = new Sprite(0, 0, rm.setting_background_region, rm.vbo);
		
		v_on = new Sprite(400,100,rm.on_region,rm.vbo){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				
				if(pSceneTouchEvent.isActionDown()){
					
					isVoice = true;
					
					v_on.setColor(0, 0, 1);
					v_off.setColor(1,0,0);
					return true;
				}
				 return false;
			}};
		v_off = new Sprite(500,100,rm.off_region,rm.vbo){
				@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
					
					if(pSceneTouchEvent.isActionDown()){
						
						isVoice = false;
						
						v_off.setColor(0, 0, 1);
						v_on.setColor(1,0,0);
						return true;
					}
					 return false;
		 }};
		s_on = new Sprite(400,170,rm.on_region,rm.vbo){
				@Override
				public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
					
					if(pSceneTouchEvent.isActionDown()){
						
						isSound = true;
						
						s_on.setColor(0, 0, 1);
						s_off.setColor(1,0,0);
						return true;
					}
					 return false;
				}};
		s_off = new Sprite(500,170,rm.off_region,rm.vbo){
					@Override
				public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
						
						if(pSceneTouchEvent.isActionDown()){
							
							isSound = false;
							
							s_off.setColor(0, 0, 1);
							s_on.setColor(1,0,0);
							return true;
						}
						 return false;
			 }};
		 
		
		 
		auto_on = new Sprite(400,240,rm.on_region,rm.vbo){
				@Override
				public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
					
					if(pSceneTouchEvent.isActionDown()){
						
						isAutofight = true;
						
						auto_on.setColor(0, 0, 1);
						auto_off.setColor(1,0,0);
						return true;
					}
					 return false;
				}};
		auto_off = new Sprite(500,240,rm.off_region,rm.vbo){
					@Override
				public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
						
						if(pSceneTouchEvent.isActionDown()){
							
							isAutofight = false;
							
							auto_off.setColor(0, 0, 1);
							auto_on.setColor(1,0,0);
							return true;
						}
						 return false;
			 }};
			 p_on = new Sprite(400,310,rm.on_region,rm.vbo){
					@Override
					public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
						
						if(pSceneTouchEvent.isActionDown()){
							
							isParticle = true;
							
							p_on.setColor(0, 0, 1);
							p_off.setColor(1,0,0);
							return true;
						}
						 return false;
					}};
			p_off = new Sprite(500,310,rm.off_region,rm.vbo){
						@Override
					public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
							
							if(pSceneTouchEvent.isActionDown()){
								
								isParticle = false;
								
								p_off.setColor(0, 0, 1);
								p_on.setColor(1,0,0);
								return true;
							}
							 return false;
				 }};
				 
				 skill_on = new Sprite(400,380,rm.on_region,rm.vbo){
						@Override
						public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
							
							if(pSceneTouchEvent.isActionDown()){
								
								showSkillEffect = true;
								
								skill_on.setColor(0, 0, 1);
								skill_off.setColor(1,0,0);
								return true;
							}
							 return false;
						}};
				skill_off = new Sprite(500,380,rm.off_region,rm.vbo){
							@Override
						public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
								
								if(pSceneTouchEvent.isActionDown()){
									
									showSkillEffect = false;
									
									skill_off.setColor(0, 0, 1);
									skill_on.setColor(1,0,0);
									return true;
								}
								 return false;
					 }};
			 
			 Sprite back = new Sprite(690,365,rm.options_back_region,rm.vbo){
					@Override
				public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
						
						if(pSceneTouchEvent.isActionDown()){
							onBackKeyPressed();
							return true;
						}
						 return false;
			 }};
			 reset();
		 
	 
		 
	  attachChild(backGround);
	  attachChild(v_off);
	  attachChild(v_on);
	  attachChild(s_off);
	  attachChild(s_on);
	  attachChild(auto_off);
	  attachChild(auto_on);
	  attachChild(p_off);
	  attachChild(p_on);
	  attachChild(skill_off);
	  attachChild(skill_on);
      attachChild(back);
			
	  registerTouchArea(v_off);
      registerTouchArea(v_on);
      registerTouchArea(s_off);
      registerTouchArea(s_on);
      registerTouchArea(auto_off);
      registerTouchArea(auto_on);
      registerTouchArea(p_off);
      registerTouchArea(p_on);
      registerTouchArea(skill_off);
      registerTouchArea(skill_on);
      
	 registerTouchArea(back);
	 
		
	}

	@Override
	public void onBackKeyPressed() {
		
		rm.unloadSettingTexture();
		SceneManager.getInstance().setScene(ESceneID.options);
	}

	@Override
	public ESceneID getSceneID() {
		return ESceneID.setting;
	}

	@Override
	public void disposeScene() {
		// TODO Auto-generated method stub
		
	}
	
	
	
	public void reset(){
		
		
		if(isVoice){
			v_on.setColor(0, 0, 1);
			v_off.setColor(1,0,0);
		}
		else{
			v_off.setColor(0, 0, 1);
			v_on.setColor(1,0,0);
		}
		if(isSound){
			s_on.setColor(0, 0, 1);
			s_off.setColor(1,0,0);
		}
		else{
			s_off.setColor(0, 0, 1);
			s_on.setColor(1,0,0);
		}
		if(isAutofight){
			auto_on.setColor(0, 0, 1);
			auto_off.setColor(1,0,0);
		}
		else{
			auto_off.setColor(0, 0, 1);
			auto_on.setColor(1,0,0);
		}
		if(isParticle){
			p_on.setColor(0, 0, 1);
			p_off.setColor(1,0,0);
		}
		else{
			p_off.setColor(0, 0, 1);
			p_on.setColor(1,0,0);
		}
		if(showSkillEffect){
			skill_on.setColor(0, 0, 1);
			skill_off.setColor(1,0,0);
		}
		else{
			skill_off.setColor(0, 0, 1);
			skill_on.setColor(1,0,0);
		}
			
	}
	
	public boolean isVoice(){
		return isVoice;
	}
	
	public boolean isSound(){
		return isSound;
	}
	
	public boolean isAutofight(){
		return isAutofight;
	}
	public boolean isParticle(){
		return isParticle;
	}
	
	public boolean showSkillEffect(){
		return showSkillEffect;
	}

}
