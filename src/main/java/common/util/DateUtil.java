package common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import com.ibm.icu.util.ChineseCalendar;

public class DateUtil {
	
	//calendar 객체는 필요할때 생성 하는 걸로 해야 오류가 없다.
	//private static Calendar cal = Calendar.getInstance(Locale.KOREA);
	private static SimpleDateFormat sdf =null;
	
	//현재 연 구하기
	public static int getcurrentYear(Calendar cal) {
		return cal.get(Calendar.YEAR);
	}
	
	//현재 월 구하기
	public static int getcurrentMonth(Calendar cal) {
		return cal.get(Calendar.MONTH)+1;
	}
	
	//현재 일 구하기
	public static int getcurrentDay(Calendar cal) {		
		return cal.get(Calendar.DAY_OF_MONTH);
	}
	
	//오늘 년-월-일 반환
	public static String getCurrentDate(Calendar cal) {		
		String year = String.valueOf(getcurrentYear(cal));
		String month = String.valueOf(getcurrentMonth(cal));
		String day = String.valueOf(getcurrentDay(cal));
		
		if(month.length()<2) {
			month = "0"+month; 
		}
		
		if(day.length()<2) {
			day = "0"+day; 
		}
				
		return year+"-"+month+"-"+day;
	}
	
	
	/**
	 * 입력받은 양력일자를 변환하여 음력일자로 반환
	 * @param sDate 양력일자
	 * @return 음력일자
	 */
	public static Map<String, String> toLunar(String sDate) {
		
		String fullDate[] = sDate.split("-");

		Map<String, String> hm = new HashMap<String, String>();
		hm.put("day", "");
		hm.put("leap", "0");

		Calendar cal;
		ChineseCalendar lcal;

		cal = Calendar.getInstance();
		lcal = new ChineseCalendar();

		cal.set(Calendar.YEAR, Integer.parseInt(fullDate[0]));
		cal.set(Calendar.MONTH, Integer.parseInt(fullDate[1]) - 1);
		cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(fullDate[2]));

		lcal.setTimeInMillis(cal.getTimeInMillis());

		String year = String.valueOf(lcal.get(ChineseCalendar.EXTENDED_YEAR) - 2637);
		String month = String.valueOf(lcal.get(ChineseCalendar.MONTH) + 1);
		String day = String.valueOf(lcal.get(ChineseCalendar.DAY_OF_MONTH));
		String leap = String.valueOf(lcal.get(ChineseCalendar.IS_LEAP_MONTH));

		String pad4Str = "0000";
		String pad2Str = "00";

		String retYear = (pad4Str + year).substring(year.length());
		String retMonth = (pad2Str + month).substring(month.length());
		String retDay = (pad2Str + day).substring(day.length());

		String SDay = retYear+"-"+retMonth+"-"+retDay;

		hm.put("day", SDay);
		hm.put("leap", leap);

		return hm;
	}	
	
	
	/** 입력받은 음력일자를 변환하여 양력일자로 반환(윤달 계산 포함)
	 * 	  
	 * @param sDate
	 * @return
	 */
	public static String toSolar(String sDate) {
		
		String fullDate[] = sDate.split("-");

		Calendar cal;
		ChineseCalendar lcal;

		int iLeapMonth = 0;
		int year = Integer.parseInt(fullDate[0]);
		
		if(year % 4 == 0 && year % 100 != 0 || year % 400 == 0){
			iLeapMonth = 1;//윤달
		}
		
		cal = Calendar.getInstance();
		lcal = new ChineseCalendar();

		lcal.set(ChineseCalendar.EXTENDED_YEAR, year + 2637);
		lcal.set(ChineseCalendar.MONTH, Integer.parseInt(fullDate[1]) - 1);
		lcal.set(ChineseCalendar.DAY_OF_MONTH, Integer.parseInt(fullDate[2]));
		lcal.set(ChineseCalendar.IS_LEAP_MONTH, iLeapMonth);

		cal.setTimeInMillis(lcal.getTimeInMillis());

		String yearStr = String.valueOf(cal.get(Calendar.YEAR));
		String monthStr = String.valueOf(cal.get(Calendar.MONTH) + 1);
		String dayStr = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));

		String pad4Str = "0000";
		String pad2Str = "00";

		String retYear = (pad4Str + yearStr).substring(yearStr.length());
		String retMonth = (pad2Str + monthStr).substring(monthStr.length());
		String retDay = (pad2Str + dayStr).substring(dayStr.length());

		return retYear+"-"+retMonth+"-"+retDay;
	}
	
	/**
	 * 
	 * @param targetDate - 기준 날짜 <예> 20200203
	 * @param day - 계산할 날짜(23 | -30)
	 * @return String
	 */
	public static String clacDate(String targetDate, int day) {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd",Locale.getDefault());
		
		try {
			cal.setTime(sdf.parse(targetDate));
		} catch (ParseException e) {
			throw new IllegalArgumentException("Invalid date format: " + targetDate);
		}
		
		if (day != 0) {
			cal.add(Calendar.DATE, day);
		}
		
		
		return sdf.format(cal.getTime());
	}

}
