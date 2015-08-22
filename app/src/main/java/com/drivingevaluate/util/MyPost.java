package com.drivingevaluate.util;

import android.util.Log;

import com.drivingevaluate.config.AppConf;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 我的post请求方式工具类
 *
 * </BR> </BR> 
 * By：苦涩 </BR> 
 * 联系作者：QQ 534429149
 * */

public class MyPost {

	public static String doPost(String url,Map<String,Object> params) throws Exception{
		if(params==null) return null;
		String result = null;
		HttpResponse httpResponse = null;
		HttpPost post = new HttpPost(url);
		post.addHeader("token", AppConf.TOKEN);
		DefaultHttpClient client = new DefaultHttpClient();
		client.getParams().setIntParameter(HttpConnectionParams.SO_TIMEOUT,	20000); // 超时设置
		client.getParams().setIntParameter(HttpConnectionParams.CONNECTION_TIMEOUT, 10000);// 连接超时
		client.getParams().setBooleanParameter(CoreConnectionPNames.STALE_CONNECTION_CHECK, true);
		ArrayList<NameValuePair> nameValuePairs = null;

		if(params !=null && params.size()>0){
			nameValuePairs = new ArrayList<NameValuePair>(params.size());
			Entry<String,Object> entry = null;
			for(Iterator<Entry<String,Object>> iter = params.entrySet().iterator();iter.hasNext();){
				entry = iter.next();
				nameValuePairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
			}
			post.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
		}
		httpResponse = client.execute(post);
		Log.e("HTTP", "CODE" + httpResponse.getStatusLine().getStatusCode());
		if (httpResponse.getStatusLine().getStatusCode() == 200) {
			result = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
			return result;
		}
		return result;
	}

}

