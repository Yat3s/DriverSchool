package com.drivingevaluate.util;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
public class UrlResolveUtil {
	public static StringBuilder generateParamentersString(StringBuilder sb,String apiPath,Map<String, String> map){
	   Entry<String, String> entry = null;
	   for(Iterator<Entry<String, String>> iter=map.entrySet().iterator();iter.hasNext();){
			entry=iter.next();
			if(entry.getKey()!=null && entry.getValue()!=null)
			sb.append(entry.getKey()).append("=").append(entry.getValue().toString()).append("&");
		}
		return sb;
	}
	public static String buildFullPath(String apiPath,Map<String, String> map) throws IOException{
		StringBuilder sb = new StringBuilder();
		sb.append(apiPath).append("?");
		return generateParamentersString(sb,apiPath,map).toString();
	}
}
