package egovframework.com.util;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SessionUtil {
	
	/**
	 * 세션에 특정 값 추가
	 * @param request
	 * @param param
	 */
	public static void setSession(HttpServletRequest request, HashMap<String, Object> param) {
		HttpSession session = request.getSession();
		for( String key : param.keySet()) {
			session.setAttribute(key, param.get(key));
		}
	}
	
	/**
	 * 세션의 특정값 가져오기
	 * @param request
	 * @param attrName
	 * @return
	 */
	public static Object getSession(HttpServletRequest request, String attrName) {
		HttpSession session = request.getSession();
		return session.getAttribute(attrName);
	}

}
