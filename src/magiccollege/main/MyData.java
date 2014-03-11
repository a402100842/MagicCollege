package magiccollege.main;

import android.app.Application;

public  class  MyData  extends  Application{
	
	private static final MyData INSTANCE = new MyData();

    private String name="";
    
    private int Id;
    
    public static MyData getInstance(){
		return INSTANCE;
	}
    
    public int getId(){
    	
    	return Id;
    }
    
    public void setId(int id){
    	
    	Id = id;
    }
    
   public String getMyName() {

	   return name;

   }

   public void setMyName(String myName) {

	   this.name = myName;
   }

}