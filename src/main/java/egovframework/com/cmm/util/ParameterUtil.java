package egovframework.com.cmm.util;

import java.lang.reflect.Field;
import java.util.HashMap;

public class ParameterUtil {
	
	/**
	 * 맵 안의 키와 값을 확인 한다.
	 * @param map
	 */
	public static void getMapValues(HashMap<String, String> map) {
		for(String key : map.keySet()) {
			System.out.println(key+" : "+map.get(key));
		}
	}
	
	/**
	 * 특정 VO 안의 값을 확인 한다.
	 * */
	public static void getVoValues(Object obj) {
		try {
	        for (Field field : obj.getClass().getDeclaredFields()) {
	            field.setAccessible(true);
				Object value = field.get(obj);				
				System.out.println(field.getName()+" = "+value);
	        }
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
