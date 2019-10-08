package egovframework.com.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class RestMsgUtil {
	
	/**
	 * 
	 * @param urls:ArrayList<String>
	 * @param header:HashMap<String,String>
	 * @param method:String
	 * @return result:String
	 */
	public static String sendMsg(ArrayList<String> urls, HashMap<String, String> header, String method) {
		StringBuffer sb = new StringBuffer();//url 사용 버퍼
		String readLine = "";//읽어들일 문자 변수
		BufferedReader reader = null;//버퍼리더
		String result = "";	
		
		for(String url : urls) {	sb.append(url);}//url 조합
		
		try {
			URL url = new URL(sb.toString());		
			HttpURLConnection con = (HttpURLConnection)url.openConnection();			
			method = method.toUpperCase();			
			con.setRequestMethod(method);//method 적용

			if(header != null) {//헤더 값이 있을 경우 추가 해 준다.
				for(String key : header.keySet()) {
					con.setRequestProperty(key, header.get(key));
				}	
			}
			
			sb = null;
			sb = new StringBuffer();
			if(con.getResponseCode()==200) {
				reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
			} else {
				reader = new BufferedReader(new InputStreamReader(con.getErrorStream()));
			}
			
			//메시지를 전송 받는다.			
			while ((readLine = reader.readLine()) != null) {sb.append(readLine);}			
			
			result = sb.toString();
			if(con.getResponseCode()!=200) {
				result = "failCode : "+con.getResponseCode();
			}
						
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(reader != null) { reader.close();}	
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
				
		return result;
	}
	
	
	

}
