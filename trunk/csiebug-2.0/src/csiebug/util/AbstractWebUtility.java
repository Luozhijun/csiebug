package csiebug.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameNotFoundException;
import javax.naming.NamingException;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.jopendocument.dom.spreadsheet.MutableCell;
import org.jopendocument.dom.spreadsheet.Sheet;
import org.jopendocument.dom.spreadsheet.SpreadSheet;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.io.StringWriter;

import csiebug.domain.User;
import csiebug.service.ServiceException;
import csiebug.service.ServiceLockException;
import csiebug.web.html.HtmlBuilder;

/**
 * Web AP常用功能
 * @author George_Tsai
 * @version 2009/3/27
 */

public abstract class AbstractWebUtility {
	private static final long serialVersionUID = 1L;
	
	private String strFunId = null;
	private String strMsg = "";
	private String strScript = "";
	
	private List<String> htmlControl = new ArrayList<String>();
	
	public AbstractWebUtility(String funId) {
		this.strFunId = funId;
		
//		所有會傳送html內容的request parameter，定義在這邊變成XSS的白名單
		htmlControl.add("GridHTML");
	}
	
	public AbstractWebUtility() {
//		所有會傳送html內容的request parameter，定義在這邊變成XSS的白名單
		htmlControl.add("GridHTML");
	}
	
	public void setFunctionId(String funId) {
		this.strFunId = funId;
	}
	
	public String getRequestValue(String name) throws NamingException {
		AssertUtility.notNullAndNotSpace(name);
		
		//本來就用來傳送HTML的request parameter
		//為了client端的安全，還是濾除掉除了HTML tag以外的XSS pattern
		//HTML的傳送全部都只能用來純顯示使用，不能有任何可執行的東西
		if(htmlControl.contains(name)) {
			return getRequestValue(name, true);
		} else {
			return getRequestValue(name, false);
		}
	}
	
	/**
	 * 檢查使用者輸入，這邊檢查輸入裡面是否含有XSS攻擊
	 * 如果有另外的邏輯需要判斷輸入是否合法，每個Action可以自行改寫validateInput
	 * @return
	 * @author George_Tsai
	 * @throws NamingException 
	 */
	public boolean validateInput() throws NamingException {
		HttpServletRequest request = getRequest();
		
		AssertUtility.notNull(request);
		
		boolean flag = true;
		
		Enumeration<String> parameterNames = request.getParameterNames();
		while(parameterNames.hasMoreElements()) {
			String parameterName = parameterNames.nextElement();
			
			//這裡可以忽略一些本來就用來傳送HTML碼的控制項所傳的HTML
			//門打開當然就有風險了
			//可配合getRequestValue來使用，把可在client端執行的東西濾掉
			if(htmlControl.contains(parameterName)) {
				//因為grid裡面可能會有script的字串或圖檔超連結等，所以還是不要做正確性阻擋
				//反正getRequestValue會把有危險的字串濾除或轉換，所以應無安全性的問題
//					if(StringUtility.isXSSPatternWithoutHTMLTag(request.getParameter(parameterName))) {
//						flag = false;
//						break;
//					}
				continue;
			} else {
				if(StringUtility.isXSSPattern(request.getParameter(parameterName))) {
					flag = false;
					break;
				}
			}
		}
		return flag;
	}
	
	/**
	 * 增加會傳送html內容的control名稱，成為XSS的白名單(但還是會濾除除了HTML tag以外的內容)
	 * @param parameterName
	 * @return
	 * @author George_Tsai
	 * @version 2009/12/14
	 */
	public void addHTMLControl(String parameterName) {
		AssertUtility.notNullAndNotSpace(parameterName);
		
		htmlControl.add(parameterName);
	}
	
	private String getRequestValue(String name, boolean htmlControl) throws NamingException {
		HttpServletRequest request = getRequest();
		
		AssertUtility.notNull(request);
		AssertUtility.notNullAndNotSpace(name);
		
		String strValue = "";
		
		if(request.getParameter(name) != null) {
			if(htmlControl) {
				//本來就用來傳送HTML的request parameter
				//為了client端的安全，還是濾除掉除了HTML tag以外的XSS pattern
				//HTML的傳送全部都只能用來純顯示使用，不能有任何可執行的東西
				strValue = StringUtility.cleanXSSPatternWithoutHTMLTag(request.getParameter(name));
			} else {
				strValue = StringUtility.cleanXSSPattern(request.getParameter(name));
			}
		}
		
		return strValue;
	}
	
	public Object getRequestAttribute(String name) {
		HttpServletRequest request = getRequest();
		
		AssertUtility.notNull(request);
		AssertUtility.notNullAndNotSpace(name);
		
		return request.getAttribute(name);
	}
	
	public void setRequestAttribute(String name, Object obj) {
		HttpServletRequest request = getRequest();
		
		AssertUtility.notNull(request);
		AssertUtility.notNullAndNotSpace(name);
		
		request.setAttribute(name, obj);
	}
	
	public void removeRequestAttribute(String name) {
		HttpServletRequest request = getRequest();
		
		AssertUtility.notNull(request);
		AssertUtility.notNullAndNotSpace(name);
		
		request.removeAttribute(name);
	}
	
	public void removeAllRequestAttribute() {
		HttpServletRequest request = getRequest();
		
		AssertUtility.notNull(request);
		
		Enumeration<String> attributeNames = request.getAttributeNames();
		while(attributeNames.hasMoreElements()) {
			removeRequestAttribute(attributeNames.nextElement());
		}
	}
	
	public Object getSessionAttribute(String name) {
		HttpSession session = getSession();
		
		AssertUtility.notNull(session);
		
		return session.getAttribute(name);
	}
	
	public void setSessionAttribute(String name, Object obj) {
		HttpSession session = getSession();
		
		AssertUtility.notNull(session);
		AssertUtility.notNullAndNotSpace(name);
		
		session.setAttribute(name, obj);
	}
	
	public void removeSessionAttribute(String name) {
		HttpSession session = getSession();
		
		AssertUtility.notNull(session);
		AssertUtility.notNullAndNotSpace(name);
		
		getSession().removeAttribute(name);
	}
	
	public void removeAllSessionAttribute() {
		HttpSession session = getSession();
		
		AssertUtility.notNull(session);
		
		Enumeration<String> attributeNames = getSession().getAttributeNames();
		while(attributeNames.hasMoreElements()) {
			removeSessionAttribute(attributeNames.nextElement());
		}
	}
	
	public Object getAppAttribute(String name) {
		ServletContext context = getServletContext();
		
		AssertUtility.notNull(context);
		AssertUtility.notNullAndNotSpace(name);
		
		return context.getAttribute(name);
	}
	
	public void setAppAttribute(String name, Object obj) {
		ServletContext context = getServletContext();
		
		AssertUtility.notNull(context);
		AssertUtility.notNullAndNotSpace(name);
		
		context.setAttribute(name, obj);
	}
	
	public void removeAppAttribute(String name) {
		ServletContext context = getServletContext();
		
		AssertUtility.notNull(context);
		AssertUtility.notNullAndNotSpace(name);
		
		context.removeAttribute(name);
	}
	
	public void removeAllAppAttribute() {
		ServletContext context = getServletContext();
		
		AssertUtility.notNull(context);
		
		Enumeration<String> attributeNames = context.getAttributeNames();
		while(attributeNames.hasMoreElements()) {
			removeAppAttribute(attributeNames.nextElement());
		}
	}
	
	public String getRequestURLWithQueryString() throws NamingException {
		HttpServletRequest request = getRequest();
		
		AssertUtility.notNull(request);
		
		StringBuffer prePageURL = request.getRequestURL();
			
		Enumeration<String> parameterNames = request.getParameterNames();
		int i = 0;
		while(parameterNames.hasMoreElements()) {
			String parameterName = parameterNames.nextElement();
			
			if(i == 0) {
				prePageURL.append("?" + parameterName + "=" + getRequestValue(parameterName));
				i++;
			} else {
				prePageURL.append("&" + parameterName + "=" + getRequestValue(parameterName));
			}
		}
		
		return prePageURL.toString();
	}
	
	public String getFunctionId() {
		if(!AssertUtility.isNotNullAndNotSpace(strFunId)) {
			String className = this.getClass().getSimpleName();
			if(className.endsWith("Action")) {
				strFunId = className.substring(0, className.length() - 6);
			} else {
				strFunId = className;
			}
		}
		
		return strFunId;
	}
	
	/**
	 * 取得MessageResources的對應文字
	 * 以系統環境變數sysLocale為優先，若沒有設定則以request的locale來設定，若都無法對應則取預設值(繁體中文)
	 * @param key
	 * @return
	 * @throws Exception
	 * @author George_Tsai
	 * @version 2009/3/27
	 * @throws NamingException 
	 * @throws UnsupportedEncodingException 
	 */
	public String getMessage(String key) throws UnsupportedEncodingException, NamingException {
		AssertUtility.notNullAndNotSpace(key);
		
		return getMessage(key, null);
	}
	
	/**
	 * 取得系統DB的JDBC名稱
	 * @return
	 * @throws Exception
	 * @author George_Tsai
	 * @version 2009/2/17
	 * @throws NamingException 
	 */
	public String getSysDbJdbcName() throws NamingException {
		return getEnvVariable("sysDB");
	}
	
