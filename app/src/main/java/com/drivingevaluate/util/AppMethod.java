package com.drivingevaluate.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import android.os.Handler;
import android.os.Message;


public class AppMethod {
	
	private static final String USER_ID_TAG = "user_id_tag";
	private static final String USER_ACCOUNT_TAG = "user_account_tag";

	private static SharedPreferencesHelper sph;
	
	public static SharedPreferencesHelper getSharedPreferencesHelper(){
		if(sph==null){
			sph = new SharedPreferencesHelper(DataContainer.getOneActivity());
		}
		return sph;
	}
	
	public static void sendMessage(Handler handler,Object obj,int what){
		Message msg=Message.obtain();
		msg.obj=obj;
		msg.what=what;
		handler.sendMessage(msg);
	}
	
	public static void sendEmptyMessage(Handler handler,int what){
		Message msg=Message.obtain();
		msg.what=what;
		handler.sendMessage(msg);
	}
	
	public static Integer getCurUserId(){
		return getSharedPreferencesHelper().getInt(USER_ID_TAG);
	}
	
	public static void setCurUserId(Integer uid){
		if(uid!=null)
		getSharedPreferencesHelper().putInt(USER_ID_TAG, uid);
	}
	
	public static String getCurUserAccount(){
		return getSharedPreferencesHelper().getString(USER_ACCOUNT_TAG);
	}
	
	public static void setCurUserAccount(String account){
		if(account!=null)
		getSharedPreferencesHelper().putString(USER_ACCOUNT_TAG, account);
	}
	
	public static void addToSharedPreference(String key,Object value){
		ByteArrayOutputStream toByte = null;
		ObjectOutputStream oos = null;
		try {
			//将obj转换为byte[]   
			toByte = new ByteArrayOutputStream();  
			oos = new ObjectOutputStream(toByte);  
			oos.writeObject(value); 
			//对byte[]进行Base64编码   
			String payCityMapBase64 = new String(Base64Coder.encode(toByte.toByteArray()));  
			// 存储   
			getSharedPreferencesHelper().putString(key, payCityMapBase64);
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			try {
				if(oos!=null)oos.close();
				if(toByte!=null)toByte.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	 
	public static Object getObject(String key){
		ByteArrayInputStream bais = null;
		ObjectInputStream ois = null;
		try {
			byte[] base64Bytes = Base64Coder.decode(getSharedPreferencesHelper().getString(key));  
			bais = new ByteArrayInputStream(base64Bytes);  
			ois = new ObjectInputStream(bais);  
			return ois.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			try {
				if(ois!=null)ois.close();
				if(bais!=null)bais.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public static void Loginout(){
		getSharedPreferencesHelper().clear();
	}
}
