package egovframework.com.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;


public class PropertiesUtil {
	
	private static String prefix = PropertiesUtil.class.getClassLoader().getResource("").getPath();
	
	
	/**
	 * 특정 프로퍼티 파일의 특정 값을 가져옴
	 * @param propName:String - 프로퍼티 파일 이름
	 * @param keyName:String - 가져올 항목 이름
	 * @return String - 항목에 대한 값
	 */
	public static String getProperty(String propName,String keyName) {		
		String value = "";
		InputStream fis = null;		
		
		String path = prefix+"egovframework/egovProps/"+propName+".properties";		
		try {
			Properties prop = new Properties();
			fis = new FileInputStream(path);
			prop.load(new BufferedInputStream(fis));
			value = (String) prop.get(keyName);
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {fis.close();} catch (IOException e){}			
		}
		return value;
	}


	public static void setProperty(String propName, HashMap<String, String> map) {
		
		Properties prop = new Properties();
		FileOutputStream fos = null;	
		BufferedOutputStream bos = null;
		for(String key : map.keySet()) {
			prop.put(key, map.get(key));
		}
		
		try {			
			String path = prefix+"config/props/"+propName+".properties";
			fos = new FileOutputStream(path);	
			bos = new BufferedOutputStream(fos);
			//4. 저장		
			prop.store(bos, "");//저장스트림, 주석 내용
			System.out.println("저장 완료");
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				bos.close();
				fos.close();	
			} catch (IOException e) {
				e.printStackTrace();
			}			
		}
		
	}	

}
