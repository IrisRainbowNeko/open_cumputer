package com.myltrjcs.test;

import android.app.*;
import android.content.*;
import android.net.wifi.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import java.io.*;
import java.net.*;
import android.util.*;
import java.util.*;
import java.text.*;
import android.net.*;

public class MainActivity extends Activity
{
	public static SharedPreferences fs;
    public int PORT=9959;
	public String ip="193.112.246.171";
	TextView tx;
	EditText ed_ip;
	
    @Override
    public void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		fs=this.getSharedPreferences("info",Context.MODE_PRIVATE);
		tx=(TextView)findViewById(R.id.mainTextView1);
		ed_ip=(EditText)findViewById(R.id.mainEditText_ip);
		
		ip=fs.getString("ip","192.168.0.1");
		PORT=fs.getInt("port",9959);
		ed_ip.setText(ip+":"+PORT);
    }
	
	public void onip(View v){
		String[] strs=ed_ip.getText().toString().split(":");
		ip=strs[0];
		PORT=Integer.parseInt(strs[1]);
		fs.edit().putString("ip",ip).commit();
		fs.edit().putInt("port",PORT).commit();
	}
	public void onsend(View v){
		new Thread(){
			@Override
			public void run()
			{
				String cmd="open";
				try{
					Socket socket=new Socket(ip,PORT);
					OutputStream out=socket.getOutputStream();
					out.write(cmd.getBytes("UTF-8"));
					out.flush();
					
					byte[] bys=new byte[4];
					socket.getInputStream().read(bys);
					if(new String(bys,"UTF-8").equals("opok")){
						handler.obtainMessage(0,"开机成功").sendToTarget();
					}
				}catch (Exception e){
					handler.obtainMessage(1,"错误:"+e.toString()).sendToTarget();
				}
			}
		}.start();
		Toast.makeText(MainActivity.this,"send",2000).show();
	}
	Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg){
			if(msg.what==0)
				tx.append((String)msg.obj+"\n");
			else if(msg.what==1)
				Toast.makeText(MainActivity.this,(String)msg.obj,2000).show();
		}
	};
	
}
