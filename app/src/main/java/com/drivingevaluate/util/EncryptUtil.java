package com.drivingevaluate.util;
import android.util.Base64;
public class EncryptUtil {
	public static String encode(String toEncodeContent) {
		if (toEncodeContent == null) {
			return null;
		}
		return new String(Base64.encode(toEncodeContent.getBytes(),Base64.DEFAULT));
	}
	public static String encode(byte[] toEncodeContent) {
		return encode(new String(toEncodeContent));
	}
	public static String decode(String toDecodeContent) {
		if (toDecodeContent == null) {
			return null;
		}
		byte[] buf = null;
		try {
			buf=Base64.encode(toDecodeContent.getBytes(),Base64.DEFAULT);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
		return new String(buf);
	}
}