	/**
	 * 取得權限控管登入頁URL
	 * @return
	 * @throws Exception
	 * @author George_Tsai
	 * @version 2009/2/17
	 */
	public String getSysURL() throws NamingException {
		return getEnvVariable("sysAPURL");
	}
	
	/**
	 * 取得系統的日期格式
	 * @return
	 * @throws Exception
	 * @author George_Tsai
	 * @version 2009/6/22
	 */
	public String getSysDateFormat() throws NamingException {
		return getEnvVariable("defaultDateFormat");
	}
	
	/**
	 * 取得此系統的APId
	 * @return
	 * @throws Exception
	 * @author George_Tsai
	 * @version 2009/2/18
	 */
	public String getAPId() throws NamingException {
		return getEnvVariable("APID");
	}
	
	/**
	 * 取得此系統Cookie的life cycle
	 * @return
	 * @throws Exception
	 * @author George_Tsai
	 * @version 2009/10/23
	 */
	public int getCookieLifecycle() throws NamingException {
		return Integer.parseInt(getEnvVariable("cookieLifecycle"));
	}
	
	/**
	 * 取得AP root的相對路徑
	 * 這只是給頁面程式使用，要取得javascript,css和image的相對路徑
	 * 如此不管頁面在哪個目錄層級底下，都可以利用這個方法正確取得js,css和image
	 * @return
	 * @throws Exception
	 * @author George_Tsai
	 * @version 2009/4/22
	 */
	public String getBasePathForHTML() throws NamingException {
		StringBuffer basePath = new StringBuffer();
		
		if(AssertUtility.isNotNullAndNotSpace(getAPId())) {
			basePath.append("/" + getAPId() + "/");
		} else {
			basePath.append("/");
		}
		
		return basePath.toString();
	}
	
	/**
	 * 取得此系統的語系
	 * @return
	 * @throws Exception
	 * @author George_Tsai
	 * @version 2009/3/26
	 */
	public Locale getSysLocale() throws NamingException {
		if(AssertUtility.isNotNullAndNotSpace(getEnvVariable("sysLocale"))) {
			String language = "";
			String country = "";
			Locale locale = null;
			
			if(getEnvVariable("sysLocale").split("_").length == 1) {
				language = getEnvVariable("sysLocale").split("_")[0];
				locale = new Locale(language);
			} else if(getEnvVariable("sysLocale").split("_").length == 2) {
				language = getEnvVariable("sysLocale").split("_")[0];
				country = getEnvVariable("sysLocale").split("_")[1];
				locale = new Locale(language, country);
			}
			
			return locale;
		} else {
			return null;
		}
	}
	
	/**
	 * 取得系統環境變數
	 * @param name
	 * @return
	 * @throws Exception
	 * @author George_Tsai
	 * @version 2009/3/26
	 */
	public String getEnvVariable(String name) throws NamingException {
		AssertUtility.notNullAndNotSpace(name);
		
		Context initContext = new InitialContext();
	    Context envContext = (Context)initContext.lookup("java:/comp/env");
	    
	    String strResult = null;
	    try {
	    	strResult = StringUtility.cleanXSSPattern((String)envContext.lookup(name));
	    } catch(NameNotFoundException nnfex) {
	    	String message = "Name " + name + " is not bound in this Context!";
	    	getLogger().debug(message);
//		    	System.out.println(message);
	    }
	    
	    return strResult;
	}
	
	/**
	 * 取得系統環境變數
	 * @param name
	 * @return
	 * @throws Exception
	 * @author George_Tsai
	 * @version 2011/4/26
	 */
	public List<String> getEnvVariables(String name) throws NamingException {
		AssertUtility.notNullAndNotSpace(name);
		
		Context initContext = new InitialContext();
	    Context envContext = (Context)initContext.lookup("java:/comp/env");
	    
	    List<String> list = new ArrayList<String>();
	    try {
	    	String[] temp = StringUtility.cleanXSSPattern((String)envContext.lookup(name)).split("\n");
	    	for(int i = 0; i < temp.length; i++) {
	    		String envVariable = StringUtility.ltrim(temp[i]).trim();
	    		
	    		if(AssertUtility.isNotNullAndNotSpace(envVariable)) {
	    			list.add(envVariable);
	    		}
	    	}
	    } catch(NameNotFoundException nnfex) {
	    	String message = "Name " + name + " is not bound in this Context!";
	    	getLogger().debug(message);
//		    	System.out.println(message);
	    }
	    
	    return list;
	}
	
	/**
	 * js的alert訊息
	 * @param script
	 * @throws Exception
	 */
	public void addPageLoadMessage(String msg) {
		strMsg = strMsg + "\n alert('" + msg + "');";
		setRequestAttribute("Msg", strMsg);
	}
	
	/**
	 * js的alert訊息可以累加
	 * 這個function是要把之前累加的訊息清除
	 */
	public void resetPageLoadMessage() {
		strMsg = "";
	}
	
	/**
	 * 自訂訊息視窗
	 * @param targetControl
	 * @param title
	 * @param msg
	 * @param button
	 * @param focusControl
	 * @param script
	 * @throws Exception
	 * @author George_Tsai
	 * @version 2009/6/18
	 */
	public void addPageLoadMessage(String targetControl, String title, String msg, String button, String focusControl, String script) {
		strMsg = "";
		if(AssertUtility.isNotNullAndNotSpace(targetControl)) {
			strMsg = strMsg + "\n var targetControl = document.getElementById('" + targetControl +"');\n";
			
			addOpenAlertDialogScript("targetControl", focusControl, title, msg, button, script);
		} else {
			addOpenAlertDialogScript("null", focusControl, title, msg, button, script);
		}
		
		setRequestAttribute("Msg", strMsg);
	}
	
	private void addOpenAlertDialogScript(String targetControl, String focusControl, String title, String msg, String button, String script) {
		if(!AssertUtility.isNotNullAndNotSpace(script)) {
			script = "";
		}
		
		if(AssertUtility.isNotNullAndNotSpace(focusControl)) {
			strMsg = strMsg + "\n var focusControl = document.getElementById('" + focusControl +"');\n";
			strMsg = strMsg + "\n openAlertDialog(" + targetControl + ", '" + title + "','" + msg + "', '" + button + "', '" + script + "', focusControl, 0);";
		} else {
			strMsg = strMsg + "\n openAlertDialog(" + targetControl + ", '" + title + "','" + msg + "', '" + button + "', '" + script + "', null, 0);";
		}
	}
	
	/**
	 * 自訂訊息視窗
	 * @param title
	 * @param msg
	 * @param button
	 * @throws Exception
	 * @author George_Tsai
	 * @version 2009/6/18
	 */
	public void addPageLoadMessage(String title, String msg, String button) {
		addPageLoadMessage(null, title, msg, button, null, "");
	}
	
	/**
	 * 自訂警告訊息視窗
	 * @param msg
	 * @throws Exception
	 * @author George_Tsai
	 * @version 2009/6/18
	 * @throws NamingException 
	 * @throws UnsupportedEncodingException 
	 */
	public void addPageLoadWarningDialog(String targetControl, String msg, String focusControl, String script) throws UnsupportedEncodingException, NamingException {
		addPageLoadMessage(targetControl, getMessage("common.warning"), msg, getMessage("common.ok"), focusControl, script);
	}
	
	/**
	 * 自訂錯誤訊息視窗
	 * @param msg
	 * @throws Exception
	 * @author George_Tsai
	 * @version 2009/6/18
	 * @throws NamingException 
	 * @throws UnsupportedEncodingException 
	 */
	public void addPageLoadErrorDialog(String targetControl, String msg, String focusControl, String script) throws UnsupportedEncodingException, NamingException {
		addPageLoadMessage(targetControl, getMessage("common.error"), msg, getMessage("common.ok"), focusControl, script);
	}
	
	/**
	 * 自訂訊息視窗
	 * @param msg
	 * @throws Exception
	 * @author George_Tsai
	 * @version 2009/6/18
	 * @throws NamingException 
	 * @throws UnsupportedEncodingException 
	 */
	public void addPageLoadMessageDialog(String targetControl, String msg, String focusControl, String script) throws UnsupportedEncodingException, NamingException {
		addPageLoadMessage(targetControl, getMessage("common.message"), msg, getMessage("common.ok"), focusControl, script);
	}
	
	/**
	 * 自訂警告訊息視窗
	 * @param msg
	 * @throws Exception
	 * @author George_Tsai
	 * @version 2009/6/17
	 * @throws NamingException 
	 * @throws UnsupportedEncodingException 
	 */
	public void addPageLoadWarningDialog(String msg) throws UnsupportedEncodingException, NamingException {
		addPageLoadWarningDialog(null, msg, null, "");
	}
	
	/**
	 * 自訂錯誤訊息視窗
	 * @param msg
	 * @throws Exception
	 * @author George_Tsai
	 * @version 2009/6/17
	 * @throws NamingException 
	 * @throws UnsupportedEncodingException 
	 */
	public void addPageLoadErrorDialog(String msg) throws UnsupportedEncodingException, NamingException {
		addPageLoadErrorDialog(null, msg, null, "");
	}
	
