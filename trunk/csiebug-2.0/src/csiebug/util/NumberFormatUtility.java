package csiebug.util;

import java.math.BigDecimal;

/**
 * 數字轉換工具
 * @author George_Tsai
 * @version 2009/3/20
 */

public class NumberFormatUtility {
	/**
	 * 傳入待處理的字串，取得三位一撇的字串
	 * @param 欲處理的字串(可接受逗號點錯的數字)
	 * @return 三位一撇的字串(若字串不合法回傳null)
	 */
	public static String getCurrency(String numString) {
		AssertUtility.notNullAndNotSpace(numString);
		
		boolean flagValid = isValidCurrency(numString);
		
		if(flagValid) {
			String value = numString.replaceAll(",", "");
			String[] tempResult;
			
			// 如果是負數的話,先取出負號,再做下面的處理
			tempResult = makeNegative(value);
			String negative = tempResult[0];
			value = tempResult[1];
	
			// 小數如果是0,要去掉小數點和小數
			tempResult = makeDecimal(value);
			String decimal = tempResult[0];
			value = tempResult[1];
	
			// 處理數字三位一個逗號
			value = makeCurrency(value);
				
			value = negative + value + decimal;
			
			return value;
		} else {
			throw new NumberFormatException("Invalid number string!!");
		}
	}
	
	private static String[] makeNegative(String value) {
		AssertUtility.notNullAndNotSpace(value);
		
		String[] result = new String[2];
		String negative = "";
		
		if (value.indexOf("-") != -1) {
			negative = "-";
			value = value.substring(value.indexOf("-") + 1, value.length());
		}
		
		result[0] = negative;
		result[1] = value;
		
		return result;
	}
	
	private static String[] makeDecimal(String value) {
		AssertUtility.notNullAndNotSpace(value);
		
		String[] result = new String[2];
		String decimal = "";
		
		if (value.indexOf(".") != -1) {
			decimal = value.substring(value.indexOf(".") + 1, value.length());
			value = value.substring(0, value.indexOf("."));

			if (Integer.parseInt(decimal) != 0) {
				decimal = "." + decimal;
			} else {
				decimal = "";
			}
		}
		
		result[0] = decimal;
		result[1] = value;
		
		return result;
	}
	
	private static String makeCurrency(String value) {
		AssertUtility.notNullAndNotSpace(value);
		
		value = value.replaceAll(",", "");
		String value2 = "";
		for(int i = 0; i < value.length(); i++) {
			if((i % 3) == 2) {
				value2 = "," + value.substring(value.length() - 1 - i, value.length() - i) + value2;
			} else {
				value2 = value.substring(value.length() - 1 - i, value.length() - i) + value2;
			}
		}
		if(value2.startsWith(",")) {
			value2 = value2.substring(1, value2.length());
		}
		
		return value2;
	}
	
	public static String getCurrency(int num) {
		return getCurrency("" + num);
	}
	
	/**
	 * 是否是合法正整數
	 * @param 欲檢查的字串
	 * @return true/false
	 */
	public static boolean isValidPositiveInteger(String numString) {
		boolean flagPositiveInteger = false;
		
		if(AssertUtility.isNotNullAndNotSpace(numString)) {
			String value = numString;
			
			for(int i = 0; i <= 9; i++) {
				value = value.replaceAll("" + i, "");
			}
			
			if(value.length() == 0) {
				flagPositiveInteger = true;
			}
		}
		
		return flagPositiveInteger;
	}
	
	/**
	 * 是否是合法正數
	 * @param 欲檢查的字串
	 * @return true/false
	 */
	public static boolean isValidPositive(String numString) {
		boolean flagPositive = true;
		
		if(AssertUtility.isNotNullAndNotSpace(numString)) {
			String value = numString;
			
			//檢查小數點
			if(value.indexOf(".") != -1 && value.indexOf(".") == value.lastIndexOf(".")) {
				value = value.replaceFirst("\\.", "");
			} else if(value.indexOf(".") != -1 && value.indexOf(".") != value.lastIndexOf(".")) {
				//有多個小數點是錯誤的數字
				flagPositive = false;
			}
			
			if(!value.trim().equals("")) {
				//去小數點以後，檢查是否是合法數字
				if(flagPositive) {
					flagPositive = isValidPositiveInteger(value);
				}
			} else {
				flagPositive = false;
			}
		} else {
			flagPositive = false;
		}
		
		return flagPositive;
	}
	
