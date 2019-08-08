package common.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DateUtil {
	
	private static Calendar cal = Calendar.getInstance(Locale.KOREA);
	private static SimpleDateFormat sdf =null;
	
	//현재 연 구하기
	public static int getcurrentYear() {
		return cal.get(Calendar.YEAR);
	}
	
	//현재 월 구하기
	public static int getcurrentMonth() {
		return cal.get(Calendar.MONTH)+1;
	}
	
	//현재 일 구하기
	public static int getcurrentDay() {		
		return cal.get(Calendar.DAY_OF_MONTH);
	}


}