	/**
	 * 自訂訊息視窗
	 * @param msg
	 * @throws Exception
	 * @author George_Tsai
	 * @version 2009/6/17
	 * @throws NamingException 
	 * @throws UnsupportedEncodingException 
	 */
	public void addPageLoadMessageDialog(String msg) throws UnsupportedEncodingException, NamingException {
		addPageLoadMessageDialog(null, msg, null, "");
	}
	
	public void addPageLoadScript(String script) {
		strScript = (String)getRequestAttribute("Script");
		if(strScript == null) {
			strScript = "";
		}
		
		strScript = strScript + "\n" + script;
		setRequestAttribute("Script", strScript);
	}
	
	public void resetPageLoadScript() {
		strScript = "";
		setRequestAttribute("Script", "");
	}
	
	/**
	 * 下載檔案
	 * @param File物件
	 * @param 檔案型別(如pdf等)
	 * @throws IOException 
	 * @throws Exception
	 */
	public void download(File file, String fileType, String encoding) throws IOException {
	    if(file != null && file.exists()) {
	      FileInputStream fis = new FileInputStream(file);
	      ServletOutputStream sos = null;
	      
	      try {
		      getResponse().reset();
		      if(AssertUtility.isNotNullAndNotSpace(fileType)) {
		    	  getResponse().setContentType("application/" + fileType);
		      }
		      getResponse().setHeader("Content-disposition", "attachment; filename=\"" + file.getName() + "\"");
		      if(AssertUtility.isNotNullAndNotSpace(encoding)) {
		    	  getResponse().setCharacterEncoding(encoding);
		      }
//		      getResponse().setContentLength(Integer.parseInt(Long.toString(file.length())));
	
		      byte[] data = new byte[(int)file.length()];
		      fis.read(data);
		      
		      sos = getResponse().getOutputStream();
		      sos.write(data);
		  } finally {
	    	  fis.close();
	    	  
	    	  if(sos != null) {
	    		  sos.flush();
			      sos.close();
	    	  }
	      }
	    } else {
	    	throw new FileNotFoundException();
	    }
	}
	
	/**
	 * 下載檔案
	 * @param 檔案名稱
	 * @param 內容
	 * @param 檔案型別(如pdf等)
	 * @throws Exception
	 */
	public void download(String fileName, byte[] content, String fileType, String encoding) throws IOException {
		AssertUtility.notNull(content);
		
		getResponse().reset();
		if(AssertUtility.isNotNullAndNotSpace(fileType)) {
			getResponse().setContentType("application/" + fileType);
		}
		if(AssertUtility.isNotNullAndNotSpace(fileName)) {
			getResponse().setHeader("Content-disposition", "attachment; filename=\"" + StringUtility.cleanCRLF(fileName) + "\"");
		}
		if(AssertUtility.isNotNullAndNotSpace(encoding)) {
			getResponse().setCharacterEncoding(encoding);
		}
//			getResponse().setContentLength(Integer.parseInt(Long.toString(content.length)));

	    ServletOutputStream sos = null;
	    try {
		    sos = getResponse().getOutputStream();
		    sos.write(content);
	    } finally {
	    	if(sos != null) {
			    sos.flush();
			    sos.close();
	    	}
	    }
	}
	
	/**
	 * 下載檔案
	 * @param 檔案名稱
	 * @param 內容
	 * @param 檔案型別(如pdf等)
	 * @throws Exception
	 */
	public void download(String fileName, String content, String fileType) throws IOException {
		AssertUtility.notNull(content);
		
		download(fileName, content, "UTF-8", fileType);
	}
	
	/**
	 * 下載Excel,用POI產生excel檔
	 * @param fileName
	 * @param content
	 * @throws IOException
	 * @throws DocumentException
	 */
	public void downloadExcelUsingPOI(String fileName, String content) throws IOException, DocumentException {
		AssertUtility.notNull(content);
		
		downloadExcelUsingPOI(fileName, content, "UTF-8");
	}
	
	/**
	 * 下載Excel,用POI產生excel檔(將html table轉成excel)
	 * @param fileName
	 * @param content
	 * @param encoding
	 * @throws IOException
	 * @throws DocumentException
	 */
	@SuppressWarnings("unchecked")
	public void downloadExcelUsingPOI(String fileName, String content, String encoding) throws IOException, DocumentException {
		AssertUtility.notNull(content);
		
		String encodedContent = new String(content.getBytes("ISO-8859-1"), encoding);
		
		Document doc;
		try {
			doc = DocumentHelper.parseText(encodedContent);
		} catch(DocumentException ex) {
			//用IE把html字串submit過來,會被IE處理過,所以丟給parseText會錯
			//必須把被IE改過的字串改回來
			doc = DocumentHelper.parseText(encodedContent.replaceAll("P align=center", "p align=\"center\"").replaceAll("</P>", "</p>"));
		}
		
		HSSFWorkbook excel = new HSSFWorkbook();
		HSSFSheet sheet = excel.createSheet();
		
		Element html = doc.getRootElement();
		Element body = html.element("body");
		Element table = body.element("table");
		List<Element> trs = table.elements("tr");
		
		for(int i = 0; i < trs.size(); i++) {
			HSSFRow row = sheet.createRow(i);
			
			Element tr = trs.get(i);
			List<Element> tds = tr.elements("td");
			
			for(int j = 0; j < tds.size(); j++) {
				HSSFCell cell = row.createCell(j);
				
				Element td = tds.get(j);
				Attribute tdStyle = td.attribute("style");
				Element p = td.element("p");
				
				HSSFCellStyle style = excel.createCellStyle();
				
				//塗滿顏色(標頭、奇數偶數列)
				style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
				if(i == 0) {
					style.setFillForegroundColor(HSSFColor.LIGHT_BLUE.index);
				} else if((i % 2) != 0) {
					style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
				} else {
					style.setFillForegroundColor(HSSFColor.WHITE.index);
				}
				
				//對齊與設值
				if(p != null) {
					String align = p.attribute("align").getValue();
					if(align.equalsIgnoreCase("center")) {
						style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
					} else if(align.equalsIgnoreCase("right")) {
						style.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
					} else if(align.equalsIgnoreCase("left")) {
						style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
					}
					
					cell.setCellValue(p.getText());
				} else if(tdStyle != null) {
					if(tdStyle.getValue().indexOf("mso-number-format") != -1) {
						cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
						style.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
					}
					
					cell.setCellValue(td.getText());
				} else {
					cell.setCellValue(td.getText());
				}
				
				cell.setCellStyle(style);
			}
		}
		
		getResponse().reset();
		getResponse().setContentType("application/vnd.ms-excel");
		if(AssertUtility.isNotNullAndNotSpace(fileName)) {
			getResponse().setHeader("Content-disposition", "attachment; filename=\"" + StringUtility.cleanCRLF(fileName) + "\"");
		}
		if(AssertUtility.isNotNullAndNotSpace(encoding)) {
			getResponse().setCharacterEncoding(encoding);
		}

	    ServletOutputStream sos = null;
	    
	    try {
		    sos = getResponse().getOutputStream();
		    excel.write(sos);
	    } finally {
	    	if(sos != null) {
			    sos.flush();
			    sos.close();
	    	}
	    }
	}
	
	/**
	 * 下載ODS檔
	 * @param fileName
	 * @param content
	 * @throws IOException
	 * @throws DocumentException
	 */
	public void downloadODS(String fileName, String content) throws IOException, DocumentException {
		AssertUtility.notNull(content);
		
		downloadODS(fileName, content, "UTF-8");
	}
	
