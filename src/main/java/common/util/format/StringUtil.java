package common.util.format;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class StringUtil {
	
	/**
	 * string 에 대한 url encode 
	 * */
	public static String encodeMsg(String plainMsg) {		
		String encMsg = "";		
		try {
			encMsg = URLEncoder.encode(plainMsg, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return encMsg;
	}


	/**
	 * string 에 대한 url decode 
	 * */
	public static String decodeMsg(String encMsg) {		
		String plainMsg = "";		
		try {
			plainMsg = URLDecoder.decode(encMsg, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return plainMsg;
	}
	
	
	/**
	 * 문자열을 java.sql.Date 타입으로 반환
	 */
	public static java.sql.Date StringToSqlDate(String inputStr, String pattern) {		
		Date date = null;
		try {
			// 문자열로 변환하기 위한 패턴 설정
			if(pattern == null || pattern.equals("")){
				pattern = "yyyy-MM-dd HH:mm:ss.SSS";
			}
			SimpleDateFormat sdfCurrent = new SimpleDateFormat(pattern, Locale.KOREA);			
			date = sdfCurrent.parse(inputStr);
		} catch (Exception e) {
			e.printStackTrace();
		}						
		return new java.sql.Date(date.getTime());
	}

}
