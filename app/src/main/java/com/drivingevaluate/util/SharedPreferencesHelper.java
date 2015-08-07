package com.drivingevaluate.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedPreferencesHelper
{
  private static final String TAG = "SharedPreferencesHelper";
  private Editor editor;
  private SharedPreferences prefs;

  public SharedPreferencesHelper(Context paramContext)
  {
    if (paramContext != null)
    {
      this.prefs = paramContext.getSharedPreferences("SharedPreferencesHelper", 0);
      this.editor = this.prefs.edit();
    }
  }

  public boolean clear()
  {
    this.editor.clear();
    return this.editor.commit();
  }

  public void close()
  {
    this.prefs = null;
  }

  public Integer getInt(String paramString)
  {
    if (paramString == null)
      return null;
    return Integer.valueOf(this.prefs.getInt(paramString, -1));
  }

  public String getString(String paramString)
  {
    return this.prefs.getString(paramString, null);
  }

  public boolean putInt(String paramString, int paramInt)
  {
    this.editor.putInt(paramString, paramInt);
    return this.editor.commit();
  }

  public boolean putString(String paramString1, String paramString2)
  {
    this.editor.putString(paramString1, paramString2);
    return this.editor.commit();
  }
  
  public boolean IsLogin() {
	  return prefs.getBoolean("isLogin",false);
  }
  public void SetLogin() {
	  editor.putBoolean("isLogin", true).commit();
  }
}