	/**
	 * 下載ODS檔(將html table轉成ods)
	 * @param fileName
	 * @param content
	 * @param encoding
	 * @throws IOException
	 * @throws DocumentException
	 */
	@SuppressWarnings("unchecked")
	public void downloadODS(String fileName, String content, String encoding) throws IOException, DocumentException {
		//TODO: jOpenDocument的bug,資料行到100筆之後設定style會有jdom attribute null的exception發生
		int maxRecord = 100;
		
		AssertUtility.notNull(content);
		
		String encodedContent = new String(content.getBytes("ISO-8859-1"), encoding);
		
		Document doc;
		try {
			doc = DocumentHelper.parseText(encodedContent);
		} catch(DocumentException ex) {
			//用IE把html字串submit過來,會被IE處理過,所以丟給parseText會錯
			//必須把被IE改過的字串改回來
			doc = DocumentHelper.parseText(encodedContent.replaceAll("P align=center", "p align=\"center\"").replaceAll("</P>", "</p>"));
		}
		
		Element html = doc.getRootElement();
		Element body = html.element("body");
		Element table = body.element("table");
		List<Element> trs = table.elements("tr");
		
		String[] odsTitles = new String[trs.get(0).elements("td").size()];
		Object[][] odsDatas = new Object[trs.size()][trs.get(0).elements("td").size()];
		TableModel model = new DefaultTableModel(odsDatas, odsTitles);
		File tempFile = new File(getServletContext().getRealPath("/") + "/temp/temp.ods");
	    SpreadSheet spreadSheet = SpreadSheet.createEmpty(model);
	    Sheet sheet = spreadSheet.getSheet(0);
		
		for(int i = 0; i < trs.size(); i++) {
			Element tr = trs.get(i);
			List<Element> tds = tr.elements("td");
			
			for(int j = 0; j < tds.size(); j++) {
				MutableCell<SpreadSheet> cell = sheet.getCellAt(j, i);
				
				Element td = tds.get(j);
				Attribute tdStyle = td.attribute("style");
				Element p = td.element("p");
				
				//塗滿顏色(標頭、奇數偶數列)
				if(i == 0) {
					cell.setBackgroundColor(Color.BLUE);
				} else if((i % 2) != 0) {
					//TODO: 如果資料筆數超過,jOpenDocument會有bug
					if(trs.size() <= maxRecord) {
						cell.setBackgroundColor(Color.LIGHT_GRAY);
					}
				} else {
					//TODO: 如果資料筆數超過,jOpenDocument會有bug
					if(trs.size() <= maxRecord) {
						cell.setBackgroundColor(Color.WHITE);
					}
				}
				
				//對齊與設值
				if(p != null) {
//					String align = p.attribute("align").getValue();
//					if(align.equalsIgnoreCase("center")) {
//						style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
//					} else if(align.equalsIgnoreCase("right")) {
//						style.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
//					} else if(align.equalsIgnoreCase("left")) {
//						style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
//					}
					
					cell.setValue(p.getText());
				} else if(tdStyle != null) {
////					if(tdStyle.getValue().indexOf("mso-number-format") != -1) {
////						cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
////						style.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
////					}
					
					cell.setValue(td.getText());
				} else {
					cell.setValue(td.getText());
				}
			}
		}
		
		getResponse().reset();
		getResponse().setContentType("application/vnd.ms-excel");
		if(AssertUtility.isNotNullAndNotSpace(fileName)) {
			getResponse().setHeader("Content-disposition", "attachment; filename=\"" + StringUtility.cleanCRLF(fileName) + "\"");
		}
		if(AssertUtility.isNotNullAndNotSpace(encoding)) {
			getResponse().setCharacterEncoding(encoding);
		}

		ServletOutputStream sos = null;
		FileInputStream fis = null;
		BufferedInputStream bis = null; 
		BufferedOutputStream bos = null;
		 
		try {
			 sos = getResponse().getOutputStream();
			 
			 spreadSheet.saveAs(tempFile);
			 
			 fis = new FileInputStream(tempFile);
			 byte[] tempData= new byte[1];
			 
			 bis = new BufferedInputStream(fis); 
			 bos = new BufferedOutputStream(sos);
			 
			 while(bis.read(tempData) != -1) { 
				 bos.write(tempData); 
			 }
		} finally {
			if(bis != null) {
				bis.close();
		    }
			
			if(bos != null) {
		    	bos.flush();
		    	bos.close();
		    }
			
			if(fis != null) {
		    	fis.close();
		    }
			
			if(sos != null) {
			    sos.flush();
			    sos.close();
		    }
		    
		    if(tempFile != null && tempFile.exists()) {
		    	tempFile.delete();
		    }
		}
	}
	
	/**
	 * 下載CSV檔(從grid或project grid塞到session中的資料來產生excel)
	 * @param fileName
	 * @param content
	 * @param encoding
	 * @throws IOException
	 * @throws DocumentException
	 * @throws NamingException 
	 */
	@SuppressWarnings("unchecked")
	public void downloadCSVFromSessionData(String fileName, String tableId, String data, String encoding, String downloadRows, String downloadColumns) throws IOException, DocumentException, NamingException {
		AssertUtility.notNullAndNotSpace(data);
		
		List<Map<String, String>> tasks = (List<Map<String, String>>)getSessionAttribute(data);
		
		if(tasks == null) { //存在session中的資料過期,download功能無法正常運作
			PrintWriter out = getResponse().getWriter();
		    
		    out.println("<script language=\"javascript\">\n");
		    out.println("alert('" + new String(getMessage("common.SessionTimeOut").getBytes("UTF-8"), "ISO-8859-1") + "');\n");
		    out.println("parent.parent.parent.location.href = \"" + getSysURL() + "\";\n");
		    out.println("</script>\n");
		} else {
			boolean partialFlag = !downloadRows.trim().equalsIgnoreCase("all");
			String[] rowIndexs = downloadRows.split(",");
			
			List<String> titles = (List<String>)getSessionAttribute(tableId + "_DownloadTitles");
			List<String> values = (List<String>)getSessionAttribute(tableId + "_DownloadValues");
			
			String[] columnIndexs;
			if(!downloadColumns.trim().equals("")) {
				columnIndexs = downloadColumns.split(",");
			} else {
				columnIndexs = new String[titles.size()];
				for(int i = 0; i < titles.size(); i++) {
					columnIndexs[i] = "" + i;
				}
			}
			
			StringBuffer content = new StringBuffer();
			for(int i = 0; i < columnIndexs.length; i++) {
				if(i != 0) {
					content.append(",");
				}
				content.append(titles.get(Integer.parseInt(columnIndexs[i])));
			}
			content.append("\n");
			
			int insertRowIndex = 1;
			for(int i = 0; i < tasks.size(); i++) {
				Map<String, String> task = tasks.get(i);
				
				if(partialFlag) { //如果是選擇的row才下載
					if(!downloadRows.trim().equals("")) { //有選擇的row才產生資料
						boolean matchFlag = false;
						for(int j = 0; j < rowIndexs.length; j++) {
							if(i == Integer.parseInt(rowIndexs[j])) {
								matchFlag = true;
								break;
							}
						}
						
						if(matchFlag) {
							for(int j = 0; j < columnIndexs.length; j++) {
								String value = values.get(Integer.parseInt(columnIndexs[j]));
								value = ListUtility.replaceVariable(task, value);
								value = ListUtility.replaceSessionVariable(value);
								value = ListUtility.replaceRequestVariable(value);
								
								while(value.indexOf("(var.SerialNumber)") != -1) {
									value = value.substring(0, value.indexOf("(var.SerialNumber)")) + insertRowIndex + value.substring(value.indexOf("(var.SerialNumber)") + "(var.SerialNumber)".length(), value.length());
						        }
								
								if(j != 0) {
									content.append(",");
								}
								content.append(value);
							}
							
							content.append("\n");
							insertRowIndex++;
						}
					}
				} else { //全部資料下載
					for(int j = 0; j < columnIndexs.length; j++) {
						String value = values.get(Integer.parseInt(columnIndexs[j]));
						value = ListUtility.replaceVariable(task, value);
						value = ListUtility.replaceSessionVariable(value);
						value = ListUtility.replaceRequestVariable(value);
						
						while(value.indexOf("(var.SerialNumber)") != -1) {
							value = value.substring(0, value.indexOf("(var.SerialNumber)")) + (i + 1) + value.substring(value.indexOf("(var.SerialNumber)") + "(var.SerialNumber)".length(), value.length());
				        }
						
						if(j != 0) {
							content.append(",");
						}
						content.append(value);
					}
					
					content.append("\n");
				}
			}
			
			download(fileName, content.toString(), encoding, "vnd.ms-excel");
		}
	}
	
