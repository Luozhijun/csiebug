package csiebug.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.naming.NamingException;
/**
 * 日期轉換工具
 * @author George_Tsai
 * @version 2008/5/27
 */

public class DateFormatUtility {
	static int[] days_of_month = new int[]{31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
	static String[] displayDay = new String[]{"日", "一", "二", "三", "四", "五", "六"};
	static String[] displayDayForEng = new String[]{"Sun", "Mon", "Tue", "Wed", "Thr", "Fri", "Sat"};
	static int[] dateTypes = new int[]{1, 2, 3, 4, 5, 10, 11, 12, 23, 101, 102, 103, 104, 105, 110, 111, 112};
	
	//此變數為系統預設採用的日期格式
	int defaultDateFormat = 23;
	
	public DateFormatUtility() throws NamingException {
		WebUtility webutil = new WebUtility();
		if(webutil.getSysDateFormat() != null) {
			defaultDateFormat = Integer.parseInt(webutil.getSysDateFormat());
		}
	}
	
	/**
	 * 取得某月份的天數
	 * @param 西元年
	 * @param 月
	 * @return 天數
	 * @author George_Tsai
	 * @version 2008/5/27
	 * @throws DateFormatException 
	 */
	public static int getDaysOfMonth(int year, int month) throws DateFormatException {
		if(isValidMonth(month)) {
			if(month == 2 && isBissextile(year)) {
				return 29;
			}
			
			return days_of_month[month - 1];
		} else {
			throw new DateFormatException("Invalid month!!");
		}
	}
	
	/**
	 * 取得顯示的星期幾
	 * @param 西元年
	 * @param 月
	 * @param 日
	 * @param 顯示格式
	 * @return 星期幾
	 * @author George_Tsai
	 * @version 2008/5/27
	 * @throws DateFormatException 
	 */
	public static String getDisplayDaysOfWeek(int year, int month, int day, int type) throws DateFormatException {
		if(isValidDate(year, month, day)) {
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, year);
			cal.set(Calendar.MONTH, month - 1);
			cal.set(Calendar.DAY_OF_MONTH, day);
			
			if(type >= 1000) {
				return "星期" + displayDay[cal.get(Calendar.DAY_OF_WEEK) - 1];
			} else {
				return displayDayForEng[cal.get(Calendar.DAY_OF_WEEK) - 1];
			}
		} else {
			throw new DateFormatException("Invalid date!!");
		}
	}
	
	/**
	 * 傳入西元年，判斷此年是否是閏年
	 * @param 欲判斷的年(西元年)
	 * @return true/false
	 * @author George_Tsai
	 * @version 2008/5/27
	 */
	public static boolean isBissextile(int year) {
		boolean flag = false;
		

		if ((year % 400 == 0) || ((year % 4 == 0) && (year % 100 != 0))) {
			flag = true;
		}
			
		return flag;
	}
	
	/**
	 * 傳入西元年，取得民國年
	 * @param 西元年
	 * @return 民國年
	 * @author George_Tsai
	 * @version 2008/5/27
	 */
	public static int getLocalYear(int year) {
		return (year - 1911);
	}

	/**
	 * 取得顯示的年
	 * @param 西元年
	 * @param 顯示格式
	 * @return 顯示年
	 * @author George_Tsai
	 * @version 2008/5/27
	 * @throws DateFormatException 
	 */
	public static String getDisplayYear(int year, int type) throws DateFormatException {
		if(type == 1 || type == 2 || type == 3 || type == 4 || type == 5 || type == 10 || type == 11) {
			if(year >= 1000) {
				return ("" + year).substring(2, 4);
			} else {
				throw new DateFormatException("Invalid year!!");
			}
		} else {
			if(type >= 1000) {
				return "" + getLocalYear(year);
			} else {
				return "" + year;
			}
		}
	}

	/**
	 * 取得顯示的月
	 * @param 月
	 * @return 顯示月
	 * @author George_Tsai
	 * @version 2008/5/27
	 * @throws DateFormatException 
	 */
	public static String getDisplayMonth(int month) throws DateFormatException {
		if(isValidMonth(month)) {
			if(month < 10) {
				return "0" + month;
			} else {
				return "" + month;
			}
		} else {
			throw new DateFormatException("Invalid month!!");
		}
	}

	/**
	 * 取得顯示的年月組合
	 * @param 西元年
	 * @param 月
	 * @param 顯示格式
	 * @return 顯示月
	 * @author George_Tsai
	 * @version 2008/5/27
	 * @throws DateFormatException 
	 */
	public static String getDisplayYearMonth(int year, int month, int type) throws DateFormatException {
		if(isValidMonth(month)) {
			return getDisplayYear(year, type) + getDisplayMonth(month);
		} else {
			throw new DateFormatException("Invalid month!!");
		}
	}

