package csiebug.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 字串常用工具
 * @author George_Tsai
 * @version 2009/2/3
 */

public class StringUtility {
	/**
	  * 數字開頭補零
	  * @param code
	  * @param length 總長度
	  * @return 補零後的字串
	  * @author George_Tsai
	  * @version 2008/6/25
	  */
	public static String addZero(int code, int length) {
		return addZero("" + code, length);
	}
	  
	/**
	  * 文字開頭補零
	  * @param code
	  * @param length 總長度
	  * @return 補零後的字串
	  * @author George_Tsai
	  * @version 2008/6/25
	  */
	public static String addZero(String code, int length) {
		AssertUtility.notNull(code);
		
		String strValue = code;
		int numOfZero = length - strValue.length();
			
		for(int i = 0; i < numOfZero; i++) {
			strValue = "0" + strValue;
		}
			
		return strValue;
	}
	
	/**
	  * 數字結尾補零
	  * @param code
	  * @param length 總長度
	  * @return 補零後的字串
	  * @author George_Tsai
	  * @version 2008/6/25
	  */
	public static String addZeroToTail(int code, int length) {
		return addZeroToTail("" + code, length);
	}
	  
	/**
	  * 文字結尾補零
	  * @param code
	  * @param length 總長度
	  * @return 補零後的字串
	  * @author George_Tsai
	  * @version 2008/6/25
	  */
	public static String addZeroToTail(String code, int length) {
		AssertUtility.notNull(code);
		
		StringBuffer strValue = new StringBuffer(code);
		int numOfZero = length - strValue.length();
			
		for(int i = 0; i < numOfZero; i++) {
			strValue.append("0");
		}
			
		return strValue.toString();
	}
		
	/**
	  * 去開頭的零
	  * @param code
	  * @return 去零後的字串
	  * @author George_Tsai
	  * @version 2008/6/25
	  */
	public static String delZero(String code) {
		AssertUtility.notNull(code);
		
		String strValue = code;
			
		while(strValue.indexOf("0") == 0) {
			strValue = strValue.substring(1, strValue.length());
		}
			
		return strValue;
	}
	
	/**
	  * 去結尾的零
	  * @param code
	  * @return 去零後的字串
	  * @author George_Tsai
	  * @version 2008/6/25
	  */
	public static String delZeroToTail(String code) {
		AssertUtility.notNull(code);
		
		String strValue = code;
			
		while(strValue.indexOf("0") == strValue.length() - 1) {
			strValue = strValue.substring(0, strValue.length() - 1);
		}
			
		return strValue;
	}
	
	/**
	 * 檢查HTML Style字串裡面的name屬性的值是否等於value
	 * @param styleString
	 * @param name
	 * @param value
	 * @return
	 * @author George_Tsai
	 * @version 2008/12/5
	 */
	public static boolean checkHtmlStyle(String styleString, String name, String value) {
		AssertUtility.notNull(styleString);
		AssertUtility.notNull(name);
		AssertUtility.notNull(value);
		
		boolean flag = false;
		
		if(styleString.indexOf(name) != -1 && styleString.indexOf(value) != -1) {
			String strTemp = styleString.substring(styleString.indexOf(name), styleString.indexOf(value)).replaceAll(name, "").replaceAll(" ", "");
			if(strTemp.equals(":")) {
				flag = true;
			}
		}
		
		return flag;
	}
	
	public static boolean isContainJavaScriptFunction(String scriptString, String functionName) {
		AssertUtility.notNull(scriptString);
		AssertUtility.notNull(functionName);
		
		boolean flag = false;
		
		if(scriptString.indexOf(functionName) != -1) {
			String strTemp = scriptString.substring(scriptString.indexOf(functionName), scriptString.length());
			
			if(strTemp.indexOf("(") != -1) {
				strTemp = strTemp.substring(0, strTemp.indexOf("("));
				strTemp = strTemp.replaceAll(" ", "");
				
				if(strTemp.equals(functionName)) {
					flag = true;
				}
			}
			
		}
		
		return flag;
	}
	