	/**
	 * 下載Excel,用POI產生excel檔(從grid或project grid塞到session中的資料來產生excel)
	 * @param fileName
	 * @param content
	 * @param encoding
	 * @throws IOException
	 * @throws DocumentException
	 * @throws NamingException 
	 */
	@SuppressWarnings("unchecked")
	public void downloadExcelFromSessionDataUsingPOI(String fileName, String tableId, String data, String encoding, String downloadRows, String downloadColumns) throws IOException, DocumentException, NamingException {
		AssertUtility.notNullAndNotSpace(data);
		
		List<Map<String, String>> tasks = (List<Map<String, String>>)getSessionAttribute(data);
		
		if(tasks == null) { //存在session中的資料過期,download功能無法正常運作
			PrintWriter out = getResponse().getWriter();
		    
		    out.println("<script language=\"javascript\">\n");
		    out.println("alert('" + new String(getMessage("common.SessionTimeOut").getBytes("UTF-8"), "ISO-8859-1") + "');\n");
		    out.println("parent.parent.parent.location.href = \"" + getSysURL() + "\";\n");
		    out.println("</script>\n");
		} else {
			boolean partialFlag = !downloadRows.trim().equalsIgnoreCase("all");
			String[] rowIndexs = downloadRows.split(",");
			
			List<String> titles = (List<String>)getSessionAttribute(tableId + "_DownloadTitles");
			List<String> values = (List<String>)getSessionAttribute(tableId + "_DownloadValues");
			List<String> dataTypes = (List<String>)getSessionAttribute(tableId + "_DownloadDataTypes");
			
			String[] columnIndexs;
			if(!downloadColumns.trim().equals("")) {
				columnIndexs = downloadColumns.split(",");
			} else {
				columnIndexs = new String[titles.size()];
				for(int i = 0; i < titles.size(); i++) {
					columnIndexs[i] = "" + i;
				}
			}
			
			HSSFWorkbook excel = new HSSFWorkbook();
			HSSFSheet sheet = excel.createSheet();
			
			HSSFRow title = sheet.createRow(0);
			for(int i = 0; i < columnIndexs.length; i++) {
				HSSFCell cell = title.createCell(i);
				
				HSSFCellStyle style = excel.createCellStyle();
				
				style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
				style.setFillForegroundColor(HSSFColor.LIGHT_BLUE.index);
				style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
				cell.setCellStyle(style);
				cell.setCellValue(titles.get(Integer.parseInt(columnIndexs[i])));
			}
			
			int insertRowIndex = 1;
			for(int i = 0; i < tasks.size(); i++) {
				Map<String, String> task = tasks.get(i);
				
				if(partialFlag) { //如果是選擇的row才下載
					if(!downloadRows.trim().equals("")) { //有選擇的row才產生資料
						boolean matchFlag = false;
						for(int j = 0; j < rowIndexs.length; j++) {
							if(i == Integer.parseInt(rowIndexs[j])) {
								matchFlag = true;
								break;
							}
						}
						
						if(matchFlag) {
							HSSFRow row = sheet.createRow(insertRowIndex);
							
							for(int j = 0; j < columnIndexs.length; j++) {
								HSSFCell cell = row.createCell(j);
								
								HSSFCellStyle style = excel.createCellStyle();
								
								//塗滿顏色(標頭、奇數偶數列)
								style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
								if(((insertRowIndex + 1) % 2) != 0) {
									style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
								} else {
									style.setFillForegroundColor(HSSFColor.WHITE.index);
								}
								
								//對齊與設值
								if(dataTypes.get(Integer.parseInt(columnIndexs[j])).equalsIgnoreCase("number") || dataTypes.get(Integer.parseInt(columnIndexs[j])).equalsIgnoreCase("currency")) {
									style.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
									cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
								} else {
									style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
								}
								
								String value = values.get(Integer.parseInt(columnIndexs[j]));
								value = ListUtility.replaceVariable(task, value);
								value = ListUtility.replaceSessionVariable(value);
								value = ListUtility.replaceRequestVariable(value);
								
								while(value.indexOf("(var.SerialNumber)") != -1) {
									value = value.substring(0, value.indexOf("(var.SerialNumber)")) + insertRowIndex + value.substring(value.indexOf("(var.SerialNumber)") + "(var.SerialNumber)".length(), value.length());
						        }
								
								cell.setCellStyle(style);
								cell.setCellValue(value);
							}
							
							insertRowIndex++;
						}
					}
				} else { //全部資料下載
					HSSFRow row = sheet.createRow(i + 1);
					
					for(int j = 0; j < columnIndexs.length; j++) {
						HSSFCell cell = row.createCell(j);
						
						HSSFCellStyle style = excel.createCellStyle();
						
						//塗滿顏色(標頭、奇數偶數列)
						style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
						if(((i + 1) % 2) != 0) {
							style.setFillForegroundColor(HSSFColor.WHITE.index);
						} else {
							style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
						}
						
						//對齊與設值
						if(dataTypes.get(Integer.parseInt(columnIndexs[j])).equalsIgnoreCase("number") || dataTypes.get(Integer.parseInt(columnIndexs[j])).equalsIgnoreCase("currency")) {
							style.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
							cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
						} else {
							style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
						}
						
						String value = values.get(Integer.parseInt(columnIndexs[j]));
						value = ListUtility.replaceVariable(task, value);
						value = ListUtility.replaceSessionVariable(value);
						value = ListUtility.replaceRequestVariable(value);
						
						while(value.indexOf("(var.SerialNumber)") != -1) {
							value = value.substring(0, value.indexOf("(var.SerialNumber)")) + (i + 1) + value.substring(value.indexOf("(var.SerialNumber)") + "(var.SerialNumber)".length(), value.length());
				        }
						
						cell.setCellStyle(style);
						cell.setCellValue(value);
					}
				}
			}
			
			getResponse().reset();
			getResponse().setContentType("application/vnd.ms-excel");
			if(AssertUtility.isNotNullAndNotSpace(fileName)) {
				getResponse().setHeader("Content-disposition", "attachment; filename=\"" + StringUtility.cleanCRLF(fileName) + "\"");
			}
			if(AssertUtility.isNotNullAndNotSpace(encoding)) {
				getResponse().setCharacterEncoding(encoding);
			}
	
		    ServletOutputStream sos = null;
	    
		    try {
			    sos = getResponse().getOutputStream();
			    excel.write(sos);
		    } finally {
		    	if(sos != null) {
				    sos.flush();
				    sos.close();
		    	}
		    }
		}
	}
	
	/**
	 * 下載ODS(從grid或project grid塞到session中的資料來產生ods)
	 * @param fileName
	 * @param content
	 * @param encoding
	 * @throws IOException
	 * @throws DocumentException
	 * @throws NamingException 
	 */
	@SuppressWarnings("unchecked")
	public void downloadODSFromSessionData(String fileName, String tableId, String data, String encoding, String downloadRows, String downloadColumns) throws IOException, DocumentException, NamingException {
		//TODO: jOpenDocument的bug,資料行到100筆之後設定style會有jdom attribute null的exception發生
		int maxRecord = 100;
		
		AssertUtility.notNullAndNotSpace(data);
		
		List<Map<String, String>> tasks = (List<Map<String, String>>)getSessionAttribute(data);
		
		if(tasks == null) { //存在session中的資料過期,download功能無法正常運作
			PrintWriter out = getResponse().getWriter();
		    
		    out.println("<script language=\"javascript\">\n");
		    out.println("alert('" + new String(getMessage("common.SessionTimeOut").getBytes("UTF-8"), "ISO-8859-1") + "');\n");
		    out.println("parent.parent.parent.location.href = \"" + getSysURL() + "\";\n");
		    out.println("</script>\n");
		} else {
			boolean partialFlag = !downloadRows.trim().equalsIgnoreCase("all");
			String[] rowIndexs = downloadRows.split(",");
			
			List<String> titles = (List<String>)getSessionAttribute(tableId + "_DownloadTitles");
			List<String> values = (List<String>)getSessionAttribute(tableId + "_DownloadValues");
			List<String> dataTypes = (List<String>)getSessionAttribute(tableId + "_DownloadDataTypes");
			
			String[] columnIndexs;
			if(!downloadColumns.trim().equals("")) {
				columnIndexs = downloadColumns.split(",");
			} else {
				columnIndexs = new String[titles.size()];
				for(int i = 0; i < titles.size(); i++) {
					columnIndexs[i] = "" + i;
				}
			}
			
			String[] odsTitles = new String[columnIndexs.length];
			Object[][] odsDatas = new Object[tasks.size()][columnIndexs.length];
			TableModel model = new DefaultTableModel(odsDatas, odsTitles);
			File tempFile = new File(getServletContext().getRealPath("/") + "/temp/temp.ods");
		    SpreadSheet spreadSheet = SpreadSheet.createEmpty(model);
		    Sheet sheet = spreadSheet.getSheet(0);
			
			for(int i = 0; i < columnIndexs.length; i++) {
				MutableCell<SpreadSheet> cell = sheet.getCellAt(i, 0);
//				style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
				cell.setBackgroundColor(Color.BLUE);
				cell.setValue(titles.get(Integer.parseInt(columnIndexs[i])));
			}
			
			int insertRowIndex = 1;
			for(int i = 0; i < tasks.size(); i++) {
				Map<String, String> task = tasks.get(i);
				
				if(partialFlag) { //如果是選擇的row才下載
					if(!downloadRows.trim().equals("")) { //有選擇的row才產生資料
						boolean matchFlag = false;
						for(int j = 0; j < rowIndexs.length; j++) {
							if(i == Integer.parseInt(rowIndexs[j])) {
								matchFlag = true;
								break;
							}
						}
						
						if(matchFlag) {
							for(int j = 0; j < columnIndexs.length; j++) {
								MutableCell<SpreadSheet> cell = sheet.getCellAt(j, insertRowIndex);
								
								//TODO: 如果資料筆數超過,jOpenDocument會有bug
								if(rowIndexs.length <= maxRecord) {
									//塗滿顏色(標頭、奇數偶數列)
									if(((insertRowIndex + 1) % 2) != 0) {
										cell.setBackgroundColor(Color.LIGHT_GRAY);
									} else {
										cell.setBackgroundColor(Color.WHITE);
									}
								}
								
								String value = values.get(Integer.parseInt(columnIndexs[j]));
								value = ListUtility.replaceVariable(task, value);
								value = ListUtility.replaceSessionVariable(value);
								value = ListUtility.replaceRequestVariable(value);
								
								while(value.indexOf("(var.SerialNumber)") != -1) {
									value = value.substring(0, value.indexOf("(var.SerialNumber)")) + insertRowIndex + value.substring(value.indexOf("(var.SerialNumber)") + "(var.SerialNumber)".length(), value.length());
						        }
								
								//對齊與設值
								if(dataTypes.get(Integer.parseInt(columnIndexs[j])).equalsIgnoreCase("number") || dataTypes.get(Integer.parseInt(columnIndexs[j])).equalsIgnoreCase("currency")) {
//									style.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
									cell.setValue(Integer.parseInt(value));
								} else {
//									style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
									cell.setValue(value);
								}
							}
							
							insertRowIndex++;
						}
					}
				} else { //全部資料下載
					for(int j = 0; j < columnIndexs.length; j++) {
						MutableCell<SpreadSheet> cell = sheet.getCellAt(j, i + 1);
						
						//TODO: 如果資料筆數超過,jOpenDocument會有bug
						if(task.size() <= maxRecord) {
							//塗滿顏色(標頭、奇數偶數列)
							if(((i + 1) % 2) != 0) {
								cell.setBackgroundColor(Color.WHITE);
							} else {
								cell.setBackgroundColor(Color.LIGHT_GRAY);
							}
						}
						
						String value = values.get(Integer.parseInt(columnIndexs[j]));
						value = ListUtility.replaceVariable(task, value);
						value = ListUtility.replaceSessionVariable(value);
						value = ListUtility.replaceRequestVariable(value);
						
						while(value.indexOf("(var.SerialNumber)") != -1) {
							value = value.substring(0, value.indexOf("(var.SerialNumber)")) + (i + 1) + value.substring(value.indexOf("(var.SerialNumber)") + "(var.SerialNumber)".length(), value.length());
				        }
						
						//對齊與設值
//						if(dataTypes.get(Integer.parseInt(columnIndexs[j])).equalsIgnoreCase("number") || dataTypes.get(Integer.parseInt(columnIndexs[j])).equalsIgnoreCase("currency")) {
////							style.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
//							cell.setValue(new BigDecimal(value));
//						} else {
//							style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
							cell.setValue(value);
//						}
					}
				}
			}
			
			getResponse().reset();
			getResponse().setContentType("application/vnd.oasis.opendocument.spreadsheet");
			if(AssertUtility.isNotNullAndNotSpace(fileName)) {
				getResponse().setHeader("Content-disposition", "attachment; filename=\"" + StringUtility.cleanCRLF(fileName) + "\"");
			}
			if(AssertUtility.isNotNullAndNotSpace(encoding)) {
				getResponse().setCharacterEncoding(encoding);
			}
	
		    ServletOutputStream sos = null;
		    FileInputStream fis = null;
		    BufferedInputStream bis = null; 
		    BufferedOutputStream bos = null;
		    
		    try {
			    sos = getResponse().getOutputStream();
			    
			    spreadSheet.saveAs(tempFile);
			    
			    fis = new FileInputStream(tempFile);
			    byte[] tempData= new byte[1];
			    
			    bis = new BufferedInputStream(fis); 
			    bos = new BufferedOutputStream(sos);
			    
			    while(bis.read(tempData) != -1) { 
			    	bos.write(tempData); 
			    }
			} finally {
				if(bis != null) {
		    		bis.close();
		    	}
				
				if(bos != null) {
		    		bos.flush();
		    		bos.close();
		    	}
				
				if(fis != null) {
		    		fis.close();
		    	}
				
				if(sos != null) {
				    sos.flush();
				    sos.close();
		    	}
		    	
		    	if(tempFile != null && tempFile.exists()) {
		    		tempFile.delete();
		    	}
		    }
		}
	}
	