	/**
	 * 是否是合法負數
	 * @param 欲檢查的字串
	 * @return true/false
	 */
	public static boolean isValidNegative(String numString) {
		boolean flagNegative = true;
		
		if(AssertUtility.isNotNullAndNotSpace(numString)) {
			String value = numString;
			
			//檢查負號
			if(value.indexOf("-") != -1 && value.lastIndexOf("-") == 0) {
				//value = value.replaceFirst("-", ""); replaceFirst有bug
				value = value.replaceAll("-", "");
			} else if(value.indexOf("-") != -1 && value.lastIndexOf("-") != 0) {
				//有多個負號或是負號不在開頭，是錯誤的數字
				flagNegative = false;
			} else if(value.indexOf("-") == -1) {
				//不是負數
				flagNegative = false;
			}
			
			if(!value.trim().equals("")) {
				//去掉負號後檢查數字是否是合法正數
				if(flagNegative) {
					flagNegative = isValidPositive(value);
				}
			} else {
				flagNegative = false;
			}
		} else {
			flagNegative = false;
		}
		
		return flagNegative;
	}
	
	/**
	 * 是否是合法數字
	 * @param 欲檢查的字串
	 * @return true/false
	 */
	public static boolean isValid(String numString) {
		boolean flagValid = false;
		
		//合法數字不是合法正數不然就是合法負數
		if(AssertUtility.isNotNullAndNotSpace(numString) && (isValidPositive(numString) || isValidNegative(numString))) {
			flagValid = true;
		}
		
		return flagValid;
	}
	
	/**
	 * 是否是合法的金額格式(逗號點錯可接受為合法)
	 * @param 欲檢查的字串
	 * @return true/false
	 */
	public static boolean isValidCurrency(String numString) {
		boolean flagValid = false;
		
		if(AssertUtility.isNotNullAndNotSpace(numString)) {
			//去掉逗號
			String value = numString.replaceAll(",", "");
			
			if(!value.trim().equals("")) {
				//檢查是否是合法數字
				flagValid = isValid(value);
			}
		}
		
		return flagValid;
	}
	
	/**
	 * 數字比較v1>v2:true;v1<=v2:false
	 * @param 比較數字value1,value2
	 * @return true/false
	 */
	public static boolean compareCurrency(String value1, String value2) {
		AssertUtility.notNullAndNotSpace(value1);
		AssertUtility.notNullAndNotSpace(value2);
		
		if(isValidCurrency(value1) && isValidCurrency(value2)) {
			boolean flag = true;
	
			String v1 = value1.replaceAll(",", "");
			String v2 = value2.replaceAll(",", "");
			String v11 = "0";
			String v21 = "0";
			
			if(v1.indexOf(".") != -1) {
				v11 = v1.substring(v1.indexOf(".") + 1, v1.length());
				v1 = v1.substring(0, v1.indexOf("."));
			}
			if(v2.indexOf(".") != -1) {
				v21 = v2.substring(v2.indexOf(".") + 1, v2.length());
				v2 = v2.substring(0, v2.indexOf("."));
			}
			
			if(new BigDecimal(v1).compareTo(new BigDecimal(v2)) < 0) {
				flag = false;
			} else if(new BigDecimal(v1).compareTo(new BigDecimal(v2)) == 0 && v11.compareTo(v21) <= 0) {
				flag = false;
			}
			
			return flag;
		} else {
			throw new NumberFormatException("Invalid number string!!");
		}
	}
	
	/**
	 * 次方
	 * @param base
	 * @param power
	 * @return
	 */
	public static int power(int base, int power) {
		int result = 1;
		
		for(int i = 0; i < power; i++) {
			result = result * base;
		}
		
		return result;
	}
	
