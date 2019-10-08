package common.util;

public class XssUtil {
	
	/** XSS 스트립트 방지(기본)
	 * 
	 * @param value:String
	 * @return returnValue:String
	 */
	public static String clearXssMin(String value) {
		
		if (value == null || value.trim().equals("")) {
			return "";
		}

		String returnValue = value;

		returnValue = returnValue.replaceAll("&", "&amp;");
		returnValue = returnValue.replaceAll("<", "&lt;");
		returnValue = returnValue.replaceAll(">", "&gt;");
		returnValue = returnValue.replaceAll("\"", "&#34;");
		returnValue = returnValue.replaceAll("\'", "&#39;");
		returnValue = returnValue.replaceAll(".", "&#46;");
		returnValue = returnValue.replaceAll("%2E", "&#46;");
		returnValue = returnValue.replaceAll("%2F", "&#47;");
		return returnValue;
	}
	
	/** XSS 스트립트 방지(최대치)
	 * 
	 * @param value:String
	 * @return returnValue:String
	 */
	public static String clearXssMax(String value) {
		String returnValue = value;
		returnValue = clearXssMin(returnValue);//기본을 통과 후...
		returnValue = returnValue.replaceAll("%00", null);
		returnValue = returnValue.replaceAll("%", "&#37;");
		returnValue = returnValue.replaceAll("\\.\\./", ""); // ../
		returnValue = returnValue.replaceAll("\\.\\.\\\\", ""); // ..\
		returnValue = returnValue.replaceAll("\\./", ""); // ./
		returnValue = returnValue.replaceAll("%2F", "");
		return returnValue;
	}	

}
