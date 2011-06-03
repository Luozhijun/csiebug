package csiebug.web.html.code;

import java.io.Serializable;

import csiebug.util.AssertUtility;
import csiebug.util.StringUtility;
import csiebug.web.html.HtmlBuilder;
import csiebug.web.html.HtmlComponent;


/**
 * 產生HTML Code
 * @author George_Tsai
 * @version 2009/8/7
 */

public class HtmlCode extends HtmlComponent implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int tabLength = 4;
	private String tabString = "&nbsp;&nbsp;&nbsp;&nbsp;";
	
	static final int NOTCOMMENT = 0;
	static final int ONELINECOMMENT = 1;
	static final int BLOCKCOMMENT = 2;
	
	private int commentMode = NOTCOMMENT;
	
	public HtmlCode(int tabLength) {
		setTabLength(tabLength);
	}
	
	public HtmlCode() {
		
	}
	
	public String renderStart() {
		HtmlBuilder htmlBuilder = new HtmlBuilder();
        htmlBuilder.tableStart();
        htmlBuilder.border("0");
        htmlBuilder.cellspacing("1");
        htmlBuilder.cellpadding("3");
        htmlBuilder.className("java");
        htmlBuilder.bgcolor("#999999");
        htmlBuilder.tagClose();
        
		return htmlBuilder.toString();
	}
	
	public String renderBody(String content) {
		AssertUtility.notNull(content);
		
		HtmlBuilder htmlBuilder = new HtmlBuilder();
        
        String[] lines = content.split("\n");
        
        int plusCount = 1;
        for(int i = 0; i < lines.length; i++) {
        	//扣掉code tag的前後可能有兩行沒用的內容要刪除
        	if(i == 0 && lines[i].trim().equals("")) {
        		plusCount = 0;
        		continue;
        	} else if(i == (lines.length - 1) && lines[i].trim().equals("")) {
        		continue;
        	} else {
            	htmlBuilder.tr();
            	
            	htmlBuilder.tdStart();
            	htmlBuilder.width("1");
            	htmlBuilder.align("left");
            	htmlBuilder.bgcolor("#dddddd");
            	htmlBuilder.tagClose();
            	
            	htmlBuilder.pre();
            	
            	htmlBuilder.fontStart();
            	htmlBuilder.color("#555555");
            	htmlBuilder.tagClose();
            	
            	htmlBuilder.text("" + (i + plusCount));
            	
            	htmlBuilder.fontEnd();
            	
            	htmlBuilder.preEnd();
            	
            	htmlBuilder.tdEnd();
            	
            	htmlBuilder.tdStart();
            	htmlBuilder.align("left");
            	htmlBuilder.bgcolor("#ffffff");
            	htmlBuilder.tagClose();
            	
            	htmlBuilder.pre();
            	
            	htmlBuilder.appendString(encodeLine(lines[i]));
            	
            	htmlBuilder.preEnd();
            	htmlBuilder.tdEnd();
            	htmlBuilder.trEnd();
        	}
        }
        
        return removeBaseIndent(htmlBuilder.toString(), lines);
	}
	
	public String renderEnd() {
		HtmlBuilder htmlBuilder = new HtmlBuilder();
        htmlBuilder.tableEnd();
        
        return htmlBuilder.toString();
	}
	
	/**
	 * 整體往左,去除不必要的縮排
	 * @param line
	 * @param lines
	 * @return
	 */
	private String removeBaseIndent(String line, String[] lines) {
		AssertUtility.notNull(line);
		AssertUtility.notNull(lines);
		
		return line.replaceAll("<pre>" + getBaseIndent(lines), "<pre>").replaceAll("<pre><font class=\"java-comment\">" + getBaseIndent(lines), "<pre><font class=\"java-comment\">");
	}
	
	/**
	 * 得到可移除的空白字串
	 * @param lines
	 * @return
	 * @author George_Tsai
	 * @version 2009/8/5
	 */
	private String getBaseIndent(String[] lines) {
		AssertUtility.notNull(lines);
		
		StringBuffer strTab = new StringBuffer();
		
		int minBaseSpaceCount = getBaseIndentCount(lines);
		
		for(int i = 0; i < minBaseSpaceCount; i++) {
			strTab.append("&nbsp;");
		}
		
		return strTab.toString();
	}
	
	/**
	 * 為了將全部程式碼的縮排往左,必須算出最小可縮排的數目
	 * @param lines
	 * @return
	 * @author George_Tsai
	 * @version 2009/8/5
	 */
	private int getBaseIndentCount(String[] lines) {
		AssertUtility.notNull(lines);
		
		//先給minCount一個極大數字,然後再縮小
		int minCount = 1000;
		
		for(int i = 0; i < lines.length; i++) {
			String line = lines[i];
			
        	//扣掉code tag的前後可能有兩行沒用的內容要刪除
        	if((i == 0 && line.trim().equals("")) || 
        	   (i == (lines.length - 1) && line.trim().equals(""))) {
        		continue;
        	} else {
        		int count = StringUtility.getStartsWithSpaceCount(line, tabLength);
        		
        		if(minCount > count) {
        			minCount = count;
        		}
        	}
        }
		
		return minCount;
	}
	
	/**
	 * 把程式碼中的字串和註解套上css style
	 * @param line
	 * @return
	 * @author George_Tsai
	 * @version 2009/8/6
	 */
	private String encodeLine(String line) {
		//前一行是單行註解,則本行會回復為非註解狀態,然後再處理本行
		if(commentMode == ONELINECOMMENT) {
			commentMode = NOTCOMMENT;
		}
		
		return encodeCode(line);
	}
	
	/**
	 * 不考慮換行的程式碼字串處理
	 * @param code
	 * @return
	 * @author George_Tsai
	 * @version 2009/8/6
	 */
	private String encodeCode(String code) {
		AssertUtility.notNull(code);
		
		StringBuffer strCode = new StringBuffer();
		
		if(commentMode == BLOCKCOMMENT) {
			if(code.indexOf("*/") == -1) {
				strCode.append(encodeCommentCode(code));
			} else {
				String strComment = code.substring(0, code.indexOf("*/") + 2);
				String strOthers = code.substring(code.indexOf("*/") + 2, code.length());
				
				strCode.append(encodeCommentCode(strComment));
				commentMode = NOTCOMMENT;
				
				strCode.append(encodeCode(strOthers));
			}
		} else {
			if(code.indexOf("//") == -1 && code.indexOf("/*") == -1) {
				commentMode = NOTCOMMENT;
				strCode.append(encodeUnCommentCode(code));
			} else if(StringUtility.ltrim(code).startsWith("//")) {
				commentMode = ONELINECOMMENT;
				strCode.append(encodeCommentCode(code));
			} else if(StringUtility.ltrim(code).startsWith("/*")) {
				commentMode = BLOCKCOMMENT;
				strCode.append(encodeCode(code));
			} else {
				if(code.indexOf("//") != -1 && code.indexOf("/*") != -1) {
					if(code.indexOf("//") < code.indexOf("/*")) {
						String strUnComment = code.substring(0, code.indexOf("//"));
						String strOthers = code.substring(code.indexOf("//"), code.length());
						
						strCode.append(encodeUnCommentCode(strUnComment));
						commentMode = ONELINECOMMENT;
						strCode.append(encodeCommentCode(strOthers));
					} else if(code.indexOf("//") > code.indexOf("/*")) {
						String strUnComment = code.substring(0, code.indexOf("/*"));
						String strOthers = code.substring(code.indexOf("/*"), code.length());
						
						strCode.append(encodeUnCommentCode(strUnComment));
						commentMode = NOTCOMMENT;
						strCode.append(encodeCode(strOthers));
					}
				} else if(code.indexOf("//") == -1) {
					String strUnComment = code.substring(0, code.indexOf("/*"));
					String strOthers = code.substring(code.indexOf("/*"), code.length());
					
					strCode.append(encodeUnCommentCode(strUnComment));
					commentMode = NOTCOMMENT;
					strCode.append(encodeCode(strOthers));
				} else if(code.indexOf("/*") == -1) {
					String strUnComment = code.substring(0, code.indexOf("//"));
					String strOthers = code.substring(code.indexOf("//"), code.length());
					
					strCode.append(encodeUnCommentCode(strUnComment));
					commentMode = ONELINECOMMENT;
					strCode.append(encodeCommentCode(strOthers));
				}
			}
		}
				
		return strCode.toString();
	}
	
	/**
	 * 註解的程式碼處理
	 * @param code
	 * @return
	 * @author George_Tsai
	 * @version 2009/8/6
	 */
	private String encodeCommentCode(String code) {
		AssertUtility.notNull(code);
		
		return "<font class=\"java-comment\">" + code.replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>").replaceAll("\t", tabString) + "</font>";
	}
	
	/**
	 * 非註解的程式碼處理
	 * @param code
	 * @return
	 * @author George_Tsai
	 * @version 2009/8/6
	 */
	private String encodeUnCommentCode(String code) {
		AssertUtility.notNull(code);
		
		StringBuffer strCode = new StringBuffer();
		
		String[] parseString = code.split("\"");
		for(int i = 0; i < parseString.length; i++) {
			if((i % 2) == 0) {
				strCode.append(parseString[i].replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>").replaceAll("\t", tabString));
			} else {
				strCode.append("<font class=\"java-string\">\"" + parseString[i].replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>").replaceAll("\t", tabString) + "\"</font>");
			}
		}
		
		return strCode.toString();
	}
	
//	元件屬性區
	/**
	 * 可自訂tab長度(預設長度4)
	 * @param spaceCount
	 * @author George_Tsai
	 * @version 2009/8/6
	 */
	public void setTabLength(int spaceCount) {
		tabLength = spaceCount;
		
		StringBuffer space = new StringBuffer();
		
		for(int i = 0; i < tabLength; i++) {
			space.append("&nbsp;");
		}
		
		tabString = space.toString();
	}
}
