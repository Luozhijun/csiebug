package csiebug.util;

import org.apache.commons.codec.DecoderException;
import org.springframework.security.Authentication;
import org.springframework.security.AuthenticationException;
import org.springframework.security.AuthenticationManager;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.GrantedAuthorityImpl;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.event.authentication.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;
import org.springframework.security.ui.FilterChainOrder;
import org.springframework.security.ui.SpringSecurityFilter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.util.Assert;

import csiebug.domain.DomainObjectFactory;
import csiebug.domain.Role;
import csiebug.domain.User;
import csiebug.service.ServiceException;
import csiebug.service.UserService;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.naming.NamingException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * RememberMe Filter
 * @author George_Tsai
 * @version 2009/11/24
 */
public class RememberMeFilter extends SpringSecurityFilter implements InitializingBean, ApplicationEventPublisherAware {
    private ApplicationEventPublisher eventPublisher;
    private AuthenticationManager authenticationManager;
    private WebUtility webutil = new WebUtility();
    private DomainObjectFactory domainObjectFactory;
    private UserService userService;
    private String loginSuccessURL;
    private String loginFormURL;
    private HashMap<String, String> filterVariable;
    
    public void setDomainObjectFactory(DomainObjectFactory domainObjectFactory) {
    	this.domainObjectFactory = domainObjectFactory;
    }
    
    public void setUserService(UserService userService) {
    	this.userService = userService;
    }
    
    public void setLoginSuccessURL(String loginSuccessURL) {
    	this.loginSuccessURL = loginSuccessURL;
    }
    
    public void setLoginFormURL(String loginFormURL) {
    	this.loginFormURL = loginFormURL;
    }
    
    public void setFilterVariable(HashMap<String, String> filterVariable) {
		this.filterVariable = filterVariable;
	}
    
    private void resetFilterVariable() {
		filterVariable.remove("URLForRemeberMe");
		filterVariable.remove("step");
	}
    
    public void afterPropertiesSet() throws Exception {
		Assert.notNull(authenticationManager, "authenticationManager must be specified");
	}