	/**
	 * 取出四捨五入之後的數字
	 * @param value
	 * @param decimalPosition
	 * @return
	 */
	public static String round(Float value, int decimalPosition) {
		AssertUtility.notNull(value);
		
		String currencyString = value.toString();
		
		if(currencyString.indexOf(".") != -1) {
			String integerString = currencyString.substring(0, currencyString.indexOf("."));
			
			if(currencyString.length() > currencyString.indexOf(".") + decimalPosition + 1) {
				String decimalString = currencyString.substring(currencyString.indexOf(".") + 1, currencyString.indexOf(".") + decimalPosition + 1);
				String checkString = currencyString.substring(currencyString.indexOf(".") + decimalPosition + 1, currencyString.indexOf(".") + decimalPosition + 2);
				
				//開始四捨五入
				if(Integer.parseInt(checkString) >= 5) {
					int decimal = Integer.parseInt(decimalString) + 1;
					if(decimal == power(10, decimalPosition)) {
						decimal = 0;
						integerString = "" + (Integer.parseInt(integerString) + 1);
					}
					
					decimalString = StringUtility.addZero(decimal, decimalPosition);
				}
				
				return integerString + "." + decimalString;
			} else {
				String decimalString = currencyString.substring(currencyString.indexOf(".") + 1, currencyString.length());
				return integerString + "." + StringUtility.addZeroToTail(decimalString, decimalPosition);
			}
		} else {
			return currencyString + "." + StringUtility.addZero(0, decimalPosition);
		}
	}
	
	/**
	 * 取出四捨五入之後的數字
	 * @param value
	 * @param decimalPosition
	 * @return
	 */
	public static String round(Double value, int decimalPosition) {
		AssertUtility.notNull(value);
		
		String currencyString = value.toString();
		
		if(currencyString.indexOf(".") != -1) {
			String integerString = currencyString.substring(0, currencyString.indexOf("."));
			
			if(currencyString.length() > currencyString.indexOf(".") + decimalPosition + 1) {
				String decimalString = currencyString.substring(currencyString.indexOf(".") + 1, currencyString.indexOf(".") + decimalPosition + 1);
				String checkString = currencyString.substring(currencyString.indexOf(".") + decimalPosition + 1, currencyString.indexOf(".") + decimalPosition + 2);
				
				//開始四捨五入
				if(Integer.parseInt(checkString) >= 5) {
					int decimal = Integer.parseInt(decimalString) + 1;
					if(decimal == power(10, decimalPosition)) {
						decimal = 0;
						integerString = "" + (Integer.parseInt(integerString) + 1);
					}
					
					decimalString = StringUtility.addZero(decimal, decimalPosition);
				}
				
				return integerString + "." + decimalString;
			} else {
				String decimalString = currencyString.substring(currencyString.indexOf(".") + 1, currencyString.length());
				return integerString + "." + StringUtility.addZeroToTail(decimalString, decimalPosition);
			}
		} else {
			return currencyString + "." + StringUtility.addZero(0, decimalPosition);
		}
	}
	
	/**
	 * 取出四捨五入之後的數字
	 * @param value
	 * @param decimalPosition
	 * @return
	 */
	public static String round(BigDecimal value, int decimalPosition) {
		AssertUtility.notNull(value);
		
		String currencyString = value.toString();
		
		if(currencyString.indexOf(".") != -1) {
			String integerString = currencyString.substring(0, currencyString.indexOf("."));
			
			if(currencyString.length() > currencyString.indexOf(".") + decimalPosition + 1) {
				String decimalString = currencyString.substring(currencyString.indexOf(".") + 1, currencyString.indexOf(".") + decimalPosition + 1);
				String checkString = currencyString.substring(currencyString.indexOf(".") + decimalPosition + 1, currencyString.indexOf(".") + decimalPosition + 2);
				
				//開始四捨五入
				if(Integer.parseInt(checkString) >= 5) {
					int decimal = Integer.parseInt(decimalString) + 1;
					if(decimal == power(10, decimalPosition)) {
						decimal = 0;
						integerString = "" + (Integer.parseInt(integerString) + 1);
					}
					
					decimalString = StringUtility.addZero(decimal, decimalPosition);
				}
				
				return integerString + "." + decimalString;
			} else {
				String decimalString = currencyString.substring(currencyString.indexOf(".") + 1, currencyString.length());
				return integerString + "." + StringUtility.addZeroToTail(decimalString, decimalPosition);
			}
		} else {
			return currencyString + "." + StringUtility.addZero(0, decimalPosition);
		}
	}
}
