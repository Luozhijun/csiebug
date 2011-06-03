package csiebug.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.mpxj.ProjectFile;
import net.sf.mpxj.mspdi.MSPDIWriter;
import net.sf.mpxj.writer.ProjectWriter;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.util.ServletContextAware;
import org.jawin.COMException;
import org.jawin.DispatchPtr;
import org.jawin.win32.Ole32;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import csiebug.domain.AP;
import csiebug.domain.DomainObjectFactory;
import csiebug.domain.Function;
import csiebug.domain.User;
import csiebug.service.ServiceException;
import csiebug.service.ServiceLockException;
import csiebug.service.ServiceStatus;
import csiebug.service.UserService;
import csiebug.service.WebUtilityService;
import csiebug.web.html.office.project.ProjectUtility;

/**
 * Web AP常用功能
 * @author George_Tsai
 * @version 2009/3/27
 */

public class WebUtility extends AbstractWebUtility implements Serializable, ApplicationContextAware, ServletContextAware, ServletRequestAware, ServletResponseAware {
	private static final long serialVersionUID = 1L;
	
	private HttpServletRequest request = null;
	private HttpServletResponse response = null;
	private ServletContext sc = null;
	private ApplicationContext ac = null;
	
	private DomainObjectFactory domainObjectFactory;
	private WebUtilityService webUtilityService;
	private UserService userService;
	
	public void setDomainObjectFactory(DomainObjectFactory domainObjectFactory) {
		this.domainObjectFactory = domainObjectFactory;
	}
	
	public DomainObjectFactory getDomainObjectFactory() {
		return this.domainObjectFactory;
	}
	
	public void setWebUtilityService(WebUtilityService webUtilityService) {
		this.webUtilityService = webUtilityService;
	}
	
	public UserService getUserService() {
		return userService;
	}
	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	public void setServletRequest(HttpServletRequest request) {
		setRequest(request);
	}
	
	public void setServletResponse(HttpServletResponse response) {
		setResponse(response);
	}
	
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
	
	public HttpServletRequest getRequest() {
		if(request == null) {
			request = ServletActionContext.getRequest();
		}
		
		return request;
	}
	
	public void setResponse(HttpServletResponse response) {
		this.response = response;
//		this.response.setCharacterEncoding("UTF-8");
	}
	
	public HttpServletResponse getResponse() {
		if(response == null) {
			response = ServletActionContext.getResponse();
//			response.setCharacterEncoding("UTF-8");
		}
		
		return response;
	}
	
	public HttpSession getSession() {
		request = getRequest();
		
		AssertUtility.notNull(request);
		
		return request.getSession();
	}
	
	public void setServletContext(ServletContext context) {
		this.sc = context;
	}
	
	public ServletContext getServletContext() {
		if(sc == null) {
			sc = ServletActionContext.getServletContext();
		}
		
		return sc;
	}
	
	public void setApplicationContext(ApplicationContext applicationContext) {
		ac = applicationContext;
	}
	
	public ApplicationContext getApplicationContext() {
		if(ac == null) {
			sc = getServletContext();
			
			AssertUtility.notNull(sc);
			
			ac = WebApplicationContextUtils.getWebApplicationContext(sc);
		}
		
		return ac;
	}
	
	public void makePrePageURL() throws UnsupportedEncodingException, NamingException {
		request = getRequest();
		
		AssertUtility.notNull(request);
		
		if(!request.getRequestURI().endsWith("/index") && !request.getRequestURI().endsWith("/login")) {
			setSessionAttribute("PrePageURL", getRequestURLWithQueryString());
		}
	}
	
	public String getMessage(String key, String[] param) throws NamingException, UnsupportedEncodingException {
		AssertUtility.notNullAndNotSpace(key);
		
		String strMessage = "";
		
		ac = getApplicationContext();
		
		AssertUtility.notNull(ac);
		
		if(!getLoginUserLocale().equals("")) {
			String language = "";
			String country = "";
			Locale locale = null;
			
			if(getLoginUserLocale().split("_").length == 1) {
				language = getLoginUserLocale().split("_")[0];
				locale = new Locale(language);
			} else if(getLoginUserLocale().split("_").length == 2) {
				language = getLoginUserLocale().split("_")[0];
				country = getLoginUserLocale().split("_")[1];
				locale = new Locale(language, country);
			}
			
			strMessage = ac.getMessage(key, param, locale);
		} else {
			if(getSysLocale() != null) {
				strMessage = ac.getMessage(key, param, getSysLocale());
			} else if(ac.getMessage(key, param, getRequest().getLocale()) != null) {
				strMessage = ac.getMessage(key, param, getRequest().getLocale());
			}
		}
		
		if(strMessage == null) {
			String message = "Name " + key + " is not bound in MessageResources!!";
			getLogger().debug(message);
//			System.out.println(message);
			strMessage = "null";
		}
		
		return new String(strMessage.getBytes("ISO-8859-1"), "UTF-8");
	}
	