	/**
	 * 去掉字串開頭的空白
	 * @param value
	 * @return
	 * @throws Exception
	 * @author George_Tsai
	 * @version 2009/1/19
	 */
	public static String ltrim(String value) {
		AssertUtility.notNull(value);
		
		String strValue = value;
		
		while(strValue.startsWith(" ") || strValue.startsWith("\t") || strValue.startsWith("\n")) {
			if(strValue.startsWith(" ")) {
				strValue = strValue.replaceFirst(" ", "");
			} else if(strValue.startsWith("\t")) {
				strValue = strValue.replaceFirst("\t", "");
			} else if(strValue.startsWith("\n")) {
				strValue = strValue.replaceFirst("\n", "");
			}
		}
		
		return strValue;
	}
	
	/**
	 * 計算字串之前有多少空白
	 * @param code
	 * @param tabLength
	 * @return
	 * @author George_Tsai
	 * @version 2009/8/5
	 */
	public static int getStartsWithSpaceCount(String code, int tabLength) {
		AssertUtility.notNull(code);
		
		String tempString = code;
		
		int count = 0;
		while(tempString.startsWith(" ") || tempString.startsWith("\t")) {
			if(tempString.startsWith(" ")) {
    			tempString = tempString.replaceFirst(" ", "");
    			count++;
			} else {
				tempString = tempString.replaceFirst("\t", "");
    			count = count + tabLength;
			}
		}
		
		return count;
	}
	
	/**
	 * 取得檔名的副檔名
	 * @param fileName
	 * @return
	 * @throws Exception
	 * @author George_Tsai
	 * @version 2009/2/3
	 */
	public static String getExtensionFileName(String fileName) {
		AssertUtility.notNull(fileName);
		
		return fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
	}
	
	/**
	 * 駱駝命名法轉成下劃式命名法
	 * @param targetString
	 * @return
	 * @author George_Tsai
	 */
	public static String camelNamingtoUderlineNaming(String targetString) {
		AssertUtility.notNull(targetString);
		
		StringBuffer result = new StringBuffer();
		
		String temp = targetString.substring(0, 1).toLowerCase() + targetString.substring(1, targetString.length());
		char[] chars = temp.toCharArray();
		for(int i = 0; i < chars.length; i++) {
			if(Character.isUpperCase(chars[i])) {
				result.append("_" + Character.toLowerCase(chars[i]));
			} else {
				result.append(chars[i]);
			}
		}
		
		return result.toString();
	}
	
	/**
	 * 下劃式命名法轉成駱駝命名法
	 * @param targetString
	 * @return
	 * @author George_Tsai
	 */
	public static String uderlineNamingtoCamelNaming(String targetString) {
		AssertUtility.notNull(targetString);
		
		StringBuffer result = new StringBuffer();
		
		String temp = targetString.substring(0, 1).toLowerCase() + targetString.substring(1, targetString.length());
		char[] chars = temp.toCharArray();
		for(int i = 0; i < chars.length; i++) {
			if(i != 0 && chars[i - 1] == '_') {
				result.append(Character.toUpperCase(chars[i]));
			} else if(chars[i] != '_') {
				result.append(chars[i]);
			}
		}
		
		return result.toString();
	}
	
	/**
	 * 產生指定長度的亂數數字字串
	 * @param length
	 * @return
	 * @author George_Tsai
	 */
	public static String makeRandomNumberKey(int length) {
		StringBuffer key = new StringBuffer();
		
		for(int i = 0; i < length; i++) {
			Random random = new Random();
			key.append(random.nextInt(10));
		}
		
		return key.toString();
	}
	
	/**
	 * 產生指定長度的亂數大寫字母字串
	 * @param length
	 * @return
	 * @author George_Tsai
	 */
	public static String makeRandomUpperCaseLetterKey(int length) {
		char[] chars = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
		StringBuffer key = new StringBuffer();
		
		for(int i = 0; i < length; i++) {
			Random random = new Random();
			int index = random.nextInt(26);
			key.append(chars[index]);
		}
		
		return key.toString();
	}
	
	/**
	 * 產生指定長度的亂數小寫字母字串
	 * @param length
	 * @return
	 * @author George_Tsai
	 */
	public static String makeRandomLowerCaseLetterKey(int length) {
		return makeRandomUpperCaseLetterKey(length).toLowerCase();
	}
	