	/**
	 * 取得顯示的日期格式字串
	 * @param 西元年
	 * @param 月
	 * @param 日
	 * @param 顯示格式
	 * @return 日期格式字串
	 * @author George_Tsai
	 * @version 2008/5/27
	 * @throws DateFormatException 
	 */
	public static String getDisplayDate(int year, int month, int day, int type) throws DateFormatException {
		if(isValidDate(year, month, day)) {
			return getValidDisplayDate(year, month, day, type);
		} else {
			throw new DateFormatException("Invalid date!!");
		}
	}
	
	/**
	 * 取得顯示的日期格式字串
	 * @param Calendar
	 * @param 顯示格式
	 * @return 日期格式字串
	 * @author George_Tsai
	 * @version 2008/5/27
	 * @throws DateFormatException 
	 */
	public static String getDisplayDate(Calendar calendar, int type) throws DateFormatException {
		return getValidDisplayDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH), type);
	}
	
	private static String getValidDisplayDate(int year, int month, int day, int type) throws DateFormatException {
		String strValue = "";
		
		boolean localYear = false;
		if(type >= 1000) {
			type = type / 1000;
			localYear = true;
		}
		
		if(localYear) {
			strValue = getValidLocalDisplayDay(year, month, day, type);
		} else {
			strValue = getValidCommonDisplayDay(year, month, day, type);
		}
		
		if(localYear) {
			type = type * 1000;
		}
		
		strValue = completeMonthDay(strValue, type);
		
		return strValue;
	}
	
	private static String getValidLocalDisplayDay(int year, int month, int day, int type) throws DateFormatException {
		if(type == 1 || type == 101) {
			return month + "/" + day + "/" + getLocalYear(year);
		} else if(type == 2 || type == 102) {
			return getLocalYear(year) + "." + month + "." + day;
		} else if(type == 3 || type == 103) {
			return day + "/" + month + "/" + getLocalYear(year);
		} else if(type == 4 || type == 104) {
			return day + "." + month + "." + getLocalYear(year);
		} else if(type == 5 || type == 105) {
			return day + "-" + month + "-" + getLocalYear(year);
		} else if(type == 10 || type == 110) {
			return month + "-" + day + "-" + getLocalYear(year);
		} else if(type == 11 || type == 111) {
			return getLocalYear(year) + "/" + month + "/" + day;
		} else if(type == 12 || type == 112) {
			return getLocalYear(year) + "" + StringUtility.addZero(month, 2) + "" + StringUtility.addZero(day, 2);
		} else if(type == 23) {
			return getLocalYear(year) + "-" + month + "-" + day;
		} else {
			throw new DateFormatException("Unknow date format type!!");
		}
	}
	
	private static String getValidCommonDisplayDay(int year, int month, int day, int type) throws DateFormatException {
		if(type == 1) {
			return month + "/" + day + "/" + ("" + year).substring(2, 4);
		} else if(type == 2) {
			return ("" + year).substring(2, 4) + "." + month + "." + day;
		} else if(type == 3) {
			return day + "/" + month + "/" + ("" + year).substring(2, 4);
		} else if(type == 4) {
			return day + "." + month + "." + ("" + year).substring(2, 4);
		} else if(type == 5) {
			return day + "-" + month + "-" + ("" + year).substring(2, 4);
		} else if(type == 10) {
			return month + "-" + day + "-" + ("" + year).substring(2, 4);
		} else if(type == 11) {
			return ("" + year).substring(2, 4) + "/" + month + "/" + day;
		} else if(type == 12) {
			return ("" + year).substring(2, 4) + "" + StringUtility.addZero(month, 2) + "" + StringUtility.addZero(day, 2);
		} else if(type == 23) {
			return year + "-" + month + "-" + day;
		} else if(type == 101) {
			return month + "/" + day + "/" + year;
		} else if(type == 102) {
			return year + "." + month + "." + day;
		} else if(type == 103) {
			return day + "/" + month + "/" + year;
		} else if(type == 104) {
			return day + "." + month + "." + year;
		} else if(type == 105) {
			return day + "-" + month + "-" + year;
		} else if(type == 110) {
			return month + "-" + day  + "-" + year;
		} else if(type == 111) {
			return year + "/" + month + "/" + day;
		} else if(type == 112) {
			return year + "" + StringUtility.addZero(month, 2) + "" + StringUtility.addZero(day, 2);
		} else {
			throw new DateFormatException("Unknow date format type!!");
		}
	}
	
	/**
	 * 自動轉換日期格式成為系統日期格式
	 * @author George_Tsai
	 * @version 2009/2/19
	 * @throws DateFormatException 
	 */
	public String autoTransDate(String value) throws DateFormatException {
		AssertUtility.notNullAndNotSpace(value);
		
		int type = 0;
		int y = 0;
		int m = 0;
		int d = 0;
		String order = "1";
		
		//檢查使用者輸入符合哪種日期格式
		for(int i = 0; i < dateTypes.length; i++) {
			if(isValidDate(value, dateTypes[i])) {
				if(defaultDateFormat >= 1000) {
					type = dateTypes[i] * 1000;
				} else {
					type = dateTypes[i];
				}
				
				String[] parseDate = getParseDate(value, type);
				y = Integer.parseInt(parseDate[0], 10);
				m = Integer.parseInt(parseDate[1], 10);
				d = Integer.parseInt(parseDate[2], 10);
				order = parseDate[4];
				
				//getDisplayDate只處理西元年,所以民國年或是兩位的西元年都要處理成四位
				if(type < 1000) {//處理西元年
					if(y < 2000) {//兩位的西元年
						if(order.equalsIgnoreCase("1")) { //有可能會符合好幾種格式,所以一律只將使用者輸入當作ymd來處理
							//考慮輸入生日等情況,會輸入19xx年,避免多加2000年
							if(y < 1000) {
								y = y + 2000;
							}
						}
					}
				} else {//處理民國年
					y = y + 1911;
				}
				
				break;
			}
		}
		
		//將parse出來的年月日,用系統預設日期格式展現出來
		return getDisplayDate(y, m, d, defaultDateFormat);
	}
	/**
	 * 手動轉換日期格式
	 * @author George_Tsai
	 * @version 2008/9/12
	 * @throws DateFormatException 
	 */
	public static String transDate(String value, int type, int targetType) throws DateFormatException {
		AssertUtility.notNullAndNotSpace(value);
		
		String[] parseDate = getParseDate(value, type);
		int y = Integer.parseInt(parseDate[0], 10);
		int m = Integer.parseInt(parseDate[1], 10);
		int d = Integer.parseInt(parseDate[2], 10);
		String yearType = parseDate[5];
		
		if(yearType.equalsIgnoreCase("2")) {
			y = y + 2000;
		} else if(yearType.equalsIgnoreCase("3")) {
			y = y + 1911;
		}
		
		//將parse出來的年月日,用指定的日期格式展現出來
		return getDisplayDate(y, m, d, targetType);
	}
	
	/**
	 * 用固定日期格式解析字串
	 * @author George_Tsai
	 * @version 2008/9/11
	 * @throws DateFormatException 
	 */
	public static String[] getParseDate(String value, int type) throws DateFormatException {
		AssertUtility.notNullAndNotSpace(value);
		
		String[] parseDate = new String[6];
		
		String y = "0";
		String m = "0";
		String d = "0";
		
		String spliter = "";
		String order = "1"; /*1:ymd 2:mdy 3: dmy*/
		String yearType = "1"; /*1:四位年 2:兩位年 3:民國年*/
		
		boolean localYear = false;
		if(type >= 1000) {
			type = type / 1000;
			localYear = true;
		}
		
		if(type == 1) {
			spliter = "/";
			order = "2";
			yearType = "2";
		} else if(type == 2) {
			spliter = ".";
			order = "1";
			yearType = "2";
		} else if(type == 3) {
			spliter = "/";
			order = "3";
			yearType = "2";
		} else if(type == 4) {
			spliter = ".";
			order = "3";
			yearType = "2";
		} else if(type == 5) {
			spliter = "-";
			order = "3";
			yearType = "2";
		} else if(type == 10) {
			spliter = "-";
			order = "2";
			yearType = "2";
		} else if(type == 11) {
			spliter = "/";
			order = "1";
			yearType = "2";
		} else if(type == 12) {
			spliter = "";
			order = "1";
			yearType = "2";
		} else if(type == 23) {
			spliter = "-";
			order = "1";
			yearType = "1";
		} else if(type == 101) {
			spliter = "/";
			order = "2";
			yearType = "1";
		} else if(type == 102) {
			spliter = ".";
			order = "1";
			yearType = "1";
		} else if(type == 103) {
			spliter = "/";
			order = "3";
			yearType = "1";
		} else if(type == 104) {
			spliter = ".";
			order = "3";
			yearType = "1";
		} else if(type == 105) {
			spliter = "-";
			order = "3";
			yearType = "1";
		} else if(type == 110) {
			spliter = "-";
			order = "2";
			yearType = "1";
		} else if(type == 111) {
			spliter = "/";
			order = "1";
			yearType = "1";
		} else if(type == 112) {
			spliter = "";
			order = "1";
			yearType = "1";
		} else {
			throw new DateFormatException("Unknow date format type!!");
		}
		
		if(localYear) {
//FindBugs Dodgy Warnings
//但是type的值被改過了,乘1000才是正確的,預防之後可能修改程式碼,所以保留此行程式碼
//			type = type * 1000;
			yearType = "3";
		}
		
		if(!spliter.equalsIgnoreCase("") && order.equalsIgnoreCase("1")) {
			String tmp = value;
			y = completeYear(tmp.substring(0, tmp.indexOf(spliter)), yearType);
			tmp = tmp.substring(tmp.indexOf(spliter) + 1, tmp.length());
			m = StringUtility.addZero(tmp.substring(0, tmp.indexOf(spliter)), 2);
			d = StringUtility.addZero(tmp.substring(tmp.indexOf(spliter) + 1, tmp.length()), 2);
		} else if(!spliter.equalsIgnoreCase("") && order.equalsIgnoreCase("2")) {
			String tmp = value;
			m = StringUtility.addZero(tmp.substring(0, tmp.indexOf(spliter)), 2);
			tmp = tmp.substring(tmp.indexOf(spliter) + 1, tmp.length());
			d = StringUtility.addZero(tmp.substring(0, tmp.indexOf(spliter)), 2);
			y = completeYear(tmp.substring(tmp.indexOf(spliter) + 1, tmp.length()), yearType);
		} else if(!spliter.equalsIgnoreCase("") && order.equalsIgnoreCase("3")) {
			String tmp = value;
			d = StringUtility.addZero(tmp.substring(0, tmp.indexOf(spliter)), 2);
			tmp = tmp.substring(tmp.indexOf(spliter) + 1, tmp.length());
			m = StringUtility.addZero(tmp.substring(0, tmp.indexOf(spliter)), 2);
			y = completeYear(tmp.substring(tmp.indexOf(spliter) + 1, tmp.length()), yearType);
		} else if(spliter.equalsIgnoreCase("") && yearType.equalsIgnoreCase("1")) {
			y = completeYear(value.substring(0, 4), yearType);
			m = StringUtility.addZero(value.substring(4, 6), 2);
			d = StringUtility.addZero(value.substring(6, 8), 2);
		} else if(spliter.equalsIgnoreCase("") && yearType.equalsIgnoreCase("2")) {
			y = completeYear(value.substring(0, 2), yearType);
			m = StringUtility.addZero(value.substring(2, 4), 2);
			d = StringUtility.addZero(value.substring(4, 6), 2);
		} else if(spliter.equalsIgnoreCase("") && yearType.equalsIgnoreCase("3")) {
			if(value.length() == 6) {
				y = completeYear(value.substring(0, 2), yearType);
				m = StringUtility.addZero(value.substring(2, 4), 2);
				d = StringUtility.addZero(value.substring(4, 6), 2);
			} else if(value.length() == 7) {
				y = completeYear(value.substring(0, 3), yearType);
				m = StringUtility.addZero(value.substring(3, 5), 2);
				d = StringUtility.addZero(value.substring(5, 7), 2);
			}
		}
		
		parseDate[0] = y;
		parseDate[1] = m;
		parseDate[2] = d;
		parseDate[3] = spliter;
		parseDate[4] = order;
		parseDate[5] = yearType;
		
		return parseDate;
	}
	
	/**
	 * 檢查12小時制時間是否合法
	 * @param value
	 * @return
	 * @author George_Tsai
	 * @version 2011/2/12
	 */
	public static boolean isValidTime12(String value) {
		String[] timePart = value.split(":");
		if(timePart.length == 2) {
			return (Integer.parseInt(timePart[0], 10) >= 0 && Integer.parseInt(timePart[0], 10) < 12 && Integer.parseInt(timePart[1], 10) >= 0 && Integer.parseInt(timePart[1]) < 60);
		} else if(timePart.length == 3) {
			return (Integer.parseInt(timePart[0], 10) >= 0 && Integer.parseInt(timePart[0], 10) < 12 && Integer.parseInt(timePart[1], 10) >= 0 && Integer.parseInt(timePart[1]) < 60 && Integer.parseInt(timePart[2]) >= 0 && Integer.parseInt(timePart[2]) < 60);
		} else {
			return false;
		}
	}
	
	/**
	 * 檢查24小時制時間是否合法
	 * @param value
	 * @return
	 * @author George_Tsai
	 * @version 2011/2/12
	 */
	public static boolean isValidTime24(String value) {
		String[] timePart = value.split(":");
		if(timePart.length == 2) {
			return (Integer.parseInt(timePart[0], 10) >= 0 && Integer.parseInt(timePart[0], 10) < 24 && Integer.parseInt(timePart[1], 10) >= 0 && Integer.parseInt(timePart[1]) < 60);
		} else if(timePart.length == 3) {
			return (Integer.parseInt(timePart[0], 10) >= 0 && Integer.parseInt(timePart[0], 10) < 24 && Integer.parseInt(timePart[1], 10) >= 0 && Integer.parseInt(timePart[1]) < 60 && Integer.parseInt(timePart[2]) >= 0 && Integer.parseInt(timePart[2]) < 60);
		} else {
			return false;
		}
	}
	
	/**
	 * 檢查是否是合法月份
	 * @param month
	 * @return
	 * @author George_Tsai
	 * @version 2009/7/25
	 */
	public static boolean isValidMonth(int month) {
		boolean flag = false;
		
		if(month >= 1 && month <=12) {
			flag = true;
		}
		
		return flag;
	}
	
	/**
	 * 檢查是否是合法日期
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 * @author George_Tsai
	 * @version 2009/7/25
	 * @throws DateFormatException 
	 */
	public static boolean isValidDate(int year, int month, int day) throws DateFormatException {
		return (isValidMonth(month) && day <= getDaysOfMonth(year, month) && day > 0);
	}
	
	/**
	 * 檢查是否是合法日期
	 * @author George_Tsai
	 * @version 2008/9/11
	 */
	public static boolean isValidDate(String value, int type) {
		AssertUtility.notNullAndNotSpace(value);
		
		boolean flag = true;
		
		try {
			String[] parseDate = getParseDate(value, type);
			String spliter = parseDate[3];
			String order = parseDate[4];
			String yearType = parseDate[5];
			
			String y = "0";
			String m = "0";
			String d = "0";
			
			if(!spliter.equalsIgnoreCase("") && order.equalsIgnoreCase("1")) {
				//沒有用分隔符號是錯誤的格式
				if(value.indexOf(spliter) == -1) {
					flag = false;
				} else {
					String tmp = value;
					int cnt = 0;
					while(tmp.indexOf(spliter) != -1) {
						if(cnt == 0) {
							y = tmp.substring(0, tmp.indexOf(spliter));
							tmp = tmp.substring(tmp.indexOf(spliter) + 1, tmp.length());
							cnt = cnt + 1;
						} else if(cnt == 1) {
							m = tmp.substring(0, tmp.indexOf(spliter));
							tmp = tmp.substring(tmp.indexOf(spliter) + 1, tmp.length());
							cnt = cnt + 1;
						}
					}
						
					//檢查是否有兩個分隔符號，並且是y/m/d格式
					if(cnt == 2 && tmp.length() != 0) {
						d = tmp;
					} else {
						flag = false;
					}
						
					if(flag) {
						flag = isValidDateWithYearType(y, m, d, yearType);
					}
				}
			} else if(!spliter.equalsIgnoreCase("") && order.equalsIgnoreCase("2")) {
				//沒有用分隔符號是錯誤的格式
				if(value.indexOf(spliter) == -1) {
					flag = false;
				} else {
					String tmp = value;
					int cnt = 0;
					while(tmp.indexOf(spliter) != -1) {
						if(cnt == 0) {
							m = tmp.substring(0, tmp.indexOf(spliter));
							tmp = tmp.substring(tmp.indexOf(spliter) + 1, tmp.length());
							cnt = cnt + 1;
						} else if(cnt == 1) {
							d = tmp.substring(0, tmp.indexOf(spliter));
							tmp = tmp.substring(tmp.indexOf(spliter) + 1, tmp.length());
							cnt = cnt + 1;
						}
					}
						
					//檢查是否有兩個分隔符號，並且是y/m/d格式
					if(cnt == 2 && tmp.length() != 0) {
						y = tmp;
					} else {
						flag = false;
					}
						
					if(flag) {
						flag = isValidDateWithYearType(y, m, d, yearType);
					}
				}
			} else if(!spliter.equalsIgnoreCase("") && order.equalsIgnoreCase("3")) {
				//沒有用分隔符號是錯誤的格式
				if(value.indexOf(spliter) == -1) {
					flag = false;
				} else {
					String tmp = value;
					int cnt = 0;
					while(tmp.indexOf(spliter) != -1) {
						if(cnt == 0) {
							d = tmp.substring(0, tmp.indexOf(spliter));
							tmp = tmp.substring(tmp.indexOf(spliter) + 1, tmp.length());
							cnt = cnt + 1;
						} else if(cnt == 1) {
							m = tmp.substring(0, tmp.indexOf(spliter));
							tmp = tmp.substring(tmp.indexOf(spliter) + 1, tmp.length());
							cnt = cnt + 1;
						}
					}
						
					//檢查是否有兩個分隔符號，並且是y/m/d格式
					if(cnt == 2 && tmp.length() != 0) {
						y = tmp;
					} else {
						flag = false;
					}
						
					if(flag) {
						flag = isValidDateWithYearType(y, m, d, yearType);
					}
				}
			} else if(spliter.equalsIgnoreCase("") && yearType.equalsIgnoreCase("1")) {
				if(value.length() != 8) {
					flag = false;
				} else {
					y = value.substring(0, 4);
					m = value.substring(4, 6);
					d = value.substring(6, 8);	
				
					flag = isValidDateWithYearType(y, m, d, yearType);
				}
			} else if(spliter.equalsIgnoreCase("") && yearType.equalsIgnoreCase("2")) {
				if(value.length() != 6) {
					flag = false;
				} else {
					y = value.substring(0, 2);
					m = value.substring(2, 4);
					d = value.substring(4, 6);	
				
					flag = isValidDateWithYearType(y, m, d, yearType);
				}
			} else if(spliter.equalsIgnoreCase("") && yearType.equalsIgnoreCase("3")) {
				if(value.length() != 7) {
					flag = false;
				} else {
					y = value.substring(0, 3);
					m = value.substring(3, 5);
					d = value.substring(5, 7);	
				
					flag = isValidDateWithYearType(y, m, d, yearType);
				}
			}
		} catch(Exception ex) {
			flag = false;
		}
		
		return flag;
	}
	
	/**
	 * 檢查是否是合法日期
	 * @author George_Tsai
	 * @version 2008/9/10
	 * @throws DateFormatException 
	 * @throws NumberFormatException 
	 */
	public static boolean isValidDateWithYearType(String y, String m, String d, String yearType) throws DateFormatException {
		AssertUtility.notNullAndNotSpace(y);
		AssertUtility.notNullAndNotSpace(m);
		AssertUtility.notNullAndNotSpace(d);
		AssertUtility.notNullAndNotSpace(yearType);
		
		int realY = Integer.parseInt(y);
		if(yearType.equalsIgnoreCase("2")) {
			realY = Integer.parseInt(y, 10) + 2000;
		} else if(yearType.equalsIgnoreCase("3")) {
			realY = Integer.parseInt(y, 10) + 1911;
		}
		
		return isValidDate(realY, Integer.parseInt(m), Integer.parseInt(d));
	}
	
	/**
	* 時和分若是固定兩位,幫忙補零
	* @param value
	* @param type
	* @return
	* @author George_Tsai
	* @version 2011/2/12
	*/
	public static String completeHourMinuteSecond(String value) throws DateFormatException {
		if(isValidTime12(value) || isValidTime24(value)) {
			String[] timePart = value.split(":");
			
			if(timePart.length == 2) {
				return StringUtility.addZero(timePart[0], 2) + ":" + StringUtility.addZero(timePart[1], 2);
			} else {
				return StringUtility.addZero(timePart[0], 2) + ":" + StringUtility.addZero(timePart[1], 2) + ":" + StringUtility.addZero(timePart[2], 2);
			}
		} else {
			throw new DateFormatException("Invalid time!!");
		}
	}
	
	/**
	 * 月和日若是固定兩位,幫忙補零
	 * @author George_Tsai
	 * @version 2008/9/11
	 * @throws DateFormatException 
	 */
	public static String completeMonthDay(String value, int type) throws DateFormatException {
		AssertUtility.notNullAndNotSpace(value);
		
		if(type == 12) {
			if(value.length() == 6) {
				return value;
			} else {
				throw new DateFormatException("Invalid date!!");
			}
		} else if(type == 112) {
			if(value.length() == 8) {
				return value;
			} else {
				throw new DateFormatException("Invalid date!!");
			}
		} else if(type == 12000 || type == 112000) {
			if(value.length() == 6) {
				return "0" + value;
			} else if(value.length() == 7) {
				return value;
			} else {
				throw new DateFormatException("Invalid date!!");
			}
		} else {
			String[] parseDate = getParseDate(value, type);
			String y = parseDate[0];
			String m = parseDate[1];
			String d = parseDate[2];
			
			String spliter = parseDate[3];
			String order = parseDate[4]; 
			String yearType = parseDate[5]; 
			
			String strComplete = "";
			y = completeYear(y, yearType);
			m = StringUtility.addZero(m, 2);
			d = StringUtility.addZero(d, 2);
			
			if(!spliter.equalsIgnoreCase("") && order.equalsIgnoreCase("1")) {
				strComplete = y + spliter + m + spliter + d;
			} else if(!spliter.equalsIgnoreCase("") && order.equalsIgnoreCase("2")) {
				strComplete = m + spliter + d + spliter + y;
			} else if(!spliter.equalsIgnoreCase("") && order.equalsIgnoreCase("3")) {
				strComplete = d + spliter + m + spliter + y;
			} else if(spliter.equalsIgnoreCase("") && yearType.equalsIgnoreCase("1")) {
				strComplete = y + spliter + m + spliter + d;
			} else if(spliter.equalsIgnoreCase("") && yearType.equalsIgnoreCase("2")) {
				strComplete = y + spliter + m + spliter + d;
			} else if(spliter.equalsIgnoreCase("") && yearType.equalsIgnoreCase("3")) {
				strComplete = y + spliter + m + spliter + d;
			}
			
			return strComplete;
		}
	}
	
	/**
	 * 年如果只有兩位,也需要幫忙補零
	 * @author George_Tsai
	 * @version 2008/9/10
	 */
	public static String completeYear(String value, String type) {
		AssertUtility.notNullAndNotSpace(value);
		
		if(type.equalsIgnoreCase("2")) {
			return StringUtility.addZero(value, 2);
		} else if(type.equalsIgnoreCase("3")) {
			return StringUtility.addZero(value, 3);
		} else {
			return value;
		}
	}
	
	/**
	 * 時間比較v1>v2:true;v1<=v2:false
	 * @param mid1 (Calendar.AM or Calendar.PM)
	 * @param value1
	 * @param mid2 (Calendar.AM or Calendar.PM)
	 * @param value2
	 * @return
	 * @author George_Tsai
	 * @version 2011/2/18
	 * @throws DateFormatException 
	 */
	public static boolean compareTime12(int mid1, String value1, int mid2, String value2) throws DateFormatException {
		if((mid1 == Calendar.AM || mid1 == Calendar.PM) && (mid2 == Calendar.AM || mid2 == Calendar.PM) && isValidTime12(value1) && isValidTime12(value2)) {
			String v1 = value1;
			String v2 = value2;
			if(mid1 == Calendar.PM) {
				String[] timePart = value1.split(":");
				
				if(timePart.length == 2) {
					v1 = (Integer.parseInt(timePart[0], 10) + 12) + ":" + timePart[1];
				} else if(timePart.length == 3) {
					v1 = (Integer.parseInt(timePart[0], 10) + 12) + ":" + timePart[1] + ":" + timePart[2];
				}
			}
			if(mid2 == Calendar.PM) {
				String[] timePart = value2.split(":");
				
				if(timePart.length == 2) {
					v2 = (Integer.parseInt(timePart[0], 10) + 12) + ":" + timePart[1];
				} else if(timePart.length == 3) {
					v2 = (Integer.parseInt(timePart[0], 10) + 12) + ":" + timePart[1] + ":" + timePart[2];
				}
			}
			
			return compareTime(v1, v2);
		} else {
			throw new DateFormatException("Invalid time!!");
		}
	}
	
	/**
	 * 時間比較v1>v2:true;v1<=v2:false
	 * @param value1
	 * @param value2
	 * @return
	 * @author George_Tsai
	 * @version 2011/2/18
	 * @throws DateFormatException 
	 */
	public static boolean compareTime(String value1, String value2) throws DateFormatException {
		if(isValidTime24(value1) && isValidTime24(value2)) {
			String v1 = completeHourMinuteSecond(value1);
			String v2 = completeHourMinuteSecond(value2);
			
			return (v1.compareTo(v2) > 0);
		} else {
			throw new DateFormatException("Invalid time!!");
		}
	}
	
	/**
	 * 日期比較v1>v2:true;v1<=v2:false
	 * @author George_Tsai
	 * @version 2008/5/29
	 * @throws DateFormatException 
	 */
	public static boolean compareDate(String value1, String value2, int type) throws DateFormatException {
		AssertUtility.notNullAndNotSpace(value1);
		AssertUtility.notNullAndNotSpace(value2);
		
		Calendar cal1 = toCalendar(value1, type);
		Calendar cal2 = toCalendar(value2, type);
		
		return cal1.after(cal2);
	}
	
	/**
	 * 將特定日期字串轉成Calendar物件
	 * @param value
	 * @param type
	 * @return
	 * @author George_Tsai
	 * @version 2009/7/17
	 * @throws DateFormatException 
	 */
	public static Calendar toCalendar(String value, int type) throws DateFormatException {
		AssertUtility.notNullAndNotSpace(value);
		
		Calendar calendar = Calendar.getInstance();
		
		String[] parseDate = getParseDate(value, type);
		int y = Integer.parseInt(parseDate[0], 10);
		int m = Integer.parseInt(parseDate[1], 10) - 1;
		int d = Integer.parseInt(parseDate[2], 10);
		String yearType = parseDate[5];
		
		if(yearType.equalsIgnoreCase("2")) {
			y = y + 2000;
		} else if(yearType.equalsIgnoreCase("3")) {
			y = y + 1911;
		}
		
		calendar.set(Calendar.YEAR, y);
		calendar.set(Calendar.MONTH, m);
		calendar.set(Calendar.DAY_OF_MONTH, d);
		
		return calendar;
	}
	
	/**
	 * 將日期與時間字串轉成Calendar物件
	 * @param date
	 * @param time
	 * @param type
	 * @return
	 * @throws DateFormatException
	 */
	public static Calendar toCalendar(String date, String time, int type) throws DateFormatException {
		Calendar calendar = toCalendar(date, type);
		String[] timePart = time.split(":");
		calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timePart[0]));
		calendar.set(Calendar.MINUTE, Integer.parseInt(timePart[1]));
		
		if(timePart.length == 3) {
			calendar.set(Calendar.SECOND, Integer.parseInt(timePart[2]));
		}
		
		return calendar;
	}
	
	/**
	 * 將Date物件轉成Calendar物件
	 * @param date
	 * @return
	 * @author George_Tsai
	 * @version 2011/4/13
	 */
	public static Calendar toCalendar(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		
		return calendar;
	}
	
	/**
	 * 將日期字串(系統預設格式)轉成Calendar物件
	 * @param value
	 * @param type
	 * @return
	 * @author George_Tsai
	 * @version 2009/7/17
	 * @throws DateFormatException 
	 */
	public Calendar toCalendar(String value) throws DateFormatException {
		AssertUtility.notNullAndNotSpace(value);
		
		return toCalendar(value, defaultDateFormat);
	}
	
	/**
	 * 取出日期格式字串,讓一些java物件(如date),可以用來格式化字串
	 * 故不考慮民國年
	 * @author George_Tsai
	 * @version 2010/12/23
	 * @throws DateFormatException 
	 */
	public static String getDateFormat(int type) throws DateFormatException {
		if(type == 1) {
			return "MM/dd/yy";
		} else if(type == 2) {
			return "yy.MM.dd";
		} else if(type == 3) {
			return "dd/MM/yy";
		} else if(type == 4) {
			return "dd.MM.yy";
		} else if(type == 5) {
			return "dd-MM-yy";
		} else if(type == 10) {
			return "MM-dd-yy";
		} else if(type == 11) {
			return "yy/MM/dd";
		} else if(type == 12) {
			return "yyMMdd";
		} else if(type == 23) {
			return "yyyy-MM-dd";
		} else if(type == 101) {
			return "MM/dd/yyyy";
		} else if(type == 102) {
			return "yyyy.MM.dd";
		} else if(type == 103) {
			return "dd/MM/yyyy";
		} else if(type == 104) {
			return "dd.MM.yyyy";
		} else if(type == 105) {
			return "dd-MM-yyyy";
		} else if(type == 110) {
			return "MM-dd-yyyy";
		} else if(type == 111) {
			return "yyyy/MM/dd";
		} else if(type == 112) {
			return "yyyyMMdd";
		} else {
			throw new DateFormatException("Unknow date format type!!");
		}
	}
	
	/**
	 * 將特定日期字串轉成Date物件
	 * @param value
	 * @param type
	 * @return
	 * @author George_Tsai
	 * @version 2010/12/23
	 * @throws DateFormatException 
	 * @throws ParseException 
	 */
	public static Date toDate(String value, int type) throws DateFormatException, ParseException {
		AssertUtility.notNullAndNotSpace(value);
		
		SimpleDateFormat sdf = new SimpleDateFormat(getDateFormat(type));
		return sdf.parse(value);
	}
	
	/**
	 * 將Calendar物件轉成Date物件
	 * @param calendar
	 * @return
	 * @throws DateFormatException
	 * @throws ParseException
	 * @author George_Tsai
	 * @version 2011/5/21
	 */
	public static Date toDate(Calendar calendar) throws DateFormatException, ParseException {
		return toDate(getDisplayDate(calendar, 23), 23);
	}
	
	/**
	 * 將日期字串(系統預設格式)轉成Date物件
	 * @param value
	 * @param type
	 * @return
	 * @author George_Tsai
	 * @version 2010/12/23
	 * @throws DateFormatException 
	 * @throws ParseException 
	 */
	public Date toDate(String value) throws DateFormatException, ParseException {
		AssertUtility.notNullAndNotSpace(value);
		
		return toDate(value, defaultDateFormat);
	}
}