	public String getUserLocale(String userId) throws ServiceException {
		AssertUtility.notNullAndNotSpace(userId);
		
		User user = domainObjectFactory.createUser();
		user.setId(userId);
		
		return webUtilityService.getUserLocale(user);
	}
	
	public boolean checkAPPermission(String strAPId) throws ServiceException {
		AssertUtility.notNullAndNotSpace(strAPId);
		
		if(!checkLogin()) { //檢查是否登入
			return false;
		} else if(getLoginAPId().trim().equals(strAPId)) { //已經登入AP了,所以當然有權限
			return true;
		} else {
			User user = domainObjectFactory.createUser();
			user.setId(getLoginUserId());
			
			AP ap = domainObjectFactory.createAP();
			ap.setApId(strAPId);
			
			return webUtilityService.checkAPPermission(user, ap); 
		}
	}
	
	@Deprecated
	//spring security可以做登入的檢查，也可以做url權限的綁定
	//做function point的權限控管，似乎可以被取代了
	public boolean checkFunctionPermission() throws ServiceException {
		if(getFunctionId().equalsIgnoreCase("Index") || getFunctionId().equalsIgnoreCase("Login")) { //不檢查此功能
			return true;
		} else if(!checkLogin()) { //檢查是否登入
			return false;
		} else if(!checkLoginAP()) { //檢查是否登入AP
			return false;
		} else {
			User user = domainObjectFactory.createUser();
			user.setId(getLoginUserId());
			
			Function function = domainObjectFactory.createFunction();
			function.setApId(getLoginAPId());
			function.setFunctionId(getFunctionId());
			
			return webUtilityService.checkFunctionPermission(user, function); 
		}
	}
	
	public String getFunctionName() throws ServiceException {
		String strFunctionName = "";
		
		if(AssertUtility.isNotNullAndNotSpace(getFunctionId())) {
			Function function = domainObjectFactory.createFunction();
			function.setApId(getLoginAPId());
			function.setFunctionId(getFunctionId());
			
			return webUtilityService.getFunctionName(function);
		}
		
		return strFunctionName;
	}
	
	public String getUserName(String userId) throws ServiceException {
		AssertUtility.notNullAndNotSpace(userId);
		
		User user = domainObjectFactory.createUser();
		user.setId(userId);
		
		return webUtilityService.getUserName(user);
	}
	
	public List<String> getAuthorizedPortlets(List<String> portletNames) throws ServiceException {
		User user = domainObjectFactory.createUser();
		user.setId(getLoginUserId());
		
		return webUtilityService.getAuthorizedPortlets(user, portletNames);
	}
	
	public List<String> getVisiblePortlets(String dashboardId, List<String> portletNames) throws ServiceException {
		AssertUtility.notNullAndNotSpace(dashboardId);
		
		User user = domainObjectFactory.createUser();
		user.setId(getLoginUserId());
		
		return webUtilityService.getSortedPortlets(user, dashboardId, webUtilityService.getVisiblePortlets(user, dashboardId, portletNames));
	}
	
	public User getLoginUser() throws ServiceException {
		User loginUser = null;
		
		User voObj = getDomainObjectFactory().createUser();
		voObj.setId(getLoginUserId());
		
		List<User> users = userService.searchUsers(voObj);
		
		if(users.size() == 1) {
			loginUser = users.get(0);
		}
		
		return loginUser;
	}
	
	public void lockService() throws ServiceException {
		webUtilityService.setServiceStatus(ServiceStatus.LOCK);
	}
	
	public void enableService() throws ServiceException {
		webUtilityService.setServiceStatus(ServiceStatus.ENABLE);
	}
	
	public void checkServiceStatus() throws ServiceLockException {
		webUtilityService.checkServiceStatus();
	}
	
	public boolean isServiceLock() {
		return webUtilityService.isServiceLock();
	}

	public void makeQRCodeImageFile(String value, int width, int height) throws Exception {
		getResponse().setContentType("image/gif");  
		ServletOutputStream sos = getResponse().getOutputStream();
		
		QRCodeWriter writer = new QRCodeWriter();
		BitMatrix bitMatrix = writer.encode(getRequestValue("QRCodeValue"), BarcodeFormat.QR_CODE, width, height);
		MatrixToImageWriter.writeToStream(bitMatrix, "gif", sos);
	}
	
	public void makeQRCodeImageFile(String value) throws Exception {
		makeQRCodeImageFile(value, 70, 70);
	}

