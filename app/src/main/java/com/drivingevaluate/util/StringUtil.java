package com.drivingevaluate.util;


public class StringUtil {
	
	public static String getPhoneStrWithStar(String str) {
		StringBuilder sb=new StringBuilder();
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (i >= 3 && i< 7) {
				sb.append("*");
			}
			else {
				sb.append(c);
			}
		}
		return sb.toString();
	}
}