	/**
	 * 產生指定長度的亂數字串(數字、大小寫字母混合)
	 * @param length
	 * @return
	 * @author George_Tsai
	 */
	public static String makeRandomMixKey(int length) {
		StringBuffer key = new StringBuffer();
		
		for(int i = 0; i < length; i++) {
			Random random = new Random();
			int index = random.nextInt(3);

			if(index == 0) {
				key.append(makeRandomNumberKey(1));
			} else if(index == 1) {
				key.append(makeRandomUpperCaseLetterKey(1));
			} else {
				key.append(makeRandomLowerCaseLetterKey(1));
			}
		}
		
		return key.toString();
	}
	
	/**
	 * 避免XSS(Cross-Site Scripting)攻擊，必須檢查使用者輸入
	 * cracker可能輸入有惡意的script或是連到cracker要的惡意網址
	 * 有可能被存到DB或是直接輸出到網頁上，讓別的使用者執行到
	 * 所以在存入DB前或是輸出到頁面前，可能要做檢查
	 * 這邊的演算法是建立黑名單並排除，但黑名單可能需要更新
	 * @author George_Tsai
	 * @version 2009/12/3
	 */
	public static String cleanXSSPattern(String value) {
		AssertUtility.notNull(value);
		
		//置換HTML tag
		value = cleanHTMLTagPattern(value);
		
		//清除pattern
		value = cleanXSSPatternWithoutHTMLTag(value);
		
		return value;
	}
	
	/**
	 * 檢查字串是否是XSS Pattern
	 * @param value
	 * @return
	 * @author George_Tsai
	 * @version 2009/12/14
	 */
	public static boolean isXSSPattern(String value) {
		AssertUtility.notNull(value);
		
		return (value.indexOf("<") != -1 ||
				value.indexOf(">") != -1 ||
				value.indexOf("'") != -1 ||
				isXSSPatternWithoutHTMLTag(value));
	}
	
	/**
	 * 檢查字串是否是除了HTMLTag以外的XSS Pattern
	 * @param value
	 * @return
	 * @author George_Tsai
	 * @version 2009/12/14
	 */
	public static boolean isXSSPatternWithoutHTMLTag(String value) {
		AssertUtility.notNull(value);
		
		return (!isBalanceTag(value, "<", ">") ||
				(isBalanceTag(value, "<", ">") && value.indexOf("<") == -1 && value.indexOf("'") != -1) ||
				value.indexOf("(") != -1 ||
				value.indexOf(")") != -1 ||
				value.toLowerCase().indexOf("eval((.*))") != -1 ||
				value.toLowerCase().indexOf("[\"\'][s]*javascript:(.*)[\"\']") != -1 ||
				value.toLowerCase().indexOf("%2e%2e/") != -1 ||
				value.toLowerCase().indexOf("?sql_debug=") != -1 ||
				value.toLowerCase().indexOf("%5c") != -1 ||
				isScriptPattern(value) ||
				isOrEqualsPattern(value) ||
				isInvalidChangeDirectoryPattern(value));
	}
	
	/**
	 * 置換HTML tag
	 * @param value
	 * @return
	 * @author George_Tsai
	 * @version 2009/12/14
	 */
	public static String cleanHTMLTagPattern(String value) {
		AssertUtility.notNull(value);
		
		value = value.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
		value = value.replaceAll("'", "&#39;");
		
		return value;
	}
	
	/**
	 * 不置換HTML tag的情況下，要把有機會連結到惡意網址的pattern給移除掉
	 * @param value
	 * @return
	 * @author George_Tsai
	 * @version 2009/12/15
	 */
	public static String cleanHTMLSrcHrefPattern(String value) {
		AssertUtility.notNull(value);
		
		if(isInvalidHTMLTagPattern(value)) {
			value = value.replaceAll(" (?i)src", " ");
			value = value.replaceAll(" (?i)href", " ");
		}
		
		return value;
	}
	