	/**
	 * 下載Microsoft Project(從project grid塞到session中的資料來產生mpp)
	 * @param fileName
	 * @param tableId
	 * @param data
	 * @param encoding
	 * @param fileType
	 * @throws Exception
	 */
	abstract public void downloadProject(String fileName, String tableId, String data, String encoding, String fileType) throws Exception;
	
	/**
	 * 下載檔案
	 * @param 檔案名稱
	 * @param 內容
	 * @param 編碼
	 * @param 檔案型別(如pdf等)
	 * @throws Exception
	 */
	public void download(String fileName, String content, String encoding, String fileType) throws IOException {
		AssertUtility.notNull(content);
		
		getResponse().reset();
		if(AssertUtility.isNotNullAndNotSpace(fileType)) {
			getResponse().setContentType("application/" + fileType);
		}
		if(AssertUtility.isNotNullAndNotSpace(fileName)) {
			getResponse().setHeader("Content-disposition", "attachment; filename=\"" + StringUtility.cleanCRLF(fileName) + "\"");
		}
		if(AssertUtility.isNotNullAndNotSpace(encoding)) {
			getResponse().setCharacterEncoding(encoding);
		}
//			getResponse().setContentLength(Integer.parseInt(Long.toString(content.getBytes().length)));

	    ServletOutputStream sos = getResponse().getOutputStream();
	    OutputStreamWriter writer;
	    if(AssertUtility.isNotNullAndNotSpace(encoding)) {
	    	writer = new OutputStreamWriter(sos, encoding);
	    } else {
	    	writer = new OutputStreamWriter(sos);
	    }
	    writer.write(content);
	    writer.flush();
	    writer.close();
	    sos.close();
	}
	
	/**
	 * 產生隨機認證碼的圖檔
	 * @param codeMode
	 * @param codeLength
	 * @param width
	 * @param height
	 * @param backgroundColor
	 * @param rectFlag
	 * @param rectColor
	 * @param fontColor
	 * @param fontName
	 * @param fontStyle
	 * @param fontSize
	 * @param fontX
	 * @param fontY
	 * @param noiseNumber
	 * @throws IOException
	 */
	public void makeAuthenticationImageFile(String codeMode, int codeLength, int width, int height, Color backgroundColor, boolean rectFlag, Color rectColor, Color fontColor, String fontName, int fontStyle, int fontSize, int fontX, int fontY, int noiseNumber) throws IOException {
		BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);  
		Graphics2D bufferedGraphics = bufferedImage.createGraphics();
		if(backgroundColor != null) {
			bufferedGraphics.setColor(backgroundColor);
		}
		bufferedGraphics.fillRect(0, 0, width, height);
		
		//畫矩形框
		if(rectFlag) {
			if(rectColor != null) {
				bufferedGraphics.setColor(rectColor);
			}
			bufferedGraphics.drawRect(0, 0, width - 1, height - 1);
		}
		
		//產生隨機安全校驗碼
		String code;
		
		if(codeMode.equalsIgnoreCase("UpperCaseLetter")) {
			code = StringUtility.makeRandomUpperCaseLetterKey(codeLength);
		} else if(codeMode.equalsIgnoreCase("LowerCaseLetter")) {
			code = StringUtility.makeRandomLowerCaseLetterKey(codeLength);
		} else if(codeMode.equalsIgnoreCase("Mix")) {
			code = StringUtility.makeRandomMixKey(codeLength);
		} else {
			code = StringUtility.makeRandomNumberKey(codeLength);
		}
		
		//將安全校驗碼存進session變數，供程式來比對
		setSessionAttribute("AuthenticationImageNumber", code);  
		
		if(fontColor != null) {
			bufferedGraphics.setColor(fontColor);
		}
		if(fontName != null) {
			bufferedGraphics.setFont( new Font(fontName, fontStyle, fontSize) ); //設定字型
		}
		bufferedGraphics.drawString(code, fontX, fontY);  

		WritableRaster raster = bufferedImage.getRaster();  
		int[] color = {0,0,0}; //black color  
		for(int i=0; i < noiseNumber; i++) { //隨機畫點  
			Random random = new Random();
			int x = random.nextInt(width); 
		    int y = random.nextInt(height);  
		    raster.setPixel(x,y,color); //畫點  
		}  

		getResponse().setContentType("image/jpeg");  
		ServletOutputStream sos = getResponse().getOutputStream();  

		Iterator<ImageWriter> iterator = ImageIO.getImageWritersByFormatName("jpeg");  
		ImageWriter imageWriter = iterator.next();  

