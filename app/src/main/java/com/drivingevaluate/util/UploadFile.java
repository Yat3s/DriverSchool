package com.drivingevaluate.util;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.drivingevaluate.config.Config;

import android.util.Log;

public class UploadFile {
	public static String s;
	public static String uploadUrl = "http://121.43.234.220:8090/TrainSocial/upload/uploadFile.jsp";
	public String defaultUploadMethod(byte[] data, String filename) throws IOException {
		String end = "\r\n";
		String twoHyphens = "--";
		String boundary = "*****";
		HttpURLConnection con = null;
		URL url;
		try {
			url = new URL(Config.imgUploadUrl1);
			con = (HttpURLConnection) url.openConnection();
			con.setDoInput(true);
			con.setDoOutput(true);
			con.setUseCaches(false);
			con.setRequestMethod("POST");
			con.setRequestProperty("Connection", "Keep-Alive");
			con.setRequestProperty("Charset", "UTF-8");
			con.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
			con.setConnectTimeout(300000);
			con.setReadTimeout(300000);		
			DataOutputStream ds = new DataOutputStream(con.getOutputStream());
			ds.writeBytes(twoHyphens + boundary + end);
			ds.writeBytes("Content-Disposition:multipart/form-data; " + "name=\"file1\";filename=\"" + filename + "\"\r\n");
			ds.writeBytes("Content-Type:application/octet-stream\r\n\r\n");
			ds.write(data, 0, data.length);
			ds.writeBytes(end);
			ds.writeBytes(twoHyphens + boundary + twoHyphens + end);
			ds.flush();
			InputStream is = con.getInputStream();
			int ch;
			StringBuffer result = new StringBuffer();
			while ((ch = is.read()) != -1) {
				result.append((char) ch);
			}
			ds.close();
			return result.toString();
		} catch (Exception e) {
			return null;
		} finally {
			if (con != null)
				con.disconnect();
		}
	}
	public static String  uploadFile(String filepathname, String filename) {
		String end = "\r\n";
		String twoHyphens = "--";
		String boundary = "*****";
		try {
			URL url = new URL(uploadUrl);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setDoInput(true);
			con.setDoOutput(true);
			con.setUseCaches(false);
			con.setRequestMethod("POST");
			con.setRequestProperty("Connection", "Keep-Alive");
			con.setRequestProperty("Charset", "UTF-8");
			con.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
			DataOutputStream ds = new DataOutputStream(con.getOutputStream());
			ds.writeBytes(twoHyphens + boundary + end);
			ds.writeBytes("Content-Disposition:form-data; " + "name=\"file1\";filename=\"" +filename+ "\"\r\n");
			ds.writeBytes(end);
			FileInputStream fStream = new FileInputStream(filepathname);
			int bufferSize = 1024;
			byte[] buffer = new byte[bufferSize];
			int length = -1;	
			while ((length = fStream.read(buffer)) != -1) {	
				Log.e("zx","length:"+length);
				ds.write(buffer, 0, length);
			}
			ds.writeBytes(end);
			ds.writeBytes(twoHyphens + boundary + twoHyphens + end);
			fStream.close();
			ds.flush();
			InputStream is = con.getInputStream();
			int ch;
			StringBuffer b = new StringBuffer();
			while ((ch = is.read()) != -1) {
				b.append((char) ch);
				
			}
			ds.close();			
			return  b.toString();
		} catch (Exception e) {
			return e.toString();
		}
	}
	public static String getPhotoFileName(String id) {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"'IMG'_yyyyMMdd_HHmmss");
		return dateFormat.format(date) + id+".jpg";
	}
}