	/**
	 * 含有src或href的屬性的HTML tag，嚴格的當作是不合法的
	 * 因為有機會加入惡意網址的連結
	 * @param value
	 * @return
	 * @author George_Tsai
	 * @version 2009/12/15
	 */
	public static boolean isInvalidHTMLTagPattern(String value) {
		AssertUtility.notNull(value);
		
		return (isBalanceTag(value, "<", ">") && (value.toLowerCase().indexOf(" src") != -1 || value.toLowerCase().indexOf(" href") != -1));
	}
	
	/**
	 * 固定一定要清除的pattern
	 * @param value
	 * @return
	 * @author George_Tsai
	 * @version 2009/12/14
	 */
	public static String cleanXSSPatternWithoutHTMLTag(String value) {
		AssertUtility.notNull(value);
		
		if(!isBalanceTag(value, "<", ">")) {
			value = cleanHTMLTagPattern(value);
		} else {
			if(value.indexOf("<") == -1) {
				value = cleanHTMLTagPattern(value);
			} else {
				value = cleanHTMLSrcHrefPattern(value);
			}
		}
		
		value = value.replaceAll("\\(", "&#40;").replaceAll("\\)", "&#41;");
		value = value.replaceAll("(?i)eval\\((.*)\\)", "");
		value = value.replaceAll("(?i)[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
		value = value.replaceAll("(?i)%2e%2e/", "");
		value = value.replaceAll("(?i)\\?sql_debug=", "");
		value = value.replaceAll("(?i)%5c", "");
		value = cleanScriptPattern(value);
		value = cleanOrEqualsPattern(value);
		value = cleanChangeDirectoryPattern(value);
		
		return value;
	}
	
	/**
	 * 清除CR與LF(可用來避免Header Manipulation攻擊)
	 * @param value
	 * @return
	 * @author George_Tsai
	 * @version 2009/12/14
	 */
	public static String cleanCRLF(String value) {
		AssertUtility.notNull(value);
		
		value = value.replaceAll("(?i)%0a", "");
		value = value.replaceAll("(?i)%0d", "");
		value = value.replaceAll("\n", "");
		value = value.replaceAll("\r", "");
		
		return value;
	}
	
	/**
	 * 清除"or xxx=xxx" pattern
	 * @param value
	 * @return
	 * @author George_Tsai
	 */
	public static String cleanOrEqualsPattern(String value) {
		AssertUtility.notNull(value);
		
		String processString = value;
		
		if(isOrEqualsPattern(processString)) {
			int startIndex = getOrIndex(value);
			int endIndex = value.indexOf("=");
			
			processString = processString.substring(0, startIndex) + processString.substring(endIndex + 1, processString.length());
			
			processString = cleanOrEqualsPattern(processString);
		}
		
		return processString;
	}
	
	/**
	 * 判斷字串是否有"or xxx=xxx" pattern
	 * @param value
	 * @return
	 * @author George_Tsai
	 */
	public static boolean isOrEqualsPattern(String value) {
		AssertUtility.notNull(value);
		
		boolean flag = false;
		
		int startIndex = getOrIndex(value);
		int endIndex = value.indexOf("=");
		
		if(startIndex != -1 && endIndex != -1 && startIndex < endIndex) {
			flag = true;
		}
		
		return flag;
	}
	
	public static String cleanScriptPattern(String value) {
		AssertUtility.notNull(value);
		
		if(isScriptPattern(value)) {
			value = value.replaceAll("(?i)script", "");
		}
		
		return value;
	}
	
	/**
	 * 因為有類似description這類的字，中間有含"script"
	 * 所以會誤判
	 * 所以另外拿出來判斷這個pattern
	 * @param value
	 * @return
	 * @author George_Tsai
	 * @version 2009/12/18
	 */
	public static boolean isScriptPattern(String value) {
		AssertUtility.notNull(value);
		
		boolean flag = false;
		
		if(value.toLowerCase().indexOf("script") != -1) {
			if(value.toLowerCase().indexOf("livescript") != -1) {
				flag = true;
			} else {
				String temp = value.substring(0, value.toLowerCase().indexOf("script"));
				if(temp.lastIndexOf("<") != -1) {
					temp = temp.substring(temp.lastIndexOf("<"), temp.length());
					if(temp.trim().equals("<")) {
						flag = true;
					}
				}
			}
		}
		
		return flag;
	}
	
	//找出or的位置
	private static int getOrIndex(String value) {
		AssertUtility.notNull(value);
		
		int startIndex = value.toLowerCase().indexOf(" or ");
		
		if(startIndex == -1) {
			startIndex = value.toLowerCase().indexOf("or ");
		}
		
		return startIndex;
	}
	
	/**
	 * 清除非法的上層目錄符號
	 * @param value
	 * @return
	 * @author George_Tsai
	 */
	public static String cleanChangeDirectoryPattern(String value) {
		AssertUtility.notNull(value);
		
		if(isInvalidChangeDirectoryPattern(value)) {
			return "";
		} else {
			return value;
		}
	}
	
	/**
	 * 有用到上層目錄符號的url,都當作是有危險的
	 * @param value
	 * @return
	 * @author George_Tsai
	 */
	public static boolean isInvalidChangeDirectoryPattern(String value) {
		AssertUtility.notNull(value);
		
		boolean flag = false;
		
		if(value.indexOf("../") != -1 ||
		   value.indexOf("..\\") != -1) {
			flag = true;
		}
		
		return flag;
	}
	
	/**
	 * 檢查字串的tag是否是平衡的
	 * @param value
	 * @return
	 * @author George_Tsai
	 * @version 2009/12/14
	 */
	public static boolean isBalanceTag(String value, String startTag, String endTag) {
		AssertUtility.notNull(value);
		AssertUtility.notNull(startTag);
		AssertUtility.notNull(endTag);
		
		int unBalanceTagCount = getUnBalanceTagCount(value, startTag, endTag, 0);
		
		return (unBalanceTagCount == 0);
	}
	
	private static int getUnBalanceTagCount(String value, String startTag, String endTag, int unBalanceTagCount) {
		AssertUtility.notNull(value);
		AssertUtility.notNull(startTag);
		AssertUtility.notNull(endTag);
		
		if(unBalanceTagCount >= 0) {
			//內容太長的話,跑recursive可能為有stack overflow的情況發生,須改成迴圈的方式執行
//			if(value.indexOf(startTag) != -1 && value.indexOf(endTag) != -1) {
//				if(value.indexOf(startTag) < value.indexOf(endTag)) {
//					return getUnBalanceTagCount(value.replaceFirst(startTag, ""), startTag, endTag, unBalanceTagCount + 1);
//				} else {
//					if(unBalanceTagCount > 0) {
//						return getUnBalanceTagCount(value.replaceFirst(endTag, ""), startTag, endTag, unBalanceTagCount - 1);
//					} else {
//						return -1;
//					}
//				}
//			} else if(value.indexOf(endTag) != -1) {
//				return getUnBalanceTagCount(value.replaceFirst(endTag, ""), startTag, endTag, unBalanceTagCount - 1);
//			} else if(value.indexOf(startTag) != -1) {
//				return -1;
//			}
			
			String temp = value;
			while(true) {
				if(temp.indexOf(startTag) != -1 && temp.indexOf(endTag) != -1) {
					if(temp.indexOf(startTag) < temp.indexOf(endTag)) {
						temp = temp.replaceFirst(startTag, "");
						unBalanceTagCount++;
					} else {
						if(unBalanceTagCount > 0) {
							temp = temp.replaceFirst(endTag, "");
							unBalanceTagCount--;
						} else {
							return -1;
						}
					}
				} else if(temp.indexOf(endTag) != -1) {
					temp = temp.replaceFirst(endTag, "");
					unBalanceTagCount--;
				} else if(temp.indexOf(startTag) != -1) {
					return -1;
				} else {
					break;
				}
			}
		}
		
		return unBalanceTagCount;
	}
	
	/**
	 * 文章做斷行處理
	 * @param originalString
	 * @param lineBreak
	 * @param maxLineLength
	 * @return
	 * @author George_Tsai
	 * @version 2009/12/17
	 */
	public static String getTypesettingString(String originalString, String lineBreak, int maxLineLength) {
		AssertUtility.notNull(originalString);
		AssertUtility.notNull(lineBreak);
		
		return getTypesettingString(originalString, lineBreak, maxLineLength, "");
	}
	
	/**
	 * 文章做斷行處理
	 * @param originalString
	 * @param lineBreak
	 * @param maxLineLength
	 * @param algorithm
	 * @return
	 * @author George_Tsai
	 * @version 2009/12/17
	 */
	public static String getTypesettingString(String originalString, String lineBreak, int maxLineLength, String algorithm) {
		AssertUtility.notNull(originalString);
		AssertUtility.notNull(lineBreak);
		AssertUtility.notNull(algorithm);
		
		StringBuffer result = new StringBuffer();
		
		List<String> list = new ArrayList<String>();
		
		if(algorithm.equalsIgnoreCase("simple")) {
			lineBreakAlgorithmSimple(originalString, list, maxLineLength);
		} else {
			lineBreakAlgorithm(originalString, list, maxLineLength);
		}
		
		for(int i = 0; i < list.size(); i++) {
			if(i != 0) {
				result.append(lineBreak);
			}
			result.append(list.get(i));
		}
		
		return result.toString();
	}
	
	/**
	 * 最簡單的演算法是固定長度就斷行
	 * @param originalString
	 * @param list
	 * @param maxLineLength
	 * @author George_Tsai
	 * @version 2009/12/17
	 */
	private static void lineBreakAlgorithmSimple(String originalString, List<String> list, int maxLineLength) {
		AssertUtility.notNull(originalString);
		AssertUtility.notNull(list);
		
		String temp = originalString;
		while(temp.length() > maxLineLength) {
			list.add(temp.substring(0, maxLineLength));
			temp = temp.substring(maxLineLength, temp.length());
		}
		
		if(temp.length() > 0) {
			list.add(temp);
		}
	}
	
	private static void lineBreakAlgorithm(String originalString, List<String> list, int maxLineLength) {
		AssertUtility.notNull(originalString);
		AssertUtility.notNull(list);
		
		String temp = originalString;
		while(temp.length() > maxLineLength) {
			int lineBreakIndex = getLastWordIndex(temp.substring(0, maxLineLength + 1));
			
			//如果是因為此字比單行長度限制還長
			//就放棄長度限制(只有這個字)，讓這個字自成一行
			if(lineBreakIndex == 0) {
				lineBreakIndex = maxLineLength + temp.substring(maxLineLength, temp.length()).indexOf(" ") + 1;
			}
			
			list.add(temp.substring(0, lineBreakIndex));
			temp = temp.substring(lineBreakIndex, temp.length());
		}
		
		if(temp.length() > 0) {
			list.add(temp);
		}
	}
	
	/**
	 * 取得此字串最後一個字詞的index
	 * @param originalString
	 * @return
	 * @author George_Tsai
	 */
	private static int getLastWordIndex(String originalString) {
		AssertUtility.notNull(originalString);
		
		int index = 1;
		
		if(originalString.length() >= 3) {
			String nextChar = originalString.substring(originalString.length() - 1, originalString.length());
			String lastChar = originalString.substring(originalString.length() - 2, originalString.length() - 1);
			
			if(isIdentifierPart(nextChar)) {
				index = originalString.lastIndexOf(" ") + 1;
			} else {
				if(isIdentifierPart(lastChar)) {
					index = originalString.lastIndexOf(" ") + 1;
				} else {
					index = originalString.length() - 1;
				}
			}
		}
		
		return index;
	}
	
	/**
	 * 檢查字串是否是由英文字母、數字、連接符號所組成
	 * Character所提供的無法區分中文的letter出來，所以使用自行開發的function
	 * @param value
	 * @return
	 * @author George_Tsai
	 */
	public static boolean isIdentifierPart(String value) {
		AssertUtility.notNull(value);
		
		String[] lowerCase = new String[]{"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
		String[] upperCase = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
		String[] number = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
		String[] connect = new String[]{"_", "-"};
		
		if(value.length() == 0) {
			return false;
		} else {
			for(int i = 0; i < value.length(); i++) {
				String ch = value.substring(i, i + 1);
				if(!isInArray(ch, lowerCase) &&
				   !isInArray(ch, upperCase) &&
				   !isInArray(ch, number) &&
				   !isInArray(ch, connect)) {
					return false;
				}
			}
			
			return true;
		}
	}
	
	/**
	 * 檢查字串開頭是否是英文字母
	 * Character所提供的無法區分中文的letter出來，所以使用自行開發的function
	 * @param value
	 * @return
	 * @author George_Tsai
	 */
	public static boolean isIdentifierStart(String value) {
		AssertUtility.notNull(value);
		
		String[] lowerCase = new String[]{"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
		String[] upperCase = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
		
		if(value.length() == 0) {
			return false;
		} else {
			String ch = value.substring(0, 1);
			return isInArray(ch, lowerCase) || isInArray(ch, upperCase);
		}
	}
	
	/**
	 * 檢查輸入字串是不是符合identifier規範
	 * Character所提供的無法區分中文的letter出來，所以使用自行開發的function
	 * @param name
	 * @return
	 * @author George_Tsai
	 */
	public static boolean isIdentifier(String name) {
		AssertUtility.notNull(name);
		
		return isIdentifierStart(name) && isIdentifierPart(name);
	}
	
	/**
	 * parse html字串,得到此tag的集合
	 * @param content
	 * @param name
	 * @return
	 * @author George_Tsai
	 */
	public static List<String> getElementsByTagName(String content, String name) {
		AssertUtility.notNull(content);
		AssertUtility.notNullAndNotSpace(name);
		
		List<String> list = new ArrayList<String>();
		
		String temp = content;
		while(temp.indexOf("<") != -1) {
			String temp2 = ltrim(temp.substring(temp.indexOf("<") + 1, temp.length()));
			
			//找到此tag開頭
			if(temp2.startsWith(name + " ")) {
				if(temp.indexOf(">") != -1) { //此tag開頭的結尾(<name )
					temp2 = temp.substring(temp.indexOf(">") + 1, temp.length());
						
					if(temp2.indexOf("<") != -1) { //尋找有沒有tag結尾(</name>)
						if(temp2.indexOf("<") == temp2.indexOf("</")) { //有</name>
							String temp3 = temp2.substring(temp2.indexOf(">") + 1, temp2.length());
							temp2 = temp2.substring(0, temp2.indexOf(">") + 1);
							
							list.add(temp.substring(temp.indexOf("<"), temp.indexOf(">") + 1) + temp2);
							temp = temp3;
						} else { //沒有</name>
							list.add(temp.substring(temp.indexOf("<"), temp.indexOf(">") + 1));
							temp = temp2;
						}
					} else {//沒有其他tag了,結束parse
						list.add(temp.substring(temp.indexOf("<"), temp.indexOf(">") + 1));
						temp = "";
					}
				} else { //此tag的開頭沒有結尾,不是完整的tag,不繼續做了,就此結束
					temp = "";
				}
			} else { //不是此tag
				if(temp.indexOf(">") != -1) { //繼續往下找下個tag
					temp = temp.substring(temp.indexOf(">") + 1, temp.length());
				} else { //不是完整的tag,不繼續做了,就此結束
					temp = "";
				}
			}
		}
		
		return list;
	}
	
	/**
	 * 取得開頭字串(不夠長度的會全部輸出,不造成index out of bound的exception)
	 * @param originalString
	 * @param length
	 * @return
	 */
	public static String substring(String originalString, int length) {
		AssertUtility.notNull(originalString);
		
		String partialString = "";
		
		if(originalString != null) {
			if(originalString.length() > length) {
				partialString = originalString.substring(0, length);
			} else {
				partialString = originalString;
			}
		}
		
		return partialString;
	}
	
	/**
	 * 去掉開頭與結尾的斜線"/"
	 * @param path
	 * @return
	 */
	public static String removeStartEndSlash(String path) {
		if(path != null) {
			if(path.startsWith("/")) {
				path = path.substring(1, path.length());
			}
			if(path.endsWith("/")) {
				path = path.substring(0, path.length() - 1);
			}
		}
		
		return path;
	}
	
	/**
	 * 查詢字串是否在String Array中有符合
	 * @param value
	 * @param values
	 * @return
	 * @throws Exception
	 * @author George_Tsai
	 * @version 2009/2/4
	 */
	public static boolean isInArray(String value, String[] values) {
		AssertUtility.notNull(value);
		AssertUtility.notNull(values);
		
		boolean flag = false;
		
		for(int i = 0; i < values.length; i++) {
			if(value.equals(values[i])) {
				flag = true;
				break;
			}
		}
		
		return flag;
	}
	
	/**
	 * 查詢字串結尾是否在String Array中有符合
	 * @param value
	 * @param values
	 * @return
	 * @throws Exception
	 * @author George_Tsai
	 * @version 2009/2/4
	 */
	public static boolean isEndsWithInArray(String value, String[] values) {
		AssertUtility.notNull(value);
		AssertUtility.notNull(values);
		
		boolean flag = false;
		
		for(int i = 0; i < values.length; i++) {
			if(value.endsWith(values[i])) {
				flag = true;
				break;
			}
		}
		
		return flag;
	}
	
	/**
	 * 查詢字串開頭是否在String Array中有符合
	 * @param value
	 * @param values
	 * @return
	 * @throws Exception
	 * @author George_Tsai
	 * @version 2009/2/4
	 */
	public static boolean isStartsWithInArray(String value, String[] values) {
		AssertUtility.notNull(value);
		AssertUtility.notNull(values);
		
		boolean flag = false;
		
		for(int i = 0; i < values.length; i++) {
			if(value.startsWith(values[i])) {
				flag = true;
				break;
			}
		}
		
		return flag;
	}
	
	/**
	 * 將字串轉換為unicode
	 * @param value
	 * @return
	 */
	public static String native2ascii(String value) {
	    StringBuffer unicode = new StringBuffer();
	    char[] chars = new char[value.length()];
	    
	    for(int i = 0; i < chars.length; i++) {
	    	chars[i] = value.charAt(i);
	    	unicode.append("\\u" + Integer.toString(chars[i], 16));
	    }
	    
	    return unicode.toString();
	}
	
	/**
	 * 分解Email
	 * @param emailAddress
	 * @return
	 * @throws EmailFormatException
	 */
	public static String[] parseEmail(String emailAddress) throws EmailFormatException {
		AssertUtility.notNullAndNotSpace(emailAddress);
		
		String[] emailPart = emailAddress.split("@");
		
		if(emailPart.length != 2) {
			throw new EmailFormatException("Email address is invalid!!");
		}
		
		return emailPart;
	}
	
	/**
	 * 用來解析指令,萃取出所有參數
	 * @param originalString
	 * @param command
	 * @return
	 * @throws StringParseException
	 */
	public static List<String> parseParameters(String originalString, String command) throws StringParseException {
		List<String> params = new ArrayList<String>();
		
		String temp = originalString;
		if(!command.equals("")) {
			temp = temp.replaceFirst(command + " ", "");
		}
		temp = StringUtility.ltrim(temp).trim();
		
		if(temp.indexOf("\"") == -1) {
			String[] tempParams = temp.split(" ");
			
			for(int i = 0; i < tempParams.length; i++) {
				if(!tempParams[i].equals("")) {
					params.add(tempParams[i]);
				}
			}
		} else {
			String[] tempParams = temp.split("\"");
			if((temp.endsWith("\"") && (tempParams.length % 2) == 0) ||
			   (!temp.endsWith("\"") && (tempParams.length % 2) == 1)) {
				for(int i = 0; i < tempParams.length; i++) {
					if(i % 2 == 0) {
						params.addAll(parseParameters(tempParams[i], ""));
					} else {
						params.add(tempParams[i]);
					}
				}
			} else {
				StringBuffer errorMessage = new StringBuffer("Parse parameter error! ");
				errorMessage.append("Original String: {" + temp + "} ");
				errorMessage.append("Split length: {" + tempParams.length + "} ");
				errorMessage.append("Parse result: {");
				for(int i = 0; i < tempParams.length; i++) {
					if(i != 0) {
						errorMessage.append(", ");
					}
					
					errorMessage.append(tempParams[i]);
				}
				errorMessage.append("}");
				
				throw new StringParseException(errorMessage.toString());
			}
		}
		
		return params;
	}
}
