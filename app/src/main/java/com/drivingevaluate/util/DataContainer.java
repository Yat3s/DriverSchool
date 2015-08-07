package com.drivingevaluate.util;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.nostra13.universalimageloader.core.ImageLoader;

public class DataContainer
{
  private static Map<String, Activity> allActivity = new HashMap();
  private static HashMap<Integer, BitmapDrawable> allMerchantIcon = new HashMap();
  private static HashMap<Integer, BitmapDrawable> alluserIcon = new HashMap();
  public static int lastActivityId;

  public static void addActivity(Activity paramActivity)
  {
    allActivity.put(paramActivity.getClass().getSimpleName(), paramActivity);
  }

  public static Map getActivities()
  {
    return allActivity;
  }

  public static Activity getActivityByName(String paramString)
  {
    return (Activity)allActivity.get(paramString);
  }
  
  public static void finishAllActivity() {
}

  public static Activity getOneActivity()
  {
    Iterator localIterator = allActivity.keySet().iterator();
    if (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      Activity localActivity = (Activity)allActivity.get(str);
      if (localActivity == null)
      {
        allActivity.remove(str);
        return getOneActivity();
      }
      return localActivity;
    }
    return null;
  }

  public static void removeActivity(Activity paramActivity)
  {
    allActivity.remove(paramActivity.getClass().getSimpleName());
  }
}