		ImageOutputStream ios = ImageIO.createImageOutputStream(sos);  
		imageWriter.setOutput(ios);  
		imageWriter.write(bufferedImage);  
		ios.flush();  
		sos.close();  
	}
	
	/**
	 * 產生隨機數字認證碼4碼的圖檔
	 * @throws IOException
	 */
	public void makeAuthenticationImageFile() throws IOException {
		makeAuthenticationImageFile("number", 4, 60, 30, Color.WHITE, true, Color.BLACK, Color.BLACK, "Arial", Font.ITALIC, 24, 2, 24, 100);
	}
	
	/**
	 * 產生QRCode圖檔
	 * @param value
	 * @param width
	 * @param height
	 * @throws Exception
	 */
	public abstract void makeQRCodeImageFile(String value, int width, int height) throws Exception;
	
	/**
	 * 產生QRCode圖檔
	 * @param value
	 * @throws Exception
	 */
	public abstract void makeQRCodeImageFile(String value) throws Exception;
	
	/**
	 * 取得Logger以寫入Log檔
	 * @return
	 */
	public Logger getLogger(String LoggerName) {
		Logger logger;
		
		try {
			if(getAppAttribute("log4j.properties") == null || !Boolean.parseBoolean((String)getAppAttribute("log4j.properties"))) {
				FileInputStream fis = new FileInputStream(getServletContext().getRealPath("/") + "WEB-INF/log4j.properties");
				
				try {
					Properties prop = new Properties();
					prop.load(fis);
					PropertyConfigurator.configure(prop);
					setAppAttribute("log4j.properties", Boolean.TRUE);
				} finally {
					fis.close();
				}
			}
			
			if(LoggerName != null) {
				logger = Logger.getLogger(LoggerName);
			} else {
				logger = Logger.getLogger("");
			}
		} catch (Exception ex) {
//			沒取到properties的錯誤訊息,要印出來
			if(LoggerName != null) {
				logger = Logger.getLogger(LoggerName);
			} else {
				logger = Logger.getLogger("");
			}
			StringWriter sw = new StringWriter();
		    PrintWriter pw = new PrintWriter(sw);
		    ex.printStackTrace(pw);
		    logger.warn("Loading Log4j properties error : \r\n" + sw.toString());
		}
		
		return logger;
	}
	
	public Logger getLogger() {
		return getLogger("");
	}

	/**
	 * 寫入Error Log
	 * @param e
	 */
	public void writeErrorLog(Exception ex) {
		AssertUtility.notNull(ex);
		
		try {
		    StringWriter sw = new StringWriter();
		    PrintWriter pw = new PrintWriter(sw);
		    ex.printStackTrace(pw);
		    getLogger().error("Function ID : " + getFunctionId() + "\r\n" + sw.toString());
		} catch(Exception ex2) { //沒取到logger時,印出本來要給logger記的exception
			ex.printStackTrace();
		}
	}
	
	/**
	 * 取得頁面動作代號
	 * @author George_Tsai
	 * @version 2009/1/15
	 * @throws NamingException 
	 * @throws Exception
	 */
	public String getActFlag() throws NamingException {
		return getRequestValue("ActFlag");
	}
	
	/**
	 * 設定控制項是否必填
	 * @param ControlId
	 * @param RequiredFlag
	 * @throws Exception
	 * @author George_Tsai
	 * @version 2009/2/14
	 */
	public void setControlRequired(String ControlId, boolean RequiredFlag) {
		if(ControlId != null && ControlId.indexOf(" ") == -1) {
			if(RequiredFlag) {
				setRequestAttribute(ControlId + "_IsRequired", "true");
			} else {
				setRequestAttribute(ControlId + "_IsRequired", "false");
			}
		} else {
			throw new NullPointerException("Control Id is invalid!!");
		}
	}
	
	/**
	 * 設定控制項是否唯讀
	 * @param ControlId
	 * @param ReadOnlyFlag
	 * @throws Exception
	 * @author George_Tsai
	 * @version 2009/2/14
	 */
	public void setControlReadOnly(String ControlId, boolean ReadOnlyFlag) {
		if(ControlId != null && ControlId.indexOf(" ") == -1) {
			if(ReadOnlyFlag) {
				setRequestAttribute(ControlId + "_IsReadOnly", "true");
			} else {
				setRequestAttribute(ControlId + "_IsReadOnly", "false");
			}
		} else {
			throw new NullPointerException("Control Id is invalid!!");
		}
	}
	
	/**
	 * 取得所有要import的js檔案
	 * @return
	 * @throws Exception
	 * @author George_Tsai
	 * @version 2009/12/10
	 * @throws FileNotFoundException 
	 */
	public String getImportJSFileLink() throws NamingException, FileNotFoundException {
		if(AssertUtility.isNotNullAndNotSpace(getEnvVariable("CDNJS")) || AssertUtility.isNotNullAndNotSpace(getEnvVariable("preloadJS"))) {
			return getAllCDNJSLink(getEnvVariables("CDNJS")) + getAllJSFileLink("js", getEnvVariables("preloadJS"), false);
		} else {
			return getAllJSFileLink("js", false);
		}
	}
	
	/**
	 * 取得要import的CDN js
	 * @param jsList
	 * @return
	 * @author George_Tsai
	 * @version 2011/4/26
	 */
	public String getAllCDNJSLink(List<String> jsList) {
		HtmlBuilder htmlBuilder = new HtmlBuilder();
		
		for(int i = 0; i < jsList.size(); i++) {
			htmlBuilder.scriptStart().src(jsList.get(i)).tagClose().scriptEnd();
		}
		
		return htmlBuilder.toString();
	}
	
	/**
	 * 取得目錄下所有js檔案
	 * @param jsDir
	 * @return
	 * @throws Exception
	 * @author George_Tsai
	 * @version 2009/6/22
	 * @throws FileNotFoundException 
	 */
	public String getAllJSFileLink(String jsDir, boolean recursive) throws NamingException, FileNotFoundException {
		return getAllJSFileLink(jsDir, null, recursive);
	}
	
	/**
	 * 取得目錄下所有js檔案;preloadJS會先定義出來
	 * @param jsDir
	 * @param preloadJS
	 * @param recursive
	 * @return
	 * @throws NamingException
	 * @throws FileNotFoundException
	 */
	public String getAllJSFileLink(String jsDir, List<String> preloadJS, boolean recursive) throws NamingException, FileNotFoundException {
		if(jsDir != null && jsDir.indexOf(" ") == -1) {
			StringBuffer link = new StringBuffer();
			
			if(preloadJS != null) {
				for(int i = 0; i < preloadJS.size(); i++) {
					if(link.length() != 0) {
						link.append("\n");
					}
					
					HtmlBuilder htmlBuilder = new HtmlBuilder();
					htmlBuilder.scriptStart().src(getBasePathForHTML() + StringUtility.removeStartEndSlash(jsDir) + "/" + preloadJS.get(i)).tagClose().scriptEnd();
					link.append(htmlBuilder.toString());
				}
			}
			
			File dir = new File(getServletContext().getRealPath("/") + "/" + jsDir + "/");
			
			if(dir.exists() && dir.isDirectory()) {
				File[] jsFiles = dir.listFiles();
				
				for(int i = 0; i < jsFiles.length; i++) {
					if(jsFiles[i].getName().endsWith(".js") && (preloadJS == null || !preloadJS.contains(jsFiles[i].getName()))) {
						if(link.length() != 0) {
							link.append("\n");
						}
						HtmlBuilder htmlBuilder = new HtmlBuilder();
						htmlBuilder.scriptStart().src(getBasePathForHTML() + StringUtility.removeStartEndSlash(jsDir) + "/" + jsFiles[i].getName()).tagClose().scriptEnd();
						link.append(htmlBuilder.toString());
					} else if(jsFiles[i].isDirectory()){
						if(recursive) {
							if(jsFiles[i].getName().equalsIgnoreCase("locale")) {
								link.append(getLocaleJSFileLink(jsDir));
							} else {
								link.append(getAllJSFileLink(jsDir + "/" + jsFiles[i].getName(), true));
							}
						}
					}
				}
			}
			
			if(!recursive) {
				link.append(getLocaleJSFileLink(jsDir));
			}
			
			return link.toString();
		} else {
			throw new FileNotFoundException("Directory not found!!");
		}
	}
	
	/**
	 * 取得locale js link
	 * @return
	 * @throws NamingException 
	 */
	public String getLocaleJSFileLink(String jsDir) throws NamingException {
		String locale = "zh_TW";
		if(!getLoginUserLocale().equals("")) {
			locale = getLoginUserLocale();
		} else {
			if(getSysLocale() != null) {
				locale = getEnvVariable("sysLocale");
			} else if(getRequest().getLocale() != null) {
				if(AssertUtility.isNotNullAndNotSpace(getRequest().getLocale().getCountry())) {
					locale = getRequest().getLocale().getLanguage().toLowerCase() + "_" + getRequest().getLocale().getCountry().toUpperCase();
				} else {
					locale = getRequest().getLocale().getLanguage().toLowerCase();
				}
			}
		}
		
		HtmlBuilder htmlBuilder = new HtmlBuilder();
		htmlBuilder.scriptStart().src(getBasePathForHTML() + StringUtility.removeStartEndSlash(jsDir) + "/locale/csiebug-" + locale + ".js").tagClose().scriptEnd();
		return htmlBuilder.toString();
	}
	
	/**
	 * 取得所有要import的css檔案
	 * @return
	 * @throws Exception
	 * @author George_Tsai
	 * @version 2009/6/22
	 * @throws FileNotFoundException 
	 */
	public String getImportCSSFileLink() throws NamingException, FileNotFoundException {
		if(AssertUtility.isNotNullAndNotSpace(getEnvVariable("CDNCSS")) || AssertUtility.isNotNullAndNotSpace(getEnvVariable("preloadCSS"))) {
			return getAllCDNCSSLink(getEnvVariables("CDNCSS")) + getAllCSSFileLink("css", getEnvVariables("preloadCSS"), false);
		} else {
			return getAllCSSFileLink("css", false);
		}
	}
	
	/**
	 * 取得要import的CDN css
	 * @param cssList
	 * @return
	 * @author George_Tsai
	 * @version 2011/4/26
	 */
	public String getAllCDNCSSLink(List<String> cssList) {
		HtmlBuilder htmlBuilder = new HtmlBuilder();
		
		for(int i = 0; i < cssList.size(); i++) {
			htmlBuilder.linkStart().href(cssList.get(i)).rel("stylesheet").type("text/css").tagClose();
		}
		
		return htmlBuilder.toString();
	}
	
	/**
	 * 取得目錄下所有的css檔案
	 * @param cssDir
	 * @return
	 * @throws Exception
	 * @author George_Tsai
	 * @version 2009/6/22
	 * @throws FileNotFoundException 
	 */
	public String getAllCSSFileLink(String cssDir, boolean recursive) throws NamingException, FileNotFoundException {
		return getAllCSSFileLink(cssDir, null, recursive);
	}
	
	/**
	 * 取得目錄下所有的css檔案;preloadCSS會先定義出來
	 * @param cssDir
	 * @param preloadCSS
	 * @param recursive
	 * @return
	 * @throws NamingException
	 * @throws FileNotFoundException
	 */
	public String getAllCSSFileLink(String cssDir, List<String> preloadCSS, boolean recursive) throws NamingException, FileNotFoundException {
		if(cssDir != null && cssDir.indexOf(" ") == -1) {
			StringBuffer link = new StringBuffer();
			
			if(preloadCSS != null) {
				for(int i = 0; i < preloadCSS.size(); i++) {
					if(link.length() != 0) {
						link.append("\n");
					}
					
					HtmlBuilder htmlBuilder = new HtmlBuilder();
					htmlBuilder.linkStart().href(getBasePathForHTML() + StringUtility.removeStartEndSlash(cssDir) + "/" + preloadCSS.get(i)).rel("stylesheet").type("text/css").tagClose();
					link.append(htmlBuilder.toString());
				}
			}
			
			File dir = new File(getServletContext().getRealPath("/") + "/" + cssDir + "/");
			
			if(dir.exists() && dir.isDirectory()) {
				File[] cssFiles = dir.listFiles();
				
				for(int i = 0; i < cssFiles.length; i++) {
					if(cssFiles[i].getName().endsWith(".css") && (preloadCSS == null || !preloadCSS.contains(cssFiles[i].getName()))) {
						if(link.length() != 0) {
							link.append("\n");
						}
						HtmlBuilder htmlBuilder = new HtmlBuilder();
						htmlBuilder.linkStart().href(getBasePathForHTML() + StringUtility.removeStartEndSlash(cssDir) + "/" + cssFiles[i].getName()).rel("stylesheet").type("text/css").tagClose();
						link.append(htmlBuilder.toString());
					} else if(cssFiles[i].isDirectory()){
						if(recursive) {
							link.append(getAllCSSFileLink(cssDir + "/" + cssFiles[i].getName(), true));
						}
					}
				}
			}
			
			return link.toString();
		} else {
			throw new FileNotFoundException("Directory not found!!");
		}
	}
	
	//以下可能要隨系統不同而覆寫
	/**
	 * 取得登入者帳號
	 * @author George_Tsai
	 * @version 2009/1/23
	 */
	public String getLoginUserId() {
		if(getSessionAttribute("UserId") == null) {
			return "";
		} else {
			return getSessionAttribute("UserId").toString();
		}
	}
	
	/**
	 * 設定登入者帳號
	 * @param value
	 * @throws Exception
	 * @author George_Tsai
	 * @version 2009/1/23
	 */
	public void setLoginUserId(String value) {
		removeSessionAttribute("UserId");
		setSessionAttribute("UserId", value);
	}
	
	/**
	 * 取得登入者使用語系
	 * @author George_Tsai
	 * @version 2009/3/27
	 */
	public String getLoginUserLocale() {
		if(getSessionAttribute("UserLocale") == null) {
			return "";
		} else {
			return getSessionAttribute("UserLocale").toString();
		}
	}
	
	/**
	 * 設定登入者使用語系
	 * @param value
	 * @throws Exception
	 * @author George_Tsai
	 * @version 2009/3/27
	 */
	public void setLoginUserLocale(String value) {
		removeSessionAttribute("UserLocale");
		setSessionAttribute("UserLocale", value);
	}
	
	/**
	 * 檢查使用者是否登入
	 * @return
	 * @throws Exception
	 * @author George_Tsai
	 * @version 2009/1/23
	 */
	public boolean checkLogin() {
		boolean flag = true;
		
		if(getLoginUserId().equals("")) {
			flag = false;
		}
		
		return flag;
	}
	
	/**
	 * 取得登入的系統Id
	 * @return
	 * @throws Exception
	 * @author George_Tsai
	 * @version 2009/1/23
	 */
	public String getLoginAPId() {
		if(getSessionAttribute("APId") == null) {
			return "";
		} else {
			return getSessionAttribute("APId").toString();
		}
	}
	
	/**
	 * 設定登入的系統Id
	 * @param value
	 * @throws Exception
	 * @author George_Tsai
	 * @version 2009/1/23
	 */
	public void setLoginAPId(String value) {
		removeSessionAttribute("APId");
		setSessionAttribute("APId", value);
	}
	
	/**
	 * 取得Sigle Sign On Id
	 * @return
	 * @throws Exception
	 * @author George_Tsai
	 * @version 2009/2/17
	 */
	public String getSSOId() {
		if(getSessionAttribute("SSOId") == null) {
			return "";
		} else {
			return getSessionAttribute("SSOId").toString();
		}
	}
	
	/**
	 * 設定Sigle Sign On Id
	 * @param value
	 * @throws Exception
	 * @author George_Tsai
	 * @version 2009/2/17
	 */
	public void setSSOId(String value) {
		removeSessionAttribute("SSOId");
		setSessionAttribute("SSOId", value);
	}
	
	/**
	 * 檢查是否登入系統
	 * @return
	 * @throws Exception
	 * @author George_Tsai
	 * @version 2009/1/23
	 */
	public boolean checkLoginAP() {
		boolean flag = true;
		
		if(getLoginAPId().equals("")) {
			flag = false;
		}
		
		return flag;
	}
	
	/**
	 * 取得登入者名稱
	 * @return
	 * @throws Exception
	 * @author George_Tsai
	 * @version 2009/2/6
	 * @throws ServiceException 
	 * @throws ServiceLockException 
	 */
	public String getUserName() throws ServiceException {
		return getUserName(getLoginUserId());
	}
	
	public abstract void setRequest(HttpServletRequest request);
	
	public abstract HttpServletRequest getRequest();
	
	public abstract void setResponse(HttpServletResponse response);
	
	public abstract HttpServletResponse getResponse();
	
	public abstract HttpSession getSession();
	
	public abstract void setServletContext(ServletContext context);
	
	public abstract ServletContext getServletContext();
	
	/**
	 * 取得MessageResources的對應文字
	 * 優先順序為,db
	 * 以系統環境變數sysLocale為優先，若沒有設定則以request的locale來設定，若都無法對應則取預設值(繁體中文)
	 * @param key
	 * @param param
	 * @return
	 * @throws Exception
	 * @author George_Tsai
	 * @version 2009/3/27
	 */
	public abstract String getMessage(String key, String[] param)throws NamingException, UnsupportedEncodingException;
	
	/**
	 * 取得使用者使用語系
	 * @param userId
	 * @return
	 * @throws Exception
	 * @author George_Tsai
	 * @version 2009/3/27
	 * @throws ServiceException 
	 * @throws ServiceLockException 
	 */
	public abstract String getUserLocale(String userId) throws ServiceException;
	
	/**
	 * 檢查是否有此系統的登入權
	 * @param strAPId
	 * @return
	 * @throws Exception
	 * @author George_Tsai
	 * @version 2009/2/6
	 * @throws ServiceException 
	 * @throws ServiceLockException 
	 */
	public abstract boolean checkAPPermission(String strAPId) throws ServiceException;
	
	/**
	 * 檢查是否有此功能的權限
	 * @return
	 * @throws Exception
	 * @author George_Tsai
	 * @version 2009/7/19
	 * @throws ServiceException 
	 * @throws ServiceLockException 
	 */
	public abstract boolean checkFunctionPermission() throws ServiceException;
	
	/**
	 * 取得function名稱
	 * @return
	 * @throws Exception
	 * @author George_Tsai
	 * @version 2009/7/19
	 * @throws ServiceException 
	 * @throws ServiceLockException 
	 */
	public abstract String getFunctionName() throws ServiceException;
	
	/**
	 * 取得使用者暱稱
	 * @param strUserId
	 * @return
	 * @throws Exception
	 * @author George_Tsai
	 * @version 2009/2/20
	 * @throws ServiceException 
	 * @throws ServiceLockException 
	 */
	public abstract String getUserName(String strUserId) throws ServiceException;
	
	/**
	 * 過濾取得有權限使用的Portlet名稱
	 * @param portletNames
	 * @return
	 * @throws Exception
	 * @author George_Tsai
	 * @version 2009/8/12
	 * @throws ServiceException 
	 * @throws ServiceLockException 
	 */
	public abstract List<String> getAuthorizedPortlets(List<String> portletNames) throws ServiceException;
	
	/**
	 * 從DB load出登入者的domain object
	 * @return
	 * @throws Exception
	 * @author George_Tsai
	 * @version 2009/8/19
	 * @throws ServiceException 
	 * @throws ServiceLockException 
	 */
	abstract public User getLoginUser() throws ServiceException;
	
	/**
	 * 把service layer的method鎖住
	 * @throws ServiceException
	 */
	abstract public void lockService() throws ServiceException;
	
	/**
	 * 把鎖住的service layer method開啟
	 * @throws ServiceException
	 */
	abstract public void enableService() throws ServiceException;
	
	/**
	 * 檢查service layer現在的狀態
	 * @throws ServiceLockException
	 */
	abstract public void checkServiceStatus() throws ServiceLockException;
	
	/**
	 * 回覆service layer現在的狀態
	 */
	abstract public boolean isServiceLock();
}
