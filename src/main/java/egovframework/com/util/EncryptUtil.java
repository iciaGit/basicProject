package egovframework.com.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncryptUtil {
	
	/**
	 *  해쉬 암호화 메서드
	 * @param txt:String - 암호화 할 문장
	 * @param arg2:String - 알고리즘(기본값:SHA-256)
	 * @return String - 암호화 된 문장
	 */
	public static String encTextConvert(String txt, String arg2) {	
		StringBuffer stringBuffer = new StringBuffer();
			//"SHA-256", "MD5" 
			MessageDigest dMessage;			
			String algo = arg2 == "" ? "SHA-256" : arg2;			
			try {
				dMessage = MessageDigest.getInstance(algo);
				dMessage.update(txt.getBytes());		     
				byte[] msgStr = dMessage.digest();
				for(int i=0; i < msgStr.length; i++){
					byte tmpStrByte = msgStr[i];	         
					stringBuffer.append(Integer.toString((tmpStrByte & 0xff) + 0x100, 16).substring(1)) ;
				}		    
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}		
			return stringBuffer.toString();
		}

}