    public void doFilterHttp(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
    	AssertUtility.notNull(request);
    	AssertUtility.notNull(response);
    	AssertUtility.notNull(chain);
    	
    	try {
    		if(request.getRequestURI().equals("/" + webutil.getAPId() + "/index") && request.getParameter("ActFlag") != null && request.getParameter("ActFlag").equalsIgnoreCase("logout")) {
    			logoutProcess(request, response);
    		} else if(request.getRequestURI().equals("/" + webutil.getAPId() + "/login") && request.getParameter("ActFlag") != null && request.getParameter("ActFlag").equalsIgnoreCase("login")) {
    			afterLoginProcess(request, response);
    			
    			if(filterVariable != null && filterVariable.get("step") != null) {
    				if(((String)filterVariable.get("step")).equals("8")) {
	    				filterVariable.remove("step");
	    				filterVariable.put("step", "9");
	    			} else {
	    				resetFilterVariable();
	    			}
    			}
    		} else if((SecurityContextHolder.getContext().getAuthentication() == null || (SecurityContextHolder.getContext().getAuthentication().getAuthorities().length == 1 && SecurityContextHolder.getContext().getAuthentication().getAuthorities()[0].getAuthority().equalsIgnoreCase("ROLE_ANONYMOUS"))) && beforeLoginProcess(request, response)) {
    			//有發生轉址(表示做了cookie自動登入)
    			if(filterVariable != null && filterVariable.get("step") != null) {
    				if(filterVariable.get("step").equals("5")) {
    	    			filterVariable.remove("step");
    	    			filterVariable.put("step", "6");
    	    		} else {
    	    			resetFilterVariable();
    	    		}
        		}
    			
    			return;
    		}
    	} catch(Exception ex) {
    		try {
				//發生錯誤，不確定是否造成持續性無法登入
	    		//所以還是強迫將client端cookie清除
				clearCookie(response);
				
				//轉址到系統登入頁
				if(loginFormURL == null) {
					loginFormURL = "/" + StringUtility.cleanCRLF(webutil.getAPId()) + "/index";
				}
				
	            response.sendRedirect(response.encodeRedirectURL(loginFormURL));
	            
	            return;
			} catch (NamingException e) {
				webutil.writeErrorLog(e);
			}
			webutil.writeErrorLog(ex);
		}
    	
    	chain.doFilter(request, response);
    }
    
    
    /**
     * Called if a remember-me token is presented and successfully authenticated by the <tt>RememberMeServices</tt>
     * <tt>autoLogin</tt> method and the <tt>AuthenticationManager</tt>.
     */
    protected void onSuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			Authentication authResult) {
    }

    /**
     * Called if the <tt>AuthenticationManager</tt> rejects the authentication object returned from the
     * <tt>RememberMeServices</tt> <tt>autoLogin</tt> method. This method will not be called when no remember-me
     * token is present in the request and <tt>autoLogin</tt> returns null.
     */
    protected void onUnsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) {
    }
    
    public void setApplicationEventPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    public int getOrder() {
        return FilterChainOrder.REMEMBER_ME_FILTER;
    }
    
    //登出的處理
    private void logoutProcess(HttpServletRequest request, HttpServletResponse response) throws NamingException, ServiceException {
    	AssertUtility.notNull(request);
    	AssertUtility.notNull(response);
    	
    	//登出要把cookie清除
		removeDBCookieInfo(request);
		clearCookie(response);
    }
    
    //登入以後的處理
    //主要是第一次啟用cookie的時候,所需要處理的程式碼
    private void afterLoginProcess(HttpServletRequest request, HttpServletResponse response) throws ServiceException, AuthenticationException, InvalidKeyException, NumberFormatException, NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeySpecException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, NamingException, DecoderException, CookieException {
    	AssertUtility.notNull(request);
    	AssertUtility.notNull(response);
    	
    	Cookie[] cookies = request.getCookies();
		if(cookies != null) {
			for(int i = 0; i < cookies.length; i++) {
	            Cookie cookie = cookies[i];
	            
	            //當沒有cookie，但有勾選記憶帳號密碼時，新建一個cookie給client
	            if(cookie.getName().equals("createCookie") && !cookie.getValue().trim().equals("")) {
	            	String[] temp = cookie.getValue().split("&");
	            	
	            	if(temp.length == 3) {
		            	String rememberMe = temp[0];
		            	String userId = temp[1];
		        		String password = temp[2];
		        		
		        		if(rememberMe.equalsIgnoreCase("true")) {
		        			User voObj = domainObjectFactory.createUser();
			            	voObj.setId(userId);
			            	List<User> list = userService.searchUsers(voObj);
			            	if(list.size() == 1) {
			            		User user = list.get(0);
			            		
			            		if(authenticate(request, response, user, userId, password)) {
			            			//建立新的cookie
			            			createCookie(response, user, password, "" + Integer.parseInt(user.getAvailableCookieSeries()));
			            			
			            			//清掉temp cookie
			                		clearCookie(response, cookie.getName());
			            		}
			            	}
		        		}
	            	} else {
	            		//cookie的格式不符，是違法的cookie
	            		throw new CookieException("Invalid cookie!!");
	            	}
	        		
	            	break;
	            }
	        }
		}
    }
    
    //登入前的處理
    //主要是如果已經啟用了cookie的時候,要用cookie做自動登入
    private boolean beforeLoginProcess(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, InvalidKeyException, NumberFormatException, InvalidKeySpecException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException, NamingException, ServiceException, DecoderException, CookieException, IOException {
    	AssertUtility.notNull(request);
    	AssertUtility.notNull(response);
    	
    	boolean goThrough = false;
    	
    	Cookie[] cookies = request.getCookies();
		if(cookies != null) {
			for(int i = 0; i < cookies.length; i++) {
	            Cookie cookie = cookies[i];
	            
	            //當有cookie存在時就自動登入
	            if(cookie.getName().equals(webutil.getAPId())) {
	            	String[] rememberMe = cookie.getValue().split("&"); 
	            	
	            	if(rememberMe.length == 3) {
		            	String userId = rememberMe[0];
		            	String series = rememberMe[2];
		            	
		            	User voObj = domainObjectFactory.createUser();
		            	voObj.setId(userId);
		            	List<User> list = userService.searchUsers(voObj);
		            	if(list.size() == 1) {
		            		User user = list.get(0);
		            		
		            		if(user.getCookie(series) != null) {
				        		String password = DesCoder.decryptCode(rememberMe[1], user.getCookie(series).getToken());
				        		
				        		if(authenticate(request, response, user, userId, password)) {
				        			//用新的key加密,更新回去,讓client端每次存的密碼都不一樣,server端每次用的key也都不一樣
				        			createCookie(response, user, password, series);
				        			
				        			//認證完成做自動登入
				        			//登入首頁的話要轉址，其他網址在登入後繼續進入此畫面s
				        			if(request.getRequestURI().equals("/" + webutil.getAPId() + "/index")) {
				        				if(loginSuccessURL == null) {
				        					loginSuccessURL = "/" + StringUtility.cleanCRLF(webutil.getAPId()) + "/login?ActFlag=login";
							            }
					        				
					        			String targetURL = request.getContextPath() + loginSuccessURL;
						    	        response.sendRedirect(response.encodeRedirectURL(targetURL));
				        				
					    	            return true;
				        			}
				        		}
		            		} else {
			            		//server端無資料，而client端卻有資料
			            		throw new CookieException("Expired cookie!!");
		            		}
		            	}
	            	} else {
	            		//cookie的格式不符，是違法的cookie
	            		throw new CookieException("Invalid cookie!!");
	            	}
	            	
	            	break;
	            }
	        }
		}
		
		return goThrough;
    }
    
    //到authenticationManager做登入認證授權
    private boolean authenticate(HttpServletRequest request, HttpServletResponse response, User user, String userId, String password) throws AuthenticationException, InvalidKeyException, NumberFormatException, NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeySpecException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, NamingException, DecoderException, ServiceException, CookieException {
    	AssertUtility.notNull(request);
    	AssertUtility.notNull(response);
    	AssertUtility.notNull(user);
    	AssertUtility.notNullAndNotSpace(userId);
    	AssertUtility.notNull(password);
    	
    	if(ShaEncoder.getSHA256String(password).equals(user.getPassword())) {
			Iterator<Role> iterator = user.getAuthorities().iterator();
    		GrantedAuthority[] authorities = new GrantedAuthority[user.getAuthorities().size()];
			
			int j = 0;
			while(iterator.hasNext()) {
				authorities[j] = new GrantedAuthorityImpl(iterator.next().getId());
				j++;
			}
			
			Authentication rememberMeAuth = new UsernamePasswordAuthenticationToken(userId, password, authorities);
			
			// Attempt authenticaton via AuthenticationManager
			rememberMeAuth = authenticationManager.authenticate(rememberMeAuth);
			
			// Store to SecurityContextHolder
            SecurityContextHolder.getContext().setAuthentication(rememberMeAuth);

            onSuccessfulAuthentication(request, response, rememberMeAuth);
            
			request.getSession().setAttribute("SPRING_SECURITY_LAST_USERNAME", userId);
            
            // Fire event
            if (this.eventPublisher != null) {
                eventPublisher.publishEvent(new InteractiveAuthenticationSuccessEvent(
                        SecurityContextHolder.getContext().getAuthentication(), this.getClass()));
            }
            
    		//清除此使用者過期的Cookie
    		clearExpiredCookie(user);
		} else {
			//密碼不符
			throw new CookieException("Invalid cookie!!");
		}
    	
    	return true;
    }
    
    //將所有remember me login 相關的cookie紀錄清除
	private void clearCookie(HttpServletResponse response) throws NamingException {
		AssertUtility.notNull(response);
		
		clearCookie(response, "createCookie");
		clearCookie(response, webutil.getAPId());
	}
	
	//清除指定名稱的cookie
	private void clearCookie(HttpServletResponse response, String cookieName) throws NamingException {
		AssertUtility.notNull(response);
		AssertUtility.notNullAndNotSpace(cookieName);
		
		String cookieValue = "";
		Cookie rememberMeCookie = new Cookie(StringUtility.cleanCRLF(cookieName), cookieValue);
		rememberMeCookie.setMaxAge(0);
		response.addCookie(rememberMeCookie);
	}
	
	//logout的時候，表示捨棄這個cookie，把DB裡面這個series的cookie資訊也一併砍掉
	private void removeDBCookieInfo(HttpServletRequest request) throws NamingException, ServiceException {
		AssertUtility.notNull(request);
		
		Cookie[] cookies = request.getCookies();
		if(cookies != null) {
			for(int i = 0; i < cookies.length; i++) {
	            Cookie cookie = cookies[i];
	            if(cookie.getName().equals(webutil.getAPId())) {
	            	String[] rememberMe = cookie.getValue().split("&"); 
	            	
	            	if(rememberMe.length == 3) {
		            	String userId = rememberMe[0];
		            	String series = rememberMe[2];
		            	
		            	User voObj = domainObjectFactory.createUser();
		            	voObj.setId(userId);
		            	List<User> list = userService.searchUsers(voObj);
		            	if(list.size() == 1) {
		            		User user = list.get(0);
		            		csiebug.domain.Cookie dbCookieInfo = user.getCookie(series);
		            		user.removeCookie(dbCookieInfo);
		            		
		            		userService.deleteCookie(dbCookieInfo);
			        	}
	            	}
	            	
	            	break;
	            }
	        }
		}
	}
	
	//建立cookie
	private void createCookie(HttpServletResponse response, User loginUser, String password, String series) throws NamingException, InvalidKeyException, InvalidKeySpecException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException, DecoderException, ServiceException {
		AssertUtility.notNull(response);
		AssertUtility.notNull(loginUser);
		AssertUtility.notNull(password);
		AssertUtility.notNullAndNotSpace(series);
		
		//先取得系統設定的cookie有效秒數
    	int lifecycle = webutil.getCookieLifecycle() * 24 * 60 * 60;
    	String cookieName = webutil.getAPId();
		String cookieKey = StringUtility.makeRandomNumberKey(48);
		String cookieValue = loginUser.getId() + "&" + DesCoder.encryptCode(password, cookieKey) + "&" + series;
		
		Cookie rememberMeCookie = new Cookie(StringUtility.cleanCRLF(cookieName), StringUtility.cleanCRLF(cookieValue));
        rememberMeCookie.setMaxAge(lifecycle);
       
        response.addCookie(rememberMeCookie);
        
        //產生的cookieKey要存回DB,以供以後自動登入的時候,可以將密碼解回來
        if(loginUser.getCookie(series) == null) {
        	csiebug.domain.Cookie cookie = domainObjectFactory.createCookie();
        	cookie.setUserId(loginUser.getId());
        	cookie.setSeries(series);
        	cookie.setToken(cookieKey);
        	cookie.setLastUsed(Calendar.getInstance());
        	loginUser.addCookie(cookie);
        } else {
        	loginUser.getCookie(series).setToken(cookieKey);
        	loginUser.getCookie(series).setLastUsed(Calendar.getInstance());
        }
        
        userService.saveUser(loginUser);
	}
	
	//查到server端的cookie資料過期，清除過期資料期
	private void clearExpiredCookie(User loginUser) throws ServiceException, NamingException {
		AssertUtility.notNull(loginUser);
		
		List<csiebug.domain.Cookie> list = loginUser.getExpiredCookie(webutil.getCookieLifecycle());
		
		for(int i = 0; i < list.size(); i++) {
			loginUser.removeCookie(list.get(i));
			userService.deleteCookie(list.get(i));
		}
	}
}
