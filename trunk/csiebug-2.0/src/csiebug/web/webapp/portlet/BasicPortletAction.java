/**
 * 
 */
package csiebug.web.webapp.portlet;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.naming.NamingException;
import javax.portlet.PortletContext;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletSession;

import org.apache.struts2.portlet.PortletApplicationMap;
import org.apache.struts2.portlet.PortletRequestMap;
import org.apache.struts2.portlet.PortletSessionMap;
import org.apache.struts2.portlet.context.PortletActionContext;
import org.apache.struts2.portlet.interceptor.PortletContextAware;
import org.apache.struts2.portlet.interceptor.PortletPreferencesAware;
import org.apache.struts2.portlet.interceptor.PortletRequestAware;
import org.apache.struts2.portlet.interceptor.PortletResponseAware;

import csiebug.domain.User;
import csiebug.service.ServiceException;
import csiebug.util.AssertUtility;
import csiebug.util.InvalidInputException;
import csiebug.util.WebUtility;

/**
 * @author George_Tsai
 * @version 2010/1/11
 */
public abstract class BasicPortletAction extends WebUtility implements PortletRequestAware, PortletResponseAware, PortletContextAware, PortletPreferencesAware {
	private static final long serialVersionUID = 1L;
	
	public static final String SUCCESS = "success";
	public static final String ERROR = "error";
	public static final String SUSPEND = "suspend";
	public static final String INPUT = "input";
	public static final String LOGIN = "login";
	public static final String NONE = "none";
	public static final String FORMLOAD = "formload";
	public static final String NOPERMISSION = "nopermission";
	public static final String REDIRECT = "redirect";
	public static final String ADD = "add";
	public static final String UPDATE = "update";
	public static final String DELETE = "delete";
	public static final String VIEW = "view";
	public static final String EDIT = "edit";
	public static final String READONLY = "readonly";
	
	private PortletRequest portletRequest = null;
	private PortletResponse portletResponse = null;
	private PortletContext portletContext = null;
	private PortletPreferences portletPreferences = null;
	
	public String execute() {
		try {
			resetPageLoadMessage();
			resetPageLoadScript();
			
			if(validateInput()) {
				return main();
			} else {
				throw new InvalidInputException("Invalid input data!!");
			}
		} catch(InvalidInputException iiex) {
			writeErrorLog(iiex);
			
			try {
				addPageLoadWarningDialog(getMessage("common.error.InvalidInput"));
				return FORMLOAD;
			} catch(Exception ex) {
				writeErrorLog(ex);
				return ERROR;
			}
		} catch(Exception ex) { //發生錯誤寫log
			ex.printStackTrace();
			writeErrorLog(ex);
			return ERROR;
		}
	}
	
	/**
	 * 無權限,導回登入頁,並提示訊息
	 * @return
	 * @throws Exception
	 * @author George_Tsai
	 * @version 2009/3/12
	 * @throws UnsupportedEncodingException 
	 */
	public String getNoPermissionForward() throws NamingException, UnsupportedEncodingException {
	    return getNoPermissionForward(getMessage("common.NoPermissionForwardToLogin"));
	}
	
	/**
	 * 無權限,導回前頁,並提示訊息
	 * @return
	 * @throws Exception
	 * @author George_Tsai
	 * @version 2009/11/25
	 * @throws UnsupportedEncodingException 
	 */
	public String getNoPermissionForwardPrePage(String prePageURL) throws NamingException, UnsupportedEncodingException {
	    return getNoPermissionForward(getMessage("common.NoPermission"), prePageURL);
	}
	
	/**
	 * TimeOut,導回登入頁,並提示訊息
	 * @return
	 * @throws Exception
	 * @author George_Tsai
	 * @version 2009/3/12
	 * @throws UnsupportedEncodingException 
	 */
	public String getSessionTimeOutForward() throws NamingException, UnsupportedEncodingException {
	    return getNoPermissionForward(getMessage("common.SessionTimeOut"));
	}
	
	/**
	 * 無權限,導回登入頁,並提示自訂訊息
	 * @param msg
	 * @return
	 * @throws Exception
	 * @author George_Tsai
	 * @version 2009/2/17
	 * @throws NamingException 
	 */
	public String getNoPermissionForward(String msg) throws NamingException {
//		PrintWriter out = getResponse().getWriter();
//	    
//	    out.println("<script language=\"javascript\">\n");
//	    out.println("alert('" + new String(msg.getBytes("UTF-8"), "ISO-8859-1") + "');\n");
//	    out.println("parent.parent.parent.location.href = \"" + getSysURL() + "\";\n");
//	    out.println("</script>\n");
//	    
//	    return null;
		
		addPageLoadMessage(msg);
		addPageLoadScript("parent.parent.parent.location.href = \"" + getSysURL() + "\";");
		
		return NOPERMISSION;
	}
	
	/**
	 * 無權限,導回前一頁,並提示自訂訊息
	 * @param msg
	 * @param prePageURL
	 * @return
	 * @throws Exception
	 * @author George_Tsai
	 * @version 2009/11/25
	 * @throws NamingException 
	 */
	public String getNoPermissionForward(String msg, String prePageURL) throws NamingException {
//		PrintWriter out = getResponse().getWriter();
//	    
//	    out.println("<script language=\"javascript\">\n");
//	    out.println("alert('" + new String(msg.getBytes("UTF-8"), "ISO-8859-1") + "');\n");
//	    out.println("parent.parent.parent.location.href = \"" + prePageURL + "\";\n");
//	    out.println("</script>\n");
//	    
//	    return null;
		
		addPageLoadMessage(msg);
		addPageLoadScript("parent.parent.parent.location.href = \"" + prePageURL + "\";");
		
		return NOPERMISSION;
	}
	
