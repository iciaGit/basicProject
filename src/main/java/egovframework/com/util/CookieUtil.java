package egovframework.com.util;

import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.util.CookieGenerator;

public class CookieUtil {
	
	public static int ONE_YEAR = 60*60*24*30*12;
	public static int ONE_MONTH = 60*60*24*30;
	public static int ONE_DAY = 60*60*24;
	public static int ZERO = 0;
		

	/**
	 * Descript : 쿠키 생성
	 * @param cookieName:String - 생성 쿠키 이름
	 * @param value:String - 쿠키 값
	 * @param maxAge:int - 쿠키 유지 시간
	 * @param resp
	 * @param domain:String - 생성 도메인(공백일 경우 현재 도메인을 기준)
	 */
	public static void setCookie(String cookieName, String value, int maxAge, HttpServletResponse resp, String domain) {
		CookieGenerator cg = new CookieGenerator();		
		try {		
			String cookieValue = value != null ? URLEncoder.encode(value,"UTF-8") : null; 
			//시큐어 코딩에 의한 개행(줄바꿈) 문자 제거
			if(cookieValue != null) {
				cookieValue = cookieValue.replaceAll("[\r\n]", "");
			}
	
			//cg.setCookieSecure(true);//쿠키 보안 속성(SSL 통신 채널 에서만 쿠키 전송)
			//cg.setCookieHttpOnly(true);//서버 요청이 있을 경우만 쿠키가 전송 되도록 설정

			cg.setCookieName(cookieName);
			cg.setCookieMaxAge(maxAge);
			
			if(domain != null && !domain.equals("")) {
				cg.setCookieDomain(domain);
			}
			
			cg.addCookie(resp, cookieValue);					

		} catch (Exception e) {
			e.printStackTrace();
		}	
		cg = null;
	}
	
	/**
	 * 해당 이름의 쿠키 값을 가져온다.
	 * @param req
	 * @param cookieName:String - 가져 올 쿠키 이름
	 * @return :String - 쿠키 값
	 */
	public static String getCookie(HttpServletRequest req, String cookieName) {				
		Cookie[] cookies = req.getCookies();		
		String value = "";
		if(cookies != null) {					
			try {
				for(Cookie cookie : cookies) {
					if(cookie.getName().equals(cookieName)) {		
						//System.out.println(cookieName+" 의 쿠키가 있음");
						value = URLDecoder.decode(cookie.getValue(),"UTF-8");							
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}	
		}				
		return value;
	}

}
