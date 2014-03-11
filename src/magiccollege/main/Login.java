package magiccollege.main;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import magiccollege.net.GameConstants;

import org.apache.http.util.EncodingUtils;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends Activity implements GameConstants{
	
	 /** Called when the activity is first created. */
	final String filename = "data/data/magiccollege.main/user.txt";
    final String tag="I/O";
   
	private Thread thread = null;
	private Socket socket = null;
	private BufferedReader br = null;
	private PrintWriter pw = null;
	private String msg;
	private String sendMsg;
	
	private String name;
	private String password;
	
	private EditText edit1;
	private EditText edit2;
	private Button btn1;
	private Button btn2;
	
	private MyData myData = MyData.getInstance();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		initStrictMode();
		
		edit1 = (EditText)findViewById(R.id.edit1);
		edit2 = (EditText)findViewById(R.id.edit2);
		btn1 = (Button)findViewById(R.id.btn1);
		btn2 = (Button)findViewById(R.id.btn2);
		
		ButtonListener buttonListener = new ButtonListener();
		btn1.setOnClickListener(buttonListener);
		btn2.setOnClickListener(buttonListener);
		
		readUser();
		try {
			socket = new Socket(DB_SERVER_IP, DB_SERVER_PORT);
			//初始化输入流, 采用 UTF-8字符集
			br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
			pw = new PrintWriter(socket.getOutputStream(), true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		thread = new Thread(runnable);
		thread.start();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}
	class ButtonListener implements OnClickListener{

		@Override
		public void onClick(View v) {		
			int id = v.getId();
			getStringFromText();
			if(checkNetWork()){
				if(id==R.id.btn1){
					if(name.length()==0||password.length()==0){
						Toast.makeText(Login.this, "账号和密码不能为空",
						       Toast.LENGTH_SHORT).show();
					}else{
						sendMsg = "L:"+name+":"+password;
						pw.println(sendMsg);
						pw.flush();
						
					}
					
					
				}else{
				    if(name.length()==0||password.length()==0){
					  Toast.makeText(Login.this, "账号和密码不能为空",
						       Toast.LENGTH_SHORT).show();
				    }else{
				    	sendMsg = "R:"+name+":"+password;
						pw.println(sendMsg);
						pw.flush();	
				    }
						
					
					    
				}
			}else{
				Toast.makeText(Login.this, "无网络连接，请检查网络设置",
					       Toast.LENGTH_SHORT).show();	
			}
				
			
		}
	}
	 @SuppressWarnings("deprecation")
	@Override  
	    public boolean onKeyDown(int keyCode, KeyEvent event)  
	    {  
	        if (keyCode == KeyEvent.KEYCODE_BACK )  
	        {  
	            // 创建退出对话框  
	            AlertDialog isExit = new AlertDialog.Builder(this).create();  
	            // 设置对话框标题  
	            isExit.setTitle("系统提示");  
	            // 设置对话框消息  
	            isExit.setMessage("确定要退出吗");  
	            // 添加选择按钮并注册监听  
	            isExit.setButton("确定", listener);  
	            isExit.setButton2("取消", listener);  
	            // 显示对话框  
	            isExit.show();  
	  
	        }  
	          
	        return false;  
	          
	    }  
	    /**监听对话框里面的button点击事件*/  
	    DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener()  
	    {  
	        public void onClick(DialogInterface dialog, int which)  
	        {  
	            switch (which)  
	            {  
	            case AlertDialog.BUTTON_POSITIVE:// "确认"按钮退出程序  
	            	 
	            	try {
						socket.close();
						br.close();
						pw.close();
						socket = null;
						br = null;
						pw = null;
					} catch (IOException e) {
						// TODO Auto-generated catch block
						//e.printStackTrace();
						 Log.e(tag, "error while close socket!");
					}
	            	finish();
	                break;  
	            case AlertDialog.BUTTON_NEGATIVE:// "取消"第二个按钮取消对话框  
	                break;  
	            default:  
	                break;  
	            }  
	        }  
	    };    
	 
    	
    
	private void  getStringFromText(){	
		name = edit1.getText().toString();
		password = edit2.getText().toString();
		
		
	}
	
	///////////记住密码功能 用文件的i/o实现
	 public void writeUser(){
	        try{
	        File file = new File(filename);
	        file.createNewFile();
	        Log.d(tag, "create flie!");
	        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file));
	        bufferedOutputStream.write((edit1.getText().toString()+":"+edit2.getText().toString()).getBytes());
	        bufferedOutputStream.close();
	        }catch (Exception e) {
	            Log.e(tag, "error in write");
	        }
	    }
	 public void readUser(){
	        
	        try {
	            File file = new File(filename);
	            BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
	            byte[] data = new byte[(int) file.length()];
	            bufferedInputStream.read(data);
	            String str = EncodingUtils.getString(data, "utf-8");
	            String[] strs = str.split("[:]");
	            bufferedInputStream.close();
	            edit1.setText(strs[0]);//
	            edit2.setText(strs[1]);//
	        } catch (Exception e) {
	    
	            Log.e(tag, "error in read,file is null now!");
	        }        
	    }    
	        //是否联网网络   
	  public  boolean checkNetWork() {  
	            try {  
	            	ConnectivityManager cManager=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);  
	      
	                NetworkInfo info =cManager.getActiveNetworkInfo();  
	                return (info!=null && info.isConnected());  
	             } catch (Exception e) {  
	                return false;  
	            }   
	    }  
	  @SuppressLint("NewApi")
	private void initStrictMode() {
		// 判断操作系统是Android版本3.0以上版本
		if(android.os.Build.VERSION.SDK_INT >= 11) {
			StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
		    .detectDiskReads()
		    .detectDiskWrites()
		    .detectNetwork()   // or .detectAll() for all detectable problems
		    .penaltyLog()
		    .build());
		    StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
		    .detectLeakedSqlLiteObjects()
//		    .detectLeakedClosableObjects()
		    .penaltyLog()
		    .penaltyDeath()
		    .build());
		
		}
	 }
	  
	  private Runnable runnable = new Runnable() {
			public void run() {
				msg = "";
				while (true) {
					//终止线程
					if (socket == null){
						break;
					}
					
					if(br == null){
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					else{
						try {
							msg = br.readLine();
							handler.sendMessage(handler.obtainMessage());
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}		
				}
			}
	  };
		Handler handler = new Handler() {
			public void handleMessage(Message msg1) {
				super.handleMessage(msg1);
				try {
					String s[] =msg.split(":");
					if(s.length==3){
						if(s[0].equals("L")){
							if(s[1].equals("1")){
								writeUser();
								//flag = true;
								socket.close();
								br.close();
								pw.close();
								socket = null;
								br = null;
								pw = null;
								myData.setId(Integer.parseInt(s[2]));
								myData.setMyName(name);
								Intent intent = new Intent();
								intent.setClass(Login.this, MainGame.class);
								Login.this.startActivity(intent);
							}else{
								Toast.makeText(Login.this, "用户名与密码不匹配",
										Toast.LENGTH_SHORT).show();
							}
							
						}else{
							if(s[1].equals("1")){
								Toast.makeText(Login.this, "注册成功",
									       Toast.LENGTH_SHORT).show();
							}
							else{
								Toast.makeText(Login.this, "该用户已存在",
										Toast.LENGTH_SHORT).show();
							}
							
						}
						
					}
//					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};

}