	abstract public String main() throws Exception;
	
	public void setPortletRequest(PortletRequest arg0) {
		portletRequest = arg0;
	}
	
	public PortletRequest getPortletRequest() {
		if(portletRequest == null) {
			portletRequest = PortletActionContext.getRequest();
		}
		
		return portletRequest;
	}
	
	public void setPortletResponse(PortletResponse arg0) {
		portletResponse = arg0;
	}
	
	public PortletResponse getPortletResponse() {
		if(portletResponse == null) {
			portletResponse = PortletActionContext.getResponse();
		}
		
		return portletResponse;
	}
	
	public PortletSession getPortletSession() {
		portletRequest = getPortletRequest();
		
		AssertUtility.notNull(portletRequest);
		
		return portletRequest.getPortletSession();
	}

	public void setPortletContext(PortletContext arg0) {
		portletContext = arg0;
	}
	
	public PortletContext getPortletContext() {
		if(portletContext == null) {
			portletContext = PortletActionContext.getPortletContext();
		}
		
		return portletContext;
	}
	
	public PortletRequestMap getPortletRequestMap() {
		portletRequest = getPortletRequest();
		
		AssertUtility.notNull(portletRequest);
		
		return new PortletRequestMap(portletRequest);
	}
	
	public PortletSessionMap getPortletSessionMap() {
		portletRequest = getPortletRequest();
		
		AssertUtility.notNull(portletRequest);
		
		return new PortletSessionMap(portletRequest);
	}
	
	public PortletApplicationMap getPortletApplicationMap() {
		portletContext = getPortletContext();
		
		AssertUtility.notNull(portletContext);
		
		return new PortletApplicationMap(portletContext);
	}
	
	public String getPortletRequestValue(String name) {
		AssertUtility.notNullAndNotSpace(name);
		
		portletRequest = getPortletRequest();
		
		AssertUtility.notNull(portletRequest);
		
		String strValue = "";
		
		if(portletRequest.getParameter(name) != null) {
			strValue = portletRequest.getParameter(name);
		}
		
		return strValue;
	}
	
	public Object getPortletRequestAttribute(String name) {
		AssertUtility.notNullAndNotSpace(name);
		
		portletRequest = getPortletRequest();
		
		AssertUtility.notNull(portletRequest);
		
		return portletRequest.getAttribute(name);
	}
	
	public void setPortletRequestAttribute(String name, Object obj) {
		AssertUtility.notNullAndNotSpace(name);
		
		portletRequest = getPortletRequest();
		
		AssertUtility.notNull(portletRequest);
		
		portletRequest.setAttribute(name, obj);
	}
	
	public void removePortletRequestAttribute(String name) {
		AssertUtility.notNullAndNotSpace(name);
		
		portletRequest = getPortletRequest();
		
		AssertUtility.notNull(portletRequest);
		
		portletRequest.removeAttribute(name);
	}
	
	public Object getPortletSessionAttribute(String name) {
		AssertUtility.notNullAndNotSpace(name);
		
		PortletSession portletSession = getPortletSession();
		
		AssertUtility.notNull(portletSession);
		
		return portletSession.getAttribute(name);
	}
	
	public void setPortletSessionAttribute(String name, Object obj) {
		AssertUtility.notNullAndNotSpace(name);
		
		PortletSession portletSession = getPortletSession();
		
		AssertUtility.notNull(portletSession);
		
		portletSession.setAttribute(name, obj);
	}
	
	public void removePortletSessionAttribute(String name) {
		AssertUtility.notNullAndNotSpace(name);
		
		PortletSession portletSession = getPortletSession();
		
		AssertUtility.notNull(portletSession);
		
		portletSession.removeAttribute(name);
	}
	
	public Object getPortletAppAttribute(String name) {
		AssertUtility.notNullAndNotSpace(name);
		
		portletContext = getPortletContext();
		
		AssertUtility.notNull(portletContext);
		
		return portletContext.getAttribute(name);
	}
	
	public void setPortletAppAttribute(String name, Object obj) {
		AssertUtility.notNullAndNotSpace(name);
		
		portletContext = getPortletContext();
		
		AssertUtility.notNull(portletContext);
		
		portletContext.setAttribute(name, obj);
	}
	
	public void removePortletAppAttribute(String name) {
		AssertUtility.notNullAndNotSpace(name);
		
		portletContext = getPortletContext();
		
		AssertUtility.notNull(portletContext);
		
		portletContext.removeAttribute(name);
	}

	public void setPortletPreferences(PortletPreferences arg0) {
		portletPreferences = arg0;
	}
	
	public PortletPreferences getPortletPreferences() {
		return portletPreferences;
	}
	
	public String getPortletUserId() {
		portletRequest = getPortletRequest();
		
		AssertUtility.notNull(portletRequest);
		
		return portletRequest.getRemoteUser();
	}
	
	public User getPortletUser() throws ServiceException {
		User loginUser = null;
		
		User voObj = getDomainObjectFactory().createUser();
		voObj.setId(getPortletUserId());
		
		List<User> users = getUserService().searchUsers(voObj);
		
		if(users.size() == 1) {
			loginUser = users.get(0);
		}
		
		return loginUser;
	}
	
	public String getPortletWindowId() {
		portletRequest = getPortletRequest();
		
		AssertUtility.notNull(portletRequest);
		
		String windowId = "";
		
		String[] windowIdInfo = portletRequest.getWindowID().split("\\|");
		
		windowId = windowIdInfo[windowIdInfo.length - 1];
		
		return windowId;
	}
}