	@SuppressWarnings("unchecked")
	public void downloadProject(String fileName, String tableId, String data, String encoding, String fileType) throws Exception {
		List<Map<String, String>> tasks = (List<Map<String, String>>)getSessionAttribute(data);
		
		if(tasks == null) { //存在session中的資料過期,download功能無法正常運作
			PrintWriter out = getResponse().getWriter();
		    
		    out.println("<script language=\"javascript\">\n");
		    out.println("alert('" + new String(getMessage("common.SessionTimeOut").getBytes("UTF-8"), "ISO-8859-1") + "');\n");
		    out.println("parent.parent.parent.location.href = \"" + getSysURL() + "\";\n");
		    out.println("</script>\n");
		} else {
			if(fileType != null && fileType.trim().equalsIgnoreCase("mspdi")) {
				downloadProjectUsingMPXJ(fileName, encoding, tasks);
			} else {
				downloadProjectUsingJawin(fileName, encoding, tasks);
			}
		}
	}
	
	/**
	 * 目前MPXJ寫的部分還不支援寫出MPP,只能產出舊版的Project檔案(MPX),或是XML格式檔案
	 * 還需注意官網更新 http://mpxj.sourceforge.net
	 * @param fileName
	 * @param encoding
	 * @param tasks
	 * @throws DateFormatException
	 * @throws ParseException
	 * @throws NamingException
	 * @throws IOException
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	private void downloadProjectUsingMPXJ(String fileName, String encoding, List<Map<String, String>> tasks) throws DateFormatException, ParseException, NamingException, IOException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		ProjectFile project = ProjectUtility.toProjectFile(tasks, Integer.parseInt(getSysDateFormat()));
		
		getResponse().reset();
		getResponse().setContentType("application/vnd.ms-project");
		if(AssertUtility.isNotNullAndNotSpace(fileName)) {
			getResponse().setHeader("Content-disposition", "attachment; filename=\"" + StringUtility.cleanCRLF(fileName.replace(".mpp", ".xml")) + "\"");
		}
		if(AssertUtility.isNotNullAndNotSpace(encoding)) {
			getResponse().setCharacterEncoding(encoding);
		}
		
		ServletOutputStream sos = getResponse().getOutputStream();
		
		ProjectWriter writer = new MSPDIWriter();
		writer.write(project, sos);
		
		sos.flush();
	    sos.close();
	}
	
	/**
	 * 用native的方式,呼叫server本機上的com元件,來寫MPP檔案
	 * 此法需要server端上裝有Microsoft Project
	 * 並將jawin.dll放到system32目錄
	 * @param fileName
	 * @param encoding
	 * @param tasks
	 * @throws COMException
	 * @throws DateFormatException
	 * @throws ParseException
	 * @throws NamingException
	 * @throws IOException
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	private void downloadProjectUsingJawin(String fileName, String encoding, List<Map<String, String>> tasks) throws COMException, DateFormatException, ParseException, NamingException, IOException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		Ole32.CoInitialize();
		DispatchPtr projectApplication = new DispatchPtr("MSProject.Application");
		DispatchPtr msprojects = (DispatchPtr)projectApplication.get("Projects");
		DispatchPtr msproject = (DispatchPtr)msprojects.invoke("Add");
		DispatchPtr mstasks = (DispatchPtr)msproject.get("Tasks"); 

		ProjectUtility.putDispatchPtr(mstasks, tasks, Integer.parseInt(getSysDateFormat()));
		
		//檔案存檔(這要當作temp file, download程序完成要刪除)
		String tempFilePath = "d:\\" + getSession().getId() + ".mpp";
		msproject.invoke("SaveAs", tempFilePath); 
		projectApplication.invoke("DocClose");
		Ole32.CoUninitialize(); 
		
		getResponse().reset();
		getResponse().setContentType("application/vnd.ms-project");
		if(AssertUtility.isNotNullAndNotSpace(fileName)) {
			getResponse().setHeader("Content-disposition", "attachment; filename=\"" + StringUtility.cleanCRLF(fileName) + "\"");
		}
		if(AssertUtility.isNotNullAndNotSpace(encoding)) {
			getResponse().setCharacterEncoding(encoding);
		}
		
		ServletOutputStream sos = getResponse().getOutputStream();
		File tempFile = new File(tempFilePath);
		FileInputStream fis = new FileInputStream(tempFile);
		
		try {
			byte[] data = new byte[1];
			while(fis.read(data) != -1) { 
				sos.write(data); 
		    }
			sos.flush();
		} finally {
			sos.close();
		    fis.close();
		    
		    if(!tempFile.delete()) {
		    	getLogger(this.getClass().getName()).warn(tempFile.getName() + " was deleted failed!");
		    }
		}
	}
}
