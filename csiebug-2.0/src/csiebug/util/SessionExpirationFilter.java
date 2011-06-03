package csiebug.util;

import org.springframework.beans.factory.InitializingBean;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;

import javax.naming.NamingException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SessionExpirationFilter implements Filter, InitializingBean {
	private String expiredURL;
	private String loginURL;
	private String logoutURL;
	private String loginFormURL;
	private String loginProcessURL;
	private HashMap<String, String> filterVariable;
	
	HttpServletRequest httpRequest;
    HttpServletResponse httpResponse;
    HttpSession session;
	private WebUtility webutil = new WebUtility();
	
	@Override
	public void destroy() {
		
	}
	
	private void init(ServletRequest request, ServletResponse response) {
		AssertUtility.notNull(request);
		AssertUtility.notNull(response);
		
		httpRequest = (HttpServletRequest) request;
        httpResponse = (HttpServletResponse) response;
        session = httpRequest.getSession(false);
        
		//若沒有設定login,logout,loginForm頁面,則用預設值
    	if(loginURL == null) {
    		try {
    			loginURL = "/" + webutil.getAPId() + "/j_spring_security_check";
    		} catch(Exception ex) {
    			webutil.writeErrorLog(ex);
    		}
    	}
    	
    	if(logoutURL == null) {
    		try {
    			logoutURL = "/" + webutil.getAPId() + "/j_spring_security_logout";
    		} catch(Exception ex) {
        		webutil.writeErrorLog(ex);
    		}
    	}
    	
    	if(loginFormURL == null) {
    		try {
    			loginFormURL = "/" + webutil.getAPId() + "/index";
    		} catch(Exception ex) {
    			webutil.writeErrorLog(ex);
    		}
    	}
    	
    	if(loginProcessURL == null) {
    		try {
    			loginProcessURL = "/" + webutil.getAPId() + "/login";
    		} catch(Exception ex) {
    			webutil.writeErrorLog(ex);
    		}
    	}
    }
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		AssertUtility.notNull(request);
		AssertUtility.notNull(response);
		AssertUtility.notNull(chain);
		
		init(request, response);
		
		//session過期的錯誤,需要過濾掉存取login和logout頁面,否則會很奇怪
        if(session == null && httpRequest.getRequestedSessionId() != null && !httpRequest.isRequestedSessionIdValid() && !httpRequest.getRequestURI().equals(loginURL) && !httpRequest.getRequestURI().equals(logoutURL) && !httpRequest.getRequestURI().equals(loginFormURL) && !httpRequest.getRequestURI().equals(loginProcessURL)) {
        	String targetURL = httpRequest.getContextPath() + expiredURL;
	        httpResponse.sendRedirect(httpResponse.encodeRedirectURL(targetURL));
	        
	        return;
        }
        
        if(filterVariable != null) {
        	if(filterVariable.get("step") != null) {
	        	if((filterVariable.get("step").equals("2") && httpRequest.getRequestURI().equals(loginFormURL))) {
	        		filterVariable.remove("step");
	        		filterVariable.put("step", "3");
	        	} else if((filterVariable.get("step").equals("6") && httpRequest.getRequestURI().equals(loginProcessURL))) {
	        		filterVariable.remove("step");
	        		filterVariable.put("step", "7");
	        	} else if(filterVariable.get("step").equals("9")) {
	        		httpResponse.sendRedirect(httpResponse.encodeRedirectURL(filterVariable.get("URLForRemeberMe")));
		            
					resetFilterVariable();
					
		            return;
	        	} else {
	        		resetFilterVariable();
	        	}
        	} else {
        		if(!httpRequest.getRequestURI().equals(loginURL) && !httpRequest.getRequestURI().equals(logoutURL) && !httpRequest.getRequestURI().equals(loginFormURL) && !httpRequest.getRequestURI().equals(loginProcessURL)) {
        		   filterVariable.put("URLForRemeberMe", getRequestURLWithQueryString());
	        		filterVariable.put("step", "1");
	        	}
        	}
        }
        
        chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		
	}
	
	public String getRequestURLWithQueryString() {
		StringBuffer prePageURL = httpRequest.getRequestURL();
			
		Enumeration<String> parameterNames = httpRequest.getParameterNames();
		int i = 0;
		while(parameterNames.hasMoreElements()) {
			String parameterName = parameterNames.nextElement();
			
			if(i == 0) {
				prePageURL.append("?" + parameterName + "=" + httpRequest.getParameter(parameterName));
				i++;
			} else {
				prePageURL.append("&" + parameterName + "=" + httpRequest.getParameter(parameterName));
			}
		}
		
		return prePageURL.toString();
	}
	
	public void setExpiredURL(String expiredURL) {
		this.expiredURL = expiredURL;
    }
	
	public void setLoginURL(String loginURL) throws NamingException  {
		this.loginURL = "/" + webutil.getAPId() + "/" + loginURL;
    }
	
	public void setLogoutURL(String logoutURL) throws NamingException {
		this.logoutURL = "/" + webutil.getAPId() + "/" + logoutURL;
    }
	
	public void setLoginFormURL(String loginFormURL) throws NamingException  {
		this.loginFormURL = "/" + webutil.getAPId() + "/" + loginFormURL;
    }
	
	public void setLoginProcessURL(String loginProcessURL) throws NamingException  {
		this.loginProcessURL = "/" + webutil.getAPId() + "/" + loginProcessURL;
    }
	
	public void setFilterVariable(HashMap<String, String> filterVariable) {
		this.filterVariable = filterVariable;
	}
	
	private void resetFilterVariable() {
		filterVariable.remove("URLForRemeberMe");
		filterVariable.remove("step");
	}
	
}
